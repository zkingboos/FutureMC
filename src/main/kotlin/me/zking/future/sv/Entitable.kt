package me.zking.future.sv

import me.zking.future.core.Core
import me.zking.future.sv.schedule.TableSchedule
import org.bukkit.plugin.java.JavaPlugin

class Entitable : JavaPlugin(){

    lateinit var core: Core
    lateinit var entitable: Entitable

    override fun onEnable() {
        val services = server.servicesManager

        entitable = this
        core = services.load(Core::class.java)

        val tableSchedule = TableSchedule(entitable)
    }
}