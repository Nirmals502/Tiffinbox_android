package com.example.easy_tiffin.ui.ui.home.adapter

// CustomPagerAdapter.kt
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.easy_tiffin.R
import java.text.SimpleDateFormat
import java.util.*

class ViewPagerAdapter : RecyclerView.Adapter<ViewPagerAdapter.ViewHolder>() {

    private val dates = getDates()
    private var selectedPosition = 5 // Initially selected position (current day)

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val titleTextView: TextView = itemView.findViewById(R.id.titleTextView)
        val textView: TextView = itemView.findViewById(R.id.textView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.fragment_first, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val date = dates[position]
        holder.titleTextView.text = getDayName(date)
        holder.textView.text = getDataForDate(date)

        // Highlight selected item
        holder.itemView.isSelected = position == selectedPosition
    }

    override fun getItemCount(): Int {
        return dates.size
    }

    private fun getDates(): List<String> {
        val calendar = Calendar.getInstance()
        val dates = mutableListOf<String>()
        val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())

        // Add dates for seven days, starting from yesterday to six days ahead
        for (i in -1..5) {
            calendar.add(Calendar.DAY_OF_MONTH, i)
            val date = sdf.format(calendar.time)
            dates.add(date)
            if (i == 0) {
                selectedPosition = 5 // Set selected position to today's index
            }
            calendar.add(Calendar.DAY_OF_MONTH, -i)
        }
        return dates
    }

    private fun getDayName(date: String): String {
        val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        val cal = Calendar.getInstance()
        cal.time = sdf.parse(date)!!
        return SimpleDateFormat("EEEE", Locale.getDefault()).format(cal.time)
    }

    private fun getDataForDate(date: String): String {
        // Your logic to fetch and return data for the given date
        return "Data for $date"
    }
}
