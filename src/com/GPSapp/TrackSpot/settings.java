package com.GPSapp.TrackSpot;

import java.util.ArrayList;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;
import android.widget.ToggleButton;

public class settings extends Activity{

	AlertDialog.Builder b;
	AlertDialog a;
	EditText tarlat;
	EditText tarlon;
	EditText range;
	ToggleButton toast;
	RadioGroup rg;
	RadioButton r1,r2,r3;
	Boolean correct=true;
	Button save,ret;
	Cursor cursor;
	ArrayList<Object> rowArray = new ArrayList<Object>();
	String newTableQueryString;
	//----------Database variables
	private SQLiteDatabase db; // a reference to the database manager class.
	private final String DB_NAME = "location_database"; // the name of our database
	private final int DB_VERSION = 1; // the version of the database
 
	// the names for our database columns
	private final String TABLE_NAME = "loc";
	private final String TABLE_ROW_ID = "id";
	private final String TABLE_ROW_ONE = "lat";
	private final String TABLE_ROW_TWO = "lon";
	private final String TABLE_ROW_THREE = "name";
	private final String TABLE_ROW_FOUR = "setting";
	private final String TABLE_ROW_FIVE = "range";
	
	
	
	private class CustomSQLiteOpenHelper extends SQLiteOpenHelper
	{

		public CustomSQLiteOpenHelper(Context context)
		{
			super(context, DB_NAME, null, DB_VERSION);
		}
	    // TODO: override the constructor and other methods for the parent class

		
		
		
		
		
		@Override
		public void onCreate(SQLiteDatabase db) {
			// TODO Auto-generated method stub
			// the SQLite query string that will create our 5 column database table.
			newTableQueryString = 	
				"CREATE TABLE IF NOT EXISTS" +
				TABLE_NAME +
				" (" +
				TABLE_ROW_ID + " integer primary key autoincrement not null," +
				TABLE_ROW_ONE + " text," +
				TABLE_ROW_TWO + " text," +
				TABLE_ROW_THREE + " text," +
				TABLE_ROW_FOUR + " text," +
				TABLE_ROW_FIVE + " text" +
				");";
		 
			// execute the query string to the database.
			db.execSQL(newTableQueryString);
		}

		
		
