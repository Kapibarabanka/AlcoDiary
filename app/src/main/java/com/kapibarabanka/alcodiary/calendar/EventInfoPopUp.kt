package com.kapibarabanka.alcodiary.calendar

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
import com.kapibarabanka.alcodiary.data.iconMap
import kotlinx.android.synthetic.main.pop_up_event_info.*


class EventInfoPopUp : AppCompatActivity(){
    var selectedEventPosition = -1
    lateinit var selectedEvent: Event

    lateinit var dbAdapter: LocalDBAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.pop_up_event_info)

        val dm = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(dm)

        val width: Int = (dm.widthPixels * 0.9).toInt()
        val height: Int = (dm.heightPixels * 0.8).toInt()

        window.setLayout(width, height)

        dbAdapter = LocalDBAdapter(this, ADMIN_USER)

        selectedEventPosition = intent.getIntExtra(selectedEventPositionExtra, -1)
        selectedEvent = allEvents[selectedEventPosition]
        showEventInfo(selectedEvent)
    }

    override fun onResume() {
        super.onResume()
        showEventInfo(selectedEvent)
    }

    fun onDeleteClicked(view: View) {
        val eventToRemove = allEvents[selectedEventPosition]
        dbAdapter.open()
        dbAdapter.deleteEvent(eventToRemove)
        dbAdapter.close()
        allEvents.removeAt(selectedEventPosition)
        finish()
    }

    fun onEditClicked(view: View) {
        val intent = Intent(applicationContext, SaveEventPopUp::class.java)
        intent.putExtra(selectedEventPositionExtra, selectedEventPosition)
        startActivity(intent)
    }

    private fun showEventInfo(event: Event) {
        nameText.text = event.name
        dateText.text = event.date.toString()
        ratingText.text = event.rating.toString()
        eventDrinksRecyclerView?.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = DrinkInEventListAdapter(R.layout.list_item_drink_in_event, event.drinks)
        }
    }
}