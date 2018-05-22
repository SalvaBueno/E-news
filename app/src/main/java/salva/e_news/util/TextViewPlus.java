package salva.e_news.util;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;

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
    }


}
