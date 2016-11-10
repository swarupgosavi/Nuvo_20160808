package xyz.theapptest.nuvo.adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import xyz.theapptest.nuvo.R;
import xyz.theapptest.nuvo.utils.ConstantData;
import xyz.theapptest.nuvo.utils.FinfjobArtist;
import xyz.theapptest.nuvo.utils.OngoingArtistJob;

/**
 * Created by trtcpu007 on 1/8/16.
 */

public class Findartistadapter extends ArrayAdapter<FinfjobArtist> {
    ArrayList<FinfjobArtist> requestjobList;
    LayoutInflater vi;
    int Resource;
    ViewHolder holder;
    String url;
    Jobwithaudition QuantityClickEvent;
    ConstantData constData;

    public Findartistadapter(Context context, int resource, ArrayList<FinfjobArtist> objects, Jobwithaudition pQuantityClickEvent) {
        super(context, resource, objects);
        vi = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        Resource = resource;
        requestjobList = objects;
        this.QuantityClickEvent = pQuantityClickEvent;

    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // convert view = design

        ViewHolder holder = null;

        if (convertView == null) {
            convertView = vi.inflate(Resource, null);
            holder = new ViewHolder();

            holder.tvjobname = (TextView) convertView.findViewById(R.id.aud_name);

            holder.tvcompanyname = (TextView) convertView.findViewById(R.id.pers_companyname);

            holder.tvfirtstname = (TextView) convertView.findViewById(R.id.pers_firstname);

            holder.tvlastname = (TextView) convertView.findViewById(R.id.pers_lastname);

            holder.tvdate = (TextView) convertView.findViewById(R.id.aud_date);

            holder.imgselection = (ImageView) convertView.findViewById(R.id.imgselected);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        if (requestjobList.get(position).getSelected() == 0)
            holder.imgselection.setBackgroundResource(R.drawable.itemselected);
        else
            holder.imgselection.setBackgroundResource(R.drawable.selected_blue);




        holder.imgselection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("id",requestjobList.get(position).getJobid());
                QuantityClickEvent.onaudionclick(v, position,requestjobList.get(position).getJobid());
            }
        });

        if (requestjobList.get(position).getFirstname() != null) {
            holder.tvfirtstname.setText(requestjobList.get(position).getFirstname());
            Typeface facetxtsigintextbox = Typeface.createFromAsset(getContext().getAssets(),
                    "font/OpenSans-Light.ttf");
            holder.tvfirtstname.setTypeface(facetxtsigintextbox);

        }
        if (requestjobList.get(position).getLastname() != null) {
            holder.tvlastname.setText(requestjobList.get(position).getLastname());
            Typeface facetxtsigintextbox = Typeface.createFromAsset(getContext().getAssets(),
                    "font/OpenSans-Light.ttf");
            holder.tvlastname.setTypeface(facetxtsigintextbox);
        }
        if (requestjobList.get(position).getTitle() != null) {
            holder.tvjobname.setText(requestjobList.get(position).getTitle());
            Typeface facetxtsigin = Typeface.createFromAsset(getContext().getAssets(),
                    "font/OpenSans-Semibold.ttf");
            holder.tvjobname.setTypeface(facetxtsigin);
        }
        if (requestjobList.get(position).getCompanyname() != null) {
            holder.tvcompanyname.setText(requestjobList.get(position).getCompanyname());
            Typeface facetxtsiginbelow = Typeface.createFromAsset(getContext().getAssets(),
                    "font/OpenSans-Light.ttf");
            holder.tvcompanyname.setTypeface(facetxtsiginbelow);
        }
        if (requestjobList.get(position).getCreatedon() != null) {
            //holder.tvdate.setText(requestjobList.get(position).getCreatedon());
            Typeface facetxtsiginbelow = Typeface.createFromAsset(getContext().getAssets(),
                    "font/OpenSans-Light.ttf");
            String[] dateArray = requestjobList.get(position).getCreatedon().split(" ");
            String createdon = dateArray[0];
            holder.tvdate.setText(createdon);


            holder.tvdate.setTypeface(facetxtsiginbelow);
        }


        return convertView;

    }


    static class ViewHolder {

        public TextView tvjobname;
        public TextView tvcompanyname;
        public TextView tvfirtstname;
        public TextView tvlastname;
        public TextView tvdate;
        public ImageView imgselection;


    }


}





