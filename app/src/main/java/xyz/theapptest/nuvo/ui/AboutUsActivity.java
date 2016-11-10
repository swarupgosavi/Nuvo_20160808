package xyz.theapptest.nuvo.ui;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import xyz.theapptest.nuvo.R;

/**
 * Created by trtcpu007 on 13/8/16.
 */

public class AboutUsActivity extends Activity{
    ImageView imgback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);
        imgback=(ImageView)findViewById(R.id.back);
        imgback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
