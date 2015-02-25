package com.GPSapp.TrackSpot;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.widget.ImageView;

public class splashact extends Activity {
	MediaPlayer startsound;
	Thread timer;	
	ImageView wallpic;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		
		startsound = MediaPlayer.create(this, R.raw.potter);
		startsound.start();
		
		timer=new Thread(){
		
			public void run(){
				
				try{
					int i;
					for(i=0;i<=800;i=i+100)
					{
						sleep(1000);

					}

					startActivity(new Intent("com.GPSapp.TrackSpot.CLEARSCREEN"));	
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				finally{
					finish();
				}
			}
			
			
	};
	
	
	timer.start();
	
	
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		startsound.stop();
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		startsound.pause();
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		startsound.start();
	}
}