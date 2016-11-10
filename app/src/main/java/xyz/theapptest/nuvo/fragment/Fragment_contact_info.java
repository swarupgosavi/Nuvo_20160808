package xyz.theapptest.nuvo.fragment;

import android.app.Activity;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;

import xyz.theapptest.nuvo.R;
import xyz.theapptest.nuvo.ui.CustomizeDialog;
import xyz.theapptest.nuvo.ui.SignInActivity;
import xyz.theapptest.nuvo.utils.ConstantData;
import xyz.theapptest.nuvo.utils.DialogShowMethods;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by trtcpu007 on 7/7/16.
 */

public class Fragment_contact_info extends Fragment implements View.OnClickListener {
    Button bt_next, bt_back;
    EditText ed_email, ed_password, ed_phonennumber;
    String strEmailAddress, strPassword, str_phonennumber;
    String regEx;
    String email, password;
    ConstantData constData;
    private int mPreviousInputType;
    String phoneValidationUSCandaRegex;
    CustomizeDialog customizeDialog = null;
    private boolean mIsShowingPassword;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_contact_info, container, false);
        init(view);
        emailidexpression();
        typeInterface();
        onclickevent();
        checkEdittextPassword();


        return view;
    }

    public interface onSomeEventListener {
        public void someEvent(int s);
    }

    private void onclickevent() {
        bt_next.setOnClickListener(this);
        bt_back.setOnClickListener(this);


    }

    private void checkEdittextPassword() {

        ed_password.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int DRAWABLE_LEFT = 0;
                final int DRAWABLE_TOP = 1;
                final int DRAWABLE_RIGHT = 2;
                final int DRAWABLE_BOTTOM = 3;

                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (event.getRawX() >= (ed_password.getRight() - ed_password.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {

                        if (mIsShowingPassword) {
                            showPassword();
                        } else {
                            hidePassword();
                        }

                    } else {

                    }
                }


                return false;
            }
        });
    }

    private void setInputType(int inputType, boolean keepState) {
        int selectionStart = -1;
        int selectionEnd = -1;
        if (keepState) {
            selectionStart = ed_password.getSelectionStart();
            selectionEnd = ed_password.getSelectionEnd();
        }
        ed_password.setInputType(inputType);
        if (keepState) {
            ed_password.setSelection(selectionStart, selectionEnd);
        }
    }

    public void showPassword() {
        mIsShowingPassword = false;
        setInputType(mPreviousInputType, true);
        mPreviousInputType = -1;
        if (null != mOnPasswordDisplayListener) {
            mOnPasswordDisplayListener.onPasswordShow();
        }
    }

    public void hidePassword() {
        mPreviousInputType = ed_password.getInputType();
        mIsShowingPassword = true;
        setInputType(EditorInfo.TYPE_CLASS_TEXT | EditorInfo.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD, true);
        if (null != mOnPasswordDisplayListener) {
            mOnPasswordDisplayListener.onPasswordHide();
        }
    }

    public interface OnPasswordDisplayListener {
        public void onPasswordShow();

        public void onPasswordHide();
    }

    SignInActivity.OnPasswordDisplayListener mOnPasswordDisplayListener;

    public void setOnPasswordDisplayListener(SignInActivity.OnPasswordDisplayListener listener) {
        mOnPasswordDisplayListener = listener;
    }

    private void emailidexpression() {
        regEx =
                "^[A-Za-z0-9._%+\\-]+@[A-Za-z0-9.\\-]+\\.[A-Za-z]{2,4}$";

        phoneValidationUSCandaRegex = "^\\([4-6]{1}[0-9]{2}\\)[0-9]{3}-[0-9]{4}$";
    }

    Fragment_CreateProfile.onSomeEventListener someEventListener;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            someEventListener = (Fragment_CreateProfile.onSomeEventListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must implement onSomeEventListener");
        }
    }

    private void typeInterface() {

        Typeface facetxtsigintextbox = Typeface.createFromAsset(getActivity().getAssets(),
                "font/OpenSans-Regular.ttf");
        ed_email.setTypeface(facetxtsigintextbox);
        ed_password.setTypeface(facetxtsigintextbox);
        ed_phonennumber.setTypeface(facetxtsigintextbox);


        Typeface facetxtsigin = Typeface.createFromAsset(getActivity().getAssets(),
                "font/OpenSans-Semibold.ttf");
        bt_next.setTypeface(facetxtsigin);
        bt_back.setTypeface(facetxtsigin);


    }

    private void init(View view) {
        bt_next = (Button) view.findViewById(R.id.next);
        bt_back = (Button) view.findViewById(R.id.back);
        ed_email = (EditText) view.findViewById(R.id.ed_email);
        ed_password = (EditText) view.findViewById(R.id.ed_password);
        ed_phonennumber = (EditText) view.findViewById(R.id.ed_phonenumber);
        constData = ConstantData.getInstance();
        email = constData.getEmail();
        password = constData.getPassword();

        ed_email.setText(email);
        ed_password.setText(password);

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.back:
                Log.e("back", "back");
                someEventListener.someEvent(0);
                break;
            case R.id.next:

                strEmailAddress = ed_email.getText().toString().trim();
                strPassword = ed_password.getText().toString().trim();
                str_phonennumber = ed_phonennumber.getText().toString().trim();
                if (strEmailAddress.matches("")) {

                    DialogShowMethods.showDialog(getActivity(), "nuvo", "Please enter email.");
                } else {

                    Pattern pattern;
                    pattern = Pattern.compile(regEx);
                    Matcher matcher = pattern.matcher(strEmailAddress);
                    if (!matcher.find()) {
                        Log.e("Invalid email", "Invalid email");
                        customizeDialog = new CustomizeDialog(getActivity());
                        customizeDialog.setTitle("nuvo");
                        customizeDialog.setCancelable(false);
                        customizeDialog.setMessage( "Please enter valid email.");
                        customizeDialog.show();


                  //      DialogShowMethods.showDialog(getActivity(), "Error", "Please enter valid email.");

                    } else {
                        if (strPassword.matches("")) {

                            customizeDialog = new CustomizeDialog(getActivity());
                            customizeDialog.setTitle("nuvo");
                            customizeDialog.setCancelable(false);
                            customizeDialog.setMessage("Please enter password.");
                            customizeDialog.show();

                            //DialogShowMethods.showDialog(getActivity(), "Error", "Please enter password.");

                        } else {
                            if (str_phonennumber.matches("")) {
                                customizeDialog = new CustomizeDialog(getActivity());
                                customizeDialog.setTitle("nuvo");
                                customizeDialog.setCancelable(false);
                                customizeDialog.setMessage("Please enter phone number.");
                                customizeDialog.show();

                             //   DialogShowMethods.showDialog(getActivity(), "Error", "Please enter phone number.");
                            } else {
                                constData.setPhonenumber(str_phonennumber);
                                someEventListener.someEvent(1);
                                // Toast.makeText(getActivity(), "Validation success", Toast.LENGTH_LONG).show();


                                /*Pattern pattern1;
                                pattern1 = Pattern.compile(regEx);
                                Matcher matcher1 = pattern1.matcher(str_phonennumber);
                                if(!matcher1.find())
                                {
                                    DialogShowMethods.showDialog(getActivity(),"Error","Phone Number Is Invalid");
                                }else {
                                    someEventListener.someEvent(1);
                                    Toast.makeText(getActivity(), "Validation success", Toast.LENGTH_LONG).show();
                                }*/
                            }
                        }
                    }

                }
                break;
            default:
                break;
        }

    }
}
