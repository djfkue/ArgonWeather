package com.argonmobile.weather;

import android.os.Bundle;
import android.support.v4.util.Pair;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends ActionBarActivity {

    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private DrawerLayout mDrawer;
    private WeatherCardAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        toolbar.setNavigationIcon(R.drawable.ic_ab_drawer);

        mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setClickable(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        // specify an adapter (see also next example)
        mAdapter = new WeatherCardAdapter(null);
        mRecyclerView.setAdapter(mAdapter);

        mDrawer = (DrawerLayout) findViewById(R.id.drawer_layout);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawer.openDrawer(Gravity.START);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private class WeatherCardAdapter extends RecyclerView.Adapter<WeatherCardAdapter.ViewHolder> {
        private String[] mDataset;

        // Provide a reference to the views for each data item
        // Complex data items may need more than one view per item, and
        // you provide access to all the views for a data item in a view holder
        public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

            public ImageView mWeatherBanner;
            public ImageView mWeatherIcon;
            public TextView mCityName;
            public TextView mCurrentTemp;
            public TextView mHighTemp;
            public TextView mLowTemp;

            // each data item is just a string in this case
            public ViewHolder(View v) {
                super(v);
                mWeatherBanner = (ImageView) v.findViewById(R.id.weather_banner);
                mCityName = (TextView) v.findViewById(R.id.text);
                mCurrentTemp = (TextView) v.findViewById(R.id.current_temp);
                mHighTemp = (TextView) v.findViewById(R.id.current_high_temp);
                mLowTemp = (TextView) v.findViewById(R.id.current_low_temp);
                mWeatherIcon = (ImageView) v.findViewById(R.id.weather_icon);
            }

            @Override
            public void onClick(View v) {
                DetailActivity.launch((ActionBarActivity) v.getContext(),
                        Pair.create(v.findViewById(R.id.weather_banner), DetailActivity.EXTRA_IMAGE),
                        Pair.create(v.findViewById(R.id.text), DetailActivity.EXTRA_CITY),
                        Pair.create(((ActionBarActivity)v.getContext()).findViewById(R.id.toolbar),
                                "toolbar"));
            }
        }

        // Provide a suitable constructor (depends on the kind of dataset)
        public WeatherCardAdapter(String[] myDataset) {
            mDataset = myDataset;
        }

        // Create new views (invoked by the layout manager)
        @Override
        public WeatherCardAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                                int viewType) {
            // create a new view
            View v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.weather_card, parent, false);

            ViewHolder vh = new ViewHolder(v);
            v.setOnClickListener(vh);
            return vh;
        }

        // Replace the contents of a view (invoked by the layout manager)
        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            // - get element from your dataset at this position
            // - replace the contents of the view with that element
            //holder.mTextView.setText(mDataset[position]);
            switch (position) {
                case 0:
                    holder.mWeatherBanner.setImageResource(R.drawable.new_york_night);
                    holder.mCurrentTemp.setText("4" + "\u00B0");
                    holder.mLowTemp.setText("\u2193" + "0" + "\u00B0");
                    holder.mHighTemp.setText("\u2191" + "7" + "\u00B0");
                    holder.mCityName.setText("New York");
                    holder.mWeatherIcon.setImageResource(R.drawable.art_clear);
                    break;
                case 1:
                    holder.mWeatherBanner.setImageResource(R.drawable.berlin_day);
                    holder.mCurrentTemp.setText("-4" + "\u00B0");
                    holder.mLowTemp.setText("\u2193" + "-4" + "\u00B0");
                    holder.mHighTemp.setText("\u2191" + "-2" + "\u00B0");
                    holder.mCityName.setText("Berlin");
                    holder.mWeatherIcon.setImageResource(R.drawable.art_snow);
                    break;
                default:
                    break;
            }
        }

        // Return the size of your dataset (invoked by the layout manager)
        @Override
        public int getItemCount() {
            return 3;
        }
    }
}
