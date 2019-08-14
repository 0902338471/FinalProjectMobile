package com.example.finalproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {

    TextInputEditText username;
    TextInputEditText password;
    Button signin;
    TextView create_account;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthState;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

    username=findViewById(R.id.input_username);
    password=findViewById(R.id.input_password);
    signin=findViewById(R.id.sign_in);
    create_account=findViewById(R.id.create_account);

    mAuth=FirebaseAuth.getInstance();
    mAuthState=new FirebaseAuth.AuthStateListener() {
        @Override
        public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
            FirebaseUser firebaseUser= mAuth.getCurrentUser();
            if (firebaseUser!=null){
                // users signin
            }
            else {
                // signout
            }
        }
    };


    signin.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            String nemail=username.getText().toString();
            String npassword=password.getText().toString();

            if (nemail.isEmpty()){
                username.setError("Username can not be empty");
                username.requestFocus();
            }
            else if (npassword.isEmpty()){
                password.setError("Password can not be empty");
                password.requestFocus();
            }
            else{
                mAuth.signInWithEmailAndPassword(nemail,npassword).addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (!task.isSuccessful()){
                            Toast.makeText(LoginActivity.this, "Wrong username or password", Toast.LENGTH_SHORT).show();
                        }
                        else{
                            Intent i= new Intent(LoginActivity.this,CalendarActivity.class);
                            String UserID= task.getResult().getUser().getUid();
                            String UserEmail=username.getText().toString();
                            String UserPasswod=password.getText().toString();
                            i.putExtra("UserID",UserID);
                            i.putExtra("Email",UserEmail);
                            i.putExtra("Pass",UserPasswod);
                            startActivity(i);
                        }
                    }
                });
            }
        }
    });
    }
}
