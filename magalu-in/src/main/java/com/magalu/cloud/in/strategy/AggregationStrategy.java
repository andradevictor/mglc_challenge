package com.magalu.cloud.in.strategy;

import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.api.java.tuple.Tuple5;
import org.apache.flink.api.common.functions.AggregateFunction;
import org.apache.flink.api.java.functions.KeySelector;
import org.apache.flink.streaming.api.windowing.assigners.TumblingProcessingTimeWindows;
import org.apache.flink.streaming.api.windowing.time.Time;

import com.magalu.cloud.in.domain.PulseEntity;

import org.apache.flink.api.common.typeinfo.TypeHint;
import org.apache.flink.api.common.typeinfo.TypeInformation;

public class AggregationStrategy {

    public static DataStream<Tuple5<String, String, String, String, Double>> aggregate(
            DataStream<PulseEntity> pulseStream) {
        return pulseStream
                .map(json -> {
                    String tenant = json.getTenant();
                    String productSku = json.getProductSku();
                    String usedUnity = json.getUseUnity();
                    double usedAmount = json.getUsedAmount();
                    return new Tuple5<>(tenant + "-" + productSku + "-" + usedUnity, tenant, productSku, usedUnity,
                            usedAmount);
                })
                .returns(TypeInformation.of(new TypeHint<Tuple5<String, String, String, String, Double>>() {
                }))
                .keyBy((KeySelector<Tuple5<String, String, String, String, Double>, String>) value -> value.f0)
                .window(TumblingProcessingTimeWindows.of(Time.seconds(30)))
                .aggregate(
                        new AggregateFunction<Tuple5<String, String, String, String, Double>, Tuple5<String, String, String, String, Double>, Tuple5<String, String, String, String, Double>>() {
                            @Override
                            public Tuple5<String, String, String, String, Double> createAccumulator() {
                                return new Tuple5<>("", "", "", "", 0.0);
                            }

                            @Override
                            public Tuple5<String, String, String, String, Double> add(
                                    Tuple5<String, String, String, String, Double> value,
                                    Tuple5<String, String, String, String, Double> accumulator) {
                                return new Tuple5<>(value.f0, value.f1, value.f2, value.f3, accumulator.f4 + value.f4);
                            }

                            @Override
                            public Tuple5<String, String, String, String, Double> getResult(
                                    Tuple5<String, String, String, String, Double> accumulator) {
                                return accumulator;
                            }

                            @Override
                            public Tuple5<String, String, String, String, Double> merge(
                                    Tuple5<String, String, String, String, Double> a,
                                    Tuple5<String, String, String, String, Double> b) {
                                return new Tuple5<>(a.f0, a.f1, a.f2, a.f3, a.f4 + b.f4);
                            }
                        });
    }
}