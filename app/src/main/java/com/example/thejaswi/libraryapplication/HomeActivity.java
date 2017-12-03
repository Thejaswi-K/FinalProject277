package com.example.thejaswi.libraryapplication;

import android.content.Intent;
import android.os.Bundle;
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
import android.widget.TextView;
import android.widget.Toast;

import com.example.thejaswi.libraryapplication.domain.api.APIService;
import com.example.thejaswi.libraryapplication.domain.api.ServiceGenerator;
import com.example.thejaswi.libraryapplication.model.entities.Login;
import com.example.thejaswi.libraryapplication.model.entities.Logout;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    APIService mAPIService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        if (Session.getEmail().matches("^[a-zA-Z0-9_.+-]+@(?:(?:[a-zA-Z0-9-]+\\.)?[a-zA-Z]+\\.)?(sjsu)\\.edu$")) {
            //lib
            navigationView.getMenu().getItem(2).setVisible(false);
            getSupportFragmentManager().beginTransaction().add(R.id.container, new LibSearchFragment()).commit();
        } else {
            //patron
            navigationView.getMenu().getItem(1).setVisible(false);
            getSupportFragmentManager().beginTransaction().add(R.id.container, new PatSearchFragment()).commit();
        }
        TextView name = navigationView.getHeaderView(0).findViewById(R.id.name);
        TextView email = navigationView.getHeaderView(0).findViewById(R.id.email);
        name.setText(Session.getName());
        email.setText(Session.getEmail());

        mAPIService = ServiceGenerator.createService(APIService.class);
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


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.search) {

            if (Session.getEmail().matches("^[a-zA-Z0-9_.+-]+@(?:(?:[a-zA-Z0-9-]+\\.)?[a-zA-Z]+\\.)?(sjsu)\\.edu$")) {
                //lib
                getSupportFragmentManager().beginTransaction().add(R.id.container, new LibSearchFragment()).commit();
            } else {
                //patron
                getSupportFragmentManager().beginTransaction().add(R.id.container, new PatSearchFragment()).commit();

            }
        } else if (id == R.id.add_books) {
            getSupportFragmentManager().beginTransaction().add(R.id.container, new AddBooksFragment()).commit();
        } else if (id == R.id.cart) {
            getSupportFragmentManager().beginTransaction().add(R.id.container, new CartFragment()).commit();
        } else if (id == R.id.logout) {


            logout();
            //startActivity(new Intent(this,LoginActivity.class));

            //finish();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    public void logout() {

        if (Session.isLoggedIn()) {

            if (Session.getEmail().matches("^[a-zA-Z0-9_.+-]+@(?:(?:[a-zA-Z0-9-]+\\.)?[a-zA-Z]+\\.)?(sjsu)\\.edu$")) {


                final Call<Void> call = mAPIService.librarianLogout();
                call.enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {

                        //Display successful response results
                        //String status = response.body();
                        if (response.code() == 200) {
                            Session.setLoggedIn(false);
                            startActivity(new Intent(HomeActivity.this,LoginActivity.class));
                        } else {
                            //responseText.setText("");
                            Toast.makeText(HomeActivity.this, "Unable to logout", Toast.LENGTH_SHORT).show();
                        }
                        //Hide progressbar when done
                        // progressBar.setVisibility(View.INVISIBLE);

                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {
                        // Display error message if the request fails
                        Toast.makeText(HomeActivity.this, "Error while log out ", Toast.LENGTH_SHORT).show();
                        //Hide progressbar when done
                        //progressBar.setVisibility(View.INVISIBLE);
                    }

                });


            } else {


                final Call<Void> call = mAPIService.patronLogout();

                call.enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {

                        //Display successful response results
                       // String status = response.body();
                        //Log.e("LOGOUT_STATUS", status);

                        if (response.code() == 200) {
                            Session.setLoggedIn(false);
                            startActivity(new Intent(HomeActivity.this,LoginActivity.class));
                        } else {
                            //responseText.setText("");
                            Toast.makeText(HomeActivity.this, "Invalid Details", Toast.LENGTH_SHORT).show();
                        }
                        //Hide progressbar when done
                        // progressBar.setVisibility(View.INVISIBLE);

                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {
                        // Display error message if the request fails
                        Toast.makeText(HomeActivity.this, "Error while login out", Toast.LENGTH_SHORT).show();
                        //Hide progressbar when done
                        //progressBar.setVisibility(View.INVISIBLE);
                    }


                });

            }
        }

    }

}

