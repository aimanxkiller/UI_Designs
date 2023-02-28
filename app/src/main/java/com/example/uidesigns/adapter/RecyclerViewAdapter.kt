package com.example.uidesigns.adapter

import android.opengl.Visibility
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.uidesigns.R
import com.example.uidesigns.model.TaskList

interface AdapterListener{
    fun onClicked(index:Int,type:Int)
}

class RecyclerViewAdapter(private val listener: AdapterListener):RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder>() {

    private var items = ArrayList<TaskList>()

    fun getData(data:ArrayList<TaskList>){
        this.items = data
    }

    class MyViewHolder(view:View):RecyclerView.ViewHolder(view) {
        val title:TextView = itemView.findViewById(R.id.textTaskTitle)
        val type:TextView = itemView.findViewById(R.id.textTaskType)
        private val segment:TextView = itemView.findViewById(R.id.textView4)


        fun bind(data: ArrayList<TaskList>, position: Int){

            //split by segment
            when (position) {
                0 -> {
                    segment.text = "Completed Inspections"
                }
                (data[0].list.size) -> {
                    segment.text = "Incomplete Inspections"
                }
                else -> {
                    segment.visibility = View.GONE
                }
            }
            //setting by type of schedule
            if (position < data[0].list.size){
                title.text = data[0].list[position].task_title
                when(data[0].list[position].type){
                    1 ->{type.text = "Daily" }
                    2 ->{type.text = "Weekly"}
                    3 ->{type.text = "Monthly"}
                }
            }else{
                title.text = data[1].list[(position - data[0].list.size)].task_title
                when(data[1].list[(position - data[0].list.size)].type){
                    1 ->{type.text = "Daily" }
                    2 ->{type.text = "Weekly"}
                    3 ->{type.text = "Monthly"}
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val inflater = LayoutInflater.from(parent.context)
            .inflate(R.layout.rows_item,parent,false)

        return MyViewHolder(inflater)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val mainHolder = holder.itemView.findViewById<ConstraintLayout>(R.id.constraintLayout)
        val holderTaskTitle = holder.title
        val holderType = holder.type

        if (position < items[0].list.size){
            when(items[0].list[position].type){
                1->{
                    holderTaskTitle.setTextColor(ContextCompat.getColor(mainHolder.context,R.color.daily_dark))
                    holderType.setTextColor(ContextCompat.getColor(mainHolder.context,R.color.daily_dark))
                    mainHolder.setBackgroundResource(R.drawable.background_daily)
                    holderType.setBackgroundResource(R.drawable.background_daily)
                }
                2->{
                    holderTaskTitle.setTextColor(ContextCompat.getColor(mainHolder.context,R.color.weekly_dark))
                    holderType.setTextColor(ContextCompat.getColor(mainHolder.context,R.color.weekly_dark))
                    mainHolder.setBackgroundResource(R.drawable.background_weekly)
                    holderType.setBackgroundResource(R.drawable.background_weekly)
                }
                3->{
                    holderTaskTitle.setTextColor(ContextCompat.getColor(mainHolder.context,R.color.monthly_dark))
                    holderType.setTextColor(ContextCompat.getColor(mainHolder.context,R.color.monthly_dark))
                    mainHolder.setBackgroundResource(R.drawable.background_monthly)
                    holderType.setBackgroundResource(R.drawable.background_monthly)
                }
            }
        }else{
            when(items[1].list[(position - items[0].list.size)].type){
                1->{
                    holderTaskTitle.setTextColor(ContextCompat.getColor(mainHolder.context,R.color.daily_dark))
                    holderType.setTextColor(ContextCompat.getColor(mainHolder.context,R.color.daily_dark))
                    mainHolder.setBackgroundResource(R.drawable.background_daily)
                    holderType.setBackgroundResource(R.drawable.background_daily)
                }
                2->{
                    holderTaskTitle.setTextColor(ContextCompat.getColor(mainHolder.context,R.color.weekly_dark))
                    holderType.setTextColor(ContextCompat.getColor(mainHolder.context,R.color.weekly_dark))
                    mainHolder.setBackgroundResource(R.drawable.background_weekly)
                    holderType.setBackgroundResource(R.drawable.background_weekly)
                }
                3->{
                    holderTaskTitle.setTextColor(ContextCompat.getColor(mainHolder.context,R.color.monthly_dark))
                    holderType.setTextColor(ContextCompat.getColor(mainHolder.context,R.color.monthly_dark))
                    mainHolder.setBackgroundResource(R.drawable.background_monthly)
                    holderType.setBackgroundResource(R.drawable.background_monthly)
                }
            }
        }

        mainHolder.setOnClickListener {
            //Do stuff clicked here
            listener.onClicked(0,0)
        }
        holder.bind(items,position)
    }

    override fun getItemCount(): Int {
        var counter:Int = 0
        items.forEachIndexed { index, taskList ->
            counter += items[index].list.size
        }
        return counter
    }

}