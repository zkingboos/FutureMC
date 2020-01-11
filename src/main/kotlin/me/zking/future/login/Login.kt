package me.zking.future.login

import me.zking.future.core.Core
import org.bukkit.plugin.java.JavaPlugin

class Login : JavaPlugin() {

    private val services = server.servicesManager

    val login by lazy { this }
    val core by lazy { services.load(Core::class.java) }

    override fun onEnable() {
    }
}