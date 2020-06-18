package com.kapibarabanka.alcodiary.drinks

import java.io.Serializable

class DrinkType(var name: String, var minAlco: Float, var maxAlco: Float, val icon: Int) : Serializable {
    var id: Long = 0
    constructor(idFromDB: Long, name: String, minAlco: Float, maxAlco:Float, icon: Int)
            : this(name, minAlco, maxAlco, icon) {id = idFromDB}
    val defaultAlco: Double
        get() = ((this.minAlco + this.maxAlco) / 2).toDouble()

    override fun toString(): String {
        return name
    }
}