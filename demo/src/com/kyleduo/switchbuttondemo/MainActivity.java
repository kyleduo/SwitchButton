package com.kyleduo.switchbuttondemo;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;

import com.kyleduo.switchbutton.Configuration;
import com.kyleduo.switchbutton.SwitchButton;

public class MainActivity extends Activity {

	private Button btChangeFace, btEnable;
	private SwitchButton sbInCode, sbEnable;
	private ViewGroup container;
	private boolean newConf = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		btChangeFace = (Button) findViewById(R.id.change_face);
		btEnable = (Button) findViewById(R.id.bt_enable);
		container = (ViewGroup) findViewById(R.id.incode_container);
		sbEnable = (SwitchButton) findViewById(R.id.sb_enable);

		sbInCode = new SwitchButton(this);
		container.addView(sbInCode, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

		btChangeFace.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (!newConf) {
					Configuration conf = Configuration.getDefault(getResources().getDisplayMetrics().density);
					conf.setThumbMargin(3);
					conf.setVelocity(8);
					conf.setThumbWidthAndHeight(30, 20);
					conf.setThumbColor(Color.RED);
					sbInCode.setConfiguration(conf);
					newConf = true;
				}
			}
		});

		btEnable.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				sbEnable.setEnabled(!sbEnable.isEnabled());
			}
		});

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
