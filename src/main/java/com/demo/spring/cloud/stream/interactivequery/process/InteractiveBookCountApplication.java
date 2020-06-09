package com.demo.spring.cloud.stream.interactivequery.process;

import com.demo.spring.cloud.stream.interactivequery.model.Book;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.common.utils.Bytes;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.kstream.Grouped;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.Materialized;
import org.apache.kafka.streams.state.KeyValueStore;
import org.apache.kafka.streams.state.QueryableStoreTypes;
import org.apache.kafka.streams.state.ReadOnlyKeyValueStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.stream.binder.kafka.streams.InteractiveQueryService;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.support.serializer.JsonSerde;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

@EnableConfigurationProperties(BookTrackerProperties.class)
@RestController
public class InteractiveBookCountApplication {

    private static final String STORE_NAME = "book-id-count-store";


    private final Log logger = LogFactory.getLog(getClass());

    @Autowired
    private InteractiveQueryService queryService;

    ReadOnlyKeyValueStore<Integer, Long> keyValueStore;


    @Bean
    public Function<KStream<Object, Book>, KStream<Integer, Long>> process() {

        return input -> input
                .map((key, value) -> new KeyValue<>(value.getId(), value))
                .groupByKey(Grouped.with(Serdes.Integer(), new JsonSerde<>(Book.class)))
                .count(Materialized.<Integer, Long, KeyValueStore<Bytes, byte[]>>as(STORE_NAME)
                        .withKeySerde(Serdes.Integer())
                        .withValueSerde(Serdes.Long()))
                .toStream();
    }

    @RequestMapping("/book/idx")
    public String countIds(@RequestParam(value="id") Integer id) {

        keyValueStore = queryService.getQueryableStore(STORE_NAME, QueryableStoreTypes.keyValueStore());

        if (keyValueStore != null & keyValueStore.get(id) !=null) {


                System.out.println("Product ID: " + id + " Count: " + keyValueStore.get(id));
            return " Count: " + keyValueStore.get(id);

        }else{
            return " not found";
        }


    }
⁄


}