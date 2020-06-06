package com.alcodiary.drink

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.alcodiary.R
import com.alcodiary.allDrinks
import kotlinx.android.synthetic.main.fragment_drinks.*

const val selectedDrinkPositionExtra = "SELECTED_DRINK_POSITION"

class DrinksFragment : Fragment(), DrinkListAdapter.OnDrinkListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true
    }

    private val drinksAdapter = DrinkListAdapter(allDrinks, this)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.fragment_drinks, null)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recycler_view_drinks.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = drinksAdapter
        }

        fabAddDrink.setOnClickListener {innerView ->
            onFabAddDrinkClicked(innerView)
        }
    }

    override fun onPause() {
        super.onPause()
        fabAddDrink.hide()
    }

    override fun onResume() {
        super.onResume()
        fabAddDrink.show()
        drinksAdapter.notifyDataSetChanged()
    }

    fun onFabAddDrinkClicked (view: View) {
        val intent = Intent(activity?.applicationContext, SaveDrinkPopUp::class.java)
        startActivity(intent)
    }

    companion object {
        fun newInstance(): DrinksFragment = DrinksFragment()
    }

    override fun onDrinkClick(position: Int) {
        val intent = Intent(activity?.applicationContext, DrinkInfoPopUp::class.java)
        intent.putExtra(selectedDrinkPositionExtra, position)
        startActivity(intent)
    }

}

