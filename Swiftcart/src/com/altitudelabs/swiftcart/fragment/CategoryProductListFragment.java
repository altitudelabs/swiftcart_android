package com.altitudelabs.swiftcart.fragment;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.altitudelabs.swiftcart.R;
import com.altitudelabs.swiftcart.activity.MainActivity;
import com.altitudelabs.swiftcart.adapter.ProductListViewAdapter;
import com.altitudelabs.swiftcart.config.Config;
import com.altitudelabs.swiftcart.fragment.CategoryProductListFragment.SortMenuFragment.SortListener;
import com.altitudelabs.swiftcart.model.ProductData;
import com.altitudelabs.swiftcart.model.ProductItem;
import com.altitudelabs.swiftcart.view.PagerSlidingTabStrip;


public class CategoryProductListFragment extends Fragment {

	public static final String ARG_PRODUCT_TYPE_IDS = "arg product type ids"; 
	public static final String ARG_DEFAULT_TAB_INDEX = "arg default tab index"; 
	private static final int LOADING_SPINNER_TIME = 0;
	
	private static final ScheduledExecutorService worker = Executors.newSingleThreadScheduledExecutor();
	
	private String mActionBarTitle;
	private ProgressBar mLoadingSpinner;
	private ViewPagerAdapter mViewPagerAdapter;
	
	
	private static int mSelectedSortOption; // 0, 1, 2
	
	private boolean alreadyCreated;

	// Sort menu
//	private ViewGroup mSortMenu;
	
	public static CategoryProductListFragment newInstance(int[] productTypeIds, int defaultTabIndex) {
		CategoryProductListFragment f = new CategoryProductListFragment();
		Bundle args = new Bundle();
		args.putIntArray(ARG_PRODUCT_TYPE_IDS, productTypeIds);
		args.putInt(ARG_DEFAULT_TAB_INDEX, defaultTabIndex);
		f.setArguments(args);
		return f;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.category_product_list_fragment, container, false);
		mLoadingSpinner = (ProgressBar) v.findViewById(R.id.loading_spinner);
		mLoadingSpinner.setVisibility(View.VISIBLE);
		return v;
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		Runnable task = new Runnable() {
			
			@Override
			public void run() {
				getActivity().runOnUiThread(new Runnable() {
					
					@Override
					public void run() {
						prepare();
						mLoadingSpinner.setVisibility(View.GONE);
					}
				});
			}
		};
		if (!alreadyCreated) {
			worker.schedule(task, LOADING_SPINNER_TIME, TimeUnit.MILLISECONDS);
			alreadyCreated = true;
		} else {
			worker.schedule(task, LOADING_SPINNER_TIME, TimeUnit.MILLISECONDS);
		}
	}
	
	private void prepare() {
		int[] productTypeIds = getArguments().getIntArray(ARG_PRODUCT_TYPE_IDS);
		int defaultTabIndex = getArguments().getInt(ARG_DEFAULT_TAB_INDEX);
		mActionBarTitle = ProductData.getInstance().getActionBarTitle(productTypeIds[0]);
		
		// Setup pager by category name

		ViewPager viewPager = (ViewPager) getActivity().findViewById(R.id.pager);
		// Set the ViewPagerAdapter into ViewPager
		mViewPagerAdapter = new ViewPagerAdapter(getChildFragmentManager(), productTypeIds);
		viewPager.setAdapter(mViewPagerAdapter);
		viewPager.setCurrentItem(defaultTabIndex, false);

		PagerSlidingTabStrip tabStrip = (PagerSlidingTabStrip) getActivity().findViewById(R.id.pager_tabs);
		tabStrip.setViewPager(viewPager);
		Config.customizePagerSlidingTabStrip(tabStrip, getActivity());
	}
	
