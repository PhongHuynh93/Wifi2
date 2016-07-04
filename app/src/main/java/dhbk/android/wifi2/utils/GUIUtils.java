package dhbk.android.wifi2.utils;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.support.annotation.ColorRes;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.animation.LinearOutSlowInInterpolator;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import dhbk.android.wifi2.R;
import dhbk.android.wifi2.interfaces.OnRevealAnimationListener;

// class contain animation when transction between fragment or activity
public class GUIUtils {

    // We have context that is used to retrieve color from @ColorRes value, duration of the animation from resources.
    // We have centerX and centerY parameter,
    //  rootView that we are making circular reveal on
    // custom listener to communicate between the AnimatorListener and our Activity.
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public static void animateRevealShow(final Context ctx, final View view, final int startRadius,
                                         @ColorRes final int color, int x, int y, final OnRevealAnimationListener listener) {
        float finalRadius = (float) Math.hypot(view.getWidth(), view.getHeight());

        // create the animator for this view
        // the start radius is center point
        // the final radius if full screen.
        Animator anim = ViewAnimationUtils.createCircularReveal(view, x, y, startRadius, finalRadius);
        anim.setDuration(ctx.getResources().getInteger(R.integer.animation_duration));
        anim.setStartDelay(100);
        anim.setInterpolator(new AccelerateDecelerateInterpolator());
        anim.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                // the circular get bigger, we'll see this "color" background.
                view.setBackgroundColor(ContextCompat.getColor(ctx, color));
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                // after anim ends, we dont see the "color" background anymore but see the color background of the view because it's now Visible
                view.setVisibility(View.VISIBLE);
                listener.onRevealShow();
            }
        });
        anim.start();
    }


    // : 6/12/16 15 After clicking back pressed button I like to hide circular reveal animation.
    // The startRadius is a full width of view, the final radius is the FloatingActionButton width divided by 2.
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public static void animateRevealHide(final Context ctx, final View parentView, final View mainContentView, @ColorRes final int color,
                                         final int finalRadius, final OnRevealAnimationListener listener) {
        int cx = (parentView.getLeft() + parentView.getRight()) / 2;
        int cy = (parentView.getTop() + parentView.getBottom()) / 2;
        int initialRadius = parentView.getWidth();
//
////        because this child view if not center so we use parentview to center it
//        final View view = parentView.findViewById(R.id.main_content_show_wifi_detail);

//        make circular from full width to the width of fab
        Animator anim = ViewAnimationUtils.createCircularReveal(mainContentView, cx, cy, initialRadius, finalRadius);
        anim.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
                mainContentView.setBackgroundColor(ContextCompat.getColor(ctx, color));
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                mainContentView.setVisibility(View.INVISIBLE);
                listener.onRevealHide();
            }
        });

        anim.setDuration(ctx.getResources().getInteger(R.integer.animation_duration));
        anim.start();
    }

    public static void startEnterTransitionSlideUp(Context ctx, View... views) {
        Animation slideAnimationUp = AnimationUtils.loadAnimation(ctx, R.anim.abc_slide_in_bottom);
        slideAnimationUp.setDuration(300);
        slideAnimationUp.setInterpolator(new LinearOutSlowInInterpolator());
        slideAnimationUp.setAnimationListener(getShowAnimationListener(null, views));
        startAnimations(slideAnimationUp, views);
    }

    public static void startEnterTransitionSlideDown(Context ctx, View... views) {
        Animation slideAnimationDown = AnimationUtils.loadAnimation(ctx, R.anim.abc_slide_in_top);
        slideAnimationDown.setDuration(300);
        slideAnimationDown.setInterpolator(new LinearOutSlowInInterpolator());
        slideAnimationDown.setAnimationListener(getShowAnimationListener(null, views));
        startAnimations(slideAnimationDown, views);
    }

    public static void startReturnTransitionSlideDown(Context ctx, OnReturnAnimationFinished listener, View... views) {
        Animation slideAnimation = AnimationUtils.loadAnimation(ctx, R.anim.slide_out_bottom);
        slideAnimation.setDuration(300);
        slideAnimation.setInterpolator(new AccelerateDecelerateInterpolator());
        slideAnimation.setAnimationListener(getGoneAnimationListener(listener, views));
        startAnimations(slideAnimation, views);
    }

    public static void startReturnTransitionSlideUp(Context ctx, OnReturnAnimationFinished listener, View... views) {
        Animation slideAnimationUp = AnimationUtils.loadAnimation(ctx, R.anim.slide_out_top);
        slideAnimationUp.setDuration(300);
        slideAnimationUp.setInterpolator(new AccelerateDecelerateInterpolator());
        slideAnimationUp.setAnimationListener(getGoneAnimationListener(listener, views));
        startAnimations(slideAnimationUp, views);
    }

    public static void startScaleUpAnimation(Context ctx, View... views) {
        Animation scaleAnimation = AnimationUtils.loadAnimation(ctx, R.anim.scale_up);
        scaleAnimation.setDuration(300);
        scaleAnimation.setInterpolator(new AccelerateDecelerateInterpolator());
        scaleAnimation.setAnimationListener(getShowAnimationListener(null, views));
        startAnimations(scaleAnimation, views);
    }

    public static void startScaleDownAnimation(Context ctx, View... views) {
        Animation scaleAnimation = AnimationUtils.loadAnimation(ctx, R.anim.scale_down);
        scaleAnimation.setDuration(300);
        scaleAnimation.setInterpolator(new AccelerateDecelerateInterpolator());
        scaleAnimation.setAnimationListener(getGoneAnimationListener(null, views));
        startAnimations(scaleAnimation, views);
    }

    private static Animation.AnimationListener getGoneAnimationListener(final OnReturnAnimationFinished listener, final View... views) {
        return new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                for (View v : views) {
                    v.setVisibility(View.INVISIBLE);
                }
                if (listener != null) {
                    listener.onAnimationFinished();
                }
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        };
    }

    private static Animation.AnimationListener getShowAnimationListener(final OnReturnAnimationFinished listener, final View... views) {
        return new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                for (View v : views) {
                    v.setVisibility(View.VISIBLE);
                }
                if (listener != null) {
                    listener.onAnimationFinished();
                }
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        };
    }

    private static void startAnimations(Animation animation, View... views) {
        for (View v : views) {
            v.startAnimation(animation);
        }
    }

    public interface OnReturnAnimationFinished {
        void onAnimationFinished();
    }
}
