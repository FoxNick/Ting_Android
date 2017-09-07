package com.github.animalize.ting.TingTTS;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.Intent;
import android.support.v7.app.NotificationCompat;

import com.github.animalize.ting.MainListActivity;
import com.github.animalize.ting.R;
import com.github.animalize.ting.TTS.Setting;
import com.github.animalize.ting.TTS.TTSService;

public class TingTTSService extends TTSService {
    @Override
    public void initTTS() {
        TTSInitializer.initialEnv(this);
    }

    @Override
    public void doStartForeground() {
        // 前台服务
        Intent notificationIntent = new Intent(this, MainListActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(
                this, 0, notificationIntent, 0);

        Notification notification = new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.notifiy_icon)
                .setContentTitle("ting的前台服务")
                .setContentText("")
                .setContentIntent(pendingIntent)
                .build();
        startForeground(828, notification);
    }

    @Override
    public Setting getSetting() {
        return TingSetting.getInstance();
    }
}
