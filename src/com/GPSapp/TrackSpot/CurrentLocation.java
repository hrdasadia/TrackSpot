package com.GPSapp.TrackSpot;

import android.app.Activity;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class CurrentLocation extends Activity{
	
	private static final long MINIMUM_DISTANCE_CHANGE_FOR_UPDATES = 1; // in Meters
	    private static final long MINIMUM_TIME_BETWEEN_UPDATES = 1000; // in Milliseconds
	    
	    protected LocationManager locationManager;
    	public float[] results = {0,0,0,0,0};
	    
	    protected Button retrieveLocationButton;
	    public Location location;
	    public TextView latitude,longitude;
	    public TextView status;
	    AudioManager am;
	    public TextView targetlat,targetlon;
	    public float distance;
	    public TextView disttext;
	    public TextView temp;
	    public MediaPlayer myalarm;
	    public void firecheck(){
			
	    	
	    	Location.distanceBetween(Double.valueOf(MyMain.latstr), Double.valueOf(MyMain.lonstr), Double.valueOf(MyMain.target_latitude), Double.valueOf(MyMain.target_longitude), results);
		    	    	
	    	am= (AudioManager) getBaseContext().getSystemService(Context.AUDIO_SERVICE);
	    	// 
	    	distance=results[0];
	    	disttext.setText(Float.toString(distance));
	    	
	    	
	    	if (results[0]<=MyMain.myrange){
	    		if(MyMain.toast==true)
	    		{
	    		Toast.makeText(getBaseContext(),"Arrived at destination" , Toast.LENGTH_SHORT).show();
	    		}
	    		
	    		switch(MyMain.target_code)
	    		{
	    		case 1:
	    		{
	    			am.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
	    			am.setRingerMode(AudioManager.RINGER_MODE_SILENT);
	    			break;
	    		}
	    		case 2:
	    		{
	    			am.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
	    			am.setRingerMode(AudioManager.RINGER_MODE_VIBRATE);
	    			break;
	    		}
	    		case 3:
	    		{
	    			//alarm
	    			
	    			//myalarm = MediaPlayer.create(this, R.raw.alarm);
	    			myalarm.start();
	    			
	    			break;
	    		}
	    		
	    		}
	    		
	    	}
	    	
	    	else{
	    		am.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
	    	}
	    }
	    
	    
	    @Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.currentloc);
		
		latitude=(TextView) findViewById(R.id.TextView01);
		 longitude=(TextView) findViewById(R.id.TextView06);
		 status=(TextView)findViewById(R.id.TextView05);
		 targetlat=(TextView) findViewById(R.id.TextView09);
		 targetlon=(TextView) findViewById(R.id.TextView11);
		 disttext=(TextView) findViewById(R.id.TextView13);
		 
		 latitude.setText(MyMain.latstr);
		 longitude.setText(MyMain.lonstr);
		 targetlat.setText(MyMain.target_latitude);
		 targetlon.setText(MyMain.target_longitude);
		 
		 myalarm = MediaPlayer.create(this, R.raw.alarm);
		 temp=(TextView) findViewById(R.id.TextView15);
		 temp.setText(": Range is "+String.valueOf(MyMain.myrange));//+"\nToast is :"+MyMain.toast+" Target code: "+MyMain.target_code);
		 
		 locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

	        
		 locationManager.requestLocationUpdates(
	                LocationManager.GPS_PROVIDER, 
	                MINIMUM_TIME_BETWEEN_UPDATES, 
	                MINIMUM_DISTANCE_CHANGE_FOR_UPDATES,
	                new MyLocationListener()
	        );
		 showCurrentLocation();
		 
	}


		@Override
		public void onBackPressed() {
			// TODO Auto-generated method stub
				
			if(myalarm.isPlaying())
			{
				myalarm.stop();
		    }
			super.onBackPressed();
		}


		protected void showCurrentLocation() {
			// TODO Auto-generated method stub
			location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
			if (location != null) {
				latitude.setText(String.format("%1$s",location.getLatitude()));
				longitude.setText(String.format("%1$s",location.getLongitude()));
				MyMain.latstr=String.format("%1$s",location.getLatitude());
				MyMain.lonstr=String.format("%1$s",location.getLongitude());
			}
		}


		public class MyLocationListener implements LocationListener {
			
			
			public void onLocationChanged(Location arg0) {
				// TODO Auto-generated method stub
				showCurrentLocation();
				if(MyMain.finalized==true)
				{
				firecheck();
				}
				status.setText("New Co-ordinates Found.");
			}

			public void onProviderDisabled(String arg0) {
				// TODO Auto-generated method stub
				status.setText("GPS Deactived.");
			}

			public void onProviderEnabled(String arg0) {
				// TODO Auto-generated method stub
			status.setText("GPS Activated.");
			}

			public void onStatusChanged(String arg0, int arg1, Bundle arg2) {
				// TODO Auto-generated method stub
				showCurrentLocation();
				if(MyMain.finalized==true)
				{
				firecheck();
				}
				status.setText("Provider Status Changed.");
			}

		}




}
