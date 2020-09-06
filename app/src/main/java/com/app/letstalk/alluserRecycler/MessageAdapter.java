package com.app.letstalk.alluserRecycler;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.app.letstalk.HomePage;
import com.app.letstalk.R;
import com.app.letstalk.fragments.ChatsFragment;

import java.util.ArrayList;
import java.util.List;

public class MessageAdapter extends RecyclerView.Adapter< MessageAdapter.MyViewHolder> {
 List<String> userText,myText = new ArrayList<>();
    Context context;

    public MessageAdapter(List<String> userText, List<String> myText, ChatsFragment homePage) {

    this.userText = userText;
    this.myText = myText;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

      View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.chat_recycler,parent,false);
        return new MyViewHolder(view,context);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        if (userText.get(position)!=null){
         String user = userText.get(position);
            holder.userText.setText(user);
        }
        if (myText.get(position)!=null){
            String my = myText.get(position);
            holder.myText.setText(my);
            }
    }

    @Override
    public int getItemCount() {
        return myText.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{

       Context context;

       TextView userText,myText;

        public MyViewHolder(@NonNull View itemView,Context ctx) {
            super(itemView);
            context =ctx;
            userText = itemView.findViewById(R.id.chat_recycler_userText);
            myText = itemView.findViewById(R.id.chat_recycler_myText);
        }


        }
    }

