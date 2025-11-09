package com.example.seuminguelas.data.repositories

import androidx.lifecycle.LiveData
import com.example.seuminguelas.data.dao.LancheDao
import com.example.seuminguelas.data.entities.Lanche
import javax.inject.Inject

class LancheRepository @Inject constructor(private val lancheDao: LancheDao) {

    fun observarTodos(): LiveData<List<Lanche>> = lancheDao.observarTodos()

    fun buscarPorNome(termo: String): LiveData<List<Lanche>> {
        return lancheDao.buscarPorNome(termo)
    }

    suspend fun buscarPorId(id: Long): Lanche? {
        return lancheDao.buscarPorId(id)
    }

    suspend fun inserir(lanche: Lanche): Long {
        return lancheDao.inserir(lanche)
    }

    suspend fun atualizar(lanche: Lanche) {
        lancheDao.atualizar(lanche)
    }

    suspend fun deletar(lanche: Lanche) {
        lancheDao.deletar(lanche)
    }
}