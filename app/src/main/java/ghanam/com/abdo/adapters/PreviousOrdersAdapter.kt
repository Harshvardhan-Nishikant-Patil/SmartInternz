package ghanam.com.abdo.adapters


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

import ghanam.com.abdo.databinding.PreviousOrderCardBinding
import ghanam.com.abdo.dataclasses.PreviousOrder
import java.text.SimpleDateFormat



class PreviousOrdersAdapter(  private val items: MutableList<PreviousOrder>) : RecyclerView.Adapter<PreviousOrdersAdapter.TaskViewHolder>() {
    class TaskViewHolder(val binding: PreviousOrderCardBinding) : RecyclerView.ViewHolder(binding.root)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        return TaskViewHolder(PreviousOrderCardBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }


    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        val currentItem = items[position]
        holder.binding.apply {
            totalTxt.text=currentItem.total.toString().format("%.2f")
            val parser = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")
            val dateFormatter = SimpleDateFormat("dd/MM/yyyy")
            val timeFormatter = SimpleDateFormat("HH:mm")
            val date = dateFormatter.format(parser.parse(currentItem.date.toString()))
            val time = timeFormatter.format(parser.parse(currentItem.date.toString()))
            dateText.text=date
            timeText.text=time
        }
    }


}