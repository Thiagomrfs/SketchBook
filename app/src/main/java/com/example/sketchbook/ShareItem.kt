package com.example.sketchbook

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class ShareItem: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_item)

        val code = intent.getStringExtra("item_code")
    }
}