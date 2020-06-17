package com.kapibarabanka.alcodiary.calendar

import com.kapibarabanka.alcodiary.data.getNewEventId
import java.time.LocalDate

class Event(var name: String, var date: LocalDate, var rating: Float = 0.0f) {
    var id = getNewEventId()
    var drinks = mutableListOf<DrinkInEvent>()
    constructor(name: String, date: LocalDate, drinks: MutableList<DrinkInEvent>) : this(name, date) {
        this.drinks.addAll(drinks)
    }
    constructor(idFromDB: Int, name: String, date: LocalDate, rating: Float) : this(name, date, rating) {id = idFromDB}
}