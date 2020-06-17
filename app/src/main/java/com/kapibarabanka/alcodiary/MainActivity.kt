package com.kapibarabanka.alcodiary

import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import com.kapibarabanka.alcodiary.calendar.CalendarFragment
import com.kapibarabanka.alcodiary.calendar.DrinkInEvent
import com.kapibarabanka.alcodiary.calendar.Event
import com.kapibarabanka.alcodiary.data.*
import com.kapibarabanka.alcodiary.drinks.Drink
import com.kapibarabanka.alcodiary.drinks.DrinkType
import com.kapibarabanka.alcodiary.drinks.DrinksFragment
import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android
import io.ktor.client.features.json.GsonSerializer
import io.ktor.client.features.json.JsonFeature
import io.ktor.client.request.get
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

const val BASE_URL = "https://alcodiary.herokuapp.com"
const val DRINK_TYPES_URL = "$BASE_URL/drinkTypes"
const val DRINKS_URL = "$BASE_URL/drinks"
const val EVENTS_URL = "$BASE_URL/events"
const val DRINKS_IN_EVENTS_URL = "$BASE_URL/drinksInEvents"
const val BASE_TAG = "AlcoDiary"

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        val toggle = ActionBarDrawerToggle(
            this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close
        )
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()

        nav_view.setNavigationItemSelectedListener(this)

        displayFragment(-1)

        val dbAdapter = LocalDBAdapter(this)
        dbAdapter.open()

        allDrinkTypes.addAll(dbAdapter.getAllTypes())
        allDrinks.addAll(dbAdapter.getAllDrinks())
        allEvents.addAll(dbAdapter.getAllEventsWithDrinks())

        dbAdapter.close()
//        GlobalScope.launch(Dispatchers.IO) {
//            loadAllFromGlobalBase()
//        }
    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        when (item.itemId) {
            R.id.action_settings -> return true
            else -> return super.onOptionsItemSelected(item)
        }
    }

    private fun displayFragment(id: Int) {
        val fragment = when (id) {
            R.id.nav_calendar -> {
                CalendarFragment()
            }
            R.id.nav_drinks -> {
                DrinksFragment()
            }
            R.id.nav_statistics -> {
                StatisticsFragment()
            }
            else -> {
                CalendarFragment()
            }
        }

        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .commit()
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        displayFragment(item.itemId)

        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }

    private suspend fun loadAllFromGlobalBase() {
        val client = HttpClient(Android) {
            install(JsonFeature) {
                serializer = GsonSerializer()
            }
        }

        val drinkTypesFromDB: List<DrinkType> = client.get(DRINK_TYPES_URL)
        for (type in drinkTypesFromDB) {
            Log.i(BASE_TAG, "DrinkType(${type.id}, ${type.name})")
        }
        allDrinkTypes.addAll(drinkTypesFromDB)

        val drinksFromDB: List<DrinkData> = client.get(DRINKS_URL)
        for (drink in drinksFromDB) {
            Log.i(BASE_TAG, "DrinkData(${drink.id}, ${drink.name})")
        }
        allDrinks.addAll(drinksFromDB.map { it.getObject() })

        val eventsFromDB: List<EventData> = client.get(EVENTS_URL)
        for (event in eventsFromDB) {
            Log.i(BASE_TAG, "EventData(${event.id}, ${event.name})")
        }
        allEvents.addAll(eventsFromDB.map { it.getObject() })

        val drinksInEventsFromDB: List<DrinkInEventData> = client.get(DRINKS_IN_EVENTS_URL)
        drinksInEventsFromDB.forEach { it.getAndAddObject() }

        for (event in allEvents) {
            for (drink in event.drinks) {
                Log.i(BASE_TAG, "${event.name}: ${drink.drink.name} - ${drink.amount}")
            }
        }

        client.close()
    }
}
