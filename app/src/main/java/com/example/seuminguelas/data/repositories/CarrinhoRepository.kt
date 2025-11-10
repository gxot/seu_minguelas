package com.example.seuminguelas.data.repositories

import androidx.lifecycle.LiveData
import com.example.seuminguelas.data.dao.CarrinhoDao
import com.example.seuminguelas.data.entities.Carrinho
import com.example.seuminguelas.data.relations.CarrinhoCompleto
import javax.inject.Inject

class CarrinhoRepository @Inject constructor (private val carrinhoDao: CarrinhoDao) {

    fun observarTodos(): LiveData<List<Carrinho>> = carrinhoDao.observarTodos()

    suspend fun buscarCarrinhoAtivo(usuarioId: Long): Carrinho? {
        return carrinhoDao.buscarCarrinhoAtivo(usuarioId)
    }

    suspend fun buscarCarrinhoCompleto(id: Long): CarrinhoCompleto? {
        return carrinhoDao.buscarCarrinhoCompleto(id)
    }

    suspend fun inserir(carrinho: Carrinho): Long {
        return carrinhoDao.inserir(carrinho)
    }

    suspend fun atualizar(carrinho: Carrinho) {
        carrinhoDao.atualizar(carrinho)
    }

    suspend fun deletar(carrinho: Carrinho) {
        carrinhoDao.deletar(carrinho)
    }
}