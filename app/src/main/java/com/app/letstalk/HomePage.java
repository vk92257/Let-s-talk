package com.app.letstalk;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.app.letstalk.alluserRecycler.MessageAdapter;
import com.app.letstalk.util.UserData;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class HomePage extends AppCompatActivity {
private Toolbar toolbar;
private  String TAG ="HOmePage";
private ViewPager viewPager;
private SectionPageAdapter sectionPageAdapter;
private TabLayout tabLayout;
private FirebaseAuth firebaseAuth ;
private FirebaseUser firebaseUser;
private FirebaseAuth.AuthStateListener authStateListener;

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference collectionReference = db.collection("Users");
    private DocumentReference documentReference;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private MessageAdapter messageAdapter;

@Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
    // recycler view data



    firebaseAuth = FirebaseAuth.getInstance();
       authStateListener= new FirebaseAuth.AuthStateListener() {
           @Override
           public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {

               firebaseUser= firebaseAuth.getCurrentUser();
               if (firebaseUser==null){

               }else{

               }

           }
       };

        toolbar = findViewById(R.id.homepage_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("LetsTalk");

        viewPager = findViewById(R.id.HomePage_viewPager);
        sectionPageAdapter = new SectionPageAdapter(this,getSupportFragmentManager());
        viewPager.setAdapter(sectionPageAdapter);
        tabLayout = findViewById(R.id.homePage_tabLayout);
        tabLayout.setupWithViewPager(viewPager);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_item,menu);
        return super.onCreateOptionsMenu(menu);


    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.menuItem_singOut:

                firebaseAuth.signOut();

                startActivity(new Intent(
                        HomePage.this,LoginPage.class));
                Toast.makeText(this, "singout", Toast.LENGTH_SHORT).show();
                finish();

                break;
            case R.id.menuItem_accountSetting:
                Toast.makeText(this, "accountsetting", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(HomePage.this,SettingActivity.class));

                break;
            case R.id.menuItem_allUser:
                Toast.makeText(this, "alluser", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(HomePage.this,AllUsers.class));
                break;
        }



        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onStart() {
        super.onStart();
        firebaseUser = firebaseAuth.getCurrentUser();
        firebaseAuth.addAuthStateListener(authStateListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
    }
}
