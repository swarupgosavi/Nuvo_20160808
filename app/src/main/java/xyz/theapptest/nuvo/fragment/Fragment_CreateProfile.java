package xyz.theapptest.nuvo.fragment;


import android.app.Activity;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import xyz.theapptest.nuvo.R;
import xyz.theapptest.nuvo.ui.CustomizeDialog;
import xyz.theapptest.nuvo.utils.ConstantData;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by trtcpu007 on 7/7/16.
 */

public class Fragment_CreateProfile extends Fragment implements View.OnClickListener {
    EditText ed_firstname, ed_lastname;
    TextView tv_gender, tv_age, tv_langauge;
    Button bt_male, bt_female, bt_genx, bt_millennials, bt_geny, bt_english, bt_french, bt_spanish, bt_british, bt_next;
    String firstname, lastname;
    ConstantData constData;
    public int genderstatus = -1;
    public int age = -1;
    public int langauge = 0;
    ArrayList<HashMap<String, String>> agelist;
    ArrayList<HashMap<String, String>> langaugelist;
    Button btn;
    Button btn1;
    LinearLayout layout;
    LinearLayout layout1;
    CustomizeDialog customizeDialog = null;
    public int selectedLanguage = 0;

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.gender_male:
                //  bt_male.setBackgroundResource(R.drawable.btn_color_dark);
                // bt_female.setBackgroundResource(R.drawable.rounded_edittext3);
                bt_male.setSelected(!bt_male.isSelected());

                if (bt_male.isSelected()) {
                    genderstatus = 14;
                    constData.setGvalue(14);
                    bt_female.setSelected(false);
                } else {


                }


                constData.setGender(bt_male.getText().toString());
                break;
            case R.id.gender_female:
                //  bt_female.setBackgroundResource(R.drawable.btn_color_dark);
                // bt_male.setBackgroundResource(R.drawable.rounded_edittext3);
                bt_female.setSelected(!bt_female.isSelected());

                if (bt_female.isSelected()) {
                    genderstatus = 15;
                    constData.setGvalue(15);
                    bt_male.setSelected(false);
                } else {

                }
                //  genderstatus = 1;
                constData.setGender(bt_female.getText().toString());
                break;

