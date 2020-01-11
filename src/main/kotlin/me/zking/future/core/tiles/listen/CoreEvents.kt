package me.zking.future.core.tiles.listen

import me.zking.future.core.Core
import me.zking.future.core.entity.async
import me.zking.future.core.entity.user.SpaceManager
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.event.player.PlayerQuitEvent

class CoreEvents(
        val core: Core
) : Listener {

    @EventHandler
    fun onJoin(event: PlayerJoinEvent){
        event.joinMessage = null
        core.async {
            SpaceManager.register(event.player.uniqueId, core)
        }
    }

    @EventHandler
    fun onLeave(event: PlayerQuitEvent){
        event.quitMessage = null
        core.async {
            SpaceManager.removeById(event.player.uniqueId)
        }
    }
}