package dam.a51564.mip2.compose.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

private val DarkColorScheme = darkColorScheme(
    primary = MutedGold,
    onPrimary = DeepCharcoal,
    secondary = WarmGrey,
    background = DeepCharcoal,
    surface = DarkGrey,
    onBackground = LightCream,
    onSurface = LightCream
)

private val LightColorScheme = lightColorScheme(
    primary = SoftBrown,
    onPrimary = Color.White,
    secondary = WarmGrey,
    background = Cream,
    surface = BeigeSurface,
    onBackground = DeepBrown,
    onSurface = DeepBrown
)

@Composable
fun DogAppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = true,
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

    MaterialTheme(
        colorScheme = colorScheme,
        content = content
    )
}
