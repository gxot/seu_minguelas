package com.example.seuminguelas.data.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.seuminguelas.data.entities.Lanche

@Dao
interface LancheDao {

    @Query("SELECT * FROM lanche ORDER BY nome ASC")
    fun observarTodos(): LiveData<List<Lanche>>

    @Query("SELECT * FROM lanche WHERE id = :id")
    suspend fun buscarPorId(id: Long): Lanche?

    @Query("SELECT * FROM lanche WHERE nome LIKE '%' || :termo || '%'")
    fun buscarPorNome(termo: String): LiveData<List<Lanche>>

    @Insert
    suspend fun inserir(lanche: Lanche): Long

    @Update
    suspend fun atualizar(lanche: Lanche)

    @Delete
    suspend fun deletar(lanche: Lanche)
}