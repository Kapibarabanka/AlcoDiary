package com.kapibarabanka.alcodiary.data

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.util.Log
import com.kapibarabanka.alcodiary.BASE_TAG
import com.kapibarabanka.alcodiary.calendar.DrinkInEvent
import com.kapibarabanka.alcodiary.calendar.Event
import com.kapibarabanka.alcodiary.drinks.Drink
import com.kapibarabanka.alcodiary.drinks.DrinkType
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.util.*
import kotlin.collections.ArrayList


enum class EntryState {
    NON, INS, UPD, DEL
}

class LocalDBAdapter(context: Context, val user: String) {
    val timeFormat = SimpleDateFormat("yyyy-mm-dd-hh-mm-ss")
    private val calendar = Calendar.getInstance()

    private val dbHelper = LocalDBHelper(context.applicationContext)
    private lateinit var db: SQLiteDatabase

    private val typeColumns = arrayOf(COL_ID, COL_NAME, COL_MIN_ALCO, COL_MAX_ALCO, COL_ICON, COL_STATE)
    private val drinkColumns = arrayOf(COL_ID, COL_NAME, COL_TYPE, COL_RATING, COL_COMMENT)
    private val eventColumns = arrayOf(COL_ID, COL_NAME, COL_DATE, COL_RATING)
    private val drinkInEventColumns = arrayOf(COL_ID, COL_EVENT, COL_DRINK, COL_AMOUNT)

    fun open(): LocalDBAdapter {
        db = dbHelper.writableDatabase
        //dbHelper.createTables(db)
        //dbHelper.dropTables(db)
        return this
    }

    fun close() {dbHelper.close()}


    fun insertType(type: DrinkType) {
        type.id = insert(TYPES_TABLE, typeCV(type))
        Log.i(BASE_TAG, "Inserted type ${type.name} to id ${type.id}")
    }

    fun insertDrink(drink: Drink) {
        drink.id = insert(DRINKS_TABLE, drinkCV(drink))
        Log.i(BASE_TAG, "Inserted drink ${drink.name} to id ${drink.id}")
    }

    fun insertEvent(event: Event) {
        val cvEvent = eventCV(event)
        event.id = insert(EVENTS_TABLE, cvEvent)
        Log.i(BASE_TAG, "Inserted event ${event.name} to id ${event.id}")
        for (die in event.drinks) {
            val cvDrink = drinkInEventCV(die, event.id)
            die.id = insert(DRINKS_IN_EVENTS_TABLE, cvDrink)
        }
    }

    private fun insert(table: String, cv: ContentValues): Long {
        cv.put(COL_USER, user)
        cv.put(COL_TIME, timeFormat.format(calendar.time))
        cv.put(COL_STATE, EntryState.INS.toString())
        return db.insert(table, null, cv)
    }


    fun updateType(type: DrinkType) {
        val cv = typeCV(type)
        val res = update(TYPES_TABLE, cv, type.id)
        Log.i(BASE_TAG, "Update: $res types were updated (id = ${type.id})")
    }

    fun updateDrink(drink: Drink) {
        val cv = drinkCV(drink)
        val res = update(DRINKS_TABLE, cv, drink.id)
        Log.i(BASE_TAG, "Update: $res drinks were updated (id = ${drink.id})")
    }

    fun updateEvent(event: Event) {
        val oldDrinks = getDiesWith(event.id, COL_EVENT)
        for (die in oldDrinks) {
            if(markDeleted(DRINKS_IN_EVENTS_TABLE, die.id))
                Log.i(BASE_TAG, "Drink $die was deleted from event ${event.name}")
        }
        for (die in event.drinks) {
            val cvDrink = drinkInEventCV(die, event.id)
            die.id = insert(DRINKS_IN_EVENTS_TABLE, cvDrink)
            Log.i(BASE_TAG, "Drink $die was added to event ${event.name}")
        }
        val cv = eventCV(event)
        val res = update(EVENTS_TABLE, cv, event.id)
        Log.i(BASE_TAG, "Update: $res events were updated (id = ${event.id})")
    }

    private fun update(table: String, cv: ContentValues, id: Long): Int {
         val whereClause = "$COL_ID = $id"
         cv.put(COL_USER, user)
         cv.put(COL_TIME, timeFormat.format(calendar.time))
         cv.put(COL_STATE, EntryState.UPD.toString())
         return db.update(table, cv, whereClause, null)
    }

    fun deleteType(type: DrinkType) {
        if(markDeleted(TYPES_TABLE, type.id))
            Log.i(BASE_TAG, "Type ${type.name} was deleted")
    }

    fun deleteDrink(drink: Drink) {
        val diesWithDrink = getDiesWith(drink.id, COL_DRINK)
        for(die in diesWithDrink) {
            if (markDeleted(DRINKS_IN_EVENTS_TABLE, die.id))
                Log.i(BASE_TAG, "Drink $die was deleted because of drink ${drink.name}")
        }
        if(markDeleted(DRINKS_TABLE, drink.id))
            Log.i(BASE_TAG, "Drink ${drink.name} was deleted")
    }

