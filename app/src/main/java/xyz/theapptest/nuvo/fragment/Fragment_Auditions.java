package xyz.theapptest.nuvo.fragment;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import android.util.Base64;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONObject;

import xyz.theapptest.nuvo.R;
import xyz.theapptest.nuvo.adapter.AuditionsAdapter;
import xyz.theapptest.nuvo.api.CheckInternetConnection;
import xyz.theapptest.nuvo.api.WebApiCall;
import xyz.theapptest.nuvo.pojo.AuditionsPojo;
import xyz.theapptest.nuvo.pojo.NewAuditionsPojo;
import xyz.theapptest.nuvo.ui.Activity_Criteradetails;
import xyz.theapptest.nuvo.ui.ArtistHomescreen;
import xyz.theapptest.nuvo.ui.CustomizeDialog;
import xyz.theapptest.nuvo.utils.ConstantData;
import xyz.theapptest.nuvo.utils.RecyclerItemClickListener;

/**
 * Created by trtcpu007 on 15/7/16.
 */

public class Fragment_Auditions extends Fragment {
    Button btn;
    RecyclerView auditionsRecyclerView;
    Context mContext;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    ConstantData constantData;
    // List<AuditionsPojo> auditionsPojoList;
    LinearLayout lyAudiotion;
    String userid, token;
    android.support.v7.widget.Toolbar toolbar1;
    TextView tx2;
    List<NewAuditionsPojo> newAuditionsPojoList;
    Button btnleft;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_fragment_auditions_layout, null);
        init(rootView);
        constantData = ConstantData.getInstance();


        AuditionsWebServiceCall auditionsWebServiceCall = new AuditionsWebServiceCall();
        auditionsWebServiceCall.execute();

        //setAdapter();
        setUpListeners();

        return rootView;
    }

    private void init(View rootView) {
        auditionsRecyclerView = (RecyclerView) rootView.findViewById(R.id.fragment_auditions_recyclerview_id);
        lyAudiotion = (LinearLayout) rootView.findViewById(R.id.fragment_audion_layout);
        ArtistHomescreen act = (ArtistHomescreen) getActivity();
        toolbar1 = (android.support.v7.widget.Toolbar) act.findViewById(R.id.toolbar_top);
        tx2 = (TextView) toolbar1.findViewById(R.id.rightlabel);
        tx2.setVisibility(View.GONE);
        btnleft = (Button) toolbar1.findViewById(R.id.btnleft);
        btnleft.setVisibility(View.GONE);

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.mContext = context;
    }


    public class AuditionsWebServiceCall extends AsyncTask<String, Void, String> {


        @Override
        protected void onPreExecute() {

            super.onPreExecute();


        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            //setAdapter(auditionsPojoList);
            try {
                if (newAuditionsPojoList.size() > 0) {
                    setAdapter(newAuditionsPojoList);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

        @Override
        protected String doInBackground(String... strings) {
            doGetAuditionsWebServiceCall();

            return null;
        }

        private void doGetAuditionsWebServiceCall() {
            if (CheckInternetConnection.isConnected(getActivity())) {
                try {
                    //  String userId = constantData.getUserid();
                    //   String token = constantData.getTokenid();

                    SharedPreferences shf = getActivity().getSharedPreferences("UserType", Context.MODE_PRIVATE);
                    String usertype = shf.getString("usertype", null);
                    userid = shf.getString("userid", null);
                    token = shf.getString("token", null);


                    // String url = WebApiCall.getAuditionsUrl;
                    String url = WebApiCall.baseURl + "job/jobs/audition";

                    URL obj = new URL(url);
                    HttpURLConnection con = (HttpURLConnection) obj.openConnection();
                    String encoded = Base64.encodeToString((userid + ":" + token).getBytes("UTF-8"), Base64.NO_WRAP);
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
                    Log.e("GETauydion", response.toString());
                    String serverResult = response.toString();
                    if (response.toString() != null) {
                        if (response.toString().startsWith("{")) {
                            JSONObject userObject = new JSONObject(response.toString());
                            String message_code = userObject.getString("message_code");
                            if (message_code.equalsIgnoreCase("112")) {
                                getActivity().runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {

                                        CustomizeDialog customizeDialog = new CustomizeDialog(getActivity());
                                        customizeDialog.setTitle("nuvo");
                                        customizeDialog.setCancelable(false);
                                        customizeDialog.setMessage("Data not found.");
                                        customizeDialog.show();

                                    }
                                });

                            }
                        } else {

                          /*  Gson gson = new Gson();
                            Type listType = new TypeToken<List<AuditionsPojo>>() {
                            }.getType();
                            auditionsPojoList = gson.fromJson(serverResult, listType);*/
                            Gson gson = new Gson();
                            Type listType = new TypeToken<List<NewAuditionsPojo>>() {
                            }.getType();
                            newAuditionsPojoList = gson.fromJson(serverResult, listType);
                        }
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
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

        }

    }


    private void setAdapter(List<NewAuditionsPojo> auditionsPojoList) {

        if (auditionsPojoList == null) {
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    CustomizeDialog customizeDialog = new CustomizeDialog(getActivity());
                    customizeDialog.setTitle("nuvo");
                    customizeDialog.setCancelable(false);
                    customizeDialog.setMessage("Data not found.");
                    customizeDialog.show();
                }
            });

        } else {
            try {
                auditionsRecyclerView.setHasFixedSize(true);

                // use a linear layout manager
                mLayoutManager = new LinearLayoutManager(mContext);
                auditionsRecyclerView.setLayoutManager(mLayoutManager);

                // specify an adapter (see also next example)
                mAdapter = new AuditionsAdapter(mContext, newAuditionsPojoList);
             //   auditionsRecyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), LinearLayoutManager.VERTICAL));
                auditionsRecyclerView.setAdapter(mAdapter);


            } catch (Exception e) {
                e.printStackTrace();
            }


        }

    }

    public class DividerItemDecoration extends RecyclerView.ItemDecoration {

        private  final int[] ATTRS = new int[]{
                android.R.attr.listDivider
        };

        public static final int HORIZONTAL_LIST = LinearLayoutManager.HORIZONTAL;

        public static final int VERTICAL_LIST = LinearLayoutManager.VERTICAL;

        private Drawable mDivider;

        private int mOrientation;

        public DividerItemDecoration(Context context, int orientation) {
            final TypedArray a = context.obtainStyledAttributes(ATTRS);
            mDivider = a.getDrawable(0);
            a.recycle();
            setOrientation(orientation);
        }

        public void setOrientation(int orientation) {
            if (orientation != HORIZONTAL_LIST && orientation != VERTICAL_LIST) {
                throw new IllegalArgumentException("invalid orientation");
            }
            mOrientation = orientation;
        }

        @Override
        public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
            if (mOrientation == VERTICAL_LIST) {
                drawVertical(c, parent);
            } else {
                drawHorizontal(c, parent);
            }
        }

        public void drawVertical(Canvas c, RecyclerView parent) {
            final int left = parent.getPaddingLeft();
            final int right = parent.getWidth() - parent.getPaddingRight();

            final int childCount = parent.getChildCount();
            for (int i = 0; i < childCount; i++) {
                final View child = parent.getChildAt(i);
                final RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child
                        .getLayoutParams();
                final int top = child.getBottom() + params.bottomMargin;
                final int bottom = top + mDivider.getIntrinsicHeight();
                mDivider.setBounds(left, top, right, bottom);
                mDivider.draw(c);
            }
        }

        public void drawHorizontal(Canvas c, RecyclerView parent) {
            final int top = parent.getPaddingTop();
            final int bottom = parent.getHeight() - parent.getPaddingBottom();

            final int childCount = parent.getChildCount();
            for (int i = 0; i < childCount; i++) {
                final View child = parent.getChildAt(i);
                final RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child
                        .getLayoutParams();
                final int left = child.getRight() + params.rightMargin;
                final int right = left + mDivider.getIntrinsicHeight();
                mDivider.setBounds(left, top, right, bottom);
                mDivider.draw(c);
            }
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            if (mOrientation == VERTICAL_LIST) {
                outRect.set(0, 0, 0, mDivider.getIntrinsicHeight());
            } else {
                outRect.set(0, 0, mDivider.getIntrinsicWidth(), 0);
            }
        }
    }


    public class SimpleDividerItemDecoration extends RecyclerView.ItemDecoration {
        private Drawable mDivider;

        public SimpleDividerItemDecoration(Context context) {
            mDivider = null;
        }

        @Override
        public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
            int left = parent.getPaddingLeft();
            int right = parent.getWidth() - parent.getPaddingRight();

            int childCount = parent.getChildCount();
            for (int i = 0; i < childCount; i++) {
                View child = parent.getChildAt(i);

                RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();

                int top = child.getBottom() + params.bottomMargin;
                int bottom = top + mDivider.getIntrinsicHeight();

                mDivider.setBounds(left, 30, right, bottom);
                mDivider.draw(c);
            }
        }
    }
    private void setUpListeners() {
        auditionsRecyclerView.addOnItemTouchListener(new RecyclerItemClickListener(mContext, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                // do whatever
                //AuditionsPojo auditionsPojo = auditionsPojoList.get(position);
                NewAuditionsPojo auditionsPojo = newAuditionsPojoList.get(position);

                try {
                    Gson gson = new Gson();
                    String gsonString = gson.toJson(auditionsPojo);
                    constantData.setAuditionresponse(gsonString);
                    constantData.setSourceauditionartist("artistaudition");
                    constantData.setAuditionartistdetails("artistaudition");
                    lyAudiotion.removeAllViewsInLayout();
                    Fragment newFragment = new FragmentAudionDetails();
                    FragmentTransaction transaction = getFragmentManager().beginTransaction();
                    transaction.replace(R.id.fragment_audion_layout, newFragment);
                    transaction.addToBackStack(null);


                    transaction.commit();


                  /*  Intent intent = new Intent(mContext, AuditionDetailsActivity.class);
                    intent.putExtra("gsonString", gsonString);
                    startActivity(intent);
*/
                } catch (Exception e) {
                    e.printStackTrace();
                }
                //getActivity().finish();
            }
        }));

    }

}
