package com.example.signup;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

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
public class UserAppliedActivityAdapter extends RecyclerView.Adapter<UserAppliedActivityAdapter.MyHolder> {

    Context context;
    ArrayList<UserModelDetails> arr=new ArrayList<>();


    AlertDialog.Builder builder;


    public UserAppliedActivityAdapter(Context context, ArrayList<UserModelDetails> arr) {
        this.context = context;
        this.arr = arr;
    }

    @NonNull
    @Override
    public UserAppliedActivityAdapter.MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view= LayoutInflater.from(context).inflate(R.layout.user_applied_card,parent,false);

        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserAppliedActivityAdapter.MyHolder holder, int position) {

        String heading;
        String status;

        heading=arr.get(position).getHeading();
        status=arr.get(position).getStatus();
        if(status.equals("1"))
            status="Status: Selected";
        else if(status.equals("0"))
            status="Status: In Review";
        FirebaseAuth auth=FirebaseAuth.getInstance();
        FirebaseUser user=auth.getCurrentUser();
        holder.heading.setText(heading);
        holder.status.setText(status);




        holder.optOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseReference ref= FirebaseDatabase.getInstance().getReference("Applied");

                builder.setTitle("OptOut").setMessage("Are you Sure? (Once you Opt Out then you can never apply to same Activity)")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                                ref.addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                                        for(DataSnapshot ds:snapshot.getChildren())
                                        {
                                            UserModelDetails obj=ds.getValue(UserModelDetails.class);
                                            if(obj.getUid().equals(user.getUid()) && obj.getHeading().equals(heading))
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
        });
    }

    @Override
    public int getItemCount() {
        return arr.size();
    }

    public class MyHolder  extends  RecyclerView.ViewHolder{

        TextView heading;
        TextView status;

        Button optOut;

        public MyHolder(@NotNull View itemView)
        {
            super(itemView);
            heading=itemView.findViewById(R.id.headingApplied);
            status=itemView.findViewById(R.id.statusApplied);
            optOut=itemView.findViewById(R.id.optOutButton);
            builder=new AlertDialog.Builder(itemView.getContext());

        }

    }
}
