package com.example.yara_tanma_ve_la_nerisi_uygulamas

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import papaya.`in`.sendmail.SendMail
import java.io.ByteArrayOutputStream
import kotlin.random.Random
import kotlin.random.nextInt

class yara_tanima : AppCompatActivity() {

    private lateinit var acilabilir_liste_ok : ImageView
    lateinit var recyclerviewadapter : RecyclerViewAdapter
    lateinit var recyclerView: RecyclerView
    private lateinit var Belirti_List : ArrayList<String>
    private lateinit var yara_tanima : Button
    private lateinit var selectedTextView : ArrayList<String>
    private lateinit var db:DBHelper
    private lateinit var yara_isim : String
    var giris_maili : String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_yara_tanima)


        db = DBHelper(this)
        yara_tanima = findViewById(R.id.hastaliğimi_bul)
        Belirti_List = ArrayList()
        selectedTextView = ArrayList()

        giris_maili = intent.getStringExtra("email").toString()

        recyclerviewadapter = RecyclerViewAdapter()

        recyclerView = findViewById(R.id.recyclerview)

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = recyclerviewadapter


        acilabilir_liste_ok = findViewById(R.id.acilabilir_liste_ok)

        val spinnerOptions = arrayOf("El","Ayak")
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, spinnerOptions)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        val spinner = findViewById<Spinner>(R.id.yara_bolge_liste)
        spinner.adapter = adapter



        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                acilabilir_liste_ok.visibility = View.VISIBLE
                val selectedValue = spinnerOptions[position]
                Toast.makeText(this@yara_tanima, "Seçilen öğe: $selectedValue", Toast.LENGTH_SHORT).show()
                Belirti_List=db.bölgeye_göre_belirti_alma(selectedValue)
                recyclerviewadapter.updateTextList(Belirti_List)
                recyclerviewadapter.size_esitleme()
                for (i in Belirti_List){
                    Log.d("VENÜS"," BELİRTİ  $i")
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                acilabilir_liste_ok.visibility = View.GONE
            }
        }



        yara_tanima.setOnClickListener{

            selectedTextView = recyclerviewadapter.getSelectedTextList()

            yara_isim = db.cilt_hastalik_bul(selectedTextView)

            Log.d("MAİL","doğru muuu  $giris_maili")
            showAlertDialog(this,yara_isim,giris_maili)


            Log.d("SATURN",selectedTextView.size.toString()+ " Boyut")
            for (i in selectedTextView) {
                Log.d("SATURN ", i + "geldi")
            }

        }



    }



    fun showAlertDialog(context: Context, yara_isim: String,gonderilecek_mail: String) {
        val builder = AlertDialog.Builder(context)
        val inflater = LayoutInflater.from(context)
        val dialogView = inflater.inflate(R.layout.custom_dialog, null)
        var yara_id =-1

        val messageTextView = dialogView.findViewById<TextView>(R.id.messageTextView)
        if(yara_isim == ""){
            messageTextView.text = "Cilt hastlağınıza kesin bir tanı konulamadı ana menüye yönlendiriliyorsunuz.\n"
        }
        else{
            messageTextView.text = "Cilt hastalığınıza $yara_isim tanısı konuldu ana menüye döndürülüyorsunuz. Ana menüden cilt hastalığınızın bilgisine ve kullanacağınız ilacınızın bilgisine ulaşabilirsiniz\n"
            yara_id = db.yara_id_cekme(yara_isim)
            Log.d("SATURN","geldi$yara_isim")
            mail_gonderme(yara_isim,gonderilecek_mail)

        }
        val okButton = dialogView.findViewById<Button>(R.id.okButton)
        okButton.setOnClickListener {
            val intent = Intent(context, ana_menu::class.java)
            intent.putExtra("yaraIdKey", yara_id)
            context.startActivity(intent)
        }

        builder.setView(dialogView)

        val dialog = builder.create()
        dialog.show()
    }

    fun mail_gonderme(yara_ismi: String,email:String){
        var mail = SendMail("tunabstrk041@gmail.com","gxqoohxqgorqgvgw",email,"Yara Tanıma ve İlaç Önerme Uygulaması Güvenlik Kodu",
            "Hastalığınıza $yara_ismi tanısı  konulmuştur. ")
        mail.execute()
    }



}