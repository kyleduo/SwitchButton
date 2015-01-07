package com.kyleduo.switchbutton;

import java.lang.reflect.Field;

import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.StateListDrawable;
import android.view.View;

/**
 * class for configuring the Switchbutton
 * used when you want to change the face of this view in code
 * 
 * @author kyleduo
 * @since 2014-09-24
 */
public class Configuration implements Cloneable {

	static class Default {
		static int DEFAULT_OFF_COLOR = Color.parseColor("#E3E3E3");
		static int DEFAULT_ON_COLOR = Color.parseColor("#02BFE7");
		static int DEFAULT_THUMB_COLOR = Color.parseColor("#FFFFFF");
		static int DEFAULT_THUMB_PRESSED_COLOR = Color.parseColor("#fafafa");
		static int DEFAULT_THUMB_MARGIN = 2;
		static int DEFAULT_RADIUS = 999;
		static float DEFAULT_MEASURE_FACTOR = 2f;
		static int DEFAULT_INNER_BOUNDS = 0;
	}

	static class Limit {
		static int MIN_THUMB_SIZE = 24;
	}

	/**
	 * drawable of background
	 */
	private Drawable mOnDrawable = null, mOffDrawable = null;
	/**
	 * drawable of thumb
	 */
	private Drawable mThumbDrawable = null;

	private int mOnColor = Default.DEFAULT_ON_COLOR, mOffColor = Default.DEFAULT_OFF_COLOR, mThumbColor = Default.DEFAULT_THUMB_COLOR, mThumbPressedColor = Default.DEFAULT_THUMB_PRESSED_COLOR;

	/**
	 * space between View's border and thumb
	 */
	private int mThumbMarginTop = 0, mThumbMarginBottom = 0, mThumbMarginLeft = 0, mThumbMarginRight = 0;

	private int mThumbWidth = -1, mThumbHeight = -1;

	private float density;

	private int mVelocity = -1;

	private float mRadius = -1;

	/**
	 * factor limit the minimum width equals almost (the height of thumb * measureFactor)
	 */
	private float mMeasureFactor = 0;

	/**
	 * inner bounds
	 */
	private Rect mInsetBounds;

	private Configuration() {
	};

	public static Configuration getDefault(float density) {
		Configuration defaultConfiguration = new Configuration();
		defaultConfiguration.density = density;
		defaultConfiguration.setThumbMarginInPixel(defaultConfiguration.getDefaultThumbMarginInPixel());

		defaultConfiguration.mInsetBounds = new Rect(Default.DEFAULT_INNER_BOUNDS, Default.DEFAULT_INNER_BOUNDS, Default.DEFAULT_INNER_BOUNDS, Default.DEFAULT_INNER_BOUNDS);

		return defaultConfiguration;
	}

	public void setBackDrawable(Drawable offDrawable, Drawable onDrawable) {
		if (onDrawable == null && offDrawable == null) {
			throw new IllegalArgumentException("back drawable can not be null");
		}

		if (offDrawable != null) {
			mOffDrawable = offDrawable;

			if (onDrawable != null) {
				mOnDrawable = onDrawable;
			} else {
				mOnDrawable = mOffDrawable;
			}
		}
	}

	void setOffDrawable(Drawable offDrawable) {
		if (offDrawable == null) {
			throw new IllegalArgumentException("off drawable can not be null");
		}

		mOffDrawable = offDrawable;
	}

	void setOnDrawable(Drawable onDrawable) {
		if (onDrawable == null) {
			throw new IllegalArgumentException("on drawable can not be null");
		}

		mOnDrawable = onDrawable;
	}

	public Drawable getOnDrawable() {
		return mOnDrawable;
	}

	public Drawable getOffDrawable() {
		return mOffDrawable;
	}

	public void setThumbDrawable(Drawable thumbDrawable) {
		if (thumbDrawable == null) {
			throw new IllegalArgumentException("thumb drawable can not be null");
		}
		mThumbDrawable = thumbDrawable;
	}

	public Drawable getThumbDrawable() {
		return mThumbDrawable;
	}

	public void setThumbMargin(int top, int bottom, int left, int right) {
		mThumbMarginTop = (int) (top * density);
		mThumbMarginBottom = (int) (bottom * density);
		mThumbMarginLeft = (int) (left * density);
		mThumbMarginRight = (int) (right * density);
	}

	public void setThumbMarginInPixel(int top, int bottom, int left, int right) {
		mThumbMarginTop = top;
		mThumbMarginBottom = bottom;
		mThumbMarginLeft = left;
		mThumbMarginRight = right;
	}

	public void setThumbMargin(int top, int bottom, int leftAndRight) {
		setThumbMargin(top, bottom, leftAndRight, leftAndRight);
	}

	public void setThumbMargin(int topAndBottom, int leftAndRight) {
		setThumbMargin(topAndBottom, topAndBottom, leftAndRight, leftAndRight);
	}

	public void setThumbMargin(int margin) {
		setThumbMargin(margin, margin, margin, margin);
	}

	public void setThumbMarginInPixel(int marginInPixel) {
		setThumbMarginInPixel(marginInPixel, marginInPixel, marginInPixel, marginInPixel);
	}

	public int getDefaultThumbMarginInPixel() {
		return (int) (Default.DEFAULT_THUMB_MARGIN * density);
	}

	public int getThumbMarginTop() {
		return mThumbMarginTop;
	}

	public int getThumbMarginBottom() {
		return mThumbMarginBottom;
	}

	public int getThumbMarginLeft() {
		return mThumbMarginLeft;
	}

	public int getThumbMarginRight() {
		return mThumbMarginRight;
	}

