package com.kapibarabanka.alcodiary.data

import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.util.Log
import com.kapibarabanka.alcodiary.BASE_TAG
import com.kapibarabanka.alcodiary.calendar.DrinkInEvent
import com.kapibarabanka.alcodiary.calendar.Event
import com.kapibarabanka.alcodiary.drinks.Drink
import com.kapibarabanka.alcodiary.drinks.DrinkType
import java.time.LocalDate

class LocalDBAdapter(context: Context) {
    private val dbHelper = LocalDBHelper(context.applicationContext)
    private lateinit var db: SQLiteDatabase

    private val typeColumns = arrayOf(COL_ID, COL_NAME, COL_MIN_ALCO, COL_MAX_ALCO)
    private val drinkColumns = arrayOf(COL_ID, COL_NAME, COL_TYPE, COL_RATING, COL_COMMENT)
    private val eventColumns = arrayOf(COL_ID, COL_NAME, COL_DATE, COL_RATING)
    private val drinkInEventColumns = arrayOf(COL_ID, COL_EVENT, COL_DRINK, COL_AMOUNT)

    fun open(): LocalDBAdapter {
        db = dbHelper.writableDatabase
        return this
    }

    fun close() {dbHelper.close()}


    fun getAllTypes(): MutableList<DrinkType> {
        val result = mutableListOf<DrinkType>()
        val cursor = getAllTypesCursor()
        if(cursor.moveToFirst()) {
            do {
                result.add(getType(cursor))
            }
            while (cursor.moveToNext())
        }
        cursor.close()
        Log.i(BASE_TAG, "Loaded types from local DB")
        return result
    }

    fun getType(id: Int): DrinkType {
        var type = emptyDrinkType
        val selection = "$COL_ID = ?"
        val selectionArgs = arrayOf(id.toString())
        val cursor = db.query(TYPES_TABLE, typeColumns, selection, selectionArgs, null, null, null)
        if(cursor.moveToFirst()){
            type = getType(cursor)
        }
        cursor.close()
        return type
    }

    private fun getAllTypesCursor(): Cursor {
        return db.query(TYPES_TABLE, typeColumns, null, null, null, null, null)
    }

    private fun getType(c: Cursor): DrinkType {
        val id = c.getInt(c.getColumnIndex(COL_ID))
        val name = c.getString(c.getColumnIndex(COL_NAME))
        val minAlco = c.getFloat(c.getColumnIndex(COL_MIN_ALCO))
        val maxAlco = c.getFloat(c.getColumnIndex(COL_MAX_ALCO))
        return DrinkType(id, name, minAlco, maxAlco)
    }


    fun getAllDrinks(): MutableList<Drink> {
        val result = mutableListOf<Drink>()
        val cursor = getAllDrinksCursor()
        if(cursor.moveToFirst()) {
            do {
                result.add(getDrink(cursor))
            }
            while (cursor.moveToNext())
        }
        cursor.close()
        Log.i(BASE_TAG, "Loaded drinks from local DB")
        return result
    }

    fun getDrink(id: Int): Drink {
        var drink = emptyDrink
        val selection = "$COL_ID = ?"
        val selectionArgs = arrayOf(id.toString())
        val cursor = db.query(DRINKS_TABLE, drinkColumns, selection, selectionArgs, null, null, null)
        if(cursor.moveToFirst()){
            drink = getDrink(cursor)
        }
        cursor.close()
        return drink
    }

    private fun getAllDrinksCursor(): Cursor {
        return db.query(DRINKS_TABLE, drinkColumns, null, null, null, null, null)
    }

    private fun getDrink(c: Cursor): Drink {
        val id = c.getInt(c.getColumnIndex(COL_ID))
        val name = c.getString(c.getColumnIndex(COL_NAME))
        val typeId = c.getInt(c.getColumnIndex(COL_TYPE))
        val type = getType(typeId)
        val rating = c.getFloat(c.getColumnIndex(COL_RATING))
        val comment = c.getString(c.getColumnIndex(COL_COMMENT))
        return Drink(id, name, type, rating, comment)
    }

    fun getAllEventsWithDrinks(): MutableList<Event> {
        val result = mutableListOf<Event>()
        val eventCursor = getAllEventsCursor()
        if(eventCursor.moveToFirst()) {
            do {
                val event = getEvent(eventCursor)
                event.drinks = getEventDrinks(event.id)
                result.add(event)
            }
            while (eventCursor.moveToNext())
        }
        eventCursor.close()
        Log.i(BASE_TAG, "Loaded events from local DB")
        return result
    }

    fun getEvent(id: Int): Event {
        var event = emptyEvent
        val selection = "$COL_ID = ?"
        val selectionArgs = arrayOf(id.toString())
        val cursor = db.query(EVENTS_TABLE, eventColumns, selection, selectionArgs, null, null, null)
        if(cursor.moveToFirst()){
            event = getEvent(cursor)
        }
        cursor.close()
        return event
    }

    private fun getAllEventsCursor(): Cursor {
        return db.query(EVENTS_TABLE, eventColumns, null, null, null, null, null)
    }

    private fun getEvent(c: Cursor): Event {
        val id = c.getInt(c.getColumnIndex(COL_ID))
        val name = c.getString(c.getColumnIndex(COL_NAME))
        val dateString = c.getString(c.getColumnIndex(COL_DATE))
        val date = LocalDate.parse(dateString)
        val rating = c.getFloat(c.getColumnIndex(COL_RATING))
        return Event(id, name, date, rating)
    }

    private fun getAllDrinksInEventsCursor(): Cursor {
        return db.query(DRINKS_IN_EVENTS_TABLE, drinkInEventColumns, null, null, null, null, null)
    }

    private fun getEventDrinks(eventId: Int): MutableList<DrinkInEvent> {
        val result = mutableListOf<DrinkInEvent>()
        val selection = "$COL_EVENT = ?"
        val selectionArgs = arrayOf(eventId.toString())
        val cursor = db.query(DRINKS_IN_EVENTS_TABLE, drinkInEventColumns, selection, selectionArgs, null, null, null)
        if(cursor.moveToFirst()) {
            do {
                result.add(getDrinkInEvent(cursor))
            }
            while (cursor.moveToNext())
        }
        cursor.close()
        return result
    }

    private fun getDrinkInEvent(c: Cursor): DrinkInEvent {
        val id = c.getInt(c.getColumnIndex(COL_ID))
        val eventId = c.getInt(c.getColumnIndex(COL_EVENT))
        val drinkId = c.getInt(c.getColumnIndex(COL_DRINK))
        val drink = getDrink(drinkId)
        val amount = c.getFloat(c.getColumnIndex(COL_AMOUNT))
        return DrinkInEvent(drink, amount)
    }
}