package me.zking.future.core.entity.sql

import me.zking.future.core.Core
import java.sql.PreparedStatement
import java.sql.ResultSet
import java.util.*

typealias SqlConsumer<T> = (ResultSet) -> T

class SqlFactory(
        val core: Core,
        login: SqlCredentials,
        maxConnections: Int = 15
) : SqlPool(login) {

    private val dataSource = dataSource(maxConnections)

    fun openConnection(): Boolean {
        val con = dataSource.connection
        return (con != null && !con.isClosed).also { close(con) }
    }

    fun <T> query(query: String, vararg ob: Any?, f: SqlConsumer<T>? = null): Optional<T> {
        val con = dataSource.connection
        val ps = con.prepareStatement(query).sync(ob)
        val set = ps.executeQuery().also {
            if (!it.next()) return Optional.empty()
//Close the connections here
        }

        val result = f?.let {
            it(set)
        }.also { close(set, ps, con) }
        return Optional.ofNullable(result)
    }

    fun <T> map(query: String, vararg ob: Any?, f: SqlConsumer<T>): List<T> {
        val con = dataSource.connection
        val ps = con.prepareStatement(query).sync(ob)
        val set = ps.executeQuery()
        return mutableListOf<T>().also {
            while(set.next()) { it.add(f(set)) }
            close(set, ps, con)
        }
    }

    fun update(query: String, vararg ob: Any?): Int {
        val con = dataSource.connection
        val ps = con.prepareStatement(query).sync(ob)
        return ps.executeUpdate().also { close(ps, con) }
    }

    private fun close(vararg a: AutoCloseable) = a.forEach(AutoCloseable::close)

    private fun PreparedStatement.sync(ob: Array<out Any?>) = also {
        ob.forEachIndexed { index, any ->
            setObject(index + 1, any)
        }
    }
}
