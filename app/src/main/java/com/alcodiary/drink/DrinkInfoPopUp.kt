package com.alcodiary.drink

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.DisplayMetrics
import com.alcodiary.R
import kotlinx.android.synthetic.main.pop_up_drink_info.*

class DrinkInfoPopUp  : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.pop_up_drink_info)

        val dm = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(dm)

        val width: Int = (dm.widthPixels * 0.7).toInt()
        val height: Int = (dm.heightPixels * 0.7).toInt()

        window.setLayout(width, height)

        val selectedDrink = intent.getSerializableExtra(selectedDrinkExtra) as? Drink

        nameText.setText(selectedDrink?.name)
        typeText.setText(selectedDrink?.type.toString())
        markText.setText(selectedDrink?.mark.toString())
        alcoText.setText(selectedDrink?.alco.toString())
        commentText.setText(selectedDrink?.comment.toString())
    }
}