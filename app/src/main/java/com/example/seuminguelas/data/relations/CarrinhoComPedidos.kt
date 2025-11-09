package com.example.seuminguelas.data.relations

import androidx.room.Embedded
import androidx.room.Relation
import com.example.seuminguelas.data.entities.Carrinho
import com.example.seuminguelas.data.entities.Pedido

data class CarrinhoComPedidos(
    @Embedded val carrinho: Carrinho,
    @Relation(
        parentColumn = "id",
        entityColumn = "carrinhoId"
    )
    val pedidos: List<Pedido>
)