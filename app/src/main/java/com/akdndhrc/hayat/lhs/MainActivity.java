package com.akdndhrc.hayat.lhs;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.media.MediaScannerConnection;
import android.os.Environment;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.akdndhrc.hayat.lhs.Adapter.Activities_Adapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;
import static android.Manifest.permission.CAMERA;
import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

public class MainActivity extends AppCompatActivity {
    Context ctx = MainActivity.this;
    RelativeLayout rl_planning, rl_search, rl_sync;
    private ViewPager viewPager;
    ViewPagerAdapter adapter;
    String date;
    ImageView iv_close;
    EditText et_date;
    public static final int RequestPermissionCodeLogin = 1;
    Button submit;
    String TodayDate;
    private TabLayout tabLayout;
    Spinner sp_hc_hw, sp_hc_list, sp_facility_outreach, sp_hw_list;
    RelativeLayout rl_hc_list, rl_hw_list;
    public static String login_useruid, login_username;
    String sp_health, sp_facility, s_date, sp_hc, sp_hw;
    String[][] mData;
    JSONObject jobj, jobj1;

    AlertDialog.Builder dialogBuilder;
    private DatePickerDialog.OnDateSetListener dateSetListener;
    int year, month, day;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        viewPager = (ViewPager) findViewById(R.id.viewpager);

        tabLayout = (TabLayout) findViewById(R.id.tabs);

        rl_planning = (RelativeLayout) findViewById(R.id.rl_planning);
        rl_search = (RelativeLayout) findViewById(R.id.rl_search);
        rl_sync = (RelativeLayout) findViewById(R.id.rl_sync);


        SimpleDateFormat dates = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();
        TodayDate = dates.format(c.getTime());

        //Get shared USer name
        try {
            SharedPreferences prefelse = getApplicationContext().getSharedPreferences("UserLogin", 0); // 0 - for private mode
            String usernaame = prefelse.getString("username", null); // getting String
            String shared_useruid = prefelse.getString("login_userid", null); // getting String
            login_useruid = shared_useruid;
            login_username = usernaame;
            Log.d("000222", "USER UID: " + login_useruid);

        } catch (Exception e) {
            Log.d("000222", "Shared Err:" + e.getMessage());
        }



