package com.example.uidesigns.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.CheckBox
import android.widget.TextView
import com.example.uidesigns.R

class SpinnerAdapterCheckbox(parentContext: Context,data:List<String>): BaseAdapter() {
    private var context:Context = parentContext
    private var item:List<String> = data
    private val checkedPositions = mutableSetOf<Int>()

    override fun getCount(): Int {
        return item.size
    }

    override fun getItem(position: Int): Any {
        return item[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val rootView = LayoutInflater.from(context)
            .inflate(R.layout.item_spinner,parent,false)

        val textName:TextView = rootView.findViewById(R.id.textViewDistributor)
        val checkBox:CheckBox = rootView.findViewById(R.id.checkboxDistributor)

        textName.text = item[position]
        checkBox.isChecked = checkedPositions.contains(position)
        checkBox.setOnCheckedChangeListener { buttonView, isChecked ->
            if(isChecked){
                checkedPositions.add(position)
            }else{
                checkedPositions.remove(position)
            }
        }

        return rootView
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val rootView = LayoutInflater.from(context)
            .inflate(R.layout.item_spinner,parent,false)

        val textName:TextView = rootView.findViewById(R.id.textViewDistributor)
        val checkBox:CheckBox = rootView.findViewById(R.id.checkboxDistributor)

        textName.text = item[position]
        checkBox.isChecked = checkedPositions.contains(position)
        checkBox.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                checkedPositions.add(position)
            } else {
                checkedPositions.remove(position)
            }
        }

        return rootView
    }

    fun getCheckedItems():List<String>{
        return checkedPositions.map { item[it] }
    }

}