package com.kapibarabanka.alcodiary.calendar

import com.kapibarabanka.alcodiary.drinks.Drink
import java.time.LocalDate

class Event(var name: String, var date: LocalDate) {
    val drinks = mutableListOf<Pair<Drink, Double>>()
    var mark: Int = 0
}