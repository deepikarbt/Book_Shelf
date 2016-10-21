package com.example.deepika.rbtlib;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

import com.example.deepika.rbtlib.Typefaces;
import com.example.deepika.rbtlib.Typefaces;



public class FontLatoRegular extends TextView {
    public FontLatoRegular(Context context) {
        super(context);
        initFont();

    }

    public FontLatoRegular(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initFont();
    }

    public FontLatoRegular(Context context, AttributeSet attrs) {
        super(context, attrs);
        initFont();
    }

    private void initFont() {
        Typeface openSansFace = Typefaces.get(getContext(), "fonts/Lato-Regular.ttf");
        setTypeface(openSansFace);
    }
}

