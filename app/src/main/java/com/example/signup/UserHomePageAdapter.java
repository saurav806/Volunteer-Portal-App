package com.example.signup;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.view.menu.MenuView;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Handler;

public class UserHomePageAdapter extends RecyclerView.Adapter<UserHomePageAdapter.MyViewHolder> {

    Context context;
    ArrayList<ModelClassUser> list;
    static String curUser;
    static String head;
    int numcount;
    int count=0;
    private static final int flag = 0;
    FirebaseAuth auth=FirebaseAuth.getInstance();
    FirebaseUser user=auth.getCurrentUser();
    FirebaseDatabase db=FirebaseDatabase.getInstance();
    DatabaseReference ref=db.getReference();
    Handler mhandler;
    public UserHomePageAdapter(Context context, ArrayList<ModelClassUser> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(context).inflate(R.layout.activity_user_card,parent,false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        ModelClassUser user=list.get(position);
        curUser=auth.getUid();

        holder.heading.setText(user.getHeading());
        holder.description.setText(user.getDescription());
        holder.eventDate.setText(user.getEventData());
        holder.preprationDate.setText(user.getPreprationDate());
        holder.numVolunteers.setText(user.getNumVolunteers());
        head=list.get(position).getHeading();

        ref=FirebaseDatabase.getInstance().getReference("Applied");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                count=0;
                for(DataSnapshot ds:snapshot.getChildren())
                {
                    UserModelDetails obj=ds.getValue(UserModelDetails.class);

                    if(obj.getUid().equals(auth.getUid()) && obj.getHeading().equals(user.getHeading()))
                    {
                       // holder.apply.setVisibility(View.GONE);

                            holder.apply.setText("Applied");
                            holder.apply.setEnabled(false);
                        if(obj.status.equals("1") && obj.getHeading().equals(user.getHeading()))
                        {
                            count+=1;
                            holder.apply.setText("Selected");
                            holder.apply.setEnabled(false);
                        }
                    }

                }

                //Toast.makeText(context, ""+count, Toast.LENGTH_SHORT).show();
                holder.selectedVolunteers.setText(Integer.toString(count));

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        ref=FirebaseDatabase.getInstance().getReference("ActivityInfo");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                numcount=0;
                for(DataSnapshot ds:snapshot.getChildren())
                {
                    ModelClass obj=ds.getValue(ModelClass.class);

                    if(obj.getHeading().equals(user.getHeading()))
                    {
                        numcount=Integer.parseInt(obj.getNumVolunteers());
                        if(count==numcount)
                        {
                            holder.apply.setVisibility(View.GONE);
//                            holder.apply.setText("Applied");
//                            holder.apply.setEnabled(false);
                        }
                        break;
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        holder.apply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Intent intent=new Intent(view.getContext(),UserDetails.class);
                intent.putExtra("heading",holder.heading.getText().toString());
                intent.putExtra("uid",curUser);
                view.getContext().startActivity(intent);
            }

        });


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        Button apply;
        FirebaseAuth auth=FirebaseAuth.getInstance();
        FirebaseUser user=auth.getCurrentUser();
        FirebaseDatabase db=FirebaseDatabase.getInstance();
        DatabaseReference ref=db.getReference();
        TextView heading,description,eventDate,preprationDate,numVolunteers,selectedVolunteers;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);


            heading= itemView.findViewById(R.id.displayHeadingToUser);
            description= itemView.findViewById(R.id.displayDescriptionToUser);
            eventDate= itemView.findViewById(R.id.displayEventDateToUser);
            preprationDate= itemView.findViewById(R.id.displayPreprationDateToUser);
            numVolunteers=itemView.findViewById(R.id.displayNumVolunteersToUser);
            selectedVolunteers=itemView.findViewById(R.id.selectedVolunteerToUser);
            apply=itemView.findViewById(R.id.ApplyForActivity);

        }



    }
}
