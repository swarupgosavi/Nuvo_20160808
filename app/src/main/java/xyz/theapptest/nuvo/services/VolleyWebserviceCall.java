package xyz.theapptest.nuvo.services;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkError;
import com.android.volley.NetworkResponse;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request.Method;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import xyz.theapptest.nuvo.utils.Dialogs;
import xyz.theapptest.nuvo.utils.Util;


/*
 * @author Shrikant
 *Volley webservice calls done here
 * 
 * */
public class VolleyWebserviceCall {

    /*
     * Post request
     *
     * @param Context
     *
     * @param URL to server
     *
     * @param HAshmap of keys and values to be passed with service
     *
     * @param Callback
     */
    ProgressDialog pd = null;
    int mStatusCode;

    public void volleyPostCall(final Context context, final String url,
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

        StringRequest gsonRequest = new StringRequest(Method.POST, url,
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

    /*
     * GET request
     *
     * @param Context
     *
     * @param URL to server
     *
     * @param Callback
     */
    public void volleyGetCall(final Context context, final String url,
                              final VolleyWebserviceCallBack callBack, final boolean showProgressDialog) {

        ((Activity) context).setProgressBarIndeterminateVisibility(true);
        String tag_json_obj = context.getClass().getSimpleName(); // tag will be
        // requied
        // to
        if (showProgressDialog) {
            pd = Dialogs.ShowProgressDialog(context);
            pd.show();
        }


        StringRequest gsonRequest = new StringRequest(url,
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String serverJson) {
                        ((Activity) context)
                                .setProgressBarIndeterminateVisibility(false);
                        if (pd != null && pd.isShowing()) {
                            pd.dismiss();
                        }

                        callBack.onSuccess(serverJson, url, mStatusCode);
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                ((Activity) context)
                        .setProgressBarIndeterminateVisibility(false);
                error.printStackTrace();
                callBack.onError(error.getMessage(), url, mStatusCode);

                // all errors will be recieved here
                boolean isAuthErr = false;
                if (pd != null && pd.isShowing()) {
                    pd.dismiss();
                }

                if (error instanceof NetworkError) {

                    Dialogs.showAlert(context, "Network Error!",
                            "Please try after some time", isAuthErr);
                } else if (error instanceof ServerError) {
                } else if (error instanceof AuthFailureError) {
                } else if (error instanceof ParseError) {
                } else if (error instanceof NoConnectionError) {
                } else if (error instanceof TimeoutError) {
                    Dialogs.showAlert(context, "Network Error!",
                            "Please try after some time, due to slow connection", isAuthErr);

                }

            }
        });

        // Adding request to request queue
        AppController.getInstance()
                .addToRequestQueue(gsonRequest, tag_json_obj);

    }

    /**
     * The default socket timeout in milliseconds
     */
    public static final int MY_SOCKET_TIMEOUT_MS = 10000;

    /**
     * The default number of retries
     */
    public static final int DEFAULT_MAX_RETRIES = 1;

    /**
     * The default backoff multiplier
     */
    public static final float DEFAULT_BACKOFF_MULT = 1f;

/*
    public void multipartCall(final Context mContext,
                              final String webserviceName,
                              final VolleyWebserviceCallBack callBack,
                              HashMap<String, File> mFileUploads, HashMap<String, String> mDtata, final boolean showProgressDialog) {
        ((Activity) mContext).setProgressBarIndeterminateVisibility(true);
        String tag_json_obj = mContext.getClass().getSimpleName();
        if (showProgressDialog) {
            pd = Dialogs.ShowProgressDialog(mContext);
            pd.show();
        }

        Util.printLog("VolleyWebServiceCall Params : ",
                mFileUploads.toString() + " : " + mDtata.toString());
        MultipartRequest mpR = new MultipartRequest(webserviceName,
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        ((Activity) mContext)
                                .setProgressBarIndeterminateVisibility(false);
                        if (pd != null && pd.isShowing()) {
                            pd.dismiss();
                        }

                        error.printStackTrace();
                    }
                }, new Response.Listener<String>() {
            @Override
            public void onResponse(String serverJson) {
                ((Activity) mContext)
                        .setProgressBarIndeterminateVisibility(false);
                if (pd != null && pd.isShowing()) {
                    pd.dismiss();
                }

                callBack.onSuccess(serverJson, webserviceName, mStatusCode);
            }
        }, mDtata, mFileUploads);

        @Override
        protected Response<String> parseNetworkResponse(NetworkResponse response) {
            mStatusCode = response.statusCode;
            return super.parseNetworkResponse(response);
        }
        mpR.setRetryPolicy(new DefaultRetryPolicy(MY_SOCKET_TIMEOUT_MS,
                DEFAULT_MAX_RETRIES, DEFAULT_BACKOFF_MULT));


        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(mpR, tag_json_obj);

    }
*/

    public void  multipartCall(final Context mContext, final String webserviceName, final VolleyWebserviceCallBack callBack,
                               HashMap<String, File> mFileUploads,  HashMap<String, String> mDtata, final Boolean showProgressDialog) {

        try {
            ((Activity) mContext).setProgressBarIndeterminateVisibility(true);
           // mContext.getApplicationContext().setP
            String tag_json_obj = mContext.getClass().getSimpleName();
            Util.printLog("WebService Tag", "WebService Tag " + tag_json_obj);
            Util.printLog("VolleyWebServiceCall Params : ",mFileUploads.toString()+" : "+mDtata.toString());
            if(showProgressDialog){
                pd = Dialogs.ShowProgressDialog(mContext);
                pd.show();
            }

            MultipartRequest mpR = new MultipartRequest(webserviceName,
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            ((Activity) mContext)
                                    .setProgressBarIndeterminateVisibility(false);
                            if (pd != null && pd.isShowing()) {
                                pd.dismiss();
                            }
                            String json = null;
                            NetworkResponse response = error.networkResponse;
                            if (response != null && response.data != null) {
                                mStatusCode = error.networkResponse.statusCode;

                                switch (response.statusCode) {
                                    case 400:
                                        json = new String(response.data);
                                        //    json = trimMessage(json, "ErrorMessage");
                                    case 401:
                                        json = new String(response.data);
                                }
                            }

                            Util.cancelWebserviceRequest(mContext);
                           // callBack.onSuccess(error.getMessage(), webserviceName, mStatusCode);
                            callBack.onError(json, webserviceName, mStatusCode);
                            error.printStackTrace();
                        }
                    },
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String serverJson) {
                            if (pd != null && pd.isShowing()) {
                                pd.dismiss();
                            }
                            ((Activity) mContext).setProgressBarIndeterminateVisibility(false);
                            callBack.onSuccess(serverJson, webserviceName, mStatusCode);
                        }
                    }, mDtata, mFileUploads){
                // method to check status code from server
                @Override
                protected Response<String> parseNetworkResponse(NetworkResponse response) {
                    mStatusCode = response.statusCode;
                    return super.parseNetworkResponse(response);
                }
            };

            mpR.setRetryPolicy(new DefaultRetryPolicy(
                    MY_SOCKET_TIMEOUT_MS,
                    DEFAULT_MAX_RETRIES,
                    DEFAULT_BACKOFF_MULT));

            // Adding request to request queue
            AppController.getInstance().addToRequestQueue(mpR, tag_json_obj);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    // method to check status code from server
  /*  @Override
    protected Response<String> parseNetworkResponse(NetworkResponse response) {
        mStatusCode = response.statusCode;
        return super.parseNetworkResponse(response);
    }
*/
}
