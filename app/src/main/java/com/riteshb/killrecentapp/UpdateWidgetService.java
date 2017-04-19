package com.riteshb.killrecentapp;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.widget.RemoteViews;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by riteshb on 4/19/2017.
 */

public class UpdateWidgetService extends Service {

    @Override
    public void onStart(Intent intent, int startId) {
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this
                .getApplicationContext());

        int[] allWidgetIds = intent
                .getIntArrayExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS);

        for (int widgetId : allWidgetIds) {
            RemoteViews remoteViews = new RemoteViews(this.getApplicationContext().getPackageName(),
                    R.layout.widget_layout);
            Class serviceManagerClass;
            try {
                serviceManagerClass = Class
                        .forName("android.os.ServiceManager");

                Method getService = serviceManagerClass.getMethod("getService",
                        String.class);
                IBinder retbinder = (IBinder) getService.invoke(
                        serviceManagerClass, "statusbar");
                Class statusBarClass = Class.forName(retbinder
                        .getInterfaceDescriptor());
                Object statusBarObject = statusBarClass.getClasses()[0]
                        .getMethod("asInterface", IBinder.class).invoke(null,
                                new Object[]{retbinder});
                Method clearAll = statusBarClass.getMethod("toggleRecentApps");
                clearAll.setAccessible(true);
                clearAll.invoke(statusBarObject);
//                Toast.makeText(
//                        this,
//                        "Developed by : Ritesh Bhavsar, \nSr. Android Developer\nContact: 8087265182",
//                        Toast.LENGTH_LONG).show();
            } catch (ClassNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (NoSuchMethodException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IllegalArgumentException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (RemoteException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            // Register an onClickListener
            Intent intentwidget = new Intent(this, ClsWidgetProvider.class);

            intentwidget.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
            intentwidget.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, allWidgetIds);

            PendingIntent pendingIntent = PendingIntent.getBroadcast(this,
                    0, intentwidget, PendingIntent.FLAG_UPDATE_CURRENT);
            remoteViews.setOnClickPendingIntent(R.id.update, pendingIntent);

            RemoteViews remoteViews_1 = new RemoteViews(
                    this.getApplicationContext().getPackageName(), R.layout.notification_view);
            Notify(this, remoteViews_1, "Hello Sir/Madam",
                    "Click to remove Recent Apps");
            ;

            appWidgetManager.updateAppWidget(widgetId, remoteViews);
        }
        stopSelf();

        super.onStart(intent, startId);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private void Notify(Context context, RemoteViews view,
                        String notificationTitle, String notificationMessage) {
        NotificationManager notificationManager = (NotificationManager) context
                .getSystemService(Context.NOTIFICATION_SERVICE);

        // app listener
        Intent app = new Intent(context, switchButtonListener.class);
        app.putExtra("DO", "app");
        PendingIntent pApp = PendingIntent.getBroadcast(context, 0, app, 0);
        view.setOnClickPendingIntent(R.id.update_noti, pApp);


        Notification.Builder builder = new Notification.Builder(context);

        builder.setAutoCancel(false);
        builder.setTicker("this is ticker text");
        builder.setContentTitle("WhatsApp Notification");
        builder.setContentText("You have a new message");
        builder.setSmallIcon(R.drawable.recent_2);
        builder.setContentIntent(pApp);
        builder.setOngoing(true);
//        builder.setSubText("This is subtext...");   //API level 16
//        builder.setNumber(100);
//        builder.

        Notification myNotication = builder.getNotification();
        myNotication.contentView = view;
        myNotication.flags |= Notification.FLAG_ONGOING_EVENT;
        notificationManager.notify(11, myNotication);

    }
}

