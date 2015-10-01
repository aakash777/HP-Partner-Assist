package com.github.jlmd.animatedcircleloadingview.sample.ACTIVITY;


import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AnalogClock;
import android.widget.DigitalClock;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.app.jlmd.animatedcircleloadingview.sample.R;
import com.github.jlmd.animatedcircleloadingview.sample.ACTIVITY.APP_UTILS.BYTECH_APP_CONSTANT;
import com.jpardogo.android.googleprogressbar.library.GoogleProgressBar;
import com.pixplicity.easyprefs.library.Prefs;
//import com.acapelagroup.android.tts.acattsandroid;
import java.util.ArrayList;
import java.util.Locale;

/**
 * Created by Mainak Karmakar on 15/09/2015.
 */
public class HP_PA_HOME extends Activity implements RecognitionListener , TextToSpeech.OnInitListener
{

    ImageButton home_speech_imgbtn;
    TextView home_speech_txtvw,home_powered_txt,footer_marque_txt,timer_txtvw;
    Typeface typeFace;
    private SpeechRecognizer speech = null;
    private Intent recognizerIntent;
    private String LOG_TAG = "HP_PA_HOME";
//  private AnimatedCircleLoadingView animatedCircleLoadingView;
    private TextToSpeech tts;
    String globaltext;
    Animation animScale;
    GoogleProgressBar mProgressBar;
    String tempresult;
    String spoken_user_words[];
    int entityflag;
    int wh_qstn_flg;
    int non_english_flag;
    String pre_validated_text;
    final Context context = this;
    private final long startTime = 30 * 1000;
    private final long interval = 1 * 1000;
    private CountDownTimer countDownTimer;
    private boolean timerHasStarted = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pre_validated_text = "";
        globaltext = Prefs.getString(BYTECH_APP_CONSTANT.shared_wishing_time, "")+"\n"+
                Prefs.getString(BYTECH_APP_CONSTANT.shared_partner_name, "")+"\n"+
                "welcome to bytech india "+"\n"+"please tab and ask your question";
        tts = new TextToSpeech(this, this);
        System.out.println("speech status onstart" + tts.isSpeaking());
        //layout declaration
        hppa_dcl_layout();
        //setting the animation
        animScale = AnimationUtils.loadAnimation(this,R.anim.anim_scale);
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

    }//oncreate ends here

    public void hppa_dcl_layout() {

        setContentView(R.layout.hp_pa_home);

    }
    public void hppa_dcl_layout_variables() {

        home_speech_imgbtn  = (ImageButton) findViewById(R.id.home_speech_imgbtn);
        home_speech_txtvw = (TextView) findViewById(R.id.home_speech_txtvw);
        home_powered_txt = (TextView) findViewById(R.id.footer_powered_txt);
        mProgressBar = (GoogleProgressBar) findViewById(R.id.google_progress);
        footer_marque_txt = (TextView) findViewById(R.id.footer_marque_txt);

        AnalogClock analog = (AnalogClock) findViewById(R.id.analog_clock);
        //analog clock
        DigitalClock digital = (DigitalClock) findViewById(R.id.digital_clock);
        //digital clock

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

                // custom dialog
                Dialog dialog = new Dialog(context, android.R.style.Theme_Translucent_NoTitleBar_Fullscreen);
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
                countDownTimer = new MyCountDownTimer(startTime, interval);
                timer_txtvw.setText(timer_txtvw.getText() + String.valueOf(startTime / 1000));
                if (!timerHasStarted) {
                    countDownTimer.start();
                    timerHasStarted = true;
                } else {
                    countDownTimer.cancel();
                    timerHasStarted = false;
                }
                view.startAnimation(animScale);
                speech.startListening(recognizerIntent);
                home_speech_imgbtn.setClickable(false);
                mProgressBar.setVisibility(View.VISIBLE);

            }
        });

    }

    public class MyCountDownTimer extends CountDownTimer {

        public MyCountDownTimer(long startTime, long interval) {

            super(startTime, interval);

        }

        @Override

        public void onFinish() {
            timer_txtvw.setText("Time's up!");
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
        home_speech_txtvw.setText("please wait");
    }

    @Override
    public void onError(int errorCode) {
        String errorMessage = getErrorText(errorCode);
        Log.d(LOG_TAG, "FAILED " + errorMessage);
        home_speech_txtvw.setText(errorMessage);
        speakOut(errorMessage + " please tab and speak again");
        pre_validated_text="";
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
             speakOut("please wait!! while we process your question");
             footer_marque_txt.setText("");

         }else
         {
             speakOut("Sorry!! we do not have any question to move forward"+
                     "\n"+"Please Tab and ask your question");
         }

     }else  if((sentence.equalsIgnoreCase("cancel"))||(sentence.equalsIgnoreCase("cancels"))
                 ||(sentence.equalsIgnoreCase("cance")))
         {
             if(!pre_validated_text.equals(""))
             {
                 speakOut("your previous question has been cancelled"+
                         "\n"+"Please Tab and ask your question");
                 pre_validated_text = "";
                 footer_marque_txt.setText("");

             } else {
                 speakOut("there is no previous question to cancel"+
                         "\n"+"Please Tab and ask your question");
             }


         }
         else {
         spoken_user_words = sentence.trim().split("\\s+");
         if (spoken_user_words.length <= 10) {
             System.out.println("word length " + spoken_user_words.length);
             for (int i = 0; i < spoken_user_words.length; i++) {
                 System.out.println("word at position " + i + " is " + spoken_user_words[i]);
                 if (spoken_user_words[i].length() > 45) {
                     speakOut("Sorry !! We found that your Question has one or more invalid " +
                             "word!!" + "\n" + "Please tab and Try again");
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
                         speakOut("Do you mean " + "\t" + tempresult + "\n" +
                                 "Please say go or proceed to process your question"
                                 + "\n" + "cancel to ask another");
                     } else {
                         speakOut("it seams you are not speaking english " + "\n" +
                                 "Please tab and ask a valid question");
                      //   footer_marque_txt.setText("Please tab and speak again");

                     }

                 } else if (entityflag >= 1) {

                     speakOut("Sorry !! there cannot be more than one Entity " +
                             "in your Question" + "\n" + "Please tab and speak a valid question");
                     //   footer_marque_txt.setText("Please tab and speak again");

                 } else if (entityflag >= 0) {
                     speakOut("Sorry !! We found no Entity in your Question" + "\n" +
                             "Please tab and speak a valid question");
                     //    footer_marque_txt.setText("Please tab and speak again");

                 }
             } else if (wh_qstn_flg > 1) {
                 speakOut("Sorry !! there cannot be more than one W.H Question" + "\n" +
                         "Please tab and speak a valid question");
                 //footer_marque_txt.setText("Please tab and speak again");

             } else if (wh_qstn_flg == 0) {
                 speakOut("Sorry !! We found no W.H Question" + "\n" +
                         "Please tab and speak a valid question");
               //  footer_marque_txt.setText("Please tab and speak again");

             }
         } else {
             speakOut("Sorry !! We found that your Question exceeds the maximum Length!!" + "\n" +
                     "Please tab and Try again with a new Question");
         //    footer_marque_txt.setText("Please tab and speak again");

         }
     }
    }
}
