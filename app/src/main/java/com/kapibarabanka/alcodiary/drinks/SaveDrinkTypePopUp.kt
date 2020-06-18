package com.kapibarabanka.alcodiary.drinks

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.DisplayMetrics
import android.view.View
import com.kapibarabanka.alcodiary.R
import com.kapibarabanka.alcodiary.data.*
import kotlinx.android.synthetic.main.pop_up_save_drink_type.*

class SaveDrinkTypePopUp : AppCompatActivity() {

    lateinit var dbAdapter: LocalDBAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.pop_up_save_drink_type)

        val dm = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(dm)

        val width: Int = (dm.widthPixels * 0.9).toInt()
        val height: Int = (dm.heightPixels * 0.7).toInt()

        window.setLayout(width, height)

        dbAdapter = LocalDBAdapter(this, ADMIN_USER)
    }

    fun onChooseIconClicked(view: View) {
        // TODO : make function, that opens ChooseDrinkIconPopUp and sets image
    }

    fun onSaveClicked(view: View) {
        val name = nameText.text.toString()
        val minAlco = minAlcoText.text.toString().toFloat()
        val maxAlco = maxAlcoText.text.toString().toFloat()

        // TODO: save alcoholtype
        val newType = DrinkType(name, minAlco, maxAlco, "flute_pink")

        dbAdapter.open()
        dbAdapter.insertType(newType)
        dbAdapter.close()

        allDrinkTypes.add(newType)

        setResult(Activity.RESULT_OK, Intent())
        finish()
    }
}