            /*case 11:

                // btn.setBackgroundResource(R.drawable.btn_color_dark);
                // recordoption = 1;
                Log.e("first button", "first button");
                break;
            case 12:
                //  btn.setBackgroundResource(R.drawable.btn_color_dark);
                //   recordoption = 1;
                Log.e("second button", "second button");
                break;
            case 13:
                //  btn.setBackgroundResource(R.drawable.btn_color_dark);
                //  recordoption = 1;
                Log.e("third button", "third button");
                break;

*/
          /*  case R.id.genx:
                bt_genx.setBackgroundResource(R.drawable.btn_color_dark);
                bt_millennials.setBackgroundResource(R.drawable.rounded_edittext3);
                bt_geny.setBackgroundResource(R.drawable.rounded_edittext3);
                age = 1;
                constData.setAge(bt_genx.getText().toString());
                break;
            case R.id.millenials:
                bt_genx.setBackgroundResource(R.drawable.rounded_edittext3);
                bt_millennials.setBackgroundResource(R.drawable.btn_color_dark);
                bt_geny.setBackgroundResource(R.drawable.rounded_edittext3);
                age = 1;
                constData.setAge(bt_millennials.getText().toString());
                break;
            case R.id.geny:
                bt_geny.setBackgroundResource(R.drawable.btn_color_dark);
                bt_millennials.setBackgroundResource(R.drawable.rounded_edittext3);
                bt_genx.setBackgroundResource(R.drawable.rounded_edittext3);
                age = 1;
                constData.setAge(bt_geny.getText().toString());
                break;
            case R.id.english:
                bt_english.setBackgroundResource(R.drawable.btn_color_dark);
                //  bt_french.setBackgroundResource(R.drawable.rounded_edittext3);
                //  bt_spanish.setBackgroundResource(R.drawable.rounded_edittext3);
                //  bt_british.setBackgroundResource(R.drawable.rounded_edittext3);
                langauge = 1;
                String langaugesstr1 = constData.getLangauge();
                if (!langaugesstr1.isEmpty()) {
                    constData.setLangauge(langaugesstr1 + "," + bt_english.getText().toString());
                } else {
                    constData.setLangauge(bt_english.getText().toString());
                }


                break;
            case R.id.french:
                // bt_english.setBackgroundResource(R.drawable.rounded_edittext3);
                bt_french.setBackgroundResource(R.drawable.btn_color_dark);
                //  bt_spanish.setBackgroundResource(R.drawable.rounded_edittext3);
                //  bt_british.setBackgroundResource(R.drawable.rounded_edittext3);
                langauge = 1;
                String langaugesstr2 = constData.getLangauge();
                if (langaugesstr2 != null) {
                    constData.setLangauge(langaugesstr2 + "," + bt_french.getText().toString());
                } else {
                    constData.setLangauge(bt_french.getText().toString());
                }

                break;
            case R.id.spanish:
                //  bt_english.setBackgroundResource(R.drawable.rounded_edittext3);
                //  bt_french.setBackgroundResource(R.drawable.rounded_edittext3);
                bt_spanish.setBackgroundResource(R.drawable.btn_color_dark);
                //  bt_british.setBackgroundResource(R.drawable.rounded_edittext3);
                langauge = 1;

                String langaugesstr3 = constData.getLangauge();
                if (langaugesstr3 != null) {
                    constData.setLangauge(langaugesstr3 + "," + bt_spanish.getText().toString());
                } else {
                    constData.setLangauge(bt_spanish.getText().toString());
                }
                break;
            case R.id.british:
                //  bt_english.setBackgroundResource(R.drawable.rounded_edittext3);
                //  bt_french.setBackgroundResource(R.drawable.rounded_edittext3);
                //  bt_spanish.setBackgroundResource(R.drawable.rounded_edittext3);
                bt_british.setBackgroundResource(R.drawable.btn_color_dark);
                langauge = 1;
                String langaugesstr4 = constData.getLangauge();
                if (langaugesstr4 != null) {
                    constData.setLangauge(langaugesstr4 + "," + bt_british.getText().toString());
                } else {
                    constData.setLangauge(bt_british.getText().toString());
                }*/

