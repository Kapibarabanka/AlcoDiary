package com.kapibarabanka.alcodiary.drinks

import java.io.Serializable

class DrinkType(var name: String, var minAlco: Float, var maxAlco: Float) : Serializable {
    var id: Long = 0
    constructor(idFromDB: Long, name: String, minAlco: Float, maxAlco:Float)
            : this(name, minAlco, maxAlco) {id = idFromDB}
    val defaultAlco: Double
        get() = ((this.minAlco + this.maxAlco) / 2).toDouble()

    override fun toString(): String {
        return name
    }
}