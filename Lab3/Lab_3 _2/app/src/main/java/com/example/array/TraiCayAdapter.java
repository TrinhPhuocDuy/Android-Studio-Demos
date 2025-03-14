package com.example.array;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class TraiCayAdapter extends BaseAdapter {
    private final Context context;
    private final int layout;
    private final List<TraiCay> traiCayList; // Use traiCayList instead of trayCayList
    private int selectedIndex = -1; // Variable to track the selected index

    public TraiCayAdapter(Context context, int layout, List<TraiCay> traiCayList) {
        this.context = context;
        this.layout = layout;
        this.traiCayList = traiCayList; // Corrected variable name
    }

    public void setSelectedIndex(int index) {
        this.selectedIndex = index;
    }

    @Override
    public int getCount() {
        return traiCayList.size(); // Corrected variable name
    }

    @Override
    public Object getItem(int position) {
        return traiCayList.get(position); // Corrected variable name
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(layout, null);

        // Find views by ID
        TextView ten = convertView.findViewById(R.id.ten);
        TextView mota = convertView.findViewById(R.id.mota);
        ImageView anh = convertView.findViewById(R.id.anh);

        // Get the current TraiCay object
        TraiCay traiCay = traiCayList.get(position);

        // Set the values for the current item
        ten.setText(traiCay.getTen());
        mota.setText(traiCay.getMota());

        if (position == selectedIndex) {
            convertView.setBackgroundColor(context.getResources().getColor(R.color.selected_item)); // Change this color to your desired one
        } else {
            convertView.setBackgroundColor(context.getResources().getColor(R.color.default_item)); // Default color for non-selected items
        }

        // Check if there's a Bitmap image. If not, use the drawable resource.
        if (traiCay.getBitmapImage() != null) {
            anh.setImageBitmap(traiCay.getBitmapImage());
        } else {
            anh.setImageResource(traiCay.getHinh());
        }

        return convertView;
    }

}





