package com.akdndhrc.hayat.lhs.Checklist;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.akdndhrc.hayat.lhs.Helper;
import com.akdndhrc.hayat.lhs.Lister;
import com.akdndhrc.hayat.lhs.MainActivity;
import com.akdndhrc.hayat.lhs.R;
import com.rey.material.widget.CheckBox;

public class Checklist_Q3 extends AppCompatActivity {
    Context ctx= Checklist_Q3.this;
    String activity_id,activity_month;
    CheckBox cb_yes_q1, cb_yes_q2, cb_yes_q3, cb_yes_q4,cb_yes_q5, cb_yes_q6, cb_yes_q7, cb_yes_q8, cb_yes_q9, cb_yes_q10,
            cb_no_q1, cb_no_q2, cb_no_q3, cb_no_q4, cb_no_q5,cb_no_q6, cb_no_q7,cb_no_q8,cb_no_q9,cb_no_q10;
    String result_q1, result_q2, result_q3, result_q4, result_q5, result_q6,result_q7,result_q8,result_q9,result_q10;
    int count, no_count, na_count;
    Helper helper;
    Button save, back, next;
    TextView key1;
    ImageView iv_close, iv_home, iv_back;
    Lister ls;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checklist__q3);

        activity_id=getIntent().getExtras().getString("activity_id");
        activity_month=getIntent().getExtras().getString("activity_month");

        helper= new Helper(ctx);
        ls= new Lister(ctx);
        ls.createAndOpenDB();

        cb_yes_q1=(CheckBox)findViewById(R.id.checkbox_haan_3_1);
        cb_yes_q2=(CheckBox)findViewById(R.id.checkbox_haan_3_2);
        cb_yes_q3=(CheckBox)findViewById(R.id.checkbox_haan_3_3);
        cb_yes_q4=(CheckBox)findViewById(R.id.checkbox_haan_3_4);
        cb_yes_q5=(CheckBox)findViewById(R.id.checkbox_haan_3_5);
        cb_yes_q6=(CheckBox)findViewById(R.id.checkbox_haan_3_6);
        cb_yes_q7=(CheckBox)findViewById(R.id.checkbox_haan_3_7);
        cb_yes_q8=(CheckBox)findViewById(R.id.checkbox_haan_3_8);
        cb_yes_q9=(CheckBox)findViewById(R.id.checkbox_haan_3_9);
        cb_yes_q10=(CheckBox)findViewById(R.id.checkbox_haan_3_10);

        cb_no_q1=(CheckBox)findViewById(R.id.checkbox_nahi_3_1);
        cb_no_q2=(CheckBox)findViewById(R.id.checkbox_nahi_3_2);
        cb_no_q3=(CheckBox)findViewById(R.id.checkbox_nahi_3_3);
        cb_no_q4=(CheckBox)findViewById(R.id.checkbox_nahi_3_4);
        cb_no_q5=(CheckBox)findViewById(R.id.checkbox_nahi_3_5);
        cb_no_q6=(CheckBox)findViewById(R.id.checkbox_nahi_3_6);
        cb_no_q7=(CheckBox)findViewById(R.id.checkbox_nahi_3_7);
        cb_no_q8=(CheckBox)findViewById(R.id.checkbox_nahi_3_8);
        cb_no_q9=(CheckBox)findViewById(R.id.checkbox_nahi_3_9);
        cb_no_q10=(CheckBox)findViewById(R.id.checkbox_nahi_3_10);


        save=(Button)findViewById(R.id.btn_save);
        next=(Button)findViewById(R.id.btn_save_next);
        iv_back=(ImageView)findViewById(R.id.iv_back);

        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        iv_home=(ImageView)findViewById(R.id.iv_home);
        iv_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(Checklist_Q3.this, MainActivity.class);
                startActivity(intent);
            }
        });

        try {
            boolean mflag= isCompleted(activity_id);

            setData(activity_id);

            if(mflag == true){
                // Toast.makeText(this, "Tool1 Completed", Toast.LENGTH_SHORT).show();
            }
            else{
                // Toast.makeText(this, "Tool1 not Completed", Toast.LENGTH_SHORT).show();
            }

        }catch (Exception e){
        }

        try {
            String complete_status[][] = ls.executeReader("Select complete_status from Activities where activity_id = '" + activity_id + "' AND " +
                    "complete_status = 1");

            String complete= complete_status[0][0];

            Log.d("000888", "onCreate: "+complete_status[0][0]);
            if (complete.equals("1")) {
              //  disableFeilds();
            }
        } catch (Exception e){
            Log.d("000888", "onCreate: ,mmmm"+e);
        }

        cb_yes_q1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(cb_yes_q1.isChecked()){
                    cb_no_q1.setChecked(false);
                }
            }
        });
        cb_no_q1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(cb_no_q1.isChecked()){
                    cb_yes_q1.setChecked(false);
                }
            }
        });

        cb_yes_q2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(cb_yes_q2.isChecked()){
                    cb_no_q2.setChecked(false);
                }
            }
        });
        cb_no_q2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(cb_no_q2.isChecked()){
                    cb_yes_q2.setChecked(false);
                }
            }
        });

        cb_yes_q3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(cb_yes_q3.isChecked()){
                    cb_no_q3.setChecked(false);
                }
            }
        });
        cb_no_q3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(cb_no_q3.isChecked()){
                    cb_yes_q3.setChecked(false);
                }
            }
        });
        cb_yes_q4.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(cb_yes_q4.isChecked()){
                    cb_no_q4.setChecked(false);
                }
            }
        });
        cb_no_q4.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(cb_no_q4.isChecked()){
                    cb_yes_q4.setChecked(false);
                }
            }
        });
        cb_yes_q5.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(cb_yes_q5.isChecked()){
                    cb_no_q5.setChecked(false);
                }
            }
        });
        cb_no_q5.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(cb_no_q5.isChecked()){
                    cb_yes_q5.setChecked(false);
                }
            }
        });
        cb_yes_q6.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(cb_yes_q6.isChecked()){
                    cb_no_q6.setChecked(false);
                }
            }
        });
        cb_no_q6.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(cb_no_q6.isChecked()){
                    cb_yes_q6.setChecked(false);
                }
            }
        });
        cb_yes_q7.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(cb_yes_q7.isChecked()){
                    cb_no_q7.setChecked(false);
                }
            }
        });
        cb_no_q7.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(cb_no_q7.isChecked()){
                    cb_yes_q7.setChecked(false);
                }
            }
        });
        cb_yes_q8.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(cb_yes_q8.isChecked()){
                    cb_no_q8.setChecked(false);
                }
            }
        });
        cb_no_q8.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(cb_no_q8.isChecked()){
                    cb_yes_q8.setChecked(false);
                }
            }
        });
        cb_yes_q9.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(cb_yes_q9.isChecked()){
                    cb_no_q9.setChecked(false);
                }
            }
        });
        cb_no_q9.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(cb_no_q9.isChecked()){
                    cb_yes_q9.setChecked(false);
                }
            }
        });
        cb_yes_q10.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(cb_yes_q10.isChecked()){
                    cb_no_q10.setChecked(false);
                }
            }
        });
        cb_no_q10.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(cb_no_q10.isChecked()){
                    cb_yes_q10.setChecked(false);
                }
            }
        });
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addData();

                try{

                    ls.createAndOpenDB();

                    String[][] mData = ls.executeReader("Select * from Question3 where activity_id  = '" + activity_id + "'");

                    boolean isInserted;

                    if (mData != null) {
                        isInserted = ls.executeNonQuery("Update Question3 set " +
                                "question3_q1 = '" + result_q1 + "', " +
                                "question3_q2 = '" + result_q2 + "', " +
                                "question3_q3 = '" + result_q3 + "', " +
                                "question3_q4 = '" + result_q4 + "', " +
                                "question3_q5 = '" + result_q5 + "', " +
                                "question3_q6 = '" + result_q6 + "', " +
                                "question3_q7 = '" + result_q7 + "', " +
                                "question3_q8 = '" + result_q8 + "', " +
                                "question3_q9 = '" + result_q9 + "', " +
                                "question3_q10 = '" + result_q10 + "', " +
                                "question3_count = '" + count + "', " +
                                "question3_no_count = '" + no_count + "', " +
                                "question3_na_count = '" + na_count + "' " +
                                " where activity_id  = '" + activity_id + "'");

                        String update_record = "UPDATE Activities SET " +
                                "is_synced_checklist='" + String.valueOf(0) + "' " +
                                "WHERE activity_id = '" + activity_id + "' ";
                        ls.executeNonQuery(update_record);

                        if (isInserted == true) {
                            Toast.makeText(ctx, "Data Updated Successfully", Toast.LENGTH_SHORT).show();
                            count = 0;
                            no_count =0;
                            na_count =0;
                            Log.d("00055", "Update: "+activity_id+" "+result_q1+" "+result_q2+" "+result_q3+" "+result_q4+" "+count);
                        } else {
                            Toast.makeText(ctx, "Data not updated successfully", Toast.LENGTH_SHORT).show();
                        }

                    } else {

                        isInserted = helper.question3(activity_id, result_q1, result_q2, result_q3, result_q4, result_q5,result_q6, result_q7,
                                result_q8,result_q9,result_q10,count,no_count,na_count);
                        if(isInserted) {
                            Toast.makeText(ctx, "Data added successfully", Toast.LENGTH_SHORT).show();
                            String update_record = "UPDATE Activities SET " +
                                    "is_synced_checklist='" + String.valueOf(0) + "' " +
                                    "WHERE activity_id = '" + activity_id + "' ";
                            ls.executeNonQuery(update_record);
                        }
                        count = 0;
                        no_count =0;
                        na_count =0;
                        Log.d("00055", "Add: "+activity_id+" "+result_q1+" "+result_q2+" "+result_q3+" "+result_q4+" "+count);

                    }
                } catch (Exception e) {

                    Toast.makeText(ctx, "=-=-=-=Exception  " + e, Toast.LENGTH_SHORT).show();
                    Log.e("000333", "Exception " + e);
                }

            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addData();

                try{

                    ls.createAndOpenDB();

                    String[][] mData = ls.executeReader("Select * from Question3 where activity_id  = '" + activity_id + "'");

                    boolean isInserted;

                    if (mData != null) {
                        isInserted = ls.executeNonQuery("Update Question3 set " +
                                "question3_q1 = '" + result_q1 + "', " +
                                "question3_q2 = '" + result_q2 + "', " +
                                "question3_q3 = '" + result_q3 + "', " +
                                "question3_q4 = '" + result_q4 + "', " +
                                "question3_q5 = '" + result_q5 + "', " +
                                "question3_q6 = '" + result_q6 + "', " +
                                "question3_q7 = '" + result_q7 + "', " +
                                "question3_q8 = '" + result_q8 + "', " +
                                "question3_q9 = '" + result_q9 + "', " +
                                "question3_q10 = '" + result_q10 + "', " +
                                "question3_count = '" + count + "', " +
                                "question3_no_count = '" + no_count + "', " +
                                "question3_na_count = '" + na_count + "' " +
                                " where activity_id  = '" + activity_id + "'");

                        String update_record = "UPDATE Activities SET " +
                                "is_synced_checklist='" + String.valueOf(0) + "' " +
                                "WHERE activity_id = '" + activity_id + "' ";
                        ls.executeNonQuery(update_record);
                        if (isInserted == true) {
                            Toast.makeText(ctx, "Data Updated Successfully", Toast.LENGTH_SHORT).show();
                            Log.d("00055", "Update: "+activity_id+" "+result_q1+" "+result_q2+" "+result_q3+" "+result_q4+" "+count);
                            count = 0;
                            no_count =0;
                            na_count=0;
                        } else {
                            Toast.makeText(ctx, "Data not updated successfully", Toast.LENGTH_SHORT).show();
                        }

                    } else {

                        isInserted =  helper.question3(activity_id, result_q1, result_q2, result_q3, result_q4, result_q5,result_q6, result_q7,
                                result_q8,result_q9,result_q10,count,no_count,na_count);
                        if(isInserted) {
                            Toast.makeText(ctx, "Data added successfully", Toast.LENGTH_SHORT).show();
                        }
                        Log.d("00055", "Add: "+activity_id+" "+result_q1+" "+result_q2+" "+result_q3+" "+result_q4+" "+count);
                        count = 0;
                        no_count =0;
                        na_count=0;
                        String update_record = "UPDATE Activities SET " +
                                "is_synced_checklist='" + String.valueOf(0) + "' " +
                                "WHERE activity_id = '" + activity_id + "' ";
                        ls.executeNonQuery(update_record);
                    }
                    Intent intent= new Intent(ctx, Checklist_Q4.class);
                    intent.putExtra("activity_id",activity_id);
                    intent.putExtra("activity_month",activity_month);
                    startActivity(intent);
                } catch (Exception e) {
                    Toast.makeText(ctx, "=-=-=-=Exception  " + e, Toast.LENGTH_SHORT).show();
                    Log.e("000333", "Exception " + e);
                }

            }
        });

    }

    public void addData(){
        if(cb_yes_q1.isChecked()){
            result_q1="1";
            count+=1;
        }
        else if(cb_no_q1.isChecked()){
            result_q1="0";
            no_count +=1;
        }
        if(cb_yes_q2.isChecked()){
            result_q2="1";
            count+=1;
        }
        else if(cb_no_q2.isChecked()){
            result_q2="0";
            no_count +=1;
        }
        if(cb_yes_q3.isChecked()){
            result_q3="1";
            count+=1;
        }
        else if(cb_no_q3.isChecked()){
            result_q3="0";
            no_count +=1;
        }
        if(cb_yes_q4.isChecked()){
            result_q4="1";
            count+=1;
        }
        else if(cb_no_q4.isChecked()){
            result_q4="0";
            no_count +=1;
        }
        if(cb_yes_q5.isChecked()){
            result_q5="1";
            count+=1;
        }
        else if(cb_no_q5.isChecked()){
            result_q5="0";
            no_count +=1;
        }
        if(cb_yes_q6.isChecked()){
            result_q6="1";
            count+=1;
        }
        else if(cb_no_q6.isChecked()){
            result_q6="0";
            no_count +=1;
        }
        if(cb_yes_q7.isChecked()){
            result_q7="1";
            count+=1;
        }
        else if(cb_no_q7.isChecked()){
            result_q7="0";
            no_count +=1;
        }
        if(cb_yes_q8.isChecked()){
            result_q8="1";
            count+=1;
        }
        else if(cb_no_q8.isChecked()){
            result_q8="0";
            no_count +=1;
        }
        if(cb_yes_q9.isChecked()){
            result_q9="1";
            count+=1;
        }
        else if(cb_no_q9.isChecked()){
            result_q9="0";
            no_count +=1;
        }
        if(cb_yes_q10.isChecked()){
            result_q10="1";
            count+=1;
        }
        else if(cb_no_q10.isChecked()){
            result_q10="0";
            no_count +=1;
        }
        na_count=10-count-no_count;
        Log.d("000333", "addData: "+na_count);
    }

    public boolean isCompleted(String activity_id) {

        Lister lister = new Lister(ctx);
        lister.createAndOpenDB();

        String[][] mData = lister.executeReader("Select * From Question3 where activity_id = '" + activity_id + "'");

        try {

            if (mData.length > 0){
                Log.d("000111", "mData[0][1] =  " + mData[0][1]);
                Log.d("000111", "mData[0][2] =  " + mData[0][2]);
                Log.d("000111", "mData[0][3] =  " + mData[0][3]);
                Log.d("000111", "mData[0][4] =  " + mData[0][4]);
                Log.d("000111", "mData[0][5] =  " + mData[0][5]);
                return  true;

            }else {
                return false;
            }

        }

        catch (Exception e) {
            Log.d("111", e.getMessage());
            return false;
        }


    }

    private void setData(String activity_id) {

        Lister lister = new Lister(ctx);
        lister.createAndOpenDB();

        String[][] mData = lister.executeReader("Select * From Question3 where activity_id = '" + activity_id + "'");

        try {

            if (mData.length > 0) {

                Log.d("000111", "mData[0][1] =  " + mData[0][1]);
                Log.d("000111", "mData[0][2] =  " + mData[0][2]);
                Log.d("000111", "mData[0][3] =  " + mData[0][3]);
                Log.d("000111", "mData[0][4] =  " + mData[0][4]);


                if (mData[0][1].equalsIgnoreCase("0")) {
                    cb_no_q1.setChecked(true);
                } else if(mData[0][1].equalsIgnoreCase("1")) {
                    cb_yes_q1.setChecked(true);
                }
                if (mData[0][2].equalsIgnoreCase("0")) {
                    cb_no_q2.setChecked(true);
                } else if(mData[0][2].equalsIgnoreCase("1")) {
                    cb_yes_q2.setChecked(true);
                }
                if (mData[0][3].equalsIgnoreCase("0")) {
                    cb_no_q3.setChecked(true);
                } else if(mData[0][3].equalsIgnoreCase("1")) {
                    cb_yes_q3.setChecked(true);
                }
                if (mData[0][4].equalsIgnoreCase("0")) {
                    cb_no_q4.setChecked(true);
                } else if(mData[0][4].equalsIgnoreCase("1")) {
                    cb_yes_q4.setChecked(true);
                }
                if (mData[0][5].equalsIgnoreCase("0")) {
                    cb_no_q5.setChecked(true);
                } else if(mData[0][5].equalsIgnoreCase("1")) {
                    cb_yes_q5.setChecked(true);
                }
                if (mData[0][6].equalsIgnoreCase("0")) {
                    cb_no_q6.setChecked(true);
                } else if(mData[0][6].equalsIgnoreCase("1")) {
                    cb_yes_q6.setChecked(true);
                }
                if (mData[0][7].equalsIgnoreCase("0")) {
                    cb_no_q7.setChecked(true);
                } else if(mData[0][7].equalsIgnoreCase("1")) {
                    cb_yes_q7.setChecked(true);
                }
                if (mData[0][8].equalsIgnoreCase("0")) {
                    cb_no_q8.setChecked(true);
                } else if(mData[0][8].equalsIgnoreCase("1")) {
                    cb_yes_q8.setChecked(true);
                }
                if (mData[0][9].equalsIgnoreCase("0")) {
                    cb_no_q9.setChecked(true);
                } else if(mData[0][9].equalsIgnoreCase("1")) {
                    cb_yes_q9.setChecked(true);
                }
                if (mData[0][10].equalsIgnoreCase("0")) {
                    cb_no_q10.setChecked(true);
                } else if(mData[0][10].equalsIgnoreCase("1")) {
                    cb_yes_q10.setChecked(true);
                }

            }

        } catch (Exception e) {
            Log.d("111", e.getMessage());

        }
    }

    private void disableFeilds() {

        save.setVisibility(View.GONE);
        next.setVisibility(View.GONE);

        cb_yes_q1.setClickable(false);
        cb_yes_q2.setClickable(false);
        cb_yes_q3.setClickable(false);
        cb_yes_q4.setClickable(false);
        cb_yes_q5.setClickable(false);
        cb_yes_q6.setClickable(false);
        cb_yes_q7.setClickable(false);
        cb_yes_q8.setClickable(false);
        cb_yes_q9.setClickable(false);
        cb_yes_q10.setClickable(false);

        cb_no_q1.setClickable(false);
        cb_no_q2.setClickable(false);
        cb_no_q3.setClickable(false);
        cb_no_q4.setClickable(false);
        cb_no_q5.setClickable(false);
        cb_no_q6.setClickable(false);
        cb_no_q7.setClickable(false);
        cb_no_q8.setClickable(false);
        cb_no_q9.setClickable(false);
        cb_no_q10.setClickable(false);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent= new Intent(ctx, LHS_Checklist.class);
        intent.putExtra("activity_id",activity_id);
        intent.putExtra("activity_month",activity_month);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }
}


