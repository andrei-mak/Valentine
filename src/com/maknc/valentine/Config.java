package com.maknc.valentine;

/**
 * Settings class
 */
public class Config {
	
	/* AdMob */
	public static final String AD_UNIT_ID = "a152b95c535dbe7";
	
	/* Debug */
	public static final boolean DEBUG_MODE = true;
	
	/* Temp values for deatail viewpager */
	public static final int NUM_ITEMS = 10;
	
	public static final String PREFS = "com.maknc.valentine.PREFERENCE_FILE_KEY";
	public static final String PREFS_FAVORITES_KEY = "favorites";
	public static final String FAV_DEF_VALUE = "";
	
	/* Intents tags */
	protected static final String TAG_AUTHOR = "com.maknc.valentine.TAG_AUTHOR";
	protected static final String TAG_AUTHOR_IMAGE = "com.maknc.valentine.TAG_AUTHOR_IMAGE";
	protected static final String TAG_DATA_TYPE = "com.maknc.valentine.TAG_DATA_TYPE";
	
	public final static String TAG_TOST_TEXT = "com.maknc.valentine.TAG_TOST_TEXT";
	public final static String TAG_TOST_ID = "com.maknc.valentine.TAG_TOST_ID";
	public final static String TAG_CAT_ID = "com.maknc.valentine.TAG_CAT_ID";
	
	/* Intents data types */
	public static final String INTENT_DATA_TYPE_CATEGORIES = "categories";
	public static final String INTENT_DATA_TYPE_DETAILS = "details";

	/* JSON load settings */
	protected static final String JSON_URL = "bright_quotes.json";
	protected static final String JSON_AUTHORS_ARRAY = "authors";
	protected static final String JSON_AUTHOR = "authorName";
	protected static final String JSON_IMAGE = "image";
	protected static final String JSON_QUOTES = "quotes";
	
	public static final String JSON_TOST_URL = "newtost2014.json";
	public static final String JSON_DETAIL_ARRAY = "wishes";
	public static final String JSON_ID = "id";
	public static final String JSON_TEXT = "text";
	public static final String JSON_TEXT_TAG = "tag";
	public static final String JSON_TEXT_CATEGORY = "category";
	
	public static final String JSON_CAT_ARRAY = "categories";
	public static final String JSON_CAT_ID = "id";
	public static final String JSON_CAT_TITLE = "title";
	public static final String JSON_CAT_IMG = "image";
	

	/* HashMap keys */
	public static final String KEY_AUTHOR_NAME = "author_name";
	public static final String KEY_AUTHOR_IMAGE = "author_image";
	public static final String KEY_QUOTES_QUANTITY = "quotes_quantity";
	public static final String KEY_QUOTE_TEXT = "quote_text";
	
	public static final String KEY_ID = "tost_id";
	public static final String KEY_TEXT = "tost_text";
	public static final String KEY_FAV = "tost_fav";
	public static final String KEY_TEXT_TAG = "tost_tag";
	public static final String KEY_TEXT_TAG_IMG = "tost_tag_img";
	
	public static final String KEY_CAT_ID = "id";
	public static final String KEY_CAT_TITLE = "title";
	public static final String KEY_CAT_IMG = "image";
	

}
