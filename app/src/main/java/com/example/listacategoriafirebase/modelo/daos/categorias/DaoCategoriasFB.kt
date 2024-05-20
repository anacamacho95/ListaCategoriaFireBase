package com.example.listacategoriafirebase.modelo.daos.categorias

import android.content.ContentValues.TAG
import android.util.Log
import com.example.listacategoriafirebase.modelo.conexiones.BDFireBase
import com.example.listacategoriafirebase.modelo.entidades.Categoria
import com.example.listacategoriafirebase.modelo.interfaces.InterfaceDaoCategorias
import com.example.listacategoriafirebase.modelo.interfaces.InterfaceDaoConexion
import com.google.firebase.Firebase
import com.google.firebase.firestore.FirebaseFirestore

class DaoCategoriasFB: InterfaceDaoCategorias, InterfaceDaoConexion {

    lateinit var conexion: FirebaseFirestore

    override fun createConexion(con: BDFireBase) {
        conexion = (con as BDFireBase).conexion
    }
    override fun addCategoria(ca: Categoria) {
//        conexion.collection("categoria")
//            .add(ca)
//            .addOnSuccessListener { documentReference ->
//                Log.d("firebase", "DocumentSnapshot written with ID: ${documentReference.id}")
//            }
//            .addOnFailureListener { e ->
//                Log.d("firebase", "Error adding document", e)
//            }
        conexion.collection("categorias")
            .add(ca)
            .addOnSuccessListener { documentReference ->
                val idDocumento = documentReference.id

                conexion.collection("categorias").document(idDocumento)
                    .update("idCategoria", idDocumento)
                    .addOnSuccessListener {
                        ca.idCategoria = documentReference.id
                        Log.d("firebase", "Se ha creado el cine correctamente")
                    }
                    .addOnFailureListener { e ->
                        // Manejar el error de actualización
                    }
            }
            .addOnFailureListener { e ->
                // Manejar el error de creación
            }
    }

    override fun getCategorias(): MutableList<Categoria> {
        var  categorias: MutableList<Categoria> = mutableListOf()
        conexion.collection("categoria")
            .get()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    for (document in task.result) {
                        val categoria = document.toObject(Categoria::class.java)
                        categoria.idCategoria=document.id
                        categorias.add(categoria)
                    }
                    //aqui es donde se muestran los resultados. Los del main no tienen datos aún. Esto es un metodo asincrono
                    categorias.forEach {
                        Log.d("firebase",it.idCategoria+"--"+it.nombre)
                    }
                }
                else {
                    Log.d("firebase", "Error al obtener documentos.", task.exception)
                }
            }
        return categorias
    }

    override fun getCategoria(nombre: String): Categoria {
        var categoriaEncontrada: Categoria? = null
        conexion.collection("categorias")
            .whereEqualTo("nombre", nombre)
            .get()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    for (document in task.result) {
                        val categoria = document.toObject(Categoria::class.java)
                        Log.d("firebase", categoriaEncontrada?.idCategoria+"--"+categoria.nombre)
                    }
                }
                else {
                    Log.d("firebase", "Error al obtener documentos.", task.exception)
                }
            }
        return categoriaEncontrada ?: Categoria("Pendiente")
    }

    override fun updateCategoria(ca: Categoria) {
        conexion.collection("categoria").document(ca.idCategoria)
            .update("nombre", ca.nombre)
            .addOnSuccessListener {  documentReference ->
                Log.d("firebase","Documento actualizado")

            }
            .addOnFailureListener { e: Exception? ->
                Log.d("firebase","Error al actualizar documento",e)
            }
    }

    override fun deleteCategoria(ca: Categoria) {
        conexion.collection("categoria").document(ca.idCategoria)
            .delete()
            .addOnSuccessListener {
                Log.d("firebase", "Categoria eliminada correctamente.")
                // Aquí puedes realizar acciones adicionales luego de la eliminación, si es necesario
            }
            .addOnFailureListener { exception ->
                Log.e("firebase", "Error al eliminar categoria: $exception")
                // Maneja el error de eliminación
            }
    }


}