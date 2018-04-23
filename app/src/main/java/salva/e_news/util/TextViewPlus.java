package salva.e_news.util;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;

/**
 * Created by dreams on 28/12/16.
 */

//public class TextViewPlus extends TextView{
public class TextViewPlus extends android.support.v7.widget.AppCompatTextView {

    public TextViewPlus(Context context) {
        super(context);
        setCustomFont(context);
    }

    public TextViewPlus(Context context, AttributeSet attrs) {
        super(context, attrs);
        setCustomFont(context);
    }

    public TextViewPlus(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        setCustomFont(context);
    }

    private void setCustomFont(Context ctx) {
        String ruta = "fonts/Jonahfonts - Micron.ttf";

        Typeface font = Typeface.createFromAsset(ctx.getAssets(), ruta);

        setTypeface(font);


//        TypedArray a = ctx.obtainStyledAttributes(attrs, R.styleable.TextViewPlus);
//        String customFont = a.getString(R.styleable.TextViewPlus_customFont);


//        setCustomFont(ctx, );
//        a.recycle();
    }

//    public boolean setCustomFont(Context ctx, String asset) {
//        Typeface tf = null;
//        try {
//            tf = Typeface.createFromAsset(ctx.getAssets(), asset);
//        } catch (Exception e) {
//            Log.e("FONT","Could not get typeface: "+e.getMessage());
//            return false;
//        }
//
//        setTypeface(tf);
//        return true;
//    }

}
