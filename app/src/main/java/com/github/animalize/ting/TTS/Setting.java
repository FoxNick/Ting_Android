package com.github.animalize.ting.TTS;


import android.util.Log;

import com.baidu.tts.client.SpeechSynthesizer;

public abstract class Setting {
    public static final int MIX_MODE_DEFAULT = 0;
    public static final int MIX_MODE_HIGH_SPEED_NETWORK = 1;
    public static final int MIX_MODE_HIGH_SPEED_SYNTHESIZE = 2;
    public static final int MIX_MODE_HIGH_SPEED_SYNTHESIZE_WIFI = 3;

    // 音量，范围[0-9]
    private int mVolume = 5;
    // 语速，范围[0-9]
    private int mSpeed = 5;
    // 语调，范围[0-9]
    private int mPitch = 5;
    /*
    * 0 (普通女声)
    * 1 (普通男声)
    * 2 (特别男声)
    * 3 (情感男声<度逍遥>)
    * 4 (情感儿童声<度丫丫>)
    * */
    private int mSpeaker = 1;
    /*
    * MIX_MODE_DEFAULT
    * (mix模式下，wifi使用在线合成，非wifi使用离线合成)
    * MIX_MODE_HIGH_SPEED_NETWORK
    * (mix模式下，wifi,4G,3G使用在线合成，其他使用离线合成)
    * MIX_MODE_HIGH_SPEED_SYNTHESIZE
    * (mix模式下，在线返回速度如果慢（超时，一般为1.2秒）直接切换离线，适用于网络环境较差的情况)
    * MIX_MODE_HIGH_SPEED_SYNTHESIZE_WIFI
    * (mix模式下，仅wifi使用在线合成,返回速度如果慢（超时，一般为1.2秒）直接切换离线，适用于仅WIFI网络环境较差的情况)
    * */
    private int mMixMode = MIX_MODE_HIGH_SPEED_SYNTHESIZE;

    public Setting() {
        loadSetting();
    }

    public abstract void loadSetting();

    public abstract void saveSetting();

    public int getmVolume() {
        return mVolume;
    }

    public void setmVolume(int mVolume) {
        if (mVolume < 0) {
            this.mVolume = 0;
        } else if (mVolume > 9) {
            this.mVolume = 9;
        } else {
            this.mVolume = mVolume;
        }
    }

    public int getmSpeed() {
        return mSpeed;
    }

    public void setmSpeed(int mSpeed) {
        if (mSpeed < 0) {
            this.mSpeed = 0;
        } else if (mSpeed > 9) {
            this.mSpeed = 9;
        } else {
            this.mSpeed = mSpeed;
        }
    }

    public int getmPitch() {
        return mPitch;
    }

    public void setmPitch(int mPitch) {
        if (mPitch < 0) {
            this.mPitch = 0;
        } else if (mPitch > 9) {
            this.mPitch = 9;
        } else {
            this.mPitch = mPitch;
        }
    }

    public int getmSpeaker() {
        return mSpeaker;
    }

    public void setmSpeaker(int mSpeaker) {
        if (mSpeaker < 0 || mSpeaker > 4) {
            this.mSpeaker = 0;
        } else {
            this.mSpeaker = mSpeaker;
        }
    }

    public int getmMixMode() {
        return mMixMode;
    }

    public void setmMixMode(int mMixMode) {
        if (mMixMode < 0 || mMixMode > 3) {
            this.mMixMode = 0;
        } else {
            this.mMixMode = mMixMode;
        }
    }

    public void setSettingToSpeechSynthesizer(SpeechSynthesizer ss) {
        ss.setParam(
                SpeechSynthesizer.PARAM_VOLUME,
                "" + getmVolume());

        ss.setParam(
                SpeechSynthesizer.PARAM_SPEED,
                "" + getmSpeed());

        ss.setParam(
                SpeechSynthesizer.PARAM_PITCH,
                "" + getmPitch());

        Log.i("setSetizer: ", "xxx " + getmSpeaker());
        ss.setParam(
                SpeechSynthesizer.PARAM_SPEAKER,
                "" + getmSpeaker());

        String mixMode;
        switch (getmMixMode()) {
            case MIX_MODE_DEFAULT:
                mixMode = SpeechSynthesizer.MIX_MODE_DEFAULT;
                break;

            case MIX_MODE_HIGH_SPEED_NETWORK:
                mixMode = SpeechSynthesizer.MIX_MODE_HIGH_SPEED_NETWORK;
                break;

            case MIX_MODE_HIGH_SPEED_SYNTHESIZE:
                mixMode = SpeechSynthesizer.MIX_MODE_HIGH_SPEED_SYNTHESIZE;
                break;

            case MIX_MODE_HIGH_SPEED_SYNTHESIZE_WIFI:
                mixMode = SpeechSynthesizer.MIX_MODE_HIGH_SPEED_SYNTHESIZE_WIFI;
                break;

            default:
                mixMode = SpeechSynthesizer.MIX_MODE_DEFAULT;
        }
        ss.setParam(SpeechSynthesizer.PARAM_MIX_MODE, mixMode);
    }
}