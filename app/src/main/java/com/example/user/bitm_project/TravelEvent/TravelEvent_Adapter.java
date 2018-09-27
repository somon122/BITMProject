package com.example.user.bitm_project.TravelEvent;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.user.bitm_project.R;

import java.util.ArrayList;

public class TravelEvent_Adapter extends ArrayAdapter<TravelEvents>{

    ArrayList<TravelEvents>travelEventList = new ArrayList<>();
    Context mContext;


    public TravelEvent_Adapter(@NonNull Context context, @NonNull ArrayList<TravelEvents>travelEventList) {
        super(context, R.layout.tavel_events_show_custom, travelEventList);
        this.travelEventList = travelEventList;
        mContext = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {


        LayoutInflater inflater = LayoutInflater.from(mContext);
        convertView = inflater.inflate(R.layout.tavel_events_show_custom,parent,false);

        TextView destination = convertView.findViewById(R.id.travelShowDestination_id);
        TextView budget = convertView.findViewById(R.id.travelShowBudget_id);
        TextView fromDate = convertView.findViewById(R.id.travelShow_FromDate_id);
        TextView toDate = convertView.findViewById(R.id.travelShow_ToDate_id);

        TravelEvents events = travelEventList.get(position);

        destination.setText(events.getDistination());
        budget.setText(events.getBudget());
        fromDate.setText(events.getFromDate());
        toDate.setText(events.getToDate());


        return convertView;
    }
}
