package me.zking.future.core.tiles.cache

import java.util.Optional
import kotlin.reflect.KProperty

typealias delegateGet<T> = () -> T
typealias delegateSet<T> = (T) -> Unit

class CacheSync<T>(
        val getter: delegateGet<T>,
        val setter: delegateSet<T>
){

    private var backup: Optional<T> = Optional.empty()

    operator fun getValue(i: Any, p: KProperty<*>):T{
        if(backup.isPresent) return backup.get()
        return getter().also { backup = Optional.of(it) }
    }

    operator fun setValue(i: Any, p: KProperty<*>, value: T) {
        setter(value).also { backup = Optional.of(value) }
    }
}