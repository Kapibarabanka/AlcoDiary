package com.alcodiary.drink

import java.io.Serializable

class DrinkType(val name: String, val minAlco: Int, val maxAlco: Int): Serializable {
    val defaultAlco: Int
        get() = (this.minAlco + this.maxAlco) / 2

    override fun toString(): String {
        return name
    }
}