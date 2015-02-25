package com.GPSapp.TrackSpot;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
 
public class MyMap extends MapActivity 
{    
    MapView mapView; 
    MapController mc;
    GeoPoint p;
    Button whereami,change;
    TextView mylatitude,mylongitude;
    Boolean satview=false;
    
    class MapOverlay extends com.google.android.maps.Overlay
    {

    	@Override
		public boolean onTouchEvent(MotionEvent event, MapView mapView) {
		// TODO Auto-generated method stub
      	//---when user lifts his finger---
            if (event.getAction() == 1) {                
                GeoPoint p = mapView.getProjection().fromPixels(
                    (int) event.getX(),
                    (int) event.getY());
                    Toast.makeText(getBaseContext(), 
                        p.getLatitudeE6() / 1E6 + "," + 
                        p.getLongitudeE6() /1E6 , 
                        Toast.LENGTH_SHORT).show();
            }                            
            return false;
        
		}
    	
    	
    	
    	
    }
    
    
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.maplayout);
 
        whereami=(Button) findViewById(R.id.Button03);
        mylatitude=(TextView) findViewById(R.id.TextView03);
        mylongitude=(TextView) findViewById(R.id.TextView05);
        
        change=(Button) findViewById(R.id.Button04);
        
        mapView = (MapView) findViewById(R.id.mapView);
        
        
 
        mc = mapView.getController();
        //String coordinates[] = {"1.352566007", "103.78921587"};
        
        
        double lat = Double.parseDouble(MyMain.latstr);
        double lng = Double.parseDouble(MyMain.lonstr);
 
        p = new GeoPoint(
            (int) (lat * 1E6), 
            (int) (lng * 1E6));
        
        
        whereami.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				mc.animateTo(p);
		        mc.setZoom(17); 
		        mapView.invalidate();
			}
		});
 
        
        
        change.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
	
				if(satview==false){
					satview=true;
					mapView.setSatellite(true);
					
					//
				}
				else{
					satview=false;
					mapView.setSatellite(false);
					mapView.setStreetView(true);	
				}
				
			}
		});
        
        
    }
 
    @Override
    protected boolean isRouteDisplayed() {
        // TODO Auto-generated method stub
        return false;
    }
}