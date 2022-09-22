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

package androidx.compose.samples.crane.util

import android.util.Log
import org.joda.time.LocalDate
import java.time.DayOfWeek
import java.time.YearMonth
import java.time.temporal.WeekFields

fun YearMonth.getNumberWeeks(firstDayOfWeek: DayOfWeek): Int {
    val weekFields = WeekFields.of(firstDayOfWeek, 1)
    val firstWeekNumber = this.atDay(1)[weekFields.weekOfMonth()]
    val lastWeekNumber = this.atEndOfMonth()[weekFields.weekOfMonth()]
    return lastWeekNumber - firstWeekNumber + 1 // Both weeks inclusive
}

fun LocalDate.getNumberWeeks(firstDayOfWeek: Int): Int {
    val firstMonthDate = withDayOfMonth(1)
    val lastMonthDate = plusMonths(1).withDayOfMonth(1).plusDays(-1)

    val from = firstMonthDate.plusDays(firstDayOfWeek % 7 - firstMonthDate.dayOfWeek % 7)
    val to = lastMonthDate.plusDays(firstDayOfWeek % 7 - lastMonthDate.dayOfWeek % 7)

    Log.d("TestDate", "firstWeekDate: ${from.weekOfWeekyear}, lastWeekDate: ${to.weekOfWeekyear}")

    val firstWeekDate = firstMonthDate.plusDays(firstDayOfWeek % 7 - firstMonthDate.dayOfWeek % 7)
    val lastWeekDate = lastMonthDate.plusDays(firstDayOfWeek % 7 - lastMonthDate.dayOfWeek % 7)
    val weekDiff = if (firstWeekDate.weekyear < lastWeekDate.weekyear)
        lastWeekDate.weekOfWeekyear + 1
    else lastWeekDate.weekOfWeekyear - firstWeekDate.weekOfWeekyear

    return weekDiff + 1
}
