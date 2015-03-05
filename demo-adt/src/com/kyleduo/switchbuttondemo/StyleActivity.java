package com.kyleduo.switchbuttondemo;

import android.app.Activity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.Toast;

import com.kyleduo.switchbutton.Configuration;
import com.kyleduo.switchbutton.SwitchButton;

public class StyleActivity extends Activity {

	private SwitchButton sbDefault, sbChangeFaceControl, sbIOS, sbInCode, sbEnable, sbMd;
	private SwitchButton[] sbs = new SwitchButton[5];
	private ViewGroup container;
	private boolean newConf = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_style);
		getActionBar().setDisplayHomeAsUpEnabled(true);

		container = (ViewGroup) findViewById(R.id.incode_container);

		sbDefault = (SwitchButton) findViewById(R.id.sb_default);
		sbIOS = (SwitchButton) findViewById(R.id.sb_ios);
		sbChangeFaceControl = (SwitchButton) findViewById(R.id.sb_changeface_control);
		sbEnable = (SwitchButton) findViewById(R.id.sb_enable);
		sbMd = (SwitchButton) findViewById(R.id.sb_md);

		sbInCode = new SwitchButton(this);
		container.addView(sbInCode, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

		sbs[0] = sbDefault;
		sbs[1] = sbIOS;
		sbs[2] = sbChangeFaceControl;
		sbs[3] = sbInCode;
		sbs[4] = sbMd;

		sbDefault.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				Toast.makeText(StyleActivity.this, "Default style button, new state: " + (isChecked ? "on" : "off"), Toast.LENGTH_SHORT).show();
			}
		});

		sbChangeFaceControl.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				if (!newConf) {
					Configuration conf = Configuration.getDefault(getResources().getDisplayMetrics().density);
					conf.setThumbMargin(2);
					conf.setVelocity(8);
					conf.setThumbWidthAndHeight(24, 14);
					conf.setRadius(6);
					conf.setMeasureFactor(2f);
					sbInCode.setConfiguration(conf);
				} else {
					Configuration conf = Configuration.getDefault(getResources().getDisplayMetrics().density);
					sbInCode.setConfiguration(conf);
				}
				newConf = isChecked;
			}
		});

		sbEnable.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				for (SwitchButton sb : sbs) {
					sb.setEnabled(isChecked);
				}
			}
		});

		sbEnable.setChecked(true);
	}

	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item) {
		if (item.getItemId() == android.R.id.home) {
			finish();
			return true;
		}
		return super.onMenuItemSelected(featureId, item);
	}
}
