package me.zking.future.core.entity.config

import me.zking.future.core.Core

class ConfigManager(
        val core: Core
) {

    private val annotations = mutableListOf<Configuration>()
    private val ref = core.ref

    fun setupAnnotation(vararg inst: Configuration) = this.also {
        annotations.addAll(inst)
        inst.forEach(::loadEach)
    }

    fun loadEach(instance: Configuration) {
        ref.getFields(instance).forEach {
            val fieldValue = instance.find<Any>(it.name)
            if(fieldValue != null) ref.changeFieldValue(it, fieldValue, instance)
        }
    }
}
