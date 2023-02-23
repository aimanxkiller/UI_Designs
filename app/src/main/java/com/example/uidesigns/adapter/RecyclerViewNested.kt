package com.example.uidesigns.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.uidesigns.R
import com.example.uidesigns.model.TaskList
import com.example.uidesigns.model.TaskModel

class RecyclerViewNested: RecyclerView.Adapter<RecyclerViewNested.MyViewHolder>()  {

    private var items = ArrayList<TaskList>()

    fun getData(data:ArrayList<TaskList>){
        this.items = data
    }

    class MyViewHolder(view:View):RecyclerView.ViewHolder(view) {
        private val titleNested: TextView = itemView.findViewById(R.id.nested_Title)
        private val recyclerNested: RecyclerView = itemView.findViewById(R.id.recycler_nested)

        fun bind(data:TaskList){
            when(data.type){
                1->{
                    titleNested.text = "Completed Inspections"
                }
                2->{
                    titleNested.text = "Incomplete Inspections"
                }
            }

            lateinit var recyclerViewAdapter:RecyclerViewAdapter

            recyclerNested.apply {
                layoutManager = LinearLayoutManager(context)
                recyclerViewAdapter = RecyclerViewAdapter()
                adapter = recyclerViewAdapter
                recyclerViewAdapter.getData(data.list)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val inflater = LayoutInflater.from(parent.context)
            .inflate(R.layout.rows_item_nested,parent,false)
        return MyViewHolder(inflater)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val mainHolder = holder.itemView

        holder.bind(items[position])






    }

    override fun getItemCount(): Int {
        return items.size
    }
}