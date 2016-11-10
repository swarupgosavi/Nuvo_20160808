package xyz.theapptest.nuvo.fragment;

import android.app.Activity;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import xyz.theapptest.nuvo.R;
import xyz.theapptest.nuvo.api.WebApiCall;
import xyz.theapptest.nuvo.ui.CustomizeDialog;
import xyz.theapptest.nuvo.utils.ConstantData;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by trtcpu007 on 7/7/16.
 */

public class Fragment_Details extends Fragment implements View.OnClickListener {
    EditText ed_description;
    TextView tv_characteristic, tv_jobcategory;
    Button bt_back, bt_next;
    Button bt_attitude, bt_dad, bt_radio, bt_commercial;
    public int characteristic = 0;
    public int jobcategory = 0;
    ConstantData constData;
    CustomizeDialog customizeDialog = null;
    ArrayList<HashMap<String, String>> characterlist;
    ArrayList<HashMap<String, String>> jobcategorylist;

    LinearLayout layout1;
    LinearLayout layout2;
    Button btn;
    Button btn1;
    public int selectedCharacteristics = 0;
    public int selectedJobcategory = 0;
    ArrayList<HashMap<String, String>> agelist;
    ArrayList<HashMap<String, String>> characterlistvalue;
    ArrayList<HashMap<String, String>> jobtypevalue;
    ArrayList<HashMap<String, String>> langaugevalues;
    ArrayList<HashMap<String, String>> recordingmethodvaluesd;

    private static final String TAG_AGE = "age";
    private static final String TAG_CHARACTERISTICS = "characteristics";
    private static final String TAG_JOBCATEGORY = "job_category";
    private static final String TAG_LANGAUGE = "language";
    private static final String TAG_RECORDINGMEHOD = "recording_methods";


