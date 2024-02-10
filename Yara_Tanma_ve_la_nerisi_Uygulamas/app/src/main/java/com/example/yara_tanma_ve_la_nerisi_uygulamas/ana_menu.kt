package com.example.yara_tanma_ve_la_nerisi_uygulamas

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.Spinner

class ana_menu : AppCompatActivity() {

    private lateinit var yara_bilgi_resim: ImageView
    private lateinit var kullanilacak_ilac_resim: ImageView
    private lateinit var yara_tani_resim: ImageView
    var kayitli_gelen_mail : String = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ana_menu)


        val intent = intent
        val yara_id = intent.getIntExtra("yaraIdKey", -1)
        kayitli_gelen_mail = intent.getStringExtra("email").toString()

        yara_bilgi_resim = findViewById(R.id.yara_bilgilendirme_resim)
        kullanilacak_ilac_resim = findViewById(R.id.kullanilcak_ilac_resim)
        yara_tani_resim = findViewById(R.id.yara_tanima_resim)


        yara_bilgi_resim.setOnClickListener {
            val intent = Intent(this, yara_bilgilendirme::class.java)
            intent.putExtra("yaraIdKey", yara_id)
            startActivity(intent)
        }

        kullanilacak_ilac_resim.setOnClickListener {
            val intent = Intent(this, kullanilacak_ilac::class.java)
            intent.putExtra("yaraIdKey", yara_id)
            startActivity(intent)
        }

        yara_tani_resim.setOnClickListener {
            val intent = Intent(this, yara_tanima::class.java)
            intent.putExtra("email",kayitli_gelen_mail)
            startActivity(intent)
        }
    }
}