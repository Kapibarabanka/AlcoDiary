package com.alcodiary

import com.alcodiary.drink.Drink
import com.alcodiary.drink.DrinkType

// Drink types
val beer = DrinkType("Beer", 4, 7)
val wine = DrinkType("Wine", 10, 17)
val vodka = DrinkType("Vodka", 37, 42)
val allDrinkTypes = listOf(beer, wine, vodka)

//Drinks
val beers = listOf(
    Drink("White night", beer, 5),
    Drink("First private", beer, 5),
    Drink("Bud", beer, 4),
    Drink("Non-filtered", beer, 5)
)
val wines = listOf(
    Drink("Bolgrad", wine, 5),
    Drink("Vila Crimea", wine, 4),
    Drink("Mikado", wine, 3)
)
val vodkas = listOf(
    Drink("Morosha", vodka, 3),
    Drink("Vozduh", vodka, 3),
    Drink("Zubrovka", vodka, 5)
)
val allDrinks = beers + wines + vodkas