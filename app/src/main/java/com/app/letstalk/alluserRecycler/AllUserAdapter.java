package com.app.letstalk.alluserRecycler;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.app.letstalk.AllUsers;
import com.app.letstalk.R;
import com.app.letstalk.UserInfo;
import com.app.letstalk.util.Data;
import com.app.letstalk.util.UserData;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.QuerySnapshot;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;


public class AllUserAdapter extends RecyclerView.Adapter <AllUserAdapter.MyViewHolder>  {

    public Context context;

  public   static List<Data> data = new ArrayList<>();


    public AllUserAdapter() {
    }



    public AllUserAdapter(List<Data> data,Context context) {
        this.data = data;
        this.context = context;
    }




    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_card,parent,false);

        return new MyViewHolder(view,context);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {

               Data data1 = data.get(position);


                holder.status.setText(data1.getStatus());
                holder.userName.setText(data1.getName());
        Picasso.get().load(data1.getProfilePic())
                .into(holder.imageView);

    }



    @Override
    public int getItemCount() {
        return  data.size();
    }



    static class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

         private    Context context;
            TextView  userName,status;
        ImageView imageView ;



        public MyViewHolder(@NonNull View itemView ,Context ctx) {
            super(itemView);
            context = ctx;
        userName = itemView.findViewById(R.id.allUser_userName);
        status = itemView.findViewById(R.id.all_user_status);
        imageView = itemView.findViewById(R.id.all_user_profile_image);
        itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
         Data data1 =  data.get(position);
            Toast.makeText(context, ""+data1.getName(), Toast.LENGTH_SHORT).show();
           Intent intent = new Intent(context, UserInfo.class);
           intent.putExtra("userName",data1.getName());
            intent.putExtra("userStatus",data1.getStatus());
            intent.putExtra("userProfilePic",data1.getProfilePic());
            intent.putExtra("id",data1.getId());
            context .startActivity(intent);
        }
    }

}
