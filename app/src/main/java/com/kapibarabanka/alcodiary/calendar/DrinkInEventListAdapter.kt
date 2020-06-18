package com.kapibarabanka.alcodiary.calendar

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.kapibarabanka.alcodiary.R
import com.kapibarabanka.alcodiary.data.iconMap

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
    private var ratingView: TextView? = null
    private var alcoholView: TextView? = null
    private var iconDrink: ImageView? = null

    init {
        typeView = itemView.findViewById(R.id.list_drink_type)
        nameView = itemView.findViewById(R.id.list_drink_name)
        amountView = itemView.findViewById(R.id.list_drink_amount)
        ratingView = itemView.findViewById(R.id.list_rating_amount)
        alcoholView = itemView.findViewById(R.id.list_alcohol_amount)
        iconDrink = itemView.findViewById(R.id.drink_icon_in_rv)
    }

    fun bind(item: DrinkInEvent) {
        typeView?.text = item.drink.type.toString()
        nameView?.text = item.drink.name
        amountView?.text = item.amount.toString()
        alcoholView?.text = item.drink.alcoholVolume.toString()
        ratingView?.text = item.drink.rating.toString()
        iconMap[item.drink.type.icon]?.let { iconDrink?.setImageResource(it) }
    }
}