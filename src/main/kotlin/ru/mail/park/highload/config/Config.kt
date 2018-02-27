package ru.mail.park.highload.config

import java.io.File
import java.nio.file.Files

class Config private constructor(configFile: File) {
    private var properties = HashMap<String, String>()

    companion object {
        fun read(): Config {
            return Config(File("/etc/httpd.conf"))
        }
    }

    init {
        Files.readAllLines(configFile.toPath())
                .map { it.split(" ") }
                .forEach { properties[it[0]] = it[1] }
    }

    operator fun get(key: String): String? {
        return properties[key]
    }
}