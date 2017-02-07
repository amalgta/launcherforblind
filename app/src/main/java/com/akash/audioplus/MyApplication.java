package com.akash.audioplus;

import android.app.Application;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.speech.tts.TextToSpeech;
import android.speech.tts.TextToSpeech.OnInitListener;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;


public class MyApplication extends Application implements OnInitListener {
    private static final String TAG = "app";
    private static Context context;
    protected int currentCallState;
    private BroadcastReceiver screenStateChangeReceiver;
    private TextToSpeech tts;
    private boolean ttsInitialized;

    @Override
    public void onCreate() {
        super.onCreate();
        MyApplication.context = getApplicationContext();


        tts = new TextToSpeech(this, this);
        ttsInitialized = false;

        TelephonyManager tm = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        tm.listen(new PhoneStateListener() {
            @Override
            public void onCallStateChanged(int state, String incomingNumber) {
                currentCallState = state;
            }
        }, PhoneStateListener.LISTEN_CALL_STATE);

        // Receive notifications about the screen power changes
        screenStateChangeReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if (intent.getAction().equals(Intent.ACTION_SCREEN_ON)) {
                    Log.e("currentCallState", currentCallState + "");
                    // If the phone is ringing or the user is talking,
                    // don't try do anything else.
                    if (currentCallState != TelephonyManager.CALL_STATE_IDLE) {
                        return;
                    }
                    if (ttsInitialized) {
                        tts.speak(getString(R.string.please_unlock), 0, null);
                    }
                }
            }
        };
        final IntentFilter screenStateChangeFilter = new IntentFilter(
                Intent.ACTION_SCREEN_ON);
        screenStateChangeFilter.addAction(Intent.ACTION_SCREEN_OFF);
        registerReceiver(screenStateChangeReceiver, screenStateChangeFilter);
    }

    public static Context getAppContext() {
        return MyApplication.context;
    }

    @Override
    public void onInit(int status) {
        if (status == TextToSpeech.SUCCESS) {
            // tts.setLanguage(Locale.GERMAN);
            ttsInitialized = true;
        } else {
            Log.e(TAG, "Initialization failed");
        }
    }

    @Override
    public void onTerminate() {
        super.onTerminate();

        if (screenStateChangeReceiver != null) {
            unregisterReceiver(screenStateChangeReceiver);
            screenStateChangeReceiver = null;
        }

        if (tts != null) {
            tts.stop();
            tts.shutdown();
            tts = null;
        }
        ttsInitialized = false;
    }
}
