package xyz.theapptest.nuvo.fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import xyz.theapptest.nuvo.R;
import xyz.theapptest.nuvo.api.CheckInternetConnection;
import xyz.theapptest.nuvo.api.WebApiCall;
import xyz.theapptest.nuvo.pojo.ServerResponsePojo;
import xyz.theapptest.nuvo.pojo.UpdatePojo;
import xyz.theapptest.nuvo.pojo.UserProfilePojo;
import xyz.theapptest.nuvo.ui.ArtistHomescreen;
import xyz.theapptest.nuvo.ui.CustomizeDialog;
import xyz.theapptest.nuvo.ui.HttpClient;
import xyz.theapptest.nuvo.ui.ProducerHomeScreen;
import xyz.theapptest.nuvo.ui.RegistrationActivity;
import xyz.theapptest.nuvo.ui.SendingEmailActivity;
import xyz.theapptest.nuvo.ui.SignUpActivity;
import xyz.theapptest.nuvo.utils.ConstantData;
import xyz.theapptest.nuvo.utils.Constants;
import xyz.theapptest.nuvo.utils.Dialogs;

import static android.app.Activity.RESULT_OK;

/**
 * Created by trtcpu007 on 15/7/16.
 */

public class Fragment_Saved extends Fragment {
    Context mContext;
    RatingBar ratingBar;
    ImageView addPhoneImv, msgImv, statusImv;
    String userid, token;
    ConstantData constantData;
    UserProfilePojo userProfilePojo;
    TextView userNameTv, genderTv, ageTv, descriptionValueTv, descriptionTitleTv, langTv, charTv;
    LinearLayout charLL, langLL;
    android.support.v7.widget.Toolbar toolbar1;
    TextView tx2;
    String firstName;
    String lastName;
    String status;
    String age;
    String phone;
    String description;
    String language;
    String characteristics;
    String selectedgallerypath;
    Uri selectedImageUri;
    String gender;
    String ratings;
    String emails;
    private int REQUEST_CAMERA = 0, SELECT_FILE = 1;
    boolean isOnline = false;
    SharedPreferences nuvoPreferences;
    SharedPreferences.Editor nuvoEditor;
    String loginEmail = "";
    String loginPassword = "";
    ImageView img_play, img_edit;
    final static int RQS_OPEN_AUDIO_MP3 = 1;
    Uri audioFileUri;
    public int REQUEST_TAKE_GALLERY_VIDEO;
    private boolean permissionToRecordAccepted = false;
    private boolean permissionToWriteAccepted = false;
    private String[] permissions = {"android.permission.RECORD_AUDIO", "android.permission.WRITE_EXTERNAL_STORAGE"};
    ImageView img_email;
    String updatedphone;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_artist_profile, null);
        init(rootView);
        /*int requestCode = 200;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(permissions, requestCode);
        }*/
        constantData = ConstantData.getInstance();
        setNuvoSharedPref();
        GetProfile getProfile = new GetProfile();
        getProfile.execute();
        //getProfile();
        //      setRatingBar();
        setUpListners();

        return rootView;

    }

    private void init(View rootView) {


        ArtistHomescreen act = (ArtistHomescreen) getActivity();
        toolbar1 = (android.support.v7.widget.Toolbar) act.findViewById(R.id.toolbar_top);
        tx2 = (TextView) toolbar1.findViewById(R.id.rightlabel);
        tx2.setText("Log out");
        tx2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity(), AlertDialog.THEME_HOLO_LIGHT);

                // Setting Dialog Title
                alertDialog.setTitle("nuvo");

                // Setting Dialog Message
                alertDialog.setMessage("Are you sure you want to logout ? ");

                // Setting Icon to Dialog


                // Setting Positive "Yes" Button
                alertDialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        nuvoEditor.clear().commit();
                        SharedPreferences shf = getActivity().getSharedPreferences("UserType", Context.MODE_PRIVATE);
                        shf.edit().clear().commit();
                        Intent intent = new Intent(getActivity(), SignUpActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                        getActivity().finish();
                    }
                });

                // Setting Negative "NO" Button
                alertDialog.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // Write your code here to invoke NO event

                        dialog.cancel();
                    }
                });

                // Showing Alert Message
                alertDialog.show();

            }
        });
        img_play = (ImageView) rootView.findViewById(R.id.artist_profile_play_imv_id);
        img_edit = (ImageView) rootView.findViewById(R.id.artist_profile_edit_imv_id);
        img_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //       selectaudio();
            }
        });
        img_email = (ImageView) rootView.findViewById(R.id.artist_profile_msg_imv_id);
        ratingBar = (RatingBar) rootView.findViewById(R.id.artist_profile_rating_bar_id);
        addPhoneImv = (ImageView) rootView.findViewById(R.id.artist_profile_call_imv_id);
        msgImv = (ImageView) rootView.findViewById(R.id.artist_profile_msg_imv_id);
        statusImv = (ImageView) rootView.findViewById(R.id.artist_profile_user_status_imv_id);
        userNameTv = (TextView) rootView.findViewById(R.id.artist_profile_user_name_tv_id);
        ageTv = (TextView) rootView.findViewById(R.id.artist_profile_age_tv_id);
        genderTv = (TextView) rootView.findViewById(R.id.artist_profile_gender_tv_id);
        descriptionValueTv = (TextView) rootView.findViewById(R.id.artist_profile_description_value_tv_id);
        descriptionTitleTv = (TextView) rootView.findViewById(R.id.artist_profile_description_tv_id);
        charLL = (LinearLayout) rootView.findViewById(R.id.artist_profile_characteristics_ll_id);
        langLL = (LinearLayout) rootView.findViewById(R.id.artist_profile_language_ll_id);
        charTv = (TextView) rootView.findViewById(R.id.artist_profile_characteristics_tv_id);
        langTv = (TextView) rootView.findViewById(R.id.artist_profile_language_tv_id);
        img_play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                try {
                    MediaPlayer player = new MediaPlayer();
                    player.setAudioStreamType(AudioManager.STREAM_MUSIC);
                    player.setDataSource(userProfilePojo.getDemo());
                    player.prepare();
                    player.start();
                } catch (Exception e) {
                    // TODO: handle exception
                    e.printStackTrace();
                }


            }
        });

        img_email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (emails != null) {
                    Intent emailsend = new Intent(Intent.ACTION_SEND);
                    emailsend.putExtra(Intent.EXTRA_EMAIL, new String[]{emails});
                    emailsend.putExtra(Intent.EXTRA_SUBJECT, "Nuvo");
                    emailsend.putExtra(Intent.EXTRA_TEXT, "agent wants to talk.");

                    //need this to prompts email client only
                    emailsend.setType("message/rfc822");

                    startActivity(Intent.createChooser(emailsend, "Choose an Email client :"));
                }
            }
        });

    }


    public void selectaudio() {
        galleryIntent();
    }

    /*@Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == getActivity().RESULT_OK) {
            if (requestCode == RQS_OPEN_AUDIO_MP3) {
                audioFileUri = data.getData();
                Log.e("audiouri", audioFileUri.toString());
                String userId = constantData.getUserid();
                String token = constantData.getTokenid();
                String url = WebApiCall.updateProfile
                        + constantData.getUserid() + "/updateaudio";
                SendHttpRequestTask t = new SendHttpRequestTask();
                String[] params = new String[]{url};
                t.execute(params);


            }
        }
    }
*/

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

            // Bitmap b = BitmapFactory.decodeResource(ImageUploadActivity.this.getResources(), userImagePath);

            try {

                baos = new ByteArrayOutputStream();
                HttpClient client = new HttpClient(url);
                client.connectForMultipart();


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


                client.finishMultipart();


                response = client.getResponse();

            } catch (Throwable t) {
                imgdialog.dismiss();
                t.printStackTrace();
            }


            return null;
        }

        @Override
        protected void onPostExecute(String data) {
            if (response != null && !response.isEmpty()) {
                Log.e("response", response);
                imgdialog.dismiss();
            }


        }


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

    private void galleryIntent() {

        Intent intent = new Intent();
        intent.setType("audio/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select audio"), REQUEST_TAKE_GALLERY_VIDEO);


    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_TAKE_GALLERY_VIDEO) {
                selectedImageUri = data.getData();


                // OI FILE Manager
                //selectedgallerypath = selectedImageUri.getPath();

                // MEDIA GALLERY
                selectedgallerypath = getPath(selectedImageUri);

                String userId = constantData.getUserid();
                String token = constantData.getTokenid();
                String url = WebApiCall.updateProfile
                        + constantData.getUserid() + "/updateaudio";
                Log.e("selectedgallerypath", selectedgallerypath);
                SendHttpRequestTask t = new SendHttpRequestTask();
                String[] params = new String[]{url};
                t.execute(params);

            }
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


    private void setNuvoSharedPref() {

        nuvoPreferences = mContext.getSharedPreferences(Constants.NUVOPREF, Context.MODE_PRIVATE);
        nuvoEditor = nuvoPreferences.edit();
        loginEmail = nuvoPreferences.getString(Constants.Key_email, "");
        loginPassword = nuvoPreferences.getString(Constants.Key_Password, "");
    }

    private void getProfile() {
        if (CheckInternetConnection.isConnected(getActivity())) {
            try {
                //   String userId = constantData.getUserid();
                //   String token = constantData.getTokenid();

                SharedPreferences shf = getActivity().getSharedPreferences("UserType", Context.MODE_PRIVATE);
                String usertype = shf.getString("usertype", null);
                userid = shf.getString("userid", null);
                token = shf.getString("token", null);


                String url = WebApiCall.getUserProfileUrl + "" + userid;
                Log.e("url", url);
                URL obj = new URL(url);
                HttpURLConnection con = (HttpURLConnection) obj.openConnection();
                String encoded = Base64.encodeToString((userid + ":" + token).getBytes("UTF-8"), Base64.NO_WRAP);
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
                Log.e("profile response", serverResult);
                Gson gson = new Gson();
                userProfilePojo = gson.fromJson(serverResult, UserProfilePojo.class);
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
/*
    private void setRatingBar() {
        Drawable progress = ratingBar.getProgressDrawable();
        DrawableCompat.setTint(progress, Color.WHITE);
        ratingBar.setNumStars(5);
    //    ratingBar.setRating(Float.parseFloat("4.0"));
        ratingBar.setClickable(false);
        ratingBar.setFocusable(false);

    }*/

    private void setUpListners() {
        addPhoneImv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openAddPhoneDialog();
            }
        });

        statusImv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // openStatusDialog();
                openStatusDialogNew();
            }
        });
    }

    private void setUserProfile() {
        if (userProfilePojo == null) {
            return;
        }
        firstName = userProfilePojo.getFirst_name();
        lastName = userProfilePojo.getLast_name();
        status = userProfilePojo.getOnline();
        age = userProfilePojo.getAge();
        phone = userProfilePojo.getPhone();
        description = userProfilePojo.getDescription();
        language = userProfilePojo.getLanguage();
        characteristics = userProfilePojo.getCharacteristics();
        gender = userProfilePojo.getGender();
        ratings = userProfilePojo.getRating();
        emails = userProfilePojo.getEmail();

        if (age.equals("1")) {
            age = "Gen X";
        } else if (age.equals("2")) {
            age = "Gen Y";
        } else {
            age = "Millenials";
        }

        switch (characteristics) {
            case "1":
                characteristics = "Attitude";
                break;
            case "2":
                characteristics = "Dad";
                break;
            case "4":
                characteristics = "Authoritative";
                break;
            case "8":
                characteristics = "Gravitas";
                break;
            case "16":
                characteristics = "Announcer";
                break;
            case "32":
                characteristics = "Wise";
                break;
            case "64":
                characteristics = "Professional";
                break;
            default:
                characteristics = "Professional";

        }

        switch (language) {
            case "1":
                language = "English";
                break;
            case "2":
                language = "French";
                break;
            case "4":
                language = "Spanish";
                break;
            case "8":
                language = "British";
                break;

            default:
                language = "English";

        }

        if (gender.equals("14")) {
            gender = "Male";
        } else {
            gender = "Female";
        }

        if (status.equals("7")) {
            isOnline = true;
            statusImv.setImageDrawable(mContext.getResources().getDrawable(R.drawable.green_circle));
        } else {
            isOnline = false;
            statusImv.setImageDrawable(mContext.getResources().getDrawable(R.drawable.red_dot));
        }
        if (ratings != null) {
            Log.e("rating", ratings);
            ratingBar.setRating(Float.parseFloat(ratings));
        }
        if (emails != null) {
          /*  Intent emailsend = new Intent(Intent.ACTION_SEND);
            emailsend.putExtra(Intent.EXTRA_EMAIL, new String[]{emails});
            emailsend.putExtra(Intent.EXTRA_SUBJECT, "Nuvo");
            emailsend.putExtra(Intent.EXTRA_TEXT, "Hi");

            //need this to prompts email client only
            emailsend.setType("message/rfc822");

            startActivity(Intent.createChooser(emailsend, "Choose an Email client :"));*/
        }

        String userName = firstName + " " + lastName;

        userNameTv.setText(userName);
        genderTv.setText(gender);
        ageTv.setText(age);
        descriptionValueTv.setText(description);


        Typeface type = Typeface.createFromAsset(mContext.getAssets(), "font/OpenSans-Semibold.ttf");
        descriptionTitleTv.setTypeface(type);
        charTv.setTypeface(type);
        langTv.setTypeface(type);

        Typeface typeRegular = Typeface.createFromAsset(mContext.getAssets(), "font/OpenSans-Regular.ttf");
        genderTv.setTypeface(typeRegular);
        ageTv.setTypeface(typeRegular);
        descriptionValueTv.setTypeface(typeRegular);


        setCharacteristics(characteristics);
        setLanguages(language);
    }

    private void setCharacteristics(String characteristics) {
        TextView btn = new TextView(mContext);
        btn.setBackgroundResource(R.drawable.button_selected);
        btn.setTextSize(12);

        Typeface facetxtsigintextbox = Typeface.createFromAsset(mContext.getAssets(),
                "font/OpenSans-Regular.ttf");
        btn.setTypeface(facetxtsigintextbox);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);


        params.setMargins(10, 5, 20, 5);
        btn.setPadding(20, 5, 20, 5);
        btn.setLayoutParams(params);
        //   btn.setLayoutParams(new LinearLayout.LayoutParams(70, 30));
        btn.setTextColor(mContext
                .getResources().getColor(R.color.white));
        btn.setText(characteristics);
        charLL.addView(btn);
    }

    private void setLanguages(String language) {
        TextView btn = new TextView(mContext);
        btn.setBackgroundResource(R.drawable.button_selected);
        btn.setTextSize(12);

        Typeface facetxtsigintextbox = Typeface.createFromAsset(mContext.getAssets(),
                "font/OpenSans-Regular.ttf");
        btn.setTypeface(facetxtsigintextbox);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);


        //  btn.setMinHeight(-2);
