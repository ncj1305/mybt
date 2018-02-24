package com.rhcj.mybt

/**
 * Created by ncj on 2018-02-21.
 */
class Coin {
    private var name: String = ""
    private var currentPrice: String = ""
    private var openingPrice: String = ""
    private var minPrice: String = ""
    private var maxPrice: String = ""
    private var volume: String = ""
    private var changingRate: String = ""

    constructor(name: String, price: String) {
        this.name = name
        this.currentPrice = price
    }

    constructor(name: String, currentPrice: String, openingPrice: String, minPrice: String, maxPrice: String, volume: String) {
        this.name = name
        this.currentPrice = currentPrice
        this.openingPrice = openingPrice
        this.minPrice = minPrice
        this.maxPrice = maxPrice
        this.volume = volume
        setChangingRate()
    }

    fun updateCoinInfo(currentPrice: String, openingPrice: String, minPrice: String, maxPrice: String, volume: String) {
        this.currentPrice = currentPrice
        this.openingPrice = openingPrice
        this.minPrice = minPrice
        this.maxPrice = maxPrice
        this.volume = volume
        setChangingRate()
    }

    fun getName(): String {
        return this.name
    }

    fun getCurrentPrice(): String {
        return this.currentPrice
    }

    fun getOpeningPrice(): String {
        return this.openingPrice
    }

    fun getMinPrice(): String {
        return this.minPrice
    }

    fun getMaxPrice(): String {
        return this.maxPrice
    }

    fun getVolume(): String {
        return this.volume
    }

    fun getChangingRate(): String {
        return this.changingRate
    }

    fun setName(name: String) {
        this.name = name
    }

    fun setCurrentPrice(price: String) {
        this.currentPrice = price
    }

    fun setOpeningPrice(openingPrice: String) {
        this.openingPrice = openingPrice
    }

    fun setMinPrice(minPrice: String) {
        this.minPrice = minPrice
    }

    fun setMaxPrice(maxPrice: String) {
        this.maxPrice = maxPrice
    }

    fun setVolume(volume: String) {
        this.volume = volume
    }

    private fun setChangingRate() {
        this.changingRate = (((currentPrice.toDouble() - openingPrice.toDouble()) / openingPrice.toDouble())).toString()
    }
}