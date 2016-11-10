
package xyz.theapptest.nuvo.fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import org.json.JSONArray;
import org.json.JSONObject;

import xyz.theapptest.nuvo.R;
import xyz.theapptest.nuvo.adapter.Criteriaadapter;
import xyz.theapptest.nuvo.adapter.FindListadapter;
import xyz.theapptest.nuvo.adapter.Gridadapter;
import xyz.theapptest.nuvo.adapter.SendjobClickEvent;
import xyz.theapptest.nuvo.agent.ArtistDetailsForAgentFragment;
import xyz.theapptest.nuvo.api.WebApiCall;
import xyz.theapptest.nuvo.pojo.AgentArtistListItemPojo;
import xyz.theapptest.nuvo.ui.CharacteristicsActivity;
import xyz.theapptest.nuvo.ui.CustomizeDialog;
import xyz.theapptest.nuvo.ui.ProducerHomeScreen;
import xyz.theapptest.nuvo.ui.Radiobuttondisplayactivity;
import xyz.theapptest.nuvo.ui.SignInActivity;
import xyz.theapptest.nuvo.ui.SplashActivity;
import xyz.theapptest.nuvo.utils.ConstantData;
import xyz.theapptest.nuvo.utils.Constants;
import xyz.theapptest.nuvo.utils.FindJobs;
import xyz.theapptest.nuvo.utils.ListsongplayedInterface;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by trtcpu007 on 15/7/16.
 */

public class Fragment_Criteria extends Fragment implements SendjobClickEvent {

    //ListView listView;
    TextView tv_criteria;
    Criteriaadapter listAdapter;
    Typeface openSansLight;
    String sOpenSansLight;
    LinearLayout layHome, laySearch;
    ArrayList<HashMap<String, String>> characterlist;
    //SL
    LinearLayout laybuttons, laygender, laycharacteristics, layjobtype, layage, laylanguage, layagebuttons, laylanguagebuttons, layjobbuttons, laycharbuttons, laygenderbuttons;

    ImageView imggender, imggender0, imggender1, imggender2, imggender3, imggender4;
    ImageView imgcharacteristic, imgcharacteristic0, imgcharacteristic1, imgcharacteristic2, imgcharacteristic3, imgcharacteristic4;
    ImageView imgjob, imgjob0, imgjob1, imgjob2, imgjob3, imgjob4;
    ImageView imgage, imgage0, imgage1, imgage2, imgage3, imgage4;
    ImageView imglanguage, imglanguage0, imglanguage1, imglanguage2, imglanguage3, imglanguage4;
    int FilterOpen = -1;

    Button btn_age;
    //SL
    ConstantData constantData;
    GridView gv;
    public int age = -1;
    Context context;
    ArrayList prgmName;
    public static String[] prgmNameList = {"Gender", "Characteristics", "Job Type", "Age", "Language", ""};
    public static int[] prgmImages = {R.drawable.gender_ic, R.drawable.charact_type_ic, R.drawable.job_type_ic, R.drawable.age_icon, R.drawable.language_ic};
    ConstantData constData;
    Context thiscontext;
    ArrayList<HashMap<String, String>> jobcategorylist;
    Button btn1;
    public int selectedJobcategory = 0;

    private LinearLayout lvroot;

    ArrayList<String> selectedartist;
    ArrayList<HashMap<String, String>> langaugelist;
    ArrayList<HashMap<String, String>> genderList = new ArrayList<>();
    Button btnage;
    Button btncharacteristics;
    Button btnlanguage;
    Button btnGender;
    public int selectedCharacteristics = 0;
    RadioButton rb;
    public int selectedLanguage = 0;
    public int selectedGender = 0;
    RadioGroup radioSexGroup;
    RadioButton radioSexButton;
    ArrayList<FindJobs> artistlist;
    FindListadapter adapter;
    ListView listview;
    LinearLayout lv_age_filter;
    private PopupWindow pwindo_filter;
    CustomizeDialog customizeDialog = null;
    MediaPlayer mMediapler;

    android.support.v7.widget.Toolbar toolbar1;
    TextView txt1, txtsendjob, txt2, tv_title;
    Button img_bell, imgleft;


    ArrayList<HashMap<String, String>> agelist;
    ArrayList<HashMap<String, String>> characterlistvalue;
    ArrayList<HashMap<String, String>> jobtypevalue;
    ArrayList<HashMap<String, String>> langaugevalues;
    ArrayList<HashMap<String, String>> recordingmethodvaluesd;
    ArrayList<HashMap<String, String>> gendervalue;

    private static final String TAG_AGE = "age";
    private static final String TAG_CHARACTERISTICS = "characteristics";
    private static final String TAG_JOBCATEGORY = "job_category";
    private static final String TAG_LANGAUGE = "language";
    private static final String TAG_RECORDINGMEHOD = "recording_methods";


    JSONArray agearray = null;
    JSONArray characteristics = null;
    JSONArray job_category = null;
    JSONArray langaugesd = null;
    JSONArray recording_methods = null;
    Button btnMale, btnFemale;
    Button showResultGenderBtn, showResultCharBtn, showResultJobCatBtn, showResultAgeBtn, showResultLangBtn;
    PopupWindow pwindo;
    // boolean isFindArtist = false;
    Context mContext;
    public int popupvisible = 0;
    boolean isMaleSelectede = false;

