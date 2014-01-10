package com.maknc.valentine.adapters;

import java.util.HashMap;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.ShareActionProvider;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.maknc.valentine.AboutActivity;
import com.maknc.valentine.Config;
import com.maknc.valentine.MainActivity;
import com.maknc.valentine.R;

public class DetailPagerAdapter extends FragmentStatePagerAdapter {

	private List<HashMap<String, String>> values;

	/* Add my List parameters to constructor */
	public DetailPagerAdapter(FragmentManager fragmentManager,
			List<HashMap<String, String>> values) {
		super(fragmentManager);
		this.values = values;
	}

	@Override
	public int getCount() {
		return values.size();
	}
	
	// Page titles for android.support.v4.view.PagerTabStrip
	@Override
	public CharSequence getPageTitle(int position) {

	    String tabTitle = "" + (position + 1);
	    return tabTitle;
	}

	@Override
	public Fragment getItem(int position) {

		HashMap<String, String> currentValue = values.get(position);
		String currentId = currentValue.get(Config.KEY_ID);
		String text = currentValue.get(Config.KEY_TEXT);

		boolean isFav = false;

		String isFavorite = currentValue.get(Config.KEY_FAV);
		if (isFavorite.equals("true")) {
			isFav = true;
		}

		return DetailFragment.newInstance(position, currentId, text, isFav);
	}

	public static class DetailFragment extends Fragment {

		int mNum;
		String mDetailId;
		String mDetailText;
		
		boolean mIsFavorite;
		private MenuItem favButton;
		private ShareActionProvider mShareActionProvider;

		private static SharedPreferences sharedPref;	

		/**
		 * Create a new instance of CountingFragment, providing "num" as an
		 * argument.
		 */
		public static DetailFragment newInstance(int num, String curId,
				String val, boolean fv) {
			DetailFragment f = new DetailFragment();

			// Supply num input as an argument.
			Bundle args = new Bundle();

			args.putInt("num", num);
			args.putString("detail_id", curId);
			args.putString("detail_text", val);
			args.putBoolean("detail_fav", fv);

			f.setArguments(args);

			return f;
		}

		/**
		 * When creating, retrieve this instance's number from its arguments.
		 */
		@Override
		public void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			mNum = getArguments() != null ? getArguments().getInt("num") : 1;
			mDetailId = getArguments() != null ? getArguments().getString(
					"detail_id") : "1";
			mDetailText = getArguments() != null ? getArguments().getString(
					"detail_text") : "No data";
			mIsFavorite = getArguments() != null ? getArguments().getBoolean(
					"detail_fav") : false;

			/*
			 * Show action bar menu for fragment
			 * http://www.grokkingandroid.com/adding
			 * -action-items-from-within-fragments/
			 */
			setHasOptionsMenu(true);

			/*
			 * try { favButton = mainMenuFragment.findItem(R.id.menu_favorites);
			 * } catch (NullPointerException e) { // TODO Auto-generated catch
			 * block e.printStackTrace(); }
			 */

		}

		/**
		 * The Fragment's UI is just a simple text view showing its instance
		 * number.
		 */
		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View v = inflater.inflate(R.layout.fragment_pager_list, container,
					false);

			// getActivity().getActionBar().setIcon(R.drawable.ic_action_important);

			View tvText = v.findViewById(R.id.detailFragmentText);
			((TextView) tvText).setText(mDetailText);

			// Set toast (any detail data) number
			/*View tvNum = v.findViewById(R.id.tvDetailId);
			((TextView) tvNum).setText((mNum + 1) + "");*/

			/*
			 * View tv = v.findViewById(R.id.detailFragmentText);
			 * 
			 * String curlyLeftDoubleQuote =
			 * Html.fromHtml("&ldquo;").toString(); String curlyRightDoubleQuote
			 * = Html.fromHtml("&rdquo;").toString();
			 * 
			 * ((TextView) tv).setText(curlyLeftDoubleQuote + mPickedQuote +
			 * curlyRightDoubleQuote);
			 */

