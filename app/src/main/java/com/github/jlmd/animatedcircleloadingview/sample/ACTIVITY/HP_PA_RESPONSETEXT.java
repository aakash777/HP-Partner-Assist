package com.github.jlmd.animatedcircleloadingview.sample.ACTIVITY;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.support.v7.widget.Toolbar;

import com.app.jlmd.animatedcircleloadingview.sample.R;
import com.github.jlmd.animatedcircleloadingview.sample.ACTIVITY.APP_UTILS.BYTECH_APP_CONSTANT;
import com.pixplicity.easyprefs.library.Prefs;
import java.util.Locale;

/**
 * Created by Mainak Karmakar on 03-10-2015.
 */
public class HP_PA_RESPONSETEXT extends Activity implements TextToSpeech.OnInitListener{

    Typeface typeFace;
    TextView response_graphvw_txtvw , response_gridvw_txtvw , response_txtvw_txtvw,responsetxt_cmpnntcount_txtvw,
            responsetxt_cmpnnt_txtvw;
    EditText footer_response_txt;
    RelativeLayout response_header_txtvw_rl,response_header_gridvw_rl,response_header_graphvw_rl;
    ImageView response_txt_imgvw,response_graph_imgvw,response_grid_imgvw,response_speak_btn , action_home;
    Animation animScale;
    String globaltext;
    android.support.v7.widget.Toolbar toolbar;

    private TextToSpeech tts2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(Prefs.getInt(BYTECH_APP_CONSTANT.shared_speak_flag,0)==1) {

            if(Prefs.getString(BYTECH_APP_CONSTANT.shared_result_count, "").equals("0"))
            {
                globaltext = "Dear" + "\n" + Prefs.getString(BYTECH_APP_CONSTANT.shared_partner_name, "") + "\n" +
                        "You will not get any purchase details for the current week";

                tts2 = new TextToSpeech(this, this);
                Prefs.putInt(BYTECH_APP_CONSTANT.shared_speak_flag, 0);
            }
            else{
                globaltext = "Dear" + "\n" + Prefs.getString(BYTECH_APP_CONSTANT.shared_partner_name, "") + "\n" +
                        "You Have" + "\n" + Prefs.getString(BYTECH_APP_CONSTANT.shared_result_count, "") +"\n"+
                        Prefs.getString(BYTECH_APP_CONSTANT.shared_product_name,"")+ "\n" +
                        Prefs.getString(BYTECH_APP_CONSTANT.shared_action_type, "");

                tts2 = new TextToSpeech(this, this);
                Prefs.putInt(BYTECH_APP_CONSTANT.shared_speak_flag, 0);
            }

        }
        //layout declaration
        hppa_dcl_layout();
        //layout reference
        hppa_dcl_layout_variables();

        action_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Prefs.putInt(BYTECH_APP_CONSTANT.shared_home_speak_flag, 0);
                Intent hom_int = new Intent(getApplicationContext(), HP_PA_HOME.class);
                startActivity(hom_int);
                finish();
            }
        });
        responsetxt_cmpnnt_txtvw.setText(Prefs.getString(BYTECH_APP_CONSTANT.shared_product_name, ""));

        //setting the animation
        animScale = AnimationUtils.loadAnimation(this, R.anim.anim_scale);
        //widget fonts
        hppa_set_widget_fonts();

        response_header_txtvw_rl.setBackgroundResource(R.drawable.response_blue_brdr);
        response_txtvw_txtvw.setTextColor(getResources().getColor(R.color.app_white));
        response_txt_imgvw.setBackgroundResource(R.drawable.textvw);

        response_header_gridvw_rl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent grid = new Intent(getApplicationContext(), HP_PA_RESPONSEGRID.class);
                startActivity(grid);
                finish();

            }
        });

        response_header_graphvw_rl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent graph = new Intent(getApplicationContext(), HP_PA_RESPONSEGRAPH.class);
                startActivity(graph);
                finish();

            }
        });
    }//oncreate ends here

    @Override
    public void onResume() {
        super.onResume();

        responsetxt_cmpnntcount_txtvw.setText(Prefs.getString(BYTECH_APP_CONSTANT.shared_result_count, ""));

        response_speak_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                view.startAnimation(animScale);
            }
        });

    }

    public void hppa_dcl_layout() {

        setContentView(R.layout.hp_pa_responsetxt);

    }
    public void hppa_dcl_layout_variables() {

        toolbar = (Toolbar) findViewById(R.id.hppa_tool_bar);
        action_home = (ImageView) toolbar.findViewById(R.id.action_home);
        response_graphvw_txtvw = (TextView) findViewById(R.id.response_graphvw_txtvw);
        response_gridvw_txtvw = (TextView) findViewById(R.id.response_gridvw_txtvw);
        response_txtvw_txtvw = (TextView) findViewById(R.id.response_txtvw);
        responsetxt_cmpnntcount_txtvw = (TextView) findViewById(R.id.responsetxt_cmpnntcount_txtvw);

        footer_response_txt = (EditText) findViewById(R.id.response_command_txtvw);
//      footer_powered_txt = (TextView) findViewById(R.id.footer_powered_txt);
        response_header_txtvw_rl = (RelativeLayout) findViewById(R.id.response_header_txtvw_rl);
        response_header_gridvw_rl = (RelativeLayout) findViewById(R.id.response_header_gridvw_rl);
        response_header_graphvw_rl = (RelativeLayout) findViewById(R.id.response_header_graphvw_rl);
        response_txt_imgvw = (ImageView) findViewById(R.id.response_txt_imgvw);
        response_graph_imgvw = (ImageView) findViewById(R.id.response_graph_imgvw);
        response_grid_imgvw = (ImageView) findViewById(R.id.response_grid_imgvw);
        response_speak_btn = (ImageView) findViewById(R.id.response_speak_btn);
        responsetxt_cmpnnt_txtvw = (TextView) findViewById(R.id.responsetxt_cmpnnt_txtvw);

    }

    public void hppa_set_widget_fonts() {

        typeFace = Typeface.createFromAsset(getAssets(),
                "fonts/OpenSans-Regular.ttf");

        response_graphvw_txtvw.setTypeface(typeFace);
        response_gridvw_txtvw.setTypeface(typeFace);
        response_txtvw_txtvw.setTypeface(typeFace);
        footer_response_txt.setTypeface(typeFace);
//        footer_powered_txt.setTypeface(typeFace);
    }

    private void speakOut(String text) {
        System.out.println("Entered Speakout");
        System.out.println("Speakout text" + text);
        tts2.speak(text, TextToSpeech.QUEUE_FLUSH, null);
    }

    @Override
    public void onInit(int status) {

        System.out.println("Enterd init Function");
        System.out.println("tts init status out" + status);
        if (status == TextToSpeech.SUCCESS) {

            int result = tts2.setLanguage(Locale.ENGLISH);
            tts2.setSpeechRate((float) 1.3);
            System.out.println("tts init status if" + status);
            tts2.setPitch((float) 1.225); // set pitch level

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

}
