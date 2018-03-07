package com.umutsoysal.example;

import android.app.Service;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.widget.RemoteViews;

import java.util.Random;

public class UpdateService extends Service {
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        Random rand = new Random();
        int  number = rand.nextInt(50) + 1;

        RemoteViews view = new RemoteViews(getPackageName(), R.layout.simple_widget);
        view.setTextViewText(R.id.counter, String.valueOf(number));
        ComponentName theWidget = new ComponentName(this, SimpleWidgetProvider.class);
        AppWidgetManager manager = AppWidgetManager.getInstance(this);
        manager.updateAppWidget(theWidget, view);

        return super.onStartCommand(intent, flags, startId);
    }
}
