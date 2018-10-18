package com.example.user.bitm_project.ExpenseRecord;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.user.bitm_project.Moment.Gallery_Show_Activity;
import com.example.user.bitm_project.Navigation_Activity;
import com.example.user.bitm_project.R;
import com.example.user.bitm_project.TravelEvent.EventShowActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;

public class ExpenseRecordShow_Activity extends AppCompatActivity {

    private ListView listView;
    private ArrayList<ExpenseRecord> expenseRecordList= new ArrayList<>();
    private ExpenseRecord_Adapter adepter;

    private FirebaseDatabase database;
    private DatabaseReference databaseReference;
    private FirebaseAuth auth;
    private FirebaseUser user;

    private Button shareButton,deleteButton,updateButton;

    String rowId;
    String uId;
    String eventId;
    String expenseUpdateId;

    EditText detailsET, amountET,fromDateET,toDateET;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expense_record_show_);

        setTitle("Expense Record Show Page ");


        database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference("UserInfo");
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        uId = user.getUid();
        rowId = getIntent().getStringExtra("rowId");

        listView = findViewById(R.id.listViewExpenseShow_id);

        databaseReference.child(uId).child("TravelEvents").child(rowId).child("ExpenseRecord").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                expenseRecordList.clear();

                for (DataSnapshot snapshot : dataSnapshot.getChildren()){

                    ExpenseRecord expenseRecord =snapshot.getValue(ExpenseRecord.class);
                    expenseRecordList.add(expenseRecord);
                }
                adepter = new ExpenseRecord_Adapter(getApplicationContext(),expenseRecordList);
                listView.setAdapter(adepter);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });



      /*  listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, final int i, long l) {

                eventId = expenseRecordList.get(i).getId();
                AlertDialog.Builder builder = new AlertDialog.Builder(ExpenseRecordShow_Activity.this);
                View mView = getLayoutInflater().inflate(R.layout.share_delete_update_custom,null);

                shareButton = mView.findViewById(R.id.expenseRecordShow_id);
                deleteButton = mView.findViewById(R.id.deleteDetails_id);
                updateButton = mView.findViewById(R.id.updateDetails_id);

                shareButton.setVisibility(View.GONE);

                deleteButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        databaseReference.child(uId).child("ExpenseRecord").child(eventId)
                                .removeValue();
                        startActivity(new Intent(ExpenseRecordShow_Activity.this,ExpenseRecordShow_Activity.class));
                        finish();
                    }
                });

                updateButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(ExpenseRecordShow_Activity.this);
                        View mView = getLayoutInflater().inflate(R.layout.custom_update_data,null);
                        Button updateButton = mView.findViewById(R.id.update_createEvent_id);

                        ExpenseRecord expenseRecord = expenseRecordList.get(i);
                        expenseUpdateId = expenseRecord.getId();

                        String details = expenseRecord.getExpenseDetails();
                        String amount = expenseRecord.getExpenseAmount();

                        detailsET = mView.findViewById(R.id.update_travelDestination_id);
                        amountET = mView.findViewById(R.id.update_travelEstimatedBudget_id);
                        fromDateET = mView.findViewById(R.id.update_travelFromDate_id);
                        toDateET = mView.findViewById(R.id.update_travelToDate_id);

                        fromDateET.setVisibility(View.GONE);
                        toDateET.setVisibility(View.GONE);

                        detailsET.setText(details);
                        amountET.setText(amount);



                        updateButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                                try{
                                    String currentDateTimeString = DateFormat.getDateTimeInstance().format(new Date());
                                    String details = detailsET.getText().toString();
                                    String amount = amountET.getText().toString();


                                    ExpenseRecord record = new ExpenseRecord(expenseUpdateId,currentDateTimeString,details,amount);

                                    databaseReference.child(uId).child("ExpenseRecord").child(eventId)
                                            .setValue(record).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()){
                                                Toast.makeText(ExpenseRecordShow_Activity.this, "Update Successfully", Toast.LENGTH_SHORT).show();
                                                startActivity(new Intent(ExpenseRecordShow_Activity.this,ExpenseRecordShow_Activity.class));
                                                finish();

                                            }
                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Toast.makeText(ExpenseRecordShow_Activity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                        }
                                    });

                                }catch (Exception e){
                                    Toast.makeText(ExpenseRecordShow_Activity.this, " Update Field", Toast.LENGTH_SHORT).show();

                                }

                            }
                        });

                        builder.setView(mView);
                        AlertDialog dialog = builder.create();
                        dialog.show();



                    }
                });

                builder.setView(mView);
                AlertDialog dialog = builder.create();
                dialog.show();

            }
        });

*/



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
            startActivity(new Intent(ExpenseRecordShow_Activity.this, Navigation_Activity.class));

            return true;
        }
        if (id == R.id.galley_Show_id) {
            startActivity(new Intent(ExpenseRecordShow_Activity.this, Gallery_Show_Activity.class));

            return true;
        }
        if (id == R.id.travel_events_Show_id) {
            startActivity(new Intent(ExpenseRecordShow_Activity.this, EventShowActivity.class));

            return true;
        }
        if (id == R.id.expenseRecordShow_id) {

            return true;
        }
        if (id == R.id.logOut_id) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


}
