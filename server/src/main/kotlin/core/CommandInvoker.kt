package core

import commands.*
import exceptions.CommandNotFoundException

class CommandInvoker(val cm: CollectionManager): CommandInvokerInterface {
    val commands: HashMap<String, Command> = HashMap()

    init {
        initializeCommand(InfoCommand(this))
        initializeCommand(ShowCommand(this))
        initializeCommand(AddCommand(this))
        initializeCommand(UpdateCommand(this))
        initializeCommand(RemoveByIdCommand(this))
        initializeCommand(ClearCommand(this))
        initializeCommand(RemoveLastCommand(this))
        initializeCommand(RemoveGreaterCommand(this))
        initializeCommand(ReorderCommand(this))
        initializeCommand(GroupCountingByNameCommand(this))
        initializeCommand(CountGreaterThenMetersAboveSeaLevelCommand(this))
        initializeCommand(PrintFieldAscendingGovernmentCommand(this))
    }

    /**
     * Инициализирует команду, добавляя её в список возможных к использованию.
     *
     * @param command Команда для инициализации, типа [Command].
     *
     * @since 1.0
     */
    fun initializeCommand(command: Command) {
        val name: String = command.getName()
        commands[name] = command
    }

    fun runOnServer(command: String) {
        when (command.trim()) {
            "exit" -> {
                val exit = ExitCommand(this)
                exit.execute(listOf(""))
            }
            "save" -> {
                val save = SaveCommand(this)
                save.execute(listOf(""))
                print(save.result)
            }
            else -> return
        }
    }

    override fun executeCommand(cw: CommandWrapper): String {
        val command = commands[cw.command] ?: throw CommandNotFoundException(cw.command)
        command.execute(cw.arguments)
        return command.result
    }
}