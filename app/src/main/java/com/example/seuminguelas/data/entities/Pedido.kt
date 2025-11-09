package com.example.seuminguelas.data.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "pedido",
    foreignKeys = [
        ForeignKey(
            entity = Carrinho::class,
            parentColumns = ["id"],
            childColumns = ["carrinhoId"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = Lanche::class,
            parentColumns = ["id"],
            childColumns = ["lancheId"],
            onDelete = ForeignKey.RESTRICT
        )
    ]
)
data class Pedido(
    @PrimaryKey(autoGenerate = true) val id: Long = 0L,
    val carrinhoId: Long,
    val lancheId: Long,
    val quantidade: Int,
    val precoUnitario: Double
)