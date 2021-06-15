package com.example.ceta.query

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import com.example.ceta.Network.Connection
import java.sql.ResultSet

class queryCarHome(city:String) {
    var name  = mutableListOf<String?>()
    val price = mutableListOf<Int?>()
    val SImage = mutableListOf<Bitmap?>()
    var city:String? = null

    init {
        this.city = city
        val conn = Connection()
        queryname(conn,this.city)
        queryprice(conn,this.city)
        querySImage(conn,this.city)
    }
    fun queryname(conn:Connection, city:String?) {
        name.clear()
        val c = conn.connect()
        val s = c!!.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE)
        val r = s.executeQuery("select name from carList where city like '%$city%' and status = 'available'")
        while (r.next()) {
            name.add(r.getString("name"))
        }
    }

    fun queryprice(conn:Connection, city:String?) {
        price.clear()
        val c = conn.connect()
        val s = c!!.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE)
        val r = s.executeQuery("select price from carList where city like '%$city%' and status = 'available' ")
        while (r.next()) {
            price.add(r.getInt("price"))
        }
    }

    fun querySImage(conn:Connection, city:String?) {
        SImage.clear()
        val c = conn.connect()
        val s = c!!.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE)
        val r = s.executeQuery("select SImage from carList where city like '%$city%'and status = 'available' ")
        while (r.next()) {
            val decodedImage = BitmapFactory.decodeByteArray(r.getBytes("SImage"), 0, r.getBytes("SImage").size)
            SImage.add(decodedImage)
        }
    }
}