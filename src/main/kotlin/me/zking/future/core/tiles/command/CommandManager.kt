package me.zking.future.core.tiles.command

import org.bukkit.Bukkit
import org.bukkit.command.Command
import org.bukkit.command.CommandSender
import org.bukkit.craftbukkit.v1_8_R3.CraftServer
import org.bukkit.plugin.java.JavaPlugin

object CommandManager {

    private val tileCommands = mutableListOf<TileCommand>()
    private val commandMap = (Bukkit.getServer() as CraftServer).commandMap

    fun find(label: String) = tileCommands.find { it.aliases.contains(label) }

    fun doRegister(
            plugin: JavaPlugin,
            vararg commands: TileCommand
    ) {
        tileCommands.addAll(commands)
        val prefix = plugin.config.name
        commands.map(TileCommand::aliases)
                .map {
                    it.map(::eachCommand)
                }.forEach {
                    commandMap.registerAll(prefix, it)
                }
    }

    private fun eachCommand(label: String): Command = object : Command(label.trim()) {
        override fun execute(
                sender: CommandSender,
                label: String,
                args: Array<out String>
        ) = Dispatcher.onCommand(sender, label, args)
    }
}