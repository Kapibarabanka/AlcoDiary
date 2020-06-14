package com.kapibarabanka.alcodiary

import com.kapibarabanka.alcodiary.calendar.Event
import com.kapibarabanka.alcodiary.drinks.Drink
import com.kapibarabanka.alcodiary.drinks.DrinkType
import java.time.LocalDate

// Drink types
val beer = DrinkType("Beer", 4, 7)
val wine = DrinkType("Wine", 10, 17)
val vodka = DrinkType("Vodka", 37, 42)
val allDrinkTypes = mutableListOf(beer, wine, vodka)

//Drinks
val beers = mutableListOf(
    Drink("White night", beer, 5.0f),
    Drink("First private", beer, 5.0f),
    Drink("Bud", beer, 4.0f),
    Drink("Non-filtered", beer, 5.0f)
)
val wines = mutableListOf(
    Drink("Bolgrad", wine, 5.0f),
    Drink("Vila Crimea", wine, 4.0f),
    Drink("Mikado", wine, 3.0f)
)
val vodkas = mutableListOf(
    Drink("Morosha", vodka, 3.0f),
    Drink("Vozduh", vodka, 3.0f),
    Drink("Zubrovka", vodka, 5.0f)
)
val allDrinks: MutableList<Drink> = (beers + wines + vodkas).toMutableList()

//Events
val allEvents = mutableListOf(
    Event("first", LocalDate.of(2020, 5, 7), mutableListOf(
        vodkas[0] to 0.3,
        beers[1] to 1.5
    )),
    Event("second", LocalDate.of(2020, 5, 9), mutableListOf(
        vodkas[0] to 0.3,
        beers[0] to 1.0,
        beers[1] to 1.0
    )),
    Event("third", LocalDate.of(2020, 5, 15), mutableListOf(
        wines[0] to 1.4
    )),
    Event("fourth", LocalDate.of(2020, 6, 2), mutableListOf(
        wines[1] to 1.4,
        wines[0] to 1.0,
        vodkas[2] to 0.1,
        beers[1] to 0.5
    )),
    Event("fifth", LocalDate.of(2020, 6, 11), mutableListOf(
        beers[2] to 1.0,
        wines[2] to 1.0
    ))
)