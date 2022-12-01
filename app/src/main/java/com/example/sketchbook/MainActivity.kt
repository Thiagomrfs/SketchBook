package com.example.sketchbook

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun goToCreateAccount(v: View){
        val intent = Intent(this, CreateAccount::class.java).apply{}
        startActivity(intent)
    }
}