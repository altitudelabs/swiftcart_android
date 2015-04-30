package com.altitudelabs.swiftcart.fragment;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.altitudelabs.swiftcart.R;
import com.altitudelabs.swiftcart.activity.MainActivity;
import com.altitudelabs.swiftcart.adapter.ProductListViewAdapter;
import com.altitudelabs.swiftcart.config.Config;
import com.altitudelabs.swiftcart.model.ProductData;
import com.altitudelabs.swiftcart.model.ProductItem;
import com.altitudelabs.swiftcart.model.ProductItemDetail;
import com.altitudelabs.swiftcart.model.ShoppingCart;
import com.altitudelabs.swiftcart.view.PagerSlidingTabStrip;

public class ProductDetailFragment extends Fragment implements View.OnClickListener {

	public static final int EMPTY_PRODUCT = 0;
	public static final String ARG_PRODUCT_ID = "arg product id";
	public static final String[] INFO_TAB_TITLES = {"DETAILS", "NUTRITION", "RELATED"};

	//	private ViewPager mPagerImage;
	private ImageView mFoodImage;
	private PagerSlidingTabStrip mPagerTabStrip;
	private ViewPager mPagerInfo;
	private ImageView mButtonAdd;
	private ImageView mButtonMinus;
	private TextView mQuantityTextView;
	private Button mButtonAddToCart;

	private int mProductId;

