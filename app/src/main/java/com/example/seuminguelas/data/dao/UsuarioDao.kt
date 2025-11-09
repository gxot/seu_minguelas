package com.example.seuminguelas.data.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.example.seuminguelas.data.entities.Usuario
import com.example.seuminguelas.data.relations.UsuarioComCarrinhos

@Dao
interface UsuarioDao {

    @Query("SELECT * FROM usuario ORDER BY id DESC")
    fun observarTodos(): LiveData<List<Usuario>>

    @Query("SELECT * FROM usuario WHERE id = :id")
    suspend fun buscarPorId(id: Long): Usuario?

    @Query("SELECT * FROM usuario WHERE nome = :nome AND senha = :senha")
    suspend fun buscarPorCredenciais(nome: String, senha: String): Usuario?

    @Transaction
    @Query("SELECT * FROM usuario WHERE id = :id")
    suspend fun buscarUsuarioComCarrinhos(id: Long): UsuarioComCarrinhos?

    @Insert
    suspend fun inserir(usuario: Usuario): Long

    @Update
    suspend fun atualizar(usuario: Usuario)

    @Delete
    suspend fun deletar(usuario: Usuario)
}