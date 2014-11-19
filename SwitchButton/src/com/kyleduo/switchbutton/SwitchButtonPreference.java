package com.kyleduo.switchbutton;

import android.content.Context;
import android.os.Build;
import android.preference.CheckBoxPreference;
import android.util.AttributeSet;

public class SwitchButtonPreference extends CheckBoxPreference {

    public SwitchButtonPreference(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public SwitchButtonPreference(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    public SwitchButtonPreference(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public SwitchButtonPreference(Context context) {
        super(context);
        init();
    }

    private void init() {
        setWidgetLayoutResource(R.layout.preference_switch_button_layout);
    }
}
