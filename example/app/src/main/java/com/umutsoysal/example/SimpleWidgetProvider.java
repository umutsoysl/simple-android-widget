package com.umutsoysal.example;


import android.app.AlarmManager;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.widget.RemoteViews;

import java.util.Random;


public class SimpleWidgetProvider extends AppWidgetProvider {
    private PendingIntent service;
    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        final AlarmManager manager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        final Intent i = new Intent(context, UpdateService.class);

        if (service == null) {
            service = PendingIntent.getService(context, 0, i, PendingIntent.FLAG_CANCEL_CURRENT);
        }
        manager.setRepeating(AlarmManager.ELAPSED_REALTIME, SystemClock.elapsedRealtime(), 60000/12, service);
        //if you need to call your service less than 5 sec
        //answer is here:
        Log.d("UpdatingWidget: ","onUpdate");

        ComponentName thisWidget = new ComponentName(context,
                SimpleWidgetProvider.class);
        int[] allWidgetIds = appWidgetManager.getAppWidgetIds(thisWidget);
        for (int widgetId : allWidgetIds) {
            // create some random data
            int number = (new Random().nextInt(100));

            RemoteViews remoteViews = new RemoteViews(context.getPackageName(),
                    R.layout.simple_widget);
            Log.w("WidgetExample", String.valueOf(number));
            // Set the text
            remoteViews.setTextViewText(R.id.counter, String.valueOf(number));

            // Register an onClickListener
            Intent intent = new Intent(context, SimpleWidgetProvider.class);

            intent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
            intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, appWidgetIds);

            PendingIntent pendingIntent = PendingIntent.getBroadcast(context,
                    0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
            remoteViews.setOnClickPendingIntent(R.id.buttonPanel, pendingIntent);
            appWidgetManager.updateAppWidget(widgetId, remoteViews);
        }

        RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.simple_widget);
        //create intent
        Intent configIntent = new Intent(context, MainActivity.class);

        PendingIntent configPendingIntent = PendingIntent.getActivity(context, 0, configIntent, 0);
        //call button
        remoteViews.setOnClickPendingIntent(R.id.buttonPanel2, configPendingIntent);
        appWidgetManager.updateAppWidget(appWidgetIds, remoteViews);

    }

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
        Log.d("UpdatingWidget: ","onReceive");

    }

    @Override
    public void onAppWidgetOptionsChanged(Context context, AppWidgetManager appWidgetManager, int appWidgetId, Bundle newOptions) {
        super.onAppWidgetOptionsChanged(context, appWidgetManager, appWidgetId, newOptions);
        Log.d("UpdatingWidget: ","onAppWidgetOptionsChanged");

    }

    @Override
    public void onDeleted(Context context, int[] appWidgetIds) {
        super.onDeleted(context, appWidgetIds);
        Log.d("UpdatingWidget: ","onDeleted");

    }

    @Override
    public void onEnabled(Context context) {
        super.onEnabled(context);
        Log.d("UpdatingWidget: ","onEnabled");

    }

    @Override
    public void onDisabled(Context context) {
        super.onDisabled(context);
        Log.d("UpdatingWidget: ","onDisabled");

    }

    @Override
    public void onRestored(Context context, int[] oldWidgetIds, int[] newWidgetIds) {
        super.onRestored(context, oldWidgetIds, newWidgetIds);
        Log.d("UpdatingWidget: ","onRestored");

    }
}