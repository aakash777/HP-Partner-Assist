package com.github.jlmd.animatedcircleloadingview.sample.ACTIVITY;


import android.app.Activity;
import android.content.Context;
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
import android.view.textservice.TextInfo;
import android.view.textservice.TextServicesManager;
import android.widget.ImageButton;
import android.widget.TextView;
import com.app.jlmd.animatedcircleloadingview.sample.R;
import com.github.jlmd.animatedcircleloadingview.AnimatedCircleLoadingView;
import com.jpardogo.android.googleprogressbar.library.GoogleProgressBar;
import java.util.ArrayList;
import java.util.Locale;
import android.view.textservice.SentenceSuggestionsInfo;
import android.view.textservice.SpellCheckerSession;
import android.view.textservice.SpellCheckerSession.SpellCheckerSessionListener;
import android.view.textservice.SuggestionsInfo;

/**
 * Created by Mainak Karmakar on 15/09/2015.
 */
public class HP_PA_HOME extends Activity implements RecognitionListener ,
        TextToSpeech.OnInitListener , SpellCheckerSession.SpellCheckerSessionListener
{

    ImageButton home_speech_imgbtn;
    TextView home_speech_txtvw,home_powered_txt,footer_marque_txt;
    Typeface typeFace;
    private SpeechRecognizer speech = null;
    private Intent recognizerIntent;
    private String LOG_TAG = "HP_PA_HOME";
    private AnimatedCircleLoadingView animatedCircleLoadingView;
    private TextToSpeech tts;
    String globaltext;
    Animation animScale;
    GoogleProgressBar mProgressBar;
    String tempresult;
    String spoken_user_words[];
    private SpellCheckerSession mScs;
    ArrayList<String> suggesion;
    TextServicesManager tsm;
    SpellCheckerSession session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        globaltext = "Welcome to Bytech India H.P Partner Assists";
        tts = new TextToSpeech(this, this);
        System.out.println("speech status onstart" + tts.isSpeaking());
        //layout declaration
        hppa_dcl_layout();
        //setting the animation
        animScale = AnimationUtils.loadAnimation(this,
                     R.anim.anim_scale);
        //layout reference
        hppa_dcl_layout_variables();
        //widget fonts
        hppa_set_widget_fonts();

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
        suggesion = new ArrayList<String>();
        fetchSuggestionsFor("liv");
        prevalidating_sentence(globaltext);

    }//oncreate ends here

    @Override
    public void onGetSuggestions(SuggestionsInfo[] results) {
        System.out.println("inside on get suggesion");
    }

    @Override
    public void onGetSentenceSuggestions(SentenceSuggestionsInfo[] results) {

       // final StringBuffer sb = new StringBuffer("");
        System.out.println("inside on get Sentence suggesion");

        for(SentenceSuggestionsInfo result:results){
            int n = result.getSuggestionsCount();
            for(int i=0; i < n; i++){
                int m = result.getSuggestionsInfoAt(i).getSuggestionsCount();

                for(int k=0; k < m; k++) {
//                    sb.append(result.getSuggestionsInfoAt(i).getSuggestionAt(k))
//                            .append("\n");
                    suggesion.add(result.getSuggestionsInfoAt(i).getSuggestionAt(k));

                    System.out.println("ongetsuggetions:" + result.getSuggestionsInfoAt(i).getSuggestionAt(k));

                }

            }
        }
        System.out.println("sugetion length"+suggesion.size());
    }

    private void fetchSuggestionsFor(String input){

        System.out.println("inside fetch suggesion");
    }

    public void hppa_dcl_layout() {

        setContentView(R.layout.hp_pa_home);

    }
    public void hppa_dcl_layout_variables() {

        home_speech_imgbtn  = (ImageButton) findViewById(R.id.home_speech_imgbtn);
        home_speech_txtvw = (TextView) findViewById(R.id.home_speech_txtvw);
        home_powered_txt = (TextView) findViewById(R.id.footer_powered_txt);
        mProgressBar = (GoogleProgressBar) findViewById(R.id.google_progress);
        footer_marque_txt = (TextView) findViewById(R.id.footer_marque_txt);

    }

    public void hppa_set_widget_fonts() {

        typeFace = Typeface.createFromAsset(getAssets(),
                "fonts/OpenSans-Regular.ttf");
        home_speech_txtvw.setTypeface(typeFace);
        home_powered_txt.setTypeface(typeFace);

    }

    @Override
    public void onResume() {
        super.onResume();

        System.out.println("speech status onresume" + tts.isSpeaking());

        home_speech_imgbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                view.startAnimation(animScale);
                speech.startListening(recognizerIntent);
                home_speech_imgbtn.setClickable(false);
                mProgressBar.setVisibility(View.VISIBLE);

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


    @Override
    public void onBeginningOfSpeech() {
        Log.i(LOG_TAG, "onBeginningOfSpeech");
        home_speech_txtvw.setText("Listening . ");
    }

    @Override
    public void onBufferReceived(byte[] buffer) {
        Log.i(LOG_TAG, "onBufferReceived: " + buffer);
        home_speech_txtvw.setText("Listening . .");

    }

    @Override
    public void onEndOfSpeech() {
        Log.i(LOG_TAG, "onEndOfSpeech");
        //  progressBar.setIndeterminate(true);
        //   toggleButton.setChecked(false);
        home_speech_txtvw.setText("Listening . . .");
    }

    @Override
    public void onError(int errorCode) {
        String errorMessage = getErrorText(errorCode);
        Log.d(LOG_TAG, "FAILED " + errorMessage);
        home_speech_txtvw.setText(errorMessage);
        speakOut(errorMessage + " please tab and speak again");
        //toggleButton.setChecked(false);
        home_speech_imgbtn.setClickable(true);
        mProgressBar.setVisibility(View.GONE);

    }

    @Override
    public void onEvent(int arg0, Bundle arg1) {
        Log.i(LOG_TAG, "onEvent");
        home_speech_txtvw.setText("Listen");

    }

    @Override
    public void onPartialResults(Bundle arg0) {
        Log.i(LOG_TAG, "onPartialResults");
        System.out.println();
        home_speech_txtvw.setText("Listening . . . .");
    }

    @Override
    public void onReadyForSpeech(Bundle arg0) {
        Log.i(LOG_TAG, "onReadyForSpeech");
        home_speech_txtvw.setText("Listening");
    }

    @Override
    public void onResults(Bundle results) {
        Log.i(LOG_TAG, "onResults");
        ArrayList<String> matches = results
                .getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
        String text = "";
        for (String result : matches)
            text += result + "\n";
        tempresult = matches.get(0);
        home_speech_txtvw.setText(matches.get(0));

   //     prevalidating_sentence(globaltext);
        footer_marque_txt.setText(tempresult);
        speakOut("Do you mean " + "\t" + tempresult+ "Please say go or proceed to process your question");
        home_speech_imgbtn.setClickable(true);
        mProgressBar.setVisibility(View.GONE);
    }

    @Override
    public void onRmsChanged(float rmsdB) {
        Log.i(LOG_TAG, "onRmsChanged: " + rmsdB);
        //   progressBar.setProgress((int) rmsdB);
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
        System.out.println("tts init status out"+status);
        if (status == TextToSpeech.SUCCESS) {

            int result = tts.setLanguage(Locale.UK);


            tts.setSpeechRate((float) 1.5);
            System.out.println("tts init status if" + status);
            tts.setPitch((float) -1); // set pitch level

            // tts.setSpeechRate(2); // set speech speed rate
            System.out.println("languge status out"+result);
            if (result == TextToSpeech.LANG_MISSING_DATA
                    || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                System.out.println("languge status if"+result);
                Log.e("TTS", "Language is not supported");
            } else {
                speakOut(globaltext);
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

    private void prevalidating_sentence(String sentence)
    {
        spoken_user_words = sentence.trim().split("\\s+");

        System.out.println("word length "+spoken_user_words.length);
        for(int i =0; i<spoken_user_words.length; i++)
        {
            System.out.println("word at position "+i+" is "+spoken_user_words[i]);
            tsm =(TextServicesManager) getSystemService(TEXT_SERVICES_MANAGER_SERVICE);

            session = tsm.newSpellCheckerSession(null, Locale.ENGLISH, this, true);
            session.getSentenceSuggestions(
                    new TextInfo[]{new TextInfo(spoken_user_words[i])},
                    8
            );
//            fetchSuggestionsFor("Peter");
            System.out.println("my length" + suggesion.size());
//            System.out.println("suggetion 1 "+suggesion.get(0));
//            System.out.println("suggetion 2 "+suggesion.get(2));
//            System.out.println("suggetion 3 "+suggesion.get(3));
//            System.out.println("suggetion 4 "+suggesion.get(4));
        }

    }
}
