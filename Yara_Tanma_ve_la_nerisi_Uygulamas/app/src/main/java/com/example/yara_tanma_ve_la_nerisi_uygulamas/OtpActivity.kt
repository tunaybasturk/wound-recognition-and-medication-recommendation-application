package com.example.yara_tanma_ve_la_nerisi_uygulamas

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import androidx.core.widget.doOnTextChanged
import com.example.yara_tanma_ve_la_nerisi_uygulamas.databinding.ActivityOtpBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import papaya.`in`.sendmail.SendMail
import kotlin.random.nextInt
import kotlin.random.Random

class OtpActivity : AppCompatActivity() {

    lateinit var binding : ActivityOtpBinding
    lateinit var auth: FirebaseAuth
    var email : String = ""
    var sifre : String = ""
    var random : Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOtpBinding.inflate(layoutInflater)
        setContentView(binding.root)
        email = intent.getStringExtra("email").toString()
        sifre = intent.getStringExtra("sifre").toString()

        auth = Firebase.auth
        random()

        binding.showEmail.setText(email.toString())

        binding.tvResend.setOnClickListener {
            random()
        }

        binding.otp1.doOnTextChanged { text, start, before, count ->

            if (!binding.otp1.text.toString().isEmpty()) {
                binding.otp2.requestFocus()
            }
            if (!binding.otp2.text.toString().isEmpty()) {
                binding.otp2.requestFocus()
            }

        }

        binding.otp2.doOnTextChanged { text, start, before, count ->

            if (!binding.otp2.text.toString().isEmpty()) {
                binding.otp3.requestFocus()
            } else {
                binding.otp1.requestFocus()
            }

        }

        binding.otp3.doOnTextChanged { text, start, before, count ->

            if (!binding.otp3.text.toString().isEmpty()) {
                binding.otp4.requestFocus()
            } else {
                binding.otp2.requestFocus()
            }

        }
        binding.otp4.doOnTextChanged { text, start, before, count ->

            if (!binding.otp4.text.toString().isEmpty()) {
                binding.otp5.requestFocus()
            } else {
                binding.otp3.requestFocus()
            }

        }
        binding.otp5.doOnTextChanged { text, start, before, count ->

            if (!binding.otp5.text.toString().isEmpty()) {
                binding.otp6.requestFocus()
            } else {
                binding.otp4.requestFocus()
            }

        }
        binding.otp6.doOnTextChanged { text, start, before, count ->

            if (!binding.otp6.text.toString().isEmpty()) {
                binding.otp5.requestFocus()
            }

            binding.button.setOnClickListener {
                var otp1 = binding.otp1.text.toString()
                var otp2 = binding.otp2.text.toString()
                var otp3 = binding.otp3.text.toString()
                var otp4 = binding.otp4.text.toString()
                var otp5 = binding.otp5.text.toString()
                var otp6 = binding.otp6.text.toString()


                var otp = "$otp1$otp2$otp3$otp4$otp5$otp6"

                if (binding.otp1.text.toString().isEmpty() ||
                    binding.otp2.text.toString().isEmpty() ||
                    binding.otp3.text.toString().isEmpty() ||
                    binding.otp4.text.toString().isEmpty() ||
                    binding.otp5.text.toString().isEmpty() ||
                    binding.otp6.text.toString().isEmpty()
                ) {
                    Toast.makeText(this, "Güvenlik Kodu Giriniz", Toast.LENGTH_SHORT).show()
                } else if (!otp.equals(random.toString())) {
                    Toast.makeText(this, "Yanlış Güvenlik Kodu", Toast.LENGTH_SHORT).show()
                } else {
                    FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, sifre).addOnCompleteListener {
                        if (it.isSuccessful) {
                            var intent = Intent(this, MainActivity::class.java)
                            startActivity(intent)
                        } else {
                            val exception = it.exception
                            if (exception is FirebaseAuthInvalidCredentialsException) {
                                // Geçersiz e-posta formatı hatası
                                // Kullanıcıya uygun bir hata mesajı gösterebilirsiniz
                                Toast.makeText(this, "${exception.toString()}", Toast.LENGTH_SHORT).show()
                            }
                        }
                    }

                }


            }


        }
    }

    fun random(){
        random = Random.nextInt(100000..999999)
        var mail = SendMail("tunabstrk041@gmail.com","gxqoohxqgorqgvgw",email,"Yara Tanıma ve İlaç Önerme Uygulaması Güvenlik Kodu",
        "Senin Güvenlik Kodun -> $random")
        mail.execute()
    }


}