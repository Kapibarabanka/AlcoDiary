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

const val selectedDrinkExtra = "SELECTED_DRINK"

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
        // RecyclerView node initialized here
        recycler_view_drinks.apply {
            // set a LinearLayoutManager to handle Android
            // RecyclerView behavior
            layoutManager = LinearLayoutManager(activity)
            // set the custom adapter to the RecyclerView
            adapter = drinksAdapter
        }

        fabAddDrink.setOnClickListener {innerView ->
            onFabAddDrinkClicked(innerView)
        }
    }

    override fun onResume() {
        super.onResume()
        drinksAdapter.notifyDataSetChanged()
    }

    fun onFabAddDrinkClicked (view: View) {
        val intent = Intent(activity?.applicationContext, AddDrinkPopUp::class.java)
        startActivity(intent)
    }

    companion object {
        fun newInstance(): DrinksFragment = DrinksFragment()
    }

    override fun onDrinkClick(position: Int) {
        val intent = Intent(activity?.applicationContext, DrinkInfoPopUp::class.java)
        intent.putExtra(selectedDrinkExtra, allDrinks[position])
        startActivity(intent)
    }

}

