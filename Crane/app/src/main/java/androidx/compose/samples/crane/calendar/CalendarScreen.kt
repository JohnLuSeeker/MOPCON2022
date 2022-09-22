/*
 * Copyright 2020 The Android Open Source Project
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
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.layout.windowInsetsTopHeight
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.samples.crane.R
import androidx.compose.samples.crane.calendar.model.CalendarState
import androidx.compose.samples.crane.home.MainViewModel
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import java.time.DayOfWeek
import java.time.LocalDate

@Composable
fun CalendarScreen(
    onBackPressed: () -> Unit,
    mainViewModel: MainViewModel
) {
    val calendarState = remember {
        mainViewModel.calendarState
    }

    CalendarContent(
        calendarState = calendarState,
        onDayClicked = { dateClicked ->
            mainViewModel.onDaySelected(dateClicked)
        },
        onBackPressed = onBackPressed
    )
}

@Composable
private fun CalendarContent(
    calendarState: CalendarState,
    onDayClicked: (LocalDate) -> Unit,
    onBackPressed: () -> Unit
) {
    Scaffold(
        modifier = Modifier.windowInsetsPadding(
            WindowInsets.navigationBars.only(WindowInsetsSides.Start + WindowInsetsSides.End)
        ),
        backgroundColor = Color.White,
        topBar = {
            CalendarTopAppBar(calendarState, onBackPressed)
        }
    ) { contentPadding ->
        RoutineCalendar(
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
            calendarState = calendarState,
            firstDayOfWeek = DayOfWeek.SUNDAY,
            dividerContent = { day, month ->
                val isInThisMonth = day.month == month
                if (isInThisMonth) {
                    Divider(
                        color = Color.LightGray,
                        modifier = Modifier
                            .align(Alignment.CenterEnd)
                            .height(24.dp)
                            .width(2.dp)
                    )
                }
            }
        ) { day, month ->
            val isLastDay = day.dayOfMonth == day.month.maxLength()
            val isFirstDay = day.dayOfMonth == 1
            val isInThisMonth = day.month == month
            if (isInThisMonth) {
                val fakeMap = mapOf<LocalDate, Boolean?>(
                    LocalDate.now() to true,
                    LocalDate.now().plusDays(1) to false,
                    LocalDate.now().plusDays(-7) to true
                )

                when (fakeMap.getOrDefault(day, null)) {
                    true -> WaterBallSolid(color = Color(0xFFF58D67))
                    false -> WaterBallOutline(color = Color(0xFFF58D67))
                    else -> WaterBallSolid(color = Color.Transparent)
                }
            } else {
                WaterBallSolid(color = Color.Transparent)
            }
        }

//        DailyReviewMonthlyCalendar(
//            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
//            calendarState = calendarState,
//            month = calendarState.getMonth(0),
//            firstDayOfWeek = DayOfWeek.SUNDAY
//        ){ day, month ->
//            val isLastDay = day.dayOfMonth == day.month.maxLength()
//            val isFirstDay = day.dayOfMonth == 1
//            val isInThisMonth = day.month == month
//            if (isInThisMonth) {
//                val fakeMap = mapOf<LocalDate, Boolean?>(
//                    LocalDate.now() to true,
//                    LocalDate.now().plusDays(1) to false,
//                    LocalDate.now().plusDays(-7) to true
//                )
//
//                when (fakeMap.getOrDefault(day, null)) {
//                    true -> WaterBallSolid(color = Color(0xFFF58D67))
//                    false -> WaterBallOutline(color = Color(0xFFF58D67))
//                    else -> WaterBallSolid(color = Color.Transparent)
//                }
//            } else {
//                WaterBallSolid(color = Color.Transparent)
//            }
//        }

//        DailyReviewWeeklyCalendar(
//            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
//            calendarState = calendarState,
//            week = calendarState.getMonth(0).weeks.first(),
//            firstDayOfWeek = DayOfWeek.SUNDAY
//        ){ day, month ->
//            val isLastDay = day.dayOfMonth == day.month.maxLength()
//            val isFirstDay = day.dayOfMonth == 1
//            val isInThisMonth = day.month == month
//            if (isInThisMonth) {
//                val fakeMap = mapOf<LocalDate, Boolean?>(
//                    LocalDate.now() to true,
//                    LocalDate.now().plusDays(1) to false,
//                    LocalDate.now().plusDays(-7) to true
//                )
//
//                when (fakeMap.getOrDefault(day, null)) {
//                    true -> WaterBallSolid(color = Color(0xFFF58D67))
//                    false -> WaterBallOutline(color = Color(0xFFF58D67))
//                    else -> WaterBallSolid(color = Color.Transparent)
//                }
//            } else {
//                WaterBallSolid(color = Color.Transparent)
//            }
//        }
    }
}

@Composable
private fun CalendarTopAppBar(calendarState: CalendarState, onBackPressed: () -> Unit) {
    val calendarUiState = calendarState.calendarUiState.value
    Column {
        Spacer(
            modifier = Modifier
                .windowInsetsTopHeight(WindowInsets.statusBars)
                .fillMaxWidth()
                .background(MaterialTheme.colors.primaryVariant)
        )
        TopAppBar(
            title = {
                Text(
                    text = if (!calendarUiState.hasSelectedDates) {
                        stringResource(id = R.string.calendar_select_dates_title)
                    } else {
                        calendarUiState.selectedDatesFormatted
                    }
                )
            },
            navigationIcon = {
                IconButton(onClick = { onBackPressed() }) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = stringResource(id = R.string.cd_back),
                        tint = MaterialTheme.colors.onSurface
                    )
                }
            },
            backgroundColor = MaterialTheme.colors.primaryVariant,
            elevation = 0.dp
        )
    }
}
