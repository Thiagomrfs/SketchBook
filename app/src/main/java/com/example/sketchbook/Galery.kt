package com.example.sketchbook

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.sketchbook.model.Item
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import java.util.Objects
import kotlin.reflect.typeOf


class Galery : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth

    private  var listItems : MutableList<Item> = mutableListOf()



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_galery)
        auth = FirebaseAuth.getInstance()

        var recyclerViewGalery : RecyclerView = findViewById(R.id.recyclerViewGalery)

        recyclerViewGalery.layoutManager = LinearLayoutManager(this)





        if(auth.currentUser == null){
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        val mydb = FirebaseDatabase.getInstance().reference
        val desenhos = mydb.child("desenhos")

        val desenhosFiltrados = desenhos
            .orderByChild("usuario")
            .equalTo(auth.currentUser?.uid.toString())

//        desenhosFiltrados.addChildEventListener(object : ChildEventListener {
//            override fun onChildAdded(dataSnapshot: DataSnapshot, s: String?) {}
//            override fun onChildChanged(dataSnapshot: DataSnapshot, s: String?) {}
//            override fun onChildRemoved(dataSnapshot: DataSnapshot) {}
//            override fun onChildMoved(dataSnapshot: DataSnapshot, s: String?) {}
//            override fun onCancelled(databaseError: DatabaseError) {}
//        })


        desenhosFiltrados.get().addOnSuccessListener {
//            Log.d("PDM", "Got value ${it.value}")

            for(valor in it.children){
                var image = valor.child("image").getValue(String::class.java)
                var descricao = valor.child("descrição").getValue(String::class.java)
                var preco = "R$ " + valor.child("preço").getValue(String::class.java)
                var categoria = valor.child("categoria").getValue(String::class.java)
                var nome = valor.child("nome").getValue(String::class.java)

                var item = Item(image!!,descricao,preco,categoria,nome!!)

                listItems += item

            }

            var adapter = ItemAdapter(listItems)

            recyclerViewGalery.adapter = adapter


        }.addOnFailureListener{
            Log.d("PDM", "Error getting data", it)
        }











    }

    fun goToCriarDesenho(v: View){
        val intent = Intent(this, CreateItem::class.java)
        startActivity(intent)
    }
}