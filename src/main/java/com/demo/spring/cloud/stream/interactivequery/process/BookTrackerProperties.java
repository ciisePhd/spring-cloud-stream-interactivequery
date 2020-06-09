package com.demo.spring.cloud.stream.interactivequery.process;


import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "app.book.tracker")
public class BookTrackerProperties {

    private String bookIds;

    public String getBookIds() {
        return bookIds;
    }

    public void setBookIds(String bookIds) {
        this.bookIds = bookIds;
    }


}
