package com.example.user.bitm_project.TravelEvent;

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

import com.example.user.bitm_project.ExpenseRecord.ExpenseRecordShow_Activity;
import com.example.user.bitm_project.Moment.Gallery_Show_Activity;
import com.example.user.bitm_project.Moment.MomentGallery;
import com.example.user.bitm_project.Navigation_Activity;
import com.example.user.bitm_project.R;
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

import java.util.ArrayList;

public class EventShowActivity extends AppCompatActivity {

    private ListView listView;
    private ArrayList<TravelEvents> travelEventsList= new ArrayList<>();
    private TravelEvent_Adapter adepter;

    private FirebaseDatabase database;
    private DatabaseReference databaseReference;
    private FirebaseAuth auth;
    private FirebaseUser user;
    private Button shareButton,deleteButton,updateButton;

    String uId;
    String eventId;
    String travelUpdateId;

    EditText destinationET,budgetET,fromDateET,toDateET;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_show);

        setTitle("Event Show Page");


        database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference("UserInfo");
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        uId = user.getUid();




        listView = findViewById(R.id.listView_EventShow_id);

        databaseReference.child(uId).child("TravelEvents").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                travelEventsList.clear();

                for (DataSnapshot snapshot : dataSnapshot.getChildren()){

                    TravelEvents travelEvents =snapshot.getValue(TravelEvents.class);
                    travelEventsList.add(travelEvents);
                }
                adepter = new TravelEvent_Adapter(getApplicationContext(),travelEventsList);
                listView.setAdapter(adepter);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, final int i, long l) {

                eventId = travelEventsList.get(i).getId();
                AlertDialog.Builder builder = new AlertDialog.Builder(EventShowActivity.this);
                View mView = getLayoutInflater().inflate(R.layout.share_delete_update_custom,null);

                shareButton = mView.findViewById(R.id.shareDetails_id);
                deleteButton = mView.findViewById(R.id.deleteDetails_id);
                updateButton = mView.findViewById(R.id.updateDetails_id);

                shareButton.setVisibility(View.GONE);

                deleteButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        databaseReference.child(uId).child("TravelEvents").child(eventId)
                                .removeValue();
                        startActivity(new Intent(EventShowActivity.this,EventShowActivity.class));
                        finish();

                    }
                });

                updateButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {




             AlertDialog.Builder builder = new AlertDialog.Builder(EventShowActivity.this);
             View mView = getLayoutInflater().inflate(R.layout.custom_update_data,null);
             Button updateButton = mView.findViewById(R.id.update_createEvent_id);

                        TravelEvents travelEvents = travelEventsList.get(i);
                        travelUpdateId = travelEvents.getId();

                        String destination = travelEvents.getDistination();
                        String budget = travelEvents.getBudget();
                        String fromDate = travelEvents.getFromDate();
                        String toDate = travelEvents.getToDate();

                        destinationET = mView.findViewById(R.id.update_travelDestination_id);
                        budgetET = mView.findViewById(R.id.update_travelEstimatedBudget_id);
                        fromDateET = mView.findViewById(R.id.update_travelFromDate_id);
                        toDateET = mView.findViewById(R.id.update_travelToDate_id);

                        destinationET.setText(destination);
                        budgetET.setText(budget);
                        fromDateET.setText(fromDate);
                        toDateET.setText(toDate);



                        updateButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                                try{
                                    String uDestination = destinationET.getText().toString();
                                    String uBudget = budgetET.getText().toString();
                                    String uFromDate = fromDateET.getText().toString();
                                    String uToDate = toDateET.getText().toString();

                                    TravelEvents travelEvents = new TravelEvents(travelUpdateId,uDestination,uBudget,uFromDate,uToDate);

                                    databaseReference.child(uId).child("TravelEvents").child(eventId)
                                            .setValue(travelEvents).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()){
                                                Toast.makeText(EventShowActivity.this, "Update Successfully", Toast.LENGTH_SHORT).show();
                                                startActivity(new Intent(EventShowActivity.this,EventShowActivity.class));
                                                finish();

                                            }
                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Toast.makeText(EventShowActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                        }
                                    });

                                }catch (Exception e){
                                    Toast.makeText(EventShowActivity.this, " Update Field", Toast.LENGTH_SHORT).show();

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
            startActivity(new Intent(EventShowActivity.this, Navigation_Activity.class));

            return true;
        }
        if (id == R.id.galley_Show_id) {
            startActivity(new Intent(EventShowActivity.this, Gallery_Show_Activity.class));

            return true;
        }
        if (id == R.id.travel_events_Show_id) {

            return true;
        }
        if (id == R.id.expenseRecordShow_id) {
            startActivity(new Intent(EventShowActivity.this, ExpenseRecordShow_Activity.class));

            return true;
        }
        if (id == R.id.logOut_id) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


}
