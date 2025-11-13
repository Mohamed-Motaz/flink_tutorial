package zein.flink.trial.sources;

import java.time.LocalDateTime;
import java.util.Random;

import org.apache.flink.streaming.api.functions.source.SourceFunction;

public class FirstExampleDataSource implements SourceFunction<String>{

    Random random = new Random();
    @Override
    public void run(SourceContext<String> ctx) throws Exception {
        for (int i = 1; i <= 1000; i++){
            int id = i;
            LocalDateTime timestamp = LocalDateTime.now();
            String message = String.format("ID: %d Timestamp: %s", id, timestamp.toString());
            ctx.collect(message);
            Thread.sleep(random.nextInt(1500));
        }
    }

    @Override
    public void cancel() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
}
