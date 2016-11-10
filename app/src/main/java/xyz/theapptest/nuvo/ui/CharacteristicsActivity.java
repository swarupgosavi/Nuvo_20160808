package xyz.theapptest.nuvo.ui;

import android.app.Activity;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import xyz.theapptest.nuvo.R;
import xyz.theapptest.nuvo.api.WebApiCall;
import xyz.theapptest.nuvo.utils.ConstantData;

/**
 * Created by trtcpu007 on 26/7/16.
 */

public class CharacteristicsActivity extends Activity implements View.OnClickListener {
    TextView tv_title;
    LinearLayout layout1;
    ConstantData constantData;
    Button btn;
    ArrayList<HashMap<String, String>> characterlist;
    public int selectedCharacteristics = 0;
    ImageView img_back;
    JSONArray age = null;
    JSONArray characteristics = null;
    JSONArray job_category = null;
    JSONArray langaugesd = null;
    JSONArray recording_methods = null;
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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_characteristics);
        init();
        agelist = new ArrayList<HashMap<String, String>>();
        characterlistvalue = new ArrayList<HashMap<String, String>>();
        jobtypevalue = new ArrayList<HashMap<String, String>>();
        langaugevalues = new ArrayList<HashMap<String, String>>();
        recordingmethodvaluesd = new ArrayList<HashMap<String, String>>();
        //  typeInterface();
        getButtonValuecharacteristics();


    }

    private void getButtonValuecharacteristics() {
        try {


            constantData = ConstantData.getInstance();
            characterlist = constantData.getCharacteristics();
            String userId = constantData.getUserid();
            String token = constantData.getTokenid();
            if (characterlist == null) {
                // basicgetauthentication(userId, token);
                return;
            }
            for (int i = 0; i < characterlist.size(); i++) {
                LinearLayout row = new LinearLayout(this);
                row.setOrientation(LinearLayout.VERTICAL);
                row.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));

                for (int j = 0; j < 3; j++) {
                    if (i < characterlist.size()) {
                        btn = new Button(this);
                        btn.setBackgroundResource(R.drawable.button_selected);
                        btn.setTextSize(12);
                        Typeface facetxtsigintextbox = Typeface.createFromAsset(getAssets(),
                                "font/OpenSans-Regular.ttf");
                        btn.setTypeface(facetxtsigintextbox);


                        btn.setId(Integer.parseInt(characterlist.get(i).get("id")) + 30);

                        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                        params.weight = 1.5f;
/*
                    params.gravity = Gravity.LEFT;
                    params.gravity = Gravity.RIGHT;
*/

                        params.gravity = Gravity.CENTER;
                        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
                        // int px1 = Math.round(140 * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
                        //  params.width = px1;
                        int px = Math.round(39 * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
                        params.height = px;
                        btn.setTransformationMethod(null);
                        params.setMargins(0, 25, 0, 10);

                        btn.setPadding(20, 0, 20, 0);
                        btn.setLayoutParams(params);
                        //  btn.setCompoundDrawablePadding(5);
                        //   btn.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                        btn.setText(characterlist.get(i).get("characteristic_name"));
                        btn.setTag(Integer.parseInt(characterlist.get(i).get("characteristics_value")));
                        // btn.setError(String.valueOf(i));

                        final List<Integer> arr = new ArrayList<Integer>();


                        if (characterlist.get(i).get("characteristics_selected").toString().equalsIgnoreCase("1"))
                            btn.setSelected(true);

                        btn.setOnClickListener(new View.OnClickListener() {


                            @Override
                            public void onClick(View v) {
                                int IDS = v.getId() - 30;


                                selectedCharacteristics = selectedCharacteristics ^ (Integer) v.getTag();

                                Log.e("characteristics: ", Integer.toString(selectedCharacteristics));
                                constantData.setCharacteristicvalue(selectedCharacteristics);


                                //  constData.setLvaue(selectedLanguage);
                                Button o = (Button) v;

                                int index = getindex(String.valueOf(IDS));
                                ;

                                if (characterlist.get(index).get("characteristics_selected").toString() == "1") {
                                    // arr.add(IDS);

                                    o.setSelected(false);
                                    if (index > -1) {
                                        HashMap<String, String> charcter = new HashMap<String, String>();
                                        charcter = characterlist.get(index);
                                        charcter.put("characteristics_selected", "0");
                                        characterlist.set(index, charcter);
                                        //   o.setSelected(false);
                                        /*characterlist.remove(index);
                                        characterlist.add(index, charcter);*/
                                    }

                                    //  Toast.makeText(getActivity(), Integer.toString(IDS), Toast.LENGTH_LONG).show();
                                } else {


                                    // int i1=Integer.toBinaryString(int i)
                                    //  langauge = 1;

                                    //  arr.add(IDS);
                                    //    Toast.makeText(getActivity(), Integer.toString(IDS), Toast.LENGTH_LONG).show();
                                    o.setSelected(true);
                                    if (index > -1) {
                                        HashMap<String, String> charcter = new HashMap<String, String>();
                                        charcter = characterlist.get(index);
                                        charcter.put("characteristics_selected", "1");
                                        characterlist.set(index, charcter);
                                        //  o.setSelected(true);
                                        // characterlist.remove(index);
                                        //  characterlist.add(index, charcter);
                                    }

                                }


                            }
                        });
                        btn.setHeight(50);
                        row.addView(btn);
                        i++;
                    }

                }
                i--;

                constantData.setCharacteristics(characterlist);
                layout1.addView(row);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private int getindex(String currentid) {
        int i;

        for (i = 0; i < characterlist.size(); i++) {
            Log.e("CaracterList:", "Current: " + currentid + "-" + "Id: " + characterlist.get(i).get("id").toString() + "-");
            if (characterlist.get(i).get("id").toString().equalsIgnoreCase(currentid))
                return i;
        }
        return -1;
    }

    private void typeInterface() {
        Typeface facetxttitle = Typeface.createFromAsset(getAssets(),
                "font/Vonique 64.ttf");
        // tv_title.setText("Characteristics");
        tv_title.setTypeface(facetxttitle);


    }

    private void init() {

        tv_title = (TextView) findViewById(R.id.toolbar_title);
        layout1 = (LinearLayout) findViewById(R.id.rootcharacteristics);
        layout1.setGravity(Gravity.CENTER);
        layout1.setPadding(0, 30, 0, 0);
        img_back = (ImageView) findViewById(R.id.back);
        img_back.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.back:
                finish();
                break;
        }

    }

    private void basicgetauthentication(String userid, String tokens) {
        try {

            constantData = ConstantData.getInstance();

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

            constantData.setAgelist(agelist);


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
            constantData.setCharacteristics(characterlistvalue);


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
            constantData.setJob_category(jobtypevalue);


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
            constantData.setLanguage(langaugevalues);


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
            constantData.setRecording_methods(recordingmethodvaluesd);

        } catch (Exception e) {
            e.printStackTrace();
        }


    }

}
