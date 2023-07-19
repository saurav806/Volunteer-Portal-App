package com.example.signup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class UserSignUp extends AppCompatActivity {

    EditText useremail,password,confirmpassword,name,phone;
    Button signupuser;

    String email,pass,pass1,pass2,uname,ph;
    FirebaseAuth auth=FirebaseAuth.getInstance();
    FirebaseDatabase database=FirebaseDatabase.getInstance();
    DatabaseReference ref=database.getReference();
    public void signUpUser()
    {
        auth.createUserWithEmailAndPassword(email,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful())
                {
                   // auth.signInWithEmailAndPassword(email,pass);
                    ref.child("user").child(auth.getCurrentUser().getUid()).child("name").setValue(name.getText().toString());
                    ref.child("user").child(auth.getCurrentUser().getUid()).child("phone number").setValue(phone.getText().toString());
                    ref.child("user").child(auth.getCurrentUser().getUid()).child("email id").setValue(useremail.getText().toString());

                    //auth.signOut();

                    Intent i=new Intent(UserSignUp.this,Login.class);
                    startActivity(i);
                    finish();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                System.out.println(e);
            }
        });
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_sign_up);

        useremail=findViewById(R.id.userEmail);
        password=findViewById(R.id.userpassword);
        confirmpassword=findViewById(R.id.userconfirmpassword);
        name=findViewById(R.id.username);
        phone=findViewById(R.id.phonenumber);

        signupuser=findViewById(R.id.signupuser);

        signupuser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // String email=useremail.getText().toString();

                email=useremail.getText().toString();
                pass=password.getText().toString();
                pass1=password.getText().toString();
                pass2=confirmpassword.getText().toString();
                uname=name.getText().toString();
                ph=phone.getText().toString();


                int flag=0;
                if(email.length()==0 || pass1.length()==0 || pass2.length()==0 || uname.length()==0 || ph.length()==0)
                {
                    flag=1;
                    Toast.makeText(UserSignUp.this, "All fields are Required!!!"+email+"-"+pass1+"-"+pass2+"-"+uname+" "+ph, Toast.LENGTH_SHORT).show();
                }
                else if(ph.length()!=10)
                {
                    flag=1;
                    Toast.makeText(UserSignUp.this, "Invalid Phone Number!!!", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Boolean x=pass1.equals(pass2);
                    if(x==false)
                    {
                        flag=1;
                        Toast.makeText(UserSignUp.this, "Password and Confirm Password don't Match!!!", Toast.LENGTH_SHORT).show();
                    }
                }

                if(flag==0)
                   signUpUser();
            }
        });
    }
}