package com.mickael.circleloading;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.LinearInterpolator;

public class LoaderStrokes extends View {
    /**
     * This is the position for the Shadow behind the strokes
     */
    private final static int SHADOW_POSITION = 2;
    /**
     * This is the min value for the size of the strokes
     */
    private int minDegree = 10;
    /**
     * This is the max value for the size of the strokes
     */
    private int maxDegree = 190;
    /**
     * This variable is true when the strokes become bigger and false when the stroke become smaller
     */
    private boolean isGrowing = true;
    /**
     * This variable is true when the loader is activated and false when it's stopped.
     */
    private boolean isStart = false;

    // GRAPHIC
    private Paint paint;
    /**
     * This is the external stroke
     */
    private RectF loadingRectOut;
    /**
     * This is the internal stroke
     */
    private RectF loadingRectIn;
    /**
     * This is the external stroke shadow
     */
    private RectF loadingRectShadowOut;
    /**
     * This is the internal stroke shadow
     */
    private RectF loadingRectShadowIn;

    /**
     * This is the time to do one rotate
     */
    private int rotationDuration;
    /**
     * This is the duration for resize the stroke from 10 to 160
     */
    private float arcAnimationDuration;
    /**
     * This variable is the width of the strokes and the shadows
     */
    private int strokeWidth;
    /**
     * This is the color used for the stroke (loader)
     */
    private int color;
    /**
     * This is the current size of the strokes
     */
    private float arcSize;


    public LoaderStrokes(Context context) {
        super(context);
    }

    public LoaderStrokes(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public LoaderStrokes(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    /**
     * This function is called to initialize the loader. The color, strokeWidth, rotationAnimation,
     * arcAnimationDuration and the arkSize are set and the strokes are initialized.
     * @param color                 This is the strokes color
     * @param strokeWidth           This is the width of the strokes
     * @param rotationDuration      This is the time to do one turn. This time is used to initialize
     *                              the stroke animation duration.
     */
    public void initializeView(int color, int strokeWidth, int rotationDuration) {
        this.color = color;
        this.strokeWidth = strokeWidth;
        this.rotationDuration = rotationDuration;
        this.arcAnimationDuration = (float) rotationDuration / 4;
        this.arcSize = minDegree;

        this.paint = new Paint();
        this.paint.setColor(this.color);
        this.paint.setAntiAlias(true);
        this.paint.setStyle(Paint.Style.STROKE);
        this.paint.setStrokeWidth(this.strokeWidth);
        this.paint.setStrokeCap(Paint.Cap.ROUND);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        this.loadingRectOut = new RectF(2 * this.strokeWidth, 2 * this.strokeWidth,
                w - 2 * this.strokeWidth, h - 2 * this.strokeWidth);
        this.loadingRectIn = new RectF(4 * this.strokeWidth, 4 * this.strokeWidth,
                w - 4 * this.strokeWidth, h - 4 * this.strokeWidth);
        this.loadingRectShadowOut = new RectF(2 * this.strokeWidth + SHADOW_POSITION, 2 * this.strokeWidth + SHADOW_POSITION,
                w - 2 * this.strokeWidth + SHADOW_POSITION, h - 2 * this.strokeWidth + SHADOW_POSITION);
        this.loadingRectShadowIn = new RectF(4 * this.strokeWidth + SHADOW_POSITION, 4 * this.strokeWidth + SHADOW_POSITION,
                w - 4 * this.strokeWidth + SHADOW_POSITION, h - 4 * this.strokeWidth + SHADOW_POSITION);
    }


    /**
     * This function is called to update the strokes size from 10 to 160 or 160 to 10. If the stroke
     * is  bigger than 160 then the variable [isGrowing] is set to false and the strokes will become
     * smaller to 10.
     * @param canvas    Only used to send to the super function.
     */
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (this.isStart) {
            this.paint.setColor(Color.parseColor("#42464c"));
            canvas.drawArc(this.loadingRectShadowOut, this.minDegree, this.arcSize, false, this.paint);
            canvas.drawArc(this.loadingRectShadowIn, this.maxDegree, this.arcSize, false, this.paint);

            this.paint.setColor(this.color);
            canvas.drawArc(this.loadingRectOut, this.minDegree, this.arcSize, false, this.paint);
            canvas.drawArc(this.loadingRectIn, this.maxDegree, this.arcSize, false, this.paint);

            this.minDegree += this.rotationDuration;
            this.maxDegree -= this.rotationDuration / 1.2f;
            if (this.minDegree > 360) {
                this.minDegree = this.minDegree - 360;
            }
            if (this.maxDegree < 360) {
                this.maxDegree = this.maxDegree + 360;
            }

            if (this.isGrowing && this.arcSize < 160) {
                this.arcSize += this.arcAnimationDuration;
            } else if (!this.isGrowing && this.arcSize > this.rotationDuration) {
                this.arcSize -= 2 * this.arcAnimationDuration;
            }
            if (this.arcSize >= 160 || this.arcSize <= 10) {
                this.isGrowing = !this.isGrowing;
            }
            invalidate();
        }
    }


    /**
     * Start the loader animation is the loader is stopped. Call the start animation and
     */
    public void start() {
        if (!this.isStart) {
            startAnimator();
            this.isStart = true;
            invalidate();
        }
    }

    /**
     * Stop the loader if the loader is running. Call the stop animation and the invalidate function
     * to update the loader graphic.
     */
    public void stop() {
        if (this.isStart) {
            stopAnimator();
            invalidate();
        }
    }

    /**
     * This function is called when the loader is starting. That scale the loader from 0 to 1.
     */
    private void startAnimator() {
        ObjectAnimator scaleXAnimator = ObjectAnimator.ofFloat(this, "scaleX", 0.0f, 1);
        ObjectAnimator scaleYAnimator = ObjectAnimator.ofFloat(this, "scaleY", 0.0f, 1);
        scaleXAnimator.setDuration(300);
        scaleXAnimator.setInterpolator(new LinearInterpolator());
        scaleYAnimator.setDuration(300);
        scaleYAnimator.setInterpolator(new LinearInterpolator());
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(scaleXAnimator, scaleYAnimator);
        animatorSet.start();
    }

    /**
     * This function is called when the loader is stopping. That scale the loader to 0 and set the
     * [isStart] variable to false when the animation is finished.
     */
    private void stopAnimator() {
        ObjectAnimator scaleXAnimator = ObjectAnimator.ofFloat(this, "scaleX", 1, 0);
        ObjectAnimator scaleYAnimator = ObjectAnimator.ofFloat(this, "scaleY", 1, 0);
        scaleXAnimator.setDuration(300);
        scaleXAnimator.setInterpolator(new LinearInterpolator());
        scaleYAnimator.setDuration(300);
        scaleYAnimator.setInterpolator(new LinearInterpolator());
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(scaleXAnimator, scaleYAnimator);
        animatorSet.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                // Not used
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                isStart = false;
            }

            @Override
            public void onAnimationCancel(Animator animation) {
                // Not used
            }

            @Override
            public void onAnimationRepeat(Animator animation) {
                // Not used
            }
        });
        animatorSet.start();
    }


    // SETTERS AND GETTERS
    public void setLoadingColor(int color) {
        this.color = color;
    }

    public void setRotationDuration(int rotationDuration) {
        this.rotationDuration = rotationDuration;
    }

    public void setStrokeWidth(int width) {
        this.strokeWidth = width;
    }

    public boolean isStart() {
        return this.isStart;
    }
}
