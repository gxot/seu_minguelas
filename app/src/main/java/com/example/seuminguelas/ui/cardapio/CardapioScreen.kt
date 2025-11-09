package com.example.seuminguelas.ui.cardapio

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.seuminguelas.data.entities.Lanche
import com.example.seuminguelas.utils.toReais

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CardapioScreen(
    usuarioId: Long,
    usuarioNome: String,
    onNavigateToCarrinho: () -> Unit,
    onSair: () -> Unit,
    viewModel: CardapioViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val lanches by viewModel.lanches.observeAsState(emptyList())

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Cardápio - $usuarioNome") },
                actions = {
                    IconButton(onClick = onNavigateToCarrinho) {
                        Icon(Icons.Default.ShoppingCart, contentDescription = "Carrinho")
                    }
                    IconButton(onClick = onSair) {
                        Icon(Icons.Default.ExitToApp, contentDescription = "Sair")
                    }
                }
            )
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(8.dp)
        ) {
            items(lanches) { lanche ->
                LancheItem(
                    lanche = lanche,
                    onAdicionarClick = { quantidade ->
                        viewModel.adicionarAoCarrinho(usuarioId, lanche, quantidade)
                        Toast.makeText(context, "${lanche.nome} adicionado ao carrinho", Toast.LENGTH_SHORT).show()
                    }
                )
            }
        }
    }
}

@Composable
fun LancheItem(
    lanche: Lanche,
    onAdicionarClick: (Int) -> Unit
) {
    var quantidade by remember { mutableStateOf(1) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
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

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                OutlinedButton(
                    onClick = { if (quantidade > 1) quantidade-- },
                    modifier = Modifier.size(48.dp),
                    contentPadding = PaddingValues(0.dp)
                ) {
                    Text("-", fontSize = 20.sp)
                }

                Text(
                    text = quantidade.toString(),
                    fontSize = 18.sp,
                    modifier = Modifier
                        .weight(1f)
                        .padding(horizontal = 16.dp),
                    style = MaterialTheme.typography.titleMedium
                )

                OutlinedButton(
                    onClick = { quantidade++ },
                    modifier = Modifier.size(48.dp),
                    contentPadding = PaddingValues(0.dp)
                ) {
                    Text("+", fontSize = 20.sp)
                }

                Spacer(modifier = Modifier.width(8.dp))

                Button(
                    onClick = {
                        onAdicionarClick(quantidade)
                        quantidade = 1
                    }
                ) {
                    Text("Adicionar")
                }
            }
        }
    }
}
