package com.example.ceta.query

import com.example.ceta.Network.Connection
import java.sql.PreparedStatement
import java.sql.ResultSet

class insertReview {

    var count: Int? = null
    init {
        count_check()
    }

    fun count_check() {
        val conn = Connection()
        val s = conn.connect()!!
            .createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE)
        val r = s.executeQuery("select count(id) as count from customerReview ")
        while (r.next()) {
            count = r.getInt("count")
        }
    }

    fun insert_review(
        id: String, email:String?, name:String, city:String, text:String, score:Int
    ) {
        val conn = Connection()
        val c = conn.connect()
        val s = c!!.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE)
        val preparedStatement: PreparedStatement =
            c.prepareStatement("INSERT INTO customerReview (id, email, name, city, text, score) VALUES (?,?,?,?,?,?)"
            )
        preparedStatement.setString(1, id)
        preparedStatement.setString(2, email)
        preparedStatement.setString(3, name)
        preparedStatement.setString(4, city)
        preparedStatement.setString(5, text)
        preparedStatement.setInt(6, score)
        preparedStatement.execute()
    }
}