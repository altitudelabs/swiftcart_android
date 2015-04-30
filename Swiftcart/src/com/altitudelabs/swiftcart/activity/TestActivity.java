package com.altitudelabs.swiftcart.activity;

import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.WindowManager;

import com.altitudelabs.swiftcart.R;

public class TestActivity extends FragmentActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.activity_test);
		getActionBar().hide();
		//		Fragment frag = new MainCheckoutCartFragment();
		//		getSupportFragmentManager().beginTransaction().add(R.id.activity_test_fragment_container, frag).commit();
	}

	@Override
	protected void onStart() {
		super.onStart();
	}

	@Override
	protected void onResume() {
		super.onResume();
	}

	@Override
	protected void onPause() {
		super.onPause();
	}

	@Override
	protected void onStop() {
		super.onStop();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}

	//	public void switchFragment(Fragment frag, boolean addBackStack, final String pageTitle) {
	//        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
	//        ft.replace(R.id.fragment_container, frag);
	//        if (addBackStack) ft.addToBackStack(null);
	//        ft.commit();
	//        
	//        runOnUiThread(new Runnable() {
	//
	//            @Override
	//            public void run() {
	//                
	//                setPageTitle(pageTitle);
	//            }
	//            
	//        });
	//    }

}
