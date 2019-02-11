package com.mickael.simplecircleloading;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

/**
 * CircleLoaderView
 * Create by Calatraba Mickael on 2019/02/01
 */
public class CircleLoaderView extends FrameLayout {
    // DEFAULT OPTIONS
    /**
     * Default value for the background alpha when the loader is started.
     */
    private final static float DEFAULT_BACKGROUND_ALPHA = 0.7f;
    /**
     * Default value for the fade-in, fade-out and scales animations.
     */
    private final static int DEFAULT_ANIMATION_DURATION = 300;
    /**
     * Default width of the loader strokes.
     */
    private static final int DEFAULT_WIDTH = 10;
    /**
     * Default value of the rotation of the loader (strokes)
     */
    private static final int DEFAULT_ROTATION_SPEED = 10;

    // BACKGROUND OPTIONS
    /**
     * This is the alpha applied to the background
     */
    private float backgroundAlpha;

    // UI ELEMENTS
    /**
     * This is the background behind the loader.
     */
    private View background;
    /**
     * This is the loader object.
     */
    private LoaderStrokes loader;


    public CircleLoaderView(@NonNull Context context) {
        super(context);
        initialise(context, null);
    }

    public CircleLoaderView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initialise(context, attrs);
    }

    public CircleLoaderView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initialise(context, attrs);
    }

    /**
     * This function is called to initialise the View. The function initialise the Image, the
     * background and the FrameLayout used to translate the loader from the LEFT to the RIGHT. This
     * function get the possible argument from the view.
     * @param context   This is the current context from the constructor.
     * @param attrs     This is the possible attributes to configure the size, duration or color of
     *                  he loader
     */
    private void initialise(Context context, AttributeSet attrs) {
        LayoutInflater.from(context).inflate(R.layout.view_loader, this, true);

        this.background = (View) findViewById(R.id.view_background);
        this.loader = (LoaderStrokes) findViewById(R.id.circle_loader);

        if (attrs != null) {
            TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.CircleLoaderView);

            // Init of the UI elements
            final int rotateDuration = ta.getInt(R.styleable.CircleLoaderView_rotate_duration, DEFAULT_ROTATION_SPEED);
            final int loaderStrokeWidth = ta.getDimensionPixelSize(R.styleable.CircleLoaderView_loading_stroke_width, DEFAULT_WIDTH);
            final int colorLoader = ta.getColor(R.styleable.CircleLoaderView_loading_color, Color.WHITE);
            final int colorBackground = ta.getColor(R.styleable.CircleLoaderView_background_color, Color.BLUE);
            this.backgroundAlpha = ta.getFloat(R.styleable.CircleLoaderView_background_alpha, DEFAULT_BACKGROUND_ALPHA);
            this.loader.getLayoutParams().height = ta.getDimensionPixelSize(R.styleable.CircleLoaderView_loading_height, 200);
            this.loader.getLayoutParams().width = ta.getDimensionPixelSize(R.styleable.CircleLoaderView_loading_width, 200);
            this.loader.initializeView(colorLoader, loaderStrokeWidth, rotateDuration);
            this.background.setBackgroundColor(colorBackground);

            ta.recycle();
        }
        // Put the view before all the UI elements
        this.setTranslationZ(50);

        // Set all the UI element visible
        this.loader.setVisibility(GONE);
        this.background.setVisibility(GONE);
    }
    /**
     * This function is called to start the fadeOut animation, at the end of the animation, the
     * background visibility is set to GONE
     */
    public void fadeOutAnimation() {
        ObjectAnimator alphaAnimation = ObjectAnimator.ofFloat(this.background, View.ALPHA, this.backgroundAlpha, 0);
        alphaAnimation.setDuration(DEFAULT_ANIMATION_DURATION);
        alphaAnimation.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                // Not Used
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                background.setVisibility(GONE);
            }

            @Override
            public void onAnimationCancel(Animator animation) {
                // Not Used
            }

            @Override
            public void onAnimationRepeat(Animator animation) {
                // Not Used
            }
        });
        alphaAnimation.start();
    }

    /**
     * This function is called to start the fadeIn animation on the background
     */
    private void fadeInAnimation() {
        ObjectAnimator alphaAnimation = ObjectAnimator.ofFloat(this.background, View.ALPHA, 0, this.backgroundAlpha);
        alphaAnimation.setDuration(DEFAULT_ANIMATION_DURATION);
        alphaAnimation.start();
    }

    /**
     * This function is called to start the animation, the function set the visibility of all UI
     * elements to VISIBLE, start the rotating animation, start the fadeIn animation and start the
     * translate animation.
     */
    public void startLoading() {
        if (!this.loader.isStart()) {
            this.loader.setVisibility(VISIBLE);
            this.background.setVisibility(VISIBLE);
            fadeInAnimation();
            this.loader.start();
        }
    }

    /**
     * This function is called to stop the animation, the function start the translation to the
     * RIGHT of the screen and start the fadeOut animation.
     */
    public void stopLoading() {
        if (this.loader.isStart()) {
            this.loader.stop();
            fadeOutAnimation();
        }
    }

    // SETTERS AND GETTERS
    public void setBackgroundAlpha(float backgroundAlpha) {
        this.backgroundAlpha = backgroundAlpha;
    }

    public void setBackground(int color) {
        this.background.setBackgroundColor(color);
    }

    public void setLoadingColor(int color) {
        this.loader.setLoadingColor(color);
    }

    public void setRotationDuration(int rotationDuration) {
        this.loader.setRotationDuration(rotationDuration);
    }

    public void setStrokeWidth(int width) {
        this.loader.setStrokeWidth(width);
    }
}
