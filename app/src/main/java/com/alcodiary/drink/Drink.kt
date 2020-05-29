package com.alcodiary.drink

class Drink(val name: String, val type: DrinkType, mark: Int) {
    var alco: Int = type.defaultAlco
    var mark: Int = mark
    var comment: String = ""
}