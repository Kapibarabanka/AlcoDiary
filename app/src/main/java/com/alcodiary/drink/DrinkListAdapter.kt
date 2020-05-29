package com.alcodiary.drink

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup

class DrinkListAdapter(private val list: List<Drink>)
    : RecyclerView.Adapter<DrinkViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DrinkViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return DrinkViewHolder(inflater, parent)
    }

    override fun onBindViewHolder(holder: DrinkViewHolder, position: Int) {
        val drink: Drink = list[position]
        holder.bind(drink)
    }

    override fun getItemCount(): Int = list.size
}