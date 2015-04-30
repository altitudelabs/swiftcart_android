package com.altitudelabs.swiftcart.view;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

public class TextViewSansRegular extends TextView {
	public TextViewSansRegular(Context context) {
		super(context);
		setFont();
	}
	public TextViewSansRegular(Context context, AttributeSet attrs) {
		super(context, attrs);
		setFont();
	}
	public TextViewSansRegular(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		setFont();
	}

	private void setFont() {
		Typeface font = Typeface.createFromAsset(getContext().getAssets(), "fonts/SourceSansPro-Regular.otf");
		setTypeface(font, Typeface.NORMAL);
	}
}