package ir.hadiagdamapps.flow.ui.theme

import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider

private val ColorScheme = darkColorScheme(
    primary = Color.primary,
//    secondary = Color.secondary,
//    tertiary = Color.tertiary,
    background = Color.background,
    surface = Color.surface,
)

@Composable
fun FlowTheme(
    content: @Composable () -> Unit
) {


    MaterialTheme(
        colorScheme = ColorScheme,
        typography = Type
    ) {
        CompositionLocalProvider(LocalContentColor provides Color.textColor) {
            content()
        }
    }


}