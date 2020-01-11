package me.zking.future.core.tiles

import me.zking.future.core.Core
import me.zking.future.core.entity.config.Configuration
import me.zking.future.core.entity.sql.SqlCredentials

class CoreData(
        core: Core
) : Configuration(core, "config.json") {

    val website = "futuremc.com"
    private val credentials = mapOf<String, String>()

    val mysqlLogin by lazy {
        SqlCredentials(credentials)
    }
}