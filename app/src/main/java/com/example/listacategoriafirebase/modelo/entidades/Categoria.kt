package com.example.listacategoriafirebase.modelo.entidades


class Categoria (var nombre: String) {
    //Una categoria tiene muchas tareas
    var tareas: MutableList<Tarea> = mutableListOf()

    var idCategoria: String=""

    override fun toString(): String {
        return "Categoria(nombre='$nombre')"
    }
}