package com.app.letstalk;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.app.letstalk.util.UserData;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import org.w3c.dom.Text;

import java.util.HashMap;
import java.util.Map;

public class CreateNewAccount extends AppCompatActivity {
    private String TAG = "CreateNewAccount";
    private EditText userName ,email,password;
    private Button singUp;
    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener authStateListener;
    private FirebaseUser currentUser;
    private ProgressBar progressBar;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference collectionReference = db.collection("Users");
    private DocumentReference documentReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



        setContentView(R.layout.activity_create_new_account);
       progressBar = findViewById(R.id.createNewAccoutn_progressBar);
        firebaseAuth = FirebaseAuth.getInstance();
        userName=findViewById(R.id.createNewAccount_enter_name);
        email = findViewById(R.id.createNewAccount_enter_email);
        password = findViewById(R.id.createNewAccount_enter_password);
        singUp= findViewById(R.id.create_new_account_create_new_account);
        singUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             progressBar.setVisibility(View.VISIBLE);
             String user_name = userName.getText().toString().trim();
             String eMail =email.getText().toString().trim();
              String pswrd = password.getText().toString().trim();
               if(!TextUtils.isEmpty(eMail)
                       &&!TextUtils.isEmpty(pswrd)
                       &&!TextUtils.isEmpty(user_name)){
                    createNewUser(user_name,eMail,pswrd);
                }else{
                   Toast.makeText(CreateNewAccount.this, "all fields are required", Toast.LENGTH_SHORT).show();
               }
                 }
        });

        authStateListener  = new FirebaseAuth.AuthStateListener() {
    @Override
    public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
        currentUser = firebaseAuth.getCurrentUser();


    }
};

    }

    private void createNewUser(final String user_name, String email, String password) {
               if (!TextUtils.isEmpty(email)
               &&!TextUtils.isEmpty(password)
               &&!TextUtils.isEmpty(user_name)){
                   progressBar.setVisibility(View.VISIBLE);
                   firebaseAuth.createUserWithEmailAndPassword(email,password)
                           .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                               @Override
                               public void onComplete(@NonNull Task<AuthResult> task) {
                                   if (task.isSuccessful()){
                                       currentUser=firebaseAuth.getCurrentUser();
                                       final String currentUid = currentUser.getUid();




                                       Map<String,String> object = new HashMap<>();
                                       object.put("userName",user_name);
                                       object.put("userImage","something");
                                       object.put("userStatus","Hey there i am Using Let'talk");
                                       Log.i(TAG, "onComplete: "+user_name+"user id =="+currentUid);
                                        
                                       collectionReference
                                               .document(currentUid)
                                               .set(object)
                                               .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                   @Override
                                                   public void onSuccess(Void aVoid) {
                                                     progressBar.setVisibility(View.INVISIBLE);
                                                     startActivity(new Intent(
                                                             CreateNewAccount.this,HomePage.class));
                                                     finish();;
                                                   }
                                               })
                                               .addOnFailureListener(new OnFailureListener() {
                                                   @Override
                                                   public void onFailure(@NonNull Exception e) {
                                                       Log.i(TAG, "onFailure: "+e.toString());
                                                   }
                                               });
                                   }else{

                                   }
                               }
                           });
               }else{


               }
    }


    @Override
    protected void onStart() {
        super.onStart();
    currentUser= firebaseAuth.getCurrentUser();
    firebaseAuth.addAuthStateListener(authStateListener);

    }
}