//	private Fragment findPagerFragmentByPosition(int position) {
////		ViewPagerAdapter fragmentPagerAdapter = mViewPagerAdapter;
//	    return getActivity().getSupportFragmentManager().findFragmentByTag(getFragmentTag(R.id.pager, position));
//	}
//	
//	private String getFragmentTag(int viewPagerId, int fragmentPosition) {
//	     return "android:switcher:" + viewPagerId + ":" + fragmentPosition;
//	}
	
	private void sortAllPageItems() {
		for (int i = 0; i < mViewPagerAdapter.getRegisteredFragments().size(); i++) {
			ListFragment frag = mViewPagerAdapter.getRegisteredFragments().get(i);
			if (frag != null) frag.sortItems(mSelectedSortOption);
		}
	}
	
	@Override
	public void onResume() {
		super.onResume();
		
		int[] productTypeIds = getArguments().getIntArray(ARG_PRODUCT_TYPE_IDS);
		int defaultTabIndex = getArguments().getInt(ARG_DEFAULT_TAB_INDEX);
		mActionBarTitle = ProductData.getInstance().getActionBarTitle(productTypeIds[0]);
		
		MainActivity mainActivity = (MainActivity)getActivity();
		mainActivity.showActionBarBackViews(mActionBarTitle);
		mainActivity.showActionBarCheckoutButton(true);
		mainActivity.showActionBarSearchButton(true);
		mainActivity.showActionBarFilterButton(true);
		
		mainActivity.setActionBarFilterButtonOnClickListener(new ActionBarFilterOnClickListener());
	}

	public static class ViewPagerAdapter extends FragmentStatePagerAdapter {

		private String[] mTabtitles;
		private int[] mTabIds;
		private SparseArray<ListFragment> mRegisteredFragments = new SparseArray<ListFragment>();
		
		public ViewPagerAdapter(FragmentManager fm, int[] tabIds) {
			super(fm);
			mTabIds = tabIds;
			mTabtitles = new String[mTabIds.length];
			ProductData productData  = ProductData.getInstance();
			for (int i = 0 ; i < mTabIds.length; i++) {
				mTabtitles[i] = productData.getProductType(mTabIds[i]);
			}
		}
		
		public SparseArray<ListFragment> getRegisteredFragments() {
			return mRegisteredFragments;
		}

		@Override
		public int getCount() {
			return mTabIds.length;
		}

		@Override
		public Fragment getItem(int position) {
			ListFragment frag = ListFragment.newInstance(mTabIds[position]);
//			mRegisteredFragments.put(position, frag);
			return frag;
		}

		@Override
	    public Object instantiateItem(ViewGroup container, int position) {
			ListFragment fragment = (ListFragment) super.instantiateItem(container, position);
	        mRegisteredFragments.put(position, fragment);
	        return fragment;
	    }

	    @Override
	    public void destroyItem(ViewGroup container, int position, Object object) {
	    	mRegisteredFragments.remove(position);
	        super.destroyItem(container, position, object);
	    }
		
		@Override
		public CharSequence getPageTitle(int position) {
			return mTabtitles[position];
		}
	}


	public static class ListFragment extends Fragment {
		
		public static String ARG_PRODUCT_TYPE_ID = "arg product type id";
		private ProductListViewAdapter mProductListViewAdapter;
		
		public static ListFragment newInstance(int productTypeId) {
			ListFragment f = new ListFragment();
			Bundle args = new Bundle();
			args.putInt(ARG_PRODUCT_TYPE_ID, productTypeId);
			f.setArguments(args);
			return f;
		}
		
		/**
		 * 
		 * @param sortMethod 0, 1, 2
		 */
		public void sortItems(int sortMethod) {
			mProductListViewAdapter.sortProductItems(sortMethod);
		}
		
		@Override
		public View onCreateView(LayoutInflater inflater,
				ViewGroup container, Bundle savedInstanceState) {
			View v = inflater.inflate(R.layout.category_product_list_fragment_list, container, false);
			return v;
		}
		
		@Override
		public void onViewCreated(View v, Bundle savedInstanceState) {
			super.onViewCreated(v, savedInstanceState);
			
			int productTypeId = getArguments().getInt(ARG_PRODUCT_TYPE_ID);
			
			ProductData productData = ProductData.getInstance();
			int[] productIds = productData.getProductId(productTypeId);
			ProductItem[] items = new ProductItem[productIds.length];
			for (int i = 0 ; i < productIds.length; i++) {
				int id = productIds[i];
				items[i] = productData.getProductItem(id);
			}
			ListView listView = (ListView) v.findViewById(R.id.listView);
			mProductListViewAdapter = new ProductListViewAdapter(getActivity(), (MainActivity)getActivity(), false, items);
			listView.setAdapter(mProductListViewAdapter);
			
			sortItems(mSelectedSortOption);
		}

		@Override
		public void onActivityCreated(Bundle savedInstanceState) {
			super.onActivityCreated(savedInstanceState);
		}
	}
	
	class ActionBarFilterOnClickListener implements View.OnClickListener {

		@Override
		public void onClick(View v) {
			
			SortMenuFragment frag = new SortMenuFragment();
			frag.setSortListener(new SortListener() {
				
				@Override
				public void onSort() {
					sortAllPageItems();
					
				}
			});
			FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
			ft.setCustomAnimations(R.anim.enter_vertical, 0, 0, R.anim.exit_vertical);
			ft.replace(R.id.sort_menu_fragment_container, frag);
			ft.addToBackStack(null);
			ft.commit();
			
			((MainActivity)getActivity()).setActionBarFilterButtonEnable(false);
		}
	}
	
	static class SortMenuFragment extends Fragment implements View.OnClickListener {
		
		private ViewGroup mBackground;
		private ViewGroup mBtnAlphabetical;
		private ViewGroup mBtnPrice;
		private ViewGroup mBtnSpecialOffers;
		private ImageView mTickAlphabetical;
		private ImageView mTickPrice;
		private ImageView mTickSpeicalOffers;
		private SortListener mSortlistener;
		
		public void setSortListener (SortListener sortListener) {
			mSortlistener = sortListener;
		}
		
		@Override
		public void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			((MainActivity)getActivity()).setActionBarFilterButtonEnable(false);
		}
		
		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View v = inflater.inflate(R.layout.category_product_list_fragment_sort_menu, container, false);
			return v;
		}
		
		@Override
		public void onViewCreated(View view, Bundle savedInstanceState) {
			super.onViewCreated(view, savedInstanceState);
			mBackground = (ViewGroup) view.findViewById(R.id.background);
			mBtnAlphabetical = (ViewGroup) view.findViewById(R.id.sort_option_1);
			mBtnPrice = (ViewGroup) view.findViewById(R.id.sort_option_2);
			mBtnSpecialOffers = (ViewGroup) view.findViewById(R.id.sort_option_3);
			mTickAlphabetical = (ImageView) view.findViewById(R.id.tick);
			mTickPrice = (ImageView) view.findViewById(R.id.tick2);
			mTickSpeicalOffers = (ImageView) view.findViewById(R.id.tick3);
			
			mBackground.setOnClickListener(this);
			mBtnAlphabetical.setOnClickListener(this);
			mBtnPrice.setOnClickListener(this);
			mBtnSpecialOffers.setOnClickListener(this);
			mTickAlphabetical.setOnClickListener(this);
			mTickPrice.setOnClickListener(this);
			mTickSpeicalOffers.setOnClickListener(this);
			
			updateTickUI();
		}
		
		@Override
		public void onActivityCreated(Bundle savedInstanceState) {
			super.onActivityCreated(savedInstanceState);
		}

		@Override
		public void onDestroy() {
			super.onDestroy();
			((MainActivity)getActivity()).setActionBarFilterButtonEnable(true);
		}

		@Override
		public void onClick(View v) {
			if (v == mBackground) {
				getActivity().getSupportFragmentManager().popBackStack();
			} else if (v == mBtnAlphabetical) {
				mSelectedSortOption = 0;
				updateTickUI();
				getActivity().getSupportFragmentManager().popBackStack();
				mSortlistener.onSort();
			} else if (v == mBtnPrice) {
				mSelectedSortOption = 1;
				updateTickUI();
				getActivity().getSupportFragmentManager().popBackStack();
				mSortlistener.onSort();
			} else if (v == mBtnSpecialOffers) {
				mSelectedSortOption = 2;
				updateTickUI();
				getActivity().getSupportFragmentManager().popBackStack();
				mSortlistener.onSort();
			}
		}
		
		private void updateTickUI() {
			mTickAlphabetical.setVisibility(View.INVISIBLE);
			mTickPrice.setVisibility(View.INVISIBLE);
			mTickSpeicalOffers.setVisibility(View.INVISIBLE);
			if (mSelectedSortOption == 0) {
				mTickAlphabetical.setVisibility(View.VISIBLE);
			} else if (mSelectedSortOption == 1) {
				mTickPrice.setVisibility(View.VISIBLE);
			} else {
				mTickSpeicalOffers.setVisibility(View.VISIBLE);
			}
		}
		
		public interface SortListener {
			void onSort();
		}
	}
}
