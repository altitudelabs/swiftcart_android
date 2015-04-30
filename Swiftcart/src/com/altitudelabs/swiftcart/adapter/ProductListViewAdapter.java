package com.altitudelabs.swiftcart.adapter;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;

import android.content.Context;
import android.graphics.Paint;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.altitudelabs.swiftcart.R;
import com.altitudelabs.swiftcart.activity.MainActivity;
import com.altitudelabs.swiftcart.fragment.CategoryProductListFragment;
import com.altitudelabs.swiftcart.fragment.ProductDetailFragment;
import com.altitudelabs.swiftcart.model.ProductData;
import com.altitudelabs.swiftcart.model.ProductItem;

public class ProductListViewAdapter extends BaseAdapter {

	class ViewHolder {
		// Header
		public ViewGroup headerView;
		public TextView headerTitle;
		public TextView viewMore;
		public ViewGroup viewMoreButton;
		// Item
		public ViewGroup grid1;
		public ViewGroup grid2;
		public ViewGroup grid3;
		// Item image
		public ImageView image1;
		public ImageView image2;
		public ImageView image3;
		// Item price info
		public TextView discountLabel1;
		public TextView discountLabel2;
		public TextView discountLabel3;
		public TextView gridTitle1;
		public TextView gridTitle2;
		public TextView gridTitle3;
		public TextView numberPerPack1;
		public TextView numberPerPack2;
		public TextView numberPerPack3;
		public TextView normalPrice1;
		public TextView normalPrice2;
		public TextView normalPrice3;
		public TextView originalPrice1;
		public TextView originalPrice2;
		public TextView originalPrice3;
	}

	private Context mContext;
	private MainActivity mActivity;
	private boolean mShowHeader;

	private ProductData mProductData;
	private ProductItem[] mProductItems;

	public ProductListViewAdapter(Context context, MainActivity activity, boolean showHeader, ProductItem[] productItems) {
		mContext = context;
		mActivity = activity;
		mShowHeader = showHeader;
		mProductItems = productItems;
		mProductData = ProductData.getInstance();
	}
	
	/**
	 * 
	 * @param sortType 0 = alphabetical, 1 = price, 2 = special offers
	 */
	public void sortProductItems(int sortType) {
		ArrayList<ProductItem> list = new ArrayList<ProductItem>(Arrays.asList(mProductItems));
		// Sort
		if (sortType == 0) {
			// alphabetical
			Collections.sort(list, new Comparator<ProductItem>(){
				  public int compare(ProductItem item1, ProductItem item2) {
				    return Character.toString(item1.name.charAt(0)).compareToIgnoreCase(Character.toString(item2.name.charAt(0)));
				  }
				});
		} else if (sortType == 1) {
			// price
			Collections.sort(list, new Comparator<ProductItem>(){
				  public int compare(ProductItem item1, ProductItem item2) {
				    return (item1.priceNow < item2.priceNow ? -1 : 1);
				  }
				});
			
		} else {
			// special offers
			
			// Get all special offers first
			ArrayList<ProductItem> tempList = new ArrayList<ProductItem>();
			for (ProductItem pItem: list) {
				if (pItem.isDiscounted) {
					tempList.add(pItem);
				}
			}
			// Sort by discount rate
			Collections.sort(tempList, new Comparator<ProductItem>(){
				  public int compare(ProductItem item1, ProductItem item2) {
				    return (item1.getSavedPercentFromDiscount() > item2.getSavedPercentFromDiscount() ? -1 : 1);
				  }
				});
			// Append non-discount product at the end
			for (ProductItem pItem: list) {
				if (!tempList.contains(pItem)) {
					tempList.add(pItem);
				}
			}
			list = tempList;
		}
		mProductItems = (ProductItem[]) list.toArray(new ProductItem[list.size()]);
		notifyDataSetChanged();
	}

