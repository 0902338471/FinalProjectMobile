package com.example.finalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class ActivityViewing extends AppCompatActivity {
    private static final int REQUEST_CALL = 1;
    static final int AUTOCOMPLETE_REQUEST_CODE=100;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewing);
        onDisplayingData();
        onBackToSpecificDate();
    }

    private void onBackToSpecificDate() {
        FloatingActionButton fab = findViewById(R.id.fab);
        final String message = getIntent().getStringExtra("positionClick");
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText nameActivity = findViewById(R.id.activity_name);
                String backStringname = nameActivity.getText().toString();
                Intent intent = new Intent();
                intent.putExtra("nameActivityBack", backStringname);
                intent.putExtra("positionClickBack", message);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
    }

    private void onDisplayingData() {
        FloatingActionButton fab = findViewById(R.id.fab);
        EditText nameActivity=findViewById(R.id.activity_name);
        Intent passIntent=getIntent();
        nameActivity.setText(passIntent.getStringExtra("nameActivity"));
    }
}
