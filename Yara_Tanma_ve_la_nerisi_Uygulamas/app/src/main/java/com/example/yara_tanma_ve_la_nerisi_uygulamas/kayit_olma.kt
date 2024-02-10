package com.example.yara_tanma_ve_la_nerisi_uygulamas

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.util.Patterns
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.yara_tanma_ve_la_nerisi_uygulamas.databinding.ActivityKayitOlmaBinding
import org.w3c.dom.Text
import java.io.ByteArrayOutputStream

class kayit_olma : AppCompatActivity() {

    private lateinit var kullanici_ad : EditText
    private lateinit var kullanici_sif : EditText
    private lateinit var kullanici_sif_tekrar : EditText
    private lateinit var kayitolbuton : Button
    private lateinit var db:DBHelper
    lateinit var    binding : ActivityKayitOlmaBinding

    private lateinit var yara_belirtileri_list : ArrayList<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityKayitOlmaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.girisYap.setOnClickListener{
            intent= Intent(this,MainActivity::class.java)
            startActivity(intent)
        }

        db = DBHelper(this)
       yara_belirtileri_list = ArrayList()
        val sifreKayitText = "myPassword"
        val sifreTekrariKayitText = "myPassword"

        if (sifreKayitText.equals(sifreTekrariKayitText)) {
            Log.d("DEBUG", "Şifreler Eşleşmiyor")
        } else {
            Log.d("DEBUG", "Şifreler Eşleşiyor")
        }

        val yara: Bitmap = BitmapFactory.decodeResource(resources, R.drawable.el_egzema) // Resmi yükleyin
        val byteArrayyara: ByteArray = convertBitmapToByteArray(yara)
        val ilac: Bitmap = BitmapFactory.decodeResource(resources, R.drawable.dermovate)
        val byteArrayilac: ByteArray = convertBitmapToByteArray(ilac)



        yara_belirtileri_list.add("Ciltte kuruluk")
        yara_belirtileri_list.add("Kaşıntı")
        yara_belirtileri_list.add("Kabarcık oluşumu")
        yara_belirtileri_list.add("Deride pullanma")
        yara_belirtileri_list.add("Kızarıklık")
        yara_belirtileri_list.add("İltihap")
        yara_belirtileri_list.add("Saç derisinde kepeklenme")

/*

        yara_belirtileri_list.add("Kapalı beyaz noktalar")
        yara_belirtileri_list.add("Siyah noktalar")
        yara_belirtileri_list.add("Kırmızı kabarıklıklar")
        yara_belirtileri_list.add("İltihaplı kabarıklıklar")

*/
     //  db.yara_ekleme("El",yara_belirtileri_list,"Egzema","Cilt yüzeyinde kuruluk ve kaşıntı veren kabarcıklar oluşumu ile karakterizedir. Kuruyup pullanan bu kabarcıklar, zamanla çatlayarak mikroorganizmaların girişine açık hale gelebilir. Bu nedenle egzama hastası bireylerde enfeksiyon da sıklıkla görülür. Hastalık genellikle çocukluğun erken dönemlerinde ortaya çıkmakla birlikte her yaşta görülebilir.",byteArrayyara)
       // db.yara_ekleme("El",yara_belirtileri_list,"Akne","Akne, cildin orta tabakasında bulunan sebum yani yağ salgılayan kanalların tıkanması, şişmesi ve daha sonra bakterilerle inflame olması sonucu ortaya çıkar. Deride artan yağ salgısı ve gözeneklerin tıkanması sonucu siyah nokta (komedon) oluşur.",byteArrayyara)
   //  db.ilac_ekleme("Dermovate","Dermovate krem genellikle sedef ve egzama tedavisinde kullanılan, uzmanların en sık reçete ettiği bir ilaçtır. Egzama gibi rahatsızlıklarla başa çıkamayan kişilere adete mucize gibi iyi gelen Dermavote egzamaya bağlı oluşan kaşıntı ve kızarıklığı da kısa sürede giderir. Ancak bu kadar etkili olmasıyla beraber yan etkileri de olan bir ilaçtır. Mutlaka doktor gözetiminde kullanılması önerilen bu krem kortizonlu kremler arasında yer alır.",byteArrayilac)
  //db.ilac_ekleme("Benzamycin Topikal Jel","Benzamycin topikal jel, sivilce tedavisinde kullanılan, sivilcelere kesin çözüm olması için doktorlar tarafından hastalarına sıkça reçete edilen, en iyi sivilce kremi markaları arasında üst sıralarda yer alan bir üründür. Çok güçlü bir etkiye sahip olan krem düzenli kullanımda uzun vadeli etki edecek kadar başarılıdır.",byteArrayilac)


