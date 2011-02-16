package com.flavourz;

import java.util.ArrayList;
import java.util.Iterator;

import com.flavourz.R;
import com.flavourz.database.DBRestaurant;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class ActivityMain extends Activity {
	
	/** Widgets **/
	protected Button buttonRests;
	protected Button buttonMeals;
	protected Button buttonSearch;
	protected Button buttonExit;
	
	
	/*************************************************************************/
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        // NewRest Button
        buttonRests = (Button) findViewById(R.id.id_main_rests_button);
        buttonRests.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				startActivity(new Intent(ActivityMain.this, ActivityRestaurants.class));
			}
		});
        
        // List Button
        buttonMeals = (Button) findViewById(R.id.id_main_meals_button);
        buttonMeals.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
			}
		});
        
        // Search Button
        buttonSearch = (Button) findViewById(R.id.id_main_search_button);
        buttonSearch.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				//TODO
			}
		});
        
        // Exit Button
        buttonExit = (Button) findViewById(R.id.id_main_exit_button);
        buttonExit.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				exitApp();
			}
		});
        
    }
    
    /*************************************************************************/
    
    // Menu creation
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
    	MenuInflater inflater = getMenuInflater();
    	inflater.inflate(R.menu.main, menu);
    	return true;
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
    	switch (item.getItemId()) {
    		
    	case R.id.id_menu_main_about:
    		return true;
    		
    	case R.id.id_menu_main_exit:
    		exitApp();
    		return true;
    		
    	default:
    		return super.onOptionsItemSelected(item);
    	}
    }
    
    /*************************************************************************/
	
	public void exitApp() {
		finish();
	}
}