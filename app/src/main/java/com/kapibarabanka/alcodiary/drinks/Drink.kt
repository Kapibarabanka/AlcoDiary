package com.kapibarabanka.alcodiary.drinks

import java.io.Serializable

class Drink(var name: String, var type: DrinkType, ratingInit: Float, var comment: String = "") : Serializable {
    var alcoholVolume: Double = type.defaultAlco
    var rating : Float = ratingInit    // This value will be updated, when user rates drink
}