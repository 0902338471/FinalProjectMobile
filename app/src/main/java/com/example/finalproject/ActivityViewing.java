package com.example.finalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class ActivityViewing extends AppCompatActivity {
    private static final int REQUEST_CALL = 1;
    static final int AUTOCOMPLETE_REQUEST_CODE=100;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.myitemactivity);
        onDisplayingData();
        onBackToSpecificDate();
    }

    private void onBackToSpecificDate() {
        Button backButton = findViewById(R.id.backbutton);
        final String message = getIntent().getStringExtra("positionClick");
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText nameActivity = findViewById(R.id.nameActivity);
                String backStringname = nameActivity.getText().toString();
                EditText status=findViewById(R.id.status);
                String backStatus=status.getText().toString();
                ImageView avatar=findViewById(R.id.avartar);//remember to send to firebase
                ImageView photoStatus=findViewById(R.id.content_image);//remember to send to firebase
                Intent intent = new Intent();
                intent.putExtra("nameActivityBack", backStringname);
                intent.putExtra("StatusBack",backStatus);
                intent.putExtra("positionClickBack", message);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
    }

    private void onDisplayingData() {
       // FloatingActionButton fab = findViewById(R.id.fab);
        EditText nameActivity=findViewById(R.id.nameActivity);
        Intent passIntent=getIntent();
        nameActivity.setText(passIntent.getStringExtra("nameActivity"));
        EditText status=findViewById(R.id.status);
        status.setText(passIntent.getStringExtra("Status"));
    }
}
