package com.example.listacategoriafirebase.modelo.entidades


class Tarea(var categoria: String, var nombre: String ) {

    //una tarea tiene un conjunto de items
    var items: MutableList<Item> = mutableListOf()

    var idTarea: String=""

    override fun toString(): String {
        return "Tarea(nombre='$nombre')"
    }
}