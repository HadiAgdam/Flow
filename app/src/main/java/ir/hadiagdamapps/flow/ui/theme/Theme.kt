package ir.hadiagdamapps.flow.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable

private val ColorScheme = darkColorScheme(
    primary = Color.primary,
    secondary = Color.secondary,
    tertiary = Color.tertiary,
    background = Color.background,
    onPrimary = Color.onPrimary,
    onSecondary = Color.onSecondary,
    onTertiary = Color.onTertiary,
    surface = Color.surface,
    onSurface = Color.onSurface

)

@Composable
fun FlowTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = ColorScheme,
        typography = Type,
        content = content,

    )
}