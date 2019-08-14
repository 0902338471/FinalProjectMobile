package com.example.finalproject;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.View;
import android.widget.CalendarView;
import android.widget.Toast;

public class CalendarActivity extends AppCompatActivity {
    CalendarView simpleCalendarView;
    User mUser;
    private FirebaseDatabase mFirebaseDatabase;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private DatabaseReference myRef;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);
        Log.d("ss","On Calendar");
        simpleCalendarView=(CalendarView)findViewById(R.id.simpleCalendarView);
        onPreparingUser();
        simpleCalendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView calendarView, int year, int month, int dayOfMonth) {
                callingSpecificActivity(calendarView,year,month,dayOfMonth);
                //Toast.makeText(getApplicationContext(), dayOfMonth + "/" + month + "/" + year, Toast.LENGTH_LONG).show();
            }
        });
    }

    private void onPreparingUser() {

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
                /*Intent i = new Intent(MainActivity.this, CalendarActivity.class);
                i.putExtra("userClass",mUser);
                startActivity(i);*/
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });
    }

    private void callingSpecificActivity(CalendarView calendarView, int year, int month, int dayOfMonth) {
        Intent i=new Intent(this,SpecificDate.class);
        //mUser=(User)getIntent().getSerializableExtra("userClass");
        onPreparingUser();
        Log.d("ss31",mUser.getEmail());
        int index=convertFromDateToIndex(month,dayOfMonth);
        Log.d("index",String.valueOf(index));
        Log.d("prepare0",String.valueOf(mUser.getuserCalendar().get(index).getSizeOfSchedule()));
        mUser.activateSpecificDate(index,dayOfMonth,month);
        i.putExtra("userClass",mUser);
        ActivityDay passActi=mUser.getuserCalendar().get(index);
        Log.d("prepare1",String.valueOf(mUser.getuserCalendar().get(index).getSizeOfSchedule()));
        i.putExtra("mActivityDay",passActi);
        i.putExtra("CurrentDate",String.valueOf(dayOfMonth));
        i.putExtra("CurrentYear",String.valueOf(year));
        i.putExtra("CurrentMonth",String.valueOf(month));
        i.putExtra("IndexOfDate",String.valueOf(index));
        startActivity(i);
    }
    public int convertFromDateToIndex(int month,int dayofMonth)
    {
        int val=0;
        if(month==1)
            val=dayofMonth;
        if(month==2)
            val= 31+dayofMonth;
        if(month==3)
            val= 31+28+dayofMonth;
        if(month==4)
            val= 31+28+31+dayofMonth;
        if(month==5)
            val= 31+28+31+30+dayofMonth;
        if(month==6)
            val= 31+28+31+30+31+dayofMonth;
        if(month==7)
            val= 31+28+31+30+31+30+dayofMonth;
        if(month==8)
            val= 31+28+31+30+31+30+31+dayofMonth;
        if(month==9)
            val= 31+28+31+30+31+30+31+31+dayofMonth;
        if(month==10)
            val= 31+28+31+30+31+30+31+31+30+dayofMonth;
        if(month==11)
            val=31+28+31+30+31+30+31+31+30+31+dayofMonth;
        if(month==12)
            val= 31+28+31+30+31+30+31+31+30+31+30+dayofMonth;
        return val;
    }
}
