package com.kapibarabanka.alcodiary.drinks

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.DisplayMetrics
import android.view.View
import android.widget.AdapterView
import com.kapibarabanka.alcodiary.R
import com.kapibarabanka.alcodiary.data.*
import kotlinx.android.synthetic.main.pop_up_save_drink.*
import kotlinx.android.synthetic.main.pop_up_save_drink_type.*
import kotlinx.android.synthetic.main.pop_up_save_drink_type.nameText

class SaveDrinkTypePopUp : AppCompatActivity() {

    lateinit var dbAdapter: LocalDBAdapter
    var selectedIcon : String = "absinthe"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.pop_up_save_drink_type)

        val dm = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(dm)

        val width: Int = (dm.widthPixels * 0.9).toInt()
        val height: Int = (dm.heightPixels * 0.8).toInt()

        window.setLayout(width, height)

        dbAdapter = LocalDBAdapter(this, ADMIN_USER)

        var iconObjects = allIcons.map {DrinkIconItem(it)}
        spinnerIcon.adapter = DrinkIconItemAdapter(this, iconObjects)
        spinnerIcon?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long)  {
                selectedIcon = allIcons[position]
            }
        }

    }

    fun onSaveClicked(view: View) {
        val name = nameText.text.toString()
        val minAlco = minAlcoText.text.toString().toFloat()
        val maxAlco = maxAlcoText.text.toString().toFloat()

        val newType = DrinkType(name, minAlco, maxAlco, selectedIcon)

        dbAdapter.open()
        dbAdapter.insertType(newType)
        dbAdapter.close()

        allDrinkTypes.add(newType)

        setResult(Activity.RESULT_OK, Intent())
        finish()
    }
}