import core.ConnectionManager
import core.InteractiveMode
import io.IOManager
import java.net.InetAddress

fun main() {
    val host = InetAddress.getLocalHost()
    val port = 1488
    val io = IOManager()
    val cm = ConnectionManager(host, port)
    val im = InteractiveMode(io, cm)
    im.start()
}