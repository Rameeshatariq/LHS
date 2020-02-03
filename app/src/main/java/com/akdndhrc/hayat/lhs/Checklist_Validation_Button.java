package com.akdndhrc.hayat.lhs;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.pdf.PdfDocument;
import android.media.Image;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.akdndhrc.hayat.lhs.Checklist.LHS_Checklist;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Document;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.NoConnectionError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

public class Checklist_Validation_Button extends AppCompatActivity {
    Context ctx= Checklist_Validation_Button.this;
   RelativeLayout checklist, validate, monthly_report;
    String activity_id, activity_month;
    ImageView iv_home;
    String added_on,added_by;
    JSONObject jobj;
    TextView count_q1, count_q2, count_q3, count_q4, count_q5, count_q6, count_q7,count_q8,count_q9,count_q10,count_q11,count_q12,count_q13,
            count_q14,count_q15,count_q16,count_q17;
    TextView count_no_q1, count_no_q2, count_no_q3, count_no_q4, count_no_q5, count_no_q6,count_no_q7,count_no_q8,count_no_q9,count_no_q10,count_no_q11,
            count_no_q12,count_no_q13,count_no_q14,count_no_q15,count_no_q16,count_no_q17;
    TextView count_na_q1, count_na_q2, count_na_q3, count_na_q4, count_na_q5, count_na_q6, count_na_q7,count_na_q8,count_na_q9,count_na_q10,count_na_q11,
            count_na_q12,count_na_q13,count_na_q14,count_na_q15,count_na_q16,count_na_q17;
    String  q1_count[][], q2_count[][], q3_count[][], q4_count[][], q5_count[][], q6_count[][], q7_count[][],q8_count[][],q9_count[][],q10_count[][],
            q11_count[][],q12_count[][],q13_count[][],q14_count[][],q15_count[][],q16_count[][],q17_count[][],q18_count[][];
    Switch status;
    String mData[][], mData1[][], mData2[][], mstatus[][];
    Button comp;
    Lister ls;

    TextView heading,date,no_validated,tv_count, tv_formula, month;

