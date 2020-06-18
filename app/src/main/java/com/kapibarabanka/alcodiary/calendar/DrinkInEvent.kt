package com.kapibarabanka.alcodiary.calendar

import com.kapibarabanka.alcodiary.drinks.Drink

data class DrinkInEvent(var id: Long, val drink: Drink, val amount: Float) {
    override fun toString(): String {
        return "${drink.name} (${amount}l)"
    }
}