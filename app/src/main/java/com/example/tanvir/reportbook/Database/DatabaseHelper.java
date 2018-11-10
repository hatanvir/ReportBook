package com.example.tanvir.reportbook.Database;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "report.db";
    public static final int DATABASE_VERSION = 3;

    //database table components
    public static final String REPORT_TABLE = "report";
    public static final String REPORT_ID = "id";
    public static final String REPORT_PRAYER = "prayer";
    public static final String REPORT_QURAN = "quran";
    public static final String REPORT_HADITH = "hadith";
    public static final String REPORT_ACADEMICBOOK = "academicBook";
    public static final String REPORT_NOVELOROTHER = "novelOrOther";
    public static final String REPORT_NEWSPAPER = "newspaper";
    public static final String REPORT_SKILLdEV = "skillDev";
    public static final String REPORT_MARKETING = "marketing";

    public static final String REPORT_WAKEUPTIME = "wakeUpTime";
    public static final String REPORT_SLEEPTIME = "sleepTime";
    public static final String REPORT_WORKOUT = "workout";


    //System.out.println(dateFormat.);

    //query for create table
    public static final String CREATE_TABLE_QUERY = "create table "+REPORT_TABLE
            +"("+REPORT_ID+" integer primary key,"
            +REPORT_PRAYER+" text,"+REPORT_QURAN+" text,"
            +REPORT_HADITH+" text,"+REPORT_ACADEMICBOOK+" text,"
            +REPORT_NOVELOROTHER+" text,"+REPORT_NEWSPAPER+" text,"
            +REPORT_SKILLdEV+" text,"+REPORT_MARKETING+" text,"
            +REPORT_WAKEUPTIME+" text,"+REPORT_SLEEPTIME+" text,"+REPORT_WORKOUT+" text);";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_TABLE_QUERY);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("drop table if EXISTS "+REPORT_TABLE);
        onCreate(sqLiteDatabase);
    }
}
