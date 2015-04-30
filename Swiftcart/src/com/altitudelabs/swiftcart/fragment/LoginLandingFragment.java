package com.altitudelabs.swiftcart.fragment;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import com.altitudelabs.swiftcart.R;
import com.altitudelabs.swiftcart.activity.MainActivity;

public class LoginLandingFragment extends Fragment implements OnClickListener {

	private static final int LOADING_SPINNER_TIME = 1000;
	private static final ScheduledExecutorService worker = Executors.newSingleThreadScheduledExecutor();
	
	private TextView mButtonLogin;
	private TextView mButtonSignUp;
	private EditText mInputEmail;
	private EditText mInputPw;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.login_landing, container, false);
		return v;
	}

	@Override
	public void onViewCreated(View v, Bundle savedInstanceState) {
		super.onViewCreated(v, savedInstanceState);
		mButtonLogin = (TextView) v.findViewById(R.id.btn_login);
		mButtonSignUp = (TextView) v.findViewById(R.id.btn_sign_up);
		mInputEmail = (EditText) v.findViewById(R.id.input_email);
		mInputPw = (EditText) v.findViewById(R.id.input_pw);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		setupUI(mButtonLogin);
		setupUI(mButtonSignUp);
		mButtonLogin.setOnClickListener(this);
		mButtonSignUp.setOnClickListener(this);
	}

	@Override
	public void onStop() {
		super.onStop();
		MainActivity mainActivity = (MainActivity)getActivity();
		mainActivity.showActionBar(true);
		final InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Activity.INPUT_METHOD_SERVICE);
		imm.hideSoftInputFromWindow(getView().getWindowToken(), 0);
	}

	@Override
	public void onResume() {
		super.onResume();
		MainActivity mainActivity = (MainActivity)getActivity();
		mainActivity.showActionBar(false);
		final InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Activity.INPUT_METHOD_SERVICE);
		imm.showSoftInputFromInputMethod(getView().getWindowToken(), 0);
	}

	@Override
	public void onClick(View v) {

		if (v == mButtonLogin) {
			
			Log.d("", "mmmmm:" + mInputEmail.getText().toString() + "eee");
			Log.d("", "uuuuu:" + mInputPw.getText().toString() + "eee");
			
			if (!isEmailValid(mInputEmail.getText().toString())) {
				showDialog(getResources().getString(R.string.email_invalid));
				
			} else if (mInputPw.getText().length() == 0) {
				showDialog(getResources().getString(R.string.PwEmpty));
				
			} else {  // Login fail
				final MainActivity mainActivity = (MainActivity) getActivity();
				Runnable task = new Runnable() {
					@Override
					public void run() {
						getActivity().runOnUiThread(new Runnable() {
							
							@Override
							public void run() {
								mainActivity.showLoadingSpinner(false);
								
								// Show dialog
								AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
								builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
									
									@Override
									public void onClick(DialogInterface dialog, int which) {
										dialog.dismiss();
									}
								});
								
								builder.setMessage(R.string.login_result_msg);
								AlertDialog dialog = builder.create();
								dialog.show();
							}
						});
					}
				};
				mainActivity.showLoadingSpinner(true);
				worker.schedule(task, LOADING_SPINNER_TIME, TimeUnit.MILLISECONDS);
			}
			
		} else if (v == mButtonSignUp) {
			MainActivity mainActivity = (MainActivity) getActivity();
			LoginSignUpFragment frag = new LoginSignUpFragment();
			mainActivity.switchFragment(frag, true, false);
		}
	}
	
	private void showDialog(String msg) {
		// Show dialog
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
			}
		});
		
		builder.setMessage(msg);
		AlertDialog dialog = builder.create();
		dialog.show();
	}
	
	private boolean isEmailValid(String email) {
	    boolean isValid = false;

	    String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
	    CharSequence inputStr = email;

	    Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
	    Matcher matcher = pattern.matcher(inputStr);
	    if (matcher.matches()) {
	        isValid = true;
	    }
	    return isValid;
	}
	
	private void setupUI(View view) {
		//Set up touch listener for non-text box views to hide keyboard.
		if(!(view instanceof EditText)) {

			view.setOnTouchListener(new OnTouchListener() {

				public boolean onTouch(View v, MotionEvent event) {
					hideSoftKeyboard(getActivity());
					return false;
				}

			});
		}

		//If a layout container, iterate over children and seed recursion.
		if (view instanceof ViewGroup) {

			for (int i = 0; i < ((ViewGroup) view).getChildCount(); i++) {

				View innerView = ((ViewGroup) view).getChildAt(i);

				setupUI(innerView);
			}
		}
	}

	public void hideSoftKeyboard(Activity activity) {
		InputMethodManager inputMethodManager = (InputMethodManager)  activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
		inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
	}
}
