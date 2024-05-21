package com.example.easy_tiffin.ui.ui.menu_creator.menu_repo

import com.example.easy_tiffin.ui.ui.home.model.MenuItem
import com.example.easy_tiffin.ui.ui.home.model.MenuType
import com.example.easy_tiffin.ui.ui.menu_creator.model.MenuItem_


interface MenuRepository {
    suspend fun getMenuItems(): List<MenuItem_>
}

class menu_repo : MenuRepository {
    override suspend fun getMenuItems(): List<MenuItem_> {
        return listOf(
            // Snacks and Appetizers
            MenuItem_("1", "Samosa"),
            MenuItem_("2", "Pakora"),
            MenuItem_("3", "Aloo Tikki"),
            MenuItem_("4", "Dhokla"),
            MenuItem_("5", "Kachori"),
            MenuItem_("6", "Paneer Tikka"),
            MenuItem_("7", "Chicken Tikka"),
            MenuItem_("8", "Seekh Kebab"),
            MenuItem_("9", "Vada Pav"),
            MenuItem_("10", "Bhaji"),
            MenuItem_("11", "Fish Amritsari"),
            MenuItem_("12", "Prawn Koliwada"),
            MenuItem_("13", "Aloo Chaat"),
            MenuItem_("14", "Chaat"),
            MenuItem_("15", "Bhel Puri"),
            MenuItem_("16", "Pani Puri"),
            MenuItem_("17", "Dahi Puri"),
            MenuItem_("18", "Ragda Pattice"),
            MenuItem_("19", "Momos"),
            MenuItem_("20", "Samosa Chaat"),
            MenuItem_("21", "Papdi Chaat"),
            MenuItem_("22", "Corn Chaat"),

            // Vegetarian Main Courses
            MenuItem_("23", "Paneer Butter Masala"),
            MenuItem_("24", "Chole Bhature"),
            MenuItem_("25", "Rajma"),
            MenuItem_("26", "Aloo Gobi"),
            MenuItem_("27", "Baingan Bharta"),
            MenuItem_("28", "Palak Paneer"),
            MenuItem_("29", "Kadhai Paneer"),
            MenuItem_("30", "Dal Makhani"),
            MenuItem_("31", "Malai Kofta"),
            MenuItem_("32", "Bhindi Masala"),
            MenuItem_("33", "Aloo Matar"),
            MenuItem_("34", "Mutter Paneer"),
            MenuItem_("35", "Vegetable Jalfrezi"),
            MenuItem_("36", "Dum Aloo"),
            MenuItem_("37", "Chana Masala"),
            MenuItem_("38", "Paneer Tikka Masala"),
            MenuItem_("39", "Aloo Baingan"),
            MenuItem_("40", "Aloo Palak"),
            MenuItem_("41", "Aloo Jeera"),
            MenuItem_("42", "Gutti Vankaya Kura"),

            // Non-Vegetarian Main Courses
            MenuItem_("43", "Butter Chicken"),
            MenuItem_("44", "Chicken Tikka Masala"),
            MenuItem_("45", "Tandoori Chicken"),
            MenuItem_("46", "Rogan Josh"),
            MenuItem_("47", "Chicken Curry"),
            MenuItem_("48", "Lamb Vindaloo"),
            MenuItem_("49", "Fish Curry"),
            MenuItem_("50", "Goan Prawn Curry"),
            MenuItem_("51", "Nihari"),
            MenuItem_("52", "Korma"),
            MenuItem_("53", "Chicken Chettinad"),
            MenuItem_("54", "Biryani (Chicken/Mutton)"),
            MenuItem_("55", "Keema"),
            MenuItem_("56", "Mutton Curry"),
            MenuItem_("57", "Murgh Malaiwala"),
            MenuItem_("58", "Egg Curry"),
            MenuItem_("59", "Fish Fry"),
            MenuItem_("60", "Prawn Masala"),
            MenuItem_("61", "Mutton Keema"),

            // Breads
            MenuItem_("62", "Naan"),
            MenuItem_("63", "Roti"),
            MenuItem_("64", "Paratha"),
            MenuItem_("65", "Puri"),
            MenuItem_("66", "Bhatura"),
            MenuItem_("67", "Kulcha"),
            MenuItem_("68", "Lachha Paratha"),
            MenuItem_("69", "Thepla"),
            MenuItem_("70", "Missi Roti"),
            MenuItem_("71", "Makki di Roti"),
            MenuItem_("72", "Roomali Roti"),
            MenuItem_("73", "Tandoori Roti"),

            // Rice Dishes
            MenuItem_("74", "Biryani"),
            MenuItem_("75", "Pulao"),
            MenuItem_("76", "Jeera Rice"),
            MenuItem_("77", "Khichdi"),
            MenuItem_("78", "Curd Rice"),
            MenuItem_("79", "Lemon Rice"),
            MenuItem_("80", "Tamarind Rice"),
            MenuItem_("81", "Peas Pulao"),
            MenuItem_("82", "Coconut Rice"),
            MenuItem_("83", "Kashmiri Pulao"),
            MenuItem_("84", "Bisi Bele Bath"),
            MenuItem_("85", "Vangi Bath"),
            MenuItem_("86", "Ghee Rice"),

            // Desserts
            MenuItem_("87", "Gulab Jamun"),
            MenuItem_("88", "Jalebi"),
            MenuItem_("89", "Kheer"),
            MenuItem_("90", "Rasgulla"),
            MenuItem_("91", "Rasmalai"),
            MenuItem_("92", "Barfi"),
            MenuItem_("93", "Gajar ka Halwa"),
            MenuItem_("94", "Peda"),
            MenuItem_("95", "Ladoo"),
            MenuItem_("96", "Sandesh"),
            MenuItem_("97", "Kulfi"),
            MenuItem_("98", "Phirni"),
            MenuItem_("99", "Shrikhand"),
            MenuItem_("100", "Mysore Pak"),
            MenuItem_("101", "Malpua"),
            MenuItem_("102", "Rabri"),
            MenuItem_("103", "Moong Dal Halwa"),
            MenuItem_("104", "Kesari Bath"),
            MenuItem_("105", "Chikki"),
            MenuItem_("106", "Sohan Halwa"),
            MenuItem_("107", "Basundi"),

            // Beverages
            MenuItem_("108", "Lassi"),
            MenuItem_("109", "Masala Chai"),
            MenuItem_("110", "Chaach"),
            MenuItem_("111", "Nimbu Pani"),
            MenuItem_("112", "Jaljeera"),
            MenuItem_("113", "Thandai"),
            MenuItem_("114", "Filter Coffee"),
            MenuItem_("115", "Badam Milk"),
            MenuItem_("116", "Aam Panna"),
            MenuItem_("117", "Sharbat"),
            MenuItem_("118", "Coconut Water"),
            MenuItem_("119", "Falooda"),
            MenuItem_("120", "Solkadhi"),
            MenuItem_("121", "Sugarcane Juice"),
            MenuItem_("122", "Buttermilk"),
            MenuItem_("123", "Saffron Milk")
        )
//        Standard Veg Package
//        Deluxe Veg Package
//        Standard Non-Veg Package
//                Deluxe Non-Veg Package
//                Family Veg Package
//        Family Non-Veg Package
//                Office Lunch Pack
//        Office Dinner Pack
//        Healthy Veg Pack
//        Healthy Non-Veg Pack

    }
}