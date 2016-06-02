package com.kyleduo.switchbutton.demo;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.kyleduo.switchbutton.SwitchButton;

public class UseActivity extends AppCompatActivity implements View.OnClickListener {

	private SwitchButton mListenerSb, mLongSb, mToggleSb, mCheckedSb, mDelaySb, mForceOpenSb, mForceOpenControlSb;
	private ProgressBar mPb;
	private Button mStartBt;
	private TextView mListenerFinish;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_use);

		findView();

		LinearLayout toggleWrapper = (LinearLayout) findViewById(R.id.toggle_wrapper);
		for (int i = 0; i < toggleWrapper.getChildCount(); i++) {
			toggleWrapper.getChildAt(i).setOnClickListener(this);
		}

		LinearLayout checkWrapper = (LinearLayout) findViewById(R.id.check_wrapper);
		for (int i = 0; i < checkWrapper.getChildCount(); i++) {
			checkWrapper.getChildAt(i).setOnClickListener(this);
		}


		// work with listener
		mListenerSb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				mListenerFinish.setVisibility(isChecked ? View.VISIBLE : View.INVISIBLE);
			}
		});

		// work with delay
		mDelaySb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				mDelaySb.setEnabled(false);
				mDelaySb.postDelayed(new Runnable() {
					@Override
					public void run() {
						mDelaySb.setEnabled(true);
					}
				}, 1500);
			}
		});

		// work with stuff takes long
		mStartBt.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				ObjectAnimator animator = ObjectAnimator.ofInt(mPb, "progress", 0, 1000);
				animator.setDuration(1000);
				animator.addListener(new Animator.AnimatorListener() {
					@Override
					public void onAnimationStart(Animator animation) {
						mStartBt.setEnabled(false);
						mLongSb.setChecked(false);
					}

					@Override
					public void onAnimationEnd(Animator animation) {
						mStartBt.setEnabled(true);
						mLongSb.setChecked(true);
					}

					@Override
					public void onAnimationCancel(Animator animation) {
						mStartBt.setEnabled(true);
					}

					@Override
					public void onAnimationRepeat(Animator animation) {

					}
				});
				animator.start();
			}
		});

		// check in check
		mForceOpenSb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				if (mForceOpenControlSb.isChecked()) {
					toast("Call mForceOpenSb.setChecked(true); in on CheckedChanged");
					mForceOpenSb.setChecked(true);
				}
			}
		});
	}

	private void findView() {
		mListenerSb = (SwitchButton) findViewById(R.id.sb_use_listener);
		mLongSb = (SwitchButton) findViewById(R.id.sb_use_long);
		mToggleSb = (SwitchButton) findViewById(R.id.sb_use_toggle);
		mCheckedSb = (SwitchButton) findViewById(R.id.sb_use_checked);
		mDelaySb = (SwitchButton) findViewById(R.id.sb_use_delay);

		mPb = (ProgressBar) findViewById(R.id.pb);
		mPb.setProgress(0);
		mPb.setMax(1000);

		mStartBt = (Button) findViewById(R.id.long_start);

		mListenerFinish = (TextView) findViewById(R.id.listener_finish);
		mListenerFinish.setVisibility(mListenerSb.isChecked() ? View.VISIBLE : View.INVISIBLE);

		mForceOpenSb = (SwitchButton) findViewById(R.id.use_focus_open);
		mForceOpenControlSb = (SwitchButton) findViewById(R.id.use_focus_open_control);

		mToggleSb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				toast("Toggle SwitchButton new check state: " + (isChecked ? "Checked" : "Unchecked"));
			}
		});

		mCheckedSb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				toast("Check SwitchButton new check state: " + (isChecked ? "Checked" : "Unchecked"));
			}
		});
	}

	@Override
	public void onClick(View v) {
		int id = v.getId();
		switch (id) {
			case R.id.toggle_ani:
				mToggleSb.toggle();
				break;
			case R.id.toggle_ani_no_event:
				mToggleSb.toggleNoEvent();
				break;
			case R.id.toggle_not_ani:
				mToggleSb.toggleImmediately();
				break;
			case R.id.toggle_not_ani_no_event:
				mToggleSb.toggleImmediatelyNoEvent();
				break;
			case R.id.checked_ani:
				mCheckedSb.setChecked(!mCheckedSb.isChecked());
				break;
			case R.id.checked_ani_no_event:
				mCheckedSb.setCheckedNoEvent(!mCheckedSb.isChecked());
				break;
			case R.id.checked_not_ani:
				mCheckedSb.setCheckedImmediately(!mCheckedSb.isChecked());
				break;
			case R.id.checked_not_ani_no_event:
				mCheckedSb.setCheckedImmediatelyNoEvent(!mCheckedSb.isChecked());
				break;
			default:
				break;
		}
	}

	private void toast(String text) {
		Toast.makeText(UseActivity.this, text, Toast.LENGTH_SHORT).show();
	}
}
