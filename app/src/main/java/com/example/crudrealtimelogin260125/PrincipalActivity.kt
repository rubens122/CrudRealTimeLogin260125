package com.example.crudrealtimelogin260125

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.crudrealtimelogin260125.adapters.ProductoAdapter
import com.example.crudrealtimelogin260125.databinding.ActivityPrincipalBinding
import com.example.crudrealtimelogin260125.models.Producto
import com.example.crudrealtimelogin260125.providers.ProductoProvider
import com.example.crudrealtimelogin260125.utils.encodeEmail
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase

class PrincipalActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPrincipalBinding

    var adapter=ProductoAdapter(mutableListOf<Producto>(), {item->borrarItem(item)}, {item->editarItem(item)})

    private lateinit var auth: FirebaseAuth

    private lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding=ActivityPrincipalBinding.inflate(layoutInflater)

        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        auth= Firebase.auth
        database= FirebaseDatabase.getInstance().getReference("agenda")
        setRecycler()
        setListeners()
    }

    private fun setRecycler() {
        val layoutManager= LinearLayoutManager(this)
        binding.recProducto.layoutManager=layoutManager

        binding.recProducto.adapter=adapter
        recuperarDatosAgenda()
    }

    private fun recuperarDatosAgenda() {
        val agendaProvider= ProductoProvider()
        agendaProvider.getDatos { todosLosRegistros->
            binding.imageView.visibility=if(todosLosRegistros.isEmpty()) View.VISIBLE else View.INVISIBLE
            adapter.lista=todosLosRegistros
            adapter.notifyDataSetChanged()
        }
    }


    private fun setListeners() {
        binding.floatingActionButton.setOnClickListener{
            irActivityAdd()
        }
    }

    private fun irActivityAdd() {
        val i = Intent(this, AddActivity::class.java)
        startActivity(i)
    }

    private fun borrarItem(item: Producto){
        database.child(item.nombre.encodeEmail()).removeValue()
            .addOnSuccessListener {
                val position=adapter.lista.indexOf(item)
                if(position!=-1){
                    adapter.lista.removeAt(position)
                    adapter.notifyItemRemoved(position)
                    Toast.makeText(this, "Item Borrado", Toast.LENGTH_SHORT).show()
                }
            }
            .addOnFailureListener {
                Toast.makeText(this, "Error al borrar item", Toast.LENGTH_SHORT).show()
            }
    }
    private fun editarItem(item: Producto){

    }

    override fun onResume() {
        super.onResume()
        recuperarDatosAgenda()
    }
}