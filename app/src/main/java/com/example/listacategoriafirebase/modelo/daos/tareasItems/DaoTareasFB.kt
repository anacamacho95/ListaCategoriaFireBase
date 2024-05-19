package com.example.listacategoriafirebase.modelo.daos.tareasItems

import android.util.Log
import com.example.listacategoriafirebase.modelo.conexiones.BDFireBase
import com.example.listacategoriafirebase.modelo.entidades.Item
import com.example.listacategoriafirebase.modelo.entidades.Tarea
import com.example.listacategoriafirebase.modelo.interfaces.InterfaceDaoConexion
import com.example.listacategoriafirebase.modelo.interfaces.InterfaceDaoTareas
import com.google.firebase.firestore.FirebaseFirestore

class DaoTareasFB: InterfaceDaoTareas, InterfaceDaoConexion{

    lateinit var conexion: FirebaseFirestore

    override fun createConexion(con: BDFireBase) {
        conexion = (con as BDFireBase).conexion
    }

    override fun addTarea(ta: Tarea) {
        conexion.collection("tarea")
            .add(ta)
            .addOnSuccessListener { documentReference ->
                Log.d("firebase", "DocumentSnapshot written with ID: ${documentReference.id}")
            }
            .addOnFailureListener { e ->
                Log.d("firebase", "Error adding document", e)
            }
    }

    override fun getTareas(id: String): MutableList<Tarea> {
        var tareas:MutableList<Tarea> = mutableListOf()

        conexion.collection("tarea")
            .whereEqualTo("id", id)
            .get()
            .addOnSuccessListener { querySnapshot ->

                for (document in querySnapshot) {
                    val tarea = document.toObject(Tarea::class.java)
                    tareas.add(tarea)
                }
                tareas.forEach {
                    Log.d("firebase",it.idTarea+"--"+it.nombre)
                }
            }
            .addOnFailureListener { exception ->
                // Maneja el error
            }
        return tareas
    }

    override fun getTarea(nombre: String): Tarea {
        var tareaEncontrada: Tarea? = null
        conexion.collection("tarea")
            .whereEqualTo("nombre", nombre)
            .get()
            .addOnSuccessListener { documents ->
                if (!documents.isEmpty) {
                    val document = documents.documents[0]
                    tareaEncontrada = document.toObject(Tarea::class.java)
                    tareaEncontrada?.idTarea = document.id
                    Log.d("Firebase", "Tarea encontrada: ${tareaEncontrada?.nombre}")
                } else {
                    Log.d("Firebase", "Tarea no encontrada")
                    tareaEncontrada = Tarea("", "No encontrada")
                }
            }
            .addOnFailureListener { exception ->
                Log.e("Firebase", "Error al obtener Tarea: $exception")
                tareaEncontrada = Tarea("","Error")
            }
        return tareaEncontrada ?: Tarea("","Pendiente")
    }

    override fun updateNombreTarea(ta: Tarea) {
        conexion.collection("tarea").document(ta.idTarea)
            .update("nombre", ta.nombre)
            .addOnSuccessListener {  documentReference ->
                Log.d("firebase","Documento actualizado")

            }
            .addOnFailureListener { e: Exception? ->
                Log.d("firebase","Error al actualizar documento",e)
            }
    }

    override fun deleteTarea(ta: Tarea) {
        conexion.collection("tarea").document(ta.idTarea)
            .delete()
            .addOnSuccessListener {
                Log.d("Firebase", "Tarea eliminada correctamente.")
                // Aquí puedes realizar acciones adicionales luego de la eliminación, si es necesario
            }
            .addOnFailureListener { exception ->
                Log.e("Firebase", "Error al eliminar tarea: $exception")
                // Maneja el error de eliminación
            }
    }

    override fun addItem(ite: Item) {
        conexion.collection("item")
            .add(ite)
            .addOnSuccessListener { documentReference ->
                Log.d("firebase", "DocumentSnapshot written with ID: ${documentReference.id}")
            }
            .addOnFailureListener { e ->
                Log.d("firebase", "Error adding document", e)
            }
    }

    override fun getItems(id: String): MutableList<Item> {
        var items: MutableList<Item> = mutableListOf()

        conexion.collection("item")
            .whereEqualTo("id", id)
            .get()
            .addOnSuccessListener { querySnapshot ->

                for (document in querySnapshot) {
                    val item = document.toObject(Item::class.java)
                    items.add(item)
                }
                items.forEach {
                    Log.d("firebase",it.idItem+"--"+it.accion)
                }
            }
            .addOnFailureListener { exception ->
                // Maneja el error
            }
        return items
    }

    override fun getItem(nombre: String): Item {
        var itenEncontrado: Item? = null
        conexion.collection("item")
            .whereEqualTo("accion", nombre)
            .get()
            .addOnSuccessListener { documents ->
                if (!documents.isEmpty) {
                    val document = documents.documents[0]
                    itenEncontrado = document.toObject(Item::class.java)
                    itenEncontrado?.idItem = document.id
                    Log.d("Firebase", "Item encontrada: ${itenEncontrado?.accion}")
                } else {
                    Log.d("Firebase", "Item no encontrada")
                    itenEncontrado = Item("","No encontrada",false)
                }
            }
            .addOnFailureListener { exception ->
                Log.e("Firebase", "Error al obtener Tarea: $exception")
                itenEncontrado = Item("","Error",false)
            }
        return itenEncontrado ?: Item("","Pendiente",false)
    }

    override fun updateItem(ite: Item) {
        conexion.collection("item").document(ite.idItem)
            .update("nombre", ite.accion)
            .addOnSuccessListener {  documentReference ->
                Log.d("firebase","Documento actualizado")

            }
            .addOnFailureListener { e: Exception? ->
                Log.d("firebase","Error al actualizar documento",e)
            }
    }

    override fun deleteItem(ite: Item) {
        conexion.collection("item").document(ite.idItem)
            .delete()
            .addOnSuccessListener {
                Log.d("Firebase", "Tarea eliminada correctamente.")
                // Aquí puedes realizar acciones adicionales luego de la eliminación, si es necesario
            }
            .addOnFailureListener { exception ->
                Log.e("Firebase", "Error al eliminar tarea: $exception")
                // Maneja el error de eliminación
            }
    }

    override fun getNItems(id: String): Int {
        var itemCount = 0

        conexion.collection("item")
            .document(id.toString())  // Aquí asumimos que el ID de la tarea es un String
            .collection("items")
            .get()
            .addOnSuccessListener { querySnapshot ->
                itemCount = querySnapshot.size()
                Log.d("firebase", "Número de items: $itemCount")
            }
            .addOnFailureListener { exception ->
                Log.e("firebase", "Error al obtener el número de items: $exception")
            }

        return itemCount
    }
}
