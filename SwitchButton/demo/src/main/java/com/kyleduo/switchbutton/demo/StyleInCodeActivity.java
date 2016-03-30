package com.kyleduo.switchbutton.demo;

import android.graphics.PointF;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.style.ImageSpan;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.kyleduo.switchbutton.SwitchButton;

public class StyleInCodeActivity extends ActionBarActivity implements AdapterView.OnItemClickListener {

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
			"setBackMeasureRatio",
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
				float size = 30 * getResources().getDisplayMetrics().density;
				mChangeSb.setThumbSize(mThumbSizeFlag ? null : new PointF(size, size));
				mThumbSizeFlag = !mThumbSizeFlag;
			}
			break;
			case 7: {
				float r = 2 * getResources().getDisplayMetrics().density;
				mChangeSb.setThumbRadius(mThumbRadiusFlag ? Math.min(mChangeSb.getThumbSizeF().x, mChangeSb.getThumbSizeF().y) / 2f : r);
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
				mChangeSb.setBackMeasureRatio(mBackMeasureRatioFlag ? SwitchButton.DEFAULT_BACK_MEASURE_RATIO : 2.4f);
				mBackMeasureRatioFlag = !mBackMeasureRatioFlag;
				break;
			case 11:
				mChangeSb.setAnimationDuration(mAnimationDurationFlag ? SwitchButton.DEFAULT_ANIMATION_DURATION : 1000);
				mAnimationDurationFlag = !mAnimationDurationFlag;
				break;
			case 12: {
				SpannableString ss = new SpannableString("abc");
				Drawable d = getResources().getDrawable(R.drawable.icon_blog);
				if (d != null) {
					d.setBounds(0, d.getIntrinsicWidth() / 4, d.getIntrinsicWidth() / 2, d.getIntrinsicHeight() * 3 / 4);
					ImageSpan span = new ImageSpan(d, ImageSpan.ALIGN_BASELINE);
					ss.setSpan(span, 0, 3, Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
					mChangeSb.setText(ss, "OFF");
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
