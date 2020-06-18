package com.kapibarabanka.alcodiary.drinks

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import com.kapibarabanka.alcodiary.R
import com.kapibarabanka.alcodiary.iconMap

class ChooseDrinkIconListAdapter(private val list: MutableList<String>)
    : RecyclerView.Adapter<ChooseDrinkIconViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChooseDrinkIconViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ChooseDrinkIconViewHolder(inflater, parent)
    }

    override fun onBindViewHolder(holder: ChooseDrinkIconViewHolder, position: Int) {
        val item = list[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int = list.size
}

class ChooseDrinkIconViewHolder(inflater: LayoutInflater, parent: ViewGroup) :
    RecyclerView.ViewHolder(inflater.inflate(R.layout.choose_drink_icon_element, parent, false)){

    var iconImage : ImageView? = null;

    init {
        iconImage = itemView.findViewById(R.id.icon_image_in_choose_menu)
    }

    fun bind(item: String) {
        // TODO : use line below to set images in all cards with "drink.drinktype.icon" instead of item
        iconMap[item]?.let { iconImage?.setImageResource(it) }
    }
}