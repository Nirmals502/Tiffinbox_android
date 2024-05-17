package com.example.easy_tiffin.ui.ui.home.fragment_menu

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.easy_tiffin.databinding.FragmentMenuListBinding
import com.example.easy_tiffin.ui.ui.home.adapter.MenuAdapter
import com.example.easy_tiffin.ui.ui.home.model.MenuItem
import com.example.easy_tiffin.ui.ui.home.model.MenuType

class MenuListFragment : Fragment(), android.view.MenuItem.OnMenuItemClickListener {
    private lateinit var binding: FragmentMenuListBinding
    private lateinit var menuType: MenuType
    private val adapter = MenuAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        menuType = arguments?.getSerializable("MENU_TYPE") as MenuType
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMenuListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.recyclerView.layoutManager = LinearLayoutManager(context)
        binding.recyclerView.adapter = adapter

    }

    companion object {
        fun newInstance(menuType: MenuType): MenuListFragment {
            val fragment = MenuListFragment()
            val args = Bundle().apply {
                putSerializable("MENU_TYPE", menuType)
            }
            fragment.arguments = args
            return fragment
        }
    }

    fun setMenuItems(items: List<MenuItem>) {
        Log.d("Check Value", "Filtered items for : $items")
        //adapter.notifyDataSetChanged()
        //adapter.submitList(emptyList())
       // adapter.submitList(items)
        adapter.submitList(emptyList()) {
            adapter.submitList(items)
        }
        //adapter.submitList(emptyList())

    }

    fun clearMenuItems() {
        adapter.submitList(emptyList())
    }


    override fun onMenuItemClick(item: android.view.MenuItem): Boolean {
        TODO("Not yet implemented")
        println("Clicked item ID: $item")
    }
}
