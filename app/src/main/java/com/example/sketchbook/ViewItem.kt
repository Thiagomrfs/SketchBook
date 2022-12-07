package com.example.sketchbook

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage

class ViewItem : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_item)

        val storageReference = Firebase.storage.reference


        //Elementos do layout
        val itemName = findViewById<EditText>(R.id.viewItemName)
        val itemCategoria = findViewById<EditText>(R.id.viewItemCategory)
        val itemPreco = findViewById<EditText>(R.id.viewItemPrice)
        val itemDescricao = findViewById<EditText>(R.id.viewItemDesc)
        val itemImage = findViewById<ImageView>(R.id.viewItemImage)
        val shareImageButton = findViewById<ImageButton>(R.id.shareImageButton)

        if(intent.getStringExtra("item_code") == null){
            shareImageButton.visibility = View.GONE
        }


        //Puxa informações do putExtra para elementos do layout
        itemName.setText(intent.getStringExtra("nome"))
        itemCategoria.setText(intent.getStringExtra("categoria"))
        itemPreco.setText(intent.getStringExtra("preco"))
        itemDescricao.setText(intent.getStringExtra("descricao"))
        GlideApp.with(this /* context */)
            .load(storageReference.child("imagens").child(intent.getStringExtra("imagem").toString()))
            .into(itemImage)
    }

    //Inicia atividade de compartilhar item
    fun goToShare(v: View) {
        val newIntent = Intent(this, ShareItem::class.java)
        newIntent.putExtra("item_code", intent.getStringExtra("item_code"))
        startActivity(newIntent)
    }
}