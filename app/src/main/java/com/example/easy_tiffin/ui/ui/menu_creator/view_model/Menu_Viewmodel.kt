package com.example.easy_tiffin.ui.ui.menu_creator.view_model

import android.app.TimePickerDialog
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.easy_tiffin.R
import com.example.easy_tiffin.ui.food_menu_creator_.UserData

import com.example.easy_tiffin.ui.food_menu_creator_.UserAdapter

import com.example.easy_tiffin.ui.ui.menu_creator.menu_repo.menu_repo_
import com.example.easy_tiffin.ui.ui.menu_creator.model.MenuItem_
import kotlinx.coroutines.launch
import java.util.Calendar

class Menu_Viewmodel(private val repository: menu_repo_) : ViewModel() {

    private lateinit var Item_name: AutoCompleteTextView
    private lateinit var quantity: EditText
    private lateinit var Recycler_: RecyclerView
    private lateinit var userList: ArrayList<UserData>
    private lateinit var food_menu: ArrayList<MenuItem_>
    private lateinit var userAdapter: UserAdapter
    private lateinit var dialog: AlertDialog
    private var foodItemss: Array<String> = arrayOf()
    private val _menuItems = MutableLiveData<List<MenuItem_>>()
    val menuItems: LiveData<List<MenuItem_>> get() = _menuItems

    init {
        viewModelScope.launch {
            loadMenuItems()
        }
    }

    suspend fun loadMenuItems() {
        _menuItems.value = repository.getMenuItems()
    }


    //private val repository = UserRepository()
    //  val users: LiveData<List<UserData>> = repository.getUsers()

    public fun addInfo(context: Context) {
        userList = ArrayList()
        userAdapter = UserAdapter(context, userList)
        val inflter = LayoutInflater.from(context)
        val v = inflter.inflate(R.layout.add_item, null)

        /**set view*/
        Item_name = v.findViewById<AutoCompleteTextView>(R.id.itemNameAutoComplete)
        quantity = v.findViewById<EditText>(R.id.userNo)
        val Done_ = v.findViewById<ImageButton>(R.id.doneButton)
        Recycler_ = v.findViewById<
                RecyclerView>(R.id.mRecycler)
        val buttonStartTime = v.findViewById<Button>(R.id.buttonStartTime)
        val buttonEndTime = v.findViewById<Button>(R.id.buttonEndTime)
        menuItems.observe(context as LifecycleOwner) { menuItems ->
            foodItemss = menuItems.map { it.name }.toTypedArray()
            // Update your adapter with the new data
            //menuItems.

            //userAdapter.notifyDataSetChanged()
        }


        val addDialog = AlertDialog.Builder(context, R.style.AlertDialogTheme)

        addDialog.setCancelable(false)

        addDialog.setView(v)


        val autoAdapter =
            ArrayAdapter(context, android.R.layout.simple_dropdown_item_1line, foodItemss)
        Item_name.setAdapter(autoAdapter)


        Done_.setOnClickListener(View.OnClickListener {
            if (Item_name.text.toString().isNotEmpty()) {
                showDaySelectorDialog(Item_name.text.toString(), quantity.text.toString(), context)
                val imm =
                    context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(it.windowToken, 0)


            }



        })
        buttonStartTime.setOnClickListener {
            showTimePickerDialog(context) { selectedTime ->
                buttonStartTime.text = selectedTime
            }
        }

        // Show TimePickerDialog when buttonEndTime is clicked
        buttonEndTime.setOnClickListener {
            showTimePickerDialog(context) { selectedTime ->
                buttonEndTime.text = selectedTime
            }
        }

        addDialog.setPositiveButton("Add Menu") { dialog, _ ->

            if (userList.isNotEmpty()) {
                val item = Item_name.text.toString()

                userList.add(UserData(item, "quantity", "selectedSpiceLevel"))

                userAdapter.notifyDataSetChanged()
                dialog.dismiss()
                val userDataString = userList.joinToString(separator = "\n") { userData ->
                    "${userData.quantity} ${userData.item_name} ${userData.spice_level}"
                }
                Log.d("UserDataList", userDataString)
            }else{

            }
        }
        addDialog.setNegativeButton("Cancel") { dialog, _ ->
            dialog.dismiss()
        }
        dialog = addDialog.create()


//        dialog.setOnShowListener {
//            val addButton = dialog.getButton(AlertDialog.BUTTON_POSITIVE)
//            addButton.isEnabled = false
//        }
        //addDialog.create()

        dialog.show()
    }

    private fun showDaySelectorDialog(Item_name_: String, Quantity: String, context: Context) {
        val inflter = LayoutInflater.from(context)
        val v = inflter.inflate(R.layout.dialog_day_selector, null)

        val addDialog = AlertDialog.Builder(context, R.style.AlertDialogTheme)

        addDialog.setCancelable(false)

        addDialog.setView(v)



        addDialog.setPositiveButton("Ok") { dialog, _ ->


            userList.add(UserData(Item_name_, Quantity, "selectedSpiceLevel"))
            userAdapter.notifyDataSetChanged()

            Item_name.setText("");
            quantity.setText("");
            // Hide the keyboard


            userAdapter = UserAdapter(context, userList)
            /**setRecycler view Adapter*/
            Recycler_.layoutManager = LinearLayoutManager(context)
            (Recycler_.layoutManager as LinearLayoutManager).reverseLayout = true
            Recycler_.adapter = userAdapter




        }
        addDialog.setNegativeButton("Cancel") { dialog, _ ->
            dialog.dismiss()
        }

        addDialog.show()

    }

    private fun showTimePickerDialog(context: Context, onTimeSelected: (String) -> Unit) {
        val calendar = Calendar.getInstance()
        val hour = calendar.get(Calendar.HOUR_OF_DAY)
        val minute = calendar.get(Calendar.MINUTE)

        val timePickerDialog = TimePickerDialog(context, { _, selectedHour, selectedMinute ->
            val isPM = selectedHour >= 12
            val hourIn12Format = if (selectedHour % 12 == 0) 12 else selectedHour % 12
            val time = String.format(
                "%02d:%02d %s",
                hourIn12Format,
                selectedMinute,
                if (isPM) "PM" else "AM"
            )
            onTimeSelected(time)
        }, hour, minute, false) // Set is24HourView to false for AM/PM format

        timePickerDialog.show()
    }


}