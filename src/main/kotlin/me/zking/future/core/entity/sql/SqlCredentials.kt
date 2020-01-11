package me.zking.future.core.entity.sql

data class SqlCredentials(val map: Map<String, String>) {
    val host = map["host"]
    val user = map["user"]
    val password = map["password"]
    val database = map["database"]
}