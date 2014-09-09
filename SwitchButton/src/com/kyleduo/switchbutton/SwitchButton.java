package com.kyleduo.switchbutton;

import com.kyleduo.switchbutton.R;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Rect;
import android.graphics.drawable.NinePatchDrawable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

public class SwitchButton extends View {

	/**
	 * whitch you actually touch and move, typically, the round button on the
	 * one of two sides.
	 */
	private Thumb mThumb;
	private float mDensity = 1f;
	/**
	 * this indicate the distance from edge of this switch to the edge of thumb
	 */
	private int mTogglePaddingLeft = 3, mTogglePaddingRight = 3, mTogglePaddingTop = 3, mTogglePaddingBottom = 1;
	/**
	 * actual bound of this switch
	 */
	private Rect mBgRect;
	/**
	 * zone for thumb to move
	 */
	private Rect mSafeZone;
	private Paint mPaint;
	private float mLastX;
	private float mOnProgress;
	private boolean mIsOn = false;
	private boolean mHasMoved = false;
	private OnToggleChangedListener mOnToggleChangedListener;
	private SwitchButton mThis;

	public SwitchButton(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		initial();
	}

	public SwitchButton(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public SwitchButton(Context context) {
		this(context, null);
	}

	private void initial() {

		this.mThis = this;

		mPaint = new Paint();
		mPaint.setAntiAlias(true);
		mPaint.setStyle(Style.FILL);
		mPaint.setColor(Color.BLACK);

		mDensity = getContext().getResources().getDisplayMetrics().density;

		Bitmap image = BitmapFactory.decodeResource(getContext().getResources(), R.drawable.do_switch_thumb);
		mThumb = new Thumb(getPaddingLeft() + (int) (mTogglePaddingLeft * mDensity), getPaddingTop() + (int) (mTogglePaddingTop * mDensity), image);

		mBgRect = new Rect();
		mSafeZone = new Rect();
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		setMeasuredDimension(measureWidth(widthMeasureSpec), measureHeight(heightMeasureSpec));
	}

	private int measureWidth(int measureSpec) {
		int measuredWidth = 0;

		int specMode = MeasureSpec.getMode(measureSpec);
		int specSize = MeasureSpec.getSize(measureSpec);

		if (specMode == MeasureSpec.EXACTLY) {
			measuredWidth = specSize;
		} else {
			measuredWidth = (int) ((this.mTogglePaddingLeft + this.mTogglePaddingRight) * mDensity + mThumb.getWidth() * 2 + getPaddingLeft() + getPaddingRight());
			System.out.println("mw: " + measuredWidth);
			if (specMode == MeasureSpec.AT_MOST) {
				measuredWidth = Math.min(measuredWidth, specSize);
			}
		}

		return measuredWidth;
	}

	private int measureHeight(int measureSpec) {
		int measuredHeight = 0;

		int specMode = MeasureSpec.getMode(measureSpec);
		int specSize = MeasureSpec.getSize(measureSpec);

		if (specMode == MeasureSpec.EXACTLY) {
			measuredHeight = specSize;
		} else {
			measuredHeight = (int) ((this.mTogglePaddingTop + this.mTogglePaddingBottom) * mDensity + mThumb.getHeight() + getPaddingTop() + getPaddingBottom());
			System.out.println("mh: " + measuredHeight);
			if (specMode == MeasureSpec.AT_MOST) {
				measuredHeight = Math.min(measuredHeight, specSize);
			}
		}

		return measuredHeight;
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);

		int width = getMeasuredWidth();
		int height = getMeasuredHeight();

		mBgRect.set(getPaddingLeft(), getPaddingTop(), width - getPaddingRight(), height - getPaddingBottom());
		mSafeZone.set((int) (getPaddingLeft() + mTogglePaddingLeft * mDensity), (int) (getPaddingTop() + mTogglePaddingTop * mDensity), (int) (width
				- getPaddingRight() - mTogglePaddingRight * mDensity - mThumb.getWidth()),
				(int) (height - getPaddingBottom() - mTogglePaddingBottom * mDensity));

		NinePatchDrawable offBg = (NinePatchDrawable) getResources().getDrawable(R.drawable.togglebutton_bg_off);
		NinePatchDrawable onBg = (NinePatchDrawable) getResources().getDrawable(R.drawable.togglebutton_bg_on);
		onBg.setAlpha((int) (255 * this.mOnProgress));
		offBg.setBounds(mBgRect);
		onBg.setBounds(mBgRect);

		offBg.draw(canvas);
		onBg.draw(canvas);

		mThumb.draw(canvas, mPaint);

	}

	@SuppressLint("ClickableViewAccessibility")
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		if (this.mThumb.isMoving()) {
			return true;
		}

		int action = event.getAction();

