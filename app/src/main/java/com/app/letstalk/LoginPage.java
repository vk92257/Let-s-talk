package com.app.letstalk;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Process;
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
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.auth.User;

import java.util.Collection;

public class LoginPage extends AppCompatActivity {
    private static final String TAG ="Loginpage" ;
    private EditText email,password;
private Button  createNewAccount,logIn;
private ProgressBar progressBar ;
private FirebaseAuth firebaseAuth;
private FirebaseUser currentUser;
private FirebaseFirestore db = FirebaseFirestore.getInstance();
private CollectionReference collectionReference = db.collection("Users");
    private DocumentReference documentReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);
            firebaseAuth= FirebaseAuth.getInstance();
        progressBar= findViewById(R.id.loginPage_progressBar);
        email = findViewById(R.id.loginPage_email);
        password= findViewById(R.id.loginPage_password);
        createNewAccount=findViewById(R.id.loginPage_create_new_account);
        createNewAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginPage.this,CreateNewAccount.class));
            }
        });

        logIn= findViewById(R.id.loginPage_logIn);
        logIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                progressBar.setVisibility(View.VISIBLE);
               String eMail = email.getText().toString().trim();
               String pswrd = password.getText().toString().trim();

                if (!TextUtils.isEmpty(eMail)&&!TextUtils.isEmpty(pswrd)){
                    sginIn(eMail,pswrd);
                }else{
                    Toast.makeText(LoginPage.this, "all Feds" +
                            "are Required", Toast.LENGTH_SHORT).show();
                }


                //startActivity(new Intent(LoginPage.this,HomePage.class));
            }
        });

    }

    private void sginIn(String eMail, String pswrd) {
    firebaseAuth.signInWithEmailAndPassword(eMail,pswrd)
            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()){}else{
                        currentUser = firebaseAuth.getCurrentUser();
                        final String uid = currentUser.getUid();
                        collectionReference.document(uid)
                        .get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                            @Override
                            public void onSuccess(DocumentSnapshot documentSnapshot) {
                                UserData data = UserData.getInstance();
                                data.setUserId(uid);
                                data.setName(documentSnapshot.getString("userName"));
                                data.setStatus(documentSnapshot.getString("userStatus"));
                                Log.i(TAG, "onSuccess: "+data.getName()+data.getStatus());
                                Toast.makeText(data, ""+data.getName()+data.getStatus(), Toast.LENGTH_SHORT).show();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.i(TAG, "onFailure: "+e.toString());
                            }
                        })      ;




                       progressBar.setVisibility(View.INVISIBLE);
                        Log.i("LoginPage", "onComplete: failed");
                    }
                }
            })
            .addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                progressBar.setVisibility(View.INVISIBLE);
                    Log.i("LoginPage", "onFailure: "+e.toString());
                }
            });


    }

    @Override
    protected void onStart() {
        super.onStart();
        currentUser = firebaseAuth.getCurrentUser();
    }
}