            // break;


        }

    }

    public interface onSomeEventListener {
        public void someEvent(int s);
    }

    onSomeEventListener someEventListener;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            someEventListener = (onSomeEventListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must implement onSomeEventListener");
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_createprofile, container, false);


        init(view);
        typeInterface();
        try {
            createdynamicbutton();
        } catch (Exception e) {
            e.printStackTrace();
        }

        onclickevent();

        return view;
    }

    private void createdynamicbutton() {
        constData = ConstantData.getInstance();
        agelist = constData.getAgelist();
        for (int i = 0; i < agelist.size(); i++) {


            btn = new Button(getActivity());
            btn.setBackgroundResource(R.drawable.button_selected);
            btn.setTextSize(12);

            Typeface facetxtsigintextbox = Typeface.createFromAsset(getActivity().getAssets(),
                    "font/OpenSans-Regular.ttf");
            btn.setTypeface(facetxtsigintextbox);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

            Log.e("age", agelist.get(i).get("age_value"));
            btn.setId(Integer.parseInt(agelist.get(i).get("id")) + 10);
            btn.setTag(Integer.parseInt(agelist.get(i).get("age_value")));

            params.weight = 1.5f;
            params.gravity = Gravity.LEFT;
            params.gravity= Gravity.RIGHT;

            DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
            // int px1 = Math.round(140 * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
            //  params.width = px1;
            int px = Math.round(39 * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
            params.height = px;

            params.setMargins(5, 10, 15, 2);
            btn.setLayoutParams(params);
            //  btn.setLayoutParams(new LinearLayout.LayoutParams(70, 30));
            btn.setTransformationMethod(null);
            btn.setTextColor(getActivity().getApplication().getResources().getColor(R.color.white));
            btn.setText(agelist.get(i).get("age_type"));
            //btn.setBackgroundResource(R.drawable.button_selected);
            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int IDS = v.getId() - 10;
                    Button o = (Button) v;
                    LinearLayout l = (LinearLayout) v.getParent();

                    for (int k = 11; k < 14; k++) {
                        o = (Button) l.findViewById(k);
                        o.setSelected(false);
                        Log.e("k", Integer.toString(k));
                    }
                    o = (Button) v;
                    o.setSelected(true);
                    age = (Integer) o.getTag();
                    Log.e("age-value", Integer.toString(age));
                    constData.setAgevalue(age);
                    //print o.getTag()

                    //Toast.makeText(getActivity(), o.getTag(), Toast.LENGTH_LONG).show();
                }
            });

            layout.addView(btn, params);
        }


        constData = ConstantData.getInstance();
        langaugelist = constData.getLanguage();
        for (int i = 0; i < langaugelist.size(); i++) {


            btn1 = new Button(getActivity());
            btn1.setBackgroundResource(R.drawable.button_selected);
            btn1.setTextSize(12);

            Typeface facetxtsigintextbox = Typeface.createFromAsset(getActivity().getAssets(),
                    "font/OpenSans-Regular.ttf");
            btn1.setTypeface(facetxtsigintextbox);

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

            btn1.setId(Integer.parseInt(langaugelist.get(i).get("id")) + 20);
          //  btn1.setMinHeight(0);

            btn1.setTransformationMethod(null);

            params.weight = 1.5f;
            params.gravity = Gravity.LEFT;
            params.gravity= Gravity.RIGHT;

            DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
            // int px1 = Math.round(140 * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
            //  params.width = px1;
            int px = Math.round(39 * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
            params.height = px;

            params.setMargins(5, 5, 15, 2);

            //   btn.setLayoutParams(new LinearLayout.LayoutParams(10, 100));
            //   btn1.setBackgroundResource(R.drawable.button_selected);
            btn1.setLayoutParams(params);
            //
            btn1.setTextColor(getActivity().getApplication().getResources().getColor(R.color.white));
            btn1.setText(langaugelist.get(i).get("language_name"));
            btn1.setTag(Integer.parseInt(langaugelist.get(i).get("language_value")));
            final List<Integer> arr = new ArrayList<Integer>();
            btn1.setOnClickListener(new View.OnClickListener() {


                @Override
                public void onClick(View v) {
                    int IDS = v.getId() - 20;


                    selectedLanguage = selectedLanguage ^ (Integer) v.getTag();

                    Log.e("dsdaasd: ", Integer.toString(selectedLanguage));
                    constData.setLvaue(selectedLanguage);
                    Button o = (Button) v;
                    if (o.isSelected()) {
                        // arr.add(IDS);

                        o.setSelected(false);

                        //  Toast.makeText(getActivity(), Integer.toString(IDS), Toast.LENGTH_LONG).show();
                    } else {


                        // int i1=Integer.toBinaryString(int i)
                        // langauge = 1;

                        //  arr.add(IDS);
                        //     Toast.makeText(getActivity(), Integer.toString(IDS), Toast.LENGTH_LONG).show();
                        o.setSelected(true);
                    }


                }
            });

            layout1.addView(btn1, params);
        }


    }

    private void onclickevent() {
        bt_next.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                constData = ConstantData.getInstance();
                Log.e("selected langauge", Integer.toString(selectedLanguage));
                if ((genderstatus > -1) && (age > -1) && (selectedLanguage > 0)) {


                    Log.e("gender", Integer.toString(constData.getGvalue()));
                    Log.e("age", Integer.toString(constData.getAgevalue()));
                    Log.e("langauge", Integer.toString(constData.getLvaue()));

                    someEventListener.someEvent(1);
                } else {
                    if (genderstatus == -1) {
                        customizeDialog = new CustomizeDialog(getActivity());
                        customizeDialog.setTitle("nuvo");
                        customizeDialog.setCancelable(false);
                        customizeDialog.setMessage("Please Select Gender.");
                        customizeDialog.show();
                    } else {
                        if (age == -1) {
                            customizeDialog = new CustomizeDialog(getActivity());
                            customizeDialog.setTitle("nuvo");
                            customizeDialog.setCancelable(false);
                            customizeDialog.setMessage("Please Select Age.");
                            customizeDialog.show();
                        } else {
                            if (selectedLanguage == 0) {
                                customizeDialog = new CustomizeDialog(getActivity());
                                customizeDialog.setTitle("nuvo");
                                customizeDialog.setCancelable(false);
                                customizeDialog.setMessage("Please Select Language.");
                                customizeDialog.show();
                            }
                        }
                    }
                }
            }
        });
    }

    private void typeInterface() {
        Typeface facetxtsigin = Typeface.createFromAsset(getActivity().getAssets(),
                "font/OpenSans-Semibold.ttf");
        bt_next.setTypeface(facetxtsigin);
        tv_gender.setTypeface(facetxtsigin);
        tv_age.setTypeface(facetxtsigin);
        tv_langauge.setTypeface(facetxtsigin);


        Typeface facetxtsigintextbox = Typeface.createFromAsset(getActivity().getAssets(),
                "font/OpenSans-Regular.ttf");
        bt_male.setTypeface(facetxtsigintextbox);
        bt_female.setTypeface(facetxtsigintextbox);
        //    bt_genx.setTypeface(facetxtsigintextbox);
        //    bt_millennials.setTypeface(facetxtsigintextbox);
        //   bt_geny.setTypeface(facetxtsigintextbox);
        //   bt_english.setTypeface(facetxtsigintextbox);
        //   bt_french.setTypeface(facetxtsigintextbox);
        //  bt_spanish.setTypeface(facetxtsigintextbox);
        //  bt_british.setTypeface(facetxtsigintextbox);
        ed_firstname.setTypeface(facetxtsigintextbox);
        ed_lastname.setTypeface(facetxtsigintextbox);


    }


    private void init(View view) {
        ed_firstname = (EditText) view.findViewById(R.id.ed_firstname);
        ed_lastname = (EditText) view.findViewById(R.id.ed_lastname);
        tv_gender = (TextView) view.findViewById(R.id.tv_gender);
        tv_age = (TextView) view.findViewById(R.id.tv_age);
        tv_langauge = (TextView) view.findViewById(R.id.tv_langauge);
        bt_male = (Button) view.findViewById(R.id.gender_male);
        bt_female = (Button) view.findViewById(R.id.gender_female);
        //  bt_genx = (Button) view.findViewById(R.id.genx);
        //  bt_millennials = (Button) view.findViewById(R.id.millenials);
        //  bt_geny = (Button) view.findViewById(R.id.geny);
        // bt_english = (Button) view.findViewById(R.id.english);
        // bt_french = (Button) view.findViewById(R.id.french);
        // bt_spanish = (Button) view.findViewById(R.id.spanish);
        //  bt_british = (Button) view.findViewById(R.id.british);
        bt_next = (Button) view.findViewById(R.id.next);
        constData = ConstantData.getInstance();
        firstname = constData.getFirstname();
        lastname = constData.getLastname();

        ed_firstname.setText(firstname);
        ed_lastname.setText(lastname);
        bt_male.setOnClickListener(this);
        bt_female.setOnClickListener(this);
        //    bt_genx.setOnClickListener(this);
        //    bt_millennials.setOnClickListener(this);
        //   bt_geny.setOnClickListener(this);
        //   bt_english.setOnClickListener(this);
        //   bt_french.setOnClickListener(this);
        //   bt_spanish.setOnClickListener(this);
        //   bt_british.setOnClickListener(this);
        constData.setGender("");
        constData.setLangauge("");
        constData.setAge("");
        layout = (LinearLayout) view.findViewById(R.id.rootage);
        layout1 = (LinearLayout) view.findViewById(R.id.rootlangauge);

    }

}