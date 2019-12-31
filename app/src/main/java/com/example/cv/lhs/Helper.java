package com.example.cv.lhs;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Environment;

import java.io.File;

public class Helper extends SQLiteOpenHelper {
    private static final String TAG = "DatabaseHelper";
    public static final String DATABASE_NAME = "HayatLHSDB";
    public static final int DATABASE_VERSION = 1;
    public static File Database_Path = null;

    public static final String TABLE_NAME_0 = "Activities";
    private static final String activity_date = "activity_date";
    private static final String activity_id = "activity_id";
    private static final String activity_health = "health";
    private static final String activity_health_centre = "health_centre";
    private static final String activity_health_worker = "health_worker";
    private static final String activity_facility = "facility";
    private static final String total_count = "total_count";
    private static final String activity_month = "activity_month";
    private static final String activity_year = "activity_year";
    private static final String activity_Status = "complete_status";

    public static final String TABLE_NAME = "Basic_Informatiom";

    public static final String TABLE_NAME_2 = "Question1";
    private static final String QUESTION1_Q1 = "question1_q1";
    private static final String QUESTION1_Q2 = "question1_q2";
    private static final String QUESTION1_Q3 = "question1_q3";
    private static final String QUESTION1_Q4 = "question1_q4";
    private static final String QUESTION1_Q5 = "question1_q5";
    private static final String QUESTION1_Q6 = "question1_q6";
    private static final String QUESTION1_COUNT = "question1_count";
    private static final String QUESTION1_NO_COUNT = "question1_no_count";
    private static final String QUESTION1_NA_COUNT = "question1_na_count";

    public static final String TABLE_NAME_3 = "Question2";
    private static final String QUESTION2_Q1 = "question2_q1";
    private static final String QUESTION2_Q2 = "question2_q2";
    private static final String QUESTION2_Q3 = "question2_q3";
    private static final String QUESTION2_Q4 = "question2_q4";
    private static final String QUESTION2_Q5 = "question2_q5";
    private static final String QUESTION2_COUNT = "question2_count";
    private static final String QUESTION2_NO_COUNT = "question2_no_count";
    private static final String QUESTION2_NA_COUNT = "question2_na_count";

    public static final String TABLE_NAME_4 = "Question3";
    private static final String QUESTION3_Q1 = "question3_q1";
    private static final String QUESTION3_Q2 = "question3_q2";
    private static final String QUESTION3_Q3 = "question3_q3";
    private static final String QUESTION3_Q4 = "question3_q4";
    private static final String QUESTION3_Q5 = "question3_q5";
    private static final String QUESTION3_Q6 = "question3_q6";
    private static final String QUESTION3_Q7 = "question3_q7";
    private static final String QUESTION3_Q8 = "question3_q8";
    private static final String QUESTION3_Q9 = "question3_q9";
    private static final String QUESTION3_Q10 = "question3_q10";
    private static final String QUESTION3_COUNT = "question3_count";
    private static final String QUESTION3_NO_COUNT = "question3_no_count";
    private static final String QUESTION3_NA_COUNT = "question3_na_count";

    public static final String TABLE_NAME_5 = "Question4";
    private static final String QUESTION4_Q1 = "question4_q1";
    private static final String QUESTION4_Q2 = "question4_q2";
    private static final String QUESTION4_Q3 = "question4_q3";
    private static final String QUESTION4_Q4 = "question4_q4";
    private static final String QUESTION4_Q5 = "question4_q5";
    private static final String QUESTION4_Q6 = "question4_q5";
    private static final String QUESTION4_COUNT = "question4_count";
    private static final String QUESTION4_NO_COUNT = "question4_no_count";
    private static final String QUESTION4_NA_COUNT = "question4_na_count";

    public static final String TABLE_NAME_6 = "Question5";
    private static final String QUESTION5_Q1 = "question5_q1";
    private static final String QUESTION5_Q2 = "question5_q2";
    private static final String QUESTION5_Q3 = "question5_q3";
    private static final String QUESTION5_Q4 = "question5_q4";
    private static final String QUESTION5_Q5 = "question5_q5";
    private static final String QUESTION5_COUNT = "question5_count";
    private static final String QUESTION5_NO_COUNT = "question5_no_count";
    private static final String QUESTION5_NA_COUNT = "question5_na_count";

    public static final String TABLE_NAME_7 = "Question6";
    private static final String QUESTION6_Q1 = "question6_q1";
    private static final String QUESTION6_Q2 = "question6_q2";
    private static final String QUESTION6_Q3 = "question6_q3";
    private static final String QUESTION6_Q4 = "question6_q4";
    private static final String QUESTION6_Q5 = "question6_q5";
    private static final String QUESTION6_Q6 = "question6_q6";
    private static final String QUESTION6_Q7 = "question6_q7";
    private static final String QUESTION6_COUNT = "question6_count";
    private static final String QUESTION6_NO_COUNT = "question6_no_count";
    private static final String QUESTION6_NA_COUNT = "question6_na_count";

    public static final String TABLE_NAME_8 = "Question7";
    private static final String QUESTION7_Q1 = "question7_q1";
    private static final String QUESTION7_Q2 = "question7_q2";
    private static final String QUESTION7_Q3 = "question7_q3";
    private static final String QUESTION7_Q4 = "question7_q4";
    private static final String QUESTION7_Q5 = "question7_q5";
    private static final String QUESTION7_Q6 = "question7_q6";
    private static final String QUESTION7_COUNT = "question7_count";
    private static final String QUESTION7_NO_COUNT = "question7_no_count";
    private static final String QUESTION7_NA_COUNT = "question7_na_count";

    public static final String TABLE_NAME_9 = "Question8";
    private static final String QUESTION8_Q1 = "question8_q1";
    private static final String QUESTION8_Q2 = "question8_q2";
    private static final String QUESTION8_Q3 = "question8_q3";
    private static final String QUESTION8_Q4 = "question8_q4";
    private static final String QUESTION8_Q5 = "question8_q5";
    private static final String QUESTION8_COUNT = "question8_count";
    private static final String QUESTION8_NO_COUNT = "question8_no_count";
    private static final String QUESTION8_NA_COUNT = "question8_na_count";

    public static final String TABLE_NAME_10 = "Question9";
    private static final String QUESTION9_Q1 = "question9_q1";
    private static final String QUESTION9_Q2 = "question9_q2";
    private static final String QUESTION9_Q3 = "question9_q3";
    private static final String QUESTION9_Q4 = "question9_q4";
    private static final String QUESTION9_COUNT = "question9_count";
    private static final String QUESTION9_NO_COUNT = "question9_no_count";
    private static final String QUESTION9_NA_COUNT = "question9_na_count";

