package xyz.theapptest.nuvo.ui;

/**
 * Created by trtcpu007 on 12/7/16.
 */


import android.Manifest;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v4.app.ActivityCompat;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import xyz.theapptest.nuvo.R;
import xyz.theapptest.nuvo.utils.CameraHelperLib;
import xyz.theapptest.nuvo.utils.ConstantData;
import xyz.theapptest.nuvo.utils.Constants;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

public class ImageUploadActivity extends Activity {

    private MenuItem item;
    private String url = "http://nuvo.theapptest.xyz/v2/api/user/register";
    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };
    CameraHelperLib camerahelp;
    Context context = ImageUploadActivity.this;
    File imageFile;
    String userImagePath;
    ConstantData constantData;
    Uri picUri;
    ImageView img_Camera;
    Bitmap thePic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload);
        constantData = ConstantData.getInstance();
        final EditText edtTxt1 = (EditText) findViewById(R.id.editTextUpl1);
        final EditText edtTxt2 = (EditText) findViewById(R.id.editTextUpl2);
        Button btnUpl = (Button) findViewById(R.id.upload);
        img_Camera = (ImageView) findViewById(R.id.imageView);
        btnUpl.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                camerahelp = new CameraHelperLib(context);
                //camerahelp.editProfileImageChooser();
                camerahelp.imageChooser();
                //  item.setActionView(R.layout.progress);
                /*SendHttpRequestTask t = new SendHttpRequestTask();

                String[] params = new String[]{url, param1, param2};
                t.execute(params);*/
            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        int result1 = 0;
        result1 = resultCode;
        switch (requestCode) {
            case Constants.PICK_FROM_CAMERA:
                // chooserDialog.dismiss();
                try {
                    checkPermissionForCamera(ImageUploadActivity.this);
                    // camerahelp = ManageFragment.getCameraHelpObj();
                    imageFile = camerahelp.getmImageCaptureUri();
                    if (imageFile != null && imageFile.exists()) {
                        //    camerahelp.startCropImage(camerahelp.getmImageCaptureUri());
                        String fileName = imageFile.getName();
                        userImagePath = imageFile.getPath();
                        constantData.setUserImagePath(userImagePath);
                        picUri = Uri.fromFile(imageFile); // convert path to
                        performCrop();
//                        attachmentNameTv.setVisibility(View.VISIBLE);
//                        attachmentNameTv.setText(fileName);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    // Utils.showToast(getResources().getString(R.string.somethingWrong),
                    // mContext);
                }
                break;
            case Constants.CROP_FROM_CAMERA:
                Bundle extras = data.getExtras();
                // get the cropped bitmap
                thePic = extras.getParcelable("data");
                userImagePath = camerahelp.saveToInternalSorage(thePic);
                constantData.setUserImagePath(userImagePath);
                if (imageFile == null) {
                } else {
                    img_Camera.setImageBitmap(thePic);
                    boolean isUpdatePhoto = true;
                    // updateProfile(isUpdatePhoto);
                 //   String param1 = edtTxt1.getText().toString();
                 //   String param2 = edtTxt2.getText().toString();
                    checkPermissionForCamera(ImageUploadActivity.this);
                    SendHttpRequestTask t = new SendHttpRequestTask();

                    String[] params = new String[]{url, "firstname", "lastname"};
                    t.execute(params);
                }
                break;
            case Constants.PICK_FROM_FILE:

                try {
                    checkPermissionForCamera(ImageUploadActivity.this);
                    InputStream inputStream = getContentResolver().openInputStream(
                            data.getData());
//                    File uploadPhotoFile = camerahelp
//                            .getNewFileForCamera(Constants.SAVED_IMAGE_FOLDER_NAME);
                    imageFile = camerahelp
                            .getNewFileForCamera(Constants.SAVED_IMAGE_FOLDER_NAME);
                    userImagePath = imageFile.getPath();
                    constantData.setUserImagePath(userImagePath);
                    camerahelp.setmImageCaptureUri(imageFile);
                    FileOutputStream fileOutputStream = new FileOutputStream(
                            imageFile);
                    camerahelp.copyStream(inputStream, fileOutputStream);
                    fileOutputStream.close();
                    inputStream.close();
                    if (imageFile.exists()) {
                        picUri = Uri.fromFile(imageFile); // convert path to
                        performCrop();
//                        camerahelp.setmImageCaptureUri(uploadPhotoFile);
//                        camerahelp.startCropImage(camerahelp.getmImageCaptureUri());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
//                    Log.e("onActivityResult:Gallery",
//                            "Error while creating temp file", e);
                }
                break;
        }
    }

    private void performCrop() {
        // take care of exceptions
        try {
            // call the standard crop action intent (the user device may not
            // support it)
            Intent cropIntent = new Intent("com.android.camera.action.CROP");
            // indicate image type and Uri
            cropIntent.setDataAndType(picUri, "image/*");
            // set crop properties
            cropIntent.putExtra("crop", "true");
            // indicate aspect of desired crop
            cropIntent.putExtra("aspectX", 1);
            cropIntent.putExtra("aspectY", 1);
            // indicate output X and Y
            cropIntent.putExtra("outputX", 256);
            cropIntent.putExtra("outputY", 256);
            // retrieve data on return
            cropIntent.putExtra("return-data", true);
            // start the activity - we handle returning in onActivityResult
            startActivityForResult(cropIntent, Constants.CROP_FROM_CAMERA);
        }
        // respond to users whose devices do not support the crop action
        catch (ActivityNotFoundException anfe) {
            Toast toast = Toast
                    .makeText(this, "This device doesn't support the crop action!", Toast.LENGTH_SHORT);
            toast.show();
        }
    }


    public void checkPermissionForCamera(ImageUploadActivity activity) {
        int permission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (permission != PackageManager.PERMISSION_GRANTED) {
            // We don't have permission so prompt the user
            ActivityCompat.requestPermissions(
                    activity,
                    PERMISSIONS_STORAGE,
                    REQUEST_EXTERNAL_STORAGE
            );
        }


    }


    private class SendHttpRequestTask extends AsyncTask<String, Void, String> {
String response;

        @Override
        protected String doInBackground(String... params) {
            String url = params[0];
            String firtsname = params[1];
            String lastname = params[2];


            // Bitmap b = BitmapFactory.decodeResource(ImageUploadActivity.this.getResources(), userImagePath);

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            thePic.compress(Bitmap.CompressFormat.PNG, 0, baos);

            try {
                HttpClient client = new HttpClient(url);
                client.connectForMultipart();
                client.addFormPart("first_name", "Swarup");
                client.addFormPart("last_name", "Gosavi");
                client.addFormPart("email", "swarup.gosavigfddgfhf@outlook.com");
                client.addFormPart("password", "AbCd1234 ");
                client.addFormPart("role", "5");
                client.addFormPart("company", "Torinit");
                client.addFormPart("phone", "1324567890");
                client.addFilePart("agency_logo", "agent.png", baos.toByteArray());
                client.finishMultipart();
                response = client.getResponse();

            } catch (Throwable t) {
                t.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(String data) {
            Log.e("data", response);
            try {
                JSONObject userObject = new JSONObject(response);
                String message_code = userObject.getString("message_code");
                String user_id = userObject.getString("user_id");
                Log.e("message_code",message_code);
                Log.e("user_id",user_id);
            }catch (Exception e)
            {
                e.printStackTrace();
            }


            //   item.setActionView(null);

        }


    }
}