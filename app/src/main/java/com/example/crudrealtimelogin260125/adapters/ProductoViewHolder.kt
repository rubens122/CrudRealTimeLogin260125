package com.example.crudrealtimelogin260125.adapters

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.crudrealtimelogin260125.databinding.ProductoLayoutBinding
import com.example.crudrealtimelogin260125.models.Producto

class ProductoViewHolder(v: View): RecyclerView.ViewHolder(v) {
    private val binding= ProductoLayoutBinding.bind(v)
    fun render(item: Producto, onBorrar: (Producto)->Unit, onEdit: (Producto)->Unit){
        binding.tvNombre.text=item.nombre
        binding.tvSupermercado.text=item.supermercado
        binding.tvPrecio.text=item.precio.toString()
        binding.btnBorrar.setOnClickListener {
            onBorrar(item)
        }
        binding.btnEditar.setOnClickListener {
            onEdit(item)
        }
    }

}