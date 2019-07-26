package com.example.finalproject;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;

public class SpecificDate extends AppCompatActivity implements ActivityAdapter.ItemClickListener {
    ActivityAdapter adapter;
    private ArrayList<myItemActivity> listItemsActivity=new ArrayList<>();
    private ActivityDay activityDay=new ActivityDay();
    int positionClick=-1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_specific_date);
        loadingData();
        updatingView();
    }

    private void loadingData() {
        //querying data from firebase and displaying on the screen
        String myDate=getIntent().getStringExtra("CurrentDate");
        String myMonth=getIntent().getStringExtra("CurrentMonth");
        String myYear=getIntent().getStringExtra("CurrentYear");
        MyDate currentDate=new MyDate(myDate,myMonth,myYear);
        activityDay.setDate(currentDate);
    }
    private void updatingView()
    {
        RecyclerView myRecyclerView=(RecyclerView)findViewById(R.id.recycle_view);
        Button addingButton=(Button)findViewById(R.id.addingButton);
        myRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter=new ActivityAdapter(this,listItemsActivity);
        adapter.setClickListener(this);
        final Context context=this;
        myRecyclerView.setAdapter(adapter);
        addingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("aa","You've clicked here");
                Intent i =new Intent(context,ActivityViewing.class);
                i.putExtra("nameActivity","Empty");
                i.putExtra("positionClick","NoPosition");
                startActivityForResult(i,1);
            }
        });
    }
    @Override
    public void onItemClick(View view, int position) {
        Intent i=new Intent(this,ActivityViewing.class);
        i.putExtra("nameActivity",adapter.getItem(position).getNameActivity());
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
                // Set text view with string
                myItemActivity tmp=new myItemActivity(returnStringname);
                String checking=data.getStringExtra("positionClickBack");
                String uid=getIntent().getStringExtra("Uid");
                if(checking.equals("NoPosition"))
                {
                    listItemsActivity.add(listItemsActivity.size(),tmp);
                    Log.d("Hello I'm Here","ss");
                    adapter.notifyItemInserted(listItemsActivity.size()-1);
                }
                else
                {
                    Log.d("Check",data.getStringExtra("positionClickBack"));
                    int position=Integer.parseInt(checking);
                    listItemsActivity.set(position,tmp);
                    adapter.notifyItemChanged(position);
                }
                /*thisuser.setList(listItems);
                myRef.child("Users/").child(uid+"/").setValue(thisuser);*/
            }
            if(resultCode==RESULT_CANCELED)
            {
                Log.d("Cancelled","ss");
            }
        }
    }
}
