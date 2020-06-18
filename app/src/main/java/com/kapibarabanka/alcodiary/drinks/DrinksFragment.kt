package com.kapibarabanka.alcodiary.drinks

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.kapibarabanka.alcodiary.R
import com.kapibarabanka.alcodiary.data.*
import kotlinx.android.synthetic.main.fragment_drinks.*

const val selectedDrinkPositionExtra = "SELECTED_DRINK_POSITION"
const val newTypeRequest = 1

class DrinksFragment : Fragment(), OnDrinkListener {

    lateinit var dbAdapter: LocalDBAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true
    }

    private lateinit var drinksAdapter: DrinksListAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_drinks, null)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val notNullActivity = activity
        if (notNullActivity != null){
            dbAdapter = LocalDBAdapter(notNullActivity, ADMIN_USER)
            loadDrinksAndTypes()
        }

        drinksAdapter = DrinksListAdapter(allDrinks, this)

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
        loadDrinksAndTypes()
        drinksAdapter.notifyDataSetChanged()
    }

    private fun onFabAddDrinkClicked (view: View) {
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

    private fun loadDrinksAndTypes() {
        allDrinkTypes.clear()
        allDrinks.clear()
        dbAdapter.open()
        allDrinkTypes.addAll(dbAdapter.getAllTypes())
        allDrinks.addAll(dbAdapter.getAllDrinks())
        dbAdapter.close()
    }

}