	public float getDensity() {
		return density;
	}

	public void setRadius(float radius) {
		this.mRadius = radius;
	}

	public float getRadius() {
		if (this.mRadius < 0) {
			return Default.DEFAULT_RADIUS;
		}
		return this.mRadius;
	}

	/**
	 * set velocity of animation
	 * @param velocity pixel moved per frame (in linear)
	 */
	public void setVelocity(int velocity) {
		this.mVelocity = velocity;
	}

	public int getVelocity() {
		return mVelocity;
	}

	public void setOnColor(int onColor) {
		this.mOnColor = onColor;
	}

	public int getOnColor(int onColor) {
		return mOnColor;
	}

	public void setOffColor(int offColor) {
		this.mOffColor = offColor;
	}

	public int getOffColor() {
		return mOffColor;
	}

	public void setThumbColor(int thumbColor) {
		this.mThumbColor = thumbColor;
	}

	public int getThumbColor() {
		return mThumbColor;
	}

	public void setThumbWidthAndHeightInPixel(int width, int height) {
		if (width > 0) {
			mThumbWidth = width;
		}

		if (height > 0) {
			mThumbHeight = height;
		}
	}

	public void setThumbWidthAndHeight(int width, int height) {
		setThumbWidthAndHeightInPixel((int) (width * density), (int) (height * density));
	}

	public Drawable getOffDrawableWithFix() {
		if (mOffDrawable != null) {
			return mOffDrawable;
		} else {
			return getDrawableFromColor(mOffColor);
		}
	}

	public Drawable getOnDrawableWithFix() {
		if (mOnDrawable != null) {
			return mOnDrawable;
		} else {
			return getDrawableFromColor(mOnColor);
		}
	}

	public Drawable getThumbDrawableWithFix() {
		if (mThumbDrawable != null) {
			return mThumbDrawable;
		} else {
			StateListDrawable drawable = new StateListDrawable();
			Drawable normalDrawable = getDrawableFromColor(this.mThumbColor);
			Drawable pressedDrawable = getDrawableFromColor(this.mThumbPressedColor);
			int[] stateSet = null;
			try {
				Field stateField = View.class.getDeclaredField("PRESSED_ENABLED_STATE_SET");
				stateField.setAccessible(true);
				stateSet = (int[]) stateField.get(null);
			} catch (Exception e) {
				e.printStackTrace();
			}
			if (stateSet != null) {
				drawable.addState(stateSet, pressedDrawable);
			}
			drawable.addState(new int[] {}, normalDrawable);
			return drawable;
		}
	}

	public float getMeasureFactor() {
		if (mMeasureFactor <= 0) {
			mMeasureFactor = Default.DEFAULT_MEASURE_FACTOR;
		}
		return mMeasureFactor;
	}

	public void setMeasureFactor(float measureFactor) {
		if (measureFactor <= 0) {
			this.mMeasureFactor = Default.DEFAULT_MEASURE_FACTOR;
		}
		this.mMeasureFactor = measureFactor;
	}

	public Rect getInsetBounds() {
		return this.mInsetBounds;
	}

	public void setInsetBounds(int left, int top, int right, int bottom) {
		setInsetLeft(left);
		setInsetTop(top);
		setInsetRight(right);
		setInsetBottom(bottom);
	}

	public void setInsetLeft(int left) {
		if (left > 0) {
			left = -left;
		}
		this.mInsetBounds.left = left;
	}

	public void setInsetTop(int top) {
		if (top > 0) {
			top = -top;
		}
		this.mInsetBounds.top = top;
	}

	public void setInsetRight(int right) {
		if (right > 0) {
			right = -right;
		}
		this.mInsetBounds.right = right;
	}

	public void setInsetBottom(int bottom) {
		if (bottom > 0) {
			bottom = -bottom;
		}
		this.mInsetBounds.bottom = bottom;
	}

	public int getInsetX() {
		return getShrinkX() / 2;
	}

	public int getInsetY() {
		return getShrinkY() / 2;
	}

	public int getShrinkX() {
		return this.mInsetBounds.left + this.mInsetBounds.right;
	}

	public int getShrinkY() {
		return this.mInsetBounds.top + this.mInsetBounds.bottom;
	}

	public boolean needShrink() {
		return this.mInsetBounds.left + this.mInsetBounds.right + this.mInsetBounds.top + this.mInsetBounds.bottom != 0;
	}

	/**
	 * use for get drawable from color
	 * 
	 * @param color
	 * @return
	 */
	private Drawable getDrawableFromColor(int color) {
		GradientDrawable tempDrawable = new GradientDrawable();
		tempDrawable.setCornerRadius(this.getRadius());
		tempDrawable.setColor(color);
		return tempDrawable;
	}

	int getThumbWidth() {
		int width = mThumbWidth;
		if (width < 0) {
			if (mThumbDrawable != null) {
				width = mThumbDrawable.getIntrinsicWidth();
				if (width > 0) {
					return width;
				}
			}
			if (density <= 0) {
				throw new IllegalArgumentException("density must be a positive number");
			}
			width = (int) (Limit.MIN_THUMB_SIZE * density);
		}
		return width;
	}

	int getThumbHeight() {
		int height = mThumbHeight;
		if (height < 0) {
			if (mThumbDrawable != null) {
				height = mThumbDrawable.getIntrinsicHeight();
				if (height > 0) {
					return height;
				}
			}
			if (density <= 0) {
				throw new IllegalArgumentException("density must be a positive number");
			}
			height = (int) (Limit.MIN_THUMB_SIZE * density);
		}
		return height;
	}
}