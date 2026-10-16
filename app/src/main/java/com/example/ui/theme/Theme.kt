package com.example.ui.theme

import android.app.Activity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val ObsidiaDarkColorScheme = darkColorScheme(
    primary = CrimsonRed,
    onPrimary = TextPrimary,
    primaryContainer = BurgundyDeep,
    onPrimaryContainer = TextPrimary,
    secondary = CrimsonRedDark,
    onSecondary = TextPrimary,
    secondaryContainer = ObsidianCardElevated,
    onSecondaryContainer = TextPrimary,
    tertiary = MetallicSilver,
    onTertiary = ObsidianBg,
    background = ObsidianBg,
    onBackground = TextPrimary,
    surface = ObsidianSurface,
    onSurface = TextPrimary,
    surfaceVariant = ObsidianCard,
    onSurfaceVariant = TextSecondary,
    outline = ObsidianBorder,
    outlineVariant = ObsidianBorderSubtle
)

@Composable
fun ObsidiaTheme(
    content: @Composable () -> Unit
) {
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as? Activity)?.window
            if (window != null) {
                window.statusBarColor = ObsidianBg.toArgb()
                window.navigationBarColor = ObsidianBg.toArgb()
                WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = false
                WindowCompat.getInsetsController(window, view).isAppearanceLightNavigationBars = false
            }
        }
    }

    MaterialTheme(
        colorScheme = ObsidiaDarkColorScheme,
        typography = Typography,
        content = content
    )
}

// Backward-compatible alias
@Composable
fun MyApplicationTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    ObsidiaTheme(content = content)
}
