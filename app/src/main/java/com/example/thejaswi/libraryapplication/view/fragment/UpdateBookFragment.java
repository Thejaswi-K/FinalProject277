package com.example.thejaswi.libraryapplication.view.fragment;

import android.app.Fragment;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.thejaswi.libraryapplication.R;
import com.example.thejaswi.libraryapplication.Session;
import com.example.thejaswi.libraryapplication.domain.api.APIService;
import com.example.thejaswi.libraryapplication.domain.api.ISBNServiceGenerator;
import com.example.thejaswi.libraryapplication.domain.api.ServiceGenerator;
import com.example.thejaswi.libraryapplication.model.entities.Catalog;
import com.example.thejaswi.libraryapplication.model.entities.PostCatalog;
import com.example.thejaswi.libraryapplication.util.AccessPermissions;
import com.example.thejaswi.libraryapplication.util.AwsUpload;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ExecutionException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.app.Activity.RESULT_OK;

/**
 * Created by Mak on 12/16/17.
 */


public class UpdateBookFragment extends Fragment {


    ImageView bookImage;
    EditText authorName;
    EditText bookTitle;
    EditText callNumber;
    EditText publisher;
    EditText numberOfCopies;
    EditText yearOfPublication;
    EditText locationInTheLibrary;
    EditText category;
    EditText keyWords;
    EditText addNumberOfCopies;
    Button submitButton;
    String imageUrl;
    Boolean imageUpdated;
    String isbn;
    APIService mAPIService;
    APIService addBookAPIService;
    Uri path;
    int catalog_id;

    final private int MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE =899;
    private int PHOTO_SELECTED = 777;
    private Bitmap bitMap;

    Catalog catalog;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addBookAPIService = ServiceGenerator.createService(APIService.class);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

//        return super.onCreateView(inflater, container, savedInstanceState);

        if (container != null) {
            container.removeAllViews();
        }

        View view = inflater.inflate(R.layout.fragment_book_form, container, false);

        bookImage = (ImageView) view.findViewById(R.id.bookImage);
        authorName = (EditText) view.findViewById(R.id.bookAuthor);
        bookTitle = (EditText) view.findViewById(R.id.bookTitle);
        callNumber = (EditText) view.findViewById(R.id.bookCallNumber);
        publisher = (EditText) view.findViewById(R.id.bookPublisher);
        numberOfCopies = (EditText) view.findViewById(R.id.bookCopies);
        addNumberOfCopies = (EditText) view.findViewById(R.id.addBookCopies);
        yearOfPublication = (EditText) view.findViewById(R.id.bookPublishedYear);
        locationInTheLibrary = (EditText) view.findViewById(R.id.bookLocation);
        category = (EditText) view.findViewById(R.id.bookCategory);
        keyWords = (EditText) view.findViewById(R.id.bookKeywords);
        submitButton = (Button) view.findViewById(R.id.bookSubmit);
        imageUpdated =false;


        if(getArguments().containsKey("updateBook")){

            Catalog book = (Catalog) getArguments().getSerializable("updateBook");

            numberOfCopies.setVisibility(View.INVISIBLE);
            addNumberOfCopies.setVisibility(View.VISIBLE);
            addNumberOfCopies.setText(Integer.toString(book.getCatalog_id()));
            authorName.setText(book.getAuthor());
            bookTitle.setText(book.getTitle());
            callNumber.setText(Long.toString(book.getCall_number()));
            publisher.setText( book.getPublisher());
            yearOfPublication.setText( book.getYear());
            locationInTheLibrary.setText( book.getLocation());
            keyWords.setText(showKeywords(book.getKeywords()));
            imageUrl=book.getImage_url();
            setBookImage(imageUrl);
            catalog_id = book.getCatalog_id();
        }



        bookImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            Log.e("UPDATE_BOOK","IMAGE CLICKED");

                checkPermission();

            }
        });


        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.e("UPDATE_BOOK","SUBMIT CLICKED");

                Catalog item = new Catalog();

                item.setAuthor(authorName.getText().toString());
                item.setTitle(bookTitle.getText().toString());
                item.setImage_url(imageUrl);
                item.setCall_number(Integer.parseInt(callNumber.getText().toString()));
                item.setPublisher(publisher.getText().toString());
                item.setNumber_of_copies(Integer.parseInt(addNumberOfCopies.getText().toString()));
                item.setYear(yearOfPublication.getText().toString());
                item.setLocation(locationInTheLibrary.getText().toString());
                item.setKeywords(getKeywords(keyWords.getText().toString()));
                item.setCatalog_id(catalog_id);


                if (!imageUpdated) {

                    submitResult(item);

                } else{
                    try {

                        imageUrl = uploadImage();

                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    item.setImage_url(imageUrl);
                    submitResult(item);
                }

            }
        });



        return view;

    }

    public void setBookImage(String url) {

        Picasso.with(getActivity().getApplicationContext()).load(url).into(bookImage);

    }

    private void submitResult(Catalog item) {

        PostCatalog postCatalog=new PostCatalog();
        postCatalog.setCatalog(item);
        postCatalog.setEmail(Session.getEmail());
        System.out.println("Post Catalog Email :"+postCatalog.getEmail() + " Catalog :"+postCatalog.getCatalog());

        final Call<String> call = addBookAPIService.addBook(postCatalog);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {

                //Display successful response results

                Log.e("ADDED_BOOK", response.body() + "");

                if (response.code() == 200) {

                    Toast.makeText(getActivity().getApplicationContext(), "Successfully added book", Toast.LENGTH_SHORT).show();

                }
                //Hide progressbar when done
                // progressBar.setVisibility(View.INVISIBLE);

            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                // Display error message if the request fails
                Toast.makeText(getActivity().getApplicationContext(), "Error while Fetching books details", Toast.LENGTH_SHORT).show();
                //Hide progressbar when done
                //progressBar.setVisibility(View.INVISIBLE);
            }


        });

    }


    public String showKeywords(Set<String> keywords){

        String result ="";

        for( String word : keywords){

            result+=word+",";
        }

        return  result;
    }

    public Set<String> getKeywords(String s) {

        Set<String> set = new HashSet<String>();

        String[] res = s.split(",");

        for (int i = 0; i < res.length; i++) {

            set.add(res[i]);
        }

        return set;
    }




    public String uploadImage() throws ExecutionException, InterruptedException {

        AwsUpload awsUpload = new AwsUpload(getActivity().getApplicationContext(),getActivity(),path);

        imageUpdated =true;
        return awsUpload.execute().get();
    }


    public void checkPermission(){
        Log.e("UPDATE_BOOK","Image Permissions");

        AccessPermissions accessPermissions = new AccessPermissions(getActivity().getApplicationContext(), getActivity(),MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);
        if (accessPermissions.validateStorageAccessPermission()){

            selectImage();
        }

    }

    public void selectImage() {

        Log.e("UPDATE_BOOK","Image selectred");

        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        startActivityForResult(intent, PHOTO_SELECTED);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PHOTO_SELECTED && resultCode == RESULT_OK && data != null) {

            path = data.getData();
            try {

                Log.e("S3_UPLOAD", "PATH==>" + path.toString());
                //MediaStore.Images.Media.getContentUri(path);
                bitMap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), path);
                bookImage.setImageBitmap(bitMap);
                bookImage.setVisibility(View.VISIBLE);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
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



}
