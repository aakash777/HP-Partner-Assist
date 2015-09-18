package com.github.jlmd.animatedcircleloadingview.sample.ACTIVITY;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.app.jlmd.animatedcircleloadingview.sample.R;
import com.github.jlmd.animatedcircleloadingview.AnimatedCircleLoadingView;

public class HP_PA_SPLASH extends Activity {


    private AnimatedCircleLoadingView animatedCircleLoadingView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        //layout declaration
        hppa_dcl_layout_t3();

        startLoading();

        startPercentMockThread();

    }
//on create ends here

    @Override
    protected void onResume() {
        super.onResume();
        
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

                }finally {
                    try {
                        Thread.sleep(2000);
                    }catch (InterruptedException e) {
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
}