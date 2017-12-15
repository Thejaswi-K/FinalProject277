package com.example.thejaswi.libraryapplication.view.activities;

import android.content.res.ColorStateList;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.thejaswi.libraryapplication.R;
import com.example.thejaswi.libraryapplication.Session;
import com.example.thejaswi.libraryapplication.domain.api.APIService;
import com.example.thejaswi.libraryapplication.domain.api.ElasticSearchServiceGenerator;
import com.example.thejaswi.libraryapplication.domain.api.ISBNServiceGenerator;
import com.example.thejaswi.libraryapplication.model.entities.Catalog;
import com.example.thejaswi.libraryapplication.model.entities.ElasticQueryObject;
import com.example.thejaswi.libraryapplication.model.entities.ElasticSearchResult;
import com.example.thejaswi.libraryapplication.model.entities.GoogleBooks;
import com.example.thejaswi.libraryapplication.view.fragment.BooksAdapter;
import com.example.thejaswi.libraryapplication.view.fragment.CartBooksAdapter;

import java.util.LinkedList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchActivity extends AppCompatActivity implements View.OnClickListener {

    EditText search;
    ImageView searchImage;
    LinearLayout filter_screen,background;
    TextView cancel,more;
    Boolean isVisible=false;
    View line1,line2;
    CheckBox addedByMe;
    APIService mAPIService;
    RecyclerView showResult;
    Button submitSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        mAPIService = ElasticSearchServiceGenerator.createService(APIService.class);

        submitSearch = (Button) findViewById(R.id.search_result_button);
        search=(EditText)findViewById(R.id.search);
        searchImage=(ImageView) findViewById(R.id.searchImage);
        filter_screen=(LinearLayout)findViewById(R.id.filter_screen);
        cancel=(TextView)findViewById(R.id.cancel);
        background=(LinearLayout)findViewById(R.id.background);
        more=(TextView)findViewById(R.id.more);
        more.setOnClickListener(this);
        line1=(View)findViewById(R.id.line1);
        line2=(View)findViewById(R.id.line2);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        addedByMe=(CheckBox)findViewById(R.id.addedByMe);

        submitSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(addedByMe.isChecked()){

                    searchAddedByMe();

                }

            }
        });

        if(Session.getEmail().matches("^[a-zA-Z0-9_.+-]+@(?:(?:[a-zA-Z0-9-]+\\.)?[a-zA-Z]+\\.)?(sjsu)\\.edu$")){
            addedByMe.setVisibility(View.VISIBLE);
        }else {
            addedByMe.setVisibility(View.GONE);
        }

        showResult = (RecyclerView) findViewById(R.id.searchResult);
        showResult.setLayoutManager(new LinearLayoutManager(this));


        search.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {}

            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {

                Log.e("TExt_Changed","I am listening");
                if(s.length()>=3)
                elasticQuery(s.toString());
            }
        });
    }

    private void searchAddedByMe() {



        final Call<ElasticSearchResult> call = mAPIService.elasticSearchByLibrarian("librarian.email:"+Session.getEmail());

        call.enqueue(new Callback<ElasticSearchResult>() {
            @Override
            public void onResponse(Call<ElasticSearchResult> call, Response<ElasticSearchResult> response) {

                //Display successful response results

                Log.e("ELASTIC_SEARCH", response.body().getProductSuggest() + "");

                if (response.code() == 200) {


                    List<ElasticSearchResult.ProductSuggest> productSuggests = response.body().getProductSuggest();
                    List<Catalog> allCatalog = new LinkedList<>();
                    for(ElasticSearchResult.ProductSuggest pr : productSuggests){

                        List<ElasticSearchResult.Option> options = pr.getOptions();

                        for(ElasticSearchResult.Option option : options){

                            allCatalog.add(option.getSource());
                        }
                    }

//                    Log.e("All CATALOG", ""+allCatalog.get(0).getAuthor());


                    showResult.setAdapter(new BooksAdapter(getApplicationContext(),allCatalog));


//                    List<GoogleBooks.Item> allItems = response.body().getItems();
//
//                    Log.e("AUTHORS", "" + allItems.get(0).getVolumeInfo().getAuthors().get(0));
//
//
//                    GoogleBooks.VolumeInfo item = getCorrectItem(response.body());
//                    imageUrl = item.getImageLinks().getThumbnail();
//                    setBookImage(imageUrl);
//
//                    bookTitle.setText(item.getTitle());
//                    authorName.setText(item.getAuthors().toString());
//                    yearOfPublication.setText(item.getPublishedDate());
//                    publisher.setText(item.getPublisher());

                }
                //Hide progressbar when done
                // progressBar.setVisibility(View.INVISIBLE);

            }

            @Override
            public void onFailure(Call<ElasticSearchResult> call, Throwable t) {
                // Display error message if the request fails
                Toast.makeText(getApplicationContext(), "Error while Querying elastic search", Toast.LENGTH_SHORT).show();
                //Hide progressbar when done
                //progressBar.setVisibility(View.INVISIBLE);
            }


        });


    }

    private void elasticQuery(String s) {


        ElasticQueryObject querObject = new ElasticQueryObject();
        querObject.getProductSuggest().setText(s);
        querObject.getProductSuggest().getCompletion().setField("keywords");

        final Call<ElasticSearchResult> call = mAPIService.elasticSearch(querObject);

        call.enqueue(new Callback<ElasticSearchResult>() {
            @Override
            public void onResponse(Call<ElasticSearchResult> call, Response<ElasticSearchResult> response) {

                //Display successful response results

                Log.e("ELASTIC_SEARCH", response.body().getProductSuggest() + "");

                if (response.code() == 200) {


                    List<ElasticSearchResult.ProductSuggest> productSuggests = response.body().getProductSuggest();
                    List<Catalog> allCatalog = new LinkedList<>();
                    for(ElasticSearchResult.ProductSuggest pr : productSuggests){

                        List<ElasticSearchResult.Option> options = pr.getOptions();

                        for(ElasticSearchResult.Option option : options){

                            allCatalog.add(option.getSource());
                        }
                    }

//                    Log.e("All CATALOG", ""+allCatalog.get(0).getAuthor());


                    showResult.setAdapter(new BooksAdapter(getApplicationContext(),allCatalog));


//                    List<GoogleBooks.Item> allItems = response.body().getItems();
//
//                    Log.e("AUTHORS", "" + allItems.get(0).getVolumeInfo().getAuthors().get(0));
//
//
//                    GoogleBooks.VolumeInfo item = getCorrectItem(response.body());
//                    imageUrl = item.getImageLinks().getThumbnail();
//                    setBookImage(imageUrl);
//
//                    bookTitle.setText(item.getTitle());
//                    authorName.setText(item.getAuthors().toString());
//                    yearOfPublication.setText(item.getPublishedDate());
//                    publisher.setText(item.getPublisher());

                }
                //Hide progressbar when done
                // progressBar.setVisibility(View.INVISIBLE);

            }

            @Override
            public void onFailure(Call<ElasticSearchResult> call, Throwable t) {
                // Display error message if the request fails
                Toast.makeText(getApplicationContext(), "Error while Querying elastic search", Toast.LENGTH_SHORT).show();
                //Hide progressbar when done
                //progressBar.setVisibility(View.INVISIBLE);
            }


        });
    }

    @Override
    public void onClick(View view) {
        if(isVisible){
            more.setText("More");
            filter_screen.setVisibility(View.GONE);
            search.setHintTextColor(getResources().getColor(android.R.color.black));
            search.setBackgroundColor(getResources().getColor(android.R.color.white));
            search.setTextColor(getResources().getColor(android.R.color.black));
            cancel.setTextColor(getResources().getColor(android.R.color.black));
            background.setBackgroundColor(getResources().getColor(android.R.color.white));
            more.setTextColor(getResources().getColor(android.R.color.black));
            more.setBackgroundDrawable(getResources().getDrawable(R.drawable.edit_text_design));
            searchImage.setImageDrawable(getResources().getDrawable(R.drawable.search));
            isVisible=false;
            line1.setBackgroundColor(getResources().getColor(android.R.color.black));
            line2.setBackgroundColor(getResources().getColor(android.R.color.black));
        }else {
            more.setText("Less");
            filter_screen.setVisibility(View.VISIBLE);
            search.setHintTextColor(getResources().getColor(android.R.color.white));
            search.setTextColor(getResources().getColor(android.R.color.white));
            cancel.setTextColor(getResources().getColor(android.R.color.white));
            background.setBackgroundColor(getResources().getColor(android.R.color.black));
            search.setBackgroundColor(getResources().getColor(android.R.color.black));
            more.setTextColor(getResources().getColor(android.R.color.white));
            more.setBackgroundDrawable(getResources().getDrawable(R.drawable.white_rect_rounded));
            searchImage.setImageDrawable(getResources().getDrawable(R.drawable.ic_search_black_24dp));
            isVisible=true;
            line1.setBackgroundColor(getResources().getColor(android.R.color.white));
            line2.setBackgroundColor(getResources().getColor(android.R.color.white));
        }

    }
}
