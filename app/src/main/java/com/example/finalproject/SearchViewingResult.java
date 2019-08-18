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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;

public class SearchViewingResult extends AppCompatActivity implements ActivityAdapter.ItemClickListener{
    ActivityAdapter adapter;
    int positionClick=-1;
    private int mIndexDate;
    private ArrayList<myItemActivity> ActivityResult;
    private String UserID;
    private User mUser;
    private MyDate curDate;
    private String textSearch;
    private ActivityDay mActivityDate;
    private FirebaseDatabase mFirebaseDatabase;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private DatabaseReference myRef;
    private myItemActivity ItemClicked;
    public static final String Database_Path = "UserImageDatabase1";
    int indexDate;
    RecyclerView myRecyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_viewing_result);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        onSettingUpLocalData();
        onReceivingDataIntent();
        onLoadingDataFromFireBase();
    }

    private void onSettingUpLocalData() {
        ActivityResult=new ArrayList<>();
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        myRef = mFirebaseDatabase.getReference(SpecificDate.Database_Path);
        myRecyclerView=(RecyclerView)findViewById(R.id.recycle_view_search);
        myRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private void onReceivingDataIntent() {
        UserID=getIntent().getStringExtra("UserID");
        Log.d("userpassed","aa"+UserID);
        textSearch=getIntent().getStringExtra("QueryActivity");
        Log.d("querypassed","aa"+textSearch);
    }

    private void onLoadingDataFromFireBase() {
            myRef.child("Users/").child(UserID)/*.child(String.valueOf(mIndexDate))*/.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot snapshot) {
                    mUser=snapshot.getValue(User.class);
                    for(int i=0;i<mUser.getuserCalendar().size();i++)
                    {
                        ActivityDay tmp=mUser.getuserCalendar().get(i);
                        for(int j=0;j<tmp.getSizeOfSchedule();j++)
                        {
                            myItemActivity currentActi=tmp.getDaySchedule().get(j);
                            Log.d("Month","aa"+currentActi.getDateInfoOfActivity().getMonth());
                            Log.d("Name","aa"+currentActi.getNameActivity());
                            currentActi.setPositionOnOneDay(j);
                            if(currentActi.getNameActivity().equals(textSearch))
                            {
                                ActivityResult.add(currentActi);
                                Log.d("size2",String.valueOf(ActivityResult.size()));
                                Log.d("Day ",String.valueOf(i));
                                Log.d("Number",String.valueOf(j));
                            }
                        }
                    }
                    //mActivityDate=mUser.getuserCalendar().get(mIndexDate);
                    if(ActivityResult.size()==0)
                    {
                        Toast.makeText(getApplicationContext(), "Result not found", Toast.LENGTH_LONG).show();
                    }
                    else
                    {
                        Toast.makeText(getApplicationContext(), "Found "+String.valueOf(ActivityResult.size())+"activity result!!", Toast.LENGTH_LONG).show();

                    }
                   // mActivityDate.setDate(curDate);
                    adapter=new ActivityAdapter(SearchViewingResult.this,ActivityResult);
                    // adapter = new RecyclerViewAdapter(getApplicationContext(), list);
                    adapter.setClickListener(SearchViewingResult.this);
                    myRecyclerView.setAdapter(adapter);
                    //mActivityDate.setDate(curDate);
                    //Toast.makeText(getApplicationContext(), "Successful Retrieving Data!!!", Toast.LENGTH_LONG).show();
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
    @Override
    public void onItemClick(View view, int position) {
        Intent i=new Intent(this,ActivityViewingSearch.class);
        ItemClicked=adapter.getItem(position);
        curDate=ItemClicked.getDateInfoOfActivity();
        int month=Integer.parseInt(ItemClicked.getDateInfoOfActivity().getMonth());
        int day=Integer.parseInt(ItemClicked.getDateInfoOfActivity().getDayOfMonth());
        indexDate=convertFromDateToIndex(month,day);
        Log.d("myindex","aa"+String.valueOf(indexDate));
        mActivityDate=mUser.getuserCalendar().get(indexDate);
        i.putExtra("nameActivity",adapter.getItem(position).getNameActivity());
        i.putExtra("Status",adapter.getItem(position).getStatus());
        //i.putExtra("Avatar",adapter.getItem(position).getAvatar().getFormat());
        i.putExtra("photoStatus",adapter.getItem(position).getPhotoStatus());
        i.putExtra("DateStructure",curDate);
        i.putExtra("IndexPass",String.valueOf(indexDate));
        i.putExtra("UserClass",mUser);
        i.putExtra("positionClick",String.valueOf(position));
        i.putExtra("UserUID",mUser.getUserID());
        //i.putExtra("SizePass",String.valueOf(mActivityDate.getSizeOfSchedule()));
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
                //String returnDate=data.getStringExtra("DateBack");
                String returnphotoURL=data.getStringExtra("photoURLBack");
                MyDate returndate=ItemClicked.getDateInfoOfActivity();
                Log.d("photoback","aa"+returnphotoURL);
                // Set text view with string
                myItemActivity tmp=new myItemActivity(String.valueOf(mActivityDate.getSizeOfSchedule()+1),returnStringname,returnStatus,returndate,returnphotoURL);
                String checking=data.getStringExtra("positionClickBack");
                String uid=getIntent().getStringExtra("Uid");
                /*if(checking.equals("NoPosition"))
                {
                    //listItemsActivity.add(listItemsActivity.size(),tmp);
                    mActivityDate.addingItemIntoDaySchedule(tmp);
                    Log.d("Hello I'm Here","ss");
                    //adapter.notifyItemInserted(listItemsActivity.size()-1);
                    adapter.notifyItemInserted(mActivityDate.getSizeOfSchedule()-1);
                    Log.d("size in no position",String.valueOf(mActivityDate.getSizeOfSchedule()));
                }
                else
                {*/
                Log.d("Check",data.getStringExtra("positionClickBack"));
                int position=Integer.parseInt(checking);
                // listItemsActivity.set(position,tmp);
                mActivityDate.setItemInDaySchedule(ItemClicked.getPositionOnOneDay(),tmp);
                //adapter.notifyItemChanged(position);
                mUser.UpdateCalendar(indexDate,mActivityDate);
                Log.d("userID","aa"+mUser.getUserID());
                myRef.child("Users/").child(mUser.getUserID()+"/").setValue(mUser);
                onSettingUpLocalData();
                Log.d("size1",String.valueOf(ActivityResult.size()));
                //onLoadingDataFromFireBase();
            }
            if(resultCode==RESULT_CANCELED)
            {
                Log.d("Cancelled","ss");
            }
        }

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


