package com.company.demo.Weather;

public class Main {

    private double humidity;
    private double temp_min;
    private double temp_max;
    private double feels_like;
    private double temp;
    private double pressure;

    public double getHumidity() {
        return humidity;
    }

    public void setHumidity(double humidity) {
        this.humidity = humidity;
    }

    //public double getTempMin() {return this.temp_min;}

    public double getTemp_min() {return temp_min;}

    public void setTempMin(double temp_min) {
        this.temp_min = temp_min;
    }

    //public double getTempMax() {return temp_max;}
    public double getTemp_max() {return temp_max;}

    public void setTempMax(double tempMax) {this.temp_max = tempMax;}

    public double getFeels_like() {
        return feels_like;
    }

    public void setFeelsLike(double feels_like) {this.feels_like = feels_like;}

    public double getTemp() {return temp;}

    public void setTemp(double temp) {
        this.temp = temp;
    }

    public double getPressure() {
        return pressure;
    }

    public void setPressure(double pressure) {
        this.pressure = pressure;
    }
}
