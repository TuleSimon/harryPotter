package com.simon.harrypotter.ui.theme

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
import androidx.compose.ui.platform.LocalWindowInfo
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import com.google.accompanist.systemuicontroller.rememberSystemUiController

private val DarkColorScheme = darkColorScheme(
    primary = primary,
    secondary = primary,
    tertiary = primary,
    onBackground = onBackgroundDark,
    background = backgroundDark,
    surface = surfaceDark
)

private val LightColorScheme = lightColorScheme(
    primary = primary,
    secondary = primary,
    tertiary = primary,
    onBackground = onBackground,
    background = background,
    surface = surface

)

@Composable
fun HarryPotterTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
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
    val uiController = rememberSystemUiController()
    if (!view.isInEditMode) {
        SideEffect {
           // (view.context as Activity).window.statusBarColor = colorScheme.primary.toArgb()
            uiController.setStatusBarColor(Color.Unspecified, darkIcons = !darkTheme)
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}