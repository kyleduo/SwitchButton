package com.kyleduo.switchbutton;

import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SoundEffectConstants;
import android.view.ViewConfiguration;
import android.view.ViewParent;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.CompoundButton;


/**
 * SwitchButton widget which is easy to use
 *
 * @author kyleduo
 * @version 1.2
 * @since 2014-09-24
 */

public class SwitchButton extends CompoundButton {
	public static final float DEFAULT_BACK_MEASURE_RATIO = 1.8f;
	public static final int DEFAULT_THUMB_SIZE = 20;
	public static final long DEFAULT_ANIMATION_DURATION = 250l;
	static int times = 0;
	private static int[] CHECKED_PRESSED_STATE = new int[]{android.R.attr.state_checked, android.R.attr.state_enabled, android.R.attr.state_pressed};
	private static int[] UNCHECKED_PRESSED_STATE = new int[]{-android.R.attr.state_checked, android.R.attr.state_enabled, android.R.attr.state_pressed};

	private PointF mThumbSizeF;
	private RectF mThumbRectF, mBackRectF, mSafeRectF;
	// 标记是否使用Drawable，如果不用Drawable，使用Color定制，在onDraw中直接绘制
	private boolean mIsThumbUseDrawable, mIsBackUseDrawable;
	private Drawable mThumbDrawable, mBackDrawable;
	private ColorStateList mBackColor, mThumbColor;
	private int mCurrThumbColor, mCurrBackColor;
	private int mNextBackColor;
	private float mThumbRadius, mBackRadius;
	private Paint mPaint;
	// save thumbMargin
	private RectF mThumbMargin;
	// exp. 实验功能，绘制文本
	private TextPaint mTextPaint;
	private String mOnText, mOffText;
	private float mBackMeasureRatio;
	private float mAnimationVelocity;
	/**
	 * 是否在滑动的时候渐隐back
	 */
	private boolean mFadeBack;
	private boolean mDrawDebugRect = false;
	private ObjectAnimator mThumbProcessAnimator;
	/**
	 * 动画进度
	 */
	private float mProcess;
	/**
	 * 存储动画过程中的临时位置
	 */
	private RectF mPresentThumbRectF;
	private float mStartX, mStartY, mLastX;
	private float mCenterPos;
	private int mTouchSlop;
	private int mClickTimeout;
	private Paint mRectPaint;
	private Rect mBounds = null;
	private CompoundButton.OnCheckedChangeListener mOnCheckedChangeListener;

	@SuppressLint("NewApi")
	public SwitchButton(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init(attrs);

//		TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.SwitchButton);

//		mConf.setThumbMarginInPixel(ta.getDimensionPixelSize(R.styleable.SwitchButton_kswThumb_margin, mConf.getDefaultThumbMarginInPixel()));
//		mConf.setThumbMarginInPixel(ta.getDimensionPixelSize(R.styleable.SwitchButton_kswThumb_marginTop, mConf.getThumbMarginTop()),
//				ta.getDimensionPixelSize(R.styleable.SwitchButton_kswThumb_marginBottom, mConf.getThumbMarginBottom()),
//				ta.getDimensionPixelSize(R.styleable.SwitchButton_kswThumb_marginLeft, mConf.getThumbMarginLeft()),
//				ta.getDimensionPixelSize(R.styleable.SwitchButton_kswThumb_marginRight, mConf.getThumbMarginRight()));
//		mConf.setRadius(ta.getInt(R.styleable.SwitchButton_kswRadius, Configuration.Default.DEFAULT_RADIUS));
//
//		mConf.setThumbWidthAndHeightInPixel(ta.getDimensionPixelSize(R.styleable.SwitchButton_kswThumb_width, -1), ta.getDimensionPixelSize(R.styleable.SwitchButton_kswThumb_height, -1));
//
//		mConf.setMeasureFactor(ta.getFloat(R.styleable.SwitchButton_kswMeasureFactor, -1));
//
//		mConf.setInsetBounds(ta.getDimensionPixelSize(R.styleable.SwitchButton_kswInsetLeft, 0), ta.getDimensionPixelSize(R.styleable.SwitchButton_kswInsetTop, 0),
//				ta.getDimensionPixelSize(R.styleable.SwitchButton_kswInsetRight, 0), ta.getDimensionPixelSize(R.styleable.SwitchButton_kswInsetBottom, 0));
//
//		int velocity = ta.getInteger(R.styleable.SwitchButton_kswAnimationVelocity, -1);
//		mAnimationController.setVelocity(velocity);
//
//		fetchDrawableFromAttr(ta);
//		ta.recycle();
//
//		if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.HONEYCOMB) {
//			this.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
//		}
	}

