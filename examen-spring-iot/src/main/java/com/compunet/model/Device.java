package com.compunet.model;

public class Device {

    private Integer id;
    private String name;
    private String serialNumber;
    private String type;
    private double maxValue;
    private double minValue;
    private long samplingPeriod;   // ms entre mediciones consecutivas
    private long timeTolerance;    // ms de margen permitido
    private String unit;

    public Device() {
    }

    public Device(Integer id, String name, String serialNumber, String type,
                  double maxValue, double minValue, long samplingPeriod,
                  long timeTolerance, String unit) {
        this.id = id;
        this.name = name;
        this.serialNumber = serialNumber;
        this.type = type;
        this.maxValue = maxValue;
        this.minValue = minValue;
        this.samplingPeriod = samplingPeriod;
        this.timeTolerance = timeTolerance;
        this.unit = unit;
    }

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getSerialNumber() { return serialNumber; }
    public void setSerialNumber(String serialNumber) { this.serialNumber = serialNumber; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public double getMaxValue() { return maxValue; }
    public void setMaxValue(double maxValue) { this.maxValue = maxValue; }

    public double getMinValue() { return minValue; }
    public void setMinValue(double minValue) { this.minValue = minValue; }

    public long getSamplingPeriod() { return samplingPeriod; }
    public void setSamplingPeriod(long samplingPeriod) { this.samplingPeriod = samplingPeriod; }

    public long getTimeTolerance() { return timeTolerance; }
    public void setTimeTolerance(long timeTolerance) { this.timeTolerance = timeTolerance; }

    public String getUnit() { return unit; }
    public void setUnit(String unit) { this.unit = unit; }
}
