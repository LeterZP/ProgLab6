package core

import commands.CommandWrapper
import java.net.InetSocketAddress
import java.nio.ByteBuffer
import java.nio.channels.DatagramChannel
import kotlinx.serialization.json.Json.Default.encodeToString
import kotlinx.serialization.json.Json.Default.decodeFromString
import java.net.InetAddress
import java.net.SocketAddress

class ConnectionManager(private val host: InetAddress, private val port: Int) {
    fun askCommandList(): List<CommandWrapper> {
        val cw = CommandWrapper()
        cw.name = "help"
        val result = sendAndReceive(cw)
        return decodeFromString(result)
    }

    fun sendAndReceive(cw: CommandWrapper): String {
        val address: SocketAddress = InetSocketAddress(host, port)
        val channel = DatagramChannel.open()
        val buffer = ByteBuffer.wrap(encodeToString(cw).toByteArray())
        channel.send(buffer, address)
        buffer.clear()
        channel.receive(buffer)
        var bytes = ByteArray(0)
        buffer.flip()
        while (buffer.hasRemaining()) {
            bytes = bytes.plus(buffer.get())
        }
        val result = decodeFromString<CommandWrapper>(bytes.decodeToString())
        return result.result
    }
}