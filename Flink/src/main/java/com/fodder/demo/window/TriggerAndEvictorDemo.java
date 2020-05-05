package com.fodder.demo.window;

import com.fodder.demo.bean.OrderLogBean;
import demo.utils.DateUtils;
import org.apache.flink.api.common.functions.AggregateFunction;
import org.apache.flink.api.common.state.ValueState;
import org.apache.flink.api.common.state.ValueStateDescriptor;
import org.apache.flink.api.common.typeinfo.TypeHint;
import org.apache.flink.api.common.typeinfo.TypeInformation;
import org.apache.flink.api.java.functions.KeySelector;
import org.apache.flink.api.java.tuple.Tuple3;
import org.apache.flink.streaming.api.TimeCharacteristic;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.timestamps.BoundedOutOfOrdernessTimestampExtractor;
import org.apache.flink.streaming.api.functions.windowing.ProcessWindowFunction;
import org.apache.flink.streaming.api.windowing.time.Time;
import org.apache.flink.streaming.api.windowing.triggers.*;
import org.apache.flink.streaming.api.windowing.windows.TimeWindow;
import org.apache.flink.util.Collector;

import java.util.Arrays;

/**
 * @author wufc
 * @create 2020-04-19 3:02 下午
 */
public class TriggerAndEvictorDemo {
    public static void main(String[] args) throws Exception {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        env.setStreamTimeCharacteristic(TimeCharacteristic.EventTime);

        env.fromCollection(Arrays.asList(
                new OrderLogBean(1L, "pen", 1, DateUtils.getTime("2020-04-19 11:01")),
//                new OrderLogBean(2L, "pen", 1, DateUtils.getTime("2020-04-19 11:01")),
                new OrderLogBean(1L, "pen", 2, DateUtils.getTime("2020-04-19 11:02")),
//                new OrderLogBean(2L, "pen", 2, DateUtils.getTime("2020-04-19 11:02")),
                new OrderLogBean(1L, "pen", 3, DateUtils.getTime("2020-04-19 11:03")),
//                new OrderLogBean(2L, "pen", 3, DateUtils.getTime("2020-04-19 11:03")),
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
                .keyBy(new KeySelector<OrderLogBean, Long>() {
                    @Override
                    public Long getKey(OrderLogBean value) {
                        return value.userId;
                    }
                })
                .timeWindow(Time.minutes(10))
//                .trigger(EventTimeTrigger.create())
//                .trigger(CountTrigger.of(2))  // 以一定条数间隔多次触发
//                .trigger(new MyCountTrigger(2L))
//                .trigger(ContinuousEventTimeTrigger.of(Time.minutes(2)))
                .trigger(PurgingTrigger.of(ContinuousEventTimeTrigger.of(Time.minutes(2))))
                .aggregate(new AggSum(), new SumResult())
                .print();

        env.execute("Test");


    }
}

class AggSum implements AggregateFunction<OrderLogBean, Integer, Integer> {

    @Override
    public Integer createAccumulator() {
        return 0;
    }

    @Override
    public Integer add(OrderLogBean value, Integer accumulator) {
        return accumulator + value.amount;
    }

    @Override
    public Integer getResult(Integer accumulator) {
        return accumulator;
    }

    @Override
    public Integer merge(Integer a, Integer b) {
        return a + b;
    }
}

class SumResult extends ProcessWindowFunction<Integer, Tuple3<Long, Integer, String>, Long, TimeWindow> {

    @Override
    public void process(Long aLong,
                        Context context,
                        Iterable<Integer> elements,
                        Collector<Tuple3<Long, Integer, String>> out) throws Exception {
        out.collect(Tuple3.of(aLong, elements.iterator().next(), DateUtils.getDate(context.window().getEnd())));

    }
}

class MyCountTrigger extends Trigger<Object, TimeWindow> {

    private Long maxCount;

    public MyCountTrigger(Long maxCount) {
        this.maxCount = maxCount;
    }

    //    定义一个状态，记录数据的条数
    private ValueState<Long> countState;


    @Override //对每到达窗口的数据
    public TriggerResult onElement(Object element,
                                   long timestamp,
                                   TimeWindow window,
                                   TriggerContext ctx) throws Exception {

        ValueStateDescriptor<Long> longValueStateDescriptor =
                new ValueStateDescriptor<>("count-state", TypeInformation.of(new TypeHint<Long>() {
                }), 0L);

        countState = ctx.getPartitionedState(longValueStateDescriptor);

        Long count = countState.value();
        count += 1;
        countState.update(count);

        if (count >= maxCount) {
            countState.clear();
            return TriggerResult.FIRE;  //触发执行
        } else {
            return TriggerResult.CONTINUE;
        }

    }

    @Override
    public TriggerResult onProcessingTime(long time, TimeWindow window, TriggerContext ctx) throws Exception {
        return TriggerResult.CONTINUE;
    }

    @Override
    public TriggerResult onEventTime(long time, TimeWindow window, TriggerContext ctx) throws Exception {
        return TriggerResult.CONTINUE;
    }

    @Override
    public void clear(TimeWindow window, TriggerContext ctx) throws Exception {
        countState.clear();
    }
}
