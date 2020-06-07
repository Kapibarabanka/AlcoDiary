package com.kapibarabanka.alcodiary.drink

class Drink(var name: String, var type: DrinkType, mark: Int, comment: String = "") {
    var alco: Int = type.defaultAlco
    var mark: Int = mark
    var comment: String = comment
}