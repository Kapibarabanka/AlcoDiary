package com.kapibarabanka.alcodiary.calendar

import com.kapibarabanka.alcodiary.drinks.Drink
import com.kapibarabanka.alcodiary.findDrink
import com.kapibarabanka.alcodiary.findEvent

class DrinkInEvent(val drink: Drink, val amount: Float) {
    constructor(id: Int, eventId: Int, drinkId: Int, amount: Float)
        : this(findDrink(drinkId), amount) {
        val event = findEvent(eventId)
        event.drinks.add(this)
    }
}