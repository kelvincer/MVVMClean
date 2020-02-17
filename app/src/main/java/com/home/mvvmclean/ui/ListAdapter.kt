package com.home.mvvmclean.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.home.mvvmclean.R
import kotlinx.android.synthetic.main.item_list.view.*

class ListAdapter(val items : List<String>): RecyclerView.Adapter<ListAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_list, parent, false))
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.txt.setText(items.get(position))
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val txt = view.item_text
    }
}