        rl_planning.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(ctx, NewActivityAdd.class);
                startActivity(intent);


            }
        });

        rl_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                 Intent intent= new Intent(MainActivity.this, Execution.class);
                 startActivity(intent);
            }
        });

        rl_sync.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(MainActivity.this, Sync_Activity.class);
                startActivity(intent);
            }
        });


    }

    private void init_Directories() {

        try {

            String folder = Environment.getExternalStorageDirectory() + File.separator + "Hayat_LHS" + File.separator + "Videos";

            File directory = new File(folder);

            boolean success = true;
            if (!directory.exists()) {
                directory.mkdirs();
                Log.d("000147", " Video Folder Created");
            } else {
                Log.e("000147", "" + directory.getAbsolutePath());
                Log.d("000147", "Video Folder Exit");
            }
            if (success) {
                MediaScannerConnection.scanFile(this, new String[]{directory.toString()}, null, null);
                Log.d("000147", " IF Success");
            } else {
                Log.d("000147", "Else Success");
            }

        } catch (Exception e) {
            // Toast.makeText(this, "Faild to Create Folder", Toast.LENGTH_SHORT).show();
            Log.d("000147", "Failed" + e.getMessage());
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.AddFragment(new TodayActivities_Fragment(), "Today Activities");
        adapter.AddFragment(new InCompleteActivities_Fragment(), "InComplete Activities");
        adapter.AddFragment(new CompleteActivities_Fragment(), "Complete Activities");

        viewPager.setAdapter(adapter);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
    }

    public boolean checkPermission() {


        //  int FirstPermissionResult = ContextCompat.checkSelfPermission(getApplicationContext(), SEND_SMS);
        int SecondPermissionResult = ContextCompat.checkSelfPermission(getApplicationContext(), READ_EXTERNAL_STORAGE);
        //  int ThirdPermissionResult = ContextCompat.checkSelfPermission(getApplicationContext(), RECEIVE_SMS);
        //  int ForthPermissionResult = ContextCompat.checkSelfPermission(getApplicationContext(), WRITE_SYNC_SETTINGS);
        int FifthPermissionResult = ContextCompat.checkSelfPermission(getApplicationContext(), WRITE_EXTERNAL_STORAGE);

        return
                SecondPermissionResult == PackageManager.PERMISSION_GRANTED &&
                        // ThirdPermissionResult == PackageManager.PERMISSION_GRANTED &&
                        //ForthPermissionResult == PackageManager.PERMISSION_GRANTED &&
                        FifthPermissionResult == PackageManager.PERMISSION_GRANTED &&
                        SecondPermissionResult == PackageManager.PERMISSION_GRANTED;
        //EigthPermissionResult == PackageManager.PERMISSION_GRANTED;

    }

    private void requestPermission() {


        ActivityCompat.requestPermissions(MainActivity.this, new String[]
                {
                        // SEND_SMS,READ_PHONE_STATE,WRITE_SYNC_SETTINGS
                        WRITE_EXTERNAL_STORAGE, READ_EXTERNAL_STORAGE, CAMERA, ACCESS_FINE_LOCATION
                }, RequestPermissionCodeLogin);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {

            case RequestPermissionCodeLogin:

                if (grantResults.length > 0) {

                    boolean WriteEXtPermission = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    boolean ReadExtPermission = grantResults[1] == PackageManager.PERMISSION_GRANTED;
                    //  boolean RecvMSGPermission = grantResults[1] == PackageManager.PERMISSION_GRANTED;
                    // boolean ReadPhoneStatePermission = grantResults[2] == PackageManager.PERMISSION_GRANTED;
                    //  boolean WritesyncPermission = grantResults[3] == PackageManager.PERMISSION_GRANTED;
                    //  boolean CallPhoneStatePermission = grantResults[5] == PackageManager.PERMISSION_GRANTED;
                    // boolean SendSMSStatePermission = grantResults[7] == PackageManager.PERMISSION_GRANTED;

                    if (WriteEXtPermission && ReadExtPermission) {

                        Log.d("000222", "All Permission Allowed");

                        /*serviceLocation = new ServiceLocation(ctx);
                        serviceLocation.locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
                        serviceLocation.callAsynchronousTask();*/

                        init_Directories();


                    } else {
                        Log.d("000222", "All Permission Not Allowed");

                        if (!WriteEXtPermission && !ReadExtPermission) {
                            Toast.makeText(ctx, "فون سٹوریج کی پرمیشن کو اجازت دیں", Toast.LENGTH_SHORT).show();
                        } else {
                            init_Directories();
                        }
                    }
                }

                break;
        }
    }


    @Override
    public void onBackPressed() {

        android.app.AlertDialog.Builder dialogBuilder = new android.app.AlertDialog.Builder(ctx);
        // dialogBuilder.setIcon(ctx.getResources().getDrawable(R.drawable.ic_exit_to_app_black_24dp));
        dialogBuilder.setTitle("Logout");
        dialogBuilder.setMessage("Are you sure you want to logout?");
        dialogBuilder.setPositiveButton("Yes", null);
        dialogBuilder.setNegativeButton("No", null);

        final android.app.AlertDialog alertDialog = dialogBuilder.create();
        alertDialog.show();

        Button positiveButton = alertDialog.getButton(DialogInterface.BUTTON_POSITIVE);
        // override the text color of positive button
        positiveButton.setTextColor(getResources().getColor(R.color.dark_blue_color));

        positiveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent newIntent = new Intent(ctx, Login_Activity.class);
                newIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                newIntent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                newIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(newIntent);
            }
        });

        Button negativeButton = alertDialog.getButton(DialogInterface.BUTTON_NEGATIVE);
        // override the text color of negative button
        negativeButton.setTextColor(getResources().getColor(android.R.color.holo_red_dark));
        // provides login_dialog_shape implementation to negative button click
        negativeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Toast.makeText(getApplicationContext(),"Work ok Cancel",Toast.LENGTH_SHORT).show();
                alertDialog.dismiss();
            }
        });
    }



    private void sendPostRequestMembers() {

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
                       /* boolean m2 = ls.executeNonQuery(Helper.CREATE_TABLE_KMEMBER);
                        String date = "";
*/                      //childhealth
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

                        boolean m2 = ls.executeNonQuery(Helper.CREATE_TABLE_KMEMBER);

                       // JSONObject obj1 = new JSONObject(String.valueOf(m_jArry));
                       // JSONArray m_jArray2 = obj1.getJSONArray("age_1233");
                      //  Log.d("000999", "JSONARRAY Leng:   " + m_jArray2.toString());

                            String query_form_get_data = "insert into MONTHLY_REPORT (household_registered_lhw, tap, spring, handpump, well, other," +
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
                                "lhs,dco,adc,fpo,ppiu)" +
                                    " values " +
                                    "(" +
                                    "'" + basic_info.getString("no_selected") + "'," +
                                    "'" + basic_info.getString("tap") + "'," +
                                    "'" + basic_info.getString("spring") + "'," +
                                    "'" + basic_info.getString("hand_pump") + "'," +
                                    "'" + basic_info.getString("well") + "'," +
                                    "'" + basic_info.getString("other") + "'," +
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
                                    "'" + supervision.getString("ppiu") + "'" +
                                    ")";
                            Log.d("000555", query_form_get_data);
                            boolean query = ls.executeNonQuery(query_form_get_data);
                           Log.d("000999", "Receiving Data: " +query);

                     //   progressDialog.dismiss();
                    } catch (Exception e) {
                     //   progressDialog.dismiss();
                        e.printStackTrace();
                        Log.d("000222", "Catch: " + e.getMessage());
                        Toast.makeText(ctx, "No data found for " + "users " + String.valueOf(e.getMessage()), Toast.LENGTH_SHORT).show();
                    }

                } catch (Exception e) {
                  //  progressDialog.dismiss();
                    Log.d("000222", "Err:    " + e.getMessage());
                    Toast.makeText(ctx, "Something wrong!!", Toast.LENGTH_SHORT).show();

                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //progressDialog.dismiss();
                Log.d("000222", "error    " + error.getMessage());
                //  Toast.makeText(Login_Activity.this, "برائے مہربانی انٹرنیٹ کنکشن چیک کریں", Toast.LENGTH_SHORT).show();


            }
        }) {
            @Override
            protected Map<String, String> getParams() {

                Map<String, String> params = new HashMap<String, String>();
                params.put("month", "10");
                params.put("year", "2019");
                params.put("uid", login_useruid);
                params.put("lhw", "edb5897998ec165546c561c3c88e806330b149e8c010cf1693cfba5ef4edbc50");


                Log.d("000222", "mParam " + params);

                return params;
            }
        };

        AppController.getInstance().addToRequestQueue(strReq1, REQUEST_TAG);
    }


}