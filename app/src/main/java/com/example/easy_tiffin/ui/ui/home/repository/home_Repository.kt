package com.example.easy_tiffin.ui.ui.home.repository

import com.example.easy_tiffin.ui.ui.home.model.MenuItem
import com.example.easy_tiffin.ui.ui.home.model.MenuType


interface MenuRepository {
    suspend fun getMenuItems(): List<MenuItem>
}

class home_Repository : MenuRepository {
    override suspend fun getMenuItems(): List<MenuItem> {
        return listOf(
            MenuItem("1", "One Time Meal 1", "Chana Daal Curry is a flavorful and nutritious dish made from split chickpeas (chana daal) cooked in a savory curry base. It is a popular North Indian dish known for its rich taste, creamy texture, and aromatic spices.", "10", MenuType.Mon),
            MenuItem("1", "One Time Meal 1", "Mon", "10", MenuType.Mon),
            MenuItem("1", "One Time Meal 1", "Mon", "10", MenuType.Mon),
            MenuItem("1", "One Time Meal 1", "Mon", "10", MenuType.Mon),
            MenuItem("1", "One Time Meal 1", "Mon", "10", MenuType.Mon),
            MenuItem("1", "One Time Meal 1", "Mon", "10", MenuType.Mon),
            MenuItem("1", "One Time Meal 1", "Mon", "10", MenuType.Mon),
            MenuItem("1", "One Time Meal 1", "Mon", "10", MenuType.Mon),
            MenuItem("2", "One Time Meal 2", "Tue", "12", MenuType.Tue),
            MenuItem("3", "Weekly Meal 1", "Wed", "88", MenuType.Wed),
            MenuItem("4", "Weekly Meal 2", "Thu", "55.0", MenuType.Thu),
            MenuItem("4", "Weekly Meal 2", "Thu", "55.0", MenuType.Thu),
            MenuItem("4", "Weekly Meal 2", "Thu", "55.0", MenuType.Thu),
            MenuItem("4", "Weekly Meal 2", "Thu", "55.0", MenuType.Thu),
            MenuItem("4", "Weekly Meal 2", "Thu", "55.0", MenuType.Thu),
            MenuItem("4", "Weekly Meal 2", "Thu", "55.0", MenuType.Thu),
            MenuItem("4", "Weekly Meal 2", "Thu", "55.0", MenuType.Thu),
            MenuItem("4", "Weekly Meal 2", "Thu", "55.0", MenuType.Thu),

            MenuItem("5", "Monthly Meal 1", "Fri", "200.0", MenuType.Fri),
            MenuItem("6", "Monthly Meal 2", "Sat", "220.0", MenuType.Sat)
        )
    }
}