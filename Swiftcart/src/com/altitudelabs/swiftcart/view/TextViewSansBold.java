package com.altitudelabs.swiftcart.view;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

public class TextViewSansBold extends TextView {
	public TextViewSansBold(Context context) {
		super(context);
		setFont();
	}
	public TextViewSansBold(Context context, AttributeSet attrs) {
		super(context, attrs);
		setFont();
	}
	public TextViewSansBold(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		setFont();
	}

	private void setFont() {
		Typeface font = Typeface.createFromAsset(getContext().getAssets(), "fonts/SourceSansPro-Bold.otf");
		setTypeface(font, Typeface.NORMAL);
	}
}