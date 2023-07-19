package com.example.signup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
public class UserAppliedActivity extends AppCompatActivity {

    RecyclerView rcy;
    FirebaseAuth auth=FirebaseAuth.getInstance();
    FirebaseUser user=auth.getCurrentUser();
    FirebaseDatabase db=FirebaseDatabase.getInstance();
    DatabaseReference ref=db.getReference();
    ArrayList<UserModelDetails> modelList=new ArrayList<>();
    ImageView back;
    TextView text;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_applied);
        // Toast.makeText(this, "hello", Toast.LENGTH_SHORT).show();
        rcy=findViewById(R.id.recyclerView2);
        rcy.setHasFixedSize(true);
        rcy.setLayoutManager(new LinearLayoutManager(UserAppliedActivity.this));
        back=findViewById(R.id.back);
        text=findViewById(R.id.text);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        getAllAppliedActivity();

    }

    void getAllAppliedActivity()
    {
        ref=FirebaseDatabase.getInstance().getReference("Applied");

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                modelList.clear();
                int flag=0;
                for(DataSnapshot ds:snapshot.getChildren())
                {
                    UserModelDetails obj=ds.getValue(UserModelDetails.class);
                    if(obj.getUid().equals(auth.getUid()))
                    {
                        modelList.add(obj);
                        flag=1;
                    }
                }
                if(flag==1)
                {
                    text.setVisibility(View.GONE);
                }
                else {
                    text.setVisibility(View.VISIBLE);
                }
                UserAppliedActivityAdapter ua=new UserAppliedActivityAdapter(UserAppliedActivity.this,modelList);
                rcy.setAdapter(ua);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

}
