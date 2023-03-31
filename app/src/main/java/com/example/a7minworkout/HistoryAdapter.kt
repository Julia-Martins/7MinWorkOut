package com.example.a7minworkout

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.a7minworkout.databinding.ItemHistoryRowBinding

class HistoryAdapter(private val items:ArrayList<String>,
                  private val deleteListener:(date:String) -> Unit
): RecyclerView.Adapter<HistoryAdapter.ViewHolder>(){
    class ViewHolder(binding: ItemHistoryRowBinding): RecyclerView.ViewHolder(binding.root) {
        val mainHistoryItem = binding.mainHistoryItem
        val num = binding.txtPosition
        val date = binding.txtDate
        val imgTrash = binding.imgTrash
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemHistoryRowBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        ))
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val date = items[position]
        holder.num.text = (position + 1).toString()
        holder.date.text = date

        if(position % 2 == 0){
            holder.mainHistoryItem.setBackgroundColor(
                ContextCompat.getColor(holder.itemView.context,
                R.color.light_blue))

            holder.num.setTextColor(
                ContextCompat.getColor(holder.itemView.context,
                R.color.white))

            holder.date.setTextColor(
                ContextCompat.getColor(holder.itemView.context,
                R.color.white))

            holder.imgTrash.setImageDrawable(ContextCompat.getDrawable(holder.itemView.context,
                R.drawable.trash_background_blue))
        }else{
            holder.mainHistoryItem.setBackgroundColor(ContextCompat.getColor(holder.itemView.context,
                R.color.white))

            holder.num.setTextColor(ContextCompat.getColor(holder.itemView.context,
                R.color.light_blue))

            holder.date.setTextColor(ContextCompat.getColor(holder.itemView.context,
                R.color.light_blue))

            holder.imgTrash.setImageDrawable(ContextCompat.getDrawable(holder.itemView.context,
                R.drawable.trash_background_white))
        }

        holder.imgTrash.setOnClickListener {
            deleteListener.invoke(date)
        }
    }
}