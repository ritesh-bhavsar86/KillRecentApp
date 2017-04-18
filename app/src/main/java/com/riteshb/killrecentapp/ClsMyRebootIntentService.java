package com.riteshb.killrecentapp;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

public class ClsMyRebootIntentService extends IntentService {
	
	public ClsMyRebootIntentService() {
		super("Empty Constructor");
	}
	
	public ClsMyRebootIntentService(String name) {
		super(name);
	}

	
	@Override
	protected void onHandleIntent(Intent intent) {
		String intentType = intent.getExtras().getString("caller");
		if (intentType == null)
			return;
		if (intentType.equals("RebootReceiver")) {
			RemoteViews remoteViews_1 = new RemoteViews(getApplicationContext()
					.getPackageName(), R.layout.notification_view);
			Notify(getApplicationContext(), remoteViews_1, "Hi Madam",
					"Click to remove Recent Apps");
			;

		}
		// Do reboot stuff
		// handle other types of callers, like a notification.

	}

	private void Notify(Context context, RemoteViews view,
						String notificationTitle, String notificationMessage) {
		NotificationManager notificationManager = (NotificationManager) context
				.getSystemService(Context.NOTIFICATION_SERVICE);

		// app listener
		Intent app = new Intent(context, ClsWidgetProvider.switchButtonListener.class);
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
