package com.maknc.valentine;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.MenuItem;

import com.google.android.gms.ads.AdView;
import com.maknc.valentine.adapters.DetailPagerAdapter;
import com.maknc.valentine.parsers.JSONParser;

public class DetailActivity extends ActionBarActivity {
    static final int NUM_ITEMS = 10;

    DetailPagerAdapter mAdapter;
    ViewPager mDetailPager;
    
	private String mPickedId;
	public boolean isFavorite;
	public String currentFragmentNum = "";
	
	private MenuItem favButton;
	
	
	private AdView mAdView;
	
	private SharedPreferences sharedPref;
	List<HashMap<String, String>> tList = new ArrayList<HashMap<String, String>>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_pager);
        
        /* Read favorites list */
		sharedPref = getSharedPreferences(Config.PREFS, Context.MODE_PRIVATE);
		String defValue = "";
		String fav = sharedPref.getString(Config.PREFS_FAVORITES_KEY, defValue);
		
        Intent mIntentStarted = getIntent();
		mPickedId = mIntentStarted.getStringExtra(Config.TAG_CAT_ID);
		
		//int wishId = getResources().getIdentifier("wishes" + mPickedId + ".json", "raw", getPackageName());
		
		String dType = mIntentStarted.getStringExtra(Config.TAG_DATA_TYPE);
		
		tList = getDetailData(R.raw.wishesval, fav, mPickedId, dType);
		

        mAdapter = new DetailPagerAdapter(getSupportFragmentManager(), tList);
        mDetailPager = (ViewPager)findViewById(R.id.detailPager);
        mDetailPager.setAdapter(mAdapter);
        

		
		//int pageId = Integer.parseInt(mPickedId) - 1;
		
        if (dType.equals(Config.INTENT_DATA_TYPE_DETAILS)) {
        	String pickedId = mIntentStarted.getStringExtra(Config.TAG_TOST_ID);
        	int pageId = pickedId != null ? Integer.parseInt(pickedId) : 1;
        	mDetailPager.setCurrentItem(pageId);
		}
		
		
		
		
		
        /*
        // Watch for button clicks.
        Button button = (Button)findViewById(R.id.goto_first);
        button.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                mPager.setCurrentItem(0);
            }
        });
        button = (Button)findViewById(R.id.goto_last);
        button.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                mPager.setCurrentItem(NUM_ITEMS-1);
            }
        });\
        */
    }

  
    
	/**
	 * Method for read JSON and save into List of HashMaps
	 * 
	 * @param url
	 * @return
	 */
	private ArrayList<HashMap<String, String>> getDetailData(int ResID, String fv, String cat, String tp) {
		
		ArrayList<HashMap<String, String>> mItemList = new ArrayList<HashMap<String, String>>();
		
		boolean isFav = false;
	
		// Creating JSON Parser instance
		JSONParser jParser = new JSONParser();
	
		// getting JSON string from URL
		JSONObject jObject = jParser.getJSONFromRaw(getApplicationContext(), ResID);
		
		if (Config.DEBUG_MODE) {
			Log.i("TEST", "jObject: " + jObject);
		}	
	
		JSONArray jArray = null;
		try {
			jArray = jObject.getJSONArray(Config.JSON_DETAIL_ARRAY);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		if (Config.DEBUG_MODE) {
			Log.i("TEST", "jArray: " + jArray);
		}
	
		//String[] Items = new String[jArray.length()];
	
		if (jArray != null) {
			for (int i = 0; i < jArray.length(); i++) {
				try {
		
					JSONObject oneObject = jArray.getJSONObject(i);
					// Pulling items from the array
					//Items[i] = oneObject.getString(Config.JSON_ID);
					
					String currentId = oneObject.getString(Config.JSON_ID);
					String currentText = oneObject.getString(Config.JSON_TEXT);
					String currentCat = oneObject.getString(Config.JSON_TEXT_CATEGORY);
					
					isFav = checkIsFavorite(fv, currentId);
					
					// Categories
					if (tp.equals(Config.INTENT_DATA_TYPE_CATEGORIES)) {
						if (currentCat.equals(cat)) {
							mItemList.add(putData(currentId,currentText,isFav + ""));
						}
					}
					
					// Favorites
					if (tp.equals(Config.INTENT_DATA_TYPE_DETAILS)) {
						if (isFav) {
							mItemList.add(putData(currentId,currentText,isFav + ""));
						}
					}
					
		
					// JSONArray subArray =
					// oneObject.getJSONArray(Config.JSON_QUOTES);
		
				} catch (JSONException e) {
					e.printStackTrace();
				}
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
	private HashMap<String, String> putData(String id, String text, String fav) {
		HashMap<String, String> item = new HashMap<String, String>();
		item.put(Config.KEY_ID, id);
		item.put(Config.KEY_TEXT, text);
		item.put(Config.KEY_FAV, fav);
		return item;
	}
	
	
	/**
	 * Set up the {@link android.app.ActionBar}, if the API is available.
	 */
	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	private void setupActionBar() {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
			getActionBar().setDisplayHomeAsUpEnabled(true);
		}
	}


/*@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.tost_menu, menu);
		mainMenu = menu;
		favButton = mainMenu.findItem(R.id.menu_favorites);
		return true;
	}*/

/*	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		 //Dynamically change favorites icon in action bar 
		
		Log.i("TEST", "onPrepareOptionsMenu started");

		 //Check if already in favorites 
		sharedPref = getSharedPreferences(Config.PREFS, Context.MODE_PRIVATE);
		String fav = sharedPref.getString(Config.PREFS_FAVORITES_KEY,
				Config.FAV_DEF_VALUE);
		isFavorite = checkIsFavorite(fav, mPickedTostId);

		MenuItem favoritesButton = menu.findItem(R.id.menu_favorites);
		if (isFavorite) {
			favoritesButton.setIcon(R.drawable.ic_action_important);
		} else {
			favoritesButton.setIcon(R.drawable.ic_action_not_important);
		}
		return super.onPrepareOptionsMenu(menu);

	}*/

	/*@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			// This ID represents the Home or Up button. In the case of this
			// activity, the Up button is shown. Use NavUtils to allow users
			// to navigate up one level in the application structure. For
			// more details, see the Navigation pattern on Android Design:
			//
			// http://developer.android.com/design/patterns/navigation.html#up-vs-back
			//
			Intent mIntent = new Intent(getApplicationContext(),
					MainActivity.class);
			mIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(mIntent);
			return true;
		case R.id.submenu_about:
			Intent mIntentAbout = new Intent(getApplicationContext(),
					AboutActivity.class);
			startActivity(mIntentAbout);
			return true;
		case R.id.menu_favorites:
			//Save or remove from favorites 
			sharedPref = getSharedPreferences(Config.PREFS,
					Context.MODE_PRIVATE);
			String fav = sharedPref.getString(Config.PREFS_FAVORITES_KEY,
					Config.FAV_DEF_VALUE);
			
			// TODO: Temporary
			//String curFav = mPageListener.getCurrentPage() + "";
			String curFav = currentFragmentNum;

			//Check if already in favorites 
			isFavorite = checkIsFavorite(fav, curFav);

			 //Write to favorites or remove 
			if (isFavorite) {
				fav = removeFromFavorite(fav, curFav);
				
				// Write to favorites
				SharedPreferences.Editor editor = sharedPref.edit();
				editor.putString(Config.PREFS_FAVORITES_KEY, fav);
				editor.commit();
				Toast.makeText(getApplicationContext(), "Удалено из избранного",
						Toast.LENGTH_SHORT).show();
				
				favButton.setIcon(R.drawable.ic_action_not_important);
			} else {
				
				// Add to favorites string
				if (fav.equals("")) {
					fav = curFav;
				} else {
					fav = fav + "," + curFav;
				}
				
				// Write to favorites
				SharedPreferences.Editor editor = sharedPref.edit();
				editor.putString(Config.PREFS_FAVORITES_KEY, fav);
				editor.commit();
				Toast.makeText(getApplicationContext(), "Добавлено в избранное" + fav,
						Toast.LENGTH_SHORT).show();

				// Change menu icon		
				favButton.setIcon(R.drawable.ic_action_important);
			}

			return true;
		}
		return super.onOptionsItemSelected(item);
	}*/

	/**
	 * Menu hardware button open action bar menu
	 * 
	 * http://stackoverflow.com/questions/12277262/
	 * opening-submenu-in-action-bar-on-hardware-menu-button-click
	 *//*
	@Override
	public boolean onKeyUp(int keyCode, KeyEvent event) {
		if (event.getAction() == KeyEvent.ACTION_UP) {
			switch (keyCode) {
			case KeyEvent.KEYCODE_MENU:

				mainMenu.performIdentifierAction(R.id.menu_more, 0);
				// Log.d("Menu", "menu button pressed");
				return true;
			}

		}
		return super.onKeyUp(keyCode, event);
	}
*/
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
	
	/**
	 * Remove from favorites string
	 */
/*	private String removeFromFavorite(String fv, String currentId) {
		
		String clearResult = "";

		String[] fvArray = fv.split(",");
		for (int i = 0; i < fvArray.length; i++) {
			if (fvArray[i].equals(currentId)) {
				// do not add this element
			} else {
				// leave element in favorites
				if (clearResult.equals("")) {
					clearResult = fvArray[i];
				} else {
					clearResult = clearResult + "," + fvArray[i];
				}
			}
		}
		return clearResult;
	}*/
	
/*	@Override
	public void onAttachFragment (Fragment fragment) {
	    Log.i("TEST", "onAttachFragment: " + fragment + " favBtn: " + favButton);
	    if(fragment.getClass()==DetailFragment.class){
	        //fragList.add(new WeakReference<EventListFragment>((EventListFragment)fragment));
	    	//updateFavIcon();
	    	//TODO: Implement favorites icon set
	    }
	}*/
	
	/**
	 * Set ActionBar Favorites icon
	 * @param isFav
	 */
	private void setFavIcon (boolean isFav) {
		// Change menu icon
		if (isFav) {
			try {
				favButton.setIcon(R.drawable.ic_action_important);
				Log.i("TEST", "favButton.setIcon i DONE");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				Log.i("TEST", "Exception i " + e);
			}
		} else {
			try {
				favButton.setIcon(R.drawable.ic_action_not_important);
				Log.i("TEST", "favButton.setIcon non i DONE");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				Log.i("TEST", "Exception non i " + e);
			}
		}
	}
	
	/**
	 * Update ActionBar Favorites icon
	 */
	public void updateFavIcon (boolean isFv) {
		//String currenFragNum = mPageListener.getCurrentPage() + "";
		
		setFavIcon(isFv);
		Log.i("TEST", "updateFavIcon " + currentFragmentNum + " isFv: " + isFv);
	}
}
