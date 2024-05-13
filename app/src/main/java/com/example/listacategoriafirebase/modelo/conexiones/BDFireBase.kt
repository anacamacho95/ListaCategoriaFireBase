package com.example.listacategoriafirebase.modelo.conexiones

import android.content.Context
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore

class BDFireBase (private val context: Context) {
    val conexion = Firebase.firestore

}