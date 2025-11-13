package zein.flink.trial;

import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

public class FirstExample {
    public static void main(String[] args) throws Exception {

        //1. We must create a stream execution environment'
        StreamExecutionEnvironment env = StreamExecutionEnvironment
            .getExecutionEnvironment();
        //2. Create a DataSream from some source
        //3. To do something with the data stream (call some of the Flink operators)
        //4. The results from step 3 should be sent to a data sink
        //5, execute

        

        env.fromElements(1,2,3,4,5).print();

        env.execute();
    }
}
