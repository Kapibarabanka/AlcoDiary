package com.kapibarabanka.alcodiary.drinks

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.DisplayMetrics
import android.view.View
import com.kapibarabanka.alcodiary.R
import com.kapibarabanka.alcodiary.data.ADMIN_USER
import com.kapibarabanka.alcodiary.data.LocalDBAdapter
import com.kapibarabanka.alcodiary.data.allDrinks
import kotlinx.android.synthetic.main.pop_up_drink_info.*

class DrinkInfoPopUp : AppCompatActivity() {

    var selectedDrinkPosition = -1
    lateinit var selectedDrink: Drink

    lateinit var dbAdapter: LocalDBAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.pop_up_drink_info)

        val dm = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(dm)

        val width: Int = (dm.widthPixels * 0.9).toInt()
        val height: Int = (dm.heightPixels * 0.7).toInt()

        window.setLayout(width, height)

        dbAdapter = LocalDBAdapter(this, ADMIN_USER)

        selectedDrinkPosition = intent.getIntExtra(selectedDrinkPositionExtra, -1)
        selectedDrink = allDrinks[selectedDrinkPosition]
        showDrinkInfo(selectedDrink)
    }

    override fun onResume() {
        super.onResume()
        showDrinkInfo(selectedDrink)
    }

    fun onDeleteClicked(view: View) {
        val drinkToRemove = allDrinks[selectedDrinkPosition]
        dbAdapter.open()
        dbAdapter.deleteDrink(drinkToRemove)
        dbAdapter.close()

        allDrinks.removeAt(selectedDrinkPosition)
        finish()
    }

    fun onEditClicked(view: View) {
        val intent = Intent(applicationContext, SaveDrinkPopUp::class.java)
        intent.putExtra(selectedDrinkPositionExtra, selectedDrinkPosition)
        startActivity(intent)
    }

    private fun showDrinkInfo(drink: Drink) {
        nameText.text = drink.name
        typeText.text = drink.type.toString()
        RateDrinkConst.rating = drink.rating
        alcoText.text = drink.alcoholVolume.toString()
        commentText.text = drink.comment
    }
}