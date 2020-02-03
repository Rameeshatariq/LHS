package com.akdndhrc.hayat.lhs;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.akdndhrc.hayat.lhs.Adapter.Activities_Adapter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class TodayActivities_Fragment extends Fragment {
    public static ListView activities_List;
    static ArrayList<String> arrayListDate = new ArrayList<>();
    static ArrayList<String> arrayListactivity = new ArrayList<>();
    static ArrayList<String> arrayListId = new ArrayList<>();
    static ArrayList<String> arrayListmonth = new ArrayList<>();
    static  ArrayList<String> arrayListyear = new ArrayList<>();
    static  ArrayList<String> arrayListStatus = new ArrayList<>();

    Activities_Adapter adt;
    String mData[][];
    String TodayDate;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View rootView = inflater.inflate(R.layout.activities_list_layout, container, false);

        SimpleDateFormat dates = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
        Calendar c = Calendar.getInstance();
        TodayDate = dates.format(c.getTime());
        Log.d("000555", "Today Date: " + TodayDate);

        Lister ls = new Lister(getActivity());
        ls.createAndOpenDB();

        activities_List=rootView.findViewById(R.id.activities_list);

        try {

            arrayListactivity.clear();
            arrayListDate.clear();
            arrayListId.clear();
            arrayListmonth.clear();
            arrayListyear.clear();
            arrayListStatus.clear();

            mData = ls.executeReader("Select health_centre, health_worker,activity_date, activity_id, activity_month, " +
                    "activity_year, complete_status from Activities where activity_date = '" + TodayDate + "' ");
            Log.d("000555", "onClick: " + mData);
            for (int i = 0; i < mData.length; i++) {
                arrayListactivity.add(mData[i][0] + "" + mData[i][1]);
                arrayListDate.add(mData[i][2]);
                arrayListId.add(mData[i][3]);
                arrayListmonth.add(mData[i][4]);
                arrayListyear.add(mData[i][5]);
                arrayListStatus.add(mData[i][6]);
                Log.d("000555", "onClick: " + mData[i][2]);
            }


            adt = new Activities_Adapter(getActivity(), arrayListactivity, arrayListDate,arrayListId, arrayListmonth,arrayListyear,arrayListStatus);
            adt.notifyDataSetChanged();
            activities_List.setAdapter(adt);

        } catch (Exception e) {
           // Toast.makeText(getActivity(), "No Activity for Today: " + e, Toast.LENGTH_SHORT).show();
            Log.d("000555", "Catch: " + e);
        }


        return rootView;
    }
}
