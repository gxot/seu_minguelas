package com.example.seuminguelas.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "usuario")
data class Usuario(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val nome: String,
    val senha: String,
    val isAdmin: Boolean = false
)