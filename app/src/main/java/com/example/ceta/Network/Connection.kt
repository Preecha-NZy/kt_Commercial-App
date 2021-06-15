package com.example.ceta.Network

import android.os.StrictMode
import android.util.Log
import java.sql.Connection
import java.sql.DriverManager
import java.sql.SQLException
import android.os.StrictMode.ThreadPolicy.Builder


class Connection {
    var conn: Connection? = null
    var ip: String = "192.168.1.36"
    var port: String = "1433"
    var classs: String = "net.sourceforge.jtds.jdbc.Driver"
    var dbname: String = "CETA"
    var user: String = "toy"
    var password: String = "0836161840"
    var url: String = "jdbc:jtds:sqlserver://" + ip + ":" + port + "/" + dbname


    fun connect(): Connection? {
        val policy = Builder().permitAll().build()
        StrictMode.setThreadPolicy(policy)
        try {
            Class.forName(classs)
            this.conn = DriverManager.getConnection(url, user, password)
            Log.i("fuck","help")
        } catch (e: ClassNotFoundException) {
            Log.i("fuck",e.printStackTrace().toString())
            Log.i("fuck","help1")
        } catch (e: SQLException) {
            Log.i("fuck",e.printStackTrace().toString())
            Log.i("fuck","help1")
        }
        return  this.conn
    }

}
