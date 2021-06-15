package com.example.ceta.query

import com.example.ceta.Network.Connection
import java.sql.PreparedStatement
import java.sql.ResultSet

class insert_back(r_id: String?, pick_id: String?) {
    var id: String? = null
    var back_date: String? = null

    init {
        val conn = Connection()
        count_check(conn)
        query_backDate(conn, r_id)
        insert_backDate(conn, id, back_date, pick_id)
    }

    fun count_check(conn:Connection) {
        val s = conn.connect()!!
            .createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE)
        val r = s.executeQuery("select count(id) as count from pickup ")
        while (r.next()) {
            var count = r.getInt("count")
            this.id = "BACK" + "%04d".format(count + 1)
        }
    }

    fun query_backDate(conn:Connection, id:String?) {
        val s = conn.connect()!!
            .createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE)
        val r = s.executeQuery("select bringback_date from rentdetail where id = '$id' ")
        while (r.next()) {
            this.back_date = r.getString("bringback_date")
        }
    }

    fun insert_backDate(conn:Connection, id: String?, back_date: String?, pick_id: String?) {
        val conn = Connection()
        val s = conn.connect()!!
            .createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE)
        val preparedStatement: PreparedStatement =
            conn.connect()!!
                .prepareStatement("INSERT INTO bring_back (id, bring_back_date, pickup_id) VALUES (?,?,?)")
        preparedStatement.setString(1, id)
        preparedStatement.setString(2, back_date)
        preparedStatement.setString(3, pick_id)
        preparedStatement.execute()
    }
}