package com.example.listacategoriafirebase.modelo.entidades


class Item(var tarea: String, var accion: String, var activo: Boolean) {

    var idItem: String=""

    override fun toString(): String {
        return "Item(accion='$accion', activo=$activo)"
    }
}