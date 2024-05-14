package com.example.easy_tiffin.ui.ui.home


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView

import androidx.fragment.app.Fragment

import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.easy_tiffin.databinding.FragmentHomeBinding
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale


class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private lateinit var viewModel: HomeViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {


        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val repository = LocalMenuRepository()
        viewModel =
            ViewModelProvider(this, ViewModelFactory(repository)).get(HomeViewModel::class.java)

        setupMenuViewPager()

        val viewPager: ViewPager2 = binding.viewPager
        val leftarrow: ImageView = binding.leftArrow
        val tabLayout: TabLayout = binding.tabLayout
        val rightarrow: ImageView = binding.rightArrow

//        val adapter = ViewPagerAdapter()
//        viewPager.adapter = adapter
//
//        // Connect TabLayout with ViewPager2
//        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
//            // Customize the appearance of the dots
//            tab.text = null // Display dots instead of text
//            //tab.view.background = requireContext().getDrawable(R.drawable.tab_indicator)
//        }.attach()

        // Center the TabLayout indicators


        leftarrow.setOnClickListener {
            if (viewPager.currentItem > 0) {
                viewPager.currentItem = viewPager.currentItem - 1
            }
        }

//        rightarrow.setOnClickListener {
//            if (viewPager.currentItem < adapter.itemCount - 1) {
//                viewPager.currentItem = viewPager.currentItem + 1
//            }
//        }

        // Set text color for unselected tabs


        return root
    }

    private fun setupMenuViewPager() {
        val adapter = MenuPagerAdapter(this)
        binding.viewPager.adapter = adapter

        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            tab.text = when (position) {
                0 -> "One-Time"
                1 -> "Weekly"
                else -> "Monthly"
            }
        }.attach()

       // viewModel.setMenuItems(sampleMenuItems)
        viewModel.menuItems.observe(viewLifecycleOwner) { items ->
            adapter.setMenuItems(items)
        }
    }

    private fun getDayName(position: Int): String {
        val calendar = Calendar.getInstance()
        calendar.add(Calendar.DAY_OF_MONTH, position - 2)
        val sdf = SimpleDateFormat("EEEE", Locale.getDefault())
        return sdf.format(calendar.time)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}