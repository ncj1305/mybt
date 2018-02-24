package com.rhcj.mybt.open;

import java.util.HashMap;

public class Main {
    public static void main(String args[]) {
		ApiClient api = new ApiClient(null,null);
	
		HashMap<String, String> rgParams = new HashMap<String, String>();
		rgParams.put("order_currency", "BTC");
		rgParams.put("payment_currency", "KRW");
	
	
		try {
		    String result = api.callPublicApi("/public/ticker/", "ALL");
		    System.out.println(result);
		} catch (Exception e) {
		    e.printStackTrace();
		}
    }
}

