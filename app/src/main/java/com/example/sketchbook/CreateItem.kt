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
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.ktx.storage
import java.util.UUID

class CreateItem : AppCompatActivity() {
    private lateinit var itemPic: ImageView
    lateinit var storage: FirebaseStorage

    // item data ----------------------
    private lateinit var nome: EditText
    private lateinit var categoria: EditText
    private lateinit var preco: EditText
    private lateinit var description: EditText
    private var itemImage: Uri? = null
    // --------------------------------

    private val resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val response: Intent? = result.data

            itemPic.setImageURI(response?.data)
            itemImage = response?.data
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_item)

        storage = Firebase.storage

        itemPic = findViewById(R.id.createItemImage)
        nome = findViewById(R.id.createItemName)
        preco = findViewById(R.id.createItemPrice)
        categoria = findViewById(R.id.createItemCategory)
        description = findViewById(R.id.createItemDesc)
    }

    fun getItemImage(v: View) {
        val gallery = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
        resultLauncher.launch(gallery)
    }

    fun saveItem(v: View) {
        if (itemImage == null) {
            Toast.makeText(baseContext, "É necessária uma imagem para criar um item",
                Toast.LENGTH_LONG).show()
            return
        }
        val storageRef = storage.reference
        val imgFolder = storageRef.child("imagens")
        val imgRef = imgFolder.child(UUID.randomUUID().toString())

        val uploadTask = imgRef.putFile(itemImage!!)

        uploadTask.addOnFailureListener { task ->
            Log.d("PDM", "deu erro: " + task.message)
        }.addOnSuccessListener { taskSnapshot ->
            Log.d("PDM", "deu certo")
        }
    }
}