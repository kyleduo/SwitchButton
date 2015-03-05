package com.kyleduo.switchbuttondemo;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.kyleduo.switchbutton.SwitchButton;

public class UseActivity extends Activity implements OnClickListener {

	private SwitchButton mListenerSb, mLongSb, mToggleSb, mCheckedSb;
	private ProgressBar mPb;
	private Button mStartBt, mToggleAniBt, mToggleNotAniBt, mCheckedAniBt, mCheckNotAniBt;
	private TextView mListenerFinish;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_use);
		getActionBar().setDisplayHomeAsUpEnabled(true);

		findView();

		// work with listener
		mListenerSb.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				mListenerFinish.setVisibility(isChecked ? View.VISIBLE : View.INVISIBLE);
			}
		});

		// work with stuff takes long
		mStartBt.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				new LongTask().execute();
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

		mPb = (ProgressBar) findViewById(R.id.pb);
		mPb.setProgress(0);
		mPb.setMax(100);

		mStartBt = (Button) findViewById(R.id.long_start);
		mToggleAniBt = (Button) findViewById(R.id.toggle_ani);
		mToggleNotAniBt = (Button) findViewById(R.id.toggle_not_ani);
		mCheckedAniBt = (Button) findViewById(R.id.checked_ani);
		mCheckNotAniBt = (Button) findViewById(R.id.checked_not_ani);

		mListenerFinish = (TextView) findViewById(R.id.listener_finish);
		mListenerFinish.setVisibility(mListenerSb.isChecked() ? View.VISIBLE : View.INVISIBLE);
	}

	class LongTask extends AsyncTask<Void, Integer, Void> {

		private int progress = 0;

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			mLongSb.setChecked(false);
			mStartBt.setEnabled(false);
		}

		@Override
		protected Void doInBackground(Void... params) {
			while (progress < 100) {
				progress++;
				try {
					Thread.sleep(20);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				publishProgress(progress);
			}
			return null;
		}

		@Override
		protected void onProgressUpdate(Integer... values) {
			super.onProgressUpdate(values);
			if (values == null || values.length == 0) {
				return;
			}
			int p = values[0];
			mPb.setProgress(p);
		}

		@Override
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);
			mLongSb.slideToChecked(true);
			mStartBt.setEnabled(true);
		}

	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getItemId() == android.R.id.home) {
			finish();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onClick(View v) {
		int id = v.getId();
		switch (id) {
		case R.id.toggle_ani:
			mToggleSb.toggle();
			break;
		case R.id.toggle_not_ani:
			mToggleSb.toggle(false);
			break;
		case R.id.checked_ani:
			mCheckedSb.slideToChecked(!mCheckedSb.isChecked());
			break;
		case R.id.checked_not_ani:
			mCheckedSb.setChecked(!mCheckedSb.isChecked());
			break;

		default:
			break;
		}
	}
}
