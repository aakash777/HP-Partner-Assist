package com.github.jlmd.animatedcircleloadingview.sample.ACTIVITY;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.speech.tts.TextToSpeech;
import android.widget.Toast;

import com.app.jlmd.animatedcircleloadingview.sample.R;
import com.github.jlmd.animatedcircleloadingview.AnimatedCircleLoadingView;
import com.github.jlmd.animatedcircleloadingview.sample.ACTIVITY.APP_UTILS.BYTECH_APP_CONSTANT;
import com.github.jlmd.animatedcircleloadingview.sample.ACTIVITY.MAIN_APPLICATION.BYTECH_CONNECTION_DETECTOR;
import com.pixplicity.easyprefs.library.Prefs;

import java.util.Calendar;

public class HP_PA_SPLASH extends Activity {

    private AnimatedCircleLoadingView animatedCircleLoadingView;
    private String current_date, current_time, day_of_week;
    private BYTECH_CONNECTION_DETECTOR cd;
    private Boolean isInternetPresent = false;
    AlertDialog ad;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        //layout declaration
        hppa_dcl_layout_t3();
        cd = new BYTECH_CONNECTION_DETECTOR(getApplicationContext());
        //Check TTS Installed or not
        //updated by Aakash
        //   initTts();
        isInternetPresent = cd.isConnectingToInternet();

    }
//on create ends here

    @Override
    protected void onResume() {

        // check for Internet status
        if (isInternetPresent) {
            startLoading();

            startPercentMockThread();

            Calendar c = Calendar.getInstance();
            int timeOfDay = c.get(Calendar.HOUR_OF_DAY);

            if (timeOfDay >= 0 && timeOfDay < 12) {
                Prefs.putString(BYTECH_APP_CONSTANT.shared_wishing_time,"Good Morning" );


            } else if (timeOfDay >= 12 && timeOfDay < 16) {
                Prefs.putString(BYTECH_APP_CONSTANT.shared_wishing_time,"Good Afternoon" );


            } else if (timeOfDay >= 16 && timeOfDay < 24) {
                Prefs.putString(BYTECH_APP_CONSTANT.shared_wishing_time,"Good Evening" );
            }
        } else {
            // Internet connection is not present
            // Ask user to connect to Internet
            showNetwrokMessage("","You don't have internet connection.", HP_PA_SPLASH.this ,R.drawable.ic_launcher);
        }

        super.onResume();

    }

    private void initTts() {
        Intent intent = new Intent();
        intent.setAction(TextToSpeech.Engine.ACTION_CHECK_TTS_DATA);
        startActivityForResult(intent, 0);
    }

    public void hppa_dcl_layout_t3() {
        setContentView(R.layout.hp_pa_splash);
        animatedCircleLoadingView = (AnimatedCircleLoadingView)
                findViewById(R.id.circle_loading_view);

    }
    public void showNetwrokMessage(String title, String message, Context context ,int res) {
        ad = new AlertDialog.Builder(HP_PA_SPLASH.this)
                .setIcon(res)
                .setMessage(message)
                .setPositiveButton("Cancel",
                        new android.content.DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog,
                                                int which) {
                                finish();
                                // the rest of your stuff
                            }
                        })
                .setNegativeButton("Go to Setting",
                        new android.content.DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog,
                                                int which) {
                                dialog.cancel();
                                startActivity(new Intent(
                                        android.provider.Settings.ACTION_WIFI_SETTINGS));
                            }
                        }).show();
    }
    private void startLoading() {
        animatedCircleLoadingView.startDeterminate();
    }


    private void startPercentMockThread() {
        Runnable runnable = new Runnable() {

            @Override
            public void run() {
                try {
                    Thread.sleep(1000);
                    for (int i = 0; i <= 100; i++) {
                        Thread.sleep(45);
                        changePercent(i);
                    }

                } catch (InterruptedException e) {


                    e.printStackTrace();


                } finally {
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    if(Prefs.getString(BYTECH_APP_CONSTANT.shared_partner_id, "").equals(""))
                    {
                        Intent splshint = new Intent(HP_PA_SPLASH.this, HP_PA_SIGNIN.class);
                        startActivity(splshint);
                        finish();
                    }
                    else{
                        Prefs.putInt(BYTECH_APP_CONSTANT.shared_home_speak_flag,1);
                        Intent splshint = new Intent(HP_PA_SPLASH.this, HP_PA_HOME.class);
                        startActivity(splshint);
                        finish();
                    }
                }
            }


        };

        new Thread(runnable).start();


    }


    private void changePercent(final int percent) {

        runOnUiThread(new Runnable() {

            @Override
            public void run() {

                animatedCircleLoadingView.setPercent(percent);

            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 0) {
            if (resultCode == TextToSpeech.Engine.CHECK_VOICE_DATA_PASS) {
                //  Toast.makeText(getApplicationContext(), "TTS Already Installed", Toast.LENGTH_LONG).show();
            } else {
                Intent installIntent = new Intent();
                installIntent.setAction(TextToSpeech.Engine.ACTION_INSTALL_TTS_DATA);
                startActivity(installIntent);
                Toast.makeText(getApplicationContext(), "Installed Now", Toast.LENGTH_LONG).show();
            }
        }
    }

}