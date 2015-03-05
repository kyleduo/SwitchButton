package com.kyleduo.switchbutton.switchbutton;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Region;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.StateListDrawable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.CompoundButton;

/**
 * SwitchButton widget which is easy to use
 * 
 * @version 1.2
 * @author kyleduo
 * @since 2014-09-24
 */

public class SwitchButton extends CompoundButton {
	private static boolean SHOW_RECT = false;

	private boolean mIsChecked = false;

	private Configuration mConf;

	/**
	 * zone for thumb to move inside
	 */
	private Rect mSafeZone;
	/**
	 * zone for background
	 */
	private Rect mBackZone;
	private Rect mThumbZone;
	private RectF mSaveLayerZone;

	private AnimationController mAnimationController;
	private SBAnimationListener mOnAnimateListener = new SBAnimationListener();
	private boolean isAnimating = false;

	private float mStartX, mStartY, mLastX;
	private float mCenterPos;

	private int mTouchSlop;
	private int mClickTimeout;

	private Paint mRectPaint;

	private Rect mBounds = null;

	private OnCheckedChangeListener mOnCheckedChangeListener;

	@SuppressLint("NewApi")
	public SwitchButton(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		initView();

		TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.SwitchButton);

		mConf.setThumbMarginInPixel(ta.getDimensionPixelSize(R.styleable.SwitchButton_thumb_margin, mConf.getDefaultThumbMarginInPixel()));
		mConf.setThumbMarginInPixel(ta.getDimensionPixelSize(R.styleable.SwitchButton_thumb_marginTop, mConf.getThumbMarginTop()),
				ta.getDimensionPixelSize(R.styleable.SwitchButton_thumb_marginBottom, mConf.getThumbMarginBottom()),
				ta.getDimensionPixelSize(R.styleable.SwitchButton_thumb_marginLeft, mConf.getThumbMarginLeft()),
				ta.getDimensionPixelSize(R.styleable.SwitchButton_thumb_marginRight, mConf.getThumbMarginRight()));
		mConf.setRadius(ta.getInt(R.styleable.SwitchButton_radius, Configuration.Default.DEFAULT_RADIUS));

		mConf.setThumbWidthAndHeightInPixel(ta.getDimensionPixelSize(R.styleable.SwitchButton_thumb_width, -1), ta.getDimensionPixelSize(R.styleable.SwitchButton_thumb_height, -1));

		mConf.setMeasureFactor(ta.getFloat(R.styleable.SwitchButton_measureFactor, -1));

		mConf.setInsetBounds(ta.getDimensionPixelSize(R.styleable.SwitchButton_insetLeft, 0), ta.getDimensionPixelSize(R.styleable.SwitchButton_insetTop, 0),
				ta.getDimensionPixelSize(R.styleable.SwitchButton_insetRight, 0), ta.getDimensionPixelSize(R.styleable.SwitchButton_insetBottom, 0));

		int velocity = ta.getInteger(R.styleable.SwitchButton_animationVelocity, -1);
		mAnimationController.setVelocity(velocity);

		fetchDrawableFromAttr(ta);
		ta.recycle();