			return v;
		}

		@Override
		public void onActivityCreated(Bundle savedInstanceState) {
			super.onActivityCreated(savedInstanceState);
		}

		/*
		 * @Override public void onStart() { super.onStart();
		 * 
		 * //TODO: Set fav ico here! ((DetailActivity)
		 * getActivity()).updateFavIcon(mIsFavorite); }
		 * 
		 * @Override public void onResume() { super.onResume();
		 * 
		 * //TODO: Set fav ico here! ((DetailActivity)
		 * getActivity()).updateFavIcon(mIsFavorite); }
		 */

		/*
		 * @Override public void setUserVisibleHint(boolean isVisibleToUser) {
		 * super.setUserVisibleHint(isVisibleToUser); if (isVisibleToUser) {
		 * ((DetailActivity) getActivity()).currentFragmentNum = (mNum + 1) +
		 * ""; ((DetailActivity) getActivity()).updateFavIcon(mIsFavorite); }
		 * else { } }
		 */

		@Override
		public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
			// Inflate the menu; this adds items to the action bar if it is
			// present.
			inflater.inflate(R.menu.detail_frag_menu, menu);
			
			favButton = menu.findItem(R.id.menu_favorites);

			if (mIsFavorite) {
				favButton.setIcon(R.drawable.ic_action_important);
			} else {
				favButton.setIcon(R.drawable.ic_action_not_important);
			}
			
			// Set up ShareActionProvider's default share intent
		    MenuItem shareItem = menu.findItem(R.id.menu_share);
		    mShareActionProvider = (ShareActionProvider)
		            MenuItemCompat.getActionProvider(shareItem);
		    mShareActionProvider.setShareIntent(getShareIntent());
		    
			// menu.clear();
			// mainMenuFragment = menu;
		}
		
		/** Defines a default (dummy) share intent to initialize the action provider.
		  * However, as soon as the actual content to be used in the intent
		  * is known or changes, you must update the share intent by again calling
		  * mShareActionProvider.setShareIntent()
		  */
		private Intent getShareIntent() {
		    Intent intent = new Intent(Intent.ACTION_SEND);
		    intent.setType("text/*");
		    intent.putExtra(android.content.Intent.EXTRA_TEXT, mDetailText);
		    return intent;
		}

		@Override
		public boolean onOptionsItemSelected(MenuItem item) {
			boolean isFavorite;
			switch (item.getItemId()) {
			case android.R.id.home:
				// This ID represents the Home or Up button. In the case of this
				// activity, the Up button is shown. Use NavUtils to allow users
				// to navigate up one level in the application structure. For
				// more details, see the Navigation pattern on Android Design:
				//
				// http://developer.android.com/design/patterns/navigation.html#up-vs-back
				//
				Intent mIntent = new Intent(getActivity(), MainActivity.class);
				mIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(mIntent);
				return true;
			case R.id.menu_share:
				Intent mIntentAbout = new Intent(getActivity(),
						AboutActivity.class);
				startActivity(mIntentAbout);
				return true;
			case R.id.menu_favorites:
				// TODO: Save or remove from favorites
				sharedPref = getActivity().getSharedPreferences(Config.PREFS,
						Context.MODE_PRIVATE);
				String fav = sharedPref.getString(Config.PREFS_FAVORITES_KEY,
						Config.FAV_DEF_VALUE);

				String curFav = mDetailId;

				// Check if already in favorites
				isFavorite = checkIsFavorite(fav, curFav);

				// Write to favorites or remove
				if (isFavorite) {
					fav = removeFromFavorite(fav, curFav);

					// Write to favorites
					SharedPreferences.Editor editor = sharedPref.edit();
					editor.putString(Config.PREFS_FAVORITES_KEY, fav);
					editor.commit();
					Toast.makeText(getActivity().getApplicationContext(),
							"Удалено из избранного", Toast.LENGTH_SHORT).show();

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
					Toast.makeText(getActivity().getApplicationContext(),
							"Добавлено в избранное " + fav, Toast.LENGTH_SHORT)
							.show();

					// Change menu icon
					favButton.setIcon(R.drawable.ic_action_important);
				}

				return true;
			}
			return super.onOptionsItemSelected(item);
		}
		
		// Call to update the share intent
		private void setShareIntent(Intent shareIntent) {
		    if (mShareActionProvider != null) {
		        mShareActionProvider.setShareIntent(shareIntent);
		    }
		}

	}

	/**
	 * Check if already in favorites
	 */
	private static boolean checkIsFavorite(String fv, String currentId) {

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
	private static String removeFromFavorite(String fv, String currentId) {

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
	}

}