	public SwitchButton(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(attrs);
	}


	public SwitchButton(Context context) {
		super(context);
		init(null);
	}

	private void init(AttributeSet attrs) {
		// TODO(kyleduo): 15/10/25 initialization
		mTouchSlop = ViewConfiguration.get(getContext()).getScaledTouchSlop();
		mClickTimeout = ViewConfiguration.getPressedStateDuration() + ViewConfiguration.getTapTimeout();

		mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		mRectPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		mRectPaint.setStyle(Paint.Style.STROKE);
		mRectPaint.setStrokeWidth(getResources().getDisplayMetrics().density);

		mThumbRectF = new RectF();
		mBackRectF = new RectF();
		mSafeRectF = new RectF();
		mThumbSizeF = new PointF();
		mThumbMargin = new RectF();

		mThumbProcessAnimator = ObjectAnimator.ofFloat(this, "process", 0, 0).setDuration(DEFAULT_ANIMATION_DURATION);
		mThumbProcessAnimator.setInterpolator(new AccelerateDecelerateInterpolator());

		mPresentThumbRectF = new RectF();

		Resources res = getResources();
		float density = res.getDisplayMetrics().density;

		Drawable thumbDrawable = null;
		ColorStateList thumbColor = null;
		float margin = density * 2;
		float marginLeft = 0;
		float marginRight = 0;
		float marginTop = 0;
		float marginBottom = 0;
		float thumbWidth = density * DEFAULT_THUMB_SIZE;
		float thumbHeight = density * DEFAULT_THUMB_SIZE;
		float thumbRadius = density * DEFAULT_THUMB_SIZE / 2;
		float backRadius = thumbRadius + density * 2;
		Log.d("radius", "thumbR: " + thumbRadius + "  backRadiusR: " + backRadius);
		Drawable backDrawable = null;
		ColorStateList backColor = null;
		float backMeasureRatio = DEFAULT_BACK_MEASURE_RATIO;
		float animationVelocity = 0.5f;
		boolean fadeBack = true;

		TypedArray ta = attrs == null ? null : getContext().obtainStyledAttributes(attrs, R.styleable.SwitchButton);
		if (ta != null) {
			thumbDrawable = ta.getDrawable(R.styleable.SwitchButton_kswThumbDrawable);
			thumbColor = ta.getColorStateList(R.styleable.SwitchButton_kswThumbColor);
			margin = ta.getDimension(R.styleable.SwitchButton_kswThumbMargin, margin);
			marginLeft = ta.getDimension(R.styleable.SwitchButton_kswThumbMarginLeft, margin);
			marginRight = ta.getDimension(R.styleable.SwitchButton_kswThumbMarginRight, margin);
			marginTop = ta.getDimension(R.styleable.SwitchButton_kswThumbMarginTop, margin);
			marginBottom = ta.getDimension(R.styleable.SwitchButton_kswThumbMarginBottom, margin);
			thumbWidth = ta.getDimension(R.styleable.SwitchButton_kswThumbWidth, thumbWidth);
			thumbHeight = ta.getDimension(R.styleable.SwitchButton_kswThumbHeight, thumbHeight);
			thumbRadius = ta.getFloat(R.styleable.SwitchButton_kswThumbRadius, Math.min(thumbWidth, thumbHeight) / 2.f);
			backRadius = ta.getFloat(R.styleable.SwitchButton_kswBackRadius, backRadius);
			backDrawable = ta.getDrawable(R.styleable.SwitchButton_kswBackDrawable);
			backColor = ta.getColorStateList(R.styleable.SwitchButton_kswBackColor);
			backMeasureRatio = ta.getFloat(R.styleable.SwitchButton_kswBackMeasureRatio, backMeasureRatio);
			animationVelocity = ta.getFloat(R.styleable.SwitchButton_kswAnimationVelocity, animationVelocity);
			fadeBack = ta.getBoolean(R.styleable.SwitchButton_kswFadeBack, true);
			ta.recycle();
		}

		// thumb drawable and color
		mThumbDrawable = thumbDrawable;
		mThumbColor = thumbColor;
		mIsThumbUseDrawable = mThumbDrawable != null;
		if (!mIsThumbUseDrawable && mThumbColor == null) {
			mThumbColor = ContextCompat.getColorStateList(getContext(), R.color.default_thumb_color);
			mCurrThumbColor = mThumbColor.getDefaultColor();
		}

		// back drawable and color
		mBackDrawable = backDrawable;
		mBackColor = backColor;
		mIsBackUseDrawable = mBackDrawable != null;
		if (!mIsBackUseDrawable && mBackColor == null) {
			mBackColor = ContextCompat.getColorStateList(getContext(), R.color.default_back_color);
			mCurrBackColor = mBackColor.getDefaultColor();
			mNextBackColor = mBackColor.getColorForState(CHECKED_PRESSED_STATE, mCurrBackColor);
		}
		// margin
		mThumbMargin.set(marginLeft, marginTop, marginRight, marginBottom);

		// size & measure params must larger than 1
		mBackMeasureRatio = mThumbMargin.width() >= 0 ? Math.max(backMeasureRatio, 1) : backMeasureRatio;
		if (mIsThumbUseDrawable) {
			thumbWidth = Math.max(thumbWidth, mThumbDrawable.getMinimumWidth());
			thumbHeight = Math.max(thumbHeight, mThumbDrawable.getMinimumHeight());
		}
		mThumbSizeF.set(thumbWidth, thumbHeight);

		mThumbRadius = thumbRadius;
		mBackRadius = backRadius;
		mAnimationVelocity = animationVelocity;
		mFadeBack = fadeBack;

		setFocusable(true);
		setClickable(true);
		setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				Log.e("onCheckedChanged", ++times + " times " + isChecked);
			}
		});
		if (isChecked()) {
			setProcess(1);
		}
	}
