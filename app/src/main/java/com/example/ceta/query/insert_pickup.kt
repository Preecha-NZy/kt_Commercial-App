package com.example.ceta.query

import com.example.ceta.Network.Connection
import java.sql.PreparedStatement
import java.sql.ResultSet

class insert_pickup(r_id: String?, pay_id: String) {
    var id: String? = null
    var pick_date: String? = null
    init {
        val conn = Connection()
        count_check(conn)
        query_pickDate(conn,r_id)
        insert_pickDate(id, pick_date, pay_id)
    }

    fun count_check(conn: Connection) {
        val s = conn.connect()!!
            .createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE)
        val r = s.executeQuery("select count(id) as count from pickup ")
        while (r.next()) {
            var count = r.getInt("count")
            this.id = "PICK" + "%04d".format(count + 1)
        }
    }

    fun query_pickDate(conn:Connection, id:String?) {
        val s = conn.connect()!!
            .createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE)
        val r = s.executeQuery("select pickup_date from rentdetail where id = '$id' ")
        while (r.next()) {
            this.pick_date = r.getString("pickup_date")
        }
    }

    fun insert_pickDate(id: String?, pick_date: String?, pay_id: String?) {
        val conn = Connection()
        val s = conn.connect()!!
            .createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE)
        val preparedStatement: PreparedStatement =
            conn.connect()!!
                .prepareStatement("INSERT INTO pickup (id, pickup_date, payment_id) VALUES (?,?,?)")
        preparedStatement.setString(1, id)
        preparedStatement.setString(2, pick_date)
        preparedStatement.setString(3, pay_id)
        preparedStatement.execute()
    }
}