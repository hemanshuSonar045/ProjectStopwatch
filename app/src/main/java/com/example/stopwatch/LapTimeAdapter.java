package com.example.stopwatch;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import java.util.List;

public class LapTimeAdapter extends ArrayAdapter<String> {

    public LapTimeAdapter(Context context, List<String> lapTimes) {
        super(context, 0, lapTimes);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.lap_time_item, parent, false);
        }
        TextView lapTimeView = convertView.findViewById(R.id.lapTime);
        String lapTime = getItem(position);
        lapTimeView.setText(lapTime);
        return convertView;
    }
}