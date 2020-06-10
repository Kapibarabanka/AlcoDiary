package com.kapibarabanka.alcodiary

import com.kapibarabanka.alcodiary.drinks.Drink
import com.kapibarabanka.alcodiary.drinks.DrinkType

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