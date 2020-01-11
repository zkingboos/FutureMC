package me.zking.future.core.entity.user

import me.zking.future.core.Core
import me.zking.future.core.entity.async
import me.zking.future.core.entity.sql.SqlFactory
import me.zking.future.core.tiles.cache.Cached
import java.lang.reflect.Field
import java.util.*

object SpaceManager {

    private val users = mutableListOf<SpaceUser>()

    fun has(id: UUID) = users.any { it.id == id }
    fun removeById(id: UUID) = users.removeIf { it.id == id }
    fun findById(id: UUID) = users.find { it.id == id }!!
    fun register(id: UUID, core: Core) { if (!has(id)) { users.add(SpaceUser(core, id)) } }

    fun loadUsersTable(provider: SqlFactory) {
        provider.update("create table if not exists users(" +
                "id varchar(50) primary key, " +
                "money float, " +
                "discord long, " +
                "password varchar(100)" +
                ")"
        )
    }
}