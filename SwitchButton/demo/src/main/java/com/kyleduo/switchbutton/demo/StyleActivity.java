package com.kyleduo.switchbutton.demo;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;

import com.kyleduo.switchbutton.SwitchButton;

public class StyleActivity extends ActionBarActivity {

	private SwitchButton mChangeSb;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_style);

		mChangeSb = (SwitchButton) findViewById(R.id.sb_code);
	}

}
