package com.example.uasmobileprogramming

import android.content.Intent
import android.database.Cursor
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.journeyapps.barcodescanner.ScanContract
import com.journeyapps.barcodescanner.ScanOptions
import java.lang.ref.WeakReference

class MainActivity : AppCompatActivity() {
    private var daftar: ArrayList<String>? = null
    private var adapter: ArrayAdapter<String>? = null
    private var database: Database? = null
    private var cursor: Cursor? = null

    val barLauncher = registerForActivityResult(
       ScanContract()
    ) { result ->
        if(result.contents != null){
            val intent = Intent(this, DetailActivity::class.java)
            intent.putExtra("nama", result.contents)
            startActivity(intent)
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(findViewById(R.id.toolbar))

        database = Database(this, null)
        val fb = findViewById<FloatingActionButton>(R.id.fb)
        fb.setOnClickListener {
            val pindah = Intent(this, TambahActivity::class.java)
            startActivity(pindah)
        }
        val scanQr = findViewById<FloatingActionButton>(R.id.scan)
        scanQr.setOnClickListener {
            scanCode()
        }
        setMainActivity(this)
        refresh()
    }

    private fun scanCode(){
        val  scanOptions = ScanOptions()
        scanOptions.setPrompt("Volume up untuk autofocus")
        scanOptions.setBeepEnabled(true)
        scanOptions.setOrientationLocked(true)
        scanOptions.setCaptureActivity(CaptureAct::class.java)
        barLauncher.launch(scanOptions)
    }

    fun refresh(){
        val listView = findViewById<ListView>(R.id.listview)
        val db = database!!.readableDatabase
        cursor = db.rawQuery("select * from karyawan", null)
        daftar = ArrayList()
        cursor!!.moveToFirst()

        for (i in 0 until cursor!!.count) {
            cursor!!.moveToPosition(i)
            daftar!!.add(cursor!!.getString(1))
            adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, daftar!!)
            listView.adapter = adapter
            listView.isSelected = true
            listView.setOnItemClickListener { parent, view, position, id ->
                val selection = daftar!![position]
                val dialogItem = arrayOf("Lihat","Ubah", "Hapus")
                val dialog = android.app.AlertDialog.Builder(this)
                dialog.setTitle("Pilihan")
                dialog.setItems(dialogItem){
                    dialog, which ->
                    when(which){
                        0 -> {
                            val intent = Intent(this, DetailActivity::class.java)
                            intent.putExtra("nama", selection)
                            startActivity(intent)
                        }
                        1 -> {
                            val intent = Intent(this, UbahActivity::class.java)
                            intent.putExtra("nama", selection)
                            startActivity(intent)
                        }
                        2 -> {
                            val db = database!!.writableDatabase
                            db.execSQL("delete from karyawan where nama = '$selection'")
                            Toast.makeText(this@MainActivity, "Data berhasil dihapus", Toast.LENGTH_SHORT).show()
                            refresh()
                        }
                    }
                }
                dialog.create().show()
            }
        }
        adapter?.notifyDataSetInvalidated()
    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_logout -> {
                val intent = Intent(applicationContext, SignInActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(intent)
                finish()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    companion object {
        private var mainActivityRef: WeakReference<MainActivity>? = null

        fun getMainActivity(): MainActivity? {
            return mainActivityRef?.get()
        }

        fun setMainActivity(activity: MainActivity) {
            mainActivityRef = WeakReference(activity)
        }
    }
}