/*
	private void initView() {
//		mConf = Configuration.getDefault(getContext().getResources().getDisplayMetrics().density);
		mTouchSlop = ViewConfiguration.get(getContext()).getScaledTouchSlop();
		mClickTimeout = ViewConfiguration.getPressedStateDuration() + ViewConfiguration.getTapTimeout();
		mAnimationController = AnimationController.getDefault().init(mOnAnimateListener);
		mBounds = new Rect();
		if (SHOW_RECT) {
			mRectPaint = new Paint();
			mRectPaint.setStyle(Style.STROKE);
		}
	}*/

	/**
	 * fetch drawable resources from attrs, drop them to conf, AFTER the size
	 * has been confirmed
	 *
	 * @param ta
	 */
/*
	private void fetchDrawableFromAttr(TypedArray ta) {
//		if (mConf == null) {
//			return;
//		}
//		mConf.setOffDrawable(fetchDrawable(ta, R.styleable.SwitchButton_kswOffDrawable, R.styleable.SwitchButton_kswOffColor, Configuration.Default.DEFAULT_OFF_COLOR));
//		mConf.setOnDrawable(fetchDrawable(ta, R.styleable.SwitchButton_kswOnDrawable, R.styleable.SwitchButton_kswOnColor, Configuration.Default.DEFAULT_ON_COLOR));
//		mConf.setThumbDrawable(fetchThumbDrawable(ta));
	}

	private Drawable fetchDrawable(TypedArray ta, int attrId, int alterColorId, int defaultColor) {
		Drawable tempDrawable = ta.getDrawable(attrId);
//		if (tempDrawable == null) {
//			int tempColor = ta.getColor(alterColorId, defaultColor);
//			tempDrawable = new GradientDrawable();
//			((GradientDrawable) tempDrawable).setCornerRadius(this.mConf.getRadius());
//			((GradientDrawable) tempDrawable).setColor(tempColor);
//		}
		return tempDrawable;
	}
*/

