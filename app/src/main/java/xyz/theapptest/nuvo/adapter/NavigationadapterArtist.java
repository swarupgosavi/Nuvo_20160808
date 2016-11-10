package xyz.theapptest.nuvo.adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import xyz.theapptest.nuvo.R;
import xyz.theapptest.nuvo.ui.ArtistHomescreen;
import xyz.theapptest.nuvo.ui.ProducerHomeScreen;

/**
 * Created by trtcpu007 on 1/8/16.
 */

public class NavigationadapterArtist extends BaseAdapter {
    String[] result;
    Context context;
    int[] imageId;
    private static LayoutInflater inflater = null;
    NavigationDraweritemselection navitemselection;
    public NavigationadapterArtist(ArtistHomescreen mainActivity, String[] prgmNameList, int[] prgmImages, NavigationDraweritemselection navitemselection) {
        // TODO Auto-generated constructor stub
        result = prgmNameList;
        context = mainActivity;
        imageId = prgmImages;
        this.navitemselection = navitemselection;
        inflater = (LayoutInflater) context.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return result.length;
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    public class Holder {
        TextView tv;
        ImageView img;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        Holder holder = new Holder();
        View rowView;
        rowView = inflater.inflate(R.layout.drawerlist_item, null);
        holder.tv = (TextView) rowView.findViewById(R.id.textView1);
        Typeface facetxtchooseregular = Typeface.createFromAsset(context.getAssets(),
                "font/OpenSans-Regular.ttf");
        holder.tv.setTypeface(facetxtchooseregular);

        holder.img = (ImageView) rowView.findViewById(R.id.leftnav_icon);
        holder.tv.setText(result[position]);
        holder.img.setImageResource(imageId[position]);
        rowView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                navitemselection.onArtistSelected(v,position);
            }
        });
        return rowView;
    }
}