    public static final String TABLE_NAME_11 = "Question10";
    private static final String QUESTION10_Q1 = "question10_q1";
    private static final String QUESTION10_Q2 = "question10_q2";
    private static final String QUESTION10_Q3 = "question10_q3";
    private static final String QUESTION10_Q4 = "question10_q4";
    private static final String QUESTION10_Q5 = "question10_q5";
    private static final String QUESTION10_COUNT = "question10_count";
    private static final String QUESTION10_NO_COUNT = "question10_no_count";
    private static final String QUESTION10_NA_COUNT = "question10_na_count";

    public static final String TABLE_NAME_12 = "Question11";
    private static final String QUESTION11_Q1 = "question11_q1";
    private static final String QUESTION11_Q2 = "question11_q2";
    private static final String QUESTION11_Q3 = "question11_q3";
    private static final String QUESTION11_COUNT = "question11_count";
    private static final String QUESTION11_NO_COUNT = "question11_no_count";
    private static final String QUESTION11_NA_COUNT = "question11_na_count";

    public static final String TABLE_NAME_13 = "Question12";
    private static final String QUESTION12_Q1 = "question12_q1";
    private static final String QUESTION12_Q2 = "question12_q2";
    private static final String QUESTION12_Q3 = "question12_q3";
    private static final String QUESTION12_Q4 = "question12_q4";
    private static final String QUESTION12_Q5 = "question12_q5";
    private static final String QUESTION12_COUNT = "question12_count";
    private static final String QUESTION12_NO_COUNT = "question12_no_count";
    private static final String QUESTION12_NA_COUNT = "question12_na_count";

    public static final String TABLE_NAME_14 = "Question13";
    private static final String QUESTION13_Q1 = "question13_q1";
    private static final String QUESTION13_Q2 = "question13_q2";
    private static final String QUESTION13_Q3 = "question13_q3";
    private static final String QUESTION13_Q4 = "question13_q4";
    private static final String QUESTION13_Q5 = "question13_q5";
    private static final String QUESTION13_COUNT = "question13_count";
    private static final String QUESTION13_NO_COUNT = "question13_no_count";
    private static final String QUESTION13_NA_COUNT = "question13_na_count";

    public static final String TABLE_NAME_15 = "Question14";
    private static final String QUESTION14_Q1 = "question14_q1";
    private static final String QUESTION14_Q2 = "question14_q2";
    private static final String QUESTION14_COUNT = "question14_count";
    private static final String QUESTION14_NO_COUNT = "question14_no_count";
    private static final String QUESTION14_NA_COUNT = "question14_na_count";

    public static final String TABLE_NAME_16 = "Question15";
    private static final String QUESTION15_Q1 = "question15_q1";
    private static final String QUESTION15_Q2 = "question15_q2";
    private static final String QUESTION15_Q3 = "question15_q3";
    private static final String QUESTION15_COUNT = "question15_count";
    private static final String QUESTION15_NO_COUNT = "question15_no_count";
    private static final String QUESTION15_NA_COUNT = "question15_na_count";

    public static final String TABLE_NAME_17 = "Question16";
    private static final String QUESTION16_Q1 = "question16_q1";
    private static final String QUESTION16_Q2 = "question16_q2";
    private static final String QUESTION16_Q3 = "question16_q3";
    private static final String QUESTION16_Q4 = "question16_q4";
    private static final String QUESTION16_Q5 = "question16_q5";
    private static final String QUESTION16_Q6 = "question16_q6";
    private static final String QUESTION16_COUNT = "question16_count";
    private static final String QUESTION16_NO_COUNT = "question16_no_count";
    private static final String QUESTION16_NA_COUNT = "question16_na_count";

    public static final String TABLE_NAME_18 = "Question17";
    private static final String QUESTION17_Q1 = "question17_q1";
    private static final String QUESTION17_Q2 = "question17_q2";
    private static final String QUESTION17_Q3 = "question17_q3";
    private static final String QUESTION17_COUNT = "question17_count";
    private static final String QUESTION17_NO_COUNT = "question17_no_count";
    private static final String QUESTION17_NA_COUNT = "question17_na_count";

    public static String CREATE_TABLE_KHANDAN;
    public static String CREATE_TABLE_USERS;
    public static String CREATE_TABLE_KMEMBER;
    public static String CREATE_TABLE_CHILD_VACINATION;
    public static String CREATE_TABLE_VACCINES;
    public static String CREATE_TABLE_MONTHLYREPORT;
    public static String CREATE_TABLE_LOW_BIRTH;
    public static String CREATE_TABLE_LIVE_BIRTH;
    public static String CREATE_TABLE_NEW_PREG;
    public static String CREATE_TABLE_TOTAL_PREG;
    public static String CREATE_TABLE_VALIDATION;
    public static String CREATE_TABLE_FACILITIES;

 /*  public Helper(Context context) {
        super(context, DATABASE_NAME, null, 1);
        Database_Path = context.getDatabasePath(Helper.DATABASE_NAME);
    }*/


