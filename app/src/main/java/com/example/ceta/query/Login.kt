package com.example.ceta.query

import com.example.ceta.Network.Connection
import java.sql.ResultSet

class Login() {
    var email:String? = null
    var password:String? = null

    fun Login_check(email:String, password:String){
        val conn = Connection()
        val s = conn.connect()!!.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE)
        val r = s.executeQuery("select * from login where email  = '$email' and password = '$password'")
        while (r.next()) {
            this.email = r.getString("email")
            this.password = r.getString("password")
        }
    }

    fun Register_check(email: String) {
        val conn = Connection()
        val s = conn.connect()!!.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE)
        val r = s.executeQuery("select * from login where email  = '$email'")
        while (r.next()) {
            this.email = r.getString("email")
        }
    }
}