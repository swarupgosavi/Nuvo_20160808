package xyz.theapptest.nuvo.fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;

import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import xyz.theapptest.nuvo.R;
import xyz.theapptest.nuvo.api.WebApiCall;
import xyz.theapptest.nuvo.ui.ArtistHomescreen;
import xyz.theapptest.nuvo.ui.CustomizeDialog;
import xyz.theapptest.nuvo.ui.HomeActivity;
import xyz.theapptest.nuvo.ui.ProducerHomeScreen;
import xyz.theapptest.nuvo.utils.ConstantData;
import xyz.theapptest.nuvo.utils.Dialogs;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import xyz.theapptest.nuvo.ui.HttpClient;

/**
 * Created by trtcpu007 on 8/7/16.
 */

public class Fragment_Profile_Recorded extends Fragment implements View.OnClickListener {
    TextView tv_recordedmethod, tv_recordeddemo, tv_attachedvoice;
    Button bt_isdn, bt_sorceconnect, bt_studio, bt_next, bt_back;
    ImageView img_record, img_play, img_detete;
    private MediaRecorder myAudioRecorder;

    private static final int MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE = 29;
    public int recordoption = 0;
    private MediaRecorder mediaRecorder;
    boolean recording = false;
    LinearLayout layout;
    public int recordingid = -1;
    private static final String LOG_TAG = "AudioRecordTest";
    private static String mFileName = null;

    Button btn;
    TextView tv;
    private MediaRecorder mRecorder = null;

