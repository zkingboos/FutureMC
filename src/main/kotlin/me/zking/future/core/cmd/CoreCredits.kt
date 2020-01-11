package me.zking.future.core.cmd

import me.zking.future.core.tiles.command.DataCommand
import me.zking.future.core.tiles.command.TileCommand
import me.zking.future.core.tiles.command.Who

class CoreCredits
    : TileCommand (
        null,
        Who.CONSOLE,
        "core",
        "help"
) {

    override fun run(data: DataCommand) {


    }
}