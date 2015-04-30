package com.altitudelabs.swiftcart.config;

import android.content.Context;
import android.graphics.Color;

import com.altitudelabs.swiftcart.R;
import com.altitudelabs.swiftcart.view.PagerSlidingTabStrip;

public class Config {

	public static void customizePagerSlidingTabStrip(PagerSlidingTabStrip tabStrip, Context context) {
		tabStrip.setIndicatorColor(context.getResources().getColor(R.color.pager_tab_strip_indicator_color));
		tabStrip.setDividerColor(Color.TRANSPARENT);
		tabStrip.setUnderlineColor(Color.TRANSPARENT);
		tabStrip.setUnderlineHeight(0);
		tabStrip.setIndicatorHeight(context.getResources().getDimensionPixelSize(R.dimen.pager_tab_strip_slider_height));
	}
	
}
