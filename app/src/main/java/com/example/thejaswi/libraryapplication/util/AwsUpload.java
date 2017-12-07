package com.example.thejaswi.libraryapplication.util;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;

import com.amazonaws.auth.CognitoCachingCredentialsProvider;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;

import java.io.File;

/**
 * Created by Mak on 12/6/17.
 */

public class AwsUpload extends AsyncTask<String, ProgressDialog, String> {


    Context context;
    Activity activity;
    Uri path;
    final private String IDENTITY_POOL_ID = "us-east-2:2822b1aa-1ed9-4bfb-83bb-214798b10cda";
    final  private String S3_BASE_URL = "https://s3.us-east-2.amazonaws.com/";
    final private String MY_BUCKET = "librarycatalogue/BookCovers";
    private String OBJECT_KEY = "";
    private File fileToUpload;
    MediaPath mediaPath;
    ProgressDialog pg ;

    public AwsUpload(Context context, Activity activity, Uri path) {
        this.context = context;
        this.activity = activity;
        this.path = path;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        pg = new ProgressDialog(activity);
        pg.setMessage("Processing request");
        pg.show();
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        pg.dismiss();
    }

    @Override
    protected String doInBackground(String... strings) {

        AmazonS3 s3 = new AmazonS3Client(Identity());
        s3.setRegion(Region.getRegion(Regions.US_EAST_2));
        mediaPath =  new MediaPath(context);
        fileToUpload = new File( mediaPath.getPath(context, path));
        Long ts = System.currentTimeMillis() / 1000;

        OBJECT_KEY = mediaPath.getImageName(path) + ts.toString();

        PutObjectRequest por = new PutObjectRequest(MY_BUCKET, OBJECT_KEY, fileToUpload);


            //making the object Public
            por.setCannedAcl(CannedAccessControlList.PublicRead);
            s3.putObject(por);

        return S3_BASE_URL+ MY_BUCKET + "/" + OBJECT_KEY;
    }



    public CognitoCachingCredentialsProvider Identity() {

        CognitoCachingCredentialsProvider credentialsProvider = new CognitoCachingCredentialsProvider(
                context, IDENTITY_POOL_ID
                , // Identity pool ID
                Regions.US_EAST_2 // Region
        );

        return credentialsProvider;
    }
}
