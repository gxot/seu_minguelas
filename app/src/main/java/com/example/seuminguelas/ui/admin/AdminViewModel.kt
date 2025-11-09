package com.example.seuminguelas.ui.admin

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.seuminguelas.data.entities.Lanche
import com.example.seuminguelas.data.repositories.LancheRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AdminViewModel @Inject constructor(
    private val lancheRepository: LancheRepository
) : ViewModel() {

    val lanches: LiveData<List<Lanche>> = lancheRepository.observarTodos()

    private val _adicionarResult = MutableLiveData<Boolean?>()
    val adicionarResult: LiveData<Boolean?> get() = _adicionarResult

    private val _deletarResult = MutableLiveData<Boolean?>()
    val deletarResult: LiveData<Boolean?> get() = _deletarResult

    fun adicionarLanche(nome: String, descricao: String, preco: Double) {
        viewModelScope.launch {
            runCatching {
                lancheRepository.inserir(Lanche(nome = nome, descricao = descricao, preco = preco))
            }.onSuccess {
                _adicionarResult.value = true
            }.onFailure {
                _adicionarResult.value = false
            }
        }
    }

    fun deletarLanche(lanche: Lanche) {
        viewModelScope.launch {
            runCatching {
                lancheRepository.deletar(lanche)
            }.onSuccess {
                _deletarResult.value = true
            }.onFailure {
                _deletarResult.value = false
            }
        }
    }

    fun resetAdicionarResult() = run { _adicionarResult.value = null }
    fun resetDeletarResult() = run { _deletarResult.value = null }
}
