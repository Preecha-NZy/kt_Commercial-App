package com.example.ceta.query

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import com.example.ceta.Network.Connection
import java.sql.ResultSet

class queryMain() {
    var name  = mutableListOf<String?>()
    val price = mutableListOf<String?>()
    val city = mutableListOf<String?>()
    val SImage = mutableListOf<Bitmap?>()
    val MImage = mutableListOf<Bitmap?>()
    val LImage = mutableListOf<Bitmap?>()

    init {
        val conn = Connection()
        queryCity(conn)
    }

    fun queryCity(conn:Connection) {
        val c = conn.connect()
        val s = c!!.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE)
        val r = s.executeQuery("select distinct(city) from carList")
        while (r.next()) {
            city.add(r.getString("city").toString())
        }
    }

    fun queryCar(Citys:String) {
        name.clear()
        price.clear()
        SImage.clear()
        val conn = Connection()
        val s = conn.connect()!!.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE)
        val r = s.executeQuery("select * from carList where city like '%$Citys%'")
        while (r.next()) {
            name.add(r.getString("name"))
            price.add(r.getString("price"))
            val decodedImage = BitmapFactory.decodeByteArray(r.getBytes("SImage"), 0, r.getBytes("SImage").size)
            SImage.add(decodedImage)
        }
    }

    fun queryCarInfo(Infos:String?) {
        name.clear()
        price.clear()
        MImage.clear()
        city.clear()
        val conn = Connection()
        val s = conn.connect()!!.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE)
        val r = s.executeQuery("select * from carList where city like '%$Infos%' or name like '%$Infos%' or price like '%$Infos%' order by city ")
        while (r.next()) {
            name.add(r.getString("name"))
            price.add(r.getString("price"))
            city.add(r.getString("city"))
            val decodedImage = BitmapFactory.decodeByteArray(r.getBytes("MImage"), 0, r.getBytes("MImage").size)
            MImage.add(decodedImage)
        }
    }

    fun query(names:String, citys:String) {
        name.clear()
        price.clear()
        LImage.clear()
        val conn = Connection()
        val s = conn.connect()!!.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE)
        val r = s.executeQuery("select * from carList where city like '%$citys%' and name like '%$names%' order by city ")
        while (r.next()) {
            name.add(r.getString("name"))
            price.add(r.getString("price"))
            val decodedImage = BitmapFactory.decodeByteArray(r.getBytes("LImage"), 0, r.getBytes("LImage").size)
            LImage.add(decodedImage)
        }
    }

}