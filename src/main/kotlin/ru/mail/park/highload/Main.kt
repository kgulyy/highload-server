package ru.mail.park.highload

import ru.mail.park.highload.config.Config
import java.io.File

fun main(args: Array<String>) {
    val config = Config.read()

    val port = config["listen"]!!.toInt()
    val numberOfThreads = config["cpu_limit"]!!.toInt()
    val rootPathName = config["document_root"]!!
    val absoluteRootFile = File(rootPathName).absoluteFile
    val availableCPUs = Runtime.getRuntime().availableProcessors()

    println("Server is starting on port: $port")
    println("Available $availableCPUs CPUs, using - $numberOfThreads")
    println("Static root path: $absoluteRootFile")

    Server(port, numberOfThreads, rootPathName).start()
}