package com.example.crudrealtimelogin260125

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.crudrealtimelogin260125.databinding.ActivityAddBinding
import com.example.crudrealtimelogin260125.models.Producto
import com.example.crudrealtimelogin260125.utils.encodeEmail
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import java.util.UUID

class AddActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddBinding

    private var nombre=""
    private var supermercado=""
    private var precio=0F


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding=ActivityAddBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        setListeners()
    }
    private fun setListeners() {
        binding.btnCancelar.setOnClickListener {
            finish()
        }
        binding.btnAdd.setOnClickListener {
            addItem()
        }
    }
    private fun addItem() {
        if (!datosOk()) return

        val database: DatabaseReference = FirebaseDatabase.getInstance().getReference("agenda") // Nodo actualizado a "agenda"
        val nodo = nombre.encodeEmail()


        database.orderByChild("nombre").equalTo(nombre).addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {

                    Toast.makeText(
                        this@AddActivity,
                        "Error: Ya existe un artículo con el nombre '$nombre'",
                        Toast.LENGTH_SHORT
                    ).show()
                } else {

                    val id = UUID.randomUUID().toString().replace("-", "") // Remover guiones del UUID
                    val item = Producto(nombre, supermercado, precio)

                    database.child(id).setValue(item)
                        .addOnSuccessListener {
                            Toast.makeText(this@AddActivity, "Artículo agregado con éxito", Toast.LENGTH_SHORT).show()
                            finish()
                        }
                        .addOnFailureListener {
                            Toast.makeText(this@AddActivity, "Error al guardar el artículo", Toast.LENGTH_SHORT).show()
                        }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@AddActivity, "Error al acceder a Firebase", Toast.LENGTH_SHORT).show()
            }
        })
    }
    private fun datosOk(): Boolean {
        nombre=binding.etNombre.text.toString().trim()
        if(nombre.length<3){
            binding.etNombre.error="Error, el nombre debe tener 3 caracteres"
            return false
        }
        supermercado=binding.etSupermercado.text.toString().trim()
        if(supermercado.length<3){
            binding.etSupermercado.error="Error, el campo debe tener al menos 3 caracteres"
            return false
        }
        precio=binding.etPrecio.text.toString().toFloat()
        if(precio<0 || precio>100){
            binding.etPrecio.error="Error, la cantidad debe estar entre 0 y 100"
            return false
        }

        return true
    }
}