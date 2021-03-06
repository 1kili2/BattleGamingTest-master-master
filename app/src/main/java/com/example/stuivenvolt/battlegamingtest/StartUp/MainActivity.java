package com.example.stuivenvolt.battlegamingtest.StartUp;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
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
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.stuivenvolt.battlegamingtest.Calender.CalenderFragment;
import com.example.stuivenvolt.battlegamingtest.Calender.DateFragment;
import com.example.stuivenvolt.battlegamingtest.Guild_Hub.GuildHubFragment;
import com.example.stuivenvolt.battlegamingtest.Guild_Hub.Guild_List.GuildListFragment;
import com.example.stuivenvolt.battlegamingtest.Guild_Hub.Members_List.GuildMembersFragment;
import com.example.stuivenvolt.battlegamingtest.News.NewsFragment;
import com.example.stuivenvolt.battlegamingtest.News.NewsItemFragment;
import com.example.stuivenvolt.battlegamingtest.Profile.PersonalProfileFragment;
import com.example.stuivenvolt.battlegamingtest.Profile.ViewProfileFragment;
import com.example.stuivenvolt.battlegamingtest.Profile.Weapons_List.WeaponListFragment;
import com.example.stuivenvolt.battlegamingtest.R;
import com.example.stuivenvolt.battlegamingtest.Settings.SettingsFragment;
import com.example.stuivenvolt.battlegamingtest.Tournament.ChampionshipCreatorFragment;
import com.example.stuivenvolt.battlegamingtest.Tournament.ChampionshipFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,
        CalenderFragment.OnFragmentInteractionListener,
        DateFragment.OnFragmentInteractionListener,
        NewsItemFragment.OnFragmentInteractionListener,
        NewsFragment.OnFragmentInteractionListener,
        SettingsFragment.OnFragmentInteractionListener,
        PersonalProfileFragment.OnFragmentInteractionListener,
        ChampionshipCreatorFragment.OnFragmentInteractionListener,
        GuildHubFragment.OnFragmentInteractionListener,
        GuildMembersFragment.OnFragmentInteractionListener,
        WeaponListFragment.OnFragmentInteractionListener,
        ViewProfileFragment.OnFragmentInteractionListener,
        GuildListFragment.OnFragmentInteractionListener,
        ChampionshipFragment.OnFragmentInteractionListener{

    FragmentManager fragmentManager = getFragmentManager();

    FirebaseAuth mAuth;
    FirebaseUser user;
    DatabaseReference myRef;
    String guild;
    boolean isConnected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        IsConnected();

        if(isConnected) {
            mAuth = FirebaseAuth.getInstance();
            user = mAuth.getCurrentUser();
            myRef = FirebaseDatabase.getInstance().getReference("usuarios");
            if (user == null) {
                Intent intent = new Intent(this, RegisterScreen.class);
                startActivity(intent);
            }
            setGuild();


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
        }else{
            Toast.makeText(MainActivity.this, "No internet connection, please check connection and reload the app!", Toast.LENGTH_LONG).show();
            setContentView(R.layout.activity_main_no_connection);
        }
    }

    @Override
    public void onBackPressed() {
        if(getFragmentManager().getBackStackEntryCount() > 0) {
            getFragmentManager().popBackStack();
            setTitle(getString(R.string.app_title));
        }else {
            super.onBackPressed();
            setTitle(getString(R.string.app_title));
        }
        //DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        /*if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }*/
    }



    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(final MenuItem item) {
        // Handle navigation view item clicks here.
        IsConnected();
        if(isConnected) {
            final android.app.FragmentManager fm = getFragmentManager();
            Handler h = new Handler();
            h.postDelayed(new Runnable() {
                @Override
                public void run() {
                    switch (item.getItemId()) {

                        case R.id.nav_news:
                            fm.beginTransaction().replace(R.id.content_frame, new NewsFragment()).commit();
                            setTitle(getString(R.string.news_title));
                            break;

                        case R.id.nav_date:
                            fm.beginTransaction().replace(R.id.content_frame, new CalenderFragment()).addToBackStack("Calender").commit();
                            break;

                        case R.id.nav_manage:
                            fm.beginTransaction().replace(R.id.content_frame, new SettingsFragment()).addToBackStack("Settings").commit();
                            setTitle(getString(R.string.app_title));
                            break;

                        /*case R.id.nav_fights:
                            fm.beginTransaction().replace(R.id.content_frame, new ChampionshipCreatorFragment()).addToBackStack("Tournaments").commit();
                            setTitle(getString(R.string.app_title));
                            break;*/

                        case R.id.nav_profile:
                            //SetInfo();
                            fm.beginTransaction().replace(R.id.content_frame, new PersonalProfileFragment()).addToBackStack("Profile").commit();
                            setTitle(getString(R.string.profile_title));

                            break;

                        case R.id.nav_Guild:
                            //SetInfo();
                            if (guild == null) {
                                fm.beginTransaction().replace(R.id.content_frame, new GuildListFragment()).commit();
                                setTitle(getString(R.string.guild_list_title));
                            } else {
                                Bundle bundle = new Bundle();
                                bundle.putString("Guild", guild);
                                android.app.FragmentManager fm = getFragmentManager();
                                GuildHubFragment ghf = new GuildHubFragment();
                                ghf.setArguments(bundle);
                                fm.beginTransaction().replace(R.id.content_frame, ghf).addToBackStack("Guild").commit();
                                setTitle(getString(R.string.guild_title));
                            }

                            break;

                        case R.id.nav_LogOut:
                            mAuth.signOut();
                            Intent intent = new Intent(MainActivity.this, RegisterScreen.class);
                            startActivity(intent);
                            break;

                        default:
                    }
                }


            }, 50);
        }else{
            Toast.makeText(MainActivity.this, "No internet connection, please check connection and reload the app!", Toast.LENGTH_LONG).show();
        }
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;

    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    public void IsConnected(){
        ConnectivityManager cm =
                (ConnectivityManager)this.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();
    }

    public void setGuild(){
        final String mail = user.getEmail().replace("."," ");
        myRef = FirebaseDatabase.getInstance().getReference("usuarios");
        DatabaseReference profileRef = myRef.child(mail).child("Public").child("Guild");
        profileRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                guild = dataSnapshot.getValue(String.class);

            }
            @Override
            public void onCancelled(DatabaseError databaseError) { }
        });
    }
}