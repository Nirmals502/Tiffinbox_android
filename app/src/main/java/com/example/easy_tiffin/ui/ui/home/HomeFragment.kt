package com.example.easy_tiffin.ui.ui.home


import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView

import androidx.fragment.app.Fragment

import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.easy_tiffin.databinding.FragmentHomeBinding
import com.example.easy_tiffin.DayOfWeekUtil.DayOfWeekUtil
import com.example.easy_tiffin.ui.ui.home.adapter.MenuPagerAdapter
import com.example.easy_tiffin.ui.ui.home.adapter.ViewPagerAdapter_order
import com.example.easy_tiffin.ui.ui.home.factory.ViewModelFactory
import com.example.easy_tiffin.ui.ui.home.model.MenuType
import com.example.easy_tiffin.ui.ui.home.repository.home_Repository
import com.example.easy_tiffin.ui.ui.home.view_model.HomeViewModel
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator


class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private lateinit var viewModel: HomeViewModel
    private lateinit var adapter: MenuPagerAdapter
    private var hasDataLoaded = false
    private var selectedDay: MenuType? = null // Store the selected day

    val animalsArray = arrayOf(
        "Preparing",
        "Prepared"
    )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {


        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val repository = home_Repository()
        viewModel =
            ViewModelProvider(this, ViewModelFactory(repository)).get(HomeViewModel::class.java)

        adapter = MenuPagerAdapter(this, DayOfWeekUtil.getCurrentDayOfWeek())
        set()
        setupMenuViewPager()

        val viewPager_order = binding.ordersViewPager
        val tabLayout_order = binding.ordersTabLayout

        val adapter_order = ViewPagerAdapter_order(childFragmentManager, lifecycle)
        viewPager_order.adapter = adapter_order

        TabLayoutMediator(tabLayout_order, viewPager_order) { tab, position ->
            tab.text = animalsArray[position]
        }.attach()

        val viewPager: ViewPager2 = binding.viewPager
        val leftarrow: ImageView = binding.leftArrow
        val tabLayout: TabLayout = binding.tabLayout
        val rightarrow: ImageView = binding.rightArrow


        // Center the TabLayout indicators


        leftarrow.setOnClickListener {
            if (viewPager.currentItem > 0) {
                viewPager.currentItem = viewPager.currentItem - 1
            }
        }

        rightarrow.setOnClickListener {
            if (viewPager.currentItem < adapter.itemCount - 1) {
                viewPager.currentItem = viewPager.currentItem + 1
            }
        }

        // Set text color for unselected tabs


        return root
    }

    private fun setupMenuViewPager() {

        // val adapter = MenuPagerAdapter(this, DayOfWeekUtil.getCurrentDayOfWeek())
        binding.viewPager.adapter = adapter

        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            tab.text = when (position) {
                0 -> "Mon"
                1 -> "Tue"
                2 -> "Wed"
                3 -> "Thu"
                4 -> "Fri"
                5 -> "Sat"
                else -> "Sun"
            }
        }.attach()

        binding.tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                tab?.let { selectedTab ->
                    // Get the text of the selected tab (representing the day)
                    // Get the text of the selected tab (representing the day)
                    val selectedDay = selectedTab.text.toString()
                    Log.d("TAG", "$selectedDay")

                    // Get the MenuType corresponding to the selected day
                    val menuType = when (selectedDay) {
                        "Sun" -> MenuType.Sun
                        "Mon" -> MenuType.Mon
                        "Tue" -> MenuType.Tue
                        "Wed" -> MenuType.Wed
                        "Thu" -> MenuType.Thu
                        "Fri" -> MenuType.Fri
                        "Sat" -> MenuType.Sat
                        else -> MenuType.Sun // Default to Sunday if unexpected day
                    }
                    viewModel.menuItems.observe(viewLifecycleOwner) { items ->
                        adapter.updatemenutype(items, menuType)
                    }
                    //viewModel.loadMenuItems(menuType)
                    //val adapter = MenuPagerAdapter(this@HomeFragment, menuType)
//                    if (hasDataLoaded) {
//                        viewModel.menuItems.removeObservers(viewLifecycleOwner)
//                    }
//                    //  viewModel.menuItems.removeObservers(viewLifecycleOwner)
//                    viewModel.menuItems.observe(viewLifecycleOwner) { items ->
//                        //adapter.setMenuItems(items)
//                        //adapter.submitList(emptyList())
//                        // adapter.updatemenutype(items,menuType)
//                        adapter.updatemenutype(viewModel.menuItems.value ?: emptyList(), menuType)
//
//                    }


                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
                // Unused, but required to implement
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
                // Unused, but required to implement
            }
        })

        // viewModel.setMenuItems(sampleMenuItems)

    }

    fun set() {
        viewModel.menuItems.observe(viewLifecycleOwner) { items ->
            // adapter.setMenuItems(items)
            val dayType = DayOfWeekUtil.getCurrentDayOfWeek()
            val position = when (dayType) {
                MenuType.Mon -> 0
                MenuType.Tue -> 1
                MenuType.Wed -> 2
                MenuType.Thu -> 3
                MenuType.Fri -> 4
                MenuType.Sat -> 5
                MenuType.Sun -> 6
            }

            if (position != -1) {
                binding.tabLayout.getTabAt(1)?.select()
                binding.tabLayout.getTabAt(position)?.select()
            }
            adapter.updatemenutype(items, DayOfWeekUtil.getCurrentDayOfWeek())

        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putSerializable("selectedDay", selectedDay) // Save the selected day
    }

    override fun onResume() {
        super.onResume()

        parentFragmentManager.beginTransaction().detach(this).attach(this).commit()

    }


}