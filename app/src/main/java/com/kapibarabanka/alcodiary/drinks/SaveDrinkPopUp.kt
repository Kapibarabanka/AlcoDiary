package com.kapibarabanka.alcodiary.drinks

import android.app.Activity
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import com.kapibarabanka.alcodiary.R
import com.kapibarabanka.alcodiary.allDrinkTypes
import com.kapibarabanka.alcodiary.allDrinks
import kotlinx.android.synthetic.main.pop_up_save_drink.*

class SaveDrinkPopUp : AppCompatActivity() {
    private var selectedDrinkPosition = -1
    private var itemsForTypeSpinner = allDrinkTypes.map { it.name }.toMutableList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.pop_up_save_drink)

        val dm = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(dm)

        val width: Int = (dm.widthPixels * 0.9).toInt()
        val height: Int = (dm.heightPixels * 0.7).toInt()

        window.setLayout(width, height)

        itemsForTypeSpinner.add(getString(R.string.addTypeLabel))

        updateTypeAdapter()
        typeSpinner?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long)  {
                if (position >= allDrinkTypes.count()) {
                    val intent = Intent(applicationContext, SaveDrinkTypePopUp::class.java)
                    startActivityForResult(intent, newTypeRequest)
                }
                else {
                    val selectedType = allDrinkTypes[position]
                    alcoText.setText(selectedType.defaultAlco.toString())
                }
            }
        }

        selectedDrinkPosition = intent.getIntExtra(selectedDrinkPositionExtra, -1)

        if (selectedDrinkPosition != -1) {
            val selectedDrink = allDrinks[selectedDrinkPosition]
            val selectedDrinkTypeIndex = allDrinkTypes.indexOf(selectedDrink.type)

            nameText.setText(selectedDrink.name)
            typeSpinner.setSelection(selectedDrinkTypeIndex)
            ratingBar.rating = selectedDrink.rating
            alcoText.setText(selectedDrink.alcoholVolume.toString())
            commentText.setText(selectedDrink.comment)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == newTypeRequest && resultCode == Activity.RESULT_OK) {
            itemsForTypeSpinner = allDrinkTypes.map { it.name }.toMutableList()
            itemsForTypeSpinner.add(getString(R.string.addTypeLabel))
            updateTypeAdapter()
            typeSpinner?.setSelection(itemsForTypeSpinner.count() - 2)
        }
        else{
            super.onActivityResult(requestCode, resultCode, data)
        }
    }

    fun onSaveClicked(view: View) {
        val name = nameText.text.toString()
        val type = allDrinkTypes[typeSpinner.selectedItemPosition]
        val rating = ratingBar.rating
        val alco = alcoText.text.toString().toDouble()
        val comment = commentText.text.toString()
        if (selectedDrinkPosition == -1) {
            allDrinks.add(0, Drink(name, type, rating, comment))
        }
        else {
            val selectedDrink = allDrinks[selectedDrinkPosition]
            selectedDrink.name = name
            selectedDrink.type = type
            selectedDrink.rating = ratingBar.rating
            selectedDrink.alcoholVolume = alco
            selectedDrink.comment = comment
        }
        finish()
    }

    private fun updateTypeAdapter() {
        val drinkTypeAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, itemsForTypeSpinner)
        drinkTypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item)
        typeSpinner?.adapter = drinkTypeAdapter
    }

}