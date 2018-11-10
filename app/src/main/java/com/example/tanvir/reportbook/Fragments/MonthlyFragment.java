package com.example.tanvir.reportbook.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.tanvir.reportbook.Adapters.RecyclerViewAdapter;
import com.example.tanvir.reportbook.Database.DatabaseManager;
import com.example.tanvir.reportbook.Models.ReportDetails;
import com.example.tanvir.reportbook.Models.ReportDayMonthAndYearItem;
import com.example.tanvir.reportbook.R;

import java.util.ArrayList;
import java.util.Calendar;

public class MonthlyFragment extends Fragment {
    public String[] monthName = {"January","February","March","April","May","June","July","August","September","October","November","December"};
    DatabaseManager databaseManager;
    RecyclerViewAdapter recyclerViewAdapter;

    private OnFragmentInteractionListener mListener;


    public interface OnFragmentInteractionListener {

    }

    public MonthlyFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_fragment__monthly, container, false);
        RecyclerView recyclerView = view.findViewById(R.id.recycler_view);

        databaseManager=new DatabaseManager(getActivity());
        //calling db for reading all data
        ArrayList<ReportDetails> arrayList = databaseManager.getAllReport();
        ArrayList<String> reportDetails2 = new ArrayList<>();

        recyclerViewAdapter = new RecyclerViewAdapter(reportDetails2,getActivity());

        recyclerView.setItemAnimator(new DefaultItemAnimator());


        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);

        recyclerView.setAdapter(recyclerViewAdapter);

        //recyclerViewAdapter.notifyDataSetChanged();
        //reading all data from db
        for (ReportDetails reportDetails: arrayList){
            String monthAndYear = yearIndicatorAlgo(reportDetails.getId());
            String day,month="",year="";
            int j = 0;
            for (int i=0;i<monthAndYear.length();i++){
                if(monthAndYear.charAt(i)!=' '){
                    month+=monthAndYear.charAt(i);
                }else{
                    j=i+1;
                    break;
                }
            }
            for(;j<monthAndYear.length();j++){
                year+=monthAndYear.charAt(j);
            }

            //Toast.makeText(getActivity(), ""+month+" "+year+ new ReportDayMonthAndYearItem(month,year), Toast.LENGTH_SHORT).show();
            if(!reportDetails2.contains(month+" "+year) && !month.equals("0")){
                //ReportDetails reportDetails1 = new ReportDetails(reportDetails.getId(),reportDetails.getPrayer(),reportDetails.getQuran(),
                        //reportDetails.getHadith(),reportDetails.getAcademicBook(),reportDetails.getNovelOrOtherBook(),
                       // reportDetails.getNewspaper(),reportDetails.getSkillDev(),reportDetails.getMarketing(),reportDetails.getWakeupTime(),
                       // reportDetails.getSleepTime(),reportDetails.getWorkout());
                //Toast.makeText(getActivity(), ""+reportDetails1.getId(), Toast.LENGTH_SHORT).show();
                //ReportDayMonthAndYearItem reportDayMonthAndYearItem = new ReportDayMonthAndYearItem(month,year);
                reportDetails2.add(month+" "+year);
            }
        }
            recyclerViewAdapter.notifyDataSetChanged();
        return view;
    }

    public String yearIndicatorAlgo(String id){
        String day,month,year;
        String date = id;
        Calendar calendar = Calendar.getInstance();
        int calenderMonth = calendar.get(Calendar.MONTH);
        int calenderYear = calendar.get(Calendar.YEAR);

        if(date.length()==8){//checking here if date.length = 8 then this condition will work date format = 10 10 2018
            day = String.valueOf(date.charAt(0)+date.charAt(1));
            month = String.valueOf(date.charAt(2))+String.valueOf(date.charAt(3));
            year = String.valueOf(date.charAt(4))+String.valueOf(date.charAt(5))+String.valueOf(date.charAt(6))+String.valueOf(date.charAt(7));
            if(String.valueOf(calenderYear).equals(year)){
               return monthName[Integer.parseInt(month)]+" "+year;
            }


        }
        //checking here if date.length = 7 and 1 no position is 0 then this condition will work date format = 1 01 2018
        else if(date.length()==7 && date.charAt(1)=='0'){
            day = String.valueOf(date.charAt(0));
            month = String.valueOf(date.charAt(2));
            year = String.valueOf(date.charAt(3))+String.valueOf(date.charAt(4))+String.valueOf(date.charAt(5))+String.valueOf(date.charAt(6));
            if(String.valueOf(calenderYear).equals(year)){
                return monthName[Integer.parseInt(month)]+" "+year;
            }
        }
        //checking here if date.length = 7 and 1 no position is not 0 then this condition will work date format = 1 10 2018
        else if(date.length()==7 && date.charAt(1)!='0') {
            day = String.valueOf(date.charAt(0));
            month = String.valueOf(date.charAt(1)) + String.valueOf(date.charAt(2));
            year = String.valueOf(date.charAt(3)) + String.valueOf(date.charAt(4)) + String.valueOf(date.charAt(5)) + String.valueOf(date.charAt(6));
            if (String.valueOf(calenderYear).equals(year)){
                return monthName[Integer.parseInt(month)]+" "+year;
            }
        }
        return "0";
    }

}
