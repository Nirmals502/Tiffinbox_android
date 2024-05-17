package com.example.easy_tiffin.ui.ui.home.adapter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.easy_tiffin.ui.ui.home.model.MenuItem
import com.example.easy_tiffin.ui.ui.home.fragment_menu.MenuListFragment
import com.example.easy_tiffin.ui.ui.home.model.MenuType

class MenuPagerAdapter(fragment: Fragment,private var selectedDay_: MenuType) : FragmentStateAdapter(fragment) {
    private val fragments = listOf(
        MenuListFragment.newInstance(MenuType.Sun),
        MenuListFragment.newInstance(MenuType.Mon),
        MenuListFragment.newInstance(MenuType.Tue),
        MenuListFragment.newInstance(MenuType.Wed),
        MenuListFragment.newInstance(MenuType.Thu),
        MenuListFragment.newInstance(MenuType.Fri),
        MenuListFragment.newInstance(MenuType.Sat)
    )

    override fun getItemCount(): Int = fragments.size

    override fun createFragment(position: Int): Fragment {
        return fragments[position]
    }


    fun setMenuItems(items: List<MenuItem>) {
        val filteredItems = items.filter { it.type == selectedDay_ }
        fragments.forEach { fragment ->
            (fragment as? MenuListFragment)?.setMenuItems(filteredItems)
        }
    }
    fun updatemenutype(items: List<MenuItem>, selectedDay: MenuType) {

        val filteredItems = items.filter { it.type == selectedDay }
        fragments.forEach { fragment ->
            (fragment as? MenuListFragment)?.setMenuItems(filteredItems)
        }


    }

}
