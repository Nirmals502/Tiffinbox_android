package com.example.easy_tiffin.DayOfWeekUtil

import com.example.easy_tiffin.ui.ui.home.model.MenuType
import java.util.*

class DayOfWeekUtil {
    companion object {
        fun getCurrentDayOfWeek(): MenuType {
            val calendar = Calendar.getInstance()
            val currentDayOfWeek = calendar.get(Calendar.DAY_OF_WEEK)
            return when (currentDayOfWeek) {
                Calendar.SUNDAY -> MenuType.Sun
                Calendar.MONDAY -> MenuType.Mon
                Calendar.TUESDAY -> MenuType.Tue
                Calendar.WEDNESDAY -> MenuType.Wed
                Calendar.THURSDAY -> MenuType.Thu
                Calendar.FRIDAY -> MenuType.Fri
                Calendar.SATURDAY -> MenuType.Sat
                else -> MenuType.Sun // Default to Sunday if unexpected day
            }
        }
    }
}
