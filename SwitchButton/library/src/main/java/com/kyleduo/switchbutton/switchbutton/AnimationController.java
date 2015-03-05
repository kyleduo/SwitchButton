package com.kyleduo.switchbutton.switchbutton;

import android.os.Handler;
import android.os.Message;

/**
 * controller of view animation
 * 
 * @author kyleduo
 * @since 2014-09-25
 *
 */
class AnimationController {

	private static int ANI_WHAT = 0x100;
	private static int DEFAULT_VELOCITY = 7;
	private static int DEFAULT_FRAME_DURATION = 1000 / 60;

	private AnimationHandler mHandler;
	private OnAnimateListener mOnAnimateListener;

	private boolean isAnimating = false;

	private int mFrame, mFrom, mTo;
	private int mVelocity = DEFAULT_VELOCITY;

	private AnimationController() {
		mHandler = new AnimationHandler();
	}

	/**
	 * get default AnimationController
	 * @return
	 */
	static AnimationController getDefault() {
		AnimationController ac = new AnimationController();
		return ac;
	}

	/**
	 * initial an AnimationController with a listener
	 * @param onAnimateListener NOT NULL
	 * @return
	 */
	AnimationController init(OnAnimateListener onAnimateListener) {
		if (onAnimateListener == null) {
			throw new IllegalArgumentException("onAnimateListener can not be null");
		}
		this.mOnAnimateListener = onAnimateListener;
		return this;
	}

	private static class AnimationHandler extends Handler {
		@Override
		public void handleMessage(Message msg) {
			if (msg.what == ANI_WHAT) {
				if (msg.obj != null) {
					((Runnable) msg.obj).run();
				}
			}
		}
	}

	void startAnimation(int from, int to) {
		this.isAnimating = true;
		this.mFrom = from;
		this.mTo = to;
		this.mFrame = mVelocity;
		if (mTo > mFrom) {
			this.mFrame = Math.abs(this.mVelocity);
		} else if (mTo < mFrom) {
			this.mFrame = -Math.abs(this.mVelocity);
		} else {
			this.isAnimating = false;
			this.mOnAnimateListener.onAnimateComplete();
			return;
		}
		this.mOnAnimateListener.onAnimationStart();
		new RequireNextFrame().run();
	}

	void stopAnimation() {
		isAnimating = false;
	}

	/**
	 * configure the velocity of animation
	 * @param velocity a positive number
	 */
	public void setVelocity(int velocity) {
		if (velocity <= 0) {
			mVelocity = DEFAULT_VELOCITY;
			return;
		}
		mVelocity = velocity;
	}

	/**
	 * calculate next frame in child thread
	 * 
	 * @author kyleduo
	 *
	 */
	class RequireNextFrame implements Runnable {
		@Override
		public void run() {
			if (!isAnimating) {
				return;
			}
			calcNextFrame();
			mOnAnimateListener.onFrameUpdate(mFrame);
			if (mOnAnimateListener.continueAnimating()) {
				requireNextFrame();
			} else {
				stopAnimation();
				mOnAnimateListener.onAnimateComplete();
				return;
			}
		}

		private void calcNextFrame() {
//			mFrame = mVelocity;
		}

		private void requireNextFrame() {
			Message msg = mHandler.obtainMessage();
			msg.what = ANI_WHAT;
			msg.obj = this;
			mHandler.sendMessageDelayed(msg, DEFAULT_FRAME_DURATION);
		}
	}

	/**
	 * interface for view animation
	 * 
	 * @author kyle
	 *
	 */
	interface OnAnimateListener {
		/**
		 * invoked when the animation start
		 */
		public void onAnimationStart();

		/**
		 * ask view whether continue Animating
		 * 
		 * @return boolean true for continueAnimating
		 */
		public boolean continueAnimating();

		/**
		 * a new frame is ready.
		 * 
		 * @param frame next step of the animation, for linear animation, it is equal to velocity
		 */
		public void onFrameUpdate(int frame);

		/**
		 * invoked when the animation complete
		 */
		public void onAnimateComplete();
	}
}
