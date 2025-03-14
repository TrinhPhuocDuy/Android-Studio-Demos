package com.example.array;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class CustomAdapter extends ArrayAdapter<String> {
    private final Context context;
    private final ArrayList<String> items;
    private int selectedPosition = -1;  // Track the selected item position

    public CustomAdapter(Context context, ArrayList<String> items) {
        super(context, android.R.layout.simple_list_item_1, items);
        this.context = context;
        this.items = items;
    }

    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(android.R.layout.simple_list_item_1, parent, false);

        TextView textView = rowView.findViewById(android.R.id.text1);
        textView.setText(items.get(position));

        // Change background if this item is selected
        if (position == selectedPosition) {
            rowView.setBackgroundColor(context.getResources().getColor(R.color.selected_item));  // Use a color resource for selected item
        } else {
            rowView.setBackgroundColor(context.getResources().getColor(R.color.default_item));  // Use default color for non-selected items
        }

        return rowView;
    }

    public void setSelectedPosition(int position) {
        this.selectedPosition = position;
        notifyDataSetChanged();  // Notify the adapter to redraw the list
    }
}
