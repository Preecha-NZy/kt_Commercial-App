package com.example.ceta.query

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import com.example.ceta.Network.Connection
import java.sql.ResultSet

class queryOrderPayment(id: String) {

    var name: String? = null
    var pick: String? = null
    var back: String? = null
    var price: Int? = null
    var deposit: Int? = null
    var date: Int? = null
    var image: Bitmap? = null
    var city: String? = null
    var id: String? = null
    var total: Int? = null

    init {
        val conn = Connection()
        queryOrder(conn, id)
        queryImage(conn, name, city)
        Log.i("aaa", name.toString())

    }

    fun queryOrder(conn: Connection, id: String?) {
        val c = conn.connect()
        val s = c!!.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE)
        val r = s.executeQuery("select id, name, price, " +
                "deposit, datediff(day,pickup_date ,bringback_date) as date, " +
                "pickup_date, bringback_date, city, total_price from rentdetail where id = '$id'")
        while (r.next()) {
            this.id = r.getString("id")
            name = r.getString("name")
            price = r.getInt("price")
            deposit = r.getInt("deposit")
            date = r.getInt("date")
            pick = r.getString("pickup_date")
            back = r.getString("bringback_date")
            city = r.getString("city")
            total = r.getInt("total_price")
        }
    }

    fun queryImage(conn: Connection, name: String?, city: String?) {
        val c = conn.connect()
        val s = c!!.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE)
        val r =
            s.executeQuery("select MImage from carList where name = '$name' and city = '$city' ")
        while (r.next()) {
            val decodedImage =
                BitmapFactory.decodeByteArray(r.getBytes("MImage"), 0, r.getBytes("MImage").size)
            image = decodedImage
        }
    }
}