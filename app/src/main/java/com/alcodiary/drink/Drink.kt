package com.alcodiary.drink

import java.io.Serializable

class Drink(val name: String, val type: DrinkType, mark: Int, comment: String = "") {
    var alco: Int = type.defaultAlco
    var mark: Int = mark
    var comment: String = comment
}