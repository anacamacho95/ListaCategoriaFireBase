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
        conexion.collection("tareas")
            .add(ta)
            .addOnSuccessListener { documentReference ->
                val idDocumento = documentReference.id
                conexion.collection("tareas").document(idDocumento)
                    .update("idTarea", idDocumento)
                    .addOnSuccessListener {
                        ta.idTarea = documentReference.id
                        Log.d("firebase", "Se ha creado la tarea correctamente")
                    }
                    .addOnFailureListener { e ->
                        // Manejar el error de actualización
                    }
            }
            .addOnFailureListener { e ->
                // Manejar el error de creación
            }
    }

    override fun getTareas(idCategoria: String): MutableList<Tarea> {
        var tareas:MutableList<Tarea> = mutableListOf()

        conexion.collection("tareas")
            .whereEqualTo("idCategoria", idCategoria)
            .get()
            .addOnSuccessListener { querySnapshot ->

                for (document in querySnapshot) {
                    val tarea = document.toObject(Tarea::class.java)
                    tarea.idTarea=document.id
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
        conexion.collection("tareas")
            .whereEqualTo("nombre", nombre)
            .get()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    for (document in task.result) {
                        val tarea = document.toObject(Tarea::class.java)
                        Log.d("firebase", tareaEncontrada?.idTarea+"--"+tarea.nombre)
                    }
                }
                else {
                    Log.d("firebase", "Error al obtener documentos.", task.exception)
                }
            }
        return tareaEncontrada ?: Tarea("","Pendiente")
    }


    override fun updateNombreTarea(ta: Tarea) {
        conexion.collection("tareas").document(ta.idTarea)
            .update("nombre", ta.nombre)
            .addOnSuccessListener {  documentReference ->
                Log.d("firebase","Documento actualizado")

            }
            .addOnFailureListener { e: Exception? ->
                Log.d("firebase","Error al actualizar documento",e)
            }
    }

    override fun deleteTarea(ta: Tarea) {
        conexion.collection("tareas").document(ta.idTarea)
            .delete()
            .addOnSuccessListener {
                Log.d("firebase", "Tarea eliminada correctamente.")
                // Aquí puedes realizar acciones adicionales luego de la eliminación, si es necesario
            }
            .addOnFailureListener { exception ->
                Log.e("firebase", "Error al eliminar tarea: $exception")
                // Maneja el error de eliminación
            }
    }

    override fun addItem(ite: Item) {
        conexion.collection("items")
            .add(ite)
            .addOnSuccessListener { documentReference ->
                val idDocumento = documentReference.id
                conexion.collection("items").document(idDocumento)
                    .update("idItem", idDocumento)
                    .addOnSuccessListener {
                        ite.idItem = documentReference.id
                        Log.d("firebase", "Se ha creado el item correctamente")
                    }
                    .addOnFailureListener { e ->
                        // Manejar el error de actualización
                    }
            }
            .addOnFailureListener { e ->
                // Manejar el error de creación
            }
    }

    override fun getItems(idTarea: String): MutableList<Item> {
        var items: MutableList<Item> = mutableListOf()

        conexion.collection("items")
            .whereEqualTo("idTarea", idTarea)
            .get()
            .addOnSuccessListener { querySnapshot ->

                for (document in querySnapshot) {
                    val item = document.toObject(Item::class.java)
                    item.idItem=document.id
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

    override fun getItem(accion: String): Item {
        var itemEncontrado: Item? = null
        conexion.collection("item")
            .whereEqualTo("accion", accion)
            .get()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    for (document in task.result) {
                        val item = document.toObject(Item::class.java)
                        Log.d("firebase", itemEncontrado?.idItem+"--"+item.accion)
                    }
                }
                else {
                    Log.d("firebase", "Error al obtener documentos.", task.exception)
                }
            }
        return itemEncontrado ?: Item("","Pendiente",false)
    }

    override fun updateItem(ite: Item) {
        conexion.collection("items").document(ite.idItem)
            .update("accion", ite.accion)
            .addOnSuccessListener {  documentReference ->
                Log.d("firebase","Documento actualizado")

            }
            .addOnFailureListener { e: Exception? ->
                Log.d("firebase","Error al actualizar documento",e)
            }
    }

    override fun deleteItem(ite: Item) {
        conexion.collection("items").document(ite.idItem)
            .delete()
            .addOnSuccessListener {
                Log.d("firebase", "Tarea eliminada correctamente.")
                // Aquí puedes realizar acciones adicionales luego de la eliminación, si es necesario
            }
            .addOnFailureListener { exception ->
                Log.e("firebase", "Error al eliminar tarea: $exception")
                // Maneja el error de eliminación
            }
    }

//    override fun getNItems(id: String): Int {
//        var itemCount = 0
//
//        conexion.collection("items")
//            .document(id.toString())  // Aquí asumimos que el ID de la tarea es un String
//            .collection("items")
//            .get()
//            .addOnSuccessListener { querySnapshot ->
//                itemCount = querySnapshot.size()
//                Log.d("firebase", "Número de items: $itemCount")
//            }
//            .addOnFailureListener { exception ->
//                Log.e("firebase", "Error al obtener el número de items: $exception")
//            }
//
//        return itemCount
//    }
}
