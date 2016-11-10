package xyz.theapptest.nuvo.adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import xyz.theapptest.nuvo.R;
import xyz.theapptest.nuvo.pojo.AuditionsPojo;
import xyz.theapptest.nuvo.pojo.NewAuditionsPojo;


/**
 * Created by ADMIN on 8/1/2016.
 */
public class AuditionsAdapter extends RecyclerView.Adapter<AuditionsAdapter.ViewHolder> {

    Context mContext;
    NewAuditionsPojo auditionsPojo;
    List<NewAuditionsPojo> auditionsPojoList;

    public AuditionsAdapter(Context ctx, List<NewAuditionsPojo> auditionsPojoList) {
        this.mContext = ctx;
        this.auditionsPojo = auditionsPojo;
        this.auditionsPojoList = auditionsPojoList;
    }

    @Override
    public int getItemCount() {
        if (auditionsPojoList == null)
            return 0;
        return auditionsPojoList.size();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.auditions_row_layout, parent, false);
        // set the view's size, margins, paddings and layout parameters
        ViewHolder vh = new ViewHolder(v);

        return vh;

    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        NewAuditionsPojo auditionsPojo = auditionsPojoList.get(position);
        String title = auditionsPojo.getJob_title();
        String description = auditionsPojo.getDescription();
        String expiryDate = auditionsPojo.getCreated_on();
        String companyName = auditionsPojo.getCompany_name();
        String firstName = auditionsPojo.getCompany_name();
        String lastName = auditionsPojo.getLast_name();
        String userName = firstName + " " + lastName;

        String[] dateArray = expiryDate.split(" ");
        expiryDate = dateArray[0];

        holder.titleTv.setText(title);
        holder.dateTv.setText(expiryDate);
        holder.cmpNameTv.setText(companyName);
        holder.userNameTv.setText(userName);

        Typeface type = Typeface.createFromAsset(mContext.getAssets(), "font/OpenSans-Bold.ttf");
        holder.titleTv.setTypeface(type);


        Typeface descriptionType = Typeface.createFromAsset(mContext.getAssets(), "font/OpenSans-Regular.ttf");
      //  holder.cmpNameTv.setTypeface(descriptionType);
      //  holder.userNameTv.setTypeface(descriptionType);

        Typeface dateType = Typeface.createFromAsset(mContext.getAssets(), "font/OpenSans-Light.ttf");
        holder.dateTv.setTypeface(dateType);
        holder.cmpNameTv.setTypeface(dateType);
        holder.userNameTv.setTypeface(dateType);

    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        TextView titleTv, dateTv, cmpNameTv, userNameTv;


        public ViewHolder(View v) {
            super(v);
            titleTv = (TextView) v.findViewById(R.id.auditions_row_title_tv_id);
            dateTv = (TextView) v.findViewById(R.id.auditions_row_date_tv_id);
            cmpNameTv = (TextView) v.findViewById(R.id.auditions_row_cmp_name_tv_id);
            userNameTv = (TextView) v.findViewById(R.id.auditions_row_user_name_tv_id);

        }
    }

}