package com.github.jlmd.animatedcircleloadingview.sample.ACTIVITY;


import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.media.AudioManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.SystemClock;
import android.provider.Settings;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.speech.tts.TextToSpeech;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.RotateAnimation;
import android.widget.AnalogClock;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.app.jlmd.animatedcircleloadingview.sample.R;
import com.github.jlmd.animatedcircleloadingview.sample.ACTIVITY.APP_UTILS.BYTECH_APP_CONSTANT;
import com.github.jlmd.animatedcircleloadingview.sample.ACTIVITY.APP_UTILS.ISPEECH_SUPPORT;
import com.github.jlmd.animatedcircleloadingview.sample.ACTIVITY.MAIN_APPLICATION.BYTECH_CONNECTION_DETECTOR;
import com.github.rahatarmanahmed.cpv.CircularProgressView;
import com.jpardogo.android.googleprogressbar.library.GoogleProgressBar;
import com.pixplicity.easyprefs.library.Prefs;
//import com.acapelagroup.android.tts.acattsandroid;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.ispeech.SpeechSynthesis;
import org.ispeech.SpeechSynthesisEvent;
import org.ispeech.error.BusyException;
import org.ispeech.error.InvalidApiKeyException;
import org.ispeech.error.NoNetworkException;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.Locale;

/**
 * Created by Mainak Karmakar on 15/09/2015.
 */
public class HP_PA_HOME extends Activity implements RecognitionListener
{

    ImageButton home_speech_imgbtn;
    TextView home_speech_txtvw,home_powered_txt,footer_marque_txt,timer_txtvw,continue_txtvw,back_txtvw;
    ImageView continue_btn_imgvw,back_btn_imgvw;
    //    ImageView timer_speak_btn;
    Typeface typeFace;
    private SpeechRecognizer speech;
    private Intent recognizerIntent;
    private String LOG_TAG = "HP_PA_HOME";
    //  private AnimatedCircleLoadingView animatedCircleLoadingView;
    private TextToSpeech tts;
    String globaltext;
    Animation animScale,animalpha;
    GoogleProgressBar mProgressBar,tprogressBar;
    String tempresult;
    String spoken_user_words[];
    int entityflag;
    int wh_qstn_flg;
    int non_english_flag;
    String pre_validated_text,pre_validated_text_replaceSpace,date_replace;
    final Context context = this;
    private final long startTime = 30 * 1000;
    private final long interval = 1 * 1000;
    private CountDownTimer countDownTimer;
    private boolean timerHasStarted = false;
    CircularProgressView progressView;
    private BYTECH_CONNECTION_DETECTOR cd;
    private Boolean isInternetPresent = false;
    String URL = "http://bytechdemo.com/spa/api/secap?text=";
    String serviceURL = "http://www.bytechdemo.com/spa1/Service1.svc/getanswersecap?";
    String product,event,date;
    String result_count;
    Dialog dialog;
    JSONObject businessObject = null;
    String result_obj ="";
    private static final String TAG = "iSpeech SDK Sample";
    SpeechSynthesis synthesis;
    Context _context;
    private ISPEECH_SUPPORT isp_sprt;

//  int speech_listner_flag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
//        prepareTTSEngine();
//        synthesis.setStreamType(AudioManager.STREAM_MUSIC);
        super.onCreate(savedInstanceState);
        isp_sprt = new ISPEECH_SUPPORT(getApplicationContext());
        prepareTTSEngine();
        synthesis.setStreamType(AudioManager.STREAM_MUSIC);

        pre_validated_text = "";
        if(Prefs.getInt(BYTECH_APP_CONSTANT.shared_home_speak_flag,0)==1) {
            globaltext = Prefs.getString(BYTECH_APP_CONSTANT.shared_wishing_time, "") + "\n" +
                    Prefs.getString(BYTECH_APP_CONSTANT.shared_partner_name, "") + "\n" +
                    "welcome to By_tech india " + "\n" + "please tab and ask your question";

            isp_sprt.go_speek(globaltext);
//            try {
//                synthesis.speak(globaltext);
//            } catch (BusyException e) {
//                Log.e(TAG, "SDK is busy");
//                e.printStackTrace();
//            } catch (NoNetworkException e) {
//                Log.e(TAG, "Network is not available\n" + e.getStackTrace());
//                Toast.makeText(_context, "ERROR: Network is not available", Toast.LENGTH_LONG).show();
//            }
 //           tts = new TextToSpeech(this, this);
        }else{
            globaltext = "please tab and ask your question";
            try {
                synthesis.speak(globaltext);
            } catch (BusyException e) {
                Log.e(TAG, "SDK is busy");
                e.printStackTrace();
            } catch (NoNetworkException e) {
                Log.e(TAG, "Network is not available\n" + e.getStackTrace());
                Toast.makeText(_context, "ERROR: Network is not available", Toast.LENGTH_LONG).show();
            }
           // tts = new TextToSpeech(this, this);
        }
//        System.out.println("speech status onstart" + tts.isSpeaking());
        //layout declaration
        hppa_dcl_layout();

