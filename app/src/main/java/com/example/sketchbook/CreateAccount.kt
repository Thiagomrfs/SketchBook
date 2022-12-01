package com.example.sketchbook

import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.ImageView
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import com.google.firebase.auth.FirebaseAuth
import java.net.URI

class CreateAccount : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var profilePic: ImageView

    private val resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val response: Intent? = result.data
            Log.d("PDM", response?.data?.path.toString())

            profilePic.setImageURI(response?.data)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_account)
        auth = FirebaseAuth.getInstance()

        profilePic = findViewById(R.id.profileImage)
    }

    fun getImage(v: View) {
        val gallery = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
        resultLauncher.launch(gallery)
    }

    fun goToLogin(v: View){
        val intent = Intent(this, MainActivity::class.java).apply{}
        startActivity(intent)
    }
}