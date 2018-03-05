package ru.mail.park.highload.models

open class ClientException(val code: Int, val description: String) : RuntimeException()

class Forbidden : ClientException(403, "Forbidden")
class NotFound : ClientException(404, "Not Found")
class MethodNotAllowed : ClientException(405, "Method Not Allowed")
class TooManyRequests : ClientException(429, "Too Many Requests")