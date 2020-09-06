package com.app.letstalk;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import com.app.letstalk.alluserRecycler.AllUserAdapter;
import com.app.letstalk.util.Data;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class AllUsers extends AppCompatActivity  {
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference collectionReference = db.collection("Users");
    private List<Data> data ;
  private   AllUserAdapter allUserAdapter;
    private String TAG = "AllUser";

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_users);

        recyclerView = findViewById(R.id.all_user_recyclerView);
        layoutManager = new LinearLayoutManager(this);
             recyclerView.setLayoutManager(layoutManager);
        data = new ArrayList<>();
        recyclerView.setHasFixedSize(true);


}

    @Override
    protected void onStart() {
        super.onStart();
        collectionReference.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                for (QueryDocumentSnapshot snapshot : queryDocumentSnapshots){
                    Data data1 = new Data();
                    data1.setName(snapshot.getString("userName"));
                    data1.setStatus(snapshot.getString("userStatus"));
                    data1.setProfilePic(snapshot.getString("userImage"));
                    data1.setId(snapshot.getId());
                    data.add(data1);
                }

                allUserAdapter = new AllUserAdapter(data,AllUsers.this);
                recyclerView.setAdapter(allUserAdapter);
                allUserAdapter.notifyDataSetChanged();

            }
        })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.i(TAG, "onFailure: ");
                    }
                });
    }


}
