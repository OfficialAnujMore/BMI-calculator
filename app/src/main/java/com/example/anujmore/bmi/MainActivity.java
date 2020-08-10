package com.example.anujmore.bmi;

import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Location;
import android.os.AsyncTask;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import android.view.Window;
import android.view.WindowManager;

import javax.xml.validation.Validator;

public class MainActivity extends AppCompatActivity {

    EditText etName, etAge, etPhno, etLocation;
    RadioGroup rgGender;
    Button btnRegister;
    TextView tvError;
    static TextView tvLocation;
    SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etName = findViewById(R.id.etName);
        etAge = findViewById(R.id.etAge);
        etPhno = findViewById(R.id.etPhnO);
        etLocation = findViewById(R.id.etLocation);
        tvLocation = findViewById(R.id.tvLocation);
        btnRegister = findViewById(R.id.btnRegister);
        tvError = findViewById(R.id.tvError);


        Window window = MainActivity.this.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.setStatusBarColor(ContextCompat.getColor(MainActivity.this, R.color.colorPrimary));



        sp = getSharedPreferences("f1", MODE_PRIVATE);
        String name = sp.getString("name", "");
        final String age= etAge.getText().toString();

        if (name.length() == 0) {


            btnRegister.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    sp = getSharedPreferences("f1", MODE_PRIVATE);
                    String name = etName.getText().toString();
                    String age=etAge.getText().toString();
                    String phno=etPhno.getText().toString();
                    String loc=etLocation.getText().toString();

//                    int gid=rgGender.getCheckedRadioButtonId();
  //                  RadioButton rg=findViewById(gid);
    //                String gender=rg.getText().toString();

                    if (name.length() == 0) {
                        etName.setError("Name is Empty");
                        etName.requestFocus();
                        return;

                    } else if (etName.length() < 2 || !isStringOnly(name)) {
                        etName.setError("Please enter a valid name");
                        etName.requestFocus();
                        return;

                    } else if (etAge.length() == 0) {
                        etAge.setError("Age is Empty");
                        etAge.requestFocus();
                        return;
                    } else if (etPhno.length() == 0 ) {
                        etPhno.setError("Phno is Empty");
                        etPhno.requestFocus();
                        return;
                    } else if (etPhno.length() < 10 || etPhno.length() > 10|| !isNumericRegex(phno)) {
                        etPhno.setError("Incorrect Phone no");
                        etPhno.requestFocus();
                        return;
                    }
                    else if(etLocation.length()<2 ||etLocation.length()==0 || !isStringOnly1(loc)){
                        etLocation.setError("Invalid Location");
                        etLocation.requestFocus();


                    }
                    else {
                        SharedPreferences.Editor editor = sp.edit();
                        editor.putString("name", name);
                        editor.putInt("age", Integer.parseInt(age));
                        editor.putLong("phno", Long.parseLong(phno));
                       // editor.putString("gender", String.valueOf(gender));
                        editor.commit();

                        String loc1= etLocation.getText().toString();

                        if (loc1.length() == 0) {
                            etLocation.setError("Location is Empty");
                            etLocation.requestFocus();
                            return;
                        }

                        Locate t1 = new Locate();
                        String w1 = "http://api.openweathermap.org/data/2.5/weather?units=metric";
                        String w2 = "&q=" + loc;
                        String w3 = "&appid=c6e315d09197cec231495138183954bd";
                        String w = w1 + w2 + w3;
                        t1.execute(w);
                        tvLocation.setText(w);


                        //startActivity(new Intent(MainActivity.this, EntryActivity.class));
                        //finish();
                    }
                }
            });
        }
        else {
            startActivity(new Intent(MainActivity.this, EntryActivity.class));
            finish();
        }
    }

    public static boolean isStringOnly(String str) {
        return ((str.matches("^[a-zA-Z]*$")));
    }

    public  static boolean isNumericRegex(String str){
        return str.matches("=?\\d+");
    }

    public static boolean isStringOnly1(String str1) {
        return (str1 !=null && (str1.matches("^[a-zA-Z]*$")));
    }

   // public static boolean isStringOnly2(String str2) {
     //   return (str2 !=null && (str2.matches("^[a-zA-Z]*$")));
    //}

    /*public static boolean isDigitOnly(Integer in){
        return ((in.equals("[0-9]")));
    }*/

    class Locate extends AsyncTask<String, Void, Double> {
        double temp;

        @Override
        protected Double doInBackground(String... strings) {
            String json = "", line = "";
            try {
                URL url = new URL(strings[0]);
                HttpURLConnection con = (HttpURLConnection) url.openConnection();
                con.connect();


                InputStreamReader isr = new InputStreamReader(con.getInputStream());
                BufferedReader br = new BufferedReader(isr);

                while ((line = br.readLine()) != null) {
                    json = json + line + "\n";

                }
                JSONObject o = new JSONObject(json);
                JSONObject p = o.getJSONObject("main");
                temp = p.getDouble("temp");
            } catch (Exception e) {
                e.printStackTrace();

            }
            return temp;
        }


        @Override
        protected void onPostExecute(Double aDouble) {
            super.onPostExecute(aDouble);
            String loc = etLocation.getText().toString();

            tvLocation.setText("Temperature in "+loc+ " is "+ aDouble);
            String loc1 = tvLocation.getText().toString();
            Intent i=new Intent(MainActivity.this,EntryActivity.class);
            i.putExtra("loc1",loc1);
            startActivity(i);

            //Toast.makeText(MainActivity.this, "Temperature in "+loc+ "is "+ aDouble, Toast.LENGTH_SHORT).show();


        }



    }
}
