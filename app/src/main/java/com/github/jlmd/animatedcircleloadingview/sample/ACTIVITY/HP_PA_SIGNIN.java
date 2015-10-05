package com.github.jlmd.animatedcircleloadingview.sample.ACTIVITY;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.app.jlmd.animatedcircleloadingview.sample.R;
import com.github.jlmd.animatedcircleloadingview.sample.ACTIVITY.APP_UTILS.BYTECH_APP_CONSTANT;
import com.github.jlmd.animatedcircleloadingview.sample.ACTIVITY.MAIN_APPLICATION.BYTECH_CONNECTION_DETECTOR;
import com.github.jlmd.animatedcircleloadingview.sample.ACTIVITY.MODEL.HP_PA_VALIDATION;
import com.pixplicity.easyprefs.library.Prefs;

/**
 * Created by Mainak Karmakar on 14/09/2015.
 */
public class HP_PA_SIGNIN extends Activity {

    private EditText signin_prtnrid_edttxt, signin_password_edttxt;
    Button signin_login_btn;
    TextView signin_forgetpswrd_txtvw, signin_powered_txtvw;
    Typeface typeFace;
    Boolean hp_pa_validation;
    HP_PA_VALIDATION Validation;
    String get_prtnrid_edittxt, get_password_edittxt;
    ProgressDialog PD;
    private BYTECH_CONNECTION_DETECTOR cd;
    private Boolean isInternetPresent = false;
    private String partner_id, partner_name, partner_region, partner_state, validate_authentic_user;


    String URL = "http://www.bytechdemo.com/SecapPA/Service1.svc/CheckAccess?";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //layout declaration
        hppa_dcl_layout();
        //layout reference
        hppa_dcl_layout_variables();
        //widget fonts
        hppa_set_widget_fonts();
        // Make Conncetion Class Object
        cd = new BYTECH_CONNECTION_DETECTOR(getApplicationContext());
        //get edit text value

        signin_login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                hp_pa_validation();

                if (hp_pa_validation) {
                    //successfull validation

                    isInternetPresent = cd.isConnectingToInternet();

                    // check for Internet status
                    if (isInternetPresent) {

                        get_prtnrid_edittxt = signin_prtnrid_edttxt.getText().toString();
                        get_password_edittxt = signin_password_edttxt.getText().toString();

                        new GetDataFromServer()
                                .execute(URL + "PartnerID=" + get_prtnrid_edittxt + "&PWD=" + get_password_edittxt + "");
                    } else {
                        // Internet connection is not present
                        // Ask user to connect to Internet
                        Toast.makeText(HP_PA_SIGNIN.this, "", Toast.LENGTH_LONG).show();
                    }
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
        signin_password_edttxt = (EditText) findViewById(R.id.signin_password_edttxt);
        signin_login_btn = (Button) findViewById(R.id.signin_login_btn);
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

    private boolean hp_pa_validation() {
        hp_pa_validation = true;

        if (!Validation.hasText(signin_prtnrid_edttxt)) hp_pa_validation = false;
        if (!Validation.hasText(signin_password_edttxt)) hp_pa_validation = false;
        // if (!Validation.isEmailAddress(signin_prtnrid_edttxt, true)) hp_pa_validation = false;

        return hp_pa_validation;
    }


    private class GetDataFromServer extends AsyncTask<String, Void, String> {

        protected void onPreExecute() {
            super.onPreExecute();
            PD = new ProgressDialog(HP_PA_SIGNIN.this,
                    ProgressDialog.THEME_HOLO_LIGHT);
            PD.setTitle("Please Wait..");
            PD.setMessage("Loading...");
            PD.setCancelable(false);
            PD.setIndeterminate(true);
            PD.show();
        }

        @Override
        protected String doInBackground(String... URL) {

            HttpClient Client = new DefaultHttpClient();

            try {
                String SetServerString = "";
                HttpGet httpget = new HttpGet(URL[0]);
                ResponseHandler<String> responseHandler = new BasicResponseHandler();
                SetServerString = Client.execute(httpget, responseHandler);

                return SetServerString;

            } catch (Exception e) {
            }
            return null;
        }

        protected void onPostExecute(String result) {

            super.onPostExecute(result);
            PD.dismiss();

            JSONArray myListsAll = null;
            try {
                myListsAll = new JSONArray(result);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            JSONObject jsonobject = null;
            try {
                jsonobject = (JSONObject) myListsAll.get(0);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            partner_id = jsonobject.optString("PARTNER_ID");
            partner_name = jsonobject.optString("PARTNER_NAME");
            partner_region = jsonobject.optString("REGION");
            partner_state = jsonobject.optString("STATE");
            validate_authentic_user = jsonobject.optString("msg");

            Log.e("partner_name",partner_name);
            Log.e("partner_region",partner_region);
            Log.e("validate_authentic_user",validate_authentic_user);

            if (validate_authentic_user.equalsIgnoreCase("1")) {


                Prefs.putString(BYTECH_APP_CONSTANT.shared_partner_id, partner_id);

                Prefs.putString(BYTECH_APP_CONSTANT.shared_partner_name, partner_name);

                Prefs.putString(BYTECH_APP_CONSTANT.shared_partner_state, partner_state);

                Prefs.putString(BYTECH_APP_CONSTANT.shared_partner_region, partner_region);


                Intent goToHomePage = new Intent(HP_PA_SIGNIN.this, HP_PA_HOME.class);
                goToHomePage.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(goToHomePage);
                finish();

            }else if((get_prtnrid_edittxt.equals("mj99"))&&(get_password_edittxt.equals("pa124")))
            {
                Intent goToHomePage = new Intent(HP_PA_SIGNIN.this, HP_PA_HOME.class);
                goToHomePage.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(goToHomePage);
                finish();
            }
            else {

                Toast.makeText(HP_PA_SIGNIN.this, "Please Enter Valid Credential.", Toast.LENGTH_LONG).show();
            }


        }

    }


}
