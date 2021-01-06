package com.weather.models;

import de.jensd.fx.glyphs.weathericons.WeatherIconName;
import org.json.JSONObject;

public class Weather {

    private float humidity;
    private float pressure;
    private float wind_speed;
    private int time_code;
    private int temp_min;
    private int temp_max;
    private int temp;
    private String name;
    private String desc;

    public Weather(String json){
        update(json);
    }

    public void update(String json){
        JSONObject data=new JSONObject(json);
        this.name=data.getString("name");
        this.humidity=data.getJSONObject("main").getFloat("humidity");
        this.pressure=data.getJSONObject("main").getFloat("pressure");
        float CONST = 273.15f;
        this.temp_min=(int)(data.getJSONObject("main").getFloat("temp_min")- CONST);
        this.temp_max=(int)(data.getJSONObject("main").getFloat("temp_max")- CONST);
        this.temp=(int)(data.getJSONObject("main").getFloat("temp")- CONST);
        this.wind_speed=data.getJSONObject("wind").getFloat("speed");
        this.time_code=data.getJSONArray("weather").getJSONObject(0).getInt("id");
        this.desc=data.getJSONArray("weather").getJSONObject(0).getString("description");
    }

    public float getHumidity() {return humidity;}

    public float getPressure() {return pressure;}

    public int getTemp() {return temp;}

    public int getTempMax() {return temp_max;}

    public int getTempMin() {return temp_min;}

    public float getWindSpeed() {return wind_speed;}

    public WeatherIconName getTimeIcon(){
        WeatherIconName weatherIconName=WeatherIconName.CLOUD;
        if(time_code>=200&&time_code<=232)weatherIconName=WeatherIconName.THUNDERSTORM;
        if(time_code>=300&&time_code<=321)weatherIconName=WeatherIconName.WINDY;
        if(time_code>=500&&time_code<=531)weatherIconName=WeatherIconName.RAIN;
        if(time_code>=600&&time_code<=622)weatherIconName=WeatherIconName.SNOW;
        if(time_code>=701&&time_code<=781)weatherIconName=WeatherIconName.TORNADO;
        if(time_code==800)weatherIconName=WeatherIconName.DAY_SUNNY;
        if(time_code>=801)weatherIconName=WeatherIconName.CLOUDY;
        return weatherIconName;
    }

    public String getDesc() {return desc;}

    public String getName() {return name;}

}
