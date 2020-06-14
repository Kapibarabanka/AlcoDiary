package com.kapibarabanka.alcodiary.drinks

import java.io.Serializable

class DrinkType(val name: String, val minAlco: Int, val maxAlco: Int) : Serializable {
    val defaultAlco: Double
        get() = ((this.minAlco + this.maxAlco) / 2).toDouble()

    override fun toString(): String {
        return name
    }
}