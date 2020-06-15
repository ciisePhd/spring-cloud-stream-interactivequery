package com.demo.spring.cloud.stream.interactivequery.process;

import com.demo.spring.cloud.stream.interactivequery.BookTrackerProperties;
import com.demo.spring.cloud.stream.interactivequery.model.Book;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.common.utils.Bytes;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.kstream.Grouped;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.Materialized;
import org.apache.kafka.streams.state.KeyValueStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.support.serializer.JsonSerde;
import org.springframework.stereotype.Component;

import java.util.function.Function;


@Component
public class BookCountApplication {


    @Autowired
    BookTrackerProperties bookTrackerProperties;



    @Bean
    public Function<KStream<Object, Book>, KStream<Integer, Long>> process() {


        return input -> input
                .map((key, value) -> new KeyValue<>(value.getId(), value))
                .groupByKey(Grouped.with(Serdes.Integer(), new JsonSerde<>(Book.class)))
                .count(Materialized.<Integer, Long, KeyValueStore<Bytes, byte[]>>as(bookTrackerProperties.getStoreName())
                        .withKeySerde(Serdes.Integer())
                        .withValueSerde(Serdes.Long()))
                .toStream();
    }

}