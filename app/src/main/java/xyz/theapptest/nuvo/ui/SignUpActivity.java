package xyz.theapptest.nuvo.ui;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.readystatesoftware.systembartint.SystemBarTintManager;

import xyz.theapptest.nuvo.R;
import xyz.theapptest.nuvo.utils.ConstantData;

/**
 * Created by trtcpu007 on 5/7/16.
 */

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener {
    ImageView im_Producer, im_Artist, im_Agent;
    TextView tv_whatsuptext, tv_selecttype, tv_title;
    Button bt_Login, bt_Register;
    LinearLayout lv_default, lv_producer, lv_artist, lv_agent;
    String typeofuser;
    CustomizeDialog customizeDialog = null;
    ConstantData constantData;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SystemBarTintManager mTintManager = new SystemBarTintManager(this);
// enable status bar tint
        mTintManager.setStatusBarTintEnabled(true);
        mTintManager.setTintColor(getResources().getColor(R.color.black));
        setContentView(R.layout.activity_signup);
// create manager instance after the content view is set
        constantData = ConstantData.getInstance();
        init();
        typeInterface();
        onClickevent();

        //// im_Producer.setImageResource(R.drawable.producer);
        //  im_Agent.setImageResource(R.drawable.agent);
        //  im_Artist.setImageResource(R.drawable.artist);

    }


    private void onClickevent() {
        bt_Login.setOnClickListener(this);
        bt_Register.setOnClickListener(this);


        bt_Login.setEnabled(true);
        bt_Register.setEnabled(true);
        lv_default = (LinearLayout) findViewById(R.id.lv_default);


        im_Producer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                typeofuser = "Producer";
                //   bt_Login.setEnabled(true);
                //  bt_Register.setEnabled(true);
                im_Producer.setImageResource(R.drawable.producer_selected);
                im_Artist.setImageResource(R.drawable.artist);
                im_Agent.setImageResource(R.drawable.agent);
                android.view.ViewGroup.LayoutParams layoutParams = im_Producer.getLayoutParams();
                DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
                int px = Math.round(150 * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
                layoutParams.width = px;
                layoutParams.height = px;
                im_Producer.setLayoutParams(layoutParams);

                android.view.ViewGroup.LayoutParams layoutParams1 = im_Agent.getLayoutParams();
                android.view.ViewGroup.LayoutParams layoutParams2 = im_Artist.getLayoutParams();
                int px1 = Math.round(70 * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
                layoutParams1.width = px1;
                layoutParams1.height = px1;
                layoutParams2.width = px1;
                layoutParams2.height = px1;

                im_Agent.setLayoutParams(layoutParams1);
                im_Artist.setLayoutParams(layoutParams2);


            }
        });

        im_Artist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                typeofuser = "Artist";
                //   bt_Login.setEnabled(true);
                //   bt_Register.setEnabled(true);
                im_Artist.setImageResource(R.drawable.artist_selected);
                im_Producer.setImageResource(R.drawable.producer);
                im_Agent.setImageResource(R.drawable.agent);
                android.view.ViewGroup.LayoutParams layoutParams = im_Artist.getLayoutParams();
                DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
                int px = Math.round(150 * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
                layoutParams.width = px;
                layoutParams.height = px;
                im_Artist.setLayoutParams(layoutParams);

                android.view.ViewGroup.LayoutParams layoutParams1 = im_Producer.getLayoutParams();
                android.view.ViewGroup.LayoutParams layoutParams2 = im_Agent.getLayoutParams();
                int px1 = Math.round(70 * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
                layoutParams1.width = px1;
                layoutParams1.height = px1;
                layoutParams2.width = px1;
                layoutParams2.height = px1;

                im_Producer.setLayoutParams(layoutParams1);
                im_Agent.setLayoutParams(layoutParams2);


            }
        });
        im_Agent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                typeofuser = "Agent";
                //    bt_Login.setEnabled(true);
                //   bt_Register.setEnabled(true);
                im_Agent.setImageResource(R.drawable.agent_selected);
                im_Producer.setImageResource(R.drawable.producer);
                im_Artist.setImageResource(R.drawable.artist);
                android.view.ViewGroup.LayoutParams layoutParams = im_Agent.getLayoutParams();
                DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
                int px = Math.round(150 * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
                layoutParams.width = px;
                layoutParams.height = px;
                im_Agent.setLayoutParams(layoutParams);

                android.view.ViewGroup.LayoutParams layoutParams1 = im_Producer.getLayoutParams();
                android.view.ViewGroup.LayoutParams layoutParams2 = im_Artist.getLayoutParams();
                int px1 = Math.round(70 * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
                layoutParams1.width = px1;
                layoutParams1.height = px1;
                layoutParams2.width = px1;
                layoutParams2.height = px1;

                im_Producer.setLayoutParams(layoutParams1);
                im_Artist.setLayoutParams(layoutParams2);


            }
        });


    }


    private void typeInterface() {
        Typeface facetxttitle = Typeface.createFromAsset(getAssets(),
                "font/Vonique 64.ttf");
        tv_title.setTypeface(facetxttitle);


        Typeface facetxt = Typeface.createFromAsset(getAssets(),
                "font/Avenir Medium.otf");
        tv_whatsuptext.setTypeface(facetxt);
        tv_selecttype.setTypeface(facetxt);

        Typeface facebtn = Typeface.createFromAsset(getAssets(),
                "font/OpenSans-Semibold.ttf");
        bt_Login.setTypeface(facebtn);
        bt_Register.setTypeface(facebtn);


    }

    public static float convertPixelsToDp(float px, Context context) {
        Resources resources = context.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        float dp = px / ((float) metrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT);
        return dp;
    }

    private void init() {
        tv_title = (TextView) findViewById(R.id.toolbar_title);


        im_Producer = (ImageView) findViewById(R.id.im_producerdefault);
        im_Artist = (ImageView) findViewById(R.id.im_artistdefault);
        im_Agent = (ImageView) findViewById(R.id.im_agentdefault);
        im_Producer.setClickable(true);
        im_Artist.setClickable(true);
        im_Agent.setClickable(true);

        tv_whatsuptext = (TextView) findViewById(R.id.tv_whats);
        tv_selecttype = (TextView) findViewById(R.id.tv_select);
        bt_Login = (Button) findViewById(R.id.login);
        bt_Register = (Button) findViewById(R.id.register);


    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.login:
                constantData = ConstantData.getInstance();
                if (typeofuser != null && !typeofuser.equals("")) {
                    if (typeofuser.equalsIgnoreCase("Producer") || typeofuser.equalsIgnoreCase("Artist") || typeofuser.equalsIgnoreCase("Agent")) {
                        Intent login = new Intent(SignUpActivity.this, SignInActivity.class);
                        if (typeofuser.equalsIgnoreCase("Producer")) {
                            constantData.setRoleofuser("Producer");
                            login.putExtra("Screentype", "Producer");
                        } else {
                            if (typeofuser.equalsIgnoreCase("Artist")) {
                                constantData.setRoleofuser("Artist");
                                login.putExtra("Screentype", "Artist");
                            } else {
                                if (typeofuser.equalsIgnoreCase("Agent")) {
                                    constantData.setRoleofuser("Agent");
                                    login.putExtra("Screentype", "Agent");
                                }
                            }
                        }

                        startActivity(login);
                        finish();

                    } else {
                        customizeDialog = new CustomizeDialog(SignUpActivity.this);
                        customizeDialog.setTitle("nuvo");
                        customizeDialog.setCancelable(false);
                        customizeDialog.setMessage("Please select user type.");
                        customizeDialog.show();
                    }
                } else {
                    customizeDialog = new CustomizeDialog(SignUpActivity.this);
                    customizeDialog.setTitle("nuvo");
                    customizeDialog.setCancelable(false);
                    customizeDialog.setMessage("Please select user type.");
                    customizeDialog.show();
                }
                break;
            case R.id.register:
                if (typeofuser != null && !typeofuser.equals("")) {

                    if (typeofuser.equalsIgnoreCase("Artist")) {
                        constantData.setRoleofuser("Artist");
                        Intent register = new Intent(SignUpActivity.this, RegistrationActivity.class);
                        register.putExtra("Screentype", "Artist");
                        startActivity(register);
                        finish();
                        //  Intent register = new Intent(SignUpActivity.this, RegistrationArtistActivity.class);
                        //   startActivity(register);

                    } else {
                        if (typeofuser.equalsIgnoreCase("Producer")) {
                            constantData.setRoleofuser("Producer");
                            Intent register = new Intent(SignUpActivity.this, RegistrationActivity.class);
                            register.putExtra("Screentype", "Producer");
                            startActivity(register);
                            finish();
                        } else {
                            if (typeofuser.equalsIgnoreCase("Agent")) {
                                constantData.setRoleofuser("Agent");
                                Intent register = new Intent(SignUpActivity.this, RegistrationActivity.class);
                                register.putExtra("Screentype", "Agent");
                                startActivity(register);
                                finish();
                            } else {
                                customizeDialog = new CustomizeDialog(SignUpActivity.this);
                                customizeDialog.setTitle("nuvo");
                                customizeDialog.setCancelable(false);
                                customizeDialog.setMessage("Please select user type.");
                                customizeDialog.show();
                            }
                        }
                    }
                } else {
                    customizeDialog = new CustomizeDialog(SignUpActivity.this);
                    customizeDialog.setTitle("nuvo");
                    customizeDialog.setCancelable(false);
                    customizeDialog.setMessage("Please select user type.");
                    customizeDialog.show();
                }
                break;
            default:

                break;
        }
    }
}