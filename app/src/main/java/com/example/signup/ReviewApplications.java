package com.example.signup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ReviewApplications extends AppCompatActivity {

    RecyclerView rcy;
    ImageView back;
    TextView text;
    FirebaseAuth auth=FirebaseAuth.getInstance();
    FirebaseUser user=auth.getCurrentUser();
    FirebaseDatabase db=FirebaseDatabase.getInstance();
    DatabaseReference ref=db.getReference();
    ArrayList<ReviewApplicationModel> modelList=new ArrayList<>();


    protected  void onRestart()
    {
        super.onRestart();
        startActivity(getIntent());

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review_applications);
            back=findViewById(R.id.back);
            text=findViewById(R.id.text);
            back.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    finish();
                }
            });
         rcy=findViewById(R.id.recyclerView3);
        rcy.setHasFixedSize(true);
        rcy.setLayoutManager(new LinearLayoutManager(ReviewApplications.this));

        getAllApplications();

    }

    void getAllApplications() {
        ref = FirebaseDatabase.getInstance().getReference("Applied");

        Intent intent=getIntent();
        String heading1=intent.getStringExtra("heading");

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                modelList.clear();
                int flag=0;
                for (DataSnapshot ds : snapshot.getChildren()) {
                    ReviewApplicationModel obj = ds.getValue(ReviewApplicationModel.class);
                   if(heading1.equals(obj.getHeading() )&& obj.getStatus().equals("0"))
                   {
                       modelList.add(obj);
                       flag=1;
                   }
                   if(flag==1)
                   {
                       text.setVisibility(View.GONE);
                   }
                   else {
                       text.setVisibility(View.VISIBLE);

                   }
                }



                ReviewApplicationAdapter ua = new ReviewApplicationAdapter(ReviewApplications.this, modelList);
                rcy.setAdapter(ua);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}