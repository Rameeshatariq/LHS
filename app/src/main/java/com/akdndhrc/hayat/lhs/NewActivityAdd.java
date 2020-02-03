package com.akdndhrc.hayat.lhs;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import com.akdndhrc.hayat.lhs.Adapter.Activities_Adapter;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;

public class NewActivityAdd extends AppCompatActivity {
    Context ctx= NewActivityAdd.this;
    String mData[][];
    ListView listView;
    String activity_id, added_by, added_on, user_id;
    FloatingActionButton floatingActionButton;
    static ArrayList<String> arrayListDate = new ArrayList<>();
    static ArrayList<String> arrayListactivity = new ArrayList<>();
    static ArrayList<String> arrayListId = new ArrayList<>();
    static ArrayList<String> arrayListmonth = new ArrayList<>();
    static ArrayList<String> arrayListyear = new ArrayList<>();
    static ArrayList<String> arrayListstatus = new ArrayList<>();

    ArrayList<String> arrayHealthCentre = new ArrayList<>();
    ArrayList<String> arrayHealthWorker = new ArrayList<>();
    Lister ls;
    Activities_Adapter adt;
    ImageView iv_close;
    EditText et_date;
    Button submit;
    CalendarView calendarView;
    String sp_health, sp_facility, s_date, sp_hc, sp_hw, date, TodayDate, month, year;
    Spinner sp_hc_hw, sp_hc_list, sp_facility_outreach, sp_hw_list, sp_month, sp_year;
    RelativeLayout rl_hc_list, rl_hw_list;
    TextView tv_hc, tv_hw;
    JSONObject jobj;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_add);


        listView=(ListView)findViewById(R.id.activity_list);

        floatingActionButton=(FloatingActionButton)findViewById(R.id.fab);
        calendarView= (CalendarView)findViewById(R.id.calendar);


        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dialog_Planning();
            }
        });

        activity_id= UUID.randomUUID().toString().replaceAll("-", "").toLowerCase();
        Log.d("000555", "onCreate: "+activity_id);
        added_on= String.valueOf(System.currentTimeMillis());
        added_by=MainActivity.login_useruid;

        ls= new Lister(ctx);
        ls.createAndOpenDB();

        try {

            String[][] healthWorker = ls.executeReader("Select username from USERS where privilege <> 11");

            for (int i = 0; i < healthWorker.length; i++) {
                Log.d("000999", "onCreate: " + healthWorker[0][0]);
                arrayHealthWorker.add(healthWorker[i][0]);
                Log.d("000999", "onCreate: " + arrayHealthWorker);
            }

            /*String[][] healthCentre = ls.executeReader("Select name from FACILITIES");

            for (int i = 0; i < healthCentre.length; i++) {
                Log.d("000999", "onCreate: " + healthCentre[0][0]);
                arrayHealthCentre.add(healthCentre[i][0]);
                Log.d("000999", "onCreate: " + arrayHealthCentre);
            }*/
        }
        catch (Exception e){
            Log.d("000999", "catch: "+e);
        }

    }
    private void Dialog_Planning() {
        final Dialog dialog = new Dialog(ctx);
        LayoutInflater layout = LayoutInflater.from(ctx);
        final View dialogView = layout.inflate(R.layout.planning_dialog_layout, null);
        dialog.setContentView(dialogView);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(false);


        iv_close = (ImageView) dialog.findViewById(R.id.iv_close);
        sp_hc_hw = (Spinner) dialog.findViewById(R.id.sp_hc_hw);
      //  sp_hc_list = (Spinner) dialog.findViewById(R.id.sp_hc_list);
        sp_hw_list = (Spinner) dialog.findViewById(R.id.sp_hw_list);
        sp_month = (Spinner) dialog.findViewById(R.id.sp_month);
        sp_year = (Spinner) dialog.findViewById(R.id.sp_year);
        sp_facility_outreach = (Spinner) dialog.findViewById(R.id.sp_facility_outreach);
        et_date = (EditText) dialog.findViewById(R.id.et_date);
       // rl_hc_list = (RelativeLayout) dialog.findViewById(R.id.rl_hc_list);
        rl_hw_list = (RelativeLayout) dialog.findViewById(R.id.rl_hw_list);
        submit = (Button) dialog.findViewById(R.id.btn_planning_submit);
       // tv_hc= (TextView) dialog.findViewById(R.id.tv_hc);
        tv_hw = (TextView) dialog.findViewById(R.id.tv_hw);

       // spineer_hc_data();
        spineer_hc_hw_data();
        spineer_hw_data();
        spinner_facility();
        spinner_month();
        dialog.show();
        select_year();

        SimpleDateFormat dates = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
        Calendar c = Calendar.getInstance();
        TodayDate = dates.format(c.getTime());
        Log.d("000555", "Today Date: " + TodayDate);


        iv_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        Log.d("000555", "Dialog_Planning: Date " +date);
        if(date != null) {
            et_date.setText(date);
        }
        else{
            et_date.setText(TodayDate);
        }
        s_date=et_date.getText().toString();

        sp_hc_hw.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (sp_hc_hw.getSelectedItemPosition() == 0) {
                    // Toast.makeText(getApplicationContext(), "Please Select One", Toast.LENGTH_LONG).show();
                } else if (sp_hc_hw.getSelectedItem().toString().equals("Health Worker")) {
                    rl_hw_list.setVisibility(View.VISIBLE);
                    tv_hw.setVisibility(View.VISIBLE);
                    //rl_hc_list.setVisibility(View.GONE);
                   // tv_hc.setVisibility(View.GONE);
                    sp_hc="";
                    sp_health = sp_hc_hw.getSelectedItem().toString();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        sp_month.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (sp_month.getSelectedItemPosition() == 1){
                    month = "01";
                }
                else if(sp_month.getSelectedItemPosition() == 2){
                    month = "02";
                }
                else if(sp_month.getSelectedItemPosition() == 3){
                    month = "03";
                }
                else if(sp_month.getSelectedItemPosition() == 4){
                    month = "04";
                }
                else if(sp_month.getSelectedItemPosition() == 5){
                    month = "05";
                }
                else if(sp_month.getSelectedItemPosition() == 6){
                    month = "06";
                }
                else if(sp_month.getSelectedItemPosition() == 7){
                    month = "07";
                }
                else if(sp_month.getSelectedItemPosition() == 8){
                    month = "08";
                }
                else if(sp_month.getSelectedItemPosition() == 9){
                    month = "09";

                }else if(sp_month.getSelectedItemPosition() == 10){
                    month = "10";
                }
                else if(sp_month.getSelectedItemPosition() == 11){
                    month = "11";
                }
                else if(sp_month.getSelectedItemPosition() == 12){
                    month = "12";
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

     /*   sp_hc_list.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (sp_hc_list.getSelectedItemPosition() == 0) {
                    sp_hc = "";
                } else {
                    sp_hc = sp_hc_list.getSelectedItem().toString();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
*/

        sp_hw_list.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (sp_hw_list.getSelectedItemPosition() == 0) {
                    sp_hw = "";
                    user_id ="";
                } else {
                    sp_hw = sp_hw_list.getSelectedItem().toString();
                    String[][] healthWorker = ls.executeReader("Select uid from USERS where privilege <> 11 AND username = '"+sp_hw+"' ");
                    user_id =healthWorker[0][0];
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        sp_facility_outreach.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (sp_facility_outreach.getSelectedItemPosition() == 0) {
                    //   Toast.makeText(ctx, "Please Select One", Toast.LENGTH_SHORT).show();
                } else {
                    sp_facility = sp_facility_outreach.getSelectedItem().toString();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("000555", "onClick: " + sp_health + " " + sp_facility + " " + sp_hw + "" + sp_hc + "" +s_date);

                if (sp_facility_outreach.getSelectedItemPosition() == 0 && sp_hc_hw.getSelectedItemPosition() == 0 && sp_month.getSelectedItemPosition() == 0
                        && sp_year.getSelectedItemPosition() == 0) {
                    Toast.makeText(ctx, "Please Select One", Toast.LENGTH_SHORT).show();
                }  else{
                    Helper helper = new Helper(ctx);
                    String totalcount = "0.0";
                    sp_hc="";
                    boolean data = helper.activities(activity_id,s_date, sp_health, sp_hc, sp_hw, user_id, sp_facility, totalcount, month, year, "0","0");

                    try {
                        if (data == true) {
                            Log.d("000555", "onClick: Added");
                            dialog.dismiss();
                            Intent intent= new Intent(ctx,MainActivity.class);
                            startActivity(intent);
                        } else {
                            Toast.makeText(ctx, "Data not Added", Toast.LENGTH_SHORT).show();
                            Log.d("000555", "onClick: Not added");
                        }

                    } catch (Exception e) {
                        Log.d("000555", "Catch: " + e);
                        Toast.makeText(ctx, "Error in Adding data: " + e, Toast.LENGTH_SHORT).show();
                    } finally {

                    }

                    try {
                        jobj=new JSONObject();
                        jobj.put("added_on",added_on);
                        jobj.put("added_by",added_by);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    String health_facility= sp_hc+""+sp_hw;
                    sendPostRequest(activity_id, health_facility, "",user_id,month,year,s_date,String.valueOf(jobj),"0",added_by,added_on);

                }
            }
        });
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


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void spineer_hw_data() {
        ArrayAdapter<String> adapter =
                new ArrayAdapter<String>(ctx,  android.R.layout.simple_spinner_dropdown_item, arrayHealthWorker);
        adapter.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item);

        sp_hw_list.setAdapter(
                new NothingSelectedSpinnerAdapter(
                        adapter,
                        R.layout.sp_hw_list_layout,
                        // R.layout.contact_spinner_nothing_selected_dropdown, // Optional
                        this));


        sp_hw_list.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

/*    private void spineer_hc_data() {
        ArrayAdapter<String> adapter =
                new ArrayAdapter<String>(ctx,  android.R.layout.simple_spinner_dropdown_item, arrayHealthCentre);
        adapter.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item);


        sp_hc_list.setAdapter(
                new NothingSelectedSpinnerAdapter(
                        adapter,
                        R.layout.sp_hc_list_layout,
                        // R.layout.contact_spinner_nothing_selected_dropdown, // Optional
                        ctx));


        sp_hc_list.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }*/
    private void spinner_facility() {
        final ArrayAdapter<CharSequence> adptr_council = ArrayAdapter.createFromResource(this, R.array.facility_outreach, android.R.layout.simple_spinner_item);
        adptr_council.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);


        sp_facility_outreach.setAdapter(
                new NothingSelectedSpinnerAdapter(
                        adptr_council,
                        R.layout.sp_facility_outreach_layout,
                        // R.layout.contact_spinner_nothing_selected_dropdown, // Optional
                        this));


        sp_facility_outreach.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void spinner_month() {
        final ArrayAdapter<CharSequence> adptr_council = ArrayAdapter.createFromResource(this, R.array.month, android.R.layout.simple_spinner_item);
        adptr_council.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);


        sp_month.setAdapter(
                new NothingSelectedSpinnerAdapter(
                        adptr_council,
                        R.layout.sp_month,
                        // R.layout.contact_spinner_nothing_selected_dropdown, // Optional
                        this));


        sp_month.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int year, int month, int day) {
                Lister ls = new Lister(NewActivityAdd.this);
                ls.createAndOpenDB();

                String sYear = String.valueOf(year);
                String sMonth = String.valueOf((month + 1));
                String sDay = String.valueOf(day);

                if (sDay.length() == 1)
                    sDay = "0" + sDay;

                if (sMonth.length() == 1)
                    sMonth = "0" + sMonth;

                if (sYear.length() == 1)
                    sYear = "0" + sYear;
                date = sYear + "-" + sMonth + "-" + sDay;


                Log.d("000555", "onDayClick: "+date);
                try {
                    arrayListactivity.clear();
                    arrayListDate.clear();
                    arrayListId.clear();
                    arrayListmonth.clear();
                    arrayListyear.clear();
                    arrayListstatus.clear();

                    mData = ls.executeReader("Select health_centre,health_worker,activity_date, activity_id, activity_month," +
                            "activity_year, complete_status from Activities where activity_date = '" + date + "' ");
                    Log.d("000555", "onClick: " + mData);
                    for (int k = 0; k < mData.length; k++) {

                        arrayListactivity.add(mData[k][0] + "" + mData[k][1]);
                        arrayListDate.add(mData[k][2]);
                        arrayListId.add(mData[k][3]);
                        arrayListmonth.add(mData[k][4]);
                        arrayListyear.add(mData[k][5]);
                        arrayListstatus.add(mData[k][6]);
                        Log.d("000555", "onClick: " + mData[k][2]);
                        Log.d("000555", "month: " + mData[k][4]);
                    }
                }
                catch (Exception e){
                    Log.d("000555", "Catch "+e);
                }

                adt = new Activities_Adapter(NewActivityAdd.this, arrayListactivity, arrayListDate,arrayListId, arrayListmonth,
                        arrayListyear,arrayListstatus);
                adt.notifyDataSetChanged();
                listView.setAdapter(adt);
            }

        });
    }

    private void select_year() {
//        final ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.Select_Months, android.R.layout.simple_spinner_item);
//        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//
        ArrayList<String> years = new ArrayList<String>();
        int thisYear = Calendar.getInstance().get(Calendar.YEAR);
        for (int i = 2018; i <= thisYear; i++) {
            years.add(Integer.toString(i));
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, years);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp_year.setAdapter(
                new NothingSelectedSpinnerAdapter(
                        adapter,
                        R.layout.sp_year,
                        // R.layout.contact_spinner_nothing_selected_dropdown, // Optional
                        this));
        sp_year.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                year = String.valueOf(sp_year.getSelectedItem());
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    private void sendPostRequest(final String uid, final String health_facility, final String facility_id, final String user_id, final String month, final String year, final String record_data,
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

                        Lister ls = new Lister(ctx);
                        ls.createAndOpenDB();
                        String update_record = "UPDATE Activities SET " +
                                "is_synced='" + String.valueOf(1) + "' " +
                                "WHERE activity_id = '" + activity_id + "' ";
                        ls.executeNonQuery(update_record);
                        Toast.makeText(ctx, "Data Synced Successfully", Toast.LENGTH_SHORT).show();
                    } else {
                        Log.d("000555", "else ");
                        Toast.makeText(ctx, "Data not Synced Successfully", Toast.LENGTH_SHORT).show();
                        //Toast.makeText(ctx, "Data has not been sent to the service.", Toast.LENGTH_SHORT).show();
                    }

                } catch (Exception e) {
                    Log.d("000555", " Error: " + e.getMessage());
                    //Toast.makeText(ctx, "Data has been sent incorrectly.", Toast.LENGTH_SHORT).show();
                    Toast.makeText(ctx, "Data not Synced Successfully", Toast.LENGTH_SHORT).show();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("000555", "error    " + error.getMessage());
                //    Toast.makeText(ctx, "برائے مہربانی انٹرنیٹ کنکشن چیک کریں", Toast.LENGTH_SHORT).show();
                Toast.makeText(ctx, "Data not Synced Successfully", Toast.LENGTH_SHORT).show();
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
}
