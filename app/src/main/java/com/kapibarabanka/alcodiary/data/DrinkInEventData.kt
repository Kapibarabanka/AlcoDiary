package com.kapibarabanka.alcodiary.data

import com.kapibarabanka.alcodiary.calendar.DrinkInEvent

data class DrinkInEventData(val id: Long, val eventId: Long, val drinkId: Long, val amount: Float) {
    fun getAndAddObject() {
        val event = findEvent(eventId)
        event.drinks.add(DrinkInEvent(id, findDrink(drinkId), amount))
    }
}