package com.example.ceta.query

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import com.example.ceta.Network.Connection
import java.sql.ResultSet

class queryOrder(email: String?) {

    var name = mutableListOf<String>()
    var price = mutableListOf<Int>()
    var deposit = mutableListOf<Int>()
    var date = mutableListOf<Int>()
    var image = mutableListOf<Bitmap>()
    var city = mutableListOf<String>()
    var id = mutableListOf<String>()
    var i = 0

    init {
        val conn = Connection()
        queryOrder(conn, email)
        while (i < name.size) {
            queryImage(conn,name[i],city[i])
            i++
        }
    }

    fun queryOrder(conn: Connection, email: String?) {
        val c = conn.connect()
        val s = c!!.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE)
        val r = s.executeQuery("select id, name, price, deposit, datediff(day,pickup_date ,bringback_date) as date, city from rentdetail where not exists (select * from payment where r_id = rentdetail.id) and email = '$email' ")
        while (r.next()) {
            name.add(r.getString("name"))
            price.add(r.getInt("price"))
            deposit.add(r.getInt("deposit"))
            date.add(r.getInt("date"))
            city.add(r.getString("city"))
            id.add(r.getString("id"))
        }
    }

    fun queryImage(conn:Connection, name:String, city:String) {
        val c = conn.connect()
        val s = c!!.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE)
        val r = s.executeQuery("select MImage from carList where name = '$name' and city = '$city' ")
        while (r.next()) {
            val decodedImage = BitmapFactory.decodeByteArray(r.getBytes("MImage"), 0, r.getBytes("MImage").size)
            image.add(decodedImage)
        }
    }
}