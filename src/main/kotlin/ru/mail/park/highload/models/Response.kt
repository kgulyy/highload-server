package ru.mail.park.highload.models

import ru.mail.park.highload.getMimeType
import ru.mail.park.highload.toFormattedString
import java.io.File
import java.util.*

class Response(private val code: Int, private val status: String = "UNKNOWN", val file: File? = null) {

    constructor(exception: ClientException) : this(exception.code, exception.description)

    fun getHeaders(): String {
        var defaultHeader = "HTTP/1.1 $code $status\r\n" +
                "Date: ${Date().toFormattedString()}\r\n" +
                "Server: highload-server\r\n" +
                "Connection: Close\r\n"

        if (file != null && file.exists()) {
            defaultHeader += "Content-Length: ${file.length()}\r\n" +
                    "Content-Type: ${file.getMimeType()}\r\n\r\n"
        }
        return defaultHeader
    }

    override fun toString(): String {
        return getHeaders() + file?.absoluteFile
    }
}