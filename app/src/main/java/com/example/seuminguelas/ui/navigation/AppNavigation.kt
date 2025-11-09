package com.example.seuminguelas.ui.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.seuminguelas.ui.admin.AdminScreen
import com.example.seuminguelas.ui.admin.AdminViewModel
import com.example.seuminguelas.ui.auth.AuthViewModel
import com.example.seuminguelas.ui.auth.CadastroScreen
import com.example.seuminguelas.ui.auth.LoginScreen
import com.example.seuminguelas.ui.cardapio.CardapioScreen
import com.example.seuminguelas.ui.cardapio.CardapioViewModel
import com.example.seuminguelas.ui.carrinho.CarrinhoScreen
import com.example.seuminguelas.ui.carrinho.CarrinhoViewModel

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "login") {

        // LOGIN
        composable("login") {
            val viewModel: AuthViewModel = hiltViewModel()
            LoginScreen(
                viewModel = viewModel,
                onLoginSuccess = { usuarioId, usuarioNome, isAdmin ->
                    if (isAdmin) {
                        navController.navigate("admin/$usuarioId/$usuarioNome") {
                            popUpTo("login") { inclusive = true }
                        }
                    } else {
                        navController.navigate("cardapio/$usuarioId/$usuarioNome") {
                            popUpTo("login") { inclusive = true }
                        }
                    }
                },
                onNavigateToCadastro = {
                    navController.navigate("cadastro")
                }
            )
        }

        // CADASTRO
        composable("cadastro") {
            val viewModel: AuthViewModel = hiltViewModel()
            CadastroScreen(
                viewModel = viewModel,
                onCadastroSuccess = { navController.popBackStack() },
                onVoltar = { navController.popBackStack() }
            )
        }

        // CARDÁPIO
        composable(
            route = "cardapio/{usuarioId}/{usuarioNome}",
            arguments = listOf(
                navArgument("usuarioId") { type = NavType.LongType },
                navArgument("usuarioNome") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val usuarioId = backStackEntry.arguments?.getLong("usuarioId") ?: 0L
            val usuarioNome = backStackEntry.arguments?.getString("usuarioNome") ?: ""

            val viewModel: CardapioViewModel = hiltViewModel()
            CardapioScreen(
                viewModel = viewModel,
                usuarioId = usuarioId,
                usuarioNome = usuarioNome,
                onNavigateToCarrinho = {
                    navController.navigate("carrinho/$usuarioId")
                },
                onSair = {
                    navController.navigate("login") {
                        popUpTo(0) { inclusive = true }
                    }
                }
            )
        }

        // CARRINHO
        composable(
            route = "carrinho/{usuarioId}",
            arguments = listOf(
                navArgument("usuarioId") { type = NavType.LongType }
            )
        ) { backStackEntry ->
            val usuarioId = backStackEntry.arguments?.getLong("usuarioId") ?: 0L
            val viewModel: CarrinhoViewModel = hiltViewModel()

            CarrinhoScreen(
                viewModel = viewModel,
                usuarioId = usuarioId,
                onVoltar = { navController.popBackStack() },
                onFinalizarSuccess = { navController.popBackStack() }
            )
        }

        // ADMIN
        composable(
            route = "admin/{usuarioId}/{usuarioNome}",
            arguments = listOf(
                navArgument("usuarioId") { type = NavType.LongType },
                navArgument("usuarioNome") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val usuarioNome = backStackEntry.arguments?.getString("usuarioNome") ?: ""
            val viewModel: AdminViewModel = hiltViewModel()

            AdminScreen(
                viewModel = viewModel,
                usuarioNome = usuarioNome,
                onSair = {
                    navController.navigate("login") {
                        popUpTo(0) { inclusive = true }
                    }
                }
            )
        }
    }
}
