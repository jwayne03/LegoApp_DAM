package com.wayne.brickapp.model;

import java.util.ArrayList;
import java.util.List;

public class ApiThemes {

    private int count;
    private String next;
    private String previous;
    private List<Theme> results;

    public ApiThemes(int count, String next, String previous) {
        this.count = count;
        this.next = next;
        this.previous = previous;
        this.results = new ArrayList<>();
    }

    public int getCount() { return count; }
    public void setCount(int count) { this.count = count; }
    public String getNext() { return next; }
    public void setNext(String next) { this.next = next; }
    public String getPrevious() { return previous; }
    public void setPrevious(String previous) { this.previous = previous; }
//    public List<Theme> getResults() { return results; }
//    public void setResults(List<Theme> results) { this.results = results; }
}
