package com.example.sketchbook

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.FirebaseDatabase
import com.google.zxing.integration.android.IntentIntegrator
import com.google.zxing.integration.android.IntentResult

class SearchItem : AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_item)

        val btnScanner = findViewById<Button>(R.id.btnLerQRCode)
        btnScanner.setOnClickListener{
            val scanner = IntentIntegrator(this)
            scanner.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE)
            scanner.setBeepEnabled(false)
            scanner.initiateScan()
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(resultCode == Activity.RESULT_OK){
            val result: IntentResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, data)
            if(result != null){
                if(result.contents == null){
                    Toast.makeText(this, "Nenhum resultado obtido!", Toast.LENGTH_LONG).show()
                }else {
                    val intent = Intent(this, ViewItem::class.java)
                    val mydb = FirebaseDatabase.getInstance().reference
                    val desenho = mydb.child("desenhos").child(result.contents)

                    desenho.get().addOnSuccessListener {
                        intent.putExtra("nome", it.child("nome").value.toString())
                        Log.d("PDM", it.child("nome").value.toString())
                        intent.putExtra("categoria", it.child("categoria").value.toString())
                        intent.putExtra("preco", it.child("preço").value.toString())
                        intent.putExtra("descricao", it.child("descrição").value.toString())
                        intent.putExtra("imagem", it.child("image").value.toString())
                        startActivity(intent)
                    }

                    }
            }else{
                super.onActivityResult(requestCode, resultCode, data)
            }
        }
    }

    fun searchItem(v: View){
        val codigoInput = findViewById<EditText>(R.id.editTextCodigoImagem)

        val intent = Intent(this, ViewItem::class.java)
        val mydb = FirebaseDatabase.getInstance().reference
        val desenho = mydb.child("desenhos").child(codigoInput.text.toString())

        desenho.get().addOnSuccessListener {
            intent.putExtra("nome", it.child("nome").value.toString())
            Log.d("PDM", it.child("nome").value.toString())
            intent.putExtra("categoria", it.child("categoria").value.toString())
            intent.putExtra("preco", it.child("preço").value.toString())
            intent.putExtra("descricao", it.child("descrição").value.toString())
            intent.putExtra("imagem", it.child("image").value.toString())
            startActivity(intent)
        }
    }



}