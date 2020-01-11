package me.zking.future.core.entity.sql

import java.lang.reflect.Field

abstract class SqlQuery {

    private val mappedColumns = mutableMapOf<String, List<String>>()

    fun mapColumns(inst: Any){
        val clazz = inst::class.java
        mappedColumns[clazz.simpleName] = clazz.declaredFields.map(Field::getName)
    }

    fun inserts(className: String) = mappedColumns[className]?.joinToString { "?" }
    fun insertColumns(className: String) = mappedColumns[className]?.joinToString()
    fun updateColumns(className: String) = mappedColumns[className]?.joinToString {
        "$it=values($it)"
    }
}