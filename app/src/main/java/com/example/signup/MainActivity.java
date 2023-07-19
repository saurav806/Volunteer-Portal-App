package com.example.signup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {


    EditText email;
    EditText password;
    Button signUp;

    String userEmail;
    String userPassword;

    FirebaseAuth auth= FirebaseAuth.getInstance();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        email=findViewById(R.id.editTextTextEmailAddressSignup);
        password=findViewById(R.id.editTextTextPassword2Signup);
        signUp=findViewById(R.id.button3Signup);

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userEmail=email.getText().toString();
                userPassword=password.getText().toString();

                performSignUp(userEmail,userPassword);
            }


        });

    }

    private void performSignUp(String userEmail, String userPassword) {

        auth.createUserWithEmailAndPassword(userEmail,userPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful())
                {
                    Toast.makeText(MainActivity.this, "SignUp Successful", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(MainActivity.this, "SignUp Unsuccessful", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}