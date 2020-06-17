package com.kapibarabanka.alcodiary.data

import com.kapibarabanka.alcodiary.calendar.Event
import com.kapibarabanka.alcodiary.drinks.Drink
import com.kapibarabanka.alcodiary.drinks.DrinkType
import java.time.LocalDate

var drinkTypeId = -1
var drinkId = -1
var eventId = -1

val allDrinkTypes = mutableListOf<DrinkType>()
val allDrinks = mutableListOf<Drink>()
val allEvents = mutableListOf<Event>()

// Drink types
//val beer = DrinkType("Beer", 4.0f, 7.0f)
//val wine = DrinkType("Wine", 10.0f, 17.0f)
//val vodka = DrinkType("Vodka", 37.0f, 42.0f)
//val allDrinkTypes = mutableListOf(beer, wine, vodka)

////Drinks
//val beers = mutableListOf(
//    Drink("White night", beer, 5.0f),
//    Drink("First private", beer, 5.0f),
//    Drink("Bud", beer, 4.0f),
//    Drink("Non-filtered", beer, 5.0f)
//)
//val wines = mutableListOf(
//    Drink("Bolgrad", wine, 5.0f),
//    Drink("Vila Crimea", wine, 4.0f),
//    Drink("Mikado", wine, 3.0f)
//)
//val vodkas = mutableListOf(
//    Drink("Morosha", vodka, 3.0f),
//    Drink("Vozduh", vodka, 3.0f),
//    Drink("Zubrovka", vodka, 5.0f)
//)
////val allDrinks: MutableList<Drink> = (beers + wines + vodkas).toMutableList()
//
////Events
//val allOldEvents = mutableListOf(
//    Event("first", LocalDate.of(2020, 5, 7), mutableListOf(
//        DrinkInEvent(vodkas[0] , 0.3f),
//        DrinkInEvent(beers[1], 1.5f)
//    )),
//    Event("second", LocalDate.of(2020, 5, 9), mutableListOf(
//        DrinkInEvent(vodkas[0] , 0.3f),
//        DrinkInEvent(beers[0] , 1.0f),
//        DrinkInEvent(beers[1] , 1.0f)
//    )),
//    Event("third", LocalDate.of(2020, 5, 15), mutableListOf(
//        DrinkInEvent(wines[0], 1.4f)
//    )),
//    Event("fourth", LocalDate.of(2020, 6, 2), mutableListOf(
//        DrinkInEvent(wines[1] , 1.4f),
//        DrinkInEvent(wines[0] , 1.0f),
//        DrinkInEvent(vodkas[2] , 0.1f),
//        DrinkInEvent(beers[1] , 0.5f)
//    )),
//    Event("fifth", LocalDate.of(2020, 6, 11), mutableListOf(
//        DrinkInEvent(beers[2] , 1.0f),
//        DrinkInEvent(wines[2] , 1.0f)
//    ))
//)

val emptyDrinkType = DrinkType("", 0.0f, 0.0f)
val emptyDrink = Drink("", emptyDrinkType, 0.0f, "")
val emptyEvent = Event("", LocalDate.MIN)

fun findDrinkType(id: Int): DrinkType {
    return allDrinkTypes.find { it.id == id } ?: emptyDrinkType
}

fun findDrink(id: Int): Drink {
    return allDrinks.find {it.id == id} ?: emptyDrink
}

fun findEvent(id: Int): Event {
    return allEvents.find {it.id == id} ?: emptyEvent
}

fun getNewDrinkTypeId(): Int {
    val max = allDrinkTypes.maxBy { it.id }?.id ?: 5
    return max + 1
}

//fun getNewDrinkTypeId(): Int {
//    return drinkTypeId++
//}

fun getNewDrinkId(): Int {
    val max = allDrinks.maxBy { it.id }?.id ?: 0
    return max + 1
}

fun getNewEventId(): Int {
    val max = allEvents.maxBy { it.id }?.id ?: 0
    return max + 1
}
