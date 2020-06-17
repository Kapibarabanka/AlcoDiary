package com.kapibarabanka.alcodiary.calendar

import com.kapibarabanka.alcodiary.data.getNewEventId
import java.time.LocalDate

class Event(var name: String, var date: LocalDate) {
    var id = getNewEventId()
    val drinks = mutableListOf<DrinkInEvent>()
    constructor(name: String, date: LocalDate, drinks: MutableList<DrinkInEvent>) : this(name, date) {
        this.drinks.addAll(drinks)
    }
    constructor(idFromDB: Int, name: String, date: LocalDate) : this(name, date) {id = idFromDB}
    var rating: Int = 0
}