        //setting the animation
        animScale = AnimationUtils.loadAnimation(this,R.anim.anim_scale);
        //layout reference
        hppa_dcl_layout_variables();
        //widget fonts
        hppa_set_widget_fonts();
        // Make Conncetion Class Object
        cd = new BYTECH_CONNECTION_DETECTOR(getApplicationContext());
        System.out.println("partner_id" + Prefs.getString(BYTECH_APP_CONSTANT.shared_partner_id,""));
        //configure speech recognizer
        speech = SpeechRecognizer.createSpeechRecognizer(this);
        speech.setRecognitionListener(this);
        recognizerIntent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        recognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_PREFERENCE,
                "en");
        recognizerIntent.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE,
                this.getPackageName());
        recognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        recognizerIntent.putExtra(RecognizerIntent.EXTRA_MAX_RESULTS, 3);
        recognizerIntent.putExtra(RecognizerIntent.EXTRA_PARTIAL_RESULTS, true);

    }//oncreate ends here

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

                    AlertDialog.Builder builder = new AlertDialog.Builder(HP_PA_HOME.this);
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

        //       System.out.println("speech status onresume" + tts.isSpeaking());
        home_speech_imgbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                view.startAnimation(animScale);
                speech.startListening(recognizerIntent);
                home_speech_imgbtn.setClickable(false);
                mProgressBar.setVisibility(View.VISIBLE);

            }
        });

        home_powered_txt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//                //check the internet connection
//                isInternetPresent = cd.isConnectingToInternet();
//                // check for Internet status
//                if (isInternetPresent) {
//                    pre_validated_text = "How many Laptop purchase Yesterday";
//                    pre_validated_text_replaceSpace = pre_validated_text.replaceAll(" ", "%20");
//                    System.out.println("pre executed url"+URL+pre_validated_text_replaceSpace);
//                    new GetParameterFromAPIAI().execute(URL + pre_validated_text_replaceSpace);
//                } else {
//
//                    // Internet connection is not present
//                    // Ask user to connect to Internet
//                    speakOut("please check your internet connection" + "\n" + "then try to proceed");
//
//                }

            }
        });
    }
    @Override
    public void onBackPressed() {
        synthesis.stop();;
        return;
    }
    public class MyCountDownTimer extends CountDownTimer {

        public MyCountDownTimer(long startTime, long interval) {
            super(startTime, interval);
        }

        @Override
        public void onFinish() {

            try {
                synthesis.speak("Dear " + "\n" + Prefs.getString(BYTECH_APP_CONSTANT.shared_partner_name, "")
                        + "\n" + " your query is taking longer then the usual." + "\n" + "If you Still Wish " +
                        "to wait please say continue " + "\n" + "otherwise say Cancel to Try with new Question.");
            } catch (BusyException e) {
                Log.e(TAG, "SDK is busy");
                e.printStackTrace();
            } catch (NoNetworkException e) {
                Log.e(TAG, "Network is not available\n" + e.getStackTrace());
                Toast.makeText(_context, "ERROR: Network is not available", Toast.LENGTH_LONG).show();
            }
//            speakOut("Dear " + "\n" + Prefs.getString(BYTECH_APP_CONSTANT.shared_partner_name, "")
//                    + "\n" + " your query is taking longer then the usual." + "\n" + "If you Still Wish " +
//                    "to wait please say continue " + "\n" + "otherwise say Cancel to Try with new Question.");
            continue_btn_imgvw.setVisibility(View.VISIBLE);
            back_btn_imgvw.setVisibility(View.VISIBLE);
            continue_txtvw.setVisibility(View.VISIBLE);
            back_txtvw.setVisibility(View.VISIBLE);
            back_txtvw.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    dialog.dismiss();

                }
            });
