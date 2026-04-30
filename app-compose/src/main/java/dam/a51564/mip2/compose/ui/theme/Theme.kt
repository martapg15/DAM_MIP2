package dam.a51564.mip2.compose.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable

private val LightColorScheme = lightColorScheme(
    primary = BeigePrimary,
    onPrimary = BeigeOnPrimary,
    secondary = BeigeSecondary,
    onSecondary = BeigeOnSecondary,
    background = BeigeBackground,
    surface = BeigeSurface,
    onSurface = BeigeOnSurface
)

@Composable
fun DogBrowserTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    // For this project, we prioritize the Beige theme even in dark mode for aesthetic consistency
    // but we could define a DarkColorScheme if needed.
    val colorScheme = LightColorScheme

    MaterialTheme(
        colorScheme = colorScheme,
        content = content
    )
}
