package com.app.letstalk.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.app.letstalk.R;
import com.app.letstalk.alluserRecycler.MessageAdapter;

import java.util.ArrayList;
import java.util.List;

public class ChatsFragment extends Fragment {

    public ChatsFragment() {
        // Required empty public constructor
    }
private RecyclerView recyclerView;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_chats, container, false);
        recyclerView = view.findViewById(R.id.fragment_chat_recyler);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        List<String> userText= new ArrayList<>();
        userText.add("hello");userText.add("hello");userText.add("hello");
        userText.add("hello");userText.add("hello");userText.add("hello");
        userText.add("hello");userText.add("hello");
  List<String> myText= new ArrayList<>();
        myText.add("hello");myText.add("hello"); myText.add("hello");myText.add("hello");
        myText.add("hello");myText.add("hello"); myText.add("hello");myText.add("hello");


      MessageAdapter  messageAdapter = new MessageAdapter(userText,myText,this);
        recyclerView.setAdapter(messageAdapter);

   return view;



    }
}
