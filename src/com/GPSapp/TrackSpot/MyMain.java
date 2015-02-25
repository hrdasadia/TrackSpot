package com.GPSapp.TrackSpot;

import java.util.List;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ToggleButton;

public class MyMain extends Activity{
	
	public static int myrange;
	public Double lastlatitude,lastlongitude;
	public static String latstr;
	public static String lonstr;
	public static String add;
	
	public static String templat;
	public static String templon;
	public static boolean showdialog=false;
	
	public static String target_latitude;
	public static String target_longitude;
	
	public static boolean reqrec; 
	public static boolean toast;
	public static int target_code;
	public static boolean finalized=false;
	
	public static String destination;
	Button b2;
	
	public static boolean manual;
	public static String smspack;
	
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
	    super.onBackPressed();
	}
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.mainmenu);

		
		
		final ToggleButton b3;
		lastlatitude=getGPS()[0];
		lastlongitude=getGPS()[1];
		latstr=Double.toString(lastlatitude);
		lonstr=Double.toString(lastlongitude);
		
		
		
		
	reqrec=false;
	manual=false;
	
	
		
		Button b1= (Button) findViewById(R.id.Button01);
		b1.setOnClickListener(new View.OnClickListener() {
		
		
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				startActivity(new Intent("com.GPSapp.TrackSpot.CURR_LOC"));
			}
		});
		
		b2=(Button) findViewById(R.id.Button02);	
		b2.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
               //startActivity(new Intent("com.GPSapp.TrackSpot.MAP_LOC"));
				startActivity(new Intent("com.GPSapp.TrackSpot.MAP_LOC2"));
			}
		});
			
		
		Button b4=(Button) findViewById(R.id.Button04);
		b4.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
			
				final Dialog d=new Dialog(MyMain.this);
				d.setContentView(R.layout.temp);
				d.setTitle("Search Location:");
				d.show();
				
				final EditText lat1;
				final EditText lon1;
				lat1=(EditText) d.findViewById(R.id.EditText01);
				lon1=(EditText) d.findViewById(R.id.EditText02);
				
				//dosent work???
				lat1.setInputType(InputType.TYPE_NUMBER_FLAG_DECIMAL);
				lon1.setInputType(InputType.TYPE_NUMBER_FLAG_DECIMAL);
				
				
				
			Button ok= (Button) d.findViewById(R.id.Button01);
				ok.setOnClickListener(new View.OnClickListener() {
					
					public void onClick(View v) {
						// TODO Auto-generated method stub
				
						MyMain.templat=MyMain.latstr;
						MyMain.templon=MyMain.lonstr;
						
											
						MyMain.latstr=lat1.getText().toString();
						MyMain.lonstr=lon1.getText().toString();
						MyMain.showdialog=true;
						startActivity(new Intent("com.GPSapp.TrackSpot.MAP_LOC2"));
						d.dismiss();
						
					
					}
				});
				
			}
		});
		
		
		Button b5=(Button) findViewById(R.id.Button05);
		b5.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				startActivity(new Intent("com.GPSapp.TrackSpot.SETTINGS"));
			}
		});
		
		
		
		//b2.setText(Double.toString(getGPS()[0]));
		
	b3=(ToggleButton) findViewById(R.id.Button03);
		
		b3.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(manual==false)
				{
				b3.setText(R.string.smsoff);
				manual=true;
				
				}
				else{
					b3.setText(R.string.smson);
					manual=false;
				
				}
			}});
	}

	
	public double[] getGPS() {  
	    LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);    
	    List<String> providers = lm.getProviders(true);  
	  
	    /* Loop over the array backwards, and if you get an accurate location, then break out the loop*/  
	    Location l = null;  
	      
	    for (int i=providers.size()-1; i>=0; i--) {  
	        l = lm.getLastKnownLocation(providers.get(i));  
	        if (l != null) break;  
	    }  
	      
	    double[] gps = new double[2];  
	    if (l != null) {  
	        gps[0] = l.getLatitude();  
	        gps[1] = l.getLongitude();  
	    }  
	    return gps;  
	}  
	

	public static void sendmsg(){
		smspack="My current location is- Latitude: "+latstr+" Longitude: "+lonstr+" "+add;
		
		SmsManager sms = SmsManager.getDefault();
        sms.sendTextMessage(destination, null, smspack, null, null);  
        }
	
	
	
	
	
	
	
	
}




