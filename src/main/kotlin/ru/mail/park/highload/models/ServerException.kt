package ru.mail.park.highload.models

open class ServerException(val code: Int, val description: String) : RuntimeException()

class Forbidden : ServerException(403, "Forbidden")
class NotFound : ServerException(404, "Not Found")
class MethodNotAllowed : ServerException(405, "Method Not Allowed")