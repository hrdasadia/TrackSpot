package com.GPSapp.TrackSpot;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.gsm.SmsMessage;
import android.widget.Toast;
 

@SuppressWarnings("deprecation")
public class SmsReceiver extends BroadcastReceiver
{
    @Override
    public void onReceive(Context context, Intent intent) 
    {
    	String universal="trackspot";
    	String address,message;
        //---get the SMS message passed in---
        Bundle bundle = intent.getExtras();        
        SmsMessage[] msgs = null;
        String str = "";            
        if (bundle != null)
        {
            //---retrieve the SMS message received---
            Object[] pdus = (Object[]) bundle.get("pdus");
            msgs = new SmsMessage[pdus.length];            
            for (int i=0; i<msgs.length; i++){
                msgs[i] = SmsMessage.createFromPdu((byte[])pdus[i]);                
                address = msgs[i].getOriginatingAddress();                     
                message = msgs[i].getMessageBody().toString();
                message = message.toLowerCase();
                if(universal.equals(message)&&MyMain.manual==true){
                	str="Request for Location Recieved";
                	MyMain.reqrec=true;
                	MyMain.destination=address;
                	MyMain.sendmsg();
                }
                
                
                        
            }
            //---display the new SMS message---
            Toast.makeText(context, str, Toast.LENGTH_SHORT).show();
        }                         
    }
}