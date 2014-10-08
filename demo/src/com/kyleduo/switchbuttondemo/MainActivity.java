package com.kyleduo.switchbuttondemo;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.Toast;

import com.kyleduo.switchbutton.Configuration;
import com.kyleduo.switchbutton.SwitchButton;

public class MainActivity extends Activity {

	private SwitchButton sbDefault, sbCustom, sbImage, sbChangeFaceControl, sbIOS, sbInCode, sbEnable;
	private SwitchButton[] sbs = new SwitchButton[6];
	private ViewGroup container;
	private boolean newConf = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		container = (ViewGroup) findViewById(R.id.incode_container);

		sbDefault = (SwitchButton) findViewById(R.id.sb_default);
		sbCustom = (SwitchButton) findViewById(R.id.sb_custom);
		sbImage = (SwitchButton) findViewById(R.id.sb_image);
		sbIOS = (SwitchButton) findViewById(R.id.sb_ios);
		sbChangeFaceControl = (SwitchButton) findViewById(R.id.sb_changeface_control);
		sbEnable = (SwitchButton) findViewById(R.id.sb_enable);

		sbInCode = new SwitchButton(this);
		container.addView(sbInCode, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

		sbs[0] = sbDefault;
		sbs[1] = sbCustom;
		sbs[2] = sbImage;
		sbs[3] = sbIOS;
		sbs[4] = sbChangeFaceControl;
		sbs[5] = sbInCode;

		sbDefault.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				Toast.makeText(MainActivity.this, "Default style button, new state: " + (isChecked ? "on" : "off"), Toast.LENGTH_SHORT).show();
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
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		Intent intent = new Intent(Intent.ACTION_VIEW);
		int id = item.getItemId();
		switch (id) {
		case R.id.action_github:
			intent.setData(Uri.parse("https://github.com/kyleduo/SwitchButton"));
			startActivity(intent);
			return true;
		case R.id.action_blog:
			intent.setData(Uri.parse("http://www.kyleduo.com"));
			startActivity(intent);
			return true;
		default:
			break;
		}
		return super.onOptionsItemSelected(item);
	}
}
