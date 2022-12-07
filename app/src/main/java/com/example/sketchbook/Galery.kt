package com.example.sketchbook

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.sketchbook.model.Item
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase



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
                val imagem = valor.child("image").getValue(String::class.java)
                val descricao = valor.child("descrição").getValue(String::class.java)
                val preco = "R$ " + valor.child("preço").getValue(String::class.java)
                val categoria = valor.child("categoria").getValue(String::class.java)
                val nome = valor.child("nome").getValue(String::class.java)
                val id = valor.key.toString()

                val item = Item(id, imagem!!, descricao, preco, categoria, nome!!)
                listItems += item

            }

            //Configurando recycler view com novos desenhos
            val adapter = ItemAdapter(listItems, this)
            recyclerViewGalery.adapter = adapter
            adapter.notifyDataSetChanged()


        }.addOnFailureListener{
            Log.d("PDM", "Error getting data", it)
        }


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
        intent.putExtra("item_code", listItems[position].id)
        startActivity(intent)
    }

    //Ir para página de visualização de desenho
    fun goToCriarDesenho(v: View){
        val intent = Intent(this, CreateItem::class.java)
        startActivity(intent)
    }

    fun goToSearch(v: View){
        val intent = Intent(this, SearchItem::class.java)
        startActivity(intent)
    }
}