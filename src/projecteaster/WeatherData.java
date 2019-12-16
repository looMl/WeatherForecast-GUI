package projecteaster;

import com.google.gson.*;
import com.google.gson.reflect.*;
import org.json.simple.parser.JSONParser;
import org.json.simple.JSONObject;
import org.json.simple.JSONArray;
import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.simple.parser.ParseException;

/**
 * Class which manage the API requests and the WeatherData objects
 *
 * @author Luca Landolfo
 */
public class WeatherData implements Serializable {

    //attributes
    private double temperature;
    private double tempMax;
    private double tempMin;
    private double humidity;
    private double pressure;
    private double windSpeed;
    private String description;
    private String icon;

    /**
     * Constructor with basic input validation
     *
     * @param city
     * @param countryCode
     */
    public WeatherData(String city, String countryCode) {
        try {

            if (city.equals("") || countryCode.equals("")) {
                getWeather("Verona", "IT");
            } else if (!city.matches("(.)*(\\d)(.)*") || !countryCode.matches("(.)*(\\d)(.)*")) {
                getWeather(city, countryCode);
            } else {
                System.out.println("Input values not correct.");
            }

        } catch (JSONException ex) {
            Logger.getLogger(WeatherData.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Blank contructor
     */
    public WeatherData() {
    }

    /**
     * Method that does the API request
     *
     * @param city
     * @param countryCode
     * @return if the API request was successfull True, else False
     * @throws JSONException
     */
    public final boolean getWeather(String city, String countryCode) throws JSONException {
        final String API_KEY = "d5909dae99e1aa809ed7219ee2f1544b";
        if (city.equals("") || countryCode.equals("")) {
            city = "Verona";
            countryCode = "IT";
        }
        String URLString = "http://api.openweathermap.org/data/2.5/weather?q=" + city + "," + countryCode + "&apikey=" + API_KEY + "&units=metric";
        StringBuilder result = new StringBuilder();
        try {
            URL url = new URL(URLString);
            URLConnection conn = url.openConnection();
            try (BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()))) {
                String line;
                while ((line = rd.readLine()) != null) {
                    result.append(line);
                }
            }
        } catch (IOException e) {
            System.out.print(e.getMessage());
        }

        return setValues(result);
    }

    /**
     * Method that creates a WeatherData object from the API request if its
     * correct
     *
     * @param result
     * @return if the API request was successfull True, else False
     */
    private boolean setValues(StringBuilder result) {
        if (jsonToMap(result.toString()) != null) {
            Map<String, Object> response = jsonToMap(result.toString());

            Map<String, Object> mainMap = jsonToMap(response.get("main").toString());
            setTemperature((double) mainMap.get("temp"));
            setTempMin((double) mainMap.get("temp_min"));
            setTempMax((double) mainMap.get("temp_max"));
            setHumidity((double) mainMap.get("humidity"));
            setPressure((double) mainMap.get("pressure"));

            Map<String, Object> windMap = jsonToMap(response.get("wind").toString());
            setWindSpeed((double) windMap.get("speed"));

            Map<String, Object> weatherMap = parseJSONArray(result.toString());
            setDescription((String) weatherMap.get("description"));
            setIcon((String) weatherMap.get("icon"));
            return true;
        } else {
            return false;
        }
    }

    /**
     * Converts normal json attributes to an HashMap
     *
     * @param str
     * @return HashMap with json data
     */
    private Map<String, Object> jsonToMap(String str) {
        Map<String, Object> map = new Gson().fromJson(
                str, new TypeToken<HashMap<String, Object>>() {
                }.getType()
        );

        return map;
    }

    /**
     * Parses the json and gets the data from JsonArrays, then puts everything
     * in an HashMap
     *
     * @param str
     * @return HashMap with jsonArray data
     */
    private Map<String, Object> parseJSONArray(String str) {
        Map<String, Object> map = new HashMap<String, Object>() {
        };
        String desc = "";
        String icon = "";
        try {
            JSONParser parser = new JSONParser();
            Object jsonText = parser.parse(str);
            JSONObject json = (JSONObject) jsonText;
            JSONArray wh = (JSONArray) json.get("weather");
            for (int i = 0; i < wh.size(); i++) {
                JSONObject element = (JSONObject) wh.get(i);
                desc = (String) element.get("description");
                icon = (String) element.get("icon");
            }
            map.put("description", desc);
            map.put("icon", icon);

        } catch (ParseException e) {
            System.out.print("");
        }

        return map;
    }

    /**
     * Getter temperature
     *
     * @return
     */
    public double getTemperature() {
        return temperature;
    }

    /**
     * Setter temperature
     *
     * @param temperature
     */
    private void setTemperature(double temperature) {
        this.temperature = temperature;
    }

    /**
     * Getter Max temperature
     *
     * @return
     */
    public double getTempMax() {
        return tempMax;
    }

    /**
     * Setter Max temperature
     *
     * @param tempMax
     */
    private void setTempMax(double tempMax) {
        this.tempMax = tempMax;
    }

    /**
     * Getter Min temperature
     *
     * @return
     */
    public double getTempMin() {
        return tempMin;
    }

    /**
     * Setter Min temperature
     *
     * @param tempMin
     */
    private void setTempMin(double tempMin) {
        this.tempMin = tempMin;
    }

    /**
     * Getter humidity
     *
     * @return
     */
    public double getHumidity() {
        return humidity;
    }

    /**
     * Setter humidity
     *
     * @param humidity
     */
    private void setHumidity(double humidity) {
        this.humidity = humidity;
    }

    /**
     * Getter pressure
     *
     * @return
     */
    public double getPressure() {
        return pressure;
    }

    /**
     * Setter pressure
     *
     * @param pressure
     */
    private void setPressure(double pressure) {
        this.pressure = pressure;
    }

    /**
     * Getter wind speed
     *
     * @return
     */
    public double getWindSpeed() {
        return windSpeed;
    }

    /**
     * Setter wind speed
     *
     * @param windSpeed
     */
    private void setWindSpeed(double windSpeed) {
        this.windSpeed = windSpeed;
    }

    /**
     * Getter description
     *
     * @return
     */
    public String getDescription() {
        return description;
    }

    /**
     * Setter description
     *
     * @param description
     */
    private void setDescription(String description) {
        this.description = description;
    }

    /**
     * Getter icon
     *
     * @return
     */
    public String getIcon() {
        return icon;
    }

    /**
     * Setter icons
     *
     * @param icon
     */
    private void setIcon(String icon) {
        this.icon = icon;
    }

    /**
     * toString method of WeatherData object
     *
     * @return
     */
    @Override
    public String toString() {
        return "WeatherData{" + "temperature=" + temperature + ", tempMax=" + tempMax
                + ", tempMin=" + tempMin + ", humidity=" + humidity
                + ", pressure=" + pressure + ", windSpeed=" + windSpeed
                + ", description=" + description + ", icon=" + icon + '}';
    }

}
