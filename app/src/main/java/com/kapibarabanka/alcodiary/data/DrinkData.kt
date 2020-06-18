package com.kapibarabanka.alcodiary.data

import com.kapibarabanka.alcodiary.drinks.Drink

data class DrinkData(val id: Long, val name: String, val type: Long, val rating: Float, val comment: String = "") {
    fun getObject(): Drink {return Drink(id, name, findDrinkType(type), rating, comment)}
}