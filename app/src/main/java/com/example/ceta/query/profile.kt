package com.example.ceta.query

import com.example.ceta.Network.Connection
import java.sql.ResultSet

class profile () {
    var email: String? = null
    var fname: String? = null
    var lname: String? = null

    fun get_profile (email:String) {
        val conn = Connection()
        val s = conn.connect()!!.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE)
        val r = s.executeQuery("select * from customer where email  = '$email'")
        while (r.next()) {
            this.email = r.getString("email")
            this.fname = r.getString("f_name")
            this.lname = r.getString("l_name")
        }
    }
}