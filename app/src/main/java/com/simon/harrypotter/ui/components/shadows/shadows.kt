import android.annotation.SuppressLint
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.dp

@SuppressLint("ComposableModifierFactory")
@Composable
fun Modifier.shadowMedium(shapes: Shape = MaterialTheme.shapes.medium): Modifier {
    return this.shadow(
        elevation = 5.dp, shape = shapes, true, ambientColor = MaterialTheme.colorScheme.onBackground.copy(0.3f),
        spotColor = MaterialTheme.colorScheme.onBackground.copy(0.3f)
    )
}
@SuppressLint("ComposableModifierFactory")
@Composable
fun Modifier.shadowSmall(shapes: Shape = MaterialTheme.shapes.medium): Modifier {
    return this.shadow(
        elevation = 3.dp, shape = shapes, true, ambientColor = MaterialTheme.colorScheme.onBackground.copy(0.3f),
        spotColor = MaterialTheme.colorScheme.onBackground.copy(0.3f)
    )
}

@SuppressLint("ComposableModifierFactory")
@Composable
fun Modifier.shadowLarge(shapes: Shape = MaterialTheme.shapes.medium): Modifier {
    return this.shadow(
        elevation = 10.dp, shape = shapes, true, ambientColor = MaterialTheme.colorScheme.onBackground.copy(0.3f),
        spotColor = MaterialTheme.colorScheme.onBackground.copy(0.3f)
    )
}