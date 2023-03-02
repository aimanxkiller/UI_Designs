package com.example.uidesigns.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.uidesigns.R

class RecyclerViewAdapterCheckbox(private val listener: AdapterListener): RecyclerView.Adapter<RecyclerViewAdapterCheckbox.MyViewHolder>()  {

    private lateinit var items:List<String>
    private var choices = mutableListOf<String>()

    fun getData(data: List<String>){
        this.items = data
    }

    class MyViewHolder(view: View):RecyclerView.ViewHolder(view) {

        private val distChoice:TextView = itemView.findViewById(R.id.distributor_choice)

        fun bind(data:List<String>, position: Int){
            distChoice.text = data[position]
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val inflater = LayoutInflater.from(parent.context)
            .inflate(R.layout.items_row_checkbox,parent,false)

        return MyViewHolder(inflater)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val buttonDone:Button = holder.itemView.findViewById(R.id.button_checkbox_done)
        val checkBox:CheckBox = holder.itemView.findViewById(R.id.checkBoxDist)

        if(position != items.size - 1){
            buttonDone.visibility = View.GONE
        }else{
            buttonDone.visibility = View.VISIBLE
        }

        //add or remove values when check or uncheck
        checkBox.setOnCheckedChangeListener { _ , isChecked ->
            if(isChecked){
                choices?.add(items[position])
                listener.onCheck(choices)
            }else{
                choices?.remove(items[position])
                listener.onCheck(choices)
            }
        }

        //do listener click button done to close dialog
        buttonDone.setOnClickListener {
            //close dialog listener here
            listener.distDone()
        }
        //binding values
        holder.bind(items,position)
    }

    override fun getItemCount(): Int {
        return items.size
    }


}