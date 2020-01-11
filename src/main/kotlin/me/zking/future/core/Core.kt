package me.zking.future.core

import com.google.gson.GsonBuilder
import me.lucko.luckperms.bukkit.vault.LuckPermsVaultPermission
import me.zking.future.core.cmd.CoreCredits
import me.zking.future.core.entity.DeepRef
import me.zking.future.core.entity.async
import me.zking.future.core.entity.config.ConfigManager
import me.zking.future.core.entity.mysqlInfo
import me.zking.future.core.entity.parse
import me.zking.future.core.entity.scheduler.UnloadChunks
import me.zking.future.core.entity.sql.SqlFactory
import me.zking.future.core.entity.user.SpaceManager
import me.zking.future.core.tiles.listen.CoreEvents
import me.zking.future.core.tiles.CoreData
import me.zking.future.core.tiles.command.CommandManager
import net.luckperms.api.LuckPerms
import net.milkbowl.vault.permission.Permission
import org.bukkit.Bukkit
import org.bukkit.plugin.ServicePriority
import org.bukkit.plugin.java.JavaPlugin
import java.util.concurrent.Executors

class Core : JavaPlugin() {

    companion object {
        lateinit var staticCore: Core
        val gson = GsonBuilder()
                .setPrettyPrinting()
                .disableHtmlEscaping()
                .create()
    }

    lateinit var core: Core
    lateinit var ref: DeepRef
    lateinit var manager: ConfigManager
    lateinit var data: CoreData

    lateinit var unloadChunksTask: UnloadChunks

    lateinit var mysql: SqlFactory
    lateinit var permissions: LuckPermsVaultPermission
    lateinit var luck: LuckPerms

    val processors = Executors
            .newFixedThreadPool(4)

    override fun onEnable() {
        core = this
        staticCore = this
        unloadChunksTask = UnloadChunks(core)

        ref = DeepRef()
        data = CoreData(core)
        manager = ConfigManager(core).setupAnnotation(data)
        mysql = SqlFactory(core, data.mysqlLogin)

        val services = server.servicesManager
        val plugin = server.pluginManager

        async {
            if(mysql.openConnection()) {
                mysqlInfo("Has been connected")
                SpaceManager.loadUsersTable(mysql)
            } else
                mysqlInfo("Can't connect")
        }

        luck = services.load(LuckPerms::class.java)
        permissions = services.load(Permission::class.java) as LuckPermsVaultPermission

        services.register(Core::class.java, core, this, ServicePriority.Normal)
        plugin.registerEvents(CoreEvents(core), core)
        listenUsers()
        registerCommands()
    }

    override fun onDisable() {
        processors.shutdown()
    }

    private fun registerCommands(){
        CommandManager.doRegister(this,
                CoreCredits()
        )
    }

    private fun listenUsers() {
        val txt = "&cNosso sistema foi reiniciado, por favor reentre para confirmar seu usuário.".parse
        Bukkit.getOnlinePlayers().forEach{ it.kickPlayer(txt) }
    }
}