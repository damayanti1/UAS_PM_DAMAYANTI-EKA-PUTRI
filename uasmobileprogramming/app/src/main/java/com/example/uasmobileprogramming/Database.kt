package com.example.uasmobileprogramming

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class Database(context: Context, factory: SQLiteDatabase.CursorFactory?) :
    SQLiteOpenHelper(context, "myDatabase.db", factory, 1) {
    override fun onCreate(p0: SQLiteDatabase?) {
        val sql = "create table users (id integer primary key autoincrement, email text, password text)"
        p0?.execSQL(sql)

        val sql1 = "create table karyawan (id integer primary key autoincrement, nama text, posisi text, alamat text)"
        p0?.execSQL(sql1)
    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {

    }
}