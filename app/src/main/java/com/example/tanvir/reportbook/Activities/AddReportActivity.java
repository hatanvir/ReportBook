package com.example.tanvir.reportbook.Activities;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.preference.PreferenceManager;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.tanvir.reportbook.Database.DatabaseManager;
import com.example.tanvir.reportbook.Models.MinMaxFilter;
import com.example.tanvir.reportbook.Models.ReportDetails;
import com.example.tanvir.reportbook.R;
import com.ikovac.timepickerwithseconds.MyTimePickerDialog;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class AddReportActivity extends AppCompatActivity {
    DatabaseManager databaseManager;
    String dateId="";

    EditText prayerQuantity,quranPageQuantityEt,hadithQuantityEt,academicBookReadingHourEt,novelOrOtherBookEt,
            skillDevHourEt,wakeUpTimeEt,sleepTimeEt,workOutEt;
    CheckBox prayerCb,quranCb,hadithCb,academicCb,novelCb,skillCb,workOutCb,newspaperCb,marketingCb;
    ImageView  prayerMinusIm,prayerPlusIm,quranMinusIm,quranPlusIm,hadithminusIm,hadithPlusIm;
    ImageView academicBookTimeSelectorImg,novelTimeSelectorImg,skillTimeSelectorImg,wakeUpTimeSelectorImg,
            sleepTimeSelectorImg,workoutTimeSelectorImg;

    String newspaperStatus ="No";
    String marketingStatus = "No";
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_report);
        databaseManager = new DatabaseManager(this);
        //dateId = new SimpleDateFormat("ddMMyyyy").format(cal.getTime());



        //Toast.makeText(this, "Tvr"+dateId, Toast.LENGTH_SHORT).show();

        viewInitialization();

        //disabling all editText

        disableEditText(prayerQuantity);
        disableEditText(quranPageQuantityEt);
        disableEditText(hadithQuantityEt);
        disableEditText(academicBookReadingHourEt);
        disableEditText(novelOrOtherBookEt);
        disableEditText(skillDevHourEt);
        disableEditText(wakeUpTimeEt);
        disableEditText(sleepTimeEt);
        disableEditText(workOutEt);

        //disabling button
        prayerMinusIm.setClickable(false);
        prayerPlusIm.setClickable(false);

        quranMinusIm.setClickable(false);
        quranPlusIm.setClickable(false);

        hadithminusIm.setClickable(false);
        hadithPlusIm.setClickable(false);

        Intent intent = getIntent();

            try{
                if(intent.getStringExtra("newspaper").equals("Yes")){
                    newspaperCb.setEnabled(true);
                }
            }catch (Exception e){}
            try{
                if(intent.getStringExtra("marketing").equals("Yes")){
                    marketingCb.setEnabled(true);
                }
            }catch (Exception e){}
            try{
                if(!intent.getStringExtra("prayer").equals("0")){
                    prayerQuantity.setText(intent.getStringExtra("prayer"));
                    prayerCb.setEnabled(true);
                }
            }catch (Exception e){}
            try{
                if(!intent.getStringExtra("quran").equals("0")){
                    quranPageQuantityEt.setText(intent.getStringExtra("quran"));
                    quranCb.setEnabled(true);
                }
            }catch (Exception e){}
            try{
                if(!intent.getStringExtra("hadith").equals("0")){
                    hadithQuantityEt.setText(intent.getStringExtra("hadith"));
                   hadithCb.setEnabled(true);
                }
            }catch (Exception e){}
            try{
                if(!intent.getStringExtra("academic").equals("0:0")){
                    academicBookReadingHourEt.setText(intent.getStringExtra("academic"));
                    academicCb.setEnabled(true);
                }
                else{
                    Toast.makeText(this, ""+intent.getStringExtra("academic").equals("0:0"), Toast.LENGTH_SHORT).show();
                }
            }catch (Exception e){}
            try{
                if(!intent.getStringExtra("quran").equals("0")){
                    quranPageQuantityEt.setText(intent.getStringExtra("quran"));
                    quranCb.setEnabled(true);
                }
            }catch (Exception e){}
            try{
                if(!intent.getStringExtra("novel").equals("0:0")){
                    novelOrOtherBookEt.setText(intent.getStringExtra("novel"));
                    novelCb.setEnabled(true);
                }
            }catch (Exception e){}

            try{
                if(!intent.getStringExtra("skill").equals("0:0")){
                    skillDevHourEt.setText(intent.getStringExtra("skill"));
                    skillCb.setEnabled(true);
                }
            }catch (Exception e){}
            try{
                if(!intent.getStringExtra("workout").equals("0:0")){
                    workOutEt.setText(intent.getStringExtra("workout"));
                    workOutCb.setEnabled(true);
                }
            }catch (Exception e){}

            try{
                if(!intent.getStringExtra("wakeup").equals("No")){
                    wakeUpTimeEt.setText(intent.getStringExtra("0wakeup"));
                }
            }catch (Exception e){}

            try{
                if(!intent.getStringExtra("sleep").equals("No")){
                    sleepTimeEt.setText(intent.getStringExtra("sleep"));
                }
            }catch (Exception e){}


        //prayer input
        if(prayerQuantity.getText().toString().length()==0 ){
            prayerQuantity.setText("0");
        }
        try{
            String prayerDur = prayerQuantity.getText().toString();
            if(prayerDur.charAt(0) != '0'){
                prayerCb.setChecked(true);
                prayerCb.setEnabled(true);
            }

            if(prayerCb.isChecked()){
                final int[] count = {Integer.parseInt(intent.getStringExtra("prayer"))};
                Toast.makeText(AddReportActivity.this, "checked", Toast.LENGTH_SHORT).show();
                enaableEditText(prayerQuantity);
                prayerMinusIm.setClickable(true);
                prayerPlusIm.setClickable(true);

                prayerPlusIm.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(count[0]==5){
                            //prayerPlusIm.setEnabled(false);
                            //prayerMinusIm.setEnabled(true);
                            count[0]=5;
                        }else{
                            count[0]++;
                            prayerQuantity.setText(String.valueOf(count[0]));
                        }
                    }
                });
                prayerMinusIm.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(count[0]<=0){
                            //prayerPlusIm.setEnabled(true);
                            count[0]=0;
                        }
                        else{
                            count[0]--;
                            prayerQuantity.setText(String.valueOf(count[0]));
                        }
                    }
                });
            }
            prayerCb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    if(prayerCb.isChecked()){
                        final int[] count = {0};
                        Toast.makeText(AddReportActivity.this, "checked", Toast.LENGTH_SHORT).show();
                        enaableEditText(prayerQuantity);
                        prayerMinusIm.setClickable(true);
                        prayerPlusIm.setClickable(true);

                        prayerPlusIm.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                if(count[0]==5){
                                    //prayerPlusIm.setEnabled(false);
                                    //prayerMinusIm.setEnabled(true);
                                    count[0]=5;
                                }else{
                                    count[0]++;
                                    prayerQuantity.setText(String.valueOf(count[0]));
                                }
                            }
                        });
                        prayerMinusIm.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                if(count[0]<=0){
                                    //prayerPlusIm.setEnabled(true);
                                    count[0]=0;
                                }
                                else{
                                    count[0]--;
                                    prayerQuantity.setText(String.valueOf(count[0]));
                                }
                            }
                        });
                    }
                    else{
                        prayerQuantity.setText("0");
                        prayerMinusIm.setClickable(false);
                        prayerPlusIm.setClickable(false);
                        prayerQuantity.setEnabled(false);
                    }
                }
            });
        }catch (Exception e){

        }
        //quran Input
        if(quranPageQuantityEt.getText().toString().length()==0 ){
            quranPageQuantityEt.setText("0");
        }
        try{
            String quranDur = quranPageQuantityEt.getText().toString();
            if( quranDur.charAt(0) != '0'){

                quranCb.setChecked(true);
            }

            if(quranCb.isChecked()){
                final int[] count = {Integer.parseInt(intent.getStringExtra("quran"))};
                Toast.makeText(AddReportActivity.this, "checked", Toast.LENGTH_SHORT).show();
                enaableEditText(quranPageQuantityEt);
                quranMinusIm.setClickable(true);
                quranPlusIm.setClickable(true);

                quranPlusIm.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        count[0]++;
                        quranPageQuantityEt.setText(String.valueOf(count[0]));

                    }
                });
                quranMinusIm.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(count[0]<=0){
                            //prayerPlusIm.setEnabled(true);
                            count[0]=0;
                        }
                        else{
                            count[0]--;
                            quranPageQuantityEt.setText(String.valueOf(count[0]));
                        }
                    }
                });
            }

            quranCb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @SuppressLint("ResourceAsColor")
                @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    if(quranCb.isChecked()){
                        final int[] count = {0};
                        Toast.makeText(AddReportActivity.this, "checked", Toast.LENGTH_SHORT).show();
                        enaableEditText(quranPageQuantityEt);
                        quranMinusIm.setClickable(true);
                        quranPlusIm.setClickable(true);

                        quranPlusIm.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                                count[0]++;
                                quranPageQuantityEt.setText(String.valueOf(count[0]));

                            }
                        });
                        quranMinusIm.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                if(count[0]<=0){
                                    //prayerPlusIm.setEnabled(true);
                                    count[0]=0;
                                }
                                else{
                                    count[0]--;
                                    quranPageQuantityEt.setText(String.valueOf(count[0]));
                                }
                            }
                        });
                    }
                    else{
                        quranPageQuantityEt.setText("0");
                        quranMinusIm.setClickable(false);
                        quranPlusIm.setClickable(false);
                        quranPageQuantityEt.setEnabled(false);

                        //quranPlusIm.setBackground(Color.DKGRAY);
                    }
                }
            });
        }catch (Exception e){}

        //hadith input
        if(hadithQuantityEt.getText().toString().length()==0 ){
            hadithQuantityEt.setText("0");
        }
        try{
            String haditQuantity = hadithQuantityEt.getText().toString();
            if( haditQuantity.charAt(0) != '0'){

                quranCb.setChecked(true);
            }

            if(hadithCb.isChecked()){
                final int[] count = {Integer.valueOf(intent.getStringExtra("hadith"))};
                Toast.makeText(AddReportActivity.this, "checked", Toast.LENGTH_SHORT).show();
                enaableEditText(hadithQuantityEt);
                hadithminusIm.setClickable(true);
                hadithPlusIm.setClickable(true);

                hadithPlusIm.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        count[0]++;
                        hadithQuantityEt.setText(String.valueOf(count[0]));

                    }
                });
                hadithminusIm.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(count[0]<=0){
                            //prayerPlusIm.setEnabled(true);
                            count[0]=0;
                        }
                        else{
                            count[0]--;
                            hadithQuantityEt.setText(String.valueOf(count[0]));
                        }
                    }
                });
            }

            hadithCb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    if(hadithCb.isChecked()){
                        final int[] count = {0};
                        Toast.makeText(AddReportActivity.this, "checked", Toast.LENGTH_SHORT).show();
                        enaableEditText(hadithQuantityEt);
                        hadithminusIm.setClickable(true);
                        hadithPlusIm.setClickable(true);

                        hadithPlusIm.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                                count[0]++;
                                hadithQuantityEt.setText(String.valueOf(count[0]));

                            }
                        });
                        hadithminusIm.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                if(count[0]<=0){
                                    //prayerPlusIm.setEnabled(true);
                                    count[0]=0;
                                }
                                else{
                                    count[0]--;
                                    hadithQuantityEt.setText(String.valueOf(count[0]));
                                }
                            }
                        });
                    }
                    else{
                        hadithQuantityEt.setText("0");
                        hadithminusIm.setClickable(false);
                        hadithPlusIm.setClickable(false);
                        hadithQuantityEt.setEnabled(false);
                    }
                }
            });
        }catch (Exception e){}


        //academic book input
        if (academicBookReadingHourEt.getText().toString().length()==0){
            academicBookReadingHourEt.setText("0h:0m");
        }
        try{
            String academicDur = academicBookReadingHourEt.getText().toString();
            if(academicDur.charAt(0) != '0' && academicDur.charAt(3) != 0){
                academicCb.setChecked(true);
                academicCb.setEnabled(true);
            }

            if(academicCb.isChecked()){
                academicBookTimeSelectorImg.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        customDialog(academicBookReadingHourEt);
                    }
                });
            }
            academicCb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    academicBookReadingHourEt.setEnabled(true);
                    academicBookTimeSelectorImg.setEnabled(true);
                    if (academicCb.isChecked()){
                        academicBookTimeSelectorImg.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Toast.makeText(AddReportActivity.this, "Clicked", Toast.LENGTH_SHORT).show();
                                customDialog(academicBookReadingHourEt);
                            }
                        });

                    }
                    else{
                        academicBookReadingHourEt.setText("0h:0m");
                        academicBookTimeSelectorImg.setEnabled(false);
                        academicBookReadingHourEt.setEnabled(false);
                    }
                }
            });
        }catch (Exception e){}


        //novel book input
        if(novelOrOtherBookEt.getText().toString().length()==0 ) {
            novelOrOtherBookEt.setText("0h:0m");
        }
        try{
            String novelDur = novelOrOtherBookEt.getText().toString();
            if(novelDur.charAt(0) != '0' && novelDur.charAt(3) != '0'){
                novelCb.setChecked(true);
                novelCb.setChecked(true);
            }

            if (novelCb.isChecked()){
                //picking time here
                novelTimeSelectorImg.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        customDialog(novelOrOtherBookEt);
                    }
                });
            }
            novelCb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    novelOrOtherBookEt.setEnabled(true);
                    novelTimeSelectorImg.setEnabled(true);
                    if (novelCb.isChecked()){
                        novelTimeSelectorImg.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                customDialog(novelOrOtherBookEt);
                            }
                        });

                    }
                    else{
                        novelOrOtherBookEt.setEnabled(false);
                        novelTimeSelectorImg.setEnabled(false);
                        novelOrOtherBookEt.setText("0h:0m");
                    }

                }
            });
        }catch (Exception e){}

        //skill development input
        if(skillDevHourEt.getText().toString().length()==0 ){
            skillDevHourEt.setText("0h:0m");
        }
        try{
            String skillDur = skillDevHourEt.getText().toString();
            if(skillDur.charAt(0) != '0' && skillDur.charAt(3) != 0){
                skillCb.setChecked(true);
                skillCb.setEnabled(true);
            }

            if (skillCb.isChecked()){
                //picking time here
                skillTimeSelectorImg.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                       customDialog(skillDevHourEt);

                    }
                });
            }

            skillCb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    skillDevHourEt.setEnabled(true);
                    skillTimeSelectorImg.setEnabled(true);
                    if (skillCb.isChecked()){
                        skillTimeSelectorImg.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                customDialog(skillDevHourEt);
                            }
                        });
                    }
                    else{
                        skillDevHourEt.setEnabled(false);
                        skillTimeSelectorImg.setEnabled(false);
                        skillDevHourEt.setText("0h:0m");
                    }

                }
            });
        }catch (Exception e){}


        //newsPaper input
        try{
            if(intent.getStringExtra("newspaper").equals("Yes")){
                newspaperCb.setChecked(true);
                newspaperCb.setEnabled(true);
                newspaperStatus = "Yes";
            }
            if(newspaperCb.isChecked()){
                newspaperStatus = "Yes";
            }
            newspaperCb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    if(newspaperCb.isChecked()){
                        newspaperStatus = "Yes";
                    }
                    else{
                        newspaperStatus = "No";
                    }
                }
            });
        }catch (Exception e){}


        //marketing input
        try{
            if(intent.getStringExtra("marketing").equals("Yes")){
                marketingCb.setChecked(true);
                marketingCb.setEnabled(true);
                marketingStatus = "Yes";

            }

            if(marketingCb.isChecked()){
                marketingStatus = "Yes";
            }

            marketingCb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    if(marketingCb.isChecked()){
                        marketingStatus = "Yes";
                    }
                    else{
                        marketingStatus = "No";
                    }
                }
            });
        }catch (Exception e){}

        //wake up time input
        if(wakeUpTimeEt.getText().toString().length()==0){
            wakeUpTimeEt.setText("0:0");
        }
        try{
                //picking time here
                        wakeUpTimeSelectorImg.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Calendar mcurrentTime = Calendar.getInstance();
                                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                                int minute = mcurrentTime.get(Calendar.MINUTE);
                                int formate = mcurrentTime.get(Calendar.AM_PM);
                                TimePickerDialog mTimePicker;
                                mTimePicker = new TimePickerDialog(AddReportActivity.this, new TimePickerDialog.OnTimeSetListener() {
                                    @Override
                                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                                        wakeUpTimeEt.setText( selectedHour + ":" + selectedMinute+" "+formate);
                                    }
                                }, hour, minute, true);//Yes 24 hour time
                                mTimePicker.setTitle("Select Time");
                                mTimePicker.show();

                            }
                        });

        }catch (Exception e){}

        //sleep time input

        if(sleepTimeEt.getText().toString().length()==0){
            sleepTimeEt.setText("0:0");
        }
        try{
            //picking time here
            sleepTimeSelectorImg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Calendar mcurrentTime = Calendar.getInstance();
                    int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                    int minute = mcurrentTime.get(Calendar.MINUTE);
                    int formate = mcurrentTime.get(Calendar.AM);
                    TimePickerDialog mTimePicker;
                    mTimePicker = new TimePickerDialog(AddReportActivity.this, new TimePickerDialog.OnTimeSetListener() {
                        @Override
                        public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {

                            sleepTimeEt.setText( selectedHour +" "+ selectedMinute+" ");
                        }
                    }, hour, minute, true);//Yes 24 hour time
                    mTimePicker.setTitle("Select Time");
                    mTimePicker.show();

                }
            });

        }catch (Exception e){}

        //workOut input

        if(workOutEt.getText().toString().length()==0 ){
            workOutEt.setText("0h:0m");
        }
        try{
            String workOutDur = workOutEt.getText().toString();
            if(workOutDur.charAt(0) != '0' && workOutDur.charAt(3) != '0'){
                workOutCb.setChecked(true);
                workOutCb.setEnabled(true);
            }

            if (workOutCb.isChecked()){
                //picking time here
                workoutTimeSelectorImg.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                       customDialog(workOutEt);

                    }
                });
            }

            workOutCb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    workOutEt.setEnabled(true);
                    workoutTimeSelectorImg.setEnabled(true);

                    if(workOutCb.isChecked()){
                        workoutTimeSelectorImg.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                customDialog(workOutEt);
                            }
                        });
                    }else{
                        workOutEt.setText("0h:0m");
                        workoutTimeSelectorImg.setEnabled(false);
                        workOutEt.setEnabled(false);
                    }
                }
            });
        }catch (Exception e){}


    }

    public void save(View view) {
        Calendar cal = Calendar.getInstance();

        //generating day,month year from calender
        String currentDay = new SimpleDateFormat("dd").format(cal.getTime());
        String currentMOnth = new SimpleDateFormat("MM").format(cal.getTime());
        String currentYear = new SimpleDateFormat("yyyy").format(cal.getTime());

        if(currentMOnth.length()==1){
            dateId = currentDay+"0"+String.valueOf(Integer.parseInt(currentMOnth)-1)+currentYear;//adding 0 for indicating single character month
        }else{
            dateId = currentDay+String.valueOf(Integer.parseInt(currentMOnth)-1)+currentYear;
        }
        Toast.makeText(this, ""+dateId, Toast.LENGTH_SHORT).show();

        String prayerTxt = prayerQuantity.getText().toString();
        String quranTxt = quranPageQuantityEt.getText().toString();
        String hadithTxt = hadithQuantityEt.getText().toString();
        String academicTxt = academicBookReadingHourEt.getText().toString();
        String novelTxt = novelOrOtherBookEt.getText().toString();
        String skillTxt = skillDevHourEt.getText().toString();
        String wakeupTxt = wakeUpTimeEt.getText().toString();
        String sleepTxt = sleepTimeEt.getText().toString();
        String workOutTxt = workOutEt.getText().toString();

        //Toast.makeText(this, "Tanvir"+dateId, Toast.LENGTH_SHORT).show();
        ReportDetails reportDetails = new ReportDetails(dateId,prayerTxt,quranTxt,hadithTxt,academicTxt,
                novelTxt,newspaperStatus,skillTxt,marketingStatus,wakeupTxt,sleepTxt,workOutTxt);


        long l = databaseManager.addReportInfo(reportDetails);

        if(l>0){
            Toast.makeText(this, "Successfully inserted", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(AddReportActivity.this,MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            finish();
            startActivity(intent);
        }
        else{
            //Toast.makeText(this, "Unsucc  "+dateId, Toast.LENGTH_SHORT).show();
            long ll = databaseManager.updateReportInfo(reportDetails);
            if(ll>0){
                Toast.makeText(this, "Successfully updatd", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(AddReportActivity.this,MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                finish();
                startActivity(intent);
            }
        }
    }




    public void viewInitialization(){
        //imageview
        prayerMinusIm = findViewById(R.id.minusImgOnPrayer);
        prayerPlusIm = findViewById(R.id.plusImgOnPrayer);

        quranMinusIm = findViewById(R.id.minusImgOnQuran);
        quranPlusIm = findViewById(R.id.plusImgOnQuran);

        hadithminusIm = findViewById(R.id.minusImgOnhadith);
        hadithPlusIm = findViewById(R.id.plusImgOnhadith);

        academicBookTimeSelectorImg = findViewById(R.id.academicBookTimeSelectorImg);
        novelTimeSelectorImg = findViewById(R.id.novelTimeSelectorImg);
        skillTimeSelectorImg = findViewById(R.id.skillDevTimeSelectorImg);
        wakeUpTimeSelectorImg = findViewById(R.id.wakeUpTimeSelectorImg);
        sleepTimeSelectorImg = findViewById(R.id.sleepTimeSelectorImg);
        workoutTimeSelectorImg = findViewById(R.id.workOutTimeSelectorImg);

        //checkbox
        prayerCb = findViewById(R.id.prayerCheckBox);
        quranCb = findViewById(R.id.quranCheckBox);
        hadithCb = findViewById(R.id.hadithCheckBox);
        academicCb = findViewById(R.id.academicCheckBox);
        novelCb = findViewById(R.id.novelCheckBox);
        skillCb = findViewById(R.id.skillCheckBox);
        newspaperCb = findViewById(R.id.newspaperCheckBox);
        marketingCb = findViewById(R.id.marketingCheckBox);
        workOutCb = findViewById(R.id.workOutCheckBox);

        //editText
        prayerQuantity = findViewById(R.id.quantityEtOnPrayer);
        quranPageQuantityEt = findViewById(R.id.quantityEtOnQuran);
        hadithQuantityEt = findViewById(R.id.quantityEtOnhadith);
        academicBookReadingHourEt = findViewById(R.id.timeEtOnAcademic);
        novelOrOtherBookEt = findViewById(R.id.timeEtOnNovel);
        skillDevHourEt = findViewById(R.id.timeEtOnSkill);
        wakeUpTimeEt = findViewById(R.id.wakeupTimeEt);
        sleepTimeEt = findViewById(R.id.sleepEt);
        workOutEt = findViewById(R.id.workOutEt);
    }

    public void disableEditText(EditText editText){
        editText.setEnabled(false);
        editText.setFocusable(false);
        //editText.setKeyListener(null);
        //editText.setBackgroundColor(Color.TRANSPARENT);
    }
    public void enaableEditText(EditText editText){
        editText.setEnabled(true);
        editText.setInputType(InputType.TYPE_CLASS_TEXT);
        editText.setFocusable(false);
        //editText.setKeyListener(true);
        //editText.setBackgroundColor(Color.TRANSPARENT);
    }
    public void customDialog(final EditText mainEditText){

        //custom dialog for taking hours and minuit from user


        final Dialog dialog = new Dialog(AddReportActivity.this);
        dialog.setContentView(R.layout.time_picker_dialog);

        final Button hourPlusBt = dialog.findViewById(R.id.hourPlusBt);
        final Button hourMinusBt = dialog.findViewById(R.id.hourMinusBt);
        final Button minuitPlusBt = dialog.findViewById(R.id.minuitPlusBt);
        final Button minuitMinusBt = dialog.findViewById(R.id.minuitMinusBt);
        final Button setId = dialog.findViewById(R.id.setId);
        final Button cancelId = dialog.findViewById(R.id.cancelId);

        final EditText hourEt = dialog.findViewById(R.id.hourEt);
        final EditText minuitEt = dialog.findViewById(R.id.minuitEt);

        hourEt.setFilters(new InputFilter[]{
                new MinMaxFilter(0,24)
        });

        minuitEt.setFilters(new InputFilter[]{
                new MinMaxFilter(0,59)
        });

        String editTextText = mainEditText.getText().toString();
        String countHourTxt="0",countMinuitTxt="0";

        if(editTextText.length()==3 && editTextText.charAt(0) != '0' && editTextText.charAt(2) != 0) {
            countHourTxt = String.valueOf(editTextText.charAt(0));
            countMinuitTxt = String.valueOf(editTextText.charAt(2));
            academicCb.setChecked(true);
            academicCb.setEnabled(true);
        }
        else if(editTextText.length()==4 && editTextText.charAt(1)==':'){
            countHourTxt = String.valueOf(editTextText.charAt(0));
            countMinuitTxt = String.valueOf(editTextText.charAt(2)+editTextText.charAt(3));
            academicCb.setChecked(true);
            academicCb.setEnabled(true);
        }
        else if(editTextText.length()==4 && editTextText.charAt(2)==':'){
            countHourTxt = String.valueOf(editTextText.charAt(0)+editTextText.charAt(1));
            countMinuitTxt = String.valueOf(editTextText.charAt(3));
            academicCb.setChecked(true);
            academicCb.setEnabled(true);
        }
        else if(editTextText.length()==5){
            countHourTxt = String.valueOf(editTextText.charAt(0)+editTextText.charAt(1));
            countMinuitTxt = String.valueOf(editTextText.charAt(3)+editTextText.charAt(4));
            academicCb.setChecked(true);
            academicCb.setEnabled(true);
        }
        final String finalCountHourTxt = countHourTxt;
        final String finalCountMinuitTxt2 = countMinuitTxt;
        //if(timrDur.charAt(0)=='0' && )
        final int[] countHour = {Integer.parseInt(finalCountHourTxt)},countMinuit = {Integer.parseInt(finalCountMinuitTxt2)};
        hourEt.setText(""+Integer.parseInt(countHourTxt));
        minuitEt.setText(""+Integer.parseInt(countMinuitTxt));
       // final int countHour = 0;
        hourPlusBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                countHour[0]++;
                hourEt.setText(String.valueOf(countHour[0]));
            }
        });
        hourMinusBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                countHour[0]--;
                hourEt.setText(String.valueOf(countHour[0]));
            }
        });

        minuitPlusBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                countMinuit[0]++;
                minuitEt.setText(String.valueOf(countMinuit[0]));
            }
        });
        minuitMinusBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                countMinuit[Integer.parseInt(finalCountMinuitTxt2)]--;
                minuitEt.setText(String.valueOf(countMinuit[0]));
            }
        });

        setId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(hourEt.getText().toString().length()==0){
                    hourEt.setText("0");
                }
                else if(minuitEt.getText().toString().length()==0){
                    minuitEt.setText("0");
                }
                mainEditText.setText(hourEt.getText().toString()+"h"+":"+minuitEt.getText().toString()+"m");
                dialog.dismiss();
            }
        });
        cancelId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }
}
