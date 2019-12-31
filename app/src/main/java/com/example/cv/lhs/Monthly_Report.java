package com.example.cv.lhs;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ExpandableListView;

import com.example.cv.lhs.Adapter.Monthly_Report_Extandable_List;
import com.example.cv.lhs.Adapter.Validation_Extandable_List;
import com.example.cv.lhs.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Monthly_Report extends AppCompatActivity {
    Monthly_Report_Extandable_List listAdapter;
    ExpandableListView expListView;
    String activity_id,activity_month;
    List<String> listDataHeader;
    List<String> listCount;
    HashMap<String, List<String>> listDataChild;
    List<String> live_birth1, low_birth1,new_preg1,total_preg1;
    String live_birth[][], low_birth[][], new_preg[][], totl_preg[][];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_monthly__report);

        // get the listview
        expListView = (ExpandableListView) findViewById(R.id.lvExp);

        activity_id= getIntent().getExtras().getString("activity_id");
        activity_month= getIntent().getExtras().getString("activity_month");

        // setting list adapter


        // preparing list data

        listDataHeader = new ArrayList<String>();
        listDataChild = new HashMap<String, List<String>>();
        prepareListData();

        listAdapter = new Monthly_Report_Extandable_List(this, listDataHeader, listDataChild,activity_id);

        expListView.setAdapter(listAdapter);

    }

    /*
     * Preparing the list data
     */
    private void prepareListData() {


        try{
            Lister ls= new Lister(Monthly_Report.this);
            ls.createAndOpenDB();

            String res= "Select member_id,full_name, added_by, month, year from live_birth where month = '"+activity_month+"' ";
            String res1= "Select member_id,full_name, added_by, month, year from total_preg where month = '"+activity_month+"' ";
            String res2= "Select member_id,full_name, added_by, month, year from new_preg where month = '"+activity_month+"' ";
            String res3= "Select member_id,full_name, added_by, month, year from low_birth where month = '"+activity_month+"' ";

            //basicinfo= ls.executeReader("Select household_registered_lhw, tap, spring, handpump, well, other from MONTHLY_REPORT ");
            live_birth= ls.executeReader(res);
            totl_preg= ls.executeReader(res1);
            new_preg= ls.executeReader(res2);
            low_birth= ls.executeReader(res3);

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
        listDataHeader.add("Low Birth Weight Babies");
        listDataHeader.add("Newly registered pregnant Women");
        listDataHeader.add("Total Pregnant Women");
        listDataHeader.add("Live Births");

            // Adding child data
        try {
            live_birth1 = new ArrayList<String>();
            for (int i = 0; i < live_birth.length; i++) {
                live_birth1.add(live_birth[i][0] + "@" + live_birth[i][1] + "@" + live_birth[i][3] + "@" + live_birth[i][4]);
                Log.d("000666", "prepareListData: " + live_birth[i][0] + "@" + live_birth[i][1] + "@" + live_birth[i][3] + "@" + live_birth[i][4]);
            }
        }
        catch (Exception e){

        }
            try {
                low_birth1 = new ArrayList<String>();
                for (int i = 0; i < low_birth.length; i++) {
                    low_birth1.add(low_birth[i][0] + "@" + low_birth[i][1] + "@" + low_birth[i][3] + "@" + low_birth[i][4]);
                    Log.d("000666", "prepareListData: " + low_birth[i][0] + "@" + low_birth[i][1]);

                }
            }
            catch (Exception e){

            }

            try {
                new_preg1 = new ArrayList<String>();
                for (int i = 0; i < new_preg.length; i++) {
                    new_preg1.add(new_preg[i][0] + "@" + new_preg[i][1] + "@" + new_preg[i][3] + "@" + new_preg[i][4]);
                    Log.d("000666", "prepareListData: " + new_preg[i][0] + "@" + new_preg[i][1] + "@" + new_preg[i][3] + "@" + new_preg[i][4]);
                }
            }
            catch (Exception e){

            }

            try {
                total_preg1 = new ArrayList<String>();
                for (int i = 0; i < totl_preg.length; i++) {
                    total_preg1.add(totl_preg[i][0] + "@" + totl_preg[i][1] + "@" + totl_preg[i][3] + "@" + totl_preg[i][4]);
                    Log.d("000666", "prepareListData: " + totl_preg[i][0] + "@" + totl_preg[i][1]);
                }
            }
            catch (Exception e){

            }

            listDataChild.put(listDataHeader.get(0), low_birth1); // Header, Child data
            listDataChild.put(listDataHeader.get(1), new_preg1);
            listDataChild.put(listDataHeader.get(2), total_preg1);
            listDataChild.put(listDataHeader.get(3), live_birth1);

    }

    public void onBackPressed() {
        super.onBackPressed();
        Intent intent= new Intent(Monthly_Report.this, Checklist_Validation_Button.class);
        intent.putExtra("activity_id",activity_id);
        intent.putExtra("activity_month",activity_month);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }
}
