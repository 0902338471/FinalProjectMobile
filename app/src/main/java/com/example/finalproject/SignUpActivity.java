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

public class SignUpActivity extends AppCompatActivity {

    TextInputEditText email;
    TextInputEditText password;
    TextInputEditText cpassword;
    Button signup;
    TextView login_act;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        email=findViewById(R.id.input_su_username);
        password=findViewById(R.id.input_su_password);
        cpassword=findViewById(R.id.input_su_cpassword);
        signup=findViewById(R.id.sign_up);
        login_act=findViewById(R.id.login_act);

        mAuth=FirebaseAuth.getInstance();

        login_act.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(SignUpActivity.this,LoginActivity.class);
                startActivity(i);
            }
        });

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username=email.getText().toString();
                final String pass=password.getText().toString();
                String cpass=cpassword.getText().toString();

                if(username.isEmpty()){
                    email.setError("Please type in username");
                    email.requestFocus();
                }
                else if(pass.isEmpty()){
                    password.setError("Please type in password");
                    password.requestFocus();
                }
                else if (cpass.isEmpty()){
                    cpassword.setError("Please type in confirm password");
                    cpassword.requestFocus();
                }
                else if(!pass.equals(cpass)){
                    cpassword.setError("Confirm password must match password");
                    cpassword.requestFocus();
                }
                else if (!(username.isEmpty() && pass.isEmpty()) && (pass.equals(cpass))){
                    mAuth.createUserWithEmailAndPassword(username,pass).addOnCompleteListener(SignUpActivity.this,
                            new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (!task.isSuccessful()){
                                        Toast.makeText(SignUpActivity.this, "failed to sign up", Toast.LENGTH_SHORT).show();
                                    }
                                    else{
                                        Toast.makeText(SignUpActivity.this, "sign up successfully", Toast.LENGTH_SHORT).show();

                                        Intent i=new Intent(SignUpActivity.this,MainActivity.class);
                                        String UserID=task.getResult().getUser().getUid();
                                        String UserEmail=email.getText().toString();
                                        String UserPassword=password.getText().toString();

                                        i.putExtra("UserID",UserID);
                                        i.putExtra("Email",UserEmail);
                                        i.putExtra("Password",UserPassword);
                                        startActivity(i);
                                    }
                                }
                            });
                }
            }
        });
    }
}
