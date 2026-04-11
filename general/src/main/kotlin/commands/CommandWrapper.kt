package commands

import java.io.Serializable

class CommandWrapper(c: Command) {
    val arguments = ArrayList<Serializable>()
}