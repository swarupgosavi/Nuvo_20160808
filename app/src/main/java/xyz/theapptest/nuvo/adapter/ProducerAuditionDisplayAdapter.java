package xyz.theapptest.nuvo.adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.util.ArrayList;

import xyz.theapptest.nuvo.R;
import xyz.theapptest.nuvo.pojo.Userinformation;
import xyz.theapptest.nuvo.utils.ConstantData;
import xyz.theapptest.nuvo.utils.ProducerJob;

/**
 * Created by trtcpu007 on 8/8/16.
 */

public class ProducerAuditionDisplayAdapter extends ArrayAdapter<Userinformation> {
    //ArrayList<Producer_audition_Object> requestjobList;
    ArrayList<Userinformation> userinfolist;
    LayoutInflater vi;
    int Resource;
    ProducerAuditionDisplayAdapter.ViewHolder holder;
    String url;
    SendOfferForProducer QuantityClickEvent;
    ConstantData constData;
    Context mContext;
    String jobiscontext;

  /*  public ProducerAuditionDisplayAdapter(Context context, int resource, ArrayList<Producer_audition_Object> objects) {
        super(context, resource, objects);
        vi = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        Resource = resource;
        requestjobList = objects;


    }*/

    public ProducerAuditionDisplayAdapter(Context context, int resource, ArrayList<Userinformation> objects,String jobid,  SendOfferForProducer pQuantityClickEvent) {
        super(context, resource, objects);
        vi = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        Resource = resource;
        userinfolist = objects;
        jobiscontext=jobid;

        this.QuantityClickEvent = pQuantityClickEvent;


    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // convert view = design

        ProducerAuditionDisplayAdapter.ViewHolder holder = null;

        if (convertView == null) {
            convertView = vi.inflate(Resource, null);
            holder = new ProducerAuditionDisplayAdapter.ViewHolder();


            holder.tv_firstname = (TextView) convertView.findViewById(R.id.txt_firstname);

            holder.tv_lastname = (TextView) convertView.findViewById(R.id.txt_lastname);
            holder.tv_imgoffer = (TextView) convertView.findViewById(R.id.img_offer);
            holder.ratingBar = (RatingBar) convertView.findViewById(R.id.ratingBar);
            holder.img_isonline=(ImageView)convertView.findViewById(R.id.imggreen);



            convertView.setTag(holder);
        } else {
            holder = (ProducerAuditionDisplayAdapter.ViewHolder) convertView.getTag();
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


        if (user.getJob_status() != null && ! user.getJob_status().isEmpty()) {
            Typeface facetxtsiginbelow = Typeface.createFromAsset(getContext().getAssets(),
                    "font/OpenSans-Regular.ttf");
            holder.tv_imgoffer.setTypeface(facetxtsiginbelow);
            if ( user.getJob_status().equalsIgnoreCase("13")) {
                holder.tv_imgoffer.setText("Completed");

            } else {
                if (user.getJob_status().equalsIgnoreCase("14")) {
                    holder.tv_imgoffer.setText("Rejected");
                } else {
                    if (user.getJob_status().equalsIgnoreCase("15")) {
                        holder.tv_imgoffer.setText("Offer");
                    } else {
                        if (user.getJob_status().equalsIgnoreCase("16")) {
                            holder.tv_imgoffer.setText("Offer");

                        } else {
                            if (user.getJob_status().equalsIgnoreCase("17")) {
                                holder.tv_imgoffer.setText("Expired");
                            } else {
                                if (user.getJob_status().equalsIgnoreCase("12")) {
                                    holder.tv_imgoffer.setText("Ongoing");
                                } else {
                                    if (user.getJob_status().equalsIgnoreCase("11")) {
                                        holder.tv_imgoffer.setText("Pending");
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        holder.tv_imgoffer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (user.getJob_status() .equalsIgnoreCase("15") || user.getJob_status() .equalsIgnoreCase("16")) {
                    Log.e("inside click","inside job status");
                    Log.e("jobiscontext",jobiscontext);

                    Log.e("user.getUser_id()",user.getUser_id());

                    QuantityClickEvent.onQuantityClickEvent(v, position, jobiscontext, user.getUser_id() );
                }
            }
        });

        if (user.getRating() != null && !user.getRating() .isEmpty()) {
            holder.ratingBar.setRating(Float.parseFloat(user.getRating()));
        }
        if (user.getStatus() != null && !user.getStatus().isEmpty()) {
            if(user.getStatus() .equalsIgnoreCase("7")) {
                holder.img_isonline.setBackgroundResource(R.drawable.green_circle);
            }else
            {
                holder.img_isonline.setBackgroundResource(R.drawable.red_dot);
            }
        }


        return convertView;

    }


    static class ViewHolder {


        public TextView tv_firstname;
        public TextView tv_lastname;
        public TextView tv_imgoffer;
        public RatingBar ratingBar;
        public ImageView img_isonline;


    }


}