//            timer_txtvw.setText("Time's up!");
            progressView.setVisibility(View.GONE);

        }

        @Override
        public void onTick(long millisUntilFinished) {
            timer_txtvw.setText("" + millisUntilFinished / 1000);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();

        if (speech != null) {
            speech.destroy();
            Log.i(LOG_TAG, "destroy");
        }
        synthesis.stop();	//Optional to stop the playback when the activity is paused
    }

    @Override
    protected void onStop()
    {
        super.onStop();
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

        home_speech_txtvw.setText("please wait");
    }

    @Override
    public void onError(int errorCode) {
        String errorMessage = getErrorText(errorCode);
        Log.d(LOG_TAG, "FAILED " + errorMessage);

        home_speech_txtvw.setText(errorMessage);
        try {
            synthesis.speak(errorMessage + " please tab and speak again");
        } catch (BusyException e) {
            Log.e(TAG, "SDK is busy");
            e.printStackTrace();
        } catch (NoNetworkException e) {
            Log.e(TAG, "Network is not available\n" + e.getStackTrace());
            Toast.makeText(_context, "ERROR: Network is not available", Toast.LENGTH_LONG).show();
        }
//        speakOut(errorMessage + " please tab and speak again");
//        pre_validated_text="";
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

        home_speech_txtvw.setText("tab to speak");
        home_speech_imgbtn.setClickable(true);
        mProgressBar.setVisibility(View.GONE);
        prevalidating_sentence(tempresult);

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
                //Network error
                message = "Sorry Network error";
                break;
            case SpeechRecognizer.ERROR_NETWORK_TIMEOUT:
                message = "Network timeout";
                break;
            case SpeechRecognizer.ERROR_NO_MATCH:
                //no match
                message = "Sorry we didn't get you";
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

//    @Override
//    public void onInit(int status) {
//
//        System.out.println("Enterd init Function");
//        System.out.println("tts init status out"+status);
//        if (status == TextToSpeech.SUCCESS) {
//
//            int result = tts.setLanguage(Locale.ENGLISH);
//            tts.setSpeechRate((float) 1.3);
//            System.out.println("tts init status if" + status);
//            tts.setPitch((float) 1.225); // set pitch level
//
//            // tts.setSpeechRate(2); // set speech speed rate
//            System.out.println("languge status out"+result);
//            if (result == TextToSpeech.LANG_MISSING_DATA
//                    || result == TextToSpeech.LANG_NOT_SUPPORTED) {
//                System.out.println("languge status if"+result);
//                Log.e("TTS", "Language is not supported");
//            } else {
//                speakOut(globaltext);
//                System.out.println("languge status else" + result);
//            }
//
//        } else {
//            Log.e("TTS", "Initilization Failed");
//            System.out.println("tts init status else" + status);
//        }
//
//    }
//
//    private void speakOut(String text) {
//        System.out.println("Entered Speakout");
//        System.out.println("Speakout text"+text);
//        tts.speak(text, TextToSpeech.QUEUE_FLUSH, null);
//    }

    private void prevalidating_sentence(String sentence)
    {

        entityflag = 0;
        wh_qstn_flg = 0;
        System.out.println("spoken word"+sentence);
        Toast.makeText(getApplicationContext(),
                "you spoke :- "+sentence ,Toast.LENGTH_SHORT).show();
        if((sentence.equalsIgnoreCase("proceed"))||(sentence.equalsIgnoreCase("proceeds"))
                ||(sentence.equalsIgnoreCase("go")))
        {
            System.out.println("validated text"+pre_validated_text);
            if(!pre_validated_text.equals(""))
            {
//                speakOut("please wait!! while we process your question");
                try {
                    synthesis.speak("please wait!! while we process your question");
                } catch (BusyException e) {
                    Log.e(TAG, "SDK is busy");
                    e.printStackTrace();
                } catch (NoNetworkException e) {
                    Log.e(TAG, "Network is not available\n" + e.getStackTrace());
                    Toast.makeText(_context, "ERROR: Network is not available", Toast.LENGTH_LONG).show();
                }
                footer_marque_txt.setText("");
                //check the internet connection
                isInternetPresent = cd.isConnectingToInternet();
                // check for Internet status
                if (isInternetPresent) {
 //                   pre_validated_text = "How many Laptop purchase Yesterday";
                    pre_validated_text_replaceSpace = pre_validated_text.replaceAll(" ", "%20");
                    System.out.println("pre executed url"+URL+pre_validated_text_replaceSpace);
                    new GetParameterFromAPIAI().execute(URL + pre_validated_text_replaceSpace);
                } else {
                    // Internet connection is not present
                    // Ask user to connect to Internet
//                    speakOut("please check your internet connection" + "\n" + "then try to proceed");
                    try {
                        synthesis.speak("please wait! while we process your question");
                    } catch (BusyException e) {
                        Log.e(TAG, "SDK is busy");
                        e.printStackTrace();
                    } catch (NoNetworkException e) {
                        Log.e(TAG, "Network is not available\n" + e.getStackTrace());
                        Toast.makeText(_context, "ERROR: Network is not available", Toast.LENGTH_LONG).show();
                    }
                }


            }else
            {
//                speakOut("Sorry!! we do not have any question to move forward"+
//                        "\n"+"Please Tab and ask your question");
                try {
                    synthesis.speak("Sorry!! we do not have any question to move forward"+
                            "\n"+"Please Tab and ask your question");
                } catch (BusyException e) {
                    Log.e(TAG, "SDK is busy");
                    e.printStackTrace();
                } catch (NoNetworkException e) {
                    Log.e(TAG, "Network is not available\n" + e.getStackTrace());
                    Toast.makeText(_context, "ERROR: Network is not available", Toast.LENGTH_LONG).show();
                }

            }

        }else  if((sentence.equalsIgnoreCase("cancel"))||(sentence.equalsIgnoreCase("cancels"))
                ||(sentence.equalsIgnoreCase("cance")))
        {
            if(!pre_validated_text.equals(""))
            {
//                speakOut("your previous question has been cancelled"+
//                        "\n"+"Please Tab and ask your question");

                try {
                    synthesis.speak("your previous question has been cancelled"+
                            "\n"+"Please Tab and ask your question");
                } catch (BusyException e) {
                    Log.e(TAG, "SDK is busy");
                    e.printStackTrace();
                } catch (NoNetworkException e) {
                    Log.e(TAG, "Network is not available\n" + e.getStackTrace());
                    Toast.makeText(_context, "ERROR: Network is not available", Toast.LENGTH_LONG).show();
                }

                pre_validated_text = "";
                footer_marque_txt.setText("");

            } else {
//                speakOut("there is no previous question to cancel"+
//                        "\n"+"Please Tab and ask your question");
                try {
                    synthesis.speak("your previous question has been cancelled"+
                            "\n"+"Please Tab and ask your question");
                } catch (BusyException e) {
                    Log.e(TAG, "SDK is busy");
                    e.printStackTrace();
                } catch (NoNetworkException e) {
                    Log.e(TAG, "Network is not available\n" + e.getStackTrace());
                    Toast.makeText(_context, "ERROR: Network is not available", Toast.LENGTH_LONG).show();
                }
            }
        }
        else {
            spoken_user_words = sentence.trim().split("\\s+");
            if (spoken_user_words.length <= 10) {
                System.out.println("word length " + spoken_user_words.length);
                for (int i = 0; i < spoken_user_words.length; i++) {
                    System.out.println("word at position " + i + " is " + spoken_user_words[i]);
                    if (spoken_user_words[i].length() > 45) {
//                        speakOut("Sorry !! We found that your Question has one or more invalid " +
//                                "word!!" + "\n" + "Please tab and Try again");
                        try {
                            synthesis.speak("Sorry !! We found that your Question has one or more invalid " +
                                    "word!!" + "\n" + "Please tab and Try again");
                        } catch (BusyException e) {
                            Log.e(TAG, "SDK is busy");
                            e.printStackTrace();
                        } catch (NoNetworkException e) {
                            Log.e(TAG, "Network is not available\n" + e.getStackTrace());
                            Toast.makeText(_context, "ERROR: Network is not available", Toast.LENGTH_LONG).show();
                        }
                        //  footer_marque_txt.setText("Please tab and speak again");

                        break;
                    } else {
                        if ((spoken_user_words[i].equalsIgnoreCase("Sale")) ||
                                (spoken_user_words[i].equalsIgnoreCase("Sales")) ||
                                (spoken_user_words[i].equalsIgnoreCase("Purchase")) ||
                                (spoken_user_words[i].equalsIgnoreCase("Purchases")) ||
                                (spoken_user_words[i].equalsIgnoreCase("Inventory")) ||
                                (spoken_user_words[i].equalsIgnoreCase("Inventories")) ||
                                (spoken_user_words[i].equalsIgnoreCase("Stock")) ||
                                (spoken_user_words[i].equalsIgnoreCase("Sold")) ||
                                (spoken_user_words[i].equalsIgnoreCase("Stocks"))) {
                            entityflag = entityflag + 1;
                        }
                        if ((spoken_user_words[i].equalsIgnoreCase("what")) ||
                                (spoken_user_words[i].equalsIgnoreCase("how")) ||
                                (spoken_user_words[i].equalsIgnoreCase("where")) ||
                                (spoken_user_words[i].equalsIgnoreCase("who")) ||
                                (spoken_user_words[i].equalsIgnoreCase("when")) ||
                                (spoken_user_words[i].equalsIgnoreCase("which"))) {
                            wh_qstn_flg = wh_qstn_flg + 1;
                        }
                    }

                }
                System.out.println("wh question flag" + wh_qstn_flg);
                System.out.println("Entity flag" + entityflag);
                if (wh_qstn_flg == 1) {
                    if (entityflag == 1) {
                        non_english_flag = 0;
                        for (int i = 0; i < spoken_user_words.length; i++) {

                            if ((spoken_user_words[i].equalsIgnoreCase("aap"))
                                    || (spoken_user_words[i].equalsIgnoreCase("mein"))
                                    || (spoken_user_words[i].equalsIgnoreCase("tum"))
                                    || (spoken_user_words[i].equalsIgnoreCase("kya"))
                                    || (spoken_user_words[i].equalsIgnoreCase("hai"))
                                    || (spoken_user_words[i].equalsIgnoreCase("aaj"))
                                    || (spoken_user_words[i].equalsIgnoreCase("kal"))
                                    || (spoken_user_words[i].equalsIgnoreCase("tera"))
                                    || (spoken_user_words[i].equalsIgnoreCase("tu"))
                                    || (spoken_user_words[i].equalsIgnoreCase("kaha"))
                                    || (spoken_user_words[i].equalsIgnoreCase("ho"))
                                    || (spoken_user_words[i].equalsIgnoreCase("mera"))
                                    || (spoken_user_words[i].equalsIgnoreCase("apne"))
                                    || (spoken_user_words[i].equalsIgnoreCase("kuch"))
                                    || (spoken_user_words[i].equalsIgnoreCase("hotā"))
                                    || (spoken_user_words[i].equalsIgnoreCase("se"))
                                    || (spoken_user_words[i].equalsIgnoreCase("bahut"))
                                    || (spoken_user_words[i].equalsIgnoreCase("hone"))
                                    || (spoken_user_words[i].equalsIgnoreCase("din"))
                                    || (spoken_user_words[i].equalsIgnoreCase("iske"))
                                    || (spoken_user_words[i].equalsIgnoreCase("liye"))
                                    || (spoken_user_words[i].equalsIgnoreCase("kyu"))
                                    || (spoken_user_words[i].equalsIgnoreCase("kaun"))
                                    || (spoken_user_words[i].equalsIgnoreCase("lage"))
                                    || (spoken_user_words[i].equalsIgnoreCase("Ki"))
                                    || (spoken_user_words[i].equalsIgnoreCase("Aur"))
                                    || (spoken_user_words[i].equalsIgnoreCase("ek"))
                                    || (spoken_user_words[i].equalsIgnoreCase("Hain"))
                                    || (spoken_user_words[i].equalsIgnoreCase("Yah"))
                                    || (spoken_user_words[i].equalsIgnoreCase("Tha"))
                                    || (spoken_user_words[i].equalsIgnoreCase("uske"))
                                    || (spoken_user_words[i].equalsIgnoreCase("uska"))
                                    || (spoken_user_words[i].equalsIgnoreCase("Khana"))
                                    || (spoken_user_words[i].equalsIgnoreCase("Naam"))
                                    || (spoken_user_words[i].equalsIgnoreCase("Sabdh"))
                                    || (spoken_user_words[i].equalsIgnoreCase("Nahi"))
                                    || (spoken_user_words[i].equalsIgnoreCase("Sab"))
                                    || (spoken_user_words[i].equalsIgnoreCase("kiska"))
                                    || (spoken_user_words[i].equalsIgnoreCase("din"))
                                    )
                                non_english_flag = 1;

                        }
                        if (non_english_flag == 0) {
                            pre_validated_text = tempresult;
                            footer_marque_txt.setText(tempresult);
//                            speakOut("Do you mean " + "\t" + tempresult + "\n" +
//                                    "Please say go or proceed to process your question"
//                                    + "\n" + "cancel to ask another");
                            try {
                                synthesis.speak("Do you mean " + "\t" + tempresult + "\n" +
                                        "Please say go or proceed to process your question"
                                        + "\n" + "cancel to ask another");
                            } catch (BusyException e) {
                                Log.e(TAG, "SDK is busy");
                                e.printStackTrace();
                            } catch (NoNetworkException e) {
                                Log.e(TAG, "Network is not available\n" + e.getStackTrace());
                                Toast.makeText(_context, "ERROR: Network is not available", Toast.LENGTH_LONG).show();
                            }


                        } else {
//                            speakOut("it seams you are not speaking english " + "\n" +
//                                    "Please tab and ask a valid question");
                            try {
                                synthesis.speak("it seams you are not speaking english " + "\n" +
                                        "Please tab and ask a valid question");
                            } catch (BusyException e) {
                                Log.e(TAG, "SDK is busy");
                                e.printStackTrace();
                            } catch (NoNetworkException e) {
                                Log.e(TAG, "Network is not available\n" + e.getStackTrace());
                                Toast.makeText(_context, "ERROR: Network is not available", Toast.LENGTH_LONG).show();
                            }
                            //   footer_marque_txt.setText("Please tab and speak again");

                        }

                    } else if (entityflag >= 1) {

//                        speakOut("Sorry !! there cannot be more than one Entity " +
//                                "in your Question" + "\n" + "Please tab and speak a valid question");
                        try {
                            synthesis.speak("Sorry !! there cannot be more than one Entity " +
                                    "in your Question" + "\n" + "Please tab and speak a valid question");
                        } catch (BusyException e) {
                            Log.e(TAG, "SDK is busy");
                            e.printStackTrace();
                        } catch (NoNetworkException e) {
                            Log.e(TAG, "Network is not available\n" + e.getStackTrace());
                            Toast.makeText(_context, "ERROR: Network is not available", Toast.LENGTH_LONG).show();
                        }
                        //   footer_marque_txt.setText("Please tab and speak again");

                    } else if (entityflag >= 0) {
//                        speakOut("Sorry !! We found no Entity in your Question" + "\n" +
//                                "Please tab and speak a valid question");
                        try {
                            synthesis.speak("Sorry !! We found no Entity in your Question" + "\n" +
                                    "Please tab and speak a valid question");
                        } catch (BusyException e) {
                            Log.e(TAG, "SDK is busy");
                            e.printStackTrace();
                        } catch (NoNetworkException e) {
                            Log.e(TAG, "Network is not available\n" + e.getStackTrace());
                            Toast.makeText(_context, "ERROR: Network is not available", Toast.LENGTH_LONG).show();
                        }

                        //    footer_marque_txt.setText("Please tab and speak again");

                    }
                } else if (wh_qstn_flg > 1) {
//                    speakOut("Sorry !! there cannot be more than one W.H Question" + "\n" +
//                            "Please tab and speak a valid question");
                    try {
                        synthesis.speak("Sorry !! there cannot be more than one W.H Question" + "\n" +
                                "Please tab and speak a valid question");
                    } catch (BusyException e) {
                        Log.e(TAG, "SDK is busy");
                        e.printStackTrace();
                    } catch (NoNetworkException e) {
                        Log.e(TAG, "Network is not available\n" + e.getStackTrace());
                        Toast.makeText(_context, "ERROR: Network is not available", Toast.LENGTH_LONG).show();
                    }
                    //footer_marque_txt.setText("Please tab and speak again");

                } else if (wh_qstn_flg == 0) {
//                    speakOut("Sorry !! We found no W.H Question" + "\n" +
//                            "Please tab and speak a valid question");
                    try {
                        synthesis.speak("Sorry !! We found no W.H Question" + "\n" +
                                "Please tab and speak a valid question");
                    } catch (BusyException e) {
                        Log.e(TAG, "SDK is busy");
                        e.printStackTrace();
                    } catch (NoNetworkException e) {
                        Log.e(TAG, "Network is not available\n" + e.getStackTrace());
                        Toast.makeText(_context, "ERROR: Network is not available", Toast.LENGTH_LONG).show();
                    }

                    //  footer_marque_txt.setText("Please tab and speak again");

                }
            } else {
//                speakOut("Sorry !! We found that your Question exceeds the maximum Length!!" + "\n" +
//                        "Please tab and Try again with a new Question");
                try {
                    synthesis.speak("Sorry !! We found that your Question exceeds the maximum Length!!" + "\n" +
                            "Please tab and Try again with a new Question");
                } catch (BusyException e) {
                    Log.e(TAG, "SDK is busy");
                    e.printStackTrace();
                } catch (NoNetworkException e) {
                    Log.e(TAG, "Network is not available\n" + e.getStackTrace());
                    Toast.makeText(_context, "ERROR: Network is not available", Toast.LENGTH_LONG).show();
                }

                //    footer_marque_txt.setText("Please tab and speak again");

            }
        }
    }

    private class GetParameterFromAPIAI extends AsyncTask<String, Void, String> {

        protected void onPreExecute() {
            super.onPreExecute();
            //call the timer dailog
            dialog = new Dialog(context, android.R.style.Theme_Translucent_NoTitleBar_Fullscreen);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setContentView(R.layout.timer_page);
            Window window = dialog.getWindow();
            WindowManager.LayoutParams wlp = window.getAttributes();
            wlp.gravity = Gravity.CENTER;
            wlp.flags &= ~WindowManager.LayoutParams.FLAG_BLUR_BEHIND;
            window.setAttributes(wlp);
            dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            dialog.show();
            timer_txtvw = (TextView) dialog.findViewById(R.id.countdown_timer);
//             timer_speak_btn = (ImageView) dialog.findViewById(R.id.timer_speak_btn);
            progressView = (CircularProgressView) dialog.findViewById(R.id.progress_view);
//             tprogressBar = (GoogleProgressBar) dialog.findViewById(R.id.timer_google_progress);
            continue_btn_imgvw = (ImageView) dialog.findViewById(R.id.continue_btn_imgvw);
            back_btn_imgvw = (ImageView) dialog.findViewById(R.id.back_btn_imgvw);
            continue_txtvw = (TextView) dialog.findViewById(R.id.continue_txtvw);
            back_txtvw = (TextView) dialog.findViewById(R.id.back_txtvw);

            countDownTimer = new MyCountDownTimer(startTime, interval);
            timer_txtvw.setText(timer_txtvw.getText() + String.valueOf(startTime / 1000));
            if (!timerHasStarted) {
                countDownTimer.start();
                progressView.startAnimation();
                timerHasStarted = true;

            } else {
                countDownTimer.cancel();
                progressView.clearAnimation();
                timerHasStarted = false;
            }
        }

        @Override
        protected String doInBackground(String... URL) {
            HttpClient Client = new DefaultHttpClient();
            System.out.println("my json" + URL);
            try {
                String SetServerString = "";
                HttpGet httpget = new HttpGet(URL[0]);
                System.out.println("my json url" + URL[0]);
                ResponseHandler<String> responseHandler = new BasicResponseHandler();
                SetServerString = Client.execute(httpget, responseHandler);

                return SetServerString;

            } catch (Exception e) {
            }
            return null;
        }

        protected void onPostExecute(String getResult) {

            super.onPostExecute(getResult);
//            dialog.dismiss();
            System.out.println("inside onpost");

            JSONObject jObject = null;
            try {
                jObject = new JSONObject(getResult);
                System.out.println("json object"+jObject);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            JSONObject offerObject = null;
            try {
                offerObject = jObject.getJSONObject("result");
                System.out.println("json result"+offerObject);
            } catch (JSONException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

//            try {
//                result_obj = jObject.getJSONObject("action").toString();
//                System.out.println("result object" + result_obj);
//                if (jObject.getJSONObject("action").equals("")) {
//
//                    speakOut("Sorry we are not able to give you instant answer for this one" + "\n" + "WE Will get back to you soon");
//                } else {
            try {
                businessObject = offerObject.getJSONObject("parameters");
                System.out.println("parameter :" + businessObject);
            } catch (JSONException e) {

                e.printStackTrace();
            }
        if((businessObject!=null)||(businessObject!=null)) {
            try {
                product = businessObject.getString("Product");
                System.out.println(product);
                Prefs.putString(BYTECH_APP_CONSTANT.shared_product_name,product);
                event = businessObject.getString("Event");
                System.out.println(event);
                date = businessObject.getString("date");
                System.out.println(date);

            } catch (JSONException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            if (!product.equals("")) {
//                    serviceURL =serviceURL+"partnerid="+"&"+Prefs.getString(BYTECH_APP_CONSTANT.shared_partner_id,"");
                Prefs.putString(BYTECH_APP_CONSTANT.shared_action_type, event);
                serviceURL = serviceURL + "partnerid="+Prefs.getString(BYTECH_APP_CONSTANT.shared_partner_id,"");
                date_replace = date.replaceAll("-", "");
                serviceURL = serviceURL + "&startdate=" + date_replace + "&enddate=" + date_replace;
                serviceURL = serviceURL + "&Product=KV" + "&Sales=" + event + "&Serialnumber=&State=&Region=";

                System.out.println("service url" + serviceURL);
                new GetDataFromServices().execute(serviceURL);


            }
//

//                }
//
//            } catch (JSONException e) {
//                // TODO Auto-generated catch block
//                e.printStackTrace();
//            }
        }
        else{
            System.out.println("parameter else");
//            speakOut("Your question did not match our query we will get back to you soon");
            try {
                synthesis.speak("Your question did not match our query we will get back to you soon");
            } catch (BusyException e) {
                Log.e(TAG, "SDK is busy");
                e.printStackTrace();
            } catch (NoNetworkException e) {
                Log.e(TAG, "Network is not available\n" + e.getStackTrace());
                Toast.makeText(_context, "ERROR: Network is not available", Toast.LENGTH_LONG).show();
            }
            dialog.dismiss();
            countDownTimer.cancel();
        }
        }

    }
    private class GetDataFromServices extends AsyncTask<String, Void, String> {

        protected void onPreExecute() {
            super.onPreExecute();
            //call the timer dailog
        }

        @Override
        protected String doInBackground(String... URL) {
            HttpClient Client = new DefaultHttpClient();
            System.out.println("service json" + URL);
            try {
                String SetServerString = "";
                HttpGet httpget = new HttpGet(URL[0]);
                System.out.println("service json url" + URL[0]);
                ResponseHandler<String> responseHandler = new BasicResponseHandler();
                SetServerString = Client.execute(httpget, responseHandler);

                return SetServerString;

            } catch (Exception e) {
            }
            return null;
        }

        protected void onPostExecute(String getResult) {

            super.onPostExecute(getResult);
            dialog.dismiss();
            System.out.println("inside service onpost");
//            speakOut("      ");
            try {
                synthesis.speak("     ");
            } catch (BusyException e) {
                Log.e(TAG, "SDK is busy");
                e.printStackTrace();
            } catch (NoNetworkException e) {
                Log.e(TAG, "Network is not available\n" + e.getStackTrace());
                Toast.makeText(_context, "ERROR: Network is not available", Toast.LENGTH_LONG).show();
            }
            System.out.println("my json object:" + getResult);
            JSONArray myListsAll = null;

            try {

                myListsAll = new JSONArray(getResult);

            } catch (JSONException e) {

                e.printStackTrace();

            }
            System.out.println("json array" + myListsAll.length());
//            speakOut("");
            try {
                synthesis.speak("   ");
            } catch (BusyException e) {
                Log.e(TAG, "SDK is busy");
                e.printStackTrace();
            } catch (NoNetworkException e) {
                Log.e(TAG, "Network is not available\n" + e.getStackTrace());
                Toast.makeText(_context, "ERROR: Network is not available", Toast.LENGTH_LONG).show();
            }
            if(myListsAll.length()!=0) {
                JSONObject jsonobject = null;
                try {
                    jsonobject = (JSONObject) myListsAll.get(0);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                System.out.println("purchase :" + jsonobject.optString("TOTALPURCHSE"));
                if (!jsonobject.optString("TOTALPURCHSE").equals("")) {
                    Prefs.putInt(BYTECH_APP_CONSTANT.shared_speak_flag, 1);
                    Prefs.putString(BYTECH_APP_CONSTANT.shared_result_count, jsonobject.optString("TOTALPURCHSE"));
//                    speakOut(" ");
                    try {
                        synthesis.speak("   ");
                    } catch (BusyException e) {
                        Log.e(TAG, "SDK is busy");
                        e.printStackTrace();
                    } catch (NoNetworkException e) {
                        Log.e(TAG, "Network is not available\n" + e.getStackTrace());
                        Toast.makeText(_context, "ERROR: Network is not available", Toast.LENGTH_LONG).show();
                    }
                    Intent txt_reslt = new Intent(getApplicationContext(), HP_PA_RESPONSETEXT.class);
                    startActivity(txt_reslt);
                    finish();
                } else {
                    //
                }
            }   else {
                Prefs.putInt(BYTECH_APP_CONSTANT.shared_speak_flag, 1);
                Prefs.putString(BYTECH_APP_CONSTANT.shared_result_count, "0");
//                speakOut(" ");
                try {
                    synthesis.speak("   ");
                } catch (BusyException e) {
                    Log.e(TAG, "SDK is busy");
                    e.printStackTrace();
                } catch (NoNetworkException e) {
                    Log.e(TAG, "Network is not available\n" + e.getStackTrace());
                    Toast.makeText(_context, "ERROR: Network is not available", Toast.LENGTH_LONG).show();
                }
                Intent txt_reslt = new Intent(getApplicationContext(), HP_PA_RESPONSETEXT.class);
                startActivity(txt_reslt);
                finish();
            }
        }

    }

}