	@Override
	public int getCount() {
		return mProductItems.length / 3;
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
			convertView = LayoutInflater.from(mContext).inflate(R.layout.product_list_item, parent, false);
			viewHolder = new ViewHolder();
			// Find views
			// Header views
			viewHolder.headerView = (ViewGroup) convertView.findViewById(R.id.header);
			viewHolder.headerTitle = (TextView) convertView.findViewById(R.id.header_title);
			viewHolder.viewMore = (TextView) convertView.findViewById(R.id.viewMore);
			viewHolder.viewMoreButton = (ViewGroup) convertView.findViewById(R.id.button_view_more);
			// Grid views
			ViewGroup gridView1 = (ViewGroup) convertView.findViewById(R.id.product_item1);
			ViewGroup gridView2 = (ViewGroup) convertView.findViewById(R.id.product_item2);
			ViewGroup gridView3 = (ViewGroup) convertView.findViewById(R.id.product_item3);
			// Details in grid views
			viewHolder.grid1 = (ViewGroup) gridView1.findViewById(R.id.grid_content_container);
			viewHolder.grid2 = (ViewGroup) gridView2.findViewById(R.id.grid_content_container);
			viewHolder.grid3 = (ViewGroup) gridView3.findViewById(R.id.grid_content_container);
			viewHolder.image1 = (ImageView) gridView1.findViewById(R.id.product_image);
			viewHolder.image2 = (ImageView) gridView2.findViewById(R.id.product_image);
			viewHolder.image3 = (ImageView) gridView3.findViewById(R.id.product_image);
			viewHolder.discountLabel1 = (TextView) gridView1.findViewById(R.id.product_discount_label);
			viewHolder.discountLabel2 = (TextView) gridView2.findViewById(R.id.product_discount_label);
			viewHolder.discountLabel3 = (TextView) gridView3.findViewById(R.id.product_discount_label);
			viewHolder.gridTitle1 = (TextView) gridView1.findViewById(R.id.product_name);
			viewHolder.gridTitle2 = (TextView) gridView2.findViewById(R.id.product_name);
			viewHolder.gridTitle3 = (TextView) gridView3.findViewById(R.id.product_name);
			viewHolder.numberPerPack1 = (TextView) gridView1.findViewById(R.id.product_quantiy);
			viewHolder.numberPerPack2 = (TextView) gridView2.findViewById(R.id.product_quantiy);
			viewHolder.numberPerPack3 = (TextView) gridView3.findViewById(R.id.product_quantiy);
			viewHolder.normalPrice1 = (TextView) gridView1.findViewById(R.id.product_price);
			viewHolder.normalPrice2 = (TextView) gridView2.findViewById(R.id.product_price);
			viewHolder.normalPrice3 = (TextView) gridView3.findViewById(R.id.product_price);
			viewHolder.originalPrice1 = (TextView) gridView1.findViewById(R.id.product_original_price);
			viewHolder.originalPrice2 = (TextView) gridView2.findViewById(R.id.product_original_price);
			viewHolder.originalPrice3 = (TextView) gridView3.findViewById(R.id.product_original_price);

			viewHolder.grid1.setFocusable(true);
			viewHolder.grid1.setClickable(true);
			viewHolder.grid2.setFocusable(true);
			viewHolder.grid2.setClickable(true);
			viewHolder.grid3.setFocusable(true);
			viewHolder.grid3.setClickable(true);
			
			convertView.setTag(viewHolder);
			
			DisplayMetrics displayMetrics = mContext.getResources().getDisplayMetrics();
	        float dpWidth = displayMetrics.widthPixels;
			dpWidth -= mContext.getResources().getDimensionPixelSize(R.dimen.product_list_item_padding_left) * 4;
			dpWidth /= 3;
			
			ViewGroup gridWrapper1 = (ViewGroup) gridView1.findViewById(R.id.grid_content_container);
			ViewGroup gridWrapper2 = (ViewGroup) gridView2.findViewById(R.id.grid_content_container);
			ViewGroup gridWrapper3 = (ViewGroup) gridView3.findViewById(R.id.grid_content_container);
			
			gridWrapper1.setLayoutParams(new RelativeLayout.LayoutParams((int)dpWidth, RelativeLayout.LayoutParams.WRAP_CONTENT));
			gridWrapper2.setLayoutParams(new RelativeLayout.LayoutParams((int)dpWidth, RelativeLayout.LayoutParams.WRAP_CONTENT));
			gridWrapper3.setLayoutParams(new RelativeLayout.LayoutParams((int)dpWidth, RelativeLayout.LayoutParams.WRAP_CONTENT));
			
			viewHolder.image1.setLayoutParams(new FrameLayout.LayoutParams((int)dpWidth, (int)dpWidth));
			viewHolder.image2.setLayoutParams(new FrameLayout.LayoutParams((int)dpWidth, (int)dpWidth));
			viewHolder.image3.setLayoutParams(new FrameLayout.LayoutParams((int)dpWidth, (int)dpWidth));
			
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}

		// Set listener
		viewHolder.grid1.setOnClickListener(new OnGridClickListener(mProductItems[position * 3 + 0].id));
		viewHolder.grid2.setOnClickListener(new OnGridClickListener(mProductItems[position * 3 + 1].id));
		viewHolder.grid3.setOnClickListener(new OnGridClickListener(mProductItems[position * 3 + 2].id));
		viewHolder.viewMore.setOnClickListener(new OnHeaderClickListener(position));

