package zein.flink.trial;

import org.apache.flink.api.common.functions.FilterFunction;
import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.util.Collector;

import com.google.gson.Gson;

import zein.flink.trial.models.SensorData;
import zein.flink.trial.sources.SensorDataSourceFunction;

//run this file using
// mvn clean package && MAVEN_OPTS="--add-opens java.base/java.lang=ALL-UNNAMED --add-opens java.base/java.util=ALL-UNNAMED" mvn -Pdev -Dexec.classpathScope=runtime -Dexec.mainClass=zein.flink.trial.FirstExample exec:java
public class FirstExample {
    public static void main(String[] args) throws Exception {

        //1. We must create a stream execution environment'
        StreamExecutionEnvironment env = StreamExecutionEnvironment
            .getExecutionEnvironment();

        //2. Create a DataSream from some source
        DataStream<String> sensorsDataStrStream = env.addSource(new SensorDataSourceFunction());

        //3. To do something with the data stream (call some of the Flink operators)
        
        // MAP operator DataStream<String> -> DataStream<SensorData>
        DataStream<SensorData> sensorDataStream = sensorsDataStrStream
        .map((MapFunction<String, SensorData>)(String value) -> new Gson().fromJson(value, SensorData.class) //JSON string to SensorData object
        );

        // FILTER operator DataStream<SensorData> -> DataStream<SensorData> 
        DataStream<SensorData> filtereDataStream = sensorDataStream
        .filter(new FilterFunction<SensorData>(){
            @Override
            public boolean filter(SensorData value) throws Exception {
                Long sensorId = value.getSensorId();
                return sensorId == 4 || sensorId == 8 || sensorId == 10 || sensorId == 13;
            }           
        });
        // FLAT MAP operator DataStream<SensorData> -> DataStream<SensorData> 
        DataStream<SensorData> sensorDataFlatMapped = filtereDataStream.flatMap(new FlatMapFunction<SensorData, SensorData>(){
            @Override
            public void flatMap(SensorData value, org.apache.flink.util.Collector<SensorData> collector) throws Exception {
                Long sensorId = value.getSensorId();;
                if (sensorId == 4){
                    generateDuplicateSensorData(1L, 3L, value, collector);
                }else if (sensorId == 8){
                    generateDuplicateSensorData(5L, 7L, value, collector);
                }else if (sensorId == 10){
                    generateDuplicateSensorData(9L, 9L, value, collector);
                }else if (sensorId == 13){
                    generateDuplicateSensorData(11L, 12L, value, collector);
                }
            }           
        });

        //4. The results from step 3 should be sent to a data sink
        sensorDataFlatMapped
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

    private static void generateDuplicateSensorData(long startId, long endId, SensorData sensorData, Collector<SensorData> collector){
        for (long i = startId; i <= endId; i++){
            SensorData newSensorData = new SensorData(
                sensorData.getSensorType(),
                sensorData.getValue(),
                i,
                sensorData.getTimestamp()
            );
            collector.collect(newSensorData);
        }
        collector.collect(sensorData);
    }
}
