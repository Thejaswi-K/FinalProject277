package com.example.thejaswi.libraryapplication.model.entities;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Mak on 12/20/17.
 */


public class ElasticSearchPojo {

    private int took;
    private boolean timedOut;
    private Shards shards;
    private Hits hits;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    public int getTook() {
        return took;
    }

    public void setTook(int took) {
        this.took = took;
    }

    public boolean isTimedOut() {
        return timedOut;
    }

    public void setTimedOut(boolean timedOut) {
        this.timedOut = timedOut;
    }

    public Shards getShards() {
        return shards;
    }

    public void setShards(Shards shards) {
        this.shards = shards;
    }

    public Hits getHits() {
        return hits;
    }

    public void setHits(Hits hits) {
        this.hits = hits;
    }

    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }


    public   class BookSet {

        private int bookId;
        private String status;
        private Object fine;
        private Object issuer;
        private Map<String, Object> additionalProperties = new HashMap<String, Object>();

        public int getBookId() {
            return bookId;
        }

        public void setBookId(int bookId) {
            this.bookId = bookId;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public Object getFine() {
            return fine;
        }

        public void setFine(Object fine) {
            this.fine = fine;
        }

        public Object getIssuer() {
            return issuer;
        }

        public void setIssuer(Object issuer) {
            this.issuer = issuer;
        }

        public Map<String, Object> getAdditionalProperties() {
            return this.additionalProperties;
        }

        public void setAdditionalProperty(String name, Object value) {
            this.additionalProperties.put(name, value);
        }

    }



    public class Hit {

        private String index;
        private String type;
        private String id;
        private double score;
        private Catalog _source;
        private Map<String, Object> additionalProperties = new HashMap<String, Object>();

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

        public double getScore() {
            return score;
        }

        public void setScore(double score) {
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

    public  class Hits {

        private int total;
        private double maxScore;
        private List<Hit> hits = null;
        private Map<String, Object> additionalProperties = new HashMap<String, Object>();

        public int getTotal() {
            return total;
        }

        public void setTotal(int total) {
            this.total = total;
        }

        public double getMaxScore() {
            return maxScore;
        }

        public void setMaxScore(double maxScore) {
            this.maxScore = maxScore;
        }

        public List<Hit> getHits() {
            return hits;
        }

        public void setHits(List<Hit> hits) {
            this.hits = hits;
        }

        public Map<String, Object> getAdditionalProperties() {
            return this.additionalProperties;
        }

        public void setAdditionalProperty(String name, Object value) {
            this.additionalProperties.put(name, value);
        }

    }


    public  class Shards {

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

    public  class Source {

        private int catalogId;
        private List<BookSet> bookSet = null;
        private List<Object> waitList = null;
        private String title;
        private String author;
        private String imageUrl;
        private Object isbn;
        private String publisher;
        private int callNumber;
        private String year;
        private String location;
        private int numberOfCopies;
        private List<String> keywords = null;
        private Object category;
        private Librarian librarian;
        private Map<String, Object> additionalProperties = new HashMap<String, Object>();

        public int getCatalogId() {
            return catalogId;
        }

        public void setCatalogId(int catalogId) {
            this.catalogId = catalogId;
        }

        public List<BookSet> getBookSet() {
            return bookSet;
        }

        public void setBookSet(List<BookSet> bookSet) {
            this.bookSet = bookSet;
        }

        public List<Object> getWaitList() {
            return waitList;
        }

        public void setWaitList(List<Object> waitList) {
            this.waitList = waitList;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getAuthor() {
            return author;
        }

        public void setAuthor(String author) {
            this.author = author;
        }

        public String getImageUrl() {
            return imageUrl;
        }

        public void setImageUrl(String imageUrl) {
            this.imageUrl = imageUrl;
        }

        public Object getIsbn() {
            return isbn;
        }

        public void setIsbn(Object isbn) {
            this.isbn = isbn;
        }

        public String getPublisher() {
            return publisher;
        }

        public void setPublisher(String publisher) {
            this.publisher = publisher;
        }

        public int getCallNumber() {
            return callNumber;
        }

        public void setCallNumber(int callNumber) {
            this.callNumber = callNumber;
        }

        public String getYear() {
            return year;
        }

        public void setYear(String year) {
            this.year = year;
        }

        public String getLocation() {
            return location;
        }

        public void setLocation(String location) {
            this.location = location;
        }

        public int getNumberOfCopies() {
            return numberOfCopies;
        }

        public void setNumberOfCopies(int numberOfCopies) {
            this.numberOfCopies = numberOfCopies;
        }

        public List<String> getKeywords() {
            return keywords;
        }

        public void setKeywords(List<String> keywords) {
            this.keywords = keywords;
        }

        public Object getCategory() {
            return category;
        }

        public void setCategory(Object category) {
            this.category = category;
        }

        public Librarian getLibrarian() {
            return librarian;
        }

        public void setLibrarian(Librarian librarian) {
            this.librarian = librarian;
        }

        public Map<String, Object> getAdditionalProperties() {
            return this.additionalProperties;
        }

        public void setAdditionalProperty(String name, Object value) {
            this.additionalProperties.put(name, value);
        }

    }

}