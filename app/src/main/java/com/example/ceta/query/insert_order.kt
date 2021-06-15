package com.example.ceta.query

import com.example.ceta.Network.Connection
import java.sql.PreparedStatement
import java.sql.ResultSet

class insert_order() {

    var count: Int? = null

    fun count_check() {
        val conn = Connection()
        val s = conn.connect()!!
            .createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE)
        val r = s.executeQuery("select count(id) as count from rentdetail ")
        while (r.next()) {
            count = r.getInt("count")
        }
    }

    fun insert_order(
        id: String?, email: String?, name: String?,
        city: String?, pickdate: String?, backdate: String?,
        price: Int, deposit: Int, total: Int,
    ) {
        val conn = Connection()
        val c = conn.connect()
        val s = c!!.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE)
        val preparedStatement: PreparedStatement =
            c.prepareStatement("INSERT INTO rentdetail (id, email, name, city, pickup_date, bringback_date, price, deposit, total_price) " +
                    "VALUES (?,?,?,?,?,?,?,?,?)"
            )
        preparedStatement.setString(1, id)
        preparedStatement.setString(2, email)
        preparedStatement.setString(3, name)
        preparedStatement.setString(4, city)
        preparedStatement.setString(5, pickdate)
        preparedStatement.setString(6, backdate)
        preparedStatement.setInt(7, price)
        preparedStatement.setInt(8, deposit)
        preparedStatement.setInt(9, total)
        preparedStatement.execute()
    }

    fun insert_select(city:String?, name:String?, id:String) {
        val conn = Connection()
        val c = conn.connect()
        val s = c!!.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE)
        val preparedStatement: PreparedStatement =
            c.prepareStatement("INSERT INTO select (city, name, id) VALUES (?,?,?)")
        preparedStatement.setString(1, city)
        preparedStatement.setString(2, name)
        preparedStatement.setString(3, id)
        preparedStatement.execute()
    }
}