package com.alcodiary.drink

import java.io.Serializable

class Drink(var name: String, var type: DrinkType, mark: Int, comment: String = "") {
    var alco: Int = type.defaultAlco
    var mark: Int = mark
    var comment: String = comment
}