//	private Drawable fetchThumbDrawable(TypedArray ta) {
//
//		Drawable tempDrawable = ta.getDrawable(R.styleable.SwitchButton_kswThumbDrawable);
//		if (tempDrawable != null) {
//			return tempDrawable;
//		}
//
//		int normalColor = ta.getColor(R.styleable.SwitchButton_kswThumbColor, Configuration.Default.DEFAULT_THUMB_COLOR);
//		int pressedColor = ta.getColor(R.styleable.SwitchButton_kswThumbPressedColor, Configuration.Default.DEFAULT_THUMB_PRESSED_COLOR);
//
//		StateListDrawable drawable = new StateListDrawable();
//		GradientDrawable normalDrawable = new GradientDrawable();
//		normalDrawable.setCornerRadius(this.mConf.getRadius());
//		normalDrawable.setColor(normalColor);
//		GradientDrawable pressedDrawable = new GradientDrawable();
//		pressedDrawable.setCornerRadius(this.mConf.getRadius());
//		pressedDrawable.setColor(pressedColor);
//		drawable.addState(View.PRESSED_ENABLED_STATE_SET, pressedDrawable);
//		drawable.addState(new int[]{}, normalDrawable);
//
//		return drawable;
//	}

	/**
	 * return a REFERENCE of configuration, it is suggested that not to change that
	 *
	 * @return
	 */
//	public Configuration getConfiguration() {
//		return mConf;
//	}
/*	public void setConfiguration(Configuration conf) {
//		if (mConf == null) {
//			mConf = Configuration.getDefault(conf.getDensity());
//		}
//		mConf.setOffDrawable(conf.getOffDrawableWithFix());
//		mConf.setOnDrawable(conf.getOnDrawableWithFix());
//		mConf.setThumbDrawable(conf.getThumbDrawableWithFix());
//		mConf.setThumbMarginInPixel(conf.getThumbMarginTop(), conf.getThumbMarginBottom(), conf.getThumbMarginLeft(), conf.getThumbMarginRight());
//		mConf.setThumbWidthAndHeightInPixel(conf.getThumbWidth(), conf.getThumbHeight());
//		mConf.setVelocity(conf.getVelocity());
//		mConf.setMeasureFactor(conf.getMeasureFactor());
//		mAnimationController.setVelocity(mConf.getVelocity());
		this.requestLayout();
		setup();
//		setChecked(mIsChecked);
	}*/

//	@Override
//	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//		setMeasuredDimension(measureWidth(widthMeasureSpec), measureHeight(heightMeasureSpec));
//	}
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		Log.e("ss", "onMeasure");
		setMeasuredDimension(measureWidth(widthMeasureSpec), measureHeight(heightMeasureSpec));
	}

	private int measureWidth(int widthMeasureSpec) {
		int widthSize = MeasureSpec.getSize(widthMeasureSpec);
		int widthMode = MeasureSpec.getMode(widthMeasureSpec);
		int measuredWidth;

		int minWidth = (int) (mThumbSizeF.x * mBackMeasureRatio);
		if (mIsBackUseDrawable) {
			minWidth = Math.max(minWidth, mBackDrawable.getMinimumWidth());
		}
		minWidth = Math.max(minWidth, (int) (minWidth + mThumbMargin.left + mThumbMargin.right));
		minWidth = Math.max(minWidth, minWidth + getPaddingLeft() + getPaddingRight());
		minWidth = Math.max(minWidth, getSuggestedMinimumWidth());

		if (widthMode == MeasureSpec.EXACTLY) {
			measuredWidth = Math.max(minWidth, widthSize);
		} else {
			measuredWidth = minWidth;
			if (widthMode == MeasureSpec.AT_MOST) {
				measuredWidth = Math.min(measuredWidth, widthSize);
			}
		}

		return measuredWidth;
	}

	private int measureHeight(int heightMeasureSpec) {
		int heightMode = MeasureSpec.getMode(heightMeasureSpec);
		int heightSize = MeasureSpec.getSize(heightMeasureSpec);
		int measuredHeight;

		int minHeight = (int) Math.max(mThumbSizeF.y, mThumbSizeF.y + mThumbMargin.top + mThumbMargin.right);
		minHeight = Math.max(minHeight, getSuggestedMinimumHeight());
		minHeight = Math.max(minHeight, minHeight + getPaddingTop() + getPaddingBottom());

		if (heightMode == MeasureSpec.EXACTLY) {
			measuredHeight = Math.max(minHeight, heightSize);
		} else {
			measuredHeight = minHeight;
			if (heightMode == MeasureSpec.AT_MOST) {
				measuredHeight = Math.min(measuredHeight, heightSize);
			}
		}

		return measuredHeight;
	}

	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		super.onSizeChanged(w, h, oldw, oldh);
		if (w != oldw || h != oldh) {
			setup();
		}
	}

	/**
	 * set up the rect of back and thumb
	 */
	private void setup() {
		float thumbTop = getPaddingTop() + Math.max(0, mThumbMargin.top);
		float thumbLeft = getPaddingLeft() + Math.max(0, mThumbMargin.left);
		mThumbRectF.set(thumbLeft, thumbTop, thumbLeft + mThumbSizeF.x, thumbTop + mThumbSizeF.y);

		float backLeft = mThumbRectF.left - mThumbMargin.left;
		mBackRectF.set(backLeft, mThumbRectF.top - mThumbMargin.top, backLeft + mThumbMargin.left + mThumbSizeF.x * mBackMeasureRatio + mThumbMargin.right, mThumbRectF.bottom + mThumbMargin.bottom);

		Log.d("setup", "thumbRect: " + mThumbRectF + " backRect: " + mBackRectF);

		mSafeRectF.set(mThumbRectF.left, 0, mBackRectF.right - mThumbMargin.right - mThumbRectF.width(), 0);

		mBackRadius = Math.min(mBackRectF.width(), mBackRectF.height()) / 2.f;

//		setupBackZone();
//		setupSafeZone();
//		setupThumbZone();
//
//		setupDrawableBounds();
//		if (this.getMeasuredWidth() > 0 && this.getMeasuredHeight() > 0) {
//			mSaveLayerZone = new RectF(0, 0, this.getMeasuredWidth(), this.getMeasuredHeight());
//		}
//
//		ViewGroup parent = (ViewGroup) this.getParent();
//		if (parent != null) {
//			parent.setClipChildren(false);
//		}
	}

	/**
	 * setup zone for thumb to move
	 */
