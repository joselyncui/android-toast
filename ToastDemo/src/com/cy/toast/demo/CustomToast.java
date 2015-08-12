package com.cy.toast.demo;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.PixelFormat;
import android.os.Handler;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

public class CustomToast {
	private WindowManager wdm;
	private double time;
	private View mView;
	private WindowManager.LayoutParams params;
	private Handler mHandler;
	private Runnable mRun;
	private boolean isAttach = false;

	@SuppressLint("ShowToast")
	private CustomToast(Context context, String text, double time) {
		wdm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
		this.mHandler = new Handler();
		
		mRun = new MRunnable();
		Toast toast = Toast.makeText(context, text, Toast.LENGTH_SHORT);
		mView = toast.getView();

		params = new WindowManager.LayoutParams();
		params.height = WindowManager.LayoutParams.WRAP_CONTENT;
		params.width = WindowManager.LayoutParams.WRAP_CONTENT;
		params.format = PixelFormat.TRANSLUCENT;
		params.windowAnimations = android.R.style.Animation_Toast;;
		params.type = WindowManager.LayoutParams.TYPE_TOAST;
		params.setTitle("Toast");
		params.flags = WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON
				| WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
				| WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE;
		params.gravity = Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL;
		params.y = 200;
		this.time = time;
	}

	public static CustomToast makeText(Context context, String text, double time) {
		CustomToast toastCustom = new CustomToast(context, text, time);
		return toastCustom;
	}

	public void show() {
		if(isAttach) 
			return;
		isAttach = true;
		wdm.addView(mView, params);
		mHandler.postDelayed(mRun, (long)time);
	}

	public void cancel() {
		if(isAttach) {
			wdm.removeView(mView);
			isAttach = false;
		}
		mHandler.removeCallbacks(mRun);
	}
	
	class MRunnable implements Runnable{

		@Override
		public void run() {
			isAttach = false;
			wdm.removeView(mView);
		}
		
	}
}
