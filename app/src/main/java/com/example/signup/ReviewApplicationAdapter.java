package com.example.signup;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class ReviewApplicationAdapter extends RecyclerView.Adapter<ReviewApplicationAdapter.MyHolder> {

    Context context;
    ArrayList<ReviewApplicationModel> arr=new ArrayList<>();
    FirebaseAuth auth=FirebaseAuth.getInstance();
    FirebaseUser user=auth.getCurrentUser();
    FirebaseDatabase db=FirebaseDatabase.getInstance();
    DatabaseReference ref=db.getReference();
    AlertDialog.Builder builder;


    public ReviewApplicationAdapter(Context context, ArrayList<ReviewApplicationModel> arr) {
        this.context = context;
        this.arr = arr;
    }

    @NonNull
    @Override
    public ReviewApplicationAdapter.MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view= LayoutInflater.from(context).inflate(R.layout.review_application_card,parent,false);

        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {

        ref = FirebaseDatabase.getInstance().getReference("user");
        String h1=arr.get(position).getHeading();
        String skills=arr.get(position).getSkills();
        String pastexp=arr.get(position).getPastExp();
        String contri=arr.get(position).getContri();
        String uid=arr.get(position).getUid();
        holder.skills.setText(skills);
        holder.pastExp.setText(pastexp);
        holder.contri.setText(contri);

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot ds:snapshot.getChildren())
                {
                      if(ds.getKey().equals(uid))
                    {
                        holder.name.setText((CharSequence) ds.child("name").getValue());
                    }

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        ref = FirebaseDatabase.getInstance().getReference("Applied");
        holder.reject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                builder.setTitle("Reject Application").setMessage("Are you Sure?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                ref.addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                        for(DataSnapshot ds:snapshot.getChildren())
                                        {
                                            if(ds.child("heading").getValue().equals(h1) && ds.child("uid").getValue().equals(uid))
                                            {
                                                ds.getRef().removeValue();
                                            }

                                        }
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

//                ref.addValueEventListener(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(@NonNull DataSnapshot snapshot) {
//                        for(DataSnapshot ds:snapshot.getChildren())
//                        {
//                            if(ds.child("heading").getValue().equals(h1) && ds.child("uid").getValue().equals(uid))
//                            {
//                                ds.getRef().removeValue();
//                            }
//
//                        }
//                    }
//
//                    @Override
//                    public void onCancelled(@NonNull DatabaseError error) {
//
//                    }
//                });


        });

        holder.accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                builder.setTitle("Accept Application").setMessage("Are you Sure?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                ref.addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                        for(DataSnapshot ds:snapshot.getChildren())
                                        {
                                            if(ds.child("heading").getValue().equals(h1) && ds.child("uid").getValue().equals(uid))
                                            {
                                                //ds.getRef().removeValue();
                                                ds.getRef().child("status").setValue("1");
                                            }

                                        }
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

//                ref.addValueEventListener(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(@NonNull DataSnapshot snapshot) {
//                        for(DataSnapshot ds:snapshot.getChildren())
//                        {
//                            if(ds.child("heading").getValue().equals(h1) && ds.child("uid").getValue().equals(uid))
//                            {
//                                //ds.getRef().removeValue();
//                                ds.getRef().child("status").setValue("1");
//                            }
//
//                        }
//                    }
//
//                    @Override
//                    public void onCancelled(@NonNull DatabaseError error) {
//
//                    }
//                });


        });
    }

    @Override
    public int getItemCount() {
        return arr.size();
    }

    public class MyHolder extends  RecyclerView.ViewHolder {
        TextView name;
        TextView skills;
        TextView pastExp;
        TextView contri;
        Button accept,reject;

        public MyHolder(@NotNull View itemView)
        {
            super(itemView);
            name=itemView.findViewById(R.id.userName);
            skills=itemView.findViewById(R.id.skills);
            pastExp=itemView.findViewById(R.id.pastExperience);
            contri=itemView.findViewById(R.id.contribution);
            accept=itemView.findViewById(R.id.accept);
            reject=itemView.findViewById(R.id.reject);
            builder=new AlertDialog.Builder(itemView.getContext());

        }
    }
}
