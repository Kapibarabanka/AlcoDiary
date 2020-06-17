package com.kapibarabanka.alcodiary.drinks

import com.kapibarabanka.alcodiary.data.getNewDrinkId
import java.io.Serializable

class Drink(var name: String, var type: DrinkType, var rating: Float, var comment: String = "") : Serializable {
    var id = getNewDrinkId()
    constructor(idFromDB: Int, name: String, type: DrinkType, rating: Float, comment: String = "")
            : this(name, type, rating, comment) {id = idFromDB}
    var alcoholVolume: Double = type.defaultAlco
}