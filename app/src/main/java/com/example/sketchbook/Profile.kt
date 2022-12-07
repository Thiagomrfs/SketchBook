package com.example.sketchbook

import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.ktx.storage
import java.util.*

class Profile : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth

    // campos do user ---------------
    private lateinit var email: EditText
    private lateinit var endereco: EditText
    private lateinit var telefone: EditText
    private lateinit var profilePic: ImageView
    // -----------------------------

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        //Inicializando e referenciando firebase storage
        auth = FirebaseAuth.getInstance()
        val storageReference = Firebase.storage.reference

        if(auth.currentUser == null){
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
        val currentUser = auth.currentUser

        profilePic = findViewById(R.id.profilePageImage)
        email = findViewById(R.id.profilePageEmail)
        endereco = findViewById(R.id.profilePageAddress)
        telefone = findViewById(R.id.profilePagePhone)

        val mydb = FirebaseDatabase.getInstance().reference
        val user = mydb.child("usuarios").child(currentUser!!.uid)

        user.get().addOnSuccessListener {
            email.setText(currentUser.email)
            endereco.setText(it.child("endereço").value.toString())
            telefone.setText(it.child("telefone").value.toString())
            GlideApp.with(this /* context */)
                .load(
                    storageReference.child("userPictures")
                        .child(it.child("perfilImg").value.toString()))
                .into(profilePic)
        }
    }
}