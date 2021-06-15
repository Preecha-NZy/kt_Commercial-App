package com.example.ceta.query

import com.example.ceta.Network.Connection
import java.sql.PreparedStatement
import java.sql.ResultSet

class insert_payment() {

    var count: Int? = null
    var id: String? = null
    var name: String? = null
    fun count_check() {
        val conn = Connection()
        val s = conn.connect()!!
            .createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE)
        val r = s.executeQuery("select count(id) as count from payment ")
        while (r.next()) {
            count = r.getInt("count")
        }
    }

    fun insert_payment(
        id: String, total: Int, r_id: String,
    ) {
        val conn = Connection()
        val c = conn.connect()
        val s = c!!.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE)
        val preparedStatement: PreparedStatement =
            c.prepareStatement("INSERT INTO payment (id, total_price, r_id) VALUES (?,?,?)"
            )
        preparedStatement.setString(1, id)
        preparedStatement.setInt(2, total)
        preparedStatement.setString(3, r_id)
        preparedStatement.execute()
    }

    fun update_car(name: String, city: String) {
        val conn = Connection()
        val c = conn.connect()
        val s = c!!.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE)
        val preparedStatement: PreparedStatement =
            c.prepareStatement("update carList set status = 'unavailable' where name =? and city = ?")
        preparedStatement.setString(1, name)
        preparedStatement.setString(2, city)
        preparedStatement.execute()
    }
}