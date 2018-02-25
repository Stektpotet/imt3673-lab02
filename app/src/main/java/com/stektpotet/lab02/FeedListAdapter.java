package com.stektpotet.lab02;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.stektpotet.lab02.parser.FeedEntry;

import java.util.List;

/**
 * Created by halvor on 25.02.18.
 */

public class FeedListAdapter extends ArrayAdapter<FeedEntry> {

    public FeedListAdapter(@NonNull Context context, int resource) {
        super(context,0, resource);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        // Get the data item for this position
        FeedEntry item = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.feed_list_item, parent, false);
        }
        // Lookup view for data population
        TextView title = (TextView) convertView.findViewById(R.id.feed_item_title);
        TextView preview = (TextView) convertView.findViewById(R.id.feed_item_preview);
        // Populate the data into the template view using the data object
        title.setText(item.title);
        preview.setText(item.link);
        // Return the completed view to render on screen
        return convertView;
    }
}
