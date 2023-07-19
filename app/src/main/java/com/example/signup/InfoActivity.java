package com.example.signup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class InfoActivity extends AppCompatActivity {
TextView heading,desc,event,prep,volunteers,list,list1;
DatabaseReference reference= FirebaseDatabase.getInstance().getReference();
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);
        heading=findViewById(R.id.infoheading);
        desc=findViewById(R.id.description);
        event=findViewById(R.id.event);
        prep=findViewById(R.id.prep);
        volunteers=findViewById(R.id.volunteers);
        list=findViewById(R.id.list);
        list1=findViewById(R.id.list1);
        Intent intent=getIntent();
        String head=intent.getStringExtra("heading");
        heading.setText(head);
        reference.child("ActivityInfo").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot ds : snapshot.getChildren())
                {
                    if(ds.getKey().equals(head))
                    {
                        desc.setText(ds.child("description").getValue().toString());
                        event.setText(ds.child("eventData").getValue().toString());
                        prep.setText(ds.child("preprationDate").getValue().toString());
                        volunteers.setText(ds.child("numVolunteers").getValue().toString());
                        break;
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        ArrayList< String> arr= new ArrayList<>();
        ArrayList< String> name= new ArrayList<>();
        ArrayList< String> selected= new ArrayList<>();
        ArrayList< String> selectedname= new ArrayList<>();

        reference.child("Applied").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot ds: snapshot.getChildren())
                {
                    if(ds.child("heading").getValue().toString().equals(head))
                    {
                        arr.add(ds.child("uid").getValue().toString());
                        if(ds.child("status").getValue().toString().equals("1"))
                        {
                            selected.add(ds.child("uid").getValue().toString());
                        }
                    }

                }
                reference.child("user").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot1) {
                        for(DataSnapshot ds: snapshot1.getChildren())
                        {
                            for(int i=0;i<arr.size();i++)
                            {
                                if(arr.get(i).equals(ds.getKey()))
                                {
                                    name.add(ds.child("name").getValue().toString());
                                }
                            }
                            for(int i=0;i<selected.size();i++)
                            {
                                if(selected.get(i).equals(ds.getKey()))
                                {
                                    selectedname.add(ds.child("name").getValue().toString());
                                }
                            }
                        }

                        String s= "";
                        for(int i=0;i<name.size();i++)
                        {
                            s=s+"\n"+name.get(i);
                        }
                        String a="";
                        for(int i=0;i<selectedname.size();i++)
                        {
                            a=a+"\n"+selectedname.get(i);
                        }
                        list.setText(s);
                        list1.setText(a);

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}