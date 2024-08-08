package com.example.uasmobileprogramming

import android.content.Intent
import android.database.Cursor
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity


class DetailActivity() : AppCompatActivity() {
    protected var cursor: Cursor? = null
    var database: Database? = null
    var btn_simpan: Button? = null
    var nama: TextView? = null
    var posisi: TextView? = null
    var alamat: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        database = Database(this, null)
        nama = findViewById(R.id.nama)
        posisi = findViewById(R.id.posisi)
        alamat = findViewById(R.id.alamat)
        btn_simpan = findViewById(R.id.btn_simpan)

        val db = database!!.readableDatabase
        cursor = db.rawQuery(
            "SELECT * FROM karyawan WHERE nama ='" +
                    intent.getStringExtra("nama") + "'", null
        )
        cursor!!.moveToPosition(0)
        nama!!.setText(cursor!!.getString(1).toString())
        posisi!!.setText(cursor!!.getString(2).toString())
        alamat!!.setText(cursor!!.getString(3).toString())

        btn_simpan!!.setOnClickListener {
            val uri = Uri.parse("http://maps.google.com/maps?q=${alamat!!.text}")
            val intent = Intent(Intent.ACTION_VIEW, uri)
            intent.setPackage("com.google.android.apps.maps")
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
        }
    }
}