//	private void setupSafeZone() {
//		int w = getMeasuredWidth();
//		int h = getMeasuredHeight();
//		if (w > 0 && h > 0) {
//			if (mSafeZone == null) {
//				mSafeZone = new Rect();
//			}
//			int left, right, top, bottom;
//			left = getPaddingLeft() + (mConf.getThumbMarginLeft() > 0 ? mConf.getThumbMarginLeft() : 0);
//			right = w - getPaddingRight() - (mConf.getThumbMarginRight() > 0 ? mConf.getThumbMarginRight() : 0) + (-mConf.getShrinkX());
//			top = getPaddingTop() + (mConf.getThumbMarginTop() > 0 ? mConf.getThumbMarginTop() : 0);
//			bottom = h - getPaddingBottom() - (mConf.getThumbMarginBottom() > 0 ? mConf.getThumbMarginBottom() : 0) + (-mConf.getShrinkY());
//			mSafeZone.set(left, top, right, bottom);
//
//			mCenterPos = mSafeZone.left + (mSafeZone.right - mSafeZone.left - mConf.getThumbWidth()) / 2;
//		} else {
//			mSafeZone = null;
//		}
//	}
//
//	private void setupBackZone() {
//		int w = getMeasuredWidth();
//		int h = getMeasuredHeight();
//		if (w > 0 && h > 0) {
//			if (mBackZone == null) {
//				mBackZone = new Rect();
//			}
//			int left, right, top, bottom;
//			left = getPaddingLeft() + (mConf.getThumbMarginLeft() > 0 ? 0 : -mConf.getThumbMarginLeft());
//			right = w - getPaddingRight() - (mConf.getThumbMarginRight() > 0 ? 0 : -mConf.getThumbMarginRight()) + (-mConf.getShrinkX());
//			top = getPaddingTop() + (mConf.getThumbMarginTop() > 0 ? 0 : -mConf.getThumbMarginTop());
//			bottom = h - getPaddingBottom() - (mConf.getThumbMarginBottom() > 0 ? 0 : -mConf.getThumbMarginBottom()) + (-mConf.getShrinkY());
//			mBackZone.set(left, top, right, bottom);
//		} else {
//			mBackZone = null;
//		}
//	}
//
//	private void setupThumbZone() {
//		int w = getMeasuredWidth();
//		int h = getMeasuredHeight();
//		if (w > 0 && h > 0) {
//			if (mThumbZone == null) {
//				mThumbZone = new Rect();
//			}
//			int left, right, top, bottom;
//			left = mIsChecked ? (mSafeZone.right - mConf.getThumbWidth()) : mSafeZone.left;
//			right = left + mConf.getThumbWidth();
//			top = mSafeZone.top;
//			bottom = top + mConf.getThumbHeight();
//			mThumbZone.set(left, top, right, bottom);
//		} else {
//			mThumbZone = null;
//		}
//	}
//
//	private void setupDrawableBounds() {
//		if (mBackZone != null) {
//			mConf.getOnDrawable().setBounds(mBackZone);
//			mConf.getOffDrawable().setBounds(mBackZone);
//		}
//		if (mThumbZone != null) {
//			mConf.getThumbDrawable().setBounds(mThumbZone);
//		}
//	}
//
//	private int measureWidth(int measureSpec) {
//		int measuredWidth = 0;
//
//		int specMode = MeasureSpec.getMode(measureSpec);
//		int specSize = MeasureSpec.getSize(measureSpec);
//
//		int minWidth = (int) (mConf.getThumbWidth() * mConf.getMeasureFactor() + getPaddingLeft() + getPaddingRight());
//		int innerMarginWidth = mConf.getThumbMarginLeft() + mConf.getThumbMarginRight();
//		if (innerMarginWidth > 0) {
//			minWidth += innerMarginWidth;
//		}
//
//		if (specMode == MeasureSpec.EXACTLY) {
//			measuredWidth = Math.max(specSize, minWidth);
//		} else {
//			measuredWidth = minWidth;
//			if (specMode == MeasureSpec.AT_MOST) {
//				measuredWidth = Math.min(specSize, minWidth);
//			}
//		}
//
//		// bounds are negative numbers
//		measuredWidth += (mConf.getInsetBounds().left + mConf.getInsetBounds().right);
//
//		return measuredWidth;
//	}
//
//	private int measureHeight(int measureSpec) {
//		int measuredHeight = 0;
//
//		int specMode = MeasureSpec.getMode(measureSpec);
//		int specSize = MeasureSpec.getSize(measureSpec);
//
//		int minHeight = mConf.getThumbHeight() + getPaddingTop() + getPaddingBottom();
//		int innerMarginHeight = mConf.getThumbMarginTop() + mConf.getThumbMarginBottom();
//
//		if (innerMarginHeight > 0) {
//			minHeight += innerMarginHeight;
//		}
//
//		if (specMode == MeasureSpec.EXACTLY) {
//			measuredHeight = Math.max(specSize, minHeight);
//		} else {
//			measuredHeight = minHeight;
//			if (specMode == MeasureSpec.AT_MOST) {
//				measuredHeight = Math.min(specSize, minHeight);
//			}
//		}
//
//		measuredHeight += (mConf.getInsetBounds().top + mConf.getInsetBounds().bottom);
//
//		return measuredHeight;
//	}
	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);

		// fade back
		if (isPressed() && mFadeBack) {
			int alpha = (int) (255 * (isChecked() ? getProcess() : (1 - getProcess())));
			mPaint.setColor(mCurrBackColor);
			mPaint.setAlpha(alpha);
			canvas.drawRoundRect(mBackRectF, mBackRadius, mBackRadius, mPaint);

			mPaint.setColor(mNextBackColor);
			alpha = (int) (255 * (isChecked() ? (1 - getProcess()) : getProcess()));
			mPaint.setAlpha(alpha);
			canvas.drawRoundRect(mBackRectF, mBackRadius, mBackRadius, mPaint);
			mPaint.setAlpha(255);
		} else {
			mPaint.setColor(mCurrBackColor);
			canvas.drawRoundRect(mBackRectF, mBackRadius, mBackRadius, mPaint);
		}

		// thumb
		mPresentThumbRectF.set(mThumbRectF);
		mPresentThumbRectF.offset(mProcess * mSafeRectF.width(), 0);
		if (mIsThumbUseDrawable) {
			mThumbDrawable.setBounds((int) mPresentThumbRectF.left, (int) mPresentThumbRectF.top, (int) mPresentThumbRectF.right, (int) mPresentThumbRectF.bottom);
			mThumbDrawable.draw(canvas);
		} else {
			mPaint.setColor(mCurrThumbColor);
			canvas.drawRoundRect(mPresentThumbRectF, mThumbRadius, mThumbRadius, mPaint);
		}

		if (mDrawDebugRect) {
			mRectPaint.setColor(Color.parseColor("#AA0000"));
			canvas.drawRect(mBackRectF, mRectPaint);
			mRectPaint.setColor(Color.parseColor("#00FF00"));
			canvas.drawRect(mSafeRectF, mRectPaint);
			mRectPaint.setColor(Color.parseColor("#0000FF"));
			canvas.drawRect(mPresentThumbRectF, mRectPaint);
		}

