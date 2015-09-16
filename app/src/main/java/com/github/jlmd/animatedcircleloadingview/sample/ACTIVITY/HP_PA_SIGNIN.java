package com.github.jlmd.animatedcircleloadingview.sample.ACTIVITY;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.app.jlmd.animatedcircleloadingview.sample.R;
import com.github.jlmd.animatedcircleloadingview.sample.ACTIVITY.MODEL.HP_PA_VALIDATION;

/**
 * Created by Mainak Karmakar on 14/09/2015.
 */
public class HP_PA_SIGNIN extends Activity
{

    private EditText signin_prtnrid_edttxt,signin_password_edttxt;
    Button signin_login_btn;
    TextView signin_forgetpswrd_txtvw,signin_powered_txtvw;
    Typeface typeFace;
    Boolean hp_pa_validation;
    HP_PA_VALIDATION Validation;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //layout declaration
        hppa_dcl_layout();
        //layout reference
        hppa_dcl_layout_variables();
        //widget fonts
        hppa_set_widget_fonts();

        signin_login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                hp_pa_validation();

                if (hp_pa_validation) {
                    //successfull validation
                    Toast.makeText(getApplicationContext(),"Successfull Registration",Toast.LENGTH_SHORT).show();
                    Intent splshint = new Intent(HP_PA_SIGNIN.this, HP_PA_HOME.class);
                    startActivity(splshint);
                }

            }
        });


    }
    //oncreate ends here
    public void hppa_dcl_layout() {
        setContentView(R.layout.hp_pa_signin);

    }
    public void hppa_dcl_layout_variables() {

        signin_prtnrid_edttxt = (EditText) findViewById(R.id.signin_prtnrid_edttxt);
        signin_password_edttxt  = (EditText) findViewById(R.id.signin_password_edttxt);
        signin_login_btn  = (Button) findViewById(R.id.signin_login_btn);
        signin_forgetpswrd_txtvw = (TextView) findViewById(R.id.signin_forgetpswrd_txtvw);
        signin_powered_txtvw = (TextView) findViewById(R.id.signin_powered_txtvw);

    }

    public void hppa_set_widget_fonts() {

        typeFace = Typeface.createFromAsset(getAssets(),
                "fonts/OpenSans-Regular.ttf");
        signin_prtnrid_edttxt.setTypeface(typeFace);
        signin_password_edttxt.setTypeface(typeFace);
        signin_login_btn.setTypeface(typeFace);
        signin_forgetpswrd_txtvw.setTypeface(typeFace);
        signin_powered_txtvw.setTypeface(typeFace);
    }

    private boolean hp_pa_validation()
    {
        hp_pa_validation = true;

        if (!Validation.hasText(signin_prtnrid_edttxt)) hp_pa_validation = false;
        if (!Validation.hasText(signin_password_edttxt)) hp_pa_validation = false;
        if (!Validation.isEmailAddress(signin_prtnrid_edttxt, true)) hp_pa_validation = false;

        return hp_pa_validation;
    }
}
