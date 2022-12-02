package com.example.sketchbook

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.google.firebase.auth.FirebaseAuth

class Galery : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_galery)
        auth = FirebaseAuth.getInstance()

        Log.d("PDM", auth.currentUser?.email.toString())
    }
}