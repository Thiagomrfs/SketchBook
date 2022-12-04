package com.example.sketchbook


import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.sketchbook.model.Item

class ItemAdapter(private val items: MutableList<Item>) : RecyclerView.Adapter<ItemAdapter.ViewHolder>(){

    // Criação de Novos ViewHolders
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        // infla o card_previous_games
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_layout, parent, false)

        return ViewHolder(view)
    }

    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        val image: ImageView = ItemView.findViewById(R.id.recyclerItemImage)
        val nome: TextView = ItemView.findViewById(R.id.recyclerItemName)
        val categoria: TextView = ItemView.findViewById(R.id.recyclerItemCategory)
        val preco: TextView = ItemView.findViewById(R.id.recyclerItemPrice)
        val inCell: LinearLayout = ItemView.findViewById(R.id.inCell)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val itemResgatado = items[position]

        holder.nome.text = itemResgatado.nome
        holder.preco.text = itemResgatado.preco
        holder.categoria.text = itemResgatado.categoria

        holder.inCell.setOnClickListener{


        }


    }

    override fun getItemCount(): Int {
        return items.size
    }

}