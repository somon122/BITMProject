package com.example.user.bitm_project;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.user.bitm_project.ExpenseRecord.ExpenseRecordShow_Activity;
import com.example.user.bitm_project.ExpenseRecord.ExpenseRecord_Activity;
import com.example.user.bitm_project.Moment.Gallery_Show_Activity;
import com.example.user.bitm_project.Moment.MomentGalleryActivity;
import com.example.user.bitm_project.NearBy.MapsActivity;
import com.example.user.bitm_project.NearBy.NearBy_Activity;
import com.example.user.bitm_project.TravelEvent.EventShowActivity;
import com.example.user.bitm_project.TravelEvent.TravelEvent_Activity;
import com.example.user.bitm_project.Weather.Weather_Activity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Navigation_Activity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    FirebaseUser user;
    ProgressDialog progressDialog;
    FirebaseAuth auth;

    Button addEventButton,showEventButton,addMomentButton,showMomentButton,addExpenseButton,showExpenseButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation_);

        auth = FirebaseAuth.getInstance();
        user = FirebaseAuth.getInstance().getCurrentUser();
        progressDialog = new ProgressDialog(this);



        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
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
            startActivity(new Intent(Navigation_Activity.this, Navigation_Activity.class));

            return true;
        }
         if (id == R.id.galley_Show_id) {
             startActivity(new Intent(Navigation_Activity.this, Gallery_Show_Activity.class));

             return true;
        }
         if (id == R.id.travel_events_Show_id) {
             startActivity(new Intent(Navigation_Activity.this, EventShowActivity.class));

             return true;
        }
        if (id == R.id.expenseRecordShow_id) {
            startActivity(new Intent(Navigation_Activity.this, ExpenseRecordShow_Activity.class));

            return true;
        }
         if (id == R.id.logOut_id) {
             FirebaseAuth.getInstance().signOut();
             Toast.makeText(Navigation_Activity.this, "LogOut Successfully", Toast.LENGTH_SHORT).show();
             startActivity(new Intent(Navigation_Activity.this,LogIn_Activity.class));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            startActivity(new Intent(Navigation_Activity.this, TravelEvent_Activity.class));

        } else if (id == R.id.nav_gallery) {
            startActivity(new Intent(Navigation_Activity.this, MomentGalleryActivity.class));

        } else if (id == R.id.nav_slideshow) {
            startActivity(new Intent(Navigation_Activity.this, ExpenseRecord_Activity.class));

        } else if (id == R.id.nearBy_id) {
            startActivity(new Intent(Navigation_Activity.this, NearBy_Activity.class));

        } else if (id == R.id.weather_id) {
            startActivity(new Intent(Navigation_Activity.this, Weather_Activity.class));
        }

        // Communication Step

        else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }



    public void TravelEvent(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(Navigation_Activity.this);
        View view1 = getLayoutInflater().inflate(R.layout.custom_image_button_,null);
        addEventButton = view1.findViewById(R.id.addEvent_id);
        showEventButton = view1.findViewById(R.id.showEvent_id);
        addMomentButton = view1.findViewById(R.id.addMoment_id);
        showMomentButton = view1.findViewById(R.id.showMoment_id);
        addExpenseButton = view1.findViewById(R.id.addExpense_id);
        showExpenseButton = view1.findViewById(R.id.showExpense_id);

        addExpenseButton.setVisibility(View.GONE);
        showExpenseButton.setVisibility(View.GONE);
        addMomentButton.setVisibility(View.GONE);
        showMomentButton.setVisibility(View.GONE);
        addEventButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Navigation_Activity.this,TravelEvent_Activity.class));
            }
        });
        showEventButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Navigation_Activity.this,EventShowActivity.class));
            }
        });

        builder.setView(view1);
        AlertDialog dialog = builder.create();
        dialog.show();


    }

    public void AddMoment(View view) {

        AlertDialog.Builder builder = new AlertDialog.Builder(Navigation_Activity.this);
        View view1 = getLayoutInflater().inflate(R.layout.custom_image_button_,null);
        addEventButton = view1.findViewById(R.id.addEvent_id);
        showEventButton = view1.findViewById(R.id.showEvent_id);
        addMomentButton = view1.findViewById(R.id.addMoment_id);
        showMomentButton = view1.findViewById(R.id.showMoment_id);
        addExpenseButton = view1.findViewById(R.id.addExpense_id);
        showExpenseButton = view1.findViewById(R.id.showExpense_id);

        addEventButton.setVisibility(View.GONE);
        showEventButton.setVisibility(View.GONE);
        addExpenseButton.setVisibility(View.GONE);
        showExpenseButton.setVisibility(View.GONE);

        addMomentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Navigation_Activity.this,MomentGalleryActivity.class));
            }
        });
        showMomentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Navigation_Activity.this,Gallery_Show_Activity.class));
            }
        });

        builder.setView(view1);
        AlertDialog dialog = builder.create();
        dialog.show();

    }

    public void AddExpense(View view) {

        AlertDialog.Builder builder = new AlertDialog.Builder(Navigation_Activity.this);
        View view1 = getLayoutInflater().inflate(R.layout.custom_image_button_,null);
        addEventButton = view1.findViewById(R.id.addEvent_id);
        showEventButton = view1.findViewById(R.id.showEvent_id);
        addMomentButton = view1.findViewById(R.id.addMoment_id);
        showMomentButton = view1.findViewById(R.id.showMoment_id);
        addExpenseButton = view1.findViewById(R.id.addExpense_id);
        showExpenseButton = view1.findViewById(R.id.showExpense_id);

        addEventButton.setVisibility(View.GONE);
        showEventButton.setVisibility(View.GONE);
        addMomentButton.setVisibility(View.GONE);
        showMomentButton.setVisibility(View.GONE);

        addExpenseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Navigation_Activity.this,ExpenseRecord_Activity.class));
            }
        });
        showExpenseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Navigation_Activity.this,ExpenseRecordShow_Activity.class));
            }
        });

        builder.setView(view1);
        AlertDialog dialog = builder.create();
        dialog.show();

    }

    public void Weather(View view) {
        startActivity(new Intent(Navigation_Activity.this,Weather_Activity.class));
    }

    public void Map(View view) {
        startActivity(new Intent(Navigation_Activity.this,MapsActivity.class));
    }
}
