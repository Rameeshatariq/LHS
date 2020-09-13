package com.akdndhrc.hayat.lhs.Adapter;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Handler;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.akdndhrc.hayat.lhs.AppController;
import com.akdndhrc.hayat.lhs.Checklist_Validation_Button;
import com.akdndhrc.hayat.lhs.Helper;
import com.akdndhrc.hayat.lhs.Lister;
import com.akdndhrc.hayat.lhs.MainActivity;
import com.akdndhrc.hayat.lhs.R;
import com.akdndhrc.hayat.lhs.TodayActivities_Fragment;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static com.akdndhrc.hayat.lhs.MainActivity.login_useruid;

public class Activities_Adapter extends BaseAdapter {
    private Context ctx;
    //    ArrayList<HashMap<String, String>> hashMapArrayList = new ArrayList<HashMap<String, String>>();
    ArrayList<String> arrayListDate;
    ArrayList<String> activity;
    ArrayList<String> activity_id;
    ArrayList<String> activity_month;
    ArrayList<String> activity_year;
    ArrayList<String> activity_status;
    String uid;
    boolean mFlag;
    String[][] lhw;
    private LayoutInflater inflater = null;
    String lhw_uid;
    Dialog alertDialog;
    ProgressBar pb;
    TextView tvTitle;
    ImageView iv_success;

    // Constructor
    public Activities_Adapter(Context ctx, ArrayList<String> activity, ArrayList<String> arrayListDate, ArrayList<String> arrayListId,
                              ArrayList<String> arrayListmonth, ArrayList<String> arrayListyear, ArrayList<String> arrayListstatus) {
        this.ctx = ctx;
        this.arrayListDate = arrayListDate;
        this.activity_id = arrayListId;
        this.activity = activity;
        this.activity_month = arrayListmonth;
        this.activity_year = arrayListyear;
        this.activity_status = arrayListstatus;

        inflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }


    public int getCount() {
        return arrayListDate.size();
    }

