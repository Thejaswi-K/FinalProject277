package com.example.thejaswi.libraryapplication.model.entities;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Mak on 12/7/17.
 */

public class ElasticSearchResult {



        private Shards shards;
        private List<ProductSuggest> productSuggest = null;
        private Map<String, Object> additionalProperties = new HashMap<String, Object>();

        public Shards getShards() {
            return shards;
        }

        public void setShards(Shards shards) {
            this.shards = shards;
        }

        public List<ProductSuggest> getProductSuggest() {
            return productSuggest;
        }

        public void setProductSuggest(List<ProductSuggest> productSuggest) {
            this.productSuggest = productSuggest;
        }

        public Map<String, Object> getAdditionalProperties() {
            return this.additionalProperties;
        }

        public void setAdditionalProperty(String name, Object value) {
            this.additionalProperties.put(name, value);
        }

    public class Option {

        private String text;
        private String index;
        private String type;
        private String id;
        private int score;
        private Catalog _source;
        private Map<String, Object> additionalProperties = new HashMap<String, Object>();

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }

        public String getIndex() {
            return index;
        }

        public void setIndex(String index) {
            this.index = index;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public int getScore() {
            return score;
        }

        public void setScore(int score) {
            this.score = score;
        }

        public Catalog getSource() {
            return _source;
        }

        public void setSource(Catalog source) {
            this._source = source;
        }

        public Map<String, Object> getAdditionalProperties() {
            return this.additionalProperties;
        }

        public void setAdditionalProperty(String name, Object value) {
            this.additionalProperties.put(name, value);
        }

    }


    public class ProductSuggest {

        private String text;
        private int offset;
        private int length;
        private List<Option> options = null;
        private Map<String, Object> additionalProperties = new HashMap<String, Object>();

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }

        public int getOffset() {
            return offset;
        }

        public void setOffset(int offset) {
            this.offset = offset;
        }

        public int getLength() {
            return length;
        }

        public void setLength(int length) {
            this.length = length;
        }

        public List<Option> getOptions() {
            return options;
        }

        public void setOptions(List<Option> options) {
            this.options = options;
        }

        public Map<String, Object> getAdditionalProperties() {
            return this.additionalProperties;
        }

        public void setAdditionalProperty(String name, Object value) {
            this.additionalProperties.put(name, value);
        }

    }
    public class Shards {

        private int total;
        private int successful;
        private int failed;
        private Map<String, Object> additionalProperties = new HashMap<String, Object>();

        public int getTotal() {
            return total;
        }

        public void setTotal(int total) {
            this.total = total;
        }

        public int getSuccessful() {
            return successful;
        }

        public void setSuccessful(int successful) {
            this.successful = successful;
        }

        public int getFailed() {
            return failed;
        }

        public void setFailed(int failed) {
            this.failed = failed;
        }

        public Map<String, Object> getAdditionalProperties() {
            return this.additionalProperties;
        }

        public void setAdditionalProperty(String name, Object value) {
            this.additionalProperties.put(name, value);
        }

    }
}
