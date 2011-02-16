package com.flavourz;

import java.util.ArrayList;
import java.util.Iterator;

import com.flavourz.database.DBRestaurant;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ListActivity;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.text.style.BulletSpan;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.TextView;

public class ActivityRestaurants extends ListActivity {
	protected ArrayList<String> restList;
	protected DBRestaurant dbRestaurants = new DBRestaurant(this);
	protected ArrayAdapter<String> restListAdapter;
	
	static final int NEW_REST_DIALOG_ID = 0;
	
	/*************************************************************************/
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		restListAdapter = new ArrayAdapter<String>(this, R.layout.restaurants, getListFromDatabase()); 
		setListAdapter(restListAdapter);
	}
	
	/*************************************************************************/
	
	// Menu creation
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
    	MenuInflater inflater = getMenuInflater();
    	inflater.inflate(R.menu.restaurants, menu);
    	return true;
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
    	switch (item.getItemId()) {
    	
    	case R.id.id_menu_rests_new:
    		showDialog(NEW_REST_DIALOG_ID);
    		return true;
    		
    	case R.id.id_menu_rests_initializedb:
    		return dbRestaurants.initializeSampleDatabase();
    		
    	case R.id.id_menu_rests_printdb:
    		ArrayList<String> rests = dbRestaurants.getAllRestaurantsData();
    		Iterator i = rests.listIterator();
    		while (i.hasNext()) {
    			System.out.println("Restaurant: " + i.next());
    		}
    		return true;
    	
    	default:
    		return super.onOptionsItemSelected(item);
    	}
    }
	
	/*************************************************************************/
    
    // Dialogs creation
    
    @Override
    public Dialog onCreateDialog(int id) {
    	switch (id) {
    	
    	case NEW_REST_DIALOG_ID:
    		
    		LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    		final View layout = inflater.inflate(R.layout.new_restaurant_dialog, (ViewGroup) findViewById(R.id.id_newrest_layout));
    		
    		final EditText textFieldRestName = (EditText) layout.findViewById(R.id.id_newrest_restname_field);
    		final EditText textFieldLocation = (EditText) layout.findViewById(R.id.id_newrest_location_field);
    		
    		AlertDialog.Builder builder = new AlertDialog.Builder(this);
    		builder.setView(layout);
    		builder.setTitle(R.string.label_newrest_title);
    		
    		// Action for "OK" Button
    		builder.setPositiveButton(R.string.label_newrest_add_button,
    			new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						System.out.println("Added a new rest: "
							+ textFieldRestName.getText().toString() + " ("
							+ textFieldLocation.getText().toString() + ")");
						insertNewRestaurant(textFieldRestName.getText().toString(),	textFieldLocation.getText().toString());
						ActivityRestaurants.this.removeDialog(NEW_REST_DIALOG_ID);
						ActivityRestaurants.this.restListAdapter.notifyDataSetChanged();
					}
				});
    		
    		// Action for "CANCEL" Button
    		builder.setNegativeButton(R.string.label_newrest_cancel_button,
    			new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						ActivityRestaurants.this.removeDialog(NEW_REST_DIALOG_ID);
					}
				});
    		 
    		AlertDialog newRestDialog = builder.create();
    		return newRestDialog;
    	}
    	return null;
    }
    
    /*************************************************************************/
	
	// Returns an ArrayList from database
	private ArrayList<String> getListFromDatabase() {
		Cursor c;
		restList = new ArrayList<String>();
		
		dbRestaurants.openDatabase();         // Open database for retrieving
		c = dbRestaurants.getAllRestaurants();
		
		if (c.moveToFirst()) {
			do {
				restList.add(c.getString(1) + " (" + c.getString(2) + ")");
			} while (c.moveToNext());
		}
		dbRestaurants.closeDatabase();
		return restList;
	}
	
	/*************************************************************************/
	
	// Insert new rest into dabatase
	private boolean insertNewRestaurant(String name, String location) {
		dbRestaurants.openDatabase();
		dbRestaurants.insertRestaurant(name, location);
		dbRestaurants.closeDatabase();
		return true;
	}
	
	/*************************************************************************/
	
	// Returns a sample list with given count
	private ArrayList<String> getListFromSample(int count) {
		restList = new ArrayList<String>();
		for (Integer i = 0; i < count; i++) {
			restList.add("Restaurant " + i.toString());
		}
		return restList;
	}

}