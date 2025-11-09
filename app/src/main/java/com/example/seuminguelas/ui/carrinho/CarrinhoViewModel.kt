package com.example.seuminguelas.ui.carrinho

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.seuminguelas.data.entities.Pedido
import com.example.seuminguelas.data.relations.CarrinhoCompleto
import com.example.seuminguelas.data.repositories.CarrinhoRepository
import com.example.seuminguelas.data.repositories.PedidoRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CarrinhoViewModel @Inject constructor(
    private val carrinhoRepository: CarrinhoRepository,
    private val pedidoRepository: PedidoRepository
) : ViewModel() {

    private val _carrinhoCompleto = MutableLiveData<CarrinhoCompleto?>()
    val carrinhoCompleto: LiveData<CarrinhoCompleto?> get() = _carrinhoCompleto

    fun carregarCarrinho(usuarioId: Long) {
        viewModelScope.launch {
            carrinhoRepository.buscarCarrinhoAtivo(usuarioId)?.let {
                _carrinhoCompleto.value = carrinhoRepository.buscarCarrinhoCompleto(it.id)
            }
        }
    }

    fun removerPedido(pedido: Pedido) {
        viewModelScope.launch {
            pedidoRepository.deletar(pedido)
            _carrinhoCompleto.value?.let {
                _carrinhoCompleto.value = carrinhoRepository.buscarCarrinhoCompleto(it.carrinho.id)
            }
        }
    }

    fun finalizarCarrinho() {
        viewModelScope.launch {
            _carrinhoCompleto.value?.let {
                carrinhoRepository.atualizar(it.carrinho.copy(status = "FINALIZADO"))
            }
        }
    }

    fun calcularTotal(): Double =
        _carrinhoCompleto.value?.pedidosComLanches?.sumOf {
            it.pedido.quantidade * it.pedido.precoUnitario
        } ?: 0.0
}
