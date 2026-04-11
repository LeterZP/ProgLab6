package commands

import exceptions.InvalidAmountOfArgumentsException

/**
 * Абстрактный класс для всех команд.
 *
 * @property argumentsAmount Количество аргументов, которые принимает команда, типа [Int].
 *
 * @since 1.0
 */
abstract class Command() {
    open val argumentsAmount: Int = 0

    override fun toString(): String {
        if (getSyntax() == "") return getName() + getSyntax()
        return getName() + " " + getSyntax()
    }

    /**
     * Исполняет команду.
     *
     * @param arguments [List], содержащий в себе все аргументы команды типа [String].
     *
     * @throws InvalidAmountOfArgumentsException В случае, если количество данных элементов не совпадает с
     *                                           количеством элементов, которые принимает команда.
     *
     * @since 1.0
     */
    open fun execute(arguments: List<String>) {
        var arguments = arguments
        while (arguments.contains("")) arguments = arguments.minus("")
        if (arguments.size != argumentsAmount) throw InvalidAmountOfArgumentsException(this, arguments.size)
    }

    /**
     * Описывает команду.
     *
     * @return Описание команды типа [String].
     *
     * @since 1.0
     */
    open fun describe(): String {
        return "У этой команды нет описания"
    }

    /**
     * Описывает синтаксис команды.
     *
     * @return Синтаксис команды типа [String].
     *
     * @since 1.0
     */
    open fun getSyntax(): String {
        return ""
    }

    /**
     * Описывает имя команды.
     *
     * @return Имя команды типа [String].
     *
     * @since 1.0
     */
    open fun getName(): String {
        return "command"
    }
}