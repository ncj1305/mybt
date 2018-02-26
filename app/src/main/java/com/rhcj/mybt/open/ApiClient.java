package com.rhcj.mybt.open;

import android.util.Log;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.security.InvalidKeyException;
import java.util.logging.Logger;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import org.codehaus.jackson.map.ObjectMapper;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.Hex;


@SuppressWarnings("unused")
public class ApiClient {
	protected String api_url = "https://api.bithumb.com";
	protected String api_key;
	protected String api_secret;

	public ApiClient() {
	}

	public ApiClient(String api_key, String api_secret) {
		this.api_key = api_key;
		this.api_secret = api_secret;
	}

	/**
	 * 현재의 시간을 ns로 리턴한다.(1/1,000,000,000 초)
	 *
	 * @return int
	 */
	private String usecTime() {
		return String.valueOf(System.currentTimeMillis());
	}

	private String requestPost(String strHost, HashMap<String, String> rgParams, HashMap<String, String> httpHeaders) {
		String response = "";

		HttpRequest request = null;

		if (this.api_key != null && this.api_secret != null) {
			request = new HttpRequest(strHost, "POST");
			request.readTimeout(10000);

			System.out.println("POST ==> " + request.url());

			if (httpHeaders != null && !httpHeaders.isEmpty()) {
				httpHeaders.put("api-client-type", "2");
				request.headers(httpHeaders);
				System.out.println(httpHeaders.toString());
			}
			if (rgParams != null && !rgParams.isEmpty()) {
				request.form(rgParams);
				System.out.println(rgParams.toString());
			}
		} else {
			response = "error : api_key or api_secret is not defined";
		}

		if (request.ok()) {
			response = request.body();
		} else {
			response = "error : " + request.code() + ", message : "
					+ request.body();
		}
		request.disconnect();
		return response;
	}

	private String requestGet(String strHost, String target, String paramString) {
		String response = "";

		HttpRequest request = null;
		if (paramString == null) {
			Log.d("ApiClient", "requestGet : " + strHost + target);
			request = HttpRequest.get(strHost + target);
		} else {
			Log.d("ApiClient", "requestGet : " + strHost + target + "?" + paramString);
			request = HttpRequest.get(strHost + target + "?" + paramString);
		}

		request.readTimeout(10000);

		System.out.println("Response was: " + response);

		if (request.ok()) {
			response = request.body();
		} else {
			response = "error : " + request.code() + ", message : "
					+ request.body();
		}
		request.disconnect();
		return response;
	}

	private HashMap<String, String> getHttpHeaders(String endpoint, HashMap<String, String> rgData, String apiKey, String apiSecret) {
		String strData = Util.mapToQueryString(rgData).replace("?", "");

		String nNonce = usecTime();

		strData = strData.substring(0, strData.length()-1);


		System.out.println("1 : " + strData);

		strData = Util.encodeURIComponent(strData);

		HashMap<String, String> array = new HashMap<String, String>();


		String str = endpoint + ";"	+ strData + ";" + nNonce;

		String encoded = asHex(hmacSha512(str, apiSecret));

		System.out.println("strData was: " + str);
		System.out.println("apiSecret was: " + apiSecret);
		array.put("Api-Key", apiKey);
		array.put("Api-Sign", encoded);
		array.put("Api-Nonce", String.valueOf(nNonce));

		return array;

	}

	private static final String DEFAULT_ENCODING = "UTF-8";
	private static final String HMAC_SHA512 = "HmacSHA512";

	public static byte[] hmacSha512(String value, String key){
		try {
			SecretKeySpec keySpec = new SecretKeySpec(
					key.getBytes(DEFAULT_ENCODING),
					HMAC_SHA512);

			Mac mac = Mac.getInstance(HMAC_SHA512);
			mac.init(keySpec);

			final byte[] macData = mac.doFinal( value.getBytes( ) );
			byte[] hex = new Hex().encode( macData );

			return hex;

		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException(e);
		} catch (InvalidKeyException e) {
			throw new RuntimeException(e);
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException(e);
		}
	}

	public static String asHex(byte[] bytes){
		return new String(Base64.encodeBase64(bytes));
	}

	public String callPrivateApi(String endpoint, HashMap<String, String> params, String requestMethod) {
		String rgResultDecode = "";
		HashMap<String, String> rgParams = new HashMap<String, String>();
		rgParams.put("endpoint", endpoint);

		if (params != null) {
			rgParams.putAll(params);
		}

		String api_host = api_url + endpoint;
		HashMap<String, String> httpHeaders = getHttpHeaders(endpoint, rgParams, api_key, api_secret);

		rgResultDecode = requestPost(api_host, rgParams, httpHeaders);

		if (!rgResultDecode.startsWith("error")) {
			HashMap<String, String> result;
			try {
				result = new ObjectMapper().readValue(rgResultDecode,
						HashMap.class);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return rgResultDecode;
	}

	public String callPublicApi(String endpoint, String target, HashMap<String, String> params) {
		String rgResultDecode = "";
		String paramString = null;
		if (params != null) {
			paramString = Util.mapToQueryString(params).replace("?", "");
		}
		String api_host = api_url + endpoint;

		rgResultDecode = requestGet(api_host, target, paramString);

		if (!rgResultDecode.startsWith("error")) {
			HashMap<String, String> result;
			try {
				result = new ObjectMapper().readValue(rgResultDecode,
						HashMap.class);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return rgResultDecode;
	}
}
