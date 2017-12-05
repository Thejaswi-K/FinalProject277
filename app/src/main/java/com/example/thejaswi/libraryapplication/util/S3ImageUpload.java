package com.example.thejaswi.libraryapplication.util;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

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
import com.example.thejaswi.libraryapplication.view.activities.HomeActivity;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.Date;

/**
 * Created by Mak on 12/4/17.
 */

public class S3ImageUpload {

    Context context;
    Activity activity;
    final private String IDENTITY_POOL_ID = "us-east-2:2822b1aa-1ed9-4bfb-83bb-214798b10cda";
    final  private String S3_BASE_URL = "https://s3.us-east-2.amazonaws.com/";
    final private String MY_BUCKET = "librarycatalogue/BookCovers";
    private String OBJECT_KEY = "";
    private File fileToUpload;
    ImageView bookImage;
    Uri path;
    Date date;
    MediaPath mediaPath;
    Boolean status =false;

    public S3ImageUpload(Context context, Activity activity, Uri path,ImageView bookImage) {

        this.context = context;
        this.activity = activity;
        this.path = path;
        this.bookImage=bookImage;



    }

    public  String upload(){

        AmazonS3 s3 = new AmazonS3Client(Identity());
        s3.setRegion(Region.getRegion(Regions.US_EAST_2));
        TransferUtility tu = transferUtility(s3);
        if( s3Observer(tu))
        {
            return S3_BASE_URL + MY_BUCKET + "/" + OBJECT_KEY;
        }

        return "FAILED";
    }

    public TransferUtility transferUtility(AmazonS3 s3) {

        TransferUtility transferUtility = new TransferUtility(s3, context);

        return transferUtility;

    }


    public boolean s3Observer(TransferUtility transferUtility) {
        mediaPath =  new MediaPath(context);
        fileToUpload = new File( mediaPath.getPath(context, path));
        Long ts = System.currentTimeMillis() / 1000;

        OBJECT_KEY = mediaPath.getImageName(path) + ts.toString();

        TransferObserver observer = transferUtility.upload(MY_BUCKET, OBJECT_KEY, fileToUpload, CannedAccessControlList.PublicRead);


        observer.setTransferListener(new TransferListener() {
            @Override
            public void onStateChanged(int id, TransferState state) {
//

                Log.e("Image_Uploaded", "" + state.name());
                bookImage.setVisibility(View.VISIBLE);
                if(state.name().equals("COMPLETED")){

                    Picasso.with(context)
                            .load( S3_BASE_URL+ MY_BUCKET + "/" + OBJECT_KEY)
                            .error(R.drawable.logo)
                            .into(bookImage);

                }



            }

            @Override
            public void onProgressChanged(int id, long bytesCurrent, long bytesTotal) {
                int percentage = (int) (bytesCurrent / bytesTotal * 100);
            }

            @Override
            public void onError(int id, Exception ex) {
                Log.e("Error  ", "" + ex);
                status=false;
            }

        });

        return status;


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
