package com.example.finalproject;

import android.content.Context;
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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;

public class SpecificDate extends AppCompatActivity implements ActivityAdapter.ItemClickListener {
    ActivityAdapter adapter;
    int positionClick=-1;
    private int mIndexDate;
    private User mUser;
    private MyDate curDate;
    private ActivityDay mActivityDate;
    private FirebaseDatabase mFirebaseDatabase;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private DatabaseReference myRef;
    public static final String Database_Path = "UserImageDatabase1";
    RecyclerView myRecyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_specific_date);
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        myRef = mFirebaseDatabase.getReference(SpecificDate.Database_Path);
        mActivityDate=new ActivityDay();
        //
        myRecyclerView=(RecyclerView)findViewById(R.id.recycle_view);
        myRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        //
        getDataFromIntent();
        AccesingDatabase();
        //getActivityDate();
        //loadingDateData();
        updatingView();
    }

    private void AccesingDatabase() {
        myRef.child("Users/").child(mUser.getUserID())/*.child(String.valueOf(mIndexDate))*/.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                mUser=snapshot.getValue(User.class);
                mActivityDate=mUser.getuserCalendar().get(mIndexDate);
                if(mActivityDate==null)
                {
                    Log.d("itisnull","meme");
                    mActivityDate=new ActivityDay();
                }
                else
                {
                    Log.d("itisnotnull","aa"+String.valueOf(mActivityDate.getSizeOfSchedule()));
                }
                mActivityDate.setDate(curDate);
                adapter=new ActivityAdapter(SpecificDate.this,mActivityDate.getDaySchedule());
               // adapter = new RecyclerViewAdapter(getApplicationContext(), list);
                adapter.setClickListener(SpecificDate.this);
                myRecyclerView.setAdapter(adapter);
                mActivityDate.setDate(curDate);
                Toast.makeText(getApplicationContext(), "Successful Retrieving Data!!!", Toast.LENGTH_LONG).show();
                //recyclerView.setAdapter(adapter);

                // Hiding the progress dialog.
               // progressDialog.dismiss();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

                // Hiding the progress dialog.
               // progressDialog.dismiss();

            }
        });
    }

    private void getDataFromIntent() {
        mUser=(User)getIntent().getSerializableExtra("userClass");
        //mActivityDate=(ActivityDay)getIntent().getSerializableExtra("mActivityDay") ;
        Log.d("prepar2",String.valueOf(mActivityDate.getSizeOfSchedule()));
        curDate=(MyDate)getIntent().getSerializableExtra("StructureOfDate");
        if(curDate==null)
            Log.d("nullcmnr","aa");
        mIndexDate=Integer.parseInt(getIntent().getStringExtra("IndexOfDate"));
        //mUser.UpdateCalendar(mIndexDate,mActivityDate);
        //Log.d("prepare3",String.valueOf(mUser.getuserCalendar().get(mIndexDate).getSizeOfSchedule()));
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
       // RecyclerView myRecyclerView=(RecyclerView)findViewById(R.id.recycle_view);
        ImageView addingButton=(ImageView) findViewById(R.id.adding_button);
       // myRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        //adapter=new ActivityAdapter(this,mActivityDate.getDaySchedule());
        Log.d("nameacti",String.valueOf((mActivityDate.getSizeOfSchedule())));
        final Context context=this;
        //adapter.setClickListener(this);
        //myRecyclerView.setAdapter(adapter);
        addingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("aa","You've clicked here");
                Intent i =new Intent(context,ActivityViewing.class);
                //i.putExtra("nameActivity","Empty");
                //i.putExtra("Status","Enter Status");
                i.putExtra("positionClick","NoPosition");
                i.putExtra("DateStructure",curDate);
                i.putExtra("UserUID",mUser.getUserID());
                i.putExtra("IndexPass",String.valueOf(mIndexDate));
                i.putExtra("UserClass",mUser);
                Log.d("Index",String.valueOf(mIndexDate));
                i.putExtra("SizePass",String.valueOf(mActivityDate.getSizeOfSchedule()));
                Log.d("Size",String.valueOf(mActivityDate.getSizeOfSchedule()));
                startActivityForResult(i,1);
            }
        });
    }
    @Override
    public void onItemClick(View view, int position) {
        Intent i=new Intent(this,ActivityViewing.class);
        i.putExtra("nameActivity",adapter.getItem(position).getNameActivity());
        i.putExtra("Status",adapter.getItem(position).getStatus());
        //i.putExtra("Avatar",adapter.getItem(position).getAvatar().getFormat());
        i.putExtra("photoStatus",adapter.getItem(position).getPhotoStatus());
        i.putExtra("DateStructure",curDate);
        i.putExtra("IndexPass",String.valueOf(mIndexDate));
        i.putExtra("UserClass",mUser);
        i.putExtra("positionClick",String.valueOf(position));
        i.putExtra("UserUID",mUser.getUserID());
        i.putExtra("SizePass",String.valueOf(mActivityDate.getSizeOfSchedule()));
        //i.putExtra("Date",adapter.getItem(position).getStringDate());
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
                String returnDate=data.getStringExtra("DateBack");
                String returnphotoURL=data.getStringExtra("photoURLBack");
                Log.d("photoback","aa"+returnphotoURL);
                // Set text view with string
                myItemActivity tmp=new myItemActivity(String.valueOf(mActivityDate.getSizeOfSchedule()+1),returnStringname,returnStatus,mActivityDate.getDate(),returnphotoURL);
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
