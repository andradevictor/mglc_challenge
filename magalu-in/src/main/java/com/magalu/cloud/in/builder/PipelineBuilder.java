package com.magalu.cloud.in.builder;

import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

import com.magalu.cloud.in.domain.PulseEntity;
import com.magalu.cloud.in.factory.KafkaSourceFactory;
import com.magalu.cloud.in.factory.SinkFactory;
import com.magalu.cloud.in.strategy.AggregationStrategy;

import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.api.java.tuple.Tuple5;

import java.util.Properties;

public class PipelineBuilder {

    private final StreamExecutionEnvironment env;
    private final Properties properties;

    public PipelineBuilder(StreamExecutionEnvironment env, Properties properties) {
        this.env = env;
        this.properties = properties;
    }

    public void buildPipeline() {
        DataStream<PulseEntity> pulseStream = KafkaSourceFactory.createPulseStream(env, properties);

        DataStream<Tuple5<String, String, String, String, Double>> aggregatedStream = AggregationStrategy
                .aggregate(pulseStream);

        SinkFactory.addKafkaSink(aggregatedStream, properties);
        SinkFactory.addJdbcSink(aggregatedStream, properties);
    }
}