package com.example.signup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class UserHomePage extends AppCompatActivity {

    RecyclerView recyclerView;
    UserHomePageAdapter myAdapter;
    DatabaseReference database;
    ArrayList<ModelClassUser> list;
    AlertDialog.Builder builder;

    Button viewAppliedActivity;
    Button signOut;
    FirebaseAuth auth=FirebaseAuth.getInstance();
    FirebaseUser user=auth.getCurrentUser();
    FirebaseDatabase db=FirebaseDatabase.getInstance();
    DatabaseReference ref=db.getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_home_page);

        recyclerView=findViewById(R.id.userrecyclerView);
        database= FirebaseDatabase.getInstance().getReference("ActivityInfo");
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        list=new ArrayList<>();
        myAdapter=new UserHomePageAdapter(this,list);
        recyclerView.setAdapter(myAdapter);

        viewAppliedActivity=findViewById(R.id.viewAppliedActivity);
        signOut=findViewById(R.id.signOut);

        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for(DataSnapshot dataSnapshot: snapshot.getChildren())
                {
                    ModelClassUser user=dataSnapshot.getValue(ModelClassUser.class);
                    list.add(user);
                }
                myAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        viewAppliedActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Toast.makeText(UserHomePage.this, "Hello", Toast.LENGTH_SHORT).show();
                Intent i=new Intent(view.getContext(),UserAppliedActivity.class);
                view.getContext().startActivity(i);

            }
        });

        signOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                builder=new AlertDialog.Builder(view.getContext());

                builder.setTitle("Sign Out").setMessage("Are you Sure?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                ref.addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot) {


                                        auth.signOut();
//                                        Intent i=new Intent(view.getContext(),Login.class);
//                                        view.getContext().startActivity(i);
                                        finish();
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError error) {

                                    }
                                });
                            }
                        }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        }).show();

            }

        });
    }
}