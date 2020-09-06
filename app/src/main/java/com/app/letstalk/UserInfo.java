package com.app.letstalk;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.app.letstalk.util.UserData;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Map;

public class UserInfo extends AppCompatActivity {
    private static final String TAG ="UserInfo" ;
    private TextView textView,status ;
private Button  sendRequest;
private ImageView userImage;
private String Uid;
private String currentState;
private FirebaseFirestore db = FirebaseFirestore.getInstance();
private CollectionReference collectionReference = db.collection("Friends_req");
private UserData userData ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);
        Bundle bundle
                = getIntent().getExtras();
        textView = findViewById(R.id.User_Info_userName);
        textView.setText(bundle.getString("userName"));
        status = findViewById(R.id.User_Info_userStatus);
        userImage = findViewById(R.id.User_Info_profilePhot);
        status.setText(bundle.getString("userStatus"));
        Picasso.get().load(bundle.getString("userProfilePic")).into(userImage);
        sendRequest = findViewById(R.id.User_Info_sendRequest);
            Uid=bundle.getString("id");
            currentState = "not_friends";
        if (userData == null)
            userData= UserData.getInstance();

          sendRequest.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (currentState.equalsIgnoreCase("not_friends")){
                        sendRequest.setEnabled(false);
                    sendFriendRequest();


            }else{

            }
        }





              private void sendFriendRequest() {
                  Map<Object,String> map = new HashMap<>();
                  map.put("userId",Uid);
                  map.put("request_type","sent");
                  collectionReference.document(userData.getUserId()).set(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                      @Override
                      public void onComplete(@NonNull Task<Void> task) {
                          if (task.isSuccessful()){


                              Map<Object,String> map = new HashMap<>();
                              map.put("userId",userData.getUserId());
                              map.put("request_type","recieved");
                              collectionReference.document(Uid).set(map).addOnSuccessListener(new OnSuccessListener<Void>() {
                                  @Override
                                  public void onSuccess(Void aVoid) {
                                      sendRequest.setEnabled(true);
                                      sendRequest.setText("Cancel Friend Request");
                                      currentState="request_sent";
                                      Toast.makeText(UserInfo.this, "success", Toast.LENGTH_SHORT).show();
                                  }
                              }).addOnFailureListener(new OnFailureListener() {
                                  @Override
                                  public void onFailure(@NonNull Exception e) {
                                      Toast.makeText(UserInfo.this, "failed", Toast.LENGTH_SHORT).show();
                                  }
                              });
                          }else{
                              Toast.makeText(userData, "failed", Toast.LENGTH_SHORT).show();
                          }
                      }
                  });

              }
          });



    }
}
