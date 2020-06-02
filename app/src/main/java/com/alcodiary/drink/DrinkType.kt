package com.alcodiary.drink

class DrinkType(val name: String, val minAlco: Int, val maxAlco: Int) {
    val defaultAlco: Int
        get() = (this.minAlco + this.maxAlco) / 2

    override fun toString(): String {
        return name
    }
}