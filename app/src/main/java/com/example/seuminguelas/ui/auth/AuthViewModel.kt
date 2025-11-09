package com.example.seuminguelas.ui.auth

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.seuminguelas.data.entities.Usuario
import com.example.seuminguelas.data.repositories.UsuarioRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val usuarioRepository: UsuarioRepository
) : ViewModel() {

    private val _loginResult = MutableLiveData<Usuario?>()
    val loginResult: LiveData<Usuario?> get() = _loginResult

    private val _cadastroResult = MutableLiveData<Boolean>()
    val cadastroResult: LiveData<Boolean> get() = _cadastroResult

    fun login(nome: String, senha: String) {
        viewModelScope.launch {
            _loginResult.value = usuarioRepository.buscarPorCredenciais(nome, senha)
        }
    }

    fun cadastrar(nome: String, senha: String) {
        viewModelScope.launch {
            runCatching {
                usuarioRepository.inserir(Usuario(nome = nome, senha = senha))
            }.onSuccess {
                _cadastroResult.value = true
            }.onFailure {
                _cadastroResult.value = false
            }
        }
    }

    fun resetLoginResult() {
        _loginResult.value = null
    }
}
