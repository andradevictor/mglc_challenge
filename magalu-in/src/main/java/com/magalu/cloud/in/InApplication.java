package com.magalu.cloud.in;

import java.io.InputStream;
import java.util.Properties;

import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import com.magalu.cloud.in.builder.PipelineBuilder;

public class InApplication {

    public static void main(String[] args) throws Exception {
        Properties properties = new Properties();
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

        try (InputStream stream = InApplication.class.getClassLoader()
                .getResourceAsStream("application.properties")) {
            properties.load(stream);
        }

        PipelineBuilder pipelineBuilder = new PipelineBuilder(env, properties);
        pipelineBuilder.buildPipeline();

        env.execute("Flink Kafka Stream Processor");
    }
}
