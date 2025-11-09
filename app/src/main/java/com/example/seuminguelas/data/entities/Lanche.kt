package com.example.seuminguelas.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "lanche")
data class Lanche(
    @PrimaryKey(autoGenerate = true) val id: Long = 0L,
    val nome: String,
    val preco: Double,
    val descricao: String
)