    public Helper(final Context context) {
        super(context, Environment.getExternalStorageDirectory()
                + File.separator + "Hayat_LHS"
                + File.separator + DATABASE_NAME, null, DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {

        String createTable0 = "CREATE TABLE " + TABLE_NAME_0 + "(" +
                "activity_id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
                "activity_date date(10) NOT NULL, " +
                "health VARCHAR(100) NULL, " +
                "health_centre VARCHAR(20) NULL, " +
                "health_worker VARCHAR(30) NULL, " +
                "facility VARCHAR(30) NULL, " +
                "total_count VARCHAR(30) NULL, " +
                "activity_month varchar(3) NOT NULL, " +
                "activity_year varchar(10) NOT NULL, " +
                "complete_status INTEGER(2) NULL " +
                " )";
        db.execSQL(createTable0);


        String createTable2 = "CREATE TABLE " + TABLE_NAME_2 + "(" +
                //"ID INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
                activity_id + " VARCHAR(20), " +
                "question1_q1 VARCHAR(10) NULL, " +
                "question1_q2 VARCHAR(10) NULL, " +
                "question1_q3 VARCHAR(10) NULL, " +
                "question1_q4 VARCHAR(10) NULL, " +
                "question1_q5 VARCHAR(10) NULL, " +
                "question1_q6 VARCHAR(10) NULL, " +
                "question1_count INT(10) NULL, " +
                "question1_no_count INT(10) NULL, " +
                "question1_na_count INT(10) NULL, " +
                " FOREIGN KEY (" + activity_id + ") REFERENCES  " + TABLE_NAME_0 + "(" + activity_id + "))";

        db.execSQL(createTable2);

        String createTable3 = "CREATE TABLE " + TABLE_NAME_3 + "(" +
                //"ID INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
                activity_id + " VARCHAR(20), " +
                "question2_q1 VARCHAR(10) NULL, " +
                "question2_q2 VARCHAR(10) NULL, " +
                "question2_q3 VARCHAR(10) NULL, " +
                "question2_q4 VARCHAR(10) NULL, " +
                "question2_q5 VARCHAR(10) NULL, " +
                "question2_count INT(10) NULL, " +
                "question2_no_count INT(10) NULL, " +
                "question2_na_count INT(10) NULL, " +
                " FOREIGN KEY (" + activity_id + ") REFERENCES  " + TABLE_NAME_0 + "(" + activity_id + "))";

        db.execSQL(createTable3);

        String createTable4 = "CREATE TABLE " + TABLE_NAME_4 + "(" +
                //"ID INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
                activity_id + " VARCHAR(20), " +
                "question3_q1 VARCHAR(10) NULL, " +
                "question3_q2 VARCHAR(10) NULL, " +
                "question3_q3 VARCHAR(10) NULL, " +
                "question3_q4 VARCHAR(10) NULL, " +
                "question3_q5 VARCHAR(10) NULL, " +
                "question3_q6 VARCHAR(10) NULL, " +
                "question3_q7 VARCHAR(10) NULL, " +
                "question3_q8 VARCHAR(10) NULL, " +
                "question3_q9 VARCHAR(10) NULL, " +
                "question3_q10 VARCHAR(10) NULL, " +
                "question3_count INT(10) NULL, " +
                "question3_no_count INT(10) NULL, " +
                "question3_na_count INT(10) NULL, " +
                " FOREIGN KEY (" + activity_id + ") REFERENCES  " + TABLE_NAME_0 + "(" + activity_id + "))";

        db.execSQL(createTable4);

        String createTable5 = "CREATE TABLE " + TABLE_NAME_5 + "(" +
                //"ID INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
                activity_id + " VARCHAR(20), " +
                "question4_q1 VARCHAR(10) NULL, " +
                "question4_q2 VARCHAR(10) NULL, " +
                "question4_q3 VARCHAR(10) NULL, " +
                "question4_q4 VARCHAR(10) NULL, " +
                "question4_q5 VARCHAR(10) NULL, " +
                "question4_q6 VARCHAR(10) NULL, " +
                "question4_count INT(10) NULL, " +
                "question4_no_count INT(10) NULL, " +
                "question4_na_count INT(10) NULL, " +
                " FOREIGN KEY (" + activity_id + ") REFERENCES  " + TABLE_NAME_0 + "(" + activity_id + "))";

        db.execSQL(createTable5);

        String createTable6 = "CREATE TABLE " + TABLE_NAME_6 + "(" +
                //"ID INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
                activity_id + " VARCHAR(20), " +
                "question5_q1 VARCHAR(10) NULL, " +
                "question5_q2 VARCHAR(10) NULL, " +
                "question5_q3 VARCHAR(10) NULL, " +
                "question5_q4 VARCHAR(10) NULL, " +
                "question5_q5 VARCHAR(10) NULL, " +
                "question5_count INT(10) NULL, " +
                "question5_no_count INT(10) NULL, " +
                "question5_na_count INT(10) NULL, " +
                " FOREIGN KEY (" + activity_id + ") REFERENCES  " + TABLE_NAME_0 + "(" + activity_id + "))";

        db.execSQL(createTable6);

        String createTable7 = "CREATE TABLE " + TABLE_NAME_7 + "(" +
                //"ID INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
                activity_id + " VARCHAR(20), " +
                "question6_q1 VARCHAR(10) NULL, " +
                "question6_q2 VARCHAR(10) NULL, " +
                "question6_q3 VARCHAR(10) NULL, " +
                "question6_q4 VARCHAR(10) NULL, " +
                "question6_q5 VARCHAR(10) NULL, " +
                "question6_q6 VARCHAR(10) NULL, " +
                "question6_q7 VARCHAR(10) NULL, " +
                "question6_count INT(10) NULL, " +
                "question6_no_count INT(10) NULL, " +
                "question6_na_count INT(10) NULL, " +
                " FOREIGN KEY (" + activity_id + ") REFERENCES  " + TABLE_NAME_0 + "(" + activity_id + "))";

        db.execSQL(createTable7);

        String createTable8 = "CREATE TABLE " + TABLE_NAME_8 + "(" +
                //"ID INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
                activity_id + " VARCHAR(20), " +
                "question7_q1 VARCHAR(10) NULL, " +
                "question7_q2 VARCHAR(10) NULL, " +
                "question7_q3 VARCHAR(10) NULL, " +
                "question7_q4 VARCHAR(10) NULL, " +
                "question7_q5 VARCHAR(10) NULL, " +
                "question7_q6 VARCHAR(10) NULL, " +
                "question7_count INT(10) NULL, " +
                "question7_no_count INT(10) NULL, " +
                "question7_na_count INT(10) NULL, " +
                " FOREIGN KEY (" + activity_id + ") REFERENCES  " + TABLE_NAME_0 + "(" + activity_id + "))";

        db.execSQL(createTable8);

        String createTable9 = "CREATE TABLE " + TABLE_NAME_9 + "(" +
                //"ID INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
                activity_id + " VARCHAR(20), " +
                "question8_q1 VARCHAR(10) NULL, " +
                "question8_q2 VARCHAR(10) NULL, " +
                "question8_q3 VARCHAR(10) NULL, " +
                "question8_q4 VARCHAR(10) NULL, " +
                "question8_q5 VARCHAR(10) NULL, " +
                "question8_count INT(10) NULL, " +
                "question8_no_count INT(10) NULL, " +
                "question8_na_count INT(10) NULL, " +
                " FOREIGN KEY (" + activity_id + ") REFERENCES  " + TABLE_NAME_0 + "(" + activity_id + "))";

        db.execSQL(createTable9);

        String createTable10 = "CREATE TABLE " + TABLE_NAME_10 + "(" +
                //"ID INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
                activity_id + " VARCHAR(20), " +
                "question9_q1 VARCHAR(10) NULL, " +
                "question9_q2 VARCHAR(10) NULL, " +
                "question9_q3 VARCHAR(10) NULL, " +
                "question9_q4 VARCHAR(10) NULL, " +
                "question9_count INT(10) NULL, " +
                "question9_no_count INT(10) NULL, " +
                "question9_na_count INT(10) NULL, " +
                " FOREIGN KEY (" + activity_id + ") REFERENCES  " + TABLE_NAME_0 + "(" + activity_id + "))";

        db.execSQL(createTable10);

        String createTable11 = "CREATE TABLE " + TABLE_NAME_11 + "(" +
                //"ID INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
                activity_id + " VARCHAR(20), " +
                "question10_q1 VARCHAR(10) NULL, " +
                "question10_q2 VARCHAR(10) NULL, " +
                "question10_q3 VARCHAR(10) NULL, " +
                "question10_q4 VARCHAR(10) NULL, " +
                "question10_q5 VARCHAR(10) NULL, " +
                "question10_count INT(10) NULL, " +
                "question10_no_count INT(10) NULL, " +
                "question10_na_count INT(10) NULL, " +
                " FOREIGN KEY (" + activity_id + ") REFERENCES  " + TABLE_NAME_0 + "(" + activity_id + "))";

        db.execSQL(createTable11);

        String createTable12 = "CREATE TABLE " + TABLE_NAME_12 + "(" +
                //"ID INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
                activity_id + " VARCHAR(20), " +
                "question11_q1 VARCHAR(10) NULL, " +
                "question11_q2 VARCHAR(10) NULL, " +
                "question11_q3 VARCHAR(10) NULL, " +
                "question11_count INT(10) NULL, " +
                "question11_no_count INT(10) NULL, " +
                "question11_na_count INT(10) NULL, " +
                " FOREIGN KEY (" + activity_id + ") REFERENCES  " + TABLE_NAME_0 + "(" + activity_id + "))";

        db.execSQL(createTable12);

        String createTable13 = "CREATE TABLE " + TABLE_NAME_13 + "(" +
                //"ID INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
                activity_id + " VARCHAR(20), " +
                "question12_q1 VARCHAR(10) NULL, " +
                "question12_q2 VARCHAR(10) NULL, " +
                "question12_q3 VARCHAR(10) NULL, " +
                "question12_q4 VARCHAR(10) NULL, " +
                "question12_q5 VARCHAR(10) NULL, " +
                "question12_count INT(10) NULL, " +
                "question12_no_count INT(10) NULL, " +
                "question12_na_count INT(10) NULL, " +
                " FOREIGN KEY (" + activity_id + ") REFERENCES  " + TABLE_NAME_0 + "(" + activity_id + "))";

        db.execSQL(createTable13);

        String createTable14 = "CREATE TABLE " + TABLE_NAME_14 + "(" +
                //"ID INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
                activity_id + " VARCHAR(20), " +
                "question13_q1 VARCHAR(10) NULL, " +
                "question13_q2 VARCHAR(10) NULL, " +
                "question13_q3 VARCHAR(10) NULL, " +
                "question13_q4 VARCHAR(10) NULL, " +
                "question13_q5 VARCHAR(10) NULL, " +
                "question13_count INT(10) NULL, " +
                "question13_no_count INT(10) NULL, " +
                "question13_na_count INT(10) NULL, " +
                " FOREIGN KEY (" + activity_id + ") REFERENCES  " + TABLE_NAME_0 + "(" + activity_id + "))";

        db.execSQL(createTable14);

        String createTable15 = "CREATE TABLE " + TABLE_NAME_15 + "(" +
                //"ID INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
                activity_id + " VARCHAR(20), " +
                "question14_q1 VARCHAR(10) NULL, " +
                "question14_q2 VARCHAR(10) NULL, " +
                "question14_count INT(10) NULL, " +
                "question14_no_count INT(10) NULL, " +
                "question14_na_count INT(10) NULL, " +
                " FOREIGN KEY (" + activity_id + ") REFERENCES  " + TABLE_NAME_0 + "(" + activity_id + "))";

        db.execSQL(createTable15);

        String createTable16 = "CREATE TABLE " + TABLE_NAME_16 + "(" +
                //"ID INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
                activity_id + " VARCHAR(20), " +
                "question15_q1 VARCHAR(10) NULL, " +
                "question15_q2 VARCHAR(10) NULL, " +
                "question15_q3 VARCHAR(10) NULL, " +
                "question15_count INT(10) NULL, " +
                "question15_no_count INT(10) NULL, " +
                "question15_na_count INT(10) NULL, " +
                " FOREIGN KEY (" + activity_id + ") REFERENCES  " + TABLE_NAME_0 + "(" + activity_id + "))";

        db.execSQL(createTable16);

        String createTable17 = "CREATE TABLE " + TABLE_NAME_17 + "(" +
                //"ID INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
                activity_id + " VARCHAR(20), " +
                "question16_q1 VARCHAR(10) NULL, " +
                "question16_q2 VARCHAR(10) NULL, " +
                "question16_q3 VARCHAR(10) NULL, " +
                "question16_q4 VARCHAR(10) NULL, " +
                "question16_q5 VARCHAR(10) NULL, " +
                "question16_q6 VARCHAR(10) NULL, " +
                "question16_count INT(10) NULL, " +
                "question16_no_count INT(10) NULL, " +
                "question16_na_count INT(10) NULL, " +
                " FOREIGN KEY (" + activity_id + ") REFERENCES  " + TABLE_NAME_0 + "(" + activity_id + "))";

        db.execSQL(createTable17);

        String createTable18 = "CREATE TABLE " + TABLE_NAME_18 + "(" +
                //"ID INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
                activity_id + " VARCHAR(20), " +
                "question17_q1 VARCHAR(10) NULL, " +
                "question17_q2 VARCHAR(10) NULL, " +
                "question17_q3 VARCHAR(10) NULL, " +
                "question17_count INT(10) NULL, " +
                "question17_no_count INT(10) NULL, " +
                "question17_na_count INT(10) NULL, " +
                " FOREIGN KEY (" + activity_id + ") REFERENCES  " + TABLE_NAME_0 + "(" + activity_id + "))";

        db.execSQL(createTable18);

        CREATE_TABLE_KHANDAN = "CREATE TABLE KHANDAN( " +
                "id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
                "manual_id varchar(128) NOT NULL," +
                "uid varchar(128) NOT NULL," +
                "province_id varchar(128) NOT NULL," +
                "district_id varchar(128) NOT NULL," +
                "subdistrict_id varchar(128) NOT NULL," +
                "uc_id varchar(128) NOT NULL," +
                "village_id varchar(128) NOT NULL," +
                "family_head_name varchar(128) NOT NULL," +
                "family_address varchar(128) NOT NULL," +
                "water_source varchar(128) NOT NULL," +
                "toilet_facility varchar(128) NOT NULL," +
                "added_by varchar(128) NOT NULL," +
                "is_synced varchar(1) NOT NULL," +
                "added_on varchar(30) NOT NULL" +
                ")";
        db.execSQL(CREATE_TABLE_KHANDAN);


                CREATE_TABLE_USERS = "CREATE TABLE USERS( " +
                "id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
                "uid varchar(50) NOT NULL," +
                "privilege varchar(50) NOT NULL," +
                "username varchar(50) NOT NULL," +
                "password varchar(150) NOT NULL," +
                "salt varchar(50) NOT NULL," +
                "district_id varchar(150) NOT NULL," +
                "country_id varchar(50) NOT NULL," +
                "province_id varchar(50) NOT NULL," +
                "uc_id varchar(50) NOT NULL," +
                "metadata text DEFAULT NULL" +
                ")";
        db.execSQL(CREATE_TABLE_USERS);

        CREATE_TABLE_FACILITIES = "CREATE TABLE FACILITIES( " +
                "id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
                "uid varchar(50) NOT NULL," +
                "name varchar(50) NOT NULL," +
                "country_id varchar(150) NOT NULL," +
                "province_id varchar(50) NOT NULL," +
                "district_id varchar(150) NOT NULL," +
                "tehsil_id varchar(50) NOT NULL," +
                "uc_id varchar(50) NOT NULL" +
                ")";
        db.execSQL(CREATE_TABLE_FACILITIES);

        CREATE_TABLE_LOW_BIRTH = "CREATE TABLE low_birth( " +
                "id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
                "member_id varchar(50) NOT NULL," +
                "full_name varchar(50) NOT NULL," +
                "added_by varchar(50) NOT NULL," +
                "month varchar(150) NOT NULL," +
                "year varchar(50) NOT NULL" +
                ")";

        db.execSQL(CREATE_TABLE_LOW_BIRTH);

        CREATE_TABLE_LIVE_BIRTH = "CREATE TABLE live_birth( " +
                "id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
                "member_id varchar(50) NOT NULL," +
                "full_name varchar(50) NOT NULL," +
                "added_by varchar(50) NOT NULL," +
                "month varchar(150) NOT NULL," +
                "year varchar(50) NOT NULL" +
                ")";

        db.execSQL(CREATE_TABLE_LIVE_BIRTH);

        CREATE_TABLE_NEW_PREG = "CREATE TABLE new_preg( " +
                "id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
                "member_id varchar(50) NOT NULL," +
                "full_name varchar(50) NOT NULL," +
                "added_by varchar(50) NOT NULL," +
                "month varchar(150) NOT NULL," +
                "year varchar(50) NOT NULL" +
                ")";

        db.execSQL(CREATE_TABLE_NEW_PREG);


        CREATE_TABLE_TOTAL_PREG = "CREATE TABLE total_preg( " +
                "id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
                "member_id varchar(50) NOT NULL," +
                "full_name varchar(50) NOT NULL," +
                "added_by varchar(50) NOT NULL," +
                "month varchar(150) NOT NULL," +
                "year varchar(50) NOT NULL" +
                ")";

        db.execSQL(CREATE_TABLE_TOTAL_PREG);

        CREATE_TABLE_VALIDATION = "CREATE TABLE validation( " +
                "id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
                "member_id varchar(50) NOT NULL," +
                "full_name varchar(50) NOT NULL," +
                "added_by varchar(50) NOT NULL," +
                "month varchar(50) NOT NULL," +
                "year varchar(50) NOT NULL," +
                "type varchar(50) NOT NULL," +
                "is_validated varchar(50) NOT NULL," +
                "activity_id varchar(50) NOT NULL," +
                " FOREIGN KEY (" + activity_id + ") REFERENCES  " + TABLE_NAME_0 + "(" + activity_id + "))";

        db.execSQL(CREATE_TABLE_VALIDATION);

        CREATE_TABLE_KMEMBER = "CREATE TABLE MEMBER( " +
                "id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
                "manual_id varchar(128) NOT NULL," +
                "uid varchar(128) NOT NULL," +
                "khandan_id varchar(128) NOT NULL," +
                "full_name varchar(128) NOT NULL," +
                "nicnumber varchar(128) NOT NULL," +
                "phone_number varchar(128) NOT NULL," +
                "data text NOT NULL," +
                "gender INTEGER NOT NULL," +
                "age INT NOT NULL," +
                "dob TEXT NOT NULL," +
                "bio_code TEXT NOT NULL," +
                "qr_code varchar(128) NOT NULL," +
                "added_by varchar(128) NOT NULL," +
                "is_synced INTEGER NOT NULL," +
                "added_on varchar(30) NOT NULL" +
                ")";
        db.execSQL(CREATE_TABLE_KMEMBER);


                CREATE_TABLE_CHILD_VACINATION = "CREATE TABLE CVACCINATION( " +
                        activity_id + " VARCHAR(20), " +
                        "id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
                "member_uid varchar(128) NOT NULL," +
                "vaccine_id varchar(128) NOT NULL," +
                "record_data varchar(100) NOT NULL," +
                "type varchar(30) NOT NULL," +
                "due_date varchar(100) NOT NULL," +
                "vaccinated_on varchar(100) NOT NULL," +
                "image_location varchar(100) NOT NULL," +
                "metadata text NOT NULL," +
                "added_by varchar(128) NOT NULL," +
                "is_synced varchar(1) NOT NULL," +
                        "is_validated varchar(1) NULL," +
                        "added_on varchar(30) NOT NULL," +
                        " FOREIGN KEY (" + activity_id + ") REFERENCES  " + TABLE_NAME_0 + "(" + activity_id + "))";

                db.execSQL(CREATE_TABLE_CHILD_VACINATION);

                CREATE_TABLE_VACCINES = "CREATE TABLE VACCINES (" +
                " id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
                " uid varchar(50) NOT NULL," +
                " defaulter_date varchar(150) NOT NULL," +
                " due_date varchar(150) NOT NULL," +
                " name varchar(150) NOT NULL" +
                ")";
        db.execSQL(CREATE_TABLE_VACCINES);

        CREATE_TABLE_MONTHLYREPORT = "CREATE TABLE MONTHLY_REPORT (" +
                " id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
                " month varchar(10) NULL," +
                " year varchar(10) NULL," +
                " total_population varchar(10) NULL," +
                " total_population_np varchar(10) NULL," +
                " FLCF_district varchar(10) NULL," +
                " reporting_FLCF varchar(10) NULL," +
                " off_road_vehicles varchar(10) NULL," +
                " pop_registered_lhw varchar(10) NULL," +
                " lhw_submit_report varchar(10) NULL," +
                //basicinfo
                " health_commettees varchar(10) NULL," +
                " women_suport_group varchar(10) NULL," +
                " household_registered_lhw varchar(10) NULL," +
                " tap varchar(10) NULL," +
                " spring varchar(10) NULL," +
                " handpump varchar(10) NULL," +
                " well varchar(10) NULL," +
                " other varchar(10) NULL," +
                " flush_system varchar(10) NULL," +
                //child_health
                " new_borns_1week varchar(10) NULL," +
                " low_birth_weight varchar(10) NULL," +
                " breast_fed varchar(10) NULL," +
                " immunized varchar(10) NULL," +
                " age_1223_count varchar(10) NULL," +
                " age_1223_fully_imunized varchar(10) NULL," +
                " age_lt3_count varchar(10) NULL," +
                " age_lt3_gm varchar(10) NULL," +
                " age_lt3_malnurished varchar(10) NULL," +
                //maternal_health
                " new_preg varchar(10) NULL," +
                " total_preg varchar(10) NULL," +
                " total_vistis varchar(10) NULL," +
                " iron_sup varchar(10) NULL," +
                " abortions varchar(10) NULL," +
                " delivey_4p varchar(10) NULL," +
                " delivery_pnc varchar(10) NULL," +
                " delivery_immunized varchar(10) NULL," +
                //familyplanning
                " eligible varchar(10) NULL," +
                " provided varchar(10) NULL," +
                " followup varchar(10) NULL," +
                " modern varchar(10) NULL," +
                " condom_users varchar(10) NULL," +
                " pill_users varchar(10) NULL," +
                " injectible_users varchar(10) NULL," +
                " iucd_users varchar(10) NULL," +
                " surgical_users varchar(10) NULL," +
                " other_users varchar(10) NULL," +
                " traditional_users varchar(10) NULL," +
                " referred varchar(10) NULL," +
                " supplied_condoms varchar(10) NULL," +
                " supplied_pills varchar(10) NULL," +
                " supplied_injectibles varchar(10) NULL," +
                //diseases
                " diarrhea_a5 varchar(10) NULL," +
                " diarrhea_u5 varchar(10) NULL," +
                " ari_a5 varchar(10) NULL," +
                " ari_u5 varchar(10) NULL," +
                " fever_a5 varchar(10) NULL," +
                " fever_u5 varchar(10) NULL," +
                " resp_a5 varchar(10) NULL," +
                " resp_u5 varchar(10) NULL," +
                " anaemia_a5 varchar(10) NULL," +
                " anaemia_u5 varchar(10) NULL," +
                " scabies_a5 varchar(10) NULL," +
                " scabies_u5 varchar(10) NULL," +
                " eye_infections_a5 varchar(10) NULL," +
                " eye_infections_u5 varchar(10) NULL," +
                " rtis_a5 varchar(10) NULL," +
                " rtis_u5 varchar(10) NULL," +
                " worm_a5 varchar(10) NULL," +
                " worm_u5 varchar(10) NULL," +
                " malaria_a5 varchar(10) NULL," +
                " malaria_u5 varchar(10) NULL," +
                " referral_a5 varchar(10) NULL," +
                " referral_u5 varchar(10) NULL," +
                " tb_suspect_a5 varchar(10) NULL," +
                " tb_suspect_u5 varchar(10) NULL," +
                " tb_diagnosed_a5 varchar(10) NULL," +
                " tb_diagnosed_u5 varchar(10) NULL," +
                " tb_followed_a5 varchar(10) NULL," +
                " tb_followed_u5 varchar(10) NULL," +
                //live_deaths
                " live varchar(10) NULL," +
                " still varchar(10) NULL," +
                " deaths_all varchar(10) NULL," +
                " noenatal varchar(10) NULL," +
                " infant varchar(10) NULL," +
                " children varchar(10) NULL," +
                " maternal varchar(10) NULL," +
                //medicines
                " tab_paracetamol varchar(10) NULL," +
                " syp_paracetamol varchar(10) NULL," +
                " tab_choloroquin varchar(10) NULL," +
                " syp_choloroquin varchar(10) NULL," +
                " tab_mebendazole varchar(10) NULL," +
                " syp_pipearzine varchar(10) NULL," +
                " ors varchar(10) NULL," +
                " eye_ontiment varchar(10) NULL," +
                " syp_contrimexazole varchar(10) NULL," +
                " iron_tab varchar(10) NULL," +
                " antiseptic_lotion varchar(10) NULL," +
                " benzyle_benzoate_lotion varchar(10) NULL," +
                " sticking_plaster varchar(10) NULL," +
                " b_complex_syp varchar(10) NULL," +
                " cotton_bandages varchar(10) NULL," +
                " cotton_wool varchar(10) NULL," +
                " condoms varchar(10) NULL," +
                " oral_pills varchar(10) NULL," +
                " contraceptive_inj varchar(10) NULL," +
                " med_others varchar(10) NULL," +
                //miscellous
                " lhw_kit_bag varchar(10) NULL," +
                " weighing_machine varchar(10) NULL," +
                " thermometer varchar(10) NULL," +
                " torch_with_cell varchar(10) NULL," +
                " scissors varchar(10) NULL," +
                " syringe_cutter varchar(10) NULL," +
                " mis_others varchar(10) NULL," +
                //supervison
                " lhs varchar(10) NULL," +
                " dco varchar(10) NULL," +
                " adc varchar(10) NULL," +
                " fpo varchar(10) NULL," +
                " ppiu varchar(10) NULL" +
                ")";
        db.execSQL(CREATE_TABLE_MONTHLYREPORT);
}


    @Override
    public void onConfigure(SQLiteDatabase db) {
        super.onConfigure(db);
        db.execSQL("PRAGMA foreign_keys=ON");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_2);
        onCreate(db);
    }

    public boolean activities(String date, String health, String healthcentre, String healthworker, String facility, String totalcount,
                              String month, String year, String status) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(activity_date, date);
        contentValues.put(activity_health, health);
        contentValues.put(activity_health_centre, healthcentre);
        contentValues.put(activity_health_worker, healthworker);
        contentValues.put(activity_facility, facility);
        contentValues.put(total_count, totalcount);
        contentValues.put(activity_month, month);
        contentValues.put(activity_year, year);
        contentValues.put(activity_Status,status);

        long result1 = db.insert(TABLE_NAME_0, null, contentValues);

        if (result1 == -1) {
            return false;
        } else {
            return true;

        }
    }

