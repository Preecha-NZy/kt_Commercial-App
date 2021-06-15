package com.example.ceta.query

import com.example.ceta.Network.Connection
import java.sql.ResultSet

class queryCity() {
    val city = mutableListOf<String?>()

    init {
        val conn = Connection()
        queryCity(conn)
    }
    fun queryCity(conn: Connection) {
        val c = conn.connect()
        val s = c!!.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE)
        val r = s.executeQuery("select distinct(city) from carList")
        while (r.next()) {
            city.add(r.getString("city").toString())
        }
    }
}