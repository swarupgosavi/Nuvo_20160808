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
import xyz.theapptest.nuvo.utils.ProducerJob;

/**
 * Created by ADMIN on 8/6/2016.
 */
public class AudiotionDsiplayAdapter extends ArrayAdapter<AuditionDisplayObjects> {
    ArrayList<AuditionDisplayObjects> requestjobList;
    LayoutInflater vi;
    int Resource;
    AudiotionDsiplayAdapter.ViewHolder holder;
    String url;

    ConstantData constData;

    public AudiotionDsiplayAdapter(Context context, int resource, ArrayList<AuditionDisplayObjects> objects) {
        super(context, resource, objects);
        vi = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        Resource = resource;
        requestjobList = objects;


    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // convert view = design

        AudiotionDsiplayAdapter.ViewHolder holder = null;

        if (convertView == null) {
            convertView = vi.inflate(Resource, null);
            holder = new AudiotionDsiplayAdapter.ViewHolder();

            holder.tv_title = (TextView) convertView.findViewById(R.id.aud_name);

            holder.tv_date = (TextView) convertView.findViewById(R.id.aud_date);

            holder.tvaidtiontime = (TextView) convertView.findViewById(R.id.audi_duration);

            holder.tv_audtionview = (TextView) convertView.findViewById(R.id.audi_views);

            holder.tv_audineers = (TextView) convertView.findViewById(R.id.audioneers);

            convertView.setTag(holder);
        } else {
            holder = (AudiotionDsiplayAdapter.ViewHolder) convertView.getTag();
        }


        if (requestjobList.get(position).getTitle() != null) {

            holder.tv_title.setText(requestjobList.get(position).getTitle());
            Typeface facetxtsigintextbox = Typeface.createFromAsset(getContext().getAssets(),
                    "font/OpenSans-Semibold.ttf");
            holder.tv_title.setTypeface(facetxtsigintextbox);


        }
        if (requestjobList.get(position).getCreated_on() != null) {

            Typeface facetxtsigintextbox = Typeface.createFromAsset(getContext().getAssets(),
                    "font/OpenSans-Light.ttf");
            String[] dateArray = requestjobList.get(position).getCreated_on().split(" ");
            String createdon = dateArray[0];
            holder.tv_date.setText(createdon);
            holder.tv_date.setTypeface(facetxtsigintextbox);
        }
        if (requestjobList.get(position).getDuration() != null) {
            holder.tvaidtiontime.setText(requestjobList.get(position).getDuration());
            Typeface facetxtsigintextbox = Typeface.createFromAsset(getContext().getAssets(),
                    "font/OpenSans-Regular.ttf");
            holder.tvaidtiontime.setTypeface(facetxtsigintextbox);
        }
        if (requestjobList.get(position).getViews() != null) {
            holder.tv_audtionview.setText(requestjobList.get(position).getViews() + " Views");
            Typeface facetxtsiginbelow = Typeface.createFromAsset(getContext().getAssets(),
                    "font/OpenSans-Light.ttf");
            holder.tv_audtionview.setTypeface(facetxtsiginbelow);
        }
        if (requestjobList.get(position).getAuditioneers() != null) {
            holder.tv_audineers.setText(requestjobList.get(position).getAuditioneers() + " Auditionees");
            Typeface facetxtsiginbelow = Typeface.createFromAsset(getContext().getAssets(),
                    "font/OpenSans-Light.ttf");
            holder.tv_audineers.setTypeface(facetxtsiginbelow);
        }



        return convertView;

    }


    static class ViewHolder {

        public TextView tv_title;
        public TextView tv_date;
        public TextView tvaidtiontime;
        public TextView tv_audtionview;
        public TextView tv_audineers;


    }


}