	public static ProductDetailFragment newInstance(int productId) {
		ProductDetailFragment f = new ProductDetailFragment();
		Bundle args = new Bundle();
		args.putInt(ARG_PRODUCT_ID, productId);
		f.setArguments(args);
		return f;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.activity_product_detail, container, false);
		return v;
	}

	@Override
	public void onViewCreated(View v, Bundle savedInstanceState) {
		super.onViewCreated(v, savedInstanceState);

		//		mPagerImage = (ViewPager) v.findViewById(R.id.pagerImage);
		mFoodImage = (ImageView) v.findViewById(R.id.food_image);
		mPagerTabStrip = (PagerSlidingTabStrip) v.findViewById(R.id.pager_tabs);
		mPagerInfo = (ViewPager) v.findViewById(R.id.pagerInfo);
		mButtonAdd = (ImageView) v.findViewById(R.id.buttonAddQuantity);
		mButtonMinus = (ImageView) v.findViewById(R.id.buttonMinusQuantity);
		mQuantityTextView = (TextView) v.findViewById(R.id.textFieldQuantity);
		mButtonAddToCart = (Button) v.findViewById(R.id.buttonAddToCart);

		mButtonAdd.setOnClickListener(this);
		mButtonMinus.setOnClickListener(this);
		mButtonAddToCart.setOnClickListener(this);

		//		ImageAdapter pagerImageAdapter = new ImageAdapter(getActivity());
		//		mPagerImage.setAdapter(pagerImageAdapter);

		mProductId = getArguments().getInt(ARG_PRODUCT_ID, EMPTY_PRODUCT);
		ProductItemDetail item = ProductData.getInstance().getProductItemDetail(mProductId);

		ViewPagerAdapter pagerInfoAdapter = new ViewPagerAdapter(getChildFragmentManager(), item, mProductId);
		mPagerInfo.setAdapter(pagerInfoAdapter);

		mPagerTabStrip.setViewPager(mPagerInfo);
		Config.customizePagerSlidingTabStrip(mPagerTabStrip, getActivity());

		mFoodImage.setImageResource(item.image);
	}

	
	
	//	public class ImageAdapter extends PagerAdapter {
	//		Context context;
	//		ImageAdapter(Context context){
	//			this.context = context;
	//		}
	//		@Override
	//		public int getCount() {
	//			return bigImageResIds.length;
	//		}
	//
	//		@Override
	//		public boolean isViewFromObject(View view, Object object) {
	//			return view == ((ImageView) object);
	//		}
	//
	//		@Override
	//		public Object instantiateItem(ViewGroup container, int position) {
	//			ImageView imageView = new ImageView(context);
	//			imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
	//			imageView.setImageResource(bigImageResIds[position]);
	//			((ViewPager) container).addView(imageView, 0);
	//			return imageView;
	//		}
	//
	//		@Override
	//		public void destroyItem(ViewGroup container, int position, Object object) {
	//			((ViewPager) container).removeView((ImageView) object);
	//		}
	//	}

	@Override
	public void onResume() {
		super.onResume();
		
		int productId = getArguments().getInt(ARG_PRODUCT_ID);
		ProductData productData = ProductData.getInstance();
		ProductItem item = productData.getProductItem(productId);
		
		MainActivity mainActivity = (MainActivity)getActivity();
		mainActivity.showActionBarBackViews("");
		mainActivity.showActionBarTitleWithOtherName(item.name);
		mainActivity.showActionBarCheckoutButton(true);
		mainActivity.showActionBarSearchButton(false);
		mainActivity.showActionBarFilterButton(false);
	}

	public static class ViewPagerAdapter extends FragmentStatePagerAdapter {

		public static ProductItemDetail mProductItemDetail;
		public static int mProductId;

		public ViewPagerAdapter(FragmentManager fm, ProductItemDetail productItemDetail, int productId) {
			super(fm);
			mProductItemDetail = productItemDetail;
			mProductId = productId;
		}

		@Override
		public int getCount() {
			return INFO_TAB_TITLES.length;
		}

		@Override
		public Fragment getItem(int position) {
			switch (position) {

			case 0:{
				InfoDetailFragment fragmenttab1 = new InfoDetailFragment();
				return fragmenttab1;
			}

			case 1:
				InfoNutritionFragment fragmenttab2 = new InfoNutritionFragment();
				return fragmenttab2;

			case 2:
				InfoRelatedFragment fragmenttab3 = InfoRelatedFragment.newInstance(mProductId);
				return fragmenttab3;
			}
			return null;
		}

		@Override
		public CharSequence getPageTitle(int position) {
			return INFO_TAB_TITLES[position];
		}

		public static class InfoDetailFragment extends Fragment {

			@Override
			public View onCreateView(LayoutInflater inflater, ViewGroup container,
					Bundle savedInstanceState) {
				View v = inflater.inflate(R.layout.product_detail_info_detail_fragment, container, false);
				return v;
			}

			@Override
			public void onViewCreated(View v, Bundle savedInstanceState) {
				super.onViewCreated(v, savedInstanceState);
				TextView title = (TextView) v.findViewById(R.id.detail_title);
				TextView description = (TextView) v.findViewById(R.id.detail_content);
				title.setText(mProductItemDetail.name);
				description.setText(mProductItemDetail.description);
			}
		}

		public static class InfoNutritionFragment extends Fragment {

			private ListView mListView;

			private String[] dummyKeys = {"Amount Per Serving", 
					"Calories", 
					"Total Fat", 
					"      - Saturated Fat", 
					"      - Polyunsaturated Fat",
					"      - Monounsaturated Fat", 
					"Cholesterol", 
					"Sodium", 
					"Potassium", 
					"Total Carbohydrate", 
			"    - Dietary fiber"};

			private String[] dummyValues; //{"100g", "16", "0.2g", "16g", "16g", "16g", "16g", "16g", "16g", "16g", "16g"};

			public InfoNutritionFragment() {
				super();
				dummyValues = new String[dummyKeys.length];
				dummyValues[0] = "" + mProductItemDetail.amtPerServing + "g";
				dummyValues[1] = "" + mProductItemDetail.calories;
				dummyValues[2] = "" + mProductItemDetail.totalFat + "g";
				dummyValues[3] = "" + mProductItemDetail.fat1 + "g";
				dummyValues[4] = "" + mProductItemDetail.fat2 + "g";
				dummyValues[5] = "" + mProductItemDetail.fat3 + "g";
				dummyValues[6] = "" + mProductItemDetail.cholesterol + "g";
				dummyValues[7] = "" + mProductItemDetail.sodium + "g";
				dummyValues[8] = "" + mProductItemDetail.potassium + "g";
				dummyValues[9] = "" + mProductItemDetail.totalCarb + "g";
				dummyValues[10] = "" + mProductItemDetail.fiber + "g";
			}

			@Override
			public View onCreateView(LayoutInflater inflater, ViewGroup container,
					Bundle savedInstanceState) {
				View v = inflater.inflate(R.layout.product_detail_info_nutrition_fragment, container, false);
				mListView = (ListView) v.findViewById(R.id.listView);
				return v;
			}

			@Override
			public void onViewCreated(View view, Bundle savedInstanceState) {
				super.onViewCreated(view, savedInstanceState);
				mListView.setAdapter(new ListViewAdapter(getActivity(), dummyKeys, dummyValues));
			}

			class ListViewAdapter extends BaseAdapter {

				class ViewHolder {
					public TextView title;
					public TextView value;
				}

				Context mContext;
				String[] mKeys;
				String[] mValues;

				public ListViewAdapter(Context context, String[] keys, String[] values) {
					mContext = context;
					mKeys = keys;
					mValues = values;
				}

				@Override
				public int getCount() {
					return mKeys.length;
				}

				@Override
				public Object getItem(int position) {
					return null;
				}

				@Override
				public long getItemId(int position) {
					return 0;
				}

				@Override
				public View getView(int position, View convertView, ViewGroup parent) {
					ViewHolder viewHolder;
					if (convertView == null) {
						convertView = LayoutInflater.from(mContext).inflate(R.layout.product_detail_fragment_nutrition_list_item, parent, false);
						viewHolder = new ViewHolder();
						viewHolder.title = (TextView) convertView.findViewById(R.id.nutrition_attribute);
						viewHolder.value = (TextView) convertView.findViewById(R.id.nutrition_value);
						convertView.setTag(viewHolder);
					} else {
						viewHolder = (ViewHolder) convertView.getTag();
					}
					
					viewHolder.title.setText(mKeys[position]);
					viewHolder.value.setText(mValues[position]);
					
					if (position < 2) {
						Typeface type = Typeface.createFromAsset(mContext.getAssets(),"fonts/SourceSansPro-Bold.otf"); 
						viewHolder.title.setTypeface(type);
					} else {
						Typeface type = Typeface.createFromAsset(mContext.getAssets(),"fonts/SourceSansPro-Regular.otf"); 
						viewHolder.title.setTypeface(type);
					}

					return convertView;
				}
			}

		}

		public static class InfoRelatedFragment extends Fragment implements View.OnClickListener {

			public static String ARG_PRODUCT_ID = "product id";

			protected InfoRelatedFragment() {
				
			}
			
			public static InfoRelatedFragment newInstance(int productId) {
				InfoRelatedFragment frag = new InfoRelatedFragment();
				Bundle args = new Bundle();
				args.putInt(ARG_PRODUCT_ID, productId);
				frag.setArguments(args);
				return frag;
			}

			@Override
			public View onCreateView(LayoutInflater inflater, ViewGroup container,
					Bundle savedInstanceState) {
				//				View v = inflater.inflate(R.layout.product_detail_info_related_fragment, container, false);
				View v = inflater.inflate(R.layout.category_product_list_fragment_list, container, false);
				return v;
			}

			@Override
			public void onViewCreated(View v, Bundle savedInstanceState) {
				super.onViewCreated(v, savedInstanceState);
				//				ViewGroup header = (ViewGroup) v.findViewById(R.id.header);
				//				header.setVisibility(View.GONE);
				//				ViewGroup item1 = (ViewGroup) v.findViewById(R.id.product_item1);
				//				ViewGroup item2 = (ViewGroup) v.findViewById(R.id.product_item2);
				//				ViewGroup item3 = (ViewGroup) v.findViewById(R.id.product_item3);
				//				
				//				mBtnItem1 = (ViewGroup) item1.findViewById(R.id.grid_content_container);
				//				mBtnItem2 = (ViewGroup) item2.findViewById(R.id.grid_content_container);
				//				mBtnItem3 = (ViewGroup) item3.findViewById(R.id.grid_content_container);
				//				
				//				mBtnItem1.setOnClickListener(this);
				//				mBtnItem2.setOnClickListener(this);
				//				mBtnItem3.setOnClickListener(this);
				ListView listView = (ListView) v.findViewById(R.id.listView);

				int productId = getArguments().getInt(ARG_PRODUCT_ID);
				ProductData productData = ProductData.getInstance();
				int productTypeId = productData.getProductTypeId(productId);
				int[] relatedProductId = productData.getProductId(productTypeId);
				ProductItem[] relatedItems = new ProductItem[3];
				for (int i = 0 ; i < relatedItems.length; i++) {
					relatedItems[i] = productData.getProductItem(relatedProductId[i]);
				}
				listView.setAdapter(new ProductListViewAdapter(getActivity(), (MainActivity)getActivity(), false, relatedItems));
			}

			@Override
			public void onClick(View v) {
				//				if (v == mBtnItem1) {
				//					
				//				} else if (v == mBtnItem2) {
				//					
				//				} else if (v == mBtnItem3) {
				//					
				//				}
				//				ProductDetailFragment f = ProductDetailFragment.newInstance(0);
				//				((MainActivity)getActivity()).switchFragment(f, true, "");
				//			}
			}
		}
	}

	// View.OnClickListener
	@Override
	public void onClick(View v) {
		int quantity = Integer.parseInt(mQuantityTextView.getText().toString());

		switch (v.getId()) {
		case (R.id.buttonAddQuantity) : {
			quantity += 1;
			quantity = Math.min(11, quantity);
			mQuantityTextView.setText("" + quantity);
		}
		break;
		case (R.id.buttonMinusQuantity) : {
			quantity -= 1;
			quantity = Math.max(1, quantity);
			mQuantityTextView.setText("" + quantity);
		}
		break;
		case (R.id.buttonAddToCart) : {
			int addAmount = Integer.parseInt((String) mQuantityTextView.getText());
			String msg = "" + addAmount + " item(s) added to cart.";
			showToast(msg);
			
			int productId = getArguments().getInt(ARG_PRODUCT_ID);
			ShoppingCart.getInstance(getActivity()).addItem(productId, addAmount);
		}
		break;
		}
	}
	
	private void showToast(String message) {
		LayoutInflater inflater = getActivity().getLayoutInflater();
		View layout = inflater.inflate(R.layout.main_custom_toast,
		                               (ViewGroup) getActivity().findViewById(R.id.toast_layout_root));

		TextView textView = (TextView) layout.findViewById(R.id.toast_text);
		textView.setText(message);

		TypedValue tv = new TypedValue();
		int actionBarHeight = 0;
		if (getActivity().getTheme().resolveAttribute(android.R.attr.actionBarSize, tv, true)) {
		    actionBarHeight = TypedValue.complexToDimensionPixelSize(tv.data,getResources().getDisplayMetrics());
		}
		
		Toast toast = new Toast(getActivity());
		toast.setGravity(Gravity.TOP | Gravity.FILL_HORIZONTAL, 0, actionBarHeight);
		toast.setDuration(2000);
		toast.setView(layout);
		toast.show();
	}
}
