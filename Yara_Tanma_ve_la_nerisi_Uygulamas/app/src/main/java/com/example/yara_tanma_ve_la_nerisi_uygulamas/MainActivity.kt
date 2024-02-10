package com.example.yara_tanma_ve_la_nerisi_uygulamas

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {

    private lateinit var giris_buton : Button
    private lateinit var kullanici_mail : EditText
    private lateinit var kullanici_sif : EditText
    private lateinit var dbh : DBHelper
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var kayit_text : TextView
    var kayitli_gelen_mail : String = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        giris_buton = findViewById(R.id.giris)
        kullanici_mail = findViewById(R.id.email_giris)
        kullanici_sif = findViewById(R.id.sifre_giris)
        kayit_text = findViewById(R.id.kayit_ol)
        dbh = DBHelper(this)



        firebaseAuth = FirebaseAuth.getInstance()


        kayit_text.setOnClickListener {
            val intent = Intent(this, kayit_olma::class.java)
            startActivity(intent)
        }

        giris_buton.setOnClickListener {
           val kullanici_mail_text = kullanici_mail.text.toString()
           val kullanici_sif_text =  kullanici_sif.text.toString()
            val tutucu_mail = kullanici_mail.text.toString()


            if(TextUtils.isEmpty(kullanici_mail_text) || TextUtils.isEmpty(kullanici_sif_text)){
                Toast.makeText(this,"Lütfen boş alan bırakmayınız",Toast.LENGTH_SHORT).show()
            }
            else{
                firebaseAuth.signInWithEmailAndPassword(kullanici_mail_text, kullanici_sif_text).addOnCompleteListener{
                    if (it.isSuccessful){
                        kullanici_mail.text.clear()
                        kullanici_sif.text.clear()
                        val intent = Intent(this, ana_menu::class.java)
                        intent.putExtra("email",tutucu_mail)
                        startActivity(intent)
                    } else {
                        Toast.makeText(this, it.exception.toString(), Toast.LENGTH_SHORT).show()
                    }
                }
            }

        }
                /*
                val kullanici_kontrol = dbh.sifre_kontrol(kullanici_ad_text,kullanici_sif_text)
                if(kullanici_kontrol==true){
                    Toast.makeText(this,"Giriş Başarılı",Toast.LENGTH_SHORT).show()
                    kullanici_ad.text.clear()
                    kullanici_sif.text.clear()
                    val intent = Intent(applicationContext,ana_menu::class.java)
                    startActivity(intent)
                }
                else{
                    Toast.makeText(this,"Hatalı Kullanıcı Adı veya Şifre",Toast.LENGTH_SHORT).show()
                }
*/

            }

}






