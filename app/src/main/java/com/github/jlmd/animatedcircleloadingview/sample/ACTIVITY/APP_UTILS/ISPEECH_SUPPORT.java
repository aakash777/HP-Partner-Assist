package com.github.jlmd.animatedcircleloadingview.sample.ACTIVITY.APP_UTILS;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.media.AudioManager;
import android.util.Log;
import android.widget.Toast;

import org.ispeech.SpeechSynthesis;
import org.ispeech.SpeechSynthesisEvent;
import org.ispeech.error.BusyException;
import org.ispeech.error.InvalidApiKeyException;
import org.ispeech.error.NoNetworkException;

/**
 * Created by Mainak Karmakar on 17-10-2015.
 */
public class ISPEECH_SUPPORT {

    private static final String TAG = "iSpeech SDK Sample";
    private SpeechSynthesis synthesis;
    private Context _context;

    public ISPEECH_SUPPORT(Context context) {
        this._context = context;
    }

    public void prepareTTSEngine(Activity myclass) {
        try {

            synthesis = SpeechSynthesis.getInstance(myclass);
            synthesis.setSpeechSynthesisEvent(new SpeechSynthesisEvent() {

                public void onPlaySuccessful() {
                    Log.i(TAG, "onPlaySuccessful");
                }

                public void onPlayStopped() {
                    Log.i(TAG, "onPlayStopped");
                }

                public void onPlayFailed(Exception e) {
                    Log.e(TAG, "onPlayFailed");

                    AlertDialog.Builder builder = new AlertDialog.Builder(_context);
                    builder.setMessage("Error[TTSActivity]: " + e.toString())
                            .setCancelable(false)
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {

                                }
                            });
                    AlertDialog alert = builder.create();
                    alert.show();
                }

                public void onPlayStart() {
                    Log.i(TAG, "onPlayStart");
                }

                @Override
                public void onPlayCanceled() {
                    Log.i(TAG, "onPlayCanceled");
                }
            });

        } catch (InvalidApiKeyException e) {
            Log.e(TAG, "Invalid API key\n" + e.getStackTrace());
            Toast.makeText(_context, "ERROR: Invalid API key", Toast.LENGTH_LONG).show();
        }
    }

    public void set_stream()
    {
        synthesis.setStreamType(AudioManager.STREAM_MUSIC);
    }
    public void go_speek(String text)
    {
        try {
            synthesis.speak(text);
        } catch (BusyException e) {
            Log.e(TAG, "SDK is busy");
            e.printStackTrace();
        } catch (NoNetworkException e) {
            Log.e(TAG, "Network is not available\n" + e.getStackTrace());
            Toast.makeText(_context, "ERROR: Network is not available", Toast.LENGTH_LONG).show();
        }
    }
}
