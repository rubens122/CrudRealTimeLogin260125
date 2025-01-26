package com.example.crudrealtimelogin260125.models

import java.io.Serializable

data class Producto(
    val nombre: String="",
    val supermercado: String="",
    val precio: Float=0F
): Serializable