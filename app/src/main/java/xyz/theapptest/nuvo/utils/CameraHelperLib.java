package xyz.theapptest.nuvo.utils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;


//import eu.janmuller.android.simplecropimage.CropImage;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.ArrayAdapter;



/**
 * Created by user on 10/12/2015.
 */
public class CameraHelperLib {
    Context mContext;
    File uploadPhotoFile;
    int itemClicked = 0;
    public File getmImageCaptureUri() {
        return uploadPhotoFile;
    }

    public void setmImageCaptureUri(File mImageCaptureFile) {
        this.uploadPhotoFile = mImageCaptureFile;
    }

    public CameraHelperLib(Context mContext) {
        super();
        this.mContext = mContext;
    }

    public String saveToInternalSorage(Bitmap bitmapImage) {
        File path = getNewFileForCamera(Constants.SAVED_IMAGE_FOLDER_NAME);
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(path);
            bitmapImage.compress(Bitmap.CompressFormat.PNG, 100, fos);
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return path.getPath();
    }

    public File getNewFileForCamera(String folderName) {
        File dataStorageDir = new File(
                Environment.getExternalStorageDirectory(), folderName);
        if (!dataStorageDir.exists()) {
            dataStorageDir.mkdirs();
        }
        File file = new File(dataStorageDir.getAbsolutePath() + File.separator
                + System.currentTimeMillis() + ".jpg");
        return file;
    }

    public void imageChooser() {
        final String[] items = new String[]{"Take from camera",
                "Select from gallery"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(mContext,
                android.R.layout.select_dialog_item, items);
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setTitle("Select Image");
        builder.setAdapter(adapter, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int item) { // pick from
                // camera
                if (item == 0) {
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

                    try {
                        // String imageFilePath =
                        // Environment.getExternalStorageDirectory().getAbsolutePath()
                        // + "/picture.jpg";
                        File imageFile = getNewFileForCamera(Constants.SAVED_IMAGE_FOLDER_NAME);// new
                        // File(imageFilePath);
                        Uri picUri = Uri.fromFile(imageFile); // convert path to
                        // Uri
                        setmImageCaptureUri(imageFile);
                        intent.putExtra("return-data", true);

                        intent.putExtra(MediaStore.EXTRA_OUTPUT, picUri);
                        ((Activity) mContext).startActivityForResult(intent,
                                Constants.PICK_FROM_CAMERA);
                    } catch (ActivityNotFoundException e) {
                        Log.e("Camera", "cannot take picture", e);
                    }
                } else if(item == 1){ // pick from file
                    Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
                    photoPickerIntent.setType("image/*");
                    photoPickerIntent.setAction(Intent.ACTION_GET_CONTENT);
                    ((Activity) mContext).startActivityForResult(
                            photoPickerIntent, Constants.PICK_FROM_FILE);
                }
            }
        });
        final AlertDialog dialog = builder.create();
        dialog.show();
    }


    public void editProfileImageChooser(){
        final String[] items = new String[]{"Take from camera",
                "Select from gallery", "Remove Photo"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(mContext,
                android.R.layout.select_dialog_item, items);
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setTitle("Select Image");

        builder.setAdapter(adapter, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int item) { // pick from
                // camera
                if (item == 0) {
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    try {
                        // String imageFilePath =
                        // Environment.getExternalStorageDirectory().getAbsolutePath()
                        // + "/picture.jpg";
                        File imageFile = getNewFileForCamera(Constants.SAVED_IMAGE_FOLDER_NAME);// new
                        // File(imageFilePath);
                        Uri picUri = Uri.fromFile(imageFile); // convert path to
                        // Uri
                        setmImageCaptureUri(imageFile);
                        intent.putExtra("return-data", true);
                        intent.putExtra(MediaStore.EXTRA_OUTPUT, picUri);
                        ((Activity) mContext).startActivityForResult(intent,
                                Constants.PICK_FROM_CAMERA);
                    } catch (ActivityNotFoundException e) {
                        Log.e("Camera", "cannot take picture", e);
                    }
                } else if(item == 1){ // pick from file
                    Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
                    photoPickerIntent.setType("image/*");
                    photoPickerIntent.setAction(Intent.ACTION_GET_CONTENT);
                    ((Activity) mContext).startActivityForResult(
                            photoPickerIntent, Constants.PICK_FROM_FILE);
                }else{

                    dialog.dismiss();
                  //  EditProfileActivity.removeProfilePhoto();
                }
            }
        });
        final AlertDialog dialog = builder.create();
        dialog.show();

    }

    public int getItemClicked(){
        return itemClicked;
    }
/*
    public void startCropImage(File mFileTemp) {
        Intent intent = new Intent(mContext, CropImage.class);
        intent.putExtra(CropImage.IMAGE_PATH, mFileTemp.getPath());
        // intent.putExtra(CropImage.SCALE, true);
        intent.putExtra(CropImage.ASPECT_X, 4);
        intent.putExtra(CropImage.ASPECT_Y, 4);
        ((Activity) mContext).startActivityForResult(intent,
                Utils.CROP_FROM_CAMERA);
    }
*/

    public String getPath(Uri uri) {
        String[] projection = {MediaStore.Images.Media.DATA};
        Cursor cursor = ((Activity) mContext).managedQuery(uri, projection,
                null, null, null);
        int column_index = cursor
                .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }

    public static void copyStream(InputStream input, OutputStream output)
            throws IOException {
        byte[] buffer = new byte[1024];
        int bytesRead;
        while ((bytesRead = input.read(buffer)) != -1) {
            output.write(buffer, 0, bytesRead);
        }
    }

    public static Bitmap compressBitmap(Bitmap bitmap) {
        Bitmap bitmap2 = null;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 50, baos);
        byte[] arr = baos.toByteArray();
        try {
            bitmap2 = BitmapFactory.decodeByteArray(arr, 0, arr.length);
        } catch (OutOfMemoryError e) {
            Log.e("inside compressBitmap", "outofmemory");
        }
        return bitmap2;
    }

}
