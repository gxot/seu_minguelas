package com.example.seuminguelas.data.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.example.seuminguelas.data.entities.Pedido
import com.example.seuminguelas.data.relations.PedidoComLanche

@Dao
interface PedidoDao {

    @Query("SELECT * FROM pedido ORDER BY id DESC")
    fun observarTodos(): LiveData<List<Pedido>>

    @Query("SELECT * FROM pedido WHERE id = :id")
    suspend fun buscarPorId(id: Long): Pedido?

    @Query("SELECT * FROM pedido WHERE carrinhoId = :carrinhoId")
    suspend fun buscarPorCarrinho(carrinhoId: Long): List<Pedido>

    @Transaction
    @Query("SELECT * FROM pedido WHERE carrinhoId = :carrinhoId")
    fun observarPedidosComLanche(carrinhoId: Long): LiveData<List<PedidoComLanche>>

    @Insert
    suspend fun inserir(pedido: Pedido): Long

    @Update
    suspend fun atualizar(pedido: Pedido)

    @Delete
    suspend fun deletar(pedido: Pedido)

    @Query("DELETE FROM pedido WHERE carrinhoId = :carrinhoId")
    suspend fun deletarPorCarrinho(carrinhoId: Long)
}