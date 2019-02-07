package com.example.meet.kissankart;

import android.app.PendingIntent;
import android.content.Intent;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by KALPESH GHARTE on 3/27/2018.
 */

public class MyFirebaseMessagingService extends FirebaseMessagingService
{
    public void onMessageRecived(RemoteMessage remoteMessage)
    {
        if (remoteMessage.getData().size() > 0)
        {
            Log.d("firebase message", "Message data payload: " + remoteMessage.getData());
            String title,message,activity;
            try
            {
                JSONObject object = new JSONObject(remoteMessage.getData().toString());
                JSONObject ThirdLevelObject = object.getJSONObject("data").getJSONObject("payload");
                title = ThirdLevelObject.getString("title");
                message = ThirdLevelObject.getString("message");
                activity = ThirdLevelObject.getString("activity");

                Log.d("firebase data","title" + title + " message " + message + " activity " + activity);
                Intent FutureIntent=null;
                if(activity.equals("home")==true)
                    FutureIntent = new Intent(getApplicationContext(),CategoryContainer.class);
                else if(activity.equals("promotional")==true)
                    FutureIntent = new Intent(getApplicationContext(),CartContainer.class);
                else
                    FutureIntent = new Intent(getApplicationContext(),CartContainer.class);
                PendingIntent pintent = PendingIntent.getActivity(getApplicationContext(),99,FutureIntent,
                        PendingIntent.FLAG_UPDATE_CURRENT);
                MyNotificationManager.addNotification(title,message,getApplicationContext(),pintent,99);
            }
            catch (JSONException e)
            {
                e.printStackTrace();
            }

        }

    }
}
