package com.kapibarabanka.alcodiary.calendar

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import com.kapibarabanka.alcodiary.R

class DrinkInEventListAdapter(private val holderLayoutId: Int, private val list: List<DrinkInEvent>)
    : RecyclerView.Adapter<DrinkInEventViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DrinkInEventViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return DrinkInEventViewHolder(holderLayoutId, inflater, parent)
    }

    override fun onBindViewHolder(holder: DrinkInEventViewHolder, position: Int) {
        val item = list[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int = list.size
}

class DrinkInEventViewHolder(holderLayoutId: Int, inflater: LayoutInflater, parent: ViewGroup) :
        RecyclerView.ViewHolder(inflater.inflate(holderLayoutId, parent, false)){

    private var typeView: TextView? = null
    private var nameView: TextView? = null
    private var amountView: TextView? = null

    init {
        typeView = itemView.findViewById(R.id.list_drink_type)
        nameView = itemView.findViewById(R.id.list_drink_name)
        amountView = itemView.findViewById(R.id.list_drink_amount)
    }

    fun bind(item: DrinkInEvent) {
        typeView?.text = item.drink.type.toString()
        nameView?.text = item.drink.name
        amountView?.text = item.amount.toString()
    }
}