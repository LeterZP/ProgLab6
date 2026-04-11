import core.CollectionManager
import core.CommandInvoker
import core.IOManager
import exceptions.ProgramExitException

fun main() {
    val io = IOManager()
    val cm = CollectionManager(io)
    val ci = CommandInvoker(cm)
    var isWorking = true
    io.print("Сервер запущен.")
    while (isWorking) {
        try {
            ci.runOnServer(io.checkForLocalCommands())
        } catch (e: ProgramExitException) {
            isWorking = false
            io.print(e.message)
        }
    }
}