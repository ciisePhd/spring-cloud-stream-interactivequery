package com.demo.spring.cloud.stream.interactivequery;


import org.springframework.boot.context.properties.ConfigurationProperties;



@ConfigurationProperties(prefix = "app.book.tracker")
public class BookTrackerProperties {

    private String bookIds;

    private String storeName;

    public String getBookIds() {
        return bookIds;
    }

    public void setBookIds(String bookIds) {
        this.bookIds = bookIds;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }


}
