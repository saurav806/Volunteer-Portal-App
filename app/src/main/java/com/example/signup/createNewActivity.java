package com.example.signup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

public class createNewActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    EditText heading;
    EditText description;
    EditText eventDate;
    EditText preprationDate;
    EditText numVolunteers;
    Button createNewActivity;

    String heading1;
    String description1;
    String eventDate1;
    String preprationDate1;
    String numVolunteer1;

    String obj;
    AdminHomePageAdapter objadapter;

    FirebaseAuth auth=FirebaseAuth.getInstance();
    FirebaseUser user=auth.getCurrentUser();
    FirebaseDatabase db=FirebaseDatabase.getInstance();
    DatabaseReference ref=db.getReference();
    ImageView back;

    public void postData()
    {
//        final String key=ref.child("ActivityInfo").push().getKey();
        final HashMap<String,Object> mp=new HashMap<>();
        mp.put("heading",heading1);
        mp.put("description",description1);
        mp.put("eventData",eventDate1);
        mp.put("preprationDate",preprationDate1);
        mp.put("numVolunteers",numVolunteer1);

        final String key=heading1;
        ref.child("ActivityInfo").child(key).setValue(mp);

    }


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_new);


        heading=findViewById(R.id.activityHeading);
        description= findViewById(R.id.editTextDescription);
        eventDate = findViewById(R.id.editTextEventDate);
        preprationDate = findViewById(R.id.editTextStartDate);
        numVolunteers =  findViewById(R.id.editTextVolunteersNumber);
        createNewActivity = findViewById(R.id.buttonCreateActivity);
        back=findViewById(R.id.back);
        eventDate.setShowSoftInputOnFocus(false);
        preprationDate.setShowSoftInputOnFocus(false);

        Intent intent=getIntent();
        heading1=intent.getStringExtra("heading");
        heading.setText(heading1);
        FirebaseDatabase db=FirebaseDatabase.getInstance();
        DatabaseReference ref=db.getReference();

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        eventDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                eventDate.setShowSoftInputOnFocus(false);
                DatePickerFragment datePicker=new DatePickerFragment();
                datePicker.show(getSupportFragmentManager(),"date picker");

            }
        });

        preprationDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                preprationDate.setShowSoftInputOnFocus(false);
                DatePickerFragment datePicker=new DatePickerFragment();
                datePicker.show(getSupportFragmentManager(),"date picker");

            }
        });



        createNewActivity.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View view) {

                    heading1=heading.getText().toString();
                    description1=description.getText().toString();
                    eventDate1=eventDate.getText().toString();
                    preprationDate1=preprationDate.getText().toString();
                    numVolunteer1=numVolunteers.getText().toString();

                    int flag=0;

                    if(heading1.isEmpty())
                    {
                        flag=1;
                        Toast.makeText(view.getContext(), "Heading Field is Required!!!", Toast.LENGTH_SHORT).show();
                    }

                    if(description1.isEmpty())
                    {
                        flag=1;
                        Toast.makeText(view.getContext(), "Description Field is Required!!!", Toast.LENGTH_SHORT).show();
                    }

                    if(eventDate1.isEmpty())
                    {
                        flag=1;
                        Toast.makeText(view.getContext(), "Event Date Field is Required!!!", Toast.LENGTH_SHORT).show();
                    }
                    else if(!eventDate1.isEmpty())
                    {
                        for(int i=0;i<eventDate1.length();i++)
                        {
                            if((eventDate1.charAt(i)>='a' && eventDate1.charAt(i)<='z')||(eventDate1.charAt(i)>='A' && eventDate1.charAt(i)<='Z'))
                            {
                                flag=1;
                                break;
                            }
                        }

                        if(eventDate1.length()!=10)
                            flag=1;
                        else if(eventDate1.charAt(2)!='/' || eventDate1.charAt(5)!='/')
                            flag=1;
                        else
                        {
                            String date=""+eventDate1.charAt(0)+eventDate1.charAt(1);
                            int d=Integer.parseInt(date);
                            String month=""+eventDate1.charAt(3)+eventDate1.charAt(4);
                            int m=Integer.parseInt(month);
                            String year=""+eventDate1.charAt(6)+eventDate1.charAt(7)+eventDate1.charAt(8)+eventDate1.charAt(9);
                            int y=Integer.parseInt(year);

                            //Toast.makeText(view.getContext(), ""+d+m+y, Toast.LENGTH_SHORT).show();

                            if(y<2023 || y>2024)
                                flag=1;
                            else if(y==2024)
                            {
                                if(m==2)
                                {
                                    if(d>29 || d<1)
                                        flag=1;
                                }
                                else if(m==1 || m==3 || m==5 || m==7 || m==8 || m==10 || m==12)
                                {
                                    if(d>31 || d<1)
                                        flag=1;
                                }
                                else
                                {
                                    if(d>30 || d<1)
                                        flag=1;
                                }
                            }
                            else
                            {
                                if(m==2)
                                {
                                    if(d>28 || d<1)
                                        flag=1;
                                }
                                else if(m==1 || m==3 || m==5 || m==7 || m==8 || m==10 || m==12)
                                {
                                    if(d>31 || d<1)
                                        flag=1;
                                }
                                else
                                {
                                    if(d>30 || d<1)
                                        flag=1;
                                }
                            }

                        }

                        if(flag==1)
                        {
                            Toast.makeText(view.getContext(), "Wrong Event Date Format!!!", Toast.LENGTH_SHORT).show();
                        }
                        else if(flag==0)
                        {
                            if(preprationDate1.isEmpty())
                            {
                                flag=1;
                                Toast.makeText(view.getContext(), "Prepration Date Field is Required!!!", Toast.LENGTH_SHORT).show();
                            }
                            else if(!preprationDate1.isEmpty())
                            {
                                // int flag=0;
                                for(int i=0;i<preprationDate1.length();i++)
                                {
                                    if((preprationDate1.charAt(i)>='a' && preprationDate1.charAt(i)<='z')||(preprationDate1.charAt(i)>='A' && eventDate1.charAt(i)<='Z'))
                                    {
                                        flag=1;
                                        break;
                                    }
                                }

                                if(preprationDate1.length()!=10)
                                {
                                    flag=1;
                                    Toast.makeText(view.getContext(), "Wrong Prepration Date Format!!!", Toast.LENGTH_SHORT).show();
                                }
                                else if(preprationDate1.charAt(2)!='/' || preprationDate1.charAt(5)!='/')
                                {
                                    flag=1;
                                    Toast.makeText(view.getContext(), "Wrong Prepration Date Format!!!", Toast.LENGTH_SHORT).show();
                                }
                                else
                                {
                                    String date=""+preprationDate1.charAt(0)+preprationDate1.charAt(1);
                                    int d=Integer.parseInt(date);
                                    String month=""+preprationDate1.charAt(3)+preprationDate1.charAt(4);
                                    int m=Integer.parseInt(month);
                                    String year=""+preprationDate1.charAt(6)+preprationDate1.charAt(7)+preprationDate1.charAt(8)+preprationDate1.charAt(9);
                                    int y=Integer.parseInt(year);

                                    if(y<2023 || y>2024)
                                        flag=1;
                                    else if(y==2024)
                                    {
                                        if(m==2)
                                        {
                                            if(d>29 || d<1)
                                                flag=1;
                                        }
                                        else if(m==1 || m==3 || m==5 || m==7 || m==8 || m==10 || m==12)
                                        {
                                            if(d>31 || d<1)
                                                flag=1;
                                        }
                                        else
                                        {
                                            if(d>30 || d<1)
                                                flag=1;
                                        }

                                    }
                                    else
                                    {
                                        if(m==2)
                                        {
                                            if(d>28 || d<1)
                                                flag=1;
                                        }
                                        else if(m==1 || m==3 || m==5 || m==7 || m==8 || m==10 || m==12)
                                        {
                                            if(d>31 || d<1)
                                                flag=1;
                                        }
                                        else
                                        {
                                            if(d>30 || d<1)
                                                flag=1;
                                        }
                                    }


                                }

                                if(flag==1)
                                {
                                    Toast.makeText(view.getContext(), "Wrong Prepration Date Format!!!", Toast.LENGTH_SHORT).show();
                                }
                            }
                        }
                    }


                    if(numVolunteer1.isEmpty())
                    {
                        flag=1;
                        Toast.makeText(view.getContext(), "Number of Volunteers Field is Required!!!", Toast.LENGTH_SHORT).show();
                    }

                    if(!eventDate1.isEmpty() && !preprationDate1.isEmpty())
                    {
                        int d1=Integer.parseInt(""+eventDate1.charAt(0)+eventDate1.charAt(1));
                        int m1=Integer.parseInt(""+eventDate1.charAt(3)+eventDate1.charAt(4));
                        int y1=Integer.parseInt(""+eventDate1.charAt(6)+eventDate1.charAt(9));

                        int d2=Integer.parseInt(""+preprationDate1.charAt(0)+preprationDate1.charAt(1));
                        int m2=Integer.parseInt(""+preprationDate1.charAt(3)+preprationDate1.charAt(4));
                        int y2=Integer.parseInt(""+preprationDate1.charAt(6)+preprationDate1.charAt(9));


                        if(y1<y2) {
                            flag = 1;
                            Toast.makeText(view.getContext(), "Event Date can't be before Prepration Date", Toast.LENGTH_SHORT).show();
                        }
                        else if(y1==y2)
                        {
                            if(m1<m2)
                            {
                                flag = 1;
                                Toast.makeText(view.getContext(), "Event Date can't be before Prepration Date", Toast.LENGTH_SHORT).show();
                            }
                            else if(m1==m2)
                            {
                                if(d1<=d2)
                                {
                                    flag = 1;
                                    Toast.makeText(view.getContext(), "Event Date can't be before Prepration Date", Toast.LENGTH_SHORT).show();
                                }
                            }
                        }
                    }

                if(flag==0)
                {
                    postData();
                    finish();
                    Toast.makeText(createNewActivity.this, "New Activity Created!!!", Toast.LENGTH_SHORT).show();
                }


            }
        });


    }

    @Override
    public void onDateSet(DatePicker view, int year,
                          int monthOfYear, int dayOfMonth) {



        int month = monthOfYear + 1; // month count given less one e.g. august then give month no 7

        String dayStr   = String.valueOf(dayOfMonth);
        String monthStr = String.valueOf(month);

        if (dayStr.length() == 1)
            dayStr = "0"+dayStr;

        if (monthStr.length() == 1)
            monthStr = "0"+monthStr;

        // dd/mm/yyyy format set.
        if(eventDate.hasFocus())
            eventDate.setText(dayStr+"/"+monthStr+"/"+year);
        if(preprationDate.hasFocus())
            preprationDate.setText(dayStr+"/"+monthStr+"/"+year);


    }




}


















