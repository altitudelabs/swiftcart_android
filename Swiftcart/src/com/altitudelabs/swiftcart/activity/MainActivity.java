package com.altitudelabs.swiftcart.activity;

import org.json.JSONException;

import android.app.ActionBar;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.DrawerLayout.DrawerListener;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.altitudelabs.swiftcart.R;
import com.altitudelabs.swiftcart.fragment.CategoryProductListFragment;
import com.altitudelabs.swiftcart.fragment.HomeProductListFragment;
import com.altitudelabs.swiftcart.fragment.LoginLandingFragment;
import com.altitudelabs.swiftcart.fragment.MainCheckoutCartFragment;
import com.altitudelabs.swiftcart.fragment.MainNavigationMenuFragment;
import com.altitudelabs.swiftcart.fragment.SearchFragment;
import com.altitudelabs.swiftcart.model.ProductData;
import com.altitudelabs.swiftcart.model.ShoppingCart;


public class MainActivity extends FragmentActivity implements DrawerListener, MainNavigationMenuFragment.OnListItemClickListener {

	private static String FRAG_TAG_CHECKOUT_DRAWER = "frag tag checkout drawer";
	
	// Drawer views
	private DrawerLayout mDrawerLayout;
	private ViewGroup mDrawerNavigationMenu;
	private ViewGroup mDrawerCheckoutContainerView;

	// Drawer states
	private boolean isDrawerMenuOpening;
	private boolean isDrawerCheckoutOpening;

	// Different types of action bars
	private ViewGroup actionBarNormal;
	private ViewGroup actionBarSearch;
	
	// Action bar left views
	private ImageView mButtonMenu;
	private ViewGroup mBackContainer;
	private ImageView mButtonBack;
	private TextView mBackTitle;

	// Action bar right views
	private ImageView mButtonCheckout;
	private ImageView mButtonSearch;
	private ImageView mButtonFilter;

	// Action bar center views
	private TextView mCentralTitle;
	
	private ProgressBar mLoadingSpinner;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		// Find views
		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		mDrawerNavigationMenu = (ViewGroup) findViewById(R.id.left_drawer);
		mDrawerCheckoutContainerView = (ViewGroup) findViewById(R.id.right_drawer);
		mLoadingSpinner = (ProgressBar) findViewById(R.id.loading_spinner);

		// Setup action bar
		final ViewGroup actionBarLayout = (ViewGroup) getLayoutInflater().inflate(R.layout.main_action_bar, null);
		final ActionBar actionBar = getActionBar();
		actionBar.setDisplayShowHomeEnabled(false);
		actionBar.setDisplayShowTitleEnabled(false);
		actionBar.setDisplayShowCustomEnabled(true);
		actionBar.setCustomView(actionBarLayout);

		// Drawer
		mDrawerLayout.setDrawerListener(this);
		// Left drawer
		MainNavigationMenuFragment navigationMenuFrag = new MainNavigationMenuFragment();
		navigationMenuFrag.setOnListItemClickListener(this);
		getSupportFragmentManager().beginTransaction().add(R.id.left_drawer, navigationMenuFrag).commit();
		// Right drawer
		MainCheckoutCartFragment checkoutCartFragment = new MainCheckoutCartFragment();
		getSupportFragmentManager().beginTransaction().add(R.id.right_drawer, checkoutCartFragment, FRAG_TAG_CHECKOUT_DRAWER).commit();

