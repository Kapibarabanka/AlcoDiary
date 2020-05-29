package com.alcodiary.drink

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import com.alcodiary.R


class DrinkViewHolder(inflater: LayoutInflater, parent: ViewGroup) :
    RecyclerView.ViewHolder(inflater.inflate(R.layout.list_item_drink, parent, false)) {
    private var dNameView: TextView? = null
    private var dTypeView: TextView? = null
    private var dMarkView: TextView? = null

    init {
        dNameView = itemView.findViewById(R.id.list_drink_name)
        dTypeView = itemView.findViewById(R.id.list_drink_type)
        dMarkView = itemView.findViewById(R.id.list_drink_mark)
    }

    fun bind(drink: Drink) {
        dNameView?.text = drink.name
        dTypeView?.text = drink.type.name
        dMarkView?.text = drink.mark.toString()
    }

}