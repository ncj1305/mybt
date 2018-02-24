package com.rhcj.mybt

import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.v7.app.AppCompatActivity
import android.widget.ListView
import com.rhcj.mybt.open.ApiClient
import java.util.*
import android.os.AsyncTask
import org.json.JSONObject
import kotlin.collections.ArrayList


/**
 * Created by ncj on 2018-02-21.
 */
class MainActivity: AppCompatActivity() {
    private val context = this
    private val coinList = ArrayList<Coin>()
    private var coinAdapter = CoinAdapter(context, coinList)
    private val apiClient = ApiClient()
    private var currentPriceList: ListView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        coinAdapter = CoinAdapter(context, coinList)
        currentPriceList = findViewById<ListView>(R.id.currentPriceList)
        currentPriceList?.adapter = coinAdapter

        val fab = findViewById<FloatingActionButton>(R.id.fab)
        fab.setOnClickListener({
            CoinInfoTask().run()
        })
    }

    @Override
    override fun onResume() {
        super.onResume()
        val coinInfoTask = CoinInfoTask()
        Timer().schedule(coinInfoTask, 0, 5000)
    }

    private inner class PriceRequestTask : AsyncTask<Pair<String, String>, Int, String>() {
        override fun doInBackground(vararg pairs: Pair<String, String>): String? {
            return apiClient.callPublicApi(pairs[0].first, pairs[0].second)
        }

        override fun onPostExecute(result: String) {
            println(result)
            buildPriceList(result)
        }

        fun buildPriceList(result: String) {
            val result = JSONObject(result)
            val data = result.getJSONObject("data")
            for (key in data.keys()) {
                if (key != "date") {
                    val coinCandidate = data.getJSONObject(key)
                    println(coinCandidate.toString())
                    if (coinCandidate != null && coinCandidate.has("closing_price")) {
                        getCoinFromList(coinList, key)
                                ?.updateCoinInfo(   // if getCoinFromList is not null
                                        coinCandidate.getString("closing_price"),
                                        coinCandidate.getString("opening_price"),
                                        coinCandidate.getString("min_price"),
                                        coinCandidate.getString("max_price"),
                                        coinCandidate.getString("volume_1day"))
                                ?: coinList.add(    // if getCoinFromList is null
                                Coin(key,
                                        coinCandidate.getString("closing_price"),
                                        coinCandidate.getString("opening_price"),
                                        coinCandidate.getString("min_price"),
                                        coinCandidate.getString("max_price"),
                                        coinCandidate.getString("volume_1day")))
                        coinAdapter.notifyDataSetChanged()
                    }
                }
            }
        }

        private fun getCoinFromList(coinList: ArrayList<Coin>, coinName: String): Coin? {
            var coinIterator = coinList.iterator()
            while (coinIterator.hasNext()) {
                val coin = coinIterator.next()
                if (coin.getName() == coinName) {
                    return coin
                }
            }
            return null
        }
    }

    private inner class CoinInfoTask: TimerTask() {
        override fun run() {
            val priceAllPair = Pair("/public/ticker/", "ALL")
            PriceRequestTask().execute(priceAllPair)
        }
    }
}