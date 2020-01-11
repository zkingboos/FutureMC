package me.zking.future.core.entity

import me.zking.future.core.Core
import org.bukkit.ChatColor
import org.bukkit.command.CommandSender
import java.text.DecimalFormat
import java.util.concurrent.CompletableFuture
import java.util.logging.Logger

val String.parse: String
    get() = ChatColor.translateAlternateColorCodes('&', this)

val numberFormats = arrayOf("", "K", "M", "B", "T", "Q", "QT", "S", "ST", "O", "N", "D", "U")
val extensiveFormats = arrayOf("", "mil", "milhões", "bilhões", "trilhões",
        "quadrilhões", "quintilhões", "sextilhões", "septilhões",
        "octilhões", "nonilhões", "decilhões", "undecilhões")

fun Number.toFormated(): String {
    val format = DecimalFormat("#,##0.00")
    val parts = format.format(this).split(".")
    return parts[0] + numberFormats[parts.size - 1]
}

fun Number.toExtensiveFormat(): String {
    val format = DecimalFormat("#,##0.00")
    val parts = format.format(this).split(".")
    return parts[0] + extensiveFormats[parts.size - 1]
}

private val logger = Logger.getLogger("minecraft")

infix fun String.info(msg: String) = logger.info(String.format("[%s] ${msg.parse}", this))
fun mysqlInfo(msg: String) = "Mysql" info msg

fun Core.async(context: () -> Unit) = processors.submit(context)

fun CommandSender.message(vararg msg: String) = msg
        .map { it.parse }
        .also { sendMessage(it.toTypedArray()) }