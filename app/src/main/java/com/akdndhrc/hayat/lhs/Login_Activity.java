package com.akdndhrc.hayat.lhs;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaScannerConnection;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.support.annotation.RequiresApi;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.lang.reflect.Array;
import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static android.Manifest.permission.CAMERA;
import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

public class Login_Activity extends AppCompatActivity {

    Context ctx = Login_Activity.this;

    Button btn_Login;
    public String user_previlage, login_useruid, login_username;
    EditText et_username, et_password;
    public static final int RequestPermissionCodeLogin = 1;
    Dialog alertDialog;
    Dialog dialog;
    TextView txt_app_version_name, txt_last_sync_datettime;
    public static String username, TodayDate;

    double latitude;
    double longitude;
    Snackbar snackbar;
    // ServiceLocation serviceLocation;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_login);

        Thread.setDefaultUncaughtExceptionHandler(new DefaultExceptionHandler(this, Login_Activity.class));

        SimpleDateFormat dates = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();
        TodayDate = dates.format(c.getTime());
        Log.d("000862", "Today Date : " + TodayDate);


        if (checkPermission()) {
            Log.d("000862", "All permission allowed now");

            init_Directories();

        } else {
            requestPermission();
            Log.d("000862", "Request Permission");
        }


        //TextVIew
        txt_last_sync_datettime = findViewById(R.id.txt_last_sync_datettime);
        txt_app_version_name = findViewById(R.id.txt_app_version_name);

        //Edittext
        et_username = findViewById(R.id.et_username);
        et_password = findViewById(R.id.et_password);
      //  et_username.setText("lhs_gb");
      //  et_password.setText("lhs123");

        //Button
        btn_Login = findViewById(R.id.btn_Login);


        PackageInfo pinfo = null;
        try {
            pinfo = getPackageManager().getPackageInfo(getPackageName(), 0);
            int versionNumber = pinfo.versionCode;
            String versionName = pinfo.versionName;

            txt_app_version_name.setText("Version: " + versionName);
            Log.d("000147", "Ver Name : " + versionName);


        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            Log.d("000862", "Ver Err:" + e.getMessage());
        }

        //Get shared USer name
        try {
            SharedPreferences prefelse = getApplicationContext().getSharedPreferences("UserLogin", 0); // 0 - for private mode
            String shared_username = prefelse.getString("username", null); // getting String

            et_username.setText(shared_username);
            Log.d("000862", "Last UserName: " + shared_username);

        } catch (Exception e) {
            Log.d("000862", "Shared Err:" + e.getMessage());
        }


        //btn_Login.performClick();


