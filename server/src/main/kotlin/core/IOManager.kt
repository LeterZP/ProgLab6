package core

import elements.City
import kotlinx.serialization.json.Json.Default.decodeFromString
import kotlinx.serialization.json.Json.Default.encodeToString
import java.io.BufferedInputStream
import java.io.BufferedWriter
import java.io.FileInputStream
import java.io.FileWriter
import java.util.Stack

class IOManager {

    fun readJsonFile(file: String): Stack<City> {
        val reader = BufferedInputStream(FileInputStream(file))
        val text = reader.readAllBytes().decodeToString()
        reader.close()
        val decodedStack: Stack<City> = Stack<City>()
        if (text != "") {
            val decodedList: List<City> = decodeFromString<List<City>>(text)
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
        val text: String = encodeToString(listToEncode)
        val writer = BufferedWriter(FileWriter(file))
        writer.write(text)
        writer.close()
    }

    fun readLocalCommands(): String {
        return readln()
    }
}