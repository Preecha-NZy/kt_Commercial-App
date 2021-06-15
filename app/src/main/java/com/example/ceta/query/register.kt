package com.example.ceta.query

import com.example.ceta.Network.Connection
import java.sql.PreparedStatement
import java.sql.ResultSet

class register() {
    fun insert_register(email: String, pass: String, fname: String, lname: String, phone: String) {
        val conn = Connection()
        val c = conn.connect()
        val s = c!!.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE)
        val preparedStatement: PreparedStatement = c.prepareStatement("INSERT INTO customer (email, f_name, l_name, phone) VALUES (?,?,?,?)")
        preparedStatement.setString(1, email)
        preparedStatement.setString(2, fname)
        preparedStatement.setString(3, lname)
        preparedStatement.setString(4, phone)
        preparedStatement.execute()
        insert_login(email, pass)
    }

    fun insert_login(email: String, pass: String) {
        val conn = Connection()
        val c = conn.connect()
        val s = c!!.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE)
        val preparedStatement: PreparedStatement = c.prepareStatement("INSERT INTO login (email, password) VALUES (?,?)")
        preparedStatement.setString(1, email)
        preparedStatement.setString(2, pass)
        preparedStatement.execute()
    }
}