package com.alcodiary.drink

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import com.alcodiary.R
import com.alcodiary.allDrinkTypes
import com.alcodiary.allDrinks
import kotlinx.android.synthetic.main.pop_up_add_drink.*


class AddDrinkPopUp : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.pop_up_add_drink)

        val dm = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(dm)

        val width: Int = (dm.widthPixels * 0.7).toInt()
        val height: Int = (dm.heightPixels * 0.7).toInt()

        window.setLayout(width, height)

        val drinkTypeAdapter = ArrayAdapter<DrinkType>(this, android.R.layout.simple_spinner_item, allDrinkTypes)
        drinkTypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item)
        typeSpinner?.adapter = drinkTypeAdapter
        typeSpinner?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long)  {
                onDrinkTypeSelected(parent, view, position, id)
            }
        }
    }

    fun onDrinkTypeSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        val selectedType = allDrinkTypes[position]
        alcoText.setText(selectedType.defaultAlco.toString())
    }

    fun onSaveClicked(view: View) {
        val name = nameText.text.toString()
        val type = allDrinkTypes[typeSpinner.selectedItemPosition]
        val mark = markText.text.toString().toInt()
        val comment = commentText.text.toString()
        allDrinks.add(0, Drink(name, type, mark, comment))
        finish()
    }

}