package xyz.theapptest.nuvo.api;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NetworkResponse;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import xyz.theapptest.nuvo.services.AppController;
import xyz.theapptest.nuvo.services.VolleyWebserviceCallBack;
import xyz.theapptest.nuvo.utils.Dialogs;
import xyz.theapptest.nuvo.utils.Util;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by trtcpu007 on 11/7/16.
 */

public class WebApiMethod {

   static ProgressDialog pd = null;
  static  int mStatusCode;

    public static void volleyPostCall(final Context context, final String url,
                               final HashMap<String, String> parameter,
                               final VolleyWebserviceCallBack callBack,
                               final Boolean showProgressDialog) {

        ((Activity) context).setProgressBarIndeterminateVisibility(true);

        String tag_json_obj = context.getClass().getSimpleName(); // tag will be
        // requied
        // to

        if (showProgressDialog) {
            pd = Dialogs.ShowProgressDialog(context);
            pd.show();
        }

        StringRequest gsonRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String serverJson) {
                        ((Activity) context)
                                .setProgressBarIndeterminateVisibility(false);
                        callBack.onSuccess(serverJson, url, mStatusCode);
                        if (pd != null && pd.isShowing()) {
                            pd.dismiss();
                        }
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                try {
                    String json = null;
                    NetworkResponse response = error.networkResponse;
                    if (response != null && response.data != null) {
                        switch (response.statusCode) {
                            case 400:
                                json = new String(response.data);
                                //    json = trimMessage(json, "ErrorMessage");

                        }
                    }
                    ((Activity) context)
                            .setProgressBarIndeterminateVisibility(false);
                    error.printStackTrace();
                    // all errors will be recieved here
                    boolean isAuthErr = false;
                    if (error instanceof NetworkError) {
                        Dialogs.showAlert(context, "Server Error!",
                                "Please try after some time.", isAuthErr);
                    } else if (error instanceof ServerError) {
                    } else if (error instanceof AuthFailureError) {
                    } else if (error instanceof ParseError) {
                    } else if (error instanceof NoConnectionError) {
                    } else if (error instanceof TimeoutError) {

                        Dialogs.showAlert(context, "Server Error!",
                                "Please try after some time.", isAuthErr);
                    }
                    //    if (showProgressDialog) {
                    if (pd != null && pd.isShowing()) {
                        pd.dismiss();
                    }
                    //    }
                    //    callBack.onError(error.getMessage(), url, mStatusCode);
                    callBack.onError(json, url, mStatusCode);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params = parameter;
                Log.e("parameter",params +"");
                Util.printLog("VolleyWebServiceCall Params : ", params + "");
                return params;
            }

            // method to check status code from server
            @Override
            protected Response<String> parseNetworkResponse(NetworkResponse response) {

                mStatusCode = response.statusCode;
                return super.parseNetworkResponse(response);
            }


        };

        // Adding request to request queue
        AppController.getInstance()
                .addToRequestQueue(gsonRequest, tag_json_obj);
    }


}
