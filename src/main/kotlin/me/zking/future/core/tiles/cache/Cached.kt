package me.zking.future.core.tiles.cache

import java.util.*
import kotlin.reflect.KProperty

abstract class Cached(
        val id: UUID
){
    fun <T> cache(
            getter: delegateGet<T>,
            setter: delegateSet<T>
    ) = CacheSync(getter, setter)

    /*fun <T> cacheAsync(
            getter: AsyncDelGet<T>,
            setter: AsyncDelSet<T>
    ) = CacheAsync(getter, setter)*/
}
