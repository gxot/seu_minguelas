package com.example.seuminguelas.ui.cardapio

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.seuminguelas.data.entities.Carrinho
import com.example.seuminguelas.data.entities.Lanche
import com.example.seuminguelas.data.entities.Pedido
import com.example.seuminguelas.data.repositories.CarrinhoRepository
import com.example.seuminguelas.data.repositories.LancheRepository
import com.example.seuminguelas.data.repositories.PedidoRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CardapioViewModel @Inject constructor(
    private val lancheRepository: LancheRepository,
    private val carrinhoRepository: CarrinhoRepository,
    private val pedidoRepository: PedidoRepository
) : ViewModel() {

    val lanches: LiveData<List<Lanche>> = lancheRepository.observarTodos()

    fun adicionarAoCarrinho(usuarioId: Long, lanche: Lanche, quantidade: Int) {
        viewModelScope.launch {
            var carrinho = carrinhoRepository.buscarCarrinhoAtivo(usuarioId)
            if (carrinho == null) {
                val carrinhoId = carrinhoRepository.inserir(Carrinho(usuarioId = usuarioId))
                carrinho = Carrinho(id = carrinhoId, usuarioId = usuarioId)
            }

            val pedido = Pedido(
                carrinhoId = carrinho.id,
                lancheId = lanche.id,
                quantidade = quantidade,
                precoUnitario = lanche.preco
            )
            pedidoRepository.inserir(pedido)
        }
    }
}
