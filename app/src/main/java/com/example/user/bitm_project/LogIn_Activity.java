package com.example.user.bitm_project;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.user.bitm_project.TravelEvent.TravelEvent_Activity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LogIn_Activity extends AppCompatActivity {

    private Button logInButton;
    private EditText emailEditText,passwordEditText;
    private TextView registerTV;

    private FirebaseAuth auth;
    private FirebaseUser user;
    private ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in_);

        setTitle(" LogIn Page ");

        logInButton = findViewById(R.id.logInButton_id);
        emailEditText = findViewById(R.id.logIn_Email_id);
        passwordEditText = findViewById(R.id.logIn_Password_id);
        registerTV = findViewById(R.id.logInClickRegister_id);

        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        progressDialog = new ProgressDialog(this);


        if (user != null)
        {
            startActivity(new Intent(LogIn_Activity.this,Navigation_Activity.class));
            finish();
        }



        logInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logInMethod();
            }
        });
        registerTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signUpMethod();
            }
        });
    }

    private void signUpMethod() {

        AlertDialog.Builder builder = new AlertDialog.Builder(LogIn_Activity.this);
        View view1 = getLayoutInflater().inflate(R.layout.signup_custom_layout,null);
        final EditText email = view1.findViewById(R.id.signUp_Email_id);
        final EditText password = view1.findViewById(R.id.signUp_Password_id);
        Button signUpButton = view1.findViewById(R.id.signUp_Register_id);
        TextView signUpTextView = view1.findViewById(R.id.signUpClickLogin_id);
        signUpTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LogIn_Activity.this,LogIn_Activity.class));
            }
        });


        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog.show();
                String mEmail = email.getText().toString();
                String mPassword = password.getText().toString();

                if (!mEmail.isEmpty() && !mPassword.isEmpty())
                {
                    auth.createUserWithEmailAndPassword(mEmail,mPassword)
                            .addOnCompleteListener(LogIn_Activity.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful())
                                    {
                                        progressDialog.dismiss();
                                        Toast.makeText(LogIn_Activity.this, "Register Successfully", Toast.LENGTH_SHORT).show();
                                        startActivity(new Intent(LogIn_Activity.this,LogIn_Activity.class));
                                    }

                                }
                            }).addOnFailureListener(LogIn_Activity.this, new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressDialog.dismiss();
                            Toast.makeText(LogIn_Activity.this, e.getMessage(), Toast.LENGTH_SHORT).show();

                        }
                    });




                }else {
                    progressDialog.dismiss();
                    Toast.makeText(LogIn_Activity.this, "Please Enter Email and Password ", Toast.LENGTH_SHORT).show();
                }

            }
        });

        builder.setView(view1);
        AlertDialog dialog = builder.create();
        dialog.show();


    }

    private void logInMethod() {

        progressDialog.show();
        String email = emailEditText.getText().toString();
        String password = passwordEditText.getText().toString();

        try {
            if (!email.isEmpty()&&!password.isEmpty()){

                auth.signInWithEmailAndPassword(email,password)
                        .addOnCompleteListener(LogIn_Activity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful())
                                {
                                progressDialog.dismiss();
                                Toast.makeText(LogIn_Activity.this, "LogIn Successfully", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(LogIn_Activity.this,Navigation_Activity.class));
                                  /*  if (user.isEmailVerified()){
                                        progressDialog.dismiss();
                                        Toast.makeText(LogIn_Activity.this, "LogIn Successfully", Toast.LENGTH_SHORT).show();
                                        startActivity(new Intent(LogIn_Activity.this,Navigation_Activity.class));

                                    }else {
                                        Toast.makeText(LogIn_Activity.this, "Please Verified your E-mail First ", Toast.LENGTH_SHORT).show();
                                    }*/

                                }

                            }
                        }).addOnFailureListener(LogIn_Activity.this, new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        progressDialog.dismiss();
                        Toast.makeText(LogIn_Activity.this, e.getMessage(), Toast.LENGTH_SHORT).show();

                    }
                });



            }else {
                progressDialog.dismiss();
                Toast.makeText(LogIn_Activity.this, "Email and Password could not Match", Toast.LENGTH_SHORT).show();
            }

        }catch (Exception e)
        {
            Toast.makeText(this, "Exception is created"+"\n"+e, Toast.LENGTH_SHORT).show();
        }

    }


}
