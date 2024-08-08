package com.example.uasmobileprogramming

import android.content.Intent
import android.database.sqlite.SQLiteDatabase
import android.nfc.Tag
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast

class SignUpActivity : AppCompatActivity() {
    private val Tag = "SignUp Activity";
    private var database :Database? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)
        database = Database(this, null)

        val textRegister = findViewById<TextView>(R.id.sgi);

        textRegister.setOnClickListener {
            goToSignInActivity();
        }


        val buttonRegister = findViewById<Button>(R.id.btn_register)
        buttonRegister.setOnClickListener {
            register()
            goToMainActivity();
        }

    }
    private fun goToSignInActivity() {
        Intent(this, SignInActivity::class.java).also {
            startActivity(it)
        }
    }
    private fun goToMainActivity() {
        Intent(this, SignInActivity::class.java).also {
            startActivity(it)
        }
    }

    private fun register(){
        val db = database!!.writableDatabase
        val email = findViewById<EditText>(R.id.edittext_email).text.toString()
        val password = findViewById<EditText>(R.id.edittext_password).text.toString()
        val confirmPassword = findViewById<EditText>(R.id.edittext_confirmpassword).text.toString()
        if(password == confirmPassword){
            db.execSQL("insert into users (email, password) values ('$email','$password')")
        }else{
            Toast.makeText(this, "Password tidak sama", Toast.LENGTH_SHORT).show()
        }
    }
}