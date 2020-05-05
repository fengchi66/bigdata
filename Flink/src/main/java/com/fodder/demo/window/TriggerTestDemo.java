package com.fodder.demo.window;

import com.fodder.demo.bean.OrderLogBean;
import demo.utils.DateUtils;
import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.api.common.functions.ReduceFunction;
import org.apache.flink.api.java.functions.KeySelector;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.streaming.api.TimeCharacteristic;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.timestamps.BoundedOutOfOrdernessTimestampExtractor;
import org.apache.flink.streaming.api.windowing.time.Time;
import org.apache.flink.streaming.api.windowing.triggers.ContinuousEventTimeTrigger;
import org.apache.flink.streaming.api.windowing.triggers.CountTrigger;

import java.util.Arrays;

/**
 * @author wufc
 * @create 2020-04-20 11:20 下午
 */
public class TriggerTestDemo {
    public static void main(String[] args) throws Exception{
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        env.setStreamTimeCharacteristic(TimeCharacteristic.EventTime);

        env.fromCollection(Arrays.asList(
                new OrderLogBean(1L, "pen", 1, DateUtils.getTime("2020-04-19 11:01")),
                new OrderLogBean(1L, "pen", 2, DateUtils.getTime("2020-04-19 11:02")),
                new OrderLogBean(1L, "pen", 3, DateUtils.getTime("2020-04-19 11:03")),
                new OrderLogBean(1L, "pen", 1, DateUtils.getTime("2020-04-19 11:05")),
                new OrderLogBean(1L, "pen", 2, DateUtils.getTime("2020-04-19 11:06")),
                new OrderLogBean(1L, "pen", 3, DateUtils.getTime("2020-04-19 11:07"))
        ))
                .assignTimestampsAndWatermarks(new BoundedOutOfOrdernessTimestampExtractor<OrderLogBean>(Time.seconds(5)) {
                    @Override
                    public long extractTimestamp(OrderLogBean element) {
                        return element.timeStamp;
                    }
                })
                .map(new MapFunction<OrderLogBean, Tuple2<Long,Integer>>() {
                    @Override
                    public Tuple2<Long, Integer> map(OrderLogBean value) throws Exception {
                        return Tuple2.of(value.userId,value.amount);
                    }
                })
                .keyBy(new KeySelector<Tuple2<Long, Integer>, Long>() {
                    @Override
                    public Long getKey(Tuple2<Long, Integer> value) throws Exception {
                        return value.f0;  //以id分组
                    }
                })
                .timeWindow(Time.minutes(10))
//                .trigger(CountTrigger.of(2))
                .trigger(ContinuousEventTimeTrigger.of(Time.minutes(3)))
                .reduce(new ReduceFunction<Tuple2<Long, Integer>>() {
                    @Override
                    public Tuple2<Long, Integer> reduce(Tuple2<Long, Integer> value1, Tuple2<Long, Integer> value2) throws Exception {
                        return Tuple2.of(value1.f0,value1.f1 + value2.f1);
                    }
                })
                .print();

                env.execute("TriggerTestDemo");

    }
}