//        params.weight = 1.5f;
//        params.gravity = Gravity.LEFT;
//        params.gravity = Gravity.RIGHT;


        params.setMargins(10, 5, 20, 5);
        btn.setPadding(20, 5, 20, 5);
        btn.setLayoutParams(params);
        //   btn.setLayoutParams(new LinearLayout.LayoutParams(70, 30));
        btn.setTextColor(mContext
                .getResources().getColor(R.color.white));
        btn.setText(language);
        langLL.addView(btn);
    }

    private void openStatusDialogNew() {
        final Dialog dialog = new Dialog(mContext);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        Window window = dialog.getWindow();
        window.setGravity(Gravity.BOTTOM);


        dialog.setContentView(R.layout.artist_status_dialog_new_layout);
        dialog.setCancelable(false);

        Button onlineBtn = (Button) dialog.findViewById(R.id.artist_status_dialog_new_onlinebtn_id);
        Button offlineBtn = (Button) dialog.findViewById(R.id.artist_status_dialog_new_offlinebtn_id);
        Button cancelBtn = (Button) dialog.findViewById(R.id.artist_status_dialog_new_cancel_btn_id);

        onlineBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                doChangeStatusWebServiceCall("online");
            }
        });

        offlineBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                doChangeStatusWebServiceCall("offline");
            }
        });

        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    private void doChangeStatusWebServiceCall(String status) {
        if (status.equals("online")) {
            ChangeStatusWebServiceCall changeStatusWebServiceCall = new ChangeStatusWebServiceCall(status);
            changeStatusWebServiceCall.execute();

        } else {
            OfflineStatusWebServiceCall changeStatusWebServiceCall = new OfflineStatusWebServiceCall(status);
            changeStatusWebServiceCall.execute();

        }
    }

    public class ChangeStatusWebServiceCall extends AsyncTask<String, Void, String> {

        ProgressDialog pd;
        String status;
        String messageCode = "";

        public ChangeStatusWebServiceCall(String status) {
            this.status = status;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            if (isOnline) {
                if (messageCode.equals("167")) {
                    isOnline = false;
                    statusImv.setImageDrawable(mContext.getResources().getDrawable(R.drawable.red_dot));

                } else {

                }

            } else {
                if (messageCode.equals("167")) {
                    isOnline = true;
                    statusImv.setImageDrawable(mContext.getResources().getDrawable(R.drawable.green_circle));

                }

            }

        }

        @Override
        protected String doInBackground(String... strings) {
            String text = "";
            BufferedReader reader = null;
            try {
                String userId = constantData.getUserid();
                String token = constantData.getTokenid();

                String url = WebApiCall.baseURl + "user/" + "" + userId + "/" + status;
                URL obj = new URL(url);


                HttpURLConnection con = (HttpURLConnection) obj.openConnection();
                String encoded = Base64.encodeToString((userId + ":" + token).getBytes("UTF-8"), Base64.NO_WRAP);
                //     String encoded = Base64.encodeToString(userid+":"+tokens).getBytes();
                con.setRequestProperty("Authorization", "Basic " + encoded);
                con.setRequestMethod("PUT");
                String data = "";

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
                Gson gson = new Gson();
                ServerResponsePojo serverResponsePojo = gson.fromJson(text, ServerResponsePojo.class);
                messageCode = serverResponsePojo.getMessage_code();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }
    }

    public class OfflineStatusWebServiceCall extends AsyncTask<String, Void, String> {
        ProgressDialog pd;
        String status;
        String messageCode = "";

        public OfflineStatusWebServiceCall(String status) {
            this.status = status;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            if (isOnline) {
                if (messageCode.equals("167")) {
                    isOnline = false;
                    statusImv.setImageDrawable(mContext.getResources().getDrawable(R.drawable.red_dot));

                } else {

                }

            } else {
                if (messageCode.equals("167")) {
                    isOnline = true;
                    statusImv.setImageDrawable(mContext.getResources().getDrawable(R.drawable.green_circle));

                }

            }

        }

        @Override
        protected String doInBackground(String... strings) {
            String text = "";
            BufferedReader reader = null;
            try {
                String userId = constantData.getUserid();
                String token = constantData.getTokenid();

                //String url = WebApiCall.baseURl + "user/" + status;
                String url = WebApiCall.baseURl + "user/" + "" + userId + "/" + status;
                URL obj = new URL(url);


                HttpURLConnection con = (HttpURLConnection) obj.openConnection();
                String encoded = Base64.encodeToString((userId + ":" + token).getBytes("UTF-8"), Base64.NO_WRAP);
                //     String encoded = Base64.encodeToString(userid+":"+tokens).getBytes();
                con.setRequestProperty("Authorization", "Basic " + encoded);
                con.setRequestMethod("DELETE");
                String data = "";

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
                Gson gson = new Gson();
                ServerResponsePojo serverResponsePojo = gson.fromJson(text, ServerResponsePojo.class);
                messageCode = serverResponsePojo.getMessage_code();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }
    }

    private void openStatusDialog() {
        final Dialog dialog = new Dialog(mContext);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.artist_status_dialog_layout);
        dialog.setCancelable(false);
        RadioButton onlineRb = (RadioButton) dialog.findViewById(R.id.artist_stauts_online_rb_id);
        RadioButton offlineRb = (RadioButton) dialog.findViewById(R.id.artist_stauts_offline_rb_id);
        Button cancelBtn = (Button) dialog.findViewById(R.id.artist_status_cancle_btn_id);
        onlineRb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                dialog.dismiss();
            }
        });

        offlineRb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                dialog.dismiss();
            }
        });

        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    private void openAddPhoneDialog() {
        final Dialog dialog = new Dialog(mContext);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.add_phone_dialog_layout);
        dialog.setCancelable(false);
        Button cancleBtn = (Button) dialog.findViewById(R.id.add_phone_cancel_btn_id);
        Button confirmBtn = (Button) dialog.findViewById(R.id.add_phone_confirm_btn_id);
        final EditText phoneEt = (EditText) dialog.findViewById(R.id.add_phone_dialog_et_id);
        TextView titleTv = (TextView) dialog.findViewById(R.id.add_phone_dialog_title_tv_id);
        if (phone != null) {
            if(updatedphone!=null) {
                phoneEt.setText(updatedphone);
            }else
            {
                phoneEt.setText(phone);
            }
        }

        cancleBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        confirmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String phoneNo = phoneEt.getText().toString();

                if (phoneNo.equals("")) {
                    //    Dialogs.showToast(mContext, "Phone number can not be blank");

                    return;
                }
                if (phoneNo.length() < 10 || phoneNo.length() > 13) {
                    //     Dialogs.showToast(mContext, "Please enter phone number between 10-13 digits");
                    return;
                }
                Log.e("phoneNo", phoneNo);
                doUpdateWebserviceCall(phoneNo);
                dialog.dismiss();


            }
        });

        dialog.show();
    }

    private void doUpdateWebserviceCall(String phone) {
        UpdatewebServiceCall updatewebServiceCall = new UpdatewebServiceCall(phone);
        updatewebServiceCall.execute();
    }

    public class UpdatewebServiceCall extends AsyncTask<String, Void, String> {

        String phone;
        ProgressDialog pd;
        String msgCode = "";
        UpdatePojo updateProfilePojo;
        public UpdatewebServiceCall(String phone) {
            this.phone = phone;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pd = new ProgressDialog(mContext);
            pd.setMessage("Please wait!!");
            pd.show();
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            updatedphone=phone;
            pd.dismiss();
            Toast.makeText(mContext, "Phone number updated", Toast.LENGTH_SHORT).show();
        }

        @Override
        protected String doInBackground(String... strings) {
            String text = "";
            BufferedReader reader = null;
            try {
                String userId = constantData.getUserid();
                String token = constantData.getTokenid();
                String url = WebApiCall.updateProfile
                        + constantData.getUserid() + "/updatephone";

                URL obj = new URL(url);
                HttpURLConnection con = (HttpURLConnection) obj.openConnection();
                String encoded = Base64.encodeToString((userId + ":" + token).getBytes("UTF-8"), Base64.NO_WRAP);
                //     String encoded = Base64.encodeToString(userid+":"+tokens).getBytes();
                con.setRequestProperty("Authorization", "Basic " + encoded);

              /*  String data = URLEncoder.encode("phone", "UTF-8")
                        + "=" + URLEncoder.encode(phone, "UTF-8");

                String data = URLEncoder.encode("phone", "UTF-8")
                        + "=" + URLEncoder.encode(phone, "UTF-8");
                String data = URLEncoder.encode("phone", "UTF-8")
                        + "=" + URLEncoder.encode(phone, "UTF-8");
                String data = URLEncoder.encode("phone", "UTF-8")
                        + "=" + URLEncoder.encode(phone, "UTF-8");*/

             /*   String data = URLEncoder.encode(WebApiCall.KEY_FIRSTNAME, "UTF-8")
                        + "=" + URLEncoder.encode(firstName, "UTF-8");
                data += "&" + URLEncoder.encode(WebApiCall.KEY_LASTNAME, "UTF-8") + "="
                        + URLEncoder.encode(lastName, "UTF-8");
                data += "&" + URLEncoder.encode("gender", "UTF-8")
                        + "=" + URLEncoder.encode(gender, "UTF-8");*/
                String data = URLEncoder.encode("phone", "UTF-8")
                        + "=" + URLEncoder.encode(phone, "UTF-8");

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
                Gson gson = new Gson();
                 updateProfilePojo = gson.fromJson(text, UpdatePojo.class);
                msgCode = updateProfilePojo.getMessage_code();


            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }
    }


    public class GetProfile extends AsyncTask<String, Void, String> {

        ProgressDialog pd;

        public GetProfile() {

        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pd = new ProgressDialog(mContext);
            pd.setCancelable(false);
            pd.setMessage("Please wait!!");
            //   pd.show();
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            pd.dismiss();
            try {
                setUserProfile();
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected String doInBackground(String... strings) {
            getProfile();
            return null;
        }
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.mContext = context;
    }
}