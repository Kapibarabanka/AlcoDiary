package com.kapibarabanka.alcodiary.calendar

import java.time.LocalDate

class Event(var name: String, var date: LocalDate, var rating: Float = 0.0f) {
    var id: Long = 0
    var drinks = mutableListOf<DrinkInEvent>()
    constructor(name: String, date: LocalDate, drinks: MutableList<DrinkInEvent>) : this(name, date) {
        this.drinks.addAll(drinks)
    }
    constructor(idFromDB: Long, name: String, date: LocalDate, rating: Float) : this(name, date, rating) {id = idFromDB}
}