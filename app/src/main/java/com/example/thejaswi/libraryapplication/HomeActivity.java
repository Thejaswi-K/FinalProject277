package com.example.thejaswi.libraryapplication;

import android.Manifest;
import android.content.ContentUris;
import android.content.Context;
import android.content.CursorLoader;
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
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.auth.CognitoCachingCredentialsProvider;
import com.amazonaws.mobile.client.AWSMobileClient;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferListener;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferObserver;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferState;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferUtility;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.example.thejaswi.libraryapplication.domain.api.APIService;
import com.example.thejaswi.libraryapplication.domain.api.ServiceGenerator;
import com.example.thejaswi.libraryapplication.model.entities.Login;
import com.example.thejaswi.libraryapplication.model.entities.Logout;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static javax.crypto.Cipher.SECRET_KEY;

public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {

    APIService mAPIService;
    private int PHOTO_SELECTED = 777;
    private Bitmap bitMap;
    ImageView bookImage;
    Button imageupload;
    Button imageSelect;
    Uri path;
    final private String MY_BUCKET = "librarycatalogue/BookCovers";
    private String OBJECT_KEY = "";
    private File fileToUpload;
    private final int MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 899;
    Date date;
    ImageView menu;

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

//        AWSMobileClient.getInstance().initialize(this).execute();

        imageSelect = findViewById(R.id.selectImage);
        imageupload = findViewById(R.id.ImageUpload);
        bookImage = findViewById(R.id.bookImage);
        imageSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectImage();
            }
        });

        imageupload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                uploadImage();
            }
        });
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

        CognitoCachingCredentialsProvider credentialsProvider = new CognitoCachingCredentialsProvider(
                getApplicationContext(),
                "us-east-2:2822b1aa-1ed9-4bfb-83bb-214798b10cda", // Identity pool ID
                Regions.US_EAST_2 // Region
        );

        AmazonS3 s3 = new AmazonS3Client(credentialsProvider);
        s3.setRegion(Region.getRegion(Regions.US_EAST_2));

        TransferUtility transferUtility = new TransferUtility(s3, HomeActivity.this);


        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.READ_EXTERNAL_STORAGE)) {
            }

            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                    MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);

            return;
        }

        bookImage.setVisibility(View.INVISIBLE);
        fileToUpload = new File(getPath(getApplicationContext(), path));
        Log.e("FILE_PATH", "" + fileToUpload);

        Long ts = System.currentTimeMillis() / 1000;
        OBJECT_KEY = getImageName(path) + ts.toString();
        TransferObserver observer = transferUtility.upload(MY_BUCKET, OBJECT_KEY, fileToUpload, CannedAccessControlList.PublicRead);

        observer.setTransferListener(new TransferListener() {
            @Override
            public void onStateChanged(int id, TransferState state) {

                Log.e("Image_Uploaded", "" + state.name());
                bookImage.setVisibility(View.VISIBLE);

                Log.e("URL_PATH", "https://s3.us-east-2.amazonaws.com/" + MY_BUCKET + "/" + OBJECT_KEY);

                Picasso.with(getApplicationContext())
                        .load("https://s3.us-east-2.amazonaws.com/" + MY_BUCKET + "/" + OBJECT_KEY)
                        .error(R.drawable.logo)
                        .into(bookImage);
            }

            @Override
            public void onProgressChanged(int id, long bytesCurrent, long bytesTotal) {
                int percentage = (int) (bytesCurrent / bytesTotal * 100);
            }

            @Override
            public void onError(int id, Exception ex) {
                Log.e("Error  ", "" + ex);
            }

        });

    }


    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.

                    Log.e("REAL_PATH", "" + getPath(getApplicationContext(), path));

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

    private String getImageName(Uri contentURI) {
        String thePath = "no-path-found";
        String[] filePathColumn = {MediaStore.Images.Media.DISPLAY_NAME};
        Log.e("FILE_PATH_REAL", filePathColumn.length + "");
        Cursor cursor = getContentResolver().query(contentURI, filePathColumn, null, null, null);
        if (cursor.moveToFirst()) {
            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            thePath = cursor.getString(columnIndex);
        }
        cursor.close();
        return thePath;
    }

    public static String getPath(final Context context, final Uri uri) {

        final boolean isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;

        // DocumentProvider
        if (isKitKat && DocumentsContract.isDocumentUri(context, uri)) {
            // ExternalStorageProvider
            if (isExternalStorageDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                if ("primary".equalsIgnoreCase(type)) {
                    return Environment.getExternalStorageDirectory() + "/" + split[1];
                }

                // TODO handle non-primary volumes
            }
            // DownloadsProvider
            else if (isDownloadsDocument(uri)) {

                final String id = DocumentsContract.getDocumentId(uri);
                final Uri contentUri = ContentUris.withAppendedId(
                        Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));

                return getDataColumn(context, contentUri, null, null);
            }
            // MediaProvider
            else if (isMediaDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                Uri contentUri = null;
                if ("image".equals(type)) {
                    contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                } else if ("video".equals(type)) {
                    contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                } else if ("audio".equals(type)) {
                    contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                }

                final String selection = "_id=?";
                final String[] selectionArgs = new String[]{
                        split[1]
                };

                return getDataColumn(context, contentUri, selection, selectionArgs);
            }
        }
        // MediaStore (and general)
        else if ("content".equalsIgnoreCase(uri.getScheme())) {
            return getDataColumn(context, uri, null, null);
        }
        // File
        else if ("file".equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        }

        return null;
    }

    /**
     * Get the value of the data column for this Uri. This is useful for
     * MediaStore Uris, and other file-based ContentProviders.
     *
     * @param context       The context.
     * @param uri           The Uri to query.
     * @param selection     (Optional) Filter used in the query.
     * @param selectionArgs (Optional) Selection arguments used in the query.
     * @return The value of the _data column, which is typically a file path.
     */
    public static String getDataColumn(Context context, Uri uri, String selection,
                                       String[] selectionArgs) {

        Cursor cursor = null;
        final String column = "_data";
        final String[] projection = {
                column
        };

        try {
            cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs,
                    null);
            if (cursor != null && cursor.moveToFirst()) {
                final int column_index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(column_index);
            }
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return null;
    }


    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is ExternalStorageProvider.
     */
    public static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is DownloadsProvider.
     */
    public static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is MediaProvider.
     */
    public static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
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
            getSupportFragmentManager().beginTransaction().add(R.id.container, new AddBooksFragment()).commit();
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

