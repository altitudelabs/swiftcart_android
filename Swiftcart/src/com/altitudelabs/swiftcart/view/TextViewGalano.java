package com.altitudelabs.swiftcart.view;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

public class TextViewGalano extends TextView {
	public TextViewGalano(Context context) {
		super(context);
		setFont();
	}
	public TextViewGalano(Context context, AttributeSet attrs) {
		super(context, attrs);
		setFont();
	}
	public TextViewGalano(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		setFont();
	}

	private void setFont() {
		Typeface font = Typeface.createFromAsset(getContext().getAssets(), "fonts/GalanoGrotesque-Bold.otf");
		setTypeface(font, Typeface.NORMAL);
	}
}