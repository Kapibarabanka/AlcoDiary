package com.kapibarabanka.alcodiary.drinks

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.kapibarabanka.alcodiary.R

class DrinksListAdapter(private val list: List<Drink>, private val onDrinkListener: OnDrinkListener)
    : RecyclerView.Adapter<DrinkViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DrinkViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return DrinkViewHolder(inflater, parent, onDrinkListener)
    }

    override fun onBindViewHolder(holder: DrinkViewHolder, position: Int) {
        val drink: Drink = list[position]
        holder.bind(drink)
    }

    override fun getItemCount(): Int = list.size
}

class DrinkViewHolder(inflater: LayoutInflater, parent: ViewGroup,
                      private val onDrinkListener: OnDrinkListener
) :
    RecyclerView.ViewHolder(inflater.inflate(R.layout.list_item_drink, parent, false)), View.OnClickListener {
    private var dNameView: TextView? = null
    private var dTypeView: TextView? = null
    private var dMarkView: TextView? = null

    init {
        dNameView = itemView.findViewById(R.id.list_drink_name)
        dTypeView = itemView.findViewById(R.id.list_drink_type)
        dMarkView = itemView.findViewById(R.id.list_drink_mark)

        itemView.setOnClickListener(this)
    }

    fun bind(drink: Drink) {
        dNameView?.text = drink.name
        dTypeView?.text = drink.type.name
        dMarkView?.text = drink.rating.toString()
    }

    override fun onClick(v: View?) {
        onDrinkListener.onDrinkClick(adapterPosition)
    }
}

interface OnDrinkListener {
    fun onDrinkClick(position: Int)
}