package com.example.seuminguelas.ui.auth

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun LoginScreen(
    onLoginSuccess: (Long, String, Boolean) -> Unit,
    onNavigateToCadastro: () -> Unit,
    viewModel: AuthViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    var nome by remember { mutableStateOf("") }
    var senha by remember { mutableStateOf("") }
    var tentouLogar by remember { mutableStateOf(false) }

    val loginResult by viewModel.loginResult.observeAsState()

    LaunchedEffect(loginResult, tentouLogar) {
        if (tentouLogar) {
            loginResult?.let { usuario ->
                onLoginSuccess(usuario.id, usuario.nome, usuario.isAdmin)
                viewModel.resetLoginResult()
                tentouLogar = false
            } ?: run {
                Toast.makeText(context, "Usuário ou senha incorretos", Toast.LENGTH_SHORT).show()
                tentouLogar = false
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Seu Minguelas",
            fontSize = 32.sp,
            style = MaterialTheme.typography.headlineLarge,
            modifier = Modifier.padding(bottom = 48.dp)
        )

        OutlinedTextField(
            value = nome,
            onValueChange = { nome = it },
            label = { Text("Nome de usuário") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            singleLine = true
        )

        OutlinedTextField(
            value = senha,
            onValueChange = { senha = it },
            label = { Text("Senha") },
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 24.dp),
            singleLine = true
        )

        Button(
            onClick = {
                if (nome.isBlank() || senha.isBlank()) {
                    Toast.makeText(context, "Preencha todos os campos", Toast.LENGTH_SHORT).show()
                } else {
                    tentouLogar = true
                    viewModel.login(nome, senha)
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
        ) {
            Text("Entrar", fontSize = 16.sp)
        }

        TextButton(
            onClick = onNavigateToCadastro,
            modifier = Modifier.padding(top = 16.dp)
        ) {
            Text("Não tem conta? Cadastre-se")
        }
    }
}
