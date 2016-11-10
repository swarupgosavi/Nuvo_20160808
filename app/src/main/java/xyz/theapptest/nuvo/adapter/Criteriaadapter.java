package xyz.theapptest.nuvo.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import xyz.theapptest.nuvo.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by trtcpu007 on 15/7/16.
 */

public class Criteriaadapter extends BaseAdapter {

    ArrayList arrayList;
    private Activity activity;
    private LayoutInflater inflater;
    private List<String> listItems;
    Typeface openSansLight;
    String sOpenSansLight;

    public Criteriaadapter(Activity activity,List<String> list){
        this.activity = activity;
        this.listItems = list;

    }
    @Override
    public int getCount() {
        if(listItems!=null) {
            return listItems.size();
        }
        return 0;
    }

    @Override
    public Object getItem(int position) {
        return listItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }



    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        sOpenSansLight = "font/OpenSans-Light.ttf";


        openSansLight = Typeface.createFromAsset(this.activity.getAssets(),
                sOpenSansLight);

        if (inflater == null)
            inflater = (LayoutInflater) activity
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.list_rows_new, null);
        }

        ImageView imgPlay = (ImageView)convertView.findViewById(R.id.img_playicon);
        TextView txtPercent = (TextView)convertView.findViewById(R.id.txtpercent);
        RatingBar ratingBar =(RatingBar)convertView.findViewById(R.id.ratingBar);
        ratingBar.setNumStars(5);


        if(position == 0 | position==1){

            imgPlay.setImageResource(R.drawable.play_button);
            txtPercent.setText("100%");
            ratingBar.setRating(5);
        }else if (position == 2 |position ==3){

            imgPlay.setImageResource(R.drawable.seventy_per);
            txtPercent.setText("70%");
            ratingBar.setRating(5);
        }
        else if (position == 4 |position == 5){
            imgPlay.setImageResource(R.drawable.fifty_per);
            txtPercent.setText("50%");
            ratingBar.setRating(4);

        }else if(position == 6 | position==7){


            imgPlay.setImageResource(R.drawable.thrty_percent);
            txtPercent.setText("30%");
            ratingBar.setRating(4);
        }else if(position ==  8| position==9){


            imgPlay.setImageResource(R.drawable.thrty_percent);
            txtPercent.setText("30%");
            ratingBar.setRating(3);
        }

        // ImageView imgDivider = (ImageView)convertView.findViewById(R.id.img_divider);
        ImageView imgGreen = (ImageView)convertView.findViewById(R.id.imggreen);
        ImageView imgSelected = (ImageView)convertView.findViewById(R.id.imgselected);


      //  TextView song = (TextView)convertView.findViewById(R.id.txt_songname);



       /* Button btnOne = (Button)convertView.findViewById(R.id.btn_one);
        btnOne.setTypeface(openSansLight);
        Button btnTwo = (Button)convertView.findViewById(R.id.btn_two);
        btnTwo.setTypeface(openSansLight);
        Button btnThree = (Button)convertView.findViewById(R.id.btn_three);
        btnThree.setTypeface(openSansLight);
        Button btnFour = (Button)convertView.findViewById(R.id.btn_four);
        btnFour.setTypeface(openSansLight);
        Button btnFive = (Button)convertView.findViewById(R.id.btn_five);
        btnFive.setTypeface(openSansLight);*/

        return convertView;
    }

}



