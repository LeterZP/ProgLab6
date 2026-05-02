package io

import java.util.logging.LogRecord
import java.util.logging.SimpleFormatter

class StandardFormatter(): SimpleFormatter() {
    override fun format(record: LogRecord?): String {
        println(super.format(record))
        val result = super.format(record).replace("\n", "k")
        return result + "\n"
    }
}