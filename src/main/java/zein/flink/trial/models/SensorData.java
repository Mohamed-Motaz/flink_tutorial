package zein.flink.trial.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SensorData {

    @SerializedName("sensorType")
    @Expose
    private String sensorType;
    @SerializedName("value")
    @Expose
    private Double value;
    @SerializedName("sensorId")
    @Expose
    private Long sensorId;
    @SerializedName("timestamp")
    @Expose
    private Long timestamp;

    public SensorData(String sensorType, Double value, Long sensorId, Long timestamp ) {
        this.sensorType = sensorType;
        this.value = value;
        this.sensorId = sensorId;
        this.timestamp = timestamp;
    }

    public String getSensorType() {
        return sensorType;
    }

    public void setSensorType(String sensorType) {
        this.sensorType = sensorType;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    public Long getSensorId() {
        return sensorId;
    }

    public void setSensorId(Long sensorId) {
        this.sensorId = sensorId;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(SensorData.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("sensorType");
        sb.append('=');
        sb.append(((this.sensorType == null) ? "<null>" : this.sensorType));
        sb.append(',');
        sb.append("value");
        sb.append('=');
        sb.append(((this.value == null) ? "<null>" : this.value));
        sb.append(',');
        sb.append("sensorId");
        sb.append('=');
        sb.append(((this.sensorId == null) ? "<null>" : this.sensorId));
        sb.append(',');
        sb.append("timestamp");
        sb.append('=');
        sb.append(((this.timestamp == null) ? "<null>" : this.timestamp));
        sb.append(',');
        if (sb.charAt((sb.length() - 1)) == ',') {
            sb.setCharAt((sb.length() - 1), ']');
        } else {
            sb.append(']');
        }
        return sb.toString();
    }

    @Override
    public int hashCode() {
        int result = 1;
        result = ((result * 31) + ((this.value == null) ? 0 : this.value.hashCode()));
        result = ((result * 31) + ((this.sensorType == null) ? 0 : this.sensorType.hashCode()));
        result = ((result * 31) + ((this.sensorId == null) ? 0 : this.sensorId.hashCode()));
        result = ((result * 31) + ((this.timestamp == null) ? 0 : this.timestamp.hashCode()));
        return result;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof SensorData) == false) {
            return false;
        }
        SensorData rhs = ((SensorData) other);
        return (((((this.value == rhs.value) || ((this.value != null) && this.value.equals(rhs.value))) && ((this.sensorType == rhs.sensorType) || ((this.sensorType != null) && this.sensorType.equals(rhs.sensorType)))) && ((this.sensorId == rhs.sensorId) || ((this.sensorId != null) && this.sensorId.equals(rhs.sensorId)))) && ((this.timestamp == rhs.timestamp) || ((this.timestamp != null) && this.timestamp.equals(rhs.timestamp))));
    }

}
