package core

import commands.CommandWrapper
import exceptions.ConnectionException
import io.IOManager
import java.net.InetSocketAddress
import java.nio.ByteBuffer
import java.nio.channels.DatagramChannel
import kotlinx.serialization.json.Json.Default.encodeToString
import kotlinx.serialization.json.Json.Default.decodeFromString
import java.net.InetAddress
import java.net.SocketAddress
import java.nio.channels.ClosedByInterruptException

class ConnectionManager(private val io: IOManager, private val host: InetAddress, private val port: Int) {
    var attempt = 1

    fun askCommandList(): List<CommandWrapper> {
        val cw = CommandWrapper()
        cw.name = "help"
        val result = sendAndReceive(cw)
        return decodeFromString(result.result)
    }

    fun sendAndReceive(cw: CommandWrapper): CommandWrapper {
//        val countConnections = Thread {
//            Thread.sleep(10000)
//            attempt++
//
//        }
//        val address: SocketAddress = InetSocketAddress(host, port)
//        val channel = DatagramChannel.open()
//        var buffer = ByteBuffer.wrap(encodeToString(cw).toByteArray())
//        channel.send(buffer, address)
//        val bytes = ByteArray(32768)
//        buffer = ByteBuffer.wrap(bytes)
//        var result: CommandWrapper
//        try {
//            countConnections.start()
//            channel.receive(buffer)
//            try {
//                countConnections.interrupt()
//            } catch (_: Exception) {
//                attempt = 0
//            }
//            result = decodeFromString<CommandWrapper>(bytes.decodeToString().replace("\u0000", ""))
//        } catch (e: ConnectionException) {
//            if (attempt <= 3) {
//                io.write(e.message + "Попытка $attempt из 3.\n")
//                result = sendAndReceive(cw)
//            } else {
//                attempt = 0
//                throw ConnectionException()
//            }
//        }
//        return result

        val address: SocketAddress = InetSocketAddress(host, port)
        val channel = DatagramChannel.open()
        var buffer = ByteBuffer.wrap(encodeToString(cw).toByteArray())
        channel.send(buffer, address)
        val bytes = ByteArray(32768)
        buffer = ByteBuffer.wrap(bytes)
        var result = CommandWrapper()
        val receive = Thread {
            try {
                channel.receive(buffer)
                if (!Thread.currentThread().isInterrupted)
                    result = decodeFromString<CommandWrapper>(bytes.decodeToString().replace("\u0000", ""))
            } catch (e: ClosedByInterruptException) {
                return@Thread
            }
        }
        val wait = Thread {
            Thread.sleep(10000)
        }
        receive.start()
        wait.start()
        while (receive.isAlive) {
            if (!wait.isAlive) {
                receive.interrupt()
                if (attempt == 3) throw ConnectionException()
                io.write("Не удалось установить соединение с сервером. Попытка $attempt из 3.\n")
                attempt++
                result = sendAndReceive(cw)
            }
        }
        return result
    }
}