package com.kapibarabanka.alcodiary.calendar

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.DisplayMetrics
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import com.kapibarabanka.alcodiary.R
import com.kapibarabanka.alcodiary.data.ADMIN_USER
import com.kapibarabanka.alcodiary.data.LocalDBAdapter
import com.kapibarabanka.alcodiary.data.allDrinkTypes
import com.kapibarabanka.alcodiary.data.allDrinks
import kotlinx.android.synthetic.main.pop_up_add_drink_to_event.*


class AddDrinkToEventPopUp : AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.pop_up_add_drink_to_event)

        val dm = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(dm)

        val width: Int = (dm.widthPixels * 0.6).toInt()
        val height: Int = (dm.heightPixels * 0.5).toInt()

        window.setLayout(width, height)

        //itemsForDrinksSpinner.add(getString(R.string.addDrinkLabel))
        updateDrinkAdapter()

        drinkSpinner?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long)  {
//                if (position >= allDrinkTypes.count()) {
//                    val intent = Intent(applicationContext, SaveDrinkTypePopUp::class.java)
//                    startActivityForResult(intent, newTypeRequest)
//                }
//                else {
//                    val selectedType = allDrinkTypes[position]
//                    alcoText.setText(selectedType.defaultAlco.toString())
//                }
            }
        }
    }

    fun onAddClicked(view: View) {
        val drink = allDrinks[drinkSpinner.selectedItemPosition]
        val amount = amountText.text.toString().toFloat()
        val intent = Intent()
        intent.putExtra(addedDrinkExtra, drink)
        intent.putExtra(addedAmountExtra, amount)
        setResult(Activity.RESULT_OK, intent)
        finish()
    }

    private fun updateDrinkAdapter() {
        if (allDrinks.isEmpty()) {
            val dbAdapter = LocalDBAdapter(this, ADMIN_USER)
            allDrinkTypes.clear()
            allDrinks.clear()
            dbAdapter.open()
            allDrinkTypes.addAll(dbAdapter.getAllTypes())
            allDrinks.addAll(dbAdapter.getAllDrinks())
            dbAdapter.close()
        }
        val itemsForDrinksSpinner = allDrinks.map { it.type.toString()+" '"+it.name+"'" }.toMutableList()
        val drinkAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, itemsForDrinksSpinner)
        drinkAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item)
        drinkSpinner?.adapter = drinkAdapter
    }
}