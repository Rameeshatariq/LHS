package com.akdndhrc.hayat.lhs;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.akdndhrc.hayat.lhs.Adapter.SyncActivities_Adapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

public class Sync_Activity extends AppCompatActivity {
    ListView activities_List;
    String[][] mData;
    String activity_date;
    Context ctx = Sync_Activity.this;
    Integer type;
    String added_on,added_by;
    JSONObject jobj;
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
    Bitmap bitmap = null;
    private RequestQueue rQueue;
    String TodayDate;
    TextView tv_record;
    LinearLayout ll_pbProgress;
    String temp_var = "null";
    Dialog alertDialog;
    ProgressBar pb;
    TextView tvTitle;
    ImageView iv_success;

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

        SimpleDateFormat dates = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();
        TodayDate = dates.format(c.getTime());

        added_by=MainActivity.login_useruid;
        added_on= String.valueOf(System.currentTimeMillis());

        try {
            jobj= new JSONObject();
            jobj.put("added_on",added_on);
            jobj.put("added_by",added_by);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        ls = new Lister(ctx);
        ls.createAndOpenDB();

        //SPinner

        uploadList();
       // adt = new SyncActivities_Adapter(ctx, arrayListactivity, arrayListDate, arrayListId, arrayListmonth, arrayListyear);
       // activities_List.setAdapter(adt);
       // new Task().execute();


        activities_List.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, final int i, long l) {
                Log.d("000999", "onItemClick: ");
                Log.d("000999", "onItemClick: "+arrayListId.get(i));

                alertDialog = new Dialog(ctx);
                LayoutInflater layout = LayoutInflater.from(ctx);
                final View dialogView = layout.inflate(R.layout.refreshing_dialog, null);

                alertDialog.setContentView(dialogView);
                alertDialog.setCanceledOnTouchOutside(false);
                alertDialog.setCancelable(false);
                alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                alertDialog.show();



                pb = dialogView.findViewById(R.id.pbProgress);
                iv_success = dialogView.findViewById(R.id.iv_success);
                tvTitle = dialogView.findViewById(R.id.tvTitle);
                tvTitle.setText("Syncing");

                Handler handler0 = new Handler();
                handler0.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        //  sendPostRequestCvacination(holder,pos, uid, activity_month.get(pos), activity_year.get(pos));
                        syncChecklist(arrayListId.get(i));
                    }
                }, 2500);

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

    private void uploadList() {
        adt = new SyncActivities_Adapter(ctx, arrayListactivity, arrayListDate, arrayListId, arrayListmonth, arrayListyear);
        activities_List.setAdapter(adt);
        new Task().execute();
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
                    "activity_year from Activities where is_synced = 0 OR is_synced_checklist = 0 OR is_synced_validation = 0");
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


    private void syncChecklist(String activity_id) {
        ls = new Lister(ctx);
        ls.createAndOpenDB();

        try {

            String[][] activity= ls.executeReader("Select is_synced_checklist from Activities where activity_id = '"+activity_id+"' ");
            if (activity[0][0].equals("1")) {

            }

            else if (activity[0][0].equals("0")) {

                String[][] mData = ls.executeReader("Select * from Question1 where activity_id = '" + activity_id + "' ");
                String[][] mData1 = ls.executeReader("Select * from Question2 where activity_id = '" + activity_id + "' ");
                String[][] mData2 = ls.executeReader("Select * from Question3 where activity_id = '" + activity_id + "' ");
                String[][] mData3 = ls.executeReader("Select * from Question4 where activity_id = '" + activity_id + "' ");
                String[][] mData4 = ls.executeReader("Select * from Question5 where activity_id = '" + activity_id + "' ");
                String[][] mData5 = ls.executeReader("Select * from Question6 where activity_id = '" + activity_id + "' ");
                String[][] mData6 = ls.executeReader("Select * from Question7 where activity_id = '" + activity_id + "' ");
                String[][] mData7 = ls.executeReader("Select * from Question8 where activity_id = '" + activity_id + "' ");
                String[][] mData8 = ls.executeReader("Select * from Question9 where activity_id = '" + activity_id + "' ");
                String[][] mData9 = ls.executeReader("Select * from Question10 where activity_id = '" + activity_id + "' ");
                String[][] mData10 = ls.executeReader("Select * from Question11 where activity_id = '" + activity_id + "' ");
                String[][] mData11 = ls.executeReader("Select * from Question12 where activity_id = '" + activity_id + "' ");
                String[][] mData12 = ls.executeReader("Select * from Question13 where activity_id = '" + activity_id + "' ");
                String[][] mData13 = ls.executeReader("Select * from Question14 where activity_id = '" + activity_id + "' ");
                String[][] mData14 = ls.executeReader("Select * from Question15 where activity_id = '" + activity_id + "' ");
                String[][] mData15 = ls.executeReader("Select * from Question16 where activity_id = '" + activity_id + "' ");
                String[][] mData16 = ls.executeReader("Select * from Question17 where activity_id = '" + activity_id + "' ");

                Integer s = 1;

                for (double i = 1.1; i <= 1.7; i += 0.1) {
                    type = 1;
                    DecimalFormat format = new DecimalFormat("#.#");
                    Double.valueOf(format.format(i));
                    Log.d("000999", "getPatientTool2: [type]=" + type + " [question]=" + Double.valueOf(format.format(i)) + " [value]=" + mData[0][s]);
                    sendPostRequest(activity_id, type, Double.valueOf(format.format(i)), mData[0][s], String.valueOf(jobj), added_by, added_on);
                    sendPostRequestQuestion(activity_id, type, Double.valueOf(format.format(i)), mData[0][s], String.valueOf(jobj), added_by, added_on);
                    s++;
                }

                s = 1;

                for (double i = 2.1; i <= 2.6; i += 0.1) {
                    type = 2;
                    DecimalFormat format = new DecimalFormat("#.#");
                    Double.valueOf(format.format(i));
                    Log.d("000999", "getPatientTool2: [type]=" + type + " [question]=" + Double.valueOf(format.format(i)) + " [value]=" + mData1[0][s]);
                    sendPostRequest(activity_id, type, Double.valueOf(format.format(i)), mData1[0][s], String.valueOf(jobj), added_by, added_on);
                    sendPostRequestQuestion(activity_id, type, Double.valueOf(format.format(i)), mData1[0][s], String.valueOf(jobj), added_by, added_on);
                    s++;
                }

                s = 1;

                for (double i = 3.1; i <= 3.9; i += 0.1) {
                    type = 3;
                    DecimalFormat format = new DecimalFormat("#.#");
                    Double.valueOf(format.format(i));
                    Log.d("000999", "getPatientTool2: [type]=" + type + " [question]=" + Double.valueOf(format.format(i)) + " [value]=" + mData2[0][s]);
                    sendPostRequest(activity_id, type, Double.valueOf(format.format(i)), mData2[0][s], String.valueOf(jobj), added_by, added_on);
                    sendPostRequestQuestion(activity_id, type, Double.valueOf(format.format(i)), mData2[0][s], String.valueOf(jobj), added_by, added_on);
                    s++;
                }

                sendPostRequest(activity_id, 3, Double.valueOf("3.9"), mData2[0][9], String.valueOf(jobj), added_by, added_on);
                sendPostRequestQuestion(activity_id, 3, Double.valueOf("3.9"), mData2[0][9], String.valueOf(jobj), added_by, added_on);
                sendPostRequest1(activity_id, 3, "3.10", mData2[0][10], String.valueOf(jobj), added_by, added_on);
                sendPostRequestQuestion1(activity_id, 3, "3.10", mData2[0][10], String.valueOf(jobj), added_by, added_on);

                s = 1;

                for (double i = 4.1; i <= 4.6; i += 0.1) {
                    type = 4;
                    DecimalFormat format = new DecimalFormat("#.#");
                    Double.valueOf(format.format(i));
                    Log.d("000999", "getPatientTool2: [type]=" + type + " [question]=" + Double.valueOf(format.format(i)) + " [value]=" + mData3[0][s]);
                    sendPostRequest(activity_id, type, Double.valueOf(format.format(i)), mData3[0][s], String.valueOf(jobj), added_by, added_on);
                    sendPostRequestQuestion(activity_id, type, Double.valueOf(format.format(i)), mData3[0][s], String.valueOf(jobj), added_by, added_on);
                    s++;
                }

                s = 1;

                for (double i = 5.1; i <= 5.5; i += 0.1) {
                    type = 5;
                    DecimalFormat format = new DecimalFormat("#.#");
                    Log.d("000999", "getPatientTool2: [type]=" + type + " [question]=" + Double.valueOf(format.format(i)) + " [value]=" + mData4[0][s]);
                    sendPostRequest(activity_id, type, Double.valueOf(format.format(i)), mData4[0][s], String.valueOf(jobj), added_by, added_on);
                    sendPostRequestQuestion(activity_id, type, Double.valueOf(format.format(i)), mData4[0][s], String.valueOf(jobj), added_by, added_on);
                    s++;
                }


                s = 1;
                for (double i = 6.1; i <= 6.7; i += 0.1) {
                    type = 6;
                    DecimalFormat format = new DecimalFormat("#.#");
                    Double.valueOf(format.format(i));
                    Log.d("000999", "getPatientTool2: [type]=" + type + " [question]=" + Double.valueOf(format.format(i)) + " [value]=" + mData5[0][s]);
                    sendPostRequest(activity_id, type, Double.valueOf(format.format(i)), mData5[0][s], String.valueOf(jobj), added_by, added_on);
                    sendPostRequestQuestion(activity_id, type, Double.valueOf(format.format(i)), mData5[0][s], String.valueOf(jobj), added_by, added_on);
                    s++;
                }

                s = 1;
                for (double i = 7.1; i <= 7.6; i += 0.1) {
                    type = 7;
                    DecimalFormat format = new DecimalFormat("#.#");
                    Double.valueOf(format.format(i));
                    Log.d("000999", "getPatientTool2: [type]=" + type + " [question]=" + Double.valueOf(format.format(i)) + " [value]=" + mData6[0][s]);
                    sendPostRequest(activity_id, type, Double.valueOf(format.format(i)), mData6[0][s], String.valueOf(jobj), added_by, added_on);
                    sendPostRequestQuestion(activity_id, type, Double.valueOf(format.format(i)), mData6[0][s], String.valueOf(jobj), added_by, added_on);
                    s++;
                }

                s = 1;
                for (double i = 8.1; i <= 8.5; i += 0.1) {
                    type = 8;
                    DecimalFormat format = new DecimalFormat("#.#");
                    Double.valueOf(format.format(i));
                    Log.d("000999", "getPatientTool2: [type]=" + type + " [question]=" + Double.valueOf(format.format(i)) + " [value]=" + mData7[0][s]);
                    sendPostRequest(activity_id, type, Double.valueOf(format.format(i)), mData7[0][s], String.valueOf(jobj), added_by, added_on);
                    sendPostRequestQuestion(activity_id, type, Double.valueOf(format.format(i)), mData7[0][s], String.valueOf(jobj), added_by, added_on);
                    s++;
                }

                s = 1;
                for (double i = 9.1; i <= 9.4; i += 0.1) {
                    type = 9;
                    DecimalFormat format = new DecimalFormat("#.#");
                    Double.valueOf(format.format(i));
                    Log.d("000999", "getPatientTool2: [type]=" + type + " [question]=" + Double.valueOf(format.format(i)) + " [value]=" + mData8[0][s]);
                    sendPostRequest(activity_id, type, Double.valueOf(format.format(i)), mData8[0][s], String.valueOf(jobj), added_by, added_on);
                    sendPostRequestQuestion(activity_id, type, Double.valueOf(format.format(i)), mData8[0][s], String.valueOf(jobj), added_by, added_on);
                    s++;
                }

                s = 1;
                for (double i = 10.1; i <= 10.5; i += 0.1) {
                    type = 10;
                    DecimalFormat format = new DecimalFormat("##.#");
                    Double.valueOf(format.format(i));
                    Log.d("000999", "getPatientTool2: [type]=" + type + " [question]=" + Double.valueOf(format.format(i)) + " [value]=" + mData9[0][s]);
                    sendPostRequest(activity_id, type, Double.valueOf(format.format(i)), mData9[0][s], String.valueOf(jobj), added_by, added_on);
                    sendPostRequestQuestion(activity_id, type, Double.valueOf(format.format(i)), mData9[0][s], String.valueOf(jobj), added_by, added_on);
                    s++;
                }

                s = 1;
                for (double i = 11.1; i <= 11.3; i += 0.1) {
                    type = 11;
                    DecimalFormat format = new DecimalFormat("##.#");
                    Double.valueOf(format.format(i));
                    Log.d("000999", "getPatientTool2: [type]=" + type + " [question]=" + Double.valueOf(format.format(i)) + " [value]=" + mData10[0][s]);
                    sendPostRequest(activity_id, type, Double.valueOf(format.format(i)), mData10[0][s], String.valueOf(jobj), added_by, added_on);
                    sendPostRequestQuestion(activity_id, type, Double.valueOf(format.format(i)), mData10[0][s], String.valueOf(jobj), added_by, added_on);
                    s++;
                }

                s = 1;
                for (double i = 12.1; i <= 12.5; i += 0.1) {
                    type = 12;
                    DecimalFormat format = new DecimalFormat("##.#");
                    Double.valueOf(format.format(i));
                    Log.d("000999", "getPatientTool2: [type]=" + type + " [question]=" + Double.valueOf(format.format(i)) + " [value]=" + mData11[0][s]);
                    sendPostRequest(activity_id, type, Double.valueOf(format.format(i)), mData11[0][s], String.valueOf(jobj), added_by, added_on);
                    sendPostRequestQuestion(activity_id, type, Double.valueOf(format.format(i)), mData11[0][s], String.valueOf(jobj), added_by, added_on);
                    s++;
                }

                s = 1;
                for (double i = 13.1; i <= 13.5; i += 0.1) {
                    type = 13;
                    DecimalFormat format = new DecimalFormat("##.#");
                    Double.valueOf(format.format(i));
                    Log.d("000999", "getPatientTool2: [type]=" + type + " [question]=" + Double.valueOf(format.format(i)) + " [value]=" + mData12[0][s]);
                    sendPostRequest(activity_id, type, Double.valueOf(format.format(i)), mData12[0][s], String.valueOf(jobj), added_by, added_on);
                    sendPostRequestQuestion(activity_id, type, Double.valueOf(format.format(i)), mData12[0][s], String.valueOf(jobj), added_by, added_on);
                    s++;
                }

                s = 1;
                for (double i = 14.1; i <= 14.2; i += 0.1) {
                    type = 14;
                    DecimalFormat format = new DecimalFormat("##.#");
                    Double.valueOf(format.format(i));
                    Log.d("000999", "getPatientTool2: [type]=" + type + " [question]=" + Double.valueOf(format.format(i)) + " [value]=" + mData13[0][s]);
                    sendPostRequest(activity_id, type, Double.valueOf(format.format(i)), mData13[0][s], String.valueOf(jobj), added_by, added_on);
                    sendPostRequestQuestion(activity_id, type, Double.valueOf(format.format(i)), mData13[0][s], String.valueOf(jobj), added_by, added_on);
                    s++;
                }

                s = 1;
                for (double i = 15.1; i <= 15.3; i += 0.1) {
                    type = 15;
                    DecimalFormat format = new DecimalFormat("##.#");
                    Double.valueOf(format.format(i));
                    Log.d("000999", "getPatientTool2: [type]=" + type + " [question]=" + Double.valueOf(format.format(i)) + " [value]=" + mData14[0][s]);
                    sendPostRequest(activity_id, type, Double.valueOf(format.format(i)), mData14[0][s], String.valueOf(jobj), added_by, added_on);
                    sendPostRequestQuestion(activity_id, type, Double.valueOf(format.format(i)), mData14[0][s], String.valueOf(jobj), added_by, added_on);
                    s++;
                }

                s = 1;
                for (double i = 16.1; i <= 16.7; i += 0.1) {
                    type = 16;
                    DecimalFormat format = new DecimalFormat("##.#");
                    Double.valueOf(format.format(i));
                    Log.d("000999", "getPatientTool2: [type]=" + type + " [question]=" + Double.valueOf(format.format(i)) + " [value]=" + mData15[0][s]);
                    sendPostRequest(activity_id, type, Double.valueOf(format.format(i)), mData15[0][s], String.valueOf(jobj), added_by, added_on);
                    sendPostRequestQuestion(activity_id, type, Double.valueOf(format.format(i)), mData15[0][s], String.valueOf(jobj), added_by, added_on);
                    s++;
                }

                s = 1;
                for (double i = 17.1; i <= 17.4; i += 0.1) {
                    type = 17;
                    DecimalFormat format = new DecimalFormat("#.#");
                    Double.valueOf(format.format(i));
                    Log.d("000999", "getPatientTool2: [type]=" + type + " [question]=" + Double.valueOf(format.format(i)) + " [value]=" + mData16[0][s]);
                    sendPostRequest(activity_id, type, Double.valueOf(format.format(i)), mData16[0][s], String.valueOf(jobj), added_by, added_on);
                    sendPostRequestQuestion(activity_id, type, Double.valueOf(format.format(i)), mData16[0][s], String.valueOf(jobj), added_by, added_on);
                    s++;
                }

                String update_record = "UPDATE Activities SET " +
                        "is_synced_checklist='" + String.valueOf(1) + "' " +
                        "WHERE activity_id = '" + activity_id + "' ";
                ls.executeNonQuery(update_record);

                uploadList();
                Toast.makeText(ctx, "Checklist Data Synced Successfully", Toast.LENGTH_SHORT).show();
            }
        }
        catch (Exception e){
            Log.d("000999", "syncChecklist:Error "+e);
            alertDialog.dismiss();
        }


        try {
            String[][] activity= ls.executeReader("Select is_synced from Activities where activity_id = '"+activity_id+"' ");
            if (activity[0][0].equals("1")) {
                alertDialog.dismiss();
            } else {
                String[][] activityData = ls.executeReader("Select * from Activities where activity_id = '" + activity_id + "' ");

                alertDialog.dismiss();
                sendPostRequestActivity(activityData[0][0], activityData[0][4], "",activityData[0][5],activityData[0][8],activityData[0][9],
                        activityData[0][1],String.valueOf(jobj),activityData[0][10],added_by,added_on);
                sendPostRequestActivityLocal(activityData[0][0], activityData[0][4], "",activityData[0][5],activityData[0][8],activityData[0][9],
                        activityData[0][1],String.valueOf(jobj),activityData[0][10],added_by,added_on);
            }
        }
        catch (Exception e){
          //  String[][] activityData = ls.executeReader("Select * from Activities where activity_id = '" + activity_id + "' ");

            //sendPostRequestActivity(activityData[0][0], activityData[0][1],activityData[0][3]+""+activityData[0][4],activityData[0][6], added_by, activityData[0][8], activityData[0][9], TodayDate, jobj.toString(),
                   // activityData[0][10], added_by, added_on);
            alertDialog.dismiss();
            Log.d("000999", "syncActivity:Error "+e);
        }

        try {
            String[][] validate_image = ls.executeReader("Select * from validation where activity_id = '" + activity_id + "' ");

            for (int j = 0; j < validate_image.length; j++) {

                if (validate_image[j][10].toString().equals("1")) {

                } else if (validate_image[j][10].toString().equals("0")) {
                  //  String[][] validate_image_Data = ls.executeReader("Select from validation where activity_id = '" + activity_id + "' AND is_synced = 0 ");

                  //  Log.d("000999", "val data length: " + validate_image_Data.length);

                  //  for (int i = 0; i < validate_image_Data.length; i++) {
                        String image_path = validate_image[j][6].toString();
                        Log.d("000999", "createImageFile: " + image_path);

                        try {
                            BitmapFactory.Options options = new BitmapFactory.Options();
                            options.inPreferredConfig = Bitmap.Config.ARGB_8888;

                            bitmap = BitmapFactory.decodeStream(new FileInputStream(image_path), null, options);
                            Log.d("000999", "createImageFile: " + bitmap);

                            uploadImage(bitmap, validate_image[j][9], validate_image[j][1], validate_image[j][2], validate_image[j][7],
                                    validate_image[j][4], validate_image[j][5], jobj.toString());

                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        }


                }
            }
        }
        catch (Exception e) {

        }

        alertDialog.dismiss();
    }


    private void sendPostRequest(final String activity_id, final Integer type, final Double question, final String value,
                                final String metadata, final String added_by, final String added_on) {

        String url = "https://development.api.teekoplus.akdndhrc.org/lhs/activities/save/question";

        Log.d("000555", "mURL " + url);
        //  Toast.makeText(getApplicationContext(),"1",Toast.LENGTH_LONG).show();

        String REQUEST_TAG = "volleyStringRequest";

        StringRequest strReq = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Log.d("000555", "onResponse: ");
                // Toast.makeText(ctx, response, Toast.LENGTH_SHORT).show();

                try {
                    //   Toast.makeText(getApplicationContext(), "2", Toast.LENGTH_LONG).show();

                    JSONObject jobj = new JSONObject(response);

                    if (jobj.getBoolean("success")) {

                        Log.d("000555", "response:  " + response);
                        //Toast.makeText(ctx, "Data has been saved", Toast.LENGTH_SHORT).show();

                      /*  Lister ls = new Lister(ctx);
                        ls.createAndOpenDB();
                        String update_record = "UPDATE MVACINE SET " +
                                "is_synced='" + String.valueOf(1) + "' " +
                                "WHERE member_uid = '" + mother_uid + "'AND added_on= '" + added_on + "'AND vaccine_id= '" + vacine_uid + "'";
                        ls.executeNonQuery(update_record);
                        Toast.makeText(ctx, "ڈیٹا سنک ہوگیا ہے", Toast.LENGTH_SHORT).show();*/
                    } else {
                        Log.d("000555", "else ");
                        //  Toast.makeText(ctx, "ڈیٹا سروس پر سینک نہیں ہوا", Toast.LENGTH_SHORT).show();
                        //Toast.makeText(ctx, "Data has not been sent to the service.", Toast.LENGTH_SHORT).show();
                    }

                } catch (Exception e) {
                    Log.d("000555", " Error: " + e.getMessage());
                    String update_record = "UPDATE Activities SET " +
                            "is_synced_checklist='" + String.valueOf(0) + "' " +
                            "WHERE activity_id = '" + activity_id + "' ";
                    ls.executeNonQuery(update_record);
                    //Toast.makeText(ctx, "Data has been sent incorrectly.", Toast.LENGTH_SHORT).show();
                    //   Toast.makeText(ctx, "ڈیٹا سینک نہیں ہوا", Toast.LENGTH_SHORT).show();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("000555", "error    " + error.getMessage());
                String update_record = "UPDATE Activities SET " +
                        "is_synced_checklist='" + String.valueOf(0) + "' " +
                        "WHERE activity_id = '" + activity_id + "' ";
                ls.executeNonQuery(update_record);
                //    Toast.makeText(ctx, "برائے مہربانی انٹرنیٹ کنکشن چیک کریں", Toast.LENGTH_SHORT).show();
                // Toast.makeText(ctx, "ڈیٹا سینک نہیں ہوا", Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {


                Map<String, String> params = new HashMap<String, String>();
                params.put("activity_id", activity_id);
                params.put("type", type.toString());
                params.put("question", question.toString());
                params.put("value", value);
                params.put("metadata", metadata);
                params.put("added_by", added_by);
                params.put("added_on", added_on);

                Log.d("000555", "mParam " + params);

                return params;
            }
        };

        AppController.getInstance().addToRequestQueue(strReq, REQUEST_TAG);
    }

    private void sendPostRequestQuestion(final String activity_id, final Integer type, final Double question, final String value,
                                 final String metadata, final String added_by, final String added_on) {

        String url = "https://localhostregister.000webhostapp.com/LHS_question_save.php";

        Log.d("000555", "mURL " + url);
        //  Toast.makeText(getApplicationContext(),"1",Toast.LENGTH_LONG).show();

        String REQUEST_TAG = "volleyStringRequest";

        StringRequest strReq = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Log.d("000555", "onResponse: ");
                // Toast.makeText(ctx, response, Toast.LENGTH_SHORT).show();


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("000555", "error    " + error.getMessage());
                String update_record = "UPDATE Activities SET " +
                        "is_synced_checklist='" + String.valueOf(0) + "' " +
                        "WHERE activity_id = '" + activity_id + "' ";
                ls.executeNonQuery(update_record);
                //    Toast.makeText(ctx, "برائے مہربانی انٹرنیٹ کنکشن چیک کریں", Toast.LENGTH_SHORT).show();
                // Toast.makeText(ctx, "ڈیٹا سینک نہیں ہوا", Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {


                Map<String, String> params = new HashMap<String, String>();
                params.put("activity_id", activity_id);
                params.put("type", type.toString());
                params.put("question", question.toString());
                params.put("value", value);
                params.put("metadata", metadata);
                params.put("added_by", added_by);
                params.put("added_on", added_on);

                Log.d("000555", "mParam " + params);

                return params;
            }
        };

        AppController.getInstance().addToRequestQueue(strReq, REQUEST_TAG);
    }


    private void sendPostRequest1(final String activity_id, final Integer type, final String question, final String value,
                                  final String metadata, final String added_by, final String added_on) {

        String url = "https://development.api.teekoplus.akdndhrc.org/lhs/activities/save/question";

        Log.d("000555", "mURL " + url);
        //  Toast.makeText(getApplicationContext(),"1",Toast.LENGTH_LONG).show();

        String REQUEST_TAG = "volleyStringRequest";

        StringRequest strReq = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Log.d("000555", "onResponse: ");
                // Toast.makeText(ctx, response, Toast.LENGTH_SHORT).show();

                try {
                    //   Toast.makeText(getApplicationContext(), "2", Toast.LENGTH_LONG).show();

                    JSONObject jobj = new JSONObject(response);

                    if (jobj.getBoolean("success")) {

                        Log.d("000555", "response:  " + response);
                        //Toast.makeText(ctx, "Data has been saved", Toast.LENGTH_SHORT).show();

                      /*  Lister ls = new Lister(ctx);
                        ls.createAndOpenDB();
                        String update_record = "UPDATE MVACINE SET " +
                                "is_synced='" + String.valueOf(1) + "' " +
                                "WHERE member_uid = '" + mother_uid + "'AND added_on= '" + added_on + "'AND vaccine_id= '" + vacine_uid + "'";
                        ls.executeNonQuery(update_record);
                        Toast.makeText(ctx, "ڈیٹا سنک ہوگیا ہے", Toast.LENGTH_SHORT).show();*/
                    } else {
                        Log.d("000555", "else ");
                        //Toast.makeText(ctx, "ڈیٹا سروس پر سینک نہیں ہوا", Toast.LENGTH_SHORT).show();
                        //Toast.makeText(ctx, "Data has not been sent to the service.", Toast.LENGTH_SHORT).show();
                    }

                } catch (Exception e) {
                    Log.d("000555", " Error: " + e.getMessage());
                    String update_record = "UPDATE Activities SET " +
                            "is_synced_checklist='" + String.valueOf(0) + "' " +
                            "WHERE activity_id = '" + activity_id + "' ";
                    ls.executeNonQuery(update_record);
                    //Toast.makeText(ctx, "Data has been sent incorrectly.", Toast.LENGTH_SHORT).show();
                    // Toast.makeText(ctx, "ڈیٹا سینک نہیں ہوا", Toast.LENGTH_SHORT).show();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("000555", "error    " + error.getMessage());
                String update_record = "UPDATE Activities SET " +
                        "is_synced_checklist='" + String.valueOf(0) + "' " +
                        "WHERE activity_id = '" + activity_id + "' ";
                ls.executeNonQuery(update_record);
                //    Toast.makeText(ctx, "برائے مہربانی انٹرنیٹ کنکشن چیک کریں", Toast.LENGTH_SHORT).show();
                //Toast.makeText(ctx, "ڈیٹا سینک نہیں ہوا", Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {


                Map<String, String> params = new HashMap<String, String>();
                params.put("activity_id", activity_id);
                params.put("type", type.toString());
                params.put("question", question);
                params.put("value", value);
                params.put("metadata", metadata);
                params.put("added_by", added_by);
                params.put("added_on", added_on);

                Log.d("000555", "mParam " + params);

                return params;
            }
        };

        AppController.getInstance().addToRequestQueue(strReq, REQUEST_TAG);
    }

    private void sendPostRequestQuestion1(final String activity_id, final Integer type, final String question, final String value,
                                  final String metadata, final String added_by, final String added_on) {

        String url = "https://localhostregister.000webhostapp.com/LHS_question_save.php";

        Log.d("000555", "mURL " + url);
        //  Toast.makeText(getApplicationContext(),"1",Toast.LENGTH_LONG).show();

        String REQUEST_TAG = "volleyStringRequest";

        StringRequest strReq = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Log.d("000555", "onResponse: "+response);
                // Toast.makeText(ctx, response, Toast.LENGTH_SHORT).show();


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("000555", "error    " + error.getMessage());
                String update_record = "UPDATE Activities SET " +
                        "is_synced_checklist='" + String.valueOf(0) + "' " +
                        "WHERE activity_id = '" + activity_id + "' ";
                ls.executeNonQuery(update_record);
                //    Toast.makeText(ctx, "برائے مہربانی انٹرنیٹ کنکشن چیک کریں", Toast.LENGTH_SHORT).show();
                //Toast.makeText(ctx, "ڈیٹا سینک نہیں ہوا", Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {


                Map<String, String> params = new HashMap<String, String>();
                params.put("activity_id", activity_id);
                params.put("type", type.toString());
                params.put("question", question);
                params.put("value", value);
                params.put("metadata", metadata);
                params.put("added_by", added_by);
                params.put("added_on", added_on);

                Log.d("000555", "mParam " + params);

                return params;
            }
        };

        AppController.getInstance().addToRequestQueue(strReq, REQUEST_TAG);
    }

    private void sendPostRequestActivity(final String uid, final String health_facility, final String facility_id, final String user_id, final String month, final String year, final String record_data,
                                 final String metadata, final String status, final String added_by, final String added_on) {

        String url = "https://development.api.teekoplus.akdndhrc.org/lhs/activities/save";

        Log.d("000555", "mURL " + url);
        //  Toast.makeText(getApplicationContext(),"1",Toast.LENGTH_LONG).show();

        String REQUEST_TAG = "volleyStringRequest";

        StringRequest strReq = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Log.d("000555", "onResponse: ");
                // Toast.makeText(ctx, response, Toast.LENGTH_SHORT).show();

                try {
                    //   Toast.makeText(getApplicationContext(), "2", Toast.LENGTH_LONG).show();

                    JSONObject jobj = new JSONObject(response);

                    if (jobj.getBoolean("success")) {

                        Log.d("000555", "response:  " + response);
                        //Toast.makeText(ctx, "Data has been saved", Toast.LENGTH_SHORT).show();


                        String update_record = "UPDATE Activities SET " +
                                "is_synced='" + String.valueOf(1) + "' " +
                                "WHERE activity_id = '" + uid + "' ";
                        ls.executeNonQuery(update_record);
                        Toast.makeText(ctx, "Data Synced Successfully", Toast.LENGTH_SHORT).show();

                        uploadList();
                    } else {
                        Log.d("000555", "else ");
                        Toast.makeText(ctx, "Data not Synced Successfully", Toast.LENGTH_SHORT).show();
                        //Toast.makeText(ctx, "Data has not been sent to the service.", Toast.LENGTH_SHORT).show();
                    }

                } catch (Exception e) {
                    Log.d("000555", " Error: " + e.getMessage());
                    //Toast.makeText(ctx, "Data has been sent incorrectly.", Toast.LENGTH_SHORT).show();
                    Toast.makeText(ctx, "Data not Synced Successfully", Toast.LENGTH_SHORT).show();
                    String update_record = "UPDATE Activities SET " +
                            "is_synced='" + String.valueOf(0) + "' " +
                            "WHERE activity_id = '" + uid + "' ";
                    ls.executeNonQuery(update_record);
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("000555", "error    " + error.getMessage());
                //    Toast.makeText(ctx, "برائے مہربانی انٹرنیٹ کنکشن چیک کریں", Toast.LENGTH_SHORT).show();
                Toast.makeText(ctx, "Data not Synced Successfully", Toast.LENGTH_SHORT).show();
                Log.d("000555", "error    " + error.getMessage());
                String update_record = "UPDATE Activities SET " +
                        "is_synced_checklist='" + String.valueOf(0) + "' " +
                        "WHERE activity_id = '" + uid + "' ";
                ls.executeNonQuery(update_record);
            }
        }) {
            @Override
            protected Map<String, String> getParams() {


                Map<String, String> params = new HashMap<String, String>();
                params.put("uid", uid);
                params.put("health_facility", health_facility);
                params.put("facility_id", facility_id);
                params.put("user_id", user_id);
                params.put("record_data", record_data);
                params.put("status", status);
                params.put("status_change", "0");
                params.put("for_month", month);
                params.put("for_year", year);
                params.put("metadata", metadata);
                params.put("added_by", added_by);
                params.put("added_on", added_on);

                Log.d("000555", "mParam " + params);

                return params;
            }

        };

        AppController.getInstance().addToRequestQueue(strReq, REQUEST_TAG);
    }

    private void sendPostRequestActivityLocal(final String uid, final String health_facility, final String facility_id, final String user_id, final String month, final String year, final String record_data,
                                         final String metadata, final String status, final String added_by, final String added_on) {

        String url = "https://localhostregister.000webhostapp.com/activityLHS.php";

        Log.d("000555", "mURL " + url);
        //  Toast.makeText(getApplicationContext(),"1",Toast.LENGTH_LONG).show();

        String REQUEST_TAG = "volleyStringRequest";

        StringRequest strReq = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Log.d("000555", "onResponse: ");
                // Toast.makeText(ctx, response, Toast.LENGTH_SHORT).show();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("000555", "error    " + error.getMessage());
                //    Toast.makeText(ctx, "برائے مہربانی انٹرنیٹ کنکشن چیک کریں", Toast.LENGTH_SHORT).show();
                Toast.makeText(ctx, "Data not Synced Successfully", Toast.LENGTH_SHORT).show();
                Log.d("000555", "error    " + error.getMessage());
                String update_record = "UPDATE Activities SET " +
                        "is_synced_checklist='" + String.valueOf(0) + "' " +
                        "WHERE activity_id = '" + uid + "' ";
                ls.executeNonQuery(update_record);
            }
        }) {
            @Override
            protected Map<String, String> getParams() {


                Map<String, String> params = new HashMap<String, String>();
                params.put("uid", uid);
                params.put("health_facility", health_facility);
                params.put("facility_id", facility_id);
                params.put("user_id", user_id);
                params.put("record_data", record_data);
                params.put("status", status);
                params.put("status_change", "0");
                params.put("for_month", month);
                params.put("for_year", year);
                params.put("metadata", metadata);
                params.put("added_by", added_by);
                params.put("added_on", added_on);

                Log.d("000555", "mParam " + params);

                return params;
            }

        };

        AppController.getInstance().addToRequestQueue(strReq, REQUEST_TAG);
    }


    private void uploadImage(final Bitmap bitmap, final String uid, final String member_id,final String full_name, final String type1, final String month, final String year,
                             final String metadata) {

        String url = "https://development.api.teekoplus.akdndhrc.org/lhs/activities/save/validation";

        Log.d("000555", "url " + url);
        VolleyMultipartRequest volleyMultipartRequest = new VolleyMultipartRequest(Request.Method.POST, url,
                new Response.Listener<NetworkResponse>() {
                    @Override
                    public void onResponse(NetworkResponse response) {
                        Log.d("000555",new String(response.data));

                        JSONObject jobj = null;
                        try {
                            jobj = new JSONObject(new String(response.data));
                            if (jobj.getBoolean("success")) {

                                Log.d("000555", "response:  " + response);
                                //Toast.makeText(ctx, "Data has been saved", Toast.LENGTH_SHORT).show();

                                Lister ls = new Lister(ctx);
                                ls.createAndOpenDB();
                                String update_record = "UPDATE validation SET " +
                                        "is_synced='" + String.valueOf(1) + "' " +
                                        "WHERE activity_id = '" + uid + "' ";
                                ls.executeNonQuery(update_record);

                                String update_record_val = "UPDATE Activities SET " +
                                        "is_synced_validation ='" + String.valueOf(1) + "' " +
                                        "WHERE activity_id = '" + uid + "' ";
                                ls.executeNonQuery(update_record_val);

                                uploadList();

                                Toast.makeText(ctx, "Data Synced Successfully", Toast.LENGTH_SHORT).show();
                            } else {
                                Log.d("000555", "else ");
                                Toast.makeText(ctx, "Data not Synced Successfully", Toast.LENGTH_SHORT).show();
                                //Toast.makeText(ctx, "Data has not been sent to the service.", Toast.LENGTH_SHORT).show();
                            }
                        }
                        catch (Exception e){
                            Log.d("000555", "Error: "+e);
                            Toast.makeText(ctx, "Data not Synced Successfully", Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("000555", "Error: "+error);
                        Toast.makeText(ctx, "Data not Synced Successfully", Toast.LENGTH_SHORT).show();
                    }
                }) {


            /*
             * If you want to add more parameters with the image
             * you can do it here
             * here we have only one parameter with the image
             * which is tags
             * */

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                // params.put("tags", "ccccc");  add string parameters
                params.put("activity_id", uid);
                params.put("member_id", member_id);
                params.put("full_name", full_name);
                params.put("type", type1);
                params.put("for_year", year);
                params.put("for_month", month);
                params.put("validated", "1");
                params.put("metadata", metadata);
                params.put("added_by", MainActivity.login_useruid);
                params.put("added_on", added_on);

                Log.d("000555", "mParam " + params);
                return params;
            }

            /*
             *pass files using below method
             * */

            @Override
            protected Map<String, DataPart> getByteData() {
                Map<String, DataPart> params = new HashMap<>();
                long imagename = System.currentTimeMillis();
                params.put("image", new DataPart(imagename + ".jpg", getFileDataFromDrawable(bitmap)));
                Log.d("000555", "mParam " + params);
                return params;
            }
        };


        volleyMultipartRequest.setRetryPolicy(new DefaultRetryPolicy(
                0,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        rQueue = Volley.newRequestQueue(Sync_Activity.this);
        rQueue.add(volleyMultipartRequest);
    }


    public byte[] getFileDataFromDrawable(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 80, byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }

}


