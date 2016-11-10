package xyz.theapptest.nuvo.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import xyz.theapptest.nuvo.R;
import xyz.theapptest.nuvo.api.CheckInternetConnection;
import xyz.theapptest.nuvo.ui.SignInActivity;

/**
 * Created by trtcpu007 on 1/8/16.
 */

public class FragmentOngoingArtist extends Fragment {
    ListView listview;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_ongoingartist, null);
        init(rootView);
        webServiceCall();




        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
      //  webServiceCall();

    }

       private void webServiceCall() {
        if (CheckInternetConnection.isConnected(getActivity())) {
            try {
                SharedPreferences shf = getActivity().getSharedPreferences("UserType", Context.MODE_PRIVATE);
                String usertype = shf.getString("usertype", null);
                String userid = shf.getString("userid", null);
                String token = shf.getString("token", null);
                String url = "http://nuvo.theapptest.xyz/v2/api/job/ongoing";
                Log.e("userid", userid);
                Log.e("token", token);
                URL obj = new URL(url);
                HttpURLConnection con = (HttpURLConnection) obj.openConnection();
                String encoded = Base64.encodeToString((userid + ":" + token).getBytes("UTF-8"), Base64.NO_WRAP);
                //     String encoded = Base64.encodeToString(userid+":"+tokens).getBytes();
                con.setRequestProperty("Authorization", "Basic " + encoded);
                // optional default is GET
                con.setRequestMethod("GET");


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
                Log.e("artistresponse", response.toString());
                if (response.toString() != null) {

/*
                *//**//*    //parsing
                    JSONArray json = new JSONArray(response.toString());
                    requestjoblist = new ArrayList<RequestJob>();
                    for (int i = 0; i < json.length(); i++) {
                        //  HashMap<String, String> map = new HashMap<String, String>();
                        JSONObject e = json.getJSONObject(i);
                        RequestJob job = new RequestJob();
                        job.setJobid(e.getString("id"));
                        Log.e("jobid", e.getString("id"));
                        job.setTitle(e.getString("title"));
                        job.setDescription(e.getString("description"));
                        job.setExpirydate(e.getString("expiry_date"));
                        job.setAttachments(e.getString("attachments"));
                        job.setView(e.getString("views"));
                        job.setStatus(e.getString("status"));
                        job.setCreatedon(e.getString("created_on"));
                        job.setCreatedby(e.getString("created_by"));
                        job.setLastmodifiedon(e.getString("last_modified_on"));
                        job.setLastmodifiedby(e.getString("last_modified_by"));
                        job.setUserid(e.getString("user_id"));
                        job.setAudiotionid(e.getString("audition_id"));
                        job.setJobstatus(e.getString("job_status"));


                        JSONObject userinfo = e.getJSONObject("user_info");
                        job.setId(userinfo.getString("id"));
                        job.setFirstname(userinfo.getString("first_name"));
                        Log.e("firstname", userinfo.getString("first_name"));
                        job.setLastname(userinfo.getString("last_name"));
                        job.setEmail(userinfo.getString("email"));
                        job.setPassword(userinfo.getString("password"));
                        job.setUsersstatus(userinfo.getString("status"));
                        job.setRole(userinfo.getString("role"));
                        job.setOnline(userinfo.getString("online"));
                        job.setUsercreatedon(userinfo.getString("created_on"));
                        job.setUserlastlogout(userinfo.getString("last_log_out"));
                        job.setUserofuserid(userinfo.getString("user_id"));
                        job.setGender(userinfo.getString("gender"));
                        job.setAge(userinfo.getString("age"));
                        job.setPhone(userinfo.getString("phone"));
                        job.setLanguage(userinfo.getString("language"));
                        job.setDescription(userinfo.getString("description"));
                        job.setUsercharacteristics(userinfo.getString("characteristics"));
                        job.setUserjobcategory(userinfo.getString("job_category"));
                        job.setUserrecordingmethod(userinfo.getString("recording_method"));
                        job.setUserdemo(userinfo.getString("demo"));


                        requestjoblist.add(job);
                    }

*//**//**/
                }

            } catch (Exception e) {
                e.printStackTrace();
            }


        }


    }

    private void init(View rootView) {
        listview = (ListView) rootView.findViewById(R.id.list_result);


    }
}
