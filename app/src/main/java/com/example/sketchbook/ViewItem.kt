package com.example.sketchbook

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.ImageView

class ViewItem : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_item)

        //Elementos do layout
        val itemName = findViewById<EditText>(R.id.viewItemName)
        val itemCategoria = findViewById<EditText>(R.id.viewItemCategory)
        val itemPreco = findViewById<EditText>(R.id.viewItemPrice)
        val itemDescricao = findViewById<EditText>(R.id.viewItemDesc)
        val itemImage = findViewById<ImageView>(R.id.viewItemImage)


        //Puxa informações do putExtra para elementos do layout
        itemName.setText(intent.getStringExtra("nome"))
        itemCategoria.setText(intent.getStringExtra("categoria"))
        itemPreco.setText(intent.getStringExtra("preco"))
        itemDescricao.setText(intent.getStringExtra("descricao"))
    }
}