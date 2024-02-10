package com.example.yara_tanma_ve_la_nerisi_uygulamas

import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView

class yara_bilgilendirme : AppCompatActivity() {


    private lateinit var db : DBHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_yara_bilgilendirme)

        db = DBHelper(this)

        val intent = intent
        val yara_id = intent.getIntExtra("yaraIdKey", -1)

        var yara_isim = ""
        var yara_bilgi = ""
        var yara_resim: ByteArray?  = null
        if(yara_id!=-1){
            yara_isim = db.yara_ismi_cekme(yara_id)
            yara_bilgi = db.yara_bilgi_cekme(yara_id)
            yara_resim = db.yara_resim_cekme(yara_id)
            val imageview_yara_resim = findViewById<ImageView>(R.id.yara_resim)
            if (yara_resim != null) {
                val bitmap = BitmapFactory.decodeByteArray(yara_resim, 0, yara_resim.size)
                imageview_yara_resim.setImageBitmap(bitmap)
                imageview_yara_resim.visibility = View.VISIBLE
            }
            val yara_bilgi_text= findViewById<TextView>(R.id.yara_bilgi)
            yara_bilgi_text.text = yara_bilgi
            yara_bilgi_text.visibility = View.VISIBLE

            val yara_isim_text = findViewById<TextView>(R.id.yara_isim)
            yara_isim_text.text = yara_isim
            yara_isim_text.visibility = View.VISIBLE
        }

        else{
            val imageview_yara_resim = findViewById<ImageView>(R.id.yara_resim)
            val yara_bilgi_text = findViewById<TextView>(R.id.yara_bilgi)
            val yara_isim_text = findViewById<TextView>(R.id.yara_isim)

            yara_bilgi_text.visibility = View.GONE
            imageview_yara_resim.visibility = View.GONE
            yara_isim_text.visibility = View.GONE


        }

    }

}