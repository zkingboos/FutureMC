package me.zking.future.core.entity.user

import me.zking.future.core.Core
import me.zking.future.core.entity.async
import java.util.UUID

abstract class UserProcess(
        val core: Core
) {

    private val className = "SpaceData"
    val mysql = core.mysql

    fun saveUser(id: UUID, data: SpaceData) = core.async {
        val insertColumns = mysql.insertColumns(className)
        val inserts = mysql.inserts(className)
        val updateColumns = mysql.updateColumns(className)

        mysql.update(
                "insert into users(id,$insertColumns) " +
                        "values (?,$inserts) " +
                        "on duplicate key update $updateColumns",
                id.toString(),
                data.money, data.discord, data.password
        )
    }
}