    Float total_count, yes_count_mp, yes_count_im, yes_count_ka, yes_count_s, yes_count_r, yes_count_vm;
    String count_mp, count_im, countka, count_s, count_r, count_vm, count_validated;
    String  mDataHealth[][], mDataFacility[][], mp_count[][], im_count[][], ka_count[][], s_count[][], vm_count[][], r_count[][];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checklist__validation__button);

        activity_id=getIntent().getExtras().getString("activity_id");
        activity_month=getIntent().getExtras().getString("activity_month");

        ls = new Lister(ctx);
        ls.createAndOpenDB();

        added_by=MainActivity.login_useruid;
        added_on= String.valueOf(System.currentTimeMillis());


        try {
            jobj = new JSONObject();
            jobj.put("added_on", "" + added_on);
            jobj.put("added_by", "" + added_by);
            jobj.put("datetime",  added_on);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Log.d("9999", "onCreate: "+activity_id);
        Log.d("9999", "onCreate: "+activity_month);

        checklist=(RelativeLayout)findViewById(R.id.rl_checklist);
        validate=(RelativeLayout)findViewById(R.id.rl_validate);
        monthly_report=(RelativeLayout)findViewById(R.id.rl_monthlyreport);
        heading=(TextView)findViewById(R.id.heading);
        date=(TextView)findViewById(R.id.date);
       // no_validated=(TextView)findViewById(R.id.no_validated);
        tv_count=(TextView)findViewById(R.id.text_count);
        tv_formula=(TextView)findViewById(R.id.text_formula);
        month=(TextView)findViewById(R.id.month);

        count_q1=(TextView)findViewById(R.id.tv_q1_yes_count);
        count_q2=(TextView)findViewById(R.id.tv_q2_yes_count);
        count_q3=(TextView)findViewById(R.id.tv_q3_yes_count);
        count_q4=(TextView)findViewById(R.id.tv_q4_yes_count);
        count_q5=(TextView)findViewById(R.id.tv_q5_yes_count);
        count_q6=(TextView)findViewById(R.id.tv_q6_yes_count);
        count_q7=(TextView)findViewById(R.id.tv_q7_yes_count);
        count_q8=(TextView)findViewById(R.id.tv_q8_yes_count);
        count_q9=(TextView)findViewById(R.id.tv_q9_yes_count);
        count_q10=(TextView)findViewById(R.id.tv_q10_yes_count);
        count_q11=(TextView)findViewById(R.id.tv_q11_yes_count);
        count_q12=(TextView)findViewById(R.id.tv_q12_yes_count);
        count_q13=(TextView)findViewById(R.id.tv_q13_yes_count);
        count_q14=(TextView)findViewById(R.id.tv_q14_yes_count);
        count_q15=(TextView)findViewById(R.id.tv_q15_yes_count);
        count_q16=(TextView)findViewById(R.id.tv_q16_yes_count);
        count_q17=(TextView)findViewById(R.id.tv_q17_yes_count);

        count_no_q1=(TextView)findViewById(R.id.tv_q1_no_count);
        count_no_q2=(TextView)findViewById(R.id.tv_q2_no_count);
        count_no_q3=(TextView)findViewById(R.id.tv_q3_no_count);
        count_no_q4=(TextView)findViewById(R.id.tv_q4_no_count);
        count_no_q5=(TextView)findViewById(R.id.tv_q5_no_count);
        count_no_q6=(TextView)findViewById(R.id.tv_q6_no_count);
        count_no_q7=(TextView)findViewById(R.id.tv_q7_no_count);
        count_no_q8 =(TextView)findViewById(R.id.tv_q8_no_count);
        count_no_q9=(TextView)findViewById(R.id.tv_q9_no_count);
        count_no_q10=(TextView)findViewById(R.id.tv_q10_no_count);
        count_no_q11=(TextView)findViewById(R.id.tv_q11_no_count);
        count_no_q12=(TextView)findViewById(R.id.tv_q12_no_count);
        count_no_q13=(TextView)findViewById(R.id.tv_q13_no_count);
        count_no_q14=(TextView)findViewById(R.id.tv_q14_no_count);
        count_no_q15=(TextView)findViewById(R.id.tv_q15_no_count);
        count_no_q16=(TextView)findViewById(R.id.tv_q16_no_count);
        count_no_q17=(TextView)findViewById(R.id.tv_q17_no_count);


        count_na_q1=(TextView)findViewById(R.id.tv_q1_na_count);
        count_na_q2=(TextView)findViewById(R.id.tv_q2_na_count);
        count_na_q3=(TextView)findViewById(R.id.tv_q3_na_count);
        count_na_q4=(TextView)findViewById(R.id.tv_q4_na_count);
        count_na_q5=(TextView)findViewById(R.id.tv_q5_na_count);
        count_na_q6=(TextView)findViewById(R.id.tv_q6_na_count);
        count_na_q7=(TextView)findViewById(R.id.tv_q7_na_count);
        count_na_q8=(TextView)findViewById(R.id.tv_q8_na_count);
        count_na_q9=(TextView)findViewById(R.id.tv_q9_na_count);
        count_na_q10=(TextView)findViewById(R.id.tv_q10_na_count);
        count_na_q11=(TextView)findViewById(R.id.tv_q11_na_count);
        count_na_q12=(TextView)findViewById(R.id.tv_q12_na_count);
        count_na_q13=(TextView)findViewById(R.id.tv_q13_na_count);
        count_na_q14=(TextView)findViewById(R.id.tv_q14_na_count);
        count_na_q15=(TextView)findViewById(R.id.tv_q15_na_count);
        count_na_q16=(TextView)findViewById(R.id.tv_q16_na_count);
        count_na_q17=(TextView)findViewById(R.id.tv_q17_na_count);

        status=(Switch) findViewById(R.id.switch_status);
        comp=(Button) findViewById(R.id.comp);

        status.setClickable(false);

        iv_home=(ImageView)findViewById(R.id.iv_home);
        iv_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(Checklist_Validation_Button.this, MainActivity.class);
                startActivity(intent);
            }
        });

        checklist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(Checklist_Validation_Button.this, LHS_Checklist.class);
                intent.putExtra("activity_id",activity_id);
                intent.putExtra("activity_month", activity_month);
                startActivity(intent);
            }
        });

        validate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(Checklist_Validation_Button.this, Validation.class);
                intent.putExtra("activity_id",activity_id);
                intent.putExtra("activity_month", activity_month);
                startActivity(intent);
            }
        });
        monthly_report.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(Checklist_Validation_Button.this, Monthly_Report.class);
                intent.putExtra("activity_id",activity_id);
                intent.putExtra("activity_month", activity_month);
                startActivity(intent);
            }
        });

        comp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Lister ls=new Lister(Checklist_Validation_Button.this);
                ls.createAndOpenDB();

                boolean isInserted= ls.executeNonQuery("Update Activities set " +
                        "complete_status = '" + 1 + "' " +
                        " where activity_id  = '" + activity_id + "'");

                if(isInserted == true){
                    status.setChecked(true);
                    comp.setText("Completed");

                    String[][] activityData= ls.executeReader("Select * from Activities where activity_id = '"+activity_id+"' ");

                    sendPostRequest(activityData[0][0], activityData[0][4], "",activityData[0][5],activityData[0][8],activityData[0][9],
                            activityData[0][1],String.valueOf(jobj),activityData[0][10],added_by,added_on);

                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent= new Intent(Checklist_Validation_Button.this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();

        Lister ls = new Lister(Checklist_Validation_Button.this);
        ls.createAndOpenDB();
        try {
            try {
                mData = ls.executeReader("Select health_centre, health_worker, activity_date, activity_month from Activities where activity_id = '" + activity_id + "' ");
                for (int i = 0; i < mData.length; i++) {
                    heading.setText(mData[i][0] + "" + mData[i][1]);
                    date.setText(mData[i][2]);
                    month.setText("Month: " + mData[i][3]);
                }
            } catch (Exception e) {
                //Toast.makeText(Checklist_Validation_Button.this, "" + e, Toast.LENGTH_SHORT).show();
            }

           /* try {
                mData1 = ls.executeReader("Select count(*) from CVACCINATION where activity_id = '" + activity_id + "' AND is_validated = 1");
                if (mData1 != null) {
                    no_validated.setText(mData1[0][0]);
                }
            } catch (Exception e) {
               // Toast.makeText(Checklist_Validation_Button.this, "" + e, Toast.LENGTH_SHORT).show();
            }*/
            try {
                    q1_count = ls.executeReader("Select question1_count, question1_no_count, question1_na_count from Question1 where activity_id = '" + activity_id + "' ");
                    for (int i = 0; i < q1_count.length; i++) {
                        count_q1.setText(q1_count[0][0]);
                        count_no_q1.setText(q1_count[0][1]);
                        count_na_q1.setText(q1_count[0][2]);
                    }
                } catch (Exception e) {
                    //Toast.makeText(ctx, ""+e, Toast.LENGTH_SHORT).show();
                    count_q1.setText("0");
                    count_no_q1.setText("0");
                    count_na_q1.setText("0");
                }


                try {
                    q2_count = ls.executeReader("Select question2_count, question2_no_count, question2_na_count from Question2 where activity_id = '" + activity_id + "' ");
                    for (int i = 0; i < q2_count.length; i++) {
                        count_q2.setText(q2_count[0][0]);
                        count_no_q2.setText(q2_count[0][1]);
                        count_na_q2.setText(q2_count[0][2]);
                    }
                } catch (Exception e) {
                    //Toast.makeText(ctx, ""+e, Toast.LENGTH_SHORT).show();
                    count_q2.setText("0");
                    count_no_q2.setText("0");
                    count_na_q2.setText("0");
                }

                try {
                    q3_count = ls.executeReader("Select question3_count, question3_no_count, question3_na_count from Question3 where activity_id = '" + activity_id + "' ");
                    for (int i = 0; i < q3_count.length; i++) {
                        count_q3.setText(q3_count[0][0]);
                        count_no_q3.setText(q3_count[0][1]);
                        count_na_q3.setText(q3_count[0][2]);
                    }
                } catch (Exception e) {
                    //Toast.makeText(ctx, ""+e, Toast.LENGTH_SHORT).show();
                    count_q3.setText("0");
                    count_no_q3.setText("0");
                    count_na_q3.setText("0");
                }

                try {
                    q4_count = ls.executeReader("Select question4_count, question4_no_count, question4_na_count from Question4 where activity_id = '" + activity_id + "' ");
                    for (int i = 0; i < q4_count.length; i++) {
                        count_q4.setText(q4_count[0][0]);
                        count_no_q4.setText(q4_count[0][1]);
                        count_na_q4.setText(q4_count[0][2]);
                    }
                } catch (Exception e) {
                    //Toast.makeText(ctx, ""+e, Toast.LENGTH_SHORT).show();
                    count_q4.setText("0");
                    count_no_q4.setText("0");
                    count_na_q4.setText("0");
                }

                try {
                    q5_count = ls.executeReader("Select question5_count, question5_no_count, question5_na_count from Question5 where activity_id = '" + activity_id + "' ");
                    for (int i = 0; i < q5_count.length; i++) {
                        count_q5.setText(q5_count[0][0]);
                        count_no_q5.setText(q5_count[0][1]);
                        count_na_q5.setText(q5_count[0][2]);
                    }
                } catch (Exception e) {
                    //Toast.makeText(ctx, ""+e, Toast.LENGTH_SHORT).show();
                    count_q5.setText("0");
                    count_no_q5.setText("0");
                    count_na_q5.setText("0");
                }

                try {
                    q6_count = ls.executeReader("Select question6_count, question6_no_count, question6_na_count from Question6 where activity_id = '" + activity_id + "' ");
                    for (int i = 0; i < q6_count.length; i++) {
                        count_q6.setText(q6_count[0][0]);
                        count_no_q6.setText(q6_count[0][1]);
                        count_na_q6.setText(q6_count[0][2]);
                    }
                } catch (Exception e) {
                    //Toast.makeText(ctx, ""+e, Toast.LENGTH_SHORT).show();
                    count_q6.setText("0");
                    count_no_q6.setText("0");
                    count_na_q6.setText("0");
                }

                try {
                    q7_count = ls.executeReader("Select question7_count, question7_no_count, question7_na_count from Question7 where activity_id = '" + activity_id + "' ");
                    for (int i = 0; i < q7_count.length; i++) {
                        count_q7.setText(q7_count[0][0]);
                        count_no_q7.setText(q7_count[0][1]);
                        count_na_q7.setText(q7_count[0][2]);
                    }
                } catch (Exception e) {
                    //Toast.makeText(ctx, ""+e, Toast.LENGTH_SHORT).show();
                    count_q7.setText("0");
                    count_no_q7.setText("0");
                    count_na_q7.setText("0");
                }

                try {
                    q8_count = ls.executeReader("Select question8_count, question8_no_count, question8_na_count from Question8 where activity_id = '" + activity_id + "' ");
                    for (int i = 0; i < q8_count.length; i++) {
                        count_q8.setText(q8_count[0][0]);
                        count_no_q8.setText(q8_count[0][1]);
                        count_na_q8.setText(q8_count[0][2]);
                    }
                } catch (Exception e) {
                    //Toast.makeText(ctx, ""+e, Toast.LENGTH_SHORT).show();
                    count_q8.setText("0");
                    count_no_q8.setText("0");
                    count_na_q8.setText("0");
                }

                try {
                    q9_count = ls.executeReader("Select question9_count, question9_no_count, question9_na_count from Question9 where activity_id = '" + activity_id + "' ");
                    for (int i = 0; i < q9_count.length; i++) {
                        count_q9.setText(q9_count[0][0]);
                        count_no_q9.setText(q9_count[0][1]);
                        count_na_q9.setText(q9_count[0][2]);
                    }
                } catch (Exception e) {
                    //Toast.makeText(ctx, ""+e, Toast.LENGTH_SHORT).show();
                    count_q9.setText("0");
                    count_no_q9.setText("0");
                    count_na_q9.setText("0");
                }

                try {
                    q10_count = ls.executeReader("Select question10_count, question10_no_count, question10_na_count from Question10 where activity_id = '" + activity_id + "' ");
                    for (int i = 0; i < q10_count.length; i++) {
                        count_q10.setText(q10_count[0][0]);
                        count_no_q10.setText(q10_count[0][1]);
                        count_na_q10.setText(q10_count[0][2]);
                    }
                } catch (Exception e) {
                    //Toast.makeText(ctx, ""+e, Toast.LENGTH_SHORT).show();
                    count_q10.setText("0");
                    count_no_q10.setText("0");
                    count_na_q10.setText("0");
                }

                try {
                    q11_count = ls.executeReader("Select question11_count, question11_no_count, question11_na_count from Question11 where activity_id = '" + activity_id + "' ");
                    for (int i = 0; i < q11_count.length; i++) {
                        count_q11.setText(q11_count[0][0]);
                        count_no_q11.setText(q11_count[0][1]);
                        count_na_q11.setText(q11_count[0][2]);
                    }
                } catch (Exception e) {
                    //Toast.makeText(ctx, ""+e, Toast.LENGTH_SHORT).show();
                    count_q11.setText("0");
                    count_no_q11.setText("0");
                    count_na_q11.setText("0");
                }

                try {
                    q12_count = ls.executeReader("Select question12_count, question12_no_count, question12_na_count from Question12 where activity_id = '" + activity_id + "' ");
                    for (int i = 0; i < q12_count.length; i++) {
                        count_q12.setText(q12_count[0][0]);
                        count_no_q12.setText(q12_count[0][1]);
                        count_na_q12.setText(q12_count[0][2]);
                    }
                } catch (Exception e) {
                    //Toast.makeText(ctx, ""+e, Toast.LENGTH_SHORT).show();
                    count_q12.setText("0");
                    count_no_q12.setText("0");
                    count_na_q12.setText("0");
                }

                try {
                    q13_count = ls.executeReader("Select question13_count, question13_no_count, question13_na_count from Question13 where activity_id = '" + activity_id + "' ");
                    for (int i = 0; i < q13_count.length; i++) {
                        count_q13.setText(q13_count[0][0]);
                        count_no_q13.setText(q13_count[0][1]);
                        count_na_q13.setText(q13_count[0][2]);
                    }
                } catch (Exception e) {
                    //Toast.makeText(ctx, ""+e, Toast.LENGTH_SHORT).show();
                    count_q13.setText("0");
                    count_no_q13.setText("0");
                    count_na_q13.setText("0");
                }

                try {
                    q14_count = ls.executeReader("Select question14_count, question14_no_count, question14_na_count from Question14 where activity_id = '" + activity_id + "' ");
                    for (int i = 0; i < q14_count.length; i++) {
                        count_q14.setText(q14_count[0][0]);
                        count_no_q14.setText(q14_count[0][1]);
                        count_na_q14.setText(q14_count[0][2]);
                    }
                } catch (Exception e) {
                    //Toast.makeText(ctx, ""+e, Toast.LENGTH_SHORT).show();
                    count_q14.setText("0");
                    count_no_q14.setText("0");
                    count_na_q14.setText("0");
                }
                try {
                    q15_count = ls.executeReader("Select question15_count, question15_no_count, question15_na_count from Question15 where activity_id = '" + activity_id + "' ");
                    for (int i = 0; i < q15_count.length; i++) {
                        count_q15.setText(q15_count[0][0]);
                        count_no_q15.setText(q15_count[0][1]);
                        count_na_q15.setText(q15_count[0][2]);
                    }
                } catch (Exception e) {
                    //Toast.makeText(ctx, ""+e, Toast.LENGTH_SHORT).show();
                    count_q15.setText("0");
                    count_no_q15.setText("0");
                    count_na_q15.setText("0");
                }

                try {
                    q16_count = ls.executeReader("Select question16_count, question16_no_count, question16_na_count from Question16 where activity_id = '" + activity_id + "' ");
                    for (int i = 0; i < q16_count.length; i++) {
                        count_q16.setText(q16_count[0][0]);
                        count_no_q16.setText(q16_count[0][1]);
                        count_na_q16.setText(q16_count[0][2]);
                    }
                } catch (Exception e) {
                    //Toast.makeText(ctx, ""+e, Toast.LENGTH_SHORT).show();
                    count_q16.setText("0");
                    count_no_q16.setText("0");
                    count_na_q16.setText("0");
                }

                try {
                    q17_count = ls.executeReader("Select question17_count, question17_no_count, question17_na_count from Question17 where activity_id = '" + activity_id + "' ");
                    for (int i = 0; i < q17_count.length; i++) {
                        count_q17.setText(q17_count[0][0]);
                        count_no_q17.setText(q17_count[0][1]);
                        count_na_q17.setText(q17_count[0][2]);
                    }
                } catch (Exception e) {
                    //Toast.makeText(ctx, ""+e, Toast.LENGTH_SHORT).show();
                    count_q17.setText("0");
                    count_no_q17.setText("0");
                    count_na_q17.setText("0");
                }

            try {
                mstatus = ls.executeReader("Select complete_status from Activities where activity_id = '" + activity_id + "' ");
                Log.d("00055", "onResume: "+mstatus[0][0]);
                if(!mstatus[0][0].equals("0")){
                    status.setChecked(true);
                }
                else{
                    status.setChecked(false);
                }

            } catch (Exception e) {
                // Toast.makeText(ctx, ""+e, Toast.LENGTH_SHORT).show();
                // count_validated = Float.parseFloat("0");
            }

                ls.closeDB();
            }
        catch (Exception e) {
                ls.closeDB();
            }

            /*total_count = yes_count_mp + yes_count_im + yes_count_ka + yes_count_s + yes_count_vm + yes_count_r;

            tv_count.setText("Total Yes Count: " + total_count.toString());
            Log.d("000555", "total: " + total_count.toString());

            ls.createAndOpenDB();
            boolean update_totalcount = ls.executeNonQuery("Update Activities set " +
                    "total_count = '" + total_count.toString() + "' " +
                    " where activity_id  = '" + activity_id + "'");*/

    }


/*
    public void createPdf() throws FileNotFoundException, DocumentException {

        String dir = Environment.getExternalStorageDirectory()+File.separator+"myLogs";
        File folder = new File(dir);
        folder.mkdirs();

        File file = new File(dir, "LogHistory.pdf");


        Cursor c1 = .rawQuery("SELECT * FROM " + Helper.TABLE_NAME_2, null);
        Document document = new Document();  // create the document
        PdfWriter.getInstance(document, new FileOutputStream(file));
        document.open();

        Paragraph p3 = new Paragraph();
        p3.add("Your Log History for \n");
        document.add(p3);

        PdfPTable table = new PdfPTable(4);
        table.addCell("Date");
        table.addCell("Start");
        table.addCell("End");
        table.addCell("Total");

        while (c1.moveToNext()) {
            String date = c1.getString(3);
            String start = c1.getString(1);
            String end = c1.getString(2);
            String total = c1.getString(4);

            table.addCell(date);
            table.addCell(start);
            table.addCell(end);
            table.addCell(total);
        }

        document.add(table);
        document.addCreationDate();
        document.close();
    }
*/

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
                    String update_record = "UPDATE Activities SET " +
                            "is_synced='" + String.valueOf(0) + "' " +
                            "WHERE activity_id = '" + activity_id + "' ";
                    ls.executeNonQuery(update_record);
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
                params.put("status_change", "1");
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
