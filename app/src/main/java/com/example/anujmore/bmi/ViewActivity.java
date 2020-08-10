package com.example.anujmore.bmi;

import android.content.Intent;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import android.view.Window;
import android.view.WindowManager;
import java.util.ArrayList;

public class ViewActivity extends AppCompatActivity {
    ListView lvViewData;
    Button btnViewBack;
    ArrayList<Result> r= new ArrayList<>();
    ArrayAdapter<Result> ad;
    FirebaseDatabase database;
    DatabaseReference myRef;
    //ArrayList<String> k=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);

        lvViewData=findViewById(R.id.lvViewData);
        btnViewBack=findViewById(R.id.btnViewBack);
        database=FirebaseDatabase.getInstance();
        myRef=database.getReference("result");

        Window window = ViewActivity.this.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.setStatusBarColor(ContextCompat.getColor(ViewActivity.this, R.color.colorPrimary));

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                r.clear();
                for (DataSnapshot d:dataSnapshot.getChildren()){
                    Result data=d.getValue(Result.class);
                    r.add(data);
                }
                ad= new ArrayAdapter<Result>(ViewActivity.this,android.R.layout.simple_list_item_1,r);
                lvViewData.setAdapter(ad);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

                /*lvViewData.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                    @Override
                    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                        Result rt=r.get(position);
                        //showUpdateDeleteDilaog(k.get(position),rt.getBmi2());

                        Toast.makeText(ViewActivity.this, rt.getBmi2()+ "", Toast.LENGTH_SHORT).show();
                        return true;
                    }
                });
                */

            }
        });
       btnViewBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ViewActivity.this,EntryActivity.class));

            }
        });

        //String data=MainActivity.db.viewResult();
    }
}
