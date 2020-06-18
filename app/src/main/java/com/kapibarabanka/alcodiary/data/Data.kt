package com.kapibarabanka.alcodiary.data

import com.kapibarabanka.alcodiary.R
import com.kapibarabanka.alcodiary.calendar.Event
import com.kapibarabanka.alcodiary.drinks.Drink
import com.kapibarabanka.alcodiary.drinks.DrinkType
import java.time.LocalDate

val allDrinkTypes = mutableListOf<DrinkType>()
val allDrinks = mutableListOf<Drink>()
val allEvents = mutableListOf<Event>()

// DrinksTypesIcons
//val iconMap = mutableMapOf<String, Int?>(
//        "absinthe" to R.drawable.absinthe_green,
//        "beer_dark" to R.drawable.beer_dark,
//        "beer_green" to R.drawable.beer_green,
//        "beer_light" to R.drawable.beer_light,
//        "cocktail_blue" to R.drawable.cocktail_blue,
//        "cocktail_green" to R.drawable.cocktail_green,
//        "cocktail_red" to R.drawable.cocktail_red,
//        "cocktail_violet" to R.drawable.cocktail_violet,
//        "cognac_brown" to R.drawable.cognac_brown,
//        "flute_green" to R.drawable.flute_green,
//        "flute_pink" to R.drawable.flute_pink,
//        "flute_yellow" to R.drawable.flute_yellow,
//        "long_blue" to R.drawable.long_blue,
//        "long_green" to R.drawable.long_green,
//        "long_red" to R.drawable.long_red,
//        "long_violet" to R.drawable.long_violet,
//        "tincture" to R.drawable.tincture,
//        "vodka" to R.drawable.vodka,
//        "whiskey_blue" to R.drawable.whiskey_blue,
//        "whiskey_brown" to R.drawable.whiskey_brown,
//        "whiskey_green" to R.drawable.whiskey_green,
//        "whiskey_red" to R.drawable.whiskey_red,
//        "whiskey_yellow" to R.drawable.whiskey_yellow,
//        "wine_pink" to R.drawable.wine_pink,
//        "wine_red" to R.drawable.wine_red,
//        "wine_white" to R.drawable.wine_white
//)


// TODO: use content description

var defaultIcon = R.drawable.whiskey_blue

val emptyDrinkType = DrinkType("", 0.0f, 0.0f, defaultIcon)
val emptyDrink = Drink("", emptyDrinkType, 0.0f, "")
val emptyEvent = Event("", LocalDate.MIN)

fun findDrinkType(id: Long): DrinkType {
    return allDrinkTypes.find { it.id == id } ?: emptyDrinkType
}

fun findDrink(id: Long): Drink {
    return allDrinks.find {it.id == id} ?: emptyDrink
}

fun findEvent(id: Long): Event {
    return allEvents.find {it.id == id} ?: emptyEvent
}

fun getNewDrinkTypeId(): Long {
    val max = allDrinkTypes.maxBy { it.id }?.id ?: 5
    return max + 1
}

fun getNewDrinkId(): Long {
    val max = allDrinks.maxBy { it.id }?.id ?: 0
    return max + 1
}

fun getNewEventId(): Long {
    val max = allEvents.maxBy { it.id }?.id ?: 0
    return max + 1
}
