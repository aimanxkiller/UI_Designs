package com.example.uidesigns.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.uidesigns.R
import com.example.uidesigns.model.TaskModel

class RecyclerViewAdapter:RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder>() {

    private var items = ArrayList<TaskModel>()

    fun getData(data:ArrayList<TaskModel>){
        this.items = data
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val inflater = LayoutInflater.from(parent.context)
            .inflate(R.layout.rows_item,parent,false)

        return MyViewHolder(inflater)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val mainHolder = holder.itemView
        val holderTitle = holder.title
        val holderType = holder.type

        when(items[position].type){
            1->{
                holderTitle.setTextColor(ContextCompat.getColor(mainHolder.context,R.color.daily_dark))
                holderType.setTextColor(ContextCompat.getColor(mainHolder.context,R.color.daily_dark))
                mainHolder.setBackgroundResource(R.drawable.background_daily)
                holderType.setBackgroundResource(R.drawable.background_daily)
            }
            2->{
                holderTitle.setTextColor(ContextCompat.getColor(mainHolder.context,R.color.weekly_dark))
                holderType.setTextColor(ContextCompat.getColor(mainHolder.context,R.color.weekly_dark))
                mainHolder.setBackgroundResource(R.drawable.background_weekly)
                holderType.setBackgroundResource(R.drawable.background_weekly)
            }
            3->{
                holderTitle.setTextColor(ContextCompat.getColor(mainHolder.context,R.color.monthly_dark))
                holderType.setTextColor(ContextCompat.getColor(mainHolder.context,R.color.monthly_dark))
                mainHolder.setBackgroundResource(R.drawable.background_monthly)
                holderType.setBackgroundResource(R.drawable.background_monthly)
            }
        }

        holder.bind(items[position])
    }

    override fun getItemCount(): Int {
        return items.size
    }

    class MyViewHolder(view:View):RecyclerView.ViewHolder(view) {
        val title:TextView = itemView.findViewById(R.id.textTaskTitle)
        val type:TextView = itemView.findViewById(R.id.textTaskType)

        fun bind(data:TaskModel){
            title.text = data.task_title
            when(data.type){
                1 ->{type.text = "Daily" }
                2 ->{type.text = "Weekly"}
                3 ->{type.text = "Monthly"}
            }
        }
    }

}