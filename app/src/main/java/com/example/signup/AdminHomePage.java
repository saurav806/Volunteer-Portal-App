package com.example.signup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class AdminHomePage extends AppCompatActivity {

    AdminHomePageAdapter adapter;
    RecyclerView rcy;
    FloatingActionButton floatAction;
    FirebaseAuth auth=FirebaseAuth.getInstance();
    FirebaseUser user=auth.getCurrentUser();
    FirebaseDatabase db=FirebaseDatabase.getInstance();
    DatabaseReference ref=db.getReference();
    ArrayList<ModelClass> modelList=new ArrayList<>();
    ImageView back;
    Button signout;


    public void getActivity()
    {
        ref.child("ActivityInfo").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                ModelClass model=snapshot.getValue(ModelClass.class);
                String id=snapshot.getKey();

                modelList.add(model);
                snapshot.getKey();
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {



            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        adapter=new AdminHomePageAdapter(modelList);
        rcy.setAdapter(adapter);


    }

    public  boolean onOptionsItemSelected(MenuItem item)
    {
        int id= item.getItemId();
        if(id==android.R.id.home)
        {
            finish();
            return  true;
        }
        return super.onOptionsItemSelected(item);
    }
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_home_page);

        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        rcy=findViewById(R.id.recyclerView);
        floatAction = findViewById(R.id.floatingActionButton);
        rcy.setLayoutManager(new LinearLayoutManager(this));
        back=findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        getActivity();


        floatAction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdminHomePage.this, createNewActivity.class);
                startActivity(intent);
            }
        });

//        signout=findViewById(R.id.signOutAdmin);
//        signout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                auth.signOut();
//                finish();
//            }
//        });


    }

    @Override
    protected void onStart() {
        super.onStart();
    }
}