//		canvas.getClipBounds(mBounds);
//		if (mBounds != null && mConf.needShrink()) {
//			mBounds.inset(mConf.getInsetX(), mConf.getInsetY());
//			canvas.clipRect(mBounds, Region.Op.REPLACE);
//			canvas.translate(mConf.getInsetBounds().left, mConf.getInsetBounds().top);
//		}
//
//		boolean useGeneralDisableEffect = !isEnabled() && this.notStatableDrawable();
//		if (useGeneralDisableEffect) {
//			canvas.saveLayerAlpha(mSaveLayerZone, 255 / 2, Canvas.MATRIX_SAVE_FLAG | Canvas.CLIP_SAVE_FLAG | Canvas.HAS_ALPHA_LAYER_SAVE_FLAG | Canvas.FULL_COLOR_LAYER_SAVE_FLAG
//					| Canvas.CLIP_TO_LAYER_SAVE_FLAG);
//		}
//
//		mConf.getOffDrawable().draw(canvas);
//		mConf.getOnDrawable().setAlpha(calcAlpha());
//		mConf.getOnDrawable().draw(canvas);
//		mConf.getThumbDrawable().draw(canvas);
//
//		if (useGeneralDisableEffect) {
//			canvas.restore();
//		}

	}

	private boolean notStatableDrawable() {
//		boolean thumbStatable = (mConf.getThumbDrawable() instanceof StateListDrawable);
//		boolean onStatable = (mConf.getOnDrawable() instanceof StateListDrawable);
//		boolean offStatable = (mConf.getOffDrawable() instanceof StateListDrawable);
//		return !thumbStatable || !onStatable || !offStatable;
		return false;
	}

	/**
	 * calculate the alpha value for on layer
	 *
	 * @return 0 ~ 255
	 */
	private int calcAlpha() {
		int alpha = 255;
//		if (mSafeZone == null || mSafeZone.right == mSafeZone.left) {
//
//		} else {
//			int backWidth = mSafeZone.right - mConf.getThumbWidth() - mSafeZone.left;
//			if (backWidth > 0) {
//				alpha = (mThumbZone.left - mSafeZone.left) * 255 / backWidth;
//			}
//		}

		return alpha;
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {

		if (!isEnabled()) {
			return false;
		}

		int action = event.getAction();

		float deltaX = event.getX() - mStartX;
		float deltaY = event.getY() - mStartY;

		// status the view going to change to when finger released
		boolean nextStatus = isChecked();

		switch (action) {
			case MotionEvent.ACTION_DOWN:
				catchView();
				mStartX = event.getX();
				mStartY = event.getY();
				mLastX = mStartX;
				setPressed(true);
				break;

			case MotionEvent.ACTION_MOVE:
				float x = event.getX();
				setProcess(getProcess() + (x - mLastX) / mSafeRectF.width());
				mLastX = x;
				break;

			case MotionEvent.ACTION_CANCEL:
			case MotionEvent.ACTION_UP:
				setPressed(false);
				nextStatus = getStatusBasedOnPos();
				float time = event.getEventTime() - event.getDownTime();
				if (deltaX < mTouchSlop && deltaY < mTouchSlop && time < mClickTimeout) {
					performClick();
				} else {
					if (nextStatus != isChecked()) {
						playSoundEffect(SoundEffectConstants.CLICK);
						setChecked(nextStatus);
					} else {
						animateToState(nextStatus);
					}
				}
				break;

			default:
				break;
		}
		return true;
	}


	/**
	 * return the status based on position of thumb
	 *
	 * @return
	 */
	private boolean getStatusBasedOnPos() {
		return getProcess() > 0.5f;
	}

	public final float getProcess() {
		return mProcess;
	}

	public final void setProcess(final float process) {
		float tp = process;
		if (tp > 1) {
			tp = 1;
		} else if (tp < 0) {
			tp = 0;
		}
		this.mProcess = tp;
		invalidate();
	}

	@Override
	public boolean performClick() {
		return super.performClick();
	}

	/**
	 * 滑动到边缘
	 *
	 * @param checked 去开还是关
	 */
	private void animateToState(boolean checked) {
		if (mThumbProcessAnimator == null) {
			return;
		}
		if (mThumbProcessAnimator.isRunning()) {
			mThumbProcessAnimator.cancel();
		}
		if (checked) {
			mThumbProcessAnimator.setFloatValues(mProcess, 1f);
		} else {
			mThumbProcessAnimator.setFloatValues(mProcess, 0);
		}
		mThumbProcessAnimator.start();
	}

	private void catchView() {
		ViewParent parent = getParent();
		if (parent != null) {
			parent.requestDisallowInterceptTouchEvent(true);
		}
	}

	@Override
	public void setChecked(final boolean checked) {
		super.setChecked(checked);
		animateToState(checked);
	}

	@Override
	protected void drawableStateChanged() {
		super.drawableStateChanged();

		if (!mIsThumbUseDrawable && mThumbColor != null) {
			mCurrThumbColor = mThumbColor.getColorForState(getDrawableState(), mCurrThumbColor);
		} else {
			setDrawableState(mThumbDrawable);
		}

		if (!mIsBackUseDrawable && mBackColor != null) {
			mCurrBackColor = mBackColor.getColorForState(getDrawableState(), mCurrBackColor);
			int[] states = isChecked() ? UNCHECKED_PRESSED_STATE : CHECKED_PRESSED_STATE;
			mNextBackColor = mBackColor.getColorForState(states, mCurrBackColor);
		} else {
			setDrawableState(mBackDrawable);
		}

//		setDrawableState(mConf.getThumbDrawable());
//		setDrawableState(mConf.getOnDrawable());
//		setDrawableState(mConf.getOffDrawable());
	}

	private void setDrawableState(Drawable drawable) {
		if (drawable != null) {
			int[] myDrawableState = getDrawableState();
			drawable.setState(myDrawableState);
			invalidate();
		}
	}

	public boolean isDrawDebugRect() {
		return mDrawDebugRect;
	}

	public void setDrawDebugRect(boolean drawDebugRect) {
		mDrawDebugRect = drawDebugRect;
	}

//	public void setOnCheckedChangeListener(CompoundButton.OnCheckedChangeListener onCheckedChangeListener) {
//		if (onCheckedChangeListener == null) {
//			throw new IllegalArgumentException("onCheckedChangeListener can not be null");
//		}
//		mOnCheckedChangeListener = onCheckedChangeListener;
//	}

//	private void setCheckedInClass(boolean checked) {
//		setCheckedInClass(checked, true);
//	}

//	private void setCheckedInClass(boolean checked, boolean trigger) {
//		if (mIsChecked == checked) {
//			return;
//		}
//		mIsChecked = checked;
//
//		refreshDrawableState();
//
//		if (mOnCheckedChangeListener != null && trigger) {
//			mOnCheckedChangeListener.onCheckedChanged(this, mIsChecked);
//		}
//	}

/*	public void slideToChecked(boolean checked) {
		if (isAnimating) {
			return;
		}
//		int from = mThumbZone.left;
//		int to = checked ? mSafeZone.right - mConf.getThumbWidth() : mSafeZone.left;
//		mAnimationController.startAnimation(from, to);
	}

	private void moveThumb(int delta) {

//		int newLeft = mThumbZone.left + delta;
//		int newRight = mThumbZone.right + delta;
//		if (newLeft < mSafeZone.left) {
//			newLeft = mSafeZone.left;
//			newRight = newLeft + mConf.getThumbWidth();
//		}
//		if (newRight > mSafeZone.right) {
//			newRight = mSafeZone.right;
//			newLeft = newRight - mConf.getThumbWidth();
//		}
//
//		moveThumbTo(newLeft, newRight);
	}

	private void moveThumbTo(int newLeft, int newRight) {
		mThumbZone.set(newLeft, mThumbZone.top, newRight, mThumbZone.bottom);
//		mConf.getThumbDrawable().setBounds(mThumbZone);
	}*/

//	class SBAnimationListener implements AnimationController.OnAnimateListener {
//
//		@Override
//		public void onAnimationStart() {
//			isAnimating = true;
//		}
//
//		@Override
//		public boolean continueAnimating() {
//			return mThumbZone.right < mSafeZone.right && mThumbZone.left > mSafeZone.left;
//		}
//
//		@Override
//		public void onFrameUpdate(int frame) {
//			moveThumb(frame);
//			postInvalidate();
//		}
//
//		@Override
//		public void onAnimateComplete() {
//			setCheckedInClass(getStatusBasedOnPos());
//			isAnimating = false;
//		}
//
//	}
}
