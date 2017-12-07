package com.example.thejaswi.libraryapplication.model.entities;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Mak on 12/7/17.
 */

public class ElasticQueryObject {


        private ProductSuggest productSuggest;

    public ElasticQueryObject() {
        this.productSuggest = new ProductSuggest();
    }

    public ProductSuggest getProductSuggest() {
            return productSuggest;
        }

        public void setProductSuggest(ProductSuggest productSuggest) {
            this.productSuggest = productSuggest;
        }


    public class Completion {

        private String field;


        public String getField() {
            return field;
        }

        public void setField(String field) {
            this.field = field;
        }

    }

    public class ProductSuggest {

        private String text;
        private Completion completion;

        public ProductSuggest() {
            this.completion = new Completion();
        }

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }

        public Completion getCompletion() {
            return completion;
        }

        public void setCompletion(Completion completion) {
            this.completion = completion;
        }
    }
}
