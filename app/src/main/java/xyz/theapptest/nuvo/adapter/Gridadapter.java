package xyz.theapptest.nuvo.adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import xyz.theapptest.nuvo.R;


/**
 * Created by trtcpu007 on 15/7/16.
 */

public class Gridadapter extends BaseAdapter {

    String [] result;
    Context contexts;
    int [] imageId;
    private static LayoutInflater inflater=null;
    public Gridadapter(Context context, String[] prgmNameList, int[] prgmImages) {
        // TODO Auto-generated constructor stub
        result=prgmNameList;
        contexts=context;
        imageId=prgmImages;
        inflater = ( LayoutInflater )context.
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

    public class Holder
    {
        TextView tv,tv1;
        ImageView img;
        ImageView imgs;
    }
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        Holder holder=new Holder();


        if (result[position].equals(""))
        {
            View rowView;
            rowView = inflater.inflate(R.layout.grid_itemlast, null);
            holder.tv=(TextView) rowView.findViewById(R.id.textView11);
            holder.tv1=(TextView) rowView.findViewById(R.id.textView12);

            Typeface facetxtchooseregular = Typeface.createFromAsset(contexts.getAssets(),
                    "font/OpenSans-Regular.ttf");

            holder.tv.setTypeface(facetxtchooseregular);
            holder.tv1.setTypeface(facetxtchooseregular);

            holder.tv.setText("Find The");
            holder.tv1.setText("Artist");


            holder.tv.setTextSize(17);
            holder.tv.setGravity(Gravity.CENTER);
            holder.tv1.setTextSize(17);
            holder.tv1.setGravity(Gravity.CENTER);

            return rowView;
        }
        else
        {
            View rowView1;
            rowView1 = inflater.inflate(R.layout.grid_items, null);
            holder.tv=(TextView) rowView1.findViewById(R.id.textView11);
            Typeface facetxtchooseregular = Typeface.createFromAsset(contexts.getAssets(),
                    "font/OpenSans-Regular.ttf");
            holder.tv.setTypeface(facetxtchooseregular);
            holder.img=(ImageView) rowView1.findViewById(R.id.imageView4);
            holder.imgs=(ImageView) rowView1.findViewById(R.id.imageView5);
            holder.tv.setText(result[position]);
            holder.img.setImageResource(imageId[position]);
            return rowView1;
        }



    }

}
