package io

import java.util.logging.LogRecord
import java.util.logging.SimpleFormatter

class StandardFormatter(): SimpleFormatter() {
    override fun format(record: LogRecord?): String {
        val result = super.format(record)
        return result.split("\n")[1] + "\n"
    }
}