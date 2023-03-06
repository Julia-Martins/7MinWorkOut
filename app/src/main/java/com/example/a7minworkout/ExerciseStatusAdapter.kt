package com.example.a7minworkout

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.a7minworkout.databinding.ItemExerciseStatusBinding

class ExerciseStatusAdapter(private val items: ArrayList<ExerciseModel>):
    RecyclerView.Adapter<ExerciseStatusAdapter.ViewHolder>() {

    class ViewHolder(binding: ItemExerciseStatusBinding):
        RecyclerView.ViewHolder(binding.root){
            val txtItem = binding.txtItem
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemExerciseStatusBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        ))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val model: ExerciseModel = items[position]

        holder.txtItem.text = model.getId().toString()

        when{
            model.getIsSelected() ->{
                holder.txtItem.background =
                    ContextCompat.getDrawable(holder.itemView.context,
                    R.drawable.item_circular_color_back_blue_background)

                holder.txtItem.setTextColor(Color.parseColor("#022182"))
            }
            model.getIsCompleted() -> {
                holder.txtItem.background =
                    ContextCompat.getDrawable(holder.itemView.context,
                        R.drawable.item_circular_color_blue_background_completed)

                holder.txtItem.setTextColor(Color.parseColor("#FFFFFF"))
            }
            else ->{
                holder.txtItem.background =
                    ContextCompat.getDrawable(holder.itemView.context,
                        R.drawable.item_circular_color_blue_background)

                holder.txtItem.setTextColor(Color.parseColor("#FFFFFF"))
            }
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }
}