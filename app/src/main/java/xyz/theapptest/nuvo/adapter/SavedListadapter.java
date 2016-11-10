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

import java.util.ArrayList;
import java.util.HashMap;

import xyz.theapptest.nuvo.R;
import xyz.theapptest.nuvo.utils.ConstantData;
import xyz.theapptest.nuvo.utils.FindJobs;
import xyz.theapptest.nuvo.utils.ListsongplayedInterface;
import xyz.theapptest.nuvo.utils.SavedJobs;

/**
 * Created by trtcpu007 on 10/8/16.
 */

public class SavedListadapter extends ArrayAdapter<SavedJobs> {
    ArrayList<SavedJobs> artistList;
    LayoutInflater vi;
    int Resource;
    SavedListadapter.ViewHolder holder;
    String url;
    ListsongplayedInterface onArtistSelected;
    MediaPlayer mMediapler;
    SavedJobClickEvent QuantityClickEvent;
    ConstantData constData;
    ArrayList<HashMap<String, String>> characterlist;
    Context mContext;


    public SavedListadapter(Context context, int resource, ArrayList<SavedJobs> objects, SavedJobClickEvent pQuantityClickEvent) {
        super(context, resource, objects);
        vi = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        Resource = resource;
        artistList = objects;
        this.QuantityClickEvent = pQuantityClickEvent;
        this.mContext = context;

    }


    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // convert view = design

        SavedListadapter.ViewHolder holder = null;

        if (convertView == null) {
            convertView = vi.inflate(Resource, null);
            holder = new SavedListadapter.ViewHolder();
            holder.imageviewprogress = (ImageView) convertView.findViewById(R.id.img_playicon);
            holder.imageviewselected = (ImageView) convertView.findViewById(R.id.imgselected);
            holder.isonline = (ImageView) convertView.findViewById(R.id.imggreen);
            holder.imgselected = (ImageView) convertView.findViewById(R.id.imgselected);
            holder.tvfirstname = (TextView) convertView.findViewById(R.id.txt_firstname);
            holder.tvurl = (TextView) convertView.findViewById(R.id.url);
            holder.tvlastname = (TextView) convertView.findViewById(R.id.txt_lastname);
            holder.percent = (TextView) convertView.findViewById(R.id.txtpercent);
            holder.layout1 = (LinearLayout) convertView.findViewById(R.id.laybtn);

            convertView.setTag(holder);
        } else {
            holder = (SavedListadapter.ViewHolder) convertView.getTag();
        }



        String userStatus = artistList.get(position).getOnline();
        if (userStatus.equals("7")) {
            holder.isonline.setImageDrawable(mContext.getResources().getDrawable(R.drawable.green_circle));
        } else {
            holder.isonline.setImageDrawable(mContext.getResources().getDrawable(R.drawable.red_dot));
        }
        holder.tvfirstname.setText(artistList.get(position).getFirstname());
        holder.tvlastname.setText(artistList.get(position).getLastname());
        holder.tvurl.setText(artistList.get(position).getUrl());
        holder.percent.setText(artistList.get(position).getPercent() + "%");

        url = artistList.get(position).getUrl();
        Log.e("url", holder.tvurl.getText().toString());


        try {
            constData = ConstantData.getInstance();
            characterlist = constData.getCharacteristics();

            JSONArray jsonArray = new JSONArray(artistList.get(position).getDisplayCharactersistics());
            //String[] strArr = new String[jsonArray.length()];
            String strArr = "";

            for (int i = 0; i < jsonArray.length(); i++) {
                strArr = jsonArray.getString(i);
                Log.e("Values: ", strArr);
                for (int i2 = 0; i2 < characterlist.size(); i2++) {
                    int id = Integer.parseInt(characterlist.get(i2).get("id"));
                    String name = characterlist.get(i2).get("characteristic_name");
                    String value = characterlist.get(i2).get("characteristics_value");
                    if (strArr.equalsIgnoreCase(value)) {
                        //Log.e("nameforbutton", name);
                        TextView btn = new TextView(getContext());
                        btn.setBackgroundResource(R.drawable.button_selected);
                        btn.setTextSize(12);
                        Typeface facetxtsigintextbox = Typeface.createFromAsset(getContext().getAssets(),
                                "font/OpenSans-Regular.ttf");
                        btn.setTypeface(facetxtsigintextbox);
                        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                        //params.setMargins(5, 10, 15, 2);
                        btn.setPadding(20, 0, 20, 0);
                        btn.setLayoutParams(params);
                        //  btn.setLayoutParams(new LinearLayout.LayoutParams(70, 30));
                        btn.setTransformationMethod(null);
                        btn.setTextColor(getContext().getResources().getColor(R.color.white));

                        btn.setText(name);

                        holder.layout1.removeAllViews();
                        holder.layout1.addView(btn, params);


                    }
                }
            }
            //Log.e("array", Arrays.toString(strArr));
            //System.out.println(Arrays.toString(strArr));
        } catch (Exception e) {
            e.printStackTrace();
        }




        if (artistList.get(position).getSelectedaudio() == 0)
            holder.imageviewprogress.setBackgroundResource(R.drawable.audio_play);
        else
            holder.imageviewprogress.setBackgroundResource(R.drawable.pause_button);


        holder.imageviewprogress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // onArtistSelected.artistRecordItem(v);
                Log.e("url-click", url);
                QuantityClickEvent.onPlayArtistRecordSaved(v, position, url);

            }
        });


        return convertView;

    }

    static class ViewHolder {
        public ImageView imageviewprogress;
        public ImageView imageviewselected;
        public ImageView isonline;
        public ImageView imgselected;
        public TextView tvfirstname;
        public TextView tvlastname;
        public TextView tvurl;
        public TextView percent;
        public LinearLayout layout1;


    }


}