    JSONArray age = null;
    JSONArray characteristics = null;
    JSONArray job_category = null;
    JSONArray langaugesd = null;
    JSONArray recording_methods = null;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profiledetails, container, false);
        init(view);
        agelist = new ArrayList<HashMap<String, String>>();
        characterlistvalue = new ArrayList<HashMap<String, String>>();
        jobtypevalue = new ArrayList<HashMap<String, String>>();
        langaugevalues = new ArrayList<HashMap<String, String>>();
        recordingmethodvaluesd = new ArrayList<HashMap<String, String>>();
        constData = ConstantData.getInstance();
        String userId = constData.getUserid();
        String token = constData.getTokenid();
        basicgetauthentication(userId, token);
        typeInterface();
        //
        Onclickevent();
        //  custombuttons();
        getButtonValuecharacteristics();
        getButtonValuejobcategory();

        return view;

    }

    private void getButtonValuejobcategory() {
        try {
        jobcategorylist = constData.getJob_category();

        for (int i = 0; i < jobcategorylist.size(); i++) {
            LinearLayout row = new LinearLayout(getActivity());
            row.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));

            for (int j = 0; j < 3; j++) {
                if (i < jobcategorylist.size()) {
                    btn1 = new Button(getActivity());
                    btn1.setBackgroundResource(R.drawable.button_selected);
                    btn1.setTextSize(12);
                    Typeface facetxtsigintextbox = Typeface.createFromAsset(getActivity().getAssets(),
                            "font/OpenSans-Regular.ttf");
                    btn1.setTypeface(facetxtsigintextbox);

                    btn1.setId(Integer.parseInt(jobcategorylist.get(i).get("id")) + 40);

                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                            ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                    params.weight = 1.5f;
                    params.gravity = Gravity.LEFT;
                    params.gravity = Gravity.RIGHT;

                    DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
                    // int px1 = Math.round(140 * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
                    //  params.width = px1;
                    int px = Math.round(39 * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
                    params.height = px;
                    btn1.setTransformationMethod(null);
                    params.setMargins(5, 5, 15, 2);
                    btn1.setLayoutParams(params);
                    btn1.setPadding(5, 0, 5, 0);
                    //  btn1.setCompoundDrawablePadding(5);
                    //   btn.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                    btn1.setText(jobcategorylist.get(i).get("category_name"));
                    btn1.setTag(Integer.parseInt(jobcategorylist.get(i).get("category_value")));
                    final List<Integer> arr = new ArrayList<Integer>();
                    btn1.setOnClickListener(new View.OnClickListener() {


                        @Override
                        public void onClick(View v) {
                            int IDS = v.getId() - 40;


                            selectedJobcategory = selectedJobcategory ^ (Integer) v.getTag();

                            Log.e("jobcategory: ", Integer.toString(selectedJobcategory));
                            //  constData.setLvaue(selectedLanguage);
                            Button o = (Button) v;
                            if (o.isSelected()) {
                                // arr.add(IDS);

                                o.setSelected(false);

                                //  Toast.makeText(getActivity(), Integer.toString(IDS), Toast.LENGTH_LONG).show();
                            } else {


                                // int i1=Integer.toBinaryString(int i)
                                //  langauge = 1;

                                //  arr.add(IDS);
                                //   Toast.makeText(getActivity(), Integer.toString(IDS), Toast.LENGTH_LONG).show();
                                o.setSelected(true);
                            }


                        }
                    });

                    row.addView(btn1);
                    i++;
                }
            }
            i--;
            layout2.addView(row);
        }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void getButtonValuecharacteristics() {
        try {


            constData = ConstantData.getInstance();
            characterlist = constData.getCharacteristics();

            if (characterlist == null) {

            }
            for (int i = 0; i < characterlist.size(); i++) {
                LinearLayout row = new LinearLayout(getActivity());
                row.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));

                for (int j = 0; j < 3; j++) {
                    if (i < characterlist.size()) {
                        btn = new Button(getActivity());
                        btn.setBackgroundResource(R.drawable.button_selected);
                        btn.setTextSize(12);
                        Typeface facetxtsigintextbox = Typeface.createFromAsset(getActivity().getAssets(),
                                "font/OpenSans-Regular.ttf");
                        btn.setTypeface(facetxtsigintextbox);


                        btn.setId(Integer.parseInt(characterlist.get(i).get("id")) + 30);

                        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                        params.weight = 1.5f;
                        params.gravity = Gravity.LEFT;
                        params.gravity = Gravity.RIGHT;

                        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
                        // int px1 = Math.round(140 * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
                        //  params.width = px1;
                        int px = Math.round(39 * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
                        params.height = px;
                        btn.setTransformationMethod(null);
                        params.setMargins(5, 5, 15, 10);
                        btn.setPadding(8, 0, 8, 0);
                        btn.setLayoutParams(params);
                        //  btn.setCompoundDrawablePadding(5);
                        //   btn.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                        btn.setText(characterlist.get(i).get("characteristic_name"));
                        btn.setTag(Integer.parseInt(characterlist.get(i).get("characteristics_value")));
                        final List<Integer> arr = new ArrayList<Integer>();
                        btn.setOnClickListener(new View.OnClickListener() {


                            @Override
                            public void onClick(View v) {
                                int IDS = v.getId() - 30;


                                selectedCharacteristics = selectedCharacteristics ^ (Integer) v.getTag();

                                Log.e("characteristics: ", Integer.toString(selectedCharacteristics));
                                //  constData.setLvaue(selectedLanguage);
                                Button o = (Button) v;
                                if (o.isSelected()) {
                                    // arr.add(IDS);

                                    o.setSelected(false);

                                    //  Toast.makeText(getActivity(), Integer.toString(IDS), Toast.LENGTH_LONG).show();
                                } else {


                                    // int i1=Integer.toBinaryString(int i)
                                    //  langauge = 1;

                                    //  arr.add(IDS);
                                    //    Toast.makeText(getActivity(), Integer.toString(IDS), Toast.LENGTH_LONG).show();
                                    o.setSelected(true);
                                }


                            }
                        });
                        btn.setHeight(50);
                        row.addView(btn);
                        i++;
                    }

                }
                i--;

                layout1.addView(row);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void custombuttons() {


        constData = ConstantData.getInstance();
        characterlist = constData.getCharacteristics();
        for (int i = 0; i < characterlist.size(); i++) {


            btn1 = new Button(getActivity());
            btn1.setBackgroundResource(R.drawable.button_selected);
            btn1.setTextSize(12);

            Typeface facetxtsigintextbox = Typeface.createFromAsset(getActivity().getAssets(),
                    "font/OpenSans-Regular.ttf");
            btn1.setTypeface(facetxtsigintextbox);

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

            btn1.setId(Integer.parseInt(characterlist.get(i).get("id")) + 30);

            //  btn1.setMinHeight(0);

            params.weight = 1.5f;
            params.gravity = Gravity.LEFT;
            params.gravity = Gravity.RIGHT;

            DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
            // int px1 = Math.round(140 * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
            //  params.width = px1;
            int px = Math.round(40 * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
            params.height = px;
            btn1.setTransformationMethod(null);

            params.setMargins(5, 10, 5, 2);
            btn1.setLayoutParams(params);
            //  params.setMargins(2, 0, 2, 0);
            //   btn.setLayoutParams(new LinearLayout.LayoutParams(10, 100));
            //   btn1.setBackgroundResource(R.drawable.button_selected);
            btn1.setTextColor(getActivity().getApplication().getResources().getColor(R.color.white));
            btn1.setText(characterlist.get(i).get("characteristic_name"));
            btn1.setTag(Integer.parseInt(characterlist.get(i).get("characteristics_value")));
            // System.out.println(characterlist.get(64).get(TAG_NAME));
            final List<Integer> arr = new ArrayList<Integer>();
            btn1.setOnClickListener(new View.OnClickListener() {


                @Override
                public void onClick(View v) {
                    int IDS = v.getId() - 30;


                    selectedCharacteristics = selectedCharacteristics ^ (Integer) v.getTag();

                    Log.e("characteristics: ", Integer.toString(selectedCharacteristics));

                    //  constData.setLvaue(selectedLanguage);
                    Button o = (Button) v;
                    if (o.isSelected()) {
                        // arr.add(IDS);

                        o.setSelected(false);

                        //  Toast.makeText(getActivity(), Integer.toString(IDS), Toast.LENGTH_LONG).show();
                    } else {


                        // int i1=Integer.toBinaryString(int i)
                        //  langauge = 1;

                        //  arr.add(IDS);
                        //   Toast.makeText(getActivity(), Integer.toString(IDS), Toast.LENGTH_LONG).show();
                        o.setSelected(true);
                    }


                }
            });

            layout1.addView(btn1, params);


        }


        constData = ConstantData.getInstance();
        jobcategorylist = constData.getJob_category();
        for (int i = 0; i < jobcategorylist.size(); i++) {


            btn = new Button(getActivity());
            btn.setBackgroundResource(R.drawable.button_selected);
            btn.setTextSize(12);

            Typeface facetxtsigintextbox = Typeface.createFromAsset(getActivity().getAssets(),
                    "font/OpenSans-Regular.ttf");
            btn.setTypeface(facetxtsigintextbox);


            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

            btn.setId(Integer.parseInt(jobcategorylist.get(i).get("id")) + 40);

            //    btn.setMinHeight(0);

            params.weight = 1.5f;
            params.gravity = Gravity.LEFT;
            params.gravity = Gravity.RIGHT;

            DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
            // int px1 = Math.round(140 * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
            //  params.width = px1;
            int px = Math.round(40 * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
            params.height = px;

            params.setMargins(5, 10, 5, 2);
            btn.setLayoutParams(params);
            //   btn.setLayoutParams(new LinearLayout.LayoutParams(10, 100));
            //   btn1.setBackgroundResource(R.drawable.button_selected);
            btn.setTextColor(getActivity().getApplication().getResources().getColor(R.color.white));
            btn.setText(jobcategorylist.get(i).get("category_name"));
            btn.setTag(Integer.parseInt(jobcategorylist.get(i).get("category_value")));
            // System.out.println(jobcategorylist.get(1).get(TAG_NAME));
            final List<Integer> arr = new ArrayList<Integer>();
            btn.setOnClickListener(new View.OnClickListener() {


                @Override
                public void onClick(View v) {
                    int IDS = v.getId() - 40;


                    selectedJobcategory = selectedJobcategory ^ (Integer) v.getTag();

                    Log.e("characteristics: ", Integer.toString(selectedJobcategory));

                    //  constData.setLvaue(selectedLanguage);
                    Button o = (Button) v;
                    if (o.isSelected()) {
                        // arr.add(IDS);

                        o.setSelected(false);

                        //  Toast.makeText(getActivity(), Integer.toString(IDS), Toast.LENGTH_LONG).show();
                    } else {


                        // int i1=Integer.toBinaryString(int i)
                        //  langauge = 1;

                        //  arr.add(IDS);
                        //  Toast.makeText(getActivity(), Integer.toString(IDS), Toast.LENGTH_LONG).show();
                        o.setSelected(true);
                    }


                }
            });

            layout2.addView(btn, params);
        }


    }

    private void Onclickevent() {
        bt_next.setOnClickListener(this);
        bt_back.setOnClickListener(this);

    }


    @Override
    public void onClick(View v) {

        switch (v.getId())

        {
            case R.id.back:
                someEventListener.someEvent(0);
                break;
            case R.id.next:
                if (ed_description.getText().toString().matches("")) {
                    customizeDialog = new CustomizeDialog(getActivity());
                    customizeDialog.setTitle("nuvo");
                    customizeDialog.setCancelable(false);
                    customizeDialog.setMessage("Please enter description.");
                    customizeDialog.show();
                    //   DialogShowMethods.showDialog(getActivity(), "Error", "Please enter description.");
                } else {
                    constData = ConstantData.getInstance();
                    constData.setDescription(ed_description.getText().toString().trim());
                    if ((selectedCharacteristics > 0) && (selectedJobcategory > 0)) {
                        constData.setCharacteristicvalue(selectedCharacteristics);
                        constData.setJobcategoryvalue(selectedJobcategory);
                        someEventListener.someEvent(2);
                    } else {
                        if (selectedCharacteristics == 0) {
                            customizeDialog = new CustomizeDialog(getActivity());
                            customizeDialog.setTitle("nuvo");
                            customizeDialog.setCancelable(false);
                            customizeDialog.setMessage("Please Select Characteristics.");
                            customizeDialog.show();
                        } else {
                            if (selectedJobcategory == 0) {
                                customizeDialog = new CustomizeDialog(getActivity());
                                customizeDialog.setTitle("nuvo");
                                customizeDialog.setCancelable(false);
                                customizeDialog.setMessage("Please Select Job Category.");
                                customizeDialog.show();
                            }
                        }

                        // }
                        // }
                    }
                }
                break;

        }

    }

    public interface onSomeEventListener {
        public void someEvent(int s);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            someEventListener = (Fragment_CreateProfile.onSomeEventListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must implement onSomeEventListener");
        }
    }

    Fragment_CreateProfile.onSomeEventListener someEventListener;

    private void typeInterface() {
        Typeface facetxtsigintextbox = Typeface.createFromAsset(getActivity().getAssets(),
                "font/OpenSans-Regular.ttf");

        ed_description.setTypeface(facetxtsigintextbox);


        Typeface facetxtsigin = Typeface.createFromAsset(getActivity().getAssets(),
                "font/OpenSans-Semibold.ttf");
        tv_characteristic.setTypeface(facetxtsigin);
        tv_jobcategory.setTypeface(facetxtsigin);
        bt_next.setTypeface(facetxtsigintextbox);
        bt_back.setTypeface(facetxtsigintextbox);


    }

    private void init(View view) {
        ed_description = (EditText) view.findViewById(R.id.ed_description);
        /*ed_description.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(MotionEvent.ACTION_UP == event.getAction()) {
                   ed_description.setText("Write a short bio about yourself in 350 Characters or less. This is a good place to also note any conflicts");
                }
                return true; // return is important...
            }
        });*/
        tv_characteristic = (TextView) view.findViewById(R.id.tv_characteristics);
        tv_jobcategory = (TextView) view.findViewById(R.id.tv_jobcategory);
        bt_back = (Button) view.findViewById(R.id.back);
        bt_next = (Button) view.findViewById(R.id.next);
        //  bt_attitude = (Button) view.findViewById(R.id.btattitudes);
        //  bt_dad = (Button) view.findViewById(R.id.btdads);
        //   bt_commercial = (Button) view.findViewById(R.id.btdad);
        //   bt_radio = (Button) view.findViewById(R.id.btradio);
        layout2 = (LinearLayout) view.findViewById(R.id.rootjobcategory);
        layout1 = (LinearLayout) view.findViewById(R.id.rootcharacteristics);

    }


    private void basicgetauthentication(String userid, String tokens) {
        try {

            constData = ConstantData.getInstance();

            String url = WebApiCall.baseURl + "default";

            URL obj = new URL(url);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();
            String encoded = Base64.encodeToString((userid + ":" + tokens).getBytes("UTF-8"), Base64.NO_WRAP);
            //     String encoded = Base64.encodeToString(userid+":"+tokens).getBytes();
            con.setRequestProperty("Authorization", "Basic " + encoded);
            // optional default is GET
            con.setRequestMethod("GET");

            //add request header


            int responseCode = con.getResponseCode();
            System.out.println("\nSending 'GET' request to URL : " + url);
            System.out.println("Response Code : " + responseCode);

            BufferedReader in = new BufferedReader(
                    new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            //print result
            System.out.println(response.toString());
            Log.e("GETresonse", response.toString());
            JSONObject jsonObj = new JSONObject(response.toString());
            //  Button myButton = new Button(this);
            //LinearLayout layout = (LinearLayout) findViewById(xyz.theapptest.nuvo.R.id.toplv);
            // Button[] buttons = new Button[agelist.size()];
            age = jsonObj.getJSONArray(TAG_AGE);
            for (int i = 0; i < age.length(); i++) {
                JSONObject c = age.getJSONObject(i);
                String id = c.getString("id");

                String agetype = c.getString("age_type");
                String displayorder = c.getString("display_order");
                String agevale = c.getString("age_value");
                Log.e("id", id);
                Log.e("agetype", agetype);
                Log.e("displayorder", displayorder);
                Log.e("agevale", agevale);

                HashMap<String, String> ages = new HashMap<String, String>();
                ages.put("id", id);
                ages.put("age_type", agetype);
                ages.put("display_order", displayorder);
                ages.put("age_value", agevale);
                ages.put("age_selected", "0");
                agelist.add(ages);


            }

  /*          Set<HashMap<String, String>> set = new HashSet<HashMap<String, String>>();
            set.addAll(agelist);
//            SharedPreferences preferences = getSharedPreferences("UserType", Context.MODE_PRIVATE);
//            SharedPreferences.Editor editor = preferences.edit();
            editor.putString("usertype", "Agent");
            editor.putStringSet("key", set);
            editor.commit();*/

            constData.setAgelist(agelist);


            characteristics = jsonObj.getJSONArray(TAG_CHARACTERISTICS);
            for (int i = 0; i < characteristics.length(); i++) {
                JSONObject c = characteristics.getJSONObject(i);
                String id = c.getString("id");
                String characteristicname = c.getString("characteristic_name");
                String displayorder = c.getString("display_order");
                String charcteristicvalue = c.getString("characteristics_value");
                HashMap<String, String> charcter = new HashMap<String, String>();
                charcter.put("id", id);
                charcter.put("characteristic_name", characteristicname);
                charcter.put("display_order", displayorder);
                charcter.put("characteristics_value", charcteristicvalue);
                charcter.put("characteristics_selected", "0");
                Log.e("......", "..........................................");
                Log.e("id", id);
                Log.e("characteristic_name", characteristicname);
                Log.e("characterics_value_json", charcteristicvalue);
                Log.e("......", "..........................................");
                characterlistvalue.add(charcter);


            }
            constData.setCharacteristics(characterlistvalue);


            job_category = jsonObj.getJSONArray(TAG_JOBCATEGORY);
            for (int i = 0; i < job_category.length(); i++) {
                JSONObject c = job_category.getJSONObject(i);
                String id = c.getString("id");
                String categoryname = c.getString("category_name");
                String displayorder = c.getString("display_order");
                String categoryvalue = c.getString("category_value");
                HashMap<String, String> job = new HashMap<String, String>();
                job.put("id", id);
                job.put("category_name", categoryname);
                job.put("display_order", displayorder);
                job.put("category_value", categoryvalue);
                Log.e("category_value", categoryvalue);
                job.put("jobcategory_selected", "0");
                jobtypevalue.add(job);
                Log.e("id", id);
                Log.e("categoryname", categoryname);
                Log.e("displayorder", displayorder);
                Log.e("categoryvalue", categoryvalue);


            }
            constData.setJob_category(jobtypevalue);


            langaugesd = jsonObj.getJSONArray(TAG_LANGAUGE);
            for (int i = 0; i < langaugesd.length(); i++) {
                JSONObject c = langaugesd.getJSONObject(i);
                String id = c.getString("id");
                String langaugename = c.getString("language_name");
                String displayorder = c.getString("display_order");
                String langaugevalue = c.getString("language_value");
                HashMap<String, String> lan = new HashMap<String, String>();
                lan.put("id", id);
                lan.put("language_name", langaugename);
                lan.put("display_order", displayorder);
                lan.put("language_value", langaugevalue);
                lan.put("language_selected", "0");
                Log.e("language_value", langaugevalue);
                langaugevalues.add(lan);


                Log.e("id", id);
                Log.e("langaugename", langaugename);
                Log.e("displayorder", displayorder);
                Log.e("langaugevalue", langaugevalue);

            }
            constData.setLanguage(langaugevalues);


            recording_methods = jsonObj.getJSONArray(TAG_RECORDINGMEHOD);
            for (int i = 0; i < recording_methods.length(); i++) {
                JSONObject c = recording_methods.getJSONObject(i);
                String id = c.getString("id");
                String recordingmethods = c.getString("recording_method");
                String displayorder = c.getString("display_method");
                String recordingmethodvalue = c.getString("recording_method_value");
                HashMap<String, String> recor = new HashMap<String, String>();
                recor.put("id", id);
                recor.put("recording_method", recordingmethods);
                recor.put("display_order", displayorder);
                recor.put("recording_method_value", recordingmethodvalue);
                Log.e("recording_method_value", recordingmethodvalue);
                recordingmethodvaluesd.add(recor);


                Log.e("id", id);
                Log.e("recordingmethods", recordingmethods);
                Log.e("displayorder", displayorder);
                Log.e("recordingmethods", recordingmethods);

            }
            constData.setRecording_methods(recordingmethodvaluesd);

        } catch (Exception e) {
            e.printStackTrace();
        }


    }

}

