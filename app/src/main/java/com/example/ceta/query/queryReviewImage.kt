package com.example.ceta.query

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import com.example.ceta.Network.Connection
import java.sql.ResultSet

class queryReviewImage(name:String) {
    var image: Bitmap? = null
    init {
        val conn = Connection()
        queryImage(conn, name)
    }
    fun queryImage(conn: Connection, name: String?) {
        val c = conn.connect()
        val s = c!!.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE)
        val r =
            s.executeQuery("select MImage from carList where name = '$name'")
        while (r.next()) {
            val decodedImage =
                BitmapFactory.decodeByteArray(r.getBytes("MImage"), 0, r.getBytes("MImage").size)
            image = decodedImage
        }
    }
}