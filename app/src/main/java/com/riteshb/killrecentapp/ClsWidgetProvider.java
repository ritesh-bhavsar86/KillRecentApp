package com.riteshb.killrecentapp;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.widget.RemoteViews;
/**
 * Created by riteshb on 4/17/2017.
 */

public class ClsWidgetProvider extends AppWidgetProvider {
    @Override
    public void onUpdate(final Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // Get all ids
        ComponentName thisWidget = new ComponentName(context,
                ClsWidgetProvider.class);
        int[] allWidgetIds = appWidgetManager.getAppWidgetIds(thisWidget);


        // Build the intent to call the service
        final Intent intent = new Intent(context.getApplicationContext(),
                UpdateWidgetService.class);
        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, allWidgetIds);

        // Update the widgets via the service
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                context.startService(intent);
            }
        }, 500);


//        for (int widgetId : allWidgetIds) {
//            // create some random data
//            // int number = (new Random().nextInt(100));
//            //
//            // RemoteViews remoteViews = new
//            // RemoteViews(context.getPackageName(),
//            // R.layout.widget_layout);
//            // Log.w("WidgetExample", String.valueOf(number));
//            // // Set the text
//            // remoteViews.setTextViewText(R.id.update, String.valueOf(number));
//            RemoteViews remoteViews = new RemoteViews(context.getPackageName(),
//                    R.layout.widget_layout);
//            Class serviceManagerClass;
//            try {
//                serviceManagerClass = Class
//                        .forName("android.os.ServiceManager");
//
//                Method getService = serviceManagerClass.getMethod("getService",
//                        String.class);
//                IBinder retbinder = (IBinder) getService.invoke(
//                        serviceManagerClass, "statusbar");
//                Class statusBarClass = Class.forName(retbinder
//                        .getInterfaceDescriptor());
//                Object statusBarObject = statusBarClass.getClasses()[0]
//                        .getMethod("asInterface", IBinder.class).invoke(null,
//                                new Object[]{retbinder});
//                Method clearAll = statusBarClass.getMethod("toggleRecentApps");
//                clearAll.setAccessible(true);
//                clearAll.invoke(statusBarObject);
//                Toast.makeText(
//                        context,
//                        "Developed by : Ritesh Bhavsar, \nSr. Android Developer\nContact: 8087265182",
//                        Toast.LENGTH_LONG).show();
//            } catch (ClassNotFoundException e) {
//                // TODO Auto-generated catch block
//                e.printStackTrace();
//            } catch (NoSuchMethodException e) {
//                // TODO Auto-generated catch block
//                e.printStackTrace();
//            } catch (IllegalAccessException e) {
//                // TODO Auto-generated catch block
//                e.printStackTrace();
//            } catch (IllegalArgumentException e) {
//                // TODO Auto-generated catch block
//                e.printStackTrace();
//            } catch (InvocationTargetException e) {
//                // TODO Auto-generated catch block
//                e.printStackTrace();
//            } catch (RemoteException e) {
//                // TODO Auto-generated catch block
//                e.printStackTrace();
//            }
//
//            // Register an onClickListener
//            Intent intent = new Intent(context, ClsWidgetProvider.class);
//
//            intent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
//            intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, appWidgetIds);
//
//            PendingIntent pendingIntent = PendingIntent.getBroadcast(context,
//                    0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
//            remoteViews.setOnClickPendingIntent(R.id.update, pendingIntent);
//
//            RemoteViews remoteViews_1 = new RemoteViews(
//                    context.getPackageName(), R.layout.notification_view);
//            Notify(context, remoteViews_1, "Hello Sir/Madam",
//                    "Click to remove Recent Apps");
//            ;
//
//            appWidgetManager.updateAppWidget(widgetId, remoteViews);
//        }
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
