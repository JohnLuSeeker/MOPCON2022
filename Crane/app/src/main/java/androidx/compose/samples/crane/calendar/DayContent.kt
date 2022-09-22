package androidx.compose.samples.crane.calendar

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp

@Composable
internal fun WaterBallSolid(
    color: Color
) {
    Box(modifier = Modifier.size(30.dp)) {
        Canvas(
            modifier = Modifier
                .align(Alignment.Center)
                .size(16.dp)
        ) {
            drawCircle(color = color)
        }
    }
}

@Composable
internal fun WaterBallOutline(
    color: Color
) {
    val widthPx = with(LocalDensity.current) { 1.5.dp.toPx() }
    Box(modifier = Modifier.size(30.dp)) {
        Canvas(
            modifier = Modifier
                .align(Alignment.Center)
                .size(16.dp)
        ) {
            drawOval(color = color, style = Stroke(width = widthPx))
        }
    }
}

@Composable
internal fun DailyReviewCompleted(
    color: Color
) {
    Box(modifier = Modifier.size(30.dp)) {
        Canvas(
            modifier = Modifier
                .align(Alignment.Center)
                .size(8.dp)
        ) {
            drawCircle(color = color)
        }
    }
}