   /* SharedPreferences nuvoPreferences;
    SharedPreferences.Editor nuvoEditor;
    String loginEmail = "";
    String loginPassword = "";
*/

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_criteria, null);

        try {


            constData = ConstantData.getInstance();
            init(rootView);
            //setNuvoSharedPref();

            thiscontext = container.getContext();
            if (constData.isFindArtist()) {
                layHome.setVisibility(LinearLayout.GONE);
                laySearch.setVisibility(LinearLayout.VISIBLE);
                imgleft = (Button) toolbar1.findViewById(R.id.btnleft);
                imgleft.setVisibility(View.GONE);
                ProducerHomeScreen act = (ProducerHomeScreen) getActivity();
                toolbar1 = (android.support.v7.widget.Toolbar) act.findViewById(R.id.toolbar_top);
                toolbar1.setNavigationIcon(R.drawable.menu_icon);
                tv_title = (TextView) toolbar1.findViewById(R.id.toolbar_title);
                tv_title.setText("Find");
                findaritst();
            } else {
                gv.setAdapter(new Gridadapter(thiscontext, prgmNameList, prgmImages));

            }

            selectedartist = new ArrayList<String>();

            constData.setselectedArtist(selectedartist);


            txtsendjob = (TextView) toolbar1.findViewById(R.id.rightlabel);

            txtsendjob.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    constData = ConstantData.getInstance();
                    Log.e("Artist Selected:", Integer.toString(selectedartist.size()));
                    constData.setselectedArtist(selectedartist);
                    Log.e("Artist Selected:", constData.getSeletedArtists());

//                TabFragment main = (TabFragment) getParentFragment();
//
//                main.SetAuditiontab();

                    constData.setSource_flag("Find");
                    constData.setAudition_flag("audition");
                    lvroot.removeAllViewsInLayout();
                    Fragment newFragment = new FragmentAuditionCreation();
                    FragmentTransaction transaction = getFragmentManager().beginTransaction();

                    transaction.replace(R.id.lvproducerfind, newFragment);
                    transaction.addToBackStack(null);
                    transaction.commitAllowingStateLoss();
                }
            });

            agelist = new ArrayList<HashMap<String, String>>();
            characterlistvalue = new ArrayList<HashMap<String, String>>();
            jobtypevalue = new ArrayList<HashMap<String, String>>();
            langaugevalues = new ArrayList<HashMap<String, String>>();
            recordingmethodvaluesd = new ArrayList<HashMap<String, String>>();
            gendervalue = new ArrayList<HashMap<String, String>>();


            showResultGenderBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    hideall();
                    laybuttons.setVisibility(View.VISIBLE);
                    findartistsub(0);

                }
            });

            showResultAgeBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    hideall();
                    laybuttons.setVisibility(View.VISIBLE);
                    findartistsub(0);
                }
            });
            showResultCharBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    hideall();
                    laybuttons.setVisibility(View.VISIBLE);
                    findartistsub(0);
                }
            });
            showResultJobCatBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    hideall();
                    laybuttons.setVisibility(View.VISIBLE);
                    findartistsub(0);
                }
            });
            showResultLangBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    hideall();
                    laybuttons.setVisibility(View.VISIBLE);
                    findartistsub(0);
                }
            });
            //SL

            imggender.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    openpanel(0);
                }
            });
            imggender0.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    openpanel(0);
                }
            });
            imggender1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    openpanel(0);
                }
            });
            imggender2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    openpanel(0);
                }
            });
            imggender3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    openpanel(0);
                }
            });
            imggender4.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    openpanel(0);
                }
            });

            imgcharacteristic.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    openpanel(1);
                }
            });
            imgcharacteristic0.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    openpanel(1);
                }
            });
            imgcharacteristic1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    openpanel(1);
                }
            });
            imgcharacteristic2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    openpanel(1);
                }
            });
            imgcharacteristic3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    openpanel(1);
                }
            });
            imgcharacteristic4.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    openpanel(1);
                }
            });

            imgjob.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    openpanel(2);
                }
            });
            imgjob0.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    openpanel(2);
                }
            });
            imgjob1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    openpanel(2);
                }
            });
            imgjob2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    openpanel(2);
                }
            });
            imgjob3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    openpanel(2);
                }
            });
            imgjob4.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    openpanel(2);
                }
            });

            imgage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    openpanel(3);
                }
            });
            imgage0.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    openpanel(3);
                }
            });
            imgage1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    openpanel(3);
                }
            });
            imgage2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    openpanel(3);
                }
            });
            imgage3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    openpanel(3);
                }
            });
            imgage4.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    openpanel(3);
                }
            });

            imglanguage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    openpanel(4);
                }
            });
            imglanguage0.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    openpanel(4);
                }
            });
            imglanguage1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    openpanel(4);
                }
            });
            imglanguage2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    openpanel(4);
                }
            });
            imglanguage3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    openpanel(4);
                }
            });
            imglanguage4.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    openpanel(4);
                }
            });


            SharedPreferences shf = getActivity().getSharedPreferences("UserType", Context.MODE_PRIVATE);
            String usertype = shf.getString("usertype", null);
            String userid = shf.getString("userid", null);
            String token = shf.getString("token", null);

            GetInitialDataFromAPI(userid, token);


            //SL

            Typeface facetxttitle = Typeface.createFromAsset(getActivity().getAssets(),
                    "font/Vonique 64.ttf");
            txt1.setText("nuvo");
            txt1.setTypeface(facetxttitle);

            // lv_age_filter = (LinearLayout) rootView.findViewById(R.id.age_view);
            Typeface facetxtchoose = Typeface.createFromAsset(getActivity().getAssets(),
                    "font/OpenSans-Light.ttf");
            tv_criteria.setTypeface(facetxtchoose);
            gv.setVerticalScrollBarEnabled(false);


            gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                    if (popupvisible == 0) {
                        popupvisible = 1;
                        switch (i) {
                            case 0:
                                initategender();

                                break;
                            case 1:
                                popupvisible = 0;
                                Intent i2 = new Intent(getActivity(), CharacteristicsActivity.class);
                                startActivity(i2);
                                break;
                            case 2:
                                initiatePopupWindowJobCategory();
                                break;
                            case 3:
                                initiatepopupage();
                                break;
                            case 4:
                                initiateLangauge();
                                break;
                            case 5:
                                popupvisible = 0;
                                findaritst();
                                break;


                        }
                    }

                }


            });

        } catch (Exception e) {
            e.printStackTrace();
        }


        return rootView;
    }


    private void init(View rootView) {

        lvroot = (LinearLayout) rootView.findViewById(R.id.lvproducerfind);

        gv = (GridView) rootView.findViewById(R.id.grid_view);
        listview = (ListView) rootView.findViewById(R.id.list_result);
        layHome = (LinearLayout) rootView.findViewById(R.id.layhome);
        laybuttons = (LinearLayout) rootView.findViewById(R.id.laybuttons);
        laySearch = (LinearLayout) rootView.findViewById(R.id.laysearch);
        showResultGenderBtn = (Button) rootView.findViewById(R.id.showresultgender);
        showResultAgeBtn = (Button) rootView.findViewById(R.id.showresultage);
        showResultCharBtn = (Button) rootView.findViewById(R.id.showresultcharacteristics);
        showResultJobCatBtn = (Button) rootView.findViewById(R.id.showresultjob);
        showResultLangBtn = (Button) rootView.findViewById(R.id.showresultlangauge);
        laygender = (LinearLayout) rootView.findViewById(R.id.laygender);
        laycharacteristics = (LinearLayout) rootView.findViewById(R.id.laycharacteristics);
        layjobtype = (LinearLayout) rootView.findViewById(R.id.layjobtype);
        layage = (LinearLayout) rootView.findViewById(R.id.layage);
        laylanguage = (LinearLayout) rootView.findViewById(R.id.laylanguage);

        layagebuttons = (LinearLayout) rootView.findViewById(R.id.layagebuttons);
        laylanguagebuttons = (LinearLayout) rootView.findViewById(R.id.laylanguagebuttons);
        layjobbuttons = (LinearLayout) rootView.findViewById(R.id.layjobbuttons);
        laycharbuttons = (LinearLayout) rootView.findViewById(R.id.laycharbuttons);
        laygenderbuttons = (LinearLayout) rootView.findViewById(R.id.laygenderbuttons);

        imggender = (ImageView) rootView.findViewById(R.id.imggender);
        imgcharacteristic = (ImageView) rootView.findViewById(R.id.imgcharacteristic);
        imgjob = (ImageView) rootView.findViewById(R.id.imgjob);
        imgage = (ImageView) rootView.findViewById(R.id.imgage);
        imglanguage = (ImageView) rootView.findViewById(R.id.imglanguage);

        imggender0 = (ImageView) rootView.findViewById(R.id.imggender0);
        imgcharacteristic0 = (ImageView) rootView.findViewById(R.id.imgcharacteristic0);
        imgjob0 = (ImageView) rootView.findViewById(R.id.imgjob0);
        imgage0 = (ImageView) rootView.findViewById(R.id.imgage0);
        imglanguage0 = (ImageView) rootView.findViewById(R.id.imglanguage0);

        imggender1 = (ImageView) rootView.findViewById(R.id.imggender1);
        imgcharacteristic1 = (ImageView) rootView.findViewById(R.id.imgcharacteristic1);
        imgjob1 = (ImageView) rootView.findViewById(R.id.imgjob1);
        imgage1 = (ImageView) rootView.findViewById(R.id.imgage1);
        imglanguage1 = (ImageView) rootView.findViewById(R.id.imglanguage1);

        imggender2 = (ImageView) rootView.findViewById(R.id.imggender2);
        imgcharacteristic2 = (ImageView) rootView.findViewById(R.id.imgcharacteristic2);
        imgjob2 = (ImageView) rootView.findViewById(R.id.imgjob2);
        imgage2 = (ImageView) rootView.findViewById(R.id.imgage2);
        imglanguage2 = (ImageView) rootView.findViewById(R.id.imglanguage2);

        imggender3 = (ImageView) rootView.findViewById(R.id.imggender3);
        imgcharacteristic3 = (ImageView) rootView.findViewById(R.id.imgcharacteristic3);
        imgjob3 = (ImageView) rootView.findViewById(R.id.imgjob3);
        imgage3 = (ImageView) rootView.findViewById(R.id.imgage3);
        imglanguage3 = (ImageView) rootView.findViewById(R.id.imglanguage3);

        imggender4 = (ImageView) rootView.findViewById(R.id.imggender4);
        imgcharacteristic4 = (ImageView) rootView.findViewById(R.id.imgcharacteristic4);
        imgjob4 = (ImageView) rootView.findViewById(R.id.imgjob4);
        imgage4 = (ImageView) rootView.findViewById(R.id.imgage4);
        imglanguage4 = (ImageView) rootView.findViewById(R.id.imglanguage4);
        tv_criteria = (TextView) rootView.findViewById(R.id.choosecriteria);
        toolbar1 = (android.support.v7.widget.Toolbar) getActivity().findViewById(R.id.toolbar_top);
        txt1 = (TextView) toolbar1.findViewById(R.id.toolbar_title);
        txt2 = (TextView) toolbar1.findViewById(R.id.rightlabel);
        txt2.setVisibility(View.GONE);


    }

   /* private void setNuvoSharedPref() {

        nuvoPreferences = mContext.getSharedPreferences(Constants.NUVOPREF, Context.MODE_PRIVATE);
        nuvoEditor = nuvoPreferences.edit();
        loginEmail = nuvoPreferences.getString(Constants.Key_email, "");
        loginPassword = nuvoPreferences.getString(Constants.Key_Password, "");
    }*/


    private void GetInitialDataFromAPI(String userid, String tokens) {
        try {

            constantData = ConstantData.getInstance();
            //String url = "http://nuvo.theapptest.xyz/v1/api/default";
            String url = WebApiCall.baseURl + "default";

            if (genderList.size() == 0) {
                String gid = "14";
                String gendertype = "Male";
                String gendervalue = "14";
                HashMap<String, String> gender = new HashMap<String, String>();
                gender.put("id", gid);
                gender.put("gender", gendertype);
                gender.put("gender_value", gendervalue);
                gender.put("gender_selected", "0");
                genderList.add(gender);

                gid = "15";
                gendertype = "Female";
                gendervalue = "15";
                gender = new HashMap<String, String>();
                gender.put("id", gid);
                gender.put("gender", gendertype);
                gender.put("gender_value", gendervalue);
                gender.put("gender_selected", "0");
                genderList.add(gender);
                constantData.setgenders(genderList);

            }


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
            //System.out.println(response.toString());
            //Log.e("GETresonse", response.toString());
            JSONObject jsonObj = new JSONObject(response.toString());
            //  Button myButton = new Button(this);
            //LinearLayout layout = (LinearLayout) findViewById(xyz.theapptest.nuvo.R.id.toplv);
            // Button[] buttons = new Button[agelist.size()];
            agearray = jsonObj.getJSONArray(TAG_AGE);
            for (int i = 0; i < agearray.length(); i++) {
                JSONObject c = agearray.getJSONObject(i);
                String id = c.getString("id");

                String agetype = c.getString("age_type");
                String displayorder = c.getString("display_order");
                String agevale = c.getString("age_value");
//                Log.e("id", id);
//                Log.e("agetype", agetype);
//                Log.e("displayorder", displayorder);
//                Log.e("agevale", agevale);

                HashMap<String, String> ages = new HashMap<String, String>();
                ages.put("id", id);
                ages.put("age_type", agetype);
                ages.put("display_order", displayorder);
                ages.put("age_value", agevale);
                ages.put("age_selected", "0");
                agelist.add(ages);


            }

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
//                Log.e("......", "..........................................");
//                Log.e("id", id);
//                Log.e("characteristic_name", characteristicname);
//                Log.e("characterics_value_json", charcteristicvalue);
//                Log.e("......", "..........................................");
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
                // Log.e("category_value", categoryvalue);
                job.put("jobcategory_selected", "0");
                jobtypevalue.add(job);
//                Log.e("id", id);
//                Log.e("categoryname", categoryname);
//                Log.e("displayorder", displayorder);
//                Log.e("categoryvalue", categoryvalue);


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
                //Log.e("language_value", langaugevalue);
                langaugevalues.add(lan);


//                Log.e("id", id);
//                Log.e("langaugename", langaugename);
//                Log.e("displayorder", displayorder);
//                Log.e("langaugevalue", langaugevalue);

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
                // Log.e("recording_method_value", recordingmethodvalue);
                recordingmethodvaluesd.add(recor);


//                Log.e("id", id);
//                Log.e("recordingmethods", recordingmethods);
//                Log.e("displayorder", displayorder);
//                Log.e("recordingmethods", recordingmethods);

            }
            constantData.setRecording_methods(recordingmethodvaluesd);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void findaritst() {
        txt1 = (TextView) toolbar1.findViewById(R.id.toolbar_title);

        Typeface face1 = Typeface.createFromAsset(getActivity().getAssets(),
                "font/HelveticaNeue.ttf");
        txt1.setText("Find");
        txt1.setTypeface(face1);
        //web service call
        findartistsub(1);
    }

    private void findartistsub(int source) {
        try {


            String agevalue, lvalue, characteristicsvalue, jobcategoryvalue, gender;
            // if (constData.getGender() != null && Integer.toString(constData.getAgevalue()) != null && Integer.toString(constData.getLvaue()) != null && Integer.toString(constData.getCharacteristicvalue()) != null && Integer.toString(constData.getJobcategoryvalue()) != null) {
            if (Integer.toString(constData.getAgevalue()) != null) {
                agevalue = Integer.toString(constData.getAgevalue());
            } else {
                agevalue = "1";
            }
            if (Integer.toString(constData.getLvaue()) != null) {
                lvalue = Integer.toString(constData.getLvaue());
            } else {
                lvalue = "1";
            }
            if (Integer.toString(constData.getCharacteristicvalue()) != null) {
                characteristicsvalue = Integer.toString(constData.getCharacteristicvalue());
            } else {
                characteristicsvalue = "1";
            }
            if (Integer.toString(constData.getJobcategoryvalue()) != null) {
                jobcategoryvalue = Integer.toString(constData.getJobcategoryvalue());
            } else {
                jobcategoryvalue = "1";
            }
            if (constData.getGender() != null) {
                gender = constData.getGender();
            } else {
                gender = "1";
            }
            imgleft = (Button) toolbar1.findViewById(R.id.btnleft);
            imgleft.setVisibility(View.GONE);
            toolbar1.setNavigationIcon(R.drawable.menu_icon);
            DoSearchApiCall searchApiCall = new DoSearchApiCall(gender, agevalue, lvalue, characteristicsvalue, "1", jobcategoryvalue, source);
            searchApiCall.execute();

        } catch (Exception e) {
            e.printStackTrace();
        }
//        } else {
//            customizeDialog = new CustomizeDialog(getActivity());
//            customizeDialog.setTitle("nuvo");
//            customizeDialog.setCancelable(false);
//            customizeDialog.setMessage("Please choose voice over criteria ");
//            customizeDialog.show();
//        }
    }


    //SL
    private void hideall() {
        laygender.setTop(laybuttons.getTop());
        laygender.setVisibility(View.GONE);
        laycharacteristics.setTop(laybuttons.getTop());
        laycharacteristics.setVisibility(View.GONE);
        layjobtype.setTop(laybuttons.getTop());
        layjobtype.setVisibility(View.GONE);
        layage.setTop(laybuttons.getTop());
        layage.setVisibility(View.GONE);
        laylanguage.setTop(laybuttons.getTop());
        laylanguage.setVisibility(View.GONE);
    }

    private void openpanel(int index) {

        hideall();
        laybuttons.setVisibility(View.VISIBLE);

        if ((index == 0) && FilterOpen != 0) {
            laybuttons.setVisibility(View.GONE);
            laygender.setVisibility(View.VISIBLE);
            FilterOpen = 0;

            dynamicgenderview();
        } else if ((index == 1) && FilterOpen != 1) {
            laybuttons.setVisibility(View.GONE);
            laycharacteristics.setVisibility(View.VISIBLE);
            FilterOpen = 1;
            dynamiccharacteisticview();
        } else if ((index == 2) && FilterOpen != 2) {
            laybuttons.setVisibility(View.GONE);
            layjobtype.setVisibility(View.VISIBLE);
            FilterOpen = 2;
            dynamicjobcategory();
        } else if ((index == 3) && FilterOpen != 3) {
            laybuttons.setVisibility(View.GONE);
            layage.setVisibility(View.VISIBLE);
            FilterOpen = 3;
            dynamicAgeView();
        } else if ((index == 4) && FilterOpen != 4) {
            laybuttons.setVisibility(View.GONE);
            laylanguage.setVisibility(View.VISIBLE);
            FilterOpen = 4;
            dynamiclanguage();
        } else {
            laybuttons.setVisibility(View.VISIBLE);
            FilterOpen = -1;
        }
    }


    public void dynamicgenderview() {
        try {
            constData = ConstantData.getInstance();
            genderList = constData.getgenders();

            laygenderbuttons.removeAllViews();
            for (int i = 0; i < genderList.size(); i++) {
                btnGender = new Button(getActivity());
                btnGender.setBackgroundResource(R.drawable.button_selected);
                btnGender.setTextSize(12);

                Typeface facetxtsigintextbox = Typeface.createFromAsset(getActivity().getAssets(),
                        "font/OpenSans-Regular.ttf");
                btnGender.setTypeface(facetxtsigintextbox);

                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                        ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

                btnGender.setId(Integer.parseInt(genderList.get(i).get("id")) + 20);
                //  btn1.setMinHeight(0);

                btnGender.setTransformationMethod(null);

                params.weight = 1.5f;
                params.gravity = Gravity.LEFT;
                params.gravity = Gravity.RIGHT;

                DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
                // int px1 = Math.round(140 * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
                //  params.width = px1;
                int px = Math.round(39 * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
                params.height = px;

                params.setMargins(5, 5, 15, 2);

                //   btn.setLayoutParams(new LinearLayout.LayoutParams(10, 100));
                //   btn1.setBackgroundResource(R.drawable.button_selected);
                btnGender.setLayoutParams(params);
                //
                btnGender.setTextColor(getActivity().getApplication().getResources().getColor(R.color.white));
                btnGender.setText(genderList.get(i).get("gender"));
                btnGender.setTag(Integer.parseInt(genderList.get(i).get("gender_value")));

                final List<Integer> arr = new ArrayList<Integer>();
                //constData = ConstantData.getInstance();

                if (i == 0) {
                    if (isMaleSelectede == true) {
                        btnGender.setSelected(true);
                    } else {
                        btnGender.setSelected(false);
                    }
                } else {
                    if (isMaleSelectede == false) {
                        btnGender.setSelected(false);
                    } else {
                        btnGender.setSelected(false);
                    }
                }
                if (genderList.get(i).get("gender_selected").toString() != null) {
                    if (genderList.get(i).get("gender_selected").toString().equalsIgnoreCase("1"))
                        btnGender.setSelected(true);
                }

                btnGender.setOnClickListener(new View.OnClickListener() {


                    @Override
                    public void onClick(View v) {
                        int IDS = v.getId();
                        selectedGender = selectedGender ^ (Integer) v.getTag();
                        constData.setGenderFilter(selectedGender);
                        Button o = (Button) v;
                        if (o.isSelected()) {
                            o.setSelected(false);
                        } else {
                            o.setSelected(true);
                        }
                    }
                });

                laygenderbuttons.addView(btnGender, params);

            }


//        Button btnDone = new Button(getActivity());
//        btnDone.setBackgroundColor(Color.parseColor("#F0F0F0"));
//        btnDone.setTextSize(12);
//        btnDone.setTypeface(facetxtsigintextbox);
//        btnMale.setTransformationMethod(null);
//        params.gravity = Gravity.CENTER;
//        params.height = 40;
//        btnDone.setLayoutParams(params);
//
//        btnDone.setPadding(3,210,3,3);
//        btnDone.setTextColor(Color.parseColor("#737687"));
//        btnDone.setText("Show Results");
//        btnDone.setTag(14);
//
//
//
//        btnDone.setOnClickListener(new View.OnClickListener() {
//
//
//            @Override
//            public void onClick(View v) {
//
//            }
//        });

            //     laygenderbuttons.addView(btnDone, params);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //SL


    public void dynamiclanguage() {
        try {


            constData = ConstantData.getInstance();
            langaugelist = constData.getLanguage();

            laylanguagebuttons.removeAllViews();
            for (int i = 0; i < langaugelist.size(); i++) {
                btnlanguage = new Button(getActivity());
                btnlanguage.setBackgroundResource(R.drawable.button_selected);
                btnlanguage.setTextSize(12);

                Typeface facetxtsigintextbox = Typeface.createFromAsset(getActivity().getAssets(),
                        "font/OpenSans-Regular.ttf");
                btnlanguage.setTypeface(facetxtsigintextbox);

                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                        ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

                btnlanguage.setId(Integer.parseInt(langaugelist.get(i).get("id")) + 20);
                //  btn1.setMinHeight(0);

                btnlanguage.setTransformationMethod(null);

                params.weight = 1.5f;
                params.gravity = Gravity.LEFT;
                params.gravity = Gravity.RIGHT;

                DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
                // int px1 = Math.round(140 * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
                //  params.width = px1;
                int px = Math.round(39 * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
                params.height = px;

                params.setMargins(5, 5, 15, 2);

                //   btn.setLayoutParams(new LinearLayout.LayoutParams(10, 100));
                //   btn1.setBackgroundResource(R.drawable.button_selected);
                btnlanguage.setLayoutParams(params);
                //
                btnlanguage.setTextColor(getActivity().getApplication().getResources().getColor(R.color.white));
                btnlanguage.setText(langaugelist.get(i).get("language_name"));
                btnlanguage.setTag(Integer.parseInt(langaugelist.get(i).get("language_value")));

                final List<Integer> arr = new ArrayList<Integer>();
                //constData = ConstantData.getInstance();

                if (langaugelist.get(i).get("language_selected").toString() != null) {
                    if (langaugelist.get(i).get("language_selected").toString().equalsIgnoreCase("1"))
                        btnlanguage.setSelected(true);
                }

                btnlanguage.setOnClickListener(new View.OnClickListener() {


                    @Override
                    public void onClick(View v) {
                        int IDS = v.getId() - 20;
                        selectedLanguage = selectedLanguage ^ (Integer) v.getTag();
                        constData.setLvaue(selectedLanguage);
                        Button o = (Button) v;
                        if (o.isSelected()) {
                            o.setSelected(false);
                        } else {
                            o.setSelected(true);
                        }


                    }
                });

                laylanguagebuttons.addView(btnlanguage, params);

//            Button btnDone = new Button(getActivity());
//            btnDone.setBackgroundColor(Color.parseColor("#F0F0F0"));
//            btnDone.setTextSize(12);
//            btnDone.setTypeface(facetxtsigintextbox);
//            btnDone.setTransformationMethod(null);
//            params.gravity = Gravity.CENTER;
//            params.height = 40;
//            btnDone.setLayoutParams(params);
//
//            btnDone.setPadding(3,210,3,3);
//            btnDone.setTextColor(Color.parseColor("#737687"));
//            btnDone.setText("Show Results");
//            btnDone.setTag(14);
//
//
//
//            btnDone.setOnClickListener(new View.OnClickListener() {
//
//
//                @Override
//                public void onClick(View v) {
//
//                }
//            });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void dynamicjobcategory() {
        try {

            constData = ConstantData.getInstance();
            jobcategorylist = constData.getJob_category();

            layjobbuttons.removeAllViews();
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
                        params.setMargins(5, 20, 15, 2);

                        btn1.setLayoutParams(params);
                        btn1.setPadding(5, 0, 5, 0);
                        //  btn1.setCompoundDrawablePadding(5);
                        //   btn.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                        btn1.setText(jobcategorylist.get(i).get("category_name"));
                        btn1.setTag(Integer.parseInt(jobcategorylist.get(i).get("category_value")));
                        final List<Integer> arr = new ArrayList<Integer>();
                        if (jobcategorylist.get(i).get("jobcategory_selected") != null) {
                            if (jobcategorylist.get(i).get("jobcategory_selected").toString().equalsIgnoreCase("1"))
                                btn1.setSelected(true);
                        }

                        btn1.setOnClickListener(new View.OnClickListener() {


                            @Override
                            public void onClick(View v) {
                                int IDS = v.getId() - 40;


                                selectedJobcategory = selectedJobcategory ^ (Integer) v.getTag();
                                constData.setJobcategoryvalue(selectedJobcategory);
                                //Log.e("jobcategory: ", Integer.toString(selectedJobcategory));
                                //  constData.setLvaue(selectedLanguage);
                                Button o = (Button) v;
                                int index = getindex(String.valueOf(IDS));
                                if (jobcategorylist.get(index).get("jobcategory_selected").toString() == "1") {
                                    // arr.add(IDS);

                                    o.setSelected(false);
                                    if (index > -1) {
                                        HashMap<String, String> charcter = new HashMap<String, String>();
                                        charcter = jobcategorylist.get(index);
                                        charcter.put("jobcategory_selected", "0");
                                        jobcategorylist.set(index, charcter);
                                        //   o.setSelected(false);
                                        /*characterlist.remove(index);
                                        characterlist.add(index, charcter);*/
                                    }
                                    //  Toast.makeText(getActivity(), Integer.toString(IDS), Toast.LENGTH_LONG).show();
                                } else {


                                    // int i1=Integer.toBinaryString(int i)
                                    //  langauge = 1;

                                    //  arr.add(IDS);
                                    //   Toast.makeText(getActivity(), Integer.toString(IDS), Toast.LENGTH_LONG).show();
                                    o.setSelected(true);
                                    if (index > -1) {
                                        HashMap<String, String> charcter = new HashMap<String, String>();
                                        charcter = jobcategorylist.get(index);
                                        charcter.put("jobcategory_selected", "1");
                                        jobcategorylist.set(index, charcter);
                                        //  o.setSelected(true);
                                        // characterlist.remove(index);
                                        //  characterlist.add(index, charcter);
                                    }
                                }


                            }
                        });

                        row.addView(btn1);
                        i++;
                    }
                }
                i--;
                constData.setJob_category(jobcategorylist);
                layjobbuttons.addView(row);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    public void dynamiccharacteisticview() {

        constData = ConstantData.getInstance();
        characterlist = constData.getCharacteristics();

        laycharbuttons.removeAllViews();
        for (int i = 0; i < characterlist.size(); i++) {
            LinearLayout row = new LinearLayout(getActivity());
            row.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));

            for (int j = 0; j < 3; j++) {
                if (i < characterlist.size()) {
                    btncharacteristics = new Button(getActivity());
                    btncharacteristics.setBackgroundResource(R.drawable.button_selected);
                    btncharacteristics.setTextSize(12);
                    Typeface facetxtsigintextbox = Typeface.createFromAsset(getActivity().getAssets(),
                            "font/OpenSans-Regular.ttf");
                    btncharacteristics.setTypeface(facetxtsigintextbox);


                    btncharacteristics.setId(Integer.parseInt(characterlist.get(i).get("id")) + 30);

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
                    btncharacteristics.setTransformationMethod(null);
                    params.setMargins(5, 5, 15, 10);
                    btncharacteristics.setPadding(8, 0, 8, 0);
                    btncharacteristics.setLayoutParams(params);
                    //  btn.setCompoundDrawablePadding(5);
                    //   btn.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                    btncharacteristics.setText(characterlist.get(i).get("characteristic_name"));
                    btncharacteristics.setTag(Integer.parseInt(characterlist.get(i).get("characteristics_value")));
                    final List<Integer> arr = new ArrayList<Integer>();

                    if (characterlist.get(i).get("characteristics_selected").toString().equalsIgnoreCase("1"))
                        btncharacteristics.setSelected(true);


                    btncharacteristics.setOnClickListener(new View.OnClickListener() {


                        @Override
                        public void onClick(View v) {
                            int IDS = v.getId() - 30;


                            selectedCharacteristics = selectedCharacteristics ^ (Integer) v.getTag();

                            // Log.e("characteristics: ", Integer.toString(selectedCharacteristics));
                            //  constData.setLvaue(selectedLanguage);
                            Button o = (Button) v;

                            int index = getindexcharacteistic(String.valueOf(IDS));

                            if (characterlist.get(index).get("characteristics_selected").toString() == "1") {
                                // arr.add(IDS);

                                o.setSelected(false);
                                if (index > -1) {
                                    HashMap<String, String> charcter = new HashMap<String, String>();
                                    charcter = characterlist.get(index);
                                    charcter.put("characteristics_selected", "0");
                                    characterlist.set(index, charcter);

                                }

                            } else {


                                o.setSelected(true);
                                if (index > -1) {
                                    HashMap<String, String> charcter = new HashMap<String, String>();
                                    charcter = characterlist.get(index);
                                    charcter.put("characteristics_selected", "1");
                                    characterlist.set(index, charcter);

                                }
                            }


                        }
                    });
                    btncharacteristics.setHeight(50);
                    row.addView(btncharacteristics);
                    i++;
                }

            }
            i--;
            constData.setCharacteristics(characterlist);
            laycharbuttons.addView(row);
        }


    }


    public void dynamicAgeView() {

        constData = ConstantData.getInstance();
        agelist = constData.getAgelist();

        layagebuttons.removeAllViews();
        if (agelist.size() > 0) {
            for (int i = 0; i < agelist.size(); i++) {


                btn_age = new Button(getActivity());
                btn_age.setBackgroundResource(R.drawable.button_selected);
                btn_age.setTextSize(12);

                Typeface facetxtsigintextbox = Typeface.createFromAsset(getActivity().getAssets(),
                        "font/OpenSans-Regular.ttf");
                btn_age.setTypeface(facetxtsigintextbox);
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                        ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

                //Log.e("age", agelist.get(i).get("age_value"));
                btn_age.setId(Integer.parseInt(agelist.get(i).get("id")) + 10);
                btn_age.setTag(Integer.parseInt(agelist.get(i).get("age_value")));

                params.weight = 1.5f;
                params.gravity = Gravity.LEFT;
                params.gravity = Gravity.RIGHT;

                DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
                // int px1 = Math.round(140 * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
                //  params.width = px1;
                int px = Math.round(39 * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
                params.height = px;

                params.setMargins(10, 50, 15, 2);
                btn_age.setLayoutParams(params);
                //  btn.setLayoutParams(new LinearLayout.LayoutParams(70, 30));
                btn_age.setTransformationMethod(null);
                btn_age.setTextColor(getActivity().getApplication().getResources().getColor(R.color.white));
                btn_age.setText(agelist.get(i).get("age_type"));


                Log.e("Selected Age:-", Integer.parseInt(Integer.toString(constData.getAgevalue())) + "-" + agelist.get(i).get("age_value"));

//                if (constData.getAgevalue() == Integer.parseInt(agelist.get(i).get("age_value")))
//                    btn_age.setSelected(true);
                if (agelist.get(i).get("age_selected").toString() == "1") {
                    btn_age.setSelected(true);
                } else {
                    btn_age.setSelected(false);
                }
                //btn_age.setBackgroundResource(R.drawable.button_selected);

                btn_age.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int IDS = v.getId() - 10;
                        Button o = (Button) v;
                        int index = getAgeIndex(String.valueOf(IDS));
                        if (agelist.get(index).get("age_selected").toString() == "1") {
                            // arr.add(IDS);

                            o.setSelected(false);
                            if (index > -1) {
                                HashMap<String, String> age = new HashMap<String, String>();
                                age = agelist.get(index);
                                age.put("age_selected", "0");
                                agelist.set(index, age);

                            }

                        } else {


                            o.setSelected(true);
                            if (index > -1) {
                                HashMap<String, String> age = new HashMap<String, String>();
                                age = agelist.get(index);
                                age.put("age_selected", "1");
                                agelist.set(index, age);

                            }
                        }


                       /* if (o.isSelected()) {
                            o.setSelected(false);
                        }else{
                            o.setSelected(true);
                        }*/
                        LinearLayout l = (LinearLayout) v.getParent();

                        for (int k = 11; k < 14; k++) {
                            o = (Button) l.findViewById(k);

                            //o.setSelected(false);
                            //Log.e("k", Integer.toString(k));
                        }
                        o = (Button) v;
                        //o.setSelected(true);
                        age = (Integer) o.getTag();
                        Log.e("age-value", Integer.toString(age));
                        constData.setAgevalue(age);
                        //print o.getTag()

                        //Toast.makeText(getActivity(), o.getTag(), Toast.LENGTH_LONG).show();
                    }
                });

                layagebuttons.addView(btn_age);
            }
        }


    }


    private void initiatePopupWindowJobCategory() {
        final PopupWindow pwindo;
        try {

            LayoutInflater inflater = (LayoutInflater) getActivity()
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View layout = inflater.inflate(R.layout.custom_dialog_home,
                    (ViewGroup) getActivity().findViewById(R.id.rootcustomdialog));
            Button btdone = (Button) layout.findViewById(R.id.done);
            Typeface facetxtchoosesemibold = Typeface.createFromAsset(getActivity().getAssets(),
                    "font/OpenSans-Semibold.ttf");
            // btdone.setTextColor(Color.parseColor("#727589"));
            //    tv_title.setTypeface(facetxtchoosesemibold);
            btdone.setTypeface(facetxtchoosesemibold);
            btdone.setAllCaps(false);
            LinearLayout layout1 = (LinearLayout) layout.findViewById(R.id.viewlayout);
            layout1.setPadding(20, 40, 20, 20);
            pwindo = new PopupWindow(layout);
            pwindo.setWidth(LinearLayout.LayoutParams.WRAP_CONTENT);
            pwindo.setHeight(LinearLayout.LayoutParams.WRAP_CONTENT);
            pwindo.setTouchable(true);
            pwindo.setFocusable(false);
            pwindo.setOutsideTouchable(false);
            pwindo.showAtLocation(layout, Gravity.CENTER, 0, 0);
            constData = ConstantData.getInstance();
            btdone.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    pwindo.dismiss();
                    popupvisible = 0;
                }
            });
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
                        params.setMargins(5, 20, 15, 2);

                        btn1.setLayoutParams(params);
                        btn1.setPadding(5, 0, 5, 0);
                        //  btn1.setCompoundDrawablePadding(5);
                        //   btn.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                        btn1.setText(jobcategorylist.get(i).get("category_name"));
                        btn1.setTag(Integer.parseInt(jobcategorylist.get(i).get("category_value")));
                        final List<Integer> arr = new ArrayList<Integer>();

                        if (jobcategorylist.get(i).get("jobcategory_selected").toString().equalsIgnoreCase("1"))
                            btn1.setSelected(true);


                        btn1.setOnClickListener(new View.OnClickListener() {


                            @Override
                            public void onClick(View v) {
                                int IDS = v.getId() - 40;


                                selectedJobcategory = selectedJobcategory ^ (Integer) v.getTag();
                                constData.setJobcategoryvalue(selectedJobcategory);
                                //Log.e("jobcategory: ", Integer.toString(selectedJobcategory));
                                //  constData.setLvaue(selectedLanguage);
                                Button o = (Button) v;
                                int index = getindex(String.valueOf(IDS));
                                if (jobcategorylist.get(index).get("jobcategory_selected").toString() == "1") {
                                    // arr.add(IDS);

                                    o.setSelected(false);
                                    if (index > -1) {
                                        HashMap<String, String> charcter = new HashMap<String, String>();
                                        charcter = jobcategorylist.get(index);
                                        charcter.put("jobcategory_selected", "0");
                                        jobcategorylist.set(index, charcter);
                                        //   o.setSelected(false);
                                        /*characterlist.remove(index);
                                        characterlist.add(index, charcter);*/
                                    }
                                    //  Toast.makeText(getActivity(), Integer.toString(IDS), Toast.LENGTH_LONG).show();
                                } else {


                                    // int i1=Integer.toBinaryString(int i)
                                    //  langauge = 1;

                                    //  arr.add(IDS);
                                    //   Toast.makeText(getActivity(), Integer.toString(IDS), Toast.LENGTH_LONG).show();
                                    o.setSelected(true);
                                    if (index > -1) {
                                        HashMap<String, String> charcter = new HashMap<String, String>();
                                        charcter = jobcategorylist.get(index);
                                        charcter.put("jobcategory_selected", "1");
                                        jobcategorylist.set(index, charcter);
                                        //  o.setSelected(true);
                                        // characterlist.remove(index);
                                        //  characterlist.add(index, charcter);
                                    }
                                }


                            }
                        });

                        row.addView(btn1);
                        i++;
                    }
                }
                i--;
                constData.setJob_category(jobcategorylist);
                layout1.addView(row);
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private int getindex(String currentid) {
        int i;

        if (jobcategorylist == null) {
            return -1;
        }
        for (i = 0; i < jobcategorylist.size(); i++) {
            //Log.e("CaracterList:", "Current: " + currentid + "-" + "Id: " + jobcategorylist.get(i).get("id").toString() + "-");
            if (jobcategorylist.get(i).get("id").toString().equalsIgnoreCase(currentid))
                return i;
        }
        return -1;
    }

    private int getindexcharacteistic(String currentid) {
        int i;

        if (characterlist == null) {
            return -1;
        }
        for (i = 0; i < characterlist.size(); i++) {
            if (characterlist.get(i).get("id").toString().equalsIgnoreCase(currentid))
                return i;
        }
        return -1;
    }

    private int getLanguageIndex(String currentId) {
        int i;
        if (langaugelist == null) {
            return -1;
        }
        for (i = 0; i < langaugelist.size(); i++) {
            if (langaugelist.get(i).get("id").toString().equalsIgnoreCase(currentId))
                return i;
        }
        return -1;
    }

    private int getAgeIndex(String currentId) {
        int i;
        if (agelist == null) {
            return -1;
        }
        for (i = 0; i < agelist.size(); i++) {
            if (agelist.get(i).get("id").toString().equalsIgnoreCase(currentId))
                return i;
        }
        return -1;
    }

    private void initiatepopupage() {

        final PopupWindow pwindo;
        try {

            LayoutInflater inflater = (LayoutInflater) getActivity()
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View layout = inflater.inflate(R.layout.custom_dialog_home,
                    (ViewGroup) getActivity().findViewById(R.id.rootcustomdialog));
            LinearLayout layout1 = (LinearLayout) layout.findViewById(R.id.viewlayout);
            Button btdone = (Button) layout.findViewById(R.id.done);
            //btdone.setTextColor(Color.parseColor("#727589"));
            //   TextView tv_title = (TextView) layout1.findViewById(R.id.tv_title);
            //   tv_title.setVisibility(View.VISIBLE);
            //  tv_title.setText("Age");
            //  tv_title.setTextSize(15);

            Typeface facetxtchoosesemibold = Typeface.createFromAsset(getActivity().getAssets(),
                    "font/OpenSans-Semibold.ttf");
            //    tv_title.setTypeface(facetxtchoosesemibold);
            btdone.setTypeface(facetxtchoosesemibold);
            btdone.setAllCaps(false);

            layout1.setOrientation(LinearLayout.HORIZONTAL);
            layout1.setPadding(20, 40, 20, 20);
            pwindo = new PopupWindow(layout);
            pwindo.setWidth(LinearLayout.LayoutParams.WRAP_CONTENT);
            pwindo.setHeight(LinearLayout.LayoutParams.WRAP_CONTENT);
            pwindo.setTouchable(true);
            pwindo.setFocusable(false);
            pwindo.setOutsideTouchable(false);
            pwindo.showAtLocation(layout, Gravity.CENTER, 0, 0);
            constData = ConstantData.getInstance();
            btdone.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    pwindo.dismiss();
                    popupvisible = 0;
                }
            });
            agelist = constData.getAgelist();
            for (int i = 0; i < agelist.size(); i++) {


                btnage = new Button(getActivity());
                btnage.setBackgroundResource(R.drawable.button_selected);
                btnage.setTextSize(12);

                Typeface facetxtsigintextbox = Typeface.createFromAsset(getActivity().getAssets(),
                        "font/OpenSans-Regular.ttf");
                btnage.setTypeface(facetxtsigintextbox);
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                        ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

                // Log.e("age", agelist.get(i).get("age_value"));
                btnage.setId(Integer.parseInt(agelist.get(i).get("id")) + 10);
                btnage.setTag(Integer.parseInt(agelist.get(i).get("age_value")));

                params.weight = 1.5f;
                params.gravity = Gravity.LEFT;
                params.gravity = Gravity.RIGHT;

                DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
                // int px1 = Math.round(140 * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
                //  params.width = px1;
                int px = Math.round(39 * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
                params.height = px;

                params.setMargins(5, 10, 15, 2);
                btnage.setLayoutParams(params);
                //  btn.setLayoutParams(new LinearLayout.LayoutParams(70, 30));
                btnage.setTransformationMethod(null);
                btnage.setTextColor(getActivity().getApplication().getResources().getColor(R.color.white));
                btnage.setText(agelist.get(i).get("age_type"));
                //btn.setBackgroundResource(R.drawable.button_selected);


                if (agelist.get(i).get("age_selected").toString().equalsIgnoreCase("1"))
                    btnage.setSelected(true);


                btnage.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int IDS = v.getId() - 10;
                        int index = getindexage(String.valueOf(IDS));
                        Button o = (Button) v;
                        LinearLayout l = (LinearLayout) v.getParent();

                        for (int k = 11; k < 14; k++) {
                            o = (Button) l.findViewById(k);
                            o.setSelected(false);
                            if (index > -1) {
                                HashMap<String, String> charcter = new HashMap<String, String>();
                                charcter = agelist.get(index);
                                charcter.put("age_selected", "0");
                                agelist.set(index, charcter);
                                //   o.setSelected(false);
                                        /*characterlist.remove(index);
                                        characterlist.add(index, charcter);*/
                            }
                            // Log.e("k", Integer.toString(k));
                        }
                        o = (Button) v;
                        o.setSelected(true);
                        if (index > -1) {
                            HashMap<String, String> charcter = new HashMap<String, String>();
                            charcter = agelist.get(index);
                            charcter.put("age_selected", "1");
                            agelist.set(index, charcter);
                            //  o.setSelected(true);
                            // characterlist.remove(index);
                            //  characterlist.add(index, charcter);
                        }
                        age = (Integer) o.getTag();
                        //  Log.e("age-value", Integer.toString(age));
                        constData.setAgevalue(age);
                        //print o.getTag()

                        //Toast.makeText(getActivity(), o.getTag(), Toast.LENGTH_LONG).show();

                    }
                });
                constData.setAgelist(agelist);
                layout1.addView(btnage, params);

            }


        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private int getindexage(String currentid) {
        int i;


        for (i = 0; i < agelist.size(); i++) {
            // Log.e("AgeList:", "Current: " + currentid + "-" + "Id: " + agelist.get(i).get("id").toString() + "-");
            if (agelist.get(i).get("id").toString().equalsIgnoreCase(currentid))
                return i;
        }
        return -1;
    }


    public void initiateLangauge() {
        //final PopupWindow pwindo;
        try {

            LayoutInflater inflater = (LayoutInflater) getActivity()
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View layout = inflater.inflate(R.layout.custom_dialog_home,
                    (ViewGroup) getActivity().findViewById(R.id.rootcustomdialog));
            LinearLayout layout1 = (LinearLayout) layout.findViewById(R.id.viewlayout);
            Button btdone = (Button) layout.findViewById(R.id.done);
            //btdone.setTextColor(Color.parseColor("#727589"));
            TextView tv_title = (TextView) layout1.findViewById(R.id.tv_title);
            tv_title.setVisibility(View.VISIBLE);
            tv_title.setText("Language");
            tv_title.setTextSize(15);
            Typeface facetxtchoosesemibold = Typeface.createFromAsset(getActivity().getAssets(),
                    "font/OpenSans-Semibold.ttf");
            tv_title.setTypeface(facetxtchoosesemibold);
            btdone.setTypeface(facetxtchoosesemibold);
            btdone.setAllCaps(false);
            //   layout1.setPadding(40, 40, 20, 20);
            layout1.setGravity(Gravity.CENTER);
            pwindo = new PopupWindow(layout);
            pwindo.setWidth(LinearLayout.LayoutParams.WRAP_CONTENT);
            pwindo.setHeight(LinearLayout.LayoutParams.WRAP_CONTENT);
            pwindo.setTouchable(true);
            pwindo.setFocusable(false);
            pwindo.setOutsideTouchable(false);

            pwindo.showAtLocation(layout, Gravity.CENTER, 0, 0);
            constData = ConstantData.getInstance();

            langaugelist = constData.getLanguage();
            if (langaugelist == null) {
                return;
            }
            for (int i = 0; i < langaugelist.size(); i++) {
                LinearLayout row = new LinearLayout(getActivity());
                row.setGravity(Gravity.CENTER_HORIZONTAL);
                row.setPadding(0, 30, 0, 0);
                row.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                //  RadioGroup rg = new RadioGroup(getActivity());
                for (int j = 0; j < 2; j++) {
                    if (i < langaugelist.size()) {
                        rb = new RadioButton(getActivity());
                        rb.setButtonDrawable(R.drawable.custom_btn_radio);
                        rb.setTextSize(12);


                        Typeface facetxtsigintextbox = Typeface.createFromAsset(getActivity().getAssets(),
                                "font/OpenSans-Regular.ttf");
                        rb.setTypeface(facetxtsigintextbox);

                        rb.setId(Integer.parseInt(langaugelist.get(i).get("id")) + 20);

                        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                        params.weight = 1.5f;
                        params.gravity = Gravity.LEFT;
                        params.gravity = Gravity.RIGHT;

                        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
                        // int px1 = Math.round(140 * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
                        //  params.width = px1;
                        int px = Math.round(50 * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
                        params.height = px;
                        rb.setTransformationMethod(null);
                        params.setMargins(40, 0, 0, 0);

                        rb.setLayoutParams(params);
                        rb.setPadding(20, 0, 5, 0);
                        //  btn1.setCompoundDrawablePadding(5);
                        //   btn.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                        rb.setText(langaugelist.get(i).get("language_name"));
                        rb.setTag(Integer.parseInt(langaugelist.get(i).get("language_value")));
                        final List<Integer> arr = new ArrayList<Integer>();
                        constData = ConstantData.getInstance();


                        if (langaugelist.get(i).get("language_selected").toString().equalsIgnoreCase("1"))
                            rb.setChecked(true);
                        rb.setSelected(true);
                        rb.setClickable(true);


                        rb.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                int IDS = v.getId() - 20;
                                selectedLanguage = selectedLanguage ^ (Integer) v.getTag();
                                constData.setLvaue(selectedLanguage);
                                // Log.e("selected langauge: ", Integer.toString(selectedLanguage));

                                RadioButton rb = (RadioButton) v;
                                //int index = getindex(String.valueOf(IDS));

                                int index = getLanguageIndex(String.valueOf(IDS));

                                if (langaugelist.get(index).get("language_selected").toString() == "1") {
                                    // arr.add(IDS);

                                    rb.setSelected(false);
                                    rb.setChecked(false);
                                    rb.setClickable(true);
                                    if (index > -1) {
                                        HashMap<String, String> charcter = new HashMap<String, String>();
                                        charcter = langaugelist.get(index);
                                        charcter.put("language_selected", "0");
                                        langaugelist.set(index, charcter);
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
                                    rb.setSelected(true);
                                    rb.setClickable(true);
                                    if (index > -1) {
                                        HashMap<String, String> charcter = new HashMap<String, String>();
                                        charcter = langaugelist.get(index);
                                        charcter.put("language_selected", "1");
                                        langaugelist.set(index, charcter);
                                        //  o.setSelected(true);
                                        // characterlist.remove(index);
                                        //  characterlist.add(index, charcter);
                                    }

                                }



                              /*
                                if (rb.isSelected() == true) {
                                    rb.setSelected(false);
                                    rb.setChecked(false);
                                    rb.setClickable(true);

                                } else {
                                    rb.setSelected(true);
                                    rb.setClickable(true);

                                }*/
                            }
                        });


                        //  rg.addView(rb);
                        row.addView(rb);
                        i++;
                    }
                }
                i--;
                constData.setLanguage(langaugelist);
                layout1.addView(row);
            }
            btdone.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    pwindo.dismiss();
                    popupvisible = 0;
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }


    }


    public void initategender() {

        final PopupWindow pwindo;
        try {


            LayoutInflater inflater = (LayoutInflater) getActivity()
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View layout = inflater.inflate(R.layout.custom_dialog_home_gender,
                    (ViewGroup) getActivity().findViewById(R.id.rootcustomdialog));
            final LinearLayout layout1 = (LinearLayout) layout.findViewById(R.id.viewlayout);
            pwindo = new PopupWindow(layout);
            pwindo.setWidth(LinearLayout.LayoutParams.WRAP_CONTENT);
            pwindo.setHeight(LinearLayout.LayoutParams.WRAP_CONTENT);
            pwindo.setTouchable(true);
            pwindo.setFocusable(false);
            pwindo.setOutsideTouchable(false);
            pwindo.showAtLocation(layout, Gravity.CENTER, 0, 0);

            radioSexGroup = (RadioGroup) layout1.findViewById(R.id.radioSex);

            Button btdone = (Button) layout.findViewById(R.id.done);
            //btdone.setTextColor(Color.parseColor("#727589"));
            TextView tv_title = (TextView) layout1.findViewById(R.id.tv_title);
            tv_title.setVisibility(View.VISIBLE);
            tv_title.setText("Gender");
            tv_title.setTextSize(15);
            Typeface facetxtchoosesemibold = Typeface.createFromAsset(getActivity().getAssets(),
                    "font/OpenSans-Semibold.ttf");
            tv_title.setTypeface(facetxtchoosesemibold);
            btdone.setTypeface(facetxtchoosesemibold);
            btdone.setAllCaps(false);

            SharedPreferences prefs = getActivity().getSharedPreferences("SelectGender", MODE_PRIVATE);
            String restoredText = prefs.getString("type", "Male");
            if (restoredText.equalsIgnoreCase("Male")) {
                radioSexGroup.check(R.id.radioMale);
            } else {
                radioSexGroup.check(R.id.radioFemale);
            }


            btdone.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    pwindo.dismiss();

                    int selectedId = radioSexGroup.getCheckedRadioButtonId();
                    // Log.e("selected gender", Integer.toString(selectedId));
                    constData = ConstantData.getInstance();
                    // find the radiobutton by returned id
                    radioSexButton = (RadioButton) radioSexGroup.findViewById(selectedId);
                    //Log.e("value", radioSexButton.getText().toString());
                    SharedPreferences.Editor editor = getActivity().getSharedPreferences("SelectGender", MODE_PRIVATE).edit();
                    if (radioSexButton.getText().toString().equalsIgnoreCase("Male")) {
                        constData.setGender("14");
                        editor.putString("type", "Male");
                        editor.commit();
                        isMaleSelectede = true;

                       /* String gid = "14";
                        String gendertype = "Male";
                        String gendervalue = "14";
                        HashMap<String, String> gender = new HashMap<String, String>();
                        gender.put("id", gid);
                        gender.put("gender", gendertype);
                        gender.put("gender_value", gendervalue);
                        gender.put("gender_selected", "0");
                        genderList.add(gender);*/


                    } else {
                        /*String gid = "15";
                        String gendertype = "Female";
                        String gendervalue = "15";
                        HashMap<String, String> gender = new HashMap<String, String>();
                        gender.put("id", gid);
                        gender.put("gender", gendertype);
                        gender.put("gender_value", gendervalue);
                        gender.put("gender_selected", "0");
                        genderList.add(gender);*/
                        isMaleSelectede = false;
                        constantData.setgenders(genderList);
                        constData.setGender("15");
                        editor.putString("type", "FEmale");
                        editor.commit();
                    }

                    popupvisible = 0;

                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    public void searchApi(String gender, String age, String language, String characteristics, String recordingmethod, String jobcategory)

    {
        String text = "";
        BufferedReader reader = null;
        try {


            String url = WebApiCall.baseURl + "user/search";

            URL obj = new URL(url);
            constData = ConstantData.getInstance();
            constData.getUserid();
            constData.getTokenid();
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();
            String encoded = Base64.encodeToString((constData.getUserid() + ":" + constData.getTokenid()).getBytes("UTF-8"), Base64.NO_WRAP);
            //     String encoded = Base64.encodeToString(userid+":"+tokens).getBytes();
            con.setRequestProperty("Authorization", "Basic " + encoded);
            String data = URLEncoder.encode("gender", "UTF-8")
                    + "=" + URLEncoder.encode(gender, "UTF-8");
            data += "&" + URLEncoder.encode("age", "UTF-8") + "="
                    + URLEncoder.encode(age, "UTF-8");
            data += "&" + URLEncoder.encode("language", "UTF-8")
                    + "=" + URLEncoder.encode(language, "UTF-8");
            data += "&" + URLEncoder.encode("characteristics", "UTF-8")
                    + "=" + URLEncoder.encode(characteristics, "UTF-8");
            data += "&" + URLEncoder.encode("recording_method", "UTF-8")
                    + "=" + URLEncoder.encode(recordingmethod, "UTF-8");

            data += "&" + URLEncoder.encode("job_category", "UTF-8")
                    + "=" + URLEncoder.encode(jobcategory, "UTF-8");


            con.setDoOutput(true);
            OutputStreamWriter wr = new OutputStreamWriter(con.getOutputStream());
            wr.write(data);
            wr.flush();
            // Get the server response
            reader = new BufferedReader(new InputStreamReader(con.getInputStream()));
            StringBuilder sb = new StringBuilder();
            String line = null;
            // Read Server Response
            while ((line = reader.readLine()) != null) {
                // Append server response in string
                sb.append(line + "\n");
            }
            text = sb.toString();
            if (text == null) {
                return;
            }
            constData.setFindArtist(true);
            Log.e("text", text);
            JSONArray json = new JSONArray(text);
            artistlist = new ArrayList<FindJobs>();

            for (int i = 0; i < json.length(); i++) {

                JSONObject e = json.getJSONObject(i);
                FindJobs artist = new FindJobs();
                artist.setUserid(e.getString("user_id"));
                artist.setFirstname(e.getString("first_name"));
                artist.setLastname(e.getString("last_name"));
                artist.setUrl(e.getString("demo"));
                artist.setOnline(e.getString("online"));
                artist.setPercent(e.getString("percentage"));
                artist.setDisplayCharactersistics(e.getString("characteristics"));


                // Log.e("firstname", e.getString("first_name"));
                // Log.e("lastname", e.getString("last_name"));
//                try {
//                    Log.e("characteristics", e.getString("characteristics"));
//                    // Log.e("category", e.getString("category"));
//                    //  Log.e("category", e.getString("category"));
//                    //  Log.e("recording_method", e.getString("recording_method"));
//                } catch (Exception e1) {
//                    e1.printStackTrace();
//                }

                artistlist.add(artist);
            }


        } catch (Exception e) {
            e.printStackTrace();
            ;
        }

    }

    private void AddSelectedArtist(String id) {

        //Log.e("AddedID:", id);
        selectedartist.add(id);
    }

    private void RemoveSelectedArtist(String id) {
        selectedartist.remove(id);

    }

    private void SendJobHideShow() {
        if (selectedartist.size() > 0) {
            txtsendjob.setVisibility(View.VISIBLE);
            img_bell.setVisibility(View.GONE);
        } else {
            txtsendjob.setVisibility(View.GONE);
            img_bell.setVisibility(View.VISIBLE);
        }

    }

    @Override
    public void onQuantityClickEvent(View v, int position) {

    }

    @Override
    public void onPlayArtistRecord(View v, int position, String url) {
        mMediapler = new MediaPlayer();
        final FindJobs fd = artistlist.get(position);
        if (fd.getSelectedaudio() == 0)
            try {
                if (mMediapler.isPlaying()) {
                    try {
                        //Log.e("stopped", "stopped");
                        mMediapler.stop();
                        mMediapler.release();
                        // fd.setSelectedaudio(0);
                        // holder.imageviewprogress.setImageResource(R.drawable.pause_button);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    // holder.imageviewprogress.setImageResource(R.drawable.play_button);
                    // Log.e("started", "started");
                    mMediapler.setDataSource(url);
                    mMediapler.prepare();
                    mMediapler.start();
                    fd.setSelectedaudio(1);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        else
            fd.setSelectedaudio(0);
        mMediapler.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                //Log.e("stopped,", "stopped");
                fd.setSelectedaudio(0);
                adapter.notifyDataSetChanged();
            }
        });
        adapter.notifyDataSetChanged();
    }

    public class DoSearchApiCall extends AsyncTask<String, Void, String> {
        ProgressDialog pd;
        String gender, ageValue, lvalue, characteristicsvalue, type, jobcategoryvalue;
        int source = 0;

        public DoSearchApiCall(String gender, String agevalue, String lvalue, String characteristicsvalue, String type, String jobcategoryvalue, int source) {
            this.gender = gender;
            this.ageValue = agevalue;
            this.lvalue = lvalue;
            this.characteristicsvalue = characteristicsvalue;
            this.type = type;
            this.jobcategoryvalue = jobcategoryvalue;
            this.source = source;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pd = new ProgressDialog(mContext, ProgressDialog.THEME_HOLO_LIGHT);
            pd.setMessage("Please Wait!!");
            pd.setCancelable(false);
            pd.show();
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            pd.dismiss();
            if (source == 1) {
                layHome.setVisibility(LinearLayout.GONE);
                laySearch.setVisibility(LinearLayout.VISIBLE);
            }
            try {

                adapter = new FindListadapter(getActivity(), R.layout.list_rows_new, artistlist, new SendjobClickEvent() {
                    @Override
                    public void onQuantityClickEvent(View v, int position) {
                        txtsendjob = (TextView) toolbar1.findViewById(R.id.rightlabel);


                        img_bell = (Button) toolbar1.findViewById(R.id.btn);
                        FindJobs fd = artistlist.get(position);
                        if (fd.getSelected() == 0) {
                            fd.setSelected(1);
//                            txtsendjob.setVisibility(View.VISIBLE);
//                            img_bell.setVisibility(View.GONE);
                            txtsendjob.setText("Send Job");

                            AddSelectedArtist(fd.getUserid());
                            adapter.notifyDataSetChanged();
                        } else {
                            fd.setSelected(0);
//                            txtsendjob.setVisibility(View.GONE);
//                            img_bell.setVisibility(View.VISIBLE);
                            RemoveSelectedArtist(fd.getUserid());
                            adapter.notifyDataSetChanged();
                        }
                        SendJobHideShow();
                    }

                    @Override
                    public void onPlayArtistRecord(View v, int position, String url) {

                    }
                });
                listview.setAdapter(adapter);
                listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                        FindJobs pojo = artistlist.get(position);
                        constantData.setFindJobsPojo(pojo);
                        lvroot.removeAllViewsInLayout();
                        toolbar1.setVisibility(View.GONE);
                        String fragmentType = "Fragment_Criteria";
                        Bundle bundle = new Bundle();
                        bundle.putString("fragmentType", fragmentType);

                        Fragment newFragment = new Fragment_Artist_Details();
                        newFragment.setArguments(bundle);
                        FragmentTransaction transaction = getFragmentManager().beginTransaction();


                        transaction.replace(R.id.lvproducerfind, newFragment);
                        transaction.addToBackStack(null);


                        transaction.commit();

                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        protected String doInBackground(String... strings) {
            searchApi(gender, ageValue, lvalue, characteristicsvalue, "1", jobcategoryvalue);

            return null;
        }
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.mContext = context;
    }

    @Override
    public void onResume() {
        super.onResume();
        toolbar1.setVisibility(View.VISIBLE);
    }
}