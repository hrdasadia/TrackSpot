package com.GPSapp.TrackSpot;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;
import com.google.android.maps.MapView.LayoutParams;
 
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Point;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;
 
public class MyMap2 extends MapActivity 
{    
    MapView mapView; 
    MapController mc;
    GeoPoint p;
    
  
    
    
    class MapOverlay extends com.google.android.maps.Overlay
    {
    	
		@Override
		public boolean draw(Canvas canvas, MapView mapView, boolean shadow,
				long when) {
			// TODO Auto-generated method stub
			super.draw(canvas, mapView, shadow);                   
			 
            //---translate the GeoPoint to screen pixels---
            Point screenPts = new Point();
            mapView.getProjection().toPixels(p, screenPts);
 
            //---add the marker---
            Bitmap bmp = BitmapFactory.decodeResource(
                getResources(), R.drawable.pushpin);            
            canvas.drawBitmap(bmp, screenPts.x, screenPts.y-50, null); 
			
			return true;
		}
		
		
    	
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
                    MyMain.target_latitude=Double.toString(p.getLatitudeE6() / 1E6);
                    MyMain.target_longitude=Double.toString(p.getLongitudeE6() / 1E6);
                    
            //-----------------------Testing for address needs internet
                  /* 
                    Geocoder geoCoder = new Geocoder(
                            getBaseContext(), Locale.getDefault());
                        try {
                            List<Address> addresses = geoCoder.getFromLocation(
                                p.getLatitudeE6()  / 1E6, 
                                p.getLongitudeE6() / 1E6, 1);
         
                            MyMain.add = "";
                            if (addresses.size() > 0) 
                            {
                                for (int i=0; i<addresses.get(0).getMaxAddressLineIndex(); 
                                     i++)
                                   MyMain.add += addresses.get(0).getAddressLine(i) + "\n";
                            }
         
                            Toast.makeText(getBaseContext(), MyMain.add, Toast.LENGTH_SHORT).show();
                        }
                        catch (IOException e) {                
                            e.printStackTrace();
                        }
                        
                    */    
                        
                        //------------test done
            }                            
            return false;
        
		}
    } 

    
    /** Called when the activity is first created. */

	@SuppressWarnings({ "deprecation" })
	@Override
    public void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mainmap);
 
        mapView = (MapView) findViewById(R.id.mapView);
        LinearLayout zoomLayout = (LinearLayout)findViewById(R.id.zoom);  
        View zoomView = mapView.getZoomControls(); 
 
        zoomLayout.addView(zoomView, 
            new LinearLayout.LayoutParams(
                LayoutParams.WRAP_CONTENT, 
                LayoutParams.WRAP_CONTENT)); 
        mapView.displayZoomControls(true);
 
        mc = mapView.getController();
        String coordinates[] = {MyMain.latstr, MyMain.lonstr};		//Last Known co-ordinates
        double lat = Double.parseDouble(coordinates[0]);
        double lng = Double.parseDouble(coordinates[1]);
 
        p = new GeoPoint(
            (int) (lat * 1E6), 
            (int) (lng * 1E6));
 
        mc.animateTo(p);
        mc.setZoom(17); 
        
        
        mapView.setSatellite(true);
        //If Location is disturbed, restore original
        if(MyMain.showdialog)
        {
        	MyMain.latstr=MyMain.templat;
    		MyMain.lonstr=MyMain.templon;
    		MyMain.showdialog=false;
	    }
        
      //---Add a location marker---
        MapOverlay mapOverlay = new MapOverlay();
        List<Overlay> listOfOverlays = mapView.getOverlays();
        listOfOverlays.clear();
        listOfOverlays.add(mapOverlay);
        
        
        mapView.invalidate();
    }
 
    @Override
    protected boolean isRouteDisplayed() {
        // TODO Auto-generated method stub
        return false;
    }
}