package com.example.sketchbook

import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import io.github.g0dkar.qrcode.QRCode
import java.io.ByteArrayOutputStream

class ShareItem: AppCompatActivity() {
    lateinit var qrCodeImg: ImageView

    //Cria QR code
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_share_item)

        val dataToEncode = intent.getStringExtra("item_code")

        val imageBytes = ByteArrayOutputStream()
            .also { it ->
                dataToEncode?.let { QRCode(it).render() }?.writeImage(it)
            }
            .toByteArray()

        val bmp = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)

        qrCodeImg = findViewById(R.id.qrCodeImg)
        qrCodeImg.setImageBitmap(bmp)
    }

    //Intent para compartilhar código por mensagem
    fun shareItemCode(v: View){
        val itemCode = intent.getStringExtra("item_code")

        val intent = Intent()
        intent.setAction(Intent.ACTION_SEND)


        intent.putExtra(Intent.EXTRA_TEXT, itemCode)

        intent.setType("text/plain")
        startActivity(intent)

    }
}