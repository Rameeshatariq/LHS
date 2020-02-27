package com.akdndhrc.hayat.lhs;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.akdndhrc.hayat.lhs.Adapter.Activities_Adapter;
import com.akdndhrc.hayat.lhs.Adapter.PreviousActivities_Adapter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class CompleteActivities_Fragment extends Fragment {
    ListView activities_List;
    static ArrayList<String> arrayListDate = new ArrayList<>();
    static ArrayList<String> arrayListactivity = new ArrayList<>();
    static ArrayList<String> arrayListId = new ArrayList<>();
    static ArrayList<String> arrayListmonth = new ArrayList<>();
    static ArrayList<String> arrayListyear = new ArrayList<>();
    static ArrayList<String> arrayListstatus = new ArrayList<>();

    PreviousActivities_Adapter adt;
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

        SimpleDateFormat dates = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();
        TodayDate = dates.format(c.getTime());

        activities_List=rootView.findViewById(R.id.activities_list);

        try {
            Lister ls = new Lister(getActivity());
            ls.createAndOpenDB();
            arrayListactivity.clear();
            arrayListDate.clear();
            arrayListId.clear();
            arrayListmonth.clear();
            arrayListyear.clear();
            arrayListstatus.clear();

            mData = ls.executeReader("Select activity_id, health_centre, health_worker, activity_date, activity_month, " +
                    "activity_year, complete_status from Activities " +
                    " where complete_status = 1 ");
            Log.d("000555", "onClick: " + mData);
            for (int i = 0; i < mData.length; i++) {
                arrayListactivity.add(mData[i][1] + "" + mData[i][2]);
                arrayListDate.add(mData[i][3]);
                arrayListId.add(mData[i][0]);
                arrayListmonth.add(mData[i][4]);
                arrayListyear.add(mData[i][5]);
                arrayListstatus.add(mData[i][6]);

                Log.d("000555", "onClick: " + mData[i][3]);
            }


            adt = new PreviousActivities_Adapter(getActivity(), arrayListactivity, arrayListDate, arrayListId,arrayListmonth, arrayListyear,arrayListstatus);
            adt.notifyDataSetChanged();
            activities_List.setAdapter(adt);

        } catch (Exception e) {
            //  Toast.makeText(getActivity(), "No Activity for Today: " + e, Toast.LENGTH_SHORT).show();
            Log.d("000555", "Catch: " + e);
        }


        return rootView;
    }
}


