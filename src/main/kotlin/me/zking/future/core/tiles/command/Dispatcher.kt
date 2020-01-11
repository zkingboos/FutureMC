package me.zking.future.core.tiles.command

import me.zking.future.core.entity.message
import org.bukkit.command.BlockCommandSender
import org.bukkit.command.CommandSender

object Dispatcher {

    fun onCommand(
            sender: CommandSender,
            label: String,
            args: Array<out String>
    ): Boolean {
        val tile = CommandManager.find(label.toLowerCase()) ?: return false
        if(sender is BlockCommandSender) return false
        if(!tile.who.executor(sender)) {
            sender.message("&cVocê precisa ser um &5&l${tile.who.name} &cpara executar isto.")
            return false
        }

        if(tile.who == Who.ENTITY) {

        }

        return false
    }
}