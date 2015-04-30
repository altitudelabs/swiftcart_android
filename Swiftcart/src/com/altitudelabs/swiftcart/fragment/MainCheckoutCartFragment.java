package com.altitudelabs.swiftcart.fragment;

import java.text.NumberFormat;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.altitudelabs.swiftcart.R;
import com.altitudelabs.swiftcart.activity.MainActivity;
import com.altitudelabs.swiftcart.model.ProductData;
import com.altitudelabs.swiftcart.model.ProductItem;
import com.altitudelabs.swiftcart.model.ShoppingCart;

public class MainCheckoutCartFragment extends Fragment implements OnClickListener{

	private ListView checkoutCartList;
	private TextView subTotalTextView;
	private TextView deliveryFeeTextView;
	private TextView totalPriceTextView;
	private ViewGroup checkoutButton;
	private ShoppingCart mShoppingCart;
	private CheckoutCartListAdapter mCheckoutCartListAdapter;
	
	public MainCheckoutCartFragment() {
		super();
	}
	
	public void updateData() {
		if (mCheckoutCartListAdapter != null) {
			mCheckoutCartListAdapter.updateData();
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.main_checkout_cart, container, false);
		return v;
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		
		mShoppingCart = ShoppingCart.getInstance(getActivity());
		
		// Hook up views
		checkoutCartList = (ListView) getActivity().findViewById(R.id.main_checkout_cart_list);
		subTotalTextView = (TextView) getActivity().findViewById(R.id.main_checkout_cart_subtotal_textView);
		deliveryFeeTextView = (TextView) getActivity().findViewById(R.id.main_checkout_cart_delivery_fee_textView);
		totalPriceTextView = (TextView) getActivity().findViewById(R.id.total_price);
		checkoutButton = (ViewGroup) getActivity().findViewById(R.id.main_checkout_cart_checkout_button);
		
		checkoutButton.setOnClickListener(this);
		
		mCheckoutCartListAdapter = new CheckoutCartListAdapter();
		mCheckoutCartListAdapter.setCartPriceChangeListener(new CartPriceChangeListener() {
			
			@Override
			public void onTotalPriceChanged(float price) {
				NumberFormat numberformatter = NumberFormat.getCurrencyInstance();
				String totalPriceStr = numberformatter.format(price);
				subTotalTextView.setText(totalPriceStr);
				totalPriceTextView.setText(totalPriceStr);
			}
		});
		checkoutCartList.setAdapter(mCheckoutCartListAdapter);
	}

	@Override
	public void onClick(View v) {
		if (v == checkoutButton) {
			AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
			builder.setPositiveButton(R.string.need_login_ok, new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					dialog.dismiss();
					LoginLandingFragment frag = new LoginLandingFragment();
					((MainActivity)getActivity()).switchFragment(frag, true, false);
				}
			});
			builder.setNegativeButton(R.string.need_login_later, new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					dialog.dismiss();
				}
			});
			
			builder.setMessage(R.string.need_login_msg);
//			       .setTitle(R.string.need_login_title);
			AlertDialog dialog = builder.create();
			dialog.show();
		}
	}
	
	public class CheckoutCartListAdapter extends BaseAdapter {

		class ViewHolder {
			public ImageView image;
			public TextView name;
			public TextView weight;
			public TextView numberAndPrice;
			public TextView totalPrice;
			public Button deleteButton;
		}
		
		private ProductData mProductData;
		private Integer[] mAllItemIds;
		private int mSelectedView = -1;
		private CartPriceChangeListener mCartPriceChangeListener;
		
		public CheckoutCartListAdapter() {
			super();
			mProductData = ProductData.getInstance();
			mAllItemIds = mShoppingCart.getAllItemIds();
		}
		
		public void updateData() {
			mAllItemIds = mShoppingCart.getAllItemIds();
			notifyDataSetChanged();
			if (mCartPriceChangeListener != null) {
				float totalPrice = mShoppingCart.getTotalPrice();
				mCartPriceChangeListener.onTotalPriceChanged(totalPrice);
			}
		}
		
		public void setCartPriceChangeListener(CartPriceChangeListener cartPriceChangeListener) {
			mCartPriceChangeListener = cartPriceChangeListener;
		}

		@Override
		public int getCount() {
			return mAllItemIds.length;
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
				convertView = LayoutInflater.from(getActivity()).inflate(R.layout.main_checkout_cart_list_item, parent, false);
				viewHolder = new ViewHolder();
				viewHolder.image = (ImageView) convertView.findViewById(R.id.product_image);
				viewHolder.name = (TextView) convertView.findViewById(R.id.product_title);
				viewHolder.weight = (TextView) convertView.findViewById(R.id.product_weight);
				viewHolder.numberAndPrice = (TextView) convertView.findViewById(R.id.product_number_and_price);
				viewHolder.totalPrice = (TextView) convertView.findViewById(R.id.product_total_price);
				viewHolder.deleteButton = (Button) convertView.findViewById(R.id.delete_button);
				convertView.setTag(viewHolder);
			} else {
				viewHolder = (ViewHolder) convertView.getTag();
			}
			
			// Render item
			int productId = mAllItemIds[position].intValue();
			ProductItem productItem = mProductData.getProductItem(productId);
			viewHolder.image.setImageResource(productItem.imageResId);
			viewHolder.name.setText(productItem.name);
			viewHolder.weight.setText(productItem.numberPerPack);
			
			float price = productItem.priceNow;
			int boughtNumber = mShoppingCart.getItemAmount(productId);
			NumberFormat formatter = NumberFormat.getCurrencyInstance();
			String priceStr = formatter.format(price);
			viewHolder.numberAndPrice.setText("" + boughtNumber + " x " + priceStr);
			
			float totalPrice = price * boughtNumber;
			String totalPriceStr = formatter.format(totalPrice);
			viewHolder.totalPrice.setText(totalPriceStr);
			
			if (mSelectedView == position) {
				// Show delete button
				viewHolder.image.setVisibility(View.GONE);
				viewHolder.deleteButton.setVisibility(View.VISIBLE);
			} else {
				// Hide delete button
				viewHolder.image.setVisibility(View.VISIBLE);
				viewHolder.deleteButton.setVisibility(View.GONE);
			}
			
			convertView.setOnClickListener(new OnComponentClickListener(position));
			viewHolder.deleteButton.setOnClickListener(new OnComponentClickListener(position));
				
			return convertView;
		}
		
		class OnComponentClickListener implements View.OnClickListener {

			int mViewPosition;
			
			public OnComponentClickListener(int viewPosition) {
				super();
				mViewPosition = viewPosition;
			}

			@Override
			public void onClick(View v) {
				if (v.getId() == R.id.delete_button) {
					mShoppingCart.removeItem(mAllItemIds[mViewPosition]);
				} else { // convert View
					if (mSelectedView != mViewPosition) {
					  mSelectedView = mViewPosition;
					} else {
						mSelectedView = -1;
					}
				}
				updateData();
			}
		}
	}
	
	interface CartPriceChangeListener {
		void onTotalPriceChanged(float price);			
	}
}
