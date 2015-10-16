package com.github.jlmd.animatedcircleloadingview.sample.ACTIVITY;

import android.app.Activity;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Vector;
import javax.xml.parsers.ParserConfigurationException;
import org.ispeech.MarkerHolder;
import org.ispeech.SpeechSynthesis;
import org.ispeech.SpeechSynthesisEvent;
import org.ispeech.VisemeHolder;
import org.ispeech.error.BusyException;
import org.ispeech.error.InvalidApiKeyException;
import org.ispeech.error.NoNetworkException;
import org.xml.sax.SAXException;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.net.Uri.Builder;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.Toast;
import com.app.jlmd.animatedcircleloadingview.sample.R;

/**
 * Created by Mainak Karmakar on 15-10-2015.
 */
public class ISPEECH_TTS extends Activity {

    private static final String TAG = "iSpeech SDK Sample";
    SpeechSynthesis synthesis;
    Context _context;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        _context = this.getApplicationContext();

        setContentView(R.layout.hp_pa_home);

//        ((EditText) findViewById(R.id.text)).setText(R.string.tts_sample_text);
        findViewById(R.id.home_speech_imgbtn).setOnClickListener(new OnSpeakListener());
        findViewById(R.id.home_speech_outline_imgbtn).setOnClickListener(new OnStopListener());
        prepareTTSEngine();
        synthesis.setStreamType(AudioManager.STREAM_MUSIC);

        try {
            String ttsText = "Hello Rajesh . Welcome to Byytech  India";
            synthesis.speak(ttsText);
        } catch (BusyException e) {
            Log.e(TAG, "SDK is busy");
            e.printStackTrace();
            Toast.makeText(_context, "ERROR: SDK is busy", Toast.LENGTH_LONG).show();
        } catch (NoNetworkException e) {
            Log.e(TAG, "Network is not available\n" + e.getStackTrace());
            Toast.makeText(_context, "ERROR: Network is not available", Toast.LENGTH_LONG).show();
        }

    }


    private void prepareTTSEngine() {
        try {
            synthesis = SpeechSynthesis.getInstance(this);
            synthesis.setSpeechSynthesisEvent(new SpeechSynthesisEvent() {

                public void onPlaySuccessful() {
                    Log.i(TAG, "onPlaySuccessful");
                }

                public void onPlayStopped() {
                    Log.i(TAG, "onPlayStopped");
                }

                public void onPlayFailed(Exception e) {
                    Log.e(TAG, "onPlayFailed");

                    AlertDialog.Builder builder = new AlertDialog.Builder(ISPEECH_TTS.this);
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


    private class OnSpeakListener implements View.OnClickListener {

        public void onClick(View v) {

            try {
                String ttsText = "hello rajesh welcome to bytech india";
                synthesis.speak(ttsText);
            } catch (BusyException e) {
                Log.e(TAG, "SDK is busy");
                e.printStackTrace();
                Toast.makeText(_context, "ERROR: SDK is busy", Toast.LENGTH_LONG).show();
            } catch (NoNetworkException e) {
                Log.e(TAG, "Network is not available\n" + e.getStackTrace());
                Toast.makeText(_context, "ERROR: Network is not available", Toast.LENGTH_LONG).show();
            }
        }
    }


    public class OnStopListener implements View.OnClickListener {

        public void onClick(View v) {
            if (synthesis != null) {
                synthesis.stop();
            }

        }
    }



    @Override
    protected void onPause() {
        synthesis.stop();	//Optional to stop the playback when the activity is paused
        super.onPause();
    }




}
