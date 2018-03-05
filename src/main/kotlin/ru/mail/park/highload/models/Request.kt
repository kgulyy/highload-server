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
        val inputLine = reader.readLine() ?: throw TooManyRequests()

        val requestParams = inputLine.split(" ")
        requestMethod = RequestMethod.valueOfString(requestParams[0])
        var pathStr = if (requestParams.size > 1) requestParams[1].substringBefore("?") else ""
        pathStr = URLDecoder.decode(pathStr, "UTF-8")
        if (pathStr.endsWith("/") || pathStr.isEmpty()) {
            pathStr += "index.html"
            isIndex = true
        }
        path = pathStr
    }
}