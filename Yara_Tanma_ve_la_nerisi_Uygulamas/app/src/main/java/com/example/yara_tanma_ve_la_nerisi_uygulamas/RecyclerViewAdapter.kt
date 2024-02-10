package com.example.yara_tanma_ve_la_nerisi_uygulamas

import android.util.Log
import android.util.SparseBooleanArray
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class RecyclerViewAdapter() : RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>() {

    var checkBoxStateArray = SparseBooleanArray()

    private var selectedTextList: ArrayList<String> = ArrayList()
    private val selectedPositions: ArrayList<Int> = ArrayList()

    fun size_esitleme(){
        Log.d("SAYIII ","$itemCount-1")
        for(i in 0..itemCount-1){
            selectedTextList.add("")
        }
        Log.d("SAYIII "," ${selectedTextList.size}")
    }

    fun getSelectedTextList(): ArrayList<String> {
        notifyDataSetChanged()
        return selectedTextList
    }

    private var newTextList: ArrayList<String> = ArrayList()

    fun updateTextList(newTextList: ArrayList<String>) {
        this.newTextList = newTextList

        notifyDataSetChanged()
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
    {
        var checkbox = itemView.findViewById<CheckBox>(R.id.checkbox)
        var textView = itemView.findViewById<TextView>(R.id.checkbox_text)



        init {

            itemView.setOnClickListener{
                val position = adapterPosition

                if(!checkBoxStateArray.get(adapterPosition,false))
                {
                    checkbox.isChecked=true
                    checkBoxStateArray.put(adapterPosition,true)
                    val selectedText = textView.text.toString()
                    Log.d("YAZII","$selectedText uuu  $adapterPosition bak  ${selectedTextList.get(adapterPosition).toString()}")

                    selectedTextList.set(adapterPosition,selectedText)


                }

                else{
                    checkbox.isChecked = false
                    checkBoxStateArray.put(absoluteAdapterPosition,false)
                    val selectedText = textView.text.toString()
                    selectedTextList.set(absoluteAdapterPosition,"")

                }
            }


            checkbox.setOnClickListener{
                if(!checkBoxStateArray.get(adapterPosition,false))
                {
                    checkbox.isChecked=true

                    checkBoxStateArray.put(adapterPosition,true)
                    val selectedText = textView.text.toString()
                    selectedTextList.set(adapterPosition,selectedText)

                }

                else{
                    checkbox.isChecked = false
                    checkBoxStateArray.put(absoluteAdapterPosition,false)
                    val selectedText = textView.text.toString()
                    selectedTextList.set(absoluteAdapterPosition,"")


                }

            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)
        val view = inflater.inflate(R.layout.checkbox_row,parent,false)

        return ViewHolder(view)
    }

    override fun getItemCount(): Int = newTextList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.checkbox.isChecked = selectedPositions.contains(position)
        if (!checkBoxStateArray.get(position, false)) {
            holder.checkbox.isChecked = false
            selectedPositions.remove(position)
        } else {
            holder.checkbox.isChecked = true
            selectedPositions.add(position)
        }

        if (position < newTextList.size) {
            holder.textView.text = newTextList[position]
        } else {

            holder.textView.text = ""
        }



    }
}

