package com.example.tanvir.reportbook.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.tanvir.reportbook.Models.ReportDetails;

import java.util.ArrayList;

public class DatabaseManager {
    DatabaseHelper databaseHelper;

    public DatabaseManager(Context context) {
        databaseHelper = new DatabaseHelper(context);
    }

    public long addReportInfo(ReportDetails reportDetails) {
        SQLiteDatabase sqLiteDatabase = databaseHelper.getReadableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseHelper.REPORT_ID,Integer.parseInt(reportDetails.getId()));
        contentValues.put(DatabaseHelper.REPORT_PRAYER, reportDetails.getPrayer());
        contentValues.put(DatabaseHelper.REPORT_QURAN, reportDetails.getQuran());
        contentValues.put(DatabaseHelper.REPORT_HADITH, reportDetails.getHadith());
        contentValues.put(DatabaseHelper.REPORT_ACADEMICBOOK, reportDetails.getAcademicBook());
        contentValues.put(DatabaseHelper.REPORT_NOVELOROTHER, reportDetails.getNovelOrOtherBook());

        contentValues.put(DatabaseHelper.REPORT_NEWSPAPER, reportDetails.getNewspaper());
        contentValues.put(DatabaseHelper.REPORT_SKILLdEV, reportDetails.getSkillDev());
        contentValues.put(DatabaseHelper.REPORT_MARKETING, reportDetails.getMarketing());
        contentValues.put(DatabaseHelper.REPORT_WAKEUPTIME, reportDetails.getWakeupTime());

        contentValues.put(DatabaseHelper.REPORT_SLEEPTIME, reportDetails.getSleepTime());
        contentValues.put(DatabaseHelper.REPORT_WORKOUT, reportDetails.getWorkout());

