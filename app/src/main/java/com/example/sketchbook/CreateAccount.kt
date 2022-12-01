package com.example.sketchbook

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View

class CreateAccount : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_account)
    }

    fun goToLogin(v: View){
        val intent = Intent(this, MainActivity::class.java).apply{}
        startActivity(intent)
    }
}