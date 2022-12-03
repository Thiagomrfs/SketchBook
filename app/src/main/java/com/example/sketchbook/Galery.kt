package com.example.sketchbook

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.google.firebase.auth.FirebaseAuth

class Galery : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_galery)
        auth = FirebaseAuth.getInstance()

        Log.d("PDM", auth.currentUser?.email.toString())
    }

    fun goToCriarDesenho(v: View){
        val intent = Intent(this, CreateItem::class.java)
        startActivity(intent)
    }
}