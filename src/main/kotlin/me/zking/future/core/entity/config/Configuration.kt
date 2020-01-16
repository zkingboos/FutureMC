package me.zking.future.core.entity.config

import com.google.common.io.Files
import me.zking.future.core.Core
import me.zking.future.core.entity.IFile
import org.bukkit.plugin.java.JavaPlugin

abstract class Configuration(
        plugin: JavaPlugin,
        fileName: String
) {

    val maps = mutableMapOf<String, Any?>()
    private val file = IFile(plugin, fileName)

    init { load() }

    private fun load() {
        val content = Files.toString(file.file, Charsets.UTF_8)
       val data = Core.gson.fromJson<Map<String, Any?>>(content, Map::class.java)
maps.also {
clear()
putAll(data)
}
    }

    private fun save() {
        val content = Core.gson.toJson(maps)
        Files.write(content, file.file, Charsets.UTF_8)
    }

    fun reload() = save().also { load() }

    fun findSection(key: String) = find(key, mapOf<String, Any?>())
    fun <T> find(key: String, def: T? = null) = maps.getOrDefault(key, def) as T?
    fun set(key: String, data: Any) = maps.set(key, data)
}
