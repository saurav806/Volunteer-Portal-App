package com.example.signup;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.util.EventLogTags;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.Serializable;
import java.util.ArrayList;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AdminHomePageAdapter extends RecyclerView.Adapter<AdminHomePageAdapter.ViewHolder> {

    ArrayList<ModelClass> arr=new ArrayList<>();
    ModelClass obj;
    FirebaseAuth auth=FirebaseAuth.getInstance();
    FirebaseUser user=auth.getCurrentUser();
    FirebaseDatabase db=FirebaseDatabase.getInstance();
    DatabaseReference ref=db.getReference();
    OnItemClickListener listener;

    AlertDialog.Builder builder;


    public interface OnItemClickListener
    {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener clickListener)
    {
        listener=clickListener;
    }

    public AdminHomePageAdapter(ArrayList<ModelClass> list) {

        arr=list;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_card,parent,false);

        return new ViewHolder(view,listener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        obj=arr.get(position);
        int ind=position;
        holder.heading.setText(arr.get(ind).getHeading());
        holder.description.setText(arr.get(ind).getDescription());
        holder.eventDate.setText(arr.get(ind).getEventData());
        holder.preprationDate.setText(arr.get(ind).getPreprationDate());
        holder.numVolunteers.setText(arr.get(ind).getNumVolunteers());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(view.getContext(),InfoActivity.class);
                intent.putExtra("heading",arr.get(ind).getHeading());
                view.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return arr.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView heading;
        TextView description;
        TextView eventDate;
        TextView preprationDate;
        TextView numVolunteers;
        Button edit,delete,review;


        public ViewHolder(@NonNull View itemView,OnItemClickListener listener) {

            super(itemView);

            heading=itemView.findViewById(R.id.displayHeading);
            description= itemView.findViewById(R.id.displayDescription);
            eventDate = itemView.findViewById(R.id.displayEventDate);
            preprationDate = itemView.findViewById(R.id.displayPreprationDate);
            numVolunteers = itemView.findViewById(R.id.displayNumVolunteers);

            edit=itemView.findViewById(R.id.button2);
            delete=itemView.findViewById(R.id.button5);
            review=itemView.findViewById(R.id.button4);
            builder=new AlertDialog.Builder(itemView.getContext());


            edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    int pos=getAdapterPosition();
                    builder.setTitle("Edit").setMessage("Are you Sure?")
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {

                                    Intent intent=new Intent(view.getContext(), com.example.signup.editNewActivity.class);
                                    intent.putExtra("heading",heading.getText().toString());
                                    intent.putExtra("description", description.getText().toString());
                                    intent.putExtra("eventDate", eventDate.getText().toString());
                                    intent.putExtra("preprationDate", preprationDate.getText().toString());
                                    intent.putExtra("numVolunteer", numVolunteers.getText().toString());

                                    view.getContext().startActivity(intent);


                                }
                            }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {

                                }
                            }).show();

                }
            });

            delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    String Heading=heading.getText().toString();

                    final String key=Heading;
                    int pos=getAdapterPosition();

                    builder.setTitle("Delete").setMessage("Are you Sure?")
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {

                                    ref.child("ActivityInfo").child(key).removeValue();
                                    notifyItemChanged(pos);
                                    Toast.makeText(view.getContext(), "Activity Deleted", Toast.LENGTH_SHORT).show();

                                }
                            }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {

                                }
                            }).show();
                }
            });


            review.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Intent i=new Intent(view.getContext(),ReviewApplications.class);
                    i.putExtra("heading",heading.getText().toString());
                    view.getContext().startActivity(i);

                }
            });
        }



    }
}
