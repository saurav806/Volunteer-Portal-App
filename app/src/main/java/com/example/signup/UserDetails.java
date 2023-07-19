package com.example.signup;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class UserDetails extends AppCompatActivity {

    String skills;
    String previousExperience;
    String contribution;
    String heading;
    String uid;
    ImageView back;
    public static final String EXTRA_DATA = "EXTRA_DATA";
    TextView skill;
    RadioGroup radioGroup;
    RadioButton radioButton;
    TextView contri;
    Button apply;

    FirebaseAuth auth=FirebaseAuth.getInstance();
    FirebaseUser user=auth.getCurrentUser();
    FirebaseDatabase db=FirebaseDatabase.getInstance();
    DatabaseReference ref=db.getReference();
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_details);


        Intent intent=getIntent();
        heading=intent.getStringExtra("heading");
        uid=intent.getStringExtra("uid");


        back=findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        skill=findViewById(R.id.skills);
        radioGroup=findViewById(R.id.radioGroup);
        contri=findViewById(R.id.contribution);
        apply=findViewById(R.id.ApplyForActivity);
        RadioButton r1=findViewById(R.id.radioYes);
        RadioButton r2=findViewById(R.id.radioNo);


        r1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                contri.setEnabled(true);
            }
        });

        r2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                contri.clearComposingText();
                contri.setEnabled(false);
            }
        });



        apply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=getIntent();
                String heading1=intent.getStringExtra("heading");
                String uid=intent.getStringExtra("uid");
                skills=skill.getText().toString();
                contribution=contri.getText().toString();
                String radio=checkButton(view);

                if(skills.length()>0)
                {
                    UserModelDetails obj=new UserModelDetails(skills,radio,contribution,uid,heading);
                    ref.child("Applied").push().setValue(obj);
                    finish();
                    Toast.makeText(view.getContext(), "Applied Succesfully", Toast.LENGTH_SHORT).show();
                }

            }
        });



    }

    public String checkButton(View view)
    {
        int radioId=radioGroup.getCheckedRadioButtonId();
        radioButton=findViewById(radioId);

        return radioButton.getText().toString();
    }

}