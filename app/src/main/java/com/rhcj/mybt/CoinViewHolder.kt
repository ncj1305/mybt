package com.rhcj.mybt

import android.view.View
import android.widget.TextView

/**
 * Created by ncj on 2018-02-21.
 */
class CoinViewHolder(view: View?) {
    val tvName: TextView
    val tvPrice: TextView
    val tvMinPrice: TextView
    val tvMaxPrice: TextView
    val tvVolume: TextView
    val tvChangingRate: TextView

    init {
        this.tvName = view?.findViewById<TextView>(R.id.tvName) as TextView
        this.tvPrice = view?.findViewById<TextView>(R.id.tvPrice) as TextView
        this.tvMinPrice = view?.findViewById<TextView>(R.id.tvMinPrice) as TextView
        this.tvMaxPrice = view?.findViewById<TextView>(R.id.tvMaxPrice) as TextView
        this.tvVolume = view?.findViewById<TextView>(R.id.tvVolume) as TextView
        this.tvChangingRate = view?.findViewById<TextView>(R.id.tvChangingRate) as TextView
    }
}