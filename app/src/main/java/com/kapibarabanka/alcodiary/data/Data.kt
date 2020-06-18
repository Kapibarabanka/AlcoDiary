package com.kapibarabanka.alcodiary.data

import com.kapibarabanka.alcodiary.calendar.Event
import com.kapibarabanka.alcodiary.drinks.Drink
import com.kapibarabanka.alcodiary.drinks.DrinkType
import java.time.LocalDate

val allDrinkTypes = mutableListOf<DrinkType>()
val allDrinks = mutableListOf<Drink>()
val allEvents = mutableListOf<Event>()

val emptyDrinkType = DrinkType("", 0.0f, 0.0f)
val emptyDrink = Drink("", emptyDrinkType, 0.0f, "")
val emptyEvent = Event("", LocalDate.MIN)

fun findDrinkType(id: Long): DrinkType {
    return allDrinkTypes.find { it.id == id } ?: emptyDrinkType
}

fun findDrink(id: Long): Drink {
    return allDrinks.find {it.id == id} ?: emptyDrink
}

fun findEvent(id: Long): Event {
    return allEvents.find {it.id == id} ?: emptyEvent
}

fun getNewDrinkTypeId(): Long {
    val max = allDrinkTypes.maxBy { it.id }?.id ?: 5
    return max + 1
}

fun getNewDrinkId(): Long {
    val max = allDrinks.maxBy { it.id }?.id ?: 0
    return max + 1
}

fun getNewEventId(): Long {
    val max = allEvents.maxBy { it.id }?.id ?: 0
    return max + 1
}
