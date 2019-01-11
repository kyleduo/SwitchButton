package com.kyleduo.switchbutton.demo;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.CompoundButton;

import com.kyleduo.switchbutton.SwitchButton;

public class StyleActivity extends AppCompatActivity {

    private SwitchButton mFlymeSb, mMIUISb, mCustomSb, mDefaultSb, mSB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_style);

        SwitchButton disableSb = (SwitchButton) findViewById(R.id.sb_disable_control);
        SwitchButton disableNoEventSb = (SwitchButton) findViewById(R.id.sb_disable_control_no_event);
        mFlymeSb = (SwitchButton) findViewById(R.id.sb_custom_flyme);
        mMIUISb = (SwitchButton) findViewById(R.id.sb_custom_miui);
        mCustomSb = (SwitchButton) findViewById(R.id.sb_custom);
        mDefaultSb = (SwitchButton) findViewById(R.id.sb_default);
        mSB = (SwitchButton) findViewById(R.id.sb_ios);

        if (disableSb != null) {
            disableSb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    mFlymeSb.setEnabled(isChecked);
                    mMIUISb.setEnabled(isChecked);
                    mCustomSb.setEnabled(isChecked);
                    mDefaultSb.setEnabled(isChecked);
                    mSB.setEnabled(isChecked);
                }
            });
        }
        if (disableNoEventSb != null) {
            disableNoEventSb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    mFlymeSb.setEnabled(isChecked);
                    mMIUISb.setEnabled(isChecked);
                    mCustomSb.setEnabled(isChecked);
                    mDefaultSb.setEnabled(isChecked);
                    mSB.setEnabled(isChecked);
                }
            });
            disableNoEventSb.setCheckedImmediatelyNoEvent(false);
        }
    }

}
