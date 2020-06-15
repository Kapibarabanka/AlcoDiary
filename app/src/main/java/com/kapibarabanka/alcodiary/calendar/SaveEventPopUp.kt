package com.kapibarabanka.alcodiary.calendar

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.util.DisplayMetrics
import android.view.View
import com.kapibarabanka.alcodiary.R
import com.kapibarabanka.alcodiary.allEvents
import com.kapibarabanka.alcodiary.drinks.Drink
import kotlinx.android.synthetic.main.pop_up_save_event.*
import java.time.LocalDate

const val addedDrinkExtra = "ADDED_DRINK"
const val addedAmountExtra = "ADDED_AMOUNT"

class SaveEventPopUp : AppCompatActivity(){
    var selectedEventPosition = -1

    lateinit var eventDrinks: MutableList<Pair<Drink, Double>>
    lateinit var drinkAdapter: EditDrinkInEventListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.pop_up_save_event)

        val dm = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(dm)

        val width: Int = (dm.widthPixels * 0.9).toInt()
        val height: Int = (dm.heightPixels * 0.7).toInt()

        window.setLayout(width, height)

        selectedEventPosition = intent.getIntExtra(selectedEventPositionExtra, -1)
        if (selectedEventPosition != -1){
            val selectedEvent = allEvents[selectedEventPosition]
            eventDrinks = selectedEvent.drinks
            showEventInfo(selectedEvent)
        }
        else {
            eventDrinks = mutableListOf()
        }
        drinkAdapter = EditDrinkInEventListAdapter(eventDrinks)
        eventDrinksRecyclerView?.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = drinkAdapter
        }
    }

    override fun onResume() {
        super.onResume()
        drinkAdapter.notifyDataSetChanged()
    }

    fun onSaveClicked(view: View) {
        val name = nameText.text.toString()
        val date = LocalDate.parse(dateText.text)
        val rating = ratingText.text.toString().toInt()
        if (selectedEventPosition == -1) {
            allEvents.add(Event(name, date, eventDrinks))
            allEvents.sortByDescending { it.date }
        }
        else {
            val selectedEvent = allEvents[selectedEventPosition]
            selectedEvent.name = name
            selectedEvent.date = date
            selectedEvent.rating = rating
            allEvents.sortByDescending { it.date }
        }
        finish()
    }

    fun onAddDrinkClicked(view: View) {
        val intent = Intent(applicationContext, AddDrinkToEventPopUp::class.java)
        startActivityForResult(intent, addDrinkRequest)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == addDrinkRequest && resultCode == Activity.RESULT_OK) {
            val newDrink: Drink = data?.getSerializableExtra(addedDrinkExtra) as Drink
            val newAmount = data.getDoubleExtra(addedAmountExtra, 0.0)
            eventDrinks.add(newDrink to newAmount)
            drinkAdapter.notifyDataSetChanged()
        }
        else{
            super.onActivityResult(requestCode, resultCode, data)
        }
    }

    private fun showEventInfo(event: Event) {
        nameText.setText(event.name)
        dateText.setText(event.date.toString())
        ratingText.setText(event.rating.toString())
    }
}