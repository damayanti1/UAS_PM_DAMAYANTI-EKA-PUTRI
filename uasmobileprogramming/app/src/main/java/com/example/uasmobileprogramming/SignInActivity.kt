package com.example.uasmobileprogramming

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast

class SignInActivity : AppCompatActivity() {
    val Tag = "SignIn Activity";
    private var database: Database? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)
        database = Database(this, null)

        val textRegister = findViewById<TextView>(R.id.sgu);

        textRegister.setOnClickListener {
            goToSignUpActivity();
        }

        val buttonRegister = findViewById<Button>(R.id.btn_signin)
        buttonRegister.setOnClickListener {
            authentication();
        }
    }

    private fun goToSignUpActivity() {
        Intent(this, SignUpActivity::class.java).also {
            startActivity(it)
        }
    }

    private fun goToMainActivity() {
        Intent(this, MainActivity::class.java).also {
            startActivity(it)
        }
    }

    @SuppressLint("Recycle")
    private fun authentication() {
        val db = database!!.readableDatabase
        val email = findViewById<EditText>(R.id.edittext_email).text.toString()
        val password = findViewById<EditText>(R.id.edittext_password).text.toString()
        val sql = db.rawQuery(
            "select * from users where email = '$email' and password = '$password'",
            null
        )
        if (sql.count > 0) {
            Toast.makeText(this, "Selamat anda berhasil login", Toast.LENGTH_SHORT).show();
            Log.d(Tag, "Button login di klik");
            goToMainActivity();
        }else{
            Toast.makeText(this, "Email atau password salah", Toast.LENGTH_SHORT).show();
        }
    }
}