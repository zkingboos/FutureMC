package me.zking.future.core.entity.sql

import org.apache.commons.dbcp2.DriverManagerConnectionFactory
import org.apache.commons.dbcp2.PoolableConnection
import org.apache.commons.dbcp2.PoolableConnectionFactory
import org.apache.commons.dbcp2.PoolingDataSource
import org.apache.commons.pool2.impl.GenericObjectPool
import javax.sql.DataSource

abstract class SqlPool(
        private val login: SqlCredentials
) : SqlQuery() {

    private lateinit var genericPool: GenericObjectPool<PoolableConnection>

    fun dataSource(maxConnections: Int): DataSource {
        val connectionFactory = with(login){
            val fullhost = "jdbc:mysql://$host/$database"
            DriverManagerConnectionFactory(
                    fullhost, user, password
            )
        }
        PoolableConnectionFactory(
                connectionFactory, null
        ).apply {
            validationQuery = "select 1"
            pool = GenericObjectPool(this).apply {
                maxTotal = maxConnections
                genericPool = this
            }
        }
        return PoolingDataSource(genericPool)
    }
}