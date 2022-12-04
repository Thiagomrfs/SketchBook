package com.example.sketchbook

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
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


class Galery : AppCompatActivity(), OnItemClickListener {
    private lateinit var auth: FirebaseAuth

    //Informações dos desenhos puxados do banco
    var listItems : MutableList<Item> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {


        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_galery)
        auth = FirebaseAuth.getInstance()

        //Lista de desenhos
        var recyclerViewGalery : RecyclerView = findViewById(R.id.recyclerViewGalery)
        recyclerViewGalery.layoutManager = LinearLayoutManager(this)



        if(auth.currentUser == null){
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        //Query de acesso aos desenhos a partir do ID do usuário
        val mydb = FirebaseDatabase.getInstance().reference
        val desenhos = mydb.child("desenhos")

        val desenhosFiltrados = desenhos
            .orderByChild("usuario")
            .equalTo(auth.currentUser?.uid.toString())


        //Resgatando desenhos do banco e passando para a lista
        desenhosFiltrados.get().addOnSuccessListener {
            for(valor in it.children){
                var imagem = valor.child("image").getValue(String::class.java)
                var descricao = valor.child("descrição").getValue(String::class.java)
                var preco = "R$ " + valor.child("preço").getValue(String::class.java)
                var categoria = valor.child("categoria").getValue(String::class.java)
                var nome = valor.child("nome").getValue(String::class.java)

                var item = Item(imagem!!,descricao,preco,categoria,nome!!)
                listItems += item

            }

            //Configurando recycler view com novos desenhos
            var adapter = ItemAdapter(listItems, this)
            recyclerViewGalery.adapter = adapter
            adapter.notifyDataSetChanged()


        }.addOnFailureListener{
            Log.d("PDM", "Error getting data", it)
        }


    }

    //Ir para página de visualização de desenho
    fun goToCriarDesenho(v: View){
        val intent = Intent(this, CreateItem::class.java)
        startActivity(intent)
    }

    //Carrega atividade para desenho clicado com suas informações
    override fun onItemClicked(position: Int) {
        super.onItemClicked(position)
        val intent = Intent(this, ViewItem::class.java)
        intent.putExtra("nome", listItems[position].nome)
        intent.putExtra("descricao", listItems[position].descricao)
        intent.putExtra("categoria", listItems[position].categoria)
        intent.putExtra("preco", listItems[position].preco)
        intent.putExtra("imagem", listItems[position].image)
        startActivity(intent)
    }
}