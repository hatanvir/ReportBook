package com.example.tanvir.reportbook.Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tanvir.reportbook.Activities.CalenderActivity;
import com.example.tanvir.reportbook.Models.ReportDayMonthAndYearItem;
import com.example.tanvir.reportbook.R;

import java.util.ArrayList;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.myViewHolder> {
    public String[] monthName = {"January","February","March","April","May","June","July","August","September","October","November","December"};
    private ArrayList<String> arrayList;
    ArrayList<String> monthList;
    RecyclerView recyclerView;

    public  RecyclerViewAdapter(ArrayList<String> arrayList, Context context){
        this.arrayList = arrayList;
        this.context = context;
    }

    Context context;
    public class myViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView dateTv;
        CardView cardView;
        public myViewHolder(@NonNull View itemView) {
            super(itemView);
            dateTv = itemView.findViewById(R.id.monthTv);
            cardView = itemView.findViewById(R.id.parentViewId);
        }

        @Override
        public void onClick(View view) {
            Toast.makeText(context, ""+getPosition()+""+getAdapterPosition(), Toast.LENGTH_SHORT).show();
        }
    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull final ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.recyclerview_shape,viewGroup,false);

        myViewHolder holder =  new myViewHolder(itemView);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull myViewHolder holder, int i) {
        String day,month,year;
        holder.dateTv.setText(arrayList.get(i));

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, ""+i, Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(context,CalenderActivity.class);
                intent.putExtra("date",arrayList.get(i));
                context.startActivity(intent);
            }
        });
        //holder.setItemclickListener

        /*String date = arrayList.get(i).getId();
        Calendar calendar = Calendar.getInstance();
        int calenderMonth = calendar.get(Calendar.MONTH);
        int calenderYear = calendar.get(Calendar.YEAR);
        if(date.length()==8){//checking here if date.length = 8 then this condition will work date format = 10 10 2018
            //day = String.valueOf(date.charAt(0)+date.charAt(1));
            month = String.valueOf(date.charAt(2))+String.valueOf(date.charAt(3));
            year = String.valueOf(date.charAt(4))+String.valueOf(date.charAt(5))+String.valueOf(date.charAt(6))+String.valueOf(date.charAt(7));
            if(String.valueOf(calenderYear).equals(year)){
                myViewHolder.dateTv.setText(monthName[calenderMonth]+" "+year);
            }


        }
        //checking here if date.length = 7 and 1 no position is 0 then this condition will work date format = 1 01 2018
        else if(date.length()==7 && date.charAt(1)=='0'){
            //day = String.valueOf(date.charAt(0));
            month = String.valueOf(date.charAt(2));
            year = String.valueOf(date.charAt(3))+String.valueOf(date.charAt(4))+String.valueOf(date.charAt(5))+String.valueOf(date.charAt(6));
            if(String.valueOf(calenderYear).equals(year)){
                myViewHolder.dateTv.setText(monthName[calenderMonth]+" "+year);
            }
        }
        //checking here if date.length = 7 and 1 no position is not 0 then this condition will work date format = 1 10 2018
        else if(date.length()==7 && date.charAt(1)!='0') {
            //day = String.valueOf(date.charAt(0));
            month = String.valueOf(date.charAt(1)) + String.valueOf(date.charAt(2));
            year = String.valueOf(date.charAt(3)) + String.valueOf(date.charAt(4)) + String.valueOf(date.charAt(5)) + String.valueOf(date.charAt(6));
            if (String.valueOf(calenderYear).equals(year)) {
                myViewHolder.dateTv.setText(monthName[calenderMonth]+" "+year);
            }
        }*/
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }
}
