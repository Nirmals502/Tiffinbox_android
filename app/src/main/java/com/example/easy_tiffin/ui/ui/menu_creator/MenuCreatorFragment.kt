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
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.RadioButton
import android.widget.RadioGroup
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat.getSystemService

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
    private lateinit var Item_name: AutoCompleteTextView
    private lateinit var quantity: EditText
    private lateinit var Recycler_: RecyclerView


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
        Item_name = v.findViewById<AutoCompleteTextView>(R.id.itemNameAutoComplete)
        quantity = v.findViewById<EditText>(R.id.userNo)
        val Done_ = v.findViewById<ImageButton>(R.id.doneButton)
        Recycler_ = v.findViewById<
                RecyclerView>(R.id.mRecycler)

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


        Done_.setOnClickListener(View.OnClickListener {
            if (Item_name.text.toString().isNotEmpty()) {
                showDaySelectorDialog(Item_name.text.toString(), quantity.text.toString())
                val imm =
                    requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(it.windowToken, 0)

                // Recycler_.adapter = userAdapter
                //Recycler_.visibility = Vis
            }
        })
        addDialog.setPositiveButton("Add Menu") { dialog, _ ->
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

    private fun showDaySelectorDialog(Item_name_: String, Quantity: String) {
        val inflter = LayoutInflater.from(requireContext())
        val v = inflter.inflate(R.layout.dialog_day_selector, null)

        val addDialog = AlertDialog.Builder(requireContext(), R.style.AlertDialogTheme)

        addDialog.setCancelable(false)

        addDialog.setView(v)



        addDialog.setPositiveButton("Ok") { dialog, _ ->


            userList.add(UserData(Item_name_, Quantity, "selectedSpiceLevel"))
            userAdapter.notifyDataSetChanged()
            //dialog.dismiss()
            val userDataString = userList.joinToString(separator = "\n") { userData ->
                "${userData.quantity} ${userData.item_name} ${userData.spice_level}"
            }
            Log.d("UserDataList", userDataString)
            Item_name.setText("");
            quantity.setText("");
            // Hide the keyboard


            userAdapter = UserAdapter(requireContext(), userList)
            /**setRecycler view Adapter*/
            Recycler_.layoutManager = LinearLayoutManager(requireContext())
            (Recycler_.layoutManager as LinearLayoutManager).reverseLayout = true
            Recycler_.adapter = userAdapter

        }
        addDialog.setNegativeButton("Cancel") { dialog, _ ->
            dialog.dismiss()
        }

        addDialog.show()

    }

}
