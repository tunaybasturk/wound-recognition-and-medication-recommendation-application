package com.example.yara_tanma_ve_la_nerisi_uygulamas

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log

class DBHelper (context: Context):SQLiteOpenHelper(context, "Kullaniciveri",null,1) {

    override fun onCreate(p0: SQLiteDatabase?)
    {
        p0?.execSQL("create table kullanici_tbl(kullanici_id INTEGER PRIMARY KEY AUTOINCREMENT, kullanici_adi TEXT, kullanici_sifre TEXT)")
        p0?.execSQL("create table yara_tbl(yara_id INTEGER PRIMARY KEY AUTOINCREMENT,yara_bolge TEXT, yara_belirti1 TEXT, yara_belirti2 TEXT, yara_belirti3 TEXT, yara_belirti4 TEXT, yara_belirti5 TEXT, yara_belirti6 TEXT, yara_belirti7 TEXT,yara_ismi TEXT, yara_bilgi TEXT, yara_resim BLOB)")
        p0?.execSQL("create table ilac_tbl(yara_id INTEGER PRIMARY KEY AUTOINCREMENT,ilac_ismi TEXT ,ilac_bilgi TEXT, ilac_resim BLOB)")
    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
        p0?.execSQL("drop table if exists kullanici_tbl")
        p0?.execSQL("drop table if exists yara_tbl")
        p0?.execSQL("drop table if exists ilac_tbl")
    }


    fun kullanici_ekleme(kullanici_adi: String, kullanici_sifre : String): Boolean{
        val p0 = this.writableDatabase
        val adKontrolQuery = "select * from kullanici_tbl where kullanici_adi = '$kullanici_adi'"
        val adKontrolCursor = p0.rawQuery(adKontrolQuery, null)
        if (adKontrolCursor.count > 0) {
            adKontrolCursor.close()
            return false
        }

        val cv = ContentValues()
        cv.put("kullanici_adi",kullanici_adi)
        cv.put("kullanici_sifre",kullanici_sifre)
        adKontrolCursor.close()
        val result = p0.insert("kullanici_tbl",null,cv)
        if(result==-1 .toLong()){
            return false
        }
        return true
    }


    fun yara_ekleme(yara_bolge: String, yara_belirtileri: ArrayList<String>, yara_ismi: String, yara_bilgi: String, yara_resim: ByteArray): Boolean {
            val p0 = this.writableDatabase

            val cv = ContentValues()
            cv.put("yara_bolge", yara_bolge)

            // Belirtiler listesinin en fazla 7 öğesini ekliyoruz
            for (i in 0 until 7) {
                if (i < yara_belirtileri.size) {
                    cv.put("yara_belirti${i + 1}", yara_belirtileri[i])
                } else {
                    cv.put("yara_belirti${i + 1}", "")
                }
            }

            cv.put("yara_ismi", yara_ismi)
            cv.put("yara_bilgi", yara_bilgi)
            cv.put("yara_resim", yara_resim)

            val result = p0.insert("yara_tbl", null, cv)

            if(result==-1 .toLong()){
                return false
            }
            return true
        }


    fun ilac_ekleme(ilac_ismi : String, ilac_bilgi : String, ilac_resim : ByteArray  ) : Boolean {

        val p0 = this.writableDatabase

        val cv = ContentValues()

        cv.put("ilac_ismi",ilac_ismi)
        cv.put("ilac_bilgi",ilac_bilgi)
        cv.put("ilac_resim",ilac_resim)

        val result = p0.insert("ilac_tbl",null,cv)

        if(result==-1 .toLong()){
            return false
        }
        return true
    }


    fun bölgeye_göre_belirti_alma(bolge :String) : ArrayList<String>{
        val belirtiler = ArrayList<String>()
        val eklenenBelirtiler = HashSet<String>() // Daha önce eklenen belirtileri saklayan küme
        val db = readableDatabase
        val query = "SELECT * FROM yara_tbl WHERE yara_bolge = ?"
        val cursor = db.rawQuery(query, arrayOf(bolge))

        cursor?.use {
            while (cursor.moveToNext()) {
                val columnNames = cursor.columnNames
                for (columnName in columnNames) {
                    if (columnName.startsWith("yara_belirti")) {
                        val columnIndex = cursor.getColumnIndex(columnName)
                        val belirti = cursor.getString(columnIndex)
                        if (!belirti.isNullOrEmpty() && !eklenenBelirtiler.contains(belirti)) {
                            eklenenBelirtiler.add(belirti)
                            belirtiler.add(belirti)
                        }
                    }
                }
            }
        }

        cursor?.close()
        return belirtiler

        }






    fun kullanici_kontrol(kullanici_adi: String,kullanici_sifre: String): Boolean{
        val p0 = this.writableDatabase
        val query = "select * from kullanici_tbl where kullanici_adi = '$kullanici_adi' and kullanici_sifre= '$kullanici_sifre'"
        val cursor = p0.rawQuery(query,null)
        if(cursor.count<=0){
            cursor.close()
            return false
        }
        cursor.close()
        return true
    }


