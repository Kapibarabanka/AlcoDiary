package com.kapibarabanka.alcodiary.drinks

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.DisplayMetrics
import android.view.View
import com.kapibarabanka.alcodiary.R
import com.kapibarabanka.alcodiary.allDrinks
import kotlinx.android.synthetic.main.pop_up_drink_info.*

class DrinkInfoPopUp  : AppCompatActivity() {

    var selectedDrinkPosition = -1
    lateinit var selectedDrink: Drink

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.pop_up_drink_info)

        val dm = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(dm)

        val width: Int = (dm.widthPixels * 0.7).toInt()
        val height: Int = (dm.heightPixels * 0.7).toInt()

        window.setLayout(width, height)

        selectedDrinkPosition = intent.getIntExtra(selectedDrinkPositionExtra, -1)
        selectedDrink = allDrinks[selectedDrinkPosition]
        showDrinkinfo(selectedDrink)
    }

    override fun onResume() {
        super.onResume()
        showDrinkinfo(selectedDrink)
    }

    fun onDeleteClicked(view: View) {
        allDrinks.removeAt(selectedDrinkPosition)
        finish()
    }

    fun onEditClicked(view: View) {
        val intent = Intent(applicationContext, SaveDrinkPopUp::class.java)
        intent.putExtra(selectedDrinkPositionExtra, selectedDrinkPosition)
        startActivity(intent)
    }

    private fun showDrinkinfo(drink: Drink) {
        nameText.setText(drink.name)
        typeText.setText(drink.type.toString())
        markText.setText(drink.mark.toString())
        alcoText.setText(drink.alco.toString())
        commentText.setText(drink.comment)
    }
}