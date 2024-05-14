package com.example.easy_tiffin.ui.ui.home



interface MenuRepository {
    suspend fun getMenuItems(): List<MenuItem>
}
class LocalMenuRepository : MenuRepository {
    override suspend fun getMenuItems(): List<MenuItem> {
        return listOf(
            MenuItem("1", "One Time Meal 1", "Description for one time meal 1", "10"),
            MenuItem("2", "One Time Meal 2", "Description for one time meal 2", "12"),
            MenuItem("3", "Weekly Meal 1", "Description for weekly meal 1", "88",),
            MenuItem("4", "Weekly Meal 2", "Description for weekly meal 2", "55.0" ),
            MenuItem("5", "Monthly Meal 1", "Description for monthly meal 1", "200.0"),
            MenuItem("6", "Monthly Meal 2", "Description for monthly meal 2", "220.0")
        )
    }
}