package com.example.sketchbook

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
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



                    startActivity(intent)




                    Toast.makeText(this, result.contents, Toast.LENGTH_LONG).show()


                    }
            }else{
                super.onActivityResult(requestCode, resultCode, data)
            }
        }
    }

}