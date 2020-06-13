package com.kapibarabanka.alcodiary.calendar

import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.kapibarabanka.alcodiary.R
import com.kapibarabanka.alcodiary.drinks.Drink

class EventsListAdapter(private val list: List<Event>, private val onEventListener: OnEventListener)
    : RecyclerView.Adapter<EventViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return EventViewHolder(inflater, parent, onEventListener)
    }

    override fun onBindViewHolder(holder: EventViewHolder, position: Int) {
        val event: Event = list[position]
        holder.bind(event)
    }

    override fun getItemCount(): Int = list.size
}

class EventViewHolder(inflater: LayoutInflater, parent: ViewGroup,
                      private val onEventListener: OnEventListener) :
    RecyclerView.ViewHolder(inflater.inflate(R.layout.list_item_event, parent, false)), View.OnClickListener {

    private var numberView: TextView? = null
    private var drinksRecyclerView: RecyclerView? = null
    private var dotsView : TextView? = null

    init {
        numberView = itemView.findViewById(R.id.list_event_number)
        drinksRecyclerView = itemView.findViewById(R.id.eventDrinksRecyclerView)
        dotsView = itemView.findViewById(R.id.dotsText)
        itemView.setOnClickListener(this)
    }

    fun bind(event: Event) {
        numberView?.text = event.date.dayOfMonth.toString()
        val drinks = getDrinks(event)
        drinksRecyclerView?.apply {
            layoutManager = LinearLayoutManager(itemView.context)
            adapter = DrinkInEventListAdapter(drinks)
        }
    }

    override fun onClick(v: View?) {
        onEventListener.onEventClick(adapterPosition)
    }

    private fun getDrinks(event: Event): MutableList<Pair<Drink, Double>> {
        if (event.drinks.count() <= 2) {
            dotsView?.visibility = View.GONE
            return event.drinks
        }
        dotsView?.visibility = View.VISIBLE
        return event.drinks.slice(0..1).toMutableList()
    }
}

interface OnEventListener {
    fun onEventClick(position: Int)
}

