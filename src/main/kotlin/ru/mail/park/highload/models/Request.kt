package ru.mail.park.highload.models

import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader
import java.net.URLDecoder

class Request(inputStream: InputStream) {
    val requestMethod: RequestMethod
    val path: String
    var isIndex = false

    init {
        val reader = BufferedReader(InputStreamReader(inputStream))
        val request = reader.readLine().split(" ")
        requestMethod = RequestMethod.valueOfString(request[0])
        var pathStr = if (request.size > 1) request[1].substringBefore("?") else ""
        pathStr = URLDecoder.decode(pathStr, "UTF-8")
        if (pathStr.endsWith("/") || pathStr.isEmpty()) {
            pathStr += "index.html"
            isIndex = true
        }
        path = pathStr
    }
}