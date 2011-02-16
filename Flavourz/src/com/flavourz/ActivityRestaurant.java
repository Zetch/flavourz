package com.flavourz;

import com.flavourz.database.DBRestaurant;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.TextView;

public class ActivityRestaurant extends Activity {
	protected TextView nameField;
	protected TextView locationField;
	protected DBRestaurant dbRests;
	protected long restID;
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.restaurant);
		dbRests = new DBRestaurant(this);
		dbRests.openDatabase();
		
		nameField = (TextView) findViewById(R.id.id_restaurant_name_value);
		locationField = (TextView) findViewById(R.id.id_restaurant_location_value);
		
		// Get params passed by previous activity through Intent()
		Bundle extras = getIntent().getExtras();
		if (extras != null) {
			restID = Long.parseLong(extras.getString("restID").trim());
			Cursor c = dbRests.getRestaurant(restID);
			nameField.setText(c.getString(c.getColumnIndex(dbRests.KEY_NAME)) + ", " + extras.getString("restID").trim());
			locationField.setText(c.getString(c.getColumnIndex(dbRests.KEY_LOCATION)));
		}
	}
	
	@Override
	public void onDestroy() {
		super.onDestroy();
		dbRests.closeDatabase();
	}

}
