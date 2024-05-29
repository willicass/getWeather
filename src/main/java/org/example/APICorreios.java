package org.example;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;
import org.json.JSONObject;


public class APICorreios {

    public static String buscarCEP(String cep) throws IOException {

        String apiUrl = "https://viacep.com.br/ws/" + cep + "/json/";

        URL url = new URL(apiUrl);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.connect();

        int responseCode = conn.getResponseCode();
        if (responseCode != 200) {
            throw new RuntimeException("HTTP response code: " + responseCode);
        }

        Scanner scanner = new Scanner(url.openStream());
        StringBuilder response = new StringBuilder();
        while (scanner.hasNext()) {
            response.append(scanner.nextLine());
        }
        scanner.close();

        return response.toString();
    }
    public static String extrairLocalidadeDoJSON(String json) {
        JSONObject jsonObject = new JSONObject(json);
        return jsonObject.getString("localidade");
    }
}
