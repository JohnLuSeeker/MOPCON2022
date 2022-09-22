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

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.samples.crane.calendar.model.CalendarUiState
import androidx.compose.samples.crane.calendar.model.Week
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.Month
import java.time.format.TextStyle
import java.time.temporal.TemporalAdjusters
import java.util.Locale

@Composable
internal fun DaysOfWeek(
    modifier: Modifier = Modifier,
    firstDayOfWeek: DayOfWeek
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        for (day in 0L until 7L) {
            DayOfWeekHeading(
                modifier = Modifier.weight(1F),
                day = firstDayOfWeek.plus(day).getDisplayName(TextStyle.SHORT, Locale.getDefault())
            )
        }
    }
}

@Composable
internal fun Week(
    modifier: Modifier = Modifier,
    calendarUiState: CalendarUiState,
    week: Week,
    firstDayOfWeek: DayOfWeek,
    dividerContent: @Composable BoxScope.(LocalDate, Month) -> Unit = { _, _ -> },
    topDayContent: @Composable (LocalDate, Month) -> Unit = { _, _ -> },
    bottomDayContent: @Composable (LocalDate, Month) -> Unit = { _, _ -> }
) {
    val beginningWeek = week.yearMonth.atDay(1).plusWeeks(week.number.toLong())
    var currentDay = beginningWeek.with(TemporalAdjusters.previousOrSame(firstDayOfWeek))

//    Box {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
//            Spacer(
//                Modifier
//                    .weight(1f)
//                    .heightIn(max = CELL_SIZE)
//            )
        for (i in 0..6) {
            Box(Modifier.weight(1F)) {
//                dividerContent(
//                    Modifier.align(Alignment.CenterStart),
//                    currentDay,
//                    week.yearMonth.month
//                )
//                if (currentDay.month == week.yearMonth.month) {
                Day(
                    calendarState = calendarUiState,
                    day = currentDay,
                    month = week.yearMonth.month,
                    topContent = topDayContent,
                    bottomContent = bottomDayContent
                )
//                }
//                else {
//                    Box(modifier = Modifier.size(CELL_SIZE))
//                }
                dividerContent(
                    currentDay,
                    week.yearMonth.month
                )
            }

            currentDay = currentDay.plusDays(1)
        }
//            Spacer(
//                Modifier
//                    .weight(1f)
//                    .heightIn(max = CELL_SIZE)
//            )
    }
//    }
}

internal val CELL_WIDTH = 30.dp
internal val CELL_HEIGHT = 18.dp
internal val CELL_ASPECT_RATIO = 5F / 3F
