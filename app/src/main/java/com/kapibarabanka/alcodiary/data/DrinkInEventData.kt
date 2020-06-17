package com.kapibarabanka.alcodiary.data

import com.kapibarabanka.alcodiary.calendar.DrinkInEvent

data class DrinkInEventData(val id: Int, val eventId: Int, val drinkId: Int, val amount: Float) {
    fun getAndAddObject() {
        val event = findEvent(eventId)
        event.drinks.add(DrinkInEvent(findDrink(drinkId), amount))
    }
}