package com.example.user.bitm_project.TravelEvent;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.example.user.bitm_project.ExpenseRecord.ExpenseRecordShow_Activity;
import com.example.user.bitm_project.Moment.Gallery_Show_Activity;
import com.example.user.bitm_project.Navigation_Activity;
import com.example.user.bitm_project.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;

public class TravelEvent_Activity extends AppCompatActivity {

    EditText destinationET,budgetET,fromDateET,toDateET;
    Button eventButton;
    private ProgressDialog progressDialog;

    FirebaseDatabase database;
    DatabaseReference rootReference;
    private DatePickerDialog.OnDateSetListener mDateSetListener;


    FirebaseAuth auth;
    FirebaseUser user;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_travel_event_);

        setTitle("Event Added Page");


        database = FirebaseDatabase.getInstance();
        rootReference = database.getReference("UserInfo");
        rootReference.keepSynced(true);

        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        progressDialog = new ProgressDialog(this);



        destinationET = findViewById(R.id.travelDestination_id);
        budgetET = findViewById(R.id.travelEstimatedBudget_id);
        fromDateET = findViewById(R.id.travelFromDate_id);
        toDateET = findViewById(R.id.travelToDate_id);
        eventButton = findViewById(R.id.createEvent_id);



        eventButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String destination = destinationET.getText().toString();
                String budget = budgetET.getText().toString();
                String fromDate = fromDateET.getText().toString();
                String toDate = toDateET.getText().toString();

                if (destination.isEmpty())
                {
                    destinationET.setError("Please Enter destination");
                }
                else if (budget.isEmpty())
                {
                    budgetET.setError("Please Enter budget");
                }
                else if (budget.isEmpty())
                {
                    fromDateET.setError("Please Enter fromDate");
                }
                else if (budget.isEmpty())
                {
                    toDateET.setError("Please Enter toDate");
                }else {
                    try{

                        progressDialog.show();
                        String rootId = user.getUid();
                        String id =rootReference.push().getKey();

                        TravelEvents travelEvents = new TravelEvents(id,destination,budget,fromDate,toDate);
                        rootReference.child(rootId).child("TravelEvents").child(id).setValue(travelEvents)
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()){

                                            destinationET.setText("");
                                            budgetET.setText("");
                                            fromDateET.setText("");
                                            toDateET.setText("");
                                            progressDialog.dismiss();
                                            Toast.makeText(TravelEvent_Activity.this, "TravelEvents is Add Successful", Toast.LENGTH_SHORT).show();
                                        }else {

                                            progressDialog.dismiss();
                                            Toast.makeText(TravelEvent_Activity.this, "Added Field", Toast.LENGTH_SHORT).show();
                                        }


                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                progressDialog.dismiss();
                                Toast.makeText(TravelEvent_Activity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });



                    }catch (Exception e) {
                        progressDialog.dismiss();
                        Toast.makeText(TravelEvent_Activity.this, "You logIn First", Toast.LENGTH_SHORT).show();

                    }

                }
            }
        });

        fromDateET.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog dialog = new DatePickerDialog(TravelEvent_Activity.this,android.R.style.
                        Theme_Holo_Light_Dialog_MinWidth,
                        mDateSetListener,year,month,day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();

            }
        });
        toDateET.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog dialog = new DatePickerDialog(TravelEvent_Activity.this,android.R.style.
                        Theme_Holo_Light_Dialog_MinWidth,
                        mDateSetListener,year,month,day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();

            }
        });

        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month = month+1;
                String date = dayOfMonth + "/" +month+ "/" +year;

                fromDateET.setText(date);
                toDateET.setText(date);
            }
        };




    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.navigation_, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            startActivity(new Intent(TravelEvent_Activity.this, Navigation_Activity.class));

            return true;
        }
        if (id == R.id.galley_Show_id) {
            startActivity(new Intent(TravelEvent_Activity.this, Gallery_Show_Activity.class));

            return true;
        }
        if (id == R.id.travel_events_Show_id) {

            startActivity(new Intent(TravelEvent_Activity.this, EventShowActivity.class));
            return true;
        }
        if (id == R.id.expenseRecordShow_id) {
            startActivity(new Intent(TravelEvent_Activity.this, ExpenseRecordShow_Activity.class));

            return true;
        }
        if (id == R.id.logOut_id) {

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
