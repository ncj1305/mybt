package com.rhcj.mybt

/**
 * Created by ncj on 2018-02-26.
 */
class CoinRequestScheme {
    var api = ""
    var target = ""
    var params = HashMap<String, String>()

    constructor(api: String, target: String, params: HashMap<String, String>) {
        this.api = api
        this.target = target
        this.params = params
    }

    constructor(api: String, target: String) {
        this.api = api
        this.target = target
    }
}