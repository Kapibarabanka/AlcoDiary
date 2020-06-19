package com.kapibarabanka.alcodiary.drinks

import com.kapibarabanka.alcodiary.data.iconMap

class DrinkIconItem(var name: String) {
    var iconId : Int? = null
    init {
        iconId = iconMap[name]
    }
}