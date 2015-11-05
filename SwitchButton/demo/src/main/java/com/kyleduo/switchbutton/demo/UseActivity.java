package com.kyleduo.switchbutton.demo;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.kyleduo.switchbutton.SwitchButton;

public class UseActivity extends AppCompatActivity implements View.OnClickListener {

	private SwitchButton mListenerSb, mLongSb, mToggleSb, mCheckedSb, mDelaySb;
	private ProgressBar mPb;
	private Button mStartBt, mToggleAniBt, mToggleNotAniBt, mCheckedAniBt, mCheckNotAniBt;
	private TextView mListenerFinish;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_use);

		findView();

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

		// toggle
		mToggleAniBt.setOnClickListener(this);
		mToggleNotAniBt.setOnClickListener(this);

		// checked
		mCheckedAniBt.setOnClickListener(this);
		mCheckNotAniBt.setOnClickListener(this);
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
		mToggleAniBt = (Button) findViewById(R.id.toggle_ani);
		mToggleNotAniBt = (Button) findViewById(R.id.toggle_not_ani);
		mCheckedAniBt = (Button) findViewById(R.id.checked_ani);
		mCheckNotAniBt = (Button) findViewById(R.id.checked_not_ani);

		mListenerFinish = (TextView) findViewById(R.id.listener_finish);
		mListenerFinish.setVisibility(mListenerSb.isChecked() ? View.VISIBLE : View.INVISIBLE);
	}

	@Override
	public void onClick(View v) {
		int id = v.getId();
		switch (id) {
			case R.id.toggle_ani:
				mToggleSb.toggle();
				break;
			case R.id.toggle_not_ani:
				mToggleSb.toggleImmediately();
				break;
			case R.id.checked_ani:
				mCheckedSb.setChecked(!mCheckedSb.isChecked());
				break;
			case R.id.checked_not_ani:
				mCheckedSb.setCheckedImmediately(!mCheckedSb.isChecked());
				break;

			default:
				break;
		}
	}
}
