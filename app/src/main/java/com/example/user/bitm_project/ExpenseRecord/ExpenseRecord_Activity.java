package com.example.user.bitm_project.ExpenseRecord;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.user.bitm_project.Moment.Gallery_Show_Activity;
import com.example.user.bitm_project.Moment.MomentGalleryActivity;
import com.example.user.bitm_project.Navigation_Activity;
import com.example.user.bitm_project.R;
import com.example.user.bitm_project.TravelEvent.EventShowActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DateFormat;
import java.util.Date;

public class ExpenseRecord_Activity extends AppCompatActivity {

    private EditText expenseDetailsET,expenseAmountET;
    private Button entryRecordButton;

    private FirebaseDatabase database;
    private DatabaseReference rootReference;
    private FirebaseAuth auth;
    private FirebaseUser user;
    ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expense_record_);

        setTitle("Expense Record Page ");


        progressDialog = new ProgressDialog(this);

        database = FirebaseDatabase.getInstance();
        rootReference = database.getReference("UserInfo");
        rootReference.keepSynced(true);

        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();


        expenseDetailsET = findViewById(R.id.expenseDetails_id);
        expenseAmountET = findViewById(R.id.expenseAmount_id);

        entryRecordButton = findViewById(R.id.expenseEntryRecordButton_id);


        entryRecordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String expenseDetails = expenseDetailsET.getText().toString();
                String expenseAmount = expenseAmountET.getText().toString();

                if (expenseDetails.isEmpty())
                {
                  expenseDetailsET.setError("Please Enter expenseDetails");
                }else if (expenseAmount.isEmpty())
                {
                    expenseAmountET.setError("Please Enter expenseAmount");
                }else {
                    try {

                        progressDialog.show();
                        String rootId = user.getUid();
                        String id = rootReference.push().getKey();

                        String currentDateTimeString = DateFormat.getDateTimeInstance().format(new Date());

                        ExpenseRecord expenseRecord = new ExpenseRecord(id,currentDateTimeString,expenseDetails,expenseAmount);

                        rootReference.child(rootId).child("ExpenseRecord").child(id).setValue(expenseRecord)
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful())
                                        {
                                            expenseDetailsET.setText("");
                                            expenseAmountET.setText("");
                                            progressDialog.dismiss();
                                            Toast.makeText(ExpenseRecord_Activity.this, "ExpenseRecord is Successful Added", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                progressDialog.dismiss();
                                Toast.makeText(ExpenseRecord_Activity.this, "Added Field ", Toast.LENGTH_SHORT).show();
                            }
                        });



                    }catch (Exception e )
                    {
                        progressDialog.dismiss();
                        Toast.makeText(ExpenseRecord_Activity.this, "You LogIn First ", Toast.LENGTH_SHORT).show();
                    }


                }



            }
        });


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
            startActivity(new Intent(ExpenseRecord_Activity.this, Navigation_Activity.class));

            return true;
        }
        if (id == R.id.galley_Show_id) {
            startActivity(new Intent(ExpenseRecord_Activity.this, Gallery_Show_Activity.class));

            return true;
        }
        if (id == R.id.travel_events_Show_id) {
            startActivity(new Intent(ExpenseRecord_Activity.this, EventShowActivity.class));

            return true;
        }
        if (id == R.id.expenseRecordShow_id) {
            startActivity(new Intent(ExpenseRecord_Activity.this, ExpenseRecordShow_Activity.class));

            return true;
        }
        if (id == R.id.logOut_id) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
