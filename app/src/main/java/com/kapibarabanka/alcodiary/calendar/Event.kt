package com.kapibarabanka.alcodiary.calendar

import com.kapibarabanka.alcodiary.drinks.Drink
import java.time.LocalDate

class Event(var name: String, var date: LocalDate) {
    val drinks = mutableListOf<Pair<Drink, Double>>()
    constructor(name: String, date: LocalDate, drinks: MutableList<Pair<Drink, Double>>) : this(name, date) {
       this.drinks.addAll(drinks)
    }
    var rating: Int = 0
}