		// Show header ?
		if (mShowHeader) {
			viewHolder.headerView.setVisibility(View.VISIBLE);
			viewHolder.headerTitle.setText("Fresh " + mProductData.getProductType(mProductData.getProductTypeIds()[position]));
			
			int[] productTypeIds = new int[getCount()];
			for (int i = 0 ; i < productTypeIds.length; i++) {
				productTypeIds[i] = mProductData.getProductTypeId(mProductItems[i *3].id);
			}
			OnViewMoreClickListener viewMoreOnClickListener = new OnViewMoreClickListener(productTypeIds, position % 3);
			viewHolder.viewMoreButton.setOnClickListener(viewMoreOnClickListener);
			viewHolder.viewMore.setOnClickListener(viewMoreOnClickListener);
		} else {
			viewHolder.headerView.setVisibility(View.GONE);
		}

		// Set price info
		if ((position * 3 + 0) < mProductItems.length) {
			setGridContent(mProductItems[position * 3 + 0], viewHolder.image1, viewHolder.discountLabel1, viewHolder.gridTitle1, viewHolder.numberPerPack1, viewHolder.normalPrice1, viewHolder.originalPrice1);
		}
		if ((position * 3 + 1) < mProductItems.length) {
			setGridContent(mProductItems[position * 3 + 1], viewHolder.image2, viewHolder.discountLabel2, viewHolder.gridTitle2, viewHolder.numberPerPack2, viewHolder.normalPrice2, viewHolder.originalPrice2);
		}
		if ((position * 3 + 2) < mProductItems.length) {
			setGridContent(mProductItems[position * 3 + 2], viewHolder.image3, viewHolder.discountLabel3, viewHolder.gridTitle3, viewHolder.numberPerPack3, viewHolder.normalPrice3, viewHolder.originalPrice3);
		}
		
		return convertView;
	}

	private void setGridContent(ProductItem item, ImageView gridImageView, TextView discountLabel, TextView productTitle, TextView numberPerPack, TextView currentPrice, TextView originalPrice) {
		gridImageView.setImageResource(item.imageResId);
		if (item.isSoldOut) {
			discountLabel.setBackgroundResource(R.drawable.product_item_label_sold_out_bg);
			discountLabel.setTextColor(mContext.getResources().getColor(R.color.product_item_label_sold_out_text_color));
		} else {
			discountLabel.setBackgroundResource(R.drawable.product_item_label_save_bg);
			discountLabel.setTextColor(mContext.getResources().getColor(R.color.product_item_label_save_text_color));
		}
		productTitle.setText(item.name);
		numberPerPack.setText(item.numberPerPack);

		NumberFormat formatter = NumberFormat.getCurrencyInstance();
		currentPrice.setText(formatter.format(item.priceNow));
		if (item.isDiscounted) {
			originalPrice.setText(formatter.format(item.priceOriginal));
			currentPrice.setTextColor(mContext.getResources().getColor(R.color.product_item_price_discount_color));
			originalPrice.setVisibility(View.VISIBLE);
			originalPrice.setPaintFlags(originalPrice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
			discountLabel.setVisibility(View.VISIBLE);
			discountLabel.setText("Save " + item.getSavedPercentFromDiscount() + "% Off");
		} else {
			currentPrice.setTextColor(mContext.getResources().getColor(R.color.product_item_price_normal_color));
			originalPrice.setVisibility(View.GONE);
			discountLabel.setVisibility(View.GONE);
		}
	}


	public class OnGridClickListener implements View.OnClickListener {

		private int mProductId;

		public OnGridClickListener(int productId) {
			mProductId = productId;
		}

		@Override
		public void onClick(View v) {
			ProductDetailFragment frag = ProductDetailFragment.newInstance(mProductId);
			mActivity.switchFragment(frag, true, true);
		}
	}

	public class OnHeaderClickListener implements View.OnClickListener {

		private int mPosRow;

		public OnHeaderClickListener(int posRow) {
			mPosRow = posRow;
		}

		@Override
		public void onClick(View v) {

		}
	}
	
	public class OnViewMoreClickListener implements View.OnClickListener {
		
		int[] mProductIds;
		int mDefaultTabIndex;
		
		public OnViewMoreClickListener(int[] productIds, int defaultTabIndex) {
			mProductIds = productIds;
			mDefaultTabIndex = defaultTabIndex;
		}
		
		@Override
		public void onClick(View v) {
			CategoryProductListFragment f = CategoryProductListFragment.newInstance(mProductIds, mDefaultTabIndex);
			String title = ProductData.getInstance().getActionBarTitle(mProductIds[0]);
			mActivity.switchFragment(f, true, true);
		}
		
	}

	//	@SuppressWarnings("unused")
	//	private int _______________ProductListViewAdapter_ProductListViewAdapterListener_______________;
	//	
	//	public interface ProductListViewAdapterHeaderListener {
	//		String getHeaderTitle(int position);
	//		void onHeaderViewMoreClicked(int position);
	//		
	//	}
}
