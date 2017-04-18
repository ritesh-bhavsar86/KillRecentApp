package com.riteshb.killrecentapp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class ClsMyRebootReceiver extends BroadcastReceiver{

	@Override
	public void onReceive(Context context, Intent intent) {
		Intent serviceIntent = new Intent(context, ClsMyRebootIntentService.class);
        serviceIntent.putExtra("caller", "RebootReceiver");
        context.startService(serviceIntent);
		
	}

	
}
