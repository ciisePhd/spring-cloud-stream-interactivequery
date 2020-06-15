package com.demo.spring.cloud.stream.interactivequery.controller;

import com.demo.spring.cloud.stream.interactivequery.BookTrackerProperties;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.kafka.streams.state.QueryableStoreTypes;
import org.apache.kafka.streams.state.ReadOnlyKeyValueStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.binder.kafka.streams.InteractiveQueryService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BookController {

    private final Log logger = LogFactory.getLog(getClass());

    @Autowired
    private InteractiveQueryService queryService;

    ReadOnlyKeyValueStore<Integer, Long> keyValueStore;

    @Autowired
    BookTrackerProperties bookTrackerProperties;


    @RequestMapping("/book/idx")
    public String countIds(@RequestParam(value="id") Integer id) {

        keyValueStore = queryService.getQueryableStore(bookTrackerProperties.getStoreName(), QueryableStoreTypes.keyValueStore());

        if (keyValueStore != null & keyValueStore.get(id) !=null) {


            System.out.println("Product ID: " + id + " Count: " + keyValueStore.get(id));

            return " Count: " + keyValueStore.get(id);

        }else{
            return " not found";
        }


    }
}
