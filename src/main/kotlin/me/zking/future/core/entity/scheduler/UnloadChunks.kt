package me.zking.future.core.entity.scheduler

import org.bukkit.Bukkit
import org.bukkit.World
import org.bukkit.plugin.java.JavaPlugin
import org.bukkit.scheduler.BukkitRunnable

class UnloadChunks(
        plugin: JavaPlugin
) : BukkitRunnable() {

    init {
        runTaskTimer(plugin, 0, 20 * 60)
    }

    override fun run() {
        Bukkit.getWorlds().forEach(::clearChunk)
    }

    private fun clearChunk(world: World) = world.loadedChunks.forEach {
        it.unload(true, true)
    }
}