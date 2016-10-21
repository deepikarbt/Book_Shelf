package com.example.deepika.rbtlib;

/**
 * Created by deepika on 19-Oct-16.
 */

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.EditText;
import android.widget.TextView;


import com.example.deepika.rbtlib.Typefaces;
import com.example.deepika.rbtlib.Typefaces;



public class FontEdittext extends EditText {
    public FontEdittext(Context context) {
        super(context);
        initFont();

    }

    public FontEdittext(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initFont();
    }

    public FontEdittext(Context context, AttributeSet attrs) {
        super(context, attrs);
        initFont();
    }

    private void initFont() {
        Typeface openSansFace = Typefaces.get(getContext(), "fonts/Lato-Regular.ttf");
        setTypeface(openSansFace);
    }
}


