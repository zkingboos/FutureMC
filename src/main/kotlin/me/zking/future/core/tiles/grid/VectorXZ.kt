package me.zking.future.core.tiles.grid

import org.bukkit.util.NumberConversions

class VectorXZ(
        val x: Double,
        val z: Double
) {

    fun squareDistance(o: VectorXZ) = NumberConversions.floor(Math.sqrt(
            NumberConversions.square(x - o.x) + NumberConversions.square(z - o.z)
    ))
}