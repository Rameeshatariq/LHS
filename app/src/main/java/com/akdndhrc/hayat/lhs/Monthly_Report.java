package com.akdndhrc.hayat.lhs;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.akdndhrc.hayat.lhs.Adapter.Monthly_Report_Extandable_List;
import com.akdndhrc.hayat.lhs.Adapter.Validation_Extandable_List;
import com.akdndhrc.hayat.lhs.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import static android.os.Looper.prepare;

public class Monthly_Report extends AppCompatActivity {
    Monthly_Report_Extandable_List listAdapter;
    Context ctx= Monthly_Report.this;
    ExpandableListView expListView;
    String activity_id,activity_month,TodayDate,type,activity_year;
    private DatePickerDialog.OnDateSetListener dateSetListener;
    private DatePickerDialog.OnDateSetListener dateSetListener2;
  static  List<String> listDataHeader;
    List<String> listCount;
    Dialog dialog;
    int year, month, day;
    ImageView iv_close;
    Spinner sp_type;
    EditText et_dob, et_vacinated_on, et_child_name, et_father_name, et_contact, et_father_cnic, et_vaccineId, et_vaccine_card_no;
    static HashMap<String, List<String>> listDataChild;
    FloatingActionButton floatingActionButton;
    static List<String> live_birth1, low_birth1,new_preg1,total_preg1,high_risk1;
    String live_birth[][], low_birth[][], new_preg[][], totl_preg[][],high_risk[][];
    Button prepare;
    String added_by;
    Lister ls;
    String vaccine_name, child_name, dob, vacinated_on, father_name, father_cnic, vaccine_id, contact,vaccine_card_no;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_monthly__report);

        // get the listview
        expListView = (ExpandableListView) findViewById(R.id.lvExp);

        activity_id= getIntent().getExtras().getString("activity_id");
        activity_month= getIntent().getExtras().getString("activity_month");

        ls=new Lister(ctx);
        ls.createAndOpenDB();

        String myear[][]= ls.executeReader("Select activity_year from Activities where activity_id = '"+activity_id+"' ");
        activity_year=myear[0][0];

        Log.d("000888", "onCreate: "+activity_year);


        floatingActionButton = (FloatingActionButton) findViewById(R.id.fab);

        // setting list adapter


        // preparing list data

        listDataHeader = new ArrayList<String>();
        listDataChild = new HashMap<String, List<String>>();

        live_birth1 = new ArrayList<String>();
        low_birth1 = new ArrayList<String>();
        new_preg1 = new ArrayList<String>();
        high_risk1 = new ArrayList<String>();
        total_preg1 = new ArrayList<String>();


        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dialog_Planning();
            }
        });


    }

    private void Dialog_Planning() {
        dialog= new Dialog(ctx);
        LayoutInflater layout = LayoutInflater.from(ctx);
        final View dialogView = layout.inflate(R.layout.manual_vaccine_prepare_dialog, null);
        dialog.setContentView(dialogView);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(false);


        iv_close = (ImageView) dialog.findViewById(R.id.iv_close);
        sp_type = (Spinner) dialog.findViewById(R.id.sp_vaccines);
        et_child_name = (EditText) dialog.findViewById(R.id.et_childName);
        et_father_name = (EditText) dialog.findViewById(R.id.et_fatherName);
        et_father_cnic = (EditText) dialog.findViewById(R.id.et_fatherCNIC);
        et_vacinated_on = (EditText) dialog.findViewById(R.id.et_dateofvaccine);
        et_contact = (EditText) dialog.findViewById(R.id.et_contact);
        et_dob = (EditText) dialog.findViewById(R.id.et_dob);
        prepare = (Button) dialog.findViewById(R.id.btn_vaccine_prepare);
        dialog.show();

        SimpleDateFormat dates = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
        Calendar c = Calendar.getInstance();
        TodayDate = dates.format(c.getTime());
        Log.d("000555", "Today Date: " + TodayDate);

        sp_type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(sp_type.getSelectedItemPosition() == 0){

                    type="0";
                }
                else {
                    type = sp_type.getItemAtPosition(i).toString();
                    Log.d("0006666", "onItemSelected: " + type);
                    }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        et_dob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Calendar cal = Calendar.getInstance();

                year = cal.get(Calendar.YEAR);
                month = cal.get(Calendar.MONTH);
                day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(
                        ctx,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        dateSetListener2,
                        year, month, day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });


        dateSetListener2 = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;
                Log.d("TAG", "onDateSet: yyyy/mm/dd: " + day + "/" + month + "/" + year);

                String date = year + "-" + month + "-" + day;
                et_dob.setText(date);

            }
        };

        et_vacinated_on.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Calendar cal = Calendar.getInstance();

                year = cal.get(Calendar.YEAR);
                month = cal.get(Calendar.MONTH);
                day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(
                        ctx,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        dateSetListener,
                        year, month, day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });


        dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;
                Log.d("TAG", "onDateSet: yyyy/mm/dd: " + day + "/" + month + "/" + year);

                String date = year + "-" + month + "-" + day;
                et_vacinated_on.setText(date);

            }
        };


        iv_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });


        prepare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                prepare();

                Log.d("000888", "type: "+type);

                if(type.equals("Live birth")){
                    insertdb_lifebirth();
                }
                else if(type.equals("Low birth")){
                    insertdb_lowbirth();
                }
                else if(type.equals("Total pregnant")){
                    insertdb_totalpreg();
                }
                else if(type.equals("New pregnant")){
                    insertdb_newpreg();
                }
                else if(type.equals("High risk")){
                    insertdb_highrisk();
                }

            }
        });
    }

    private void insertdb_newpreg(){
        String added_on = String.valueOf(System.currentTimeMillis());

        Lister ls = new Lister(ctx);
        ls.createAndOpenDB();
        String query_form_get_data = "insert into new_preg (member_id, full_name, added_by, month,year)" +
                " values " +
                "(" +
                "'" + child_name+""+father_name + "'," +
                "'" + child_name + "'," +
                "'" + added_by + "'," +
                "'" + activity_month + "'," +
                "'" + activity_year + "'" +
                ")";

        Boolean res = ls.executeNonQuery(query_form_get_data);
        Log.d("000555","Data: "+query_form_get_data);
        Log.d("000555","Query: "+res);

        if(res){
            Toast.makeText(ctx, "Manual data added to database", Toast.LENGTH_SHORT).show();

            dialog.dismiss();

            Intent intent= new Intent(ctx, Monthly_Report.class);
            intent.putExtra("activity_id",activity_id);
            intent.putExtra("activity_month",activity_month);
            ctx.startActivity(intent);

        }
        else{
            Toast.makeText(ctx, "Manual data not added to database", Toast.LENGTH_SHORT).show();

        }
    }
    private void insertdb_totalpreg(){
        String added_on = String.valueOf(System.currentTimeMillis());

        Lister ls = new Lister(ctx);
        ls.createAndOpenDB();
        String query_form_get_data = "insert into total_preg (member_id, full_name, added_by, month,year)" +
                " values " +
                "(" +
                "'" + child_name+""+father_name + "'," +
                "'" + child_name + "'," +
                "'" + added_by + "'," +
                "'" + activity_month + "'," +
                "'" + activity_year + "'" +
                ")";

        Boolean res = ls.executeNonQuery(query_form_get_data);
        Log.d("000555","Data: "+query_form_get_data);
        Log.d("000555","Query: "+res);

        if(res){
            Toast.makeText(ctx, "Manual data added to database", Toast.LENGTH_SHORT).show();

            dialog.dismiss();

            Intent intent= new Intent(ctx, Monthly_Report.class);
            intent.putExtra("activity_id",activity_id);
            intent.putExtra("activity_month",activity_month);
            ctx.startActivity(intent);

        }
        else{
            Toast.makeText(ctx, "Manual data not added to database", Toast.LENGTH_SHORT).show();

        }
    }

    private void insertdb_lifebirth(){
        String added_on = String.valueOf(System.currentTimeMillis());

        Lister ls = new Lister(ctx);
        ls.createAndOpenDB();
        String query_form_get_data = "insert into life_birth (member_id, full_name, added_by, month,year)" +
                " values " +
                "(" +
                "'" + child_name+""+father_name + "'," +
                "'" + child_name + "'," +
                "'" + added_by + "'," +
                "'" + activity_month + "'," +
                "'" + activity_year + "'" +
                ")";

        Boolean res = ls.executeNonQuery(query_form_get_data);
        Log.d("000555","Data: "+query_form_get_data);
        Log.d("000555","Query: "+res);

        if(res){
            Toast.makeText(ctx, "Manual data added to database", Toast.LENGTH_SHORT).show();

            dialog.dismiss();

            Intent intent= new Intent(ctx, Monthly_Report.class);
            intent.putExtra("activity_id",activity_id);
            intent.putExtra("activity_month",activity_month);
            ctx.startActivity(intent);

        }
        else{
            Toast.makeText(ctx, "Manual data not added to database", Toast.LENGTH_SHORT).show();

        }
    }

    private void insertdb_lowbirth(){
        String added_on = String.valueOf(System.currentTimeMillis());

        Lister ls = new Lister(ctx);
        ls.createAndOpenDB();
        String query_form_get_data = "insert into low_birth (member_id, full_name, added_by, month,year)" +
                " values " +
                "(" +
                "'" + child_name+""+father_name + "'," +
                "'" + child_name + "'," +
                "'" + added_by + "'," +
                "'" + activity_month + "'," +
                "'" + activity_year + "'" +
                ")";

        Boolean res = ls.executeNonQuery(query_form_get_data);
        Log.d("000555","Data: "+query_form_get_data);
        Log.d("000555","Query: "+res);

        if(res){
            Toast.makeText(ctx, "Manual data added to database", Toast.LENGTH_SHORT).show();

            dialog.dismiss();

            Intent intent= new Intent(ctx, Monthly_Report.class);
            intent.putExtra("activity_id",activity_id);
            intent.putExtra("activity_month",activity_month);
            ctx.startActivity(intent);

        }
        else{
            Toast.makeText(ctx, "Manual data not added to database", Toast.LENGTH_SHORT).show();

        }
    }

    private void insertdb_highrisk(){
        String added_on = String.valueOf(System.currentTimeMillis());

        Lister ls = new Lister(ctx);
        ls.createAndOpenDB();
        String query_form_get_data = "insert into high_risk (member_id, full_name, added_by, month,year)" +
                " values " +
                "(" +
                "'" + child_name+""+father_name + "'," +
                "'" + child_name + "'," +
                "'" + added_by + "'," +
                "'" + activity_month + "'," +
                "'" + activity_year + "'" +
                ")";

        Boolean res = ls.executeNonQuery(query_form_get_data);
        Log.d("000555","Data: "+query_form_get_data);
        Log.d("000555","Query: "+res);

        if(res){
            Toast.makeText(ctx, "Manual Data added to database", Toast.LENGTH_SHORT).show();

            dialog.dismiss();

            Intent intent= new Intent(ctx, Monthly_Report.class);
            intent.putExtra("activity_id",activity_id);
            intent.putExtra("activity_month",activity_month);
            ctx.startActivity(intent);

        }
        else{
            Toast.makeText(ctx, "Manual Data not added to database", Toast.LENGTH_SHORT).show();

        }
    }


    private void prepare(){

        if(type.equals("0")){
            Toast.makeText(ctx, "Please select one type", Toast.LENGTH_SHORT).show();
            return;
        }
        if(et_dob.getText().toString().isEmpty()){
            Toast.makeText(ctx, "Please enter date of birth", Toast.LENGTH_SHORT).show();
            return;
        }
        else{
            dob=et_dob.getText().toString();
        }
        if(et_contact.getText().toString().isEmpty()){
            //Toast.makeText(ctx, "Please Enter Contact", Toast.LENGTH_SHORT).show();
            //return;
        }
        else{
            contact=et_contact.getText().toString();

        }
        if(et_vacinated_on.getText().toString().isEmpty()){
            Toast.makeText(ctx, "Please enter vaccination date", Toast.LENGTH_SHORT).show();
            return;
        }
        else{
            vacinated_on=et_vacinated_on.getText().toString();
        }
        if(et_child_name.getText().toString().isEmpty()){
            Toast.makeText(ctx, "Please enter child name", Toast.LENGTH_SHORT).show();
            return;
        }
        else{
            child_name=et_child_name.getText().toString();
        }
        if(et_father_name.getText().toString().isEmpty()){
            Toast.makeText(ctx, "Please enter father name", Toast.LENGTH_SHORT).show();
            return;
        }
        else{
            father_name=et_father_name.getText().toString();
        }
        if(et_father_cnic.getText().toString().isEmpty()){
            //Toast.makeText(ctx, "Please Enter Father CNIC", Toast.LENGTH_SHORT).show();
           // return;
        }
        else {
            father_cnic=et_father_cnic.getText().toString();
        }
    }


    /*
     * Preparing the list data
     */


    private void prepareListData() {

        listDataChild.clear();
        listDataHeader.clear();

        live_birth1.clear();
        low_birth1.clear();
        new_preg1.clear();
        high_risk1.clear();
        total_preg1.clear();

        try{
            Lister ls= new Lister(Monthly_Report.this);
            ls.createAndOpenDB();

            String res= "Select member_id,full_name, added_by, month, year from live_birth where month = '"+activity_month+"' ";
            String res1= "Select member_id,full_name, added_by, month, year from total_preg where month = '"+activity_month+"' ";
            String res2= "Select member_id,full_name, added_by, month, year from new_preg where month = '"+activity_month+"' ";
            String res3= "Select member_id,full_name, added_by, month, year from low_birth where month = '"+activity_month+"' ";
            String res4= "Select member_id,full_name, added_by, month, year from high_risk where month = '"+activity_month+"' ";


            //basicinfo= ls.executeReader("Select household_registered_lhw, tap, spring, handpump, well, other from MONTHLY_REPORT ");
            live_birth= ls.executeReader(res);
            totl_preg= ls.executeReader(res1);
            new_preg= ls.executeReader(res2);
            low_birth= ls.executeReader(res3);
            high_risk= ls.executeReader(res4);

            Log.d("000999", "prepareListData: "+res);
            Log.d("000999", "prepareListData: "+res1);
            Log.d("000999", "prepareListData: "+res2);
            Log.d("000999", "prepareListData: "+res3);

            Log.d("000999", "prepareListData: "+live_birth);
            Log.d("000999", "prepareListData: "+totl_preg);
            Log.d("000999", "prepareListData: "+new_preg);
            Log.d("000999", "prepareListData: "+low_birth);
        }
        catch (Exception e){
            Log.d("000999", "prepareListData: "+e);
        }


        // Adding header data
        listDataHeader.add("Low birth weight babies");
        listDataHeader.add("Newly registered pregnant women");
        listDataHeader.add("Total pregnant women");
        listDataHeader.add("Live births");
        listDataHeader.add("High risk");

            // Adding child data
        try {
            for (int i = 0; i < live_birth.length; i++) {
                live_birth1.add(live_birth[i][0] + "@" + live_birth[i][1] + "@" + live_birth[i][3] + "@" + live_birth[i][4]);
                Log.d("000666", "prepareListData: " + live_birth[i][0] + "@" + live_birth[i][1] + "@" + live_birth[i][3] + "@" + live_birth[i][4]);
            }
        }
        catch (Exception e){

        }
            try {
                for (int i = 0; i < low_birth.length; i++) {
                    low_birth1.add(low_birth[i][0] + "@" + low_birth[i][1] + "@" + low_birth[i][3] + "@" + low_birth[i][4]);
                    Log.d("000666", "prepareListData: " + low_birth[i][0] + "@" + low_birth[i][1]);

                }
            }
            catch (Exception e){

            }

            try {
                for (int i = 0; i < new_preg.length; i++) {
                    new_preg1.add(new_preg[i][0] + "@" + new_preg[i][1] + "@" + new_preg[i][3] + "@" + new_preg[i][4]);
                    Log.d("000666", "prepareListData: " + new_preg[i][0] + "@" + new_preg[i][1] + "@" + new_preg[i][3] + "@" + new_preg[i][4]);
                }
            }
            catch (Exception e){

            }

            try {
                for (int i = 0; i < totl_preg.length; i++) {
                    total_preg1.add(totl_preg[i][0] + "@" + totl_preg[i][1] + "@" + totl_preg[i][3] + "@" + totl_preg[i][4]);
                    Log.d("000666", "prepareListData: " + totl_preg[i][0] + "@" + totl_preg[i][1]);
                }
            }
            catch (Exception e){

            }

        try {
            for (int i = 0; i < high_risk.length; i++) {
                high_risk1.add(high_risk[i][0] + "@" + high_risk[i][1] + "@" + high_risk[i][3] + "@" + high_risk[i][4]);
                Log.d("000666", "prepareListData: " + high_risk[i][0] + "@" + high_risk[i][1]);
            }
        }
        catch (Exception e){

        }


            listDataChild.put(listDataHeader.get(0), low_birth1); // Header, Child data
            listDataChild.put(listDataHeader.get(1), new_preg1);
            listDataChild.put(listDataHeader.get(2), total_preg1);
            listDataChild.put(listDataHeader.get(3), live_birth1);
            listDataChild.put(listDataHeader.get(4), high_risk1);

    }

    public void onBackPressed() {
        super.onBackPressed();
        Intent intent= new Intent(Monthly_Report.this, Checklist_Validation_Button.class);
        intent.putExtra("activity_id",activity_id);
        intent.putExtra("activity_month",activity_month);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    protected void onResume() {
        super.onResume();

        prepareListData();
        listAdapter = new Monthly_Report_Extandable_List(this, listDataHeader, listDataChild, activity_id);

        expListView.setAdapter(listAdapter);
    }
}
