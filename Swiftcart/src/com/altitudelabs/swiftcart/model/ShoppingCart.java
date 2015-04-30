package com.altitudelabs.swiftcart.model;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.SharedPreferences;

public class ShoppingCart {

	private static String KEY_SHARED_PREF = "com.swiftcart";
	private static String KEY_SHARED_PREF_SAVED_DATA = "saved data";
	private static String JSON_OBJ_KEY_ITEM_ID = "item id";
	private static String JSON_OBJ_KEY_ITEM_AMT = "amt";

	private static ShoppingCart instance = null;
	private Map<Integer, Integer> addedItems; // <item id, amount>
	SharedPreferences prefs;
	Context mContext;

	public static ShoppingCart getInstance(Context context) {
		if (instance == null) {
			instance = new ShoppingCart(context);
		}
		return instance;
	}

	protected ShoppingCart() {
	}

	protected ShoppingCart(Context context) {
		addedItems = new HashMap<Integer, Integer>();
		mContext = context;
		prefs = mContext.getSharedPreferences(KEY_SHARED_PREF, Context.MODE_PRIVATE);
		try {
			loadData();
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	public void addItem(int itemId, int amount) {
		Integer savedAmount = addedItems.get(Integer.valueOf(itemId));
		// Add amount with bought amount
		int currentAmount = 0;
		if (savedAmount != null) {
			currentAmount = savedAmount.intValue();
		}
		currentAmount += amount;

		addedItems.remove(Integer.valueOf(itemId));
		if (currentAmount > 0) {
			addedItems.put(Integer.valueOf(itemId), Integer.valueOf(currentAmount));
		}
	}

	public void removeItem(int itemId) {
		addedItems.remove(Integer.valueOf(itemId));
	}

	public Integer[] getAllItemIds() {
		return addedItems.keySet().toArray(new Integer[addedItems.size()]);
	}

	public int getItemAmount(int productItemId) {
		return addedItems.get(Integer.valueOf(productItemId)).intValue();
	}

	public float getTotalPrice() {
		float totalPrice = 0;
		Integer[] allItemIds = getAllItemIds();
		for (int i = 0 ; i < allItemIds.length; i++) {
			int itemId = allItemIds[i].intValue();
			int amount = getItemAmount(itemId);

			ProductItem productItem = ProductData.getInstance().getProductItem(itemId);
			float itemPrice = productItem.priceNow;

			totalPrice = totalPrice + amount * itemPrice;
		}
		return totalPrice;
	}

	public void saveData() throws JSONException {
		JSONArray jsonArray = new JSONArray();

		Integer[] allItemIds = getAllItemIds();
		for (int i = 0; i< allItemIds.length; i++) {
			int amount = getItemAmount(allItemIds[i].intValue());
			JSONObject jObj = new JSONObject();
			jObj.put(JSON_OBJ_KEY_ITEM_ID, allItemIds[i].intValue());
			jObj.put(JSON_OBJ_KEY_ITEM_AMT, amount);
			jsonArray.put(jObj);
		}

		String saveData = jsonArray.toString();
		prefs.edit().putString(KEY_SHARED_PREF_SAVED_DATA, saveData).commit();
	}

	private void loadData() throws JSONException {
		String savedData = prefs.getString(KEY_SHARED_PREF_SAVED_DATA, null);
		if (savedData != null) {
			JSONArray jsonArray = new JSONArray(savedData);
			for (int i = 0 ; i < jsonArray.length(); i++) {
				JSONObject jObj = jsonArray.getJSONObject(i);
				addItem(jObj.getInt(JSON_OBJ_KEY_ITEM_ID), jObj.getInt(JSON_OBJ_KEY_ITEM_AMT));
			}
		}
	}
}
