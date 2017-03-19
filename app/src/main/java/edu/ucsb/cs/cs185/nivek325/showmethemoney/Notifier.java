package edu.ucsb.cs.cs185.nivek325.showmethemoney;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;


/**
 * Created by Wesley on 3/18/2017.
 */

public class Notifier extends Service {
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        long[] vibes = {0,50,5};
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        TaskStackBuilder sb = TaskStackBuilder.create(getApplicationContext());
        sb.addParentStack(MainActivity.class);
        sb.addNextIntent(intent);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this,123,intent,0);
        NotificationCompat.Builder build = new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.ic_money)
                .setContentTitle("Payment Reminder")
                .setVibrate(vibes)
                .setAutoCancel(true);
        build.setContentIntent(pendingIntent);
        build.setDefaults(Notification.DEFAULT_SOUND);
        build.setAutoCancel(true);
        NotificationManager manager = (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(123,build.build());
    }
}
