package com.simon.harrypotter.ui.components

import androidx.compose.material.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.sp

@Composable
fun BodyTextSmall(
    text: String,
    modifier: Modifier = Modifier,
    textAlign: TextAlign = TextAlign.Start,
    color: Color = MaterialTheme.colorScheme.onBackground.copy(0.5f),
    maxlines: Int = Int.MAX_VALUE,
    overflow: TextOverflow = TextOverflow.Ellipsis,
    textStyle: TextStyle = MaterialTheme.typography.bodySmall.copy(color = color)
) {

    Text(text = text, style = textStyle, modifier = modifier, maxLines = maxlines, overflow = overflow, textAlign = textAlign)
}

@Composable
fun BodyTextSmallBold(
    text: String,
    modifier: Modifier = Modifier,
    textAlign: TextAlign = TextAlign.Start,
    color: Color = MaterialTheme.colorScheme.onBackground.copy(0.7f),
    maxlines: Int = Int.MAX_VALUE,
    overflow: TextOverflow = TextOverflow.Ellipsis,
    textStyle: TextStyle = MaterialTheme.typography.bodySmall.copy(color = color, fontWeight = FontWeight.Bold)
) {

    Text(text = text, style = textStyle, modifier = modifier, maxLines = maxlines, overflow = overflow, textAlign = textAlign)
}

@Composable
fun BodyTextReallySmall(
    text: String,
    modifier: Modifier = Modifier,
    textAlign: TextAlign = TextAlign.Start,
    color: Color = MaterialTheme.colorScheme.onBackground.copy(0.5f),
    maxlines: Int = Int.MAX_VALUE,
    overflow: TextOverflow = TextOverflow.Ellipsis,
    textStyle: TextStyle = MaterialTheme.typography.bodySmall.copy(color = color)
) {

    Text(text = text, style = textStyle.copy(fontSize = 12.sp), modifier = modifier, maxLines = maxlines, overflow = overflow, textAlign = textAlign)
}

@Composable
fun BodyText(
    text: String,
    modifier: Modifier = Modifier,
    textAlign: TextAlign = TextAlign.Start,
    color: Color = MaterialTheme.colorScheme.onBackground.copy(0.7f),
    maxlines: Int = Int.MAX_VALUE,
    overflow: TextOverflow = TextOverflow.Ellipsis,
    textStyle: TextStyle = MaterialTheme.typography.bodyMedium.copy(color = color)
) {

    Text(text = text, style = textStyle, modifier = modifier, maxLines = maxlines, overflow = overflow, textAlign = textAlign)
} @Composable
fun BodyText(
    text: AnnotatedString,
    modifier: Modifier = Modifier,
    textAlign: TextAlign = TextAlign.Start,
    color: Color = MaterialTheme.colorScheme.onBackground.copy(0.7f),
    maxlines: Int = Int.MAX_VALUE,
    overflow: TextOverflow = TextOverflow.Ellipsis,
    textStyle: TextStyle = MaterialTheme.typography.bodyMedium.copy(color = color)
) {

    Text(text = text, style = textStyle, modifier = modifier, maxLines = maxlines, overflow = overflow, textAlign = textAlign)
}

@Composable
fun BodyTextBold(
    text: String,
    modifier: Modifier = Modifier,
    textAlign: TextAlign = TextAlign.Start,
    color: Color = MaterialTheme.colorScheme.onBackground.copy(0.7f),
    maxlines: Int = Int.MAX_VALUE,
    overflow: TextOverflow = TextOverflow.Ellipsis,
    textStyle: TextStyle = MaterialTheme.typography.bodyMedium.copy(color = color, fontWeight = FontWeight.Bold)
) {

    Text(text = text, style = textStyle, modifier = modifier, maxLines = maxlines, overflow = overflow, textAlign = textAlign)
}

@Composable
fun TitleText(
    text: String,
    modifier: Modifier = Modifier,
    textAlign: TextAlign = TextAlign.Start,
    color: Color = MaterialTheme.colorScheme.onBackground,
    maxlines: Int = Int.MAX_VALUE,
    overflow: TextOverflow = TextOverflow.Ellipsis,
    textStyle: TextStyle = MaterialTheme.typography.bodyLarge.copy(color = color,
        fontWeight = FontWeight.Bold)
) {

    Text(text = text, style = textStyle, modifier = modifier,
        lineHeight = 20.sp,
        maxLines = maxlines, overflow = overflow, textAlign = textAlign)
}

@Composable
fun TitleTextMedium(
    text: String,
    modifier: Modifier = Modifier,
    textAlign: TextAlign = TextAlign.Start,
    color: Color = MaterialTheme.colorScheme.onBackground,
    maxlines: Int = Int.MAX_VALUE,
    overflow: TextOverflow = TextOverflow.Ellipsis,
    textStyle: TextStyle = MaterialTheme.typography.titleMedium.copy(color = color, fontWeight = FontWeight.Bold)
) {

    Text(text = text, style = textStyle, modifier = modifier, maxLines = maxlines, overflow = overflow, textAlign = textAlign)
}