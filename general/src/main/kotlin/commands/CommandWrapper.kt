package commands

import exceptions.InvalidElementValueException
import kotlinx.serialization.Serializable

@Serializable
class CommandWrapper() {
    var command: String = ""
    var argumentsAmount = 0
    var arguments: List<String> = listOf("")
    var string: String = ""
    var describe: String = ""
    var syntax: String = ""
    var name: String = ""
    var result: String = ""

    fun wrapCommand(c: Command) {
        command = c.getName()
        argumentsAmount = c.argumentsAmount
        string = c.toString()
        describe = c.describe()
        syntax = c.getSyntax()
        name = c.getName()
    }

    fun wrapArguments(args: List<String>) {
        if (validate(args)) {
            arguments = args
        } else {
            throw InvalidElementValueException(args)
        }
    }

    fun wrapResult(result: String) {
        this.result = result
    }

    fun validate(args: List<String>): Boolean {
        if (args.size == argumentsAmount) return true
        if (args.size == 1) {
            try {
                args[0].toLong()
                return true
            } catch (_: NumberFormatException) {
                return false
            }
        }
        return false
    }
}