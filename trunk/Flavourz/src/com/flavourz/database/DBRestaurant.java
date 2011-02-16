package com.flavourz.database;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.text.method.MovementMethod;
import android.util.Log;

public class DBRestaurant {
	
	// Table definition
	public static final String KEY_ROWID       = "_id";
	public static final String KEY_NAME        = "name";
	public static final String KEY_LOCATION    = "location";
	public static final String TAG             = "Restaurant";
	
	// Database definition
	private static final String DATABASE_NAME  = "flavourz";
	private static final String DATABASE_TABLE = "restaurants";
	private static final int DATABASE_VERSION  = 1;
	
	// Database SQL string creation
	private static final String DATABASE_CREATE =
		"CREATE TABLE restaurants ("
		+ "_id INTEGER PRIMARY KEY AUTOINCREMENT, "
		+ "name TEXT NOT NULL, "
		+ "location TEXT NOT NULL);";
	
	// Dabatase interface
	private final Context context;
	private DatabaseHelper DBHelper;
	private SQLiteDatabase db;
	
	/*************************************************************************/
	
	// Constructor
	public DBRestaurant(Context ctx) {
		this.context = ctx;
		DBHelper = new DatabaseHelper(context);
	}
	
	/*************************************************************************/
	
	// Internal class for database creation or update
	private static class DatabaseHelper extends SQLiteOpenHelper {
		
		public DatabaseHelper(Context context) {
			super(context, DATABASE_NAME, null, DATABASE_VERSION);
		}

		// Initial Creation
		@Override
		public void onCreate(SQLiteDatabase db) {
			db.execSQL(DATABASE_CREATE);
		}

		// Update
		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			Log.w(TAG, "Upgrading database from version " + oldVersion 
					+ " to " + newVersion + ", which will destroy all old data");
			db.execSQL("DROP TABLE IF EXISTS restaurants");
			onCreate(db);
		}	
	}
	/*************************************************************************/
	
	// Open database connection, returns Database object
	public DBRestaurant openDatabase() throws SQLException {
		db = DBHelper.getWritableDatabase();
		return this;
	}
	
	/*************************************************************************/
	
	// Close database connection
	public void closeDatabase() {
		DBHelper.close();
	}
	
	/*************************************************************************/
	
	// Insert a new row
	public long insertRestaurant(String name, String location) {
		ContentValues initialValues = new ContentValues();
		initialValues.put(KEY_NAME, name);
		initialValues.put(KEY_LOCATION, location);
		return db.insert(DATABASE_TABLE, null, initialValues);
	}
	
	/*************************************************************************/
	
	// Delete given row by id
	public boolean deleteRestaurant(long rowId) {
		return db.delete(DATABASE_TABLE, KEY_ROWID + "=" + rowId, null) > 0;
	}
	
	/*************************************************************************/
	
	// Get all records
	public Cursor getAllRestaurants() {
		return db.query(DATABASE_TABLE,
				new String[] {KEY_ROWID, KEY_NAME, KEY_LOCATION},
				null, null, null, null, null);
	}
	
	// Get all restaurants name as ArrayList<String>
	public ArrayList<String> getAllRestaurantsData() {
		Cursor cursor;
		ArrayList<String> restList = new ArrayList<String>();
		
		openDatabase();
		cursor = getAllRestaurants();
		if (cursor.moveToFirst()) {
			do {
				restList.add(cursor.getString(1));
			} while (cursor.moveToNext());
		}
		closeDatabase();
		return restList;
	}
	
	/*************************************************************************/
	
	// Get given row by id
	public Cursor getRestaurant(long rowId) {
		Cursor mCursor = db.query(true, DATABASE_TABLE, 
				new String[] {KEY_ROWID, KEY_NAME, KEY_LOCATION},
				KEY_ROWID + "=" + rowId, null, null, null, null, null);
		
		if (mCursor != null) {
			mCursor.moveToFirst();
		}
		return mCursor;
	}
	
	/*************************************************************************/
	
	// Updates a record
	public boolean updateRestaurant(long rowId, String name, String location) {
		ContentValues newValues = new ContentValues();
		newValues.put(KEY_NAME, name);
		newValues.put(KEY_LOCATION, location);
		return db.update(DATABASE_TABLE, newValues, KEY_ROWID + "=" + rowId, null) > 0;
	}
	
	/*************************************************************************/
	
	// Drop all restaurants from table
	public boolean dropAll() {
		db.execSQL("DELETE FROM restaurants");
		return true;
	}
	
	/*************************************************************************/
	
	// Creates sample data
	public boolean initializeSampleDatabase() {
		long[] id = new long[6];
		
    	openDatabase();
    	if (dropAll()) {
	    	id[0] = insertRestaurant("Chipi Chipi", "Velhoco, Breña Alta");
	    	id[1] = insertRestaurant("El Azul",     "Las Tricias, Garafía");
	    	id[2] = insertRestaurant("Il Forno",    "Puerto Naos, Los Llanos");
	    	id[3] = insertRestaurant("Il Adagio",   "Los Llanos");
	    	id[4] = insertRestaurant("El Secadero", "Las Manchas");
	    	id[5] = insertRestaurant("La Colonial", "Los Llanos");
    	} else {
    		return false;
    	}
    	closeDatabase();
    	
    	for (int i = 0; i < id.length; i++) {
    		if (id[i] == -1) return false; 
    	}
    	return true;
    }
}