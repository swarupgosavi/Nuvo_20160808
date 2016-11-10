package xyz.theapptest.nuvo.utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;

/**
 * Created by trtcpu007 on 8/7/16.
 */

public  class DialogShowMethods {

    public static void showDialog(Activity act,String title, String message)
    {
        AlertDialog alertDialog = new AlertDialog.Builder(act).create();

        // Setting Dialog Title
        alertDialog.setTitle(title);

        // Setting Dialog Message
        alertDialog.setMessage(message);



        // Setting OK Button
        alertDialog.setButton("OK", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog,int which)
            {
                // Write your code here to execute after dialog    closed

            }
        });

        // Showing Alert Message
        alertDialog.show();
    }

}
