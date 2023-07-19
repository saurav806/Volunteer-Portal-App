package com.example.signup;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Login extends AppCompatActivity {

    EditText username;
    EditText password;
    Button login;
    Button signUpUser;

    String uname;
    String pass;

    FirebaseAuth auth=FirebaseAuth.getInstance();
    FirebaseUser user = auth.getCurrentUser();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        username=findViewById(R.id.editTextTextEmailAddress);
        password=findViewById(R.id.editTextTextPassword2);
        login=findViewById(R.id.button3);
        signUpUser=findViewById(R.id.signUpUserButton);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                uname=username.getText().toString();
                pass=password.getText().toString();

                if(uname.equals("mayank17.mewar@gmail.com") && pass.equals("admin123"))
                {
                    Intent i=new Intent(Login.this,AdminWelcomePage.class);
                    startActivity(i);
                    //finish();
                }
                else
                {
                    if(uname.length()==0 || pass.length()==0)
                    {
                        Toast.makeText(Login.this, "Please enter all Fields!!!", Toast.LENGTH_SHORT).show();
                    }
                    else if(uname.endsWith("@nitc.ac.in"))
                    {
                        signIn();
                    }
                    else
                    {
                        Toast.makeText(Login.this, "Wrong Email or Password!!!", Toast.LENGTH_SHORT).show();
                    }
                }


            }
        });

        signUpUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(Login.this,UserSignUp.class);
                startActivity(i);
            }
        });

    }

    private void signIn()
    {
        auth.signInWithEmailAndPassword(uname,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful())
                {
                    Toast.makeText(Login.this, "SignIn Successful", Toast.LENGTH_SHORT).show();
                    Intent i=new Intent(Login.this,UserHomePage.class);
                    startActivity(i);

                }
                else
                {
                    Toast.makeText(Login.this, "SignIn Unsuccessful!!", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }


}
