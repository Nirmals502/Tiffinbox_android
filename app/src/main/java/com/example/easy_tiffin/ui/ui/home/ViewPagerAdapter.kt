package com.example.easy_tiffin.ui.ui.home

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.easy_tiffin.ui.ui.FirstFragment
import com.example.easy_tiffin.ui.ui.SecondFragment

class ViewPagerAdapter(private val fragmentManager: FragmentManager) : FragmentPagerAdapter(fragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    private val fragmentList = listOf(
        FirstFragment(),  // Your fragment for today's orders
        SecondFragment() // Your fragment for completed orders
    )

    private val fragmentTitleList = listOf(
        "Today's Orders",
        "Completed Orders"
    )

    override fun getItem(position: Int): Fragment {
        return fragmentList[position]
    }

    override fun getCount(): Int {
        return fragmentList.size
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return fragmentTitleList[position]
    }
}
