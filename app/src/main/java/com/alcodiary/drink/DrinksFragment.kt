package com.alcodiary.drink

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.alcodiary.R
import com.alcodiary.allDrinks
import kotlinx.android.synthetic.main.fragment_drinks.*

class DrinksFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true
    }

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
            adapter = DrinkListAdapter(allDrinks)
        }
        fabAddDrink.setOnClickListener {innerView ->
            onFabAddDrinkClicked(innerView)
        }
    }

    fun onFabAddDrinkClicked (view: View) {
        val intent = Intent(activity?.applicationContext, AddDrinkPopUp::class.java)
        startActivity(intent)
    }

    companion object {
        fun newInstance(): DrinksFragment = DrinksFragment()
    }

}