package com.example.seuminguelas.ui.carrinho

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.seuminguelas.data.relations.PedidoComLanche
import com.example.seuminguelas.utils.toReais

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CarrinhoScreen(
    usuarioId: Long,
    onVoltar: () -> Unit,
    onFinalizarSuccess: () -> Unit,
    viewModel: CarrinhoViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val carrinhoCompleto by viewModel.carrinhoCompleto.observeAsState()
    var showDialog by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        viewModel.carregarCarrinho(usuarioId)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Meu Carrinho") },
                navigationIcon = {
                    IconButton(onClick = onVoltar) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Voltar")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            if (carrinhoCompleto == null || carrinhoCompleto?.pedidosComLanches?.isEmpty() == true) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Carrinho vazio",
                        fontSize = 18.sp,
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
            } else {
                LazyColumn(
                    modifier = Modifier
                        .weight(1f)
                        .padding(8.dp)
                ) {
                    items(carrinhoCompleto?.pedidosComLanches ?: emptyList()) { pedidoComLanche ->
                        PedidoItem(
                            pedidoComLanche = pedidoComLanche,
                            onRemoverClick = {
                                viewModel.removerPedido(pedidoComLanche.pedido)
                            }
                        )
                    }
                }

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.primaryContainer
                    )
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = "Total:",
                                fontSize = 20.sp,
                                style = MaterialTheme.typography.titleLarge
                            )
                            Text(
                                text = viewModel.calcularTotal().toReais(),
                                fontSize = 20.sp,
                                style = MaterialTheme.typography.titleLarge,
                                color = MaterialTheme.colorScheme.primary
                            )
                        }

                        Button(
                            onClick = { showDialog = true },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 12.dp)
                                .height(50.dp)
                        ) {
                            Text("Finalizar Pedido", fontSize = 16.sp)
                        }
                    }
                }
            }
        }
    }

    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = { Text("Finalizar pedido") },
            text = { Text("Deseja finalizar o pedido?") },
            confirmButton = {
                TextButton(
                    onClick = {
                        viewModel.finalizarCarrinho()
                        Toast.makeText(context, "Pedido finalizado com sucesso!", Toast.LENGTH_SHORT).show()
                        showDialog = false
                        onFinalizarSuccess()
                    }
                ) {
                    Text("Sim")
                }
            },
            dismissButton = {
                TextButton(onClick = { showDialog = false }) {
                    Text("Não")
                }
            }
        )
    }
}

@Composable
fun PedidoItem(
    pedidoComLanche: PedidoComLanche,
    onRemoverClick: () -> Unit
) {
    var showDialog by remember { mutableStateOf(false) }
    val pedido = pedidoComLanche.pedido
    val lanche = pedidoComLanche.lanche

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
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = lanche.nome,
                    fontSize = 16.sp,
                    style = MaterialTheme.typography.titleMedium
                )

                Text(
                    text = "Quantidade: ${pedido.quantidade}",
                    fontSize = 14.sp,
                    modifier = Modifier.padding(top = 4.dp),
                    style = MaterialTheme.typography.bodyMedium
                )

                Text(
                    text = "Preço unitário: ${pedido.precoUnitario.toReais()}",
                    fontSize = 14.sp,
                    modifier = Modifier.padding(top = 4.dp),
                    style = MaterialTheme.typography.bodyMedium
                )

                Text(
                    text = "Subtotal: ${(pedido.quantidade * pedido.precoUnitario).toReais()}",
                    fontSize = 16.sp,
                    modifier = Modifier.padding(top = 8.dp),
                    color = MaterialTheme.colorScheme.primary,
                    style = MaterialTheme.typography.titleSmall
                )
            }

            OutlinedButton(
                onClick = { showDialog = true },
                modifier = Modifier.align(Alignment.CenterVertically)
            ) {
                Text("Remover")
            }
        }
    }

    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = { Text("Remover item") },
            text = { Text("Deseja remover este item do carrinho?") },
            confirmButton = {
                TextButton(
                    onClick = {
                        onRemoverClick()
                        showDialog = false
                    }
                ) {
                    Text("Sim")
                }
            },
            dismissButton = {
                TextButton(onClick = { showDialog = false }) {
                    Text("Não")
                }
            }
        )
    }
}
