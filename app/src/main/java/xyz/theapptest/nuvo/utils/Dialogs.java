package xyz.theapptest.nuvo.utils;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;

import xyz.theapptest.nuvo.R;


public class Dialogs {

    /**
     * Display Common Alert Dialog
     *
     * @param context
     * @param title
     * @param message
     */
    public static void showAlert(final Context context, String title, String message, final boolean isAuthErr) {
        new AlertDialog.Builder(context)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton(android.R.string.ok,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,
                                                int which) {
                                dialog.dismiss();
                                if (isAuthErr) {
                                 /*   Intent intent = new Intent(context, LoginActivity.class);
                                    intent.putExtra(Constants.Key_Auth_Err, isAuthErr);
                                    context.startActivity(intent);*/

                                }
                            }
                        }).show();


    }


    public static void showAlertcommon(final Context context, String title, String message) {
        new AlertDialog.Builder(context)
                .setTitle(title)
                .setMessage(message)
                .setCancelable(false)
                .setPositiveButton(android.R.string.ok,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,
                                                int which) {
                                dialog.dismiss();

                            }
                        }).show();


    }


    public static void showNoConnectionDialog(final Context context) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(R.string.no_connection_title);
        builder.setMessage(R.string.no_connection);
        builder.setCancelable(true);

        builder.setNegativeButton(R.string.ok,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                        return;
                    }
                });


        builder.show();
    }

    /**
     * @param context
     * @return
     */
    public static ProgressDialog ShowProgressDialog(Context context) {
        ProgressDialog progressdialog;
        progressdialog = new ProgressDialog(context);
        progressdialog.setTitle(context
                .getString(R.string.ProgressDialog_title));
        progressdialog.setMessage(context
                .getString(R.string.ProgressDialog_message));
        progressdialog.setCancelable(false);
        progressdialog.setIndeterminate(true);
        return progressdialog;
    }

}
