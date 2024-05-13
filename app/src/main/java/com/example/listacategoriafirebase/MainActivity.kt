package com.example.listacategoriafirebase

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.listacategoriafirebase.modelo.conexiones.BDFireBase
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore

class MainActivity : AppCompatActivity() {

    lateinit var conexion: BDFireBase
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        conexion=BDFireBase(this)


    }
}