		switch (action) {
		case MotionEvent.ACTION_DOWN:
			mLastX = event.getX();
			break;

		case MotionEvent.ACTION_MOVE:

			float x = event.getX();

			mThumb.moveBy(x - mLastX, 0);
			mLastX = x;

			if (!mThumb.isOnLeftEdge() && !mThumb.isOnRightEdge()) {
				mHasMoved = true;
			}

			updateOnProgress();

			break;

		case MotionEvent.ACTION_UP:
			if (!this.mHasMoved) {
				mThumb.setIsMoving(true);
				if (mIsOn) {
					goingOff();
				} else {
					goingOn();
				}
			} else {
				if (mIsOn) {
					if (this.mThumb.isOnRightEdge()) {
						mHasMoved = false;
						return true;
					} else {
						mThumb.setIsMoving(true);
						goingOff();
					}
				} else {

					if (this.mThumb.isOnLeftEdge()) {
						mHasMoved = false;
						return true;
					} else {
						mThumb.setIsMoving(true);
						goingOn();
					}
				}
			}

			break;

		default:
			break;
		}

		invalidate();

		return true;
	}

	public boolean isOn() {
		return this.mIsOn;
	}

	public void setIsOn(boolean isOn) {
		if (isOn == !this.mIsOn) {
			this.mThumb.setIsMoving(true);
			if (isOn) {
				goingOn();
			} else {
				goingOff();
			}
		}
	}

	public void setOnToggleChangedListener(OnToggleChangedListener onToggleChangedListener) {
		this.mOnToggleChangedListener = onToggleChangedListener;
	}

	private void updateOnProgress() {
		this.mOnProgress = (float) ((this.mThumb.getX() - this.mSafeZone.left) / (this.mSafeZone.width() + 0.0f));
		if (this.mOnProgress < 0) {
			this.mOnProgress = 0;
		}
		if (this.mOnProgress > 1) {
			this.mOnProgress = 1;
		}
	}

	private void goingOn() {
		postDelayed(new Runnable() {

			@Override
			public void run() {
				if (mThumb.isMoving()) {
					mThumb.moveBy(2 * mDensity, 0);
					postInvalidate();
					updateOnProgress();
					goingOn();
				}
			}
		}, 15);
	}

	private void goingOff() {
		postDelayed(new Runnable() {

			@Override
			public void run() {
				if (mThumb.isMoving()) {
					mThumb.moveBy(-2 * mDensity, 0);
					postInvalidate();
					updateOnProgress();
					goingOff();
				}
			}
		}, 15);
	}

	class Thumb {
		private int x, y, w, h;
		private Bitmap image;
		private boolean mIsMoving = false;

		public Thumb(int x, int y, Bitmap image) {
			this.x = x;
			this.y = y;
			this.image = image;
			this.w = image.getWidth();
			this.h = image.getHeight();
		}

		public int getX() {
			return x;
		}

		public void setX(int x) {
			this.x = x;
		}

		public int getY() {
			return y;
		}

		public void setY(int y) {
			this.y = y;
		}

		public int getCenterX() {
			return this.x + this.w / 2;
		}

		public int getCenterY() {
			return this.y + this.h / 2;
		}

		public void moveBy(float deltX, float deltY) {
			this.x += deltX;
			this.y += deltY;

			if (mIsMoving && isCollision()) {
				mIsMoving = false;
				mHasMoved = false;
				mIsOn = !mIsOn;
				if (mOnToggleChangedListener != null) {
					mOnToggleChangedListener.onToggleChanged(mIsOn, mThis);
				}
			}

			if (this.x < mSafeZone.left) {
				this.x = mSafeZone.left;
			}

			if (this.x > mSafeZone.right) {
				this.x = mSafeZone.right;
			}

			if (this.y < mSafeZone.top) {
				this.y = mSafeZone.top;
			}

			if (this.y > mSafeZone.bottom) {
				this.y = mSafeZone.bottom;
			}

		}

		public Bitmap getImage() {
			return image;
		}

		public void setImage(Bitmap image) {
			this.image = image;
		}

		public int getWidth() {
			return w;
		}

		public int getHeight() {
			return h;
		}

		public boolean isOnRightEdge() {
			return this.x == mSafeZone.right;
		}

		public boolean isOnLeftEdge() {
			return this.x == mSafeZone.left;
		}

		public boolean isMoving() {
			return this.mIsMoving;
		}

		public void setIsMoving(boolean isMoving) {
			this.mIsMoving = isMoving;
		}

		private boolean isCollision() {
			return this.isXCollision() || this.isYCollision();
		}

		private boolean isXCollision() {
			return this.x < mSafeZone.left || this.x > mSafeZone.right;
		}

		private boolean isYCollision() {
			return this.y < mSafeZone.top || this.y > mSafeZone.bottom;
		}

		public void draw(Canvas canvas, Paint paint) {
			canvas.drawBitmap(image, x, y, paint);
		}

	}

	interface OnToggleChangedListener {
		public void onToggleChanged(boolean newState, SwitchButton view);
	}
}
