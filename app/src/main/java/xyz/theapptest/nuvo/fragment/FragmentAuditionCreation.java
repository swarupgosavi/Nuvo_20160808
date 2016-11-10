package xyz.theapptest.nuvo.fragment;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.TimePicker;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

import xyz.theapptest.nuvo.R;
import xyz.theapptest.nuvo.api.WebApiCall;
import xyz.theapptest.nuvo.services.VolleyWebserviceCall;
import xyz.theapptest.nuvo.services.VolleyWebserviceCallBack;
import xyz.theapptest.nuvo.ui.CustomizeDialog;
import xyz.theapptest.nuvo.ui.HttpClient;
import xyz.theapptest.nuvo.ui.ProducerHomeScreen;
import xyz.theapptest.nuvo.utils.ConstantData;
import xyz.theapptest.nuvo.utils.Dialogs;

import static android.app.Activity.RESULT_OK;

/**
 * Created by ADMIN on 8/6/2016.
 */
public class FragmentAuditionCreation extends Fragment implements View.OnClickListener, VolleyWebserviceCallBack {

    EditText ed_desc, ed_jobtitle;
    TextView tv_jobposting, tv_date, tv_attachment, tv_setdeadlines;
    Button bt_datepicket, bt_timepicker;
    ImageView imgfirst, imgsecond, imgthird;
    private int mYear, mMonth, mDay, mHour, mMinute, timer;
    //String urlpostaudion = "http://nuvo.theapptest.xyz/v1/api/auditions";
    String urlpostaudion = WebApiCall.baseURl + "job/saveandroid";
    private boolean permissionToRecordAccepted = false;
    private boolean permissionToWriteAccepted = false;
    private String[] permissions = {"android.permission.RECORD_AUDIO", "android.permission.WRITE_EXTERNAL_STORAGE"};
    public int REQUEST_TAKE_GALLERY_VIDEO;
    String selectedgallerypath;
    Uri selectedImageUri;
    CustomizeDialog customizeDialog = null;
    LinearLayout lvroot;
    android.support.v7.widget.Toolbar toolbar1;
    Button btnleft, btn;
    TextView txtPost, tv_title;
    ImageView imgehidedate;
    ConstantData constData;
    String str = "visible";
    LinearLayout lvjobposting;
    TextView tv_datetxt;
    ProgressDialog imgdialog;
   /* private int mYear;
    private int mmonth;
    private int mday;*/

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_createaudion, null);
        int requestCode = 200;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(permissions, requestCode);
        }
        constData = ConstantData.getInstance();
        init(rootView);
        setTypeface();
        //  webservicevedioupload();


        return rootView;
    }

    private void webservicevedioupload() {


        SendHttpRequestTask t = new SendHttpRequestTask();
        String jobtitle = ed_jobtitle.getText().toString();
        String decription = ed_desc.getText().toString();
        String tvdate = tv_date.getText().toString();
        if (tvdate.equals("")) {

        } else {
            tvdate = mYear + "-" + (mMonth + 1) + "-" + mDay + " " + mHour + ":" + mMinute + ":00";

        }
        String[] params = new String[]{urlpostaudion, jobtitle, decription, tvdate};
        t.execute(params);


    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 200:
                permissionToRecordAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                permissionToWriteAccepted = grantResults[1] == PackageManager.PERMISSION_GRANTED;
                break;
        }
        if (!permissionToRecordAccepted)
            Dialogs.showAlertcommon(getActivity(), "Permission", "Please allow us to record audio");
        if (!permissionToWriteAccepted)
            Dialogs.showAlertcommon(getActivity(), "Permission", "Please allow us to write audio");

    }

    private void uploadVideo(String videoPath) throws ParseException, IOException {

        DefaultHttpClient httpclient = new DefaultHttpClient();
        HttpPost httppost = new HttpPost(urlpostaudion);

        FileBody filebodyVideo = new FileBody(new File(videoPath));
        StringBody title = new StringBody("new job");
        StringBody description = new StringBody("This is a description of the video");
        StringBody expirydate = new StringBody("");
        StringBody attachment = new StringBody(videoPath);
        MultipartEntity reqEntity = new MultipartEntity();
        reqEntity.addPart("job_title", title);
        reqEntity.addPart("description", description);
        reqEntity.addPart("expiry_date", expirydate);
        reqEntity.addPart("attachment", attachment);
        httppost.setEntity(reqEntity);

        // DEBUG
        System.out.println("executing request " + httppost.getRequestLine());
        HttpResponse response = httpclient.execute(httppost);
        HttpEntity resEntity = response.getEntity();

        // DEBUG
        System.out.println(response.getStatusLine());
        if (resEntity != null) {
            System.out.println(EntityUtils.toString(resEntity));
        } // end if

        if (resEntity != null) {
            resEntity.consumeContent();
        } // end if

        httpclient.getConnectionManager().shutdown();
    }


    private class SendHttpRequestTask extends AsyncTask<String, Void, String> {
        ByteArrayOutputStream baos;
        String response;


        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected String doInBackground(String... params) {
            String url = params[0];
            Log.e("url", url);
            String job_title = params[1];
            String description = params[2];
            String tvdate = params[3];


            // Bitmap b = BitmapFactory.decodeResource(ImageUploadActivity.this.getResources(), userImagePath);
            try {

                baos = new ByteArrayOutputStream();
                HttpClient client = new HttpClient(url);
                client.connectForMultipart();


                client.addFormPart("job_title", job_title);
                client.addFormPart("description", description);
                client.addFormPart("expiry_date", "");

                Log.e("Artist List", "Checking Done");
                constData = ConstantData.getInstance();
                Log.e("Artist List:", constData.getSeletedArtists());
                if (!constData.getSeletedArtists().equals("")) {
                    client.addFormPart("artist_id", constData.getSeletedArtists());
                    constData.setselectedArtist(new ArrayList<String>());
                    client.addFormPart("type", "1");

                } else
                    client.addFormPart("type", "0");


                ByteArrayOutputStream baos = null;

                byte[] music = new byte[1];
                ;
                try {

                    byte[] soundBytes;

                    try {
                        InputStream inputStream = getActivity().getContentResolver().openInputStream(Uri.fromFile(new File(selectedgallerypath)));
                        soundBytes = new byte[inputStream.available()];

                        soundBytes = toByteArray(inputStream);
                        Log.e("File name-Size:", selectedgallerypath + "-" + Integer.toString(soundBytes.length));
                        String ext = "mp4";
                        String out = new SimpleDateFormat("yyyyMMddHHMMSS").format(new Date());
                        client.addFilePart("attachment", out + "." + ext, soundBytes);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } catch (Exception ioe) {
                    Log.e("Error: ", ioe.getMessage());
                    ioe.printStackTrace();
                } finally {

                }


//                  File file = new File(selectedgallerypath);
//                FileInputStream fin = null;
//                fin = new FileInputStream(file);
//               /* String mFileName = Environment.getExternalStorageDirectory().getAbsolutePath();
//                mFileName += "/audiorecordtest.3gp";
//                File file = new File(mFileName);
//                FileInputStream fin = null;
//                fin = new FileInputStream(file);*/
//
//                // soundBytes = new byte[inputStream.available()];
//
//                //soundBytes = toByteArray(inputStream);
//
//                // InputStream inStream = getActivity().getResources().openRawResource(R.raw.song);
//
//                byte[] music = new byte[fin.available()];
//                music = toByteArray(fin);
//
//                String out = new SimpleDateFormat("yyyy-MM-dd_hh:mm:ss").format(new Date());
//
//                client.addFilePart("attachment", "abc" + ".mp4", music);
//
//                //File file = new File(selectedgallerypath);
//
//
////                FileInputStream fin = null;
////                fin = new FileInputStream(file);
////
////
////                byte[] music = new byte[fin.available()];
////                music = toByteArray(fin);
////
////                String out = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
////
////              client.addFilePart("attachment", "abc" + ".mp4", music);
//
//
//                //   client.addFilePart("attachment", out, music);


                client.finishMultipart();


                response = client.getResponse();

            } catch (Throwable t) {
                imgdialog.dismiss();
                t.printStackTrace();
            }

            return null;
        }

        public byte[] toByteArray(InputStream in) throws IOException {
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            int read = 0;
            byte[] buffer = new byte[1024];
            while (read != -1) {
                read = in.read(buffer);
                if (read != -1)
                    out.write(buffer, 0, read);
            }
            out.close();
            return out.toByteArray();
        }

        @Override
        protected void onPostExecute(String data) {

            imgdialog.dismiss();


            if (response != null && !response.isEmpty()) {
                Log.e("response", response);

                customizeDialog = new CustomizeDialog(getActivity());
                customizeDialog.setTitle("nuvo");
                customizeDialog.setCancelable(false);
                customizeDialog.setMessage("Audition job posted.");
                customizeDialog.show();

                customizeDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialog) {
                        onBackPressed();
                    }
                });


            } else {
                customizeDialog = new CustomizeDialog(getActivity());
                customizeDialog.setTitle("nuvo");
                customizeDialog.setCancelable(false);
                customizeDialog.setMessage("Recording file not present.");
                customizeDialog.show();

            }


        }


    }


    private void setTypeface() {
        Typeface facetxtsiginbelow = Typeface.createFromAsset(getActivity().getAssets(),
                "font/OpenSans-Light.ttf");

        Typeface facetxtsigin = Typeface.createFromAsset(getActivity().getAssets(),
                "font/OpenSans-Semibold.ttf");
        tv_setdeadlines.setTypeface(facetxtsigin);
        tv_attachment.setTypeface(facetxtsigin);


        Typeface facetxtsigintextbox = Typeface.createFromAsset(getActivity().getAssets(),
                "font/OpenSans-Regular.ttf");

        Typeface facetxtsigintextboxroboto = Typeface.createFromAsset(getActivity().getAssets(),
                "font/Roboto-Regular.ttf");

        ed_jobtitle.setTypeface(facetxtsiginbelow);
        ed_desc.setTypeface(facetxtsiginbelow);
        tv_jobposting.setTypeface(facetxtsiginbelow);
        tv_date.setTypeface(facetxtsiginbelow);


    }

    public void init(View rootView) {
        lvroot = (LinearLayout) rootView.findViewById(R.id.fragment_createaudi);
        tv_date = (TextView) rootView.findViewById(R.id.tv_date);
        ProducerHomeScreen act = (ProducerHomeScreen) getActivity();
        lvjobposting = (LinearLayout) rootView.findViewById(R.id.rootjobposting);

        // act.getActionBar().setDisplayShowHomeEnabled(true);
        toolbar1 = (android.support.v7.widget.Toolbar) act.findViewById(R.id.toolbar_top);
        toolbar1.setNavigationIcon(null);
        btn = (Button) toolbar1.findViewById(R.id.btn);
        btnleft = (Button) toolbar1.findViewById(R.id.btnleft);
        btn.setVisibility(View.GONE);
        tv_title = (TextView) toolbar1.findViewById(R.id.toolbar_title);
        tv_title.setText("New Job");
        txtPost = (TextView) toolbar1.findViewById(R.id.rightlabel);
        txtPost.setText("Post ");
        txtPost.setVisibility(View.VISIBLE);
        imgehidedate = (ImageView) rootView.findViewById(R.id.hidedatedialog);

        imgehidedate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (str.equalsIgnoreCase("visible")) {
                    bt_datepicket.setVisibility(View.GONE);
                    bt_timepicker.setVisibility(View.GONE);
                    str = "hide";
                    lvjobposting.setVisibility(View.GONE);

                } else {
                    bt_datepicket.setVisibility(View.VISIBLE);
                    bt_timepicker.setVisibility(View.VISIBLE);
                    str = "visible";
                    lvjobposting.setVisibility(View.VISIBLE);
                }
            }
        });

        // ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayShowHomeEnabled(false);
        //  context.getActionBar().setDisplayShowHomeEnabled(false);

        btnleft.setVisibility(View.VISIBLE);
        btnleft.setBackgroundResource(R.drawable.backimg);
        btnleft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();


            }
        });


        txtPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ed_jobtitle.getText().toString().isEmpty()) {
                    CustomizeDialog customizeDialog = new CustomizeDialog(getActivity());
                    customizeDialog.setTitle("nuvo");
                    customizeDialog.setCancelable(false);
                    customizeDialog.setMessage("Please enter job title.");
                    customizeDialog.show();
                } else {
                    if (ed_desc.getText().toString().isEmpty()) {
                        CustomizeDialog customizeDialog = new CustomizeDialog(getActivity());
                        customizeDialog.setTitle("nuvo");
                        customizeDialog.setCancelable(false);
                        customizeDialog.setMessage("Please enter job description");
                        customizeDialog.show();
                    } else {
                        imgdialog = new ProgressDialog(getActivity(), ProgressDialog.THEME_HOLO_LIGHT);
                        imgdialog.setMessage("Please Wait!!");
                        imgdialog.setCancelable(false);
                        imgdialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                        imgdialog.show();

                        webservicevedioupload();
                    }
                }


            }
        });

        ed_desc = (EditText) rootView.findViewById(R.id.ed_description);
        ed_jobtitle = (EditText) rootView.findViewById(R.id.ed_jobtitle);
        tv_jobposting = (TextView) rootView.findViewById(R.id.tv_jobposting);
        tv_date = (TextView) rootView.findViewById(R.id.tv_date);
        tv_attachment = (TextView) rootView.findViewById(R.id.tv_attachment);
        imgfirst = (ImageView) rootView.findViewById(R.id.imgfirst);
        imgfirst.setOnClickListener(this);
        imgsecond = (ImageView) rootView.findViewById(R.id.imgsecond);
        imgsecond.setOnClickListener(this);
        imgthird = (ImageView) rootView.findViewById(R.id.imgthird);
        imgthird.setOnClickListener(this);
        bt_datepicket = (Button) rootView.findViewById(R.id.bt_datepicket);
        bt_datepicket.setOnClickListener(this);

        bt_timepicker = (Button) rootView.findViewById(R.id.bt_timepicker);
        bt_timepicker.setOnClickListener(this);
        tv_attachment = (TextView) rootView.findViewById(R.id.tv_attachment);
        tv_setdeadlines = (TextView) rootView.findViewById(R.id.tv_setdeadlines);

    }

    private void onBackPressed() {
        constData.setselectedArtist(new ArrayList<String>());

        if (constData.getSource_flag().equals("Find")) {
            btnleft.setVisibility(View.GONE);
            toolbar1.setNavigationIcon(R.drawable.menu_icon);
            lvroot.removeAllViews();
            Fragment newFragment = new Fragment_Criteria();
            FragmentTransaction transaction = getFragmentManager().beginTransaction();

            transaction.replace(R.id.lvproducerfind, newFragment);
            transaction.addToBackStack(null);
            transaction.commitAllowingStateLoss();

        } else {
            lvroot.removeAllViews();
            Fragment newFragment = new Fragment_audion_producer();
            FragmentTransaction transaction = getFragmentManager().beginTransaction();

            transaction.replace(R.id.fragment_createaudi, newFragment);
            transaction.addToBackStack(null);
            transaction.commitAllowingStateLoss();

        }
    }


    public String getPath(Uri uri) {
        String[] projection = {MediaStore.Images.Media.DATA};
        Cursor cursor = getActivity().managedQuery(uri, projection, null, null, null);
        if (cursor != null) {
            // HERE YOU WILL GET A NULLPOINTER IF CURSOR IS NULL
            // THIS CAN BE, IF YOU USED OI FILE MANAGER FOR PICKING THE MEDIA
            int column_index = cursor
                    .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        } else
            return null;
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_TAKE_GALLERY_VIDEO) {
                selectedImageUri = data.getData();


                // OI FILE Manager
                //selectedgallerypath = selectedImageUri.getPath();

                // MEDIA GALLERY
                selectedgallerypath = getPath(selectedImageUri);

            }
        }
    }


    public byte[] getBytes(InputStream inputStream) throws IOException {
        ByteArrayOutputStream byteBuffer = new ByteArrayOutputStream();
        int bufferSize = 1024;
        byte[] buffer = new byte[bufferSize];

        int len = 0;
        while ((len = inputStream.read(buffer)) != -1) {
            byteBuffer.write(buffer, 0, len);
        }

        return byteBuffer.toByteArray();
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_datepicket:

                // Get Current Date
                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);


                DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(),
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {

                                // bt_datepicket.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

                                mYear = year;
                                mMonth = monthOfYear;
                                mDay = dayOfMonth;

                                Log.e("date", Integer.toString(dayOfMonth));

                                SimpleDateFormat newformat = new SimpleDateFormat("MMM");
                                try {
                                    SimpleDateFormat oldformat = new SimpleDateFormat("MM");
                                    Date myDate = oldformat.parse(String.valueOf(mMonth + 1));
                                    String monthName = newformat.format(myDate);
                                    Log.e("monthName", monthName);
                                    bt_datepicket.setText(monthName + " " + Integer.toString(dayOfMonth));
                                    tv_date.setText(bt_datepicket.getText().toString() + " " + mHour + ":" + mMinute);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }

                            }
                        }, mYear, mMonth, mDay) {
                    @Override
                    public void onDateChanged(DatePicker view, int year, int month, int dayOfMonth) {
                        super.onDateChanged(view, year, month, dayOfMonth);
                        if (year < mYear)
                            view.updateDate(mYear, mMonth, mDay);

                        if (month < mMonth && year == mYear)
                            view.updateDate(mYear, mMonth, mDay);

                        if (dayOfMonth < mDay && year == mYear && month == mMonth)
                            view.updateDate(mYear, mMonth, mDay);

                    }
                };
                datePickerDialog.show();

                break;
            case R.id.bt_timepicker:
                final Calendar c1 = Calendar.getInstance();
                mHour = c1.get(Calendar.HOUR);
                mMinute = c1.get(Calendar.MINUTE);
                if (c1.get(Calendar.AM_PM) == 0)
                    Log.e("time", "AM");

                else
                    Log.e("time", "PM");

                //     timer = c1.get(Calendar.);

                // Launch Time Picker Dialog
                TimePickerDialog timePickerDialog = new TimePickerDialog(getActivity(),
                        new TimePickerDialog.OnTimeSetListener() {

                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay,
                                                  int minute) {

                                //  bt_timepicker.setText(hourOfDay + ":" + minute);
                                mHour = hourOfDay;
                                mMinute = minute;
                                String curTime = String.format("%02d:%02d", mHour, mMinute);
                                bt_timepicker.setText(curTime);
                                tv_date.setText(bt_datepicket.getText().toString() + " at  " +curTime);
                                Log.e("time", mHour + " : " + mMinute);

                            }
                        }, mHour, mMinute, false);


                timePickerDialog.show();


                break;
            case R.id.imgsecond:
                Intent intent = new Intent();
                intent.setType("video/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Video"), REQUEST_TAKE_GALLERY_VIDEO);


                break;

            case R.id.imgthird:
                Intent intent1 = new Intent();
                intent1.setType("video/*");
                intent1.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent1, "Select Video"), REQUEST_TAKE_GALLERY_VIDEO);


                break;

            case R.id.imgfirst:
                Intent intent2 = new Intent();
                intent2.setType("video/*");
                intent2.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent2, "Select Video"), REQUEST_TAKE_GALLERY_VIDEO);


                break;

        }

    }

    /*private void getProfile() {

      //  String getProfileUrl = Constants.getProfileUrl + "/" + speId;
        new VolleyWebserviceCall().volleyGetCall(mContext, getProfileUrl, this, true);

    }
*/

    private TimePickerDialog.OnTimeSetListener timePickerListener = new TimePickerDialog.OnTimeSetListener() {


        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minutes) {
            // TODO Auto-generated method stub

            updateTime(hourOfDay, minutes);

        }

    };

    private void updateTime(int hr, int mMinute) {
        bt_timepicker.setText(hr + " : " + mMinute);
    }

    @Override
    public void onSuccess(String serverResult, String requestTag, int statusCode) {

    }

    @Override
    public void onError(String serverResult, String requestTag, int statusCode) {

    }


}








