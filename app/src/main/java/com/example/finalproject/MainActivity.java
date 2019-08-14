package com.example.finalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    CalendarView simpleCalendarView;
    ArrayList<ActivityDay> tmp=new ArrayList<>();
    private User mUser;
    final boolean dataReady=false;
    private FirebaseDatabase mFirebaseDatabase;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private DatabaseReference myRef;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        /*Button camera = (Button) findViewById(R.id.camera);
        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, 1);
            }
        });*/
        final String userId=getIntent().getStringExtra("UserID");
        Log.d("UserID",userId);
        final String email=getIntent().getStringExtra("Email");
        final String pass=getIntent().getStringExtra("Pass");
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        myRef = mFirebaseDatabase.getReference();
        myRef.child("Users/").child(userId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);
                if(user==null)
                {
                    Log.d("nana","value");
                    mUser =new User(email,userId,pass);
                    myRef.child("Users/").child(userId+"/").setValue(mUser);
                }
                else
                {
                    Log.d("userEmail","aa"+user.getEmail());
                    Log.d("userPass","aa"+user.getUserPassword());
                    Log.d("userID","aa"+user.getUserID());
                    mUser=user;
                }
                Log.d("mEmail","aa"+mUser.getEmail());
                Log.d("mPass","aa"+mUser.getUserPassword());
                Log.d("mEmail","aa"+mUser.getEmail());
                Intent i = new Intent(MainActivity.this, CalendarActivity.class);
                i.putExtra("userClass",mUser);
                startActivity(i);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });
       /* Log.d("emailout","gg"+mUser.getEmail());
        Intent i = new Intent(this, CalendarActivity.class);
        i.putExtra("userClass",mUser);
        startActivity(i);*/
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1&& resultCode == RESULT_OK) {
            Bitmap photo = (Bitmap) data.getExtras().get("data");
            ImageView imageView=(ImageView)findViewById(R.id.imageview);
            imageView.setImageBitmap(photo);
        }
    }
}
