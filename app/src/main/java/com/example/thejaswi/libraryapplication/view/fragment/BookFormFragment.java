package com.example.thejaswi.libraryapplication.view.fragment;

import android.app.Fragment;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.thejaswi.libraryapplication.R;
import com.example.thejaswi.libraryapplication.Session;
import com.example.thejaswi.libraryapplication.domain.api.APIService;
import com.example.thejaswi.libraryapplication.domain.api.ISBNServiceGenerator;
import com.example.thejaswi.libraryapplication.domain.api.ServiceGenerator;
import com.example.thejaswi.libraryapplication.model.entities.Catalog;
import com.example.thejaswi.libraryapplication.model.entities.GoogleBooks;
import com.example.thejaswi.libraryapplication.model.entities.PostCatalog;
import com.example.thejaswi.libraryapplication.util.AccessPermissions;
import com.example.thejaswi.libraryapplication.util.AwsUpload;
import com.example.thejaswi.libraryapplication.util.S3ImageUpload;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutionException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.app.Activity.RESULT_OK;

/**
 * Created by Mak on 12/6/17.
 */

public class BookFormFragment extends Fragment {


    ImageView bookImage;
    EditText authorName;
    EditText bookTitle;
    EditText callNumber;
    EditText publisher;
    EditText numberOfCopies;
    EditText yearOfPublication;
    Spinner locationInTheLibrary;
    EditText currentStatus;
    EditText keyWords;
    Button submitButton;
    String imageUrl;
    String isbn;
    APIService mAPIService;
    APIService addBookAPIService;
    Uri path;

    final private int MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE =899;
    private int PHOTO_SELECTED = 777;
    private Bitmap bitMap;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mAPIService = ISBNServiceGenerator.createService(APIService.class);
        addBookAPIService = ServiceGenerator.createService(APIService.class);




    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
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
        yearOfPublication = (EditText) view.findViewById(R.id.bookPublishedYear);
        locationInTheLibrary = (Spinner) view.findViewById(R.id.bookLocation);
        currentStatus = (EditText) view.findViewById(R.id.bookCurrentStatus);
        keyWords = (EditText) view.findViewById(R.id.bookKeywords);
        submitButton = (Button) view.findViewById(R.id.bookSubmit);


        List<String> list=new ArrayList<>();
        list.add("Select Location");
        list.add("floor 1");
        list.add("floor 2");
        list.add("floor 3");
        list.add("floor 4");
        ArrayAdapter<String> adapter=new ArrayAdapter<String>(getActivity(),R.layout.spinner_layout,R.id.text,list);
        locationInTheLibrary.setAdapter(adapter);

        if (getArguments().containsKey("ISBN")) {

            fillForm(getArguments().getString("ISBN"));
        }

        bookImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                checkPermission();

            }
        });

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(locationInTheLibrary.getSelectedItemPosition()!=0){
                Catalog item = new Catalog();

                item.setAuthor(authorName.getText().toString());
                item.setTitle(bookTitle.getText().toString());
                item.setImage_url(imageUrl);
                item.setCall_number(Integer.parseInt(callNumber.getText().toString()));
                item.setPublisher(publisher.getText().toString());
                item.setNumber_of_copies(Integer.parseInt(numberOfCopies.getText().toString()));
                item.setYear(yearOfPublication.getText().toString());
                item.setLocation(locationInTheLibrary.getSelectedItem().toString());
                item.setKeywords(getKeywords(keyWords.getText().toString()));

                if (getArguments().containsKey("ISBN")) {

                    submitResult(item);

                } else {
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
            }else {
                    Toast.makeText(getActivity(), "Please select book location", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return view;
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


    public Set<String> getKeywords(String s) {

        Set<String> set = new HashSet<String>();

        String[] res = s.split(",");

        for (int i = 0; i < res.length; i++) {

            set.add(res[i]);
        }

        return set;
    }

    public void fillForm(String isbn) {

        this.isbn = isbn;

        final Call<GoogleBooks> call = mAPIService.getISBNDetails("ISBN:" + isbn);
        call.enqueue(new Callback<GoogleBooks>() {
            @Override
            public void onResponse(Call<GoogleBooks> call, Response<GoogleBooks> response) {

                //Display successful response results

                Log.e("GOOGLE_API", response.body() + "");

                if (response.code() == 200) {

                    List<GoogleBooks.Item> allItems = response.body().getItems();

                    Log.e("AUTHORS", "" + allItems.get(0).getVolumeInfo().getAuthors().get(0));


                    GoogleBooks.VolumeInfo item = getCorrectItem(response.body());
                    imageUrl = item.getImageLinks().getThumbnail();
                    setBookImage(imageUrl);

                    bookTitle.setText(item.getTitle());
                    authorName.setText(item.getAuthors().toString());
                    yearOfPublication.setText(item.getPublishedDate());
                    publisher.setText(item.getPublisher());

                }
                //Hide progressbar when done
                // progressBar.setVisibility(View.INVISIBLE);

            }

            @Override
            public void onFailure(Call<GoogleBooks> call, Throwable t) {
                // Display error message if the request fails
                Toast.makeText(getActivity().getApplicationContext(), "Error while Fetching books details", Toast.LENGTH_SHORT).show();
                //Hide progressbar when done
                //progressBar.setVisibility(View.INVISIBLE);
            }


        });

    }


    public String getAuthors(List<String> arr){

        String res ="";
        for(int i =0 ; i< arr.size(); i++){

            res+=arr.get(i)+",";
        }
        return res;
    }
    public void setBookImage(String url) {

        Picasso.with(getActivity().getApplicationContext()).load(url).into(bookImage);

    }

    public GoogleBooks.VolumeInfo getCorrectItem(GoogleBooks gb) {

        List<GoogleBooks.Item> items = gb.getItems();

        Log.e("ISBN_VALUE",isbn);
        for (int i = 0; i < items.size(); i++) {

            GoogleBooks.VolumeInfo volumeInfo = items.get(i).getVolumeInfo();
            List<GoogleBooks.IndustryIdentifier> identifiers = volumeInfo.getIndustryIdentifiers();

            for (GoogleBooks.IndustryIdentifier id : identifiers) {

                Log.e("IDENTIFIER",id.getIdentifier());
                if ((id.getIdentifier()).equals(isbn))
                    return volumeInfo;
            }

        }

        return null;
    }


  // upload image to s3

    public void checkPermission(){

        AccessPermissions accessPermissions = new AccessPermissions(getActivity().getApplicationContext(), getActivity(),MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);
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


    public String uploadImage() throws ExecutionException, InterruptedException {

        AwsUpload awsUpload = new AwsUpload(getActivity().getApplicationContext(),getActivity(),path);

        return awsUpload.execute().get();
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