		// Content
		HomeProductListFragment productListFragment = new HomeProductListFragment();
		getSupportFragmentManager().beginTransaction().add(R.id.content_frame, productListFragment).commit();
	}

	@Override
	protected void onStart() {
		super.onStart();
		// Action bar views
		
		actionBarNormal = (ViewGroup) findViewById(R.id.action_bar_normal);
		actionBarSearch = (ViewGroup) findViewById(R.id.action_bar_search);
		
		// Left views
		mButtonMenu = (ImageView) findViewById(R.id.topbar_menu_button_open_menu);
		mBackContainer = (ViewGroup) findViewById(R.id.topbar_back_button_and_title_container);
		mButtonBack = (ImageView) findViewById(R.id.topbar_button_back);
		mBackTitle = (TextView) findViewById(R.id.topbar_back_title);
		// Right views
		mButtonCheckout = (ImageView) findViewById(R.id.topbar_menu_button_open_checkout);
		mButtonSearch = (ImageView) findViewById(R.id.topbar_menu_button_search_button);
		mButtonFilter = (ImageView) findViewById(R.id.topbar_menu_button_filter_button);
		// Central view
		mCentralTitle = (TextView) findViewById(R.id.top_bar_title);

		// Action bar Buttons
		View.OnClickListener topBarOnClickListener = new View.OnClickListener () {
			@Override
			public void onClick(View v) {
				if (v == mButtonMenu) {
					isDrawerMenuOpening = !isDrawerMenuOpening;
					toogleMenuDrawer(isDrawerMenuOpening);

				} else if (v == mButtonBack) {
					getSupportFragmentManager().popBackStack();

				} else if (v == mButtonCheckout) {
					isDrawerCheckoutOpening = !isDrawerCheckoutOpening;
					toogleCheckoutDrawer(isDrawerCheckoutOpening);
				} else if (v == mButtonSearch) {
//					Intent searchActivityIntent = new Intent(MainActivity.this, SearchActivity.class);
//					startActivity(searchActivityIntent);
//					overridePendingTransition(0,0);
					SearchFragment searchFrag = new SearchFragment();
					switchFragment(searchFrag, true, true);
					if (isDrawerMenuOpening) {
						toogleMenuDrawer(false);
					}
					if (isDrawerCheckoutOpening) {
						toogleCheckoutDrawer(false);
					}
					
				} else if (v == mButtonFilter) {

				}
			}
		};
		mButtonMenu.setOnClickListener(topBarOnClickListener);
		mBackContainer.setOnClickListener(topBarOnClickListener);
		mButtonBack.setOnClickListener(topBarOnClickListener);
		mButtonCheckout.setOnClickListener(topBarOnClickListener);
		mButtonSearch.setOnClickListener(topBarOnClickListener);
		mButtonFilter.setOnClickListener(topBarOnClickListener);

		showActionBarForHome();
	}

	@Override
	protected void onResume() {
		super.onResume();
	}
	
	@Override
	protected void onStop() {
		super.onStop();
		try {
			ShoppingCart.getInstance(this).saveData();
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	public void showActionBar() {
		actionBarNormal.setVisibility(View.VISIBLE);
		actionBarSearch.setVisibility(View.INVISIBLE);
	}
	
	public void showSearchBar() {
		actionBarNormal.setVisibility(View.INVISIBLE);
		actionBarSearch.setVisibility(View.VISIBLE);
	}

	public void switchFragment(Fragment frag, boolean addBackStack, boolean animate) {
		if (isDrawerCheckoutOpening) {
			toogleCheckoutDrawer(false);
		}
		if (isDrawerMenuOpening) {
			toogleMenuDrawer(false);
		}
		
		FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
		if (animate) {
			ft.setCustomAnimations(R.anim.enter_horizontal1, R.anim.enter_horizontal2, R.anim.exit_horizontal1, R.anim.exit_horizontal2);
		}
		ft.replace(R.id.content_frame, frag);
		if (addBackStack) ft.addToBackStack(null);
		ft.commit();
	}

	private void toogleMenuDrawer(boolean open) {
		if (open) {
			mDrawerLayout.openDrawer(mDrawerNavigationMenu);
			isDrawerMenuOpening = true;
			toogleCheckoutDrawer(false);
		} else {
			mDrawerLayout.closeDrawer(mDrawerNavigationMenu);
			isDrawerMenuOpening = false;
		}
	}

	private void toogleCheckoutDrawer(boolean open) {
		if (open) {
			mDrawerLayout.openDrawer(mDrawerCheckoutContainerView);
			isDrawerCheckoutOpening = true;
			toogleMenuDrawer(false);
		} else {
			mDrawerLayout.closeDrawer(mDrawerCheckoutContainerView);
			isDrawerCheckoutOpening = false;
		}
	}

	//	 DrawerListener

	@Override
	public void onDrawerClosed(View drawerView) {
		if (drawerView == mDrawerNavigationMenu) {
			isDrawerMenuOpening = false;
		} else if (drawerView == mDrawerCheckoutContainerView) {
			isDrawerCheckoutOpening = false;
		}
	}

	@Override
	public void onDrawerOpened(View drawerView) {
		if (drawerView == mDrawerNavigationMenu) {
			isDrawerMenuOpening = true;
		} else if (drawerView == mDrawerCheckoutContainerView) {
			isDrawerCheckoutOpening = true;
		}
	}

	@Override
	public void onDrawerSlide(View drawerView, float slideOffset) {
		if (drawerView == mDrawerCheckoutContainerView) {
			// Going to open drawer now
			if (isDrawerCheckoutOpening) {
				MainCheckoutCartFragment fragCheckout = (MainCheckoutCartFragment)getSupportFragmentManager().findFragmentByTag(FRAG_TAG_CHECKOUT_DRAWER);
				fragCheckout.updateData();
			}
		}
	}

	@Override
	public void onDrawerStateChanged(int newState) {
	}

	@SuppressWarnings("unused")
	private int _______________MainNavigationMenuFragment_OnListItemClickListener_______________;

	@Override
	public boolean onGroupItemClicked(View v, String itemTitle, int groupItemIndex) {
		if (itemTitle.equals(MainNavigationMenuFragment.MENU_TITLE_MY_ACCT)) {
			// My Account clicked
			toogleMenuDrawer(false);
			LoginLandingFragment frag = new LoginLandingFragment();
			switchFragment(frag, true, false);
			return true;
			
		} else if (itemTitle.equals(MainNavigationMenuFragment.MENU_TITLE_FRESH_PRODUCE)) {
			return false;
			
		} else {
			// Empty food category clicked
//			toogleMenuDrawer(false);
//			CategoryProductListFragment f = CategoryProductListFragment.newInstance(null, 0);
//			switchFragment(f, true, null);
//			return true;
			return false;
		}
	}

	@Override
	public void onChildItemClicked(View v, String itemTitle,
			int groupItemIndex, int childItemIndex) {
		toogleMenuDrawer(false);
		CategoryProductListFragment f = CategoryProductListFragment.newInstance(ProductData.getInstance().getProductTypeIds(), childItemIndex);
		switchFragment(f, true, true);
	}

	@SuppressWarnings("unused")
	private int _______________ActionBar_______________;

	public void showActionBarTitle(boolean show) {
		mCentralTitle.setVisibility(show ? View.VISIBLE : View.GONE);
		mCentralTitle.setText("Swiftcart");
		Typeface type = Typeface.createFromAsset(getAssets(),"fonts/GalanoGrotesque-Bold.otf"); 
		mCentralTitle.setTypeface(type);
	}
	
	public void showActionBarTitleWithOtherName(String newTitle) {
		mCentralTitle.setVisibility(View.VISIBLE);
		mCentralTitle.setText(newTitle);
		Typeface type = Typeface.createFromAsset(getAssets(),"fonts/SourceSansPro-Light.otf"); 
		mCentralTitle.setTypeface(type);
	}

	public void showActionBarBackViews(String backButtonTitle) {
		mBackContainer.setVisibility(View.VISIBLE);
		mBackTitle.setText(backButtonTitle);
		mButtonMenu.setVisibility(View.INVISIBLE);

		showActionBarTitle(false);
	}

	public void showActionBarMenuButton() {
		mButtonMenu.setVisibility(View.VISIBLE);
		mBackContainer.setVisibility(View.INVISIBLE);
	}

	public void showActionBarFilterButton(boolean show) {
		mButtonFilter.setVisibility(show ? View.VISIBLE : View.GONE);
	}

	public void showActionBarSearchButton(boolean show) {
		mButtonSearch.setVisibility(show ? View.VISIBLE : View.GONE);
	}

	public void showActionBarCheckoutButton(boolean show) {
		mButtonCheckout.setVisibility(show ? View.VISIBLE : View.GONE);
	}

	public void hideAllActionBarComponents() {
		// left
		showActionBarFilterButton(false);
		showActionBarSearchButton(false);
		showActionBarCheckoutButton(false);
		showActionBarTitle(false);
		mBackContainer.setVisibility(View.GONE);
		mCentralTitle.setVisibility(View.GONE);
	}
	
	public void showActionBar(boolean show) {
		if (show) {
			    getActionBar().show();
			} else {
				getActionBar().hide();
			}
//		actionBarNormal.setVisibility(show ? View.VISIBLE : View.INVISIBLE);
	}
	
	public void setActionBarFilterButtonOnClickListener(View.OnClickListener listener) {
		mButtonFilter.setOnClickListener(listener);
	}
	
	public void setActionBarFilterButtonEnable(boolean enable) {
		mButtonFilter.setEnabled(enable);
	}

	private void showActionBarForHome() {
		showActionBarFilterButton(false);
		showActionBarSearchButton(true);
		showActionBarCheckoutButton(true);
		showActionBarTitle(true);
		showActionBarMenuButton();
	}
	
	public void showLoadingSpinner(boolean show) {
		mLoadingSpinner.setVisibility(show ? View.VISIBLE : View.INVISIBLE);
	}
}
