package com.kapibarabanka.alcodiary.calendar

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.kapibarabanka.alcodiary.R
import com.kapibarabanka.alcodiary.data.ADMIN_USER
import com.kapibarabanka.alcodiary.data.LocalDBAdapter
import com.kapibarabanka.alcodiary.data.allEvents
import kotlinx.android.synthetic.main.fragment_calendar.*

const val selectedEventPositionExtra = "SELECTED_EVENT_POSITION"
const val addDrinkRequest = 2

class CalendarFragment : Fragment(), OnEventListener {

    lateinit var dbAdapter: LocalDBAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_calendar, null)
    }

    private lateinit var eventsAdapter: EventsListAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val notNullActivity = activity
        if (notNullActivity != null){
            dbAdapter = LocalDBAdapter(notNullActivity, ADMIN_USER)
            loadEvents()
        }

        eventsAdapter  = EventsListAdapter(allEvents, this)

        eventsRecyclerView.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = eventsAdapter
        }

        fabAddEvent.setOnClickListener {innerView ->
            onFabAddDrinkClicked(innerView)
        }
    }

    override fun onPause() {
        super.onPause()
        fabAddEvent.hide()
    }

    override fun onResume() {
        super.onResume()
        fabAddEvent.show()
        loadEvents()
        eventsAdapter.notifyDataSetChanged()
    }

    fun onFabAddDrinkClicked (view: View) {
        val intent = Intent(activity?.applicationContext, SaveEventPopUp::class.java)
        startActivity(intent)
    }

    companion object {
        fun newInstance(): CalendarFragment = CalendarFragment()
    }

    override fun onEventClick(position: Int) {
        val intent = Intent(activity?.applicationContext, EventInfoPopUp::class.java)
        intent.putExtra(selectedEventPositionExtra, position)
        startActivity(intent)
    }

    private fun loadEvents() {
        allEvents.clear()
        dbAdapter.open()
        allEvents.addAll(dbAdapter.getAllEventsWithDrinks())
        dbAdapter.close()
        allEvents.sortByDescending { it.date }
    }
}