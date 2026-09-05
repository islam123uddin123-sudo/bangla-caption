package com.example.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val LightColorScheme = lightColorScheme(
    primary = Emerald600,
    onPrimary = Color.White,
    primaryContainer = Emerald50,
    onPrimaryContainer = Emerald800,
    secondary = Emerald700,
    onSecondary = Color.White,
    secondaryContainer = Emerald100,
    onSecondaryContainer = Emerald900,
    tertiary = Emerald500,
    background = PolishBackground,
    onBackground = Slate900,
    surface = PolishSurface,
    onSurface = Slate800,
    surfaceVariant = Emerald50,
    onSurfaceVariant = Slate600,
    outline = PolishBorder,
    outlineVariant = Slate200
)

private val DarkColorScheme = darkColorScheme(
    primary = Emerald500,
    onPrimary = Color(0xFF022C22),
    primaryContainer = Emerald800,
    onPrimaryContainer = Emerald100,
    secondary = Emerald200,
    onSecondary = Color(0xFF064E3B),
    secondaryContainer = Emerald900,
    onSecondaryContainer = Emerald200,
    tertiary = Emerald600,
    background = Color(0xFF0F172A),
    onBackground = Color(0xFFF8FAFC),
    surface = Color(0xFF1E293B),
    onSurface = Color(0xFFF1F5F9),
    surfaceVariant = Color(0xFF334155),
    onSurfaceVariant = Slate400,
    outline = Color(0xFF334155),
    outlineVariant = Color(0xFF1E293B)
)

@Composable
fun MyApplicationTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as? Activity)?.window
            if (window != null) {
                window.statusBarColor = colorScheme.surface.toArgb()
                WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = !darkTheme
            }
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
