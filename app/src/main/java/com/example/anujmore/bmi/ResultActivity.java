package com.example.anujmore.bmi;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import android.view.Window;
import android.view.WindowManager;

import java.sql.RowId;
import java.time.LocalDateTime;

public class ResultActivity extends AppCompatActivity {
    TextView tvResultAns,tvResultStatus,tvResultUnderWeight,tvResultNormal,tvResultOverWeight,tvResultObese;
    Button btnResultBack,btnResultShare,btnResultSave;
    FirebaseDatabase database;
    DatabaseReference myRef;
    SharedPreferences sp;
    //tvResultLocation,


    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        //tvResultLocation=findViewById(R.id.tvResultLocation);
        tvResultAns=findViewById(R.id.tvResultAns);
        tvResultStatus=findViewById(R.id.tvResultStatus);
        tvResultUnderWeight=findViewById(R.id.tvResultUnderweight);
        tvResultNormal=findViewById(R.id.tvResultNormal);
        tvResultOverWeight=findViewById(R.id.tvResultOverweight);
        tvResultObese=findViewById(R.id.tvResultObese);
        btnResultBack=findViewById(R.id.btnResultBack);
        btnResultShare=findViewById(R.id.btnResultShare);
        btnResultSave=findViewById(R.id.btnResultSave);
        database=FirebaseDatabase.getInstance();
        myRef=database.getReference("result");


        Window window = ResultActivity.this.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.setStatusBarColor(ContextCompat.getColor(ResultActivity.this, R.color.colorPrimary));

        Intent l=getIntent();
        final String bmi1=l.getStringExtra("bmi");
        tvResultAns.setText("Your Bmi is "+bmi1);
        final Double bmi2=Double.parseDouble(bmi1);


        if(bmi2<=18.5){

            tvResultUnderWeight.setText("Below 18.5 is UnderWeight");
            tvResultUnderWeight.setTextColor(Color.RED);
            tvResultStatus.setText("Underweight");
            tvResultUnderWeight.setTextSize(25);
        }

        else if(bmi2>=18.6  && bmi2<=25.0){

            tvResultNormal.setText("Between 18.6 to 25 is Normal");
            tvResultNormal.setTextColor(Color.RED);
            tvResultStatus.setText("Normal");
            tvResultNormal.setTextSize(25);
        }

        else if(bmi2>=25.1 && bmi2<=30.0){

            tvResultOverWeight.setText("Between 25.1 to 30 is OverWeight");
            tvResultOverWeight.setTextColor(Color.RED);
            tvResultStatus.setText("Overweight");
            tvResultOverWeight.setTextSize(25);
        }

        else if(bmi2>=30.1){

            tvResultObese.setText("More than 30.1 is Obese");
            tvResultObese.setTextColor(Color.RED);
            tvResultStatus.setText("Obese");
            tvResultObese.setTextSize(25);
        }

        tvResultUnderWeight.setText("Below 18.5 is UnderWeight");
        tvResultNormal.setText("Between 18.5 to 25 is Normal");
        tvResultOverWeight.setText("Between 25 to 30  is OverWeight");
        tvResultObese.setText("More than 30 is Obese");

        String status1=tvResultStatus.getText().toString();
        tvResultStatus.setText("You are "+status1);


        final String status=tvResultStatus.getText().toString();





        btnResultBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ResultActivity.this,EntryActivity.class));

            }
        });

        btnResultShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                sp=getSharedPreferences("f1",MODE_PRIVATE);
                String name=sp.getString("name","");
                int age=sp.getInt("age",0);
                long phno=sp.getLong("phno",0);
                //String gender=sp.getString("gender","");

                Intent i=new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse("sms:"));
                i.putExtra("sms_body","Name: "+name+"\n"+ "Age: "+age+"\n"+ "Phone Number:"+phno+"\n"+"My BMI is"+bmi2+ "\n"+ "and "+status);
                startActivity(i);
//

               /* Intent i=new Intent(Intent.ACTION_SEND);
                i.setType("text/plain");
                i.putExtra(Intent.EXTRA_TEXT,tvResultAns.get);
                i.putExtra(Intent.EXTRA_TEXT,status);
*/
                try{
                    startActivity(i);
                }
                catch (Exception e){
                    Toast.makeText(ResultActivity.this, "" +e, Toast.LENGTH_SHORT).show();
                }


            }
        });
        //LocalDateTime time=LocalDateTime.now();
        //final String time1= String.valueOf(time);


        btnResultSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                String s1 =tvResultStatus.getText().toString();
                Result r= new Result(Double.parseDouble(bmi1),s1);
                myRef.push().setValue(r);
                Toast.makeText(ResultActivity.this, "Record added", Toast.LENGTH_SHORT).show();

            }
        });



    }
}