    public boolean question1(String id, String question1_q1, String question1_q2, String question1_q3, String question1_q4, String question1_q5,
                             String question1_q6, Integer question1_count, Integer question1_no_count, Integer question1_na_count) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(activity_id, id);
        contentValues.put(QUESTION1_Q1, question1_q1);
        contentValues.put(QUESTION1_Q2, question1_q2);
        contentValues.put(QUESTION1_Q3, question1_q3);
        contentValues.put(QUESTION1_Q4, question1_q4);
        contentValues.put(QUESTION1_Q5, question1_q5);
        contentValues.put(QUESTION1_Q6, question1_q6);
        contentValues.put(QUESTION1_COUNT, question1_count);
        contentValues.put(QUESTION1_NO_COUNT, question1_no_count);
        contentValues.put(QUESTION1_NA_COUNT, question1_na_count);

        long result1 = db.insert(TABLE_NAME_2, null, contentValues);

        if (result1 == -1) {
            return false;
        } else {
            return true;

        }
    }

    public boolean question2(String id, String question2_q1, String question2_q2, String question2_q3, String question2_q4, String question2_q5,
                             Integer question2_count, Integer question2_no_count, Integer question2_na_count) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(activity_id, id);
        contentValues.put(QUESTION2_Q1, question2_q1);
        contentValues.put(QUESTION2_Q2, question2_q2);
        contentValues.put(QUESTION2_Q3, question2_q3);
        contentValues.put(QUESTION2_Q4, question2_q4);
        contentValues.put(QUESTION2_Q5, question2_q5);
        contentValues.put(QUESTION2_COUNT, question2_count);
        contentValues.put(QUESTION2_NO_COUNT, question2_no_count);
        contentValues.put(QUESTION2_NA_COUNT, question2_na_count);

        long result1 = db.insert(TABLE_NAME_3, null, contentValues);

        if (result1 == -1) {
            return false;
        } else {
            return true;

        }
    }

    public boolean question3(String id, String question3_q1, String question3_q2, String question3_q3, String question3_q4, String question3_q5,
                             String question3_q6, String question3_q7, String question3_q8, String question3_q9, String question3_q10,
                             Integer question3_count, Integer question3_no_count, Integer question3_na_count) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(activity_id, id);
        contentValues.put(QUESTION3_Q1, question3_q1);
        contentValues.put(QUESTION3_Q2, question3_q2);
        contentValues.put(QUESTION3_Q3, question3_q3);
        contentValues.put(QUESTION3_Q4, question3_q4);
        contentValues.put(QUESTION3_Q5, question3_q5);
        contentValues.put(QUESTION3_Q6, question3_q6);
        contentValues.put(QUESTION3_Q7, question3_q7);
        contentValues.put(QUESTION3_Q8, question3_q8);
        contentValues.put(QUESTION3_Q9, question3_q9);
        contentValues.put(QUESTION3_Q10, question3_q10);
        contentValues.put(QUESTION3_COUNT, question3_count);
        contentValues.put(QUESTION3_NO_COUNT, question3_no_count);
        contentValues.put(QUESTION3_NA_COUNT, question3_na_count);

        long result1 = db.insert(TABLE_NAME_4, null, contentValues);

        if (result1 == -1) {
            return false;
        } else {
            return true;

        }
    }

    public boolean question4(String id, String question4_q1, String question4_q2, String question4_q3, String question4_q4, String question4_q5,
                             String question4_q6, Integer question4_count, Integer question4_no_count, Integer question4_na_count) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(activity_id, id);
        contentValues.put(QUESTION4_Q1, question4_q1);
        contentValues.put(QUESTION4_Q2, question4_q2);
        contentValues.put(QUESTION4_Q3, question4_q3);
        contentValues.put(QUESTION4_Q4, question4_q4);
        contentValues.put(QUESTION4_Q5, question4_q5);
        contentValues.put(QUESTION4_Q6, question4_q6);
        contentValues.put(QUESTION4_COUNT, question4_count);
        contentValues.put(QUESTION4_NO_COUNT, question4_no_count);
        contentValues.put(QUESTION4_NA_COUNT, question4_na_count);

        long result1 = db.insert(TABLE_NAME_5, null, contentValues);

        if (result1 == -1) {
            return false;
        } else {
            return true;

        }
    }

    public boolean question5(String id, String question5_q1, String question5_q2, String question5_q3, String question5_q4, String question5_q5,
                             Integer question5_count, Integer question5_no_count, Integer question5_na_count) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(activity_id, id);
        contentValues.put(QUESTION5_Q1, question5_q1);
        contentValues.put(QUESTION5_Q2, question5_q2);
        contentValues.put(QUESTION5_Q3, question5_q3);
        contentValues.put(QUESTION5_Q4, question5_q4);
        contentValues.put(QUESTION5_Q5, question5_q5);
        contentValues.put(QUESTION5_COUNT, question5_count);
        contentValues.put(QUESTION5_NO_COUNT, question5_no_count);
        contentValues.put(QUESTION5_NA_COUNT, question5_na_count);

        long result1 = db.insert(TABLE_NAME_6, null, contentValues);

        if (result1 == -1) {
            return false;
        } else {
            return true;

        }
    }

    public boolean question6(String id, String question6_q1, String question6_q2, String question6_q3, String question6_q4, String question6_q5,
                             String question6_q6, String question6_q7, Integer question6_count, Integer question6_no_count, Integer question6_na_count) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(activity_id, id);
        contentValues.put(QUESTION6_Q1, question6_q1);
        contentValues.put(QUESTION6_Q2, question6_q2);
        contentValues.put(QUESTION6_Q3, question6_q3);
        contentValues.put(QUESTION6_Q4, question6_q4);
        contentValues.put(QUESTION6_Q5, question6_q5);
        contentValues.put(QUESTION6_Q6, question6_q6);
        contentValues.put(QUESTION6_Q7, question6_q7);
        contentValues.put(QUESTION6_COUNT, question6_count);
        contentValues.put(QUESTION6_NO_COUNT, question6_no_count);
        contentValues.put(QUESTION6_NA_COUNT, question6_na_count);

        long result1 = db.insert(TABLE_NAME_7, null, contentValues);

        if (result1 == -1) {
            return false;
        } else {
            return true;

        }
    }

    public boolean question7(String id, String question7_q1, String question7_q2, String question7_q3, String question7_q4, String question7_q5,
                             String question7_q6, Integer question7_count, Integer question7_no_count, Integer question7_na_count) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(activity_id, id);
        contentValues.put(QUESTION7_Q1, question7_q1);
        contentValues.put(QUESTION7_Q2, question7_q2);
        contentValues.put(QUESTION7_Q3, question7_q3);
        contentValues.put(QUESTION7_Q4, question7_q4);
        contentValues.put(QUESTION7_Q5, question7_q5);
        contentValues.put(QUESTION7_Q6, question7_q6);
        contentValues.put(QUESTION7_COUNT, question7_count);
        contentValues.put(QUESTION7_NO_COUNT, question7_no_count);
        contentValues.put(QUESTION7_NA_COUNT, question7_na_count);

        long result1 = db.insert(TABLE_NAME_8, null, contentValues);

        if (result1 == -1) {
            return false;
        } else {
            return true;

        }
    }

    public boolean question8(String id, String question8_q1, String question8_q2, String question8_q3, String question8_q4,String question8_q5,
                             Integer question8_count, Integer question8_no_count, Integer question8_na_count) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(activity_id, id);
        contentValues.put(QUESTION8_Q1, question8_q1);
        contentValues.put(QUESTION8_Q2, question8_q2);
        contentValues.put(QUESTION8_Q3, question8_q3);
        contentValues.put(QUESTION8_Q4, question8_q4);
        contentValues.put(QUESTION8_Q5, question8_q5);
        contentValues.put(QUESTION8_COUNT, question8_count);
        contentValues.put(QUESTION8_NO_COUNT, question8_no_count);
        contentValues.put(QUESTION8_NA_COUNT, question8_na_count);

        long result1 = db.insert(TABLE_NAME_9, null, contentValues);

        if (result1 == -1) {
            return false;
        } else {
            return true;

        }
    }

    public boolean question9(String id, String question9_q1, String question9_q2, String question9_q3, String question9_q4,
                             Integer question9_count, Integer question9_no_count, Integer question9_na_count) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(activity_id, id);
        contentValues.put(QUESTION9_Q1, question9_q1);
        contentValues.put(QUESTION9_Q2, question9_q2);
        contentValues.put(QUESTION9_Q3, question9_q3);
        contentValues.put(QUESTION9_Q4, question9_q4);
        contentValues.put(QUESTION9_COUNT, question9_count);
        contentValues.put(QUESTION9_NO_COUNT, question9_no_count);
        contentValues.put(QUESTION9_NA_COUNT, question9_na_count);

        long result1 = db.insert(TABLE_NAME_10, null, contentValues);

        if (result1 == -1) {
            return false;
        } else {
            return true;

        }
    }

    public boolean question10(String id, String question10_q1, String question10_q2, String question10_q3, String question10_q4, String question10_q5,
                              Integer question10_count, Integer question10_no_count, Integer question10_na_count) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(activity_id, id);
        contentValues.put(QUESTION10_Q1, question10_q1);
        contentValues.put(QUESTION10_Q2, question10_q2);
        contentValues.put(QUESTION10_Q3, question10_q3);
        contentValues.put(QUESTION10_Q4, question10_q4);
        contentValues.put(QUESTION10_Q5, question10_q5);
        contentValues.put(QUESTION10_COUNT, question10_count);
        contentValues.put(QUESTION10_NO_COUNT, question10_no_count);
        contentValues.put(QUESTION10_NA_COUNT, question10_na_count);

        long result1 = db.insert(TABLE_NAME_11, null, contentValues);

        if (result1 == -1) {
            return false;
        } else {
            return true;

        }
    }

    public boolean question11(String id, String question11_q1, String question11_q2, String question11_q3, Integer question11_count,
                              Integer question11_no_count, Integer question11_na_count) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(activity_id, id);
        contentValues.put(QUESTION11_Q1, question11_q1);
        contentValues.put(QUESTION11_Q2, question11_q2);
        contentValues.put(QUESTION11_Q3, question11_q3);
        contentValues.put(QUESTION11_COUNT, question11_count);
        contentValues.put(QUESTION11_NO_COUNT, question11_no_count);
        contentValues.put(QUESTION11_NA_COUNT, question11_na_count);

        long result1 = db.insert(TABLE_NAME_12, null, contentValues);

        if (result1 == -1) {
            return false;
        } else {
            return true;

        }
    }

    public boolean question12(String id, String question12_q1, String question12_q2, String question12_q3, String question12_q4, String question12_q5,
                              Integer question12_count, Integer question12_no_count, Integer question12_na_count) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(activity_id, id);
        contentValues.put(QUESTION12_Q1, question12_q1);
        contentValues.put(QUESTION12_Q2, question12_q2);
        contentValues.put(QUESTION12_Q3, question12_q3);
        contentValues.put(QUESTION12_Q4, question12_q4);
        contentValues.put(QUESTION12_Q5, question12_q5);
        contentValues.put(QUESTION12_COUNT, question12_count);
        contentValues.put(QUESTION12_NO_COUNT, question12_no_count);
        contentValues.put(QUESTION12_NA_COUNT, question12_na_count);

        long result1 = db.insert(TABLE_NAME_13, null, contentValues);

        if (result1 == -1) {
            return false;
        } else {
            return true;

        }
    }

    public boolean question13(String id, String question13_q1, String question13_q2, String question13_q3, String question13_q4, String question13_q5,
                              Integer question13_count, Integer question13_no_count, Integer question13_na_count) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(activity_id, id);
        contentValues.put(QUESTION13_Q1, question13_q1);
        contentValues.put(QUESTION13_Q2, question13_q2);
        contentValues.put(QUESTION13_Q3, question13_q3);
        contentValues.put(QUESTION13_Q4, question13_q4);
        contentValues.put(QUESTION13_Q5, question13_q5);
        contentValues.put(QUESTION13_COUNT, question13_count);
        contentValues.put(QUESTION13_NO_COUNT, question13_no_count);
        contentValues.put(QUESTION13_NA_COUNT, question13_na_count);

        long result1 = db.insert(TABLE_NAME_14, null, contentValues);

        if (result1 == -1) {
            return false;
        } else {
            return true;

        }
    }

    public boolean question14(String id, String question14_q1, String question14_q2,Integer question14_count, Integer question14_no_count,
                              Integer question14_na_count) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(activity_id, id);
        contentValues.put(QUESTION14_Q1, question14_q1);
        contentValues.put(QUESTION14_Q2, question14_q2);
        contentValues.put(QUESTION14_COUNT, question14_count);
        contentValues.put(QUESTION14_NO_COUNT, question14_no_count);
        contentValues.put(QUESTION14_NA_COUNT, question14_na_count);

        long result1 = db.insert(TABLE_NAME_15, null, contentValues);

        if (result1 == -1) {
            return false;
        } else {
            return true;

        }
    }

    public boolean question15(String id, String question15_q1, String question15_q2, String question15_q3,Integer question15_count,
                              Integer question15_no_count, Integer question15_na_count) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(activity_id, id);
        contentValues.put(QUESTION15_Q1, question15_q1);
        contentValues.put(QUESTION15_Q2, question15_q2);
        contentValues.put(QUESTION15_Q3, question15_q3);
        contentValues.put(QUESTION15_COUNT, question15_count);
        contentValues.put(QUESTION15_NO_COUNT, question15_no_count);
        contentValues.put(QUESTION15_NA_COUNT, question15_na_count);

        long result1 = db.insert(TABLE_NAME_16, null, contentValues);

        if (result1 == -1) {
            return false;
        } else {
            return true;

        }
    }

    public boolean question16(String id, String question16_q1, String question16_q2, String question16_q3, String question16_q4, String question16_q5,
                             String question16_q6, Integer question16_count, Integer question16_no_count, Integer question16_na_count) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(activity_id, id);
        contentValues.put(QUESTION16_Q1, question16_q1);
        contentValues.put(QUESTION16_Q2, question16_q2);
        contentValues.put(QUESTION16_Q3, question16_q3);
        contentValues.put(QUESTION16_Q4, question16_q4);
        contentValues.put(QUESTION16_Q5, question16_q5);
        contentValues.put(QUESTION16_Q6, question16_q6);
        contentValues.put(QUESTION16_COUNT, question16_count);
        contentValues.put(QUESTION16_NO_COUNT, question16_no_count);
        contentValues.put(QUESTION16_NA_COUNT, question16_na_count);

        long result1 = db.insert(TABLE_NAME_17, null, contentValues);

        if (result1 == -1) {
            return false;
        } else {
            return true;

        }
    }

    public boolean question17(String id, String question17_q1, String question17_q2, String question17_q3, Integer question17_count,
                              Integer question17_no_count, Integer question17_na_count) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(activity_id, id);
        contentValues.put(QUESTION17_Q1, question17_q1);
        contentValues.put(QUESTION17_Q2, question17_q2);
        contentValues.put(QUESTION17_Q3, question17_q3);
        contentValues.put(QUESTION17_COUNT, question17_count);
        contentValues.put(QUESTION17_NO_COUNT, question17_no_count);
        contentValues.put(QUESTION17_NA_COUNT, question17_na_count);

        long result1 = db.insert(TABLE_NAME_18, null, contentValues);

        if (result1 == -1) {
            return false;
        } else {
            return true;

        }
    }

    public void deleteUsers() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("USERS",null,null);
        db.close();
    }

    public void deleteFacilities() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("FACILITIES",null,null);
        db.close();
    }
}