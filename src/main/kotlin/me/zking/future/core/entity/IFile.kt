package me.zking.future.core.entity

import org.bukkit.plugin.java.JavaPlugin
import java.io.File

class IFile(
        private val plugin: JavaPlugin,
        fileName: String,
        path: String = plugin.dataFolder.path
) {

    var file = File(path, fileName)

    init {
        if(!file.exists()) plugin.saveResource(fileName, false)
    }
}