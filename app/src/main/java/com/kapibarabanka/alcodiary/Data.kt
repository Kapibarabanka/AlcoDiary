package com.kapibarabanka.alcodiary

import android.widget.ImageView
import com.kapibarabanka.alcodiary.calendar.DrinkInEvent
import com.kapibarabanka.alcodiary.calendar.Event
import com.kapibarabanka.alcodiary.drinks.Drink
import com.kapibarabanka.alcodiary.drinks.DrinkType
import java.time.LocalDate

var drinkTypeId = 0
var drinkId = 0
var eventId = 0

// Drink types
val beer = DrinkType("Beer", 4.0f, 7.0f, "beer_dark")
val wine = DrinkType("Wine", 10.0f, 17.0f, "wine_red")
val vodka = DrinkType("Vodka", 37.0f, 42.0f, "vodka")
val allDrinkTypes = mutableListOf(beer, wine, vodka)

// DrinksTypesIcons
val iconMap = mutableMapOf<String, Int?>(
    "absinthe" to R.drawable.absinthe_green,
    "beer_dark" to R.drawable.beer_dark,
    "beer_green" to R.drawable.beer_green,
    "beer_light" to R.drawable.beer_light,
    "cocktail_blue" to R.drawable.cocktail_blue,
    "cocktail_green" to R.drawable.cocktail_green,
    "cocktail_red" to R.drawable.cocktail_red,
    "cocktail_violet" to R.drawable.cocktail_violet,
    "cognac_brown" to R.drawable.cognac_brown,
    "flute_green" to R.drawable.flute_green,
    "flute_pink" to R.drawable.flute_pink,
    "flute_yellow" to R.drawable.flute_yellow,
    "long_blue" to R.drawable.long_blue,
    "long_green" to R.drawable.long_green,
    "long_red" to R.drawable.long_red,
    "long_violet" to R.drawable.long_violet,
    "tincture" to R.drawable.tincture,
    "vodka" to R.drawable.vodka,
    "whiskey_blue" to R.drawable.whiskey_blue,
    "whiskey_brown" to R.drawable.whiskey_brown,
    "whiskey_green" to R.drawable.whiskey_green,
    "whiskey_red" to R.drawable.whiskey_red,
    "whiskey_yellow" to R.drawable.whiskey_yellow,
    "wine_pink" to R.drawable.wine_pink,
    "wine_red" to R.drawable.wine_red,
    "wine_white" to R.drawable.wine_white
)

val allIcons = mutableListOf<String>(
    "absinthe",
    "beer_dark",
    "beer_green",
    "beer_light",
    "cocktail_blue",
    "cocktail_green",
    "cocktail_red",
    "cocktail_violet",
    "cognac_brown",
    "flute_green",
    "flute_pink",
    "flute_yellow",
    "long_blue",
    "long_green",
    "long_red",
    "long_violet",
    "tincture",
    "vodka",
    "whiskey_blue",
    "whiskey_brown",
    "whiskey_green",
    "whiskey_red",
    "whiskey_yellow",
    "wine_pink",
    "wine_red",
    "wine_white")

// TODO: use content description

var defaultIcon : String = "whiskey_blue"

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
        DrinkInEvent(vodkas[0] , 0.3f),
        DrinkInEvent(beers[1], 1.5f)
    )),
    Event("second", LocalDate.of(2020, 5, 9), mutableListOf(
        DrinkInEvent(vodkas[0] , 0.3f),
        DrinkInEvent(beers[0] , 1.0f),
        DrinkInEvent(beers[1] , 1.0f)
    )),
    Event("third", LocalDate.of(2020, 5, 15), mutableListOf(
        DrinkInEvent(wines[0], 1.4f)
    )),
    Event("fourth", LocalDate.of(2020, 6, 2), mutableListOf(
        DrinkInEvent(wines[1] , 1.4f),
        DrinkInEvent(wines[0] , 1.0f),
        DrinkInEvent(vodkas[2] , 0.1f),
        DrinkInEvent(beers[1] , 0.5f)
    )),
    Event("fifth", LocalDate.of(2020, 6, 11), mutableListOf(
        DrinkInEvent(beers[2] , 1.0f),
        DrinkInEvent(wines[2] , 1.0f)
    ))
)

val emptyDrinkType = DrinkType("", 0.0f, 0.0f, defaultIcon)
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

fun getNewEventId(): Int {
    return eventId++
}

fun getNewDrinkId(): Int {
    return drinkId++
}

fun getNewDrinkTypeId(): Int {
    return drinkTypeId++
}
