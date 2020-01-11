package me.zking.future.login.events

import me.zking.future.core.Core
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.AsyncPlayerPreLoginEvent

class LoginEvents(
        val core: Core
) : Listener {

    @EventHandler
    fun verifyHas(event: AsyncPlayerPreLoginEvent) {
    }
}