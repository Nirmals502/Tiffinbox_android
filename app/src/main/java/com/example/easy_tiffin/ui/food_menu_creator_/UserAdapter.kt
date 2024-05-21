package com.example.easy_tiffin.ui.food_menu_creator_

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.easy_tiffin.R


class UserAdapter(val c: Context, val userList: ArrayList<UserData>) :
    RecyclerView.Adapter<UserAdapter.UserViewHolder>() {


    inner class UserViewHolder(val v: View) : RecyclerView.ViewHolder(v) {
        var name: TextView
        var mbNum: TextView
        var mMenus: ImageView

        init {
            name = v.findViewById<TextView>(R.id.mTitle)
            mbNum = v.findViewById<TextView>(R.id.mSubTitle)
            mMenus = v.findViewById(R.id.mMenus)
            mMenus.setOnClickListener { popupMenus(it) }
        }

        private fun popupMenus(v: View) {
            val position = userList[bindingAdapterPosition]
            val popupMenus = PopupMenu(c, v)
            popupMenus.inflate(R.menu.show_menu)
            popupMenus.setOnMenuItemClickListener {
                when (it.itemId) {
                    R.id.editText -> {
                        val v = LayoutInflater.from(c).inflate(R.layout.add_item, null)
                        val name = v.findViewById<EditText>(R.id.quantity_)
                        val number = v.findViewById<EditText>(R.id.userNo)
                        val editText_item: EditText =
                            v.findViewById(R.id.quantity_) // Assuming R.id.userName is the ID of your EditText

                        editText_item.setText(position.item_name)
                        val editText_quantity: EditText =
                            v.findViewById(R.id.userNo) // Assuming R.id.userName is the ID of your EditText

                        editText_quantity.setText(position.quantity)


                        position.quantity = number.text.toString()
                        AlertDialog.Builder(c, R.style.AlertDialogTheme)
                            .setView(v)
                            .setPositiveButton("Ok") { dialog, _ ->
                                position.item_name = name.text.toString()
                                position.quantity = number.text.toString()
                                notifyDataSetChanged()
                                dialog.dismiss()

                            }
                            .setNegativeButton("Cancel") { dialog, _ ->
                                dialog.dismiss()

                            }
                            .create()
                            .show()

                        true
                    }

                    R.id.delete -> {
                        /**set delete*/
                        AlertDialog.Builder(c, R.style.AlertDialogTheme)
                            .setTitle("Delete")
                            .setIcon(R.drawable.ic_warning)
                            .setMessage("Are you sure delete this Information")
                            .setPositiveButton("Yes") { dialog, _ ->
                                userList.removeAt(adapterPosition)
                                notifyDataSetChanged()
                                dialog.dismiss()
                            }
                            .setNegativeButton("No") { dialog, _ ->
                                dialog.dismiss()
                            }
                            .create()
                            .show()

                        true
                    }

                    else -> true
                }

            }
            popupMenus.show()
            val popup = PopupMenu::class.java.getDeclaredField("mPopup")
            popup.isAccessible = true
            val menu = popup.get(popupMenus)
            menu.javaClass.getDeclaredMethod("setForceShowIcon", Boolean::class.java)
                .invoke(menu, true)
        }
    }

    fun showDayPickerDialog() {
        val days =
            arrayOf(
                "Mon to Fri",
                "Mon to Sat",
                "Mon to Sun",
                "Monday",
                "Tuesday",
                "Wednesday",
                "Thursday",
                "Friday",
                "Saturday",
                "Sunday"
            )
        val selectedDays =
            booleanArrayOf(false, false, false, false, false, false, false, false, false, false)

        val builder = androidx.appcompat.app.AlertDialog.Builder(c)
        builder.setTitle("Select Days")
        builder.setMultiChoiceItems(days, selectedDays) { _, which, isChecked ->
            selectedDays[which] = isChecked
        }
        builder.setPositiveButton("OK") { _, _ ->
            val selectedDaysList = mutableListOf<String>()
            for (i in days.indices) {
                if (selectedDays[i]) {
                    selectedDaysList.add(days[i])
                }
            }
            // Update the TextView with the selected days

        }
        builder.setNegativeButton("Cancel", null)
        builder.show()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val v = inflater.inflate(R.layout.list_item, parent, false)
        return UserViewHolder(v)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val newList = userList[position]
        holder.name.text = newList.item_name
        holder.mbNum.text = newList.quantity
    }

    override fun getItemCount(): Int {
        return userList.size
    }
}