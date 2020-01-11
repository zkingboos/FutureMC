package me.zking.future.sv.entity

import me.zking.future.core.entity.parse
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import org.bukkit.scoreboard.DisplaySlot
import org.bukkit.scoreboard.Objective

typealias TableConsumer = Player.() -> String

class Table(
        private val title: String = "Unknown"
) {

    private val lines = mutableListOf<TableLine>()

    fun setLine(line: Int, text: String) = lines.add(TableLine(line, text, null))
    fun setLine(line: Int, c: TableConsumer) = lines.add(TableLine(line, null, c))

    private fun updateLines(player: Player) {
        val objective = getObjective(player)
        for (i in lines.size downTo 1) {
            val line = lines[i - 1]
            val text = (line.line ?: line.f?.invoke(player) ?: "Invalid").parse
            val score = scoreEntry(objective, line.slot)
            if (score != null) {
                if (score.equals(text, true)) continue
                objective.scoreboard.resetScores(score)
            }
            objective.getScore(text).score = line.slot
        }
    }

    fun show(player: Player) {
        val scoreboard = player.scoreboard ?: Bukkit.getScoreboardManager().newScoreboard

        getObjective(player).apply {
            displayName = title.parse
            displaySlot = DisplaySlot.SIDEBAR
        }

        updateLines(player) //tips
        player.scoreboard = scoreboard
    }

    private fun scoreEntry(o: Objective, score: Int) =
            o.scoreboard.entries.find { o.getScore(it).score == score }

    private fun getObjective(player: Player) = with(player.scoreboard) {
        getObjective(player.name) ?: registerNewObjective(player.name, "dummy")
    }
}

class TableLine(
        val slot: Int,
        val line: String?,
        val f: TableConsumer? = null
)