        btn_Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {

                if (et_username.getText().toString().isEmpty()) {
                    //btn_jamaa_kre.setVisibility(View.GONE);
                    Toast.makeText(getApplicationContext(), "Please Enter Username", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (et_password.getText().toString().isEmpty()) {
                    //btn_jamaa_kre.setVisibility(View.GONE);
                    Toast.makeText(getApplicationContext(), "Please Enter Password", Toast.LENGTH_SHORT).show();
                    return;
                }

                alertDialog = new Dialog(ctx);
                LayoutInflater layout = LayoutInflater.from(ctx);
                final View dialogView = layout.inflate(R.layout.login_dialog_loading, null);
                alertDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                alertDialog.setContentView(dialogView);
                alertDialog.setCanceledOnTouchOutside(false);
                alertDialog.setCancelable(false);
                alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                alertDialog.show();


                try {
                    Lister ls = new Lister(ctx);
                    ls.createAndOpenDB();
                    String[][] data_check = ls.executeReader("Select count(*) from USERS");
                    if (Integer.parseInt(data_check[0][0]) > 0) {
                        Log.d("000862", "USER COUNT IFFF: " + data_check[0][0]);
                        if (haveNetworkConnection(ctx) > 0) {
                            Log.d("000862", "INTERNET AVAILAB");
                            sendPostRequest(et_username.getText().toString(), et_password.getText().toString());
                        } else {

                            Log.d("000862", "NO INTERNET OFFLINE LOGIN");
                        }

                    } else {
                        Log.d("000862", "USER COUNT ELSE: " + data_check[0][0]);
                        if (haveNetworkConnection(ctx) > 0) {
                            sendPostRequest(et_username.getText().toString(), et_password.getText().toString());

                        } else {
                            alertDialog.dismiss();

                            Dialog dialog1 = new Dialog(ctx);
                            LayoutInflater layout1 = LayoutInflater.from(ctx);
                            final View dialogView1 = layout1.inflate(R.layout.login_error_dialog, null);

                            dialog1.setContentView(dialogView1);
                            dialog1.setCanceledOnTouchOutside(true);
                            dialog1.setCancelable(true);
                            dialog1.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                            dialog1.show();
                            return;
                        }

                    }

                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {

                            try {
                                checkLoginCreden(et_username.getText().toString(), et_password.getText().toString());

                            } catch (Exception e) {
                                alertDialog.dismiss();
                                Log.d("000862", "Er" + e.getMessage());
                            } finally {

                                alertDialog.dismiss();
                            }
                        }

                    }, 4000);
                } catch (Exception e) {
                    Log.d("000862", "Er: " + e.getMessage());

                    if (haveNetworkConnection(ctx) > 0) {
                        sendPostRequest(et_username.getText().toString(), et_password.getText().toString());
                    } else {
                        alertDialog.dismiss();
                        Dialog dialog1 = new Dialog(ctx);
                        LayoutInflater layout2 = LayoutInflater.from(ctx);
                        final View dialogView2 = layout2.inflate(R.layout.login_error_dialog, null);

                        dialog1.setContentView(dialogView2);
                        dialog1.setCanceledOnTouchOutside(true);
                        dialog1.setCancelable(true);
                        dialog1.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                        dialog1.show();
                        return;
                    }
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {

                            try {
                                checkLoginCreden(et_username.getText().toString(), et_password.getText().toString());
                            } catch (Exception e) {
                                Log.d("000862", "Er" + e.getMessage());
                            } finally {
                                alertDialog.dismiss();
                            }
                        }

                    }, 4000);
                }


            }
        });
    }


    private void init_Directories() {

        try {

            String folder = Environment.getExternalStorageDirectory() + File.separator + "Hayat_DHSV" + File.separator + "Videos";

            File directory = new File(folder);

            boolean success = true;
            if (!directory.exists()) {
                directory.mkdirs();
                Log.d("000862", " Video Folder Created");
            } else {
                Log.e("000862", "" + directory.getAbsolutePath());
                Log.d("000862", "Video Folder Exit");
            }
            if (success) {
                MediaScannerConnection.scanFile(this, new String[]{directory.toString()}, null, null);
                Log.d("000862", " IF Success");
            } else {
                Log.d("000862", "Else Success");
            }

        } catch (Exception e) {
            // Toast.makeText(this, "Faild to Create Folder", Toast.LENGTH_SHORT).show();
            Log.d("000862", "Failed" + e.getMessage());
        }
    }


    private void sendPostRequest(final String username, final String password) {

        String url = "https://development.api.teekoplus.akdndhrc.org/lhs/login";

        Log.d("000862", "mURL " + url);
        //  Toast.makeText(getApplicationContext(),"1",Toast.LENGTH_LONG).show();

        String REQUEST_TAG = "volleyStringRequest";

        StringRequest strReq = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Log.d("000yyy", "Response:    " + response);


                try {

                    /////////////Current Date in Persian and save in Shared Pref
                    SharedPreferences preferences = getApplicationContext().getSharedPreferences("MyPreferences", 0); // 0 - for private mode
                    SharedPreferences.Editor editor = preferences.edit();

                    String timeStamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
                    Log.d("000862", "Current  DateTime: " + timeStamp);
                    editor.putString("last_sync_time", timeStamp); // Storing string
                    editor.apply();

                } catch (Exception e) {
                    Log.d("000862", "Syn ERr:    " + e.getMessage());
                }



                // Toast.makeText(ctx, response, Toast.LENGTH_SHORT).show();

                try {
                    // Toast.makeText(getApplicationContext(), "2", Toast.LENGTH_LONG).show();

                    JSONObject jobj = new JSONObject(response);
                    Log.d("000862", response);
                    // if(jobj.getBoolean("success")){
                    Log.d("testlinksync", response);

                    Lister ls = new Lister(ctx);
                    // ls.closeDB();
                    ls.createAndOpenDB();

                    Helper hp= new Helper(ctx);
                    hp.deleteUsers();
                    hp.deleteFacilities();

//                    boolean m17 = ls.executeNonQuery("DROP TABLE IF EXISTS VACCINES ");
//                    boolean m18 = ls.executeNonQuery("DROP TABLE IF EXISTS USERS ");


                    //////////////users

                    try {

                        JSONObject obj = new JSONObject(String.valueOf(response));
                        JSONArray m_jArry = obj.getJSONArray("users");
                        Log.d("000862", "onResponse: " + m_jArry.toString());
                        boolean m2 = ls.executeNonQuery(Helper.CREATE_TABLE_USERS);
                        String date = "";
                        for (int i = 0; i < m_jArry.length(); i++) {
                            JSONObject jo_inside = m_jArry.getJSONObject(i);

                            boolean mFlag = ls.executeNonQuery("insert or ignore into USERS(uid,username,password,privilege,salt,district_id,country_id,province_id, " +
                                    "uc_id) values " +
                                    "(" +
                                    "'" + jo_inside.getString("uid") + "'," +
                                    "'" + jo_inside.getString("username") + "'," +
                                    "'" + jo_inside.getString("password") + "'," +
                                    "'" + jo_inside.getInt("privilege") + "'," +
                                    "'" + jo_inside.getString("salt") + "'," +
                                    "'" + jo_inside.getString("district_id") + "'," +
                                    "'" + jo_inside.getString("country_id") + "'," +
                                    "'" + jo_inside.getString("province_id") + "'," +
                                    "'" + jo_inside.getString("uc_id") + "'" +
                                    ")");

                            Log.d("000862", "USER Insert Data:     " + mFlag);
                        }

                        Log.d("000862", "User Data Inserted Success !!!!");
                        // Toast.makeText(getApplicationContext(), "province ", Toast.LENGTH_SHORT).show();
                    } catch (Exception e) {
                        e.printStackTrace();
                        Log.d("000862", "Catch: " + e.getMessage());
                        Toast.makeText(getApplicationContext(), "No data found for " + "vacnines " + String.valueOf(e.getMessage()), Toast.LENGTH_SHORT).show();
                    }


                    try {

                        JSONObject obj = new JSONObject(String.valueOf(response));
                        JSONArray m_jArry = obj.getJSONArray("facilities");
                        Log.d("000862", "onResponse: " + m_jArry.toString());
                        boolean m2 = ls.executeNonQuery(Helper.CREATE_TABLE_FACILITIES);
                        String date = "";
                        for (int i = 0; i < m_jArry.length(); i++) {
                            JSONObject jo_inside = m_jArry.getJSONObject(i);

                            boolean mFlag = ls.executeNonQuery("insert or ignore into FACILITIES(uid,name,country_id,province_id,district_id,tehsil_id, " +
                                    "uc_id) values " +
                                    "(" +
                                    "'" + jo_inside.getString("uid") + "'," +
                                    "'" + jo_inside.getString("name") + "'," +
                                    "'" + jo_inside.getString("country_id") + "'," +
                                    "'" + jo_inside.getString("province_id") + "'," +
                                    "'" + jo_inside.getString("district_id") + "'," +
                                    "'" + jo_inside.getString("tehsil_id") + "'," +
                                    "'" + jo_inside.getString("uc_id") + "'" +
                                    ")");

                            Log.d("000862", "Facilities Insert Data:     " + mFlag);
                        }

                        Log.d("000862", "facilities Data Inserted Success !!!!");
                        // Toast.makeText(getApplicationContext(), "province ", Toast.LENGTH_SHORT).show();
                    } catch (Exception e) {
                        e.printStackTrace();
                        Log.d("000862", "Catch: " + e.getMessage());
                        Toast.makeText(getApplicationContext(), "No data found for " + "facilities " + String.valueOf(e.getMessage()), Toast.LENGTH_SHORT).show();
                    }

                } catch (Exception e) {
                    Log.d("000862", "Err:    " + e.getMessage());
                    Toast.makeText(Login_Activity.this, "Something wrong!!", Toast.LENGTH_SHORT).show();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Log.d("000862", "error    " + error.getMessage());
                //  Toast.makeText(Login_Activity.this, "برائے مہربانی انٹرنیٹ کنکشن چیک کریں", Toast.LENGTH_SHORT).show();


            }
        }) {
            @Override
            protected Map<String, String> getParams() {

                Map<String, String> params = new HashMap<String, String>();
                params.put("username", username);
                params.put("password", password);


                Log.d("000862", "mParam " + params);

                return params;
            }

        };

        AppController.getInstance().addToRequestQueue(strReq, REQUEST_TAG);
    }


    private static String convertToHex(byte[] data) {
        StringBuilder buf = new StringBuilder();
        for (byte b : data) {
            int halfbyte = (b >>> 4) & 0x0F;
            int two_halfs = 0;
            do {
                buf.append((0 <= halfbyte) && (halfbyte <= 9) ? (char) ('0' + halfbyte) : (char) ('a' + (halfbyte - 10)));
                halfbyte = b & 0x0F;
            } while (two_halfs++ < 1);
        }
        return buf.toString();
    }

    public void checkLoginCreden(String userName, String pass) {
        try {
            Lister ls = new Lister(getApplicationContext());
            ls.createAndOpenDB();
            String[][] mData = ls.executeReader("Select password, salt, uid, privilege from USERS where username = '" + userName + "' LIMIT 1");
            if (mData.length > 0) {
                String password = mData[0][0];
                // String checkpassword = password;
                String salt = mData[0][1];
                // user_facilities =  mData[0][2];
                String salted = pass + salt;
                MessageDigest md = null;
                try {
                    md = MessageDigest.getInstance("SHA-1");
                    byte[] textBytes = salted.getBytes("iso-8859-1");
                    md.update(textBytes, 0, textBytes.length);
                    byte[] sha1hash = md.digest();
                    String vartodis = convertToHex(sha1hash);
                    Log.d("000862", "USER-cresalted: " + vartodis);
                    Log.d("000862", "USER-credsalt: " + salt);
                    Log.d("000862", "USER-creddb:" + password);
                    Log.d("000862", "USER-UID:" + mData[0][2]);
                    Log.d("000862", "USER-privilege:" + mData[0][3]);
                    user_previlage = mData[0][3];
                    if (vartodis.trim().equalsIgnoreCase(password.trim())) {

                        login_useruid = mData[0][2];
                        login_username = userName;
                        ////////////Save user name in shared pref
                        SharedPreferences prefw = getApplicationContext().getSharedPreferences("UserLogin", 0); // 0 - for private mode
                        SharedPreferences.Editor editorw = prefw.edit();
                        editorw.putString("username", userName);
                        editorw.putString("login_userid", login_useruid);

                        username = userName;
                        //  editorw.putString("password", mData[0][2]);
                        editorw.apply();


                        final Snackbar snackbar = Snackbar.make(findViewById(R.id.login_layout), "User Login Successfully.", Snackbar.LENGTH_SHORT);
                        View mySbView = snackbar.getView();
                        mySbView.setLayoutDirection(View.LAYOUT_DIRECTION_LTR);
                        mySbView.setBackgroundColor(ctx.getResources().getColor(android.R.color.black));
                        TextView textView = mySbView.findViewById(android.support.design.R.id.snackbar_text);
                        textView.setTextColor(Color.WHITE);
                        textView.setTextSize(15);
                        textView.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_check_black_24dp, 0);
                        textView.setCompoundDrawablePadding(10);
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            textView.setCompoundDrawableTintList(ctx.getResources().getColorStateList(R.color.green_color));
                        }
                        snackbar.setDuration(2000);
                        snackbar.show();

                        Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                Intent intent1 = new Intent(ctx, MainActivity.class);
                                startActivity(intent1);

                            }
                        }, 1000);




                    } else {
                        Log.d("000862", "USER CREDENTIALS FAILED");
//                        Toast.makeText(getApplicationContext(), "صارف کا نام اور پاسورڈ صحیح نہیں", Toast.LENGTH_SHORT).show();
                        alertDialog.dismiss();
                        final Snackbar snackbar = Snackbar.make(findViewById(R.id.login_layout), "Please Enter Correct Username And Password.", Snackbar.LENGTH_SHORT);
                        snackbar.setDuration(3000);
                        snackbar.show();


                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    alertDialog.dismiss();
                    Log.d("000862", "FAILED" + e.getMessage());
                }
            } else {
                alertDialog.dismiss();
                Log.d("000862", "USER NOT FOUND");
            }
            Log.d("000862", "mData " + mData[0][0]);
            boolean mFlag = false;

  /*                      if (mData[0][1].equals(userName) && mData[0][2].equals(pass)) {


//                            startActivity(new Intent(getApplicationContext(), Dashboard.class));

                            mFlag = true;
                        } else {
                            Toast.makeText(getApplicationContext(), "Username or password is incorrect.else", Toast.LENGTH_SHORT).show();
                        }
*/

        } catch (Exception e) {
            alertDialog.dismiss();
            final Snackbar snackbar = Snackbar.make(findViewById(R.id.login_layout), "Please Check Username And Password.", Snackbar.LENGTH_SHORT);
            snackbar.setDuration(3000);
            snackbar.show();
            Log.d("000862", "Exception " + e.getMessage());
        }

    }

    @Override
    protected void onResume() {
        super.onResume();

        Log.d("000862", "ONResUME");

        SimpleDateFormat dates = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();
        TodayDate = dates.format(c.getTime());
        Log.d("000862", "Today Date : " + TodayDate);

        if (checkPermission()) {
            Log.d("000862", "All permission allowed now");

           /* serviceLocation = new ServiceLocation(ctx);
            serviceLocation.locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            serviceLocation.callAsynchronousTask();*/

            init_Directories();

        } else {
            requestPermission();
            Log.d("000862", "Request Permission");
        }


//        try {
//            SharedPreferences preferences = getApplicationContext().getSharedPreferences("MyPreferences", 0); // 0 - for private mode
//            SharedPreferences.Editor editor = preferences.edit();
//
//            String last_sync_time = preferences.getString("last_sync_time", "");
//            txt_last_sync_datettime.setText(last_sync_time);
//            Log.d("000862", "Last_sync_time : " + last_sync_time);
//
//        } catch (Exception e) {
//            Log.d("000862", "ERRRROR : " + e.getMessage());
//        }

    }


    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onBackPressed() {
        Log.d("000862", "onBackPressed Called");
        finishAffinity();
    }

    public boolean checkPermission() {


        //  int FirstPermissionResult = ContextCompat.checkSelfPermission(getApplicationContext(), SEND_SMS);
        int SecondPermissionResult = ContextCompat.checkSelfPermission(getApplicationContext(), READ_EXTERNAL_STORAGE);
        //  int ThirdPermissionResult = ContextCompat.checkSelfPermission(getApplicationContext(), RECEIVE_SMS);
          int ForthPermissionResult = ContextCompat.checkSelfPermission(getApplicationContext(), CAMERA);
        int FifthPermissionResult = ContextCompat.checkSelfPermission(getApplicationContext(), WRITE_EXTERNAL_STORAGE);

        return
                SecondPermissionResult == PackageManager.PERMISSION_GRANTED &&
                        // ThirdPermissionResult == PackageManager.PERMISSION_GRANTED &&
                        ForthPermissionResult == PackageManager.PERMISSION_GRANTED &&
                        FifthPermissionResult == PackageManager.PERMISSION_GRANTED &&
                        SecondPermissionResult == PackageManager.PERMISSION_GRANTED;
        //EigthPermissionResult == PackageManager.PERMISSION_GRANTED;

    }

    private void requestPermission() {


        ActivityCompat.requestPermissions(Login_Activity.this, new String[]
                {
                        // SEND_SMS,READ_PHONE_STATE,WRITE_SYNC_SETTINGS
                        WRITE_EXTERNAL_STORAGE, READ_EXTERNAL_STORAGE, CAMERA
                }, RequestPermissionCodeLogin);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {

            case RequestPermissionCodeLogin:

                if (grantResults.length > 0) {

                    boolean WriteEXtPermission = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    boolean ReadExtPermission = grantResults[1] == PackageManager.PERMISSION_GRANTED;
                      boolean CAMERA = grantResults[1] == PackageManager.PERMISSION_GRANTED;
                    // boolean ReadPhoneStatePermission = grantResults[2] == PackageManager.PERMISSION_GRANTED;
                    //  boolean WritesyncPermission = grantResults[3] == PackageManager.PERMISSION_GRANTED;
                    //  boolean CallPhoneStatePermission = grantResults[5] == PackageManager.PERMISSION_GRANTED;
                    // boolean SendSMSStatePermission = grantResults[7] == PackageManager.PERMISSION_GRANTED;

                    if (WriteEXtPermission && ReadExtPermission && CAMERA) {

                        Log.d("000862", "All Permission Allowed");

                        /*serviceLocation = new ServiceLocation(ctx);
                        serviceLocation.locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
                        serviceLocation.callAsynchronousTask();*/

                        init_Directories();


                    } else {
                        Log.d("000862", "All Permission Not Allowed");

                        if (!WriteEXtPermission && !ReadExtPermission && !CAMERA) {
                            Toast.makeText(ctx, "فون سٹوریج کی پرمیشن کو اجازت دیں", Toast.LENGTH_SHORT).show();
                        } else {
                            init_Directories();
                        }
                    }
                }

                break;
        }
    }


    public static int haveNetworkConnection(Context ctx) {

        int status = 0;

        ConnectivityManager cm = (ConnectivityManager) ctx.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo[] netInfo = cm.getAllNetworkInfo();
        for (NetworkInfo ni : netInfo) {
            if (ni.getTypeName().equalsIgnoreCase("WIFI"))
                if (ni.isConnected())
                    status = 1;
            //   Log.d("000333","WIFI");
            if (ni.getTypeName().equalsIgnoreCase("MOBILE"))
                if (ni.isConnected())
                    status = 2;
            //  Log.d("000333","Mobie");
        }

        return status;
    }


}

