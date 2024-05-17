package com.example.easy_tiffin.ui.ui.home.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.easy_tiffin.ui.ui.home.fragments_user_order.order_prepared
import com.example.easy_tiffin.ui.ui.home.fragments_user_order.order_preparing

private const val NUM_TABS = 2

public class ViewPagerAdapter_order(fragmentManager: FragmentManager, lifecycle: Lifecycle) :
        FragmentStateAdapter(fragmentManager, lifecycle) {

    override fun getItemCount(): Int {
        return NUM_TABS
    }

    override fun createFragment(position: Int): Fragment {
        when (position) {
            0 -> return order_preparing()
            1 -> return order_prepared()
        }
        return order_preparing()
    }
}