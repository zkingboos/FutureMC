package me.zking.future.sv.schedule

import me.zking.future.core.entity.toExtensiveFormat
import me.zking.future.core.entity.user.SpaceManager
import me.zking.future.sv.Entitable
import me.zking.future.sv.entity.Table
import org.bukkit.Bukkit
import org.bukkit.scheduler.BukkitRunnable

class TableSchedule(
        entity: Entitable
) : BukkitRunnable() {

    private val table = Table("&5&lFuture&f&lMC").apply {
        setLine(2){
            val user = SpaceManager.findById(uniqueId)
            user.data.money.toExtensiveFormat()
        }
        setLine(1, "&7")
        setLine(0, entity.core.data.website)
    }

    init {
        runTaskTimerAsynchronously(entity, 0, 60)
    }

    override fun run() {
        Bukkit.getOnlinePlayers().forEach(table::show)
    }
}