package com.example.ceta.query

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import com.example.ceta.Network.Connection
import java.sql.ResultSet

class querySearch() {
    var name  = mutableListOf<String?>()
    val price = mutableListOf<Int?>()
    val city = mutableListOf<String?>()
    val MImage = mutableListOf<Bitmap?>()

    fun queryCarInfo(Infos:String?) {
        name.clear()
        price.clear()
        MImage.clear()
        city.clear()
        val conn = Connection()
        val s = conn.connect()!!.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE)
        val r = s.executeQuery("select * from carList where (city like '%$Infos%' or name like '%$Infos%' or price like '%$Infos%') and status = 'available' order by city ")
        while (r.next()) {
            name.add(r.getString("name"))
            price.add(r.getInt("price"))
            city.add(r.getString("city"))
            val decodedImage = BitmapFactory.decodeByteArray(r.getBytes("MImage"), 0, r.getBytes("MImage").size)
            MImage.add(decodedImage)
        }
    }

    fun queryTopBangkok() {
        name.clear()
        price.clear()
        MImage.clear()
        city.clear()
        val conn = Connection()
        val s = conn.connect()!!.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE)
        val r = s.executeQuery("select top 3 * from carList where city ='bangkok' and status = 'available'")
        while (r.next()) {
            name.add(r.getString("name"))
            price.add(r.getInt("price"))
            city.add(r.getString("city"))
            val decodedImage = BitmapFactory.decodeByteArray(r.getBytes("MImage"), 0, r.getBytes("MImage").size)
            MImage.add(decodedImage)
        }
        queryTopChonburi()
    }

    fun queryTopChonburi() {
        val conn = Connection()
        val s = conn.connect()!!.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE)
        val r = s.executeQuery("select top 3 * from carList where  city ='Chon Buri' and status = 'available' ")
        while (r.next()) {
            name.add(r.getString("name"))
            price.add(r.getInt("price"))
            city.add(r.getString("city"))
            val decodedImage = BitmapFactory.decodeByteArray(r.getBytes("MImage"), 0, r.getBytes("MImage").size)
            MImage.add(decodedImage)
        }
        queryTopSuratthani()
    }

    fun queryTopSuratthani() {
        val conn = Connection()
        val s = conn.connect()!!.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE)
        val r = s.executeQuery("select top 3 * from carList where  city ='Suratthani' and status = 'available' ")
        while (r.next()) {
            name.add(r.getString("name"))
            price.add(r.getInt("price"))
            city.add(r.getString("city"))
            val decodedImage = BitmapFactory.decodeByteArray(r.getBytes("MImage"), 0, r.getBytes("MImage").size)
            MImage.add(decodedImage)
        }
        queryTopChaingmai()
    }

    fun queryTopChaingmai() {
        val conn = Connection()
        val s = conn.connect()!!.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE)
        val r = s.executeQuery("select top 3 * from carList where  city ='Chiang mai' and status = 'available'")
        while (r.next()) {
            name.add(r.getString("name"))
            price.add(r.getInt("price"))
            city.add(r.getString("city"))
            val decodedImage = BitmapFactory.decodeByteArray(r.getBytes("MImage"), 0, r.getBytes("MImage").size)
            MImage.add(decodedImage)
        }
    }
}