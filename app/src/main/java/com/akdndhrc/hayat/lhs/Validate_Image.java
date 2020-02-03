package com.akdndhrc.hayat.lhs;

import android.app.Dialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import uk.co.senab.photoview.PhotoViewAttacher;

import static android.graphics.BitmapFactory.decodeFile;

public class Validate_Image extends AppCompatActivity {

    Context ctx = Validate_Image.this;
    private static final String TAG = "000555";
    public static final int MEDIA_TYPE_IMAGE = 1;
    ImageView iv, iv_navigation_drawer, iv_home;
    Button jamma_karayn;
    JSONObject jsonObject;
    Bitmap bitmap = null;
    double latitude;
    double longitude;
    // GPSTracker class
    String activity_id, member_id, full_name, month, added_on, type,added_by,year;
    String TodayDate, vacine_uid, vacine_name, vacine_place, file_value, vac_duedate, timeStamp, vac_place_pos;
    ContentValues values;
    Uri imageUri = Uri.parse(Environment.getExternalStorageDirectory().toString() + "/HayatLHS/" + "Vaccines/");
    private static final int PICTURE_RESULT = 100;
    Bitmap thumbnail;

    static final int REQUEST_IMAGE_CAPTURE = 1;
    private Bitmap mImageBitmap;
    private String mCurrentPhotoPath;
    File image_path;
    Dialog alertDialog;
    private RequestQueue rQueue;
    PhotoViewAttacher photoViewAttacher;
    Snackbar snackbar;
    String login_useruid;
   // private FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_validate__image);

        activity_id = getIntent().getExtras().getString("activity_id");
        member_id = getIntent().getExtras().getString("member_id");
        type = getIntent().getExtras().getString("type");
        month = getIntent().getExtras().getString("activity_month");
        year = getIntent().getExtras().getString("activity_year");
        full_name = getIntent().getExtras().getString("full_name");

        SimpleDateFormat dates = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();
        TodayDate = dates.format(c.getTime());

        //ImageView
        iv = findViewById(R.id.iv);
        iv_navigation_drawer = findViewById(R.id.iv_navigation_drawer);
        iv_home = findViewById(R.id.iv_home);

        iv_navigation_drawer.setVisibility(View.GONE);
        iv_home.setVisibility(View.GONE);


        //GPS\
       /* gps = new GPSTracker(ctx);

        // check if GPS enabled
        if (gps.canGetLocation()) {

            latitude = gps.getLatitude();
            longitude = gps.getLongitude();
            Log.d("000555", "latitude value: " + latitude);
            Log.d("000555", "longitude value: " + longitude);
        } else {
            gps.showSettingsAlert();
            Toast.makeText(ctx, "برائے مہربانی جی پی ایس پوزیشن کو آن کریں", Toast.LENGTH_LONG).show();
            return;
        }*/


        init_Directories();


        //Button
        jamma_karayn = (Button) findViewById(R.id.btn_jamaa_kre);


