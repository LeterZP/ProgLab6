import core.*
import exceptions.ProgramExitException

fun main() {
    val port = 1488
    val io = IOManager()
    val cm = CollectionManager(io)
    val ci = CommandInvoker(cm)
    val cr = ConnectionReceiver(ci, port)
    val server = Thread {
        var isWorking = true
        while (isWorking) {
            try {
                ci.runOnServer(io.readLocalCommands())
            } catch (e: ProgramExitException) {
                isWorking = false
                io.print(e.message)
            }
        }
    }
    val client = Thread {
        var isWorking = true
        while (isWorking) {
            try {
                cr.checkConnection()
            } catch (e: ProgramExitException) {
                isWorking = false
            }
        }
    }
    io.print("Сервер запущен.")
    server.start()
    client.start()
    server.join()
    cr.saveInterrupt()
}