    ConstantData constData;
    private MediaPlayer mPlayer = null;
    boolean mStartRecording = true;
    boolean mStartPlaying = true;
    private boolean permissionToRecordAccepted = false;
    private boolean permissionToWriteAccepted = false;
    private String[] permissions = {"android.permission.RECORD_AUDIO", "android.permission.WRITE_EXTERNAL_STORAGE"};
    CustomizeDialog customizeDialog = null;
    ArrayList<HashMap<String, String>> recordedemethod;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profilerecord, container, false);
        init(view);
        typeInterface();
        Onclickevent();
        getButtonValue();
        int requestCode = 200;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(permissions, requestCode);
        }
        mFileName = Environment.getExternalStorageDirectory().getAbsolutePath();
        mFileName += "/audiorecordtest.3gp";

        return view;
    }

    private void getButtonValue() {
        constData = ConstantData.getInstance();
        recordedemethod = constData.getRecording_methods();
        for (int i = 0; i < recordedemethod.size(); i++) {


            btn = new Button(getActivity());
            btn.setBackgroundResource(R.drawable.button_selected);
            btn.setTextSize(12);

            Typeface facetxtsigintextbox = Typeface.createFromAsset(getActivity().getAssets(),
                    "font/OpenSans-Regular.ttf");
            btn.setTypeface(facetxtsigintextbox);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

            Log.e("recordingvalue", recordedemethod.get(i).get("recording_method_value"));
            btn.setId(Integer.parseInt(recordedemethod.get(i).get("id")) + 10);
            btn.setTag(Integer.parseInt(recordedemethod.get(i).get("recording_method_value")));

            //  btn.setMinHeight(-2);
            params.weight = 1.5f;
            params.gravity = Gravity.LEFT;
            params.gravity = Gravity.RIGHT;

            DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
            // int px1 = Math.round(140 * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
            //  params.width = px1;
            int px = Math.round(39 * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
            params.height = px;
            btn.setTransformationMethod(null);

            btn.setPadding(3, 0, 3, 1);
            params.setMargins(5, 5, 15, 2);
            btn.setLayoutParams(params);
            //   btn.setLayoutParams(new LinearLayout.LayoutParams(70, 30));
            btn.setTextColor(getActivity().getApplication().getResources().getColor(R.color.white));
            btn.setText(recordedemethod.get(i).get("recording_method"));
            //btn.setBackgroundResource(R.drawable.button_selected);
            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int IDS = v.getId() - 10;
                    Button o = (Button) v;
                    LinearLayout l = (LinearLayout) v.getParent();

                    for (int k = 11; k < 14; k++) {
                        o = (Button) l.findViewById(k);
                        o.setSelected(false);
                        Log.e("k", Integer.toString(k));
                    }
                    o = (Button) v;
                    o.setSelected(true);
                    recordingid = (Integer) o.getTag();
                    constData.setRecordingvalue(recordingid);
                    Log.e("age-value", Integer.toString(recordingid));
                    // constData.setAgevalue(age);
                    //print o.getTag()

                    //Toast.makeText(getActivity(), o.getTag(), Toast.LENGTH_LONG).show();
                }
            });

            layout.addView(btn, params);


        }
    }

    private void getallvalues() {
        try {
            constData = ConstantData.getInstance();
            String firstname = constData.getFirstname();
            String lastname = constData.getLastname();
            String phonenumber = constData.getPhonenumber();
            String token = constData.getTokenid();
            Log.e("firstname", firstname);
            Log.e("lastname", lastname);
            Log.e("phonennumber", phonenumber);
            Log.e("token", token);

            int gvalue = constData.getGvalue();
            Log.e("gvalue", Integer.toString(gvalue));
            int langvalue = constData.getLvaue();
            Log.e("langvalue", Integer.toString(langvalue));
            int agevalue = constData.getAgevalue();
            Log.e("agevalue", Integer.toString(agevalue));

            String email = constData.getEmail();
            String password = constData.getPassword();
            Log.e("email", email);
            Log.e("password", password);


            String phonennumber = constData.getPhonenumber();
            Log.e("phonennumber", phonennumber);


            String description = constData.getDescription();
            Log.e("description", constData.getDescription());


            String userid = constData.getUserid();
            Log.e("userid", userid);

            int characteristicvalue = constData.getCharacteristicvalue();
            Log.e("characteristicvalue", Integer.toString(characteristicvalue));

            int jobcategoryvalue = constData.getJobcategoryvalue();
            Log.e("jobcategoryvalue", Integer.toString(jobcategoryvalue));

            int recordingvalue = constData.getRecordingvalue();
            Log.e("recordingvalue", Integer.toString(recordingvalue));


        } catch (Exception e) {
            e.printStackTrace();
        }


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

    /*  @Override
      public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[], @NonNull int[] grantResults) {
          switch (requestCode) {
              case MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE: {
                  if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                      Log.d("Home", "Permission Granted");

                  } else {
                      Log.d("Home", "Permission Failed");
                      Toast.makeText(getActivity().getBaseContext(), "You must allow permission record audio to your mobile device.", Toast.LENGTH_SHORT).show();
                      getActivity().finish();
                  }
              }
              // Add additional cases for other permissions you may have asked for
          }
      }


      private void recordingLogic() {
          try {


              mRecorder = new MediaRecorder();
              mRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
              mRecorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);
              mRecorder.setOutputFile(getActivity().getFilesDir() + "/audio.m4a");
              mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AAC);

              try {
                  mRecorder.prepare();
              } catch (IOException e) {

              }

              mRecorder.start();
          } catch (Exception e) {
              e.printStackTrace();
          }


      }
  */
    @Override
    public void onPause() {
        super.onPause();
        if (mRecorder != null) {
            mRecorder.release();
            mRecorder = null;
        }

        if (mPlayer != null) {
            mPlayer.release();
            mPlayer = null;
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
        //  bt_isdn.setTypeface(facetxtsigintextbox);
        //   bt_sorceconnect.setTypeface(facetxtsigintextbox);
        //   bt_studio.setTypeface(facetxtsigintextbox);

        Typeface facetxtsigin = Typeface.createFromAsset(getActivity().getAssets(),
                "font/OpenSans-Semibold.ttf");
        tv_recordedmethod.setTypeface(facetxtsigin);
        tv_recordeddemo.setTypeface(facetxtsigin);
        tv_attachedvoice.setTypeface(facetxtsigin);
        bt_back.setTypeface(facetxtsigin);
        bt_next.setTypeface(facetxtsigin);


    }

    private void Onclickevent() {
        bt_next.setOnClickListener(this);
        bt_back.setOnClickListener(this);
        img_record.setOnClickListener(this);
        img_play.setOnClickListener(this);
        //     img_stop.setOnClickListener(this);
        //  bt_studio.setOnClickListener(this);
        //  bt_isdn.setOnClickListener(this);
        //  bt_sorceconnect.setOnClickListener(this);
        img_detete.setOnClickListener(this);

    }

    private void startRecording() {
        mRecorder = new MediaRecorder();
        mRecorder.reset();
        mRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mRecorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);
        mRecorder.setOutputFile(mFileName);
        mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AAC);

        try {
            mRecorder.prepare();
        } catch (IOException e) {
            Log.e(LOG_TAG, "prepare() failed");
        }

        mRecorder.start();

    }

    private void stopRecording() {

        mRecorder.stop();
        mRecorder.release();
        mRecorder = null;
    }


    private void onRecord(boolean start) {
        if (start) {
            startRecording();
        } else {
            stopRecording();
        }
    }

    private void onPlay(boolean start) {
        if (start) {
            startPlaying();
        } else {
            stopPlaying();
        }
    }

    private void startPlaying() {
        File file = new File(mFileName);

        if (file.exists()) {
            mPlayer = new MediaPlayer();
            try {
                mPlayer.setDataSource(mFileName);
                mPlayer.prepare();
                mPlayer.start();
               /* if(mPlayer.isPlaying())
                {
                    //stop or pause your media player mediaPlayer.stop(); or mediaPlayer.pause();
                    img_play.setImageResource(R.drawable.pause_button);
                    mPlayer.pause();
                }
                else
                {
                    img_play.setImageResource(R.drawable.play_button);
                    mPlayer.start();
                    mStartPlaying=true;
                }*/

                mPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mp) {
                        img_play.setImageResource(R.drawable.play_button);
                    }
                });
            } catch (IOException e) {
                Log.e(LOG_TAG, "prepare() failed");

            }


        } else {

            Dialogs.showAlertcommon(getActivity(), "Error", "Recording file not available");
        }
    }

    private void stopPlaying() {
        mPlayer.release();
        mPlayer = null;
    }

    @Override
    public void onClick(View v) {


        switch (v.getId())

        {
            case R.id.back:
                constData = ConstantData.getInstance();
                constData.setLangauge("");
                constData.setEmail("");
                constData.setGender("");
                someEventListener.someEvent(0);
                break;
            case R.id.next:
                // someEventListener.someEvent(3);
                getallvalues();
                File file = new File(mFileName);
                //   boolean deleted = file.delete();


                if (recordingid > -1) {
                    if (mFileName != null) {
                        uploadaudiofileusingmultipart();

                    } else {
                        Log.e("no file", "no file");
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                customizeDialog = new CustomizeDialog(getActivity());
                                customizeDialog.setTitle("nuvo");
                                customizeDialog.setCancelable(false);
                                customizeDialog.setMessage("Recording file not present.");
                                customizeDialog.show();
                            }
                        });

                    }
                } else {
                    Log.e("no file", "no file");
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            customizeDialog = new CustomizeDialog(getActivity());
                            customizeDialog.setTitle("nuvo");
                            customizeDialog.setCancelable(false);
                            customizeDialog.setMessage("Please Select Recording Methods.");
                            customizeDialog.show();
                        }
                    });


                }


                break;

            case R.id.audiorecord:


                onRecord(mStartRecording);
                if (mStartRecording) {
                    //    Toast.makeText(getActivity(), "Recording started", Toast.LENGTH_LONG).show();
                    img_record.setImageResource(R.drawable.recording_audio);
                } else {
                    //   Toast.makeText(getActivity(), "Recording stopped", Toast.LENGTH_LONG).show();
                    img_record.setImageResource(R.drawable.music_icon);

                   /* new AlertDialog.Builder(getActivity())
                            .setTitle("Success")
                            .setMessage("Recording Completed.")
                            .setPositiveButton(android.R.string.ok,
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog,
                                                            int which) {
                                            mStartRecording = true;
                                            img_record.setImageResource(R.drawable.music_icon);
                                            dialog.dismiss();

                                        }
                                    }).show();*/
                }
                mStartRecording = !mStartRecording;

                break;

            case R.id.audiorecordplay:
                try {
                    onPlay(mStartPlaying);
                    if (mStartPlaying) {
                        // Toast.makeText(getActivity(), "Start playing", Toast.LENGTH_LONG).show();
                        img_play.setImageResource(R.drawable.pause_button);
                        //  img_play.setImageResource(R.drawable.play_button);
                        Log.e("mStartPlaying", Boolean.toString(mStartPlaying));
                    } else {
                        //  Toast.makeText(getActivity(), "Stop playing", Toast.LENGTH_LONG).show();
                        img_play.setImageResource(R.drawable.pause_button);

                        Log.e("mStartPlaying", Boolean.toString(mStartPlaying));
                    }

                    //  mStartPlaying = !mStartPlaying;

                } catch (Exception e) {

                    e.printStackTrace();
                }

                break;

            case R.id.audiorecorddelete:

                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

                builder.setTitle("Delete");
                builder.setMessage("Are you sure want delete recorded file?");

                builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int which) {
                        // Do nothing but close the dialog
                        File file = new File(mFileName);
                        boolean deleted = file.delete();
                        Log.e("delete file", Boolean.toString(deleted));

                        dialog.dismiss();
                    }
                });

                builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        // Do nothing
                        dialog.dismiss();
                    }
                });

                AlertDialog alert = builder.create();
                alert.show();

                break;
            case 1:

                // btn.setBackgroundResource(R.drawable.btn_color_dark);
                // recordoption = 1;
                Log.e("first button", "first button");
                break;
            case 2:
                //  btn.setBackgroundResource(R.drawable.btn_color_dark);
                //   recordoption = 1;
                Log.e("second button", "second button");
                break;
            case 3:
                //  btn.setBackgroundResource(R.drawable.btn_color_dark);
                //  recordoption = 1;
                Log.e("third button", "third button");
                break;


        }

    }

    public void uploadaudiofile(String firstname, String lastname, String gender, String age, String phone, String langauge, String description, String characteristic, String recordingmethod, String jobcaregory, String audiofile) {


        try {

            String data = URLEncoder.encode("first_name", "UTF-8")
                    + "=" + URLEncoder.encode(firstname, "UTF-8");

            data += "&" + URLEncoder.encode("last_name", "UTF-8") + "="
                    + URLEncoder.encode(lastname, "UTF-8");
            data += "&" + URLEncoder.encode("gender", "UTF-8") + "="
                    + URLEncoder.encode(gender, "UTF-8");
            data += "&" + URLEncoder.encode("age", "UTF-8") + "="
                    + URLEncoder.encode(age, "UTF-8");
            data += "&" + URLEncoder.encode("phone", "UTF-8") + "="
                    + URLEncoder.encode(phone, "UTF-8");
            data += "&" + URLEncoder.encode("language", "UTF-8") + "="
                    + URLEncoder.encode(langauge, "UTF-8");
            data += "&" + URLEncoder.encode("description", "UTF-8") + "="
                    + URLEncoder.encode(description, "UTF-8");

            data += "&" + URLEncoder.encode("characteristics", "UTF-8") + "="
                    + URLEncoder.encode(characteristic, "UTF-8");

            data += "&" + URLEncoder.encode("recording_method", "UTF-8") + "="
                    + URLEncoder.encode(recordingmethod, "UTF-8");
            data += "&" + URLEncoder.encode("job_category", "UTF-8") + "="
                    + URLEncoder.encode(jobcaregory, "UTF-8");


            String userid = constData.getUserid();
            String token = constData.getTokenid();

            String url = WebApiCall.baseURl + "user/" + userid;

            URL obj = new URL(url);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();
            String encoded = Base64.encodeToString((userid + ":" + token).getBytes("UTF-8"), Base64.NO_WRAP);
            //     String encoded = Base64.encodeToString(userid+":"+tokens).getBytes();
            con.setRequestProperty("Authorization", "Basic " + encoded);
            con.setReadTimeout(15000 /* milliseconds */);
            con.setConnectTimeout(15000 /* milliseconds */);
            con.setRequestMethod("POST");
            con.setDoInput(true);
            con.setDoOutput(true);


        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    private void init(View view) {

        tv_recordedmethod = (TextView) view.findViewById(R.id.tv_recordingmethod);
        tv_recordeddemo = (TextView) view.findViewById(R.id.tv_recordeddemo);
        tv_attachedvoice = (TextView) view.findViewById(R.id.tv_attachvoicedemo);
        //  bt_isdn = (Button) view.findViewById(R.id.isdn);
        //   bt_sorceconnect = (Button) view.findViewById(R.id.sourceconnect);
        // bt_studio = (Button) view.findViewById(R.id.studio);
        bt_next = (Button) view.findViewById(R.id.next);
        bt_back = (Button) view.findViewById(R.id.back);
        img_record = (ImageView) view.findViewById(R.id.audiorecord);
        img_play = (ImageView) view.findViewById(R.id.audiorecordplay);
        img_detete = (ImageView) view.findViewById(R.id.audiorecorddelete);
        //    img_stop = (ImageView) view.findViewById(R.id.audiorecordstop);
        layout = (LinearLayout) view.findViewById(R.id.recordedlayout);
    }

    public void uploadaudiofileusingmultipart() {

        try {
            constData = ConstantData.getInstance();
            String firstname = constData.getFirstname();
            String lastname = constData.getLastname();
            String phonenumber = constData.getPhonenumber();
            String token = constData.getTokenid();
            Log.e("firstname", firstname);
            Log.e("lastname", lastname);
            Log.e("phonennumber", phonenumber);
            Log.e("token", token);

            int gvalue = constData.getGvalue();
            Log.e("gvalue", Integer.toString(gvalue));
            int langvalue = constData.getLvaue();
            Log.e("langvalue", Integer.toString(langvalue));
            int agevalue = constData.getAgevalue();
            Log.e("agevalue", Integer.toString(agevalue));

            String email = constData.getEmail();
            String password = constData.getPassword();
            Log.e("email", email);
            Log.e("password", password);


            String phonennumber = constData.getPhonenumber();
            Log.e("phonennumber", phonennumber);


            String description = constData.getDescription();
            Log.e("description", constData.getDescription());


            String userid = constData.getUserid();
            Log.e("userid", userid);

            int characteristicvalue = constData.getCharacteristicvalue();
            Log.e("characteristicvalue", Integer.toString(characteristicvalue));

            int jobcategoryvalue = constData.getJobcategoryvalue();
            Log.e("jobcategoryvalue", Integer.toString(jobcategoryvalue));

            int recordingvalue = constData.getRecordingvalue();
            Log.e("recordingvalue", Integer.toString(recordingvalue));



            String url = WebApiCall.baseURl + "user/" + userid;

            Log.e("url", url);
            SendHttpRequestTask t = new SendHttpRequestTask();

            String[] params = new String[]{url, firstname, lastname, Integer.toString(gvalue), Integer.toString(agevalue), phonennumber, Integer.toString(langvalue), description, Integer.toString(characteristicvalue), Integer.toString(recordingvalue), Integer.toString(jobcategoryvalue)};
            t.execute(params);
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    private class SendHttpRequestTask extends AsyncTask<String, Void, String> {
        ByteArrayOutputStream baos;
        String response;
        ProgressDialog imgdialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            imgdialog = new ProgressDialog(getActivity(),ProgressDialog.THEME_HOLO_LIGHT);
            imgdialog.setMessage("Please Wait!!");
            imgdialog.setCancelable(false);
            imgdialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            imgdialog.show();
        }

        @Override
        protected String doInBackground(String... params) {
            String url = params[0];
            String firtsname = params[1];
            String lastname = params[2];
            String gender = params[3];
            String age = params[4];
            String phone = params[5];
            String language = params[6];
            String description = params[7];
            String characteristics = params[8];
            String recordingmethods = params[9];
            String jobcategory = params[10];

            // Bitmap b = BitmapFactory.decodeResource(ImageUploadActivity.this.getResources(), userImagePath);
            try {
                baos = new ByteArrayOutputStream();
                HttpClient client = new HttpClient(url);
                client.connectForMultipart();

                //     UsernamePasswordCredentials creds = new UsernamePasswordCredentials(username, password);
                //     client.getCredentialsProvider().setCredentials(AuthScope.ANY, creds);


                client.addFormPart("first_name", firtsname);
                client.addFormPart("last_name", lastname);
                client.addFormPart("gender", gender);
                client.addFormPart("age", age);
                client.addFormPart("phone", phone);
                client.addFormPart("language", language);
                client.addFormPart("description", description);
                client.addFormPart("characteristics", characteristics);
                client.addFormPart("recording_method", recordingmethods);
                client.addFormPart("job_category", jobcategory);


                mFileName = Environment.getExternalStorageDirectory().getAbsolutePath();
                mFileName += "/audiorecordtest.3gp";
                File file = new File(mFileName);
                FileInputStream fin = null;
                fin = new FileInputStream(file);

                // soundBytes = new byte[inputStream.available()];

                //soundBytes = toByteArray(inputStream);

                // InputStream inStream = getActivity().getResources().openRawResource(R.raw.song);
                byte[] music = new byte[fin.available()];
                music = toByteArray(fin);

                String out = new SimpleDateFormat("yyyy-MM-dd_hh:mm:ss").format(new Date());

                client.addFilePart("demo", out + ".mp4", music);


                client.finishMultipart();

                /*if (!baos.equals("")) {

                    client.addFilePart("agency_logo", "agent.png", baos.toByteArray());
                    client.finishMultipart();
                    response = client.getResponse();
                } else {

                    response = client.getResponse();
                }
*/

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

            if (response != null && !response.isEmpty()) {
                Log.e("response", response);
                imgdialog.dismiss();
                String message_code = "";
                String user_id = "";
                if (response.contains("user_id")) {
                    try {
                        JSONObject userObject = new JSONObject(response);
                        message_code = userObject.getString("message_code");
                        user_id = userObject.getString("user_id");
                        if (user_id != null && !user_id.equals("")) {
                            Log.e("user_id", user_id);

                        }
                        Log.e("message_code", message_code);
                        if (message_code.equalsIgnoreCase("151")) {
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    File file = new File(mFileName);
                                    boolean deleted = file.delete();

                                }
                            });
                        }
                    } catch (Exception ex) {
                        //don't forget this
                        ex.printStackTrace();
                    }


                } else {
                    try {
                        JSONObject userObject = new JSONObject(response);
                        message_code = userObject.getString("message_code");
                        Log.e("messagecode", message_code);
                        if (message_code.equalsIgnoreCase("151")) {
                            File file = new File(mFileName);
                            boolean deleted = file.delete();
                            Intent i = new Intent(getActivity(), ArtistHomescreen.class);

                            startActivity(i);
                            getActivity().finish();
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }


            } else {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        customizeDialog = new CustomizeDialog(getActivity());
                        customizeDialog.setTitle("nuvo");
                        customizeDialog.setCancelable(false);
                        customizeDialog.setMessage("Recording file not present.");
                        customizeDialog.show();
                    }
                });
            }


        }


    }
}