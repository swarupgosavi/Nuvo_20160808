package xyz.theapptest.nuvo.fragment;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.util.AsyncListUtil;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import xyz.theapptest.nuvo.R;
import xyz.theapptest.nuvo.agent.ArtistsListAdapter;
import xyz.theapptest.nuvo.api.CheckInternetConnection;
import xyz.theapptest.nuvo.api.WebApiCall;
import xyz.theapptest.nuvo.pojo.AgentArtistListItemPojo;
import xyz.theapptest.nuvo.ui.CustomizeDialog;
import xyz.theapptest.nuvo.ui.ProducerHomeScreen;
import xyz.theapptest.nuvo.utils.ConstantData;

/**
 * Created by trtcpu007 on 3/8/16.
 */

public class Fragment_saved_producer extends Fragment {
    TextView tv_title;
    android.support.v7.widget.Toolbar toolbar1;
    Button btnleftimg;
    TextView tvright;
    ListView savedArtistListview;
    ConstantData constantData;
    Context mContext;
    ArrayList<AgentArtistListItemPojo> savedArtistList;
    ArtistsListAdapter artistsListAdapter;
    LinearLayout mainLL;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_saved_producer, null);
        constantData = ConstantData.getInstance();
        setUpToolbar();
        init(rootView);
        getSavedArtistList();
        setUpListner();
        return rootView;
    }

    private void setUpToolbar() {
        ProducerHomeScreen act = (ProducerHomeScreen) getActivity();

// act.getActionBar().setDisplayShowHomeEnabled(true);
        toolbar1 = (android.support.v7.widget.Toolbar) act.findViewById(R.id.toolbar_top);
        btnleftimg = (Button) toolbar1.findViewById(R.id.btnleft);
        btnleftimg.setVisibility(View.GONE);
        toolbar1.setNavigationIcon(R.drawable.menu_icon);
        tvright = (TextView) toolbar1.findViewById(R.id.rightlabel);
        tvright.setVisibility(View.GONE);
        tv_title = (TextView) toolbar1.findViewById(R.id.toolbar_title);
        tv_title.setTextSize(19);

    }

    private void init(View view) {

        savedArtistListview = (ListView) view.findViewById(R.id.fragment_saved_producer_listview_id);
        mainLL = (LinearLayout) view.findViewById(R.id.fragment_saved_main_ll_id);
    }

    private void setUpListner() {
        savedArtistListview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                try {
                    AgentArtistListItemPojo pojo = savedArtistList.get(i);
                    Gson gson = new Gson();
                    String savedItemPojoString = gson.toJson(pojo);
                    mainLL.removeAllViewsInLayout();
                    toolbar1.setVisibility(View.GONE);
                    String fragmentType = "Fragment_Saved";
                    Bundle bundle = new Bundle();
                    bundle.putString("fragmentType", fragmentType);
                    bundle.putString("GsonString", savedItemPojoString);
                    Fragment newFragment = new Fragment_Artist_Details();
                    newFragment.setArguments(bundle);
                    FragmentTransaction transaction = getFragmentManager().beginTransaction();


                    transaction.replace(R.id.fragment_saved_main_ll_id, newFragment);
                    transaction.addToBackStack(null);


                    transaction.commit();

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void getSavedArtistList() {
        GetSavedArtistList getSavedArtistList = new GetSavedArtistList();
        getSavedArtistList.execute();
    }

    private class GetSavedArtistList extends AsyncTask<String, Void, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            setListAdapter();
        }

        @Override
        protected String doInBackground(String... strings) {
            String userId = constantData.getUserid();
            String token = constantData.getTokenid();
            try {
                if (CheckInternetConnection.isConnected(getActivity())) {
                    String url = WebApiCall.baseURl + "user/savedartist";
                    URL obj = new URL(url);
                    HttpURLConnection con = (HttpURLConnection) obj.openConnection();
                    String encoded = Base64.encodeToString((userId + ":" + token).getBytes("UTF-8"), Base64.NO_WRAP);
                    //     String encoded = Base64.encodeToString(userid+":"+tokens).getBytes();
                    con.setRequestProperty("Authorization", "Basic " + encoded);
                    // optional default is GET
                    con.setRequestMethod("GET");

                    //add request header


                    int responseCode = con.getResponseCode();

                    BufferedReader in = new BufferedReader(
                            new InputStreamReader(con.getInputStream()));
                    String inputLine;
                    StringBuffer response = new StringBuffer();

                    while ((inputLine = in.readLine()) != null) {
                        response.append(inputLine);
                    }
                    in.close();

                    //print result
                    String serverResult = response.toString();
                    serverResult = serverResult.trim();
                    Log.e("allresponsestring", serverResult);
                    Gson gson = new Gson();
                    JsonReader reader = new JsonReader(new StringReader(serverResult));
                    reader.setLenient(true);
                    Type listType = new TypeToken<List<AgentArtistListItemPojo>>() {
                    }.getType();
                    savedArtistList = gson.fromJson(reader, listType);
                } else {
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            CustomizeDialog customizeDialog = new CustomizeDialog(getActivity());
                            customizeDialog.setTitle("nuvo");
                            customizeDialog.setCancelable(false);
                            customizeDialog.setMessage("Internet not available, Please check your Internet connectivity and try again.");
                            customizeDialog.show();

                        }
                    });

                }

            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }
    }

    private void setListAdapter() {
        if (savedArtistList == null) {
            return;
        }
        artistsListAdapter = new ArtistsListAdapter(mContext, savedArtistList);
        savedArtistListview.setAdapter(artistsListAdapter);
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
