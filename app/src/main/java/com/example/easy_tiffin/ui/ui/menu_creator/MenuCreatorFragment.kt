package com.example.easy_tiffin.ui.ui.menu_creator

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.EditText
import android.widget.ImageButton
import androidx.appcompat.app.AlertDialog

import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.easy_tiffin.R
import com.example.easy_tiffin.ui.food_menu_creator_.UserAdapter
import com.example.easy_tiffin.ui.food_menu_creator_.UserData
import com.example.easy_tiffin.ui.ui.home.factory.ViewModelFactory
import com.example.easy_tiffin.ui.ui.home.repository.home_Repository
import com.example.easy_tiffin.ui.ui.home.view_model.HomeViewModel
import com.example.easy_tiffin.ui.ui.menu_creator.factory.menu_factory
import com.example.easy_tiffin.ui.ui.menu_creator.menu_repo.menu_repo
import com.example.easy_tiffin.ui.ui.menu_creator.menu_repo.menu_repo_
import com.example.easy_tiffin.ui.ui.menu_creator.view_model.Menu_Viewmodel
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MenuCreatorFragment() : Fragment() {
    private lateinit var addsBtn: FloatingActionButton
    private lateinit var recv: RecyclerView
    private lateinit var userList: ArrayList<UserData>
    private lateinit var userAdapter: UserAdapter
    private lateinit var Item_name: AutoCompleteTextView
    private lateinit var quantity: EditText
    private lateinit var viewModel: Menu_Viewmodel
    private lateinit var Recycler_: RecyclerView


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.create_menu_listiew, container, false)
        val repository = menu_repo()
        viewModel =
            ViewModelProvider(this, menu_factory(repository)).get(Menu_Viewmodel::class.java)
        // viewModel.addInfo(requireContext())

        /**set List*/
        userList = ArrayList()
        /**set find Id*/
        addsBtn = root.findViewById(R.id.addingBtn)
        recv = root.findViewById(R.id.mRecycler)
        /**set Adapter*/
        userAdapter = UserAdapter(requireContext(), userList)
        /**setRecycler view Adapter*/
        recv.layoutManager = LinearLayoutManager(requireContext())
        recv.adapter = userAdapter
        /**set Dialog*/
        addsBtn.setOnClickListener {
            //addInfo()
            viewModel.addInfo(requireContext())
        }

        return root
    }


}
