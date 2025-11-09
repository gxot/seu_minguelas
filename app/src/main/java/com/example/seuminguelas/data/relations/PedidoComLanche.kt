package com.example.seuminguelas.data.relations

import androidx.room.Embedded
import androidx.room.Relation
import com.example.seuminguelas.data.entities.Lanche
import com.example.seuminguelas.data.entities.Pedido

data class PedidoComLanche(
    @Embedded val pedido: Pedido,
    @Relation(
        parentColumn = "lancheId",
        entityColumn = "id"
    )
    val lanche: Lanche
)