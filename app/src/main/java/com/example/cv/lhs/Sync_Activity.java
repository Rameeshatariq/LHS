package com.example.cv.lhs;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cv.lhs.Adapter.SyncActivities_Adapter;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class Sync_Activity extends AppCompatActivity {
    ListView activities_List;
    String[][] mData;
    String activity_date;
    Context ctx = Sync_Activity.this;
    static ArrayList<String> arrayListDate = new ArrayList<>();
    static ArrayList<String> arrayListactivity = new ArrayList<>();
    static ArrayList<String> arrayListId = new ArrayList<>();
    static ArrayList<String> arrayListmonth = new ArrayList<>();
    static ArrayList<String> arrayListyear = new ArrayList<>();
    SyncActivities_Adapter adt;
    Spinner sp_hc_hw;
    String sp_data;
    ImageView iv_home;
    Lister ls;
    String[][] mData_search;

    TextView tv_record;
    LinearLayout ll_pbProgress;
    String temp_var = "null";


    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sync_);


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
                Intent intent = new Intent(ctx, MainActivity.class);
                startActivity(intent);
            }
        });

        //Linear Layout
        ll_pbProgress = findViewById(R.id.ll_pbProgress);

        ls = new Lister(ctx);
        ls.createAndOpenDB();

        //SPinner

        adt = new SyncActivities_Adapter(ctx, arrayListactivity, arrayListDate, arrayListId, arrayListmonth, arrayListyear);
        activities_List.setAdapter(adt);
        new Task().execute();


        activities_List.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Log.d("000999", "onItemClick: ");
                Log.d("000999", "onItemClick: "+arrayListId.get(i));
                getPatientTool1(arrayListId.get(i));
            }
        });

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

        try {

            mData = ls.executeReader("Select activity_id, health_centre, health_worker, activity_date, activity_month, " +
                    "activity_year from Activities where complete_status = 1");
            Log.d("000321", "onClick: " + mData.length);

            if (mData != null) {
                temp_var = "1";

                for (int k = 0; k < mData.length; k++) {
                    arrayListactivity.add(mData[k][1] + "" + mData[k][2]);
                    arrayListDate.add(mData[k][3]);
                    arrayListId.add(mData[k][0]);
                    arrayListmonth.add(mData[k][4]);
                    arrayListyear.add(mData[k][5]);

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

    private JSONObject getPatientTool1(String activity_id) {

        try {


            ls = new Lister(ctx);
            ls.createAndOpenDB();
            JSONObject jObj = new JSONObject();
            String[][] mData = ls.executeReader("Select * from Microplanning where activity_id = '" + activity_id + "' ");
            String[][] mData1 = ls.executeReader("Select * from Immunization where activity_id = '" + activity_id + "' ");

            JSONObject jObjMain = new JSONObject();

            for (int i = 0; i < mData.length; i++) {
                for (int j = 0; j < mData[i].length; j++) {
                    Log.d("000999", "   mData[" + i + "][" + j + "]" + mData[i][j]);
                }

                jObj.put("activity_id", mData[i][0]);
                jObj.put("mp_q1", mData[i][1]);
                jObj.put("mp_q2", mData[i][2]);
                jObj.put("mp_q3", mData[i][3]);
                jObj.put("mp_q4", mData[i][4]);
                jObj.put("mp_count_yes", mData[i][5]);
                jObj.put("mp_count_no", mData[i][6]);
                jObj.put("mp_count_na", mData[i][7]);
            /*    jObj.put("im_q1", mData[i][8]);
                jObj.put("im_absent", mData[i][9]);
                jObj.put("im_q2", mData[i][10]);
                jObj.put("im_q3", mData[i][11]);
                jObj.put("im_q4", mData[i][12]);
                jObj.put("im_q5", mData[i][13]);
                jObj.put("im_q6", mData[i][14]);
                jObj.put("im_count_yes", mData[i][15]);
                jObj.put("im_count_yes", mData[i][16]);
                jObj.put("im_count_yes", mData[i][17]);
                jObj.put("ka_q1", mData[i][18]);
                jObj.put("ka_q2", mData[i][19]);
                jObj.put("ka_q3", mData[i][20]);
                jObj.put("ka_q4", mData[i][21]);
                jObj.put("ka_q5", mData[i][22]);
                jObj.put("ka_count_yes", mData[i][23]);
                jObj.put("ka_count_yes", mData[i][24]);
                jObj.put("ka_count_yes", mData[i][25]);*/


            }

            for (int i = 0; i < mData1.length; i++) {
                for (int j = 0; j < mData1[i].length; j++) {
                    Log.d("000999", "   mData[" + i + "][" + j + "]" + mData1[i][j]);
                }
                jObj.put("im_q1", mData1[i][1]);
                jObj.put("im_absent", mData1[i][2]);
                jObj.put("im_q2", mData1[i][3]);
                jObj.put("im_q3", mData1[i][4]);
                jObj.put("im_q4", mData1[i][5]);
                jObj.put("im_q5", mData1[i][6]);
                jObj.put("im_q6", mData1[i][7]);
                jObj.put("im_count_yes", mData1[i][8]);
                jObj.put("im_count_yes", mData1[i][9]);
                jObj.put("im_count_yes", mData1[i][10]);
            }

            JSONArray jArray = new JSONArray();
            jArray.put(jObj);

            jObjMain.put("response", jArray);
            Log.d("ABC", "getPatientData: " + jArray);

            return jObjMain;

        } catch (Exception e) {
            Log.d("000999", "Exception   " + e);

            return new JSONObject();
        }


    }
}


