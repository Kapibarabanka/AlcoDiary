package com.kapibarabanka.alcodiary.drinks

import com.kapibarabanka.alcodiary.getNewDrinkTypeId
import java.io.Serializable

class DrinkType(val name: String, val minAlco: Float, val maxAlco: Float, val icon : String) : Serializable {
    var id = getNewDrinkTypeId()
    constructor(idFromDB: Int, name: String, minAlco: Float, maxAlco: Float, icon: String)
            : this(name, minAlco, maxAlco, icon) {id = idFromDB}
    val defaultAlco: Double
        get() = ((this.minAlco + this.maxAlco) / 2).toDouble()

    override fun toString(): String {
        return name
    }
}