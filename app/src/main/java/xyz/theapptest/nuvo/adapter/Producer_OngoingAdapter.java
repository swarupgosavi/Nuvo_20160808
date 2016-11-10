package xyz.theapptest.nuvo.adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.ArrayList;

import xyz.theapptest.nuvo.R;
import xyz.theapptest.nuvo.pojo.Userinformation;
import xyz.theapptest.nuvo.utils.ConstantData;
import xyz.theapptest.nuvo.utils.ProducerJob;

/**
 * Created by trtcpu007 on 8/8/16.
 */

public class Producer_OngoingAdapter extends ArrayAdapter<Userinformation> {
    ArrayList<Producer_Ongoing> requestjobList;
    LayoutInflater vi;
    int Resource;
    Producer_OngoingAdapter.ViewHolder holder;
    String url;
    ArrayList<Userinformation> userinfolist;
    ConstantData constData;

    public Producer_OngoingAdapter(Context context, int resource, ArrayList<Userinformation> objects) {
        super(context, resource, objects);
        vi = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        Resource = resource;
        userinfolist = objects;



    }




    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // convert view = design

        Producer_OngoingAdapter.ViewHolder holder = null;

        if (convertView == null) {
            convertView = vi.inflate(Resource, null);
            holder = new Producer_OngoingAdapter.ViewHolder();


            holder.tv_firstname = (TextView) convertView.findViewById(R.id.txt_firstname);

            holder.tv_lastname = (TextView) convertView.findViewById(R.id.txt_lastname);

            holder.tv_rating = (RatingBar) convertView.findViewById(R.id.ratingBar);
            holder.imgonline=(ImageView)convertView.findViewById(R.id.imggreen);


            convertView.setTag(holder);
        } else {
            holder = (Producer_OngoingAdapter.ViewHolder) convertView.getTag();
        }
        final Userinformation user = userinfolist.get(position);


        if (user.getFirst_name() != null && !user.getFirst_name().isEmpty()) {
            holder.tv_firstname.setText(user.getFirst_name());
            Typeface facetxtsigintextbox = Typeface.createFromAsset(getContext().getAssets(),
                    "font/OpenSans-Regular.ttf");
            holder.tv_firstname.setTypeface(facetxtsigintextbox);
        }
        if (user.getLast_name() != null && !user.getLast_name().isEmpty()) {
            holder.tv_lastname.setText(user.getLast_name());
            Typeface facetxtsiginbelow = Typeface.createFromAsset(getContext().getAssets(),
                    "font/OpenSans-Regular.ttf");
            holder.tv_lastname.setTypeface(facetxtsiginbelow);
        }
        if (user.getRating() != null && !user.getRating() .isEmpty()) {
            holder.tv_rating.setRating(Float.parseFloat(user.getRating()));
        }
        if (user.getStatus() != null && !user.getStatus().isEmpty()) {
            if(user.getStatus() .equalsIgnoreCase("7")) {
                holder.imgonline.setBackgroundResource(R.drawable.green_circle);
            }else
            {
                holder.imgonline.setBackgroundResource(R.drawable.red_dot);
            }
        }





        return convertView;

    }


    static class ViewHolder {


        public TextView tv_firstname;
        public TextView tv_lastname;
        public RatingBar tv_rating;
        public ImageView imgonline;



    }


}



