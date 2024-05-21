package com.example.easy_tiffin.ui.ui.menu_creator

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
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
        val Item_name = v.findViewById<AutoCompleteTextView>(R.id.itemNameAutoComplete)

        val addDialog = AlertDialog.Builder(requireContext(), R.style.AlertDialogTheme)

        addDialog.setCancelable(false)

        addDialog.setView(v)
        val foodItems = arrayOf(
            // Appetizers
            "Samosa", "Pakora", "Aloo Tikki", "Dhokla", "Kachori", "Paneer Tikka",
            "Chicken Tikka", "Seekh Kebab", "Vada Pav", "Bhaji", "Fish Amritsari",
            "Prawn Koliwada", "Aloo Chaat", "Chaat", "Bhel Puri", "Pani Puri",
            "Dahi Puri", "Ragda Pattice", "Momos", "Samosa Chaat", "Papdi Chaat",
            "Corn Chaat",

            // Vegetarian Main Courses
            "Paneer Butter Masala", "Chole Bhature", "Rajma", "Aloo Gobi",
            "Baingan Bharta", "Palak Paneer", "Kadhai Paneer", "Dal Makhani",
            "Malai Kofta", "Bhindi Masala", "Aloo Matar", "Mutter Paneer",
            "Vegetable Jalfrezi", "Dum Aloo", "Chana Masala", "Paneer Tikka Masala",
            "Aloo Baingan", "Aloo Palak", "Aloo Jeera", "Gutti Vankaya Kura",

            // Non-Vegetarian Main Courses
            "Butter Chicken", "Chicken Tikka Masala", "Tandoori Chicken",
            "Rogan Josh", "Chicken Curry", "Lamb Vindaloo", "Fish Curry",
            "Goan Prawn Curry", "Nihari", "Korma", "Chicken Chettinad",
            "Biryani (Chicken/Mutton)", "Keema", "Mutton Curry", "Murgh Malaiwala",
            "Egg Curry", "Fish Fry", "Prawn Masala", "Mutton Keema",

            // Breads
            "Naan", "Roti", "Paratha", "Puri", "Bhatura", "Kulcha",
            "Lachha Paratha", "Thepla", "Missi Roti", "Makki di Roti",
            "Roomali Roti", "Tandoori Roti",

            // Rice Dishes
            "Biryani", "Pulao", "Jeera Rice", "Khichdi", "Curd Rice",
            "Lemon Rice", "Tamarind Rice", "Peas Pulao", "Coconut Rice",
            "Kashmiri Pulao", "Bisi Bele Bath", "Vangi Bath", "Ghee Rice",

            // Desserts
            "Gulab Jamun"
        )
        val autoAdapter =
            ArrayAdapter(requireContext(), android.R.layout.simple_dropdown_item_1line, foodItems)
        Item_name.setAdapter(autoAdapter)
        addDialog.setPositiveButton("Ok") { dialog, _ ->
            if (Item_name.text.toString().isNotEmpty()) {
                val item = Item_name.text.toString()

                userList.add(UserData(item, "quantity", "selectedSpiceLevel"))
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
