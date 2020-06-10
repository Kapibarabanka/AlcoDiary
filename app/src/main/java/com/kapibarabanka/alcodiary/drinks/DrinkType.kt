package com.kapibarabanka.alcodiary.drinks

class DrinkType(val name: String, val minAlco: Int, val maxAlco: Int) {
    val defaultAlco: Double
        get() = ((this.minAlco + this.maxAlco) / 2).toDouble()

    override fun toString(): String {
        return name
    }
}