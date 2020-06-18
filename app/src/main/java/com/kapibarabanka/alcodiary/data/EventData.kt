package com.kapibarabanka.alcodiary.data

import com.kapibarabanka.alcodiary.calendar.Event
import java.time.LocalDate

data class EventData(val id: Long, val name: String, val date: String, val rating: Float) {
    fun getObject(): Event {return Event(id, name, LocalDate.parse(date), rating)}
}