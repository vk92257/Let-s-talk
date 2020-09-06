package com.app.letstalk;

import androidx.activity.OnBackPressedDispatcherOwner;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.letstalk.util.UserData;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.HashMap;
import java.util.Map;

public class SettingActivity extends AppCompatActivity implements View.OnClickListener {
private ImageView userImage;
private TextView userName,userStatus;
 private Button status,image;
  private UserData  userData ;
    private int GALLRY_CODE = 1;
    private Uri uri;
    private StorageReference storageReference ;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference collectionReference=db.collection("Users");
    private String TAG="SettingActivity";


    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        userData = UserData.getInstance();

    userImage = findViewById(R.id.settingActivity_profile_photo);
    userName = findViewById(R.id.settingActivity_userName);
    userStatus = findViewById(R.id.settingActivity_status);
    status = findViewById(R.id.settingActivity_changeStatus);
    image = findViewById(R.id.settingActivity_changeImage);
        storageReference =  FirebaseStorage.getInstance().getReference();
    userName.setText(userData.getName());
    userStatus.setText(userData.getStatus());
        Picasso.get()
                .load(userData.getProfilePic())

                .into(userImage);



        image.setOnClickListener(this);
    status.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.settingActivity_changeImage:
                Intent intent   = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent,GALLRY_CODE);
                break;
            case R.id.settingActivity_changeStatus:
                break;

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == GALLRY_CODE && resultCode== RESULT_OK ){

            if (data!= null){
                 uri = data.getData();
                    userImage.setImageURI(uri);
                     final StorageReference filePath = storageReference.child("profile_image")
                            .child("profilePic.jpeg"+ Timestamp.now().getSeconds());
                            filePath.putFile(uri)
                            .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                    filePath.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                        @Override
                                        public void onSuccess(Uri uri) {
                                            String imageUri = uri.toString();
                                            UserData data1 = UserData.getInstance();
                                            data1.setProfilePic(imageUri);

                                                 Map<String,Object> object = new HashMap<>();
                                            object.put("userImage",data1.getProfilePic());


                                            collectionReference.document(data1.getUserId())
                                                    .update(object).addOnSuccessListener(new OnSuccessListener<Void>() {
                                                @Override
                                                public void onSuccess(Void aVoid) {

                                                }
                                            })
                                                    .addOnFailureListener(new OnFailureListener() {
                                                        @Override
                                                        public void onFailure(@NonNull Exception e) {

                                                        }
                                                    });


                                        }
                                    })
                                            .addOnFailureListener(new OnFailureListener() {
                                                @Override
                                                public void onFailure(@NonNull Exception e) {

                                                }
                                            });
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {

                                }
                            });

            }else{

            }
        }

    }

    @Override
    protected void onStart() {
        super.onStart();

        Log.i(TAG, "onStart: "+uri);


    }
}
