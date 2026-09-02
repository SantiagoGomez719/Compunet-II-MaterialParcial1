package com.compunet.model;

public class Measurement {

    private Integer id;
    private long timestamp;
    private double value;
    private Integer assetId; // el examen pide este nombre en vez de "deviceId"

    public Measurement() {
    }

    public Measurement(Integer id, long timestamp, double value, Integer assetId) {
        this.id = id;
        this.timestamp = timestamp;
        this.value = value;
        this.assetId = assetId;
    }

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public long getTimestamp() { return timestamp; }
    public void setTimestamp(long timestamp) { this.timestamp = timestamp; }

    public double getValue() { return value; }
    public void setValue(double value) { this.value = value; }

    public Integer getAssetId() { return assetId; }
    public void setAssetId(Integer assetId) { this.assetId = assetId; }
}
