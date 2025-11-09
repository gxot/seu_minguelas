package com.example.seuminguelas.data.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "carrinho",
    foreignKeys = [ForeignKey(
        entity = Usuario::class,
        parentColumns = ["id"],
        childColumns = ["usuarioId"],
        onDelete = ForeignKey.CASCADE
    )]
)
data class Carrinho(
    @PrimaryKey(autoGenerate = true) val id: Long = 0L,
    val usuarioId: Long,
    val dataCriacao: Long = System.currentTimeMillis(),
    val status: String = "ATIVO"
)