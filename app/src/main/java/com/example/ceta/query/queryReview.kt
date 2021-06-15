package com.example.ceta.query

import java.sql.ResultSet
import com.example.ceta.Network.Connection

class queryReview(city: String?, name: String?) {

    var fname:MutableList<String> = ArrayList()
    var text:MutableList<String> = ArrayList()
    var score:MutableList<Int> = ArrayList()
    val id:MutableList<String> = ArrayList()

    init {
        val conn = Connection()
        queryReviews(conn, city, name)
        var i = 0
        while (i<id.size) {
            queryFname(conn,id[i])
            i++
        }
    }

    fun queryReviews(conn: Connection, city: String?, name: String?) {
        val c = conn.connect()
        val s = c!!.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE)
        val r =
            s.executeQuery("select text, score, id from customerReview where city = '$city' and name = '$name' ")
        while (r.next()) {
            text.add(r.getString("text"))
            id.add(r.getString("id"))
            score.add(r.getInt("score"))
        }
    }

    fun queryFname(conn:Connection, id:String){
        val c = conn.connect()
        val s = c!!.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE)
        val r =
            s.executeQuery("select f_name from customer as c,customerReview as r where c.email = r.email and r.id = '$id'")
        while (r.next()) {
            fname.add(r.getString("f_name"))
        }
    }
}