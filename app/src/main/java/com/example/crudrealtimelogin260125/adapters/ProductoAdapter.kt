package com.example.crudrealtimelogin260125.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.crudrealtimelogin260125.R
import com.example.crudrealtimelogin260125.models.Producto

class ProductoAdapter(
    var lista: MutableList<Producto>,
    private val onBorrar: (Producto)->Unit,
    private val onEdit: (Producto)->Unit
): RecyclerView.Adapter<ProductoViewHolder> (){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductoViewHolder {
        val v= LayoutInflater.from(parent.context).inflate(R.layout.producto_layout, parent, false)
        return ProductoViewHolder(v)
    }

    override fun getItemCount()=lista.size

    override fun onBindViewHolder(holder: ProductoViewHolder, position: Int) {
        holder.render(lista[position], onBorrar, onEdit)
    }
}