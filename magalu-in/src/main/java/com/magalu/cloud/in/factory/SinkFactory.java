package com.magalu.cloud.in.factory;

import org.apache.flink.streaming.api.datastream.DataStream;

import com.magalu.cloud.in.domain.PulseEntity;

import org.apache.flink.api.java.tuple.Tuple5;
import org.apache.flink.connector.kafka.sink.KafkaSink;
import org.apache.flink.shaded.jackson2.com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.flink.connector.kafka.sink.KafkaRecordSerializationSchema;
import org.apache.flink.api.common.serialization.SimpleStringSchema;
import org.apache.flink.connector.jdbc.JdbcConnectionOptions;
import org.apache.flink.connector.jdbc.JdbcExecutionOptions;
import org.apache.flink.connector.jdbc.JdbcSink;
import org.apache.flink.api.common.typeinfo.TypeHint;
import org.apache.flink.api.common.typeinfo.TypeInformation;

import java.util.Properties;

public class SinkFactory {

    public static void addKafkaSink(DataStream<Tuple5<String, String, String, String, Double>> aggregatedStream,
            Properties properties) {
        KafkaRecordSerializationSchema<String> kafkaSerializationSchema = KafkaRecordSerializationSchema
                .builder()
                .setTopic((String) properties.get("topic-out"))
                .setValueSerializationSchema(new SimpleStringSchema())
                .build();

        KafkaSink<String> kafkaSink = KafkaSink.<String>builder()
                .setBootstrapServers((String) properties.get("bootstrap.servers"))
                .setRecordSerializer(kafkaSerializationSchema)
                .build();

        aggregatedStream
                .map(value -> {
                    String summarized = new ObjectMapper().writeValueAsString(
                            new PulseEntity(value.f1, value.f2, value.f3, value.f4));
                    return summarized;
                })
                .sinkTo(kafkaSink);
    }

    public static void addJdbcSink(DataStream<Tuple5<String, String, String, String, Double>> aggregatedStream,
            Properties properties) {
        JdbcConnectionOptions jdbcConnectionOptions = new JdbcConnectionOptions.JdbcConnectionOptionsBuilder()
                .withUrl((String) properties.get("db.url"))
                .withDriverName((String) properties.get("db.driver"))
                .withUsername((String) properties.get("db.user"))
                .withPassword((String) properties.get("db.password"))
                .build();

        String insertQuery = "INSERT INTO summarized_pulse (tenant, product_sku, use_unity, summarized_amount) VALUES (?, ?, ?, ?)";

        DataStream<Tuple5<String, String, String, String, Double>> jdbcStream = aggregatedStream
                .map(value -> {
                    String[] parts = value.f0.split("-");
                    return new Tuple5<>(parts[0], parts[1], parts[2], parts[3], value.f4);
                })
                .returns(TypeInformation
                        .of(new TypeHint<Tuple5<String, String, String, String, Double>>() {
                        }));

        jdbcStream.addSink(JdbcSink.sink(
                insertQuery,
                (ps, tuple) -> {
                    ps.setString(1, tuple.f0);
                    ps.setString(2, tuple.f1);
                    ps.setString(3, tuple.f2);
                    ps.setDouble(4, tuple.f4);
                },
                JdbcExecutionOptions.builder()
                        .withBatchSize(1000)
                        .withBatchIntervalMs(200)
                        .withMaxRetries(3)
                        .build(),
                jdbcConnectionOptions));
    }
}