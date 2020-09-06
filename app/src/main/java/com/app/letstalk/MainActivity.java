package com.app.letstalk;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.app.letstalk.util.UserData;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class MainActivity extends AppCompatActivity {
private Button letsGo;
private  String TAG ="main Activity";
private FirebaseAuth firebaseAuth ;
private FirebaseAuth.AuthStateListener authStateListener;
private FirebaseUser currentUser;

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference collectionReference = db.collection("Users");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        firebaseAuth = FirebaseAuth.getInstance();
        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                currentUser = firebaseAuth.getCurrentUser();
                if(currentUser!=null){
                   final String uid = currentUser.getUid();

                    collectionReference.document(uid)
                            .get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                        @Override
                        public void onSuccess(DocumentSnapshot documentSnapshot) {
                            UserData data = UserData.getInstance();
                            data.setUserId(uid);
                            data.setName(documentSnapshot.getString("userName"));
                            data.setStatus(documentSnapshot.getString("userStatus"));
                            data.setProfilePic(documentSnapshot.getString("userImage"));
                            Log.i(TAG, "onSuccess: "+data.getName()+data.getStatus());
                         //   Toast.makeText(data, ""+data.getName()+data.getStatus(), Toast.LENGTH_SHORT).show();
                        }
                    })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.i(TAG, "onFailure: "+e.toString());
                                }
                            })      ;

                     startActivity(new Intent(MainActivity.this,HomePage.class));
                    finish();
                }else{

                }
            }
        };



        letsGo = findViewById(R.id.main_letsGo);
            letsGo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(MainActivity.this,LoginPage.class));

                }
            });
    }

    @Override
    protected void onStart() {
        super.onStart();
        currentUser= firebaseAuth.getCurrentUser();
        firebaseAuth.addAuthStateListener(authStateListener);
    }
}
