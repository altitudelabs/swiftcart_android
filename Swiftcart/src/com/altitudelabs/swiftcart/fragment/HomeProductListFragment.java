package com.altitudelabs.swiftcart.fragment;

import java.util.Timer;
import java.util.TimerTask;

import android.content.Context;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;

import com.altitudelabs.swiftcart.R;
import com.altitudelabs.swiftcart.activity.MainActivity;
import com.altitudelabs.swiftcart.adapter.ProductListViewAdapter;
import com.altitudelabs.swiftcart.model.ProductData;
import com.altitudelabs.swiftcart.model.ProductItem;

public class HomeProductListFragment extends Fragment {

	public static final String ARG_PRODUCT_DATA = "arg product data";
	private final int BANNER_SWAP_TIME = 4000;

	private ViewPager bannerPager;
	private ListView productListView;
	private Timer bannerTimer;

	// Dummy data
	private static int[] bannerImages = {
		R.drawable.butcher,  
		R.drawable.icecream, 
		R.drawable.truck,
	};

	public static HomeProductListFragment newInstance(Parcelable[] productData) {
		HomeProductListFragment f = new HomeProductListFragment();
		Bundle args = new Bundle();
		args.putParcelableArray(ARG_PRODUCT_DATA, productData);
		f.setArguments(args);
		return f;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.home_product_list_fragment, container, false);
		return v;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		bannerPager = (ViewPager) getActivity().findViewById(R.id.pager);
		ImageAdapter bannerPagerAdapter = new ImageAdapter(getActivity());
		bannerPager.setAdapter(bannerPagerAdapter);
		
		// Create product items
		ProductData productData = ProductData.getInstance();
		int productTypeId1 = productData.getProductTypeIds()[0];
		int productTypeId2 = productData.getProductTypeIds()[1];
		
		int[] productIdVegetable = productData.getProductId(productTypeId1);
		int[] productIdFruit = productData.getProductId(productTypeId2);
		
		ProductItem[] productItems = new ProductItem[]{
				productData.getProductItem(productIdVegetable[0]), 
				productData.getProductItem(productIdVegetable[1]), 
				productData.getProductItem(productIdVegetable[2]),
				productData.getProductItem(productIdFruit[0]), 
				productData.getProductItem(productIdFruit[1]), 
				productData.getProductItem(productIdFruit[2])};
		
		productListView = (ListView)getActivity().findViewById(R.id.product_list);
		productListView.setAdapter(new ProductListViewAdapter(getActivity(), (MainActivity)getActivity(), true, productItems));
	}
	
	@Override
	public void onResume() {
		super.onResume();
		MainActivity mainActivity = (MainActivity)getActivity();
		mainActivity.showActionBarTitle(true);
		mainActivity.showActionBarMenuButton();
		mainActivity.showActionBarCheckoutButton(true);
		mainActivity.showActionBarSearchButton(true);
		mainActivity.showActionBarFilterButton(false);
		
		// Banner anim
		
		TimerTask tt = new TimerTask() {
			@Override
			public void run() {
				getActivity().runOnUiThread(new Runnable() {
					@Override
					public void run() {
						int nextPage = bannerPager.getCurrentItem() + 1;
						nextPage = nextPage % bannerImages.length;
						bannerPager.setCurrentItem(nextPage);
					}
				});
			}
		};
		bannerTimer = new Timer();
		bannerTimer.schedule(tt, BANNER_SWAP_TIME, BANNER_SWAP_TIME);
	}
	
	@Override
	public void onPause() {
		super.onPause();
		bannerTimer.cancel();
	}

	public class ImageAdapter extends PagerAdapter {
		Context context;
		ImageAdapter(Context context){
			this.context=context;
		}
		@Override
		public int getCount() {
			return bannerImages.length;
		}

		@Override
		public boolean isViewFromObject(View view, Object object) {
			return view == ((ImageView) object);
		}

		@Override
		public Object instantiateItem(ViewGroup container, int position) {
			ImageView imageView = new ImageView(context);
			imageView.setLayoutParams(new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.WRAP_CONTENT));
			imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
			imageView.setImageResource(bannerImages[position]);
			((ViewPager) container).addView(imageView, 0);
			return imageView;
		}

		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			((ViewPager) container).removeView((ImageView) object);
		}
	}
}