        binding.kayitOl.setOnClickListener{

            val kullanici_email_text = binding.emailKayit.text.toString().trim()
            val kullanici_sif_text = binding.sifreKayit.text.toString().trim()
            val kullanici_sif_tekrar_text = binding.sifreTekrariKayit.text.toString().trim()




            if(TextUtils.isEmpty(kullanici_email_text)){
                binding.kayitOlEmail.error = "Email Giriniz"
            }
            else if(!email_kontrol(kullanici_email_text)){
                binding.kayitOlEmail.error = "Geçersiz Email"
            }
            else if(TextUtils.isEmpty(kullanici_sif_text)){
                binding.kayitOlSifre.error = "Şifre Giriniz"
            }
            else if(kullanici_sif_text.length <8){
                binding.kayitOlSifre.error = "Şifre en az 8 karakter olmalı"
            }
            else if(TextUtils.isEmpty(kullanici_sif_tekrar_text)){
                binding.kayitOlSifreTekrar.error = "Şifre Giriniz"
            }
            else if(!(kullanici_sif_text.equals(kullanici_sif_tekrar_text))){
                Log.d("SATURN","${binding.sifreKayit.text}")
                Log.d("SATURN","${binding.sifreTekrariKayit.text}")
                Log.d("VENUS","${(binding.sifreKayit.text.trim().toString().equals(binding.sifreTekrariKayit.toString().trim()))}")
                binding.kayitOlSifreTekrar.error= "Şifreler Eşleşmiyor"
            }
            else{
                var intent = Intent(this@kayit_olma,OtpActivity::class.java)
                intent.putExtra("email",binding.emailKayit.text.toString())
                intent.putExtra("sifre",binding.sifreKayit.text.toString())
                startActivity(intent)
            }



/*

            val kullanici_ad_text = kullanici_ad.text.toString()
            val kullanici_sif_text = kullanici_sif.text.toString()
            val kullanici_sif_tekrar_text = kullanici_sif_tekrar.text.toString()


            if(TextUtils.isEmpty(kullanici_ad_text) || TextUtils.isEmpty(kullanici_sif_text) || TextUtils.isEmpty(kullanici_sif_tekrar_text)){
                Toast.makeText(this,"Boş alan bırakmayınız lütfen",Toast.LENGTH_SHORT).show()
            }
            else{
                val verisakla = db.kullanici_ekleme(kullanici_ad_text,kullanici_sif_text)
                if(kullanici_sif_text.equals(kullanici_sif_tekrar_text)){
                    if(verisakla == true){
                        Toast.makeText(this,"Kayıt Başarılı",Toast.LENGTH_SHORT).show()
                        val intent = Intent(applicationContext,MainActivity::class.java)
                        startActivity(intent)
                    }
                    else{
                        Toast.makeText(this,"Böyle bir kullanıcı adı bulunuyor",Toast.LENGTH_SHORT).show()
                    }
                }
                else{
                    Toast.makeText(this,"Şifreler uyuşmuyor",Toast.LENGTH_SHORT).show()
                }
            }
*/
        }

    }

    fun resizeBitmap(bitmap: Bitmap, newWidth: Int, newHeight: Int): Bitmap {
        return Bitmap.createScaledBitmap(bitmap, newWidth, newHeight, false)
    }


    fun convertBitmapToByteArray(bitmap: Bitmap): ByteArray {
        val outputStream = ByteArrayOutputStream()
        val yeni_bitmap = resizeBitmap(bitmap,300,300)
        yeni_bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)
        return outputStream.toByteArray()
    }
    fun email_kontrol(email: String): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }
}