    fun cilt_hastalik_bul(belirtiler : ArrayList<String>) : String{

        val db = readableDatabase
        val yeni_belirti : ArrayList<String>
        yeni_belirti = ArrayList()

        var sayi : Int = 0


        for (i in belirtiler){
            if (i != ""){
                sayi++
            }
        }


        if(sayi > 7 ){
            return ""
        }
        else{
            for(i in belirtiler){
                if(i != ""){
                    yeni_belirti.add(i)
                }
            }

        }

        if(yeni_belirti.size != 7 ){
            for(i in yeni_belirti.size..7)
            {
                yeni_belirti.add("")
            }
        }


        val query = "SELECT yara_ismi FROM yara_tbl WHERE yara_belirti1 IN (?) " +
                "AND yara_belirti2 IN (?) AND yara_belirti3 IN (?) " +
                "AND yara_belirti4 IN (?) AND yara_belirti5 IN (?) " +
                "AND yara_belirti6 IN (?) AND yara_belirti7 IN (?)"

        val selectionArgs = arrayOf(
            yeni_belirti[0], yeni_belirti[1], yeni_belirti[2],
            yeni_belirti[3], yeni_belirti[4], yeni_belirti[5], yeni_belirti[6]
        )

        val cursor = db.rawQuery(query, selectionArgs)

        var yaraIsmi = ""

        cursor?.use {

            if (cursor.moveToFirst()) {
                val columnIndex = cursor.getColumnIndex("yara_ismi")
                if (columnIndex != -1) {
                    yaraIsmi = cursor.getString(columnIndex)
                }
            }
        }

        cursor?.close()
        return yaraIsmi



    }


    fun yara_id_cekme(yara_ismi:String):Int{

        val db = readableDatabase
        val query = "SELECT * FROM yara_tbl WHERE yara_ismi = ?"
        val cursor = db.rawQuery(query, arrayOf(yara_ismi))


        var yaraID = -1

        cursor?.use {
            if (cursor.moveToFirst()) {
                val columnIndex = cursor.getColumnIndex("yara_id")
                yaraID = cursor.getInt(columnIndex)
            }
        }

        cursor?.close()
        return yaraID

    }


    fun yara_ismi_cekme(yara_id:Int):String{
        val db = readableDatabase
        val query = "SELECT * FROM yara_tbl WHERE yara_id = ?"
        val selectionArgs = arrayOf(yara_id.toString())
        val cursor = db.rawQuery(query, selectionArgs)


        var yara_ismi=""

        cursor?.use {
            if (cursor.moveToFirst()) {
                val columnIndex = cursor.getColumnIndex("yara_ismi")
                yara_ismi = cursor.getString(columnIndex)
            }
        }

        cursor?.close()
        return yara_ismi
    }

    fun yara_bilgi_cekme(yara_id:Int):String{
        val db = readableDatabase
        val query = "SELECT * FROM yara_tbl WHERE yara_id = ?"
        val selectionArgs = arrayOf(yara_id.toString())
        val cursor = db.rawQuery(query, selectionArgs)


        var yara_bilgi=""

        cursor?.use {
            if (cursor.moveToFirst()) {
                val columnIndex = cursor.getColumnIndex("yara_bilgi")
                yara_bilgi = cursor.getString(columnIndex)
            }
        }

        cursor?.close()
        return yara_bilgi
    }

    fun yara_resim_cekme(yara_id:Int):ByteArray?{
        val db = readableDatabase
        val query = "SELECT * FROM yara_tbl WHERE yara_id = ?"
        val selectionArgs = arrayOf(yara_id.toString())
        val cursor = db.rawQuery(query, selectionArgs)


        var yara_resim: ByteArray? = null

        cursor?.use {
            if (cursor.moveToFirst()) {
                val columnIndex = cursor.getColumnIndex("yara_resim")
                yara_resim = cursor.getBlob(columnIndex)
            }
        }

        cursor?.close()
        return yara_resim
    }


    fun ilac_ismi_cekme(yara_id:Int):String{
        val db = readableDatabase
        val query = "SELECT * FROM ilac_tbl WHERE yara_id = ?"
        val selectionArgs = arrayOf(yara_id.toString())
        val cursor = db.rawQuery(query, selectionArgs)


        var ilac_ismi=""

        cursor?.use {
            if (cursor.moveToFirst()) {
                val columnIndex = cursor.getColumnIndex("ilac_ismi")
                ilac_ismi = cursor.getString(columnIndex)
            }
        }

        cursor?.close()
        return ilac_ismi
    }

    fun ilac_bilgi_cekme(yara_id:Int):String{
        val db = readableDatabase
        val query = "SELECT * FROM ilac_tbl WHERE yara_id = ?"
        val selectionArgs = arrayOf(yara_id.toString())
        val cursor = db.rawQuery(query, selectionArgs)


        var ilac_bilgi=""

        cursor?.use {
            if (cursor.moveToFirst()) {
                val columnIndex = cursor.getColumnIndex("ilac_bilgi")
                ilac_bilgi = cursor.getString(columnIndex)
            }
        }

        cursor?.close()
        return ilac_bilgi
    }

    fun ilac_resim_cekme(yara_id:Int):ByteArray?{
        val db = readableDatabase
        val query = "SELECT * FROM ilac_tbl WHERE yara_id = ?"
        val selectionArgs = arrayOf(yara_id.toString())
        val cursor = db.rawQuery(query, selectionArgs)


        var ilac_resim: ByteArray? = null

        cursor?.use {
            if (cursor.moveToFirst()) {
                val columnIndex = cursor.getColumnIndex("ilac_resim")
                ilac_resim = cursor.getBlob(columnIndex)
            }
        }

        cursor?.close()
        return ilac_resim
    }



}