package com.example.sketchbook

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class MainActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth

    private lateinit var email: EditText
    private lateinit var senha: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        auth = FirebaseAuth.getInstance()
        val currentUser = auth.currentUser
//        if(currentUser != null){
//            val intent = Intent(this, Galery::class.java)
//            startActivity(intent)
//        }


        setContentView(R.layout.activity_main)


        email = findViewById(R.id.editTextEmail)
        senha = findViewById(R.id.editTextPassword)
    }

    fun goToCreateAccount(v: View){
        val intent = Intent(this, CreateAccount::class.java).apply{}
        startActivity(intent)
    }

    fun checarLogin(v: View) {
        auth.signInWithEmailAndPassword(email.text.toString(), senha.text.toString())
            .addOnCompleteListener(this) { task ->
            if (task.isSuccessful) {
                val user = auth.currentUser
                val intent = Intent(this, Galery::class.java)
                startActivity(intent)
            } else {
                Log.w("PDM", "createUserWithEmail:failure", task.exception)
                Toast.makeText(baseContext, "Autenticação falha",
                    Toast.LENGTH_SHORT).show()
            }
        }
    }

}
