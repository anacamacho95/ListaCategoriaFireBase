package com.example.listacategoriafirebase.modelo.interfaces

import com.example.listacategoriafirebase.modelo.conexiones.BDFireBase

interface InterfaceDaoConexion {

    fun createConexion(con:BDFireBase)
}