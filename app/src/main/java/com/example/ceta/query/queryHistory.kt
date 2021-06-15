package com.example.ceta.query

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import com.example.ceta.Network.Connection
import java.sql.ResultSet

class queryHistory(email: String?) {

    var r_id = mutableListOf<String>()
    var name = mutableListOf<String>()
    var price = mutableListOf<Int>()
    var date = mutableListOf<Int>()
    var image = mutableListOf<Bitmap>()
    var city = mutableListOf<String>()
    var id = mutableListOf<String>()

    init {
        val conn = Connection()
        queryid(conn, email)
        var i = 0
        while (i < this.r_id.size) {
            Log.i("apptest",id.size.toString()+"size")
            queryDetail(conn, r_id[i])
            queryImage(conn, city[i], name[i])
            Log.i("apptest",i.toString())
            i++
        }
        Log.i("apptest",i.toString())
    }

    fun queryid(conn: Connection, email: String?) {
        val c = conn.connect()
        val s = c!!.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE)
        val r = s.executeQuery("select r_id, id from  payment where exists (select * from rentdetail where rentdetail.id = payment.r_id and email = '$email')")
        while (r.next()) {
            r_id.add(r.getString("r_id"))
            id.add(r.getString("id"))
        }
    }

    fun queryDetail(conn: Connection, r_id: String) {
        val c = conn.connect()
        val s = c!!.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE)
        val r = s.executeQuery("select id, name, city, datediff(day,pickup_date ,bringback_date) as date, total_price from rentdetail where id = '$r_id'")
        while (r.next()) {
            name.add(r.getString("name"))
            city.add(r.getString("city"))
            date.add(r.getInt("date"))
            price.add(r.getInt("total_price"))
        }
    }

    fun queryImage(conn: Connection, city: String, name: String) {
        val c = conn.connect()
        val s = c!!.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE)
        val r = s.executeQuery("select MImage from carlist where name = '$name' and city = '$city'")
        while (r.next()) {
            val decodedImage = BitmapFactory.decodeByteArray(r.getBytes("MImage"), 0, r.getBytes("MImage").size)
            image.add(decodedImage)
        }
    }
}