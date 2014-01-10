package com.maknc.valentine;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.maknc.valentine.adapters.CatArrayAdapter;
import com.maknc.valentine.parsers.JSONParser;

public class CatListFragment extends Fragment {

	private static final int JSON_CAT_URL = R.raw.categories;

	private SharedPreferences sharedPref;
	private CatArrayAdapter mArrayAdapter;
	private ListView mListView;
	private View mRootView;
	List<HashMap<String, String>> cList = new ArrayList<HashMap<String, String>>();

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		mRootView = inflater.inflate(R.layout.fragment_tost_list, container,
				false);

		/* Read favorites list */
		sharedPref = getActivity().getSharedPreferences(Config.PREFS,
				Context.MODE_PRIVATE);
		String defValue = "";
		String fav = sharedPref.getString(Config.PREFS_FAVORITES_KEY, defValue);

		cList = getCategories(JSON_CAT_URL);

		// Log.d("AAA", "qList: " + qList);

		mArrayAdapter = new CatArrayAdapter(getActivity(), cList);

		mListView = (ListView) mRootView.findViewById(R.id.lvQuotesFrag);

		// Log.d("AAA", "mList: " + mListView);

		// Set the ArrayAdapter as the ListView's adapter.
		mListView.setAdapter(mArrayAdapter);
		mListView.setTextFilterEnabled(true);

		/* --- Making list clickable --- */
		mListView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View v,
					int position, long id) {

				String pickedId = mArrayAdapter.getItem(position).get(
						Config.KEY_CAT_ID);

				Intent mIntent = new Intent(getActivity(), DetailActivity.class);
				mIntent.putExtra(Config.TAG_CAT_ID, pickedId);
				mIntent.putExtra(Config.TAG_DATA_TYPE,
						Config.INTENT_DATA_TYPE_CATEGORIES);
				startActivity(mIntent);

			}
		});

		return mRootView;
	}

	@Override
	public void onResume() {
		super.onResume();

		/* Read favorites list */
		sharedPref = getActivity().getSharedPreferences(Config.PREFS,
				Context.MODE_PRIVATE);
		String defValue = "";
		String favReload = sharedPref.getString(Config.PREFS_FAVORITES_KEY,
				defValue);

		// mStarEmpty.setText(favReload);

		/* Reload */
		cList.clear();
		cList = getCategories(JSON_CAT_URL);
		// Log.d("OUT", tList + "");

		if (mArrayAdapter == null) {
			mArrayAdapter = new CatArrayAdapter(getActivity(), cList);
			mListView.setAdapter(mArrayAdapter);
			// Log.d("OUT", "Adapter NULL");
		} else {
			// Log.d("OUT", "Adapter EXIST");
			mArrayAdapter.updateItemsList(cList);
		}

		/*
		 * // Works but not good mArrayAdapter = new
		 * TostsArrayAdapter(getActivity(), tList);
		 * mListView.setAdapter(mArrayAdapter);
		 */

	};

	/**
	 * Method for read JSON and save into List of HashMaps
	 * 
	 * @param url
	 * @return
	 */
	private ArrayList<HashMap<String, String>> getCategories(int ResID) {

		ArrayList<HashMap<String, String>> mItemList = new ArrayList<HashMap<String, String>>();

		boolean isFav = false;

		// Creating JSON Parser instance
		JSONParser jParser = new JSONParser();

		// getting JSON string from URL
		JSONObject jObject = jParser.getJSONFromRaw(getActivity(), ResID);

		if (Config.DEBUG_MODE) {
			Log.i("TEST", "jObject: " + jObject);
		}

		JSONArray jArray = null;

		try {
			jArray = jObject.getJSONArray(Config.JSON_CAT_ARRAY);
		} catch (JSONException e) {
			e.printStackTrace();
		}

		if (Config.DEBUG_MODE) {
			Log.i("TEST", "jArray: " + jArray);
		}

		// String[] Items = new String[jArray.length()];

		for (int i = 0; i < jArray.length(); i++) {
			try {

				JSONObject oneObject = jArray.getJSONObject(i);
				// Pulling items from the array
				// Items[i] = oneObject.getString(Config.JSON_ID);

				String currentCatId = oneObject.getString(Config.JSON_CAT_ID);
				String currentCatTitle = oneObject
						.getString(Config.JSON_CAT_TITLE);
				String currentCatImage = oneObject
						.getString(Config.JSON_CAT_IMG);

				// isFav = checkIsFavorite(fv, currentId);

				mItemList.add(putData(currentCatId, currentCatTitle,
						currentCatImage));

				// JSONArray subArray =
				// oneObject.getJSONArray(Config.JSON_QUOTES);

			} catch (JSONException e) {
				e.printStackTrace();
			}
		}

		return mItemList;
	}

	/**
	 * Method save quote data into several HashMap
	 * 
	 * @param text
	 * @return
	 */
	private HashMap<String, String> putData(String id, String title, String img) {
		HashMap<String, String> item = new HashMap<String, String>();
		item.put(Config.KEY_CAT_ID, id);
		item.put(Config.KEY_CAT_TITLE, title);
		item.put(Config.KEY_CAT_IMG, img);
		return item;
	}

	/**
	 * Check if already in favorites
	 */
	private boolean checkIsFavorite(String fv, String currentId) {

		boolean checkResult = false;

		String[] fvArray = fv.split(",");
		for (int i = 0; i < fvArray.length; i++) {
			if (fvArray[i].equals(currentId)) {
				// set already in favorites
				checkResult = true;
			}
		}
		return checkResult;
	}

}
