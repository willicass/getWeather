package org.example;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;
import java.net.URLEncoder;
import org.json.JSONObject;
import org.json.JSONArray;


public class APIClima {

    private static final String API_KEY = "1b23c55e0668e990a1c50d8c69472b07";

    public static String buscarClima(String city) throws IOException {
        String cityEncode = URLEncoder.encode(city, "UTF-8");
        String apiUrl = "https://api.openweathermap.org/data/2.5/weather?q=" + cityEncode + "&appid=" + API_KEY;

        URL url = new URL(apiUrl);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.connect();

        int responseCode = conn.getResponseCode();
        if (responseCode != 200) {
            throw new RuntimeException("HTTP response code: " + apiUrl + responseCode);
        }

        Scanner scanner = new Scanner(url.openStream());
        StringBuilder response = new StringBuilder();
        while (scanner.hasNext()) {
            response.append(scanner.nextLine());
        }
        scanner.close();

        return response.toString();
    }
    public static String extrairClimaDoJSON(String json) {
        JSONObject jsonObject = new JSONObject(json);
        JSONArray weatherArray = jsonObject.getJSONArray("weather");
        JSONObject weatherObject = weatherArray.getJSONObject(0); // Assumindo que estamos interessados apenas na primeira entrada do array
        String description = weatherObject.getString("description");
        return description;
    }
}