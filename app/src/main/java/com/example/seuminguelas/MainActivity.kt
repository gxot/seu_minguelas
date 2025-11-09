package com.example.seuminguelas

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.seuminguelas.ui.navigation.AppNavigation
import com.example.seuminguelas.ui.theme.SeuMinguelasTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SeuMinguelasTheme {
                AppNavigation()
            }
        }
    }
}