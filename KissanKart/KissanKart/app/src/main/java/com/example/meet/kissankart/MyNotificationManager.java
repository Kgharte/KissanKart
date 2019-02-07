package com.example.meet.kissankart;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.support.v4.app.NotificationCompat;

import java.util.GregorianCalendar;

public class MyNotificationManager
{
    public static void addNotification(String title, String message,Context ctx,PendingIntent pintent,int RequestCode)
    {
        GregorianCalendar gc1 = new GregorianCalendar();

        NotificationCompat.Builder builder =
                new NotificationCompat.Builder(ctx);

        builder.setSmallIcon(R.mipmap.ic_launcher);
        builder.setContentTitle(title);
        builder.setWhen(gc1.getTimeInMillis());
        builder.setContentText(message);
        builder.setContentIntent(pintent);

        // Add as notification
        NotificationManager manager = (NotificationManager)
                ctx.getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(RequestCode, builder.build());
    }



}
