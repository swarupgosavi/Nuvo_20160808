package xyz.theapptest.nuvo.adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.json.JSONArray;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import xyz.theapptest.nuvo.R;
import xyz.theapptest.nuvo.utils.ConstantData;
import xyz.theapptest.nuvo.utils.FindJobs;
import xyz.theapptest.nuvo.utils.ListsongplayedInterface;
import xyz.theapptest.nuvo.utils.OngoingArtistJob;


/**
 * Created by Swarup on 7/30/2016.
 */

public class JobRequestAdaper extends ArrayAdapter<OngoingArtistJob> {
    ArrayList<OngoingArtistJob> requestjobList;
    LayoutInflater vi;
    int Resource;
    ViewHolder holder;
    String url;

    ConstantData constData;

    public JobRequestAdaper(Context context, int resource, ArrayList<OngoingArtistJob> objects) {
        super(context, resource, objects);
        vi = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        Resource = resource;
        requestjobList = objects;


    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // convert view = design

        ViewHolder holder = null;

        if (convertView == null) {
            convertView = vi.inflate(Resource, null);
            holder = new ViewHolder();

            holder.tvfirstname = (TextView) convertView.findViewById(R.id.pers_firstname);

            holder.tvlastname = (TextView) convertView.findViewById(R.id.pers_lastname);

            holder.jobname = (TextView) convertView.findViewById(R.id.aud_name);

            holder.userdate = (TextView) convertView.findViewById(R.id.aud_date);

            holder.status = (TextView) convertView.findViewById(R.id.pers_status);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }


        if (requestjobList.get(position).getFirstname() != null &&  !requestjobList.get(position).getFirstname().isEmpty()) {
            holder.tvfirstname.setText(requestjobList.get(position).getFirstname());
            Typeface facetxtsigintextbox = Typeface.createFromAsset(getContext().getAssets(),
                    "font/OpenSans-Regular.ttf");
            holder.tvfirstname.setTypeface(facetxtsigintextbox);


        }
        if (requestjobList.get(position).getLastname() != null  &&  !requestjobList.get(position).getLastname().isEmpty()) {
            holder.tvlastname.setText(requestjobList.get(position).getLastname());
            Typeface facetxtsigintextbox = Typeface.createFromAsset(getContext().getAssets(),
                    "font/OpenSans-Regular.ttf");
            holder.tvlastname.setTypeface(facetxtsigintextbox);
        }
        if (requestjobList.get(position).getTitle() != null &&  !requestjobList.get(position).getTitle().isEmpty()) {
            holder.jobname.setText(requestjobList.get(position).getTitle());
            Typeface facetxtsigintextbox = Typeface.createFromAsset(getContext().getAssets(),
                    "font/OpenSans-Regular.ttf");
            holder.jobname.setTypeface(facetxtsigintextbox);
        }
        if (requestjobList.get(position).getStatus() != null &&  !requestjobList.get(position).getStatus().isEmpty()) {
            holder.status.setText(requestjobList.get(position).getStatus());
            Typeface facetxtsiginbelow = Typeface.createFromAsset(getContext().getAssets(),
                    "font/OpenSans-Light.ttf");
            holder.status.setTypeface(facetxtsiginbelow);
        }
        if (requestjobList.get(position).getCreatedon() != null && !requestjobList.get(position).getCreatedon().isEmpty()) {
            holder.userdate.setText(requestjobList.get(position).getCreatedon());
            Typeface facetxtsiginbelow = Typeface.createFromAsset(getContext().getAssets(),
                    "font/OpenSans-Light.ttf");


            holder.userdate.setTypeface(facetxtsiginbelow);
        }




        return convertView;

    }


    static class ViewHolder {

        public TextView tvfirstname;
        public TextView tvlastname;
        public TextView userdate;
        public TextView status;
        public TextView jobname;


    }


}




