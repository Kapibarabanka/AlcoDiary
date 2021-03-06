package com.kapibarabanka.alcodiary.calendar

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.util.DisplayMetrics
import android.view.View
import com.kapibarabanka.alcodiary.R
import com.kapibarabanka.alcodiary.data.ADMIN_USER
import com.kapibarabanka.alcodiary.data.LocalDBAdapter
import com.kapibarabanka.alcodiary.data.allEvents
import com.kapibarabanka.alcodiary.drinks.Drink
import kotlinx.android.synthetic.main.pop_up_save_event.*
import java.time.LocalDate

const val addedDrinkExtra = "ADDED_DRINK"
const val addedAmountExtra = "ADDED_AMOUNT"

class SaveEventPopUp : AppCompatActivity(){
    var selectedEventPosition = -1

    lateinit var eventDrinks: MutableList<DrinkInEvent>
    lateinit var drinkAdapter: EditDrinkInEventListAdapter

    lateinit var dbAdapter: LocalDBAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.pop_up_save_event)

        val dm = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(dm)

        val width: Int = (dm.widthPixels * 0.9).toInt()
        val height: Int = (dm.heightPixels * 0.8).toInt()

        window.setLayout(width, height)

        dbAdapter = LocalDBAdapter(this, ADMIN_USER)

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
        val rating = ratingText.text.toString().toFloat()
        if (selectedEventPosition == -1) {
            val newEvent = Event(name, date, eventDrinks)
            allEvents.add(newEvent)
            dbAdapter.open()
            dbAdapter.insertEvent(newEvent)
            dbAdapter.close()
            allEvents.sortByDescending { it.date }
        }
        else {
            val selectedEvent = allEvents[selectedEventPosition]
            selectedEvent.name = name
            selectedEvent.date = date
            selectedEvent.rating = rating
            dbAdapter.open()
            dbAdapter.updateEvent(selectedEvent)
            dbAdapter.close()
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
            val newAmount = data.getFloatExtra(addedAmountExtra, 0.0f)
            eventDrinks.add(DrinkInEvent(0, newDrink, newAmount))
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