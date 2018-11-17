package com.example.tanvir.reportbook.Activities;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tanvir.reportbook.R;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DetailsActivity extends AppCompatActivity {

    TextView prayerCountTv,quranTv,hadithTv,academicTv,novelTv,newspaperTv,skillTv,marketingTv,wakeUpTv,sleepTv,workOutTv;
    ScrollView scrollView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        //title;
        this.setTitle("Details");
        //adding home button
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        viewInitialization();
        Intent intent = getIntent();
        prayerCountTv.setText(intent.getStringExtra("prayer"));
        quranTv.setText(intent.getStringExtra("quran"));
        hadithTv.setText(intent.getStringExtra("hadith"));
        academicTv.setText(intent.getStringExtra("academic"));
        novelTv.setText(intent.getStringExtra("novel"));
        newspaperTv.setText(intent.getStringExtra("newspaper"));
        skillTv.setText(intent.getStringExtra("skill"));
        marketingTv.setText(intent.getStringExtra("marketing"));
        wakeUpTv.setText(intent.getStringExtra("wakeup"));
        sleepTv.setText(intent.getStringExtra("sleep"));
        workOutTv.setText(intent.getStringExtra("workout"));

    }

    private void viewInitialization() {
        prayerCountTv = findViewById(R.id.prayerCount);
        quranTv = findViewById(R.id.quranRecDurTv);
        hadithTv = findViewById(R.id.hadithReadDurTv);
        academicTv = findViewById(R.id.academicReadDurTv);
        novelTv = findViewById(R.id.novelReadDurTv);
        newspaperTv = findViewById(R.id.newspaperStatusTv);
        skillTv = findViewById(R.id.skillDevDurTv);
        marketingTv = findViewById(R.id.marketingSatusTv);
        wakeUpTv = findViewById(R.id.wakeUpTimeTv);
        sleepTv = findViewById(R.id.sleepTimeTv);
        workOutTv = findViewById(R.id.workOutDurTv);

        scrollView = findViewById(R.id.scrollViewId);
    }

    public void sreenShotBt(View view) {
        Dialog dialog = new Dialog(this);
        dialog.setTitle("Saving...");
        dialog.show();
        Calendar calendar = Calendar.getInstance();
        String currentDate = new SimpleDateFormat("dd-MM-yyyy").format(calendar.getTime());
        Bitmap bitmap = getBitmapFromView(scrollView, scrollView.getChildAt(0).getHeight(), scrollView.getChildAt(0).getWidth());

        try {
            File defaultFile = new File(Environment.getExternalStorageDirectory().getAbsolutePath()+"/Report book");
            if (!defaultFile.exists())
                defaultFile.mkdirs();

            String filename = "Report books report on "+currentDate+".jpg";
            File file = new File(defaultFile,filename);
            if (file.exists()) {
                file.delete();
                file = new File(defaultFile,filename);
            }

            FileOutputStream output = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, output);
            output.flush();
            output.close();

            dialog.dismiss();
            showPhoto(Uri.fromFile(file));

            //Toast.makeText(this, "", Toast.LENGTH_SHORT).show();.makeText(getActivity(), Message.RECEIPT_SAVE, Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
            dialog.dismiss();
            //Toast.makeText(getActivity(), Message.RECEIPT_SAVE_FAILED, Toast.LENGTH_SHORT).show();
        }
    }

    private Bitmap getBitmapFromView(ScrollView scrollView, int height, int width) {
        Bitmap bitmap = Bitmap.createBitmap(width, height,Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        Drawable bgDrawable =scrollView.getBackground();
        if (bgDrawable!=null)
            bgDrawable.draw(canvas);
        else
            canvas.drawColor(Color.WHITE);
        scrollView.draw(canvas);
        return bitmap;
    }

    private void showPhoto(Uri photoUri){
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        intent.setDataAndType(photoUri, "image/*");
        startActivity(intent);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home) {
            finish();
        }

        return super.onOptionsItemSelected(item);
    }
}
