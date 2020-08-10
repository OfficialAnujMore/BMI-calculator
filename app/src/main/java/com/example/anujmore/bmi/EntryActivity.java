package com.example.anujmore.bmi;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
//import android.support.annotation.NonNull;
//import android.support.v7.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import java.lang.Math;
import android.view.Window;
import android.view.WindowManager;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class EntryActivity extends AppCompatActivity {
    TextView tvEntryName,tvEntryFeets,tvEntryInches,tvEntryData,tvEntryLocation;
    EditText etEntryWeight;
    Button btnEntryCalculate,btnEntryView;
    Spinner spfeets,spinches;
    SharedPreferences sp;

    //static DbHandler db;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entry);

        tvEntryName= findViewById(R.id.tvEntryName);
        tvEntryFeets= findViewById(R.id.tvEntryFeets);
        tvEntryInches= findViewById(R.id.tvEntryInches);
        tvEntryLocation= findViewById(R.id.tvEntryLocation);
        spfeets=findViewById(R.id.spfeets);
        spinches=findViewById(R.id.spinches);
        etEntryWeight= findViewById(R.id.etEntryWeight);
        tvEntryData=findViewById(R.id.tvEntryData);
        btnEntryCalculate= findViewById(R.id.btnEntryCalculate);
        btnEntryView=findViewById(R.id.btnEntryView);

        Window window = EntryActivity.this.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.setStatusBarColor(ContextCompat.getColor(EntryActivity.this, R.color.colorPrimary));

        Intent i=getIntent();
        final String loc1=i.getStringExtra("loc1");
        String locstr=tvEntryLocation.getText().toString();


       //if (!isStringOnly2(locstr)|| locstr==null){
         //tvEntryLocation.setText("Location not found");
           //return;
        //}
        //else{
//           tvEntryLocation.setText(" "+loc1);
        //}
        //tvEntryLocation.setText(" " +loc1);


        sp=getSharedPreferences("f1",MODE_PRIVATE);
        String name=sp.getString("name","");

        //sp1=getSharedPreferences("f2",MODE_PRIVATE);
        //String location=sp1.getString("location","");

       // Toast.makeText(this, ""+location, Toast.LENGTH_SHORT).show();
        tvEntryName.setText("Welcome " +name);
        tvEntryName.setElegantTextHeight(true);

        //tvEntryLocation.setText("Loaction is "+location);
       //tvEntryLocation.setElegantTextHeight(true);

        final ArrayList<String> s1= new ArrayList<>();
        s1.add("1.00");
        s1.add("2.00");
        s1.add("3.00");
        s1.add("4.00");
        s1.add("5.00");
        s1.add("6.00");
        s1.add("7.00");
        s1.add("8.00");
        ArrayAdapter arrayAdapter= new ArrayAdapter(this,android.R.layout.simple_spinner_item,s1);
        spfeets.setAdapter(arrayAdapter);


        final ArrayList<String> s2= new ArrayList<>();
        s2.add("0");
        s2.add("1");
        s2.add("2");
        s2.add("3");
        s2.add("4");
        s2.add("5");
        s2.add("6");
        s2.add("7");
        s2.add("8");
        s2.add("9");
        s2.add("10");
        s2.add("11");
        s2.add("12");
        ArrayAdapter arrayAdapter1= new ArrayAdapter(this,android.R.layout.simple_spinner_item,s2);
        spinches.setAdapter(arrayAdapter1);






        btnEntryCalculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if(etEntryWeight.length()==0)
                {
                    etEntryWeight.setError("Entry your weight");
                }

                else {

                    int f = spfeets.getSelectedItemPosition();
                    Float feets = Float.valueOf(s1.get(f));

                    int i = spinches.getSelectedItemPosition();
                    Float inches = Float.valueOf(s2.get(i));

                    String w = etEntryWeight.getText().toString();
                    double weight = Float.parseFloat(w);


                    double height = 0.305 * feets + 0.0254 * inches;
                    double bmi_test = weight / (height * height);
                    double bmi= Math.round(bmi_test);

                    tvEntryData.setText("" +bmi);

                    String bmi1 = tvEntryData.getText().toString();

                    Intent l = new Intent(EntryActivity.this, ResultActivity.class);
                    l.putExtra("bmi", bmi1);
                    etEntryWeight.setText("");
                    startActivity(l);
                }

            }
        });

        btnEntryView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(EntryActivity.this,ViewActivity.class));
            }
        });


    }
    public static boolean isStringOnly2(String str) {
        return (str ==null ); //&& (str.matches("^[a-zA-Z]*$"))
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.m1,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==R.id.website){
            Intent i= new Intent(Intent.ACTION_VIEW);
            i.setData(Uri.parse("https://www.linkedin.com/in/anuj-more-8391b6193/"));
            startActivity(i);
        }
        if(item.getItemId()==R.id.about){
            Toast.makeText(this, "Created by Mr.Anuj More", Toast.LENGTH_LONG).show();
        }
        return super.onOptionsItemSelected(item);
    }
}