		public void addRow(String rowStringOne, String rowStringTwo, String rowStringThree, String rowStringFour, String rowStringFive)
		{
			// this is a key value pair holder used by android's SQLite functions
			ContentValues values = new ContentValues();
		 
			// this is how you add a value to a ContentValues object
			// we are passing in a key string and a value string each time
			values.put(TABLE_ROW_ONE, rowStringOne);
			values.put(TABLE_ROW_TWO, rowStringTwo);
			values.put(TABLE_ROW_THREE, rowStringThree);
			values.put(TABLE_ROW_FOUR, rowStringFour);
			values.put(TABLE_ROW_FIVE, rowStringFive);
			
			
			// ask the database object to insert the new data 
			try
			{
				db.insert(TABLE_NAME, null, values);
			}
			catch(Exception e)
			{
				Log.e("DB ERROR", e.toString()); // prints the error message to the log
				e.printStackTrace(); // prints the stack trace to the log
			}
		}
		
		
		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			// TODO Auto-generated method stub
			
		}
	}
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.settings);
		
		final CustomSQLiteOpenHelper tab = new CustomSQLiteOpenHelper(settings.this);
		
		tarlat=(EditText) findViewById(R.id.EditText01);
		tarlon=(EditText) findViewById(R.id.EditText02);
		range=(EditText) findViewById(R.id.EditText03);
	
		toast=(ToggleButton) findViewById(R.id.ToggleButton01);
		
		rg=(RadioGroup) findViewById(R.id.RadioGroup01);
	    r1=(RadioButton) findViewById(R.id.RadioButton01);
	    r2=(RadioButton) findViewById(R.id.RadioButton02);
	    r3=(RadioButton) findViewById(R.id.RadioButton03);
		
		tarlat.setText(MyMain.target_latitude);
		tarlon.setText(MyMain.target_longitude);
		
		tarlat.setInputType(InputType.TYPE_NUMBER_FLAG_DECIMAL);
		tarlon.setInputType(InputType.TYPE_NUMBER_FLAG_DECIMAL);
		
		r1.setChecked(true);
		range.setText("50");
		
		
		
		save=(Button) findViewById(R.id.Button01);
		ret=(Button) findViewById(R.id.Button02);
		
		
		save.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				
				   
		        if(rg.getCheckedRadioButtonId()==r1.getId())
		        MyMain.target_code=1;
		        
		        if(rg.getCheckedRadioButtonId()==r2.getId())
		        MyMain.target_code=2;
		            
		        if(rg.getCheckedRadioButtonId()==r3.getId())
		        MyMain.target_code=3;
		        

				db =openOrCreateDatabase(DB_NAME, MODE_PRIVATE, null);
	
				db.execSQL("CREATE TABLE IF NOT EXISTS "+
				TABLE_NAME +
				" (" +
				TABLE_ROW_ID + " integer primary key autoincrement not null," +
				TABLE_ROW_ONE + " text," +
				TABLE_ROW_TWO + " text," +
				TABLE_ROW_THREE + " text," +
				TABLE_ROW_FOUR + " text," +
				TABLE_ROW_FIVE + " text" +
				");");
		
		
		        
		        final Dialog d=new Dialog(settings.this);
				d.setContentView(R.layout.locname);
				d.setTitle("Save Location:");
				d.show();
				
				final EditText loc_name;
				
				loc_name=(EditText) d.findViewById(R.id.EditText01);
				
								
				
			Button ok= (Button) d.findViewById(R.id.Button01);
				ok.setOnClickListener(new View.OnClickListener() {
					
					public void onClick(View v) {
						// TODO Auto-generated method stub
						
						tab.addRow(tarlat.getText().toString(), tarlon.getText().toString(), loc_name.getText().toString(), Integer.toString(MyMain.target_code), range.getText().toString());
			    		Toast.makeText(getBaseContext(),"Location "+loc_name.getText().toString()+" has been saved." , Toast.LENGTH_SHORT).show();

						d.dismiss();
						
					
					}
				});
		        
		        
				}
		});
		
		
		
		
		ret.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				
				
						db =openOrCreateDatabase(DB_NAME, MODE_PRIVATE, null);
			
						db.execSQL("CREATE TABLE IF NOT EXISTS "+
						TABLE_NAME +
						" (" +
						TABLE_ROW_ID + " integer primary key autoincrement not null," +
						TABLE_ROW_ONE + " text," +
						TABLE_ROW_TWO + " text," +
						TABLE_ROW_THREE + " text," +
						TABLE_ROW_FOUR + " text," +
						TABLE_ROW_FIVE + " text" +
						");");
				
				
				final Dialog d=new Dialog(settings.this);
				d.setContentView(R.layout.retloc);
				d.setTitle("Retrieve Location:");
				d.show();
				
				
				final EditText loc_name;
				
				loc_name=(EditText) d.findViewById(R.id.EditText01);
				
								
				
			Button ok= (Button) d.findViewById(R.id.Button01);
				ok.setOnClickListener(new View.OnClickListener() {
					
					public void onClick(View v) {
						// TODO Auto-generated method stub
						
						Cursor c=db.rawQuery("SELECT * FROM "+TABLE_NAME+" WHERE name ='"+loc_name.getText().toString()+"'", null);
						if (c.moveToNext())
						{
							tarlat.setText(c.getString(1));
							tarlon.setText(c.getString(2));
							range.setText(c.getString(5));
							
							if(c.getString(4).equals("1"))
								r1.setChecked(true);
							
							if(c.getString(4).equals("2"))
								r2.setChecked(true);
							
							if(c.getString(4).equals("3"))
								r3.setChecked(true);
							
							
							Toast.makeText(getBaseContext(),"Location successfully retrieved.", Toast.LENGTH_SHORT).show();
						}
							else
						Toast.makeText(getBaseContext(),"Location NOT found." , Toast.LENGTH_SHORT).show();

						d.dismiss();
						
					
					}
				});
		        
				
						
				
			}
		});
		
		
	}

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		
		b= new AlertDialog.Builder(settings.this);
		a = b.create();
		
				
		if(tarlat.getText().toString().length()==0)
		{
			a.setMessage("Please Enter a valid float value for target latitude");
			correct=false;
			a.show();
		}
		else
		{
			correct=true;
		}
		
		
		if(tarlon.getText().toString().length()==0)
		{
			a.setMessage("Please Enter a valid float value for target longitude");
			correct=false;
			a.show();
		}
		else
		{
			correct=true;
		}
		
		if(correct==true)
		
		{
		MyMain.target_latitude=tarlat.getText().toString();
        MyMain.target_longitude=tarlon.getText().toString();
        MyMain.myrange=Integer.parseInt(range.getText().toString());
		
        String temp=toast.getText().toString();
        temp=temp.toUpperCase();
        
        if(temp.equals("ON"))
        {
        MyMain.toast=true;
        }
        else
        {
        MyMain.toast=false;
        }
        
        
        if(rg.getCheckedRadioButtonId()==r1.getId())
        MyMain.target_code=1;
        
        if(rg.getCheckedRadioButtonId()==r2.getId())
        MyMain.target_code=2;
            
        if(rg.getCheckedRadioButtonId()==r3.getId())
        MyMain.target_code=3;
        
        
        
        
        MyMain.finalized=true;
		super.onBackPressed();
		}
		
		
	}

}
