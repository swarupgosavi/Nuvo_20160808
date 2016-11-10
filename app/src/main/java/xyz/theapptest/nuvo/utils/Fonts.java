package xyz.theapptest.nuvo.utils;

import android.content.Context;
import android.graphics.Typeface;

/**
 * Created by torinit01 on 10/8/16.
 */
public class Fonts {

    public Typeface robotoRegular;
    public Typeface voniqueSixtyFourBoldItalic, voniqueSixtyFourItalic, voniqueSixtyFourBold;
    public Typeface voniqueSixtyFour, avenirMedium, avenirRoman, halveticaNeue;
    public Typeface openSansLight;
    public Typeface openSansBold;
    public Typeface openSansSemiBold, openSansRegular;
    public Context mContext;


    public Fonts(Context mContext) {
        this.mContext = mContext;
        openSansRegular  = Typeface.createFromAsset(mContext.getAssets(),
                "font/OpenSans-Regular.ttf");
        openSansSemiBold= Typeface.createFromAsset(mContext.getAssets(),
                "font/OpenSans-Semibold.ttf");

         voniqueSixtyFour = Typeface.createFromAsset(mContext.getAssets(),
                "font/Vonique 64.ttf");
         openSansLight = Typeface.createFromAsset(mContext.getAssets(),
                "font/OpenSans-Light.ttf");
         openSansBold = Typeface.createFromAsset(mContext.getAssets(),
                "font/OpenSans-Bold.ttf");


        voniqueSixtyFourBoldItalic = Typeface.createFromAsset(mContext.getAssets(),
                "font/Vonique 64 Bold Italic.ttf");
        voniqueSixtyFourBold = Typeface.createFromAsset(mContext.getAssets(),
                "font/Vonique 64 Bold.ttf");
        voniqueSixtyFourItalic = Typeface.createFromAsset(mContext.getAssets(),
                "font/Vonique 64 Italic.ttf");

        avenirMedium = Typeface.createFromAsset(mContext.getAssets(),
                "font/Avenir Medium.otf");
        avenirRoman = Typeface.createFromAsset(mContext.getAssets(),
                "font/Avenir_Roman.otf");
        halveticaNeue = Typeface.createFromAsset(mContext.getAssets(),
                "font/HelveticaNeue.ttf");
        robotoRegular = Typeface.createFromAsset(mContext.getAssets(),
                "font/Roboto-Regular.ttf");

    }
}
