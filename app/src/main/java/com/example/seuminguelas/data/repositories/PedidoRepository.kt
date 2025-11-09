package com.example.seuminguelas.data.repositories

import androidx.lifecycle.LiveData
import com.example.seuminguelas.data.dao.PedidoDao
import com.example.seuminguelas.data.entities.Pedido
import com.example.seuminguelas.data.relations.PedidoComLanche
import javax.inject.Inject

class PedidoRepository @Inject constructor(private val pedidoDao: PedidoDao) {

    fun observarTodos(): LiveData<List<Pedido>> = pedidoDao.observarTodos()

    fun observarPedidosComLanche(carrinhoId: Long): LiveData<List<PedidoComLanche>> {
        return pedidoDao.observarPedidosComLanche(carrinhoId)
    }

    suspend fun buscarPorId(id: Long): Pedido? {
        return pedidoDao.buscarPorId(id)
    }

    suspend fun buscarPorCarrinho(carrinhoId: Long): List<Pedido> {
        return pedidoDao.buscarPorCarrinho(carrinhoId)
    }

    suspend fun inserir(pedido: Pedido): Long {
        return pedidoDao.inserir(pedido)
    }

    suspend fun atualizar(pedido: Pedido) {
        pedidoDao.atualizar(pedido)
    }

    suspend fun deletar(pedido: Pedido) {
        pedidoDao.deletar(pedido)
    }

    suspend fun deletarPorCarrinho(carrinhoId: Long) {
        pedidoDao.deletarPorCarrinho(carrinhoId)
    }
}