package com.example.easy_tiffin.ui.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.easy_tiffin.R
import com.easy_tiffin.databinding.FragmentMenuListBinding

class MenuListFragment : Fragment() {
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
        adapter.submitList(items)
    }
}
