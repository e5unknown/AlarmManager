package com.dev420.alarmmanager;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import androidx.core.app.NotificationCompat;

public class AlertReceiver extends BroadcastReceiver {
    public static final String ACTION_ALARM = "com.dev420.alarmmanager.ACTION_ALARM";

    @Override
    public void onReceive(Context context, Intent intent) {
        NotificationHelper helper = new NotificationHelper(context);
        NotificationCompat.Builder nb = helper.getChannelNotification();
        helper.getManager().notify(1, nb.build());
    }
}
