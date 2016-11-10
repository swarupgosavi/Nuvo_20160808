package xyz.theapptest.nuvo.agent;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.hardware.camera2.CameraManager;
import android.media.Image;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;

import xyz.theapptest.nuvo.R;
import xyz.theapptest.nuvo.api.WebApiCall;
import xyz.theapptest.nuvo.pojo.CheckArtistPojo;
import xyz.theapptest.nuvo.pojo.User_info;
import xyz.theapptest.nuvo.ui.CustomizeDialog;
import xyz.theapptest.nuvo.ui.HttpClient;
import xyz.theapptest.nuvo.ui.SendingEmailActivity;
import xyz.theapptest.nuvo.ui.SignInActivity;
import xyz.theapptest.nuvo.ui.SignUpActivity;
import xyz.theapptest.nuvo.utils.ConstantData;
import xyz.theapptest.nuvo.utils.Constants;
import xyz.theapptest.nuvo.utils.Fonts;
import xyz.theapptest.nuvo.utils.Utility;
import xyz.theapptest.nuvo.widget.RoundedImageView;

public class MyProfileFragment extends Fragment implements View.OnClickListener {

    TextView hdrMyProfileTxt, logOutTxt, addYourLogotxt;
    EditText firstNameEdt, lastNAmeEdt, phoneNumberEdt, emailEdt, passwordEdt;
    ImageView editIcon;
    RoundedImageView roundImg;
    Button save;
    TextView tv_title;
    Fonts fonts;
    private static final int REQUEST_CAMERA_RESULT = 1;
    private String userChoosenTask;
    private int REQUEST_CAMERA = 0, SELECT_FILE = 1;
    Bitmap bmp;

    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };
    Bitmap thePic;
    Context mContext;
    ConstantData constantData;
    SharedPreferences nuvoPreferences;
    SharedPreferences.Editor nuvoEditor;
    String loginEmail;
    String loginPassword;
    User_info userInfo;
    Image logo;
    Uri imageUri;
    private CustomizeDialog customizeDialog;
    private String imgUrlStr;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_my_profile, null);
        constantData = ConstantData.getInstance();
        initViews(rootView);
        setUpListeners();
        setNuvoSharedPref();
        AgentProfileInfofetch agentProfileInfofetch = new AgentProfileInfofetch();
        agentProfileInfofetch.execute();


        return rootView;
    }



    private void setUpListeners() {
        openCamera();
        editIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                selectImage();
            }
        });

        roundImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                selectImage();
            }
        });

        logOutTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity(), AlertDialog.THEME_HOLO_LIGHT);

                // Setting Dialog Title
                alertDialog.setTitle("nuvo");

                // Setting Dialog Message
                alertDialog.setMessage("Are you sure you want to logout ? ");

                // Setting Icon to Dialog


                // Setting Positive "Yes" Button
                alertDialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        constantData.setUserid("");
                        SharedPreferences shf = mContext.getSharedPreferences("UserType", Context.MODE_PRIVATE);
                        shf.edit().clear().commit();
                        nuvoEditor.putString(Constants.Key_email, "");
                        nuvoEditor.putString(Constants.Key_Password, "");
                        nuvoEditor.commit();

                        Intent intent = new Intent(mContext, SignUpActivity.class);
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
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String userId = constantData.getUserid();
                SaveAgentProfile saveAgentProfile = new SaveAgentProfile();

                String url = WebApiCall.baseURl + "user/" + userId;
                String[] params = new String[]{url, firstNameEdt.getText().toString().trim(), lastNAmeEdt.getText().toString().trim(), phoneNumberEdt.getText().toString().trim(), emailEdt.getText().toString().trim()};
                saveAgentProfile.execute(params);
            }
        });

    }

    private void setNuvoSharedPref() {

        nuvoPreferences = mContext.getSharedPreferences(Constants.NUVOPREF, Context.MODE_PRIVATE);
        nuvoEditor = nuvoPreferences.edit();
        loginEmail = nuvoPreferences.getString(Constants.Key_email, "");
        loginPassword = nuvoPreferences.getString(Constants.Key_Password, "");
    }
    protected void setExistingUserInfo(){

        Log.e("setExistingUserInfo","Here it is");
        if (userInfo!=null){

            firstNameEdt.setText(userInfo.getFirst_name());
            lastNAmeEdt.setText(userInfo.getLast_name());
            if (userInfo.getPhone().equalsIgnoreCase("0")) {

                phoneNumberEdt.setText("");

            }else {

                phoneNumberEdt.setText(userInfo.getPhone());
            }
            emailEdt.setText(userInfo.getEmail());
            //passwordEdt.setText(userInfo.pa);
        }


                try {
                    addYourLogotxt.setVisibility(View.GONE);
                    Log.e("URLIMG",imgUrlStr);



                    URL url = new URL(imgUrlStr);
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    connection.setDoInput(true);
                    connection.connect();
                    InputStream input = connection.getInputStream();
                    Bitmap myBitmap = BitmapFactory.decodeStream(input);
                    roundImg.setImageBitmap(myBitmap);

                //    roundImg.setImageURI(imageUri);
                }catch (Exception e){
                    e.printStackTrace();
                }




        // Toast.makeText(getActivity(),"Image Not Avilable", Toast.LENGTH_LONG).show();


    }

    private void initViews(View rootView) {

        fonts = new Fonts(getActivity());

        hdrMyProfileTxt = (TextView)rootView.findViewById(R.id.amp_myprofile_toolbar_title);
        hdrMyProfileTxt.setTypeface(fonts.halveticaNeue);
        logOutTxt = (TextView)rootView.findViewById(R.id.my_profile_agent_logout);
        logOutTxt.setTypeface(fonts.openSansRegular);
        logOutTxt.setOnClickListener(this);
        addYourLogotxt = (TextView)rootView.findViewById(R.id.aad_artist_your_logo);
        addYourLogotxt.setTypeface(fonts.openSansRegular);

        firstNameEdt = (EditText)rootView.findViewById(R.id.my_profile_ed_firstname);
        firstNameEdt.setTypeface(fonts.openSansRegular);
        lastNAmeEdt = (EditText)rootView.findViewById(R.id.my_profile_ed_lastname);
        lastNAmeEdt.setTypeface(fonts.openSansRegular);
        phoneNumberEdt = (EditText)rootView.findViewById(R.id.ed_my_profile_phone_number);
        phoneNumberEdt.setTypeface(fonts.openSansRegular);
        emailEdt = (EditText)rootView.findViewById(R.id.my_profile_ed_email);
        emailEdt.setTypeface(fonts.openSansRegular);
        passwordEdt = (EditText)rootView.findViewById(R.id.my_profile_ed_password);
        passwordEdt.setTypeface(fonts.openSansRegular);

       editIcon = (ImageView)rootView.findViewById(R.id.my_profile_edit_icon);
        roundImg = (RoundedImageView)rootView.findViewById(R.id.my_profile_agency_logo_imv_id);
        save = (Button)rootView.findViewById(R.id.my_profile_save_btn);
        save.setTypeface(fonts.openSansSemiBold);

    }



    private void openCamera() {
        //CameraManager cameraManager = (CameraManager) getSystemService(Context.CAMERA_SERVICE);
        CameraManager cameraManager = (CameraManager)getActivity().getSystemService(Context.CAMERA_SERVICE);
        try {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA)
                        == PackageManager.PERMISSION_GRANTED) {

                } else {
                    if (shouldShowRequestPermissionRationale(Manifest.permission.CAMERA)) {
                        Toast.makeText(getActivity(), "No Permission to use the Camera services", Toast.LENGTH_SHORT).show();
                    }
                    requestPermissions(new String[]{Manifest.permission.CAMERA}, REQUEST_CAMERA_RESULT);
                }
            } else {

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CAMERA_RESULT:
                if (grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(getActivity(), "Cannot run application because camera service permission have not been granted", Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
                break;
        }
    }

    private void selectImage() {
        final CharSequence[] items = {"Take Photo", "Choose from Library",
                "Cancel"};

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Add Photo!");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                boolean result = Utility.checkPermission(getActivity());

                if (items[item].equals("Take Photo")) {
                    userChoosenTask = "Take Photo";
                    if (result)
                        cameraIntent();

                } else if (items[item].equals("Choose from Library")) {
                    userChoosenTask = "Choose from Library";
                    if (result)
                        galleryIntent();

                } else if (items[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    private void galleryIntent() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);//
        startActivityForResult(Intent.createChooser(intent, "Select File"), SELECT_FILE);
    }

    private void cameraIntent() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, REQUEST_CAMERA);
    }

    private void onCaptureImageResult(Intent data) {
        thePic = (Bitmap) data.getExtras().get("data");
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        thePic.compress(Bitmap.CompressFormat.JPEG, 90, bytes);

        File destination = new File(Environment.getExternalStorageDirectory(),
                System.currentTimeMillis() + ".jpg");

        FileOutputStream fo;
        try {
            destination.createNewFile();
            fo = new FileOutputStream(destination);
            fo.write(bytes.toByteArray());
            fo.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        roundImg.setImageBitmap(thePic);
        addYourLogotxt.setVisibility(View.GONE);

    }

    @SuppressWarnings("deprecation")
    private void onSelectFromGalleryResult(Intent data) {


        if (data != null) {
            try {
                thePic = MediaStore.Images.Media.getBitmap(getActivity().getApplicationContext().getContentResolver(), data.getData());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        roundImg.setImageBitmap(thePic);
        addYourLogotxt.setVisibility(View.GONE);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == SELECT_FILE)
                onSelectFromGalleryResult(data);
            else if (requestCode == REQUEST_CAMERA)
                onCaptureImageResult(data);
        }
    }

    @Override
    public void onClick(View view) {



    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.mContext = context;
    }

    public class AgentProfileInfofetch extends AsyncTask<String, Void, String>{
        String emailId = "";
   //   ProgressDialog pd;
        String msgCode;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        //    pd = new ProgressDialog(mContext);
         //   pd.setMessage("Please wait...");
          //  pd.setCancelable(false);
          //  pd.show();
        }

        @Override
        protected String doInBackground(String... strings) {
            Log.e("InDoInBackgrnd","Here it is");
            getProfileData();

            return null;
        }

        private void getProfileData() {

            String userId = constantData.getUserid();
            String token = constantData.getTokenid();
            //String url = WebApiCall.getUserProfileUrl;
            String url = WebApiCall.baseURl + "user/" + userId;
            String text = "";
            BufferedReader reader = null;
            try {

                Log.e("InDoInBackgrnd","Calling API" + url );
                URL obj = new URL(url);
                HttpURLConnection con = (HttpURLConnection) obj.openConnection();
                String encoded = Base64.encodeToString((userId + ":" + token).getBytes("UTF-8"), Base64.NO_WRAP);
                con.setRequestProperty("Authorization", "Basic " + encoded);
                con.setRequestMethod("GET");
                Log.e("AgentJson",encoded);

                String data = "";/*URLEncoder.encode("email", "UTF-8")
                        + "=" + URLEncoder.encode(emailId, "UTF-8");
*/
                int responseCode = con.getResponseCode();
                Log.e("AgentJson",""+responseCode);
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
                Log.e("GETresonse", response.toString());
                JSONObject jsonObj = new JSONObject(response.toString());

                text = response.toString();
                Gson gson = new Gson();
                userInfo = new User_info();//gson.fromJson(text, User_info.class);
                if (jsonObj!=null){

                    userInfo.setId(jsonObj.getString("id"));
                    userInfo.setFirst_name(jsonObj.getString("first_name"));
                    userInfo.setLast_name(jsonObj.getString("last_name"));
                    userInfo.setEmail(jsonObj.getString("email"));
                    userInfo.setStatus(jsonObj.getString("status"));
                    userInfo.setRole(jsonObj.getString("role"));
                    userInfo.setOnline(jsonObj.getString("online"));
                    userInfo.setCreated_on(jsonObj.getString("created_on"));
                    userInfo.setLast_log_out(jsonObj.getString("last_log_out"));
                    userInfo.setUser_id(jsonObj.getString("user_id"));
                    userInfo.setId(jsonObj.getString("agency_logo"));
                    userInfo.setCompany_name(jsonObj.getString("company_name"));
                    userInfo.setPhone(jsonObj.getString("phone"));


                }
                imgUrlStr = jsonObj.getString("agency_logo");

                Log.e("imgUrlStr::>>",imgUrlStr);
                URL imgURL = new URL(imgUrlStr);
                bmp = BitmapFactory.decodeStream(imgURL.openConnection().getInputStream());

                imageUri = Uri.parse(imgUrlStr);

                if (userInfo.getAgency_logo() != "" || userInfo.getAgency_logo() != null || userInfo.getAgency_logo().length() >= 5){

                    /*String imgUrlStr = userInfo.getAgency_logo();

                    Log.e("imgUrlStr::>>",imgUrlStr);
                    URL imgURL = new URL(imgUrlStr);
                    bmp = BitmapFactory.decodeStream(imgURL.openConnection().getInputStream());

                    imageUri = Uri.parse(imgUrlStr);*/
                    /*ImageView img;
                    uri = URL
                    img.setImageURI();*/




                    Log.e("AgentName",userInfo.getFirst_name());
                    Log.e("Img",userInfo.getAgency_logo());
                }else {

                    Log.e("AgentName","Error fetching Agent info");
                }
                //msgCode = userInfo.getIsexist();


            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Log.e("InonPostExecute","Here it is");
         //   pd.dismiss();
            setExistingUserInfo();
        }
    }


    public class SaveAgentProfile extends AsyncTask<String , Void , String >{


        private ProgressDialog imgdialog;
        ByteArrayOutputStream baos;
        String response;
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
            String phone = params[3];
            String email = params[4];
            /*String strrolemsg = params[5];
            String company = params[6];
            String phone = params[7];*/

            // Bitmap b = BitmapFactory.decodeResource(ImageUploadActivity.this.getResources(), userImagePath);
            try {
                baos = new ByteArrayOutputStream();
                HttpClient client = new HttpClient(url);
                client.connectForMultipart();
                client.addFormPart("first_name", firtsname);
                client.addFormPart("last_name", lastname);
                client.addFormPart("phone", phone);
                client.addFormPart("email", email);
                //client.addFormPart("agency_logo","");

                /*client.addFormPart("password", password);
                client.addFormPart("role", strrolemsg);
                client.addFormPart("company", company);*/


                //  InputStream inStream = context.getResources().openRawResource(R.raw.cheerapp);
                //  byte[] music = new byte[inStream.available()];


                if (thePic == null) {

                    client.finishMultipart();
                } else {
                    thePic.compress(Bitmap.CompressFormat.PNG, 0, baos);
                    String out = new SimpleDateFormat("yyyy-MM-dd_hh:mm:ss").format(new Date());

                    client.addFilePart("agency_logo", out + "." + "png", baos.toByteArray());


                    client.finishMultipart();
                }
                /*if (!baos.equals("")) {

                    client.addFilePart("agency_logo", "agent.png", baos.toByteArray());
                    client.finishMultipart();
                    response = client.getResponse();
                } else {

                    response = client.getResponse();
                }
*/

                response = client.getResponse();
                JSONObject jsonObj = new JSONObject(response.toString());
                String code = jsonObj.getString("message");
                //if ();


                Log.e("SavingResponse",response);
            } catch (Throwable t) {
                imgdialog.dismiss();
                t.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            imgdialog.dismiss();

            customizeDialog = new CustomizeDialog(getActivity());
            customizeDialog.setTitle("nuvo");
            customizeDialog.setCancelable(false);
            customizeDialog.setMessage("Agent Profile updated.");
            customizeDialog.show();
        }
    }




}