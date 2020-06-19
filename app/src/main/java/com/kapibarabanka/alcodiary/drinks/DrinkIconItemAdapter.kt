package com.kapibarabanka.alcodiary.drinks

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.kapibarabanka.alcodiary.R
import kotlinx.android.synthetic.main.choose_drink_icon_element.view.*

class DrinkIconItemAdapter(ctx: Context, icons: List<DrinkIconItem>) :
    ArrayAdapter<DrinkIconItem>(ctx, 0, icons) {

    override fun getView(position: Int, recycledView: View?, parent: ViewGroup): View {
        return this.createView(position, recycledView, parent)
    }

    override fun getDropDownView(position: Int, recycledView: View?, parent: ViewGroup): View {
        return this.createView(position, recycledView, parent)
    }

    private fun createView(position: Int, recycledView: View?, parent: ViewGroup): View {
        val icon = getItem(position)
        val view = recycledView ?: LayoutInflater.from(context).inflate(
            R.layout.choose_drink_icon_element,
            parent,
            false
        )
        icon.iconId?.let { view.icon_image_in_choose_menu.setImageResource(it) }
        return view
    }
}
