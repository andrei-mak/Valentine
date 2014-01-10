package com.maknc.valentine.adapters;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.maknc.valentine.Config;
import com.maknc.valentine.R;

/**
 * Adapter for ListView in quotes list activity
 */
public class CatArrayAdapter extends ArrayAdapter<HashMap<String, String>> {
	
	private static final int ROW_LAYOUT_ID = R.layout.row_cat;

	private final Context context;
	private List<HashMap<String, String>> values;

	public CatArrayAdapter(Context context,
			List<HashMap<String, String>> values) {
		super(context, ROW_LAYOUT_ID, values);
		this.context = context;
		this.values = values;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View rowView = inflater.inflate(ROW_LAYOUT_ID, parent, false);

		TextView tv1 = (TextView) rowView.findViewById(R.id.tvCat);
		ImageView iv = (ImageView) rowView.findViewById(R.id.imCat);

		HashMap<String, String> currentValue = values.get(position);

		tv1.setText(currentValue.get(Config.KEY_CAT_TITLE));
		/*int itemNum = position + 1;
		tv3.setText(itemNum + "");*/
		
		//iv.setImageResource(R.drawable.ic_action_important);
		iv.setImageDrawable(getImageFromAsset(context, currentValue.get(Config.KEY_CAT_IMG)));
		
		return rowView;
	}
	
	public void updateItemsList(List<HashMap<String, String>> items) {
		this.values.clear();
	    this.values.addAll(items);
	    notifyDataSetChanged();
	}
	
	/**
	 * Method load images from assets (input imageUrl like "folder/image.jpg")
	 * 
	 * http://xjaphx.wordpress.com/2011/10/02/store-and-use-files-in-assets/
	 * 
	 * @param context
	 * @param imageUrl
	 * @return
	 */
	private Drawable getImageFromAsset(Context context, String imageUrl) {
		//TODO: pull out into separate helper class
		Drawable mDrawable = null;
		try {
			AssetManager mAssetManager = context.getAssets();
			// get input stream
			InputStream is = mAssetManager.open(imageUrl);
			// load image as Drawable
			mDrawable = Drawable.createFromStream(is, null);
			is.close();

		} catch (IOException e) {
			e.printStackTrace();
		}

		return mDrawable;
	}
}
