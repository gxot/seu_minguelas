package com.example.seuminguelas.data.relations

import androidx.room.Embedded
import androidx.room.Relation
import com.example.seuminguelas.data.entities.Carrinho
import com.example.seuminguelas.data.entities.Usuario

data class UsuarioComCarrinhos(
    @Embedded val usuario: Usuario,
    @Relation(
        parentColumn = "id",
        entityColumn = "usuarioId"
    )
    val carrinhos: List<Carrinho>
)