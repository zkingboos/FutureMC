package me.zking.future.core.tiles.command

import me.zking.future.core.entity.user.SpaceUser
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import org.bukkit.command.ConsoleCommandSender

abstract class TileCommand(
        val permission: String?,
        val who: Who,
        vararg val aliases: String
){
    abstract fun run(data: DataCommand)
}

class DataCommand(
        val args: Array<out String>,
        val sender: CommandSender,
        val user: SpaceUser?
)

enum class Who (
        inline val executor: (CommandSender) -> Boolean
){
    ENTITY({ it is Player }),
    CONSOLE({ it is ConsoleCommandSender }),
    BOTH({true})
}