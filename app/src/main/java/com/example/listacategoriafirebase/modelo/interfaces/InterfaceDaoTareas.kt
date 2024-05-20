package com.example.listacategoriafirebase.modelo.interfaces

import com.example.listacategoriafirebase.modelo.entidades.Categoria
import com.example.listacategoriafirebase.modelo.entidades.Item
import com.example.listacategoriafirebase.modelo.entidades.Tarea

interface InterfaceDaoTareas {
    //CRUD TAREAS
    //crear
    fun addTarea (ta: Tarea)
    //leer Todas las tareas
    fun getTareas(idCategoria: String): MutableList<Tarea>
    //obtener el objeto Tarea
    fun getTarea (nombre: String): Tarea
    //actualizar
    fun updateNombreTarea(ta: Tarea)
    //borrar
    fun deleteTarea (ta: Tarea)

    //CRUD ITEMS
    //crear
    fun addItem (ite: Item)
    //leer Todos los items
    fun getItems(idTarea: String): MutableList<Item>
    //obtener el objeto Item
    fun getItem (nombre: String): Item
    //actualizar item
    fun updateItem(ite: Item)
    //borrar
    fun deleteItem (ite: Item)

    //NºItems
    //fun getNItems(id: String): Int
}