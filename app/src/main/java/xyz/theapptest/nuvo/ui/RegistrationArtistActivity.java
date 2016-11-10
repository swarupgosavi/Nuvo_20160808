package xyz.theapptest.nuvo.ui;

import android.app.Activity;
import android.graphics.Typeface;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import xyz.theapptest.nuvo.R;

/**
 * Created by trtcpu007 on 7/7/16.
 */

public class RegistrationArtistActivity extends Activity {
    TextView tv_register,tv_title;
    EditText ed_email,ed_password;
    Button bt_signup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_artist);
        init();
        typeInterface();
        onClickevent();
        
    }

    private void onClickevent() {





    }

    private void typeInterface() {

        Typeface facetxttitle = Typeface.createFromAsset(getAssets(),
                "font/Vonique 64.ttf");
        tv_title.setTypeface(facetxttitle);

        Typeface facetxtsigin = Typeface.createFromAsset(getAssets(),
                "font/OpenSans-Semibold.ttf");
        tv_register.setTypeface(facetxtsigin);
        bt_signup.setTypeface(facetxtsigin);


        Typeface facetxtsigintextbox = Typeface.createFromAsset(getAssets(),
                "font/OpenSans-Regular.ttf");
        ed_email.setTypeface(facetxtsigintextbox);
        ed_password.setTypeface(facetxtsigintextbox);



    }

    private void init() {
        tv_title=(TextView)findViewById(R.id.toolbar_title);
        tv_register=(TextView)findViewById(R.id.tv_register);
        ed_email=(EditText)findViewById(R.id.ed_email);
        ed_password=(EditText)findViewById(R.id.ed_password);
        bt_signup=(Button)findViewById(R.id.signup);



    }
}