        long insertedRow = sqLiteDatabase.insert(DatabaseHelper.REPORT_TABLE, null, contentValues);
        sqLiteDatabase.close();
        return insertedRow;
    }


    /*public long updateContactInfo(ContactsInfo contactsInfo) {
        SQLiteDatabase sqLiteDatabase = databaseHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseHelper.CONTACT_NAME, contactsInfo.getContactName());
        contentValues.put(DatabaseHelper.CONTACT_NUMBER, contactsInfo.getContactNumber());
        contentValues.put(DatabaseHelper.CONTACT_EMAIL, contactsInfo.getContactEmail());
        contentValues.put(DatabaseHelper.CONTACT_DESCRIPTION, contactsInfo.getContactDescription());
        contentValues.put(DatabaseHelper.CONTACT_RATING, contactsInfo.getContactRating());
        long updatedRow = sqLiteDatabase.update(DatabaseHelper.CONTACT_TABLE, contentValues,
                DatabaseHelper.CONTACT_ID + " =? ", new String[]{String.valueOf(contactsInfo.getId())});
        return updatedRow;
    }*/

    public ReportDetails getREportById(int id) {
        SQLiteDatabase sqLiteDatabase = databaseHelper.getReadableDatabase();
        String selectQuery = "select * from " + DatabaseHelper.REPORT_TABLE
                + " where id = " + id;
        Cursor cursor = sqLiteDatabase.rawQuery(selectQuery, null);
        ReportDetails reportDetails = null;
        if (cursor.moveToFirst()) {
            String reportPrayer = cursor.getString(cursor.getColumnIndex(DatabaseHelper.REPORT_PRAYER));
            String reportQuran = cursor.getString(cursor.getColumnIndex(DatabaseHelper.REPORT_QURAN));
            String reportHadith = cursor.getString(cursor.getColumnIndex(DatabaseHelper.REPORT_HADITH));
            String reportAcademic = cursor.getString(cursor.getColumnIndex(DatabaseHelper.REPORT_ACADEMICBOOK));
            String reportNovel = cursor.getString(cursor.getColumnIndex(DatabaseHelper.REPORT_NOVELOROTHER));

            String reportNewspaper = cursor.getString(cursor.getColumnIndex(DatabaseHelper.REPORT_NEWSPAPER));
            String reportSkillDEv = cursor.getString(cursor.getColumnIndex(DatabaseHelper.REPORT_SKILLdEV));
            String reportMarketing = cursor.getString(cursor.getColumnIndex(DatabaseHelper.REPORT_MARKETING));
            String reportWakeUp = cursor.getString(cursor.getColumnIndex(DatabaseHelper.REPORT_WAKEUPTIME));
            String reportSleep = cursor.getString(cursor.getColumnIndex(DatabaseHelper.REPORT_SLEEPTIME));
            String date =  cursor.getString(cursor.getColumnIndex(DatabaseHelper.REPORT_SLEEPTIME));

            String reportWorkOut = cursor.getString(cursor.getColumnIndex(DatabaseHelper.REPORT_WORKOUT));
            reportDetails = new ReportDetails(String.valueOf(id), reportPrayer, reportQuran, reportHadith,
                    reportAcademic,reportNovel,reportNewspaper,reportSkillDEv,reportMarketing,reportWakeUp,reportSleep,reportWorkOut);
        }
        return reportDetails;
    }

    public long updateReportInfo(ReportDetails reportDetails) {
        SQLiteDatabase sqLiteDatabase = databaseHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(DatabaseHelper.REPORT_ID,Integer.parseInt(reportDetails.getId()));
        contentValues.put(DatabaseHelper.REPORT_PRAYER, reportDetails.getPrayer());
        contentValues.put(DatabaseHelper.REPORT_QURAN, reportDetails.getQuran());
        contentValues.put(DatabaseHelper.REPORT_HADITH, reportDetails.getHadith());
        contentValues.put(DatabaseHelper.REPORT_ACADEMICBOOK, reportDetails.getAcademicBook());
        contentValues.put(DatabaseHelper.REPORT_NOVELOROTHER, reportDetails.getNovelOrOtherBook());

        contentValues.put(DatabaseHelper.REPORT_NEWSPAPER, reportDetails.getNewspaper());
        contentValues.put(DatabaseHelper.REPORT_SKILLdEV, reportDetails.getSkillDev());
        contentValues.put(DatabaseHelper.REPORT_MARKETING, reportDetails.getMarketing());
        contentValues.put(DatabaseHelper.REPORT_WAKEUPTIME, reportDetails.getWakeupTime());

        contentValues.put(DatabaseHelper.REPORT_SLEEPTIME, reportDetails.getSleepTime());
        contentValues.put(DatabaseHelper.REPORT_WORKOUT, reportDetails.getWorkout());

        long updatedRow = sqLiteDatabase.update(DatabaseHelper.REPORT_TABLE, contentValues,
                DatabaseHelper.REPORT_ID + " =? ", new String[]{String.valueOf(reportDetails.getId())});
        return updatedRow;
    }

    public ArrayList<ReportDetails> getAllReport() {
        SQLiteDatabase sqLiteDatabase = databaseHelper.getReadableDatabase();
        ReportDetails reportDetails;
        ArrayList<ReportDetails> contacts = new ArrayList<>();
        String selectQuery = "select * from " + DatabaseHelper.REPORT_TABLE;
        Cursor cursor = sqLiteDatabase.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                String id = cursor.getString(cursor.getColumnIndex(DatabaseHelper.REPORT_ID));
                String reportPrayer = cursor.getString(cursor.getColumnIndex(DatabaseHelper.REPORT_PRAYER));
                String reportQuran = cursor.getString(cursor.getColumnIndex(DatabaseHelper.REPORT_QURAN));
                String reportHadith = cursor.getString(cursor.getColumnIndex(DatabaseHelper.REPORT_HADITH));
                String reportAcademic = cursor.getString(cursor.getColumnIndex(DatabaseHelper.REPORT_ACADEMICBOOK));
                String reportNovel = cursor.getString(cursor.getColumnIndex(DatabaseHelper.REPORT_NOVELOROTHER));

                String reportNewspaper = cursor.getString(cursor.getColumnIndex(DatabaseHelper.REPORT_NEWSPAPER));
                String reportSkillDEv = cursor.getString(cursor.getColumnIndex(DatabaseHelper.REPORT_SKILLdEV));
                String reportMarketing = cursor.getString(cursor.getColumnIndex(DatabaseHelper.REPORT_MARKETING));
                String reportWakeUp = cursor.getString(cursor.getColumnIndex(DatabaseHelper.REPORT_WAKEUPTIME));
                String reportSleep = cursor.getString(cursor.getColumnIndex(DatabaseHelper.REPORT_SLEEPTIME));

                String reportWorkOut = cursor.getString(cursor.getColumnIndex(DatabaseHelper.REPORT_WORKOUT));
                reportDetails = new ReportDetails(id, reportPrayer, reportQuran, reportHadith,
                        reportAcademic,reportNovel,reportNewspaper,reportSkillDEv,reportMarketing,reportWakeUp,reportSleep,reportWorkOut);
                contacts.add(reportDetails);
            } while (cursor.moveToNext());
        }
        return contacts;
    }

    //checking here db id exist or not
    public boolean checkIdExistOrNot(int id){
        boolean indicator = false;
        SQLiteDatabase sqLiteDatabase = databaseHelper.getReadableDatabase();
        String selectQuery = "select * from " + DatabaseHelper.REPORT_TABLE
                + " where id = " + id;
        Cursor cursor = sqLiteDatabase.rawQuery(selectQuery, null);

        if(cursor.moveToFirst()){
            indicator = true;
        }
        return  indicator;
    }
}
