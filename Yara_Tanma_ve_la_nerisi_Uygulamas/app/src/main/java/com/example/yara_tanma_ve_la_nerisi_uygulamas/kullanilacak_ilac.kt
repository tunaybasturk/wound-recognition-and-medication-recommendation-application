package com.example.yara_tanma_ve_la_nerisi_uygulamas

import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView

class kullanilacak_ilac : AppCompatActivity() {

    private lateinit var db : DBHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_kullanilacak_ilac)


        db = DBHelper(this)
        val intent = intent
        val yara_id = intent.getIntExtra("yaraIdKey", -1)

        var ilac_isim = ""
        var ilac_bilgi = ""
        var ilac_resim: ByteArray?  = null
        if(yara_id!=-1){
            ilac_isim = db.ilac_ismi_cekme(yara_id)
            ilac_bilgi = db.ilac_bilgi_cekme(yara_id)
            ilac_resim = db.ilac_resim_cekme(yara_id)
            val imageview_ilac_resim = findViewById<ImageView>(R.id.ilac_resim)
            if (ilac_resim != null) {
                val bitmap = BitmapFactory.decodeByteArray(ilac_resim, 0, ilac_resim.size)
                imageview_ilac_resim.setImageBitmap(bitmap)
                imageview_ilac_resim.visibility = View.VISIBLE
            }
            val ilac_bilgi_text= findViewById<TextView>(R.id.ilac_bilgi)
            ilac_bilgi_text.text = ilac_bilgi
            ilac_bilgi_text.visibility = View.VISIBLE

            val ilac_isim_text = findViewById<TextView>(R.id.ilac_isim)
            ilac_isim_text.text = ilac_isim
            ilac_isim_text.visibility = View.VISIBLE
        }

        else{
            val imageview_ilac_resim = findViewById<ImageView>(R.id.ilac_resim)
            val ilac_bilgi_text = findViewById<TextView>(R.id.ilac_bilgi)
            val ilac_isim_text = findViewById<TextView>(R.id.ilac_isim)

            ilac_bilgi_text.visibility = View.GONE
            imageview_ilac_resim.visibility = View.GONE
            ilac_isim_text.visibility = View.GONE


        }

    }





        }

