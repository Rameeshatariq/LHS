package com.example.cv.lhs;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cv.lhs.Adapter.PreviousActivities_Adapter;

import java.util.ArrayList;

public class Execution extends AppCompatActivity {
    ListView activities_List;
    String[][] mData;
    String activity_date;
    Context ctx = Execution.this;
    static ArrayList<String> arrayListDate = new ArrayList<>();
    static ArrayList<String> arrayListactivity = new ArrayList<>();
    static ArrayList<String> arrayListId = new ArrayList<>();
    static ArrayList<String> arrayListmonth = new ArrayList<>();
    static ArrayList<String> arrayListyear = new ArrayList<>();
    static  ArrayList<String> arrayListStatus = new ArrayList<>();

    PreviousActivities_Adapter adt;
    Spinner sp_hc_hw;
    String sp_data;
    ImageView iv_home;
    Lister ls;
    String[][] mData_search;

    TextView tv_record;
    LinearLayout ll_pbProgress;
    String temp_var = "null";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_execution);

        // activity_date=getIntent().getExtras().getString("date");.

        temp_var = "0";

        //ListView
        activities_List = (ListView) findViewById(R.id.lv);

        //TextView
        tv_record = findViewById(R.id.tv_record);


        //ImageView
        iv_home = (ImageView) findViewById(R.id.iv_home);
        iv_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Execution.this, MainActivity.class);
                startActivity(intent);
            }
        });

        //Linear Layout
        ll_pbProgress = findViewById(R.id.ll_pbProgress);

        ls = new Lister(ctx);
        ls.createAndOpenDB();

        //SPinner
        sp_hc_hw = (Spinner) findViewById(R.id.sp_hc_hw);

        spineer_hc_hw_data();

        adt = new PreviousActivities_Adapter(Execution.this, arrayListactivity, arrayListDate, arrayListId, arrayListmonth, arrayListyear,
                arrayListStatus);
        activities_List.setAdapter(adt);
        new Task().execute();

     /*   sp_hc_hw.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (sp_hc_hw.getSelectedItemPosition() == 0) {
                    try {
                        arrayListactivity.clear();
                        arrayListDate.clear();
                        arrayListId.clear();
                        arrayListmonth.clear();

                        mData = ls.executeReader("Select activity_id, health_centre, health_worker, activity_date, activity_month from Activities");
                        Log.d("000321", "onClick: " + mData);
                        for (int k = 0; k < mData.length; k++) {
                            arrayListactivity.add(mData[k][1] + "" + mData[k][2]);
                            arrayListDate.add(mData[k][3]);
                            arrayListId.add(mData[k][0]);
                            arrayListmonth.add(mData[k][4]);

                            Log.d("000321", "Query: " + mData);
                            Log.d("000321", "onClick: " + mData[k][2]);
                        }


                        adt = new PreviousActivities_Adapter(Execution.this, arrayListactivity, arrayListDate, arrayListId, arrayListmonth);
                        adt.notifyDataSetChanged();
                        activities_List.setAdapter(adt);

                    } catch (Exception e) {
                        Toast.makeText(ctx, "No Previous Activity ", Toast.LENGTH_SHORT).show();
                        Log.d("000321", "Catch: " + e);
                    }

                } else {
                    sp_data = sp_hc_hw.getSelectedItem().toString();
                    Log.d("00055", "onItemSelected: " + sp_data);
                    arrayListactivity.clear();
                    arrayListDate.clear();
                    arrayListId.clear();
                    arrayListmonth.clear();
                    try {
                        mData = ls.executeReader("Select activity_id, health_centre, health_worker, activity_date, activity_month from Activities" +
                                " where health = '" + sp_data + "' ");
                        Log.d("000321", "onClick: " + mData);
                        for (int k = 0; k < mData.length; k++) {
                            arrayListactivity.add(mData[k][1] + "" + mData[k][2]);
                            arrayListDate.add(mData[k][3]);
                            arrayListId.add(mData[k][0]);
                            arrayListmonth.add(mData[k][4]);

                            Log.d("000321", "Query: " + mData);
                            Log.d("000321", "onClick: " + mData[k][2]);
                        }


                        adt = new PreviousActivities_Adapter(Execution.this, arrayListactivity, arrayListDate, arrayListId, arrayListmonth);
                        adt.notifyDataSetChanged();
                        activities_List.setAdapter(adt);

                    } catch (Exception e) {
                        arrayListactivity.clear();
                        arrayListDate.clear();
                        arrayListId.clear();
                        arrayListmonth.clear();
                        Toast.makeText(ctx, "No Previous Activity for " + sp_data, Toast.LENGTH_SHORT).show();
                        Log.d("000321", "Catch: " + e);
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });*/

    }

    private void spineer_hc_hw_data() {
        final ArrayAdapter<CharSequence> adptr_council = ArrayAdapter.createFromResource(this, R.array.hw_hc_list, android.R.layout.simple_spinner_item);
        adptr_council.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);


        sp_hc_hw.setAdapter(
                new NothingSelectedSpinnerAdapter(
                        adptr_council,
                        R.layout.sp_layout,
                        // R.layout.contact_spinner_nothing_selected_dropdown, // Optional
                        this));


        sp_hc_hw.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (sp_hc_hw.getSelectedItemPosition() == 0) {
                    Log.d("000321", "spArea IF !!!!!!!!");
                } else {
                    sp_data = sp_hc_hw.getSelectedItem().toString();
                    SearchQueryData(sp_data);
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void SearchQueryData(String sp_data) {

        arrayListactivity.clear();
        arrayListDate.clear();
        arrayListId.clear();
        arrayListmonth.clear();
        arrayListyear.clear();
        arrayListStatus.clear();

        try {
            mData = ls.executeReader("Select activity_id, health_centre, health_worker, activity_date, activity_month, " +
                    "activity_year,complete_status from Activities" +
                    " where health = '" + sp_data + "' ");
            Log.d("000321", "onClick: " + mData.length);

            tv_record.setVisibility(View.GONE);

            for (int k = 0; k < mData.length; k++) {
                arrayListactivity.add(mData[k][1] + "" + mData[k][2]);
                arrayListDate.add(mData[k][3]);
                arrayListId.add(mData[k][0]);
                arrayListmonth.add(mData[k][4]);
                arrayListyear.add(mData[k][5]);
                arrayListStatus.add(mData[k][6]);

                Log.d("000321", "Query: " + mData);
                Log.d("000321", "onClick: " + mData[k][2]);

                adt.notifyDataSetChanged();
            }

        } catch (Exception e) {
            arrayListactivity.clear();
            arrayListDate.clear();
            arrayListId.clear();
            arrayListmonth.clear();
            arrayListyear.clear();
            arrayListStatus.clear();

            adt.notifyDataSetChanged();
            tv_record.setText("No Previous Activity for " + sp_data);
            tv_record.setVisibility(View.VISIBLE);

            // Toast.makeText(ctx, "No Previous Activity for "+sp_data, Toast.LENGTH_SHORT).show();
            Log.d("000321", "Catch: " + e);
        }


    }


    class Task extends AsyncTask<String, Integer, Boolean> {
        @Override
        protected void onPreExecute() {
            Log.d("000321", "ON PREEEEE: ");

            activities_List.setVisibility(View.GONE);
            ll_pbProgress.setVisibility(View.VISIBLE);
            super.onPreExecute();
        }

        @Override
        protected Boolean doInBackground(String... params) {
            try {
                Log.d("000321", "ON BACkGROUND: ");
                read_all_data();
                Thread.sleep(1500);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Boolean result) {
            Log.d("000321", "ON EXECUTE: ");
            ll_pbProgress.setVisibility(View.GONE);

            if (temp_var.equalsIgnoreCase("1")) {

                activities_List.setVisibility(View.VISIBLE);
                tv_record.setVisibility(View.GONE);
            } else {
                tv_record.setVisibility(View.VISIBLE);
            }

            adt.notifyDataSetChanged();
            super.onPostExecute(result);
        }

    }

    private void read_all_data() {


        arrayListactivity.clear();
        arrayListDate.clear();
        arrayListId.clear();
        arrayListmonth.clear();
        arrayListyear.clear();
        arrayListStatus.clear();

        try {

            mData = ls.executeReader("Select activity_id, health_centre, health_worker, activity_date, activity_month, activity_year," +
                    "complete_status from Activities");
            Log.d("000321", "onClick: " + mData.length);

            if (mData != null) {
                temp_var = "1";

                for (int k = 0; k < mData.length; k++) {
                    arrayListactivity.add(mData[k][1] + "" + mData[k][2]);
                    arrayListDate.add(mData[k][3]);
                    arrayListId.add(mData[k][0]);
                    arrayListmonth.add(mData[k][4]);
                    arrayListyear.add(mData[k][5]);
                    arrayListStatus.add(mData[k][6]);

                    Log.d("000321", "Query: " + mData);
                    Log.d("000321", "onClick: " + mData[k][2]);
                }
            }

//            adt = new PreviousActivities_Adapter(Execution.this, arrayListactivity, arrayListDate,arrayListId,arrayListmonth);
//            adt.notifyDataSetChanged();
//            activities_List.setAdapter(adt);

        } catch (Exception e) {
            Toast.makeText(ctx, "No Previous Activity ", Toast.LENGTH_SHORT).show();
            Log.d("000321", "Catch: " + e);
            tv_record.setText("No Previous Activity ");
            tv_record.setVisibility(View.VISIBLE);
        }

    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}


