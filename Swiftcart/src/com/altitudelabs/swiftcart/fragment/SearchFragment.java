package com.altitudelabs.swiftcart.fragment;

import java.text.NumberFormat;
import java.util.ArrayList;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.altitudelabs.swiftcart.R;
import com.altitudelabs.swiftcart.activity.MainActivity;
import com.altitudelabs.swiftcart.model.ProductData;
import com.altitudelabs.swiftcart.model.ProductItem;
import com.altitudelabs.swiftcart.model.ShoppingCart;

public class SearchFragment extends Fragment {

	private Button btnCancel;
	private EditText searchBox;
	private ListView listView;
	private int[] productItemIds;
	private ArrayList<Integer> filteredProductItemIds;
	private SearchListAdapter searchListAdapter;
	private ProductData productData;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.activity_search, container, false);
		return v;
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		((MainActivity)getActivity()).showSearchBar();

		// Prepare data

		productData = ProductData.getInstance();
		// Get all products
		productItemIds = new int[0];
		int[] productTypeIds = productData.getProductTypeIds();
		ArrayList<Integer> productIdList = new ArrayList<Integer>();
		for (int i = 0; i < productTypeIds.length; i++) {
			int[] productIds = productData.getProductId(productTypeIds[i]);
			for (int j = 0; j < productIds.length; j++) {
				productIdList.add(productIds[j]);
			}
		}
		productItemIds = new int[productIdList.size()];
		for (int i = 0; i < productIdList.size(); i++) {
			productItemIds[i] = productIdList.get(i).intValue();
		}

		updateFilteredProductItem("");

		// Setup views
		btnCancel = (Button) getActivity().findViewById(R.id.btn_cancel);
		searchBox = (EditText) getActivity().findViewById(R.id.search_box);
		listView = (ListView) getActivity().findViewById(R.id.search_list);

		btnCancel.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				getActivity().getSupportFragmentManager().popBackStack();
//				getActivity().onBackPressed();
			}
		});

		searchBox.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				updateFilteredProductItem(searchBox.getText().toString());
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {

			}

			@Override
			public void afterTextChanged(Editable s) {

			}
		});

		searchListAdapter = new SearchListAdapter();

		listView.setAdapter(searchListAdapter);
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				int productId = filteredProductItemIds.get(position);
				ProductDetailFragment frag = ProductDetailFragment.newInstance(productId);
				((MainActivity)getActivity()).switchFragment(frag, true, true);
			}
		});
	}
	
	@Override
	public void onPause() {
		super.onPause();
		((MainActivity)getActivity()).showActionBar();
		final InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(getActivity().INPUT_METHOD_SERVICE);
	    imm.hideSoftInputFromWindow(getView().getWindowToken(), 0);
	}

	@Override
	public void onResume() {
		super.onResume();
		((MainActivity)getActivity()).showSearchBar();
		final InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(getActivity().INPUT_METHOD_SERVICE);
	    imm.showSoftInputFromInputMethod(getView().getWindowToken(), 0);
	}

	private void updateFilteredProductItem (String filteringText) {
		filteringText = filteringText.toLowerCase();
		filteredProductItemIds = new ArrayList<Integer>();
		for (int i = 0; i < productItemIds.length; i++) {
			ProductItem item = productData.getProductItem(productItemIds[i]);
			String name = item.name.toLowerCase();
			if (filteringText.length() == 0 || name.contains(filteringText)) {
				filteredProductItemIds.add(productItemIds[i]);
			}
		}
		if (searchListAdapter != null) searchListAdapter.notifyDataSetChanged();
	}

	class SearchListAdapter extends BaseAdapter {

		private ShoppingCart mShoppingCart;

		class ViewHolder {
			public ImageView image;
			public TextView name;
			public TextView weight;
			public TextView totalPrice;
		}

		public SearchListAdapter() {
			super();
			mShoppingCart = ShoppingCart.getInstance(getActivity());
		}

		@Override
		public int getCount() {
			return filteredProductItemIds.size();
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
				convertView = LayoutInflater.from(getActivity()).inflate(R.layout.search_list_item, parent, false);
				viewHolder = new ViewHolder();
				viewHolder.image = (ImageView) convertView.findViewById(R.id.product_image);
				viewHolder.name = (TextView) convertView.findViewById(R.id.product_title);
				viewHolder.weight = (TextView) convertView.findViewById(R.id.product_weight);
				viewHolder.totalPrice = (TextView) convertView.findViewById(R.id.product_total_price);
				convertView.setTag(viewHolder);
			} else {
				viewHolder = (ViewHolder) convertView.getTag();
			}

			// Render item
			ProductItem productItem = productData.getProductItem(filteredProductItemIds.get(position).intValue());
			viewHolder.image.setImageResource(productItem.imageResId);
			viewHolder.name.setText(productItem.name);
			viewHolder.weight.setText(productItem.numberPerPack);

			float price = productItem.priceNow;
			NumberFormat formatter = NumberFormat.getCurrencyInstance();
			String priceStr = formatter.format(price);
			viewHolder.totalPrice.setText(priceStr);

			return convertView;
		}
	}
}