		if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.HONEYCOMB) {
			this.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
		}
	}

	public SwitchButton(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public SwitchButton(Context context) {
		this(context, null);
	}

	private void initView() {
		mConf = Configuration.getDefault(getContext().getResources().getDisplayMetrics().density);
		mTouchSlop = ViewConfiguration.get(getContext()).getScaledTouchSlop();
		mClickTimeout = ViewConfiguration.getPressedStateDuration() + ViewConfiguration.getTapTimeout();
		mAnimationController = AnimationController.getDefault().init(mOnAnimateListener);
		mBounds = new Rect();
		if (SHOW_RECT) {
			mRectPaint = new Paint();
			mRectPaint.setStyle(Style.STROKE);
		}
	}

	/**
	 * fetch drawable resources from attrs, drop them to conf, AFTER the size
	 * has been confirmed
	 * 
	 * @param ta
	 */
	private void fetchDrawableFromAttr(TypedArray ta) {
		if (mConf == null) {
			return;
		}
		mConf.setOffDrawable(fetchDrawable(ta, R.styleable.SwitchButton_offDrawable, R.styleable.SwitchButton_offColor, Configuration.Default.DEFAULT_OFF_COLOR));
		mConf.setOnDrawable(fetchDrawable(ta, R.styleable.SwitchButton_onDrawable, R.styleable.SwitchButton_onColor, Configuration.Default.DEFAULT_ON_COLOR));
		mConf.setThumbDrawable(fetchThumbDrawable(ta));
	}

	private Drawable fetchDrawable(TypedArray ta, int attrId, int alterColorId, int defaultColor) {
		Drawable tempDrawable = ta.getDrawable(attrId);
		if (tempDrawable == null) {
			int tempColor = ta.getColor(alterColorId, defaultColor);
			tempDrawable = new GradientDrawable();
			((GradientDrawable) tempDrawable).setCornerRadius(this.mConf.getRadius());
			((GradientDrawable) tempDrawable).setColor(tempColor);
		}
		return tempDrawable;
	}

	private Drawable fetchThumbDrawable(TypedArray ta) {

		Drawable tempDrawable = ta.getDrawable(R.styleable.SwitchButton_thumbDrawable);
		if (tempDrawable != null) {
			return tempDrawable;
		}

		int normalColor = ta.getColor(R.styleable.SwitchButton_thumbColor, Configuration.Default.DEFAULT_THUMB_COLOR);
		int pressedColor = ta.getColor(R.styleable.SwitchButton_thumbPressedColor, Configuration.Default.DEFAULT_THUMB_PRESSED_COLOR);

		StateListDrawable drawable = new StateListDrawable();
		GradientDrawable normalDrawable = new GradientDrawable();
		normalDrawable.setCornerRadius(this.mConf.getRadius());
		normalDrawable.setColor(normalColor);
		GradientDrawable pressedDrawable = new GradientDrawable();
		pressedDrawable.setCornerRadius(this.mConf.getRadius());
		pressedDrawable.setColor(pressedColor);
		drawable.addState(View.PRESSED_ENABLED_STATE_SET, pressedDrawable);
		drawable.addState(new int[] {}, normalDrawable);

		return drawable;
	}

	public void setConfiguration(Configuration conf) {
		if (mConf == null) {
			mConf = Configuration.getDefault(conf.getDensity());
		}
		mConf.setOffDrawable(conf.getOffDrawableWithFix());
		mConf.setOnDrawable(conf.getOnDrawableWithFix());
		mConf.setThumbDrawable(conf.getThumbDrawableWithFix());
		mConf.setThumbMarginInPixel(conf.getThumbMarginTop(), conf.getThumbMarginBottom(), conf.getThumbMarginLeft(), conf.getThumbMarginRight());
		mConf.setThumbWidthAndHeightInPixel(conf.getThumbWidth(), conf.getThumbHeight());
		mConf.setVelocity(conf.getVelocity());
		mConf.setMeasureFactor(conf.getMeasureFactor());
		mAnimationController.setVelocity(mConf.getVelocity());
		this.requestLayout();
		setup();
		setChecked(mIsChecked);
	}

	/**
	 * return a REFERENCE of configuration, it is suggested that not to change that
	 * 
	 * @return
	 */
	public Configuration getConfiguration() {
		return mConf;
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		setMeasuredDimension(measureWidth(widthMeasureSpec), measureHeight(heightMeasureSpec));
	}

	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		super.onSizeChanged(w, h, oldw, oldh);
		setup();
	}

	private void setup() {
		setupBackZone();
		setupSafeZone();
		setupThumbZone();

		setupDrawableBounds();
		if (this.getMeasuredWidth() > 0 && this.getMeasuredHeight() > 0) {
			mSaveLayerZone = new RectF(0, 0, this.getMeasuredWidth(), this.getMeasuredHeight());
		}

		ViewGroup parent = (ViewGroup) this.getParent();
		if (parent != null) {
			parent.setClipChildren(false);
		}
	}

	/**
	 * setup zone for thumb to move
	 * 
	 * @param w
	 * @param h
	 */
	private void setupSafeZone() {
		int w = getMeasuredWidth();
		int h = getMeasuredHeight();
		if (w > 0 && h > 0) {
			if (mSafeZone == null) {
				mSafeZone = new Rect();
			}
			int left, right, top, bottom;
			left = getPaddingLeft() + (mConf.getThumbMarginLeft() > 0 ? mConf.getThumbMarginLeft() : 0);
			right = w - getPaddingRight() - (mConf.getThumbMarginRight() > 0 ? mConf.getThumbMarginRight() : 0) + (-mConf.getShrinkX());
			top = getPaddingTop() + (mConf.getThumbMarginTop() > 0 ? mConf.getThumbMarginTop() : 0);
			bottom = h - getPaddingBottom() - (mConf.getThumbMarginBottom() > 0 ? mConf.getThumbMarginBottom() : 0) + (-mConf.getShrinkY());
			mSafeZone.set(left, top, right, bottom);

			mCenterPos = mSafeZone.left + (mSafeZone.right - mSafeZone.left - mConf.getThumbWidth()) / 2;
		} else {
			mSafeZone = null;
		}
	}

	private void setupBackZone() {
		int w = getMeasuredWidth();
		int h = getMeasuredHeight();
		if (w > 0 && h > 0) {
			if (mBackZone == null) {
				mBackZone = new Rect();
			}
			int left, right, top, bottom;
			left = getPaddingLeft() + (mConf.getThumbMarginLeft() > 0 ? 0 : -mConf.getThumbMarginLeft());
			right = w - getPaddingRight() - (mConf.getThumbMarginRight() > 0 ? 0 : -mConf.getThumbMarginRight()) + (-mConf.getShrinkX());
			top = getPaddingTop() + (mConf.getThumbMarginTop() > 0 ? 0 : -mConf.getThumbMarginTop());
			bottom = h - getPaddingBottom() - (mConf.getThumbMarginBottom() > 0 ? 0 : -mConf.getThumbMarginBottom()) + (-mConf.getShrinkY());
			mBackZone.set(left, top, right, bottom);
		} else {
			mBackZone = null;
		}
	}

	private void setupThumbZone() {
		int w = getMeasuredWidth();
		int h = getMeasuredHeight();
		if (w > 0 && h > 0) {
			if (mThumbZone == null) {
				mThumbZone = new Rect();
			}
			int left, right, top, bottom;
			left = mIsChecked ? (mSafeZone.right - mConf.getThumbWidth()) : mSafeZone.left;
			right = left + mConf.getThumbWidth();
			top = mSafeZone.top;
			bottom = top + mConf.getThumbHeight();
			mThumbZone.set(left, top, right, bottom);
		} else {
			mThumbZone = null;
		}
	}

	private void setupDrawableBounds() {
		if (mBackZone != null) {
			mConf.getOnDrawable().setBounds(mBackZone);
			mConf.getOffDrawable().setBounds(mBackZone);
		}
		if (mThumbZone != null) {
			mConf.getThumbDrawable().setBounds(mThumbZone);
		}
	}

	private int measureWidth(int measureSpec) {
		int measuredWidth = 0;

		int specMode = MeasureSpec.getMode(measureSpec);
		int specSize = MeasureSpec.getSize(measureSpec);

		int minWidth = (int) (mConf.getThumbWidth() * mConf.getMeasureFactor() + getPaddingLeft() + getPaddingRight());
		int innerMarginWidth = mConf.getThumbMarginLeft() + mConf.getThumbMarginRight();
		if (innerMarginWidth > 0) {
			minWidth += innerMarginWidth;
		}

		if (specMode == MeasureSpec.EXACTLY) {
			measuredWidth = Math.max(specSize, minWidth);
		} else {
			measuredWidth = minWidth;
			if (specMode == MeasureSpec.AT_MOST) {
				measuredWidth = Math.min(specSize, minWidth);
			}
		}

		// bounds are negative numbers
		measuredWidth += (mConf.getInsetBounds().left + mConf.getInsetBounds().right);

		return measuredWidth;
	}

	private int measureHeight(int measureSpec) {
		int measuredHeight = 0;

		int specMode = MeasureSpec.getMode(measureSpec);
		int specSize = MeasureSpec.getSize(measureSpec);

		int minHeight = mConf.getThumbHeight() + getPaddingTop() + getPaddingBottom();
		int innerMarginHeight = mConf.getThumbMarginTop() + mConf.getThumbMarginBottom();

		if (innerMarginHeight > 0) {
			minHeight += innerMarginHeight;
		}

		if (specMode == MeasureSpec.EXACTLY) {
			measuredHeight = Math.max(specSize, minHeight);
		} else {
			measuredHeight = minHeight;
			if (specMode == MeasureSpec.AT_MOST) {
				measuredHeight = Math.min(specSize, minHeight);
			}
		}

		measuredHeight += (mConf.getInsetBounds().top + mConf.getInsetBounds().bottom);

		return measuredHeight;
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);

		canvas.getClipBounds(mBounds);
		if (mBounds != null && mConf.needShrink()) {
			mBounds.inset(mConf.getInsetX(), mConf.getInsetY());
			canvas.clipRect(mBounds, Region.Op.REPLACE);
			canvas.translate(mConf.getInsetBounds().left, mConf.getInsetBounds().top);
		}

		boolean useGeneralDisableEffect = !isEnabled() && this.notStatableDrawable();
		if (useGeneralDisableEffect) {
			canvas.saveLayerAlpha(mSaveLayerZone, 255 / 2, Canvas.MATRIX_SAVE_FLAG | Canvas.CLIP_SAVE_FLAG | Canvas.HAS_ALPHA_LAYER_SAVE_FLAG | Canvas.FULL_COLOR_LAYER_SAVE_FLAG
					| Canvas.CLIP_TO_LAYER_SAVE_FLAG);
		}

		mConf.getOffDrawable().draw(canvas);
		mConf.getOnDrawable().setAlpha(calcAlpha());
		mConf.getOnDrawable().draw(canvas);
		mConf.getThumbDrawable().draw(canvas);

		if (useGeneralDisableEffect) {
			canvas.restore();
		}

		if (SHOW_RECT) {
			mRectPaint.setColor(Color.parseColor("#AA0000"));
			canvas.drawRect(mBackZone, mRectPaint);
			mRectPaint.setColor(Color.parseColor("#00FF00"));
			canvas.drawRect(mSafeZone, mRectPaint);
			mRectPaint.setColor(Color.parseColor("#0000FF"));
			canvas.drawRect(mThumbZone, mRectPaint);
		}
	}

	private boolean notStatableDrawable() {
		boolean thumbStatable = (mConf.getThumbDrawable() instanceof StateListDrawable);
		boolean onStatable = (mConf.getOnDrawable() instanceof StateListDrawable);
		boolean offStatable = (mConf.getOffDrawable() instanceof StateListDrawable);
		return !thumbStatable || !onStatable || !offStatable;
	}

	/**
	 * calculate the alpha value for on layer
	 * @return 0 ~ 255
	 */
	private int calcAlpha() {
		int alpha = 255;
		if (mSafeZone == null || mSafeZone.right == mSafeZone.left) {

		} else {
			int backWidth = mSafeZone.right - mConf.getThumbWidth() - mSafeZone.left;
			if (backWidth > 0) {
				alpha = (mThumbZone.left - mSafeZone.left) * 255 / backWidth;
			}
		}

		return alpha;
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {

		if (isAnimating || !isEnabled()) {
			return false;
		}
		int action = event.getAction();

		float deltaX = event.getX() - mStartX;
		float deltaY = event.getY() - mStartY;

		// status the view going to change to when finger released
		boolean nextStatus = mIsChecked;

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
			moveThumb((int) (x - mLastX));
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
				slideToChecked(nextStatus);
			}

			break;

		default:
			break;
		}
		invalidate();
		return true;
	}

	/**
	 * return the status based on position of thumb
	 * @return
	 */
	private boolean getStatusBasedOnPos() {
		return mThumbZone.left > mCenterPos;
	}

	@Override
	public void invalidate() {
		if (mBounds != null && mConf.needShrink()) {
			invalidate(mBounds);
		} else {
			super.invalidate();
		}
	}

	@Override
	public boolean performClick() {
		return super.performClick();
	}

	private void catchView() {
		ViewParent parent = getParent();
		if (parent != null) {
			parent.requestDisallowInterceptTouchEvent(true);
		}
	}

	@Override
	public void setChecked(final boolean checked) {
		setChecked(checked, true);
	}

	public void setChecked(final boolean checked, boolean trigger) {
		if (mThumbZone != null) {
			moveThumb(checked ? getMeasuredWidth() : -getMeasuredWidth());
		}
		setCheckedInClass(checked, trigger);
	}

	@Override
	public boolean isChecked() {
		return mIsChecked;
	}

	@Override
	public void toggle() {
		toggle(true);
	}

	public void toggle(boolean animated) {
		if (animated) {
			slideToChecked(!mIsChecked);
		} else {
			setChecked(!mIsChecked);
		}
	}

	@Override
	protected void drawableStateChanged() {
		super.drawableStateChanged();
		setDrawableState(mConf.getThumbDrawable());
		setDrawableState(mConf.getOnDrawable());
		setDrawableState(mConf.getOffDrawable());
	}

	private void setDrawableState(Drawable drawable) {
		if (drawable != null) {
			int[] myDrawableState = getDrawableState();
			drawable.setState(myDrawableState);
			invalidate();
		}
	}

	public void setOnCheckedChangeListener(OnCheckedChangeListener onCheckedChangeListener) {
		if (onCheckedChangeListener == null) {
			throw new IllegalArgumentException("onCheckedChangeListener can not be null");
		}
		mOnCheckedChangeListener = onCheckedChangeListener;
	}

	private void setCheckedInClass(boolean checked) {
		setCheckedInClass(checked, true);
	}

	private void setCheckedInClass(boolean checked, boolean trigger) {
		if (mIsChecked == checked) {
			return;
		}
		mIsChecked = checked;

		refreshDrawableState();

		if (mOnCheckedChangeListener != null && trigger) {
			mOnCheckedChangeListener.onCheckedChanged(this, mIsChecked);
		}
	}

	public void slideToChecked(boolean checked) {
		if (isAnimating) {
			return;
		}
		int from = mThumbZone.left;
		int to = checked ? mSafeZone.right - mConf.getThumbWidth() : mSafeZone.left;
		mAnimationController.startAnimation(from, to);
	}

	private void moveThumb(int delta) {

		int newLeft = mThumbZone.left + delta;
		int newRight = mThumbZone.right + delta;
		if (newLeft < mSafeZone.left) {
			newLeft = mSafeZone.left;
			newRight = newLeft + mConf.getThumbWidth();
		}
		if (newRight > mSafeZone.right) {
			newRight = mSafeZone.right;
			newLeft = newRight - mConf.getThumbWidth();
		}

		moveThumbTo(newLeft, newRight);
	}

	private void moveThumbTo(int newLeft, int newRight) {
		mThumbZone.set(newLeft, mThumbZone.top, newRight, mThumbZone.bottom);
		mConf.getThumbDrawable().setBounds(mThumbZone);
	}

	class SBAnimationListener implements AnimationController.OnAnimateListener {

		@Override
		public void onAnimationStart() {
			isAnimating = true;
		}

		@Override
		public boolean continueAnimating() {
			return mThumbZone.right < mSafeZone.right && mThumbZone.left > mSafeZone.left;
		}

		@Override
		public void onFrameUpdate(int frame) {
			moveThumb(frame);
			postInvalidate();
		}

		@Override
		public void onAnimateComplete() {
			setCheckedInClass(getStatusBasedOnPos());
			isAnimating = false;
		}

	}
}
