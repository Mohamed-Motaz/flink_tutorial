package zein.flink.trial;

import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

import zein.flink.trial.sources.SensorDataSourceFunction;

//run this file using
// MAVEN_OPTS="--add-opens java.base/java.lang=ALL-UNNAMED" mvn -Dexec.mainClass=zein.flink.trial.FirstExample exec:java
public class FirstExample {
    public static void main(String[] args) throws Exception {

        //1. We must create a stream execution environment'
        StreamExecutionEnvironment env = StreamExecutionEnvironment
            .getExecutionEnvironment();

        //2. Create a DataSream from some source
        DataStream<String> inpuDataStream = env.addSource(new SensorDataSourceFunction());

        //3. To do something with the data stream (call some of the Flink operators)

        //4. The results from step 3 should be sent to a data sink
        inpuDataStream
        // .process(new ProcessFunction<String, String>(){
        //     @Override
        //     public void processElement(String value, Context ctx, Collector<String> collector) throws Exception {
        //         System.out.println(value);
        //         collector.collect(value);
        //     }
        // })
        .print();
        //5, execute

        
        env.execute(FirstExample.class.getName());
    }
}
