package com.example.tanvir.reportbook.Fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.tanvir.reportbook.Adapters.RecyclerViewAdapter;
import com.example.tanvir.reportbook.Adapters.YearlyRecyclerViewAdapter;
import com.example.tanvir.reportbook.Database.DatabaseManager;
import com.example.tanvir.reportbook.Models.MonthAndYear;
import com.example.tanvir.reportbook.Models.ReportDetails;
import com.example.tanvir.reportbook.R;

import java.util.ArrayList;
import java.util.Calendar;

public class YearlyFragment extends Fragment {


    private OnFragmentInteractionListener mListener;

    public interface OnFragmentInteractionListener {

    }
    public YearlyFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        DatabaseManager databaseManager;
        YearlyRecyclerViewAdapter yearlyRecyclerViewAdapter;
        String year = "",month;
        
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_yearly, container, false);
        RecyclerView recyclerView = view.findViewById(R.id.yearlyRecyclerView);

        databaseManager=new DatabaseManager(getActivity());
        //calling db for reading all data
        ArrayList<ReportDetails> arrayList = databaseManager.getAllReport();
        ArrayList<String> reportDetails2 = new ArrayList<>();
        ArrayList<MonthAndYear> montYEarLIst = new ArrayList<>();//mont and year list
        //reading all data from db
        for (ReportDetails reportDetails: arrayList){
            String monthAndYear = yearIndicatorAlgo(reportDetails.getId());

            month="";
            year="";
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

            if(!reportDetails2.contains(year)){
                reportDetails2.add(year);
            }
            MonthAndYear monthAndYearModel = new MonthAndYear(month,year);
            montYEarLIst.add(monthAndYearModel);
        }

        yearlyRecyclerViewAdapter = new YearlyRecyclerViewAdapter(reportDetails2,montYEarLIst,getActivity(),year);

        recyclerView.setItemAnimator(new DefaultItemAnimator());
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);

        recyclerView.setAdapter(yearlyRecyclerViewAdapter);
        yearlyRecyclerViewAdapter.notifyDataSetChanged();

        return view;
    }

    //year separation from id
    public String yearIndicatorAlgo(String id){
        String day,month,year;
        String date = id;
        Calendar calendar = Calendar.getInstance();
        int calenderMonth = calendar.get(Calendar.MONTH);
        int calenderYear = calendar.get(Calendar.YEAR);

        if(date.length()==8){//checking here if date.length = 8 then this condition will work date format = 10 10 2018
            //day = String.valueOf(date.charAt(0)+date.charAt(1));
            month = String.valueOf(date.charAt(2))+String.valueOf(date.charAt(3));
            year = String.valueOf(date.charAt(4))+String.valueOf(date.charAt(5))+String.valueOf(date.charAt(6))+String.valueOf(date.charAt(7));
            if(String.valueOf(calenderYear).equals(year)){
                return Integer.parseInt(month)+" "+year;
            }


        }
        //checking here if date.length = 7 and 1 no position is 0 then this condition will work date format = 1 01 2018
        else if(date.length()==7 && date.charAt(1)=='0'){
            //day = String.valueOf(date.charAt(0));
            month = String.valueOf(date.charAt(2));
            year = String.valueOf(date.charAt(3))+String.valueOf(date.charAt(4))+String.valueOf(date.charAt(5))+String.valueOf(date.charAt(6));
            if(String.valueOf(calenderYear).equals(year)){
                return Integer.parseInt(month)+" "+year;
            }
        }
        //checking here if date.length = 7 and 1 no position is not 0 then this condition will work date format = 1 10 2018
        else if(date.length()==7 && date.charAt(1)!='0') {
            //day = String.valueOf(date.charAt(0));
            month = String.valueOf(date.charAt(1)) + String.valueOf(date.charAt(2));
            year = String.valueOf(date.charAt(3)) + String.valueOf(date.charAt(4)) + String.valueOf(date.charAt(5)) + String.valueOf(date.charAt(6));
            if (String.valueOf(calenderYear).equals(year)){
                return Integer.parseInt(month)+" "+year;
            }
        }
        return "0";
    }

}
