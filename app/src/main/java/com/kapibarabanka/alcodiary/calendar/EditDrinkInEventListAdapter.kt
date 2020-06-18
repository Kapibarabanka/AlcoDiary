package com.kapibarabanka.alcodiary.calendar

import android.annotation.SuppressLint
import android.support.v7.widget.AppCompatImageButton
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.kapibarabanka.alcodiary.R
import com.kapibarabanka.alcodiary.data.iconMap

class EditDrinkInEventListAdapter(private val list: MutableList<DrinkInEvent>)
    : RecyclerView.Adapter<EditDrinkInEventViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EditDrinkInEventViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return EditDrinkInEventViewHolder(inflater, parent)
    }

    override fun onBindViewHolder(holder: EditDrinkInEventViewHolder, position: Int) {
        val item = list[position]
        holder.bind(item)
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
    private var iconImage: ImageView? = null

    init {
        typeView = itemView.findViewById(R.id.list_drink_type)
        nameView = itemView.findViewById(R.id.list_drink_name)
        amountView = itemView.findViewById(R.id.list_drink_amount)
        iconImage = itemView.findViewById(R.id.drink_icon_in_rv)
    }

    @SuppressLint("SetTextI18n")
    fun bind(item: DrinkInEvent) {
        typeView?.text = item.drink.type.toString()
        nameView?.text = item.drink.name
        amountView?.text = item.amount.toString() + "L"
        iconMap[item.drink.type.icon]?.let { iconImage?.setImageResource(it) }
    }
}