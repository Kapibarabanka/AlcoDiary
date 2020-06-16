package com.kapibarabanka.alcodiary.drinks

import java.io.Serializable

class Drink(var name: String, var type: DrinkType, var rating: Float, var comment: String = "") : Serializable {
    var alcoholVolume: Double = type.defaultAlco
}