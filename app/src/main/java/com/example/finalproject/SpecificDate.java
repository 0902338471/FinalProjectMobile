package com.example.finalproject;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.util.ArrayList;

public class SpecificDate extends AppCompatActivity implements ActivityAdapter.ItemClickListener {
    ActivityAdapter adapter;
    int positionClick=-1;
    private int mIndexDate;
    private User mUser;
    private ActivityDay mActivityDate;
    private FirebaseDatabase mFirebaseDatabase;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private DatabaseReference myRef;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_specific_date);
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        myRef = mFirebaseDatabase.getReference();
        getDataFromIntent();
        //getActivityDate();
        //loadingDateData();
        updatingView();
    }

    private void getDataFromIntent() {
        mUser=(User)getIntent().getSerializableExtra("userClass");
        mActivityDate=(ActivityDay)getIntent().getSerializableExtra("mActivityDay") ;
        Log.d("prepar2",String.valueOf(mActivityDate.getSizeOfSchedule()));
        mIndexDate=Integer.parseInt(getIntent().getStringExtra("IndexOfDate"));
        mUser.UpdateCalendar(mIndexDate,mActivityDate);
        Log.d("prepare3",String.valueOf(mUser.getuserCalendar().get(mIndexDate).getSizeOfSchedule()));
    }

    private void getActivityDate()
    {
        Log.d("indexDate",String.valueOf(mIndexDate));
        mActivityDate=mUser.getuserCalendar().get(mIndexDate);
        Log.d("itisnull",mActivityDate.getDate().getMonth());
        //String tmp=mActivityDate.getDaySchedule().get(0).getNameActivity();
        //Log.d("Name here","gg"+tmp);
    }
    /*private void loadingDateData() {
        //querying data from firebase and displaying on the screen
        String myDate=getIntent().getStringExtra("CurrentDate");
        String myMonth=getIntent().getStringExtra("CurrentMonth");
        String myYear=getIntent().getStringExtra("CurrentYear");
        MyDate currentDate=new MyDate(myDate,myMonth,myYear);
        activityDay.setDate(currentDate);
    }*/
    private void updatingView()
    {
        RecyclerView myRecyclerView=(RecyclerView)findViewById(R.id.recycle_view);
        ImageView addingButton=(ImageView) findViewById(R.id.adding_button);
        myRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter=new ActivityAdapter(this,mActivityDate.getDaySchedule());
        Log.d("nameacti",String.valueOf((mActivityDate.getSizeOfSchedule())));
        adapter.setClickListener(this);
        final Context context=this;
        myRecyclerView.setAdapter(adapter);
        addingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("aa","You've clicked here");
                Intent i =new Intent(context,ActivityViewing.class);
                i.putExtra("nameActivity","Empty");
                i.putExtra("Status","Enter Status");
                i.putExtra("positionClick","NoPosition");
                startActivityForResult(i,1);
            }
        });
    }
    @Override
    public void onItemClick(View view, int position) {
        Intent i=new Intent(this,ActivityViewing.class);
        i.putExtra("nameActivity",adapter.getItem(position).getNameActivity());
        i.putExtra("Status",adapter.getItem(position).getStatus());
       // i.putExtra("Avatar",adapter.getItem(position).getAvatar().getFormat());
       // i.putExtra("photoStatus",adapter.getItem(position).getPhotoStatus().getFormat());
        i.putExtra("positionClick",String.valueOf(position));
        positionClick=position;
        startActivityForResult(i,1);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Check that it is the SecondActivity with an OK result
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                // Get String data from Intent
                String returnStringname = data.getStringExtra("nameActivityBack");
                String returnStatus=data.getStringExtra("StatusBack");
                // Set text view with string
                myItemActivity tmp=new myItemActivity(returnStringname,returnStatus,mActivityDate.getDate());
                String checking=data.getStringExtra("positionClickBack");
                String uid=getIntent().getStringExtra("Uid");
                if(checking.equals("NoPosition"))
                {
                    //listItemsActivity.add(listItemsActivity.size(),tmp);
                    mActivityDate.addingItemIntoDaySchedule(tmp);
                    Log.d("Hello I'm Here","ss");
                    //adapter.notifyItemInserted(listItemsActivity.size()-1);
                    adapter.notifyItemInserted(mActivityDate.getSizeOfSchedule()-1);
                    Log.d("size in no position",String.valueOf(mActivityDate.getSizeOfSchedule()));
                }
                else
                {
                    Log.d("Check",data.getStringExtra("positionClickBack"));
                    int position=Integer.parseInt(checking);
                   // listItemsActivity.set(position,tmp);
                    mActivityDate.setItemInDaySchedule(position,tmp);
                    adapter.notifyItemChanged(position);
                }
                mUser.UpdateCalendar(mIndexDate,mActivityDate);
                Log.d("userID","aa"+mUser.getUserID());
                myRef.child("Users/").child(mUser.getUserID()+"/").setValue(mUser);
            }
            if(resultCode==RESULT_CANCELED)
            {
                Log.d("Cancelled","ss");
            }
        }
    }
}
