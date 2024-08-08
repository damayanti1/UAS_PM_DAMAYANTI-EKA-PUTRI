package com.example.uasmobileprogramming

import android.database.Cursor
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity


class TambahActivity : AppCompatActivity() {
    protected var cursor: Cursor? = null
    var database: Database? = null
    var btn_simpan: Button? = null
    var nama: EditText? = null
    var posisi: EditText? = null
    var alamat: EditText? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tambah)
        database = Database(this, null)
        nama = findViewById(R.id.nama)
        posisi = findViewById(R.id.posisi)
        alamat = findViewById(R.id.alamat)
        btn_simpan = findViewById(R.id.btn_simpan)
        btn_simpan!!.setOnClickListener {
            val db = database!!.writableDatabase
            db.execSQL(
                "insert into karyawan(nama, posisi, alamat) values('" +
                        nama!!.text.toString() + "','" +posisi!!.text.toString() + "','" + alamat!!.text.toString() + "')"
            )
            Toast.makeText(this@TambahActivity, "Data Tersimpan Bro", Toast.LENGTH_LONG).show()
            MainActivity.getMainActivity()!!.refresh()
            finish()
        }
    }
}