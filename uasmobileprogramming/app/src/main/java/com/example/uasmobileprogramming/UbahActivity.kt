package com.example.uasmobileprogramming

import android.database.Cursor
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class UbahActivity() : AppCompatActivity() {
    protected var cursor: Cursor? = null
    var database: Database? = null
    var btn_simpan: Button? = null
    var nama: EditText? = null
    var posisi: EditText? = null
    var alamat: EditText? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ubah)
        database = Database(this, null)
        nama = findViewById(R.id.nama)
        alamat = findViewById(R.id.alamat)
        posisi = findViewById(R.id.posisi)
        btn_simpan = findViewById(R.id.btn_simpan)

        val db = database!!.readableDatabase
        cursor = db.rawQuery(
            "SELECT * FROM karyawan WHERE nama ='" +
                    intent.getStringExtra("nama") + "'", null
        )
        cursor!!.moveToFirst()
        if (cursor!!.getCount() > 0) {
            cursor!!.moveToPosition(0)
            nama!!.setText(cursor!!.getString(1).toString())
            posisi!!.setText(cursor!!.getString(2).toString())
            alamat!!.setText(cursor!!.getString(3).toString())
        }

        btn_simpan!!.setOnClickListener {
            val db = database!!.writableDatabase
            db.execSQL(
                ("update karyawan set nama='" +
                        nama!!.getText().toString() + "', posisi='" +
                        posisi!!.getText().toString() + "', alamat='" + alamat!!.getText()
                    .toString() + "' where nama = '" +
                        intent.getStringExtra("nama") + "'")
            )
            Toast.makeText(this@UbahActivity, "Data berhasil diubah", Toast.LENGTH_SHORT).show()
            MainActivity.getMainActivity()!!.refresh()
            finish()
        }
    }
}