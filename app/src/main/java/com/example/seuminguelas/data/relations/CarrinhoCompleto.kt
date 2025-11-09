package com.example.seuminguelas.data.relations

import androidx.room.Embedded
import androidx.room.Relation
import com.example.seuminguelas.data.entities.Carrinho
import com.example.seuminguelas.data.entities.Pedido

data class CarrinhoCompleto(
    @Embedded val carrinho: Carrinho,
    @Relation(
        parentColumn = "id",
        entityColumn = "carrinhoId",
        entity = Pedido::class
    )
    val pedidosComLanches: List<PedidoComLanche>
)