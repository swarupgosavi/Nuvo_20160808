package xyz.theapptest.nuvo.ui;

/**
 * Created by trtcpu007 on 19/7/16.
 */

import android.app.Dialog;
import android.content.Context;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import xyz.theapptest.nuvo.R;

public class CustomizeDialog extends Dialog implements OnClickListener {
    Button okButton;
    Context mContext;
    TextView mTitle = null;
    TextView mMessage = null;
    View v = null;
    public CustomizeDialog(Context context) {
        super(context);
        mContext = context;
        /** 'Window.FEATURE_NO_TITLE' - Used to hide the mTitle */
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        /** Design the dialog in main.xml file */
        setContentView(R.layout.iphone_alertdialog);
        v = getWindow().getDecorView();
        v.setBackgroundResource(android.R.color.transparent);
        mTitle = (TextView) findViewById(R.id.dialogTitle);
        mMessage = (TextView) findViewById(R.id.dialogMessage);
        okButton = (Button) findViewById(R.id.OkButton);
        okButton.setOnClickListener(this);
    }
    @Override
    public void onClick(View v) {
        /** When OK Button is clicked, dismiss the dialog */
        if (v == okButton) {
            dismiss();
        }
    }
    @Override
    public void setTitle(CharSequence title) {
        super.setTitle(title);
        mTitle.setText(title);
    }
    @Override
    public void setTitle(int titleId) {
        super.setTitle(titleId);
        mTitle.setText(mContext.getResources().getString(titleId));
    }
    /**
     * Set the message text for this dialog's window.
     *
     * @param message
     *      - The new message to display in the title.
     */
    public void setMessage(CharSequence message) {
        mMessage.setText(message);
        mMessage.setMovementMethod(ScrollingMovementMethod.getInstance());
    }
    /**
     * Set the message text for this dialog's window. The text is retrieved from the resources with the supplied
     * identifier.
     *
     * @param messageId
     *      - the message's text resource identifier <br>
     * @see <b>Note : if resourceID wrong application may get crash.</b><br>
     *   Exception has not handle.
     */
    public void setMessage(int messageId) {
        mMessage.setText(mContext.getResources().getString(messageId));
        mMessage.setMovementMethod(ScrollingMovementMethod.getInstance());
    }
}