package ru.mail.park.highload

import kotlinx.coroutines.experimental.launch
import kotlinx.coroutines.experimental.newFixedThreadPoolContext
import ru.mail.park.highload.models.*
import java.io.BufferedOutputStream
import java.io.File
import java.net.ServerSocket
import java.net.Socket
import java.nio.charset.Charset

class Server(private val port: Int, numberOfThreads: Int, private val rootPathName: String) {
    private val threadPoolContext = newFixedThreadPoolContext(numberOfThreads, "highload-server")

    fun start() {
        val server = ServerSocket(port)
        while (true) {
            val socket = server.accept()
            launch(threadPoolContext) {
                socket.use {
                    processRequest(it)
                }
            }
        }
    }

    private fun processRequest(socket: Socket) {
        val request = Request(socket.getInputStream())

        val response = try {
            getResponse(request)
        } catch (serverException: ServerException) {
            Response(serverException)
        }

        BufferedOutputStream(socket.getOutputStream()).use { outputStream ->
            outputStream.write(response.getHeaders().toByteArray(Charset.forName("UTF-8")))
            if (request.requestMethod != RequestMethod.HEAD) {
                response.file?.inputStream().use { it?.copyTo(outputStream) }
            }
        }
    }

    private fun getResponse(request: Request): Response {
        if (request.requestMethod == RequestMethod.UNKNOWN) {
            throw MethodNotAllowed()
        }

        if (request.path.contains("../")) {
            throw Forbidden()
        }

        val file = File(rootPathName, request.path)
        if (!file.exists()) {
            if (request.isIndex) {
                throw Forbidden()
            }
            throw NotFound()
        }

        return Response(200, "OK", file)
    }
}