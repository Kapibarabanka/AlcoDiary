package com.kapibarabanka.alcodiary.drinks

class Drink(var name: String, var type: DrinkType, ratingInit: Float, var comment: String = "") {
    var alcoholVolume: Double = type.defaultAlco
    var rating : Float = ratingInit    // This value will be updated, when user rates drink
}