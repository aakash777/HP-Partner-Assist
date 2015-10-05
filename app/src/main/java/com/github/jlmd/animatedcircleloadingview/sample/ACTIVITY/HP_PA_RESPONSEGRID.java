package com.github.jlmd.animatedcircleloadingview.sample.ACTIVITY;

import android.app.Activity;
import android.graphics.Typeface;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.app.jlmd.animatedcircleloadingview.sample.R;

/**
 * Created by Mainak Karmakar on 03-10-2015.
 */
public class HP_PA_RESPONSEGRID extends Activity {

    Typeface typeFace;
    TextView response_graphvw_txtvw , response_gridvw_txtvw , response_txtvw_txtvw
            , footer_response_txt , footer_powered_txt;
    RelativeLayout response_header_gridvw_rl,response_header_txtvw_rl ,
            response_header_graphvw_rl;
    ImageView response_txt_imgvw,response_graph_imgvw,response_grid_imgvw;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //layout declaration
        hppa_dcl_layout();
        //layout reference
        hppa_dcl_layout_variables();
        //widget fonts
        hppa_set_widget_fonts();
        response_header_gridvw_rl.setBackgroundResource(R.drawable.response_blue_brdr);
        response_txtvw_txtvw.setTextColor(getResources().getColor(R.color.app_white));
        response_txt_imgvw.setBackgroundResource(R.drawable.textvw);
    }//oncreate ends here

    public void hppa_dcl_layout() {

        setContentView(R.layout.hp_pa_responsegrid);

    }
    public void hppa_dcl_layout_variables() {

        response_graphvw_txtvw = (TextView) findViewById(R.id.response_graphvw_txtvw);
        response_gridvw_txtvw = (TextView) findViewById(R.id.response_gridvw_txtvw);
        response_txtvw_txtvw = (TextView) findViewById(R.id.response_txtvw);
        footer_response_txt = (TextView) findViewById(R.id.footer_response_txt);
        footer_powered_txt = (TextView) findViewById(R.id.footer_powered_txt);
        response_header_txtvw_rl = (RelativeLayout) findViewById(R.id.response_header_txtvw_rl);
        response_header_gridvw_rl = (RelativeLayout) findViewById(R.id.response_header_gridvw_rl);
        response_header_graphvw_rl = (RelativeLayout) findViewById(R.id.response_header_graphvw_rl);
        response_txt_imgvw = (ImageView) findViewById(R.id.response_txt_imgvw);
        response_graph_imgvw = (ImageView) findViewById(R.id.response_graph_imgvw);
        response_grid_imgvw = (ImageView) findViewById(R.id.response_grid_imgvw);
    }

    public void hppa_set_widget_fonts() {

        typeFace = Typeface.createFromAsset(getAssets(),
                "fonts/OpenSans-Regular.ttf");

        response_graphvw_txtvw.setTypeface(typeFace);
        response_gridvw_txtvw.setTypeface(typeFace);
        response_txtvw_txtvw.setTypeface(typeFace);
        footer_response_txt.setTypeface(typeFace);
        footer_powered_txt.setTypeface(typeFace);
    }

}
