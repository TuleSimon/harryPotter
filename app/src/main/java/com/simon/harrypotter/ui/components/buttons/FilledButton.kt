package com.simon.harrypotter.ui.components.buttons

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.simon.harrypotter.ui.components.BodyText

@Composable
fun FilledButton(modifier: Modifier = Modifier,text:String, onClick:() -> Unit, ){
    Button(onClick = { onClick.invoke()  }, modifier = modifier) {
        BodyText(text = text,color = MaterialTheme.colorScheme.onPrimary)
    }
}