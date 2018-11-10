package com.example.tanvir.reportbook.Activities;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Environment;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CalendarView;
import android.widget.Toast;

import com.applandeo.materialcalendarview.EventDay;
import com.applandeo.materialcalendarview.exceptions.OutOfDateRangeException;
import com.applandeo.materialcalendarview.listeners.OnDayClickListener;
import com.example.tanvir.reportbook.Database.DatabaseManager;
import com.example.tanvir.reportbook.Models.ReportDetails;
import com.example.tanvir.reportbook.R;
import com.itextpdf.text.Anchor;
import com.itextpdf.text.BadElementException;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chapter;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.ListItem;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Section;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class CalenderActivity extends AppCompatActivity {
    DatabaseManager databaseManager;
    com.applandeo.materialcalendarview.CalendarView calendarView;
    FloatingActionButton fab;
    String dateFromCalenderActivity,year,month;
    File myFile;

    //font customization
    private static Font catFont = new Font(Font.FontFamily.TIMES_ROMAN, 13, Font.BOLD);
    private static Font redFont = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.NORMAL);
    private static Font subFont = new Font(Font.FontFamily.TIMES_ROMAN, 16, Font.BOLD);
    private static Font smallBold = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD);

    // Storage Permissions
    private static final int REQUEST_EXTERNAL_STORAGE = 1;

    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calender);

        databaseManager=new DatabaseManager(this);
        calendarView = findViewById(R.id.calenderId);
        fab = findViewById(R.id.calenderFab);

        Calendar calendar = Calendar.getInstance();

        dateFromCalenderActivity = getIntent().getStringExtra("date");
        year = "";
        month = "";
        int m = 0;
        //date separation
        for (int j=0;j<dateFromCalenderActivity.length();j++){
            if (dateFromCalenderActivity.charAt(j) != ' '){
                month+=dateFromCalenderActivity.charAt(j);
            }else {
                m = j+1;
                break;
            }
            //month+=date.charAt(j);
        }
        //year separation from sting
        for (;m<dateFromCalenderActivity.length();m++){
            year+=dateFromCalenderActivity.charAt(m);
        }
        calendar.set(Integer.parseInt(year),monthToInt(month),1);//setting date to calender

        try {
            calendarView.setDate(calendar);//setting date to calender view
           // Toast.makeText(this, ""+Integer.parseInt(year)+" "+monthToInt(month), Toast.LENGTH_SHORT).show();
        } catch (OutOfDateRangeException e) {
            e.printStackTrace();
        }

        settingIconOnDate(year,month);
        String finalMonth = month;
        String finalYear = year;
        calendarView.setOnDayClickListener(new OnDayClickListener() {
            @Override
            public void onDayClick(EventDay eventDay) {
                //Calendar calendar1 = eventDay.getCalendar();
                //calendarView.setEvents((List<EventDay>) eventDay.getCalendar());
                String date = String.valueOf(eventDay.getCalendar().get(Calendar.DAY_OF_MONTH))+ monthToInt(finalMonth) + finalYear;
                if(databaseManager.checkIdExistOrNot(Integer.parseInt(date))){
                    ReportDetails reportDetails = databaseManager.getREportById(Integer.parseInt(date));

                    Toast.makeText(CalenderActivity.this, ""+reportDetails.getPrayer()+" "+reportDetails.getHadith(), Toast.LENGTH_SHORT).show();
                }
                //Toast.makeText(CalenderActivity.this, ""+date, Toast.LENGTH_SHORT).show();
            }
        });

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                verifyStoragePermissions(CalenderActivity.this);
                openPdf();
            }
        });
    }

    /**
     * Checks if the app has permission to write to device storage
     *
     * If the app does not has permission then the user will be prompted to grant permissions
     *
     * @param activity
     */
    public  void verifyStoragePermissions(Activity activity) {
        // Check if we have write permission
        if (ContextCompat.checkSelfPermission(activity,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(activity,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)) {

                // Show an expanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(activity, PERMISSIONS_STORAGE, REQUEST_EXTERNAL_STORAGE);

            }


        } else {
            createPDF();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case REQUEST_EXTERNAL_STORAGE: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                    createPDF();

                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.

                    Toast.makeText(CalenderActivity.this, "No podemos escribir sin tener permiso", Toast.LENGTH_SHORT).show();
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }

    //creating pdf here
    public  void createPDF(){
        //create document object
        com.itextpdf.text.Document document = new com.itextpdf.text.Document();

        //output file path
        //String outpath= Environment.getExternalStorageDirectory()+"PDF1.pdf";
        Date date = new Date() ;
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(date);
        myFile = new File(Environment.getExternalStorageDirectory()+ File.separator +"Report book's report on "+dateFromCalenderActivity + ".pdf");
        try {
            myFile.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        OutputStream output = null;
        try {
            output = new FileOutputStream(myFile);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        try {
            PdfWriter.getInstance(document, output);
            document.open();
            //addMetaData(document);
            //addTitlePage(document);
            addContent(document);
            document.close();

        } catch (DocumentException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }


    /*private static void addTitlePage(com.itextpdf.text.Document document)
            throws DocumentException {
        Paragraph preface = new Paragraph();
        // We add one empty line
        addEmptyLine(preface, 1);
        // Lets write a big header
        preface.add(new Paragraph("Title of the document", catFont));

        addEmptyLine(preface, 1);
        // Will create: Report generated by: _name, _date
        preface.add(new Paragraph("Report generated by: " + System.getProperty("user.name") + ", " + new Date(), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
                smallBold));
        addEmptyLine(preface, 3);
        preface.add(new Paragraph("This document describes something which is very important ",
                smallBold));

        addEmptyLine(preface, 8);

        preface.add(new Paragraph("This document is a preliminary version and not subject to your license agreement or any other agreement with vogella.com ;-).",
                redFont));

        document.add(preface);
        // Start a new page
        document.newPage();
    }*/

    private void addContent(com.itextpdf.text.Document document) throws DocumentException {
        ArrayList<Integer> idList = new ArrayList<Integer>();
        //Document document1 = new Document();
        Date date = new Date();
        String timeStamp = new SimpleDateFormat("dd-MM-yyyy  HH:mm:ss", Locale.getDefault()).format(date);
        //titel of pdf
        Paragraph preface = new Paragraph();

        preface.add(new Paragraph("Monthly report", catFont));//adding title here
        preface.add(new Paragraph(""+timeStamp, redFont));
        addEmptyLine(preface,1);//empty line after date
        PdfPTable table = new PdfPTable(new float[] { 6, 3, 3 ,3,6,6,3,3,3,6,6,6});

        //table header attributes
        table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell("Date");
        table.addCell("Prayer");
        table.addCell("Quran rec:");
        table.addCell("Hadith rec");
        table.addCell("Academic book");
        table.addCell("Novel or other");
        table.addCell("Newspaper");
        table.addCell("Skill dev");
        table.addCell("Marketing");
        table.addCell("Wake up");
        table.addCell("Sleep");
        table.addCell("Workout");

        table.setHeaderRows(1);
        PdfPCell[] cells = table.getRow(0).getCells();
        for (int j=0;j<cells.length;j++){
            cells[j].setBackgroundColor(BaseColor.GRAY);
        }
        int countId = 0;
        //counting available date on database
        for(int i=1;i<=31;i++){
            //database
            String id =String.valueOf(i)+String.valueOf(monthToInt(month))+year;
            if(databaseManager.checkIdExistOrNot(Integer.parseInt(id))){
                //Toast.makeText(this, ""+id, Toast.LENGTH_SHORT).show();
                //Toast.makeText(this, ""+id, Toast.LENGTH_SHORT).show();
                idList.add(Integer.parseInt(id));
                countId++;
            }
        }
        //getting id's values from database
        for (int i=0;i<countId;i++){
            ReportDetails reportDetails = databaseManager.getREportById(idList.get(i));
            table.addCell(reportDetails.getId());
            table.addCell(reportDetails.getPrayer());
            table.addCell(reportDetails.getQuran());
            table.addCell(reportDetails.getHadith());
            table.addCell(reportDetails.getAcademicBook());
            table.addCell(reportDetails.getNovelOrOtherBook());
            table.addCell(reportDetails.getNewspaper());
            table.addCell(reportDetails.getSkillDev());
            table.addCell(reportDetails.getMarketing());
            table.addCell(reportDetails.getWakeupTime());
            table.addCell(reportDetails.getSleepTime());
            table.addCell(reportDetails.getWorkout());
        }
        //table component
        /*for (int i=1;i<5;i++){
            table.addCell("Name:"+i);
            table.addCell("Age:"+i);
            table.addCell("Location:"+i);
        }*/
        //PdfWriter.getInstance(document, new FileOutputStream("sample3.pdf"));
        document.open();
        document.add(preface);
        document.add(table);
        document.close();
       // System.out.println("Done");

    }

    private static void createTable(Section subCatPart)
            throws BadElementException {
        PdfPTable table = new PdfPTable(3);

        // t.setBorderColor(BaseColor.GRAY);
        // t.setPadding(4);
        // t.setSpacing(4);
        // t.setBorderWidth(1);

        PdfPCell c1 = new PdfPCell(new Phrase("Table Header 1"));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(c1);

        c1 = new PdfPCell(new Phrase("Table Header 2"));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(c1);

        c1 = new PdfPCell(new Phrase("Table Header 3"));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(c1);
        table.setHeaderRows(1);

        table.addCell("1.0");
        table.addCell("1.1");
        table.addCell("1.2");

        table.addCell("2.1");
        table.addCell("2.2");
        table.addCell("2.3");

        table.addCell("1.0");
        table.addCell("1.1");
        table.addCell("1.2");

        table.addCell("2.1");
        table.addCell("2.2");
        table.addCell("tanvir");

        // subCatPart.add(table);

    }

    private static void createList(Section subCatPart) {
        com.itextpdf.text.List list = new com.itextpdf.text.List(true, false, 10);
        list.add(new ListItem("First point"));
        list.add(new ListItem("Second point"));
        list.add(new ListItem("Third point"));
        subCatPart.add(list);
    }

    private static void addEmptyLine(Paragraph paragraph, int number) {
        for (int i = 0; i < number; i++) {
            paragraph.add(new Paragraph(" "));
        }
    }


    public int monthToInt(String month){
        String[] monthName = {"January","February","March","April","May","June","July","August","September","October","November","December"};
        for (int i=0;i<monthName.length;i++){
            if (month.equals(monthName[i])){
                return i;
            }
        }
        return 0;
    }

    public void settingIconOnDate(String y,String m){

        ArrayList<EventDay> eventDays = new ArrayList<>();
        ArrayList<Integer> dateList = new ArrayList<>();
        String date;
        for (int i=1;i<=31;i++){
            date = String.valueOf(i)+String.valueOf(monthToInt(m))+y;
            //Toast.makeText(this, ""+date, Toast.LENGTH_SHORT).show();
            if(databaseManager.checkIdExistOrNot(Integer.parseInt(date))){
                //Toast.makeText(this, "Yes monthIdgeneration", Toast.LENGTH_SHORT).show();
                Calendar calendar = Calendar.getInstance();
                calendar.set(Integer.parseInt(y),monthToInt(m),i);
                eventDays.add(new EventDay(calendar,R.drawable.academicbook));
                calendarView.setEvents(eventDays);
            }
        }
    }

    public void openPdf(){
        //File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath()+"/example.pdf");
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(Uri.fromFile(myFile), "application/pdf");
        intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        startActivity(intent);
    }

}
