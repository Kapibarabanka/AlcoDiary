package com.kapibarabanka.alcodiary.calendar

import android.support.v7.widget.AppCompatImageButton
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import com.kapibarabanka.alcodiary.R
import com.kapibarabanka.alcodiary.drinks.Drink

class EditDrinkInEventListAdapter(private val list: MutableList<Pair<Drink, Double>>)
    : RecyclerView.Adapter<EditDrinkInEventViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EditDrinkInEventViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return EditDrinkInEventViewHolder(inflater, parent)
    }

    override fun onBindViewHolder(holder: EditDrinkInEventViewHolder, position: Int) {
        val pair = list[position]
        holder.bind(pair)
        val deleteButton: AppCompatImageButton = holder.itemView.findViewById(R.id.deleteButton)
        deleteButton.setOnClickListener {
            list.removeAt(position)
            notifyItemRemoved(position)
            notifyItemRangeChanged(position, list.size)
        }
    }

    override fun getItemCount(): Int = list.size
}

class EditDrinkInEventViewHolder(inflater: LayoutInflater, parent: ViewGroup) :
    RecyclerView.ViewHolder(inflater.inflate(R.layout.list_item_edit_drink_in_event, parent, false)){

    private var typeView: TextView? = null
    private var nameView: TextView? = null
    private var amountView: TextView? = null

    init {
        typeView = itemView.findViewById(R.id.list_drink_type)
        nameView = itemView.findViewById(R.id.list_drink_name)
        amountView = itemView.findViewById(R.id.list_drink_amount)
    }

    fun bind(pair: Pair<Drink, Double>) {
        val drink = pair.first
        val amount = pair.second
        typeView?.text = drink.type.toString()
        nameView?.text = drink.name
        amountView?.text = amount.toString()
    }
}