/*
 * Copyright 2022 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package androidx.compose.samples.crane.calendar

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.samples.crane.calendar.model.CalendarUiState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.SemanticsPropertyKey
import androidx.compose.ui.semantics.SemanticsPropertyReceiver
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.time.LocalDate
import java.time.Month

@Composable
internal fun DayOfWeekHeading(
    modifier: Modifier = Modifier,
    day: String
) {
    DayContainer(
        modifier = modifier,
        text = day
    )
}

@Composable
private fun DayContainer(
    modifier: Modifier = Modifier,
    text: String,
    textColor: Color = Color.Unspecified,
    highlighted: Boolean = false,
    onClick: () -> Unit = { },
    onClickEnabled: Boolean = true,
    backgroundColor: Color = Color.Transparent,
    onClickLabel: String? = null,
    topContent: @Composable () -> Unit = {},
    bottomContent: @Composable () -> Unit = {}
) {
//    val stateDescriptionLabel = stringResource(
//        if (selected) R.string.state_descr_selected else R.string.state_descr_not_selected
//    )
    Column(
        modifier = modifier
            .fillMaxWidth()

//            .pointerInput(Any()) {
//                detectTapGestures {
//                    onClick()
//                }
//            }
//            .then(
//                if (onClickEnabled) {
//                    modifier.semantics {
//                        stateDescription = stateDescriptionLabel
//                        onClick(label = onClickLabel, action = null)
//                    }
//                } else {
//                    modifier.clearAndSetSemantics { }
//                }
//            )
        ,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        topContent()
        DayText {
            if (highlighted)
                HighlightTextContent(text = text)
            else
                TextContent(text = text, color = textColor)
        }
        bottomContent()
    }
}

@Composable
fun BoxScope.TextContent(
    text: String,
    color: Color = Color.Unspecified
) {
    Text(
        text = text,
        modifier = Modifier.align(Alignment.Center),
        color = color,
        fontSize = 12.sp,
        fontWeight = FontWeight.W400,
        textAlign = TextAlign.Center
    )
}

@Composable
fun BoxScope.HighlightTextContent(text: String) {
    Text(
        text = text,
        modifier = Modifier.align(Alignment.Center),
        color = Color.Blue,
        fontSize = 12.sp,
        fontWeight = FontWeight.W400,
        textAlign = TextAlign.Center
    )
    Spacer(
        modifier = Modifier
            .size(height = 2.dp, width = 14.dp)
            .clip(RoundedCornerShape(5.dp))
            .background(Color.Blue)
            .align(Alignment.BottomCenter)
    )
}

@Composable
fun DayText(textContent: @Composable BoxScope.() -> Unit = {}) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(CELL_HEIGHT)
    ) {
        textContent()
    }
}

@Composable
internal fun Day(
    modifier: Modifier = Modifier,
    day: LocalDate,
    calendarState: CalendarUiState,
//    onDayClicked: (LocalDate) -> Unit,
    month: Month,
    topContent: @Composable (LocalDate, Month) -> Unit,
    bottomContent: @Composable (LocalDate, Month) -> Unit
) {
    val highlighted = calendarState.isDateOnToday(day)
    val isInThisMonth = day.month == month
    val textColor =
        if (highlighted) Color(0xFF779DD5)
        else if (isInThisMonth) Color.Black
        else Color.LightGray
    DayContainer(
        modifier = modifier,
        text = day.dayOfMonth.toString(),
        textColor = textColor,
        topContent = {
            topContent(day, month)
        },
        highlighted = highlighted
//        modifier = modifier.semantics {
//            text = AnnotatedString("${month.month.name.lowercase().capitalize(Locale.current)} ${day.dayOfMonth} ${month.year}")
//          dayStatusProperty = selected
//        },
//        selected = selected,
//        onClick = { onDayClicked(day) },
//        onClickLabel = stringResource(id = R.string.click_label_select)
    ) {
        bottomContent(day, month)
    }
}

val DayStatusKey = SemanticsPropertyKey<Boolean>("DayStatusKey")
var SemanticsPropertyReceiver.dayStatusProperty by DayStatusKey
