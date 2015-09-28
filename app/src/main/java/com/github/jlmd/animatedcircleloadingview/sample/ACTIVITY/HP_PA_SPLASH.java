package com.github.jlmd.animatedcircleloadingview.sample.ACTIVITY;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.widget.Toast;

import com.app.jlmd.animatedcircleloadingview.sample.R;
import com.github.jlmd.animatedcircleloadingview.AnimatedCircleLoadingView;
import com.github.jlmd.animatedcircleloadingview.sample.ACTIVITY.APP_CONSTANT.AppConstant;
import com.pixplicity.easyprefs.library.Prefs;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class HP_PA_SPLASH extends Activity {


    private AnimatedCircleLoadingView animatedCircleLoadingView;
    private String current_date, current_time, day_of_week;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        //layout declaration
        hppa_dcl_layout_t3();

        //Check TTS Installed or not
        //updated by Aakash
        initTts();

        startLoading();

        startPercentMockThread();

        Calendar c = Calendar.getInstance();
        int timeOfDay = c.get(Calendar.HOUR_OF_DAY);


        if (timeOfDay >= 0 && timeOfDay < 12) {
            Prefs.putString(AppConstant.shared_wishing_time,"Good Morning" );
            Toast.makeText(this, "Good Morning", Toast.LENGTH_LONG).show();
        } else if (timeOfDay >= 12 && timeOfDay < 16) {
            Prefs.putString(AppConstant.shared_wishing_time,"Good Afternoon" );
            Toast.makeText(this, "Good Afternoon", Toast.LENGTH_LONG).show();
        } else if (timeOfDay >= 16 && timeOfDay < 24) {
            Prefs.putString(AppConstant.shared_wishing_time,"Good Evening" );
            Toast.makeText(this, "Good Evening", Toast.LENGTH_LONG).show();
        }

    }
//on create ends here

    @Override
    protected void onResume() {
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
                    Intent splshint = new Intent(HP_PA_SPLASH.this, HP_PA_SIGNIN.class);
                    startActivity(splshint);
                    finish();
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