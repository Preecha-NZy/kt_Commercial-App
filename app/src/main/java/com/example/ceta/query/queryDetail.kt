package com.example.ceta.query

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import com.example.ceta.Network.Connection
import java.sql.ResultSet

class queryDetail(name:String, city: String) {

    var name:String? = null
    var price:Int? = null
    var deposit:Int? = null
    var fuel:String? = null
    var engine:String? = null
    var drive_system:String? = null
    var gear:String? = null
    var city:String? = null
    var LImage:Bitmap? = null
    var MImage:Bitmap? = null

    var conn:Connection? = null
    var city_data:String? = null
    var name_data:String? = null

    init {
        this.conn = Connection()
        this.city_data = city
        this.name_data = name
        queryImage(conn,name_data,city_data)
        queryDetail(conn,name_data)
    }

    fun queryImage(conn:Connection?,names:String?, citys:String?) {
        val s = conn?.connect()!!.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE)
        val r = s.executeQuery("select * from carList where city like '%$citys%' and name like '%$names%' order by city ")
        while (r.next()) {
            name = r.getString("name")
            price = r.getInt("price")
            deposit = r.getInt("deposit")
            val decodedImage = BitmapFactory.decodeByteArray(r.getBytes("LImage"), 0, r.getBytes("LImage").size)
            LImage = decodedImage
            val decodedImage1 = BitmapFactory.decodeByteArray(r.getBytes("MImage"), 0, r.getBytes("MImage").size)
            MImage = decodedImage1
        }
    }

    fun queryDetail(conn:Connection?,names:String?) {
        val s = conn?.connect()!!.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE)
        val r = s.executeQuery("select * from car where name like '%$names%' ")
        while (r.next()) {
            fuel = r.getString("fuel")
            engine = r.getString("engine")
            drive_system = r.getString("drive_system")
            gear = r.getString("gear")
        }
    }
}