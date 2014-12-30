package com.argonmobile.weather;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.graphics.Color;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.SharedElementCallback;
import android.support.v4.util.Pair;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPropertyAnimatorCompat;
import android.support.v4.view.ViewPropertyAnimatorListener;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewTreeObserver;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;


public class DetailActivity extends ActionBarActivity {

    public static final String EXTRA_IMAGE = "DetailActivity:weather_banner";
    public static final String EXTRA_CITY = "DetailActivity:city_name";

    public static void launch(ActionBarActivity activity, Pair<View, String>... sharedElements) {
        ActivityOptionsCompat options =
                ActivityOptionsCompat.makeSceneTransitionAnimation(
                        activity, sharedElements);
        Intent intent = new Intent(activity, DetailActivity.class);
//        intent.putExtra(EXTRA_IMAGE, url);
        ActivityCompat.startActivity(activity, intent, options.toBundle());
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        getWindow().setAllowEnterTransitionOverlap(true);

        setContentView(R.layout.activity_detail);

        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        toolbar.setBackgroundColor(Color.TRANSPARENT);
        toolbar.bringToFront();

        ImageView image = (ImageView) findViewById(R.id.weather_banner);
        ViewCompat.setTransitionName(image, EXTRA_IMAGE);

        final TextView cityName = (TextView) findViewById(R.id.city_name);
        ViewCompat.setTransitionName(cityName, EXTRA_CITY);

        ViewCompat.setTransitionName(toolbar, "toolbar");

        final View containerView = findViewById(R.id.main_condition);
        containerView.setVisibility(View.INVISIBLE);
        ActivityCompat.setEnterSharedElementCallback(this, new SharedElementCallback() {
            @Override
            public void onSharedElementStart(List<String> sharedElementNames, List<View> sharedElements, List<View> sharedElementSnapshots) {
                super.onSharedElementStart(sharedElementNames, sharedElements, sharedElementSnapshots);
                Log.e("SD_TRACE", "share element start");
            }

            @Override
            public void onSharedElementEnd(List<String> sharedElementNames,
                                           List<View> sharedElements, List<View> sharedElementSnapshots) {
                super.onSharedElementEnd(sharedElementNames, sharedElements, sharedElementSnapshots);
                Log.e("SD_TRACE", "share element end");

                View containerView = findViewById(R.id.main_condition);
                //containerView.setAlpha(0.0f);
                containerView.setVisibility(View.VISIBLE);
                Animator animator = ViewAnimationUtils.createCircularReveal(
                        containerView,
                        containerView.getWidth() / 2,
                        0,
                        0,
                        (float) Math.hypot(containerView.getWidth()/2, containerView.getHeight()));

                animator.setInterpolator(new AccelerateDecelerateInterpolator());
                animator.setDuration(800);
                // Finally start the animation
                animator.start();
            }
        });
        ActivityCompat.postponeEnterTransition(this);

        containerView.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            @Override
            public boolean onPreDraw() {
                containerView.getViewTreeObserver().removeOnPreDrawListener(this);
                ActivityCompat.startPostponedEnterTransition(DetailActivity.this);
                return false;
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == android.R.id.home) {
            onBackPressed();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
