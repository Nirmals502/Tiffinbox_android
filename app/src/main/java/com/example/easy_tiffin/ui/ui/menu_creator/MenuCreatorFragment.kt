package com.example.easy_tiffin.ui.ui.menu_creator

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.RadioButton
import android.widget.RadioGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.easy_tiffin.R
import com.example.easy_tiffin.ui.food_menu_creator_.UserAdapter
import com.example.easy_tiffin.ui.food_menu_creator_.UserData
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MenuCreatorFragment : Fragment() {
    private lateinit var addsBtn: FloatingActionButton
    private lateinit var recv: RecyclerView
    private lateinit var userList: ArrayList<UserData>
    private lateinit var userAdapter: UserAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.create_menu_listiew, container, false)

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
        addsBtn.setOnClickListener { addInfo() }

        return root
    }

    private fun addInfo() {
        val inflter = LayoutInflater.from(requireContext())
        val v = inflter.inflate(R.layout.add_item, null)

        /**set view*/
        val userName = v.findViewById<EditText>(R.id.userName)
        val spice_level = v.findViewById<RadioGroup>(R.id.spiceLevelGroup)

        val addDialog = AlertDialog.Builder(requireContext(), R.style.AlertDialogTheme)

        addDialog.setView(v)
        addDialog.setPositiveButton("Ok") { dialog, _ ->
            if (userName.text.toString().isNotEmpty()) {
                val item = userName.text.toString()

                val selectedSpiceLevelId = spice_level.checkedRadioButtonId
                val selectedRadioButton = v.findViewById<RadioButton>(selectedSpiceLevelId)
                val selectedSpiceLevel = selectedRadioButton?.text.toString()
                userList.add(UserData(item, "quantity", selectedSpiceLevel))
                userAdapter.notifyDataSetChanged()
                dialog.dismiss()
                val userDataString = userList.joinToString(separator = "\n") { userData ->
                    "${userData.quantity} ${userData.item_name} ${userData.spice_level}"
                }
                Log.d("UserDataList", userDataString)
            }
        }
        addDialog.setNegativeButton("Cancel") { dialog, _ ->
            dialog.dismiss()
        }
        addDialog.create()
        addDialog.show()
    }
}
