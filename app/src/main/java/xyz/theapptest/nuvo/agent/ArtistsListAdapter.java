package xyz.theapptest.nuvo.agent;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.ArrayList;

import xyz.theapptest.nuvo.R;
import xyz.theapptest.nuvo.pojo.AgentArtistListItemPojo;
import xyz.theapptest.nuvo.utils.Fonts;

/**
 * Created by torinit01 on 9/8/16.
 */
public class ArtistsListAdapter extends BaseAdapter {


    private final LayoutInflater vi;
    public Fonts fonts;
    ArrayList<AgentArtistListItemPojo> agentArtistList;
    Context mContext;

    public ArtistsListAdapter(Context context, ArrayList<AgentArtistListItemPojo> agentArtistList) {
        vi = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        fonts = new Fonts(context);
        this.agentArtistList = agentArtistList;
        this.mContext = context;
    }

    @Override
    public int getCount() {
        if (agentArtistList == null) {
            return 0;
        }
        return agentArtistList.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {

        ArtistViewHolder holder = null;

        if (view == null) {
            view = vi.inflate(R.layout.list_row_saved_artists, null);
            holder = new ArtistViewHolder();
            holder.firstName = (TextView) view.findViewById(R.id.rsa_txt_firstname);
            holder.firstName.setTypeface(fonts.openSansSemiBold);
            holder.lastName = (TextView) view.findViewById(R.id.rsa_txt_lastname);
            holder.lastName.setTypeface(fonts.openSansSemiBold);
            holder.ratings = (RatingBar) view.findViewById(R.id.rsa_ratingBar);
            holder.statusImg = (ImageView) view.findViewById(R.id.rsa_imggreen);
            holder.charLL = (LinearLayout) view.findViewById(R.id.list_row_saved_artist_char_ll_id);

            AgentArtistListItemPojo agentArtistListItemPojo = agentArtistList.get(position);
            String firstName = agentArtistListItemPojo.getFirst_name();
            String lastName = agentArtistListItemPojo.getLast_name();
            String rating = agentArtistListItemPojo.getRating();
            String staus = agentArtistListItemPojo.getOnline();
            String[] charArray = agentArtistListItemPojo.getCharacteristics();
            for (int i2 = 0; i2 < charArray.length; i2++) {
                //int id = Integer.parseInt(characterlist.g);
                String name = getCharName(charArray[i2]);

                // String value = characterlist.get(i2).get("characteristics_value");
                //if (strArr.equalsIgnoreCase(value)) {
                //Log.e("nameforbutton", name);
                TextView btn = new TextView(mContext);
                btn.setBackgroundResource(R.drawable.button_selected);
                btn.setTextSize(12);
                Typeface facetxtsigintextbox = Typeface.createFromAsset(mContext.getAssets(),
                        "font/OpenSans-Regular.ttf");
                btn.setTypeface(facetxtsigintextbox);
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                        ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                //params.setMargins(5, 10, 15, 2);
                btn.setPadding(20, 0, 20, 0);
                btn.setLayoutParams(params);
                //  btn.setLayoutParams(new LinearLayout.LayoutParams(70, 30));
                btn.setTransformationMethod(null);
                btn.setTextColor(mContext.getResources().getColor(R.color.white));

                btn.setText(name);

                // holder.charLL.removeAllViews();
                holder.charLL.addView(btn, params);


                //  }
            }
            if (staus == null) {
                holder.statusImg.setImageDrawable(mContext.getResources().getDrawable(R.drawable.red_dot));
            } else {
                if (staus.equals("7")) {

                    holder.statusImg.setImageDrawable(mContext.getResources().getDrawable(R.drawable.green_circle));
                } else {

                    holder.statusImg.setImageDrawable(mContext.getResources().getDrawable(R.drawable.red_dot));

                }

            }
            String[] characteristicsArray = agentArtistListItemPojo.getCharacteristics();



            double ratingDouble = 0;
            int ratingValue = 0;
            if (rating == null) {

            } else {
                if (rating.equals("")) {

                } else {
                    ratingDouble = Double.parseDouble(rating);
                    //ratingValue = (Integer)ratingDouble;
                }
            }

            holder.firstName.setText(firstName);
            holder.lastName.setText(lastName);
            holder.ratings.setRating(Float.parseFloat(agentArtistListItemPojo.getRating()));
        }

        return view;
    }

    private String getCharName(String characteristics) {
        switch (characteristics) {
            case "1":
                characteristics = "Attitude";
                break;
            case "2":
                characteristics = "Dad";
                break;
            case "4":
                characteristics = "Authoritative";
                break;
            case "8":
                characteristics = "Gravitas";
                break;
            case "16":
                characteristics = "Announcer";
                break;
            case "32":
                characteristics = "Wise";
                break;
            case "64":
                characteristics = "Professional";
                break;
            default:
                characteristics = "Professional";

        }
        return characteristics;
    }

    public class ArtistViewHolder {
        public TextView firstName, lastName;
        public RatingBar ratings;
        public ImageView statusImg;
        LinearLayout charLL;

    }
}