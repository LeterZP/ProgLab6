package io

import elements.City
import kotlinx.serialization.json.Json
import java.io.BufferedInputStream
import java.io.BufferedWriter
import java.io.FileInputStream
import java.io.FileWriter
import java.util.Stack
import java.util.logging.Handler
import java.util.logging.Level
import java.util.logging.Logger
import java.util.logging.SimpleFormatter
import java.util.logging.StreamHandler

class IOManager {
    val logger: Logger = Logger.getLogger("server")

    init {
        logger.parent.handlers[0].formatter = StandardFormatter()
    }

    fun readJsonFile(file: String): Stack<City> {
        val reader = BufferedInputStream(FileInputStream(file))
        val text = reader.readAllBytes().decodeToString()
        reader.close()
        val decodedStack: Stack<City> = Stack<City>()
        if (text != "") {
            val decodedList: List<City> = Json.decodeFromString<List<City>>(text)
            for (element in decodedList) {
                decodedStack.push(element)
            }
        }
        return decodedStack
    }

    fun print(s: String) {
        println(s)
    }

    fun writeJsonFile(file: String, stack: Stack<City>) {
        val listToEncode: List<City> = stack.toList()
        val text: String = Json.encodeToString(listToEncode)
        val writer = BufferedWriter(FileWriter(file))
        writer.write(text)
        writer.close()
    }

    fun readLocalCommands(): String {
        return readln()
    }
}