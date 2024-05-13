package com.example.listacategoriafirebase.modelo.interfaces

import com.example.listacategoriafirebase.modelo.entidades.Categoria

interface InterfaceDaoCategorias {
    //CRUD
    //crear
    fun addCategoria (ca: Categoria)
    //leer Todas las categorias
    fun getCategorias(): MutableList<Categoria>
    //obtener el objeto Categoria
    fun getCategoria (nombre: String): Categoria
    //actualizar
    fun updateCategoria(ca: Categoria)
    //borrar
    fun deleteCategoria (ca: Categoria)
}