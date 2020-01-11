package me.zking.future.core.entity.user

import me.zking.future.core.Core
import me.zking.future.core.tiles.cache.Cached
import me.zking.future.core.entity.async
import net.luckperms.api.cacheddata.CachedMetaData
import java.util.*

class SpaceUser(
        core: Core,
        val id: UUID
) : UserProcess(core) {

    private val luck = core.luck
    private val query = luck.contextManager.staticQueryOptions

    val data by lazy {
        mysql.query("select * from users where id=?", id.toString()) {
            val money = it.getFloat("money")
            val discord = it.getLong("discord")
            val password = it.getString("password")
            SpaceData(money, discord, password)
        }.orElse(SpaceData())
    }

    fun save() = saveUser(id, data)

    fun getGroupMeta(): CachedMetaData {
        val user = luck.userManager.getUser(id)!!
        return user.cachedData.getMetaData(query)
    }
}