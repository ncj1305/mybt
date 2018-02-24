package com.rhcj.mybt

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import java.text.NumberFormat
import java.util.*


/**
 * Created by ncj on 2018-02-21.
 */
class CoinAdapter (val context: Context, val coinList: ArrayList<Coin>): BaseAdapter() {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view: View?
        val vh: CoinViewHolder
        val layoutInflater: LayoutInflater = LayoutInflater.from(context)

        if (convertView == null) {
            view = layoutInflater.inflate(R.layout.coin_item, parent, false)
            vh = CoinViewHolder(view)
            view.tag = vh
        } else {
            view = convertView
            vh = view.tag as CoinViewHolder
        }

        val currencyFomatter = NumberFormat.getNumberInstance(Locale.KOREA)
        val portionFomatter = NumberFormat.getPercentInstance()
        portionFomatter.minimumFractionDigits = 2

        vh.tvName.text = coinList[position].getName()
        vh.tvPrice.text = currencyFomatter.format(coinList[position].getCurrentPrice().toDouble().toInt())
        vh.tvMinPrice.text = "Min : " + currencyFomatter.format(coinList[position].getMinPrice().toDouble().toInt())
        vh.tvMaxPrice.text = "Max : " + currencyFomatter.format(coinList[position].getMaxPrice().toDouble().toInt())
        vh.tvVolume.text = "Vol : " + currencyFomatter.format(coinList[position].getVolume().toDouble())

        val changingRate = coinList[position].getChangingRate().toDouble()
        vh.tvChangingRate.text = portionFomatter.format(changingRate)
        if (changingRate > 0) {
            vh.tvChangingRate.setTextColor(Color.parseColor("#f41f1f"))
        } else {
            vh.tvChangingRate.setTextColor(Color.parseColor("#3240bc"))
        }

        return view!!
    }

    override fun getItem(position: Int): Any {
        return coinList[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getCount(): Int {
        return coinList.size
    }
}