package com.kyleduo.switchbuttondemo;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class MainActivity extends Activity implements OnItemClickListener {

	private ListView mListView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		mListView = (ListView) findViewById(R.id.list);
		mListView.setOnItemClickListener(this);
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

	private void jumpToStyle() {
		startActivity(new Intent(this, StyleActivity.class));
	}

	private void jumpToUse() {
		startActivity(new Intent(this, UseActivity.class));
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		switch (position) {
		case 0:
			jumpToStyle();
			break;

		case 1:
			jumpToUse();
			break;

		default:
			break;
		}
	}

}
