package com.kyleduo.switchbutton.demo;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.widget.CompoundButton;

import com.kyleduo.switchbutton.SwitchButton;

public class StyleActivity extends ActionBarActivity {

	private SwitchButton mChangeSb, mFlymeSb, mMiuiSb;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_style);

		mChangeSb = (SwitchButton) findViewById(R.id.sb_code);
		mFlymeSb = (SwitchButton) findViewById(R.id.sb_custom_flyme);
		mMiuiSb = (SwitchButton) findViewById(R.id.sb_custom_miui);

		mChangeSb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				mFlymeSb.setEnabled(isChecked);
				mMiuiSb.setEnabled(isChecked);
			}
		});
	}

}
