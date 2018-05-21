package com.example.stuivenvolt.battlegamingtest.StartUp;

import android.net.Uri;
import android.os.Handler;
import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.stuivenvolt.battlegamingtest.Calender.CalenderFragment;
import com.example.stuivenvolt.battlegamingtest.Calender.DateFragment;
import com.example.stuivenvolt.battlegamingtest.News.NewsFragment;
import com.example.stuivenvolt.battlegamingtest.News.NewsItemFragment;
import com.example.stuivenvolt.battlegamingtest.Profile.PersonalProfileFragment;
import com.example.stuivenvolt.battlegamingtest.R;
import com.example.stuivenvolt.battlegamingtest.Settings.SettingsFragment;
import com.example.stuivenvolt.battlegamingtest.Tournament.ChampionshipCreatorFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,
        CalenderFragment.OnFragmentInteractionListener,
        DateFragment.OnFragmentInteractionListener,
        NewsItemFragment.OnFragmentInteractionListener,
        NewsFragment.OnFragmentInteractionListener,
        SettingsFragment.OnFragmentInteractionListener,
        PersonalProfileFragment.OnFragmentInteractionListener,
        ChampionshipCreatorFragment.OnFragmentInteractionListener{
    FragmentManager fragmentManager = getFragmentManager();

    FirebaseAuth mAuth;
    FirebaseUser user;
    DatabaseReference myRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        myRef = FirebaseDatabase.getInstance().getReference("usuarios");
        if(user == null){
            Intent intent = new Intent(this, RegisterScreen.class);
            startActivity(intent);
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        FragmentManager fm = getFragmentManager();
        fm.beginTransaction().replace(R.id.content_frame, new NewsFragment()).commit();
    }

    @Override
    public void onBackPressed() {
        //DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        /*if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }*/
    }


    /*
        **************************Opciones derecho***************************
        @Override
        public boolean onCreateOptionsMenu(Menu menu) {
            // Inflate the menu; this adds items to the action bar if it is present.
            getMenuInflater().inflate(R.menu.main, menu);
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
                return true;
            }

            return super.onOptionsItemSelected(item);
        }
    */
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(final MenuItem item) {
        // Handle navigation view item clicks here.

        final android.app.FragmentManager fm = getFragmentManager();
        Handler h = new Handler();
        h.postDelayed(new Runnable() {
            @Override
            public void run() {
                switch (item.getItemId()) {

                    case R.id.nav_news:
                        fm.beginTransaction().replace(R.id.content_frame, new NewsFragment()).commit();
                        setTitle("Battle Gaming");
                        break;

                    case R.id.nav_date:
                        fm.beginTransaction().replace(R.id.content_frame, new CalenderFragment()).commit();
                        break;

                    case R.id.nav_manage:
                        fm.beginTransaction().replace(R.id.content_frame, new SettingsFragment()).commit();
                        setTitle("Battle Gaming");
                        break;

                    case R.id.nav_fights:
                        fm.beginTransaction().replace(R.id.content_frame, new ChampionshipCreatorFragment()).commit();
                        setTitle("Battle Gaming");
                        break;

                    case R.id.nav_test1:
                        //SetInfo();
                        fm.beginTransaction().replace(R.id.content_frame, new PersonalProfileFragment()).commit();
                        setTitle("Profile");

                        break;

                    case R.id.nav_test2:
                        if (user != null) {
                            Toast.makeText(MainActivity.this, user.getEmail() + "    " + user.getDisplayName(), Toast.LENGTH_LONG).show();
                        }
                        setTitle("Battle Gaming");
                        break;

                    case R.id.nav_LogOut:
                        mAuth.signOut();
                        setTitle("Battle Gaming");
                        Intent intent = new Intent(MainActivity.this, RegisterScreen.class);
                        startActivity(intent);
                        break;

                    default:
                }
            }


        }, 50);
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}