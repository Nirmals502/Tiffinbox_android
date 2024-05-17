package com.example.easy_tiffin.ui.ui.home.model

data class MenuItem(
    val id: String,
    val title: String,
    val description: String,
    val price: String,
    val type: MenuType
)

enum class MenuType {
    Sun, Mon, Tue, Wed, Thu, Fri, Sat
}
