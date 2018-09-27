package com.example.user.bitm_project.Moment;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.example.user.bitm_project.ExpenseRecord.ExpenseRecordShow_Activity;
import com.example.user.bitm_project.Navigation_Activity;
import com.example.user.bitm_project.R;
import com.example.user.bitm_project.TravelEvent.EventShowActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class Gallery_Show_Activity extends AppCompatActivity {

    private ListView listView;
    private ArrayList<MomentGallery>momentGalleryList= new ArrayList<>();
    private GalleryAdepter adepter;

    private FirebaseDatabase database;
    private DatabaseReference databaseReference;
    private FirebaseAuth auth;
    private FirebaseUser user;
    private Button shareButton,deleteButton,updateButton;

    String uId;
    String eventId;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery__show_);

        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        uId = user.getUid();

        setTitle("Moment Gallery Show Page");


        database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference("UserInfo");
        listView = findViewById(R.id.listViewGallery_id);

        databaseReference.child(uId).child("MomentGallery").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                momentGalleryList.clear();

                for (DataSnapshot snapshot : dataSnapshot.getChildren()){

                    MomentGallery momentGallery =snapshot.getValue(MomentGallery.class);
                    momentGalleryList.add(momentGallery);
                }
                adepter = new GalleryAdepter(getApplicationContext(),momentGalleryList);
                listView.setAdapter(adepter);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, final int i, long l) {

                eventId = momentGalleryList.get(i).getId();
                AlertDialog.Builder builder = new AlertDialog.Builder(Gallery_Show_Activity.this);
                View mView = getLayoutInflater().inflate(R.layout.share_delete_update_custom,null);

                shareButton = mView.findViewById(R.id.shareDetails_id);
                deleteButton = mView.findViewById(R.id.deleteDetails_id);
                updateButton = mView.findViewById(R.id.updateDetails_id);
                updateButton.setVisibility(View.GONE);


                deleteButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        databaseReference.child(uId).child("MomentGallery").child(eventId)
                                .removeValue();
                        startActivity(new Intent(Gallery_Show_Activity.this,Gallery_Show_Activity.class));
                        finish();
                    }
                });


                builder.setView(mView);
                AlertDialog dialog = builder.create();
                dialog.show();

                return true;
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
            startActivity(new Intent(Gallery_Show_Activity.this, Navigation_Activity.class));

            return true;
        }
        if (id == R.id.galley_Show_id) {

            return true;
        }
        if (id == R.id.travel_events_Show_id) {
            startActivity(new Intent(Gallery_Show_Activity.this, EventShowActivity.class));

            return true;
        }
        if (id == R.id.expenseRecordShow_id) {
            startActivity(new Intent(Gallery_Show_Activity.this, ExpenseRecordShow_Activity.class));

            return true;
        }
        if (id == R.id.logOut_id) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
