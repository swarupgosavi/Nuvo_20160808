package xyz.theapptest.nuvo.agent;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
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
import xyz.theapptest.nuvo.api.WebApiCall;
import xyz.theapptest.nuvo.fragment.Fragment_Request_Details;
import xyz.theapptest.nuvo.pojo.AgentArtistListItemPojo;
import xyz.theapptest.nuvo.pojo.NewAuditionsPojo;
import xyz.theapptest.nuvo.pojo.UserProfilePojo;
import xyz.theapptest.nuvo.utils.ConstantData;
import xyz.theapptest.nuvo.utils.Fonts;

/**
 * Created by torinit01 on 10/8/16.
 */
public class ArtistsListFragment extends Fragment implements View.OnClickListener {


    ListView artistList;
    ArtistsListAdapter artistsListAdapter;
    TextView btnAddArtist, artistsHdrTxt;
    Fonts fonts;
    Context mContext;
    ConstantData constantData;
    ArrayList<AgentArtistListItemPojo> agentArtistList;
    LinearLayout mainLL;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_artists_list, null);
        constantData = ConstantData.getInstance();
        initViews(rootView);
        getArtistFromServer();
        setUpListner();
        return rootView;
    }

    private void initViews(View rootView) {

        fonts = new Fonts(getActivity());
        artistsHdrTxt = (TextView) rootView.findViewById(R.id.aal_toolbar_title);
        artistsHdrTxt.setTypeface(fonts.halveticaNeue);
        btnAddArtist = (TextView) rootView.findViewById(R.id.aal_add_an_artist);
        btnAddArtist.setTypeface(fonts.openSansRegular);
        btnAddArtist.setOnClickListener(this);
        artistList = (ListView) rootView.findViewById(R.id.aal_artist_list_for_agent);
        mainLL = (LinearLayout) rootView.findViewById(R.id.activity_artist_list_main_ll_id);


    }

    private void setUpListner() {
        artistList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                AgentArtistListItemPojo pojo = agentArtistList.get(position);
                constantData.setAgentArtistListItemPojo(pojo);
                mainLL.removeAllViews();

                Fragment newFragment = new ArtistDetailsForAgentFragment();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();


                transaction.replace(R.id.activity_artist_list_main_ll_id, newFragment);
                transaction.addToBackStack(null);


                transaction.commit();


            }
        });
    }

    private void getArtistFromServer() {
        GetArtistFromServerCall getArtistFromServerCall = new GetArtistFromServerCall();
        getArtistFromServerCall.execute();
    }

    @Override
    public void onClick(View view) {
        if (view == btnAddArtist) {

            //startActivity(new Intent(getActivity(), AgentsAddArtistActivity.class));
            startActivity(new Intent(mContext, AgentAddSingleArtistActivity.class));

        }

    }

    public class GetArtistFromServerCall extends AsyncTask<String, Void, String> {

        ProgressDialog pd;
        String response;
        AgentArtistListItemPojo agentArtistListItemPojo;

        public GetArtistFromServerCall() {

        }

        @Override
        protected void onPreExecute() {
            pd = new ProgressDialog(mContext,ProgressDialog.THEME_HOLO_LIGHT);
            pd.setTitle("Please wait");
            pd.setCancelable(false);
            pd.show();
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(String s) {
            pd.dismiss();
            setListAdapter();
            super.onPostExecute(s);
        }

        @Override
        protected String doInBackground(String... strings) {
            try {
                String userId = constantData.getUserid();
                String token = constantData.getTokenid();

              /*  SharedPreferences shf = getActivity().getSharedPreferences("UserType", Context.MODE_PRIVATE);
                String usertype = shf.getString("usertype", null);
                userid = shf.getString("userid", null);
                token = shf.getString("token", null);
*/

                String url = WebApiCall.baseURl + "user/all_artist";
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
                Log.e("allresponsestring",serverResult);
                Gson gson = new Gson();
                JsonReader reader = new JsonReader(new StringReader(serverResult));
                reader.setLenient(true);
                Type listType = new TypeToken<List<AgentArtistListItemPojo>>() {
                }.getType();
                agentArtistList = gson.fromJson(reader, listType);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.mContext = context;
    }

    private void setListAdapter() {
        artistsListAdapter = new ArtistsListAdapter(getActivity(), agentArtistList);
        artistList.setAdapter(artistsListAdapter);
    }
}