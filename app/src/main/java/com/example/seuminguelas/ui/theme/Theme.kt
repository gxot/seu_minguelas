package com.example.seuminguelas.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val LightColorScheme = lightColorScheme(
    primary = androidx.compose.ui.graphics.Color(0xFF9C27B0),
    secondary = androidx.compose.ui.graphics.Color(0xFF673AB7),
    tertiary = androidx.compose.ui.graphics.Color(0xFF03DAC5)
)

@Composable
fun SeuMinguelasTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = LightColorScheme,
        typography = MaterialTheme.typography,
        content = content
    )
}