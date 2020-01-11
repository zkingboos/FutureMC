package me.zking.future.core.tiles.grid

import org.bukkit.World
import org.bukkit.generator.ChunkGenerator
import java.util.Random

class Void : ChunkGenerator() {
    override fun generateChunkData(
            world: World,
            random: Random,
            x: Int,
            z: Int,
            biome: BiomeGrid
    ) = createChunkData(world)
}