package com.example.easy_tiffin.ui.food_menu_creator_

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.EditText
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.easy_tiffin.R
import com.google.android.material.floatingactionbutton.FloatingActionButton

class food_menu_creator : AppCompatActivity() {
    private lateinit var addsBtn: FloatingActionButton
    private lateinit var recv: RecyclerView
    private lateinit var userList: ArrayList<UserData>
    private lateinit var userAdapter: UserAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.create_menu_listiew)
        /**set List*/
        userList = ArrayList()
        /**set find Id*/
        addsBtn = findViewById(R.id.addingBtn)
        recv = findViewById(R.id.mRecycler)
        /**set Adapter*/
        userAdapter = UserAdapter(this, userList)
        /**setRecycler view Adapter*/
        recv.layoutManager = LinearLayoutManager(this)
        recv.adapter = userAdapter
        /**set Dialog*/
        addsBtn.setOnClickListener { addInfo() }

    }

    private fun addInfo() {
        val inflter = LayoutInflater.from(this)
        val v = inflter.inflate(R.layout.activity_menu_creater, null)

        /**set view*/
        val userName = v.findViewById<EditText>(R.id.userName)

        val spice_level = v.findViewById<RadioGroup>(R.id.spiceLevelGroup)


        val addDialog = AlertDialog.Builder(this, R.style.AlertDialogTheme)

        addDialog.setView(v)
        addDialog.setPositiveButton("Ok") { dialog, _ ->
            if (userName.text.toString().isNotEmpty()) {
                val item = userName.text.toString()

                val selectedSpiceLevelId = spice_level.checkedRadioButtonId
                val selectedRadioButton = findViewById<RadioButton>(selectedSpiceLevelId)
                val selectedSpiceLevel = selectedRadioButton?.text.toString()
                userList.add(UserData(item, "quantity",selectedSpiceLevel))
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