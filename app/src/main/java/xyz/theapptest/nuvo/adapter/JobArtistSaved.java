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
import xyz.theapptest.nuvo.utils.OngoingArtistJob;

/**
 * Created by trtcpu007 on 17/8/16.
 */

public class JobArtistSaved extends ArrayAdapter<OngoingArtistJob> {
    ArrayList<OngoingArtistJob> requestjobList;
    LayoutInflater vi;
    int Resource;
    JobArtistSaved.ViewHolder holder;
    String url;
    UnsaveJobListner QuantityClickEvent;
    ConstantData constData;

    public JobArtistSaved(Context context, int resource, ArrayList<OngoingArtistJob> objects, UnsaveJobListner pQuantityClickEvent) {
        super(context, resource, objects);
        vi = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        Resource = resource;
        requestjobList = objects;
        this.QuantityClickEvent = pQuantityClickEvent;

    }

    @Override
    public int getCount() {
        if(requestjobList == null){
            return 0;
        }
        return requestjobList.size();
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // convert view = design

        JobArtistSaved.ViewHolder holder = null;

        if (convertView == null) {
            convertView = vi.inflate(Resource, null);
            holder = new JobArtistSaved.ViewHolder();

            holder.tvfirstname = (TextView) convertView.findViewById(R.id.pers_firstname);

            holder.tvlastname = (TextView) convertView.findViewById(R.id.pers_lastname);

            holder.jobname = (TextView) convertView.findViewById(R.id.aud_name);

            holder.userdate = (TextView) convertView.findViewById(R.id.aud_date);

            holder.company = (TextView) convertView.findViewById(R.id.company_name);
            holder.imgselection = (ImageView) convertView.findViewById(R.id.imgselected);
            convertView.setTag(holder);
        } else {
            holder = (JobArtistSaved.ViewHolder) convertView.getTag();
        }


        holder.imgselection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("id",requestjobList.get(position).getJobid());
                QuantityClickEvent.onUnsavedata(v, position,requestjobList.get(position).getJobid());
            }
        });
        if (requestjobList.get(position).getFirstname() != null &&  !requestjobList.get(position).getFirstname().isEmpty()) {
            holder.tvfirstname.setText(requestjobList.get(position).getFirstname());
            Typeface facetxtsigintextbox = Typeface.createFromAsset(getContext().getAssets(),
                    "font/OpenSans-Light.ttf");
            holder.tvfirstname.setTypeface(facetxtsigintextbox);


        }
        if (requestjobList.get(position).getLastname() != null  &&  !requestjobList.get(position).getLastname().isEmpty()) {
            holder.tvlastname.setText(requestjobList.get(position).getLastname());
            Typeface facetxtsigintextbox = Typeface.createFromAsset(getContext().getAssets(),
                    "font/OpenSans-Light.ttf");
            holder.tvlastname.setTypeface(facetxtsigintextbox);
        }
        if (requestjobList.get(position).getTitle() != null &&  !requestjobList.get(position).getTitle().isEmpty()) {
            holder.jobname.setText(requestjobList.get(position).getTitle());
            Typeface facetxtsigintextbox = Typeface.createFromAsset(getContext().getAssets(),
                    "font/OpenSans-Regular.ttf");
            holder.jobname.setTypeface(facetxtsigintextbox);
        }
        if (requestjobList.get(position).getCompanyname() != null &&  !requestjobList.get(position).getCompanyname().isEmpty()) {
            holder.company.setText(requestjobList.get(position).getCompanyname());
            Typeface facetxtsiginbelow = Typeface.createFromAsset(getContext().getAssets(),
                    "font/OpenSans-Light.ttf");
            holder.company.setTypeface(facetxtsiginbelow);
        }
        if (requestjobList.get(position).getCreatedon() != null && !requestjobList.get(position).getCreatedon().isEmpty()) {
            String date = requestjobList.get(position).getCreatedon();
            if(date == null){

            }else{
                if(date.contains(" ")){
                    String[] dateArray = date.split(" ");
                    date = dateArray[0];

                }
                holder.userdate.setText(date);

            }

            //holder.userdate.setText(requestjobList.get(position).getCreatedon());
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
        public TextView company;
        public ImageView imgselection;
    }


}




