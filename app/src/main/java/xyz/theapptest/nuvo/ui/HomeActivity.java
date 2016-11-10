package xyz.theapptest.nuvo.ui;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import xyz.theapptest.nuvo.R;
import xyz.theapptest.nuvo.utils.ConstantData;

/**
 * Created by trtcpu007 on 8/7/16.
 */

public class HomeActivity extends Activity implements View.OnClickListener {
    TextView tv_title;
    ImageView img_back;
    ConstantData constData;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homescreen);
        init();
        typeInterface();
    }

    private void typeInterface() {
        Typeface facetxttitle = Typeface.createFromAsset(getAssets(),
                "font/Vonique 64.ttf");
        tv_title.setTypeface(facetxttitle);




    }

    @Override
    public void onBackPressed() {



    }

    private void init() {
        tv_title = (TextView) findViewById(R.id.toolbar_title);
        img_back = (ImageView) findViewById(R.id.back);
        img_back.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                Intent intent = new Intent(getApplicationContext(), SignUpActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);

                break;
        }

    }
}
