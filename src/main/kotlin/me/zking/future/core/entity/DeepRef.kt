package me.zking.future.core.entity

import java.lang.reflect.Field

class DeepRef {

    fun <T: Annotation> annotationsClass(inst: Any, an: Class<T>):T?{
        val clazz = inst.javaClass
        return clazz.getAnnotation(an)
    }

    fun <T: Annotation> annotationField(field: Field, an: Class<T>):T?{
        return field.getAnnotation(an)
    }

    fun changeFieldValue(field: Field, value: Any, inst: Any? = null) {
        if(!field.isAccessible) field.isAccessible = true
        field.set(inst, value)
    }

    fun fieldValue(field: Field, inst: Any? = null):Any{
        return field.get(inst)
    }

    fun getFields(inst: Any):Array<Field>{
        return inst::class.java.declaredFields
    }
}