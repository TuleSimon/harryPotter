import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.ui.unit.dp

val simonShapes = Shapes(
     small = RoundedCornerShape(10.dp),
     medium = RoundedCornerShape(15.dp),
     large = RoundedCornerShape(20.dp),
)

val onlyBottomRounded =  simonShapes.large.copy(topStart = CornerSize(0.dp), topEnd = CornerSize(0.dp))