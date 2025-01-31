package com.magalu.cloud.in.factory;

import org.apache.flink.connector.kafka.source.KafkaSource;
import org.apache.flink.connector.kafka.source.enumerator.initializer.OffsetsInitializer;
import org.apache.flink.formats.json.JsonDeserializationSchema;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

import com.magalu.cloud.in.domain.PulseEntity;

import org.apache.flink.api.common.eventtime.WatermarkStrategy;

import java.util.Properties;

public class KafkaSourceFactory {

    public static DataStream<PulseEntity> createPulseStream(StreamExecutionEnvironment env, Properties properties) {
        KafkaSource<PulseEntity> kafkaSource = KafkaSource.<PulseEntity>builder()
                .setProperties(properties)
                .setTopics((String) properties.get("topic-in"))
                .setStartingOffsets(OffsetsInitializer.earliest())
                .setValueOnlyDeserializer(new JsonDeserializationSchema<>(PulseEntity.class))
                .build();

        return env.fromSource(
                kafkaSource,
                WatermarkStrategy.noWatermarks(),
                "pulseSource");
    }
}