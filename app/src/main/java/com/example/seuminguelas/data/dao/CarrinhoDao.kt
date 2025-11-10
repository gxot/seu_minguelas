package com.example.seuminguelas.data.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.example.seuminguelas.data.entities.Carrinho
import com.example.seuminguelas.data.relations.CarrinhoCompleto

@Dao
interface CarrinhoDao {

    @Query("SELECT * FROM carrinho ORDER BY id DESC")
    fun observarTodos(): LiveData<List<Carrinho>>

    @Query("SELECT * FROM carrinho WHERE usuarioId = :usuarioId AND status = 'ATIVO'")
    suspend fun buscarCarrinhoAtivo(usuarioId: Long): Carrinho?

    @Transaction
    @Query("SELECT * FROM carrinho WHERE id = :id")
    suspend fun buscarCarrinhoCompleto(id: Long): CarrinhoCompleto?

    @Insert
    suspend fun inserir(carrinho: Carrinho): Long

    @Update
    suspend fun atualizar(carrinho: Carrinho)

    @Delete
    suspend fun deletar(carrinho: Carrinho)
}