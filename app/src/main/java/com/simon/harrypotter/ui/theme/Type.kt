package com.simon.harrypotter.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.simon.harrypotter.R

// Set of Material typography styles to start with
val gilroy = FontFamily(
    Font(R.font.gilroyblack, FontWeight.Black),
    Font(R.font.gilroybold, FontWeight.Bold),
    Font(R.font.gilroyblackitalic, FontWeight.Black, FontStyle.Italic),
    Font(R.font.gilroybolditalic, FontWeight.Bold, FontStyle.Italic),
    Font(R.font.gilroyextrabold, FontWeight.ExtraBold, FontStyle.Normal),
    Font(R.font.gilroyextrabolditalic, FontWeight.ExtraBold, FontStyle.Italic),
    Font(R.font.gilroyheavy, FontWeight.W900, FontStyle.Italic),
    Font(R.font.gilroyheavyitalic, FontWeight.W900),
    Font(R.font.gilroylight, FontWeight.Light),
    Font(R.font.gilroylight, FontWeight.Light),
    Font(R.font.gilroylightitalic, FontWeight.Light, FontStyle.Italic),
    Font(R.font.gilroymedium, FontWeight.Medium, FontStyle.Normal),
    Font(R.font.gilroymediumitalic, FontWeight.Medium, FontStyle.Italic),
    Font(R.font.gilroyregular, FontWeight.Normal),
    Font(R.font.gilroyregularitalic, FontWeight.Normal, FontStyle.Italic),
    Font(R.font.gilroysemibold, FontWeight.SemiBold),
    Font(R.font.gilroysemibolditalic, FontWeight.SemiBold, FontStyle.Italic),
    Font(R.font.gilroythin, FontWeight.Thin),
    Font(R.font.gilroythinitalic, FontWeight.Thin, FontStyle.Italic),
    Font(R.font.gilroyultralight, FontWeight.ExtraLight),
    Font(R.font.gilroyultralightitalic, FontWeight.ExtraLight, FontStyle.Italic),
)
val Typography = Typography(
    bodyMedium = TextStyle(
        fontFamily = gilroy,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp
    ),
    bodySmall = TextStyle(
        fontFamily = gilroy,
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp
    ),
    bodyLarge = TextStyle(
        fontFamily = gilroy,
        fontWeight = FontWeight.Bold,
        fontSize = 18.sp
    ),
    titleLarge = TextStyle(
        fontFamily = gilroy,
        fontWeight = FontWeight.Bold,
        fontSize = 30.sp
    ),
    titleMedium = TextStyle(
        fontFamily = gilroy,
        fontWeight = FontWeight.SemiBold,
        fontSize = 25.sp,
    ),
    titleSmall = TextStyle(
        fontFamily = gilroy,
        fontWeight = FontWeight.SemiBold,
        fontSize = 20.sp
    ),
    headlineMedium = TextStyle(
        fontFamily = gilroy,
        fontWeight = FontWeight.Bold,
        fontSize = 16.sp
    ),
    headlineSmall = TextStyle(
        fontFamily = gilroy,
        fontWeight = FontWeight.Bold,
        fontSize = 14.sp
    ),
    headlineLarge = TextStyle(
        fontFamily = gilroy,
        fontWeight = FontWeight.Bold,
        fontSize = 18.sp
    ),
    labelMedium = TextStyle(
        fontFamily = gilroy,
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp
    ),
    labelSmall = TextStyle(
        fontFamily = gilroy,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp
    ),
    displayMedium = TextStyle(
        fontFamily = gilroy,
        fontWeight = FontWeight.SemiBold,
        fontSize = 14.sp
    ),
    displaySmall = TextStyle(
        fontFamily = gilroy,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp
    ),

    )