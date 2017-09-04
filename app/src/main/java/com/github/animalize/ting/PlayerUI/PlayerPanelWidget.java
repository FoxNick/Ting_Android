package com.github.animalize.ting.PlayerUI;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.SeekBar;

import com.github.animalize.ting.R;
import com.github.animalize.ting.TTS.ArticleTtsService;


public class PlayerPanelWidget extends LinearLayout implements View.OnClickListener {
    private ArticleTtsService.ArticleTtsBinder mBinder;

    private SeekBar mProgress;
    private Button mPlay;

    private LocalBroadcastManager mLBM;
    private SpeechEventReciver mSpeechEventReciver = new SpeechEventReciver();
    private SpeechStartReciver mSpeechStartReciver = new SpeechStartReciver();

    public PlayerPanelWidget(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.view_player_panel, this);

        mProgress = (SeekBar) findViewById(R.id.progress);

        mPlay = (Button) findViewById(R.id.play_pause);
        mPlay.setOnClickListener(this);
        Button bt = (Button) findViewById(R.id.stop);
        bt.setOnClickListener(this);
    }

    public void setTTSBinder(ArticleTtsService.ArticleTtsBinder binder) {
        mBinder = binder;

        mBinder.play();
        refreshPlayButton();
    }

    private void refreshPlayButton() {
        int state = mBinder.getState();

        String s;
        if (state == ArticleTtsService.PLAYING) {
            s = "暂停";
        } else if (state == ArticleTtsService.PAUSING) {
            s = "恢复";
        } else {
            s = "播放";
        }
        mPlay.setText(s);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.play_pause:
                int state = mBinder.getState();

                if (state == ArticleTtsService.PLAYING) {
                    mBinder.pause();
                } else if (state == ArticleTtsService.PAUSING) {
                    mBinder.resume();
                } else if (state == ArticleTtsService.STOP) {
                    mBinder.play();
                }
                refreshPlayButton();
                break;

            case R.id.stop:
                mBinder.stop();
                refreshPlayButton();
                break;
        }
    }

    public void onStart() {
        mLBM.registerReceiver(
                mSpeechEventReciver,
                ArticleTtsService.getSpeechEventIntentFilter());
        mLBM.registerReceiver(
                mSpeechStartReciver,
                ArticleTtsService.getSpeechStartIntentFilter());
    }

    public void onStop() {
        mLBM.unregisterReceiver(mSpeechEventReciver);
        mLBM.unregisterReceiver(mSpeechStartReciver);
    }

    public void setLBM(LocalBroadcastManager LBM) {
        mLBM = LBM;
    }

    private class SpeechStartReciver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
        }
    }

    private class SpeechEventReciver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
        }
    }
}