    public Object getItem(int position) {
        return null;
    }

    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int pos, View row, ViewGroup viewGroup) {

        final ViewHolder holder;
        if (row == null) {
            holder = new ViewHolder();

            row = inflater.inflate(R.layout.custom_activities_lv_layout, null);
            holder.list_background = row.findViewById(R.id.rl_background);
            holder.rl_left_topbottomcorner = (RelativeLayout) row.findViewById(R.id.rl_left_topbottomcorner);
            holder.activities_lv_activity = row.findViewById(R.id.activity);
            holder.activities_lv_date = row.findViewById(R.id.date);
            holder.activities_lv_id = row.findViewById(R.id.activity_id);
            holder.activities_lv_status = row.findViewById(R.id.activitySatus);
            holder.iv_refresh = row.findViewById(R.id.iv_refresh);
            holder.mView = row;

            holder.activities_lv_status.setVisibility(View.GONE);

            holder.execute = row.findViewById(R.id.execute);

            row.setTag(holder);

        } else {
            holder = (ViewHolder) row.getTag();
        }

        holder.activities_lv_activity.setText(activity.get(pos));
        holder.activities_lv_date.setText(arrayListDate.get(pos));
        holder.activities_lv_id.setText(activity_id.get(pos));
        holder.activities_lv_status.setText(activity_status.get(pos));

        holder.mView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                Log.d("000666", "onLongClick: ");

                new AlertDialog.Builder(ctx)
                        .setTitle("Alert!")
                        .setMessage("Are you sure you want to delete this activity?")
                        .setCancelable(false)
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                            }
                        })
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Lister ls = new Lister(ctx);
                                ls.createAndOpenDB();

                                boolean res = ls.executeNonQuery("Delete from Activities where activity_id = '"+holder.activities_lv_id.getText().toString()+"' ");

                                Log.d("000666", "Query: "+res);

                                if(res){
                                    Toast.makeText(ctx, "Activity deleted successfully!", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(ctx, MainActivity.class);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                    ctx.startActivity(intent);
                                }
                                else{
                                    Toast.makeText(ctx, "You can't delete this activity!", Toast.LENGTH_SHORT).show();
                                }
                                dialog.dismiss();
                            }
                        })
                        .show();


                return false;
            }
        });


        try{
            Lister ls= new Lister(ctx);
            ls.createAndOpenDB();

            String query= "select activity_id, activity_month from Activities " +
                    "INNER JOIN MONTHLY_REPORT ON MONTHLY_REPORT.month = Activities.activity_month " +
                    "where Activities.activity_month = '"+activity_month.get(pos)+"' AND Activities.activity_year = '"+activity_year.get(pos)+"' ";

            Boolean res= ls.executeNonQuery(query);
            String mdata[][]=ls.executeReader(query);

            Log.d("000333", "Query: "+query);
            Log.d("000333", "Res: "+res);
            Log.d("000333", "mdata: "+mdata);


           /* String query2= "select activity_id, activity_month from Activities " +
                    "INNER JOIN MANUAL_VALIDATION ON MANUAL_VALIDATION.month = Activities.activity_month " +
                    "where Activities.activity_month = '"+activity_month.get(pos)+"' AND Activities.activity_year = '"+activity_year.get(pos)+"' ";

            Boolean res2= ls.executeNonQuery(query2);
            String mdata2[][]=ls.executeReader(query2);

            Log.d("000333", "Query: "+query2);
            Log.d("000333", "Res: "+res2);
            Log.d("000333", "mdata: "+mdata2);*/

            if(mdata != null){
                holder.execute.setText("Execute");
                holder.iv_refresh.setVisibility(View.VISIBLE);
                Log.d("000333", "if: "+mdata);
            }
            else{
                holder.execute.setText("Prepare");
                Log.d("000333", "else: "+mdata);
            }
        }
        catch (Exception e){
            Log.d("000333", "Catch: "+e);

        }


        if(holder.activities_lv_status.getText().toString().equals("1")){
            holder.activities_lv_status.setVisibility(View.VISIBLE);
            holder.activities_lv_status.setText("Completed");
            holder.execute.setBackgroundColor(ctx.getResources().getColor(R.color.chtr_dpt_green_txt_color));

        }



        //uid= "4d15a7ae41da19b874e00ec764ce4104000690d32451bbd99778236da769cedf";
        uid= login_useruid;

        TodayActivities_Fragment.activities_List.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                Log.d("000666", "onLongClick: ");
                return false;
            }
        });

      /*  if(holder.activities_lv_status.getText().toString().equals("Completed")) {
            //Toast.makeText(ctx, "Activity is completed", Toast.LENGTH_SHORT).show();
            holder.execute.setVisibility(View.GONE);
            TodayActivities_Fragment.activities_List.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    Intent intent= new Intent(ctx,Checklist_Validation_Button.class);
                    intent.putExtra("activity_id",activity_id.get(pos));
                    intent.putExtra("activity_month",activity_month.get(pos));
                    ctx.startActivity(intent);
                }
            });
        }*/

      holder.iv_refresh.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View view) {
              try {
                  Lister ls=new Lister(ctx);
                  ls.createAndOpenDB();

                  lhw = ls.executeReader("Select uid from USERS where username= '" + activity.get(pos) + "'");
                  lhw_uid = lhw[0][0];

                  Log.d("000999", "onClick: " + lhw[0][0]);
                  ls.closeDB();
              }
              catch(Exception e){

              }

              try {
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

                  Handler handler0 = new Handler();
                  handler0.postDelayed(new Runnable() {
                      @Override
                      public void run() {
                          //  sendPostRequestCvacination(holder,pos, uid, activity_month.get(pos), activity_year.get(pos));
                          sendPostRequestCount(holder,pos, activity_month.get(pos), activity_year.get(pos));
                      }
                  }, 2500);


                  Handler handler = new Handler();
                  handler.postDelayed(new Runnable() {
                      @Override
                      public void run() {

                          sendPostRequestLive_Birth(holder,pos, activity_month.get(pos), activity_year.get(pos));
                          //  sendPostRequestCvacination(holder,pos, uid, activity_month.get(pos), activity_year.get(pos));

                      }
                  }, 2500);

                  Handler handler2 = new Handler();
                  handler2.postDelayed(new Runnable() {
                      @Override
                      public void run() {

                          sendPostRequestLow_Birth(holder,pos, activity_month.get(pos), activity_year.get(pos));
                          //  sendPostRequestCvacination(holder,pos, uid, activity_month.get(pos), activity_year.get(pos));

                      }
                  }, 2500);

                  Handler handler8 = new Handler();
                  handler8.postDelayed(new Runnable() {
                      @Override
                      public void run() {

                          sendPostRequestHigh_Risk(holder,pos, activity_month.get(pos), activity_year.get(pos));
                          //  sendPostRequestCvacination(holder,pos, uid, activity_month.get(pos), activity_year.get(pos));

                      }
                  }, 2500);

                  Handler handler3 = new Handler();
                  handler3.postDelayed(new Runnable() {
                      @Override
                      public void run() {
                          //  sendPostRequestCvacination(holder,pos, uid, activity_month.get(pos), activity_year.get(pos));
                          sendPostRequestNew_Preg(holder,pos, activity_month.get(pos), activity_year.get(pos));
                      }
                  }, 2500);

                  Handler handler4 = new Handler();
                  handler4.postDelayed(new Runnable() {
                      @Override
                      public void run() {
                          sendPostRequestTotal_Preg(holder,pos, activity_month.get(pos), activity_year.get(pos));
                          //  sendPostRequestCvacination(holder,pos, uid, activity_month.get(pos), activity_year.get(pos));
                      }
                  }, 2500);

              }
              catch (Exception e){

              }
              finally{

              }
          }
      });

        holder.execute.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onClick(final View view) {

                try {
                    Lister ls=new Lister(ctx);
                    ls.createAndOpenDB();

                    lhw = ls.executeReader("Select uid from USERS where username= '" + activity.get(pos) + "'");
                    lhw_uid = lhw[0][0];

                    Log.d("000999", "onClick: " + lhw[0][0]);
                    ls.closeDB();
                }
                catch(Exception e){

                }

                if (holder.execute.getText().toString().equals("Execute")) {

                        Intent intent = new Intent(ctx, Checklist_Validation_Button.class);
                        intent.putExtra("activity_id", holder.activities_lv_id.getText().toString());
                        intent.putExtra("activity_month", activity_month.get(pos));
                        Log.d("000555", "onClick: " + holder.activities_lv_id.getText().toString());
                        ctx.startActivity(intent);

                } else if(holder.execute.getText().toString().equals("Prepare")) {
                    try {

                        alertDialog = new Dialog(ctx);
                        LayoutInflater layout = LayoutInflater.from(ctx);
                        final View dialogView = layout.inflate(R.layout.downloading_dialog, null);

                        alertDialog.setContentView(dialogView);
                        alertDialog.setCanceledOnTouchOutside(false);
                        alertDialog.setCancelable(false);
                        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                        alertDialog.show();



                        pb = dialogView.findViewById(R.id.pbProgress);
                        iv_success = dialogView.findViewById(R.id.iv_success);
                        tvTitle = dialogView.findViewById(R.id.tvTitle);

                        Handler handler0 = new Handler();
                        handler0.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                //  sendPostRequestCvacination(holder,pos, uid, activity_month.get(pos), activity_year.get(pos));
                                sendPostRequestCount(holder,pos, activity_month.get(pos), activity_year.get(pos));
                            }
                        }, 2500);


                        Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {

                                sendPostRequestLive_Birth(holder,pos, activity_month.get(pos), activity_year.get(pos));
                              //  sendPostRequestCvacination(holder,pos, uid, activity_month.get(pos), activity_year.get(pos));

                            }
                        }, 2500);

                        Handler handler2 = new Handler();
                        handler2.postDelayed(new Runnable() {
                            @Override
                            public void run() {

                                sendPostRequestLow_Birth(holder,pos, activity_month.get(pos), activity_year.get(pos));
                                //  sendPostRequestCvacination(holder,pos, uid, activity_month.get(pos), activity_year.get(pos));

                            }
                        }, 2500);

                        Handler handler8 = new Handler();
                        handler8.postDelayed(new Runnable() {
                            @Override
                            public void run() {

                                sendPostRequestHigh_Risk(holder,pos, activity_month.get(pos), activity_year.get(pos));
                                //  sendPostRequestCvacination(holder,pos, uid, activity_month.get(pos), activity_year.get(pos));

                            }
                        }, 2500);

                        Handler handler3 = new Handler();
                        handler3.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                //  sendPostRequestCvacination(holder,pos, uid, activity_month.get(pos), activity_year.get(pos));
                                sendPostRequestNew_Preg(holder,pos, activity_month.get(pos), activity_year.get(pos));
                            }
                        }, 2500);

                        Handler handler4 = new Handler();
                        handler4.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                sendPostRequestTotal_Preg(holder,pos, activity_month.get(pos), activity_year.get(pos));
                                //  sendPostRequestCvacination(holder,pos, uid, activity_month.get(pos), activity_year.get(pos));
                            }
                        }, 2500);

                    }
                    catch (Exception e){

                    }
                    finally{

                    }
                }
            }
        });

        if (pos % 2 == 0) {

            holder.list_background.setBackgroundColor(ctx.getResources().getColor(R.color.light_pink_color));
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                holder.rl_left_topbottomcorner.setBackgroundTintList(ctx.getResources().getColorStateList(R.color.hp_listview_textview_redcolor));
            }
        } else {

            holder.list_background.setBackgroundColor(ctx.getResources().getColor(R.color.color_white));
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                holder.rl_left_topbottomcorner.setBackgroundTintList(ctx.getResources().getColorStateList(R.color.hp_listview_textview_bluecolor));
            }
        }

        return row;
    }


    private void sendPostRequestTotal_Preg(final ViewHolder holder, final int pos, final String month, final String year) {

        String url = "https://development.api.teekoplus.akdndhrc.org/lhs/validation-list";

        Log.d("000222", "mURL " + url);
        //  Toast.makeText(getApplicationContext(),"1",Toast.LENGTH_LONG).show();

        String REQUEST_TAG = "volleyStringRequest";

        StringRequest strReq1 = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Log.d("000222", "Response:    " + response);


                // Toast.makeText(ctx, response, Toast.LENGTH_SHORT).show();

                try {
                    // Toast.makeText(getApplicationContext(), "2", Toast.LENGTH_LONG).show();


                    Lister ls = new Lister(ctx);
                    // ls.closeDB();
                    ls.createAndOpenDB();

                    //////////////users
                    ////////////////////////VACCINES
                    try {

                        //  JSONArray jobj = new JSONArray(response);
                        Log.d("000999", response);
                        JSONArray jsonArray = new JSONArray(String.valueOf(response));
                        Log.d("000999", jsonArray.toString());
                        //basicinfo
                     //   JSONObject basic_info = obj.getJSONObject("basic_info");
                       // Log.d("000999", "onResponse!!!!!!!!!!!!: " + basic_info.toString());
                       /* boolean m2 = ls.executeNonQuery(Helper.CREATE_TABLE_KMEMBER);
                        String date = "";
*/

                        boolean m2 = ls.executeNonQuery(Helper.CREATE_TABLE_TOTAL_PREG);

                        // JSONObject obj1 = new JSONObject(String.valueOf(m_jArry));
                        // JSONArray m_jArray2 = obj1.getJSONArray("age_1233");
                        //  Log.d("000999", "JSONARRAY Leng:   " + m_jArray2.toString());

                        for(int i=0; i<jsonArray.length(); i++) {

                            String query_form_get_data = "insert or ignore into total_preg (member_id, full_name, added_by, month,year)" +
                                    " values " +
                                    "(" +
                                    "'" + jsonArray.getJSONObject(i).getString("member_id") + "'," +
                                    "'" + jsonArray.getJSONObject(i).getString("full_name") + "'," +
                                    "'" + jsonArray.getJSONObject(i).getString("added_by") + "'," +
                                    "'" + activity_month.get(pos) + "'," +
                                    "'" + activity_year.get(pos) + "'" +

                                    ")";
                            Log.d("000555", query_form_get_data);
                            mFlag = ls.executeNonQuery(query_form_get_data);
                            Log.d("000999", "Receiving Data: " + mFlag);

                        }


                        if(mFlag){
                            holder.execute.setText("Execute");
                            alertDialog.dismiss();
                            holder.iv_refresh.setVisibility(View.VISIBLE);
                            Toast.makeText(ctx, "Data downloaded successfully", Toast.LENGTH_LONG).show();

                        }
                        /*else{
                            alertDialog.dismiss();
                            holder.execute.setText("Execute");
                            holder.iv_refresh.setVisibility(View.VISIBLE);
                            Toast.makeText(ctx, "Total pregnant women data not downloaded successfully, Please use manual option", Toast.LENGTH_LONG).show();

                        }*/
                        //   progressDialog.dismiss();
                    } catch (Exception e) {
                        //   progressDialog.dismiss();
                        alertDialog.dismiss();
                        Log.d("000222", "Err:    " + e.getMessage());
                        holder.execute.setText("Execute");
                        holder.iv_refresh.setVisibility(View.VISIBLE);
                        Toast.makeText(ctx, "Total pregnant women data not downloaded successfully, Please use manual option", Toast.LENGTH_LONG).show();
                    }

                } catch (Exception e) {
                    //  progressDialog.dismiss();
                    alertDialog.dismiss();
                    Log.d("000222", "Err:    " + e.getMessage());
                    holder.execute.setText("Execute");
                    holder.iv_refresh.setVisibility(View.VISIBLE);
                    Toast.makeText(ctx, "Total pregnant women data not downloaded successfully, Please use manual option", Toast.LENGTH_LONG).show();

                }
                holder.execute.setText("Execute");
                holder.iv_refresh.setVisibility(View.VISIBLE);
                //  Toast.makeText(ctx, "Total Pregnant Women data not downloaded successfully, Please use Manual Option", Toast.LENGTH_LONG).show();
                alertDialog.dismiss();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //progressDialog.dismiss();
                Log.d("000222", "error    " + error.getMessage());
                //  Toast.makeText(Login_Activity.this, "برائے مہربانی انٹرنیٹ کنکشن چیک کریں", Toast.LENGTH_SHORT).show();
                alertDialog.dismiss();
                holder.execute.setText("Execute");
                holder.iv_refresh.setVisibility(View.VISIBLE);
                Toast.makeText(ctx, "Total pregnant women data not downloaded successfully, Please use manual option", Toast.LENGTH_LONG).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {

                Map<String, String> params = new HashMap<String, String>();
                params.put("month", month);
                params.put("year", year);
                params.put("uid", login_useruid);
                params.put("lhw", lhw_uid);
                params.put("type", "total_preg");


                Log.d("000222", "mParam " + params);

                return params;
            }
        };


        strReq1.setRetryPolicy(new DefaultRetryPolicy(
                30000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));


        AppController.getInstance().addToRequestQueue(strReq1, REQUEST_TAG);
    }
    private void sendPostRequestLive_Birth(final ViewHolder holder, final int pos, final String month, final String year) {

        String url = "https://development.api.teekoplus.akdndhrc.org/lhs/validation-list";

        Log.d("000222", "mURL " + url);
        //  Toast.makeText(getApplicationContext(),"1",Toast.LENGTH_LONG).show();

        String REQUEST_TAG = "volleyStringRequest";

        StringRequest strReq1 = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Log.d("000222", "Response:    " + response);


                // Toast.makeText(ctx, response, Toast.LENGTH_SHORT).show();

                try {
                    // Toast.makeText(getApplicationContext(), "2", Toast.LENGTH_LONG).show();


                    Lister ls = new Lister(ctx);
                    // ls.closeDB();
                    ls.createAndOpenDB();

                    //////////////users
                    ////////////////////////VACCINES
                    try {

                        //  JSONArray jobj = new JSONArray(response);
                        Log.d("000999", response);
                        JSONArray jsonArray = new JSONArray(String.valueOf(response));
                        Log.d("000999", jsonArray.toString());
                        //basicinfo
                        //   JSONObject basic_info = obj.getJSONObject("basic_info");
                        // Log.d("000999", "onResponse!!!!!!!!!!!!: " + basic_info.toString());
                       /* boolean m2 = ls.executeNonQuery(Helper.CREATE_TABLE_KMEMBER);
                        String date = "";
*/

                        boolean m2 = ls.executeNonQuery(Helper.CREATE_TABLE_LIVE_BIRTH);

                        for(int i=0; i<jsonArray.length(); i++) {

                            String query_form_get_data = "insert or ignore into live_birth (member_id, full_name, added_by, month,year)" +
                                    " values " +
                                    "(" +
                                    "'" + jsonArray.getJSONObject(i).getString("member_id") + "'," +
                                    "'" + jsonArray.getJSONObject(i).getString("full_name") + "'," +
                                    "'" + jsonArray.getJSONObject(i).getString("added_by") + "'," +
                                    "'" + activity_month.get(pos) + "'," +
                                    "'" + activity_year.get(pos) + "'" +

                                    ")";
                            Log.d("000555", query_form_get_data);
                            mFlag = ls.executeNonQuery(query_form_get_data);
                            Log.d("000999", "Receiving Data: " + mFlag);
                        }

                            //   progressDialog.dismiss();
                    } catch (Exception e) {
                        //   progressDialog.dismiss();
                        e.printStackTrace();
                        Log.d("000222", "Catch: " + e.getMessage());
                        Toast.makeText(ctx, "Live birth data not downloaded successfully, Please use manual option", Toast.LENGTH_LONG).show();
                    }

                } catch (Exception e) {
                    //  progressDialog.dismiss();
                    Log.d("000222", "Err:    " + e.getMessage());
                    Toast.makeText(ctx, "Live birth data not downloaded successfully, Please use manual option", Toast.LENGTH_LONG).show();

                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //progressDialog.dismiss();
                Log.d("000222", "error    " + error.getMessage());
                Toast.makeText(ctx, "Live birth data not downloaded successfully, Please use manual option", Toast.LENGTH_LONG).show();
                //  Toast.makeText(Login_Activity.this, "برائے مہربانی انٹرنیٹ کنکشن چیک کریں", Toast.LENGTH_SHORT).show();


            }
        }) {
            @Override
            protected Map<String, String> getParams() {

                Map<String, String> params = new HashMap<String, String>();
                params.put("month", month);
                params.put("year", year);
                params.put("uid", login_useruid);
                params.put("lhw", lhw_uid);
                params.put("type", "live_birth");


                Log.d("000222", "mParam " + params);

                return params;
            }
        };

        strReq1.setRetryPolicy(new DefaultRetryPolicy(
                30000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        AppController.getInstance().addToRequestQueue(strReq1, REQUEST_TAG);
    }
    private void sendPostRequestLow_Birth(final ViewHolder holder, final int pos, final String month, final String year) {

        String url = "https://development.api.teekoplus.akdndhrc.org/lhs/validation-list";

        Log.d("000222", "mURL " + url);
        //  Toast.makeText(getApplicationContext(),"1",Toast.LENGTH_LONG).show();

        String REQUEST_TAG = "volleyStringRequest";

        StringRequest strReq1 = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Log.d("000222", "Response:    " + response);


                // Toast.makeText(ctx, response, Toast.LENGTH_SHORT).show();

                try {
                    // Toast.makeText(getApplicationContext(), "2", Toast.LENGTH_LONG).show();


                    Lister ls = new Lister(ctx);
                    // ls.closeDB();
                    ls.createAndOpenDB();

                    //////////////users
                    ////////////////////////VACCINES
                    try {

                        //  JSONArray jobj = new JSONArray(response);
                        Log.d("000999", response);
                        JSONArray jsonArray = new JSONArray(String.valueOf(response));
                        Log.d("000999", jsonArray.toString());
                        //basicinfo
                        //   JSONObject basic_info = obj.getJSONObject("basic_info");
                        // Log.d("000999", "onResponse!!!!!!!!!!!!: " + basic_info.toString());
                       /* boolean m2 = ls.executeNonQuery(Helper.CREATE_TABLE_KMEMBER);
                        String date = "";
*/

                        boolean m2 = ls.executeNonQuery(Helper.CREATE_TABLE_LOW_BIRTH);
                        for(int i=0; i<jsonArray.length(); i++) {

                            String query_form_get_data = "insert or ignore into low_birth (member_id, full_name, added_by, month,year)" +
                                    " values " +
                                    "(" +
                                    "'" + jsonArray.getJSONObject(i).getString("member_id") + "'," +
                                    "'" + jsonArray.getJSONObject(i).getString("full_name") + "'," +
                                    "'" + jsonArray.getJSONObject(i).getString("added_by") + "'," +
                                    "'" + activity_month.get(pos) + "'," +
                                    "'" + activity_year.get(pos) + "'" +

                                    ")";
                            Log.d("000555", query_form_get_data);
                            mFlag = ls.executeNonQuery(query_form_get_data);
                            Log.d("000999", "Receiving Data: " + mFlag);
                        }
                        //   progressDialog.dismiss();
                    } catch (Exception e) {
                        //   progressDialog.dismiss();
                        e.printStackTrace();
                        Log.d("000222", "Catch: " + e.getMessage());
                        Toast.makeText(ctx, "Low weight birth data not downloaded successfully, Please use manual option", Toast.LENGTH_LONG).show();
                    }

                } catch (Exception e) {
                    //  progressDialog.dismiss();
                    Log.d("000222", "Err:    " + e.getMessage());
                    Toast.makeText(ctx, "Low weight birth data not downloaded successfully, Please use manual option", Toast.LENGTH_LONG).show();

                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //progressDialog.dismiss();
                Log.d("000222", "error    " + error.getMessage());
                Toast.makeText(ctx, "Low weight birth data not downloaded successfully, Please use manual option", Toast.LENGTH_LONG).show();
                //  Toast.makeText(Login_Activity.this, "برائے مہربانی انٹرنیٹ کنکشن چیک کریں", Toast.LENGTH_SHORT).show();


            }
        }) {
            @Override
            protected Map<String, String> getParams() {

                Map<String, String> params = new HashMap<String, String>();
                params.put("month", month);
                params.put("year", year);
                params.put("uid", login_useruid);
                params.put("lhw", lhw_uid);
                params.put("type", "low_birth");


                Log.d("000222", "mParam " + params);

                return params;
            }
        };


        strReq1.setRetryPolicy(new DefaultRetryPolicy(
                30000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        AppController.getInstance().addToRequestQueue(strReq1, REQUEST_TAG);
    }
    private void sendPostRequestNew_Preg(final ViewHolder holder, final int pos, final String month, final String year) {

        String url = "https://development.api.teekoplus.akdndhrc.org/lhs/validation-list";

        Log.d("000222", "mURL " + url);
        //  Toast.makeText(getApplicationContext(),"1",Toast.LENGTH_LONG).show();

        String REQUEST_TAG = "volleyStringRequest";

        StringRequest strReq1 = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Log.d("000222", "Response:    " + response);


                // Toast.makeText(ctx, response, Toast.LENGTH_SHORT).show();

                try {
                    // Toast.makeText(getApplicationContext(), "2", Toast.LENGTH_LONG).show();


                    Lister ls = new Lister(ctx);
                    // ls.closeDB();
                    ls.createAndOpenDB();

                    //////////////users
                    ////////////////////////VACCINES
                    try {

                        //  JSONArray jobj = new JSONArray(response);
                        Log.d("000999", response);
                        JSONArray jsonArray = new JSONArray(String.valueOf(response));
                        Log.d("000999", jsonArray.toString());
                        //basicinfo
                        //   JSONObject basic_info = obj.getJSONObject("basic_info");
                        // Log.d("000999", "onResponse!!!!!!!!!!!!: " + basic_info.toString());
                       /* boolean m2 = ls.executeNonQuery(Helper.CREATE_TABLE_KMEMBER);
                        String date = "";
*/

                        boolean m2 = ls.executeNonQuery(Helper.CREATE_TABLE_NEW_PREG);

                        for(int i=0; i<jsonArray.length(); i++) {

                            String query_form_get_data = "insert or ignore into new_preg (member_id, full_name, added_by, month,year)" +
                                    " values " +
                                    "(" +
                                    "'" + jsonArray.getJSONObject(i).getString("member_id") + "'," +
                                    "'" + jsonArray.getJSONObject(i).getString("full_name") + "'," +
                                    "'" + jsonArray.getJSONObject(i).getString("added_by") + "'," +
                                    "'" + activity_month.get(pos) + "'," +
                                    "'" + activity_year.get(pos) + "'" +

                                    ")";
                            Log.d("000555", query_form_get_data);
                            mFlag = ls.executeNonQuery(query_form_get_data);
                            Log.d("000999", "Receiving Data: " + mFlag);
                        }
                            //   progressDialog.dismiss();
                    } catch (Exception e) {
                        //   progressDialog.dismiss();
                        e.printStackTrace();
                        Log.d("000222", "Catch: " + e.getMessage());
                        Toast.makeText(ctx, "New pregnant women data not downloaded successfully, Please use manual option", Toast.LENGTH_LONG).show();
                    }

                } catch (Exception e) {
                    //  progressDialog.dismiss();
                    Log.d("000222", "Err:    " + e.getMessage());
                    Toast.makeText(ctx, "New pregnant women data not downloaded successfully, Please use manual option", Toast.LENGTH_LONG).show();

                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //progressDialog.dismiss();
                Log.d("000222", "error    " + error.getMessage());
                Toast.makeText(ctx, "New pregnant women data not downloaded successfully, Please use manual option", Toast.LENGTH_LONG).show();
                //  Toast.makeText(Login_Activity.this, "برائے مہربانی انٹرنیٹ کنکشن چیک کریں", Toast.LENGTH_SHORT).show();


            }
        }) {
            @Override
            protected Map<String, String> getParams() {

                Map<String, String> params = new HashMap<String, String>();
                params.put("month", month);
                params.put("year", year);
                params.put("uid", login_useruid);
                params.put("lhw", lhw_uid);
                params.put("type", "new_preg");


                Log.d("000222", "mParam " + params);

                return params;
            }
        };


        strReq1.setRetryPolicy(new DefaultRetryPolicy(
                30000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        AppController.getInstance().addToRequestQueue(strReq1, REQUEST_TAG);
    }

    private void sendPostRequestHigh_Risk(final ViewHolder holder, final int pos, final String month, final String year) {

        String url = "https://development.api.teekoplus.akdndhrc.org/lhs/validation-list";

        Log.d("000222", "mURL " + url);
        //  Toast.makeText(getApplicationContext(),"1",Toast.LENGTH_LONG).show();

        String REQUEST_TAG = "volleyStringRequest";

        StringRequest strReq1 = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Log.d("000222", "Response:    " + response);


                // Toast.makeText(ctx, response, Toast.LENGTH_SHORT).show();

                try {
                    // Toast.makeText(getApplicationContext(), "2", Toast.LENGTH_LONG).show();


                    Lister ls = new Lister(ctx);
                    // ls.closeDB();
                    ls.createAndOpenDB();

                    //////////////users
                    ////////////////////////VACCINES
                    try {

                        //  JSONArray jobj = new JSONArray(response);
                        Log.d("000999", response);
                        JSONArray jsonArray = new JSONArray(String.valueOf(response));
                        Log.d("000999", jsonArray.toString());
                        //basicinfo
                        //   JSONObject basic_info = obj.getJSONObject("basic_info");
                        // Log.d("000999", "onResponse!!!!!!!!!!!!: " + basic_info.toString());
                       /* boolean m2 = ls.executeNonQuery(Helper.CREATE_TABLE_KMEMBER);
                        String date = "";
*/

                        boolean m2 = ls.executeNonQuery(Helper.CREATE_TABLE_HIGH_RISK);

                        for(int i=0; i<jsonArray.length(); i++) {

                            String query_form_get_data = "insert or ignore into high_risk (member_id, full_name, added_by, month,year)" +
                                    " values " +
                                    "(" +
                                    "'" + jsonArray.getJSONObject(i).getString("member_id") + "'," +
                                    "'" + jsonArray.getJSONObject(i).getString("full_name") + "'," +
                                    "'" + jsonArray.getJSONObject(i).getString("added_by") + "'," +
                                    "'" + activity_month.get(pos) + "'," +
                                    "'" + activity_year.get(pos) + "'" +

                                    ")";
                            Log.d("000555", query_form_get_data);
                            mFlag = ls.executeNonQuery(query_form_get_data);
                            Log.d("000999", "Receiving Data: " + mFlag);
                        }
                        //   progressDialog.dismiss();
                    } catch (Exception e) {
                        //   progressDialog.dismiss();
                        e.printStackTrace();
                        Log.d("000222", "Catch: " + e.getMessage());
                        Toast.makeText(ctx, "High risk data not downloaded successfully, Please use manual option", Toast.LENGTH_LONG).show();
                    }

                } catch (Exception e) {
                    //  progressDialog.dismiss();
                    Log.d("000222", "Err:    " + e.getMessage());
                    Toast.makeText(ctx, "High risk data not downloaded successfully, Please use manual option", Toast.LENGTH_LONG).show();

                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //progressDialog.dismiss();
                Log.d("000222", "error    " + error.getMessage());
                Toast.makeText(ctx, "High risk data not downloaded successfully, Please use manual option", Toast.LENGTH_LONG).show();
                //  Toast.makeText(Login_Activity.this, "برائے مہربانی انٹرنیٹ کنکشن چیک کریں", Toast.LENGTH_SHORT).show();


            }
        }) {
            @Override
            protected Map<String, String> getParams() {

                Map<String, String> params = new HashMap<String, String>();
                params.put("month", month);
                params.put("year", year);
                params.put("uid", login_useruid);
                params.put("lhw", lhw_uid);
                params.put("type", "high_risk");


                Log.d("000222", "mParam " + params);

                return params;
            }
        };


        strReq1.setRetryPolicy(new DefaultRetryPolicy(
                30000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        AppController.getInstance().addToRequestQueue(strReq1, REQUEST_TAG);
    }

    private void sendPostRequestCount(final ViewHolder holder, final int pos, final String month, final String year) {

        String url = "https://development.api.teekoplus.akdndhrc.org/lhs/report";

        Log.d("000222", "mURL " + url);
        //  Toast.makeText(getApplicationContext(),"1",Toast.LENGTH_LONG).show();

        String REQUEST_TAG = "volleyStringRequest";

        StringRequest strReq1 = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Log.d("000222", "Response:    " + response);


                // Toast.makeText(ctx, response, Toast.LENGTH_SHORT).show();

                try {
                    // Toast.makeText(getApplicationContext(), "2", Toast.LENGTH_LONG).show();


                    Lister ls = new Lister(ctx);
                    // ls.closeDB();
                    ls.createAndOpenDB();

                    //////////////users
                    ////////////////////////VACCINES
                    try {

                        //  JSONArray jobj = new JSONArray(response);
                        Log.d("000999", response);
                        JSONObject obj = new JSONObject(String.valueOf(response));
                        Log.d("000999", obj.toString());

                        //basicinfo
                        JSONObject basic_info = obj.getJSONObject("basic_info");
                        Log.d("000999", "onResponse!!!!!!!!!!!!: " + basic_info.toString());
                        JSONObject watersource = basic_info.getJSONObject("water_source");
                        Log.d("000999", "onResponse!!!!!!!!!!!!: " + watersource.toString());

                        JSONObject child_health = obj.getJSONObject("child_health");
                        Log.d("000999", "onResponse!!!!!!!!!!!!: " + child_health.toString());
                        JSONObject age1223 = child_health.getJSONObject("age_1223");
                        Log.d("000999", "onResponse!!!!!!!!!!!!: " + age1223.toString());
                        JSONObject age_lt3 = child_health.getJSONObject("age_lt3");
                        Log.d("000999", "onResponse!!!!!!!!!!!!: " + age_lt3.toString());

                        //maternalhealth
                        JSONObject maternal_health = obj.getJSONObject("maternal_health");
                        Log.d("000999", "onResponse!!!!!!!!!!!!: " + maternal_health.toString());

                        //familyplan
                        JSONObject family_planning = obj.getJSONObject("family_plan");
                        Log.d("000999", "onResponse!!!!!!!!!!!!: " + family_planning.toString());

                        //diseases
                        JSONObject diseases = obj.getJSONObject("diseases");
                        Log.d("000999", "onResponse!!!!!!!!!!!!: " + diseases.toString());

                        JSONObject anamia = diseases.getJSONObject("anaemia");
                        Log.d("000999", "onResponse!!!!!!!!!!!!: " + anamia.toString());

                        JSONObject ari = diseases.getJSONObject("ari");
                        Log.d("000999", "onResponse!!!!!!!!!!!!: " + ari.toString());

                        JSONObject diarrhea = diseases.getJSONObject("diarrhea");
                        Log.d("000999", "onResponse!!!!!!!!!!!!: " + diarrhea.toString());

                        JSONObject eye_infections = diseases.getJSONObject("eye_infections");
                        Log.d("000999", "onResponse!!!!!!!!!!!!: " + eye_infections.toString());

                        JSONObject fever = diseases.getJSONObject("fever");
                        Log.d("000999", "onResponse!!!!!!!!!!!!: " + fever.toString());

                        JSONObject malaria = diseases.getJSONObject("malaria");
                        Log.d("000999", "onResponse!!!!!!!!!!!!: " + malaria.toString());

                        JSONObject referral = diseases.getJSONObject("referral");
                        Log.d("000999", "onResponse!!!!!!!!!!!!: " + referral.toString());

                        JSONObject resp = diseases.getJSONObject("resp");
                        Log.d("000999", "onResponse!!!!!!!!!!!!: " + resp.toString());

                        JSONObject rtis = diseases.getJSONObject("rtis");
                        Log.d("000999", "onResponse!!!!!!!!!!!!: " + rtis.toString());

                        JSONObject scabies = diseases.getJSONObject("scabies");
                        Log.d("000999", "onResponse!!!!!!!!!!!!: " + scabies.toString());

                        JSONObject tb_diagnosed = diseases.getJSONObject("tb_diagnosed");
                        Log.d("000999", "onResponse!!!!!!!!!!!!: " + tb_diagnosed.toString());

                        JSONObject tb_followed = diseases.getJSONObject("tb_followed");
                        Log.d("000999", "onResponse!!!!!!!!!!!!: " + tb_followed.toString());

                        JSONObject tb_suspect = diseases.getJSONObject("tb_suspect");
                        Log.d("000999", "onResponse!!!!!!!!!!!!: " + tb_suspect.toString());

                        JSONObject worm = diseases.getJSONObject("worm");
                        Log.d("000999", "onResponse!!!!!!!!!!!!: " + worm.toString());

                        //birth_deaths
                        JSONObject births_deaths = obj.getJSONObject("births_deaths");
                        Log.d("000999", "onResponse!!!!!!!!!!!!: " + births_deaths.toString());

                        //medicines
                        JSONObject medicines = obj.getJSONObject("medicines");
                        Log.d("000999", "onResponse!!!!!!!!!!!!: " + medicines.toString());

                        //mis
                        JSONObject misc = obj.getJSONObject("misc");
                        Log.d("000999", "onResponse!!!!!!!!!!!!: " + misc.toString());

                        //supervisor
                        JSONObject supervision = obj.getJSONObject("supervision");
                        Log.d("000999", "onResponse!!!!!!!!!!!!: " + supervision.toString());

                        boolean m2 = ls.executeNonQuery(Helper.CREATE_TABLE_MONTHLYREPORT);

                        String added_on = String.valueOf(System.currentTimeMillis());

                        String query_form_get_data = "insert or ignore into MONTHLY_REPORT (month, year, health_commettees,women_suport_group,household_registered_lhw, " +
                                " tap, spring, handpump, well, other,flush_system," +
                                " age_1223_count, age_1223_fully_imunized, age_lt3_count, age_lt3_gm, age_lt3_malnurished, new_borns_1week," +
                                " low_birth_weight, breast_fed, immunized,new_preg,total_preg,total_vistis,iron_sup,abortions,delivey_4p,delivery_pnc," +
                                "delivery_immunized,eligible,provided, followup,modern,condom_users,pill_users,injectible_users,iucd_users,surgical_users," +
                                "other_users,traditional_users,referred,supplied_condoms,supplied_pills,supplied_injectibles,diarrhea_a5,diarrhea_u5," +
                                "ari_a5,ari_u5,fever_a5,fever_u5,resp_a5,resp_u5,anaemia_a5,anaemia_u5,scabies_a5,scabies_u5,eye_infections_a5,eye_infections_u5," +
                                "rtis_a5,rtis_u5,worm_a5,worm_u5,malaria_a5,malaria_u5,referral_a5,referral_u5,tb_suspect_a5,tb_suspect_u5,tb_diagnosed_a5," +
                                "tb_diagnosed_u5,tb_followed_a5,tb_followed_u5,live,still,deaths_all,noenatal,infant,children,maternal,tab_paracetamol," +
                                "syp_paracetamol,tab_choloroquin,syp_choloroquin,tab_mebendazole,syp_pipearzine,ors,eye_ontiment,syp_contrimexazole,iron_tab," +
                                "antiseptic_lotion,benzyle_benzoate_lotion,sticking_plaster,b_complex_syp,cotton_bandages,cotton_wool,condoms,oral_pills," +
                                "contraceptive_inj,med_others,lhw_kit_bag,weighing_machine,thermometer,torch_with_cell,scissors,syringe_cutter,mis_others," +
                                "lhs,dco,adc,fpo,ppiu,added_on)" +
                                " values " +
                                "(" +
                                "'" + activity_month.get(pos) + "'," +
                                "'" + activity_year.get(pos) + "'," +
                                "'" + basic_info.getString("health_committees") + "'," +
                                "'" + basic_info.getString("support_groups") + "'," +
                                "'" + basic_info.getString("registerations") + "'," +
                                "'" + watersource.getString("tap") + "'," +
                                "'" + watersource.getString("spring") + "'," +
                                "'" + watersource.getString("hand_pump") + "'," +
                                "'" + watersource.getString("well") + "'," +
                                "'" + watersource.getString("other") + "'," +
                                "'" + basic_info.getString("flush_system") + "'," +
                                "'" + age1223.getString("count") + "'," +
                                "'" + age1223.getString("fully_imunized") + "'," +
                                "'" + age_lt3.getString("count") + "'," +
                                "'" + age_lt3.getString("gm") + "'," +
                                "'" + age_lt3.getString("malnurished") + "'," +
                                "'" + child_health.getString("new_borns_1week") + "'," +
                                "'" + child_health.getString("low_birth_weight") + "'," +
                                "'" + child_health.getString("breast_fed") + "'," +
                                "'" + child_health.getString("immunized") + "'," +
                                "'" + maternal_health.getString("new_preg") + "'," +
                                "'" + maternal_health.getString("total_preg") + "'," +
                                "'" + maternal_health.getString("total_vistis") + "'," +
                                "'" + maternal_health.getString("iron_sup") + "'," +
                                "'" + maternal_health.getString("abortions") + "'," +
                                "'" + maternal_health.getString("delivey_4p") + "'," +
                                "'" + maternal_health.getString("delivery_pnc") + "'," +
                                "'" + maternal_health.getString("delivery_immunized") + "'," +
                                "'" + family_planning.getString("eligible") + "'," +
                                "'" + family_planning.getString("provided") + "'," +
                                "'" + family_planning.getString("followup") + "'," +
                                "'" + family_planning.getString("modern") + "'," +
                                "'" + family_planning.getString("condom_users") + "'," +
                                "'" + family_planning.getString("pill_users") + "'," +
                                "'" + family_planning.getString("injectible_users") + "'," +
                                "'" + family_planning.getString("iucd_users") + "'," +
                                "'" + family_planning.getString("surgical_users") + "'," +
                                "'" + family_planning.getString("other_users") + "'," +
                                "'" + family_planning.getString("traditional_users") + "'," +
                                "'" + family_planning.getString("referred") + "'," +
                                "'" + family_planning.getString("supplied_condoms") + "'," +
                                "'" + family_planning.getString("supplied_pills") + "'," +
                                "'" + family_planning.getString("supplied_injectibles") + "'," +
                                "'" + diarrhea.getString("a5") + "'," +
                                "'" + diarrhea.getString("u5") + "'," +
                                "'" + ari.getString("a5") + "'," +
                                "'" + ari.getString("u5") + "'," +
                                "'" + fever.getString("a5") + "'," +
                                "'" + fever.getString("u5") + "'," +
                                "'" + resp.getString("a5") + "'," +
                                "'" + resp.getString("u5") + "'," +
                                "'" + anamia.getString("a5") + "'," +
                                "'" + anamia.getString("u5") + "'," +
                                "'" + scabies.getString("a5") + "'," +
                                "'" + scabies.getString("u5") + "'," +
                                "'" + eye_infections.getString("a5") + "'," +
                                "'" + eye_infections.getString("u5") + "'," +
                                "'" + rtis.getString("a5") + "'," +
                                "'" + rtis.getString("u5") + "'," +
                                "'" + worm.getString("a5") + "'," +
                                "'" + worm.getString("u5") + "'," +
                                "'" + malaria.getString("a5") + "'," +
                                "'" + malaria.getString("u5") + "'," +
                                "'" + referral.getString("a5") + "'," +
                                "'" + referral.getString("u5") + "'," +
                                "'" + tb_suspect.getString("a5") + "'," +
                                "'" + tb_suspect.getString("u5") + "'," +
                                "'" + tb_diagnosed.getString("a5") + "'," +
                                "'" + tb_diagnosed.getString("u5") + "'," +
                                "'" + tb_followed.getString("a5") + "'," +
                                "'" + tb_followed.getString("u5") + "'," +
                                "'" + births_deaths.getString("live") + "'," +
                                "'" + births_deaths.getString("still") + "'," +
                                "'" + births_deaths.getString("deaths_all") + "'," +
                                "'" + births_deaths.getString("noenatal") + "'," +
                                "'" + births_deaths.getString("infant") + "'," +
                                "'" + births_deaths.getString("children") + "'," +
                                "'" + births_deaths.getString("maternal") + "'," +
                                "'" + medicines.getString("tab_paracetamol") + "'," +
                                "'" + medicines.getString("syp_paracetamol") + "'," +
                                "'" + medicines.getString("tab_choloroquin") + "'," +
                                "'" + medicines.getString("syp_choloroquin") + "'," +
                                "'" + medicines.getString("tab_mebendazole") + "'," +
                                "'" + medicines.getString("syp_pipearzine") + "'," +
                                "'" + medicines.getString("ors") + "'," +
                                "'" + medicines.getString("eye_ontiment") + "'," +
                                "'" + medicines.getString("syp_contrimexazole") + "'," +
                                "'" + medicines.getString("iron_tab.") + "'," +
                                "'" + medicines.getString("antiseptic_lotion") + "'," +
                                "'" + medicines.getString("benzyle_benzoate_lotion") + "'," +
                                "'" + medicines.getString("sticking_plaster") + "'," +
                                "'" + medicines.getString("b_complex_syp") + "'," +
                                "'" + medicines.getString("cotton_bandages") + "'," +
                                "'" + medicines.getString("cotton_wool") + "'," +
                                "'" + medicines.getString("condoms") + "'," +
                                "'" + medicines.getString("oral_pills") + "'," +
                                "'" + medicines.getString("contraceptive_inj") + "'," +
                                "'" + medicines.getString("others") + "'," +
                                "'" + misc.getString("lhw_kit_bag") + "'," +
                                "'" + misc.getString("weighing_machine") + "'," +
                                "'" + misc.getString("thermometer") + "'," +
                                "'" + misc.getString("torch_with_cell") + "'," +
                                "'" + misc.getString("scissors") + "'," +
                                "'" + misc.getString("syringe_cutter") + "'," +
                                "'" + misc.getString("others") + "'," +
                                "'" + supervision.getString("lhs") + "'," +
                                "'" + supervision.getString("dco") + "'," +
                                "'" + supervision.getString("adc") + "'," +
                                "'" + supervision.getString("fpo") + "'," +
                                "'" + supervision.getString("ppiu") + "'," +
                                "'" + added_on + "'" +
                                ")";

                        Log.d("000555", query_form_get_data);
                        boolean query = ls.executeNonQuery(query_form_get_data);
                        Log.d("000555", "Receiving Data count: " +query);

                        //   progressDialog.dismiss();
                    } catch (Exception e) {
                        //   progressDialog.dismiss();
                        e.printStackTrace();
                        Log.d("000222", "Catch: " + e.getMessage());
                        Toast.makeText(ctx, "Count data not downloaded successfully, Please use manual option", Toast.LENGTH_LONG).show();
                    }

                } catch (Exception e) {
                    //  progressDialog.dismiss();
                    Log.d("000222", "Err:    " + e.getMessage());
                    Toast.makeText(ctx, "Count data not downloaded successfully, Please use Manual Option", Toast.LENGTH_LONG).show();

                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //progressDialog.dismiss();
                Log.d("000222", "error    " + error.getMessage());
                Toast.makeText(ctx, "Count data not downloaded successfully, Please use manual option", Toast.LENGTH_LONG).show();
                //  Toast.makeText(Login_Activity.this, "برائے مہربانی انٹرنیٹ کنکشن چیک کریں", Toast.LENGTH_SHORT).show();


            }
        }) {
            @Override
            protected Map<String, String> getParams() {

                Map<String, String> params = new HashMap<String, String>();
                params.put("month", month);
                params.put("year", year);
                params.put("uid", login_useruid);
                params.put("lhw", lhw_uid);


                Log.d("000222", "mParam " + params);

                return params;
            }
        };


        strReq1.setRetryPolicy(new DefaultRetryPolicy(
                30000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        AppController.getInstance().addToRequestQueue(strReq1, REQUEST_TAG);
    }


    static class ViewHolder {
        View mView;
        TextView activities_lv_date, activities_lv_activity, activities_lv_id, activities_lv_status;
        RelativeLayout list_background, rl_left_topbottomcorner;
        ImageView iv_refresh;
        Button prepare, execute;
    }

}
