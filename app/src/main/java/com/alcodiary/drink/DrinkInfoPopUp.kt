package com.alcodiary.drink

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.DisplayMetrics
import android.view.View
import com.alcodiary.R
import com.alcodiary.allDrinks
import kotlinx.android.synthetic.main.pop_up_drink_info.*

class DrinkInfoPopUp  : AppCompatActivity() {
    var selectedDrinkPosition = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.pop_up_drink_info)

        val dm = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(dm)

        val width: Int = (dm.widthPixels * 0.7).toInt()
        val height: Int = (dm.heightPixels * 0.7).toInt()

        window.setLayout(width, height)

        selectedDrinkPosition = intent.getIntExtra(selectedDrinkPositionExtra, -1)
        val selectedDrink = allDrinks[selectedDrinkPosition]

        nameText.setText(selectedDrink.name)
        typeText.setText(selectedDrink.type.toString())
        markText.setText(selectedDrink.mark.toString())
        alcoText.setText(selectedDrink.alco.toString())
        commentText.setText(selectedDrink.comment)
    }

    fun onDeleteClicked(view: View) {
        allDrinks.removeAt(selectedDrinkPosition)
        finish()
    }

    fun onEditClicked(view: View) {

    }
}