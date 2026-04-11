package core

import commands.ExitCommand

class CommandInvoker(cm: CollectionManager) {
    fun runOnServer(command: String) {
        when (command) {
            "exit" -> {val exit = ExitCommand()
                exit.execute(listOf(""))}
            "" -> return
            else -> println(command)
        }
    }
}