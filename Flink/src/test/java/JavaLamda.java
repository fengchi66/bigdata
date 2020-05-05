//import org.apache.flink.api.java.tuple.Tuple2;
//import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
//
///**
// * @author wufc
// * @create 2020-04-12 11:03 下午
// */
//public class JavaLamda {
//    public static void main(String[] args) {
//        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
//        env.setParallelism(1);
//
//        env.socketTextStream("localhost",9999)
//                .map(line-> Tuple2(line,1))
//    }
//}
