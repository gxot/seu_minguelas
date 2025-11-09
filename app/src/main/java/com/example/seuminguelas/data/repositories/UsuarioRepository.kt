package com.example.seuminguelas.data.repositories

import androidx.lifecycle.LiveData
import com.example.seuminguelas.data.dao.UsuarioDao
import com.example.seuminguelas.data.entities.Usuario
import com.example.seuminguelas.data.relations.UsuarioComCarrinhos
import javax.inject.Inject

class UsuarioRepository @Inject constructor(private val usuarioDao: UsuarioDao) {

    fun observarTodos(): LiveData<List<Usuario>> = usuarioDao.observarTodos()

    suspend fun buscarPorId(id: Long): Usuario? {
        return usuarioDao.buscarPorId(id)
    }

    suspend fun buscarPorCredenciais(nome: String, senha: String): Usuario? {
        return usuarioDao.buscarPorCredenciais(nome, senha)
    }

    suspend fun buscarUsuarioComCarrinhos(id: Long): UsuarioComCarrinhos? {
        return usuarioDao.buscarUsuarioComCarrinhos(id)
    }

    suspend fun inserir(usuario: Usuario): Long {
        return usuarioDao.inserir(usuario)
    }

    suspend fun atualizar(usuario: Usuario) {
        usuarioDao.atualizar(usuario)
    }

    suspend fun deletar(usuario: Usuario) {
        usuarioDao.deletar(usuario)
    }
}