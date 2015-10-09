package com.github.jlmd.animatedcircleloadingview.sample.ACTIVITY;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.app.jlmd.animatedcircleloadingview.sample.R;
import com.github.jlmd.animatedcircleloadingview.sample.ACTIVITY.APP_UTILS.BYTECH_APP_CONSTANT;
import com.pixplicity.easyprefs.library.Prefs;

import java.util.ArrayList;
import java.util.Locale;

/**
 * Created by Mainak Karmakar on 05-10-2015.
 */
public class HP_PA_RESPONSEGRAPH extends Activity implements RecognitionListener , TextToSpeech.OnInitListener{

    Typeface typeFace;
    private SpeechRecognizer speech = null;
    private String LOG_TAG = "HP_PA_RESPONSEGRAPH";
    private Intent recognizerIntent;
    private TextToSpeech tts;
    TextView response_graphvw_txtvw , response_gridvw_txtvw , response_txtvw_txtvw
            , footer_powered_txt;
    EditText footer_response_txt;
    RelativeLayout response_header_gridvw_rl,response_header_txtvw_rl ,
            response_header_graphvw_rl;
    ImageView response_txt_imgvw,response_graph_imgvw,response_grid_imgvw,response_speak_btn;
    Animation animScale;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        tts = new TextToSpeech(this, this);
        //configure speech recognizer
        speech = SpeechRecognizer.createSpeechRecognizer(this);
        speech.setRecognitionListener(this);
        recognizerIntent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        recognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_PREFERENCE,
                "en");
        recognizerIntent.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE,
                this.getPackageName());
        recognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_WEB_SEARCH);
        recognizerIntent.putExtra(RecognizerIntent.EXTRA_MAX_RESULTS, 3);
        //layout declaration
        hppa_dcl_layout();
        //layout reference
        hppa_dcl_layout_variables();
        //setting the animation
        animScale = AnimationUtils.loadAnimation(this, R.anim.anim_scale);
        //widget fonts
        hppa_set_widget_fonts();
        response_header_graphvw_rl.setBackgroundResource(R.drawable.response_blue_brdr);
        response_graphvw_txtvw.setTextColor(getResources().getColor(R.color.app_white));
        response_graph_imgvw.setBackgroundResource(R.drawable.line_chart_white);

        response_header_txtvw_rl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent grid = new Intent(getApplicationContext(), HP_PA_RESPONSETEXT.class);
                startActivity(grid);
                finish();

            }
        });

        response_header_gridvw_rl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent graph = new Intent(getApplicationContext(), HP_PA_RESPONSEGRID.class);
                startActivity(graph);
                finish();

            }
        });
    }//oncreate ends here

    @Override
    public void onResume() {
        super.onResume();

       response_speak_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                view.startAnimation(animScale);

            }
        });

    }

    @Override
    protected void onPause() {
        super.onPause();

        if (speech != null) {
            speech.destroy();

            Log.i(LOG_TAG, "destroy");
        }

    }

    public void hppa_dcl_layout() {

        setContentView(R.layout.hp_pa_responsegraph);

    }
    public void hppa_dcl_layout_variables() {

        response_graphvw_txtvw = (TextView) findViewById(R.id.response_graphvw_txtvw);
        response_gridvw_txtvw = (TextView) findViewById(R.id.response_gridvw_txtvw);
        response_txtvw_txtvw = (TextView) findViewById(R.id.response_txtvw);
        footer_response_txt = (EditText) findViewById(R.id.response_command_txtvw);
        footer_powered_txt = (TextView) findViewById(R.id.footer_powered_txt);
        response_header_txtvw_rl = (RelativeLayout) findViewById(R.id.response_header_txtvw_rl);
        response_header_gridvw_rl = (RelativeLayout) findViewById(R.id.response_header_gridvw_rl);
        response_header_graphvw_rl = (RelativeLayout) findViewById(R.id.response_header_graphvw_rl);
        response_txt_imgvw = (ImageView) findViewById(R.id.response_txt_imgvw);
        response_graph_imgvw = (ImageView) findViewById(R.id.response_graph_imgvw);
        response_grid_imgvw = (ImageView) findViewById(R.id.response_grid_imgvw);
        response_speak_btn = (ImageView) findViewById(R.id.response_speak_btn);
    }

    public void hppa_set_widget_fonts() {

        typeFace = Typeface.createFromAsset(getAssets(),
                "fonts/OpenSans-Regular.ttf");
        response_graphvw_txtvw.setTypeface(typeFace);
        response_gridvw_txtvw.setTypeface(typeFace);
        response_txtvw_txtvw.setTypeface(typeFace);
        footer_response_txt.setTypeface(typeFace);

    }

    @Override
    public void onBeginningOfSpeech() {
        Log.i(LOG_TAG, "onBeginningOfSpeech");
    }

    @Override
    public void onBufferReceived(byte[] buffer) {
        Log.i(LOG_TAG, "onBufferReceived: " + buffer);
    }

    @Override
    public void onEndOfSpeech() {
        Log.i(LOG_TAG, "onEndOfSpeech");
    }

    @Override
    public void onError(int errorCode) {
        String errorMessage = getErrorText(errorCode);
        Log.d(LOG_TAG, "FAILED " + errorMessage);
    }

    @Override
    public void onEvent(int arg0, Bundle arg1) {
        Log.i(LOG_TAG, "onEvent");
    }

    @Override
    public void onPartialResults(Bundle arg0) {
        Log.i(LOG_TAG, "onPartialResults");
        System.out.println("Partial Result:" + arg0);
    }

    @Override
    public void onReadyForSpeech(Bundle arg0) {
        Log.i(LOG_TAG, "onReadyForSpeech");
    }

    @Override
    public void onResults(Bundle results) {
        Log.i(LOG_TAG, "onResults");
        ArrayList<String> matches = results
                .getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
        String text = "";
        for (String result : matches)
            text += result + "\n";

    }

    @Override
    public void onRmsChanged(float rmsdB) {
        Log.i(LOG_TAG, "onRmsChanged: " + rmsdB);

    }

    public static String getErrorText(int errorCode) {
        String message;
        switch (errorCode) {
            case SpeechRecognizer.ERROR_AUDIO:
                message = "Audio recording error";
                break;
            case SpeechRecognizer.ERROR_CLIENT:
                message = "Client side error";
                break;
            case SpeechRecognizer.ERROR_INSUFFICIENT_PERMISSIONS:
                message = "Insufficient permissions";
                break;
            case SpeechRecognizer.ERROR_NETWORK:
                message = "Network error";
                break;
            case SpeechRecognizer.ERROR_NETWORK_TIMEOUT:
                message = "Network timeout";
                break;
            case SpeechRecognizer.ERROR_NO_MATCH:
                message = "No match";
                break;
            case SpeechRecognizer.ERROR_RECOGNIZER_BUSY:
                message = "RecognitionService busy";
                break;
            case SpeechRecognizer.ERROR_SERVER:
                message = "error from server";
                break;
            case SpeechRecognizer.ERROR_SPEECH_TIMEOUT:
                message = "No speech input";
                break;
            default:
                message = "Didn't understand, please try again.";
                break;
        }
        return message;
    }

    @Override
    public void onInit(int status) {

        System.out.println("Enterd init Function");
        System.out.println("tts init status out" + status);
        if (status == TextToSpeech.SUCCESS) {

            int result = tts.setLanguage(Locale.ENGLISH);

            tts.setSpeechRate((float) 1.1);
            System.out.println("tts init status if" + status);
            tts.setPitch((float) 1.0); // set pitch level

            // tts.setSpeechRate(2); // set speech speed rate
            System.out.println("languge status out"+result);
            if (result == TextToSpeech.LANG_MISSING_DATA
                    || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                System.out.println("languge status if"+result);
                Log.e("TTS", "Language is not supported");
            } else {
                System.out.println("response flag value" + Prefs.getBoolean(BYTECH_APP_CONSTANT.shared_response_flag, false));
                if((Prefs.getBoolean(BYTECH_APP_CONSTANT.shared_response_flag, false)))
                {
            speakOut("342 LAPTOPS");
                    Prefs.putBoolean(BYTECH_APP_CONSTANT.shared_response_flag, false);
                }

                System.out.println("languge status else" + result);
            }

        } else {
            Log.e("TTS", "Initilization Failed");
            System.out.println("tts init status else" + status);
        }

    }

    private void speakOut(String text) {
        System.out.println("Entered Speakout");
        System.out.println("Speakout text"+text);
        tts.speak(text, TextToSpeech.QUEUE_FLUSH, null);
    }

}
