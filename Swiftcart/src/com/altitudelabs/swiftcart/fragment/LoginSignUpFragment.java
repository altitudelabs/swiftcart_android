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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import com.altitudelabs.swiftcart.R;
import com.altitudelabs.swiftcart.activity.MainActivity;

public class LoginSignUpFragment extends Fragment {
	
	private static final int LOADING_SPINNER_TIME = 1000;
	private static final ScheduledExecutorService worker = Executors.newSingleThreadScheduledExecutor();

	private EditText mInputEmail;
	private EditText mInputPw;
	private EditText mInputConfirmPw;
	private Button mButtonSignUp;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.login_sign_up, container, false);
		return v;
	}
	
	@Override
	public void onViewCreated(View v, Bundle savedInstanceState) {
		super.onViewCreated(v, savedInstanceState);
		mInputEmail = (EditText) v.findViewById(R.id.sign_up_input_email);
		mInputPw = (EditText) v.findViewById(R.id.sign_up_input_pw);
		mInputConfirmPw = (EditText) v.findViewById(R.id.sign_up_input_confirm_pw);
		mButtonSignUp = (Button) v.findViewById(R.id.sign_up_btn_sign_up);
		
		mButtonSignUp.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if (!(isEmailValid(mInputEmail.getText().toString()))) {
					showDialog(getActivity().getResources().getString(R.string.email_invalid));
					
				} else if (mInputPw.getText().toString().length() < 8) {
					showDialog(getActivity().getResources().getString(R.string.PwEmpty));
					
				} else if (!(mInputPw.getText().toString().equals(mInputConfirmPw.getText().toString()))) {
					showDialog(getActivity().getResources().getString(R.string.confirmPwNotCorrect));
					
				} else { // Sign up success
					final MainActivity mainActivity = (MainActivity) getActivity();
					mainActivity.showLoadingSpinner(true);
					
					Runnable task = new Runnable(){

						@Override
						public void run() {
							getActivity().runOnUiThread(new Runnable() {

								@Override
								public void run() {
									mainActivity.showLoadingSpinner(false);
									showDialogAndPop(getActivity().getResources().getString(R.string.signUpSuccess));
								}
							});
						}};
					worker.schedule(task, LOADING_SPINNER_TIME, TimeUnit.MILLISECONDS);
				}
			}
		});
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
	
	private void showDialog(String msg) {
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
	
	private void showDialogAndPop(String msg) {
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
				getActivity().getSupportFragmentManager().popBackStack();
			}
		});
		
		builder.setMessage(msg);
		AlertDialog dialog = builder.create();
		dialog.show();
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
	}
	
	@Override
	public void onStop() {
		super.onStop();
		MainActivity mainActivity = (MainActivity)getActivity();
		final InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Activity.INPUT_METHOD_SERVICE);
		imm.hideSoftInputFromWindow(getView().getWindowToken(), 0);
	}

	@Override
	public void onResume() {
		super.onResume();
		MainActivity mainActivity = (MainActivity)getActivity();
		mainActivity.showActionBar(true);
		mainActivity.showActionBarBackViews("Login");
		mainActivity.showActionBarCheckoutButton(false);
		mainActivity.showActionBarFilterButton(false);
		mainActivity.showActionBarSearchButton(false);
		mainActivity.showActionBarTitle(false);
		
		final InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Activity.INPUT_METHOD_SERVICE);
		imm.showSoftInputFromInputMethod(getView().getWindowToken(), 0);
	}
}
