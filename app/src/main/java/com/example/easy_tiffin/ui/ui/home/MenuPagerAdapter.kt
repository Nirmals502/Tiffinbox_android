package com.example.easy_tiffin.ui.ui.home

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class MenuPagerAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {
    private val fragments = listOf(
        MenuListFragment.newInstance(MenuType.ONE_TIME),
        MenuListFragment.newInstance(MenuType.WEEKLY),
        MenuListFragment.newInstance(MenuType.MONTHLY)
    )

    override fun getItemCount(): Int = fragments.size

    override fun createFragment(position: Int): Fragment {
        return fragments[position]
    }

    fun setMenuItems(items: List<MenuItem>) {
        fragments.forEach { fragment ->
            (fragment as? MenuListFragment)?.setMenuItems(items)
        }
    }
}
