package com.example.sketchbook

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        auth = FirebaseAuth.getInstance()
    }

    fun goToCreateAccount(v: View){
        val intent = Intent(this, CreateAccount::class.java).apply{}
        startActivity(intent)
    }

    fun checarLogin(v: View) {
        val email = "thiagomrfs@gmail.com"
        val senha = "thiago"

        auth.signInWithEmailAndPassword(email, senha)
            .addOnCompleteListener(this) { task ->
            if (task.isSuccessful) {
                // Sign in success, update UI with the signed-in user's information
                Log.d("PDM", "createUserWithEmail:success")
                val user = auth.currentUser
                Toast.makeText(baseContext, "Autenticação deu certo",
                    Toast.LENGTH_SHORT).show()
                //updateUI(user)
            } else {
                // If sign in fails, display a message to the user.
                Log.w("PDM", "createUserWithEmail:failure", task.exception)
                Toast.makeText(baseContext, "Autenticação falha",
                    Toast.LENGTH_SHORT).show()
                //updateUI(null)
            }
        }
    }

}
