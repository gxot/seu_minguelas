package com.example.seuminguelas.ui.admin

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.ui.text.input.ImeAction
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.seuminguelas.data.entities.Lanche
import com.example.seuminguelas.utils.toReais

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdminScreen(
    usuarioNome: String,
    onSair: () -> Unit,
    viewModel: AdminViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val lanches by viewModel.lanches.observeAsState(emptyList())
    var showDialog by remember { mutableStateOf(false) }

    val adicionarResult by viewModel.adicionarResult.observeAsState()
    val deletarResult by viewModel.deletarResult.observeAsState()

    LaunchedEffect(adicionarResult) {
        adicionarResult?.let { sucesso ->
            if (sucesso) {
                Toast.makeText(context, "Lanche adicionado com sucesso!", Toast.LENGTH_SHORT).show()
                viewModel.resetAdicionarResult()
            } else {
                Toast.makeText(context, "Erro ao adicionar lanche", Toast.LENGTH_SHORT).show()
                viewModel.resetAdicionarResult()
            }
        }
    }

    LaunchedEffect(deletarResult) {
        deletarResult?.let { sucesso ->
            if (sucesso) {
                Toast.makeText(context, "Lanche removido com sucesso!", Toast.LENGTH_SHORT).show()
                viewModel.resetDeletarResult()
            } else {
                Toast.makeText(context, "Erro ao remover lanche", Toast.LENGTH_SHORT).show()
                viewModel.resetDeletarResult()
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Admin - $usuarioNome") },
                actions = {
                    IconButton(onClick = onSair) {
                        Icon(Icons.Default.ExitToApp, contentDescription = "Sair")
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { showDialog = true }) {
                Icon(Icons.Default.Add, contentDescription = "Adicionar Lanche")
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            Text(
                text = "Gerenciar Cardápio",
                fontSize = 24.sp,
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier.padding(16.dp)
            )

            if (lanches.isEmpty()) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Nenhum lanche cadastrado",
                        fontSize = 18.sp,
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
            } else {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 8.dp)
                ) {
                    items(lanches) { lanche ->
                        AdminLancheItem(
                            lanche = lanche,
                            onDeletarClick = {
                                viewModel.deletarLanche(lanche)
                            }
                        )
                    }
                }
            }
        }
    }

    if (showDialog) {
        AdicionarLancheDialog(
            onDismiss = { showDialog = false },
            onConfirm = { nome, descricao, preco ->
                viewModel.adicionarLanche(nome, descricao, preco)
                showDialog = false
            }
        )
    }
}

@Composable
fun AdminLancheItem(
    lanche: Lanche,
    onDeletarClick: () -> Unit
) {
    var showDeleteDialog by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = lanche.nome,
                    fontSize = 18.sp,
                    style = MaterialTheme.typography.titleMedium
                )

                Text(
                    text = lanche.descricao,
                    fontSize = 14.sp,
                    modifier = Modifier.padding(top = 4.dp),
                    style = MaterialTheme.typography.bodyMedium
                )

                Text(
                    text = lanche.preco.toReais(),
                    fontSize = 16.sp,
                    modifier = Modifier.padding(top = 8.dp),
                    color = MaterialTheme.colorScheme.primary,
                    style = MaterialTheme.typography.titleSmall
                )
            }

            IconButton(onClick = { showDeleteDialog = true }) {
                Icon(
                    Icons.Default.Delete,
                    contentDescription = "Deletar",
                    tint = MaterialTheme.colorScheme.error
                )
            }
        }
    }

    if (showDeleteDialog) {
        AlertDialog(
            onDismissRequest = { showDeleteDialog = false },
            title = { Text("Remover lanche") },
            text = { Text("Deseja remover '${lanche.nome}' do cardápio?") },
            confirmButton = {
                TextButton(
                    onClick = {
                        onDeletarClick()
                        showDeleteDialog = false
                    }
                ) {
                    Text("Sim", color = MaterialTheme.colorScheme.error)
                }
            },
            dismissButton = {
                TextButton(onClick = { showDeleteDialog = false }) {
                    Text("Não")
                }
            }
        )
    }
}

@Composable
fun AdicionarLancheDialog(
    onDismiss: () -> Unit,
    onConfirm: (String, String, Double) -> Unit
) {
    val context = LocalContext.current
    var nome by remember { mutableStateOf("") }
    var descricao by remember { mutableStateOf("") }
    var preco by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Adicionar Lanche") },
        text = {
            Column {
                OutlinedTextField(
                    value = nome,
                    onValueChange = { nome = it },
                    label = { Text("Nome") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 8.dp),
                    singleLine = true
                )

                OutlinedTextField(
                    value = descricao,
                    onValueChange = { descricao = it },
                    label = { Text("Descrição") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 8.dp),
                    maxLines = 3
                )

                OutlinedTextField(
                    value = preco,
                    onValueChange = {
                        if (it.isEmpty() || it.matches(Regex("^\\d*\\.?\\d*$"))) {
                            preco = it
                        }
                    },
                    label = { Text("Preço (ex: 15.50)") },
                    modifier = Modifier.fillMaxWidth(),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Decimal,
                        imeAction = ImeAction.Done
                    ),
                    singleLine = true
                )
            }
        },
        confirmButton = {
            TextButton(
                onClick = {
                    when {
                        nome.isBlank() -> {
                            Toast.makeText(context, "Preencha o nome", Toast.LENGTH_SHORT).show()
                        }
                        descricao.isBlank() -> {
                            Toast.makeText(context, "Preencha a descrição", Toast.LENGTH_SHORT).show()
                        }
                        preco.isBlank() -> {
                            Toast.makeText(context, "Preencha o preço", Toast.LENGTH_SHORT).show()
                        }
                        else -> {
                            val precoDouble = preco.toDoubleOrNull()
                            if (precoDouble != null && precoDouble > 0) {
                                onConfirm(nome.trim(), descricao.trim(), precoDouble)
                            } else {
                                Toast.makeText(context, "Preço inválido", Toast.LENGTH_SHORT).show()
                            }
                        }
                    }
                }
            ) {
                Text("Adicionar")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancelar")
            }
        }
    )
}
