package xyz.theapptest.nuvo.adapter;


import android.content.Context;
import android.graphics.Typeface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import xyz.theapptest.nuvo.R;
import xyz.theapptest.nuvo.utils.ConstantData;
import xyz.theapptest.nuvo.utils.OngoingArtistJob;
import xyz.theapptest.nuvo.utils.ProducerJob;

import android.content.Context;
import android.graphics.Typeface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import xyz.theapptest.nuvo.R;
import xyz.theapptest.nuvo.utils.ConstantData;
import xyz.theapptest.nuvo.utils.OngoingArtistJob;
import xyz.theapptest.nuvo.utils.ProducerJob;

/**
 * Created by ADMIN on 8/6/2016.
 */
public class ProducerJobRequest extends ArrayAdapter<ProducerJob> {
    ArrayList<ProducerJob> requestjobList;
    LayoutInflater vi;
    int Resource;
    ProducerJobRequest.ViewHolder holder;
    String url;

    ConstantData constData;

    public ProducerJobRequest(Context context, int resource, ArrayList<ProducerJob> objects) {
        super(context, resource, objects);
        vi = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        Resource = resource;
        requestjobList = objects;


    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // convert view = design

        ProducerJobRequest.ViewHolder holder = null;

        if (convertView == null) {
            convertView = vi.inflate(Resource, null);
            holder = new ProducerJobRequest.ViewHolder();

            holder.tv_title = (TextView) convertView.findViewById(R.id.job_title);

            holder.tv_date = (TextView) convertView.findViewById(R.id.job_date);

            holder.tv_firstname = (TextView) convertView.findViewById(R.id.job_firstname);

            holder.tv_lastname = (TextView) convertView.findViewById(R.id.job_lastname);

            holder.tv_status = (TextView) convertView.findViewById(R.id.job_status);

            convertView.setTag(holder);
        } else {
            holder = (ProducerJobRequest.ViewHolder) convertView.getTag();
        }


        if (requestjobList.get(position).getTitle() != null && !requestjobList.get(position).getTitle().isEmpty()) {
            holder.tv_title.setText(requestjobList.get(position).getTitle());
            Typeface facetxtsigintextbox = Typeface.createFromAsset(getContext().getAssets(),
                    "font/OpenSans-Semibold.ttf");
            holder.tv_title.setTypeface(facetxtsigintextbox);


        }
        if (requestjobList.get(position).getCreatedon() != null && !requestjobList.get(position).getCreatedon().isEmpty()) {

            Typeface facetxtsigintextbox = Typeface.createFromAsset(getContext().getAssets(),
                    "font/OpenSans-Light.ttf");
            String[] dateArray = requestjobList.get(position).getCreatedon().split(" ");
            String createdon = dateArray[0];
            holder.tv_date.setText(createdon);
            holder.tv_date.setTypeface(facetxtsigintextbox);
        }

        if (requestjobList.get(position).getStatus() != null && !requestjobList.get(position).getStatus().isEmpty()) {
            if (requestjobList.get(position).getStatus().equalsIgnoreCase("11")) {
                holder.tv_status.setText("Pending");
            } else {
                if (requestjobList.get(position).getStatus().equalsIgnoreCase("12")) {
                    holder.tv_status.setText("Ongoing");
                } else {
                    if (requestjobList.get(position).getStatus().equalsIgnoreCase("13")) {
                        holder.tv_status.setText("Completed");
                    }else
                    {
                        if(requestjobList.get(position).getStatus().equalsIgnoreCase("14"))
                        {
                            holder.tv_status.setText("Rejected");
                        }else
                        {
                            if(requestjobList.get(position).getStatus().equalsIgnoreCase("15"))
                            {
                                holder.tv_status.setText("Accepted");
                            }else
                            {
                                if(requestjobList.get(position).getStatus().equalsIgnoreCase("16"))
                                {
                                    holder.tv_status.setText("Applied");
                                }else
                                {
                                    if(requestjobList.get(position).getStatus().equalsIgnoreCase("17"))
                                    {
                                        holder.tv_status.setText("Expired");
                                    }
                                }
                            }
                        }
                    }
                }
            }


            Typeface facetxtsiginbelow = Typeface.createFromAsset(getContext().getAssets(),
                    "font/OpenSans-Light.ttf");


            holder.tv_status.setTypeface(facetxtsiginbelow);
        }

        if (requestjobList.get(position).getFirstname() != null && !requestjobList.get(position).getFirstname().isEmpty()) {
            holder.tv_firstname.setText(requestjobList.get(position).getFirstname());
            Typeface facetxtsigintextbox = Typeface.createFromAsset(getContext().getAssets(),
                    "font/OpenSans-Regular.ttf");
            holder.tv_firstname.setTypeface(facetxtsigintextbox);
        }
        if (requestjobList.get(position).getLastname() != null && !requestjobList.get(position).getLastname().isEmpty()) {
            holder.tv_lastname.setText(requestjobList.get(position).getLastname());
            Typeface facetxtsiginbelow = Typeface.createFromAsset(getContext().getAssets(),
                    "font/OpenSans-Regular.ttf");
            holder.tv_lastname.setTypeface(facetxtsiginbelow);
        }


        return convertView;

    }


    static class ViewHolder {

        public TextView tv_title;
        public TextView tv_date;
        public TextView tv_firstname;
        public TextView tv_lastname;
        public TextView tv_status;


    }


}



