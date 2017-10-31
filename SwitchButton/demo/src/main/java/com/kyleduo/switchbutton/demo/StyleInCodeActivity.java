package com.kyleduo.switchbutton.demo;

import android.graphics.RectF;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.ImageSpan;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.kyleduo.switchbutton.SwitchButton;

public class StyleInCodeActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    private SwitchButton mChangeSb;
    private boolean mThumbMarginFlag, mThumbSizeFlag, mThumbRadiusFlag, mBackRadiusFlag, mBackMeasureRatioFlag, mAnimationDurationFlag;
    private String[] opts = new String[]{
            "setThumbColorRes/setThumbColor",
            "setThumbDrawableRes/setThumbDrawable",
            "setBackColorRes/setBackColor",
            "setBackDrawableRes/setBackDrawable",
            "setTintColor",
            "setThumbMargin",
            "setThumbSize",
            "setThumbRadius (color-mode only)",
            "setBackRadius (color-mode only)",
            "setFadeBack",
            "setThumbRangeRatio",
            "setAnimationDuration",
            "setText",
            "setDrawDebugRect",
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_style_in_code);

        mChangeSb = (SwitchButton) findViewById(R.id.sb_code);
        ListView optLv = (ListView) findViewById(R.id.opt_lv);

        optLv.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, android.R.id.text1, opts));
        optLv.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        switch (position) {
            case 0:
                mChangeSb.setThumbColorRes(R.color.custom_thumb_color);
                break;
            case 1:
                mChangeSb.setThumbDrawableRes(R.drawable.miui_thumb_drawable);
                break;
            case 2:
                mChangeSb.setBackColorRes(R.color.custom_back_color);
                break;
            case 3:
                mChangeSb.setBackDrawableRes(R.drawable.miui_back_drawable);
                break;
            case 4:
                mChangeSb.setTintColor(0x9F6C66);
                break;
            case 5: {
                float margin = 10 * getResources().getDisplayMetrics().density;
                float defaultMargin = SwitchButton.DEFAULT_THUMB_MARGIN_DP * getResources().getDisplayMetrics().density;
                mChangeSb.setThumbMargin(mThumbMarginFlag ? new RectF(defaultMargin, defaultMargin, defaultMargin, defaultMargin) : new RectF(margin, margin, margin, margin));
                mThumbMarginFlag = !mThumbMarginFlag;
            }
            break;
            case 6: {
                int size = (int) (30 * getResources().getDisplayMetrics().density);
                mChangeSb.setThumbSize(size, size);
                mThumbSizeFlag = !mThumbSizeFlag;
            }
            break;
            case 7: {
                float r = 2 * getResources().getDisplayMetrics().density;
                mChangeSb.setThumbRadius(mThumbRadiusFlag ? Math.min(mChangeSb.getThumbWidth(), mChangeSb.getThumbHeight()) / 2f : r);
                mThumbRadiusFlag = !mThumbRadiusFlag;
            }
            break;
            case 8: {
                float r = 2 * getResources().getDisplayMetrics().density;
                mChangeSb.setBackRadius(mBackRadiusFlag ? Math.min(mChangeSb.getBackSizeF().x, mChangeSb.getBackSizeF().y) / 2f : r);
                mBackRadiusFlag = !mBackRadiusFlag;
            }
            break;
            case 9:
                mChangeSb.setFadeBack(!mChangeSb.isFadeBack());
                break;
            case 10:
                mChangeSb.setThumbRangeRatio(mBackMeasureRatioFlag ? SwitchButton.DEFAULT_THUMB_RANGE_RATIO : 4f);
                mBackMeasureRatioFlag = !mBackMeasureRatioFlag;
                break;
            case 11:
                mChangeSb.setAnimationDuration(mAnimationDurationFlag ? SwitchButton.DEFAULT_ANIMATION_DURATION : 1000);
                mAnimationDurationFlag = !mAnimationDurationFlag;
                break;
            case 12: {
                CharSequence on = mChangeSb.getTextOn();
                CharSequence off = mChangeSb.getTextOff();
                if (TextUtils.isEmpty(on) || TextUtils.isEmpty(off)) {
                    SpannableString ss = new SpannableString("[icon]");
                    ImageSpan span = new ImageSpan(this, R.drawable.icon_blog_small, ImageSpan.ALIGN_BOTTOM);
                    ss.setSpan(span, 0, ss.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                    mChangeSb.setText(ss, "OFF");
                    mChangeSb.setTextExtra((int) (getResources().getDisplayMetrics().density * 4));
                } else {
                    mChangeSb.setText("", "");
                }
            }
            break;
            case 13:
                mChangeSb.setDrawDebugRect(!mChangeSb.isDrawDebugRect());
                break;
            default:
                break;
        }
    }
}
