package com.example.tanvir.reportbook.Fragments;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tanvir.reportbook.Activities.AddReportActivity;
import com.example.tanvir.reportbook.Activities.MainActivity;
import com.example.tanvir.reportbook.Database.DatabaseManager;
import com.example.tanvir.reportbook.Models.ReportDetails;
import com.example.tanvir.reportbook.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class TodayFragment extends Fragment {
    TextView prayerCountTv,quranTv,hadithTv,academicTv,novelTv,newspaperTv,skillTv,marketingTv,wakeUpTv,sleepTv,workOutTv;
    TextView dateTv;
    DatabaseManager databaseManager;
    String customDate;
    TodayFragment todayFragment;
    String currentDate;
    public String[] monthName = {"January","February","March","April","May","June","July","August","September","October","November","December"};
    public interface OnFragmentInteractionListener{

    }
    public TodayFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        databaseManager = new DatabaseManager(getActivity());
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_todays, container, false);

        viewInitialization(view);

        //current time picking from calender
        Calendar cal = Calendar.getInstance();
        String currentDay = new SimpleDateFormat("dd").format(cal.getTime());
        String currentMOnth = new SimpleDateFormat("MM").format(cal.getTime());
        String currentYear = new SimpleDateFormat("yyyy").format(cal.getTime());
        String dateId = currentDay+String.valueOf(Integer.parseInt(currentMOnth)-1)+currentYear;

        currentDate = currentDay+" "+monthName[Integer.parseInt(currentMOnth)-1]+" "+currentYear;

        dateTv.setText(currentDate);
        //ArrayList<ReportDetails> arrayList = new ArrayList<>();
        final ReportDetails reportDetails  = databaseManager.getREportById(Integer.parseInt(dateId));
        try{
            //text view formation
            if(reportDetails.getPrayer().equals("1") || reportDetails.getPrayer().equals("0")){
                prayerCountTv.setText(reportDetails.getPrayer()+" time");
            }else {
                prayerCountTv.setText(reportDetails.getPrayer()+" times");
            }
            //prayerCountTv.setText(reportDetails.getPrayer()+" times");
            if(reportDetails.getQuran().equals("1") || reportDetails.getQuran().equals("0")){
                quranTv.setText(reportDetails.getQuran()+" page");
            }else {
                quranTv.setText(reportDetails.getQuran()+" pages");
            }

            if(reportDetails.getHadith().equals("1") || reportDetails.getHadith().equals("0")){
                hadithTv.setText(reportDetails.getHadith());
            }else {
                hadithTv.setText(reportDetails.getHadith());
            }

            academicTv.setText(reportDetails.getAcademicBook());
            novelTv.setText(reportDetails.getNovelOrOtherBook());
            newspaperTv.setText(reportDetails.getNewspaper());
            skillTv.setText(reportDetails.getSkillDev());
            marketingTv.setText(reportDetails.getMarketing());
            wakeUpTv.setText(reportDetails.getWakeupTime());
            sleepTv.setText(reportDetails.getSleepTime());
            workOutTv.setText(reportDetails.getWorkout());
        }catch (Exception e){

        }
        FloatingActionButton fab = view.findViewById(R.id.fab);
        if(currentDate.equals(currentDate)){
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getActivity(),AddReportActivity.class);
                    try{
                        intent.putExtra("prayer",reportDetails.getPrayer());
                        intent.putExtra("quran",reportDetails.getQuran());
                        intent.putExtra("hadith",reportDetails.getHadith());
                        intent.putExtra("academic",reportDetails.getAcademicBook());
                        intent.putExtra("novel",reportDetails.getNovelOrOtherBook());
                        intent.putExtra("newspaper",reportDetails.getNewspaper());
                        intent.putExtra("skill",reportDetails.getSkillDev());
                        intent.putExtra("marketing",reportDetails.getMarketing());
                        intent.putExtra("wakeup",reportDetails.getWakeupTime());
                        intent.putExtra("sleep",reportDetails.getSleepTime());
                        intent.putExtra("workout",reportDetails.getWorkout());
                       // Toast.makeText(getContext(), ""+reportDetails.getWorkout(), Toast.LENGTH_SHORT).show();
                        intent.putExtra("currentDate",currentDate);
                        startActivity(intent);
                    }catch (Exception e){
                        startActivity(intent);
                    }
                }
            });
        }
        fragmentLoading();

        return view;
    }

    public Fragment fragmentLoading (){
        //layout click listener
        final boolean[] dateFindOrNotIndicator = {false};
        dateTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar calendar = Calendar.getInstance();
                //.set(2018,10,29);
                int day = calendar.get(Calendar.DAY_OF_MONTH);
                final int month = calendar.get(Calendar.MONTH);
                int year = calendar.get(Calendar.YEAR);
                DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                        String dbId;

                        if (String.valueOf(i1+1).length()==1){
                            dbId = String.valueOf(i2)+"0"+String.valueOf(i1)+String.valueOf(i);
                        }else {
                            dbId = String.valueOf(i2)+String.valueOf(i1)+String.valueOf(i);
                        }
                        Toast.makeText(getActivity(), ""+dbId, Toast.LENGTH_SHORT).show();

                        if(databaseManager.checkIdExistOrNot(Integer.parseInt(dbId))==true){
                            //Toast.makeText(getActivity(), "Db found"+dbId, Toast.LENGTH_SHORT).show();
                            ReportDetails reportDetails1 = databaseManager.getREportById(Integer.parseInt(dbId));
                            prayerCountTv.setText(reportDetails1.getPrayer()+" times");
                            quranTv.setText(reportDetails1.getQuran());
                            hadithTv.setText(reportDetails1.getHadith());
                            academicTv.setText(reportDetails1.getAcademicBook());
                            novelTv.setText(reportDetails1.getNovelOrOtherBook());
                            newspaperTv.setText(reportDetails1.getNewspaper());
                            skillTv.setText(reportDetails1.getSkillDev());
                            marketingTv.setText(reportDetails1.getMarketing());
                            wakeUpTv.setText(reportDetails1.getWakeupTime());
                            sleepTv.setText(reportDetails1.getSleepTime());
                            workOutTv.setText(reportDetails1.getWorkout());
                            Toast.makeText(getActivity(), "Yes", Toast.LENGTH_SHORT).show();
                            customDate = String.valueOf(i2)+" "+monthName[i1]+" "+String.valueOf(i);
                            dateTv.setText(currentDate);
                            //todayFragment = new TodayFragment();
                            dateFindOrNotIndicator[0] = true;

                        }else {
                            Toast.makeText(getActivity(), "You don't have enter any report in this date. "+dbId, Toast.LENGTH_SHORT).show();
                        }
                    }
                }, year, month, day);
                datePickerDialog.show();

            }
        });
        if (dateFindOrNotIndicator[0]==true){
            TodayFragment todayFragment = new TodayFragment();
            return todayFragment;
        }else {
            return null;
        }
    }

    public void viewInitialization(View view){

        prayerCountTv = view.findViewById(R.id.prayerCount);
        quranTv = view.findViewById(R.id.quranRecDurTv);
        hadithTv = view.findViewById(R.id.hadithReadDurTv);
        academicTv = view.findViewById(R.id.academicReadDurTv);
        novelTv = view.findViewById(R.id.novelReadDurTv);
        newspaperTv = view.findViewById(R.id.newspaperStatusTv);
        skillTv = view.findViewById(R.id.skillDevDurTv);
        marketingTv = view.findViewById(R.id.marketingSatusTv);
        wakeUpTv = view.findViewById(R.id.wakeUpTimeTv);
        sleepTv = view.findViewById(R.id.sleepTimeTv);
        workOutTv = view.findViewById(R.id.workOutDurTv);

        dateTv = view.findViewById(R.id.linearLayoutBt);
    }
}
