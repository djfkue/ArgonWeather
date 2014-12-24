package com.argonmobile.weather;

import android.support.v4.util.Pair;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by argon on 12/24/14.
 */
public class WeatherCardAdapter extends RecyclerView.Adapter<WeatherCardAdapter.ViewHolder> {
    private String[] mDataset;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        // each data item is just a string in this case
        public ViewHolder(View v) {
            super(v);
        }

        @Override
        public void onClick(View v) {
            DetailActivity.launch((ActionBarActivity) v.getContext(),
                    Pair.create(v.findViewById(R.id.weather_banner), DetailActivity.EXTRA_IMAGE),
                    Pair.create(v.findViewById(R.id.text), DetailActivity.EXTRA_CITY));
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

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return 3;
    }
}