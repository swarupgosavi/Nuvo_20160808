package xyz.theapptest.nuvo.ui;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import java.util.ArrayList;
import java.util.HashMap;

import xyz.theapptest.nuvo.R;
import xyz.theapptest.nuvo.utils.ConstantData;

/**
 * Created by trtcpu007 on 22/7/16.
 */

public class Radiobuttondisplayactivity extends Activity{
    ArrayList<HashMap<String, String>> langaugelist;
    ConstantData constantData;
    RadioButton rb;
    public int selectedLanguage = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_radiobutton);
        LinearLayout layout = (LinearLayout) findViewById(R.id.rootradiobutton);
        constantData = ConstantData.getInstance();
        langaugelist = constantData.getLanguage();
         RadioGroup rg = new RadioGroup(this); // create the RadioGroup

        LinearLayout.LayoutParams p = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.FILL_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        layout.addView(rg, p);
        rg.setOrientation(RadioGroup.HORIZONTAL);// or RadioGroup.VERTICAL
        for (int i = 0; i < langaugelist.size(); i++) {
             rb = new RadioButton(this);
            rb.setButtonDrawable(R.drawable.custom_btn_radio);
            rb.setId(Integer.parseInt(langaugelist.get(i).get("id")) + 20);
            rb.setText(langaugelist.get(i).get("language_name"));
            rb.setTag(Integer.parseInt(langaugelist.get(i).get("language_value")));
            rg.addView(rb);
            if(rb.isChecked())
            {
                int IDS = rb.getId() - 20;


                selectedLanguage = selectedLanguage ^ (Integer) rb.getTag();

                Log.e("dsdaasd: ", Integer.toString(selectedLanguage));

            }
            else
            {

            }
        }

    }
}
