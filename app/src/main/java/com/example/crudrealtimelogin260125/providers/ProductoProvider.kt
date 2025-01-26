package com.example.crudrealtimelogin260125.providers

import com.example.crudrealtimelogin260125.models.Producto
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class ProductoProvider {
    private val database= FirebaseDatabase.getInstance().getReference("agenda")
    fun getDatos(datosAgenda: (MutableList<Producto>)->Unit){
        database.addListenerForSingleValueEvent(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val listado= mutableListOf<Producto>()
                for(item in snapshot.children){
                    val valor=item.getValue(Producto::class.java)
                    if(valor!=null){
                        listado.add(valor)
                    }
                }
                listado.sortBy { it.nombre }
                datosAgenda(listado)
            }

            override fun onCancelled(error: DatabaseError) {
                println("Error al leer realtime: ${error.message}")
            }

        })
    }
}