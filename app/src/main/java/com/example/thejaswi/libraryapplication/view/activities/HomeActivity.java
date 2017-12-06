package com.example.thejaswi.libraryapplication.view.activities;

import android.Manifest;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.amazonaws.auth.CognitoCachingCredentialsProvider;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferListener;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferObserver;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferState;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferUtility;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.example.thejaswi.libraryapplication.R;
import com.example.thejaswi.libraryapplication.Session;
import com.example.thejaswi.libraryapplication.domain.api.APIService;
import com.example.thejaswi.libraryapplication.domain.api.ServiceGenerator;
import com.example.thejaswi.libraryapplication.util.AccessPermissions;
import com.example.thejaswi.libraryapplication.util.S3ImageUpload;
import com.example.thejaswi.libraryapplication.view.fragment.AddBooksFragment;
import com.example.thejaswi.libraryapplication.view.fragment.CartFragment;
import com.example.thejaswi.libraryapplication.view.fragment.LibSearchFragment;
import com.example.thejaswi.libraryapplication.view.fragment.PatSearchFragment;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.IOException;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {

    APIService mAPIService;
    private int PHOTO_SELECTED = 777;
    private Bitmap bitMap;
    ImageView bookImage;
    Button imageupload;
    Button imageSelect;
    Uri path;
    ImageView menu;
    final private int MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE =899;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
       /* Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();*/

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        if (Session.getEmail().matches("^[a-zA-Z0-9_.+-]+@(?:(?:[a-zA-Z0-9-]+\\.)?[a-zA-Z]+\\.)?(sjsu)\\.edu$")) {
            //lib
            navigationView.getMenu().getItem(2).setVisible(false);
        } else {
            //patron
            navigationView.getMenu().getItem(1).setVisible(false);
        }

        getSupportFragmentManager().beginTransaction().add(R.id.container, new PatSearchFragment()).commit();
        TextView name = navigationView.getHeaderView(0).findViewById(R.id.name);
        TextView email = navigationView.getHeaderView(0).findViewById(R.id.email);
        name.setText(Session.getName());
        email.setText(Session.getEmail());
        findViewById(R.id.menu).setOnClickListener(this);

        mAPIService = ServiceGenerator.createService(APIService.class);

        imageSelect = findViewById(R.id.selectImage);
        imageupload = findViewById(R.id.ImageUpload);
        bookImage = findViewById(R.id.bookImage);
        imageSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //checkPermission();

                getFragmentManager().beginTransaction().add(R.id.container, new AddBooksFragment(),"ADD_BOOK_FRAGMENT").commit();
            }
        });

        imageupload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                uploadImage();
            }
        });
    }

    public void checkPermission(){

        AccessPermissions accessPermissions = new AccessPermissions(this, this,MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);
        if (accessPermissions.validateStorageAccessPermission()){

            selectImage();

        }

    }

    public void selectImage() {

        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        startActivityForResult(intent, PHOTO_SELECTED);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PHOTO_SELECTED && resultCode == RESULT_OK && data != null) {

            path = data.getData();
            try {

                Log.e("S3_UPLOAD", "PATH==>" + path.toString());
                //MediaStore.Images.Media.getContentUri(path);
                bitMap = MediaStore.Images.Media.getBitmap(getContentResolver(), path);
                bookImage.setImageBitmap(bitMap);
                bookImage.setVisibility(View.VISIBLE);
                imageSelect.setVisibility(View.INVISIBLE);
                imageupload.setVisibility(View.VISIBLE);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    public void uploadImage() {

            bookImage.setVisibility(View.INVISIBLE);
            S3ImageUpload s3ImageUpload = new S3ImageUpload(this,this,path,bookImage);
            s3ImageUpload.upload();

        return;
    }



    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    selectImage();


                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }



    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.END)) {
            drawer.closeDrawer(GravityCompat.END);
        } else {
            super.onBackPressed();
        }
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        Log.e("logout","Clicked navigation item");
        if (id == R.id.search) {

            if (Session.getEmail().matches("^[a-zA-Z0-9_.+-]+@(?:(?:[a-zA-Z0-9-]+\\.)?[a-zA-Z]+\\.)?(sjsu)\\.edu$")) {
                //lib
                getSupportFragmentManager().beginTransaction().add(R.id.container, new LibSearchFragment()).commit();
            } else {
                //patron
                getSupportFragmentManager().beginTransaction().add(R.id.container, new PatSearchFragment()).commit();

            }
        } else if (id == R.id.add_books) {
            getFragmentManager().beginTransaction().add(R.id.container, new AddBooksFragment()).commit();
        } else if (id == R.id.cart) {
            getSupportFragmentManager().beginTransaction().add(R.id.container, new CartFragment()).commit();
        } else if (id == R.id.logout) {
            Log.e("logout","Clicked");
            logout();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.END);
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
                            startActivity(new Intent(HomeActivity.this, LoginActivity.class));
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
                            startActivity(new Intent(HomeActivity.this, LoginActivity.class));
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

    @Override
    public void onClick(View view) {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.openDrawer(GravityCompat.END);
    }
}

