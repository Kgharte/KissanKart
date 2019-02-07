package com.example.meet.kissankart;

import android.content.Context;
import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;
import com.google.firebase.messaging.FirebaseMessaging;

/**
 * Created by KALPESH GHARTE on 3/27/2018.
 */

public class MyFirebaseInstanceIDService extends FirebaseInstanceIdService
{
    public void onTokenRefreh()
    {
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        FirebaseMessaging.getInstance().subscribeToTopic("global");
        FirebaseMessaging.getInstance().subscribeToTopic("topic1");

        Log.d("firebase trace", "Refreshed token: " + refreshedToken);
        Context ctx = getApplicationContext();
        DataStorage storage = new DataStorage(ctx.getResources().getString(R.string.sharedprefname),ctx);
        storage.write("regid",refreshedToken);
        Log.d("firebase trace", "token from sharedpreference" + storage.read("regid",DataStorage.STRING).toString());



    }
}