        iv_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent newIntent = new Intent(ctx, MainActivity.class);
                newIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                newIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(newIntent);
            }
        });

        LaunchCamera();


        jamma_karayn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                alertDialog = new Dialog(ctx);
                LayoutInflater layout = LayoutInflater.from(ctx);
                final View dialogView = layout.inflate(R.layout.lay_dialog_loading, null);

                alertDialog.setContentView(dialogView);
                alertDialog.setCanceledOnTouchOutside(false);
                alertDialog.setCancelable(false);
                alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                alertDialog.show();


                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        insert_db();
                     //   addDataFirestore();
                        uploadImage(bitmap);

                    }
                }, 2000);


            }
        });
    }


    private void init_Directories() {

        try {

            String folder_Vaccines = Environment.getExternalStorageDirectory() + File.separator + "Hayat_LHS" + File.separator + "Vaccines";
            File directory = new File(folder_Vaccines);


            boolean success = true;
            if (!directory.exists()) {
                directory.mkdirs();
            }

            if (success) {
                // Toast.makeText(ctx, "Suceess", Toast.LENGTH_SHORT).show();
            } else {
                //Toast.makeText(ctx, "Failed", Toast.LENGTH_SHORT).show();
            }


        } catch (Exception e) {
            // Toast.makeText(this, "Faild to Create Folder", Toast.LENGTH_SHORT).show();
            Log.d("Folder", "Failed" + e.getMessage());
        }
    }

    public void LaunchCamera() {
        try {

            if (Build.VERSION.SDK_INT >= 24) {
                try {
                    Method m = StrictMode.class.getMethod("disableDeathOnFileUriExposure");
                    m.invoke(null);
                } catch (Exception e) {
                    e.printStackTrace();
                    Log.d("000555", "Error:" + e.getMessage());
                }
            }


       /* Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(cameraIntent, PICTURE_RESULT);*/


           /* String timeStamp = new SimpleDateFormat("dd-MM-yyyy_hh:mm:ss aa", Locale.getDefault()).format(new Date());
            File cachePath = new File(Environment.getExternalStorageDirectory() + File.separator + "HayatPK" + File.separator + "Vaccines" + File.separator + "IMG_" + timeStamp + ".png");
            file_value = String.valueOf(cachePath);
            Log.d("000555", "Path:" + file_value);*/

            File cachePath = createImageFile();
            cachePath.createNewFile();
            imageUri = Uri.fromFile(cachePath);
            Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            i.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
            startActivityForResult(i, PICTURE_RESULT);

            Log.d("000555", "try:");

        } catch (Exception e) {
            Log.d("000555", "Cam Err:" + e.getMessage());
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        Log.d("000555", "requestCode:" + requestCode);
        Log.d("000555", "resultCode:" + resultCode);
        if (requestCode == PICTURE_RESULT) {
            if (resultCode == RESULT_OK) {
                Log.d("000555", "A:");
                try {
                    mImageBitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri);
                    mImageBitmap = scaleDown(mImageBitmap, 640, true);
                    iv.setTag(true);
                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    mImageBitmap.compress(Bitmap.CompressFormat.JPEG, 70, stream);
                    byte[] byteArray = stream.toByteArray();
                    Log.d("000555", "B:");
                    FileOutputStream fo = new FileOutputStream(new File(mCurrentPhotoPath));

                    Log.d("000555", "C:");

                    fo.write(byteArray);
                    fo.flush();
                    fo.close();
                    mImageBitmap.recycle();

                    iv.setImageBitmap(decodeFile(String.valueOf(mCurrentPhotoPath)));
                    photoViewAttacher = new PhotoViewAttacher(iv);

                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                    Log.d("000555", "Img_Er11r:" + e.getMessage());
                    Toast.makeText(ctx, "Something wrong!!", Toast.LENGTH_SHORT).show();
                    File file = new File(String.valueOf(image_path));
                    Log.d("000555", "Delete Path :" + image_path);
                    file.delete();
                    Intent newIntent = new Intent(ctx, Monthly_Report.class);
                    newIntent.putExtra("activity_id", activity_id);
                    startActivity(newIntent);
                } catch (IOException e) {
                    e.printStackTrace();
                    Toast.makeText(ctx, "Something wrong!!", Toast.LENGTH_SHORT).show();
                    Log.d("000555", "Img_Err:" + e.getMessage());
                    File file = new File(String.valueOf(image_path));
                    Log.d("000555", "Delete Path :" + image_path);
                    file.delete();
                    Intent newIntent = new Intent(ctx, Monthly_Report.class);
                    newIntent.putExtra("activity_id", activity_id);
                    startActivity(newIntent);
                }


            } else if (resultCode == RESULT_CANCELED) {
                // User cancelled the image capture
                Log.d("000555", "ELSE IF:");
                File file = new File(String.valueOf(image_path));
                Log.d("000555", "Delete Path :" + image_path);
                file.delete();
                Intent newIntent = new Intent(ctx, Monthly_Report.class);
                newIntent.putExtra("activity_id", activity_id);
                startActivity(newIntent);
            } else {
                // Image capture failed, advise user
                Toast.makeText(ctx, "Something wrong!!", Toast.LENGTH_SHORT).show();
                Log.d("000555", "ELSE:");
                File file = new File(String.valueOf(image_path));
                Log.d("000555", "Delete Path :" + image_path);
                file.delete();
                Intent newIntent = new Intent(ctx, Monthly_Report.class);
                newIntent.putExtra("activity_id", activity_id);
                startActivity(newIntent);
            }
        }

//Working  code

       /* if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {

            try {

                File photoFile = null;
                photoFile = createImageFile();

                thumbnail = MediaStore.Images.Media.getBitmap(getContentResolver(), imageUri);
                thumbnail= scaleDown(thumbnail, 640, true);

                photoFile.createNewFile();
                Log.d("000555", "ssss:");

                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                thumbnail.compress(Bitmap.CompressFormat.JPEG, 60, stream);
                byte[] byteArray = stream.toByteArray();

                Log.d("000555", "File_Path:" + image_path);

                // Log.d("000555", "mCurrentPhotoPath:" + mCurrentPhotoPath);
                FileOutputStream fo = new FileOutputStream(image_path);
                fo.write(byteArray);
                fo.flush();
                fo.close();
                thumbnail.recycle();

                Log.d("000555", "Created Image:");
                iv.setImageBitmap(decodeFile(String.valueOf(image_path)));
                photoViewAttacher = new PhotoViewAttacher(iv);

            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(ctx, "Image not saved", Toast.LENGTH_SHORT).show();
                Log.d("000555", "Img_Err:" + e.getMessage());
            }

            /*finally {
                finish();
            }*/

      /*  } else {
            Log.d("000555", "Else");
            finish();
        }*/


    }


    public static Bitmap scaleDown(Bitmap realImage, float maxImageSize, boolean filter) {
        float ratio = Math.min(
                (float) maxImageSize / realImage.getWidth(),
                (float) maxImageSize / realImage.getHeight());
        int width = Math.round((float) ratio * realImage.getWidth());
        int height = Math.round((float) ratio * realImage.getHeight());

        Bitmap newBitmap = Bitmap.createScaledBitmap(realImage, width,
                height, filter);
        return newBitmap;
    }


    private File createImageFile() throws IOException {
        // Create an image file name
        String uuid = UUID.randomUUID().toString().replace("-", "");
        Log.d("000555", "UUID:" + uuid);

        SimpleDateFormat dates = new SimpleDateFormat("ddMMyyyy_hhmmss");
        Calendar c = Calendar.getInstance();
        timeStamp = dates.format(c.getTime());
        Log.d("000555", "timestamp:" + timeStamp);


        //String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").toString();
        String imageFileName = "IMG_" + timeStamp + "_" + uuid;
        String folder_Vaccines = Environment.getExternalStorageDirectory() + File.separator + "Hayat_LHS" + File.separator + "Vaccines";
        File storageDir = new File(folder_Vaccines);
        image_path = File.createTempFile(
                imageFileName,  // prefix
                ".jpg",         // suffix
                storageDir      // directory
        );
        mCurrentPhotoPath = image_path.getPath();
        Log.d("000555", "FilePath: " + image_path.getPath());


        // Save a file: path for use with ACTION_VIEW intents
        //imageUri = Uri.fromFile(image_path);
        Log.d("000555", "ImageURI (create file): " + mCurrentPhotoPath);
        return image_path;
    }

    public void insert_db() {
        try {

            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inPreferredConfig = Bitmap.Config.ARGB_8888;

            bitmap = BitmapFactory.decodeStream(new FileInputStream(image_path), null, options);

            Log.d(TAG, "createImageFile: " + bitmap);

            Lister ls = new Lister(ctx);
            ls.createAndOpenDB();

            // et_refferal_ki_waja = findViewById(R.id.et_refferal_ki_waja);
            // et_refferal_hospital = findViewById(R.id.et_refferal_hospital);

            //Edittext

            SimpleDateFormat dates = new SimpleDateFormat("dd-MM-yyyy_hh:mm:ss aa");
            Calendar c = Calendar.getInstance();
            String current_timeStamp = dates.format(c.getTime());
            Log.d("000555", "timestamp:" + current_timeStamp);


            added_on = String.valueOf(System.currentTimeMillis());

            jsonObject=new JSONObject();
            jsonObject.put("added_by",MainActivity.login_useruid);
            jsonObject.put("added_on",added_on);

            String ans1 = "insert into validation (activity_id, member_id,full_name,month, year, is_validated,added_by,type,is_synced, image_location)" +
                    "values" +
                    "(" +
                    "'" + activity_id + "'," +
                    "'" + member_id + "'," +
                    "'" + full_name + "'," +
                    "'" + month + "'," +
                    "'" + year + "'," +
                    "'" + "1" + "'," +
                    "'" + MainActivity.login_useruid + "'," +
                    "'" + type + "'," +
                    "'" + "0" + "'," +
                    "'" + image_path + "'" +
                    ")";

            Boolean res = ls.executeNonQuery(ans1);
            Log.d("000555", "Data: " + ans1);
            Log.d("000555", "Query: " + res);

            String update_record = "UPDATE Activities SET " +
                    "is_synced_validation ='" + String.valueOf(0) + "' " +
                    "WHERE activity_id = '" + activity_id + "' ";
            ls.executeNonQuery(update_record);


            //  Toast.makeText(getApplicationContext(),String.valueOf(res)+String.valueOf(ans1),Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            Log.d("000555", " Error: " + e.getMessage());
            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
        } finally {
            Log.d("000555", " BAC: ");
//            alertDialog.dismiss();
            Intent intent = new Intent(ctx, Monthly_Report.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.putExtra("activity_id", activity_id);
            intent.putExtra("activity_month", month);
            startActivity(intent);
        }

    }

/*
    private void addDataFirestore() {
        Map<String, Object> note = new HashMap<>();
        note.put("activity_id", activity_id);
        note.put("member_id", member_id);
        note.put("vaccine_id", vaccine_id);
        note.put("image_path", image_path.toString());
        note.put("is_sync", "0");
        note.put("is_validated", "1");
        note.put("added_on", added_on);

        db.collection("Validation").document().update(note).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });

        db.collection("Validation").add(note).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {
                Log.d("000666", "onSuccess: " + documentReference.getId());
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(ctx, "Error!", Toast.LENGTH_SHORT).show();
                Log.d("000666", e.toString());
            }
        });

        db.collection("Validation").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(QuerySnapshot queryDocumentSnapshots, FirebaseFirestoreException e) {

                if (e != null) {
                    Log.w("000666", "Listen error", e);
                    return;
                }

                for (DocumentChange change : queryDocumentSnapshots.getDocumentChanges()) {
                    if (change.getType() == DocumentChange.Type.ADDED) {
                        Log.d("000666", "New Data:" + change.getDocument().getData());
                    }

                    String source = queryDocumentSnapshots.getMetadata().isFromCache() ?
                            "local cache" : "server";
                    Log.d("000666", "Data fetched from " + source);
                }

            }

        });
    }
*/


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Log.d("000555", "BACK ");
        File file = new File(String.valueOf(image_path));
        Log.d("000555", "Delete Path :" + image_path);
        file.delete();
        Intent newIntent = new Intent(ctx, Monthly_Report.class);
        newIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        newIntent.putExtra("activity_id", activity_id);
        newIntent.putExtra("activity_month", month);
        startActivity(newIntent);

    }


    /*    public void LaunchCamera() {
        try {

            if (Build.VERSION.SDK_INT >= 24) {
                try {
                    Method m = StrictMode.class.getMethod("disableDeathOnFileUriExposure");
                    m.invoke(null);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            if (cameraIntent.resolveActivity(getPackageManager()) != null) {
                // Create the File where the photo should go
                File photoFile = null;
                try {
                    photoFile = createImageFile();
                } catch (IOException ex) {
                    // Error occurred while creating the File
                    Log.d("000555", "IOException");
                }
                // Continue only if the File was successfully created
                if (photoFile != null) {

                    cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(photoFile));
                    startActivityForResult(cameraIntent, REQUEST_IMAGE_CAPTURE);

                }

            }
        }
             catch (Exception e) {
            Log.d("000555", "Cam Err:" + e.getMessage());
        }
    }*/


   /* protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == PICTURE_RESULT && resultCode == Activity.RESULT_OK) {

            try {

                String timeStamp = new SimpleDateFormat("dd-MM-yyyy_hh:mm:ss aa", Locale.getDefault()).format(new Date());
                File cachePath = new File(Environment.getExternalStorageDirectory() + File.separator + "HayatPK" + File.separator + "Vaccines" + File.separator + "IMG_" + timeStamp + ".png");
                file_value = String.valueOf(cachePath);

                thumbnail = MediaStore.Images.Media.getBitmap(getContentResolver(), imageUri);
//               iv.setImageBitmap(thumbnail);

                //Another Way to show Image
                //  mImageBitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), Uri.parse(mCurrentPhotoPath));
                //iv.setImageBitmap(mImageBitmap);


                Log.d("000555", "LOC: " + file_value);

                cachePath.createNewFile();

                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                thumbnail.compress(Bitmap.CompressFormat.PNG, 100, stream);
                byte[] byteArray = stream.toByteArray();

                FileOutputStream fo = new FileOutputStream(cachePath);
                fo.write(byteArray);
                fo.flush();
                fo.close();
                thumbnail.recycle();

                //  insert_db();

                //  uploadToServer(file_value);

            } catch (Exception e) {
                e.printStackTrace();
                Log.d("000555", "ERROR: " + e.getMessage());
            }


        } else {
            Log.d("000555", "CANACEL 3");
        }

    }*/



    private void uploadImage(final Bitmap bitmap) {

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
                                        "WHERE activity_id = '" + activity_id + "' ";
                                ls.executeNonQuery(update_record);

                                String update_record_val = "UPDATE Activities SET " +
                                        "is_synced_validation ='" + String.valueOf(1) + "' " +
                                        "WHERE activity_id = '" + activity_id + "' ";
                                ls.executeNonQuery(update_record_val);


                                String[][] validate_image_Data = ls.executeReader("Select is_synced from validation where activity_id = '" + activity_id + "' ");

                                for(int k =0; k< validate_image_Data.length; k++){
                                    Log.d("000555", "validate image: "+validate_image_Data[k][0]);

                                    if(validate_image_Data[k][0].equals("0")){
                                        String update_record_val1 = "UPDATE Activities SET " +
                                                "is_synced_validation='" + String.valueOf(0) + "' " +
                                                "WHERE activity_id = '" + activity_id+ "' ";
                                        ls.executeNonQuery(update_record_val1);
                                    }

                                }

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
                params.put("activity_id", activity_id);
                params.put("member_id", member_id);
                params.put("full_name", full_name);
                params.put("type", type);
                params.put("for_year", year);
                params.put("for_month", month);
                params.put("validated", "1");
                params.put("metadata", jsonObject.toString());
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
        rQueue = Volley.newRequestQueue(Validate_Image.this);
        rQueue.add(volleyMultipartRequest);
    }


    public byte[] getFileDataFromDrawable(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 80, byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }

}
