package zein.flink.trial.sources;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.apache.flink.streaming.api.functions.source.SourceFunction;
import org.json.JSONObject;


public class SensorDataSourceFunction implements SourceFunction<String>{

    int n = 2000;
    Random random = new Random();

    List<String> sensorTypes = List.of("temp", "co", "pres", "hum");
    Map<String, List<Integer>> sensorIdsBySensorType = new HashMap<>();
    Map<String, Double> minValueBySensorType = new HashMap<>();
    Map<String, Double> maxValueBySensorType = new HashMap<>();

    public SensorDataSourceFunction(){
        // populate sensor types and their corresponding sensor IDs
        sensorIdsBySensorType.put("temp", List.of(1,2,3,4));
        sensorIdsBySensorType.put("co", List.of(5, 6, 7, 8));
        sensorIdsBySensorType.put("pres", List.of(9, 10));
        sensorIdsBySensorType.put("hum", List.of(11, 12, 13));

        minValueBySensorType.put("temp",15D);
        minValueBySensorType.put("co", 0.5);
        minValueBySensorType.put("pres", 101.0);
        minValueBySensorType.put("hum", 30.0);

        maxValueBySensorType.put("temp", 30D);
        maxValueBySensorType.put("co", 5.0);
        maxValueBySensorType.put("pres", 103.0);
        maxValueBySensorType.put("hum", 50.0);
    }

    private double generateRandomValue(double min, double max){
        return min + (max - min) * random.nextDouble();
    }

    @Override
    public void run(SourceContext<String> ctx) throws Exception {
        /*
         {
            "sensorId": "___",
            "sensorType": "___", //possible values: temp, co, pres, hum
            "value": ___,
            "timestamp": ___ (localdate time object/long)
         } 
         */
        for (int i = 0; i < n; i++){
            JSONObject object = new JSONObject();
            String randomSensorType = sensorTypes.get(random.nextInt(sensorTypes.size()));
            List<Integer> possibleSensorIds = sensorIdsBySensorType.get(randomSensorType);
            int randomSensorId = possibleSensorIds.get(random.nextInt(possibleSensorIds.size()));
            double minValue = minValueBySensorType.get(randomSensorType);
            double maxValue = maxValueBySensorType.get(randomSensorType);
            double randomValue = generateRandomValue(minValue, maxValue);
            if (i%25 == 0 && random.nextBoolean()){ // create some anomalies
                System.out.println("Generating anomaly for sensor type: " + randomSensorType);
                randomValue = maxValue + random.nextInt(20) + 10;
            }
            LocalDateTime timestamp = LocalDateTime.now();    

            object.put("sensorId", randomSensorId);
            object.put("sensorType", randomSensorType);
            object.put("value", randomValue);
            object.put("timestamp", timestamp.atZone(ZoneId.systemDefault()).toEpochSecond());

            ctx.collect(object.toString());
            Thread.sleep(random.nextInt(500));
        }
    }

    @Override
    public void cancel() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public static void main(String[] args) {
        SensorDataSourceFunction source = new SensorDataSourceFunction();
        System.out.println(source.generateRandomValue(10, 20));
    }
    
    
}
