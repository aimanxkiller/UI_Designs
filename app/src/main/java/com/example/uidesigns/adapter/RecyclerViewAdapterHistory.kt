package com.example.uidesigns.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.uidesigns.R
import com.example.uidesigns.model.TaskModel

class RecyclerViewAdapterHistory: RecyclerView.Adapter<RecyclerViewAdapterHistory.MyViewHolder>() {

    private var items = ArrayList<TaskModel>()

    fun getData(data:ArrayList<TaskModel>){
        this.items = data
    }

    class MyViewHolder(view: View):RecyclerView.ViewHolder(view) {
        private val title:TextView= itemView.findViewById(R.id.history_title)

        fun bind(data:ArrayList<TaskModel>,position: Int){
            title.text = data[position].task_title
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val inflater = LayoutInflater.from(parent.context)
            .inflate(R.layout.rows_item_history,parent,false)

        return MyViewHolder(inflater)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(items,position)
    }

    override fun getItemCount(): Int {
        return items.size
    }


}