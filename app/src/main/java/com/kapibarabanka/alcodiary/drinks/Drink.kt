package com.kapibarabanka.alcodiary.drinks

import com.kapibarabanka.alcodiary.findDrinkType
import com.kapibarabanka.alcodiary.getNewDrinkId
import java.io.Serializable

class Drink(var name: String, var type: DrinkType, var rating: Float, var comment: String = "") : Serializable {
    var id = getNewDrinkId()
    constructor(idFromDB: Int, name: String, typeId: Int, rating: Float, comment: String = "")
            : this(name, findDrinkType(typeId), rating, comment) {id = idFromDB}
    var alcoholVolume: Double = type.defaultAlco
}