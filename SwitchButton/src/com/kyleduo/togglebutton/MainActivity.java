package com.kyleduo.togglebutton;

import android.app.Activity;
import android.os.Bundle;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		

		/*if (savedInstanceState == null) {
			getSupportFragmentManager().beginTransaction().add(R.id.container, new PlaceholderFragment()).commit();
		}*/
//		ToggleButton toggleButton = (ToggleButton) findViewById(R.id.toggle);
//		toggleButton.setIsOn(true);
	}

}

