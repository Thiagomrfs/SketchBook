package com.example.sketchbook


import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.sketchbook.model.Item
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage


class ItemAdapter(private val items: MutableList<Item>, private val onItemClickListener: OnItemClickListener) : RecyclerView.Adapter<ItemAdapter.ViewHolder>(){


    val context: Context? = null


    // Criação de Novos ViewHolders
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        // infla o card_previous_games
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_layout, parent, false)

        return ViewHolder(view)
    }

    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView), View.OnClickListener {

        //Elementos do layout do card
        val image: ImageView = ItemView.findViewById(R.id.recyclerItemImage)
        val nome: TextView = ItemView.findViewById(R.id.recyclerItemName)
        val categoria: TextView = ItemView.findViewById(R.id.recyclerItemCategory)
        val preco: TextView = ItemView.findViewById(R.id.recyclerItemPrice)
        val inCell: LinearLayout = ItemView.findViewById(R.id.inCell)


        override fun onClick(v: View?) {

        }


    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        val storageReference = Firebase.storage.reference
        Log.w("PDM", item.image)

        //Seta informações dos elementos do card
        holder.nome.text = item.nome
        holder.preco.text = item.preco
        holder.categoria.text = item.categoria
        GlideApp.with(holder.itemView)
            .load(storageReference.child("imagens").child(item.image))
            .into(holder.image)

        //Executa função para clique nos elementos
        holder.itemView.setOnClickListener{
                onItemClickListener.onItemClicked(position)
        }

    }

    override fun getItemCount(): Int {
        return items.size
    }

}