    fun deleteEvent(event: Event) {
        for (die in event.drinks) {
            if (markDeleted(DRINKS_IN_EVENTS_TABLE, die.id))
                Log.i(BASE_TAG, "Drink $die was deleted from event ${event.name}")
        }
        if (markDeleted(EVENTS_TABLE, event.id))
            Log.i(BASE_TAG, "Event ${event.name} was deleted")
    }

    private fun markDeleted(table: String, id: Long): Boolean {
        val whereClause = "$COL_ID = $id"
        val cv = ContentValues()
        cv.put(COL_STATE, EntryState.DEL.toString())
        val res = db.update(table, cv, whereClause, null)
        return res == 1
    }


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
        val selection = "$COL_STATE <> ?"
        val selectionArgs = arrayOf(EntryState.DEL.toString())
        return db.query(TYPES_TABLE, typeColumns, selection, selectionArgs, null, null, null)
    }

    private fun getType(c: Cursor): DrinkType {
        val id = c.getLong(c.getColumnIndex(COL_ID))
        val name = c.getString(c.getColumnIndex(COL_NAME))
        val minAlco = c.getFloat(c.getColumnIndex(COL_MIN_ALCO))
        val maxAlco = c.getFloat(c.getColumnIndex(COL_MAX_ALCO))
        val icon = c.getInt(c.getColumnIndex(COL_ICON))
        return DrinkType(id, name, minAlco, maxAlco, icon)
    }


    fun getAllDrinks(): ArrayList<Drink> {
        val result = arrayListOf<Drink>()
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
        val selection = "$COL_STATE <> ?"
        val selectionArgs = arrayOf(EntryState.DEL.toString())
        return db.query(DRINKS_TABLE, drinkColumns, selection, selectionArgs, null, null, null)
    }

    private fun getDrink(c: Cursor): Drink {
        val id = c.getLong(c.getColumnIndex(COL_ID))
        val name = c.getString(c.getColumnIndex(COL_NAME))
        val typeId = c.getInt(c.getColumnIndex(COL_TYPE))
        val type = getType(typeId)
        val rating = c.getFloat(c.getColumnIndex(COL_RATING))
        val comment = c.getString(c.getColumnIndex(COL_COMMENT))
        return Drink(id, name, type, rating, comment)
    }

    fun getAllEventsWithDrinks(): ArrayList<Event> {
        val result = arrayListOf<Event>()
        val eventCursor = getAllEventsCursor()
        if(eventCursor.moveToFirst()) {
            do {
                val event = getEvent(eventCursor)
                event.drinks = getDiesWith(event.id, COL_EVENT)
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
        val selection = "$COL_STATE <> ?"
        val selectionArgs = arrayOf(EntryState.DEL.toString())
        return db.query(EVENTS_TABLE, eventColumns, selection, selectionArgs, null, null, null)
    }

    private fun getEvent(c: Cursor): Event {
        val id = c.getLong(c.getColumnIndex(COL_ID))
        val name = c.getString(c.getColumnIndex(COL_NAME))
        val dateString = c.getString(c.getColumnIndex(COL_DATE))
        val date = LocalDate.parse(dateString)
        val rating = c.getFloat(c.getColumnIndex(COL_RATING))
        return Event(id, name, date, rating)
    }

    private fun getAllDrinksInEventsCursor(): Cursor {
        return db.query(DRINKS_IN_EVENTS_TABLE, drinkInEventColumns, null, null, null, null, null)
    }

    //column - parameter for WHERE (COL_EVENT or COL_DRINK)
    private fun getDiesWith(id: Long, column: String): ArrayList<DrinkInEvent> {
        val result = arrayListOf<DrinkInEvent>()
        val selection = "$column = ? AND $COL_STATE <> ?"
        val selectionArgs = arrayOf(id.toString(), EntryState.DEL.toString())
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
        val id = c.getLong(c.getColumnIndex(COL_ID))
        val eventId = c.getInt(c.getColumnIndex(COL_EVENT))
        val drinkId = c.getInt(c.getColumnIndex(COL_DRINK))
        val drink = getDrink(drinkId)
        val amount = c.getFloat(c.getColumnIndex(COL_AMOUNT))
        return DrinkInEvent(id, drink, amount)
    }

    private fun typeCV(type: DrinkType): ContentValues {
        val cv = ContentValues()
        cv.put(COL_NAME, type.name)
        cv.put(COL_MIN_ALCO, type.minAlco)
        cv.put(COL_MAX_ALCO, type.maxAlco)
        cv.put(COL_ICON, type.icon)
        return cv
    }

    private fun drinkCV(drink: Drink): ContentValues {
        val cv = ContentValues()
        cv.put(COL_NAME, drink.name)
        cv.put(COL_TYPE, drink.type.id)
        cv.put(COL_RATING, drink.rating)
        cv.put(COL_COMMENT, drink.comment)
        return cv
    }

    private fun eventCV(event: Event): ContentValues {
        val cv = ContentValues()
        cv.put(COL_NAME, event.name)
        cv.put(COL_DATE, event.date.toString())
        cv.put(COL_RATING, event.rating)
        return cv
    }

    private fun drinkInEventCV(die: DrinkInEvent, eventId: Long): ContentValues {
        val cv = ContentValues()
        cv.put(COL_EVENT, eventId)
        cv.put(COL_DRINK, die.drink.id)
        cv.put(COL_AMOUNT, die.amount)
        return cv
    }
}